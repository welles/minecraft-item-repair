package dev.welles.itemrepair;

//? if fabric {
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerConfigurationConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerConfigurationNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.chat.Component;

public class ItemRepairFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        ItemRepair.init(FabricLoader.getInstance().getConfigDir());

        //? if >=26.1 {
        /*PayloadTypeRegistry.clientboundConfiguration().register(HandshakePayload.TYPE, HandshakePayload.CODEC);
        *///?} else {
        PayloadTypeRegistry.configurationS2C().register(HandshakePayload.TYPE, HandshakePayload.CODEC);
        //?}
        // The client predicts mining speed and right-click behaviour, so it needs the mod as well
        ServerConfigurationConnectionEvents.CONFIGURE.register((handler, server) -> {
            if (!ServerConfigurationNetworking.canSend(handler, HandshakePayload.TYPE)) handler.disconnect(missingModMessage());
        });
    }

    /** Plain text, a client without the mod has no translations for it. */
    private static Component missingModMessage() {
        String version = FabricLoader.getInstance().getModContainer(ItemRepair.MOD_ID)
            .map(mod -> " " + mod.getMetadata().getVersion().getFriendlyString())
            .orElse("");
        return Component.literal("This server requires the Item Repair mod" + version + " on the client as well.\n"
            + "Please install it (with Fabric API) and join again.");
    }
}
//?}
