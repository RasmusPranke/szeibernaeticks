package de.grzb.szeibernaeticks.container.layout;

import de.grzb.szeibernaeticks.container.slot.SlotFactory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraftforge.items.IItemHandler;

public class GuiLayoutDefinition {
    public static final int BORDER_SIZE = 7;
    public static final int NAME_FIELD_HEIGHT = 10;
    public static final int ITEM_SLOT_SIZE = 18;
    public static final int HOTBAR_HEIGHT = ITEM_SLOT_SIZE;
    public static final int PLAYER_INVENTORY_ROWS = 3;
    public static final int PLAYER_INVENTORY_COLUMNS = 9;
    public static final int PLAYER_INVENTORY_HEIGHT = PLAYER_INVENTORY_ROWS * ITEM_SLOT_SIZE;
    public static final int PLAYER_INVENTORY_WIDTH = PLAYER_INVENTORY_COLUMNS * ITEM_SLOT_SIZE;
    public static final int HOTBAR_MARGIN_TOP = 4;
    public static final int PLAYER_HOTBAR_SLOTS = PLAYER_INVENTORY_COLUMNS;

    private SlotDefinition[] slotDefinitions;
    private IItemHandler itemHandler;
    private IInventory playerInventory;
    private int height;
    private int width;
    private int innerSectionHeight;
    private int innerSectionWidth;
    private int containerSize;

    public GuiLayoutDefinition(IItemHandler itemHandler, SlotDefinition[] slotDefinitions, IInventory playerInventory, int innerWidth, int innerHeight) {
        this.itemHandler = itemHandler;
        this.slotDefinitions = slotDefinitions;
        this.containerSize = slotDefinitions.length;
        this.playerInventory = playerInventory;

        this.innerSectionWidth = innerWidth;
        this.innerSectionHeight = innerHeight;

        this.width = BORDER_SIZE + ((this.innerSectionWidth > PLAYER_INVENTORY_WIDTH) ? this.innerSectionWidth : PLAYER_INVENTORY_WIDTH) + BORDER_SIZE;
        this.height = BORDER_SIZE + NAME_FIELD_HEIGHT + this.innerSectionHeight + PLAYER_INVENTORY_HEIGHT + HOTBAR_MARGIN_TOP + HOTBAR_HEIGHT + BORDER_SIZE;
    }

    public GuiLayoutDefinition(IItemHandler itemHandler, SlotDefinition[] slotDefinitions, IInventory playerInventory) {
        this.itemHandler = itemHandler;
        this.slotDefinitions = slotDefinitions;
        this.containerSize = slotDefinitions.length;
        this.playerInventory = playerInventory;

        int y;
        int x;
        int yMax = 0;
        int xMax = 0;
        for(SlotDefinition slotDefinition : slotDefinitions) {
            x = slotDefinition.x;
            y = slotDefinition.y;
            if(x > xMax) xMax = x;
            if(y > yMax) yMax = y;
        }
        this.innerSectionWidth = xMax + ITEM_SLOT_SIZE;
        this.innerSectionHeight = yMax + ITEM_SLOT_SIZE;

        this.width = BORDER_SIZE + ((this.innerSectionWidth > PLAYER_INVENTORY_WIDTH) ? this.innerSectionWidth : PLAYER_INVENTORY_WIDTH) + BORDER_SIZE;
        this.height = BORDER_SIZE + NAME_FIELD_HEIGHT + this.innerSectionHeight + PLAYER_INVENTORY_HEIGHT + HOTBAR_MARGIN_TOP + HOTBAR_HEIGHT + BORDER_SIZE;
    }

    public Slot[] getLayout() {
        int j = 0;
        int x;
        int y;

        Slot[] slots = new Slot[this.slotDefinitions.length + PLAYER_INVENTORY_ROWS * PLAYER_INVENTORY_COLUMNS + PLAYER_HOTBAR_SLOTS];

        /*
         * Hotbar slots
         */
        y = BORDER_SIZE + NAME_FIELD_HEIGHT + this.innerSectionHeight + PLAYER_INVENTORY_HEIGHT + HOTBAR_MARGIN_TOP + 1;
        for(int row = 0; row < PLAYER_HOTBAR_SLOTS; ++row) {
            x = BORDER_SIZE + row * ITEM_SLOT_SIZE + 1;
            slots[j] = new Slot(this.playerInventory, j, x, y);
            j++;
        }

        int yOffset = BORDER_SIZE + NAME_FIELD_HEIGHT + this.innerSectionHeight;
        /*
         * Main inventory slots
         */
        for(int row = 0; row < PLAYER_INVENTORY_ROWS; ++row) {
            for(int column = 0; column < PLAYER_INVENTORY_COLUMNS; ++column) {
                x = BORDER_SIZE + column * ITEM_SLOT_SIZE + 1;
                y = yOffset + row * ITEM_SLOT_SIZE + 1;
                slots[j] = new Slot(this.playerInventory, j, x, y);
                j++;
            }
        }

        yOffset = BORDER_SIZE + NAME_FIELD_HEIGHT;
        for(int i = 0; i < this.slotDefinitions.length; i++) {
            x = BORDER_SIZE + this.slotDefinitions[i].x + 1;
            y = yOffset + this.slotDefinitions[i].y + 1;
            slots[j] = SlotFactory.getNewSlotItemHandler(this.slotDefinitions[i], this.itemHandler, i, x, y);
            j++;
        }

        return slots;
    }

    public int getContainerSize() {
        return this.containerSize;
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

}
