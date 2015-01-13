package com.Orion.Armory.Client.Renderer.TileEntities;
/*
 *   FirePitTESR
 *   Created by: Orion
 *   Created on: 10-10-2014
 */

import com.Orion.Armory.Client.Models.ModelFirePit;
import com.Orion.Armory.Common.TileEntity.TileEntityFirePit;
import com.Orion.Armory.Util.References;
import cpw.mods.fml.common.registry.GameData;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;

import java.util.Random;

public class FirePitTESR extends TileEntitySpecialRenderer
{
    protected final ResourceLocation iOffTexture = new ResourceLocation(References.General.MOD_ID + ":" + "textures/blocks/FirePitTextureOff.png");
    protected final ResourceLocation iBurningTexture = new ResourceLocation(References.General.MOD_ID + ":" + "texture/blocks/FirePitTextureOn.png");
    protected final ResourceLocation iCoalSurfaceTexture = new ResourceLocation(References.General.MOD_ID + ":" + "textures/blocks/CoalField.png");

    public final ModelFirePit iModel = new ModelFirePit();
    public final RenderItem iItemRenderer;

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
        renderTEModelAt(tTEFirePit, pX, pY, pZ, pPartialTickTime);


        /*TODO: On next release make the FirePIt pretty!
        renderFlintAndSteelAt(tTEFirePit, pX, pY, pZ, pPartialTickTime);
        renderCoalStack(tTEFirePit, pX, pY, pZ, pPartialTickTime);
        */
    }

    private void renderTEModelAt(TileEntityFirePit pEntity, double pX, double pY, double pZ, float pPartialTickTime)
    {
        boolean tIsBurning = pEntity.isBurning();

        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        scaleTranslateRotateTEModel(pX, pY, pZ, pEntity.getDirection());

        if(tIsBurning)
        {
            this.bindTexture(iOffTexture);
            iModel.renderPart("Furnace_Cube");

            this.bindTexture(iCoalSurfaceTexture);
            iModel.renderPart("CoalSurface_Plane");
        }
        else
        {
            this.bindTexture(iOffTexture);
            iModel.renderPart("Furnace_Cube");
        }
        GL11.glPopMatrix();
    }

    private void scaleTranslateRotateTEModel(double x, double y, double z, ForgeDirection orientation)
    {
        if (orientation == ForgeDirection.NORTH)
        {
            GL11.glTranslated(x+1, y, z+1);
            GL11.glRotatef(180F, 0F, 1F, 0F);
        }
        else if (orientation == ForgeDirection.EAST)
        {
            GL11.glTranslated(x, y, z + 1);
            GL11.glRotatef(90F, 0F, 1F, 0F);
        }
        else if (orientation == ForgeDirection.SOUTH)
        {
            GL11.glTranslated(x, y, z);
        }
        else if (orientation == ForgeDirection.WEST)
        {
            GL11.glTranslated(x +1, y, z);
            GL11.glRotatef(-90F, 0F, 1F, 0F);
        }
    }

    private void renderFlintAndSteelAt(TileEntityFirePit pEntity, double pX, double pY, double pZ, float pPartialTickTime)
    {
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_DEPTH_TEST);

        //TODO: Render the front tools.
        EntityItem gostItem = new EntityItem(pEntity.getWorldObj());
        gostItem.hoverStart = 0.0F;
        gostItem.setEntityItemStack(new ItemStack(Items.flint_and_steel));

        translateAndRotateFlintAndSteelPosition(gostItem.getEntityItem(), pX, pY, pZ, pEntity.getDirection());
        GL11.glEnable(GL11.GL_DEPTH_TEST);

        renderItem(gostItem);

        GL11.glPopMatrix();
    }

    private void translateAndRotateFlintAndSteelPosition(ItemStack pItemStack, double pX, double pY, double pZ, ForgeDirection pForgeDirection)
    {
        if (pItemStack.getItem() == Items.flint_and_steel)
        {
            if (pForgeDirection == ForgeDirection.NORTH)
            {
                GL11.glTranslatef((float) pX + 0.60F, (float) pY + 0.2F, (float) pZ + 0.15F);
                GL11.glRotatef(90F, 0F, 1F, 0F);
                GL11.glRotatef(45F, 1F, 0F, 0F);
                GL11.glRotatef(-45F, 0F, 1F, 0F);
                GL11.glScalef(0.2F, 0.2F, 0.3F);

                return;
            }
            else if(pForgeDirection == ForgeDirection.SOUTH)
            {
                GL11.glTranslatef((float) pX + 0.40F, (float) pY + 0.2F, (float) pZ + 0.85F);
                GL11.glRotatef(-90F, 0F, 1F, 0F);
                GL11.glRotatef(45F, 1F, 0F, 0F);
                GL11.glRotatef(-45F, 0F, 1F, 0F);
                GL11.glScalef(0.2F, 0.2F, 0.3F);

                return;
            }
            else if(pForgeDirection == ForgeDirection.EAST)
            {
                GL11.glTranslatef((float) pX + 0.85F, (float) pY + 0.2F, (float) pZ + 0.6F);
                GL11.glRotatef(90F, 0F, 1F, 0F);
                GL11.glRotatef(-90F, 0F, 1F, 0F);
                GL11.glRotatef(45F, 1F, 0F, 0F);
                GL11.glRotatef(-45F, 0F, 1F, 0F);
                GL11.glScalef(0.2F, 0.2F, 0.3F);
                return;
            }
            else if (pForgeDirection == ForgeDirection.WEST)
            {
                GL11.glTranslatef((float) pX + 0.15F, (float) pY + 0.2F, (float) pZ + 0.4F);
                GL11.glRotatef(-90F, 0F, 1F, 0F);
                GL11.glRotatef(-90F, 0F, 1F, 0F);
                GL11.glRotatef(45F, 1F, 0F, 0F);
                GL11.glRotatef(-45F, 0F, 1F, 0F);
                GL11.glScalef(0.2F, 0.2F, 0.3F);
                return;
            }
        }
    }

    private void renderCoalStack(TileEntityFirePit pEntity, double pX, double pY, double pZ, float pPartialTickTime)
    {
        Random random = new Random();
        random.setSeed(187L);

        int iRenderPasses = pEntity.getFuelReserveAmount();
        for (int k = 0; k < iRenderPasses; k ++)
        {
            GL11.glPushMatrix();
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            translateAndRotateCoalStackPosition(pEntity.getStackInSlot(3 + k), pX, pY, pZ, pEntity.getDirection());

            float f9 = 0.0625F;
            float f10 = 0.021875F;

            float x = (random.nextFloat() * 2.0F - 1.0F) * 0.3F / 0.5F;
            float y = (random.nextFloat() * 2.0F - 1.0F) * 0.3F / 0.5F;
            float z = (random.nextFloat() * 2.0F - 1.0F) * 0.3F / 0.5F;
            GL11.glTranslatef(x, y, f9 + f10);

            EntityItem tGhostItem = new EntityItem(pEntity.getWorldObj());
            tGhostItem.hoverStart = 0F;

            if (pEntity.getStackInSlot(3+k) != null) {
                tGhostItem.setEntityItemStack(pEntity.getStackInSlot(3+k));

                renderItem(tGhostItem);
            }

            GL11.glPopMatrix();
        }
    }

    private void translateAndRotateCoalStackPosition(ItemStack pItemStack, double pX, double pY, double pZ, ForgeDirection pForgeDirection)
    {
        if (pForgeDirection == ForgeDirection.NORTH)
            {
                GL11.glTranslatef((float) pX + 0.40F, (float) pY + 0.2F, (float) pZ + 0.15F);
                GL11.glRotatef(90F, 0F, 1F, 0F);
                GL11.glRotatef(45F, 1F, 0F, 0F);
                GL11.glRotatef(-45F, 0F, 1F, 0F);
                GL11.glScalef(0.2F, 0.2F, 0.3F);

                return;
            }
            else if(pForgeDirection == ForgeDirection.SOUTH)
            {
                GL11.glTranslatef((float) pX + 0.60F, (float) pY + 0.2F, (float) pZ + 0.85F);
                GL11.glRotatef(-90F, 0F, 1F, 0F);
                GL11.glRotatef(45F, 1F, 0F, 0F);
                GL11.glRotatef(-45F, 0F, 1F, 0F);
                GL11.glScalef(0.2F, 0.2F, 0.3F);

                return;
            }
            else if(pForgeDirection == ForgeDirection.EAST)
            {
                GL11.glTranslatef((float) pX + 0.85F, (float) pY + 0.2F, (float) pZ + 0.4F);
                GL11.glRotatef(90F, 0F, 1F, 0F);
                GL11.glRotatef(-90F, 0F, 1F, 0F);
                GL11.glRotatef(45F, 1F, 0F, 0F);
                GL11.glRotatef(-45F, 0F, 1F, 0F);
                GL11.glScalef(0.2F, 0.2F, 0.3F);
                return;
            }
            else if (pForgeDirection == ForgeDirection.WEST)
            {
                GL11.glTranslatef((float) pX + 0F, (float) pY + 0.1F, (float) pZ + 0.6F);
                GL11.glRotatef(-90F, 0F, 1F, 0F);
                GL11.glRotatef(45F, 0F, 1F, 0F);
                GL11.glRotatef(60F, 1F, 0F, 0F);
                GL11.glRotatef(45F, 0F, 1F, 0F);
                GL11.glScalef(0.2F, 0.2F, 0.3F);
        return;
        }
    }

    private void renderItem(EntityItem pGostEntity)
    {
        this.bindTexture(TextureMap.locationItemsTexture);


        Tessellator tTessallator = Tessellator.instance;
        IIcon icIcon = pGostEntity.getEntityItem().getIconIndex();
        double iMaxU = icIcon.getMaxU();
        double iMaxV = icIcon.getMaxV();
        double iMinU = icIcon.getMinU();
        double iMinV = icIcon.getMinV();
        double iIconHeight = icIcon.getIconHeight();
        double iIconWidth = icIcon.getIconWidth();
        double iThickness = 0.0625F;


        tTessallator.startDrawingQuads();
        tTessallator.setNormal(0.0F, 0.0F, 1.0F);
        tTessallator.addVertexWithUV(0.0D, 0.0D, 0.0D, (double)iMaxU, (double)iMaxV);
        tTessallator.addVertexWithUV(1.0D, 0.0D, 0.0D, (double)iMinU, (double)iMaxV);
        tTessallator.addVertexWithUV(1.0D, 1.0D, 0.0D, (double) iMinU, (double) iMinV);
        tTessallator.addVertexWithUV(0.0D, 1.0D, 0.0D, (double) iMaxU, (double) iMinV);
        tTessallator.draw();
        tTessallator.startDrawingQuads();
        tTessallator.setNormal(0.0F, 0.0F, -1.0F);
        tTessallator.addVertexWithUV(0.0D, 1.0D, (double)(0.0F - iThickness), (double)iMaxU, (double)iMinV);
        tTessallator.addVertexWithUV(1.0D, 1.0D, (double)(0.0F - iThickness), (double)iMinU, (double)iMinV);
        tTessallator.addVertexWithUV(1.0D, 0.0D, (double)(0.0F - iThickness), (double)iMinU, (double)iMaxV);
        tTessallator.addVertexWithUV(0.0D, 0.0D, (double)(0.0F - iThickness), (double)iMaxU, (double)iMaxV);
        tTessallator.draw();
        float f5 = (float) (0.5F * (iMaxU - iMinU) / (float)iIconHeight);
        float f6 = (float) (0.5F * (iMaxV - iMinV) / (float)iIconWidth);
        tTessallator.startDrawingQuads();
        tTessallator.setNormal(-1.0F, 0.0F, 0.0F);
        int k;
        float f7;
        float f8;

        for (k = 0; k < iIconHeight; ++k)
        {
            f7 = (float)k / (float)iIconHeight;
            f8 = (float) (iMaxU + (iMinU - iMaxU) * f7 - f5);
            tTessallator.addVertexWithUV((double)f7, 0.0D, (double)(0.0F - iThickness), (double)f8, (double)iMaxV);
            tTessallator.addVertexWithUV((double)f7, 0.0D, 0.0D, (double)f8, (double)iMaxV);
            tTessallator.addVertexWithUV((double)f7, 1.0D, 0.0D, (double)f8, (double)iMinV);
            tTessallator.addVertexWithUV((double)f7, 1.0D, (double)(0.0F - iThickness), (double)f8, (double)iMinV);
        }

        tTessallator.draw();
        tTessallator.startDrawingQuads();
        tTessallator.setNormal(1.0F, 0.0F, 0.0F);
        float f9;

        for (k = 0; k < iIconHeight; ++k)
        {
            f7 = (float)k / (float)iIconHeight;
            f8 = (float) (iMaxU + (iMinU - iMaxU) * f7 - f5);
            f9 = f7 + 1.0F / (float)iIconHeight;
            tTessallator.addVertexWithUV((double)f9, 1.0D, (double)(0.0F - iThickness), (double)f8, (double)iMinV);
            tTessallator.addVertexWithUV((double)f9, 1.0D, 0.0D, (double)f8, (double)iMinV);
            tTessallator.addVertexWithUV((double)f9, 0.0D, 0.0D, (double)f8, (double)iMaxV);
            tTessallator.addVertexWithUV((double)f9, 0.0D, (double)(0.0F - iThickness), (double)f8, (double)iMaxV);
        }

        tTessallator.draw();
        tTessallator.startDrawingQuads();
        tTessallator.setNormal(0.0F, 1.0F, 0.0F);

        for (k = 0; k < iIconWidth; ++k)
        {
            f7 = (float)k / (float)iIconWidth;
            f8 = (float) (iMaxV + (iMinV - iMaxV) * f7 - f6);
            f9 = f7 + 1.0F / (float)iIconWidth;
            tTessallator.addVertexWithUV(0.0D, (double)f9, 0.0D, (double)iMaxU, (double)f8);
            tTessallator.addVertexWithUV(1.0D, (double)f9, 0.0D, (double)iMinU, (double)f8);
            tTessallator.addVertexWithUV(1.0D, (double)f9, (double)(0.0F - iThickness), (double)iMinU, (double)f8);
            tTessallator.addVertexWithUV(0.0D, (double)f9, (double)(0.0F - iThickness), (double)iMaxU, (double)f8);
        }

        tTessallator.draw();
        tTessallator.startDrawingQuads();
        tTessallator.setNormal(0.0F, -1.0F, 0.0F);

        for (k = 0; k < iIconWidth; ++k)
        {
            f7 = (float)k / (float)iIconWidth;
            f8 = (float) (iMaxV + (iMinV - iMaxV) * f7 - f6);
            tTessallator.addVertexWithUV(1.0D, (double)f7, 0.0D, (double)iMinU, (double)f8);
            tTessallator.addVertexWithUV(0.0D, (double)f7, 0.0D, (double)iMaxU, (double)f8);
            tTessallator.addVertexWithUV(0.0D, (double)f7, (double)(0.0F - iThickness), (double)iMaxU, (double)f8);
            tTessallator.addVertexWithUV(1.0D, (double)f7, (double)(0.0F - iThickness), (double)iMinU, (double)f8);
        }

        tTessallator.draw();
    }

}
