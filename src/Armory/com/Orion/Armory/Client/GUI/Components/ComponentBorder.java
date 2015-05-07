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

        GL11.glTranslatef(iLeft, iTop, 0F);

        if (iUpperCornerType == CornerTypes.Outwarts)
        {
            //renderCenter();
            renderCenter();

            renderCornerOutwarts(0, 0, 0);
            renderCornerOutwarts(iWidth, 0, 1);
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
            renderCornerStraightVertical(iWidth, 0, 1);
            renderCornerInwarts(iWidth, iHeight, 2);
            renderCornerInwarts(0, iHeight, 3);

            renderBorder(3, 0, 0, false);
            renderBorder(iWidth, 3, 1, true);
            renderBorder(iWidth - 3, iHeight, 2, true);
            renderBorder(0, iHeight - 3, 3, true);
        }
        else
        {

            renderCenter();


            renderCornerInwarts(0, 0, 0);
            renderCornerInwarts(iWidth, 0, 1);
            renderCornerInwarts(iWidth, iHeight, 2);
            renderCornerInwarts(0, iHeight, 3);


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
        if(iWidth - 6 <= Textures.Gui.Basic.Border.CENTER.getWidth() && iHeight - 6 <= Textures.Gui.Basic.Border.CENTER.getHeigth())
        {
            drawTexturedModalRect(3, 3, Textures.Gui.Basic.Border.CENTER.getU(), Textures.Gui.Basic.Border.CENTER.getV(), iWidth - 6, iHeight - 6);
        }
        else
        {
            int tDrawnHeigth = 3;
            int tDrawnWidth = 3;
            while(tDrawnHeigth < (iHeight - 3))
            {
                int tHeightToRender = iHeight - 3 - tDrawnHeigth;
                if (tHeightToRender >= Textures.Gui.Basic.Border.CENTER.getWidth())
                    tHeightToRender = Textures.Gui.Basic.Border.CENTER.getWidth();

                if (iWidth - 6 <= Textures.Gui.Basic.Border.CENTER.getWidth())
                {
                    drawTexturedModalRect(tDrawnWidth, tDrawnHeigth, Textures.Gui.Basic.Border.CENTER.getU(), Textures.Gui.Basic.Border.CENTER.getV(), iWidth - 6, tHeightToRender);
                    tDrawnWidth += (iWidth - 6);
                    tDrawnHeigth += Textures.Gui.Basic.Border.CENTER.getHeigth();
                }
                else
                {
                    while (tDrawnWidth < (iWidth - 3))
                    {
                        if (iHeight - 8 < Textures.Gui.Basic.Border.CENTER.getHeigth())
                        {
                            int tWidthToRender = iWidth - 3 - tDrawnWidth;
                            if (tWidthToRender >= Textures.Gui.Basic.Border.CENTER.getWidth())
                                tWidthToRender = Textures.Gui.Basic.Border.CENTER.getWidth();

                            drawTexturedModalRect(tDrawnWidth, 3, Textures.Gui.Basic.Border.CENTER.getU(), Textures.Gui.Basic.Border.CENTER.getV(), tWidthToRender, tHeightToRender);
                            tDrawnWidth += tWidthToRender;
                        }
                        else
                        {
                            drawTexturedModalRect(tDrawnWidth, tDrawnHeigth, Textures.Gui.Basic.Border.CENTER.getU(), Textures.Gui.Basic.Border.CENTER.getV(), Textures.Gui.Basic.Border.CENTER.getWidth(), Textures.Gui.Basic.Border.CENTER.getHeigth());
                            tDrawnWidth += Textures.Gui.Basic.Border.CENTER.getWidth();
                        }
                    }
                    tDrawnWidth = 0;
                    tDrawnHeigth += tHeightToRender;
                }
            }
        }
    }

    //pCornerNumber: 0: leftTop, 1: rightTop, 2: rightBottom, 3: leftBottom.
    private void renderCornerOutwarts(int pCornerXPos, int pCornerYPos, int pCornerNumber)
    {
        GL11.glPushMatrix();

        GL11.glTranslatef(pCornerXPos, pCornerYPos, 0F);
        switch(pCornerNumber)
        {
            case 0:
                //NOOP
                break;
            case 1:
                GL11.glRotatef(270F, 0F, 0F, 1F);
                GL11.glTranslatef(-3F, -3F, 0F);
                break;
            case 2:
                GL11.glRotatef(90F, 0F, 0F, 1F);
                GL11.glTranslatef(-3F, 0F,0F);
                break;
            case 3:
                GL11.glRotatef(180F, 0F, 0F, 1F);
                GL11.glTranslatef(0F, -3F, 0F);
        }


        bindTexture(Textures.Gui.Basic.Border.OUTWARTSCORNER.getPrimaryLocation());
        drawTexturedModalRect(0, 0, Textures.Gui.Basic.Border.OUTWARTSCORNER.getU(), Textures.Gui.Basic.Border.OUTWARTSCORNER.getV(), Textures.Gui.Basic.Border.OUTWARTSCORNER.getWidth(), Textures.Gui.Basic.Border.OUTWARTSCORNER.getHeigth());

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
        drawTexturedModalRect(0,0,tResource.getU(), tResource.getV(), tResource.getWidth(), tResource.getHeigth());

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
        drawTexturedModalRect(pCornerXPos-3, pCornerYPos, tResource.getU(), tResource.getV(),6, tResource.getHeigth());

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

        if(iWidth - 6 <= tResource.getWidth())
        {
            drawTexturedModalRect(0, 0, tResource.getU(), tResource.getV(), tLength, tHeigth);
        }
        else
        {
            int tDrawnHeigth = 0;
            int tDrawnWidth = 0;
            if (iWidth - 6 <= tResource.getWidth())
            {
                drawTexturedModalRect(tDrawnWidth, tDrawnHeigth, tResource.getU(), tResource.getV(), iWidth - 6, tHeigth);
            }
            else
            {
                while (tDrawnWidth < (tLength))
                {
                    int tWidthToRender = tLength - tDrawnWidth;
                    if (tWidthToRender >= tResource.getWidth())
                        tWidthToRender = tResource.getWidth();

                    drawTexturedModalRect(tDrawnWidth, 0, tResource.getU(), tResource.getV(), tWidthToRender, tHeigth);
                    tDrawnWidth += tWidthToRender;
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
