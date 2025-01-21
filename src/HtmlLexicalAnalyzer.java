import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HtmlLexicalAnalyzer {
    private final String _inputString;
    private int _index;
    private int _line;
    private int _currentCharacterCode;
    private char _currentCharacter;
    private int _lookAheadCharacterCode;
    private char _lookAheadCharacter;
    private char _previousCharacter;
    private boolean _ignoreNextWhitespace;
    private boolean _isCurrentCharacterEntity;
    private final HtmlSchema htmlSchema = new HtmlSchema();

    private final List<HtmlToken> _tokenList = new ArrayList<>();

    public HtmlLexicalAnalyzer(String inputStirng) {
        _inputString = inputStirng;
        _index = 0;
        _line = 1;
        _currentCharacterCode = 0;
        _currentCharacter = ' ';
        _lookAheadCharacterCode = !inputStirng.isEmpty() ? inputStirng.charAt(0) : 0;
        _lookAheadCharacter = (char) _lookAheadCharacterCode;
        _previousCharacter = ' ';
        _ignoreNextWhitespace = true;
        GetNextCharacter();
    }

    public List<HtmlToken> GetTokens() {
        System.out.println("a");
        while (_tokenList.isEmpty() || _tokenList.getLast() != HtmlToken.EOF) GetNextContentToken();
        System.out.println("b");
        return _tokenList;
    }

    private boolean IsGoodForNameStart(char character) {
        return character == '_' || Character.isLetter(character);
    }

    private boolean IsGoodForName(char character) {
        return IsGoodForNameStart(character) ||
                        character == '.' ||
                        character == '-' ||
                        character == ':' ||
                        Character.isDigit(character);
    }

    private boolean IsAtTagStart(){
        return _currentCharacter == '<' && (_lookAheadCharacter == '/' || IsGoodForNameStart(_lookAheadCharacter)) && !_isCurrentCharacterEntity;
    }

    private boolean IsAtEndOfStream(){
        return _currentCharacterCode == -1;
    }

    private boolean IsAtDirectiveStart(){
        return (_currentCharacter == '<' && (_lookAheadCharacter == '!' || _lookAheadCharacter == '?') && !_isCurrentCharacterEntity);
    }

    private void ReadLookAheadCharacter() {
        if (_lookAheadCharacterCode != -1) {
            _lookAheadCharacterCode = (_index < _inputString.length() - 1) ? _inputString.charAt(++_index) : -1;
            _lookAheadCharacter = (char) _lookAheadCharacterCode;
        }
    }

    private void GetNextCharacter() {
        if (_currentCharacterCode == -1) {
            System.out.println("GetNextCharacter method called at the end of a stream");
            return;
        }

        if (_lookAheadCharacter == '\n') _line++;

        _previousCharacter = _currentCharacter;
        _currentCharacter = _lookAheadCharacter;
        _currentCharacterCode = _lookAheadCharacterCode;
        _isCurrentCharacterEntity = false;
        ReadLookAheadCharacter();

        if (_currentCharacter == '&') {
            if (_lookAheadCharacter == '#') {
                // numeric entity - parse digits - &#DDDDD;
                int entityCode;
                entityCode = 0;
                ReadLookAheadCharacter();

                // largest numeric entity is 7 characters
                for (int i = 0; i < 7 && Character.isDigit(_lookAheadCharacter); i++) {
                    entityCode = 10 * entityCode + (_lookAheadCharacterCode - (int)'0');
                    ReadLookAheadCharacter();
                }

                if (_lookAheadCharacter == ';') {
                    // correct format - advance
                    ReadLookAheadCharacter();
                    _currentCharacterCode = entityCode;

                    // if this is out of range it will set the character to '?'
                    _currentCharacter = (char)_currentCharacterCode;

                    // as far as we are concerned, this is an entity
                    _isCurrentCharacterEntity = true;
                }
                else {
                    // not an entity, set next character to the current lookahread character
                    // we would have eaten up some digits
                    _currentCharacter = _lookAheadCharacter;
                    _currentCharacterCode = _lookAheadCharacterCode;
                    ReadLookAheadCharacter();
                    _isCurrentCharacterEntity = false;
                }
            }
            else if (Character.isLetter(_lookAheadCharacter)) {
                // entity is written as a string
                StringBuilder entity = new StringBuilder();

                // maximum length of string entities is 10 characters
                for (int i = 0; i < 10 && (Character.isLetter(_lookAheadCharacter) || Character.isDigit(_lookAheadCharacter)); i++) {
                    entity.append(_lookAheadCharacter);
                    ReadLookAheadCharacter();
                }

                if (_lookAheadCharacter == ';') {
                    // advance
                    ReadLookAheadCharacter();

                    if ( htmlSchema.IsEntity(entity.toString())) {
                        _currentCharacter =  htmlSchema.EntityCharacterValue(entity.toString());
                        _currentCharacterCode = _currentCharacter;
                        _isCurrentCharacterEntity = true;
                    }
                    else {
                        // just skip the whole thing - invalid entity
                        // move on to the next character
                        _currentCharacter = _lookAheadCharacter;
                        _currentCharacterCode = _lookAheadCharacterCode;
                        ReadLookAheadCharacter();

                        // not an entity
                        _isCurrentCharacterEntity = false;
                    }
                }
                else {
                    // skip whatever we read after the ampersand
                    // set next character and move on
                    _currentCharacter = _lookAheadCharacter;
                    ReadLookAheadCharacter();
                    _isCurrentCharacterEntity = false;
                }
            }
        }
    }

    private void GetNextContentToken() {
        System.out.println(this);
        System.out.println(SymbolTable.symbolTable);
        if (IsAtEndOfStream()) {
            System.out.println("c");
            _tokenList.add(HtmlToken.EOF);
            return;
        }
        if (IsAtTagStart()) {
            GetNextCharacter();

            if (_currentCharacter == '/') {
                _tokenList.add(HtmlToken.ClosingTagStart);
                SymbolTable.symbolTable.put(_tokenList.size() - 1, new SymbolTableValue(_line, Map.of()));
                // advance
                GetNextCharacter();
                _ignoreNextWhitespace = false; // Whitespaces after closing tags are significant
            }
            else {
                _tokenList.add(HtmlToken.OpeningTagStart);
                SymbolTable.symbolTable.put(_tokenList.size() - 1, new SymbolTableValue(_line, Map.of()));
                _ignoreNextWhitespace = true; // Whitespaces after opening tags are insignificant
            }

            while (_tokenList.getLast() != HtmlToken.TagEnd && _tokenList.getLast() != HtmlToken.EmptyTagEnd) GetNextTagToken();
        }
        else if (IsAtDirectiveStart()) {
            GetNextCharacter();
            if (_currentCharacter == '!' && _lookAheadCharacter == '-') {
                _tokenList.add(HtmlToken.Comment);
                SymbolTable.symbolTable.put(_tokenList.size() - 1, new SymbolTableValue(_line, Map.of(
                        "comment", ReadComment()
                )));
            }
            else {
                // not a comment, should be something like DOCTYPE
                // skip till the next tag ender
                ReadUnknownDirective();
                SkipWhiteSpace();
            }
        }
        else {
            // read text content, unless you encounter a tag
            _tokenList.add(HtmlToken.Text);
            StringBuilder _textValue = new StringBuilder();
            while (!IsAtEndOfStream() && !IsAtTagStart() && !IsAtDirectiveStart()) {
                if (_currentCharacter <= ' ') {
                    if (!_ignoreNextWhitespace) _textValue.append(' ');
                    _ignoreNextWhitespace = true; // and keep ignoring the following whitespaces
                }
                else {
                    _textValue.append(_currentCharacter);
                    _ignoreNextWhitespace = false;
                }
                GetNextCharacter();
            }
            SymbolTable.symbolTable.put(_tokenList.size() - 1, new SymbolTableValue(_line, Map.of(
                    "text", _textValue.toString()
            )));
        }
    }

    private void GetNextTagToken() {
        System.out.println(this + " tag");
        System.out.println(SymbolTable.symbolTable);
//        try {
//            Thread.sleep(100);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        if (IsAtEndOfStream()) {
            _tokenList.add(HtmlToken.EOF);
            return;
        }

        SkipWhiteSpace();

        if (_currentCharacter == '>' && !_isCurrentCharacterEntity) {
            _tokenList.add(HtmlToken.TagEnd);
            SymbolTable.symbolTable.put(_tokenList.size() - 1, new SymbolTableValue(_line, Map.of()));
            GetNextCharacter();
            SkipWhiteSpace();
        }
        else if (_currentCharacter == '/' && _lookAheadCharacter == '>') {
            // could be start of closing of empty tag
            _tokenList.add(HtmlToken.EmptyTagEnd);
            SymbolTable.symbolTable.put(_tokenList.size() - 1, new SymbolTableValue(_line, Map.of()));
            GetNextCharacter();
            GetNextCharacter();
            _ignoreNextWhitespace = false; // Whitespace after no-scope tags are significant
        }
        else if (_tokenList.getLast() == HtmlToken.Name && IsGoodForNameStart(_currentCharacter)){
            _tokenList.add(HtmlToken.Attribute);
            StringBuilder _attrName = new StringBuilder();
            while (!IsAtEndOfStream() && _currentCharacter != '=') {
                _attrName.append(_currentCharacter);
                GetNextCharacter();
            }
            GetNextCharacter();
            SymbolTable.symbolTable.put(_tokenList.size() - 1, new SymbolTableValue(_line, Map.of(
                    _attrName.toString(), ReadAttributeValue()
            )));
        }
        else if (_tokenList.getLast() == HtmlToken.Attribute && IsGoodForNameStart(_currentCharacter)){
            _tokenList.add(HtmlToken.Attribute);
            StringBuilder _attrName = new StringBuilder();
            while (!IsAtEndOfStream() && _currentCharacter != '=') {
                _attrName.append(_currentCharacter);
                GetNextCharacter();
            }
            GetNextCharacter();
            SymbolTable.symbolTable.put(_tokenList.size() - 1, new SymbolTableValue(_line, Map.of(
                    _attrName.toString(), ReadAttributeValue()
            )));
        }
        else if (IsGoodForNameStart(_currentCharacter)) {
            _tokenList.add(HtmlToken.Name);
            StringBuilder _tagName = new StringBuilder();
            while (!IsAtEndOfStream() && IsGoodForName(_currentCharacter)) {
                _tagName.append(_currentCharacter);
                GetNextCharacter();
            }
            SymbolTable.symbolTable.put(_tokenList.size() - 1, new SymbolTableValue(_line, Map.of(
                    "name", _tagName.toString()
            )));
        }
    }
    
    private String ReadAttributeValue() {
        StringBuilder _value = new StringBuilder();
        if (IsAtEndOfStream()) return "";
        _value.append(_currentCharacter);
        GetNextCharacter();
        
        if (_previousCharacter == '\'') {
            while (!IsAtEndOfStream() && _currentCharacter != '\'') {
                _value.append(_currentCharacter);
                GetNextCharacter();
            }
        }
        else if (_previousCharacter == '\"') {
            while (!IsAtEndOfStream() && _currentCharacter != '\"') {
                _value.append(_currentCharacter);
                GetNextCharacter();
            }
        }
        else {
            while (!IsAtEndOfStream() && IsGoodForName(_currentCharacter)) {
                _value.append(_currentCharacter);
                GetNextCharacter();
            }
        }
        
        if (!IsAtEndOfStream() && (_currentCharacter == '\'' || _currentCharacter == '\"')) {
            _value.append(_currentCharacter);
            GetNextCharacter();
        }

        System.out.println("attr " + _value);
        
        return _value.toString();
    }

    private void ReadUnknownDirective() {
        while (!IsAtEndOfStream() && !(_currentCharacter == '>' && !_isCurrentCharacterEntity)) {
            GetNextCharacter();
        }

        if (!IsAtEndOfStream()) GetNextCharacter();
    }

    private String ReadComment() {
        StringBuilder _comment = new StringBuilder();

        // advance to the next character, so that to be at the start of comment value
        GetNextCharacter(); // get first '-'
        GetNextCharacter(); // get second '-'
        GetNextCharacter(); // get first character of comment content

        while (true) {
            // Read text until end of comment
            // Note that in many actual html pages comments end with "!>" (while xml standard is "-->")
            while (!IsAtEndOfStream() && !(_currentCharacter == '-' && _lookAheadCharacter == '-' || _currentCharacter == '!' && _lookAheadCharacter == '>')) {
                _comment.append(_currentCharacter);
                GetNextCharacter();
            }

            // Finish comment reading
            GetNextCharacter();
            if (_previousCharacter == '-' && _currentCharacter == '-' && _lookAheadCharacter == '>') {
                // Standard comment end. Eat it and exit the loop
                GetNextCharacter(); // get '>'
                break;
            }
            else if (_previousCharacter == '!' && _currentCharacter == '>') {
                // Nonstandard but possible comment end - '!>'. Exit the loop
                break;
            }
            else {
                // Not an end. Save character and continue reading
                _comment.append(_previousCharacter);
            }
        }

        // Read end of comment combination
        if (_currentCharacter == '>') {
            GetNextCharacter();
        }

        return _comment.toString();
    }

    private void SkipWhiteSpace() {
        while (true) {
            if (IsAtDirectiveStart()) {
                GetNextCharacter();
                // Skip processing instruction, comments
                while (!IsAtEndOfStream() && _currentCharacter != '>') GetNextCharacter();
                if (_currentCharacter == '>') GetNextCharacter();
            }

            if (!Character.isWhitespace(_currentCharacter)) break;
            GetNextCharacter();
        }
    }

    @Override
    public String toString() {
        return "\n\nHtmlLexicalAnalyzer{" +
                ", _index=" + _index +
                ", _line=" + _line +
                ", _currentCharacter=" + _currentCharacter +
                " (" + _currentCharacterCode + ")" +
                ", _lookAheadCharacter=" + _lookAheadCharacter +
                " (" + _lookAheadCharacterCode + ")" +
                ", _previousCharacter=" + _previousCharacter +
                ", _ignoreNextWhitespace=" + _ignoreNextWhitespace +
                ", _isCurrentCharacterEntity=" + _isCurrentCharacterEntity +
                ", \n_tokenList=" + _tokenList +
                '}';
    }
}
