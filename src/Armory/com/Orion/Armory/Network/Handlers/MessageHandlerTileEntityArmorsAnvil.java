package com.Orion.Armory.Network.Handlers;

import com.Orion.Armory.Common.TileEntity.Anvil.TileEntityArmorsAnvil;
import com.Orion.Armory.Common.TileEntity.FirePit.TileEntityFirePit;
import com.Orion.Armory.Network.Messages.MessageTileEntityArmorsAnvil;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by Orion
 * Created on 03.05.2015
 * 16:34
 * <p/>
 * Copyrighted according to Project specific license
 */
public class MessageHandlerTileEntityArmorsAnvil extends MessageHandlerTileEntityArmory implements IMessageHandler<MessageTileEntityArmorsAnvil, IMessage> {

    @Override
    public IMessage onMessage(MessageTileEntityArmorsAnvil message, MessageContext ctx) {
        super.onMessage(message, ctx);

        TileEntity tEntity = FMLClientHandler.instance().getClient().theWorld.getTileEntity(message.xCoord, message.yCoord, message.zCoord);
        if (tEntity instanceof TileEntityFirePit)
        {
            ((TileEntityArmorsAnvil) tEntity).iCraftingStacks = message.iCraftingStacks;
            ((TileEntityArmorsAnvil) tEntity).iOutPutStacks = message.iOutPutStacks;
            ((TileEntityArmorsAnvil) tEntity).iHammerStacks = message.iHammerStacks;
            ((TileEntityArmorsAnvil) tEntity).iTongStacks = message.iTongStacks;
            ((TileEntityArmorsAnvil) tEntity).iAdditionalCraftingStacks = message.iAdditionalCraftingStacks;
            ((TileEntityArmorsAnvil) tEntity).iCoolStacks = message.iCoolStacks;
        }

        return null;
    }
}
