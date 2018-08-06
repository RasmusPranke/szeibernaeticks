package main.de.grzb.szeibernaeticks.szeibernaeticks;

import main.de.grzb.szeibernaeticks.networking.NetworkWrapper;
import main.de.grzb.szeibernaeticks.networking.SzeiberCapMessage;
import main.de.grzb.szeibernaeticks.szeibernaeticks.event.SzeibernaetickInstalledEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SzeibernaetickNetworkBroadcaster {
    @SubscribeEvent
    public void onSzeibernaetickInstalled(SzeibernaetickInstalledEvent event) {
        if(!event.armoury.getEntity().world.isRemote) {
            NetworkWrapper.INSTANCE.sendToAll(new SzeiberCapMessage(event.installedSzeibernaetick));
        }
    }
}
