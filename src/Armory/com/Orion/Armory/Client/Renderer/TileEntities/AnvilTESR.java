package com.Orion.Armory.Client.Renderer.TileEntities;

import com.Orion.Armory.Client.Models.ModelAnvil;
import com.Orion.Armory.Common.TileEntity.Anvil.TileEntityArmorsAnvil;
import com.Orion.Armory.Util.References;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;

/**
 * Created by Orion
 * Created on 02.05.2015
 * 00:27
 * <p/>
 * Copyrighted according to Project specific license
 */
public class AnvilTESR  extends TileEntitySpecialRenderer
{
    protected final ResourceLocation iAnvilTexture = new ResourceLocation(References.General.MOD_ID + ":" + "textures/blocks/AnvilTextured.png");

    public final ModelAnvil iModel = new ModelAnvil();

    public AnvilTESR()
    {

    }

    @Override
    public void renderTileEntityAt(TileEntity pEntity, double pX, double pY, double pZ, float pPartialTickTime) {
        if (!(pEntity instanceof TileEntityArmorsAnvil))
        {
            return;
        }

        TileEntityArmorsAnvil tTEAnvil = (TileEntityArmorsAnvil) pEntity;
        renderTEModelAt(tTEAnvil, pX, pY, pZ, pPartialTickTime);
    }

    private void renderTEModelAt(TileEntityArmorsAnvil pEntity, double pX, double pY, double pZ, float pPartialTickTime)
    {
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        scaleTranslateRotateTEModel(pX, pY, pZ, pEntity.getDirection());

        GL11.glRotatef(-90F, 1F, 0F, 0F);
        GL11.glRotatef(180F, 1F, 0F, 0F);
        Minecraft.getMinecraft().renderEngine.bindTexture(iAnvilTexture);
        iModel.renderAll();

        GL11.glPopMatrix();
    }

    private void scaleTranslateRotateTEModel(double x, double y, double z, ForgeDirection orientation)
    {
        if (orientation == ForgeDirection.NORTH)
        {
            GL11.glTranslated(x + 0.5F, y, z + 0.5F);
        }
        else if (orientation == ForgeDirection.EAST)
        {
            GL11.glTranslated(x + 0.5F, y, z + 0.5F);
            GL11.glRotatef(90F, 0F, 1F, 0F);
        }
        else if (orientation == ForgeDirection.SOUTH)
        {
            GL11.glTranslated(x + 0.5F, y, z + 0.5F);
        }
        else if (orientation == ForgeDirection.WEST)
        {
            GL11.glTranslated(x + 0.5F, y, z + 0.5F);
            GL11.glRotatef(90F, 0F, 1F, 0F);
        }
    }
}
