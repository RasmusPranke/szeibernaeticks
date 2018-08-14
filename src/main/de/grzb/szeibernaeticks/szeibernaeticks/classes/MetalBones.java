package main.de.grzb.szeibernaeticks.szeibernaeticks.classes;

import java.util.ArrayList;

import main.de.grzb.szeibernaeticks.Szeibernaeticks;
import main.de.grzb.szeibernaeticks.item.szeibernaetick.SzeibernaetickBase;
import main.de.grzb.szeibernaeticks.szeibernaeticks.BodyPart;
import main.de.grzb.szeibernaeticks.szeibernaeticks.control.Switch;
import net.minecraft.nbt.NBTTagCompound;

/**
 * The Capability corresponding to {@link ItemMetalBones}.
 *
 * @author DemRat
 */
public class MetalBones extends SzeibernaetickBase {
    private static final String identifier = Szeibernaeticks.MOD_ID + ":MetalBones";
    private int damage;

    @Override
    public String getIdentifier() {
        return identifier;
    }

    public MetalBones() {
        this.damage = 0;
    }

    @Override
    public NBTTagCompound toNBT() {
        NBTTagCompound base = new NBTTagCompound();
        base.setInteger("damage", this.damage);
        return base;
    }

    @Override
    public void fromNBT(NBTTagCompound nbt) {
        this.damage = nbt.getInteger("damage");
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
