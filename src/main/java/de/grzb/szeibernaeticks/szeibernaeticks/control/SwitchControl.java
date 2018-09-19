package de.grzb.szeibernaeticks.szeibernaeticks.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.BiConsumer;

import org.lwjgl.input.Keyboard;

import com.ibm.icu.text.UTF16.StringComparator;

import de.grzb.szeibernaeticks.Szeibernaeticks;
import de.grzb.szeibernaeticks.szeibernaeticks.ISzeibernaetick;
import de.grzb.szeibernaeticks.szeibernaeticks.armoury.ArmouryProvider;
import de.grzb.szeibernaeticks.szeibernaeticks.armoury.IArmoury;
import de.grzb.szeibernaeticks.utility.Wrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;

public class SwitchControl {
    private static final int maxRowCount = 9;
    public static KeyBinding SzeiberHUDKey = new KeyBinding("Switches to SzeiberSwitches.", Keyboard.KEY_B,
            Szeibernaeticks.MOD_ID);

    private ActualOverlay overlay;

    private class ActualOverlay extends GuiScreen {
        private SortedMap<ISzeibernaetick, ArrayList<Switch>> sortedSwitches;
        private int page = 0;

        public ActualOverlay(Minecraft mc) {
            IArmoury armoury = mc.player.getCapability(ArmouryProvider.ARMOURY_CAP, null);
            this.mc = mc;

            if(armoury != null) {
                sortedSwitches = getAndSortSwitches(armoury);
            }
        }

        @Override
        public void drawScreen(int mouseX, int mouseY, float partialTicks) {
            super.drawScreen(mouseX, mouseY, partialTicks);

            ScaledResolution scaled = new ScaledResolution(mc);
            int width = scaled.getScaledWidth();
            int row = 1;

            Iterator<Entry<ISzeibernaetick, ArrayList<Switch>>> szeiberIter = sortedSwitches.entrySet().iterator();
            while(row <= maxRowCount && szeiberIter.hasNext()) {
                Entry<ISzeibernaetick, ArrayList<Switch>> entry = szeiberIter.next();

                ISzeibernaetick s = entry.getKey();
                drawString(s.toNiceString(), width - 1, row);
                row++;

                Iterator<Switch> switchIter = entry.getValue().iterator();
                while(row <= maxRowCount && switchIter.hasNext()) {
                    Switch nextSwitch = switchIter.next();
                    drawString(nextSwitch.toNiceString(), width + 1, row);
                    row++;
                }
            }
        }

        private void drawString(String s, int x, int row) {
            drawCenteredString(mc.fontRenderer, s, x / 2, row * (mc.fontRenderer.FONT_HEIGHT + 1),
                    Integer.parseInt("FFAA00", 16));
        }

        @Override
        protected void keyTyped(char typedChar, int keyCode) throws IOException {
            super.keyTyped(typedChar, keyCode);
            for(int i = 0; i < 9; i++) {
                if(keyCode == mc.gameSettings.keyBindsHotbar[i].getKeyCode()) {
                    Wrapper<Integer> index = new Wrapper<Integer>(page * 10 + i);
                    sortedSwitches.forEach(new BiConsumer<ISzeibernaetick, ArrayList<Switch>>() {
                        boolean done = false;

                        @Override
                        public void accept(ISzeibernaetick t, ArrayList<Switch> u) {
                            if(index.val < u.size() && !done) {
                                u.get(index.val).press();
                                done = true;
                            }
                            else {
                                index.val -= u.size();
                            }
                        }
                    });
                }
            }
        }
    }

    @SubscribeEvent
    public void onKeyPressed(KeyInputEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        if(SzeiberHUDKey.isPressed()) {
            overlay = new ActualOverlay(mc);
            mc.displayGuiScreen(overlay);
        }
    }

    private static SortedMap<ISzeibernaetick, ArrayList<Switch>> getAndSortSwitches(IArmoury armoury) {

        Iterable<ISzeibernaetick> szeibers = armoury.getSzeibernaeticks();
        SortedMap<ISzeibernaetick, ArrayList<Switch>> map = new TreeMap<>(new Comparator<ISzeibernaetick>() {
            @Override
            public int compare(ISzeibernaetick o1, ISzeibernaetick o2) {
                return new StringComparator().compare(o1.toNiceString(), o2.toNiceString());
            }
        });
        for(ISzeibernaetick i : szeibers) {
            ArrayList<Switch> switches = new ArrayList<Switch>();
            for(Switch s : i.getSwitches()) {
                switches.add(s);
            }
            switches.sort(new Comparator<Switch>() {
                @Override
                public int compare(Switch s1, Switch s2) {
                    return new StringComparator().compare(s1.getIdentifier(), s2.getIdentifier());
                }

            });
            map.put(i, switches);
        }
        return map;
    }
}
