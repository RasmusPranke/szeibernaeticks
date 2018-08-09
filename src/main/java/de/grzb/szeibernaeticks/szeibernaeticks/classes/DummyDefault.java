package de.grzb.szeibernaeticks.szeibernaeticks.classes;

import de.grzb.szeibernaeticks.control.Log;
import de.grzb.szeibernaeticks.control.LogType;
import de.grzb.szeibernaeticks.szeibernaeticks.BodyPart;
import de.grzb.szeibernaeticks.szeibernaeticks.ISzeibernaetick;
import net.minecraft.nbt.NBTTagCompound;

public class DummyDefault implements ISzeibernaetick {

    public DummyDefault() {
        Log.log("Dummy was instantiated! Why?", LogType.DEBUG, LogType.ERROR);
    }

    @Override
    public String getIdentifier() {
        return "DEFAULT_SHOULD_NOT_SHOW_UP";
    }

    @Override
    public NBTTagCompound toNBT() {
        return new NBTTagCompound();
    }

    @Override
    public void fromNBT(NBTTagCompound nbt) {}

    @Override
    public BodyPart getBodyPart() {
        return BodyPart.BONES;
    }

}
