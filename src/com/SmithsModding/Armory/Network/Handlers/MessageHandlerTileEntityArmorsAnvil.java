package com.SmithsModding.Armory.Network.Handlers;

import com.SmithsModding.Armory.API.Crafting.SmithingsAnvil.AnvilRecipeRegistry;
import com.SmithsModding.Armory.API.Crafting.SmithingsAnvil.Recipe.VanillaAnvilRecipe;
import com.SmithsModding.Armory.Common.TileEntity.Anvil.TileEntityArmorsAnvil;
import com.SmithsModding.Armory.Network.Messages.MessageTileEntityArmorsAnvil;
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
        if (tEntity instanceof TileEntityArmorsAnvil) {
            ((TileEntityArmorsAnvil) tEntity).iCraftingStacks = message.iCraftingStacks;
            ((TileEntityArmorsAnvil) tEntity).iOutPutStacks = message.iOutPutStacks;
            ((TileEntityArmorsAnvil) tEntity).iHammerStacks = message.iHammerStacks;
            ((TileEntityArmorsAnvil) tEntity).iTongStacks = message.iTongStacks;
            ((TileEntityArmorsAnvil) tEntity).iAdditionalCraftingStacks = message.iAdditionalCraftingStacks;
            ((TileEntityArmorsAnvil) tEntity).iCoolStacks = message.iCoolStacks;
            ((TileEntityArmorsAnvil) tEntity).iConnectedPlayerCount = message.iConnectedPlayers;
            if (!message.iCurrentValidRecipeID.equals("VanillaRepair")) {
                ((TileEntityArmorsAnvil) tEntity).iCurrentValidRecipe = AnvilRecipeRegistry.getInstance().getRecipe(message.iCurrentValidRecipeID);
            } else if (message.iCurrentValidRecipeID.equals("VanillaRepair")) {
                ((TileEntityArmorsAnvil) tEntity).iCurrentValidRecipe = new VanillaAnvilRecipe((TileEntityArmorsAnvil) tEntity);
            } else {
                ((TileEntityArmorsAnvil) tEntity).iCurrentValidRecipe = null;
            }

        }

        return null;
    }
}
