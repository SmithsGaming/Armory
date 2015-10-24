package com.SmithsModding.Armory.Client.GUI.Components;

import com.SmithsModding.Armory.Client.GUI.Components.Core.IComponentHost;
import com.SmithsModding.Armory.Client.GUI.Components.Core.IGUIComponent;
import com.SmithsModding.Armory.Client.GUI.Components.ToolTips.IToolTip;
import com.SmithsModding.Armory.Util.Client.Color.Color;
import com.SmithsModding.Armory.Util.Client.CustomResource;
import com.SmithsModding.Armory.Util.Core.Rectangle;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

/**
 * Created by Orion
 * Created on 11.05.2015
 * 15:59
 * <p/>
 * Copyrighted according to Project specific license
 */
public class ComponentTextbox extends GuiTextField implements IGUIComponent {
    String iInternalName;
    String iInputID;
    private IComponentHost iHost;
    private boolean iUseDefaultBackground = true;
    private CustomResource iBackGround;

    public ComponentTextbox(IComponentHost pHost, String pInternalName, FontRenderer pFontRenderer, int pX, int pY, int pWidth, int pHeight, String pInputID) {
        super(pFontRenderer, pX, pY, pWidth, pHeight);
        iHost = pHost;
        iInternalName = pInternalName;
        iInputID = pInputID;
    }

    public ComponentTextbox(IComponentHost pHost, String pInternalName, FontRenderer pFontRenderer, int pX, int pY, int pWidth, int pHeight, String pInputID, CustomResource pBackground) {
        super(pFontRenderer, pX, pY, pWidth, pHeight);
        iHost = pHost;
        iInternalName = pInternalName;

        setEnableBackgroundDrawing(false);

        iUseDefaultBackground = false;
        iBackGround = pBackground;
        iInputID = pInputID;
    }

    @Override
    public void onUpdate() {
        //NOOP
    }

    @Override
    public IComponentHost getComponentHost() {
        return iHost;
    }

    @Override
    public String getInternalName() {
        return iInternalName;
    }

    @Override
    public Rectangle getToolTipVisibileArea() {
        return new Rectangle(-1, 0, -1, 0, 0);
    }

    @Override
    public void setToolTipVisibleArea(Rectangle pNewArea) {
        return;
    }

    @Override
    public Rectangle getOccupiedArea() {
        return getToolTipVisibileArea();
    }

    @Override
    public ArrayList<IToolTip> getToolTipLines() {
        return null;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    @Override
    public void draw(int pX, int pY) {
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_BLEND);
        this.drawTextBox();
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_BLEND);

        if (!iUseDefaultBackground) {
            drawBackGround(pX, pY);
            drawForeGround(pX, pY);
        }

    }

    @Override
    public void drawForeGround(int pX, int pY) {
        //NOOP
    }

    @Override
    public void drawBackGround(int pX, int pY) {
        GL11.glPushMatrix();

        iBackGround.getColor().performGLColor();

        Color.resetGLColor();
    }

    @Override
    public boolean checkIfPointIsInComponent(int pTargetX, int pTargetY) {
        Rectangle tBox = new Rectangle(this.xPosition, 0, this.yPosition, this.width, this.height);
        return tBox.contains(pTargetX, pTargetY);
    }

    @Override
    public boolean handleMouseClicked(int pMouseX, int pMouseY, int pMouseButton) {
        this.mouseClicked(pMouseX, pMouseY, pMouseButton);
        return true;
    }

    @Override
    public boolean handleKeyTyped(char pKey, int pPara) {
        if (this.textboxKeyTyped(pKey, pPara)) {
            iHost.updateComponentResult(this, iInputID, this.getText());
            return true;
        }

        return false;
    }

    @Override
    public boolean requiresForcedInput() {
        return true;
    }
}
