package de.grzb.szeibernaeticks.tileentity;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nullable;

/**
 * Holds the tile entity's name which is used to register it in the
 * {@link net.minecraftforge.fml.common.registry.GameRegistry GameRegistry}.
 *
 * @author yuri
 * @see ModTileEntities
 */
public abstract class TileEntityBase extends TileEntity {
    private String name;
    private Block block;

    protected TileEntityBase(String name) {
        this(name, null);
    }

    protected TileEntityBase(String name, Block block) {
        super();
        this.name = name;
        this.block = block;
    }

    public String getName() {
        return this.name;
    }

    @Nullable
    public Block getBlock() {
        return this.block;
    }
}
