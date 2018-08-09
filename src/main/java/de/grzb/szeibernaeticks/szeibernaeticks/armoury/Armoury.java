package de.grzb.szeibernaeticks.szeibernaeticks.armoury;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

import de.grzb.szeibernaeticks.control.Log;
import de.grzb.szeibernaeticks.control.LogType;
import de.grzb.szeibernaeticks.szeibernaeticks.BodyPart;
import de.grzb.szeibernaeticks.szeibernaeticks.ISzeibernaetick;
import de.grzb.szeibernaeticks.szeibernaeticks.event.SzeibernaetickInstalledEvent;
import de.grzb.szeibernaeticks.szeibernaeticks.event.SzeibernaetickRemovedEvent;
import net.minecraft.entity.Entity;
import net.minecraftforge.common.MinecraftForge;

public class Armoury implements IArmoury {

    private Entity entityAttachedTo;
    protected ConcurrentHashMap<BodyPart, ISzeibernaetick> bodyMap;
    protected ConcurrentHashMap<Class<? extends ISzeibernaetick>, ISzeibernaetick> itemMap;

    public Armoury() {
        this.bodyMap = new ConcurrentHashMap<>();
        this.itemMap = new ConcurrentHashMap<>();
    }

    public Armoury(Entity entity) {
        this();
        this.entityAttachedTo = entity;
    }

    @Override
    public boolean addSzeibernaetick(ISzeibernaetick szeiber) {
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
    public ISzeibernaetick getSzeibernaetick(Class<? extends ISzeibernaetick> szeiberClass) {
        return this.itemMap.get(szeiberClass);
    }

    @Override
    public Collection<ISzeibernaetick> getSzeibernaeticks() {
        return this.bodyMap.values();
    }

    @Override
    public ISzeibernaetick removeSzeibernaetick(ISzeibernaetick szeiber) {
        Class<? extends ISzeibernaetick> szeibernaetickClass = szeiber.getClass();
        if(this.itemMap.get(szeibernaetickClass) != null) {
            MinecraftForge.EVENT_BUS.post(new SzeibernaetickRemovedEvent(this, szeiber));
            this.itemMap.remove(szeibernaetickClass);
            this.bodyMap.remove(szeiber.getBodyPart());
            return szeiber;
        }
        return null;
    }

    @Override
    public ISzeibernaetick getBodyPart(BodyPart b) {
        return this.bodyMap.get(b);
    }

    @Override
    public Entity getEntity() {
        return this.entityAttachedTo;
    }

}
