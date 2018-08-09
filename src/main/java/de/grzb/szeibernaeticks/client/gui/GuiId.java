package de.grzb.szeibernaeticks.client.gui;

public enum GuiId {
    ASSEMBLER(0);

    private final int id;

    GuiId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }
}
