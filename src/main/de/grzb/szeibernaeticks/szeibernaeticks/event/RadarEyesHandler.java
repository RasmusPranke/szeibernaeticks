package main.de.grzb.szeibernaeticks.szeibernaeticks.event;

import main.de.grzb.szeibernaeticks.control.Log;
import main.de.grzb.szeibernaeticks.control.LogType;
import main.de.grzb.szeibernaeticks.szeibernaeticks.armoury.ArmouryProvider;
import main.de.grzb.szeibernaeticks.szeibernaeticks.armoury.IArmoury;
import main.de.grzb.szeibernaeticks.szeibernaeticks.classes.RadarEyes;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RadarEyesHandler implements ISzeibernaetickEventHandler {
    @SubscribeEvent
    public void onLivingUpdate(LivingUpdateEvent e) {
        Log.log("[RadEyesHandler] Recieving event!", LogType.DEBUG, LogType.SPAMMY, LogType.SZEIBER_HANDLER);

        EntityLivingBase updated = e.getEntityLiving();
        IArmoury armoury = updated.getCapability(ArmouryProvider.ARMOURY_CAP, null);

        if(armoury != null) {
            Log.log("[RadEyesHandler] Found Armory!", LogType.DEBUG, LogType.SPAMMY, LogType.SZEIBER_HANDLER);
            RadarEyes eyes = (RadarEyes) armoury
                    .getSzeibernaetick(RadarEyes.class);

            if(eyes != null) {
                Log.log("[RadEyesHandler] Found Eyes!", LogType.DEBUG, LogType.SPAMMY, LogType.SZEIBER_HANDLER);
                eyes.grantVision(e);
            }
        }
    }
}
