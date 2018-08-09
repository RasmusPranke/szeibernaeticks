package de.grzb.szeibernaeticks.networking;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagByteArray;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagLong;
import net.minecraft.nbt.NBTTagShort;
import net.minecraft.nbt.NBTTagString;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public abstract class NBTMessage implements IMessage {

    protected String readString(ByteBuf buf) {
        int idLength = buf.readInt();
        StringBuilder id = new StringBuilder();
        for(int i = 0; i < idLength; i++) {
            char c = buf.readChar();
            id.append(c);
        }
        return id.toString();
    }

    protected void writeString(ByteBuf buf, String s) {
        int k = s.length();
        buf.writeInt(k);
        for(int i = 0; i < k; i++) {
            buf.writeChar(s.charAt(i));
        }
    }

    protected NBTTagCompound readCompoundTag(ByteBuf buf) {
        String nextKey;
        NBTTagCompound tag = new NBTTagCompound();

        while(buf.isReadable()) {
            nextKey = this.readString(buf);
            tag.setTag(nextKey, this.readTag(buf));
        }
        return tag;
    }

    protected void writeCompoundTag(ByteBuf buf, NBTTagCompound comp) {
        for(String s : comp.getKeySet()) {
            this.writeString(buf, s);
            NBTBase tag = comp.getTag(s);
            this.writeTag(buf, tag);
        }
    }

    protected void writeTag(ByteBuf buf, NBTBase tag) {
        byte id = tag.getId();
        buf.writeByte(id);
        switch(id) {
            case 0:
                // End tags do literally nothing
                break;
            case 1:
                NBTTagByte byt = (NBTTagByte) tag;
                buf.writeByte(byt.getByte());
                break;
            case 2:
                NBTTagShort shor = (NBTTagShort) tag;
                buf.writeShort(shor.getShort());
                break;
            case 3:
                NBTTagInt in = (NBTTagInt) tag;
                buf.writeInt(in.getInt());
                break;
            case 4:
                NBTTagLong lon = (NBTTagLong) tag;
                buf.writeLong(lon.getLong());
                break;
            case 5:
                NBTTagFloat floa = (NBTTagFloat) tag;
                buf.writeFloat(floa.getFloat());
                break;
            case 6:
                NBTTagDouble doubl = (NBTTagDouble) tag;
                buf.writeDouble(doubl.getDouble());
                break;
            case 7:
                NBTTagByteArray byteArra = (NBTTagByteArray) tag;
                buf.writeInt(byteArra.getByteArray().length);
                buf.writeBytes(byteArra.getByteArray());
                break;
            case 8:
                NBTTagString strin = (NBTTagString) tag;
                this.writeString(buf, strin.getString());
                break;
            case 9:
                NBTTagList lis = (NBTTagList) tag;
                int max = lis.tagCount();
                for(int i = 0; i < max; i++) {
                    this.writeTag(buf, lis.get(i));
                }
                break;
            case 10:
                NBTTagCompound compound = (NBTTagCompound) tag;
                this.writeCompoundTag(buf, compound);
                break;
            case 11:
                NBTTagIntArray intArra = (NBTTagIntArray) tag;
                int length = intArra.getIntArray().length;
                buf.writeInt(length);
                for(int i = 0; i < length; i++) {
                    buf.writeInt(intArra.getIntArray()[i]);
                }
                break;
        }
    }

    protected NBTBase readTag(ByteBuf buf) {
        byte id = buf.readByte();
        NBTBase tag = null;
        switch(id) {
            case 0:
                // End tags do literally nothing
                break;
            case 1:
                tag = new NBTTagByte(buf.readByte());
                break;
            case 2:
                tag = new NBTTagShort(buf.readShort());
                break;
            case 3:
                tag = new NBTTagInt(buf.readInt());
                break;
            case 4:
                tag = new NBTTagLong(buf.readLong());
                break;
            case 5:
                tag = new NBTTagFloat(buf.readFloat());
                break;
            case 6:
                tag = new NBTTagDouble(buf.readDouble());
                break;
            case 7:
                int length = buf.readInt();
                tag = new NBTTagByteArray(buf.readBytes(length).array());
                break;
            case 8:
                tag = new NBTTagString(this.readString(buf));
                break;
            case 9:
                int max = buf.readInt();
                tag = new NBTTagList();
                for(int i = 0; i < max; i++) {
                    ((NBTTagList) tag).appendTag(this.readTag(buf));
                }
                break;
            case 10:
                tag = this.readCompoundTag(buf);
                break;
            case 11:
                int total = buf.readInt();
                int[] allInts = new int[total];
                for(int i = 0; i < total; i++) {
                    allInts[i] = buf.readInt();
                }
                tag = new NBTTagIntArray(allInts);
                break;
        }
        return tag;
    }
}
