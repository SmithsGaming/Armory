/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.Orion.Armory.Network.Handlers;

import com.Orion.Armory.API.Knowledge.IKnowledgedGameElement;
import com.Orion.Armory.API.Knowledge.KnowledgeEntityProperty;
import com.Orion.Armory.API.Knowledge.KnowledgeRegistry;
import com.Orion.Armory.Armory;
import com.Orion.Armory.Network.Messages.MessageKnowledgeUpdate;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.entity.player.EntityPlayer;

public class MessageHandlerKnowledgeUpdate implements IMessageHandler<MessageKnowledgeUpdate, IMessage> {

    @Override
    public IMessage onMessage(MessageKnowledgeUpdate message, MessageContext ctx) {
        handleMessage(message, ctx);

        return null;
    }

    private void handleMessage(MessageKnowledgeUpdate message, MessageContext ctx) {
        EntityPlayer pPlayer = Armory.proxy.getPlayer(ctx);

        KnowledgeEntityProperty tKnowledgeProperty = (new KnowledgeEntityProperty()).get(pPlayer);
        IKnowledgedGameElement tKnowledge = tKnowledgeProperty.getKnowledge(message.getKnowledgeSaveKey());

        if (tKnowledge == null) {
            tKnowledge = KnowledgeRegistry.getInstance().getNewKnowledgedGameElement(message.getKnowledgeSaveKey());
        }
        tKnowledge.setExperienceLevel(Float.parseFloat(message.getKnowledgeValue()));

        tKnowledgeProperty.setKnowledge(tKnowledge);
    }
}
