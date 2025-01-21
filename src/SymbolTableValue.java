import java.util.Map;

public record SymbolTableValue(int line,
                               Map<String, String> value) {
}
