package com.SmithsModding.Armory.API.Structures;

import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;

/**
 * Created by Orion
 * Created on 07.07.2015
 * 10:07
 * <p/>
 * Copyrighted according to Project specific license
 */
public interface IStructureComponentRenderer {

    IStructureRenderLayer[] getRenderLayers();

    void setRenderDispatcher(TileEntityRendererDispatcher pDispatcher);

    void renderComponentAt(IStructureComponent pComponentToRender, float pScreenX, float pScreenY, float pScreenZ, float pPartialTickTime, IStructureRenderLayer pLayer);
}
