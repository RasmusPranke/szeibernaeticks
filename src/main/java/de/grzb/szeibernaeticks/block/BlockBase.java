package de.grzb.szeibernaeticks.block;

import de.grzb.szeibernaeticks.Szeibernaeticks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;

/**
 * A simple base class for creating blocks.
 *
 * @author yuri
 */
public abstract class BlockBase extends Block {

    protected String name;

    public BlockBase(String name, Material material) {
        super(material);

        this.name = name;
        this.setTranslationKey(name);
        this.setRegistryName(Szeibernaeticks.MOD_ID, name);
    }

    /**
     * Loads the resources for this block's item on the client-side.
     *
     * @param itemBlock
     */
    public void registerItemModel(ItemBlock itemBlock) {
        Szeibernaeticks.proxy.registerItemRenderer(itemBlock, 0, this.name);
    }

    @Override
    public BlockBase setCreativeTab(CreativeTabs tab) {
        super.setCreativeTab(tab);
        return this;
    }
}
