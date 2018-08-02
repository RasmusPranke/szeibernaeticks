package main.de.grzb.szeibernaeticks.szeibernaeticks;

import java.util.concurrent.ConcurrentHashMap;

import main.de.grzb.szeibernaeticks.control.Log;
import main.de.grzb.szeibernaeticks.control.LogType;
import main.de.grzb.szeibernaeticks.item.szeibernaetick.SzeibernaetickBase;
import main.de.grzb.szeibernaeticks.szeibernaeticks.capability.ISzeibernaetickCapability;

/**
 * Maps the identifiers of Capabilities to the Capability classes as well as
 * those Classes to Items.
 *
 * @author DemRat
 */
public class SzeibernaetickMapper {

    public static final SzeibernaetickMapper instance = new SzeibernaetickMapper();

    private ConcurrentHashMap<Class<? extends ISzeibernaetickCapability>, SzeibernaetickBase> itemMap;
    private ConcurrentHashMap<String, Class<? extends ISzeibernaetickCapability>> idMap;

    private SzeibernaetickMapper() {
        this.itemMap = new ConcurrentHashMap<Class<? extends ISzeibernaetickCapability>, SzeibernaetickBase>();
        this.idMap = new ConcurrentHashMap<String, Class<? extends ISzeibernaetickCapability>>();
    }

    /**
     * Registers the given Class as corresponding to the given Item. This also
     * adds the mapping from identifier to Class.
     *
     * @param cap
     * @param item
     */
    public void register(Class<? extends ISzeibernaetickCapability> cap, SzeibernaetickBase item) {
        Log.log("Trying to register Capability!", LogType.DEBUG, LogType.SETUP);
        if(this.itemMap.put(cap, item) != null) {
            Log.log("Overrode Szeibernaetick Item Mapping for " + item.getRegistryName() + "/" + cap.toString()
                            + "! This should not happen. Did you try to register different items for the same Capability?",
                    LogType.SETUP, LogType.ERROR);
        }

        try {
            String identifier = cap.newInstance().getIdentifier();
            Log.log("Registering Capability!", LogType.INFO, LogType.SETUP);
            if(this.idMap.put(identifier, cap) != null) {
                Log.log("Overrode Szeibernaetick Capability Mapping for " + item.getRegistryName() + "/" + identifier
                                + "! This should not happen. Did you register 2 Capabilities with identical Identifiers?",
                        LogType.SETUP, LogType.ERROR);
            }
        }
        catch(InstantiationException e) {
            e.printStackTrace();
        }
        catch(IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public SzeibernaetickBase getItemFromCapability(Class<? extends ISzeibernaetickCapability> capability) {
        return this.itemMap.get(capability);
    }

    public SzeibernaetickBase getItemFromIdentifier(String identifier) {
        return this.getItemFromCapability(this.idMap.get(identifier));
    }

    public Class<? extends ISzeibernaetickCapability> getCapabilityFromIdentifier(String identifier) {
        Log.log("Capability class was requested. Do I have it? Its name is " + identifier + " :"
                + (this.idMap.get(identifier) != null), LogType.SPECIFIC, LogType.DEBUG);
        return this.idMap.get(identifier);
    }

}
