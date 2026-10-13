package dev.welles.itemrepair.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import dev.welles.itemrepair.ItemRepair;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerMixin {
    /**
     * Entities ignore a broken item: no shearing sheep, igniting creepers or brushing armadillos.
     * The client then tries the other hand, like for any item without an entity interaction.
     */
    @Inject(method = "interactOn", at = @At("HEAD"), cancellable = true)
    private void itemrepair$noEntityInteraction(CallbackInfoReturnable<InteractionResult> cir, @Local(argsOnly = true) InteractionHand hand) {
        Player player = (Player) (Object) this;
        if (!player.isSpectator() && ItemRepair.isBroken(player.getItemInHand(hand))) cir.setReturnValue(InteractionResult.PASS);
    }

    /** No item specific attack bonus (the mace's fall damage bonus) with a broken weapon. */
    @WrapOperation(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/Item;getAttackDamageBonus(Lnet/minecraft/world/entity/Entity;FLnet/minecraft/world/damagesource/DamageSource;)F"))
    private float itemrepair$noBonusWhenBroken(Item item, Entity target, float damage, DamageSource source, Operation<Float> original) {
        if (ItemRepair.isBroken(((Player) (Object) this).getWeaponItem())) return 0.0F;
        return original.call(item, target, damage, source);
    }
}
