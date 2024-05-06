package stored_classes.enums;

import java.util.HashMap;
import java.util.Map;

public enum View {
    STREET,
    YARD,
    PARK,
    BAD,
    NORMAL;
    public static final Map<String, View> naming = new HashMap<>();
    static {
        for (View view : View.values()) {
            naming.put(view.name(), view);
        }
    }
}
