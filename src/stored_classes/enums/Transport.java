package stored_classes.enums;

import java.util.HashMap;
import java.util.Map;

public enum Transport {
    FEW,
    LITTLE,
    NORMAL,
    ENOUGH;
    public static final Map<String, Transport> naming = new HashMap<>();
    static {
        for (Transport transport : Transport.values()) {
            naming.put(transport.name(), transport);
        }
    }
}
