package com.Orion.Armory.Client.GUI.Components;
/*
/  ComponentSlot
/  Created by : Orion
/  Created on : 26-4-2015
*/

import com.Orion.Armory.Client.GUI.ArmoryBaseGui;
import com.Orion.Armory.Client.GUI.Components.Core.AbstractGUIComponent;
import com.Orion.Armory.Util.Client.*;
import com.Orion.Armory.Util.Core.Coordinate;
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
        GL11.glColor4f(iColor.getColorRedFloat(), iColor.getColorGreenFloat(), iColor.getColorBlueFloat(), iColor.getAlphaFloat());

        Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(iSlotResource.getPrimaryLocation()));
       TextureComponent tCenterComponent = new TextureComponent(Textures.Gui.Basic.Slots.DEFAULT.getPrimaryLocation(), 1, 1, 16, 16, new UIRotation(false, false, false, 0), new Coordinate(0,0,0));
        
        TextureComponent[] tCornerComponents = new TextureComponent[4];
        tCornerComponents[0] = new TextureComponent(Textures.Gui.Basic.Slots.DEFAULT.getPrimaryLocation(), 0, 0, 1, 1, new UIRotation(false, false, false, 0), new Coordinate(0,0,0));
        tCornerComponents[1] = new TextureComponent(Textures.Gui.Basic.Slots.DEFAULT.getPrimaryLocation(), 17, 0, 1, 1, new UIRotation(false, false, false, 0), new Coordinate(0,0,0));
        tCornerComponents[2] = new TextureComponent(Textures.Gui.Basic.Slots.DEFAULT.getPrimaryLocation(), 17, 17, 1, 1, new UIRotation(false, false, false, 0), new Coordinate(0,0,0));
        tCornerComponents[3] = new TextureComponent(Textures.Gui.Basic.Slots.DEFAULT.getPrimaryLocation(), 0, 17, 1, 1, new UIRotation(false, false, false, 0), new Coordinate(0,0,0));

        TextureComponent[] tSideComponents = new TextureComponent[4];
        tSideComponents[0] = new TextureComponent(Textures.Gui.Basic.Slots.DEFAULT.getPrimaryLocation(), 1, 0, 16, 1, new UIRotation(false, false, false, 0), new Coordinate(0,0,0));
        tSideComponents[1] = new TextureComponent(Textures.Gui.Basic.Slots.DEFAULT.getPrimaryLocation(), 17, 1, 1, 16, new UIRotation(false, false, true, -90), new Coordinate(0,0,0));
        tSideComponents[2] = new TextureComponent(Textures.Gui.Basic.Slots.DEFAULT.getPrimaryLocation(), 1, 17, 1, 16, new UIRotation(false, false, false, 0), new Coordinate(0,0,0));
        tSideComponents[3] = new TextureComponent(Textures.Gui.Basic.Slots.DEFAULT.getPrimaryLocation(), 1, 0, 16, 1, new UIRotation(false, false, true, -90), new Coordinate(0,0,0));

        GuiHelper.drawRectangleStretched(tCenterComponent, tSideComponents, tCornerComponents, iWidth, iHeight, new Coordinate(iLeft, iTop,(int) this.zLevel));

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
