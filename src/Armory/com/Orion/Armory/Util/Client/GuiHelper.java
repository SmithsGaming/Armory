package com.Orion.Armory.Util.Client;

import com.Orion.Armory.Util.Core.Coordinate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * Created by Orion
 * Created on 06.05.2015
 * 19:15
 * <p/>
 * Copyrighted according to Project specific license
 */
public final class GuiHelper
{

    public static void drawRectangleStretched(TextureComponent pCenterComponent, TextureComponent[] pSideComponents, TextureComponent[] pCornerComponents, int pWidth, int pHeight, Coordinate pElementCoordinate)
    {
        renderCenter(pCenterComponent, pWidth - pCornerComponents[0].iWidth - pCornerComponents[1].iWidth, pHeight - pCornerComponents[0].iHeight - pCornerComponents[3].iHeight, new Coordinate(pElementCoordinate.getXComponent() + pCornerComponents[0].iWidth, pElementCoordinate.getYComponent() + pCornerComponents[0].iHeight, pElementCoordinate.getZComponent()));
        renderCorner(pCornerComponents[0], pElementCoordinate);
        renderCorner(pCornerComponents[1], new Coordinate(pElementCoordinate.getXComponent() + pWidth, pElementCoordinate.getYComponent(), pElementCoordinate.getZComponent()));
        renderCorner(pCornerComponents[2], new Coordinate(pElementCoordinate.getXComponent() + pWidth, pElementCoordinate.getYComponent() + pHeight, pElementCoordinate.getZComponent()));
        renderCorner(pCornerComponents[3], new Coordinate(pElementCoordinate.getXComponent(), pElementCoordinate.getYComponent() + pHeight, pElementCoordinate.getZComponent()));

        renderBorder(pSideComponents[0], pWidth - pCornerComponents[0].iWidth - pCornerComponents[1].iWidth, pSideComponents[0].iHeight, new Coordinate(pElementCoordinate.getXComponent() + pCornerComponents[0].iWidth, pElementCoordinate.getYComponent(), pElementCoordinate.getZComponent()));
        renderBorder(pSideComponents[1],pHeight - pCornerComponents[1].iHeight - pCornerComponents[2].iHeight, pSideComponents[1].iHeight , new Coordinate(pElementCoordinate.getXComponent() + pWidth - pSideComponents[1].iHeight, pElementCoordinate.getYComponent() + pHeight - pCornerComponents[2].iHeight, pElementCoordinate.getZComponent()));
        renderBorder(pSideComponents[2],pWidth - pCornerComponents[2].iWidth - pCornerComponents[3].iWidth, pSideComponents[2].iHeight , new Coordinate(pElementCoordinate.getXComponent() + pCornerComponents[3].iWidth, pElementCoordinate.getYComponent() + pHeight - pSideComponents[2].iHeight, pElementCoordinate.getZComponent()));
        renderBorder(pSideComponents[3],pHeight - pCornerComponents[3].iHeight - pCornerComponents[0].iHeight, pSideComponents[3].iHeight , new Coordinate(pElementCoordinate.getXComponent(), pElementCoordinate.getYComponent() + pHeight - pCornerComponents[3].iHeight, pElementCoordinate.getZComponent()));
    }

    private static void renderCenter(TextureComponent pComponent, int pWidth, int pHeight, Coordinate pElementCoordinate)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef(pElementCoordinate.getXComponent()+ pComponent.iRelativeTranslation.getXComponent(), pElementCoordinate.getYComponent()+ pComponent.iRelativeTranslation.getYComponent(), pElementCoordinate.getZComponent()+ pComponent.iRelativeTranslation.getZComponent());
        pComponent.iRotation.performGLRotation();

