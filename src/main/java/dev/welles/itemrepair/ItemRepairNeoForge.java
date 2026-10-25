package dev.welles.itemrepair;

//? if neoforge {
/*import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

@Mod(ItemRepair.MOD_ID)
public class ItemRepairNeoForge {
    public ItemRepairNeoForge(IEventBus modBus) {
        ItemRepair.init(FMLPaths.CONFIGDIR.get());
        modBus.addListener(ItemRepairNeoForge::registerPayloads);
    }

    // A required channel: NeoForge disconnects players whose client does not have the mod, since the client predicts
    // mining speed and right-click behaviour

    private static void registerPayloads(RegisterPayloadHandlersEvent event) {
        event.registrar("1").configurationToClient(HandshakePayload.TYPE, HandshakePayload.CODEC, (payload, context) -> {});
    }
}
*///?}
