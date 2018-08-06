package main.de.grzb.szeibernaeticks.szeibernaeticks.event;

import main.de.grzb.szeibernaeticks.control.Log;
import main.de.grzb.szeibernaeticks.control.LogType;
import main.de.grzb.szeibernaeticks.szeibernaeticks.armoury.ArmouryProvider;
import main.de.grzb.szeibernaeticks.szeibernaeticks.armoury.IArmoury;
import main.de.grzb.szeibernaeticks.szeibernaeticks.capability.ConductiveVeins;
import main.de.grzb.szeibernaeticks.szeibernaeticks.capability.ISzeibernaetick;
import main.de.grzb.szeibernaeticks.szeibernaeticks.energy.EnergyConsumptionEvent;
import main.de.grzb.szeibernaeticks.szeibernaeticks.energy.EnergyProductionEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ConductiveVeinsHandler implements ISzeibernaetickEventHandler {

    @SubscribeEvent
    public void onSzeibernaetickInstalled(SzeibernaetickInstalledEvent e) {
        Log.log("[ConVeinsHandler] SzeiberVeins checking Installation!", LogType.DEBUG, LogType.SZEIBER_ENERGY,
                LogType.SZEIBER_HANDLER);

        // If these veins were just installed
        if(e.installedSzeibernaetick instanceof ConductiveVeins) {
            Log.log("[ConVeinsHandler] SzeiberVeins were installed.", LogType.DEBUG, LogType.SZEIBER_ENERGY,
                    LogType.SZEIBER_HANDLER);
            ConductiveVeins veins = (ConductiveVeins) e.installedSzeibernaetick;
            // Register all other Szeibernaeticks to these veins.
            for(ISzeibernaetick szeiber : e.armoury.getSzeibernaeticks()) {
                Log.log("[ConVeinsHandler] Adding existing szeibernaeticks!", LogType.DEBUG, LogType.SZEIBER_ENERGY,
                        LogType.SPECIFIC);
                veins.register(szeiber);
            }
        }
        else {
            // Otherwise, check if these veins are installed right now.
            if(e.armoury.getSzeibernaetick(ConductiveVeins.class) != null) {
                Log.log("[ConVeinsHandler] Something else was installed and SzeiberVeins exist.", LogType.DEBUG,
                        LogType.SZEIBER_ENERGY, LogType.SZEIBER_HANDLER);
                // If they are, register the newly installed Szeibernaetick.
                ((ConductiveVeins) e.armoury.getSzeibernaetick(ConductiveVeins.class))
                        .register(e.installedSzeibernaetick);
            }
        }
    }

    @SubscribeEvent
    public void onSzeibernaetickRemoved(SzeibernaetickRemovedEvent e) {
        Log.log("[ConVeinsHandler] SzeiberVeins checking Removal!", LogType.DEBUG, LogType.SZEIBER_ENERGY,
                LogType.SZEIBER_HANDLER);
        if(e.removedSzeibernaetick instanceof ConductiveVeins) {
            Log.log("[ConVeinsHandler] SzeiberVeins were removed.", LogType.DEBUG, LogType.SZEIBER_ENERGY,
                    LogType.SZEIBER_HANDLER);
            ConductiveVeins veins = (ConductiveVeins) e.removedSzeibernaetick;
            // Register all other Szeibernaeticks to these veins.
            for(ISzeibernaetick szeiber : e.armoury.getSzeibernaeticks()) {
                veins.unregister(szeiber);
            }
        }
        else {
            // Otherwise, check if these veins are installed right now.
            if(e.armoury.getSzeibernaetick(ConductiveVeins.class) != null) {
                Log.log("[ConVeinsHandler] Something else was removed and SzeiberVeins exist.", LogType.DEBUG,
                        LogType.SZEIBER_ENERGY, LogType.SZEIBER_HANDLER);
                // If they are, register the newly installed Szeibernaetick.
                ((ConductiveVeins) e.armoury.getSzeibernaetick(ConductiveVeins.class))
                        .unregister(e.removedSzeibernaetick);
            }
        }
    }

    @SubscribeEvent
    public void onEnergyConsumptionEvent(EnergyConsumptionEvent e) {
        Log.log("[ConVeinsHandler] An EnergyConsumptionEvent happened", LogType.DEBUG, LogType.SZEIBER_ENERGY,
                LogType.SZEIBER_HANDLER, LogType.SPAMMY);
        IArmoury armoury = e.getEntity().getCapability(ArmouryProvider.ARMOURY_CAP, null);
        ConductiveVeins veins = (ConductiveVeins) armoury.getSzeibernaetick(ConductiveVeins.class);
        if(veins != null) {
            Log.log("[ConVeinsHandler] An EnergyConsumptionEvent is being handled.", LogType.DEBUG,
                    LogType.SZEIBER_ENERGY, LogType.SZEIBER_HANDLER, LogType.SPAMMY);
            veins.handleConsumptionEvent(e);
        }
    }

    @SubscribeEvent
    public void onEnergyProductionEvent(EnergyProductionEvent e) {
        Log.log("[ConVeinsHandler] An EnergyProductionEvent happened", LogType.DEBUG, LogType.SZEIBER_ENERGY,
                LogType.SZEIBER_HANDLER, LogType.SPAMMY);
        IArmoury armoury = e.getEntity().getCapability(ArmouryProvider.ARMOURY_CAP, null);
        ConductiveVeins veins = (ConductiveVeins) armoury.getSzeibernaetick(ConductiveVeins.class);
        if(veins != null) {
            Log.log("[ConVeinsHandler] An EnergyProductionEvent is being handled.", LogType.DEBUG,
                    LogType.SZEIBER_ENERGY, LogType.SZEIBER_HANDLER, LogType.SPAMMY);
            veins.handleProductionEvent(e);
        }

    }
}
