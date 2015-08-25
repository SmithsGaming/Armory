package com.Orion.Armory.Client.GUI.Components;
/*
/  ComponentSlot
/  Created by : Orion
/  Created on : 26-4-2015
*/

import com.Orion.Armory.Client.GUI.Components.Core.AbstractGUIComponent;
import com.Orion.Armory.Client.GUI.Components.Core.IComponentHost;
import com.Orion.Armory.Util.Client.Color.Color;
import com.Orion.Armory.Util.Client.Colors;
import com.Orion.Armory.Util.Client.CustomResource;
import com.Orion.Armory.Util.Client.GUI.GuiHelper;
import com.Orion.Armory.Util.Client.GUI.MultiComponentTexture;
import com.Orion.Armory.Util.Client.Textures;
import com.Orion.Armory.Util.Core.Coordinate;
import net.minecraft.inventory.Slot;
import org.lwjgl.opengl.GL11;

public class ComponentSlot extends AbstractGUIComponent
{
    private CustomResource iIconResource;
    private CustomResource iSlotResource;
    private Color iColor;
    private int iSlotID;

    public ComponentSlot(IComponentHost pHost, String pInternalName, int pSlotID, int pHeight, int pWidth, int pLeft, int pTop, CustomResource pSlotResource, Color pColor, CustomResource pIconResource) {
        super(pHost, pInternalName, pLeft, pTop, pWidth, pHeight);

        iSlotResource = pSlotResource;
        iColor = pColor;
        iSlotID = pSlotID;
        iIconResource = pIconResource;
    }

    public ComponentSlot(IComponentHost pHost, String pInternalName, int pSlotID, int pHeight, int pWidth, int pLeft, int pTop, CustomResource pSlotResource, Color pColor) {
        this(pHost, pInternalName, pSlotID, pHeight, pWidth, pLeft, pTop, pSlotResource, pColor, null);
    }

    public ComponentSlot(IComponentHost pHost, String pInternalName, Slot pContainerSlot)
    {
        this(pHost, pInternalName, pContainerSlot, Textures.Gui.Basic.Slots.DEFAULT);
    }

    public ComponentSlot(IComponentHost pHost, String pInternalName, Slot pContainerSlot, CustomResource pSlotResource)
    {
        this(pHost, pInternalName, pContainerSlot.getSlotIndex(), 18, 18, pContainerSlot.xDisplayPosition - 1, pContainerSlot.yDisplayPosition - 1, pSlotResource, Colors.DEFAULT);
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

        GuiHelper.drawRectangleStretched(new MultiComponentTexture(iSlotResource, iSlotResource.getWidth(), iSlotResource.getHeigth(), 1, 1), iWidth, iHeight, new Coordinate(iLeft, iTop, (int) this.zLevel));

        if ((iIconResource != null) && ((getComponentHost().getItemStackInSlot(iSlotID)) == null))
        {
            GuiHelper.bindTexture(iIconResource.getPrimaryLocation());
            drawTexturedModalRect(iLeft + 1, iTop + 1, 0,0, iWidth, iTop);
        }

        GL11.glColor4f(1F, 1F, 1F, 1F);
        GL11.glPopMatrix();
    }


    @Override
    public boolean handleMouseClicked(int pMouseX, int pMouseY, int pMouseButton) {
        return false;
    }
}
