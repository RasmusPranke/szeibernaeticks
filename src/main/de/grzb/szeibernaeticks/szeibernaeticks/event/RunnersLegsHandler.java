package main.de.grzb.szeibernaeticks.szeibernaeticks.event;

import main.de.grzb.szeibernaeticks.szeibernaeticks.capability.RunnersLegsCapability;
import main.de.grzb.szeibernaeticks.szeibernaeticks.capability.armoury.ArmouryProvider;
import main.de.grzb.szeibernaeticks.szeibernaeticks.capability.armoury.IArmouryCapability;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RunnersLegsHandler implements ISzeibernaetickEventHandler {

    private static Potion szeiberEffect = MobEffects.HASTE;

    @SubscribeEvent
    public void onLivingUpdateEvent(LivingUpdateEvent e) {
        IArmouryCapability armoury = e.getEntity().getCapability(ArmouryProvider.ARMOURY_CAP, null);

        if(armoury != null) {
            RunnersLegsCapability legs = (RunnersLegsCapability) armoury.getSzeibernaetick(RunnersLegsCapability.class);

            if(legs != null) {
                PotionEffect effect = e.getEntityLiving().getActivePotionEffect(szeiberEffect);
                if(effect == null) {
                    if(legs.grantSpeed(e.getEntity())) {
                        e.getEntityLiving().addPotionEffect(new PotionEffect(szeiberEffect, 60000, 0, false, false));
                    }
                }
                else if(effect.getDuration() <= 59800) {
                    if(legs.grantSpeed(e.getEntity())) {
                        effect.combine(new PotionEffect(szeiberEffect, 60000, 0, false, false));
                    }
                    else {
                        e.getEntityLiving().removeActivePotionEffect(szeiberEffect);
                    }
                }
            }
        }
    }

}
