package main.de.grzb.szeibernaeticks.container;

import main.de.grzb.szeibernaeticks.client.gui.GuiId;
import main.de.grzb.szeibernaeticks.container.layout.GuiLayoutDefinition;
import main.de.grzb.szeibernaeticks.container.slot.SlotBodyPart;
import main.de.grzb.szeibernaeticks.szeibernaeticks.ISzeibernaetick;
import main.de.grzb.szeibernaeticks.szeibernaeticks.armoury.ArmouryProvider;
import main.de.grzb.szeibernaeticks.szeibernaeticks.armoury.IArmoury;
import main.de.grzb.szeibernaeticks.szeibernaeticks.capability.CapabilityProvider;
import main.de.grzb.szeibernaeticks.tileentity.TileEntityGuiContainerBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;

import java.util.Collection;

public class GuiContainerAssembler extends GuiContainerBase {

    public GuiContainerAssembler(TileEntityGuiContainerBase tileEntity, GuiLayoutDefinition layout, GuiId guiId) {
        super(tileEntity, layout, guiId);
    }

    @Override
    public void onContainerClosed(EntityPlayer player) {

        IArmoury playerCapability = player.getCapability(ArmouryProvider.ARMOURY_CAP, null);
        Collection<ISzeibernaetick> playerSzeibernaeticks = playerCapability.getSzeibernaeticks();

        for(ISzeibernaetick szeibernaetick : playerSzeibernaeticks) {
            playerCapability.removeSzeibernaetick(szeibernaetick);
        }

        for(Slot slot : this.inventorySlots) {
            if(slot instanceof SlotBodyPart && slot.getStack().hasCapability(CapabilityProvider.SZEIBER_CAP, null)) {
                ISzeibernaetick szeibernaetick = slot.getStack().getCapability(CapabilityProvider.SZEIBER_CAP, null);
                boolean installed = false;
                for(ISzeibernaetick playerSzeibernaetick : playerSzeibernaeticks) {
                    if(playerSzeibernaetick.equals(szeibernaetick)) {
                        // player didn't modify this slot; reattach the player's Szeibernaetick, NOT a new one
                        playerCapability.addSzeibernaetick(playerSzeibernaetick);
                        installed = true;
                        break;
                    }
                }
                // player doesn't have this Szeibernaetick yet, attach it
                if(!installed) playerCapability.addSzeibernaetick(szeibernaetick);
            }
        }
    }

}
