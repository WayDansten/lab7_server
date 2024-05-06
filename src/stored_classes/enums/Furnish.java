package stored_classes.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public enum Furnish {
    DESIGNER (4),
    NONE (0),
    FINE (3),
    BAD (1),
    LITTLE (2);
    final int quality;
    private Furnish(int quality) {
        this.quality = quality;
    }
    public int getQuality() {
        return quality;
    }
    public static final Map<String, Furnish> naming = new HashMap<>();
    static {
        for (Furnish furnish : Furnish.values()) {
            naming.put(furnish.name(), furnish);
        }
    }
    public static final ArrayList<Integer> qualities = new ArrayList<>();
    static {
        for (Furnish furnish : Furnish.values()) {
            qualities.add(furnish.quality);
        }
    }
}
