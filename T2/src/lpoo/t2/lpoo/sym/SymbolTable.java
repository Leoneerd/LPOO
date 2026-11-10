package lpoo.sym;

import java.util.Set;
import java.util.TreeMap;

public final class SymbolTable {
    private static final TreeMap<String, Expression> symbols = new TreeMap<>();

    private SymbolTable() {

    }

    public static boolean contains(String name) {
        return symbols.containsKey(name);
    }

    public static Expression get(String name) {
        return symbols.get(name);
    }

    public static void put(String name, Expression expression) {
        symbols.put(name, expression);
    }

    public static Expression remove(String name) {
        return symbols.remove(name);
    }

    public static void clear() {
        symbols.clear();
    }

    public static Set<String> names() {
        return symbols.keySet();
    }
}