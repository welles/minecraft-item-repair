package dev.welles.itemrepair.mixin;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import dev.welles.itemrepair.ItemRepair;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.BiConsumer;

/**
 * Backport of the 1.21.2 behaviour: broken equipment adds no attribute modifiers (attack damage, armor, ...) and
 * no location based enchantment effects. Only applied before 1.21.2.
 */
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @WrapWithCondition(
        method = "collectEquipmentChanges",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;forEachModifier(Lnet/minecraft/world/entity/EquipmentSlot;Ljava/util/function/BiConsumer;)V", ordinal = 1)
    )
    private boolean itemrepair$skipBrokenEquipment(ItemStack stack, EquipmentSlot slot, BiConsumer<Holder<Attribute>, AttributeModifier> consumer) {
        return !ItemRepair.isBroken(stack);
    }
}
