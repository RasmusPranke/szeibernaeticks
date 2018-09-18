package de.grzb.szeibernaeticks.szeibernaeticks;

import net.minecraft.item.Item;

public class WrongSzeibernaetickException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 8878601731410938459L;

    private final Item expected;
    private final Item received;

    public WrongSzeibernaetickException(Item expectedItem, Item receivedItem) {
        this.expected = expectedItem;
        this.received = receivedItem;
    }

    @Override
    public String getMessage() {
        return "Expected: " + this.expected.getRegistryName() + ", but got: " + this.received.getRegistryName();
    }

}
