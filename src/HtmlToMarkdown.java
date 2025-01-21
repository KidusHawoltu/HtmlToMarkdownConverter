import java.util.HashMap;
import java.util.Map;

import static java.util.Map.entry;

public class HtmlToMarkdown {
    private final Map<String, Map<Boolean, String >> HtmlMd;

    public HtmlToMarkdown() {
        HtmlMd = new HashMap<>(Map.ofEntries(
                entry("p", Map.of(true,"  \n", false, "  \n")),
                entry("strong", Map.of(true,"**", false, "**")),
                entry("b", Map.of(true,"**", false, "**")),
                entry("em", Map.of(true,"*", false, "*")),
                entry("i", Map.of(true,"*", false, "*")),
                entry("title", Map.of(true, "\n", false, "\n========================\n")),
                entry("sub", Map.of(true,"~", false, "~")),
                entry("sup", Map.of(true,"^", false, "^")),
                entry("mark", Map.of(true,"==", false, "==")),
                entry("del", Map.of(true,"~~", false, "~~")),
                entry("a", Map.of(true,"\n[", false, "]")),
                entry("br", Map.of(true,"   \n", false, "  \n")),
                entry("dt", Map.of(true,"\n", false, "")),
                entry("dd", Map.of(true,"\n: ", false, "")),
                entry("h1", Map.of(true,"\n# ", false, "\n")),
                entry("h2", Map.of(true,"\n## ", false, "\n")),
                entry("h3", Map.of(true,"\n### ", false, "\n")),
                entry("h4", Map.of(true,"\n#### ", false, "\n")),
                entry("h5", Map.of(true,"\n##### ", false, "\n")),
                entry("h6", Map.of(true,"\n###### ", false, "\n")),
                entry("hr", Map.of(true,"\n---\n", false, "\n---\n")),
                entry("tr", Map.of(true,"\n| ", false, "")),
                entry("th", Map.of(true,"", false, " |")),
                entry("td", Map.of(true,"", false, " |")),
                entry("", Map.of(true,"", false, ""))
        ));
    }

    public String convert(String html, boolean begin) {
        String md = HtmlMd.get(html) == null ? null : HtmlMd.get(html).get(begin);
        return md == null ? "" : md;
    }
}
