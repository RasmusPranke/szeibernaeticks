package main.de.grzb.szeibernaeticks.tileentity;

import io.netty.util.internal.ConcurrentSet;
import main.de.grzb.szeibernaeticks.client.gui.GuiId;
import main.de.grzb.szeibernaeticks.container.GuiContainerAssembler;
import main.de.grzb.szeibernaeticks.container.GuiContainerBase;
import main.de.grzb.szeibernaeticks.container.layout.GuiLayoutDefinition;
import main.de.grzb.szeibernaeticks.container.layout.SlotBodyPartDefinition;
import main.de.grzb.szeibernaeticks.container.slot.SlotType;
import main.de.grzb.szeibernaeticks.szeibernaeticks.BodyPart;
import main.de.grzb.szeibernaeticks.szeibernaeticks.SzeibernaetickMapper;
import main.de.grzb.szeibernaeticks.szeibernaeticks.armoury.ArmouryProvider;
import main.de.grzb.szeibernaeticks.szeibernaeticks.capability.ISzeibernaetick;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;

import java.util.Collection;

public class TileEntityGuiContainerAssembler extends TileEntityGuiContainerBase {
    private static final int SLOTS_PER_ROW = 6;

    public TileEntityGuiContainerAssembler() {
        this(null);
    }

    public TileEntityGuiContainerAssembler(Block block) {
        this("assembler", block, BodyPart.getBodySet().size());
    }

    public TileEntityGuiContainerAssembler(String tileEntityName, Block block, int slotSize) {
        super(tileEntityName, block, slotSize, GuiId.ASSEMBLER);
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

}
