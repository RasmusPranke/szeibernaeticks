package main.de.grzb.szeibernaeticks.szeibernaeticks.event;

import main.de.grzb.szeibernaeticks.szeibernaeticks.armoury.ArmouryProvider;
import main.de.grzb.szeibernaeticks.szeibernaeticks.armoury.IArmoury;
import main.de.grzb.szeibernaeticks.szeibernaeticks.capability.GeneratorStomach;
import net.minecraft.item.ItemFood;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GeneratorStomachHandler implements ISzeibernaetickEventHandler {

    @SubscribeEvent
    public void onFoodEaten(LivingEntityUseItemEvent.Finish event) {
        if(event.getItem().getItem() instanceof ItemFood) {
            ItemFood food = (ItemFood) event.getItem().getItem();

            IArmoury armoury = event.getEntity().getCapability(ArmouryProvider.ARMOURY_CAP, null);
            if(armoury != null) {
                GeneratorStomach genStomach = (GeneratorStomach) armoury.getSzeibernaetick(GeneratorStomach.class);
                if(genStomach != null) {
                    int totalGeneration = (int) ((food.getHealAmount(event.getItem()) + food.getSaturationModifier(event.getItem())) / 2);
                    genStomach.produce(totalGeneration, event.getEntity());
                }
            }
        }
    }

}
