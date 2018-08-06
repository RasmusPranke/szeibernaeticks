package main.de.grzb.szeibernaeticks.szeibernaeticks.event;

import main.de.grzb.szeibernaeticks.control.Log;
import main.de.grzb.szeibernaeticks.control.LogType;
import main.de.grzb.szeibernaeticks.szeibernaeticks.armoury.ArmouryProvider;
import main.de.grzb.szeibernaeticks.szeibernaeticks.armoury.IArmoury;
import main.de.grzb.szeibernaeticks.szeibernaeticks.capability.DynamoJoints;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class DynamoJointsHandler implements ISzeibernaetickEventHandler {

    @SubscribeEvent
    public void onEntityFall(LivingFallEvent e) {
        IArmoury armoury = e.getEntity().getCapability(ArmouryProvider.ARMOURY_CAP, null);
        if(armoury != null) {
            DynamoJoints dynamo = (DynamoJoints) armoury.getSzeibernaetick(DynamoJoints.class);
            if(dynamo != null) {
                Log.log("Entity with dynamo Fell!", LogType.DEBUG, LogType.SZEIBER_HANDLER, LogType.SPAMMY);

                float height = e.getDistance();
                dynamo.produce(height, e.getEntity());
                e.setDistance(height / 2);
            }
        }
    }

}
