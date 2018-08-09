package de.grzb.szeibernaeticks.item;

import de.grzb.szeibernaeticks.Szeibernaeticks;
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
        this.setTranslationKey(name);
        this.setRegistryName(Szeibernaeticks.MOD_ID, name);
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
