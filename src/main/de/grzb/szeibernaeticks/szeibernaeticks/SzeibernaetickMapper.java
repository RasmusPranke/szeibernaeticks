package main.de.grzb.szeibernaeticks.szeibernaeticks;

import java.util.concurrent.ConcurrentHashMap;

import main.de.grzb.szeibernaeticks.control.Log;
import main.de.grzb.szeibernaeticks.control.LogType;
import main.de.grzb.szeibernaeticks.item.SzeibernaetickItem;

/**
 * Maps the identifiers of Capabilities to the Capability classes as well as
 * those Classes to Items.
 *
 * @author DemRat
 */
public enum SzeibernaetickMapper {

    INSTANCE;

    private ConcurrentHashMap<Class<? extends ISzeibernaetick>, SzeibernaetickItem> itemMap;
    private ConcurrentHashMap<SzeibernaetickIdentifier, Class<? extends ISzeibernaetick>> idMap;

    private SzeibernaetickMapper() {
        this.itemMap = new ConcurrentHashMap<Class<? extends ISzeibernaetick>, SzeibernaetickItem>();
        this.idMap = new ConcurrentHashMap<SzeibernaetickIdentifier, Class<? extends ISzeibernaetick>>();
    }

    /**
     * Registers the given Class as corresponding to the given Item. This also
     * adds the mapping from identifier to Class.
     *
     * @param cap
     * @param item
     */
    public void register(Class<? extends ISzeibernaetick> cap, SzeibernaetickItem item,
            SzeibernaetickIdentifier identifier) {
        Log.log("Trying to register Capability!", LogType.DEBUG, LogType.SETUP);
        if(this.itemMap.put(cap, item) != null) {
            Log.log("Overrode Szeibernaetick Item Mapping for " + item.getRegistryName() + "/" + cap.toString()
                    + "! This should not happen. Did you try to register different items for the same Capability?",
                    LogType.SETUP, LogType.ERROR);
        }

        Log.log("Registering Capability!", LogType.INFO, LogType.SETUP);
        if(this.idMap.put(identifier, cap) != null) {
            Log.log("Overrode Szeibernaetick Capability Mapping for " + item.getRegistryName() + "/" + identifier
                    + "! This should not happen. Did you register 2 Capabilities with identical Identifiers?",
                    LogType.SETUP, LogType.ERROR);
        }
    }

    public SzeibernaetickItem getItemFromCapability(Class<? extends ISzeibernaetick> capability) {
        return this.itemMap.get(capability);
    }

    public SzeibernaetickItem getItemFromIdentifier(SzeibernaetickIdentifier identifier) {
        return this.getItemFromCapability(this.idMap.get(identifier));
    }

    public Class<? extends ISzeibernaetick> getCapabilityFromIdentifier(SzeibernaetickIdentifier identifier) {
        Log.log("Capability class was requested. Do I have it? Its name is " + identifier + " :"
                + (this.idMap.get(identifier) != null), LogType.SPECIFIC, LogType.DEBUG);
        return this.idMap.get(identifier);
    }

}
