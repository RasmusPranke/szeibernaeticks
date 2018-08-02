package main.de.grzb.szeibernaeticks.szeibernaeticks.event;

import main.de.grzb.szeibernaeticks.control.Log;
import main.de.grzb.szeibernaeticks.control.LogType;
import main.de.grzb.szeibernaeticks.szeibernaeticks.capability.ISzeibernaetick;
import main.de.grzb.szeibernaeticks.szeibernaeticks.capability.armoury.IArmoury;
import net.minecraftforge.fml.common.eventhandler.Event;

public class SzeibernaetickInstalledEvent extends Event {

    public final IArmoury armoury;
    public final ISzeibernaetick installedSzeibernaetick;

    public SzeibernaetickInstalledEvent(IArmoury armoury, ISzeibernaetick installedSzeibernaetick) {
        this.armoury = armoury;
        this.installedSzeibernaetick = installedSzeibernaetick;

        Log.log("Installed Szeibernaetick: " + installedSzeibernaetick.getIdentifier(), LogType.INFO);
    }

}
