package dev.welles.itemrepair;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

//? if >=1.21.11 {
/*import net.minecraft.resources.Identifier;
*///?} else {
import net.minecraft.resources.ResourceLocation;
//?}

/**
 * Empty payload that is never sent. The client mod only registers its channel, so the server can tell whether a
 * joining player has the mod installed and disconnect them otherwise.
 */
public record HandshakePayload() implements CustomPacketPayload {
    //? if >=1.21.11 {
    /*public static final Type<HandshakePayload> TYPE = new Type<>(Identifier.fromNamespaceAndPath(ItemRepair.MOD_ID, "handshake"));
    *///?} else {
    public static final Type<HandshakePayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(ItemRepair.MOD_ID, "handshake"));
    //?}
    public static final StreamCodec<FriendlyByteBuf, HandshakePayload> CODEC = StreamCodec.unit(new HandshakePayload());

    @Override
    public Type<HandshakePayload> type() {
        return TYPE;
    }
}
