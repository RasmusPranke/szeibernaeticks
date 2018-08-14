package main.de.grzb.szeibernaeticks.item.szeibernaetick;

import main.de.grzb.szeibernaeticks.control.Log;
import main.de.grzb.szeibernaeticks.control.LogType;
import main.de.grzb.szeibernaeticks.item.ItemBase;
import main.de.grzb.szeibernaeticks.szeibernaeticks.BodyPart;
import main.de.grzb.szeibernaeticks.szeibernaeticks.armoury.ArmouryProvider;
import main.de.grzb.szeibernaeticks.szeibernaeticks.armoury.IArmoury;
import main.de.grzb.szeibernaeticks.szeibernaeticks.control.Switch;
import main.de.grzb.szeibernaeticks.szeibernaeticks.event.ISzeibernaetickEventHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

/**
 * TODO: Overhaul this Class Comment Stores and retrieves the data behind a
 * Szeibernaetick.
 *
 *
 * Structure of an NBTTag containing a Szeibernaeticks Tag: { OtherTag : Value,
 * "Szeibernaeticks" : { Unlocalized Name : { "Properties" : { "damage" : The
 * damage value, Item Specific Properties : Their value } } }, AnotherTag :
 * Value }
 *
 * When passing this between methods/objects, the "Szeibernaeticks" Compound Tag
 * is the base tag: { Unlocalized Name : { "Properties" : { "damage" : The
 * damage value, Item Specific Properties : Their value } } }
 *
 * Naming convention: Compound Tags are written in PascalCase : "Properties",
 * "ThisIsAdvancedStuff" Primitive Tags are written in camelCase : "damage",
 * "advancedValue"
 *
 *
 * Denotes an Item usable as an Szeibernaetick.<br>
 * <br>
 * To add a new Szeibernaetick, you need a) an Item implementing
 * SzeibernaetickBase, b) any Method of adding it to the
 * ISzeibernaetickStorageCapability of an Entity and c) an
 * ISzeibernaetickEventHandler implementing the functionality via
 * EventSubscriptions.
 *
 * @author DemRat
 */
public abstract class SzeibernaetickBase extends ItemBase {

    public SzeibernaetickBase(String name, Class<? extends ISzeibernaetickEventHandler> handler) {
        this(name, handler, CreativeTabs.COMBAT);
    }

    public SzeibernaetickBase(String name, Class<? extends ISzeibernaetickEventHandler> handler, CreativeTabs tab) {
        super(name);
        Log.log("Creating Item of type: " + this.getClass(), LogType.DEBUG, LogType.INSTANTIATION, LogType.ITEM);

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

        this.setCreativeTab(tab);
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
            if(szeiberArm != null && szeiberArm.addSzeibernaetick(this)) {
                Log.log("Successfully added Capability! Shrinking stack now.", LogType.ITEM, LogType.DEBUG,
                        LogType.SPECIFIC);
                thisStack.shrink(1);
            }
        }

        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, thisStack);
    }

    /**
     * The {@code BodyPart} this Capability inhabits when installed.
     *
     * @return
     */
    public abstract BodyPart getBodyPart();

    /**
     * Returns an unique Identifier for this capability _class_, not instance.
     * This is primarily used as part of the key when attaching this capability
     * to items.
     *
     * @return An unique Identifier.
     */
    public abstract String getIdentifier();

    /**
     * Stores this Capability as a NBTTagCompound.
     *
     * @return The NBT storing this capability.
     */
    public abstract NBTTagCompound toNBT();

    /**
     * Restores this capabilities values from an NBTTagCompound.
     *
     * @param nbt
     */
    public abstract void fromNBT(NBTTagCompound nbt);

    /**
     * Returns a list of switchables which together control all ad-hoc
     * modifiable state of this Szeibernaetick.
     * 
     * @return
     */
    public abstract Iterable<Switch> GetSwitches();
}
