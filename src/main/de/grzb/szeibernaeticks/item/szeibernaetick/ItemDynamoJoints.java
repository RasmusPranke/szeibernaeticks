package main.de.grzb.szeibernaeticks.item.szeibernaetick;

import main.de.grzb.szeibernaeticks.szeibernaeticks.capability.DynamoJoints;
import main.de.grzb.szeibernaeticks.szeibernaeticks.event.DynamoJointsHandler;
import net.minecraft.creativetab.CreativeTabs;

public class ItemDynamoJoints extends SzeibernaetickBase {

    public ItemDynamoJoints(String name) {
        super(name, DynamoJoints.class, DynamoJointsHandler.class);
        this.setCreativeTab(CreativeTabs.COMBAT);
    }

}
