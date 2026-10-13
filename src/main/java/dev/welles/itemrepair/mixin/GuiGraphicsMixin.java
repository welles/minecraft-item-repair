package dev.welles.itemrepair.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import dev.welles.itemrepair.ItemRepair;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//? if >=26.1 {
/*import net.minecraft.client.gui.GuiGraphicsExtractor;
*///?} else {
import net.minecraft.client.gui.GuiGraphics;
//?}
//? if <1.21.6 {
import net.minecraft.client.renderer.RenderType;
//?}

/** Strikes broken items through with a red line in inventories and the hotbar. */
//? if >=26.1 {
/*@Mixin(GuiGraphicsExtractor.class)
*///?} else {
@Mixin(GuiGraphics.class)
//?}
public abstract class GuiGraphicsMixin {
    private static final int STRIKE_COLOR = 0xFFE02020;

    //? if >=26.1 {
    /*@Inject(method = "itemDecorations(Lnet/minecraft/client/gui/Font;Lnet/minecraft/world/item/ItemStack;IILjava/lang/String;)V", at = @At("TAIL"))
    *///?} else {
    @Inject(method = "renderItemDecorations(Lnet/minecraft/client/gui/Font;Lnet/minecraft/world/item/ItemStack;IILjava/lang/String;)V", at = @At("TAIL"))
    //?}
    private void itemrepair$strikeThroughBroken(
        CallbackInfo ci, @Local(argsOnly = true) ItemStack stack, @Local(argsOnly = true, ordinal = 0) int x, @Local(argsOnly = true, ordinal = 1) int y
    ) {
        if (!ItemRepair.isBroken(stack)) return;
        // diagonal from the bottom left to the top right corner of the 16x16 slot, two pixels wide
        for (int i = 0; i < 16; i++) {
            int left = x + i;
            int top = y + 15 - i;
            //? if <1.21.6 {
            // drawn like the cooldown overlay, on top of the item
            ((GuiGraphics) (Object) this).fill(RenderType.guiOverlay(), left, top, Math.min(left + 2, x + 16), top + 1, STRIKE_COLOR);
            //?} else if >=26.1 {
            /*((GuiGraphicsExtractor) (Object) this).fill(left, top, Math.min(left + 2, x + 16), top + 1, STRIKE_COLOR);
            *///?} else {
            /*((GuiGraphics) (Object) this).fill(left, top, Math.min(left + 2, x + 16), top + 1, STRIKE_COLOR);
            *///?}
        }
    }
}
