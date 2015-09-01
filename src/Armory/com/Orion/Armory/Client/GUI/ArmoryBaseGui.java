package com.Orion.Armory.Client.GUI;
/*
/  ArmoryBaseGui
/  Created by : Orion
/  Created on : 15/01/2015
*/

import com.Orion.Armory.Client.GUI.Components.Core.IGUIComponent;
import com.Orion.Armory.Client.GUI.Components.Core.StandardComponentManager;
import com.Orion.Armory.Client.GUI.Components.Ledgers.ILedgerHost;
import com.Orion.Armory.Client.GUI.Components.Ledgers.LedgerManager;
import com.Orion.Armory.Common.Inventory.ContainerArmory;
import com.Orion.Armory.Common.TileEntity.Core.TileEntityArmory;
import com.Orion.Armory.Util.Client.GUI.GuiHelper;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

import java.util.List;

public abstract class ArmoryBaseGui extends GuiContainer implements ILedgerHost {
    protected StandardComponentManager iComponents = new StandardComponentManager(this);
    LedgerManager iLedgers = new LedgerManager(this);
    TileEntityArmory iBaseTE;

    public ArmoryBaseGui(Container pTargetedContainer) {
        super(pTargetedContainer);

        iBaseTE = ((ContainerArmory) pTargetedContainer).iTargetTE;
    }

    @Override
    public void initGui() {
        super.initGui();

        GuiHelper.calcScaleFactor();
    }

    @Override
    public LedgerManager getLedgerManager() {
        return iLedgers;
    }

    public StandardComponentManager Components()
    {
        return iComponents;
    }

    public int getWidth() {
        return xSize;
    }

    @Override
    public int getXOrigin() {
        return guiLeft;
    }

    @Override
    public int getYOrigin() {
        return guiTop;
    }

    @Override
    public int getXSize() {
        return this.xSize;
    }

    @Override
    public void updateComponentResult(IGUIComponent pComponent, String pComponentID, String pNewValue) {
        getContainer().updateComponentResult(pComponent, pComponentID, pNewValue);
    }

    @Override
    public int getYSize() {
        return this.ySize;
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

        iComponents.drawComponentToolTips(pMouseX - guiLeft, pMouseY - guiTop);

        GL11.glPopMatrix();
    }

    protected void drawGuiContainerBackGroundFeatures(float pFloat, int pMouseX, int pMouseY) {
        //NOOP by default
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        GuiHelper.calcScaleFactor();

        super.mouseClicked(mouseX, mouseY, mouseButton);

        // / Handle ledger clicks
        if (iLedgers.handleMouseClicked(mouseX, mouseY, mouseButton)) {
            return;
        }

        mouseX -= guiLeft;
        mouseY -= guiTop;

        iComponents.handleMouseClicked(mouseX, mouseY, mouseButton);
    }

    protected void keyTyped(char pKey, int pPara)
    {
        if (iLedgers.handleKeyTyped(pKey, pPara))
            return;

        if (iComponents.handleKeyTyped(pKey, pPara))
            return;

        super.keyTyped(pKey, pPara);
    }

    @Override
    public float getProgressBarValue(String pProgressBarID)
    {
        return iBaseTE.getProgressBarValue(pProgressBarID);
    }

    @Override
    public ContainerArmory getContainer() {
        return (ContainerArmory) inventorySlots;
    }

    @Override
    public Object getComponentRelatedObject(String pComponentID)
    {
        return iBaseTE.getGUIComponentRelatedObject(pComponentID);
    }

    @Override
    public void drawHoveringText(List pToolTipLines, int pX, int pY, FontRenderer pFontRenderer)
    {
        super.drawHoveringText(pToolTipLines, pX, pY, pFontRenderer);
    }

    @Override
    public ItemStack getItemStackInSlot(int pSlotIndex) {
        if (iBaseTE instanceof IInventory)
            return ((IInventory) iBaseTE).getStackInSlot(pSlotIndex);

        return null;
    }
}

