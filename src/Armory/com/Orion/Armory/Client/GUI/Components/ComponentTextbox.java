package com.Orion.Armory.Client.GUI.Components;

import com.Orion.Armory.Client.GUI.ArmoryBaseGui;
import com.Orion.Armory.Client.GUI.Components.Core.IGUIComponent;
import com.Orion.Armory.Common.Inventory.ContainerArmory;
import com.Orion.Armory.Util.Client.Color.Color;
import com.Orion.Armory.Util.Client.CustomResource;
import com.Orion.Armory.Util.Core.Rectangle;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.opengl.GL11;

/**
 * Created by Orion
 * Created on 11.05.2015
 * 15:59
 * <p/>
 * Copyrighted according to Project specific license
 */
public class ComponentTextbox extends GuiTextField implements IGUIComponent
{
    private ArmoryBaseGui iGui;
    String iInternalName;

    private boolean iUseDefaultBackground = true;
    private CustomResource iBackGround;

    public ComponentTextbox(ArmoryBaseGui pGui, String pInternalName, FontRenderer pFontRenderer, int pX, int pY, int pWidth, int pHeight) {
        super(pFontRenderer, pX, pY, pWidth, pHeight);
        iGui = pGui;
        iInternalName = pInternalName;
    }

    public ComponentTextbox(ArmoryBaseGui pGui, String pInternalName, FontRenderer pFontRenderer, int pX, int pY, int pWidth, int pHeight, CustomResource pBackground) {
        super(pFontRenderer, pX, pY, pWidth, pHeight);
        iGui = pGui;
        iInternalName = pInternalName;

        setEnableBackgroundDrawing(false);

        iUseDefaultBackground = false;
        iBackGround = pBackground;
    }

    @Override
    public void onUpdate() {
        //NOOP
    }

    @Override
    public String getInternalName() {
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

        if (!iUseDefaultBackground)
        {
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
    public void drawToolTips(int pMouseX, int pMouseY) {
        //NOOP
    }

    @Override
    public boolean checkIfPointIsInComponent(int pTargetX, int pTargetY) {
        Rectangle tBox = new Rectangle(this.xPosition, this.yPosition, this.width, this.height);
        return tBox.contains(pTargetX, pTargetY);
    }

    @Override
    public boolean handleMouseClicked(int pMouseX, int pMouseY, int pMouseButton) {
        this.mouseClicked(pMouseX, pMouseY, pMouseButton);
        return true;
    }

    @Override
    public boolean handleKeyTyped(char pKey, int pPara) {
        if (this.textboxKeyTyped(pKey, pPara))
        {
            ((ContainerArmory) iGui.inventorySlots).updateComponentResult(iInternalName, this.getText());
            return true;
        }

        return false;
    }

    @Override
    public boolean requiresForcedInput() {
        return true;
    }
}
