package com.Orion.Armory.Client.GUI.Components;
/*
/  ComponentBorder
/  Created by : Orion
/  Created on : 27-4-2015
*/

import com.Orion.Armory.Client.GUI.ArmoryBaseGui;
import com.Orion.Armory.Client.GUI.Components.Core.AbstractGUIComponent;
import com.Orion.Armory.Util.Client.Color;
import com.Orion.Armory.Util.Client.CustomResource;
import com.Orion.Armory.Util.Client.Textures;
import org.lwjgl.opengl.GL11;


public class ComponentBorder extends AbstractGUIComponent
{
    Color iBackGroundColor;
    CornerTypes iUpperCornerType;

    public ComponentBorder(ArmoryBaseGui pGui, String pInternalName, int pHeight, int pWidth, int pLeft, int pTop, Color pBackGroundColor, CornerTypes pUpperCornerType) {
        super(pGui, pInternalName, pHeight, pWidth, pLeft, pTop);

        iBackGroundColor = pBackGroundColor;
        iUpperCornerType = pUpperCornerType;
    }

    @Override
    public void onUpdate() {
        //NOOP
    }

    @Override
    public void drawForeGround(int pX, int pY) {
        //NOOP
    }

    @Override
    public void drawBackGround(int pX, int pY) {
        GL11.glPushMatrix();
        GL11.glColor4f(iBackGroundColor.getColorRedFloat(), iBackGroundColor.getColorGreenFloat(), iBackGroundColor.getColorBlueFloat(), iBackGroundColor.getAlphaFloat());

        if (iUpperCornerType == CornerTypes.Outwarts)
        {

        }
        else if (iUpperCornerType == CornerTypes.StraightVertical)
        {
            //Render the center
            //Render the four borders
            //Render the two lower corners(rotate around Z-axis)
        }
        else
        {
            //Render center border part
            renderCenter();

            //Render four Corners
            //renderCornerInwarts(iLeft, iTop, 0);
            //renderCornerInwarts(iLeft + iWidth - 4, iTop, 1);
            //renderCornerInwarts(iLeft + iWidth - 4, iTop + iHeight - 4, 2);
            //renderCornerInwarts(iLeft, iTop + iHeight -4, 3);

            //RenderBorders
            //renderBorder(iLeft + 4, iTop, 0, true);
            //renderBorder(iLeft + iWidth - 4, iTop + 4, 1, true);
            //renderBorder(iLeft + 4, iTop + iHeight - 4, 2, true);
            //renderBorder(iLeft, iTop + 4, 3, true);
        }

        GL11.glColor4f(1F, 1F, 1F, 1F);
        GL11.glPopMatrix();
    }

    private void renderCenter()
    {
        bindTexture(Textures.Gui.Basic.Border.CENTER.getPrimaryLocation());
        if(iWidth - 8 <= Textures.Gui.Basic.Border.CENTER.getWidth() && iHeight - 8 <= Textures.Gui.Basic.Border.CENTER.getHeigth())
        {
            drawTexturedModalRect(iLeft +4, iTop + 4, Textures.Gui.Basic.Border.CENTER.getDistanceToLeft(), Textures.Gui.Basic.Border.CENTER.getDistanceToTop(), iWidth - 8, iHeight - 8);
        }
        else
        {
            int tDrawnHeigth = 5;
            int tDrawnWidth = 5;
            while(tDrawnHeigth < iHeight)
            {
                if (iWidth - 8 <= Textures.Gui.Basic.Border.CENTER.getWidth())
                {
                    drawTexturedModalRect(tDrawnWidth, tDrawnHeigth, Textures.Gui.Basic.Border.CENTER.getDistanceToLeft(), Textures.Gui.Basic.Border.CENTER.getDistanceToTop(), iWidth - 8, Textures.Gui.Basic.Border.CENTER.getHeigth());
                    tDrawnWidth += (iWidth - 8);
                    tDrawnHeigth += Textures.Gui.Basic.Border.CENTER.getHeigth();
                }
                else
                {
                    while (tDrawnWidth <= iWidth)
                    {
                        if (iHeight - 8 < Textures.Gui.Basic.Border.CENTER.getHeigth())
                        {
                            drawTexturedModalRect(tDrawnWidth, tDrawnHeigth, Textures.Gui.Basic.Border.CENTER.getDistanceToLeft(), Textures.Gui.Basic.Border.CENTER.getDistanceToTop(), Textures.Gui.Basic.Border.CENTER.getWidth(), iHeight - 8);
                            tDrawnWidth += Textures.Gui.Basic.Border.CENTER.getWidth();
                            tDrawnHeigth += iHeight - 8;
                        }
                        else
                        {
                            drawTexturedModalRect(tDrawnWidth, tDrawnHeigth, Textures.Gui.Basic.Border.CENTER.getDistanceToLeft(), Textures.Gui.Basic.Border.CENTER.getDistanceToTop(), Textures.Gui.Basic.Border.CENTER.getWidth(), Textures.Gui.Basic.Border.CENTER.getHeigth());
                            tDrawnWidth += Textures.Gui.Basic.Border.CENTER.getWidth();
                            tDrawnHeigth += Textures.Gui.Basic.Border.CENTER.getHeigth();
                        }
                    }
                    tDrawnWidth = 0;
                }
            }
        }
    }

