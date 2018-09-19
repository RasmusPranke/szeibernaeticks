package main.de.grzb.szeibernaeticks.szeibernaeticks.handler;

import main.de.grzb.szeibernaeticks.szeibernaeticks.armoury.ArmouryProvider;
import main.de.grzb.szeibernaeticks.szeibernaeticks.armoury.IArmoury;
import main.de.grzb.szeibernaeticks.szeibernaeticks.classes.GeneratorStomach;
import main.de.grzb.szeibernaeticks.szeibernaeticks.event.SzeibernaetickInstalledEvent;
import main.de.grzb.szeibernaeticks.szeibernaeticks.event.SzeibernaetickRemovedEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import squeek.applecore.api.food.FoodEvent;

public class GeneratorStomachHandler implements ISzeibernaetickEventHandler {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onFoodEaten(FoodEvent.GetPlayerFoodValues event) {

        IArmoury armoury = event.player.getCapability(ArmouryProvider.ARMOURY_CAP, null);
        if(armoury != null) {
            GeneratorStomach genStomach = (GeneratorStomach) armoury.getSzeibernaetick(GeneratorStomach.class);
            if(genStomach != null) {
                genStomach.convertFood(event);
            }
        }
    }

    @SubscribeEvent
    public void onStomachAttached(SzeibernaetickInstalledEvent event) {
        if(event.armoury.getEntity() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.armoury.getEntity();
            if(event.installedSzeibernaetick instanceof GeneratorStomach) {
                GeneratorStomach stomach = (GeneratorStomach) event.installedSzeibernaetick;
                stomach.setPlayer(player);
            }
        }
    }

    @SubscribeEvent
    public void onStomachRemoved(SzeibernaetickRemovedEvent event) {
        if(event.removedSzeibernaetick instanceof GeneratorStomach) {
            GeneratorStomach stomach = (GeneratorStomach) event.removedSzeibernaetick;
            stomach.setPlayer(null);
        }
    }
}
