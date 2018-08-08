package main.de.grzb.szeibernaeticks.szeibernaeticks;

import net.minecraft.nbt.NBTTagCompound;

//TODO: Add option to enable/disable certain cybernetics.
//TODO: Add alternate quickbar for this. RenderGameOverlayEvent!

/**
 * Stores and retrieves the data behind a Szeibernaetick.
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
 * @author DemRat
 */
public interface ISzeibernaetick {

    /**
     * Returns an unique Identifier for this capability _class_, not instance.
     * This is primarily used as part of the key when attaching this capability
     * to items.
     *
     * @return An unique Identifier.
     */
    String getIdentifier();

    /**
     * Stores this Capability as a NBTTagCompound.
     *
     * @return The NBT storing this capability.
     */
    NBTTagCompound toNBT();

    /**
     * Restores this capabilities values from an NBTTagCompound.
     *
     * @param nbt
     */
    void fromNBT(NBTTagCompound nbt);

    /**
     * The {@code BodyPart} this Capability inhabits when installed.
     *
     * @return
     */
    BodyPart getBodyPart();
}
