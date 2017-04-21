package main.de.grzb.szeibernaeticks.szeibernaeticks;

import net.minecraft.item.Item;

public class WrongSzeibernaetickException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 8878601731410938459L;

    private final Item expected;
    private final Item received;

    public WrongSzeibernaetickException(Item expectedItem, Item recievedItem) {
        this.expected = expectedItem;
        this.received = recievedItem;
    }

    @Override
    public String getMessage() {
        return "Expected: " + this.expected.getUnlocalizedName() + ", but got: " + this.received.getUnlocalizedName();
    }

}
