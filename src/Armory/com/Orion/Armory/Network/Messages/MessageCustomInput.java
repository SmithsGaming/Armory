package com.Orion.Armory.Network.Messages;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

/**
 * Created by Orion
 * Created on 02.06.2015
 * 10:52
 * <p/>
 * Copyrighted according to Project specific license
 */
public class MessageCustomInput implements IMessage {

    public int iXCoord, iYCoord, iZCoord;

    public String iInputID;
    public String iInput;

    public MessageCustomInput(String pInputID, String pInput, int pXCoord, int pYCoord, int pZCoord) {
        iInput = pInput;
        iInputID = pInputID;

        iXCoord = pXCoord;
        iYCoord = pYCoord;
        iZCoord = pZCoord;
    }

    public MessageCustomInput() {
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        iInputID = ByteBufUtils.readUTF8String(buf);

        iInput = ByteBufUtils.readUTF8String(buf);

        iXCoord = buf.readInt();
        iYCoord = buf.readInt();
        iZCoord = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, iInputID);

        ByteBufUtils.writeUTF8String(buf, iInput);

        buf.writeInt(iXCoord);
        buf.writeInt(iYCoord);
        buf.writeInt(iZCoord);
    }
}
