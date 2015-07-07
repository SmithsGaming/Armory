package com.Orion.Armory.API.Structures;

import com.Orion.Armory.Common.TileEntity.FirePit.FirePitRenderLayer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.TileEntity;

import java.util.ArrayList;

/**
 * Created by Orion
 * Created on 07.07.2015
 * 10:07
 * <p/>
 * Copyrighted according to Project specific license
 */

@SideOnly(Side.CLIENT)
public interface IStructureComponentRenderer
{

    IStructureRenderLayer[] getRenderLayers();

    void setRenderDispatcher(TileEntityRendererDispatcher pDispatcher);

    void renderComponentAt(IStructureComponent pComponentToRender, float pScreenX, float pScreenY, float pScreenZ, float pPartialTickTime, IStructureRenderLayer pLayer);
}
