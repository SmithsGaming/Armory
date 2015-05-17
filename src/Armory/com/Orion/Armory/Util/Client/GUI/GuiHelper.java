package com.Orion.Armory.Util.Client.GUI;

import com.Orion.Armory.Util.Core.Coordinate;
import com.Orion.Armory.Util.Core.Rectangle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
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
    public static int DISPLAYHEIGHT;
    public static int DISPLAYWIDTH;
    public static int GUISCALE;

    public static void drawRectangleStretched(TextureComponent pCenterComponent, int pWidth, int pHeight, Coordinate pElementCoordinate)
    {
        renderCenter(pCenterComponent, pWidth, pHeight, pElementCoordinate);
    }

    public static void drawRectangleStretched(MultiComponentTexture pComponents, int pWidth, int pHeight, Coordinate pElementCoordinate)
    {
        renderCenter(pComponents.iCenterComponent, pWidth - pComponents.iCornerComponents[0].iWidth - pComponents.iCornerComponents[1].iWidth, pHeight - pComponents.iCornerComponents[0].iHeight - pComponents.iCornerComponents[3].iHeight, new Coordinate(pElementCoordinate.getXComponent() + pComponents.iCornerComponents[0].iWidth, pElementCoordinate.getYComponent() + pComponents.iCornerComponents[0].iHeight, pElementCoordinate.getZComponent()));

        renderCorner(pComponents.iCornerComponents[0], pElementCoordinate);
        renderCorner(pComponents.iCornerComponents[1], new Coordinate(pElementCoordinate.getXComponent() + pComponents.iCenterComponent.iWidth + pComponents.iCornerComponents[0].iWidth, pElementCoordinate.getYComponent(), pElementCoordinate.getZComponent()));
        renderCorner(pComponents.iCornerComponents[2], new Coordinate(pElementCoordinate.getXComponent() + pComponents.iCenterComponent.iWidth  + pComponents.iCornerComponents[0].iWidth, pElementCoordinate.getYComponent() + pComponents.iCenterComponent.iHeight  + pComponents.iCornerComponents[0].iHeight, pElementCoordinate.getZComponent()));
        renderCorner(pComponents.iCornerComponents[3], new Coordinate(pElementCoordinate.getXComponent(), pElementCoordinate.getYComponent() + pComponents.iCenterComponent.iHeight  + pComponents.iCornerComponents[0].iHeight, pElementCoordinate.getZComponent()));

        renderBorder(pComponents.iSideComponents[0], pWidth - (pComponents.iCornerComponents[0].iWidth  * 2), pComponents.iSideComponents[0].iHeight, new Coordinate(pElementCoordinate.getXComponent() + pComponents.iCornerComponents[0].iWidth, pElementCoordinate.getYComponent(), pElementCoordinate.getZComponent()));
        renderBorder(pComponents.iSideComponents[1], pComponents.iSideComponents[1].iWidth, pHeight -  (pComponents.iCornerComponents[0].iHeight * 2), new Coordinate(pElementCoordinate.getXComponent() + (pWidth - pComponents.iCenterComponent.iWidth), pElementCoordinate.getYComponent() + pComponents.iCornerComponents[0].iHeight, pElementCoordinate.getZComponent()));
        renderBorder(pComponents.iSideComponents[2], pWidth - (pComponents.iCornerComponents[0].iWidth  * 2), pComponents.iSideComponents[2].iHeight , new Coordinate(pElementCoordinate.getXComponent() + pComponents.iCornerComponents[0].iWidth, pElementCoordinate.getYComponent() + (pComponents.iCornerComponents[0].iHeight * 2), pElementCoordinate.getZComponent()));
        renderBorder(pComponents.iSideComponents[3], pComponents.iSideComponents[3].iWidth, pHeight -  (pComponents.iCornerComponents[0].iHeight * 2), new Coordinate(pElementCoordinate.getXComponent(), pElementCoordinate.getYComponent() + pComponents.iCornerComponents[0].iHeight, pElementCoordinate.getZComponent()));
   }

    public static void drawRectangleStretched(TextureComponent pCenterComponent, TextureComponent[] pSideComponents, TextureComponent[] pCornerComponents, int pWidth, int pHeight, Coordinate pElementCoordinate)
    {
        renderCenter(pCenterComponent, pWidth - pCornerComponents[0].iWidth - pCornerComponents[1].iWidth, pHeight - pCornerComponents[0].iHeight - pCornerComponents[3].iHeight, new Coordinate(pElementCoordinate.getXComponent() + pCornerComponents[0].iWidth, pElementCoordinate.getYComponent() + pCornerComponents[0].iHeight, pElementCoordinate.getZComponent()));

        renderCorner(pCornerComponents[0], pElementCoordinate);
        renderCorner(pCornerComponents[1], new Coordinate(pElementCoordinate.getXComponent() + pWidth, pElementCoordinate.getYComponent(), pElementCoordinate.getZComponent()));
        renderCorner(pCornerComponents[2], new Coordinate(pElementCoordinate.getXComponent() + pWidth, pElementCoordinate.getYComponent() + pHeight, pElementCoordinate.getZComponent()));
        renderCorner(pCornerComponents[3], new Coordinate(pElementCoordinate.getXComponent(), pElementCoordinate.getYComponent() + pHeight, pElementCoordinate.getZComponent()));

        renderBorder(pSideComponents[0], pWidth - pCornerComponents[0].iWidth - pCornerComponents[1].iWidth, pSideComponents[0].iHeight, new Coordinate(pElementCoordinate.getXComponent() + pCornerComponents[0].iWidth, pElementCoordinate.getYComponent(), pElementCoordinate.getZComponent()));
        renderBorder(pSideComponents[1], pHeight - pCornerComponents[1].iHeight - pCornerComponents[2].iHeight, pSideComponents[1].iHeight, new Coordinate(pElementCoordinate.getXComponent() + pWidth - pSideComponents[1].iHeight, pElementCoordinate.getYComponent() + pHeight - pCornerComponents[2].iHeight, pElementCoordinate.getZComponent()));
        renderBorder(pSideComponents[2],pWidth - pCornerComponents[2].iWidth - pCornerComponents[3].iWidth, pSideComponents[2].iHeight , new Coordinate(pElementCoordinate.getXComponent() + pCornerComponents[3].iWidth, pElementCoordinate.getYComponent() + pHeight - pSideComponents[2].iHeight, pElementCoordinate.getZComponent()));
        renderBorder(pSideComponents[3], pHeight - pCornerComponents[3].iHeight - pCornerComponents[0].iHeight, pSideComponents[3].iHeight, new Coordinate(pElementCoordinate.getXComponent(), pElementCoordinate.getYComponent() + pHeight - pCornerComponents[3].iHeight, pElementCoordinate.getZComponent()));
    }



    public static void drawFluid(FluidStack pFluidStack, int pX, int pY, int pZ, int pWidth, int pHeight) {
        if (pFluidStack == null || pFluidStack.getFluid() == null) {
            return;
        }
        IIcon tIcon = pFluidStack.getFluid().getIcon(pFluidStack);

        if (tIcon == null) {
            tIcon = ((TextureMap) Minecraft.getMinecraft().getTextureManager().getTexture(TextureMap.locationBlocksTexture)).getAtlasSprite("missingno");
        }

        bindTexture(TextureMap.locationBlocksTexture);
        setGLColorFromInt(pFluidStack.getFluid().getColor(pFluidStack));
        int tFullX = pWidth / 16 + 1;
        int tFullY = pHeight / 16 + 1;
        for (int i = 0; i < tFullX; i++) {
            for (int j = 0; j < tFullY; j++) {
                    drawCutIcon(tIcon, pX + i * 16, pY + j * 16, pZ, 16, 16, 0);
            }
        }
    }

    private static void drawCutIcon(IIcon pIcon, int pX, int pY, int pZ, int pWidth, int pHeight, int pCutOffVertical) {
        Tessellator tess = Tessellator.instance;
        tess.startDrawingQuads();
        tess.addVertexWithUV(pX, pY + pHeight, pZ, pIcon.getMinU(), pIcon.getInterpolatedV(pHeight));
        tess.addVertexWithUV(pX + pWidth, pY + pHeight, pZ, pIcon.getInterpolatedU(pWidth), pIcon.getInterpolatedV(pHeight));
        tess.addVertexWithUV(pX + pWidth, pY + pCutOffVertical, pZ, pIcon.getInterpolatedU(pWidth), pIcon.getInterpolatedV(pCutOffVertical));
        tess.addVertexWithUV(pX, pY + pCutOffVertical, pZ, pIcon.getMinU(), pIcon.getInterpolatedV(pCutOffVertical));
        tess.draw();
    }


    private static void renderCenter(TextureComponent pComponent, int pWidth, int pHeight, Coordinate pElementCoordinate)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef(pElementCoordinate.getXComponent() + pComponent.iRelativeTranslation.getXComponent(), pElementCoordinate.getYComponent() + pComponent.iRelativeTranslation.getYComponent(), pElementCoordinate.getZComponent() + pComponent.iRelativeTranslation.getZComponent());
        pComponent.iRotation.performGLRotation();

        bindTexture(pComponent.iAddress);
        if(pWidth <= pComponent.iWidth && pHeight <= pComponent.iHeight)
        {
            drawTexturedModalRect(0, 0, 0, pComponent.iU, pComponent.iV, pWidth, pHeight);
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
                        int tWidthToRender = pWidth - tDrawnWidth;
                        if (tWidthToRender >= pComponent.iWidth)
                            tWidthToRender = pComponent.iWidth;

                        if (pHeight < pComponent.iHeight)
                        {
                            drawTexturedModalRect(tDrawnWidth, 0, 0, pComponent.iU, pComponent.iV, tWidthToRender, tHeightToRender);
                            tDrawnWidth += tWidthToRender;
                        }
                        else
                        {
                            drawTexturedModalRect(tDrawnWidth, tDrawnHeight, 0, pComponent.iU, pComponent.iV, tWidthToRender, pComponent.iHeight);
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
        GL11.glTranslatef(pElementCoordinate.getXComponent() + pComponent.iRelativeTranslation.getXComponent(), pElementCoordinate.getYComponent() + pComponent.iRelativeTranslation.getYComponent(), pElementCoordinate.getZComponent() + pComponent.iRelativeTranslation.getZComponent());
        pComponent.iRotation.performGLRotation();

        bindTexture(pComponent.iAddress);
        drawTexturedModalRect(0, 0, 0, pComponent.iU, pComponent.iV, pComponent.iWidth, pComponent.iHeight);

        GL11.glPopMatrix();
    }

    private static void renderBorder(TextureComponent pComponent, int pWidth, int pHeight, Coordinate pElementCoordinate)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef(pElementCoordinate.getXComponent() + pComponent.iRelativeTranslation.getXComponent(), pElementCoordinate.getYComponent() + pComponent.iRelativeTranslation.getYComponent(), pElementCoordinate.getZComponent() + pComponent.iRelativeTranslation.getZComponent());
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

                    drawTexturedModalRect(tDrawnWidth, 0, 0, pComponent.iU, pComponent.iV, tWidthToRender, pComponent.iHeight);
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
        tessellator.addVertexWithUV((double) (pXKoord + 0), (double) (pYKoord + pHeight), (double) pZKoord, (double) ((float) (pU + 0) * f), (double) ((float) (pV + pHeight) * f1));
        tessellator.addVertexWithUV((double) (pXKoord + pWidth), (double) (pYKoord + pHeight), (double) pZKoord, (double) ((float) (pU + pWidth) * f), (double) ((float) (pV + pHeight) * f1));
        tessellator.addVertexWithUV((double) (pXKoord + pWidth), (double) (pYKoord + 0), (double) pZKoord, (double) ((float) (pU + pWidth) * f), (double) ((float) (pV + 0) * f1));
        tessellator.addVertexWithUV((double) (pXKoord + 0), (double) (pYKoord + 0), (double) pZKoord, (double) ((float) (pU + 0) * f), (double) ((float) (pV + 0) * f1));
        tessellator.draw();
    }

    public static void drawTexturedModelRectFromIcon(int pXCoord, int pYCoord, int pZCoord, IIcon pIIcon, int pWidth, int pHeight)
    {
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV((double)(pXCoord + 0), (double)(pYCoord + pHeight), (double)pZCoord, (double)pIIcon.getMinU(), (double)pIIcon.getMaxV());
        tessellator.addVertexWithUV((double)(pXCoord + pWidth), (double)(pYCoord + pHeight), (double)pZCoord, (double)pIIcon.getMaxU(), (double)pIIcon.getMaxV());
        tessellator.addVertexWithUV((double)(pXCoord + pWidth), (double)(pYCoord + 0), (double)pZCoord, (double)pIIcon.getMaxU(), (double)pIIcon.getMinV());
        tessellator.addVertexWithUV((double)(pXCoord + 0), (double)(pYCoord + 0), (double)pZCoord, (double)pIIcon.getMinU(), (double)pIIcon.getMinV());
        tessellator.draw();
    }

    public static void drawColoredRect(Rectangle pRectangle, int pColor)
    {
        float f3 = (float)(pColor >> 24 & 255) / 255.0F;
        float f = (float)(pColor >> 16 & 255) / 255.0F;
        float f1 = (float)(pColor >> 8 & 255) / 255.0F;
        float f2 = (float)(pColor & 255) / 255.0F;
        Tessellator tessellator = Tessellator.instance;
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f(f, f1, f2, f3);
        tessellator.startDrawingQuads();
        tessellator.addVertex((double)pRectangle.getTopLeftCoord().getXComponent(), (double)pRectangle.getLowerRightCoord().getYComponent(), 0.0D);
        tessellator.addVertex((double)pRectangle.getLowerRightCoord().getXComponent(), (double)pRectangle.getLowerRightCoord().getYComponent(), 0.0D);
        tessellator.addVertex((double)pRectangle.getLowerRightCoord().getXComponent(), (double)pRectangle.getTopLeftCoord().getYComponent(), 0.0D);
        tessellator.addVertex((double)pRectangle.getTopLeftCoord().getXComponent(), (double)pRectangle.getTopLeftCoord().getYComponent(), 0.0D);
        tessellator.draw();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
    }

    public static void bindTexture(String pTextureAddress)
    {
        bindTexture(new ResourceLocation(pTextureAddress));
    }

    public static void bindTexture(ResourceLocation pTextureLocation)
    {
        Minecraft.getMinecraft().renderEngine.bindTexture(pTextureLocation);
    }

    public static void calcScaleFactor() {
        Minecraft mc = Minecraft.getMinecraft();
        ScaledResolution sc = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
        DISPLAYWIDTH = sc.getScaledWidth();
        DISPLAYHEIGHT = sc.getScaledHeight();
        GUISCALE = sc.getScaleFactor();
    }

    public static void enableScissor(Rectangle pTargetRectangle)
    {
        calcScaleFactor();

        GL11.glPushAttrib(GL11.GL_SCISSOR_BIT);
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        GL11.glScissor(pTargetRectangle.getTopLeftCoord().getXComponent() * GUISCALE, ((DISPLAYHEIGHT - pTargetRectangle.getTopLeftCoord().getYComponent()) * GUISCALE), (pTargetRectangle.iWidth) * GUISCALE, (pTargetRectangle.iHeigth) * GUISCALE);
    }

    public static void disableScissor()
    {
        GL11.glPopAttrib();
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
    }

    public static void renderScissorDebugOverlay()
    {
        bindTexture(TextureMap.locationItemsTexture);
        drawTexturedModalRect(0,0,10, 0,0, DISPLAYWIDTH, DISPLAYHEIGHT);
    }

    public static void setGLColorFromInt(int color) {
        float red = (color >> 16 & 255) / 255.0F;
        float green = (color >> 8 & 255) / 255.0F;
        float blue = (color & 255) / 255.0F;
        GL11.glColor4f(red, green, blue, 1.0F);
    }
}
