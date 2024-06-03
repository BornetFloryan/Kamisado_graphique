package control;

import java.util.ArrayList;
import java.util.List;

public class EntryFileContainer {
    public static List<String> entryFromFile = new ArrayList<>();

    public static void addEntry(String entry) {
        entryFromFile.add(entry);
    }

    public static String getFirstEntry() {
        if (entryFromFile.isEmpty()) {
            return null;
        }
        return entryFromFile.get(0);
    }

    public static void removeFirstEntry() {
        entryFromFile.remove(0);
    }
}
