package com.Orion.Armory.Client.GUI.Components;
/*
/  ComponentSlot
/  Created by : Orion
/  Created on : 26-4-2015
*/

import com.Orion.Armory.Client.GUI.ArmoryBaseGui;
import com.Orion.Armory.Client.GUI.Components.Core.AbstractGUIComponent;
import com.Orion.Armory.Common.Inventory.ContainerArmory;
import com.Orion.Armory.Common.TileEntity.TileEntityArmory;
import com.Orion.Armory.Util.Client.*;
import com.Orion.Armory.Util.Client.GUI.GuiHelper;
import com.Orion.Armory.Util.Client.GUI.MultiComponentTexture;
import com.Orion.Armory.Util.Client.GUI.TextureComponent;
import com.Orion.Armory.Util.Client.GUI.UIRotation;
import com.Orion.Armory.Util.Core.Coordinate;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

public class ComponentSlot extends AbstractGUIComponent
{
    private CustomResource iIconResource;
    private CustomResource iSlotResource;
    private Color iColor;
    private int iSlotID;

    public ComponentSlot(ArmoryBaseGui pGui, String pInternalName, int pSlotID, int pHeight, int pWidth, int pLeft, int pTop, CustomResource pSlotResource, Color pColor, CustomResource pIconResource) {
        super(pGui, pInternalName, pLeft, pTop, pWidth, pHeight);

        iSlotResource = pSlotResource;
        iColor = pColor;
        iSlotID = pSlotID;
        iIconResource = pIconResource;
    }

    public ComponentSlot(ArmoryBaseGui pGui, String pInternalName, int pSlotID, int pHeight, int pWidth, int pLeft, int pTop, CustomResource pSlotResource, Color pColor) {
        this(pGui, pInternalName, pSlotID, pHeight, pWidth, pLeft, pTop, pSlotResource, pColor, null);
    }

    public ComponentSlot(ArmoryBaseGui pGui, String pInternalName, Slot pContainerSlot)
    {
        this(pGui, pInternalName, pContainerSlot, Textures.Gui.Basic.Slots.DEFAULT);
    }

    public ComponentSlot(ArmoryBaseGui pGui, String pInternalName, Slot pContainerSlot, CustomResource pSlotResource)
    {
        this(pGui, pInternalName, pContainerSlot.getSlotIndex(), 18, 18, pContainerSlot.xDisplayPosition -1, pContainerSlot.yDisplayPosition -1, pSlotResource, Colors.DEFAULT);
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

        if ((iIconResource != null) && (((IInventory) ((ContainerArmory) iGui.inventorySlots).iTargetTE).getStackInSlot(iSlotID) == null))
        {
            GuiHelper.bindTexture(iIconResource.getPrimaryLocation());
            drawTexturedModalRect(iLeft + 1, iTop + 1, 0,0, iWidth, iTop);
        }

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
