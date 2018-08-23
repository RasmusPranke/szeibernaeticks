package main.de.grzb.szeibernaeticks.item;

import main.de.grzb.szeibernaeticks.control.Log;
import main.de.grzb.szeibernaeticks.control.LogType;
import main.de.grzb.szeibernaeticks.szeibernaeticks.BodyPart;
import main.de.grzb.szeibernaeticks.szeibernaeticks.ISzeibernaetick;
import main.de.grzb.szeibernaeticks.szeibernaeticks.SzeibernaetickIdentifier;
import main.de.grzb.szeibernaeticks.szeibernaeticks.armoury.ArmouryProvider;
import main.de.grzb.szeibernaeticks.szeibernaeticks.armoury.IArmoury;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

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
 * SzeibernaetickItem, b) any Method of adding it to the
 * ISzeibernaetickStorageCapability of an Entity and c) an
 * ISzeibernaetickEventHandler implementing the functionality via
 * EventSubscriptions.
 *
 * @author DemRat
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

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        Log.log("[SzeiberBase]" + this.getClass() + " right clicked!", LogType.ITEM, LogType.DEBUG);
        // Get the Stack currently right-clicked
        ItemStack thisStack = playerIn.getHeldItem(handIn);
        // Make sure that the itemStack actually is of this Item.
        if(thisStack.getItem().equals(this)) {
            Log.log("[SzeiberBase] Correct Item!", LogType.ITEM, LogType.DEBUG, LogType.SPECIFIC);

            IArmoury szeiberArm = playerIn.getCapability(ArmouryProvider.ARMOURY_CAP, null);
            Log.log("Armoury is: " + szeiberArm, LogType.ITEM, LogType.SZEIBER_ARM, LogType.DEBUG, LogType.SPECIFIC);
            if(szeiberArm != null) {
                if(szeiberArm.addSzeibernaetick(thisStack.getCapability(SZEIBER_CAP, null))) {
                    Log.log("[SzeiberBase] Successfully added Capability! Shrinking stack now.", LogType.ITEM,
                            LogType.DEBUG, LogType.SPECIFIC);
                    thisStack.shrink(1);
                }
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

    @Override
    public abstract ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt);
}
