package com.Orion.Armory.Client.GUI;
/*
/  ArmoryBaseGui
/  Created by : Orion
/  Created on : 15/01/2015
*/

import com.Orion.Armory.Client.GUI.Components.ComponentSlot;
import com.Orion.Armory.Client.GUI.Components.Core.StandardComponentManager;
import com.Orion.Armory.Client.GUI.Components.Ledgers.LedgerManager;
import com.Orion.Armory.Common.Inventory.ContainerArmory;
import com.Orion.Armory.Common.TileEntity.TileEntityArmory;
import com.Orion.Armory.Util.Client.GUI.GuiHelper;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import org.lwjgl.opengl.GL11;

import java.util.List;

public abstract class ArmoryBaseGui extends GuiContainer {
    LedgerManager iLedgers = new LedgerManager(this);
    StandardComponentManager iComponents = new StandardComponentManager(this);

    TileEntityArmory iBaseTE;

    @Override
    public void initGui() {
        super.initGui();

        GuiHelper.calcScaleFactor();
    }

    public ArmoryBaseGui(Container pTargetedContainer) {
        super(pTargetedContainer);

        iBaseTE = ((ContainerArmory) pTargetedContainer).iTargetTE;
    }

    public LedgerManager Ledgers() {
        return iLedgers;
    }

    public StandardComponentManager Components()
    {
        return iComponents;
    }

    public int getWidth() {
        return xSize;
    }

    public int getHeigth() {
        return ySize;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float pFloat, int pMouseX, int pMouseY) {
        //Render ledgers background at a lower level then the rest!
        GL11.glPushMatrix();
        iLedgers.drawLedgers();
        GL11.glPopMatrix();

        GL11.glPushMatrix();
        GL11.glTranslatef(guiLeft, guiTop, 0F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        iComponents.drawComponents();

        drawGuiContainerBackGroundFeatures(pFloat, pMouseX, pMouseY);

        iComponents.drawComponentToolTips(pMouseX, pMouseY);

        GL11.glPopMatrix();
    }

    protected void drawGuiContainerBackGroundFeatures(float pFloat, int pMouseX, int pMouseY) {
        //NOOP by default
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        GuiHelper.calcScaleFactor();

        int mX = (mouseX / GuiHelper.GUISCALE);
        int mY = (mouseY / GuiHelper.GUISCALE);

        super.mouseClicked(mouseX, mouseY, mouseButton);

        // / Handle ledger clicks
        if (iLedgers.handleMouseClicked(mouseX, mouseY, mouseButton)) {
            return;
        }

        iComponents.handleMouseClicked(mX, mY, mouseButton);
    }

    protected void keyTyped(char pKey, int pPara)
    {
        if (iLedgers.handleKeyTyped(pKey, pPara))
            return;

        if (iComponents.handleKeyTyped(pKey, pPara))
            return;

        super.keyTyped(pKey, pPara);
    }

    public float getProgressBarValue(String pProgressBarID)
    {
        return iBaseTE.getProgressBarValue(pProgressBarID);
    }

    public Object getComponentRelatedObject(String pComponentID)
    {
        return iBaseTE.getGUIComponentRelatedObject(pComponentID);
    }

    @Override
    public void drawHoveringText(List pToolTipLines, int pX, int pY, FontRenderer pFontRenderer)
    {
        super.drawHoveringText(pToolTipLines, pX, pY, pFontRenderer);
    }
}

