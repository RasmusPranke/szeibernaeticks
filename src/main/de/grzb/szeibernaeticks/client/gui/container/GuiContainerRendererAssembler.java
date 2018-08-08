package main.de.grzb.szeibernaeticks.client.gui.container;

import io.netty.util.internal.ConcurrentSet;
import main.de.grzb.szeibernaeticks.client.gui.Colours;
import main.de.grzb.szeibernaeticks.container.GuiContainerBase;
import main.de.grzb.szeibernaeticks.container.layout.GuiLayoutDefinition;
import main.de.grzb.szeibernaeticks.control.Log;
import main.de.grzb.szeibernaeticks.control.LogType;
import main.de.grzb.szeibernaeticks.networking.GuiMessage;
import main.de.grzb.szeibernaeticks.networking.NetworkWrapper;
import main.de.grzb.szeibernaeticks.szeibernaeticks.BodyPart;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiInventory;

import java.io.IOException;

public class GuiContainerRendererAssembler extends GuiContainerRendererBase {

    public GuiContainerRendererAssembler(GuiContainerBase container) {
        super(container);
    }

    @Override
    public void initGui() {
        super.initGui();
        int width = 100;
        int height = 20;
        int x = this.guiLeft + (this.xSize - width) / 2;
        int y = this.guiTop + GuiLayoutDefinition.BORDER_SIZE + GuiLayoutDefinition.NAME_FIELD_HEIGHT + 108 - height;
        this.buttonList.add(new GuiButton(0, x, y, width, height, "INSTALL"));
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        Log.log("Player pressed INSTALL", LogType.INFO);
        NetworkWrapper.INSTANCE.sendToServer(new GuiMessage("Player pressed INSTALL"));
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);

        int offsetRight = this.xSize / 2;
        int offsetTop = this.ySize / 2 - GuiLayoutDefinition.BORDER_SIZE;
        int posX = this.guiLeft + offsetRight;
        int posY = this.guiTop + offsetTop;
        mouseX = posX - mouseX;
        mouseY = (this.guiTop + offsetTop / 2) - mouseY;
        GuiInventory.drawEntityOnScreen(posX, posY, 30, mouseX, mouseY, this.mc.player);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        this.fontRenderer.drawString("Assembler", GuiLayoutDefinition.BORDER_SIZE, GuiLayoutDefinition.BORDER_SIZE, Colours.GREY);

        int y;
        int marginTop = GuiLayoutDefinition.BORDER_SIZE + GuiLayoutDefinition.NAME_FIELD_HEIGHT + GuiLayoutDefinition.ITEM_SLOT_SIZE / 2;
        int marginLeft = GuiLayoutDefinition.BORDER_SIZE + GuiLayoutDefinition.ITEM_SLOT_SIZE;
        int x1 = marginLeft + 30;
        int x2 = this.xSize / 2;
        int y2 = marginTop + 3 * GuiLayoutDefinition.ITEM_SLOT_SIZE;
        ConcurrentSet<BodyPart> bodySet = BodyPart.getBodySet();

        for(y = 0; y < 6; y++) {
            this.drawHorizontalLine(marginLeft, x1,  marginTop + y * GuiLayoutDefinition.ITEM_SLOT_SIZE, Colours.BLACK);
            this.drawLine(x1, marginTop + y * GuiLayoutDefinition.ITEM_SLOT_SIZE, x2, y2, Colours.BLACK);
        }
    }

}
