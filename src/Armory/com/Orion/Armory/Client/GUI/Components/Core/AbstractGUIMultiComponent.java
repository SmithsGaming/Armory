package com.Orion.Armory.Client.GUI.Components.Core;
/*
/  AbstractGUIMultiComponent
/  Created by : Orion
/  Created on : 27-4-2015
*/

import com.Orion.Armory.Client.GUI.Components.ToolTips.IToolTip;
import com.Orion.Armory.Util.Core.Rectangle;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

public abstract class AbstractGUIMultiComponent implements IComponentManager, IGUIComponent
{
    public int iHeight = 0;
    public int iWidth = 0;
    public int iLeft = 0;
    public int iTop = 0;
    protected IComponentHost iHost;
    protected StandardComponentManager iSubComponents;
    String iInternalName;

    public AbstractGUIMultiComponent(IComponentHost pHost, String pInternalName, int pLeft, int pTop, int pWidth, int pHeigth) {
        iSubComponents = new StandardComponentManager(pHost);

        iHost = pHost;

        iLeft = pLeft;
        iTop = pTop;

        iWidth = pWidth;
        iHeight = pHeigth;

        iInternalName = pInternalName;
    }

    @Override
    public Rectangle getToolTipVisibileArea() {
        return new Rectangle(-1,0,-1,1,1);
    }

    @Override
    public void setToolTipVisibleArea(Rectangle pNewArea) {
        return;
    }

    @Override
    public Rectangle getOccupiedArea() {
        return new Rectangle(-1,0,-1,1,1);
    }

    @Override
    public ArrayList<IToolTip> getToolTipLines() {
        return new ArrayList<IToolTip>();
    }

    @Override
    public IComponentHost getComponentHost() {
        return iHost;
    }

    public String getInternalName() {
        return iInternalName;
    }


    @Override
    public ArrayList<IGUIComponent> getComponents() {
        return iSubComponents.getComponents();
    }

    @Override
    public void addComponent(IGUIComponent pNewComponent) {
        iSubComponents.addComponent(pNewComponent);
    }

    @Override
    public IGUIComponent getComponentAt(int pTargetX, int pTargetY) {
        pTargetX -= iLeft;
        pTargetY -=iTop;

        return iSubComponents.getComponentAt(pTargetX, pTargetY);
    }

    @Override
    public void drawComponents() {
        iSubComponents.drawComponents();
    }

    @Override
    public boolean drawComponentToolTips(int pMouseX, int pMouseY) {
       boolean tResult;

        GL11.glPushMatrix();
        GL11.glTranslatef(iLeft, iTop, 0F);
        tResult = iSubComponents.drawComponentToolTips(pMouseX - iLeft, pMouseY - iTop);
        GL11.glPopMatrix();

        return tResult;
    }

    @Override
    public void onUpdate() {
        iSubComponents.onUpdate();
    }

    @Override
    public int getHeight() {
        return iHeight;
    }

    @Override
    public int getWidth() {
        return iWidth;
    }

    @Override
    public void draw(int pX, int pY) {
        GL11.glPushMatrix();
        GL11.glTranslatef(iLeft, iTop, 0F);

        iSubComponents.drawComponents();

        GL11.glPopMatrix();
    }

    @Override
    public void drawForeGround(int pX, int pY) {
        //NOOP
    }

    @Override
    public void drawBackGround(int pX, int pY) {
        //NOOP
    }

    @Override
    public boolean checkIfPointIsInComponent(int pTargetX, int pTargetY) {
        Rectangle tComponentBounds = new Rectangle(iLeft, 0, iTop, iWidth, iHeight);

        return tComponentBounds.contains(pTargetX, pTargetY);
    }

    @Override
    public abstract boolean handleMouseClicked(int pMouseX, int pMouseY, int pMouseButton);

    public boolean handleKeyTyped(char pKey, int pPara)
    {
        return false;
    }

    public boolean requiresForcedInput()
    {
        return false;
    }
}
