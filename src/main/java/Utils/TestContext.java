package Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestContext {

    public static final String TOTAL_PRICE = "totalPrice";
    public static final String ITEM_COUNT  = "itemCount";
    public static final String ITEM_NAMES = "itemNames";

    private static final ThreadLocal<Map<String, Object>> context =
            ThreadLocal.withInitial(HashMap::new);

    public static void set(String key, Object value) {
        context.get().put(key, value);
    }

    @SuppressWarnings("unchecked")
    public static <T> T get(String key) {
        return (T) context.get().get(key);
    }

    public static boolean contains(String key) {
        return context.get().containsKey(key);
    }

    public static void remove(String key) {
        context.get().remove(key);
    }

    public static void clear() {
        context.get().clear();
    }
}