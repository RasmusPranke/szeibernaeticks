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
        return sourceSzeiber + ":" + name;
    }

    public abstract boolean IsActive();

    public abstract int GetState();

    public abstract void SetState(int state);

    public ResourceLocation GetSprite() {
        return GetSprite(GetState());
    }

    public abstract ResourceLocation GetSprite(int state);

    public static abstract class BooleanSwitch extends Switch {
        public BooleanSwitch(ISzeibernaetick sourceSzeiber, String name) {// ,
                                                                          // BufferedImage
                                                                          // falseImage,
            // BufferedImage trueImage) {
            super(sourceSzeiber, name);
        }
        
        private boolean value = true;

        @Override
        public ResourceLocation GetSprite(int state) {
            // TODO: Proper sprites for OnOff switches
            return null;
        }

        protected abstract boolean getValue();

        protected abstract void setValue(boolean val);

        @Override
        public void press() {
            setValue(!getValue());
        }

        @Override
        public int GetState() {
            return getValue() ? 1 : 0;
        }

        @Override
        public void SetState(int state) {
            setValue(state % 2 > 0 ? true : false);
        }
    }
}
