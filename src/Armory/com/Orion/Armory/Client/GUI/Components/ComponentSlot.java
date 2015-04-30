package com.Orion.Armory.Client.GUI.Components;
/*
/  ComponentSlot
/  Created by : Orion
/  Created on : 26-4-2015
*/

import com.Orion.Armory.Client.GUI.ArmoryBaseGui;
import com.Orion.Armory.Client.GUI.Components.Core.AbstractGUIComponent;
import com.Orion.Armory.Util.Client.CustomResource;
import com.Orion.Armory.Util.Client.Textures;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class ComponentSlot extends AbstractGUIComponent
{
    private CustomResource iSlotResource;

    public ComponentSlot(ArmoryBaseGui pGui, String pInternalName, int pHeight, int pWidth, int pLeft, int pTop, CustomResource pSlotResource) {
        super(pGui, pInternalName, pHeight, pWidth, pLeft, pTop);

        iSlotResource = pSlotResource;
    }

    public ComponentSlot(ArmoryBaseGui pGui, String pInternalName, Slot pContainerSlot)
    {
        this(pGui, pInternalName, pContainerSlot, Textures.Gui.Basic.Slots.DEFAULT);
    }

    public ComponentSlot(ArmoryBaseGui pGui, String pInternalName, Slot pContainerSlot, CustomResource pSlotResource)
    {
        this(pGui, pInternalName, 18, 18, pContainerSlot.xDisplayPosition -1, pContainerSlot.yDisplayPosition -1, pSlotResource);
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
        GL11.glColor4f(iSlotResource.getColor().getColorRedFloat(), iSlotResource.getColor().getColorGreenFloat(), iSlotResource.getColor().getColorBlueFloat(), iSlotResource.getColor().getAlphaFloat());

        Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(iSlotResource.getPrimaryLocation()));
        drawTexturedModalRect(0, 0, iSlotResource.getDistanceToLeft(), iSlotResource.getDistanceToTop(), iWidth, iHeight);

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
