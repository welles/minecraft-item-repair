package dev.welles.itemrepair;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

public final class ItemRepair {
    public static final String MOD_ID = "itemrepair";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private ItemRepair() {}

    public static void init(Path configDir) {
        RepairConfig.init(configDir);
        LOGGER.info("Item Repair loaded");
    }
}
