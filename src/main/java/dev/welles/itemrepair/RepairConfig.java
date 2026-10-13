package dev.welles.itemrepair;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Settings stored in {@code config/itemrepair.properties}.
 * The file is re-read automatically whenever it changes on disk.
 */
public final class RepairConfig {
    public boolean enabled = true;
    public Set<String> excludedItems = Set.of();

    private static final String HEADER = """
        Item Repair configuration
        Changes are picked up automatically, no restart required.

        enabled        Master switch. true  = items of players stay in the inventory as broken items when they run out of durability
                                      false = items break and disappear like in vanilla (already broken items stay broken until repaired)
        excludedItems  Comma separated item ids that break and disappear like in vanilla, e.g. minecraft:fishing_rod,minecraft:shears""";

    private static Path file;
    private static FileTime loadedAt;
    private static RepairConfig current = new RepairConfig();

    public static synchronized void init(Path configDir) {
        file = configDir.resolve(ItemRepair.MOD_ID + ".properties");
        reload();
    }

    /** Returns the current settings, re-reading the file if it was modified. */
    public static synchronized RepairConfig get() {
        if (file != null) {
            try {
                FileTime modified = Files.exists(file) ? Files.getLastModifiedTime(file) : null;
                if (modified == null || !modified.equals(loadedAt)) reload();
            } catch (IOException e) {
                ItemRepair.LOGGER.warn("Could not check config file {}", file, e);
            }
        }
        return current;
    }

    private static void reload() {
        RepairConfig config = new RepairConfig();
        Properties props = new Properties();
        boolean complete = false;

        if (Files.exists(file)) {
            try (Reader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
                props.load(reader);
                complete = config.read(props);
            } catch (IOException | RuntimeException e) {
                ItemRepair.LOGGER.error("Could not read config file {}, using defaults", file, e);
                current = config;
                return;
            }
        }

        if (!complete) {
            try {
                Files.createDirectories(file.getParent());
                try (Writer writer = Files.newBufferedWriter(file, StandardCharsets.UTF_8)) {
                    config.write(writer);
                }
            } catch (IOException e) {
                ItemRepair.LOGGER.error("Could not write config file {}", file, e);
            }
        }

        try {
            loadedAt = Files.exists(file) ? Files.getLastModifiedTime(file) : null;
        } catch (IOException e) {
            loadedAt = null;
        }
        current = config;
    }

    /** Applies all values present in {@code props}; returns whether every key was present. */
    private boolean read(Properties props) {
        String value = props.getProperty("enabled");
        if (value != null) enabled = Boolean.parseBoolean(value.trim());
        value = props.getProperty("excludedItems");
        if (value != null) excludedItems = parseItems(value);
        return props.keySet().containsAll(toProperties().keySet());
    }

    private void write(Writer writer) throws IOException {
        for (String line : HEADER.split("\n")) writer.write(("# " + line).stripTrailing() + "\n");
        writer.write("\n");
        for (var entry : toProperties().entrySet()) {
            writer.write(entry.getKey() + "=" + entry.getValue() + "\n");
        }
    }

    private Map<String, String> toProperties() {
        Map<String, String> props = new LinkedHashMap<>();
        props.put("enabled", String.valueOf(enabled));
        props.put("excludedItems", String.join(",", excludedItems));
        return props;
    }

    /** Parses comma separated item ids; ids without namespace default to {@code minecraft:}. */
    private static Set<String> parseItems(String value) {
        return Arrays.stream(value.split(","))
            .map(id -> id.trim().toLowerCase(Locale.ROOT))
            .filter(id -> !id.isEmpty())
            .map(id -> id.contains(":") ? id : "minecraft:" + id)
            .collect(Collectors.toUnmodifiableSet());
    }
}
