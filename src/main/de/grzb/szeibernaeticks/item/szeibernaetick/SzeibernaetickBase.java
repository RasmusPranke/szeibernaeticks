package main.de.grzb.szeibernaeticks.item.szeibernaetick;

import main.de.grzb.szeibernaeticks.control.Log;
import main.de.grzb.szeibernaeticks.control.LogType;
import main.de.grzb.szeibernaeticks.item.ItemBase;
import main.de.grzb.szeibernaeticks.szeibernaeticks.BodyPart;
import main.de.grzb.szeibernaeticks.szeibernaeticks.SzeibernaetickMapper;
import main.de.grzb.szeibernaeticks.szeibernaeticks.capability.ISzeibernaetick;
import main.de.grzb.szeibernaeticks.szeibernaeticks.capability.CapabilityProvider;
import main.de.grzb.szeibernaeticks.szeibernaeticks.capability.armoury.ArmouryProvider;
import main.de.grzb.szeibernaeticks.szeibernaeticks.capability.armoury.IArmoury;
import main.de.grzb.szeibernaeticks.szeibernaeticks.event.ISzeibernaetickEventHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

/**
 * Denotes an Item usable as an Szeibernaetick. The only method is to identify
 * in what {@code BodyPart} this is to be installed.<br>
 * <br>
 * To add a new Szeibernaetick, you need a) an Item implementing
 * ISzeibernaetick, b) any Method of adding it to the
 * ISzeibernaetickStorageCapability of an Entity and c) an
 * ISzeibernaetickEventHandler implementing the functionality via
 * EventSubscriptions.
 *
 * @author DemRat
 */
public class SzeibernaetickBase extends ItemBase {

    private Class<? extends ISzeibernaetick> capabilityClass;

    public SzeibernaetickBase(String name, Class<? extends ISzeibernaetick> capability, Class<? extends ISzeibernaetickEventHandler> handler) {
        super(name);
        Log.log("Creating Item of type: " + this.getClass(), LogType.DEBUG, LogType.INSTANTIATION, LogType.ITEM);

        this.capabilityClass = capability;
        SzeibernaetickMapper.instance.register(this.capabilityClass, this);
        try {
            MinecraftForge.EVENT_BUS.register(handler.newInstance());
        }
        catch(InstantiationException e) {
            Log.log("Could not instantiate the Handler for this Szeibernaetick.", LogType.EXCEPTION);
            Log.logThrowable(e);
        }
        catch(IllegalAccessException e) {
            Log.log("Could not access the Handler for this Szeibernaetick.", LogType.EXCEPTION);
            Log.logThrowable(e);
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        Log.log(this.getClass() + " right clicked!", LogType.ITEM, LogType.DEBUG);
        // Get the Stack currently right-clicked
        ItemStack thisStack = playerIn.getHeldItem(handIn);
        // Make sure that the itemStack actually is of this Item.
        if(thisStack.getItem().equals(this)) {
            Log.log("Correct Item!", LogType.ITEM, LogType.DEBUG, LogType.SPECIFIC);

            IArmoury szeiberArm = playerIn.getCapability(ArmouryProvider.ARMOURY_CAP, null);
            Log.log("Armoury is: " + szeiberArm, LogType.ITEM, LogType.SZEIBER_ARM, LogType.DEBUG, LogType.SPECIFIC);
            // Add the ItemStack to it.
            if(szeiberArm != null && szeiberArm.addSzeibernaetick(this.getCapabilityInstance())) {
                Log.log("Successfully added Capability! Shrinking stack now.", LogType.ITEM, LogType.DEBUG, LogType.SPECIFIC);
                thisStack.shrink(1);
            }
        }

        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, thisStack);
    }

    /**
     * Returns a new instance of the ISzeibernaetick corresponding to
     * this ISzeibernaetick.
     *
     * @return A new instance of the correct ISzeibernaetick.
     */
    public ISzeibernaetick getCapabilityInstance() {
        Log.log("Attempting to instantiate " + this.getClass() + "'s Capability.", LogType.ITEM, LogType.SZEIBER_CAP, LogType.DEBUG);
        try {
            ISzeibernaetick capability = this.capabilityClass.newInstance();
            Log.log("Successfully instantiated capability: " + capability, LogType.ITEM, LogType.SZEIBER_CAP, LogType.DEBUG);
            return capability;
        }
        catch(InstantiationException e) {
            Log.log("Cannot instantiate SzeiberCapability from Class Object!", LogType.ERROR);
            Log.logThrowable(e);
            e.printStackTrace();
        }
        catch(IllegalAccessException e) {
            Log.log("Cannot access the default constructor of SzeiberCapability!", LogType.ERROR);
            Log.logThrowable(e);
            e.printStackTrace();
        }
        return null;
    }

    public BodyPart getBodyPart() {
        Log.log("Getting BodyPart.", LogType.ITEM, LogType.SPECIFIC, LogType.DEBUG);
        return this.getCapabilityInstance().getBodyPart();
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
        Log.log("Adding Capability to this item.", LogType.ITEM, LogType.SZEIBER_CAP, LogType.DEBUG);
        return new CapabilityProvider(this.getCapabilityInstance());
    }
}
