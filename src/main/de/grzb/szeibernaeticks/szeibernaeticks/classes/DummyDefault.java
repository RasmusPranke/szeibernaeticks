package main.de.grzb.szeibernaeticks.szeibernaeticks.classes;

import java.util.ArrayList;

import main.de.grzb.szeibernaeticks.control.Log;
import main.de.grzb.szeibernaeticks.control.LogType;
import main.de.grzb.szeibernaeticks.item.szeibernaetick.SzeibernaetickBase;
import main.de.grzb.szeibernaeticks.szeibernaeticks.BodyPart;
import main.de.grzb.szeibernaeticks.szeibernaeticks.control.Switch;
import net.minecraft.nbt.NBTTagCompound;

public class DummyDefault extends SzeibernaetickBase {

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
    public void fromNBT(NBTTagCompound nbt) {
        return;
    }

    @Override
    public BodyPart getBodyPart() {
        return BodyPart.BONES;
    }

    @Override
    public Iterable<Switch> GetSwitches() {
        return new ArrayList<Switch>();
    }

}
