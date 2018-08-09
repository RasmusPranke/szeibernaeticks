package de.grzb.szeibernaeticks.szeibernaeticks.event;

import de.grzb.szeibernaeticks.control.Log;
import de.grzb.szeibernaeticks.control.LogType;
import de.grzb.szeibernaeticks.szeibernaeticks.armoury.IArmoury;
import de.grzb.szeibernaeticks.szeibernaeticks.ISzeibernaetick;
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
