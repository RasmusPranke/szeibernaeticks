package main.de.grzb.szeibernaeticks.szeibernaeticks.capability;

import io.netty.util.internal.ConcurrentSet;
import main.de.grzb.szeibernaeticks.control.Log;
import main.de.grzb.szeibernaeticks.control.LogType;
import main.de.grzb.szeibernaeticks.szeibernaeticks.BodyPart;
import main.de.grzb.szeibernaeticks.szeibernaeticks.energy.EnergyConsumptionEvent;
import main.de.grzb.szeibernaeticks.szeibernaeticks.energy.EnergyPriority;
import main.de.grzb.szeibernaeticks.szeibernaeticks.energy.EnergyProductionEvent;
import main.de.grzb.szeibernaeticks.szeibernaeticks.energy.IEnergyConsumer;
import main.de.grzb.szeibernaeticks.szeibernaeticks.energy.IEnergyProducer;
import net.minecraft.nbt.NBTTagCompound;

public class ConductiveVeinsCapability implements ISzeibernaetickCapability {

    private ConcurrentSet<IEnergyProducer> producers = new ConcurrentSet<IEnergyProducer>();
    private ConcurrentSet<IEnergyConsumer> consumers = new ConcurrentSet<IEnergyConsumer>();

    public ConductiveVeinsCapability() {
    }

    public void register(ISzeibernaetickCapability szeiber) {
        Log.log("Adding " + szeiber.getIdentifier() + " to the Energy Network.", LogType.DEBUG, LogType.SZEIBER_HANDLER,
                LogType.SZEIBER_ENERGY);
        if(szeiber instanceof IEnergyProducer) {
            Log.log("It's a Producer.", LogType.DEBUG, LogType.SZEIBER_HANDLER, LogType.SZEIBER_ENERGY,
                    LogType.SPECIFIC);
            this.producers.add((IEnergyProducer) szeiber);
        }

        if(szeiber instanceof IEnergyConsumer) {
            Log.log("It's a Consumer.", LogType.DEBUG, LogType.SZEIBER_HANDLER, LogType.SZEIBER_ENERGY,
                    LogType.SPECIFIC);
            this.consumers.add((IEnergyConsumer) szeiber);
        }
    }

    @Override
    public String getIdentifier() {
        return "VeinsCapability";
    }

    @Override
    public NBTTagCompound toNBT() {
        return new NBTTagCompound();
    }

    @Override
    public void fromNBT(NBTTagCompound nbt) {
    }

    @Override
    public BodyPart getBodyPart() {
        return BodyPart.VEINS;
    }

    /**
     * Handles the given EnergyConsumptionEvent TODO: DOesn't quite work right
     * now, debug this + Archers eyes
     *
     * @param e
     *            The given Event.
     */
    public void handleConsumptionEvent(EnergyConsumptionEvent e) {
        Log.log("Consuming Energy on Entity: " + e.getEntity().getName(), LogType.DEBUG, LogType.SZEIBER_HANDLER,
                LogType.SZEIBER_ENERGY, LogType.SPAMMY);
        // Iterate over all Priorities, in order of severance
        EnergyPriority[] prioArray = EnergyPriority.values();
        int length = prioArray.length;
        for(int i = 0; i < length / 2; i++) {
            EnergyPriority temp = prioArray[i];
            prioArray[i] = prioArray[length - i - 1];
            prioArray[length - i - 1] = temp;
        }
        outestLoop: for(EnergyPriority prio : EnergyPriority.values()) {
            boolean canStillProduce = true;
            // As long as at least one producer of the current prio can still
            // produce, repeat
            while(canStillProduce) {
                canStillProduce = false;
                // Ask each Producer to produce
                for(IEnergyProducer producer : this.producers) {
                    // But only if they belong to the current Priority
                    if(producer.currentProductionPriority() == prio) {
                        e.cover(producer.produceAdHoc());
                        if(e.getRemainingAmount() == 0) {
                            break outestLoop;
                        }
                        // Remember whether there are still Producers able to
                        // produce
                        canStillProduce = canStillProduce || producer.canStillProduce();
                    }
                }
            }
        }
    }

    /**
     * Handles the given EnergyProductionEvent.
     *
     * @param e
     *            The given Event.
     */
    public void handleProductionEvent(EnergyProductionEvent e) {
        Log.log("Producing Energy on Entity: " + e.getEntity().getName(), LogType.DEBUG, LogType.SZEIBER_HANDLER,
                LogType.SZEIBER_ENERGY, LogType.SPAMMY);
        // Iterate over all Priorities, in order of severance
        outestLoop: for(EnergyPriority prio : EnergyPriority.values()) {
            boolean canStillProduce = true;
            // As long as at least one producer of the current prio can still
            // produce, repeat
            while(canStillProduce) {
                canStillProduce = false;
                // Ask each Producer to produce
                for(IEnergyConsumer consumer : this.consumers) {
                    // But only if they belong to the current Priority
                    if(consumer.currentConsumptionPrio() == prio) {
                        e.consume(consumer.consume());
                        if(e.getRemainingAmount() == 0) {
                            break outestLoop;
                        }
                        // Remember whether there are still Producers able to
                        // produce
                        canStillProduce = canStillProduce || consumer.canStillConsume();
                    }
                }
            }
        }
    }
}
