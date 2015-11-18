package com.SmithsModding.Armory.Client.GUI.Components;
/*
/  ComponentSlot
/  Created by : Orion
/  Created on : 26-4-2015
*/

import com.SmithsModding.Armory.Client.GUI.Components.Core.AbstractGUIComponent;
import com.SmithsModding.Armory.Client.GUI.Components.Core.IComponentHost;
import com.SmithsModding.Armory.Util.Client.Color.Color;
import com.SmithsModding.Armory.Util.Client.Colors;
import com.SmithsModding.Armory.Util.Client.CustomResource;
import com.SmithsModding.Armory.Util.Client.GUI.GuiHelper;
import com.SmithsModding.Armory.Util.Client.GUI.MultiComponentTexture;
import com.SmithsModding.Armory.Util.Client.Textures;
import com.SmithsModding.Armory.Util.Core.Coordinate;
import com.SmithsModding.Armory.Util.Core.ItemStackHelper;
import com.SmithsModding.Armory.Util.References;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class ComponentSlot extends AbstractGUIComponent {
    private ItemStack iDisplayedItemStack;
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

    public ComponentSlot(IComponentHost pHost, String pInternalName, Slot pContainerSlot) {
        this(pHost, pInternalName, pContainerSlot, Textures.Gui.Basic.Slots.DEFAULT);
    }

    public ComponentSlot(IComponentHost pHost, String pInternalName, Slot pContainerSlot, CustomResource pSlotResource) {
        this(pHost, pInternalName, pContainerSlot.getSlotIndex(), 18, 18, pContainerSlot.xDisplayPosition - 1, pContainerSlot.yDisplayPosition - 1, pSlotResource, Colors.DEFAULT);
    }

    @Override
    public void onUpdate() {
        ItemStack tContainerStack = iHost.getItemStackInSlot(iSlotID);
        if (!ItemStackHelper.equals(tContainerStack, iDisplayedItemStack)) {
            iHost.updateComponentResult(this, References.InternalNames.InputHandlers.Components.SLOTCHANGED, String.valueOf(iSlotID));
            iDisplayedItemStack = tContainerStack;
        }
    }

    @Override
    public void drawForeGround(int pX, int pY) {
        //NOOP
    }

    @Override
    public void drawBackGround(int pX, int pY) {
        GL11.glPushMatrix();
        GL11.glColor4f(iColor.getColorRedFloat(), iColor.getColorGreenFloat(), iColor.getColorBlueFloat(), iColor.getAlphaFloat());

        GuiHelper.drawRectangleStretched(new MultiComponentTexture(iSlotResource, iSlotResource.getWidth(), iSlotResource.getHeight(), 1, 1), iWidth, iHeight, new Coordinate(iLeft, iTop, (int) this.zLevel));

        if ((iIconResource != null) && ((getComponentHost().getItemStackInSlot(iSlotID)) == null)) {
            GuiHelper.bindTexture(iIconResource.getPrimaryLocation());
            drawTexturedModalRect(iLeft + 1, iTop + 1, iIconResource.getU(), iIconResource.getV(), iIconResource.getWidth(), iIconResource.getHeight());
        }

        GL11.glColor4f(1F, 1F, 1F, 1F);
        GL11.glPopMatrix();
    }


    @Override
    public boolean handleMouseClicked(int pMouseX, int pMouseY, int pMouseButton) {
        return false;
    }
}