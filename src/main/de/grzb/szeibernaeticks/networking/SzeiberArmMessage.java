package main.de.grzb.szeibernaeticks.networking;

import io.netty.buffer.ByteBuf;
import main.de.grzb.szeibernaeticks.szeibernaeticks.capability.armoury.IArmoury;
import main.de.grzb.szeibernaeticks.szeibernaeticks.capability.armoury.ArmouryStorage;
import main.de.grzb.szeibernaeticks.szeibernaeticks.capability.armoury.ArmouryProvider;

public class SzeiberArmMessage extends NBTMessage {

    private IArmoury arm;

    public SzeiberArmMessage(IArmoury arm) {
        this.arm = arm;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        ArmouryStorage armStore = new ArmouryStorage();
        armStore.readNBT(ArmouryProvider.ARMOURY_CAP, this.arm, null, this.readTag(buf));
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ArmouryStorage armStore = new ArmouryStorage();
        this.writeTag(buf, armStore.writeNBT(ArmouryProvider.ARMOURY_CAP, this.arm, null));
    }

    /*
     * public static class SzeiberArmMessageHandler implements
     * IMessageHandler<SzeiberArmMessage, IMessage> {
     *
     * @Override public IMessage onMessage(SzeiberArmMessage message,
     * MessageContext ctx) { // Get the correct PlayerEntity final
     * EntityPlayerMP serverPlayer = ctx.getServerHandler().playerEntity;
     *
     * // Retrieve data from the message final IArmoury
     * arm = message.arm;
     *
     * // Find out which side this is on and set the listener to schedule // the
     * task on accordingly. IThreadListener listener; if (ctx.side.isClient()) {
     * listener = Minecraft.getMinecraft(); } else { return null; }
     *
     * // To access the Player Object and all that, schedule the task by //
     * creating an anonymous class with the logic in the run function
     * listener.addScheduledTask(new Runnable() {
     *
     * @Override public void run() { serverPlayer.get } try { cap =
     * SzeibernaetickMapper.instance.getCapabilityFromIdentifier(identifier).
     * newInstance(); } catch (InstantiationException e) {
     * Szeibernaeticks.getLogger()
     * .error("Error on instantiating Capability while sending it via SzeiberCapMessage!"
     * ); e.printStackTrace(); return; } catch (IllegalAccessException e) {
     * Szeibernaeticks.getLogger()
     * .error("Error on accessing Capability class while sending it via SzeiberCapMessage!"
     * ); e.printStackTrace(); return; } catch (NullPointerException e) {
     * Szeibernaeticks.getLogger()
     * .error("Error on getting Capability class from identifier, probably!");
     * e.printStackTrace(); return; }
     *
     * cap.fromNBT(comp);
     *
     * IArmoury armoury = serverPlayer
     * .getCapability(ArmouryProvider.ARMOURY_CAP, null);
     * armoury.addSzeibernaetick(cap); } });
     *
     * return null; }
     */

}
