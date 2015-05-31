package com.Orion.Armory.Common.Handlers;
/*
 *   GuiHandler
 *   Created by: Orion
 *   Created on: 18-1-2015
 */

import com.Orion.Armory.Client.GUI.GuiArmorsAnvilMinimal;
import com.Orion.Armory.Client.GUI.GuiArmorsAnvilStandard;
import com.Orion.Armory.Client.GUI.GuiFirePit;
import com.Orion.Armory.Client.GUI.GuiHeater;
import com.Orion.Armory.Common.Inventory.ContainerArmorsAnvilMinimal;
import com.Orion.Armory.Common.Inventory.ContainerArmorsAnvilStandard;
import com.Orion.Armory.Common.Inventory.ContainerFirepit;
import com.Orion.Armory.Common.Inventory.ContainerHeater;
import com.Orion.Armory.Common.TileEntity.TileEntityArmorsAnvil;
import com.Orion.Armory.Common.TileEntity.TileEntityFirePit;
import com.Orion.Armory.Common.TileEntity.TileEntityHeater;
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
        else if (pID == References.GuiIDs.HEATERID)
        {
            return new ContainerHeater(pPlayer.inventory, (TileEntityHeater) pWorld.getTileEntity(pX, pY, pZ));
        }
        else if (pID == References.GuiIDs.ANVILID)
        {
            TileEntityArmorsAnvil tAnvil = (TileEntityArmorsAnvil) pWorld.getTileEntity(pX, pY, pZ);
            if (tAnvil.getCurrentState() == TileEntityArmorsAnvil.AnvilState.Minimal)
            {
                return new ContainerArmorsAnvilMinimal(pPlayer.inventory, tAnvil);
            }
            else if (tAnvil.getCurrentState() == TileEntityArmorsAnvil.AnvilState.Standard) {
                return new ContainerArmorsAnvilStandard(pPlayer.inventory, tAnvil);
            }
        }

        return null;
    }

    @Override
    public Object getClientGuiElement(int pID, EntityPlayer pPlayer, World pWorld, int pX, int pY, int pZ) {
        if (pID == References.GuiIDs.FIREPITID)
        {
            return new GuiFirePit(new ContainerFirepit(pPlayer.inventory, (TileEntityFirePit) pWorld.getTileEntity(pX, pY, pZ)));
        }
        else if (pID == References.GuiIDs.HEATERID)
        {
            return new GuiHeater(new ContainerHeater(pPlayer.inventory, (TileEntityHeater) pWorld.getTileEntity(pX, pY, pZ)));
        }
        else if (pID == References.GuiIDs.ANVILID)
        {
            TileEntityArmorsAnvil tAnvil = (TileEntityArmorsAnvil) pWorld.getTileEntity(pX, pY, pZ);
            if (tAnvil.getCurrentState() == TileEntityArmorsAnvil.AnvilState.Minimal)
            {
                return new GuiArmorsAnvilMinimal(new ContainerArmorsAnvilMinimal(pPlayer.inventory, tAnvil));
            }
            else if (tAnvil.getCurrentState() == TileEntityArmorsAnvil.AnvilState.Standard) {
                return new GuiArmorsAnvilStandard(new ContainerArmorsAnvilStandard(pPlayer.inventory, tAnvil));
            }
        }

        return null;
    }
}
