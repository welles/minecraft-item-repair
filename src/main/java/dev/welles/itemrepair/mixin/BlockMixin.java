package dev.welles.itemrepair.mixin;

import dev.welles.itemrepair.ItemRepair;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

//? if >=26.1 {
/*import net.minecraft.world.item.ItemInstance;
*///?}

@Mixin(Block.class)
public abstract class BlockMixin {
    /** Block loot treats a broken tool as an empty hand (no Silk Touch, no shears on leaves, ...). */
    //? if >=26.1 {
    /*@ModifyVariable(
        method = "getDrops(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/entity/BlockEntity;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/item/ItemInstance;)Ljava/util/List;",
        at = @At("HEAD"), argsOnly = true
    )
    private static ItemInstance itemrepair$brokenToolDropsNothing(ItemInstance tool) {
        return tool instanceof ItemStack stack && ItemRepair.isBroken(stack) ? ItemStack.EMPTY : tool;
    }
    *///?} else {
    @ModifyVariable(
        method = "getDrops(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/entity/BlockEntity;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/item/ItemStack;)Ljava/util/List;",
        at = @At("HEAD"), argsOnly = true
    )
    private static ItemStack itemrepair$brokenToolDropsNothing(ItemStack tool) {
        return ItemRepair.isBroken(tool) ? ItemStack.EMPTY : tool;
    }
    //?}
}
