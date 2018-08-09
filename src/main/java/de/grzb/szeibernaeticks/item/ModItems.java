package de.grzb.szeibernaeticks.item;

import de.grzb.szeibernaeticks.control.Log;
import de.grzb.szeibernaeticks.control.LogType;
import de.grzb.szeibernaeticks.item.szeibernaetick.SzeibernaetickBase;
import de.grzb.szeibernaeticks.CommonProxy;
import de.grzb.szeibernaeticks.szeibernaeticks.classes.ArchersEyes;
import de.grzb.szeibernaeticks.szeibernaeticks.classes.ConductiveVeins;
import de.grzb.szeibernaeticks.szeibernaeticks.classes.DynamoJoints;
import de.grzb.szeibernaeticks.szeibernaeticks.classes.GeneratorStomach;
import de.grzb.szeibernaeticks.szeibernaeticks.classes.MetalBones;
import de.grzb.szeibernaeticks.szeibernaeticks.classes.RadarEyes;
import de.grzb.szeibernaeticks.szeibernaeticks.classes.RunnersLegs;
import de.grzb.szeibernaeticks.szeibernaeticks.classes.SyntheticEyes;
import de.grzb.szeibernaeticks.szeibernaeticks.event.ConductiveVeinsHandler;
import de.grzb.szeibernaeticks.szeibernaeticks.event.DynamoJointsHandler;
import de.grzb.szeibernaeticks.szeibernaeticks.event.GeneratorStomachHandler;
import de.grzb.szeibernaeticks.szeibernaeticks.event.MetalBonesHandler;
import de.grzb.szeibernaeticks.szeibernaeticks.event.RunnersLegsHandler;
import de.grzb.szeibernaeticks.szeibernaeticks.event.SyntheticEyesHandler;
import de.grzb.szeibernaeticks.szeibernaeticks.event.SzeibernaetickArchersEyesHandler;
import de.grzb.szeibernaeticks.szeibernaeticks.event.SzeibernaetickRadarEyesHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

/**
 * Registers all mod items during pre-init. Configure src/main/resources as
 * source folder in Eclipse to make resources work while debugging.
 *
 * @author yuri
 * @see CommonProxy CommonProxy
 */
public final class ModItems {
    public static IForgeRegistry<Item> itemRegistry;

    public static ItemBase ingot_copper;

    /**
     * Initializes the mod items. Configure src/main/resources as source folder
     * in Eclipse to make resources work.
     * <p>
     * This is called from {@link CommonProxy}
     */
    public static void init() {
        ModItems.itemRegistry = GameRegistry.findRegistry(Item.class);

        Log.log("Initiating items!", LogType.DEBUG, LogType.SETUP);
        ingot_copper = register(new ItemBase("ingot_copper").setCreativeTab(CreativeTabs.MATERIALS));
        register(new SzeibernaetickBase("metal_bones", MetalBones.class, MetalBonesHandler.class));
        register(new SzeibernaetickBase("conductive_veins", ConductiveVeins.class, ConductiveVeinsHandler.class));
        register(new SzeibernaetickBase("dynamo_joints", DynamoJoints.class, DynamoJointsHandler.class));
        register(new SzeibernaetickBase("synthetic_eyes", SyntheticEyes.class, SyntheticEyesHandler.class));
        register(new SzeibernaetickBase("generator_stomach", GeneratorStomach.class, GeneratorStomachHandler.class));
        register(new SzeibernaetickBase("archers_eyes", ArchersEyes.class, SzeibernaetickArchersEyesHandler.class));
        register(new SzeibernaetickBase("radar_eyes", RadarEyes.class, SzeibernaetickRadarEyesHandler.class));
        register(new SzeibernaetickBase("runners_legs", RunnersLegs.class, RunnersLegsHandler.class));
    }

    private static <T extends Item> T register(T item) {
        ModItems.itemRegistry.register(item);

        if(item instanceof ItemBase) {
            ((ItemBase) item).registerItemModel();
        }

        return item;
    }
}
