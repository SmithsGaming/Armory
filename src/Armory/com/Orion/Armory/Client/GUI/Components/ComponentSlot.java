package com.Orion.Armory.Client.GUI.Components;
/*
/  ComponentSlot
/  Created by : Orion
/  Created on : 26-4-2015
*/

import com.Orion.Armory.Client.GUI.ArmoryBaseGui;
import com.Orion.Armory.Client.GUI.Components.Core.AbstractGUIComponent;
import com.Orion.Armory.Util.Client.Color;
import com.Orion.Armory.Util.Client.Colors;
import com.Orion.Armory.Util.Client.CustomResource;
import com.Orion.Armory.Util.Client.Textures;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class ComponentSlot extends AbstractGUIComponent
{
    private CustomResource iSlotResource;
    private Color iColor;

    public ComponentSlot(ArmoryBaseGui pGui, String pInternalName, int pHeight, int pWidth, int pLeft, int pTop, CustomResource pSlotResource, Color pColor) {
        super(pGui, pInternalName, pLeft, pTop, pWidth, pHeight);

        iSlotResource = pSlotResource;
        iColor = pColor;
    }

    public ComponentSlot(ArmoryBaseGui pGui, String pInternalName, Slot pContainerSlot)
    {
        this(pGui, pInternalName, pContainerSlot, Textures.Gui.Basic.Slots.DEFAULT);
    }

    public ComponentSlot(ArmoryBaseGui pGui, String pInternalName, Slot pContainerSlot, CustomResource pSlotResource)
    {
        this(pGui, pInternalName, 18, 18, pContainerSlot.xDisplayPosition -1, pContainerSlot.yDisplayPosition -1, pSlotResource, Colors.DEFAULT);
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
        GL11.glTranslatef(iLeft, iTop, 0F);
        GL11.glColor4f(iColor.getColorRedFloat(), iColor.getColorGreenFloat(), iColor.getColorBlueFloat(), iColor.getAlphaFloat());

        Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(iSlotResource.getPrimaryLocation()));
        drawTexturedModalRect(0,0, iSlotResource.getU(), iSlotResource.getV(), 1, 1);

        int tWidthRepeated = iWidth / iSlotResource.getWidth();
        int tWidthLeftAfterRepeating = iWidth % iSlotResource.getWidth();

        int tHeightRepeated = iHeight / iSlotResource.getHeigth();
        int tHeightLeftAfterRepeating = iHeight % Textures.Gui.Basic.Components.TANKGAUGE.getHeigth();






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
}
