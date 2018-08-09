package de.grzb.szeibernaeticks.block;

import de.grzb.szeibernaeticks.CommonProxy;
import de.grzb.szeibernaeticks.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

/**
 * Initializes the mod blocks and creates corresponding ItemBlocks. Configure
 * src/main/resources as source folder in Eclipse to make resources work while
 * debugging.
 *
 * @author yuri
 * @see CommonProxy CommonProxy
 */
public final class ModBlocks {
    public static IForgeRegistry<Block> blockRegistry;

    public static BlockBase ore_copper;
    public static BlockBase assembler;

    public static void init() {
        ModBlocks.blockRegistry = GameRegistry.findRegistry(Block.class);

        ore_copper = register(new BlockOreCopper());
        assembler = register(new BlockTileEntityGuiContainerAssembler());
    }

    /**
     * Registers a block and it's corresponding item and item model.
     *
     * @param block
     * @param itemBlock
     * @return {@link BlockBase}
     */
    private static <T extends Block> T register(T block, ItemBlock itemBlock) {
        ModBlocks.blockRegistry.register(block);
        ModItems.itemRegistry.register(itemBlock);

        if(block instanceof BlockBase) {
            ((BlockBase) block).registerItemModel(itemBlock);
        }

        return block;
    }

    /**
     * Creates the corresponding item to a given block and registers both.
     *
     * @param block
     * @return {@link BlockBase}
     */
    private static <T extends Block> T register(T block) {
        ItemBlock itemBlock = new ItemBlock(block);
        itemBlock.setRegistryName(block.getRegistryName());
        return register(block, itemBlock);
    }
}