        bindTexture(pComponent.iAddress);
        if(pWidth <= pComponent.iWidth && pHeight <= pComponent.iHeight)
        {
            drawTexturedModalRect(0, 0,0 , pComponent.iU, pComponent.iV, pWidth, pHeight);
        }
        else
        {
            int tDrawnHeight = 0;
            int tDrawnWidth = 0;
            while(tDrawnHeight < (pHeight))
            {
                int tHeightToRender = pHeight - tDrawnHeight;
                if (tHeightToRender >= pComponent.iWidth)
                    tHeightToRender = pComponent.iWidth;

                if (pWidth <= pComponent.iWidth)
                {
                    drawTexturedModalRect(tDrawnWidth, tDrawnHeight, 0, pComponent.iU, pComponent.iV, pWidth, tHeightToRender);
                    tDrawnWidth += (pWidth);
                    tDrawnHeight += pComponent.iHeight;
                }
                else
                {
                    while (tDrawnWidth < (pWidth))
                    {
                        if (pHeight < pComponent.iHeight)
                        {
                            int tWidthToRender = pWidth - tDrawnWidth;
                            if (tWidthToRender >= pComponent.iWidth)
                                tWidthToRender = pComponent.iWidth;

                            drawTexturedModalRect(tDrawnWidth, 0, 0, pComponent.iU, pComponent.iV, tWidthToRender, tHeightToRender);
                            tDrawnWidth += tWidthToRender;
                        }
                        else
                        {
                            drawTexturedModalRect(tDrawnWidth, tDrawnHeight, 0, pComponent.iU, pComponent.iV, pComponent.iWidth, pComponent.iHeight);
                            tDrawnWidth += pComponent.iWidth;
                        }
                    }
                    tDrawnWidth = 0;
                    tDrawnHeight += tHeightToRender;
                }
            }
        }
        
        GL11.glPopMatrix();
    }

    private static void renderCorner(TextureComponent pComponent, Coordinate pElementCoordinate)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef(pElementCoordinate.getXComponent()+ pComponent.iRelativeTranslation.getXComponent(), pElementCoordinate.getYComponent()+ pComponent.iRelativeTranslation.getYComponent(), pElementCoordinate.getZComponent()+ pComponent.iRelativeTranslation.getZComponent());
        pComponent.iRotation.performGLRotation();

        bindTexture(pComponent.iAddress);
        drawTexturedModalRect(0, 0, 0, pComponent.iU, pComponent.iV, pComponent.iWidth, pComponent.iHeight);

        GL11.glPopMatrix();
    }

    private static void renderBorder(TextureComponent pComponent, int pWidth, int pHeight, Coordinate pElementCoordinate)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef(pElementCoordinate.getXComponent()+ pComponent.iRelativeTranslation.getXComponent(), pElementCoordinate.getYComponent()+ pComponent.iRelativeTranslation.getYComponent(), pElementCoordinate.getZComponent()+ pComponent.iRelativeTranslation.getZComponent());
        pComponent.iRotation.performGLRotation();

        bindTexture(pComponent.iAddress);

        if(pWidth <= pComponent.iWidth)
        {
            drawTexturedModalRect(0, 0, 0, pComponent.iU, pComponent.iV, pWidth, pHeight);
        }
        else
        {
            int tDrawnHeigth = 0;
            int tDrawnWidth = 0;
            if (pWidth <= pComponent.iWidth)
            {
                drawTexturedModalRect(tDrawnWidth, tDrawnHeigth, 0, pComponent.iU, pComponent.iV, pWidth, pHeight);
            }
            else
            {
                while (tDrawnWidth < (pWidth))
                {
                    int tWidthToRender = pWidth - tDrawnWidth;
                    if (tWidthToRender >= pComponent.iWidth)
                        tWidthToRender = pComponent.iWidth;

                    drawTexturedModalRect(tDrawnWidth, 0, 0, pComponent.iU, pComponent.iV, tWidthToRender, pWidth);
                    tDrawnWidth += tWidthToRender;
                }
            }
        }
        GL11.glPopMatrix();
    }

    public static void drawTexturedModalRect(int pXKoord, int pYKoord, int pZKoord, int pU, int pV, int pWidth, int pHeight)
    {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV((double)(pXKoord + 0), (double)(pYKoord + pHeight), (double)pZKoord, (double)((float)(pU + 0) * f), (double)((float)(pV + pHeight) * f1));
        tessellator.addVertexWithUV((double)(pXKoord + pWidth), (double)(pYKoord + pHeight), (double)pZKoord, (double)((float)(pU + pWidth) * f), (double)((float)(pV + pHeight) * f1));
        tessellator.addVertexWithUV((double)(pXKoord + pWidth), (double)(pYKoord + 0), (double)pZKoord, (double)((float)(pU + pWidth) * f), (double)((float)(pV + 0) * f1));
        tessellator.addVertexWithUV((double)(pXKoord + 0), (double)(pYKoord + 0), (double)pZKoord, (double)((float)(pU + 0) * f), (double)((float)(pV + 0) * f1));
        tessellator.draw();
    }

    public static void bindTexture(String pTextureAddress)
    {
        bindTexture(new ResourceLocation(pTextureAddress));
    }

    public static void bindTexture(ResourceLocation pTextureLocation)
    {
        Minecraft.getMinecraft().renderEngine.bindTexture(pTextureLocation);
    }
}
