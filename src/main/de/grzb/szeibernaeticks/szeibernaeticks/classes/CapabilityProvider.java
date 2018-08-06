package main.de.grzb.szeibernaeticks.szeibernaeticks.classes;

import main.de.grzb.szeibernaeticks.szeibernaeticks.ISzeibernaetick;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class CapabilityProvider implements ICapabilityProvider {

    @CapabilityInject(ISzeibernaetick.class)
    public static final Capability<ISzeibernaetick> SZEIBER_CAP = null;

    private ISzeibernaetick instance;

    public CapabilityProvider(ISzeibernaetick iSzeibernaetickCapability) {
        this.instance = iSzeibernaetickCapability;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability.equals(SZEIBER_CAP);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        // Test whether the Capability<T> is the existing and injected
        // Capability<ISzeibernaetick> of which there is only one
        if(capability.equals(SZEIBER_CAP)) {
            // If it is, return the instance
            return SZEIBER_CAP.cast(this.instance);
        }
        return null;
    }

    public String getIdentifier() {
        return this.instance.getIdentifier();
    }

}
