package main.de.grzb.szeibernaeticks.szeibernaeticks.event;

import main.de.grzb.szeibernaeticks.control.Log;
import main.de.grzb.szeibernaeticks.control.LogType;
import main.de.grzb.szeibernaeticks.szeibernaeticks.capability.ISzeibernaetick;
import main.de.grzb.szeibernaeticks.szeibernaeticks.capability.armoury.IArmoury;
import net.minecraftforge.fml.common.eventhandler.Event;

public class SzeibernaetickRemovedEvent extends Event {

    public final IArmoury armoury;
    public final ISzeibernaetick removedSzeibernaetick;

    public SzeibernaetickRemovedEvent(IArmoury armoury, ISzeibernaetick removedSzeibernaetick) {
        this.armoury = armoury;
        this.removedSzeibernaetick = removedSzeibernaetick;

        Log.log("Removed Szeibernaetick: " + removedSzeibernaetick.getIdentifier(), LogType.INFO);
    }

}
