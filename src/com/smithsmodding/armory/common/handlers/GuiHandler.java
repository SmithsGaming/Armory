package com.smithsmodding.armory.common.handlers;
/*
 *   GuiHandler
 *   Created by: Orion
 *   Created on: 18-1-2015
 */


import com.smithsmodding.armory.api.References;
import com.smithsmodding.armory.client.gui.blacksmithsanvil.GuiBlacksmithsAnvil;
import com.smithsmodding.armory.client.gui.firepit.GuiFirePit;
import com.smithsmodding.armory.client.gui.fireplace.GuiFireplace;
import com.smithsmodding.armory.common.inventory.ContainerBlacksmithsAnvil;
import com.smithsmodding.armory.common.inventory.ContainerFirepit;
import com.smithsmodding.armory.common.inventory.ContainerFireplace;
import com.smithsmodding.armory.common.tileentity.TileEntityBlackSmithsAnvil;
import com.smithsmodding.armory.common.tileentity.TileEntityFirePit;
import com.smithsmodding.armory.common.tileentity.TileEntityFireplace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

    @Override
    public Object getServerGuiElement (int pID, EntityPlayer pPlayer, World pWorld, int pX, int pY, int pZ) {
        if (pID == References.GuiIDs.FIREPITID) {
            return new ContainerFirepit(pPlayer, (TileEntityFirePit) pWorld.getTileEntity(new BlockPos(pX, pY, pZ)));
        }

        if (pID == References.GuiIDs.FIREPLACEID) {
            return new ContainerFireplace(pPlayer, (TileEntityFireplace) pWorld.getTileEntity(new BlockPos(pX, pY, pZ)));
        }

        if (pID == References.GuiIDs.ANVILID) {
            return new ContainerBlacksmithsAnvil(pPlayer, (TileEntityBlackSmithsAnvil) pWorld.getTileEntity(new BlockPos(pX, pY, pZ)));
        }

        return null;
    }

    @Override
    public Object getClientGuiElement (int pID, EntityPlayer pPlayer, World pWorld, int pX, int pY, int pZ) {
        if (pID == References.GuiIDs.FIREPITID) {
            return new GuiFirePit(new ContainerFirepit(pPlayer, (TileEntityFirePit) pWorld.getTileEntity(new BlockPos(pX, pY, pZ))));
        }

        if (pID == References.GuiIDs.FIREPLACEID) {
            return new GuiFireplace(new ContainerFireplace(pPlayer, (TileEntityFireplace) pWorld.getTileEntity(new BlockPos(pX, pY, pZ))));
        }

        if (pID == References.GuiIDs.ANVILID) {
            return new GuiBlacksmithsAnvil(new ContainerBlacksmithsAnvil(pPlayer, (TileEntityBlackSmithsAnvil) pWorld.getTileEntity(new BlockPos(pX, pY, pZ))));
        }

        return null;
    }
}
