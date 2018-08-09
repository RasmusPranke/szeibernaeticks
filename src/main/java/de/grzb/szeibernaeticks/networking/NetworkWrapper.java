package de.grzb.szeibernaeticks.networking;

import de.grzb.szeibernaeticks.Szeibernaeticks;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public class NetworkWrapper {

    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Szeibernaeticks.MOD_ID);

    private static int id = 0;

    public static int getId() {
        return id++;
    }

}
