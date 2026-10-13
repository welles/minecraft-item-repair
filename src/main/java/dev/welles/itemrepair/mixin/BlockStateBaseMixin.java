package dev.welles.itemrepair.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import dev.welles.itemrepair.ItemRepair;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

//? if >=1.21.2 {
/*import net.minecraft.world.InteractionResult;
*///?} else {
import net.minecraft.world.ItemInteractionResult;
//?}

@Mixin(BlockBehaviour.BlockStateBase.class)
public abstract class BlockStateBaseMixin {
    /** Blocks react to a broken item like to an empty hand: chests still open, TNT is not lit, pumpkins are not carved. */
    @Inject(method = "useItemOn", at = @At("HEAD"), cancellable = true)
    //? if >=1.21.2 {
    /*private void itemrepair$brokenItemAsEmptyHand(CallbackInfoReturnable<InteractionResult> cir, @Local(argsOnly = true) ItemStack stack) {
        if (ItemRepair.isBroken(stack)) cir.setReturnValue(InteractionResult.TRY_WITH_EMPTY_HAND);
    }
    *///?} else {
    private void itemrepair$brokenItemAsEmptyHand(CallbackInfoReturnable<ItemInteractionResult> cir, @Local(argsOnly = true) ItemStack stack) {
        if (ItemRepair.isBroken(stack)) cir.setReturnValue(ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION);
    }
    //?}
}
