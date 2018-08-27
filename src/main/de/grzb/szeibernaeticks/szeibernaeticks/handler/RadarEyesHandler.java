package main.de.grzb.szeibernaeticks.szeibernaeticks.handler;

import main.de.grzb.szeibernaeticks.szeibernaeticks.armoury.ArmouryProvider;
import main.de.grzb.szeibernaeticks.szeibernaeticks.armoury.IArmoury;
import main.de.grzb.szeibernaeticks.szeibernaeticks.classes.RadarEyes;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RadarEyesHandler implements ISzeibernaetickEventHandler {
    @SubscribeEvent
    public void onLivingUpdate(LivingUpdateEvent e) {
        EntityLivingBase updated = e.getEntityLiving();
        IArmoury armoury = updated.getCapability(ArmouryProvider.ARMOURY_CAP, null);

        if(armoury != null) {
            RadarEyes eyes = (RadarEyes) armoury.getSzeibernaetick(RadarEyes.class);

            if(eyes != null) {
                eyes.grantVision(e);
            }
        }
    }
}
