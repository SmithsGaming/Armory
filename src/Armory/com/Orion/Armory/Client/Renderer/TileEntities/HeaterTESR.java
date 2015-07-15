/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.Orion.Armory.Client.Renderer.TileEntities;

import com.Orion.Armory.Client.Models.ModelHeater;
import com.Orion.Armory.Common.TileEntity.FirePit.TileEntityHeater;
import com.Orion.Armory.Util.References;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;

public class HeaterTESR extends TileEntitySpecialRenderer
{
    public final ModelHeater iModel = new ModelHeater();
    protected final ResourceLocation iHeaterTexture = new ResourceLocation(References.General.MOD_ID + ":" + "textures/blocks/Heater.png");
    protected final ResourceLocation iFanTexture = new ResourceLocation(References.General.MOD_ID + ":" + "textures/blocks/Fan.png");

    public HeaterTESR()
    {
    }

    @Override
    public void renderTileEntityAt(TileEntity pEntity, double pX, double pY, double pZ, float pPartialTickTime) {
        if (!(pEntity instanceof TileEntityHeater))
        {
            return;
        }

        TileEntityHeater tTEHeater = (TileEntityHeater) pEntity;
        renderTEModelAt(tTEHeater, pX, pY, pZ, pPartialTickTime);
    }

    private void renderTEModelAt(TileEntityHeater pEntity, double pX, double pY, double pZ, float pPartialTickTime)
    {
        boolean tIsContainingAFan = pEntity.IsContainingAFan();
        boolean tIsHelpingAFirePit = pEntity.IsHelpingAFirePit();

        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        scaleTranslateRotateTEModel(pX, pY, pZ, pEntity.getDirection());

        this.bindTexture(iHeaterTexture);
        iModel.renderPart("Heater_Cube");

        if(tIsContainingAFan)
        {
            float tRotationAngle = 0F;
            if (tIsHelpingAFirePit)
            {
                if (pEntity.iItemInSlotTicks <= 200)
                {
                    int tTickRotationPerSecond = (int) (720 * (pEntity.iItemInSlotTicks / 200F));
                    int tTickRotation = (int) ((tTickRotationPerSecond / 20F) * pPartialTickTime);

                    tRotationAngle = pEntity.iLastRotationAngle + tTickRotation;

                    if (tRotationAngle > 360)
                    {
                        tRotationAngle -= 360F;
                    }

                    pEntity.iLastRotationAngle = tRotationAngle;
                }
                else
                {
                    int tFullSpeedTime = pEntity.iItemInSlotTicks - 200;
                    int tRotationTick = tFullSpeedTime % 20;

                    tRotationAngle = tRotationTick * 36 + pPartialTickTime * 36;

                    if (tRotationAngle > 360F)
                    {
                        tRotationAngle -= 360F;
                    }
                }
            }

            GL11.glRotatef(tRotationAngle, 0F, 1F, 0F);
            this.bindTexture(iFanTexture);
            iModel.renderPart("Fan_Cylinder");
        }
        GL11.glPopMatrix();
    }

    private void scaleTranslateRotateTEModel(double x, double y, double z, ForgeDirection orientation)
    {
        GL11.glTranslatef((float) x + 0.5F, (float) y, (float) z + 0.5F);
    }
}
