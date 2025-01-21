public enum HtmlToken {
    OpeningTagStart,
    ClosingTagStart,
    TagEnd,
    EmptyTagEnd,
    Name,
    Attribute, // any attribute value not in quotes
    Text, //text content when accepting text
    Comment,
    EOF,
}
