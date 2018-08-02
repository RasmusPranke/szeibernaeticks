package main.de.grzb.szeibernaeticks.szeibernaeticks.capability.armoury;

import main.de.grzb.szeibernaeticks.control.Log;
import main.de.grzb.szeibernaeticks.control.LogType;
import main.de.grzb.szeibernaeticks.szeibernaeticks.SzeibernaetickMapper;
import main.de.grzb.szeibernaeticks.szeibernaeticks.capability.ISzeibernaetick;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

import java.util.Set;

/**
 * Responsible for converting ISzeibernaetickStorageCapabilities to NBT and
 * back, for saving and loading.
 *
 * @author DemRat
 */
public class ArmouryStorage implements IStorage<IArmoury> {

    @Override
    public void readNBT(Capability<IArmoury> capability, IArmoury instance, EnumFacing side, NBTBase nbt) {
        // Attempt to read the NBT as an Compound. If this fails, the Capability
        // wasn't saved correctly.
        NBTTagCompound tag;
        try {
            tag = (NBTTagCompound) nbt;
        }
        catch(ClassCastException e) {
            Log.log("Failed loading of ISzeibernaetick. NBT is not a NBTTagCompound.", LogType.ERROR);
            Log.logThrowable(e);
            return;
        }

        // All Tags should be compound tags, as the ItemStacks should have been
        // serialized into those. So we can simply generate the ItemStacks from
        // the NBTTags
        Set<String> keys = tag.getKeySet();
        for(String s : keys) {
            NBTTagCompound compound = tag.getCompoundTag(s);
            if(compound != null) {
                ISzeibernaetick cap;
                Class<? extends ISzeibernaetick> capClass = SzeibernaetickMapper.instance.getCapabilityFromIdentifier(s);
                try {
                    cap = capClass.newInstance();
                    cap.fromNBT(compound);
                    instance.addSzeibernaetick(cap);
                }
                catch(InstantiationException e) {
                    Log.log("Could not instantiate ISzeibernaetick of class: " + capClass.toString(), LogType.ERROR);
                    Log.logThrowable(e);
                    e.printStackTrace();
                }
                catch(IllegalAccessException e) {
                    Log.log("Could not access the constructor of the class " + capClass.toString(), LogType.ERROR);
                    Log.logThrowable(e);
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public NBTBase writeNBT(Capability<IArmoury> capability, IArmoury instance, EnumFacing side) {
        // ItemStacks have NBT-representations, so we simply get those and write
        // them to the NBT.
        NBTTagCompound tag = new NBTTagCompound();
        for(ISzeibernaetick szeiber : instance.getSzeibernaeticks()) {
            tag.setTag(szeiber.getIdentifier(), szeiber.toNBT());
        }

        return tag;
    }

}
