package main.de.grzb.szeibernaeticks.szeibernaeticks.capability.armoury;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

import main.de.grzb.szeibernaeticks.control.Log;
import main.de.grzb.szeibernaeticks.control.LogType;
import main.de.grzb.szeibernaeticks.szeibernaeticks.BodyPart;
import main.de.grzb.szeibernaeticks.szeibernaeticks.capability.ISzeibernaetickCapability;
import main.de.grzb.szeibernaeticks.szeibernaeticks.event.SzeibernaetickInstalledEvent;
import main.de.grzb.szeibernaeticks.szeibernaeticks.event.SzeibernaetickRemovedEvent;
import net.minecraft.entity.Entity;
import net.minecraftforge.common.MinecraftForge;

public class Armoury implements IArmouryCapability {

    private Entity entityAttachedTo;
    protected ConcurrentHashMap<BodyPart, ISzeibernaetickCapability> bodyMap;
    protected ConcurrentHashMap<Class<? extends ISzeibernaetickCapability>, ISzeibernaetickCapability> itemMap;

    public Armoury() {
        this.bodyMap = new ConcurrentHashMap<>();
        this.itemMap = new ConcurrentHashMap<>();
    }

    public Armoury(Entity entity) {
        this();
        this.entityAttachedTo = entity;
    }

    @Override
    public boolean addSzeibernaetick(ISzeibernaetickCapability szeiber) {
        Log.log("Attempting to add " + szeiber.getIdentifier(), LogType.DEBUG, LogType.SZEIBER_ARM);
        Log.log("BodyPart is: " + szeiber.getBodyPart().toString(), LogType.DEBUG, LogType.SZEIBER_ARM,
                LogType.SPECIFIC);
        Log.log("Szeiber in that Slot is: " + this.bodyMap.get(szeiber.getBodyPart()), LogType.DEBUG,
                LogType.SZEIBER_ARM, LogType.SPECIFIC);

        if(this.bodyMap.get(szeiber.getBodyPart()) == null) {
            Log.log("Body Part is not used.", LogType.DEBUG, LogType.SZEIBER_ARM, LogType.SPECIFIC);
            // Tell anyone interested that you are installing a Szeibernaetick
            MinecraftForge.EVENT_BUS.post(new SzeibernaetickInstalledEvent(this, szeiber));
            this.bodyMap.put(szeiber.getBodyPart(), szeiber);
            this.itemMap.put(szeiber.getClass(), szeiber);
            return true;
        }
        return false;
    }

    @Override
    public ISzeibernaetickCapability getSzeibernaetick(Class<? extends ISzeibernaetickCapability> szeiberClass) {
        return this.itemMap.get(szeiberClass);
    }

    @Override
    public Collection<ISzeibernaetickCapability> getSzeibernaeticks() {
        return this.bodyMap.values();
    }

    @Override
    public ISzeibernaetickCapability removeSzeibernaetick(ISzeibernaetickCapability szeiber) {
        Class<? extends ISzeibernaetickCapability> szeibernaetickClass = szeiber.getClass();
        if(this.itemMap.get(szeibernaetickClass) != null) {
            MinecraftForge.EVENT_BUS.post(new SzeibernaetickRemovedEvent(this, szeiber));
            this.itemMap.remove(szeibernaetickClass);
            this.bodyMap.remove(szeiber.getBodyPart());
            return szeiber;
        }
        return null;
    }

    @Override
    public ISzeibernaetickCapability getBodyPart(BodyPart b) {
        return this.bodyMap.get(b);
    }

    @Override
    public Entity getEntity() {
        return this.entityAttachedTo;
    }

}
