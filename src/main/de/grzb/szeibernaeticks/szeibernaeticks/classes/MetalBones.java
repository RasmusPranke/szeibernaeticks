package main.de.grzb.szeibernaeticks.szeibernaeticks.classes;

import java.util.ArrayList;

import main.de.grzb.szeibernaeticks.Szeibernaeticks;
import main.de.grzb.szeibernaeticks.item.SzeibernaetickItem;
import main.de.grzb.szeibernaeticks.szeibernaeticks.BodyPart;
import main.de.grzb.szeibernaeticks.szeibernaeticks.ISzeibernaetick;
import main.de.grzb.szeibernaeticks.szeibernaeticks.Szeibernaetick;
import main.de.grzb.szeibernaeticks.szeibernaeticks.SzeibernaetickCapabilityProvider;
import main.de.grzb.szeibernaeticks.szeibernaeticks.SzeibernaetickIdentifier;
import main.de.grzb.szeibernaeticks.szeibernaeticks.control.Switch;
import main.de.grzb.szeibernaeticks.szeibernaeticks.handler.MetalBonesHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

/**
 * The Capability corresponding to {@link ItemMetalBones}.
 *
 * @author DemRat
 */
@Szeibernaetick(handler = { MetalBonesHandler.class }, item = MetalBones.Item.class)
public class MetalBones implements ISzeibernaetick {
    @Szeibernaetick.Identifier
    public static final SzeibernaetickIdentifier identifier = new SzeibernaetickIdentifier(Szeibernaeticks.MOD_ID,
            "MetalBones");
    @Szeibernaetick.ItemInject
    public static final Item item = null;
    private static final BodyPart bodyPart = BodyPart.BONES;
    private int damage;

    @Override
    public SzeibernaetickIdentifier getIdentifier() {
        return identifier;
    }

    public MetalBones() {
        this.damage = 0;
    }

    @Override
    public NBTTagCompound toNBT() {
        NBTTagCompound base = new NBTTagCompound();
        base.setInteger("damage", this.damage);
        return base;
    }

    @Override
    public void fromNBT(NBTTagCompound nbt) {
        this.damage = nbt.getInteger("damage");
    }

    @Override
    public BodyPart getBodyPart() {
        return bodyPart;
    }

    @Override
    public Iterable<Switch> getSwitches() {
        return new ArrayList<Switch>();
    }

    @Override
    public ItemStack generateItemStack() {
        ItemStack stack = new ItemStack(item);
        return stack;
    }

    public static class Item extends SzeibernaetickItem {
        public Item() {
            super(identifier);
        }

        @Override
        public BodyPart getBodyPart() {
            return bodyPart;
        }

        @Override
        public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
            MetalBones cap = new MetalBones();
            if(nbt != null) {
                cap.fromNBT(nbt);
            }
            return new SzeibernaetickCapabilityProvider(cap);
        }
    }
}
