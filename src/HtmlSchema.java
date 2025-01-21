import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HtmlSchema {
    public HtmlSchema(){
        InitializeInlineElements();
        InitializeBlockElements();
        InitializeEmptyElements();
        InitializeElementsClosingOnParentElementEnd();
        InitializeElementsClosingOnNewElementStart();
        InitializeHtmlCharacterEntities();
    }

    // html element names
    private ArrayList<String> _htmlInlineElements;
    private ArrayList<String> _htmlBlockElements;

    // list of html empty element names
    private ArrayList<String> _htmlEmptyElements;

    // names of html elements for which closing tags are optional, and close when the outer nested element closes
    private ArrayList<String> _htmlElementsClosingOnParentElementEnd;

    // names of elements closing the colgroup element
    private ArrayList<String> _htmlElementsClosingColgroup;

    // names of elements closing the dd element
    private ArrayList<String> _htmlElementsClosingDD;

    // names of elements closing the dt element
    private ArrayList<String> _htmlElementsClosingDT;

    // names of elements closing the li element
    private ArrayList<String> _htmlElementsClosingLI;

    // names of elements closing the tbody element
    private ArrayList<String> _htmlElementsClosingTbody;

    // names of elements closing the td element
    private ArrayList<String> _htmlElementsClosingTD;

    // names of elements closing the tfoot element
    private ArrayList<String> _htmlElementsClosingTfoot;

    // names of elements closing the thead element
    private ArrayList<String> _htmlElementsClosingThead;

    // names of elements closing the th element
    private ArrayList<String> _htmlElementsClosingTH;

    // names of elements closing the tr element
    private ArrayList<String> _htmlElementsClosingTR;

    // html character entities hashtable
    private HashMap<String, Character> _htmlCharacterEntities;

    private void InitializeInlineElements() {
        _htmlInlineElements = new ArrayList<>(List.of("a", "b"));
        _htmlInlineElements.add("a");
        _htmlInlineElements.add("abbr");
        _htmlInlineElements.add("acronym");
        _htmlInlineElements.add("address");
        _htmlInlineElements.add("audio");
        _htmlInlineElements.add("b");
        _htmlInlineElements.add("bdo");
        _htmlInlineElements.add("big");
        _htmlInlineElements.add("button");
        _htmlInlineElements.add("code");
        _htmlInlineElements.add("del"); // deleted text (strikethrough)
        _htmlInlineElements.add("dfn");
        _htmlInlineElements.add("em");
        _htmlInlineElements.add("font");
        _htmlInlineElements.add("i");
        _htmlInlineElements.add("ins"); // inserted text (just underlined)
        _htmlInlineElements.add("kbd");
        _htmlInlineElements.add("label");
        _htmlInlineElements.add("legend");
        _htmlInlineElements.add("mark");
        _htmlInlineElements.add("q");
        _htmlInlineElements.add("s"); // strike-through text style
        _htmlInlineElements.add("samp");
        _htmlInlineElements.add("small");
        _htmlInlineElements.add("span");
        _htmlInlineElements.add("strike");
        _htmlInlineElements.add("strong");
        _htmlInlineElements.add("sub");
        _htmlInlineElements.add("sup");
        _htmlInlineElements.add("u");
        _htmlInlineElements.add("var");
        _htmlInlineElements.add("video");
    }

    private void InitializeBlockElements() {
        _htmlBlockElements = new ArrayList<>();

        _htmlBlockElements.add("blockquote");
        _htmlBlockElements.add("body");
        _htmlBlockElements.add("header");
        _htmlBlockElements.add("footer");
        _htmlBlockElements.add("caption");
        _htmlBlockElements.add("center");
        _htmlBlockElements.add("cite");
        _htmlBlockElements.add("dd");
        _htmlBlockElements.add("dir");
        _htmlBlockElements.add("div");
        _htmlBlockElements.add("dl");
        _htmlBlockElements.add("dt");
        _htmlBlockElements.add("form");
        _htmlBlockElements.add("h1");
        _htmlBlockElements.add("h2");
        _htmlBlockElements.add("h3");
        _htmlBlockElements.add("h4");
        _htmlBlockElements.add("h5");
        _htmlBlockElements.add("h6");
        _htmlBlockElements.add("html");
        _htmlBlockElements.add("li");
        _htmlBlockElements.add("menu");
        _htmlBlockElements.add("ol");
        _htmlBlockElements.add("p");
        _htmlBlockElements.add("pre");
        _htmlBlockElements.add("table");
        _htmlBlockElements.add("tbody");
        _htmlBlockElements.add("td");
        _htmlBlockElements.add("textarea");
        _htmlBlockElements.add("tfoot");
        _htmlBlockElements.add("th");
        _htmlBlockElements.add("thead");
        _htmlBlockElements.add("tr");
        _htmlBlockElements.add("tt");
        _htmlBlockElements.add("ul");
        _htmlBlockElements.add("applet");
        _htmlBlockElements.add("base");
        _htmlBlockElements.add("basefont");
        _htmlBlockElements.add("colgroup");
        _htmlBlockElements.add("fieldset");
        _htmlBlockElements.add("frameset");
        _htmlBlockElements.add("head");
        _htmlBlockElements.add("iframe");
        _htmlBlockElements.add("map");
        _htmlBlockElements.add("noframes");
        _htmlBlockElements.add("noscript");
        _htmlBlockElements.add("object");
        _htmlBlockElements.add("optgroup");
        _htmlBlockElements.add("option");
        _htmlBlockElements.add("script");
        _htmlBlockElements.add("select");
        _htmlBlockElements.add("style");
        _htmlBlockElements.add("title");
    }

    private void InitializeEmptyElements() {
        _htmlEmptyElements = new ArrayList<>();
        _htmlEmptyElements.add("area");
        _htmlEmptyElements.add("base");
        _htmlEmptyElements.add("basefont");
        _htmlEmptyElements.add("br");
        _htmlEmptyElements.add("col");
        _htmlEmptyElements.add("frame");
        _htmlEmptyElements.add("hr");
        _htmlEmptyElements.add("img");
        _htmlEmptyElements.add("input");
        _htmlEmptyElements.add("isindex");
        _htmlEmptyElements.add("link");
        _htmlEmptyElements.add("meta");
        _htmlEmptyElements.add("param");
        _htmlEmptyElements.add("track");
        _htmlEmptyElements.add("source");
    }

    private void InitializeElementsClosingOnParentElementEnd() {
        _htmlElementsClosingOnParentElementEnd = new ArrayList<>();
        _htmlElementsClosingOnParentElementEnd.add("body");
        _htmlElementsClosingOnParentElementEnd.add("colgroup");
        _htmlElementsClosingOnParentElementEnd.add("dd");
        _htmlElementsClosingOnParentElementEnd.add("dt");
        _htmlElementsClosingOnParentElementEnd.add("head");
        _htmlElementsClosingOnParentElementEnd.add("html");
        _htmlElementsClosingOnParentElementEnd.add("li");
        _htmlElementsClosingOnParentElementEnd.add("p");
        _htmlElementsClosingOnParentElementEnd.add("tbody");
        _htmlElementsClosingOnParentElementEnd.add("td");
        _htmlElementsClosingOnParentElementEnd.add("tfoot");
        _htmlElementsClosingOnParentElementEnd.add("thead");
        _htmlElementsClosingOnParentElementEnd.add("th");
        _htmlElementsClosingOnParentElementEnd.add("tr");
    }

    private void InitializeElementsClosingOnNewElementStart() {
        _htmlElementsClosingColgroup = new ArrayList<>();
        _htmlElementsClosingColgroup.add("colgroup");
        _htmlElementsClosingColgroup.add("tr");
        _htmlElementsClosingColgroup.add("thead");
        _htmlElementsClosingColgroup.add("tfoot");
        _htmlElementsClosingColgroup.add("tbody");

        _htmlElementsClosingDD = new ArrayList<>();
        _htmlElementsClosingDD.add("dd");
        _htmlElementsClosingDD.add("dt");

        _htmlElementsClosingDT = new ArrayList<>();
        _htmlElementsClosingDT.add("dd");
        _htmlElementsClosingDT.add("dt");

        _htmlElementsClosingLI = new ArrayList<>();
        _htmlElementsClosingLI.add("li");

        _htmlElementsClosingTbody = new ArrayList<>();
        _htmlElementsClosingTbody.add("tbody");
        _htmlElementsClosingTbody.add("thead");
        _htmlElementsClosingTbody.add("tfoot");

        _htmlElementsClosingTR = new ArrayList<>();
        _htmlElementsClosingTR.add("thead");
        _htmlElementsClosingTR.add("tfoot");
        _htmlElementsClosingTR.add("tbody");
        _htmlElementsClosingTR.add("tr");

        _htmlElementsClosingTD = new ArrayList<>();
        _htmlElementsClosingTD.add("td");
        _htmlElementsClosingTD.add("th");
        _htmlElementsClosingTD.add("tr");
        _htmlElementsClosingTD.add("tbody");
        _htmlElementsClosingTD.add("tfoot");
        _htmlElementsClosingTD.add("thead");

        _htmlElementsClosingTH = new ArrayList<>();
        _htmlElementsClosingTH.add("td");
        _htmlElementsClosingTH.add("th");
        _htmlElementsClosingTH.add("tr");
        _htmlElementsClosingTH.add("tbody");
        _htmlElementsClosingTH.add("tfoot");
        _htmlElementsClosingTH.add("thead");

        _htmlElementsClosingThead = new ArrayList<>();
        _htmlElementsClosingThead.add("tbody");
        _htmlElementsClosingThead.add("tfoot");

        _htmlElementsClosingTfoot = new ArrayList<>();
        _htmlElementsClosingTfoot.add("tbody");
        _htmlElementsClosingTfoot.add("thead");
    }

    private void InitializeHtmlCharacterEntities() {
        _htmlCharacterEntities = new HashMap<>();
        _htmlCharacterEntities.put("Aacute", (char)193);
        _htmlCharacterEntities.put("aacute", (char)225);
        _htmlCharacterEntities.put("Acirc", (char)194);
        _htmlCharacterEntities.put("acirc", (char)226);
        _htmlCharacterEntities.put("acute", (char)180);
        _htmlCharacterEntities.put("AElig", (char)198);
        _htmlCharacterEntities.put("aelig", (char)230);
        _htmlCharacterEntities.put("Agrave", (char)192);
        _htmlCharacterEntities.put("agrave", (char)224);
        _htmlCharacterEntities.put("alefsym", (char)8501);
        _htmlCharacterEntities.put("Alpha", (char)913);
        _htmlCharacterEntities.put("alpha", (char)945);
        _htmlCharacterEntities.put("amp", (char)38);
        _htmlCharacterEntities.put("and", (char)8743);
        _htmlCharacterEntities.put("ang", (char)8736);
        _htmlCharacterEntities.put("Aring", (char)197);
        _htmlCharacterEntities.put("aring", (char)229);
        _htmlCharacterEntities.put("asymp", (char)8776);
        _htmlCharacterEntities.put("Atilde", (char)195);
        _htmlCharacterEntities.put("atilde", (char)227);
        _htmlCharacterEntities.put("Auml", (char)196);
        _htmlCharacterEntities.put("auml", (char)228);
        _htmlCharacterEntities.put("bdquo", (char)8222);
        _htmlCharacterEntities.put("Beta", (char)914);
        _htmlCharacterEntities.put("beta", (char)946);
        _htmlCharacterEntities.put("brvbar", (char)166);
        _htmlCharacterEntities.put("bull", (char)8226);
        _htmlCharacterEntities.put("cap", (char)8745);
        _htmlCharacterEntities.put("Ccedil", (char)199);
        _htmlCharacterEntities.put("ccedil", (char)231);
        _htmlCharacterEntities.put("cent", (char)162);
        _htmlCharacterEntities.put("Chi", (char)935);
        _htmlCharacterEntities.put("chi", (char)967);
        _htmlCharacterEntities.put("circ", (char)710);
        _htmlCharacterEntities.put("clubs", (char)9827);
        _htmlCharacterEntities.put("cong", (char)8773);
        _htmlCharacterEntities.put("copy", (char)169);
        _htmlCharacterEntities.put("crarr", (char)8629);
        _htmlCharacterEntities.put("cup", (char)8746);
        _htmlCharacterEntities.put("curren", (char)164);
        _htmlCharacterEntities.put("dagger", (char)8224);
        _htmlCharacterEntities.put("Dagger", (char)8225);
        _htmlCharacterEntities.put("darr", (char)8595);
        _htmlCharacterEntities.put("dArr", (char)8659);
        _htmlCharacterEntities.put("deg", (char)176);
        _htmlCharacterEntities.put("Delta", (char)916);
        _htmlCharacterEntities.put("delta", (char)948);
        _htmlCharacterEntities.put("diams", (char)9830);
        _htmlCharacterEntities.put("divide", (char)247);
        _htmlCharacterEntities.put("Eacute", (char)201);
        _htmlCharacterEntities.put("eacute", (char)233);
        _htmlCharacterEntities.put("Ecirc", (char)202);
        _htmlCharacterEntities.put("ecirc", (char)234);
        _htmlCharacterEntities.put("Egrave", (char)200);
        _htmlCharacterEntities.put("egrave", (char)232);
        _htmlCharacterEntities.put("empty", (char)8709);
        _htmlCharacterEntities.put("emsp", (char)8195);
        _htmlCharacterEntities.put("ensp", (char)8194);
        _htmlCharacterEntities.put("Epsilon", (char)917);
        _htmlCharacterEntities.put("epsilon", (char)949);
        _htmlCharacterEntities.put("equiv", (char)8801);
        _htmlCharacterEntities.put("Eta", (char)919);
        _htmlCharacterEntities.put("eta", (char)951);
        _htmlCharacterEntities.put("ETH", (char)208);
        _htmlCharacterEntities.put("eth", (char)240);
        _htmlCharacterEntities.put("Euml", (char)203);
        _htmlCharacterEntities.put("euml", (char)235);
        _htmlCharacterEntities.put("euro", (char)8364);
        _htmlCharacterEntities.put("exist", (char)8707);
        _htmlCharacterEntities.put("fnof", (char)402);
        _htmlCharacterEntities.put("forall", (char)8704);
        _htmlCharacterEntities.put("frac12", (char)189);
        _htmlCharacterEntities.put("frac14", (char)188);
        _htmlCharacterEntities.put("frac34", (char)190);
        _htmlCharacterEntities.put("frasl", (char)8260);
        _htmlCharacterEntities.put("Gamma", (char)915);
        _htmlCharacterEntities.put("gamma", (char)947);
        _htmlCharacterEntities.put("ge", (char)8805);
        _htmlCharacterEntities.put("gt", (char)62);
        _htmlCharacterEntities.put("harr", (char)8596);
        _htmlCharacterEntities.put("hArr", (char)8660);
        _htmlCharacterEntities.put("hearts", (char)9829);
        _htmlCharacterEntities.put("hellip", (char)8230);
        _htmlCharacterEntities.put("Iacute", (char)205);
        _htmlCharacterEntities.put("iacute", (char)237);
        _htmlCharacterEntities.put("Icirc", (char)206);
        _htmlCharacterEntities.put("icirc", (char)238);
        _htmlCharacterEntities.put("iexcl", (char)161);
        _htmlCharacterEntities.put("Igrave", (char)204);
        _htmlCharacterEntities.put("igrave", (char)236);
        _htmlCharacterEntities.put("image", (char)8465);
        _htmlCharacterEntities.put("infin", (char)8734);
        _htmlCharacterEntities.put("int", (char)8747);
        _htmlCharacterEntities.put("Iota", (char)921);
        _htmlCharacterEntities.put("iota", (char)953);
        _htmlCharacterEntities.put("iquest", (char)191);
        _htmlCharacterEntities.put("isin", (char)8712);
        _htmlCharacterEntities.put("Iuml", (char)207);
        _htmlCharacterEntities.put("iuml", (char)239);
        _htmlCharacterEntities.put("Kappa", (char)922);
        _htmlCharacterEntities.put("kappa", (char)954);
        _htmlCharacterEntities.put("Lambda", (char)923);
        _htmlCharacterEntities.put("lambda", (char)955);
        _htmlCharacterEntities.put("lang", (char)9001);
        _htmlCharacterEntities.put("laquo", (char)171);
        _htmlCharacterEntities.put("larr", (char)8592);
        _htmlCharacterEntities.put("lArr", (char)8656);
        _htmlCharacterEntities.put("lceil", (char)8968);
        _htmlCharacterEntities.put("ldquo", (char)8220);
        _htmlCharacterEntities.put("le", (char)8804);
        _htmlCharacterEntities.put("lfloor", (char)8970);
        _htmlCharacterEntities.put("lowast", (char)8727);
        _htmlCharacterEntities.put("loz", (char)9674);
        _htmlCharacterEntities.put("lrm", (char)8206);
        _htmlCharacterEntities.put("lsaquo", (char)8249);
        _htmlCharacterEntities.put("lsquo", (char)8216);
        _htmlCharacterEntities.put("lt", (char)60);
        _htmlCharacterEntities.put("macr", (char)175);
        _htmlCharacterEntities.put("mdash", (char)8212);
        _htmlCharacterEntities.put("micro", (char)181);
        _htmlCharacterEntities.put("middot", (char)183);
        _htmlCharacterEntities.put("minus", (char)8722);
        _htmlCharacterEntities.put("Mu", (char)924);
        _htmlCharacterEntities.put("mu", (char)956);
        _htmlCharacterEntities.put("nabla", (char)8711);
        _htmlCharacterEntities.put("nbsp", (char)160);
        _htmlCharacterEntities.put("ndash", (char)8211);
        _htmlCharacterEntities.put("ne", (char)8800);
        _htmlCharacterEntities.put("ni", (char)8715);
        _htmlCharacterEntities.put("not", (char)172);
        _htmlCharacterEntities.put("notin", (char)8713);
        _htmlCharacterEntities.put("nsub", (char)8836);
        _htmlCharacterEntities.put("Ntilde", (char)209);
        _htmlCharacterEntities.put("ntilde", (char)241);
        _htmlCharacterEntities.put("Nu", (char)925);
        _htmlCharacterEntities.put("nu", (char)957);
        _htmlCharacterEntities.put("Oacute", (char)211);
        _htmlCharacterEntities.put("ocirc", (char)244);
        _htmlCharacterEntities.put("OElig", (char)338);
        _htmlCharacterEntities.put("oelig", (char)339);
        _htmlCharacterEntities.put("Ograve", (char)210);
        _htmlCharacterEntities.put("ograve", (char)242);
        _htmlCharacterEntities.put("oline", (char)8254);
        _htmlCharacterEntities.put("Omega", (char)937);
        _htmlCharacterEntities.put("omega", (char)969);
        _htmlCharacterEntities.put("Omicron", (char)927);
        _htmlCharacterEntities.put("omicron", (char)959);
        _htmlCharacterEntities.put("oplus", (char)8853);
        _htmlCharacterEntities.put("or", (char)8744);
        _htmlCharacterEntities.put("ordf", (char)170);
        _htmlCharacterEntities.put("ordm", (char)186);
        _htmlCharacterEntities.put("Oslash", (char)216);
        _htmlCharacterEntities.put("oslash", (char)248);
        _htmlCharacterEntities.put("Otilde", (char)213);
        _htmlCharacterEntities.put("otilde", (char)245);
        _htmlCharacterEntities.put("otimes", (char)8855);
        _htmlCharacterEntities.put("Ouml", (char)214);
        _htmlCharacterEntities.put("ouml", (char)246);
        _htmlCharacterEntities.put("para", (char)182);
        _htmlCharacterEntities.put("part", (char)8706);
        _htmlCharacterEntities.put("permil", (char)8240);
        _htmlCharacterEntities.put("perp", (char)8869);
        _htmlCharacterEntities.put("Phi", (char)934);
        _htmlCharacterEntities.put("phi", (char)966);
        _htmlCharacterEntities.put("pi", (char)960);
        _htmlCharacterEntities.put("piv", (char)982);
        _htmlCharacterEntities.put("plusmn", (char)177);
        _htmlCharacterEntities.put("pound", (char)163);
        _htmlCharacterEntities.put("prime", (char)8242);
        _htmlCharacterEntities.put("Prime", (char)8243);
        _htmlCharacterEntities.put("prod", (char)8719);
        _htmlCharacterEntities.put("prop", (char)8733);
        _htmlCharacterEntities.put("Psi", (char)936);
        _htmlCharacterEntities.put("psi", (char)968);
        _htmlCharacterEntities.put("quot", (char)34);
        _htmlCharacterEntities.put("radic", (char)8730);
        _htmlCharacterEntities.put("rang", (char)9002);
        _htmlCharacterEntities.put("raquo", (char)187);
        _htmlCharacterEntities.put("rarr", (char)8594);
        _htmlCharacterEntities.put("rArr", (char)8658);
        _htmlCharacterEntities.put("rceil", (char)8969);
        _htmlCharacterEntities.put("rdquo", (char)8221);
        _htmlCharacterEntities.put("real", (char)8476);
        _htmlCharacterEntities.put("reg", (char)174);
        _htmlCharacterEntities.put("rfloor", (char)8971);
        _htmlCharacterEntities.put("Rho", (char)929);
        _htmlCharacterEntities.put("rho", (char)961);
        _htmlCharacterEntities.put("rlm", (char)8207);
        _htmlCharacterEntities.put("rsaquo", (char)8250);
        _htmlCharacterEntities.put("rsquo", (char)8217);
        _htmlCharacterEntities.put("sbquo", (char)8218);
        _htmlCharacterEntities.put("Scaron", (char)352);
        _htmlCharacterEntities.put("scaron", (char)353);
        _htmlCharacterEntities.put("sdot", (char)8901);
        _htmlCharacterEntities.put("sect", (char)167);
        _htmlCharacterEntities.put("shy", (char)173);
        _htmlCharacterEntities.put("Sigma", (char)931);
        _htmlCharacterEntities.put("sigma", (char)963);
        _htmlCharacterEntities.put("sigmaf", (char)962);
        _htmlCharacterEntities.put("sim", (char)8764);
        _htmlCharacterEntities.put("spades", (char)9824);
        _htmlCharacterEntities.put("sub", (char)8834);
        _htmlCharacterEntities.put("sube", (char)8838);
        _htmlCharacterEntities.put("sum", (char)8721);
        _htmlCharacterEntities.put("sup", (char)8835);
        _htmlCharacterEntities.put("sup1", (char)185);
        _htmlCharacterEntities.put("sup2", (char)178);
        _htmlCharacterEntities.put("sup3", (char)179);
        _htmlCharacterEntities.put("supe", (char)8839);
        _htmlCharacterEntities.put("szlig", (char)223);
        _htmlCharacterEntities.put("Tau", (char)932);
        _htmlCharacterEntities.put("tau", (char)964);
        _htmlCharacterEntities.put("there4", (char)8756);
        _htmlCharacterEntities.put("Theta", (char)920);
        _htmlCharacterEntities.put("theta", (char)952);
        _htmlCharacterEntities.put("thetasym", (char)977);
        _htmlCharacterEntities.put("thinsp", (char)8201);
        _htmlCharacterEntities.put("THORN", (char)222);
        _htmlCharacterEntities.put("thorn", (char)254);
        _htmlCharacterEntities.put("tilde", (char)732);
        _htmlCharacterEntities.put("times", (char)215);
        _htmlCharacterEntities.put("trade", (char)8482);
        _htmlCharacterEntities.put("Uacute", (char)218);
        _htmlCharacterEntities.put("uacute", (char)250);
        _htmlCharacterEntities.put("uarr", (char)8593);
        _htmlCharacterEntities.put("uArr", (char)8657);
        _htmlCharacterEntities.put("Ucirc", (char)219);
        _htmlCharacterEntities.put("ucirc", (char)251);
        _htmlCharacterEntities.put("Ugrave", (char)217);
        _htmlCharacterEntities.put("ugrave", (char)249);
        _htmlCharacterEntities.put("uml", (char)168);
        _htmlCharacterEntities.put("upsih", (char)978);
        _htmlCharacterEntities.put("Upsilon", (char)933);
        _htmlCharacterEntities.put("upsilon", (char)965);
        _htmlCharacterEntities.put("Uuml", (char)220);
        _htmlCharacterEntities.put("uuml", (char)252);
        _htmlCharacterEntities.put("weierp", (char)8472);
        _htmlCharacterEntities.put("Xi", (char)926);
        _htmlCharacterEntities.put("xi", (char)958);
        _htmlCharacterEntities.put("Yacute", (char)221);
        _htmlCharacterEntities.put("yacute", (char)253);
        _htmlCharacterEntities.put("yen", (char)165);
        _htmlCharacterEntities.put("Yuml", (char)376);
        _htmlCharacterEntities.put("yuml", (char)255);
        _htmlCharacterEntities.put("Zeta", (char)918);
        _htmlCharacterEntities.put("zeta", (char)950);
        _htmlCharacterEntities.put("zwj", (char)8205);
        _htmlCharacterEntities.put("zwnj", (char)8204);
    }

    public boolean IsElement(String elementName) {
        return IsBlockElement(elementName) ||
                IsEmptyElement(elementName) ||
                IsInlineElement(elementName);
    }

    public boolean IsEmptyElement(String elementName) {
        return _htmlEmptyElements.contains(elementName.toLowerCase());
    }

    public boolean IsBlockElement(String elementName) {
        return _htmlBlockElements.contains(elementName.toLowerCase());
    }

    public boolean IsInlineElement(String elementName) {
        return _htmlInlineElements.contains(elementName.toLowerCase());
    }

    public boolean ClosesOnParentElementEnd(String elementName) {
        return _htmlElementsClosingOnParentElementEnd.contains(elementName.toLowerCase());
    }

    public boolean ClosesOnNextElementStart(String currentElementName, String nextElementName) {
        nextElementName = nextElementName.toLowerCase();
        return switch (currentElementName.toLowerCase()) {
            case "colgroup" -> _htmlElementsClosingColgroup.contains(nextElementName) && _htmlBlockElements.contains(nextElementName);
            case "dd" -> _htmlElementsClosingDD.contains(nextElementName) && _htmlBlockElements.contains(nextElementName);
            case "dt" -> _htmlElementsClosingDT.contains(nextElementName) && _htmlBlockElements.contains(nextElementName);
            case "li" -> _htmlElementsClosingLI.contains(nextElementName);
            case "p" -> _htmlBlockElements.contains(nextElementName);
            case "tbody" -> _htmlElementsClosingTbody.contains(nextElementName);
            case "tfoot" -> _htmlElementsClosingTfoot.contains(nextElementName);
            case "thead" -> _htmlElementsClosingThead.contains(nextElementName);
            case "tr" -> _htmlElementsClosingTR.contains(nextElementName);
            case "td" -> _htmlElementsClosingTD.contains(nextElementName);
            case "th" -> _htmlElementsClosingTH.contains(nextElementName);
            default -> false;
        };
    }

    public boolean IsEntity(String entityName) {
        return _htmlCharacterEntities.containsKey(entityName);
    }

    public char EntityCharacterValue(String entityName) {
        return _htmlCharacterEntities.getOrDefault(entityName, (char) 0);
    }
}
