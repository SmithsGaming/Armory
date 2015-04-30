package com.Orion.Armory.Client.Renderer.TileEntities;

import com.Orion.Armory.Client.Models.ModelHeater;
import com.Orion.Armory.Common.TileEntity.TileEntityHeater;
import com.Orion.Armory.Util.References;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;

/**
 * Created by Orion
 * Created on 23.04.2015
 * 10:37
 * <p/>
 * Copyrighted according to Project specific license
 */
public class HeaterTESR extends TileEntitySpecialRenderer
{
    protected final ResourceLocation iHeaterTexture = new ResourceLocation(References.General.MOD_ID + ":" + "textures/blocks/HeaterHull.png");
    protected final ResourceLocation iFanTexture = new ResourceLocation(References.General.MOD_ID + ":" + "textures/blocks/FanIron.png");

    public final ModelHeater iModel = new ModelHeater();

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
        iModel.renderPart("Hull_Cube");

        if(tIsContainingAFan)
        {
            float tRotationAngle = 0F;
            if (tIsHelpingAFirePit)
            {
                if (pEntity.iItemInSlotTicks <= 100)
                {
                    int tTickRotationPerSecond =(int) (720 * (pEntity.iItemInSlotTicks / 100F));
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
                    int tFullSpeedTime = pEntity.iItemInSlotTicks - 100;
                    int tRotationTick = tFullSpeedTime % 20;

                    tRotationAngle = tRotationTick * 36 + pPartialTickTime * 36;

                    if (tRotationAngle > 360F)
                    {
                        tRotationAngle -= 360F;
                    }
                }
            }

            GL11.glRotatef(tRotationAngle, 1F, 0F, 0F);
            this.bindTexture(iFanTexture);
            iModel.renderPart("Fan_Cylinder");
        }
        GL11.glPopMatrix();
    }

    private void scaleTranslateRotateTEModel(double x, double y, double z, ForgeDirection orientation)
    {
        if (orientation == ForgeDirection.NORTH)
        {
            GL11.glTranslated(x + 0.5F, y + 0.5F, z + 0.5F);
            GL11.glRotatef(-90F, 0F, 1F, 0F);
        }
        else if (orientation == ForgeDirection.EAST)
        {
            GL11.glTranslated(x + 0.5F, y + 0.5F, z + 0.5F);
            GL11.glRotatef(180F, 0F, 1F, 0F);
        }
        else if (orientation == ForgeDirection.SOUTH)
        {
            GL11.glTranslated(x + 0.5F, y + 0.5F, z + 0.5F);
            GL11.glRotatef(90F, 0F, 1F, 0F);
        }
        else if (orientation == ForgeDirection.WEST)
        {
            GL11.glTranslated(x + 0.5F, y + 0.5F, z + 0.5F);
        }
    }
}
