package de.grzb.szeibernaeticks.szeibernaeticks.armoury;

import java.util.Collection;

import de.grzb.szeibernaeticks.szeibernaeticks.BodyPart;
import de.grzb.szeibernaeticks.szeibernaeticks.ISzeibernaetick;
import net.minecraft.entity.Entity;

/**
 * Stores {@code ISzeibernaeticksCapabilities}.<br>
 * <br>
 * Provides Methods to store and retrieve {@code ISzeibernaetickCapabilities}
 * based on their class or their {@code BodyPart}.
 *
 * @author DemRat
 */
public interface IArmoury {

    /**
     * Returns the Entity the armoury is attached to.
     *
     * @return Duh.
     */
    Entity getEntity();

    /**
     * Adds the given ISzeibernaetick to storage.
     *
     * @param szeibernaetick
     *            The {@code ISzeibernaetick} to add to this Storage.
     * @return True if successful
     */
    boolean addSzeibernaetick(ISzeibernaetick szeibernaetick);

    /**
     * Returns the instance of the given {@code ISzeibernaetick}, or
     * null if it is none is installed.
     *
     * @param szeiberClass
     *            The class of the {@code ISzeibernaetick} to look up.
     * @return The {@code ISzeibernaetick} instance of the given
     *         class, or null
     */
    ISzeibernaetick getSzeibernaetick(Class<? extends ISzeibernaetick> szeiberClass);

    /**
     * Returns an Array containing all installed
     * {@code ISzeibernaetickCapabilities}.
     *
     * @return A Collection of all installed {@code ISzeibernaeticks}.
     */
    Collection<ISzeibernaetick> getSzeibernaeticks();

    /**
     * Removes the given {@code ISzeibernaetick}, if it is installed, and
     * returns its {@code ItemStack}.
     *
     * @param szeibernaetick
     *            The {@code ISzeibernaetick} to remove.
     * @return A copy of the {@code ItemStack} of the removed
     *         {@code ISzeibernaetick}, or null if it wasn't installed.
     */
    ISzeibernaetick removeSzeibernaetick(ISzeibernaetick szeibernaetick);

    /**
     * Returns the {@code ISzeibernaetick} associated with this
     * BodyPart.
     *
     * @param bodyPart
     *            The {@code BodyPart} being asked for.
     * @return The {@code ISzeibernaetick} stored for the given
     *         {@code BodyPart}.
     */
    ISzeibernaetick getBodyPart(BodyPart bodyPart);

}
