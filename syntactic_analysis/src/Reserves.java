import java.util.HashMap;

public interface Reserves {
    public static HashMap<String, String> resSymbols = new HashMap<>();

    public static boolean reserve(String token) {
        return resSymbols.containsKey(token);
    }
}
