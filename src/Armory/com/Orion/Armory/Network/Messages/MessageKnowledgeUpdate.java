/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.Orion.Armory.Network.Messages;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

public class MessageKnowledgeUpdate implements IMessage {

    private String iKnowledgeID;
    private String iNewValue;

    public MessageKnowledgeUpdate() {
    }

    public MessageKnowledgeUpdate(String pKnowledgeID, String pNewValue) {
        iKnowledgeID = pKnowledgeID;
        iNewValue = pNewValue;
    }


    @Override
    public void fromBytes(ByteBuf buf) {
        iKnowledgeID = ByteBufUtils.readUTF8String(buf);
        iNewValue = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, iKnowledgeID);
        ByteBufUtils.writeUTF8String(buf, iNewValue);
    }

    public String getKnowledgeSaveKey() {
        return iKnowledgeID;
    }

    public String getKnowledgeValue() {
        return iNewValue;
    }
}
