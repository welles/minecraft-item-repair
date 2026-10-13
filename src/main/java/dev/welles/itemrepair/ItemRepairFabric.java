package dev.welles.itemrepair;

//? if fabric {
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class ItemRepairFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        ItemRepair.init(FabricLoader.getInstance().getConfigDir());
    }
}
//?}
