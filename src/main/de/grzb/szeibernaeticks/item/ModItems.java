package main.de.grzb.szeibernaeticks.item;

import main.de.grzb.szeibernaeticks.control.Log;
import main.de.grzb.szeibernaeticks.control.LogType;
import main.de.grzb.szeibernaeticks.item.szeibernaetick.ItemConductiveVeins;
import main.de.grzb.szeibernaeticks.item.szeibernaetick.ItemDynamoJoints;
import main.de.grzb.szeibernaeticks.item.szeibernaetick.ItemMetalBones;
import main.de.grzb.szeibernaeticks.item.szeibernaetick.SzeibernaetickBase;
import main.de.grzb.szeibernaeticks.szeibernaeticks.capability.GeneratorStomach;
import main.de.grzb.szeibernaeticks.szeibernaeticks.capability.RunnersLegs;
import main.de.grzb.szeibernaeticks.szeibernaeticks.capability.SyntheticEyesCapability;
import main.de.grzb.szeibernaeticks.szeibernaeticks.capability.ArchersEyes;
import main.de.grzb.szeibernaeticks.szeibernaeticks.capability.RadarEyes;
import main.de.grzb.szeibernaeticks.szeibernaeticks.event.GeneratorStomachHandler;
import main.de.grzb.szeibernaeticks.szeibernaeticks.event.RunnersLegsHandler;
import main.de.grzb.szeibernaeticks.szeibernaeticks.event.SyntheticEyesHandler;
import main.de.grzb.szeibernaeticks.szeibernaeticks.event.SzeibernaetickArchersEyesHandler;
import main.de.grzb.szeibernaeticks.szeibernaeticks.event.SzeibernaetickRadarEyesHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
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
    public static IForgeRegistry<Item> itemRegistry;

    public static ItemBase ingot_copper;

    public static SzeibernaetickBase metal_bones;

    public static SzeibernaetickBase conductive_veins;

    public static SzeibernaetickBase runners_legs;

    public static SzeibernaetickBase dynamo_joints;

    public static SzeibernaetickBase synthetic_eyes;
    public static SzeibernaetickBase archers_eyes;
    public static SzeibernaetickBase radar_eyes;

    public static SzeibernaetickBase generator_stomach;

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
        metal_bones = register(new ItemMetalBones("metal_bones"));
        conductive_veins = register(new ItemConductiveVeins("conductive_veins"));
        dynamo_joints = register(new ItemDynamoJoints("dynamo_joints"));
        synthetic_eyes = register(new SzeibernaetickBase("synthetic_eyes", SyntheticEyesCapability.class, SyntheticEyesHandler.class));
        generator_stomach = register(new SzeibernaetickBase("generator_stomach", GeneratorStomach.class, GeneratorStomachHandler.class));
        archers_eyes = register(new SzeibernaetickBase("archers_eyes", ArchersEyes.class, SzeibernaetickArchersEyesHandler.class));
        radar_eyes = register(new SzeibernaetickBase("radar_eyes", RadarEyes.class, SzeibernaetickRadarEyesHandler.class));
        runners_legs = register(new SzeibernaetickBase("runners_legs", RunnersLegs.class, RunnersLegsHandler.class));
    }

    private static <T extends Item> T register(T item) {
        ModItems.itemRegistry.register(item);

        if(item instanceof ItemBase) {
            ((ItemBase) item).registerItemModel();
        }

        return item;
    }
}
