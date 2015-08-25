package com.Orion.Armory.Common.Handlers;
/*
 *   GuiHandler
 *   Created by: Orion
 *   Created on: 18-1-2015
 */

import com.Orion.Armory.Client.GUI.GuiHeater;
import com.Orion.Armory.Client.GUI.Implementation.ArmorsAnvil.GuiArmorsAnvilMinimal;
import com.Orion.Armory.Client.GUI.Implementation.ArmorsAnvil.GuiArmorsAnvilStandard;
import com.Orion.Armory.Client.GUI.Implementation.BookBinder.GuiBookBinder;
import com.Orion.Armory.Client.GUI.Implementation.FirePit.GuiFirePit;
import com.Orion.Armory.Common.Inventory.*;
import com.Orion.Armory.Common.TileEntity.Anvil.TileEntityArmorsAnvil;
import com.Orion.Armory.Common.TileEntity.Core.TileEntityArmory;
import com.Orion.Armory.Common.TileEntity.FirePit.TileEntityFirePit;
import com.Orion.Armory.Common.TileEntity.FirePit.TileEntityHeater;
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
        } else if (pID == References.GuiIDs.BOOKBINDERID) {
            return new ContainerBookBinder((TileEntityArmory) pWorld.getTileEntity(pX, pY, pZ), 0);
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
        } else if (pID == References.GuiIDs.BOOKBINDERID) {
            return new GuiBookBinder(new ContainerBookBinder((TileEntityArmory) pWorld.getTileEntity(pX, pY, pZ), 0));
        }

        return null;
    }
}
