/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.Orion.Armory.Client.Renderer.TileEntities;
/*
 *   FirePitTESR
 *   Created by: Orion
 *   Created on: 10-10-2014
 */

import com.Orion.Armory.Client.Models.ModelBookBinder;
import com.Orion.Armory.Common.TileEntity.TileEntityBookBinder;
import com.Orion.Armory.Util.References;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;

public class BookBinderTESR extends TileEntitySpecialRenderer {
    public final ModelBookBinder iModelBookBinder = new ModelBookBinder();
    public final RenderItem iItemRenderer;
    protected final ResourceLocation iBookBinderTexture = new ResourceLocation(References.General.MOD_ID + ":" + "/textures/blocks/BookBinder/BookBinder.png");
    protected final ResourceLocation iInkPotTexture = new ResourceLocation(References.General.MOD_ID + ":" + "textures/blocks/BookBinder/InkPotEmpty.png");
    protected final ResourceLocation iFullInkPotTextures = new ResourceLocation(References.General.MOD_ID + ":" + "textures/blocks/BookBinder/InkPotFull.png");
    protected final ResourceLocation iPaperStack = new ResourceLocation(References.General.MOD_ID + ":" + "textures/blocks/BookBinder/Paper.png");

    public BookBinderTESR() {
        iItemRenderer = new RenderItem() {
            @Override
            public boolean shouldBob() {
                return false;
            }
        };

        iItemRenderer.setRenderManager(RenderManager.instance);
    }

    @Override
    public void renderTileEntityAt(TileEntity pEntity, double pX, double pY, double pZ, float pPartialTickTime) {
        if (!(pEntity instanceof TileEntityBookBinder)) {
            return;
        }

        renderTEModelAt((TileEntityBookBinder) pEntity, pX, pY, pZ, pPartialTickTime);
    }

    private void renderTEModelAt(TileEntityBookBinder pEntity, double pX, double pY, double pZ, float pPartialTickTime) {
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_DEPTH_TEST);

        scaleTranslateRotateTEModel(pX, pY, pZ, pEntity.getDirection());

        renderNonSeeThroughBinder(pEntity);
        renderSeeThroughInkHolder(pEntity);

        //GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glPopMatrix();
    }

    private void scaleTranslateRotateTEModel(double x, double y, double z, ForgeDirection orientation) {
        GL11.glTranslated(x + 0.5, y, z + 0.5);

        if (orientation == ForgeDirection.SOUTH) {
            // NOOP
        } else if (orientation == ForgeDirection.EAST) {
            GL11.glRotatef(90, 0F, 1F, 0F);
        } else if (orientation == ForgeDirection.NORTH) {
            GL11.glRotatef(180, 0F, 1F, 0F);
        } else if (orientation == ForgeDirection.WEST) {
            GL11.glRotatef(-90, 0F, 1F, 0F);
        }
    }

    private void renderNonSeeThroughBinder(TileEntityBookBinder pEntity) {
        bindTexture(iBookBinderTexture);
        iModelBookBinder.renderPart("BookHolder_Cube");

        bindTexture(iPaperStack);
        iModelBookBinder.renderPart("PaperReserve_Cube.002");

        bindTexture(iFullInkPotTextures);
        iModelBookBinder.renderPart("InkHolder_Sphere");
        iModelBookBinder.renderPart("InkHolderReserve_Sphere.001");
        iModelBookBinder.renderPart("InkHolderReserve2_Sphere.002");
    }

    private void renderSeeThroughInkHolder(TileEntityBookBinder pEntity) {

    }
}
