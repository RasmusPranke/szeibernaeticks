package de.grzb.szeibernaeticks.szeibernaeticks.event;

import de.grzb.szeibernaeticks.control.Log;
import de.grzb.szeibernaeticks.control.LogType;
import de.grzb.szeibernaeticks.szeibernaeticks.armoury.IArmoury;
import de.grzb.szeibernaeticks.szeibernaeticks.ISzeibernaetick;
import net.minecraftforge.fml.common.eventhandler.Event;

public class SzeibernaetickInstalledEvent extends Event {

    public final IArmoury armoury;
    public final ISzeibernaetick installedSzeibernaetick;

    public SzeibernaetickInstalledEvent(IArmoury armoury, ISzeibernaetick installedSzeibernaetick) {
        this.armoury = armoury;
        this.installedSzeibernaetick = installedSzeibernaetick;

        Log.log("Installed SzeiberClass: " + installedSzeibernaetick.getIdentifier(), LogType.INFO);
    }

}
