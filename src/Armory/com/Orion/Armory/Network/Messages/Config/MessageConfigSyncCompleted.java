package com.Orion.Armory.Network.Messages.Config;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

/**
 * Created by Orion
 * Created on 10.06.2015
 * 22:25
 * <p/>
 * Copyrighted according to Project specific license
 */
public class MessageConfigSyncCompleted implements IMessage
{
    public MessageConfigSyncCompleted() {}

    @Override
    public void fromBytes(ByteBuf buf) {
        //NOOP Just a MessageBoard Packet to tell the Client to recalculate the Anvil recipes.
    }

    @Override
    public void toBytes(ByteBuf buf) {
        //NOOP Just a MessageBoard Packet to tell the Client to recalculate the Anvil recipes.
    }
}
