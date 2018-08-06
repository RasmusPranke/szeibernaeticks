package main.de.grzb.szeibernaeticks.tileentity;

import com.google.common.collect.ImmutableMap;
import io.netty.util.internal.ConcurrentSet;
import main.de.grzb.szeibernaeticks.Szeibernaeticks;
import main.de.grzb.szeibernaeticks.client.gui.GuiId;
import main.de.grzb.szeibernaeticks.container.GuiContainerAssembler;
import main.de.grzb.szeibernaeticks.container.GuiContainerBase;
import main.de.grzb.szeibernaeticks.container.layout.GuiLayoutDefinition;
import main.de.grzb.szeibernaeticks.container.layout.SlotBodyPartDefinition;
import main.de.grzb.szeibernaeticks.container.slot.SlotType;
import main.de.grzb.szeibernaeticks.szeibernaeticks.BodyPart;
import main.de.grzb.szeibernaeticks.szeibernaeticks.SzeibernaetickMapper;
import main.de.grzb.szeibernaeticks.szeibernaeticks.capability.ISzeibernaetick;
import main.de.grzb.szeibernaeticks.szeibernaeticks.capability.armoury.ArmouryProvider;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.animation.ITimeValue;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.model.animation.CapabilityAnimation;
import net.minecraftforge.common.model.animation.IAnimationStateMachine;
import net.minecraftforge.items.CapabilityItemHandler;

import java.util.Collection;

import static main.de.grzb.szeibernaeticks.Szeibernaeticks.MOD_ID;

public class TileEntityGuiContainerAssembler extends TileEntityGuiContainerBase {
    private final IAnimationStateMachine asm;
    private static final int SLOTS_PER_ROW = 6;

    public TileEntityGuiContainerAssembler(Block block) {
        this("assembler", block, BodyPart.getBodySet().size());
    }

    public TileEntityGuiContainerAssembler(String tileEntityName, Block block, int slotSize) {
        super(tileEntityName, block, slotSize, GuiId.ASSEMBLER);
        this.asm = Szeibernaeticks.proxy.loadASM(new ResourceLocation(MOD_ID, "asms/block/assembler.json"), ImmutableMap.<String, ITimeValue>of());
    }

    @Override
    public GuiContainerBase getContainer(EntityPlayer player) {
        Collection<ISzeibernaetick> szeibernaeticks = player.getCapability(ArmouryProvider.ARMOURY_CAP, null).getSzeibernaeticks();
        int width = 162;
        int height = 118;

        ConcurrentSet<BodyPart> bodyParts = BodyPart.getBodySet();
        SlotBodyPartDefinition[] slotBodyPartDefinitions = new SlotBodyPartDefinition[bodyParts.size()];
        int x, y;
        int i = 0;
        for(BodyPart bodyPart : bodyParts) {
            if(i < TileEntityGuiContainerAssembler.SLOTS_PER_ROW) {
                x = 0;
                y = i * GuiLayoutDefinition.ITEM_SLOT_SIZE;
            }
            else {
                x = width - GuiLayoutDefinition.ITEM_SLOT_SIZE;
                y = (i - TileEntityGuiContainerAssembler.SLOTS_PER_ROW) * GuiLayoutDefinition.ITEM_SLOT_SIZE;
            }

            ItemStack itemStack = ItemStack.EMPTY;
            for(ISzeibernaetick capability : szeibernaeticks) {
                if(bodyPart.equals(capability.getBodyPart())) {
                    itemStack = new ItemStack(SzeibernaetickMapper.instance.getItemFromIdentifier(capability.getIdentifier()));
                }
            }

            slotBodyPartDefinitions[i] = new SlotBodyPartDefinition(SlotType.BODYPART, x, y, bodyPart, itemStack);
            i++;
        }

        GuiLayoutDefinition layout = new GuiLayoutDefinition(this.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null), slotBodyPartDefinitions, player.inventory, width, height);
        return new GuiContainerAssembler(this, layout, this.guiId);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if(capability == CapabilityAnimation.ANIMATION_CAPABILITY) {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if(capability == CapabilityAnimation.ANIMATION_CAPABILITY) {
            return CapabilityAnimation.ANIMATION_CAPABILITY.cast(this.asm);
        }
        return super.getCapability(capability, facing);
    }
}
