import java.util.HashMap;
import java.util.Map;

public class SymbolTable {
    public static Map<Integer, SymbolTableValue> symbolTable = new HashMap<>();

    public static void clear(){
        symbolTable.clear();
    }
}
