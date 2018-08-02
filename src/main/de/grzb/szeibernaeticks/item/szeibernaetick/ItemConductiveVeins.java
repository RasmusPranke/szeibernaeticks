package main.de.grzb.szeibernaeticks.item.szeibernaetick;

import main.de.grzb.szeibernaeticks.szeibernaeticks.capability.ConductiveVeins;
import main.de.grzb.szeibernaeticks.szeibernaeticks.event.ConductiveVeinsHandler;
import net.minecraft.creativetab.CreativeTabs;

public class ItemConductiveVeins extends SzeibernaetickBase {

    public ItemConductiveVeins(String name) {
        super(name, ConductiveVeins.class, ConductiveVeinsHandler.class);
        this.setCreativeTab(CreativeTabs.COMBAT);
    }

}
