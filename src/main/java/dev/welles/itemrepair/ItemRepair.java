package dev.welles.itemrepair;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ItemStack;
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

    /**
     * Whether the stack is broken: it has no durability left. Same as vanilla's {@code ItemStack#isBroken()}
     * (added in 1.21.2), which already ignores such equipment for attributes and location based enchantments.
     */
    public static boolean isBroken(ItemStack stack) {
        return stack.isDamageableItem() && stack.getMaxDamage() > 0 && stack.getDamageValue() >= stack.getMaxDamage();
    }

    /** Whether a player's stack that just ran out of durability stays in the inventory as a broken item. */
    public static boolean keepsBrokenItem(ItemStack stack) {
        RepairConfig config = RepairConfig.get();
        return config.enabled && !config.excludedItems.contains(BuiltInRegistries.ITEM.getKey(stack.getItem()).toString());
    }
}
