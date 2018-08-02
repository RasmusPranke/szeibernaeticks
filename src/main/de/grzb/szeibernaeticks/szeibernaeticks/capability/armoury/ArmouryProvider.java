package main.de.grzb.szeibernaeticks.szeibernaeticks.capability.armoury;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

/**
 * Does Stuff related to Capabilities. TODO: What exactly, again?
 *
 * @author DemRat
 */
public class ArmouryProvider implements ICapabilitySerializable<NBTBase> {

    @CapabilityInject(IArmoury.class)
    public static final Capability<IArmoury> ARMOURY_CAP = null;

    private IArmoury instance;
    private Entity attachedTo;

    public ArmouryProvider(Entity attachedTo) {
        this.attachedTo = attachedTo;
        this.instance = new Armoury(attachedTo);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability.equals(ARMOURY_CAP);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if(capability.equals(ARMOURY_CAP)) {
            return ARMOURY_CAP.cast(this.instance);
        }
        return null;
    }

    @Override
    public NBTBase serializeNBT() {
        return ARMOURY_CAP.getStorage().writeNBT(ARMOURY_CAP, this.instance, null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
        ARMOURY_CAP.getStorage().readNBT(ARMOURY_CAP, this.instance, null, nbt);
    }

    public Entity getEntity() {
        return attachedTo;
    }

}
