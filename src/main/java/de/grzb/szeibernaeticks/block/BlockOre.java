package de.grzb.szeibernaeticks.block;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public abstract class BlockOre extends BlockBase {

    public BlockOre(String name, float hardness, float resistance) {
        super(name, Material.ROCK);
        this.setHardness(hardness);
        this.setResistance(resistance);
        this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
    }
}
