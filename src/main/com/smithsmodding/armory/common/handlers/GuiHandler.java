package com.smithsmodding.armory.common.handlers;
/*
 *   GuiHandler
 *   Created by: Orion
 *   Created on: 18-1-2015
 */


import com.smithsmodding.armory.api.util.references.References;
import com.smithsmodding.armory.client.gui.implementations.blacksmithsanvil.GuiBlacksmithsAnvil;
import com.smithsmodding.armory.client.gui.implementations.fireplace.GuiFireplace;
import com.smithsmodding.armory.client.gui.implementations.forge.GuiForge;
import com.smithsmodding.armory.client.gui.implementations.moltenmetalmixer.GuiMoltenMetalMixer;
import com.smithsmodding.armory.common.inventory.ContainerBlacksmithsAnvil;
import com.smithsmodding.armory.common.inventory.ContainerFireplace;
import com.smithsmodding.armory.common.inventory.ContainerForge;
import com.smithsmodding.armory.common.inventory.ContainerMoltenMetalMixer;
import com.smithsmodding.armory.common.tileentity.TileEntityBlackSmithsAnvil;
import com.smithsmodding.armory.common.tileentity.TileEntityFireplace;
import com.smithsmodding.armory.common.tileentity.TileEntityForge;
import com.smithsmodding.armory.common.tileentity.TileEntityMoltenMetalMixer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class GuiHandler implements IGuiHandler {

    @Nullable
    @Override
    public Object getServerGuiElement(int pID, @Nonnull EntityPlayer pPlayer, @Nonnull World pWorld, int pX, int pY, int pZ) {
        if (pID == References.GuiIDs.FORGEID) {
            return new ContainerForge(pPlayer, (TileEntityForge) pWorld.getTileEntity(new BlockPos(pX, pY, pZ)));
        }

        if (pID == References.GuiIDs.FIREPLACEID) {
            return new ContainerFireplace(pPlayer, (TileEntityFireplace) pWorld.getTileEntity(new BlockPos(pX, pY, pZ)));
        }

        if (pID == References.GuiIDs.ANVILID) {
            return new ContainerBlacksmithsAnvil(pPlayer, (TileEntityBlackSmithsAnvil) pWorld.getTileEntity(new BlockPos(pX, pY, pZ)));
        }

        if (pID == References.GuiIDs.MOLTENMETALMIXER) {
            return new ContainerMoltenMetalMixer(pPlayer, (TileEntityMoltenMetalMixer) pWorld.getTileEntity(new BlockPos(pX, pY, pZ)));
        }

        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int pID, @Nonnull EntityPlayer pPlayer, @Nonnull World pWorld, int pX, int pY, int pZ) {
        if (pID == References.GuiIDs.FORGEID) {
            return new GuiForge(new ContainerForge(pPlayer, (TileEntityForge) pWorld.getTileEntity(new BlockPos(pX, pY, pZ))));
        }

        if (pID == References.GuiIDs.FIREPLACEID) {
            return new GuiFireplace(new ContainerFireplace(pPlayer, (TileEntityFireplace) pWorld.getTileEntity(new BlockPos(pX, pY, pZ))));
        }

        if (pID == References.GuiIDs.ANVILID) {
            return new GuiBlacksmithsAnvil(new ContainerBlacksmithsAnvil(pPlayer, (TileEntityBlackSmithsAnvil) pWorld.getTileEntity(new BlockPos(pX, pY, pZ))));
        }

        if (pID == References.GuiIDs.MOLTENMETALMIXER) {
            return new GuiMoltenMetalMixer(new ContainerMoltenMetalMixer(pPlayer, (TileEntityMoltenMetalMixer) pWorld.getTileEntity(new BlockPos(pX, pY, pZ))));
        }

        return null;
    }
}
