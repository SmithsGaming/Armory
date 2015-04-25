package com.Orion.Armory.Client.GUI;
/*
/  ArmoryBaseGui
/  Created by : Orion
/  Created on : 15/01/2015
*/

import com.Orion.Armory.Client.GUI.Components.Core.ComponentManager;
import com.Orion.Armory.Client.GUI.Components.Ledgers.LedgerManager;
import com.Orion.Armory.Util.Client.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.inventory.Container;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Collections;

public abstract class ArmoryBaseGui extends GuiContainer {
    LedgerManager iLedgers = new LedgerManager(this);
    ComponentManager iComponents = new ComponentManager(this);
    ResourceLocation iBackGroundTexture;
    public int iDisplayHeight;
    public int iDisplayWidth;
    public int iGuiScale;

    public ArmoryBaseGui(Container pTargetedContainer) {
        super(pTargetedContainer);
        calcScaleFactor();
    }

    public void calcScaleFactor() {
        Minecraft mc = Minecraft.getMinecraft();
        ScaledResolution sc = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
        iDisplayWidth = sc.getScaledWidth();
        iDisplayHeight = sc.getScaledHeight();
        iGuiScale = sc.getScaleFactor();
    }

    public LedgerManager Ledgers() {
        return iLedgers;
    }

    public ComponentManager Components()
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
        this.mc.getTextureManager().bindTexture(iBackGroundTexture);
        this.drawTexturedModalRect(0, 0, 0, 0, this.xSize, this.ySize);

        iComponents.drawComponents();

        drawGuiContainerBackGroundFeatures(pFloat, pMouseX, pMouseY);

        GL11.glPopMatrix();
    }

    protected void drawGuiContainerBackGroundFeatures(float pFloat, int pMouseX, int pMouseY) {
        //NOOP by default
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        int mX = mouseX - guiLeft;
        int mY = mouseY - guiTop;

        super.mouseClicked(mouseX, mouseY, mouseButton);

        // / Handle ledger clicks
        if (iLedgers.handleMouseClicked(mouseX, mouseY, mouseButton)) {
            return;
        }
    }
}

