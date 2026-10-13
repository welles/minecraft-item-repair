package dev.welles.itemrepair.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import dev.welles.itemrepair.ItemRepair;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Set;
import java.util.stream.Collectors;

//? if >=26.1 {
/*import net.minecraft.world.item.ItemInstance;
*///?}

/**
 * Enchantments of broken items have no effect, except those that repair the item with experience (Mending).
 * Curses still apply, they are checked separately via {@code EnchantmentHelper.has}.
 */
@Mixin(EnchantmentHelper.class)
public abstract class EnchantmentHelperMixin {
    /** Filters the enchantments every effect (damage, protection, thorns, attributes, ...) iterates over. */
    @ModifyExpressionValue(method = "runIterationOnItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/enchantment/ItemEnchantments;entrySet()Ljava/util/Set;"))
    private static Set<Object2IntMap.Entry<Holder<Enchantment>>> itemrepair$onlyRepairWhenBroken(
        Set<Object2IntMap.Entry<Holder<Enchantment>>> enchantments, @Local(argsOnly = true) ItemStack stack
    ) {
        if (!ItemRepair.isBroken(stack)) return enchantments;
        return enchantments.stream().filter(entry -> itemrepair$repairs(entry.getKey())).collect(Collectors.toSet());
    }

    /** Covers enchantments that are looked up by level, like Fortune and Looting. */
    @Inject(method = "getItemEnchantmentLevel", at = @At("HEAD"), cancellable = true)
    //? if >=26.1 {
    /*private static void itemrepair$noLevelWhenBroken(Holder<Enchantment> enchantment, ItemInstance item, CallbackInfoReturnable<Integer> cir) {
        if (item instanceof ItemStack stack && ItemRepair.isBroken(stack) && !itemrepair$repairs(enchantment)) cir.setReturnValue(0);
    }
    *///?} else {
    private static void itemrepair$noLevelWhenBroken(Holder<Enchantment> enchantment, ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        if (ItemRepair.isBroken(stack) && !itemrepair$repairs(enchantment)) cir.setReturnValue(0);
    }
    //?}

    private static boolean itemrepair$repairs(Holder<Enchantment> enchantment) {
        return enchantment.value().effects().has(EnchantmentEffectComponents.REPAIR_WITH_XP);
    }
}
