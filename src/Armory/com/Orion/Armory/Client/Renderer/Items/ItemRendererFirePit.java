package com.Orion.Armory.Client.Renderer.Items;
/*
 *   ItemRendererFirePit
 *   Created by: Orion
 *   Created on: 10-10-2014
 */

import com.Orion.Armory.Client.Models.ModelFirePit;
import com.Orion.Armory.Util.References;
import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class ItemRendererFirePit implements IItemRenderer
{
    protected final ResourceLocation iDarkTexture = new ResourceLocation(References.General.MOD_ID + ":" + "textures/blocks/FirePit/DarkSheet.png");
    protected final ResourceLocation iLightTexture = new ResourceLocation(References.General.MOD_ID + ":" + "textures/blocks/FirePit/LightSheet.png");
    protected final ResourceLocation iDestroyedMetalBasinTexture = new ResourceLocation(References.General.MOD_ID + ":" + "textures/blocks/FirePit/DestroyedMetalBasin.png");
    protected final ResourceLocation iDestroyedMetalWallTexture = new ResourceLocation(References.General.MOD_ID + ":" + "textures/blocks/FirePit/DestroyedMetalWall.png");
    protected final ResourceLocation iFuelBasinBottomTexture = new ResourceLocation(References.General.MOD_ID + ":" + "textures/blocks/FirePit/FuelBasinBottom.png");
    protected final ResourceLocation iFuelBasinWallTexture = new ResourceLocation(References.General.MOD_ID + ":" + "textures/blocks/FirePit/FuelBasinWall.png");
    protected final ResourceLocation iIngotHolderRimTexture = new ResourceLocation(References.General.MOD_ID + ":" + "textures/blocks/FirePit/IngotHolderRim.png");

    private final ModelFirePit iModelFirePit;

    public ItemRendererFirePit()
    {
        iModelFirePit = new ModelFirePit();
    }

    @Override
    public boolean handleRenderType(ItemStack itemStack, ItemRenderType itemRenderType)
    {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType itemRenderType, ItemStack itemStack, ItemRendererHelper itemRendererHelper)
    {
        return true;
    }

    @Override
    public void renderItem(ItemRenderType itemRenderType, ItemStack itemStack, Object... data)
    {
        switch (itemRenderType)
        {
            case ENTITY:
            {
                renderFirePit(0.0F, 0F, 0.5F);
                return;
            }
            case EQUIPPED:
            {
                renderFirePit(0.5F, 0.0F, 1.5F);
                return;
            }
            case EQUIPPED_FIRST_PERSON:
            {
                renderFirePit(0.5F, 0.0F, 1.5F);
                return;
            }
            case INVENTORY:
            {
                renderFirePit(-1.0F, -1.3F, 0F);
                return;
            }
            default:
        }
    }

    private void renderFirePit(float x, float y, float z)
    {
        GL11.glPushMatrix();

        // Scale, Translate, Rotate
        GL11.glScalef(1F, 1F, 1F);
        GL11.glTranslatef(x, y, z - 1F);

        GL11.glEnable(GL11.GL_BLEND);

        FMLClientHandler.instance().getClient().renderEngine.bindTexture(iFuelBasinBottomTexture);
        iModelFirePit.renderPart("FuelHolderBottom_Cube.001");

        FMLClientHandler.instance().getClient().renderEngine.bindTexture(iDarkTexture);
        iModelFirePit.renderPart("FuelHolderCornerNegXNegY_Cube.008");
        iModelFirePit.renderPart("FuelHolderCornerNegXPosY_Cube.010");
        iModelFirePit.renderPart("FuelHolderCornerPosXNegY_Cube.007");
        iModelFirePit.renderPart("FuelHolderCornerPosXPosY_Cube.009");

        FMLClientHandler.instance().getClient().renderEngine.bindTexture(iFuelBasinWallTexture);
        iModelFirePit.renderPart("FuelHolderWallNegX_Cube.016");
        iModelFirePit.renderPart("FuelHolderWallNegY_Cube.003");
        iModelFirePit.renderPart("FuelHolderWallPosX_Cube.005");
        iModelFirePit.renderPart("FuelHolderWallPosY_Cube.002");

        FMLClientHandler.instance().getClient().renderEngine.bindTexture(iDarkTexture);
        iModelFirePit.renderPart("DestroyedMetalHolderCornerNegXNegY_Cube.011");
        iModelFirePit.renderPart("DestroyedMetalHolderCornerPosXNegY_Cube.012");
        iModelFirePit.renderPart("DestroyedMetalHolderCornerNegXPosY_Cube.006");
        iModelFirePit.renderPart("DestroyedMetalHolderCornerPosXPosY_Cube.027");

        FMLClientHandler.instance().getClient().renderEngine.bindTexture(iDestroyedMetalBasinTexture);
        iModelFirePit.renderPart("DestroyedMetalBasin_Cube.019");

        FMLClientHandler.instance().getClient().renderEngine.bindTexture(iLightTexture);
        iModelFirePit.renderPart("DestroyedMetalBasinWallNegX_Cube.021");
        iModelFirePit.renderPart("DestroyedMetalBasinWallPosX_Cube.017");
        iModelFirePit.renderPart("DestroyedMetalBasinWallNegY_Cube.020");
        iModelFirePit.renderPart("DestroyedMetalBasinWallPosY_Cube.018");

        FMLClientHandler.instance().getClient().renderEngine.bindTexture(iDestroyedMetalWallTexture);
        iModelFirePit.renderPart("DestroyedMetalHolderWallNegX_Cube.004");
        iModelFirePit.renderPart("DestroyedMetalHolderWallPosX_Cube.014");
        iModelFirePit.renderPart("DestroyedMetalHolderWallNegY_Cube.015");
        iModelFirePit.renderPart("DestroyedMetalHolderWallPosY_Cube.013");

        FMLClientHandler.instance().getClient().renderEngine.bindTexture(iDarkTexture);
        iModelFirePit.renderPart("IngotHolderCornerPosXNegY_Cube.028");
        iModelFirePit.renderPart("IngotHolderCornerPosXPosY_Cube.029");
        iModelFirePit.renderPart("IngotHolderCornerNegXNegY_Cube.030");
        iModelFirePit.renderPart("IngotHolderCornerNegXPosY_Cube.000");

        FMLClientHandler.instance().getClient().renderEngine.bindTexture(iDarkTexture);
        iModelFirePit.renderPart("IngotHolderBase_Cube.026");

        FMLClientHandler.instance().getClient().renderEngine.bindTexture(iDarkTexture);
        iModelFirePit.renderPart("IngotHolderRimPosXNegY_Cube.031");
        iModelFirePit.renderPart("IngotHolderRimPosXPosY_Cube.032");
        iModelFirePit.renderPart("IngotHolderRimNegXNegY_Cube.034");
        iModelFirePit.renderPart("IngotHolderRimNegXPosY_Cube.033");

        FMLClientHandler.instance().getClient().renderEngine.bindTexture(iIngotHolderRimTexture);
        iModelFirePit.renderPart("IngotHolderRimNegX_Cube.035");
        iModelFirePit.renderPart("IngotHolderRimPosX_Cube.036");
        iModelFirePit.renderPart("IngotHolderRimNegY_Cube.038");
        iModelFirePit.renderPart("IngotHolderRimPosY_Cube.037");

        GL11.glDisable(GL11.GL_BLEND);

        GL11.glPopMatrix();
    }
}
