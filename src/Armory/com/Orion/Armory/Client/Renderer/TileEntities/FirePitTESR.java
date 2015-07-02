package com.Orion.Armory.Client.Renderer.TileEntities;
/*
 *   FirePitTESR
 *   Created by: Orion
 *   Created on: 10-10-2014
 */

import com.Orion.Armory.Client.Models.ModelFirePit;
import com.Orion.Armory.Common.TileEntity.Core.Multiblock.IStructureComponent;
import com.Orion.Armory.Common.TileEntity.FirePit.TileEntityFirePit;
import com.Orion.Armory.Util.Core.Coordinate;
import com.Orion.Armory.Util.References;
import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.HashMap;

public class FirePitTESR extends TileEntitySpecialRenderer
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

        TileEntityFirePit tTEFirePit = (TileEntityFirePit) pEntity;
        if (tTEFirePit.isSlaved())
            return;

        renderTEModelAt(tTEFirePit, pX, pY, pZ, pPartialTickTime, false);

        if (tTEFirePit.getSlaveEntities() == null)
            return;

        ArrayList<Coordinate> tNonExistingCoordinates = new ArrayList<Coordinate>();
        for(Coordinate tCoordinate : tTEFirePit.getSlaveEntities().keySet())
        {
            TileEntity tEntity = tTEFirePit.getWorldObj().getTileEntity(tCoordinate.getXComponent(), tCoordinate.getYComponent(), tCoordinate.getZComponent());
            if (tEntity == null) {
                tNonExistingCoordinates.add(tCoordinate);
                continue;
            }

            if (!(tEntity instanceof IStructureComponent)) {
                tNonExistingCoordinates.add(tCoordinate);
                continue;
            }

            if (!((IStructureComponent) tEntity).getStructureType().equals(tTEFirePit.getStructureType())) {
                tNonExistingCoordinates.add(tCoordinate);
                continue;
            }
        }

        for (Coordinate tCoordinate : tNonExistingCoordinates)
        {
            tTEFirePit.removeSlave(tCoordinate);
        }

        for(Coordinate tCoordinate : tTEFirePit.getSlaveEntities().keySet())
        {
            renderTEModelAt(((TileEntityFirePit) tTEFirePit.getSlaveEntities().get(tCoordinate)), pX + (tCoordinate.getXComponent() - tTEFirePit.xCoord), pY + (tCoordinate.getYComponent() - tTEFirePit.yCoord),  pZ + (tCoordinate.getZComponent() - tTEFirePit.zCoord), pPartialTickTime, false);
        }

        renderTEModelAt(tTEFirePit, pX, pY, pZ, pPartialTickTime, true);

        for(Coordinate tCoordinate : tTEFirePit.getSlaveEntities().keySet())
        {
            renderTEModelAt(((TileEntityFirePit) tTEFirePit.getSlaveEntities().get(tCoordinate)), pX + (tCoordinate.getXComponent() - tTEFirePit.xCoord), pY + (tCoordinate.getYComponent() - tTEFirePit.yCoord),  pZ + (tCoordinate.getZComponent() - tTEFirePit.zCoord), pPartialTickTime, true);
        }
    }

    private void renderTEModelAt(TileEntityFirePit pEntity, double pX, double pY, double pZ, float pPartialTickTime, boolean pSeeThroughMode)
    {
        boolean tIsBurning = pEntity.isBurning();

        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);

        scaleTranslateRotateTEModel(pX, pY, pZ, pEntity.getDirection());

        if (!pSeeThroughMode)
        {
            if(tIsBurning)
            {
                bindTexture(iCoalSurfaceTexture);
                iModelFirePit.renderPart("Fuel_Plane");

                renderNonSeeThroughFirePit(pEntity);
            }
            else
            {
                renderNonSeeThroughFirePit(pEntity);
            }
        }
        else
        {
            renderSeeThroughFirePit(pEntity);
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
