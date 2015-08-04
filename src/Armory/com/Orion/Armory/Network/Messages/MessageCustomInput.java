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

    public String iInputID;
    public String iInput;

    public MessageCustomInput(String pInputID, String pInput) {
        iInput = pInput;
        iInputID = pInputID;
    }

    public MessageCustomInput() {
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        iInputID = ByteBufUtils.readUTF8String(buf);

        iInput = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, iInputID);

        ByteBufUtils.writeUTF8String(buf, iInput);
    }
}
