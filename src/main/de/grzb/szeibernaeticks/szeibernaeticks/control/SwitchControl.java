package main.de.grzb.szeibernaeticks.szeibernaeticks.control;

import org.lwjgl.input.Keyboard;

import main.de.grzb.szeibernaeticks.Szeibernaeticks;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;

public class SwitchControl {
    public static KeyBinding SzeiberHUDKey = new KeyBinding("Switches to SzeiberSwitches.", Keyboard.KEY_B,
            Szeibernaeticks.MOD_ID);

    @SubscribeEvent
    void OnKeyPressed(KeyInputEvent e) {
        // TODO: Figure out how to do stuff to the current player
        FMLCommonHandler.instance().getSide();
    }
}