    //pCornerNumber: 0: leftTop, 1: rightTop, 2: rightBottom, 5: leftBottom.
    private void renderCornerOutwarts(int pCornerXPos, int pCornerYPos, int pCornerNumber)
    {
        GL11.glPushMatrix();
        GL11.glColor4f(iBackGroundColor.getColorRedFloat(), iBackGroundColor.getColorGreenFloat(), iBackGroundColor.getColorBlueFloat(), iBackGroundColor.getAlphaFloat());

        GL11.glTranslatef(pCornerXPos, pCornerYPos, 0F);
        GL11.glRotatef(pCornerNumber * 90F, 0F, 0F, 1F);

        bindTexture(Textures.Gui.Basic.Border.OUTWARTSCORNER.getPrimaryLocation());
        drawTexturedModalRect(0,0, Textures.Gui.Basic.Border.OUTWARTSCORNER.getDistanceToLeft(), Textures.Gui.Basic.Border.OUTWARTSCORNER.getDistanceToTop(), Textures.Gui.Basic.Border.OUTWARTSCORNER.getWidth(), Textures.Gui.Basic.Border.OUTWARTSCORNER.getHeigth());

        GL11.glColor4f(1F, 1F, 1F, 1F);
        GL11.glPopMatrix();
    }

    //pCornerNumber: 0: leftTop, 1: rightTop, 2: rightBottom, 3: leftBottom.
    private void renderCornerInwarts(int pCornerXPos, int pCornerYPos, int pCornerNumber)
    {
        GL11.glPushMatrix();
        GL11.glColor4f(iBackGroundColor.getColorRedFloat(), iBackGroundColor.getColorGreenFloat(), iBackGroundColor.getColorBlueFloat(), iBackGroundColor.getAlphaFloat());

        GL11.glTranslatef(pCornerXPos, pCornerYPos, 0F);
        GL11.glRotatef(pCornerNumber * 90F, 0F, 0F, 1F);

        bindTexture(Textures.Gui.Basic.Border.INWARTSCORNER.getPrimaryLocation());
        drawTexturedModalRect(0,0, Textures.Gui.Basic.Border.INWARTSCORNER.getDistanceToLeft(), Textures.Gui.Basic.Border.INWARTSCORNER.getDistanceToTop(), Textures.Gui.Basic.Border.INWARTSCORNER.getWidth(), Textures.Gui.Basic.Border.INWARTSCORNER.getHeigth());

        GL11.glColor4f(1F, 1F, 1F, 1F);
        GL11.glPopMatrix();
    }

    //pCornerNumber: 0: leftTop, 1: rightTop, 2: rightBottom, 3: leftBottom.
    private void renderCornerStraightVertical(int pCornerXPos, int pCornerYPos, int pCornerNumber)
    {
        GL11.glPushMatrix();
        GL11.glColor4f(iBackGroundColor.getColorRedFloat(), iBackGroundColor.getColorGreenFloat(), iBackGroundColor.getColorBlueFloat(), iBackGroundColor.getAlphaFloat());

        GL11.glTranslatef(pCornerXPos, pCornerYPos, 0F);
        GL11.glRotatef(pCornerNumber * 90F, 0F, 0F, 1F);

        bindTexture(Textures.Gui.Basic.Border.INWARTSCORNER.getPrimaryLocation());
        drawTexturedModalRect(0,0, Textures.Gui.Basic.Border.INWARTSCORNER.getDistanceToLeft(), Textures.Gui.Basic.Border.INWARTSCORNER.getDistanceToTop(), Textures.Gui.Basic.Border.INWARTSCORNER.getWidth(), Textures.Gui.Basic.Border.INWARTSCORNER.getHeigth());

        GL11.glColor4f(1F, 1F, 1F, 1F);
        GL11.glPopMatrix();
    }

