package de.grzb.szeibernaeticks.szeibernaeticks;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

/**
 * Class for storing SzeibernaetickCapabilities as NBTs and restoring them
 * again.
 *
 * @author DemRat
 */
public class SzeibernaetickCapabilityStorage implements IStorage<ISzeibernaetick> {

    @Override
    public NBTBase writeNBT(Capability<ISzeibernaetick> capability, ISzeibernaetick instance, EnumFacing side) {
        return instance.toNBT();
    }

    @Override
    public void readNBT(Capability<ISzeibernaetick> capability, ISzeibernaetick instance, EnumFacing side, NBTBase nbt) {
        try {
            instance.fromNBT((NBTTagCompound) nbt);
        }
        catch(ClassCastException e) {
            return;
        }
    }

}
