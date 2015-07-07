package com.Orion.Armory.Client.Renderer.TileEntities;
/*
 *   FirePitTESR
 *   Created by: Orion
 *   Created on: 10-10-2014
 */

import com.Orion.Armory.API.Structures.IStructureComponent;
import com.Orion.Armory.API.Structures.IStructureComponentRenderer;
import com.Orion.Armory.API.Structures.IStructureRenderLayer;
import com.Orion.Armory.Client.Models.ModelFirePit;
import com.Orion.Armory.Common.Registry.GeneralRegistry;
import com.Orion.Armory.Common.TileEntity.FirePit.FirePitRenderLayer;
import com.Orion.Armory.Common.TileEntity.FirePit.TileEntityFirePit;
import com.Orion.Armory.Util.Client.Color.Color;
import com.Orion.Armory.Util.Client.Colors;
import com.Orion.Armory.Util.Core.Coordinate;
import com.Orion.Armory.Util.References;
import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.HashMap;

public class FirePitTESR extends TileEntitySpecialRenderer implements IStructureComponentRenderer
{
    protected final ResourceLocation iDarkTexture = new ResourceLocation(References.General.MOD_ID + ":" + "textures/blocks/FirePit/DarkSheet.png");
    protected final ResourceLocation iLightTexture = new ResourceLocation(References.General.MOD_ID + ":" + "textures/blocks/FirePit/LightSheet.png");
    protected final ResourceLocation iDestroyedMetalBasinTexture = new ResourceLocation(References.General.MOD_ID + ":" + "textures/blocks/FirePit/DestroyedMetalBasin.png");
    protected final ResourceLocation iDestroyedMetalWallTexture = new ResourceLocation(References.General.MOD_ID + ":" + "textures/blocks/FirePit/DestroyedMetalWall.png");
    protected final ResourceLocation iFuelBasinBottomTexture = new ResourceLocation(References.General.MOD_ID + ":" + "textures/blocks/FirePit/FuelBasinBottom.png");
    protected final ResourceLocation iFuelBasinWallTexture = new ResourceLocation(References.General.MOD_ID + ":" + "textures/blocks/FirePit/FuelBasinWall.png");
    protected final ResourceLocation iIngotHolderRimTexture = new ResourceLocation(References.General.MOD_ID + ":" + "textures/blocks/FirePit/IngotHolderRim.png");

    protected final ResourceLocation iCoalSurfaceTexture = new ResourceLocation(References.General.MOD_ID + ":" + "textures/blocks/FirePit/CoalField.png");

    protected static final HashMap<String, ForgeDirection> iDirectionMapping = new HashMap<String, ForgeDirection>();

    public final ModelFirePit iModelFirePit = new ModelFirePit();
    public final RenderItem iItemRenderer;

    static{
        iDirectionMapping.put("NegX", ForgeDirection.WEST);
        iDirectionMapping.put("PosX", ForgeDirection.EAST);
        iDirectionMapping.put("NegY", ForgeDirection.SOUTH);
        iDirectionMapping.put("PosY", ForgeDirection.NORTH);
    }

    public FirePitTESR()
    {
        iItemRenderer = new RenderItem()
        {
            @Override
            public boolean shouldBob()
            {
                return false;
            }
        };

        iItemRenderer.setRenderManager(RenderManager.instance);
    }

    @Override
    public void renderTileEntityAt(TileEntity pEntity, double pX, double pY, double pZ, float pPartialTickTime) {
        if (!(pEntity instanceof TileEntityFirePit))
        {
            return;
        }

        renderTEModelAt((TileEntityFirePit) pEntity, pX, pY, pZ, pPartialTickTime);
    }

    private void renderTEModelAt(TileEntityFirePit pEntity, double pX, double pY, double pZ, float pPartialTickTime)
    {
        if (pEntity.isSlaved())
            return;

        for(IStructureRenderLayer tLayer : getRenderLayers())
        {
            if (GeneralRegistry.iIsInDevEnvironment)
                Colors.General.RED.performGLColor();

            renderComponentAt(pEntity, (float) pX, (float) pY, (float) pZ, pPartialTickTime, tLayer);
            Color.resetGLColor();

            if (pEntity.getSlaveEntities() == null)
                continue;

            for(Coordinate tCoordinate : pEntity.getSlaveEntities().keySet())
            {
                IStructureComponent tComponent = pEntity.getSlaveEntities().get(tCoordinate);
                IStructureComponentRenderer tRenderer = tComponent.getRenderer(tComponent);

                tRenderer.setRenderDispatcher(this.field_147501_a);

                tRenderer.renderComponentAt(tComponent, (float) pX - (pEntity.xCoord - tComponent.getLocation().getXComponent()), (float) pY - (pEntity.yCoord - tComponent.getLocation().getYComponent()), (float) pZ - (pEntity.zCoord - tComponent.getLocation().getZComponent()), pPartialTickTime, tLayer);
            }
        }
    }

