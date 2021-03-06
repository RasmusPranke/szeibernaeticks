package de.grzb.szeibernaeticks.szeibernaeticks.handler;

import de.grzb.szeibernaeticks.control.Log;
import de.grzb.szeibernaeticks.control.LogType;
import de.grzb.szeibernaeticks.szeibernaeticks.armoury.ArmouryProvider;
import de.grzb.szeibernaeticks.szeibernaeticks.armoury.IArmoury;
import de.grzb.szeibernaeticks.szeibernaeticks.classes.SyntheticEyes;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SyntheticEyesHandler implements ISzeibernaetickEventHandler {

    {
        Log.log("Creating instance of " + this.getClass(), LogType.DEBUG, LogType.INSTANTIATION,
                LogType.SZEIBER_HANDLER);
    }

    @SubscribeEvent
    public void onLivingUpdateEvent(LivingUpdateEvent e) {
        IArmoury armoury = e.getEntity().getCapability(ArmouryProvider.ARMOURY_CAP, null);

        if(armoury != null) {
            SyntheticEyes eyes = (SyntheticEyes) armoury.getSzeibernaetick(SyntheticEyes.class);

            if(eyes != null) {
                PotionEffect effect = e.getEntityLiving().getActivePotionEffect(MobEffects.NIGHT_VISION);
                if(effect == null) {
                    if(eyes.grantVision(e.getEntity())) {
                        Log.log("[SynthEyesHandler] Eyes granted vision.", LogType.DEBUG, LogType.SZEIBER_HANDLER,
                                LogType.SPAMMY);
                        e.getEntityLiving()
                                .addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 60000, 0, false, false));
                    }
                }
                else if(effect.getDuration() <= 59800) {
                    if(eyes.grantVision(e.getEntity())) {
                        effect.combine(new PotionEffect(MobEffects.NIGHT_VISION, 60000, 0, false, false));
                    }
                    else {
                        e.getEntityLiving().removeActivePotionEffect(MobEffects.NIGHT_VISION);
                    }
                }
            }
        }
    }

}
