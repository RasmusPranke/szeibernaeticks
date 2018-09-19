package main.de.grzb.szeibernaeticks.szeibernaeticks.control;

import main.de.grzb.szeibernaeticks.szeibernaeticks.ISzeibernaetick;
import main.de.grzb.szeibernaeticks.szeibernaeticks.SzeibernaetickIdentifier;
import net.minecraft.util.ResourceLocation;

public abstract class Switch {
    protected SzeibernaetickIdentifier sourceSzeiber;
    protected String name;

    public Switch(ISzeibernaetick sourceSzeiber, String name) {
        this.name = name;
        this.sourceSzeiber = sourceSzeiber.getIdentifier();
    }

    public abstract void press();

    public String getIdentifier() {
        return sourceSzeiber.getFullIdentifier() + ":" + name;
    }

    public abstract boolean isActive();

    public abstract int GetState();

    public abstract void SetState(int state);

    public ResourceLocation GetSprite() {
        return GetSprite(GetState());
    }

    public abstract ResourceLocation GetSprite(int state);

    public abstract String toNiceString();

    public static abstract class BooleanSwitch extends Switch {
        private boolean value;

        public BooleanSwitch(ISzeibernaetick sourceSzeiber, String name) {// ,
                                                                          // BufferedImage
                                                                          // falseImage,
            // BufferedImage trueImage) {
            super(sourceSzeiber, name);
        }

        @Override
        public ResourceLocation GetSprite(int state) {
            // TODO: Proper sprites for OnOff switches
            return null;
        }

        public final boolean getValue() {
            return value;
        }

        public final void setValue(boolean val) {
            value = val;
        }

        @Override
        public final void press() {
            setValue(!getValue());
        }

        @Override
        public final int GetState() {
            return getValue() ? 1 : 0;
        }

        @Override
        public final void SetState(int state) {
            setValue(state % 2 > 0 ? true : false);
        }

        @Override
        public String toNiceString() {
            // TODO: Utilize localization
            return name + ": " + getValue();
        }
    }
}
