package de.grzb.szeibernaeticks.szeibernaeticks.overlay;

import de.grzb.szeibernaeticks.szeibernaeticks.armoury.ArmouryProvider;
import de.grzb.szeibernaeticks.szeibernaeticks.armoury.IArmoury;
import de.grzb.szeibernaeticks.szeibernaeticks.classes.ConductiveVeins;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnergyOverlay {

    private class ActualOverlay extends Gui {
        public ActualOverlay(Minecraft mc) {
            IArmoury armoury = mc.player.getCapability(ArmouryProvider.ARMOURY_CAP, null);

            if(armoury != null) {
                ConductiveVeins veins = (ConductiveVeins) armoury.getSzeibernaetick(ConductiveVeins.class);
                if(veins != null) {
                    int cEnergy = veins.getCurrentEnergy();
                    int mEnergy = veins.getMaxEnergy();

                    ScaledResolution scaled = new ScaledResolution(mc);
                    int width = scaled.getScaledWidth();
                    // int height = scaled.getScaledHeight();

                    drawCenteredString(mc.fontRenderer, cEnergy + "/" + mEnergy, width / 2, 0,
                            Integer.parseInt("FFAA00", 16));
                }
            }
        }

    }

    @SubscribeEvent
    public void onRenderGui(RenderGameOverlayEvent.Post event) {
        Minecraft mc = Minecraft.getMinecraft();

        // Draw when the experience bar is drawn.
        if(event.getType() == ElementType.EXPERIENCE) {
            new ActualOverlay(mc);
        }
    }
}
