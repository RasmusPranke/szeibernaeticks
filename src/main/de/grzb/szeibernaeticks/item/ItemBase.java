package main.de.grzb.szeibernaeticks.item;

import main.de.grzb.szeibernaeticks.Szeibernaeticks;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

/**
 * A simple base class for creating items.
 *
 * @author yuri
 */
public class ItemBase extends Item {

    protected String name;

    public ItemBase(String name) {
        this.name = name;
        // deleted method
        // this.setUnlocalizedName(name);
        this.setRegistryName(Szeibernaeticks.RESOURCE_PREFIX + name);
    }

    public void registerItemModel() {
        Szeibernaeticks.proxy.registerItemRenderer(this, 0, this.name);
    }

    @Override
    public ItemBase setCreativeTab(CreativeTabs tab) {
        super.setCreativeTab(tab);
        return this;
    }
}
