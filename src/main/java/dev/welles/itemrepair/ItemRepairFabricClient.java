package dev.welles.itemrepair;

//? if fabric {
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientConfigurationNetworking;

public class ItemRepairFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Registering a receiver announces the channel to the server, see ItemRepairFabric
        ClientConfigurationNetworking.registerGlobalReceiver(HandshakePayload.TYPE, (payload, context) -> {});
    }
}
//?}
