package main.de.grzb.szeibernaeticks.szeibernaeticks;

import main.de.grzb.szeibernaeticks.Szeibernaeticks;
import main.de.grzb.szeibernaeticks.networking.NetworkWrapper;
import main.de.grzb.szeibernaeticks.networking.SzeiberCapMessage;
import main.de.grzb.szeibernaeticks.szeibernaeticks.armoury.ArmouryProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Responsible for attaching the {@code ISzeibernaetickStorageCapability} to
 * Entities.
 *
 * @author DemRat
 */
public class ArmouryAttacher {

    /**
     * Attaches ISzeibernaetickStorage to entities.
     *
     * @param event
     *            The event prompting to attach the Capability.
     */
    @SubscribeEvent
    public void attachToLiving(AttachCapabilitiesEvent<Entity> event) {
        if(event.getObject() instanceof EntityLivingBase) {
            event.addCapability(new ResourceLocation(Szeibernaeticks.MOD_ID), new ArmouryProvider(event.getObject()));
        }
    }

    // TODO: Test for multiplayer
    @SubscribeEvent
    public void attachToJoining(EntityJoinWorldEvent event) {
        Entity entity = event.getEntity();
        if(entity instanceof EntityPlayerMP && entity.hasCapability(ArmouryProvider.ARMOURY_CAP, null)) {
            for(ISzeibernaetick szeiber : entity.getCapability(ArmouryProvider.ARMOURY_CAP, null)
                    .getSzeibernaeticks()) {
                NetworkWrapper.INSTANCE.sendToAll(new SzeiberCapMessage(szeiber));
            }
        }
    }
}