    //pSideNumber: 0: Top, 1: RightSide, 2: Bottom, 3: BottomSide
    //pBordered: true: Renders as a Border, false renders as a centerpiece.
    private void renderBorder(int pTopLeftXPos, int pTopLeftYPos, int pSideNumber, boolean pBordered)
    {
        GL11.glPushMatrix();
        GL11.glColor4f(iBackGroundColor.getColorRedFloat(), iBackGroundColor.getColorGreenFloat(), iBackGroundColor.getColorBlueFloat(), iBackGroundColor.getAlphaFloat());

        CustomResource tResource;

        if (pBordered)
        {
            tResource = Textures.Gui.Basic.Border.STRAIGHTBORDER;
        }
        else
        {
            tResource = Textures.Gui.Basic.Border.CENTER;
        }

        GL11.glTranslatef(pTopLeftXPos, pTopLeftYPos, 0F);
        GL11.glRotatef(pSideNumber * 90F, 0F, 0F, 1F);

        bindTexture(tResource.getPrimaryLocation());
        if(iWidth - 8 <= Textures.Gui.Basic.Border.CENTER.getWidth() && iHeight - 8 <= Textures.Gui.Basic.Border.CENTER.getHeigth())
        {
            drawTexturedModalRect(0,0, Textures.Gui.Basic.Border.CENTER.getDistanceToLeft(), Textures.Gui.Basic.Border.CENTER.getDistanceToTop(), iWidth - 8, iHeight - 8);
        }
        else
        {
            int tDrawnHeigth = 0;
            int tDrawnWidth = 0;
            while(tDrawnHeigth < iHeight)
            {
                if (iWidth - 8 <= Textures.Gui.Basic.Border.CENTER.getWidth())
                {
                    drawTexturedModalRect(tDrawnWidth, tDrawnHeigth, Textures.Gui.Basic.Border.CENTER.getDistanceToLeft(), Textures.Gui.Basic.Border.CENTER.getDistanceToTop(), iWidth - 8, Textures.Gui.Basic.Border.CENTER.getHeigth());
                    tDrawnWidth += (iWidth - 8);
                    tDrawnHeigth += Textures.Gui.Basic.Border.CENTER.getHeigth();
                }
                else
                {
                    while (tDrawnWidth <= iWidth)
                    {
                        if (iHeight - 8 < Textures.Gui.Basic.Border.CENTER.getHeigth())
                        {
                            drawTexturedModalRect(tDrawnWidth, tDrawnHeigth, Textures.Gui.Basic.Border.CENTER.getDistanceToLeft(), Textures.Gui.Basic.Border.CENTER.getDistanceToTop(), Textures.Gui.Basic.Border.CENTER.getWidth(), iHeight - 8);
                            tDrawnWidth += Textures.Gui.Basic.Border.CENTER.getWidth();
                            tDrawnHeigth += iHeight - 8;
                        }
                        else
                        {
                            drawTexturedModalRect(tDrawnWidth, tDrawnHeigth, Textures.Gui.Basic.Border.CENTER.getDistanceToLeft(), Textures.Gui.Basic.Border.CENTER.getDistanceToTop(), Textures.Gui.Basic.Border.CENTER.getWidth(), Textures.Gui.Basic.Border.CENTER.getHeigth());
                            tDrawnWidth += Textures.Gui.Basic.Border.CENTER.getWidth();
                            tDrawnHeigth += Textures.Gui.Basic.Border.CENTER.getHeigth();
                        }
                    }
                    tDrawnWidth = 0;
                }
            }
        }

        GL11.glColor4f(1F, 1F, 1F, 1F);
        GL11.glPopMatrix();
    }


    @Override
    public void drawToolTips(int pMouseX, int pMouseY) {
        //NOOP
    }

    @Override
    public boolean handleMouseClicked(int pMouseX, int pMouseY, int pMouseButton) {
        return false;
    }

    public enum CornerTypes
    {
        Inwarts,
        Outwarts,
        StraightVertical,
        StraightHorizontal
    }
}
