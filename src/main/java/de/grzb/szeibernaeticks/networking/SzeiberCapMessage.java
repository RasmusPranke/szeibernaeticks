package de.grzb.szeibernaeticks.networking;

import io.netty.buffer.ByteBuf;
import de.grzb.szeibernaeticks.control.Log;
import de.grzb.szeibernaeticks.control.LogType;
import de.grzb.szeibernaeticks.szeibernaeticks.ISzeibernaetick;
import de.grzb.szeibernaeticks.szeibernaeticks.SzeibernaetickMapper;
import de.grzb.szeibernaeticks.szeibernaeticks.armoury.ArmouryProvider;
import de.grzb.szeibernaeticks.szeibernaeticks.armoury.IArmoury;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SzeiberCapMessage extends NBTMessage {

    private NBTTagCompound capTag;
    private String identifier;

    public SzeiberCapMessage() {}

    public SzeiberCapMessage(ISzeibernaetick capability) {
        this.capTag = capability.toNBT();
        this.identifier = capability.getIdentifier();
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        // entityId = buf.readInt();

        this.identifier = this.readString(buf);
        this.capTag = this.readCompoundTag(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        // buf.writeInt(entityId);

        this.writeString(buf, this.identifier);
        this.writeCompoundTag(buf, this.capTag);
    }

    public static class SzeiberCapMessageHandler implements IMessageHandler<SzeiberCapMessage, IMessage> {

        @Override
        public IMessage onMessage(SzeiberCapMessage message, MessageContext context) {
            // Retrieve data from the message
            final String identifier = message.identifier;
            final NBTTagCompound comp = message.capTag;

            // Find out which side this is on and set the listener to schedule
            // the task on accordingly.
            IThreadListener listener;
            if(context.side.isClient()) {
                listener = Minecraft.getMinecraft();
            }
            else {
                return null;
            }

            // To access the Player Object and all that, schedule the task by
            // creating an anonymous class with the logic in the run function
            listener.addScheduledTask(new Runnable() {

                @Override
                public void run() {
                    ISzeibernaetick cap;
                    try {
                        cap = SzeibernaetickMapper.instance.getCapabilityFromIdentifier(identifier).newInstance();
                    }
                    catch(InstantiationException e) {
                        Log.log("Error on instantiating Capability while sending it via SzeiberCapMessage!", LogType.ERROR);
                        Log.logThrowable(e);
                        e.printStackTrace();
                        return;
                    }
                    catch(IllegalAccessException e) {
                        Log.log("Error on accessing Capability class while sending it via SzeiberCapMessage!", LogType.ERROR);
                        Log.logThrowable(e);
                        e.printStackTrace();
                        return;
                    }
                    catch(NullPointerException e) {
                        Log.log("Error on getting Capability class from identifier, probably!", LogType.ERROR);
                        Log.logThrowable(e);
                        e.printStackTrace();
                        return;
                    }

                    cap.fromNBT(comp);

                    Capability<IArmoury> prov = ArmouryProvider.ARMOURY_CAP;

                    IArmoury armoury = Minecraft.getMinecraft().player.getCapability(prov, null);
                    armoury.addSzeibernaetick(cap);
                }
            });

            return null;
        }

    }

}
