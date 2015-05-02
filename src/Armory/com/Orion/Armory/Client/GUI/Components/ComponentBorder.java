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

    public ComponentBorder(ArmoryBaseGui pGui, String pInternalName,int pLeft, int pTop, int pWidth, int pHeight, Color pBackGroundColor, CornerTypes pUpperCornerType) {
        super(pGui, pInternalName, pLeft, pTop, pWidth, pHeight);

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
            //renderCenter();

            //renderCornerOutwarts(-4, -4, 0);
            renderCornerOutwarts(-4, iWidth, 1);
            renderCornerInwarts(iWidth, iHeight, 2);
            renderCornerInwarts(0, iHeight, 3);

            renderBorder(3, 0, 0, false);
            renderBorder(iWidth, 3, 1, true);
            renderBorder(iWidth - 3, iHeight, 2, true);
            renderBorder(0, iHeight - 3, 3, true);
        }
        else if (iUpperCornerType == CornerTypes.StraightVertical)
        {
            renderCenter();

            renderCornerStraightVertical(0, 0, 0);
            renderCornerStraightVertical(0, iWidth, 1);
            renderCornerInwarts(iWidth, iHeight, 2);
            renderCornerInwarts(0, iHeight, 3);

            renderBorder(3, 0, 0, false);
            renderBorder(iWidth, 3, 1, true);
            renderBorder(iWidth - 3, iHeight, 2, true);
            renderBorder(0, iHeight - 3, 3, true);
        }
        else
        {
            //Render center border part
            renderCenter();

            //Render four Corners
            renderCornerInwarts(0, 0, 0);
            renderCornerInwarts(iWidth, 0, 1);
            renderCornerInwarts(iWidth, iHeight, 2);
            renderCornerInwarts(0, iHeight, 3);

            //RenderBorders
            renderBorder(3, 0, 0, true);
            renderBorder(iWidth, 3, 1, true);
            renderBorder(iWidth - 3, iHeight, 2, true);
            renderBorder(0, iHeight - 3, 3, true);
        }

        GL11.glColor4f(1F, 1F, 1F, 1F);
        GL11.glPopMatrix();
    }

    private void renderCenter()
    {
        bindTexture(Textures.Gui.Basic.Border.CENTER.getPrimaryLocation());
        if(iWidth - 6 <= Textures.Gui.Basic.Border.CENTER.getWidth() && iHeight - 8 <= Textures.Gui.Basic.Border.CENTER.getHeigth())
        {
            drawTexturedModalRect(3, 3, Textures.Gui.Basic.Border.CENTER.getDistanceToLeft(), Textures.Gui.Basic.Border.CENTER.getDistanceToTop(), iWidth - 6, iHeight - 6);
        }
        else
        {
            int tDrawnHeigth = 3;
            int tDrawnWidth = 3;
            while(tDrawnHeigth < iHeight)
            {
                if (iWidth - 6 <= Textures.Gui.Basic.Border.CENTER.getWidth())
                {
                    drawTexturedModalRect(tDrawnWidth, tDrawnHeigth, Textures.Gui.Basic.Border.CENTER.getDistanceToLeft(), Textures.Gui.Basic.Border.CENTER.getDistanceToTop(), iWidth - 6, Textures.Gui.Basic.Border.CENTER.getHeigth());
                    tDrawnWidth += (iWidth - 6);
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
                            tDrawnHeigth += iHeight - 6;
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

        GL11.glTranslatef(pCornerXPos, pCornerYPos, 0F);
        GL11.glRotatef(pCornerNumber * 90F, 0F, 0F, 1F);

        bindTexture(Textures.Gui.Basic.Border.OUTWARTSCORNER.getPrimaryLocation());
        drawTexturedModalRect(0, 0, Textures.Gui.Basic.Border.OUTWARTSCORNER.getDistanceToLeft(), Textures.Gui.Basic.Border.OUTWARTSCORNER.getDistanceToTop(), Textures.Gui.Basic.Border.OUTWARTSCORNER.getWidth(), Textures.Gui.Basic.Border.OUTWARTSCORNER.getHeigth());

        GL11.glPopMatrix();
    }

    //pCornerNumber: 0: leftTop, 1: rightTop, 2: rightBottom, 3: leftBottom.
    private void renderCornerInwarts(int pCornerXPos, int pCornerYPos, int pCornerNumber)
    {
        GL11.glPushMatrix();

        GL11.glTranslatef(pCornerXPos, pCornerYPos, 0F);

        CustomResource tResource;

        if (pCornerNumber != 2)
        {
            GL11.glRotatef(pCornerNumber * 90F, 0F, 0F, 1F);
            tResource = Textures.Gui.Basic.Border.INWARTSCORNERLIGHT;
        }
        else
        {
            tResource = Textures.Gui.Basic.Border.INWARTSCORNERDARK;
            GL11.glTranslatef(-4F, -4F, 0F);
        }

        bindTexture(tResource.getPrimaryLocation());
        drawTexturedModalRect(0,0,tResource.getDistanceToLeft(), tResource.getDistanceToTop(), tResource.getWidth(), tResource.getHeigth());

        GL11.glPopMatrix();
    }

    //pCornerNumber: 0: leftTop, 1: rightTop, 2: rightBottom, 3: leftBottom.
    private void renderCornerStraightVertical(int pCornerXPos, int pCornerYPos, int pCornerNumber)
    {
        GL11.glPushMatrix();

        pCornerNumber = (pCornerNumber % 2);

        GL11.glTranslatef(pCornerXPos, pCornerYPos, 0F);

        CustomResource tResource;
        if (pCornerNumber == 0)
        {
            tResource = Textures.Gui.Basic.Border.STRAIGHTBORDERLIGHT;
            GL11.glRotatef(270F, 0F, 0F, 1F);
        }
        else
        {
            tResource = Textures.Gui.Basic.Border.STRAIGHTBORDERDARK;
            GL11.glRotatef(90F, 0F, 0F, 1F);
        }

        bindTexture(tResource.getPrimaryLocation());
        drawTexturedModalRect(pCornerXPos-3, pCornerYPos, tResource.getDistanceToLeft(), tResource.getDistanceToTop(),6, tResource.getHeigth());

        GL11.glPopMatrix();
    }

    //pSideNumber: 0: Top, 1: RightSide, 2: Bottom, 3: BottomSide
    //pBordered: true: Renders as a Border, false renders as a centerpiece.
    private void renderBorder(int pTopLeftXPos, int pTopLeftYPos, int pSideNumber, boolean pBordered)
    {
        GL11.glPushMatrix();

        CustomResource tResource;
        int tLength = iWidth - 6;
        int tHeigth;

        if ((pSideNumber % 2) == 1)
        {
            tLength = iHeight - 6;
        }

        GL11.glTranslatef(pTopLeftXPos, pTopLeftYPos, 0F);
        GL11.glRotatef(pSideNumber * 90F, 0F, 0F, 1F);

        if (pBordered)
        {
            if ((pSideNumber != 1) && (pSideNumber != 2))
            {
                tResource = Textures.Gui.Basic.Border.STRAIGHTBORDERLIGHT;
                tHeigth = tResource.getHeigth();
            }
            else
            {
                tResource = Textures.Gui.Basic.Border.STRAIGHTBORDERDARK;
                tHeigth = tResource.getHeigth();
                GL11.glRotatef(180F, 0F, 0F, 1F);
                GL11.glTranslatef(-tLength, -3F, 0F);
            }
        }
        else {
            tResource = Textures.Gui.Basic.Border.CENTER;
            tHeigth = 6;
            GL11.glTranslatef(0F, -3F, 0F);
        }

        bindTexture(tResource.getPrimaryLocation());

        if(tLength <= Textures.Gui.Basic.Border.STRAIGHTBORDERLIGHT.getWidth())
        {
            drawTexturedModalRect(0, 0, tResource.getDistanceToLeft(), tResource.getDistanceToTop(), tLength, tHeigth);
        }
        else
        {
            int tDrawnHeigth = 0;
            int tDrawnWidth = 0;
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
        }
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