    @Override
    public IStructureRenderLayer[] getRenderLayers() {
        return FirePitRenderLayer.values();
    }

    @Override
    public void setRenderDispatcher(TileEntityRendererDispatcher pDispatcher) {
        this.func_147497_a(pDispatcher);
    }

    @Override
    public void renderComponentAt(IStructureComponent pComponentToRender, float pScreenX, float pScreenY, float pScreenZ, float pPartialTickTime, IStructureRenderLayer pLayer) {
        boolean tIsBurning = ((TileEntityFirePit) pComponentToRender).isBurning();

        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);

        scaleTranslateRotateTEModel(pScreenX, pScreenY, pScreenZ, ((TileEntityFirePit) pComponentToRender).getDirection());

        if (pLayer == FirePitRenderLayer.SOLID)
        {
            if(tIsBurning)
            {
                bindTexture(iCoalSurfaceTexture);
                iModelFirePit.renderPart("Fuel_Plane");

                renderNonSeeThroughFirePit(((TileEntityFirePit) pComponentToRender));
            }
            else
            {
                renderNonSeeThroughFirePit(((TileEntityFirePit) pComponentToRender));
            }
        }
        else
        {
            renderSeeThroughFirePit(((TileEntityFirePit) pComponentToRender));
        }

        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }

    private void scaleTranslateRotateTEModel(double x, double y, double z, ForgeDirection orientation)
    {
        GL11.glTranslated(x + 0.5, y, z + 0.5);
    }

    private void renderNonSeeThroughFirePit(TileEntityFirePit pEntity)
    {
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(iFuelBasinBottomTexture);
        iModelFirePit.renderPart("FuelHolderBottom_Cube.001");

        FMLClientHandler.instance().getClient().renderEngine.bindTexture(iDarkTexture);
        renderMultiBlockComponent(pEntity, "FuelHolderCornerNegXNegY_Cube.008", true);
        renderMultiBlockComponent(pEntity, "FuelHolderCornerNegXPosY_Cube.010", true);
        renderMultiBlockComponent(pEntity, "FuelHolderCornerPosXNegY_Cube.007", true);
        renderMultiBlockComponent(pEntity, "FuelHolderCornerPosXPosY_Cube.009", true);

        FMLClientHandler.instance().getClient().renderEngine.bindTexture(iFuelBasinWallTexture);
        renderMultiBlockComponent(pEntity, "FuelHolderWallNegX_Cube.016", true);
        renderMultiBlockComponent(pEntity, "FuelHolderWallNegY_Cube.003", true);
        renderMultiBlockComponent(pEntity, "FuelHolderWallPosX_Cube.005", true);
        renderMultiBlockComponent(pEntity, "FuelHolderWallPosY_Cube.002", true);

        FMLClientHandler.instance().getClient().renderEngine.bindTexture(iDarkTexture);
        renderMultiBlockComponent(pEntity, "DestroyedMetalHolderCornerNegXNegY_Cube.011", true);
        renderMultiBlockComponent(pEntity, "DestroyedMetalHolderCornerPosXNegY_Cube.012", true);
        renderMultiBlockComponent(pEntity, "DestroyedMetalHolderCornerNegXPosY_Cube.006", true);
        renderMultiBlockComponent(pEntity, "DestroyedMetalHolderCornerPosXPosY_Cube.027", true);

        FMLClientHandler.instance().getClient().renderEngine.bindTexture(iDestroyedMetalBasinTexture);
        iModelFirePit.renderPart("DestroyedMetalBasin_Cube.019");

        FMLClientHandler.instance().getClient().renderEngine.bindTexture(iLightTexture);
        renderMultiBlockComponent(pEntity, "DestroyedMetalBasinConnectorNegX_Cube", false);
        renderMultiBlockComponent(pEntity, "DestroyedMetalBasinConnectorPosX_Cube.024", false);
        renderMultiBlockComponent(pEntity, "DestroyedMetalBasinConnectorNegY_Cube.022", false);
        renderMultiBlockComponent(pEntity, "DestroyedMetalBasinConnectorPosY_Cube.023", false);

        FMLClientHandler.instance().getClient().renderEngine.bindTexture(iLightTexture);
        renderMultiBlockComponent(pEntity, "DestroyedMetalBasinWallNegX_Cube.021", true);
        renderMultiBlockComponent(pEntity, "DestroyedMetalBasinWallPosX_Cube.017", true);
        renderMultiBlockComponent(pEntity, "DestroyedMetalBasinWallNegY_Cube.020", true);
        renderMultiBlockComponent(pEntity, "DestroyedMetalBasinWallPosY_Cube.018", true);

        FMLClientHandler.instance().getClient().renderEngine.bindTexture(iDarkTexture);
        renderMultiBlockComponent(pEntity, "IngotHolderCornerPosXNegY_Cube.028", true);
        renderMultiBlockComponent(pEntity, "IngotHolderCornerPosXPosY_Cube.029", true);
        renderMultiBlockComponent(pEntity, "IngotHolderCornerNegXNegY_Cube.030", true);
        renderMultiBlockComponent(pEntity, "IngotHolderCornerNegXPosY_Cube.000", true);

        FMLClientHandler.instance().getClient().renderEngine.bindTexture(iDarkTexture);
        iModelFirePit.renderPart("IngotHolderBase_Cube.026");

        FMLClientHandler.instance().getClient().renderEngine.bindTexture(iDarkTexture);
        renderMultiBlockComponent(pEntity, "IngotHolderRimPosXNegY_Cube.031", true);
        renderMultiBlockComponent(pEntity, "IngotHolderRimPosXPosY_Cube.032", true);
        renderMultiBlockComponent(pEntity, "IngotHolderRimNegXNegY_Cube.034", true);
        renderMultiBlockComponent(pEntity, "IngotHolderRimNegXPosY_Cube.033", true);

        FMLClientHandler.instance().getClient().renderEngine.bindTexture(iIngotHolderRimTexture);
        renderMultiBlockComponent(pEntity, "IngotHolderRimNegX_Cube.035", true);
        renderMultiBlockComponent(pEntity, "IngotHolderRimPosX_Cube.036", true);
        renderMultiBlockComponent(pEntity, "IngotHolderRimNegY_Cube.038", true);
        renderMultiBlockComponent(pEntity, "IngotHolderRimPosY_Cube.037", true);
    }

    private void renderSeeThroughFirePit(TileEntityFirePit pEntity)
    {
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(iDestroyedMetalWallTexture);
        renderMultiBlockComponent(pEntity, "DestroyedMetalHolderWallNegX_Cube.004", true);
        renderMultiBlockComponent(pEntity, "DestroyedMetalHolderWallPosX_Cube.014", true);
        renderMultiBlockComponent(pEntity, "DestroyedMetalHolderWallNegY_Cube.015", true);
        renderMultiBlockComponent(pEntity, "DestroyedMetalHolderWallPosY_Cube.013", true);
    }

    private void renderMultiBlockComponent(TileEntityFirePit pEntity, String pComponentName, boolean pDefaultRender)
    {
        ArrayList<Boolean> tSidedComponents = new ArrayList<Boolean>();

        for(String tSide: iDirectionMapping.keySet())
        {
            if (pComponentName.contains(tSide))
            {
                TileEntity tEntity = pEntity.getWorldObj().getTileEntity(pEntity.xCoord + iDirectionMapping.get(tSide).offsetX, pEntity.yCoord + iDirectionMapping.get(tSide).offsetY, pEntity.zCoord + iDirectionMapping.get(tSide).offsetZ);
                if (tEntity == null)
                {
                    tSidedComponents.add(false);
                }
                else if (tEntity instanceof TileEntityFirePit)
                {
                    tSidedComponents.add(true);
                }
                else
                {
                    tSidedComponents.add(false);
                }
            }
        }

        boolean tShouldBeRendered = true;

        for(Boolean tSideState : tSidedComponents)
        {
            if(!tSideState)
            {
                tShouldBeRendered = false;
                break;
            }
        }

        if (tShouldBeRendered && !pDefaultRender)
        {
            iModelFirePit.renderPart(pComponentName);
        }
        else if (!tShouldBeRendered && pDefaultRender)
        {
            iModelFirePit.renderPart(pComponentName);
        }
    }
}
