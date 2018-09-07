package main.de.grzb.szeibernaeticks.szeibernaeticks.classes;

import java.util.ArrayList;

import main.de.grzb.szeibernaeticks.control.Log;
import main.de.grzb.szeibernaeticks.control.LogType;
import main.de.grzb.szeibernaeticks.szeibernaeticks.BodyPart;
import main.de.grzb.szeibernaeticks.szeibernaeticks.ISzeibernaetick;
import main.de.grzb.szeibernaeticks.szeibernaeticks.SzeibernaetickIdentifier;
import main.de.grzb.szeibernaeticks.szeibernaeticks.control.Switch;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class DummyDefault implements ISzeibernaetick {

    public DummyDefault() {
        Log.log("Dummy was instantiated! Why?", LogType.DEBUG, LogType.ERROR);
    }

    @Override
    public SzeibernaetickIdentifier getIdentifier() {
        return new SzeibernaetickIdentifier("SRSRLY_NO", "DEFAULT_SHOULD_NOT_SHOW_UP");
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
    public Iterable<Switch> getSwitches() {
        return new ArrayList<Switch>();
    }

    @Override
    public ItemStack generateItemStack() {
        return null;
    }

    @Override
    public String toNiceString() {
        return "WHY IS THIS HERE";
    }

}
