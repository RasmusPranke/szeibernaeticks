package de.grzb.szeibernaeticks.client.gui;

import java.util.concurrent.ConcurrentHashMap;

import de.grzb.szeibernaeticks.client.gui.container.GuiContainerRendererAssembler;
import de.grzb.szeibernaeticks.client.gui.container.GuiContainerRendererBase;
import de.grzb.szeibernaeticks.container.GuiContainerBase;

public class GuiFactory {
    private ConcurrentHashMap<GuiId, GuiContainerRendererBase> guis;

    public GuiFactory(GuiContainerBase container) {
        this.guis = new ConcurrentHashMap<>();
        this.guis.put(GuiId.ASSEMBLER, new GuiContainerRendererAssembler(container));
    }

    public GuiContainerRendererBase getGui(GuiId guiId) {
        return this.guis.get(guiId);
    }

}
