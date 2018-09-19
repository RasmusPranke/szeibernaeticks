package main.de.grzb.szeibernaeticks.item;

import main.de.grzb.szeibernaeticks.control.Log;
import main.de.grzb.szeibernaeticks.control.LogType;
import main.de.grzb.szeibernaeticks.szeibernaeticks.BodyPart;
import main.de.grzb.szeibernaeticks.szeibernaeticks.ISzeibernaetick;
import main.de.grzb.szeibernaeticks.szeibernaeticks.SzeibernaetickIdentifier;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

/**
 * Represents a SzeiberClass in item form. There must always exist a single
 * implementation for each SzeiberClass.
 * 
 * @author DemRat
 *
 */
public abstract class SzeibernaetickItem extends ItemBase {

    @CapabilityInject(ISzeibernaetick.class)
    public static final Capability<ISzeibernaetick> SZEIBER_CAP = null;

    public SzeibernaetickItem(SzeibernaetickIdentifier name) {
        this(name, CreativeTabs.COMBAT);
    }

    public SzeibernaetickItem(SzeibernaetickIdentifier name, CreativeTabs tab) {
        super(name.getShortIdentifier());
        Log.log("[SzeiberBase] Creating Item of type: " + this.getClass(), LogType.DEBUG, LogType.INSTANTIATION,
                LogType.ITEM);

        this.setCreativeTab(tab);
    }

    /**
     * The {@code BodyPart} this Capability inhabits when installed.
     *
     * @return
     */
    public abstract BodyPart getBodyPart();

    /**
     * {@inheritDoc}<br>
     * <br>
     * Must initialize the stack to contain the szeibernaetick represented by
     * this item.
     */
    @Override
    public abstract ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt);
}
