package de.grzb.szeibernaeticks.client.gui.container;

import de.grzb.szeibernaeticks.container.GuiContainerBase;
import de.grzb.szeibernaeticks.client.gui.Colours;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

/**
 * Base class to create a GUI for a container. Not relevant for the server.
 *
 * @author yuri
 */
public abstract class GuiContainerRendererBase extends GuiContainer {
    protected final ResourceLocation background;

    public GuiContainerRendererBase(GuiContainerBase container) {
        super(container);
        this.xSize = container.getLayoutDefinition().getWidth();
        this.ySize = container.getLayoutDefinition().getHeight();
        this.background = container.getGuiBackground();
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        this.mc.getTextureManager().bindTexture(this.background);
        this.drawTexturedModalRect((this.width - this.xSize) / 2, (this.height - this.ySize) / 2, 0, 0, this.xSize, this.ySize);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    protected void drawPoint(int x, int y, int colour) {
        this.drawHorizontalLine(x, x, y, colour);
    }

    protected void drawLine(int x1, int y1, int x2, int y2, int colour) {
        double m = (double) (y2 - y1) / (double) (x2 - x1);
        int y;
        int yPrev = y1;
        int sign;
        for(int x = 0; x < x2 - x1; x++) {
            y = (int) (m * x + y1);
            this.drawPoint(x + x1, y, Colours.BLACK);

            sign = (int) Math.signum(yPrev - y);
            if(sign * (yPrev - y) > 1) {
                for(int i = 0; i < sign * (yPrev - y) / 2; i++) {
                    this.drawPoint(x + x1, y + sign * i, Colours.BLACK);
                    this.drawPoint(x - 1 + x1, y + sign * i + (yPrev - y) / 2, Colours.BLACK);
                }
            }

            yPrev = y;
        }
    }
}
