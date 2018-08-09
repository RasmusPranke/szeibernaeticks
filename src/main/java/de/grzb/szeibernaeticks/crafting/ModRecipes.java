package de.grzb.szeibernaeticks.crafting;

import de.grzb.szeibernaeticks.CommonProxy;
import de.grzb.szeibernaeticks.block.ModBlocks;
import de.grzb.szeibernaeticks.item.ModItems;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Registers all recipes during init.
 *
 * @author yuri
 * @see CommonProxy CommonProxy
 */
public class ModRecipes {

    public static void init() {
        OreDictionary.registerOre("oreCopper", ModBlocks.ore_copper);
        OreDictionary.registerOre("ingotCopper", ModItems.ingot_copper);

        GameRegistry.addSmelting(ModBlocks.ore_copper, new ItemStack(ModItems.ingot_copper), 0.7F);
    }
}
