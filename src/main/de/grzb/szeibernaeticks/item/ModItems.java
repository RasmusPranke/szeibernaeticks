package main.de.grzb.szeibernaeticks.item;

import main.de.grzb.szeibernaeticks.control.Log;
import main.de.grzb.szeibernaeticks.control.LogType;
import main.de.grzb.szeibernaeticks.item.szeibernaetick.SzeibernaetickBase;
import main.de.grzb.szeibernaeticks.szeibernaeticks.classes.ArchersEyes;
import main.de.grzb.szeibernaeticks.szeibernaeticks.classes.ConductiveVeins;
import main.de.grzb.szeibernaeticks.szeibernaeticks.classes.DynamoJoints;
import main.de.grzb.szeibernaeticks.szeibernaeticks.classes.GeneratorStomach;
import main.de.grzb.szeibernaeticks.szeibernaeticks.classes.MetalBones;
import main.de.grzb.szeibernaeticks.szeibernaeticks.classes.RadarEyes;
import main.de.grzb.szeibernaeticks.szeibernaeticks.classes.RunnersLegs;
import main.de.grzb.szeibernaeticks.szeibernaeticks.classes.SyntheticEyes;
import main.de.grzb.szeibernaeticks.szeibernaeticks.handler.ISzeibernaetickEventHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

/**
 * Registers all mod items during pre-init. Configure src/main/resources as
 * source folder in Eclipse to make resources work while debugging.
 *
 * @author yuri
 * @see main.de.grzb.szeibernaeticks.CommonProxy CommonProxy
 */
public final class ModItems {
    public interface RegisteringMethod {
        <T extends SzeibernaetickBase> T registerSzeibernaetick(T item,
                Class<? extends ISzeibernaetickEventHandler> handler);
    }

    public static IForgeRegistry<Item> itemRegistry;

    public static ItemBase ingot_copper;

    /**
     * Initializes the mod items. Configure src/main/resources as source folder
     * in Eclipse to make resources work.
     * <p>
     * This is called from {@link main.de.grzb.szeibernaeticks.CommonProxy}
     */
    public static void init() {
        ModItems.itemRegistry = GameRegistry.findRegistry(Item.class);

        Log.log("Initiating items!", LogType.DEBUG, LogType.SETUP);
        ingot_copper = register(new ItemBase("ingot_copper").setCreativeTab(CreativeTabs.MATERIALS));
        MetalBones.register(ModItems::registerSzeibernaetick);
        ConductiveVeins.register(ModItems::registerSzeibernaetick);
        DynamoJoints.register(ModItems::registerSzeibernaetick);
        SyntheticEyes.register(ModItems::registerSzeibernaetick);
        GeneratorStomach.register(ModItems::registerSzeibernaetick);
        ArchersEyes.register(ModItems::registerSzeibernaetick);
        RadarEyes.register(ModItems::registerSzeibernaetick);
        RunnersLegs.register(ModItems::registerSzeibernaetick);
    }

    private static <T extends Item> T register(T item) {
        ModItems.itemRegistry.register(item);

        if(item instanceof ItemBase) {
            ((ItemBase) item).registerItemModel();
        }

        return item;
    }

    private static <T extends SzeibernaetickBase> T registerSzeibernaetick(T item,
            Class<? extends ISzeibernaetickEventHandler> handler) {
        try {
            MinecraftForge.EVENT_BUS.register(handler.newInstance());
        }
        catch(InstantiationException e) {
            Log.log("Could not instantiate the Handler for this Szeibernaetick.", LogType.EXCEPTION);
            Log.logThrowable(e);
        }
        catch(IllegalAccessException e) {
            Log.log("Could not access the Handler for this Szeibernaetick.", LogType.EXCEPTION);
            Log.logThrowable(e);
        }
        return register(item);
    }
}
