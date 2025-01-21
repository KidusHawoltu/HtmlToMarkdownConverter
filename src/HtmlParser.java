import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class HtmlParser {
    HtmlSchema htmlSchema;
    HtmlToMarkdown htmlToMarkdown;
    List<HtmlToken> _tokenList;
    int _index;
    int _columns;
    Stack<String> _openedTags;
    Stack<Boolean> _isOpenedTagClosed;
    Stack<String> _lists;
    List<Integer> _listCount;
    boolean _parseFailed;
    boolean _canAddToStack;
    StringBuilder mdString;
    boolean _countColumn;
    String url;

    public HtmlParser(String inputString) {
        htmlSchema = new HtmlSchema();
        htmlToMarkdown = new HtmlToMarkdown();
        HtmlLexicalAnalyzer htmlLexicalAnalyzer = new HtmlLexicalAnalyzer(inputString);
        _tokenList = htmlLexicalAnalyzer.GetTokens();
        _index = 0;
        _columns = 0;
        _openedTags = new Stack<>();
        _isOpenedTagClosed = new Stack<>();
        _lists = new Stack<>();
        _listCount = new ArrayList<>();
        _parseFailed = false;
        _canAddToStack = false;
        _countColumn = false;
        mdString = new StringBuilder();
    }

    public String GetMd() {
        while (!_tokenList.isEmpty() && !_parseFailed && _tokenList.get(_index) != HtmlToken.EOF) {
            ParseNextToken();
            _index++;
        }
        if (_parseFailed) return null;
        while (!_isOpenedTagClosed.isEmpty()) {
            if (!_isOpenedTagClosed.pop()) return null;
        }
        return _parseFailed ? null : mdString.toString();
    }

    private void ParseNextToken(){
        if (_tokenList.get(_index) == HtmlToken.OpeningTagStart) {
//            System.out.println("1");
            if (_tokenList.get(_index + 1) != HtmlToken.Name || _canAddToStack) {
                System.out.println("Invalid syntax around Line: " +
                        SymbolTable.symbolTable.get(_index).line());
                _parseFailed = true;
            }
            else _canAddToStack = true;
        }
        else if (_tokenList.get(_index) == HtmlToken.Name){
//            System.out.println("2");
            String elementName = SymbolTable.symbolTable.get(_index).value().get("name");
            if (!htmlSchema.IsElement(elementName)){
                System.out.println("Unknown tag " + elementName + " on line: " +
                        SymbolTable.symbolTable.get(_index).line());
                _parseFailed = true;
                return;
            }
            if (_canAddToStack) {
                if (!_openedTags.isEmpty()) {
                    _isOpenedTagClosed.add(htmlSchema.ClosesOnNextElementStart(_openedTags.peek(), elementName));
                }
                if (elementName.equals("img")) mdString.append(AddImage());
                if (elementName.equals("table")) _countColumn = true;
                if (elementName.equals("a") && SymbolTable.symbolTable.get(_index + 1) != null) url = SymbolTable.symbolTable.get(_index + 1).value().get("href");
                if (_countColumn && elementName.equals("th")) _columns++;
                _openedTags.add(elementName);
                mdString.append(htmlToMarkdown.convert(elementName, true));
            }
            else {
                int pos = _openedTags.search(elementName);
                if (pos == -1) {
                    System.out.println("Invalid syntax around Line: " +
                            SymbolTable.symbolTable.get(_index).line());
                    _parseFailed = true;
                    return;
                }
                while (!_openedTags.isEmpty() && !_openedTags.peek().equals(elementName)) {
                    if (!htmlSchema.ClosesOnParentElementEnd(_openedTags.pop())) {
                        System.out.println("Invalid syntax around Line: " +
                                SymbolTable.symbolTable.get(_index).line());
                        _parseFailed = true;
                        return;
                    }
                }
                _openedTags.pop();
                if (!_isOpenedTagClosed.isEmpty()) _isOpenedTagClosed.pop();
                mdString.append(htmlToMarkdown.convert(elementName, false));
                if (_countColumn && elementName.equals("tr")) {
                    mdString.append(AddTable());
                    _countColumn = false;
                    _columns = 0;
                }
                if (elementName.equals("a")) mdString.append(AddLink());
                _canAddToStack = true;
            }

            if (SymbolTable.symbolTable.get(_index).value().get("name").equals("ol") ||
                    SymbolTable.symbolTable.get(_index).value().get("name").equals("ul") ||
                    SymbolTable.symbolTable.get(_index).value().get("name").equals("li")) {
                mdString.append(AddList());
            }
        }
        else if (_tokenList.get(_index) == HtmlToken.Attribute && !_canAddToStack) {
//            System.out.println("3");
            System.out.println("Invalid syntax around Line: " +
                    SymbolTable.symbolTable.get(_index).line());
            _parseFailed = true;
        }
        else if (_tokenList.get(_index) == HtmlToken.TagEnd) {
//            System.out.println("4");
            if (!_canAddToStack) {
                System.out.println("Invalid syntax around Line: " +
                        SymbolTable.symbolTable.get(_index).line());
                _parseFailed = true;
                return;
            }
            if (!_openedTags.isEmpty() && htmlSchema.IsEmptyElement(_openedTags.peek())) {
                _openedTags.pop();
                if (!_isOpenedTagClosed.isEmpty()) _isOpenedTagClosed.pop();
            }
            _canAddToStack = false;
        }
        else if (_tokenList.get(_index) == HtmlToken.EmptyTagEnd) {
//            System.out.println("5");
            if (!_canAddToStack || _openedTags.isEmpty()) {
                System.out.println("Invalid syntax around Line: " +
                        SymbolTable.symbolTable.get(_index).line());
                _parseFailed = true;
                return;
            }
            else if (!htmlSchema.IsEmptyElement(_openedTags.peek())) {
                System.out.println(_openedTags.peek() + " is not self closing tag around Line: " +
                        SymbolTable.symbolTable.get(_index).line());
                _parseFailed = true;
                return;
            }

            _openedTags.pop();
            _canAddToStack = false;
        }
        else if (_tokenList.get(_index) == HtmlToken.ClosingTagStart) {
//            System.out.println("6");
            if (!_canAddToStack && _openedTags.isEmpty()) {
                System.out.println("Invalid syntax around Line: " +
                        SymbolTable.symbolTable.get(_index).line());
                _parseFailed = true;
                return;
            }
            _canAddToStack = false;
        }
        else if (_tokenList.get(_index) == HtmlToken.Text) {
//            System.out.println("7");
            if (_canAddToStack) {
                System.out.println("Invalid syntax around Line: " +
                        SymbolTable.symbolTable.get(_index).line());
                _parseFailed = true;
                return;
            }
            mdString.append(AddText(SymbolTable.symbolTable.get(_index).value().get("text")));
        }
        else if (_tokenList.get(_index) == HtmlToken.Comment) {
//            System.out.println("8");
            if (_canAddToStack) {
                System.out.println("Invalid syntax around Line: " +
                        SymbolTable.symbolTable.get(_index).line());
                _parseFailed = true;
                return;
            }
            mdString.append(AddComment(SymbolTable.symbolTable.get(_index).value().get("comment")));
        }
    }

    private String AddComment(String comment) {
        return "\n[comment]: # (" + comment + ")\n";
    }

    private String AddText(String text) {
        return text;
    }

    private String AddImage() {
        if (SymbolTable.symbolTable.get(_index + 1) == null) {
            System.out.println("Invalid syntax around Line: " +
                    SymbolTable.symbolTable.get(_index).line());
            _parseFailed = true;
            return "";
        }

        String src = SymbolTable.symbolTable.get(_index + 1).value().get("src");
        String alt = SymbolTable.symbolTable.get(_index + 1).value().get("alt");
        if (src == null) {
            System.out.println("src attribute is necessary for image tag in Line: " +
                    SymbolTable.symbolTable.get(_index + 1).line());
            _parseFailed = true;
            return "";
        }

        return "\n![" + (alt == null ? "" : alt) + "](" + src + ")\n";
    }

    private String AddTable() {
        return "\n| " + "--- | ".repeat(Math.max(0, _columns));
    }

    private String AddLink() {
        if (url == null) {
            System.out.println("href attribute is necessary for a tag in Line: " +
                    SymbolTable.symbolTable.get(_index).line());
            _parseFailed = true;
            return "";
        }
        String val = "(" + url + ")\n";
        url = "";
        return val;
    }

    private String AddList() {
        String elementName = SymbolTable.symbolTable.get(_index).value().get("name");
        if (_index > 0 && _tokenList.get(_index - 1) == HtmlToken.OpeningTagStart && !elementName.equals("li")) {
            _lists.add(elementName);
            _listCount.add(0);
        }
        else if (_index > 0 && _tokenList.get(_index - 1) == HtmlToken.ClosingTagStart && !elementName.equals("li")) {
            _lists.pop();
            _listCount.removeLast();
        }
        else if (_index > 0 && elementName.equals("li") && _tokenList.get(_index - 1) != HtmlToken.ClosingTagStart) {
            _listCount.set(_listCount.size() - 1, _listCount.getLast() + 1);
            return "\n" + "\t".repeat(_lists.size() - 1) + (_lists.peek().equals("ul") ? "- " : (_listCount.getLast() + ". "));
        }
        return "";
    }

    @Override
    public String toString() {
        return "\n\nHtmlParser{" +
                "_index=" + _index +
                " (" + _tokenList.get(_index) + ")" +
                ", _columns=" + _columns +
                ", _openedTags=" + _openedTags +
                ", _isOpenedTagClosed=" + _isOpenedTagClosed +
                ", _parseFailed=" + _parseFailed +
                ", \nmdString=" + mdString +
                ", \n_canAddToStack=" + _canAddToStack +
                ", _countColumn=" + _countColumn +
                ", url='" + url + '\'' +
                '}';
    }
}
