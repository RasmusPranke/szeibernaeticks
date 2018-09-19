package de.grzb.szeibernaeticks.item;

import de.grzb.szeibernaeticks.control.Log;
import de.grzb.szeibernaeticks.control.LogType;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

/**
 * Registers all mod items during pre-init. Configure src/main/resources as
 * source folder in Eclipse to make resources work while debugging.
 *
 * @author yuri
 * @see de.grzb.szeibernaeticks.CommonProxy CommonProxy
 */
public final class ModItems {
    public static IForgeRegistry<Item> itemRegistry;

    public static ItemBase ingot_copper;

    /**
     * Initializes the mod items. Configure src/main/resources as source folder
     * in Eclipse to make resources work.
     * <p>
     * This is called from {@link de.grzb.szeibernaeticks.CommonProxy}
     */
    public static void init() {
        ModItems.itemRegistry = GameRegistry.findRegistry(Item.class);

        Log.log("Initiating items!", LogType.DEBUG, LogType.SETUP);
        ingot_copper = register(new ItemBase("ingot_copper").setCreativeTab(CreativeTabs.MATERIALS));
    }

    public static <T extends Item> T register(T item) {
        ModItems.itemRegistry.register(item);

        if(item instanceof ItemBase) {
            ((ItemBase) item).registerItemModel();
        }

        return item;
    }
}
