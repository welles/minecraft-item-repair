package dev.welles.itemrepair.mixin;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import dev.welles.itemrepair.ItemRepair;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

//? if neoforge {
/*import net.minecraft.world.entity.LivingEntity;
*///?} else {
import net.minecraft.server.level.ServerPlayer;
//?}
//? if >=1.21.2 {
/*import net.minecraft.core.component.DataComponents;
*///?} else {
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ElytraItem;
//?}

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    // NeoForge widens the owner of damaged items from ServerPlayer to LivingEntity
    //? if neoforge {
    /*private static final String OWNER = "Lnet/minecraft/world/entity/LivingEntity;";
    *///?} else {
    private static final String OWNER = "Lnet/minecraft/server/level/ServerPlayer;";
    //?}
    private static final String HURT_AND_BREAK = "hurtAndBreak(ILnet/minecraft/server/level/ServerLevel;" + OWNER + "Ljava/util/function/Consumer;)V";

    private ItemStack itemrepair$self() {
        return (ItemStack) (Object) this;
    }

    /** Broken items of players take no further damage. */
    @Inject(method = HURT_AND_BREAK, at = @At("HEAD"), cancellable = true)
    private void itemrepair$noDamageWhenBroken(CallbackInfo ci, @Local(argsOnly = true)
        //? if neoforge {
        /*LivingEntity owner
        *///?} else {
        ServerPlayer owner
        //?}
    ) {
        if (owner instanceof Player && ItemRepair.isBroken(itemrepair$self())) ci.cancel();
    }

    /**
     * Keeps a player's item that ran out of durability instead of removing it. The break callback still runs, so the
     * vanilla break sound and particles play and running equipment effects are stopped.
     */
    //? if >=1.21.2 {
    /*@WrapWithCondition(method = "applyDamage(I" + OWNER + "Ljava/util/function/Consumer;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;shrink(I)V"))
    *///?} else {
    @WrapWithCondition(method = HURT_AND_BREAK, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;shrink(I)V"))
    //?}
    private boolean itemrepair$keepBrokenItem(ItemStack stack, int amount, @Local(argsOnly = true)
        //? if neoforge {
        /*LivingEntity owner
        *///?} else {
        ServerPlayer owner
        //?}
    ) {
        if (!(owner instanceof Player player) || !ItemRepair.keepsBrokenItem(stack)) return true;
        // e.g. a shield that broke while blocking
        if (player.isUsingItem() && player.getUseItem() == stack) player.stopUsingItem();
        return false;
    }

    /** Broken items cannot be used, but armor and elytras can still be equipped by right-clicking. */
    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    //? if >=1.21.2 {
    /*private void itemrepair$noUse(CallbackInfoReturnable<InteractionResult> cir) {
        ItemStack stack = itemrepair$self();
        if (ItemRepair.isBroken(stack) && !stack.has(DataComponents.EQUIPPABLE)) cir.setReturnValue(InteractionResult.PASS);
    }
    *///?} else {
    private void itemrepair$noUse(CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
        ItemStack stack = itemrepair$self();
        if (ItemRepair.isBroken(stack) && !(stack.getItem() instanceof ArmorItem || stack.getItem() instanceof ElytraItem)) {
            cir.setReturnValue(InteractionResultHolder.pass(stack));
        }
    }
    //?}

    @Inject(method = {"useOn", "interactLivingEntity"}, at = @At("HEAD"), cancellable = true)
    private void itemrepair$noUseOn(CallbackInfoReturnable<InteractionResult> cir) {
        if (ItemRepair.isBroken(itemrepair$self())) cir.setReturnValue(InteractionResult.PASS);
    }

    /** Broken tools mine like an empty hand. */
    @Inject(method = "getDestroySpeed", at = @At("HEAD"), cancellable = true)
    private void itemrepair$handDestroySpeed(CallbackInfoReturnable<Float> cir) {
        if (ItemRepair.isBroken(itemrepair$self())) cir.setReturnValue(1.0F);
    }

    @Inject(method = "isCorrectToolForDrops", at = @At("HEAD"), cancellable = true)
    private void itemrepair$noDrops(CallbackInfoReturnable<Boolean> cir) {
        if (ItemRepair.isBroken(itemrepair$self())) cir.setReturnValue(false);
    }

    /** Broken weapons have no on-hit effects (e.g. the mace smash). */
    @Inject(method = "hurtEnemy", at = @At("HEAD"), cancellable = true)
    private void itemrepair$noHurtEnemy(CallbackInfoReturnable<Boolean> cir) {
        if (ItemRepair.isBroken(itemrepair$self())) cir.setReturnValue(false);
    }

    @Inject(method = "postHurtEnemy", at = @At("HEAD"), cancellable = true)
    private void itemrepair$noPostHurtEnemy(CallbackInfo ci) {
        if (ItemRepair.isBroken(itemrepair$self())) ci.cancel();
    }
}
