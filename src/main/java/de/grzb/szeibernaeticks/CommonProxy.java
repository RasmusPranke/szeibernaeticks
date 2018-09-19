package de.grzb.szeibernaeticks;

import com.google.common.collect.ImmutableMap;
import de.grzb.szeibernaeticks.block.ModBlocks;
import de.grzb.szeibernaeticks.control.Log;
import de.grzb.szeibernaeticks.control.LogType;
import de.grzb.szeibernaeticks.crafting.ModRecipes;
import de.grzb.szeibernaeticks.item.ModItems;
import de.grzb.szeibernaeticks.networking.GuiMessage;
import de.grzb.szeibernaeticks.networking.NetworkWrapper;
import de.grzb.szeibernaeticks.networking.SzeiberCapMessage;
import de.grzb.szeibernaeticks.potion.ModPotions;
import de.grzb.szeibernaeticks.szeibernaeticks.ISzeibernaetick;
import de.grzb.szeibernaeticks.szeibernaeticks.InvalidSzeibernaetickException;
import de.grzb.szeibernaeticks.szeibernaeticks.SzeibernaetickCapabilityStorage;
import de.grzb.szeibernaeticks.szeibernaeticks.SzeibernaetickInit;
import de.grzb.szeibernaeticks.szeibernaeticks.armoury.Armoury;
import de.grzb.szeibernaeticks.szeibernaeticks.armoury.ArmouryAttacher;
import de.grzb.szeibernaeticks.szeibernaeticks.armoury.ArmouryStorage;
import de.grzb.szeibernaeticks.szeibernaeticks.armoury.IArmoury;
import de.grzb.szeibernaeticks.szeibernaeticks.classes.DummyDefault;
import de.grzb.szeibernaeticks.szeibernaeticks.control.SwitchControl;
import de.grzb.szeibernaeticks.szeibernaeticks.entity.EntityBlockMarker;
import de.grzb.szeibernaeticks.szeibernaeticks.overlay.EnergyOverlay;
import de.grzb.szeibernaeticks.tileentity.ModTileEntities;
import de.grzb.szeibernaeticks.world.ModWorldGenerator;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.animation.ITimeValue;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.LoaderExceptionModCrash;
import net.minecraftforge.common.model.animation.IAnimationStateMachine;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

import javax.annotation.Nullable;

/**
 * Executes code on both, server and client.
 *
 * @author yuri
 * @see ClientProxy
 */
public class CommonProxy {

    public void preInit(FMLPreInitializationEvent e) {
        Szeibernaeticks.setLogger(e.getModLog());
        Log.getLogger().setForgeLogger(e.getModLog());

        Log.log("PreInit!", LogType.SETUP, LogType.INFO);
        ModItems.init();
        ModBlocks.init();
        try {
            SzeibernaetickInit.init();
        }
        catch(InvalidSzeibernaetickException invalidSzeiberException) {
            throw new LoaderExceptionModCrash("Error while initializing Szeibernaeticks!", invalidSzeiberException);
        }
        ModTileEntities.init();
        ModPotions.init();

        NetworkWrapper.INSTANCE.registerMessage(SzeiberCapMessage.SzeiberCapMessageHandler.class, SzeiberCapMessage.class, NetworkWrapper.getId(), Side.CLIENT);
        NetworkWrapper.INSTANCE.registerMessage(GuiMessage.GuiMessageHandler.class, GuiMessage.class, NetworkWrapper.getId(), Side.SERVER);

        MinecraftForge.EVENT_BUS.register(new ArmouryAttacher());
        MinecraftForge.EVENT_BUS.register(new ModConfig());

        // TODO: Change this, maybe put somewhere else
        EntityRegistry.registerModEntity(new ResourceLocation(Szeibernaeticks.MOD_ID, "block_marker"), EntityBlockMarker.class, "block_marker", 0, Szeibernaeticks.instance, 20, 3, false);
    }

    public void init(FMLInitializationEvent e) {
        ModRecipes.init();
        GameRegistry.registerWorldGenerator(new ModWorldGenerator(), 0);
        NetworkRegistry.INSTANCE.registerGuiHandler(Szeibernaeticks.instance, new GuiProxy());

        CapabilityManager.INSTANCE.register(IArmoury.class, new ArmouryStorage(), Armoury::new);
        CapabilityManager.INSTANCE.register(ISzeibernaetick.class, new SzeibernaetickCapabilityStorage(), DummyDefault::new);
    }

    public void postInit(FMLPostInitializationEvent e) {
        MinecraftForge.EVENT_BUS.register(new EnergyOverlay());
        MinecraftForge.EVENT_BUS.register(new SwitchControl());
    }

    public void registerItemRenderer(Item item, int meta, String id) {
        // method stub, client-only method
    }

    @Nullable
    public IAnimationStateMachine loadASM(ResourceLocation location, ImmutableMap<String, ITimeValue> parameters) {
        // client-only method
        return null;
    }
}
