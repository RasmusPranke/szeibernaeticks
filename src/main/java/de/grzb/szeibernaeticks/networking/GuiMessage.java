package de.grzb.szeibernaeticks.networking;

import io.netty.buffer.ByteBuf;
import de.grzb.szeibernaeticks.control.Log;
import de.grzb.szeibernaeticks.control.LogType;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class GuiMessage extends NBTMessage {
    private String text;

    public GuiMessage() {}

    public GuiMessage(String text) {
        this.text = text;
    }

    @Override
    public void fromBytes(ByteBuf buffer) {
        this.text = this.readString(buffer);
    }

    @Override
    public void toBytes(ByteBuf buffer) {
        this.writeString(buffer, this.text);
    }

    public static class GuiMessageHandler implements IMessageHandler<GuiMessage, IMessage> {

        @Override
        public IMessage onMessage(GuiMessage message, MessageContext context) {
            // check if we are on the server side, then if we have an EntityPlayerMP
            if(context.side.isServer() && context.getServerHandler().player != null) {
                final String text = message.text;
                // tell the server to execute our task in the main thread
                context.getServerHandler().player.getServerWorld().addScheduledTask(new Runnable() {
                    @Override
                    public void run() {
                        Log.log(text, LogType.INFO);
                    }
                });
            }
            return null;
        }

    }

}
