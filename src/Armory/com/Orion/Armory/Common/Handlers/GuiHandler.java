package com.Orion.Armory.Common.Handlers;
/*
 *   GuiHandler
 *   Created by: Orion
 *   Created on: 18-1-2015
 */

import com.Orion.Armory.Client.GUI.GuiFirePit;
import com.Orion.Armory.Common.Inventory.ContainerFirepit;
import com.Orion.Armory.Common.TileEntity.TileEntityFirePit;
import com.Orion.Armory.Util.References;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler
{
    @Override
    public Object getServerGuiElement(int pID, EntityPlayer pPlayer, World pWorld, int pX, int pY, int pZ) {
        if (pID == References.GuiIDs.FIREPITID)
        {
            return new ContainerFirepit(pPlayer.inventory, (TileEntityFirePit) pWorld.getTileEntity(pX, pY, pZ));
        }

        return null;
    }

    @Override
    public Object getClientGuiElement(int pID, EntityPlayer pPlayer, World pWorld, int pX, int pY, int pZ) {
        if (pID == References.GuiIDs.FIREPITID)
        {
            return new GuiFirePit(new ContainerFirepit(pPlayer.inventory, (TileEntityFirePit) pWorld.getTileEntity(pX, pY, pZ)));
        }

        return null;
    }
}
