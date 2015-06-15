package com.Orion.Armory.Client.GUI.Components;

import com.Orion.Armory.Client.GUI.ArmoryBaseGui;
import com.Orion.Armory.Client.GUI.Components.Core.AbstractGUIComponent;
import com.Orion.Armory.Util.Client.Color.Color;
import com.Orion.Armory.Util.Client.Colors;
import com.Orion.Armory.Util.Client.GUI.GuiDirection;
import com.Orion.Armory.Util.Client.GUI.GuiHelper;
import com.Orion.Armory.Util.Client.GUI.TextureComponent;
import com.Orion.Armory.Util.Client.GUI.UIRotation;
import com.Orion.Armory.Util.Client.Textures;
import com.Orion.Armory.Util.Core.Coordinate;
import com.Orion.Armory.Util.Core.Rectangle;
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.opengl.GL11;

/**
 * Created by Orion
 * Created on 06.05.2015
 * 16:29
 * <p/>
 * Copyrighted according to Project specific license
 */
public class ComponentFluidTank extends AbstractGUIComponent
{
    Color iColor;
    FluidStack iFluidStack;
    float iMaxCapacity;
    GuiDirection iDirection;

    public ComponentFluidTank(ArmoryBaseGui pGui, String pInternalName, int pLeft, int pTop, int pWidth, int pHeight, Color pColor, int pMaxCapacity, GuiDirection pDirection) {
        super(pGui, pInternalName, pLeft, pTop, pWidth, pHeight);

        iColor = pColor;
        iFluidStack = (FluidStack) pGui.getComponentRelatedObject(pInternalName);
        iMaxCapacity = pMaxCapacity;
        iDirection = pDirection;
    }

    @Override
    public void onUpdate() {

    }

    @Override
    public void drawForeGround(int pX, int pY) {
        int tBlockDistance = (iWidth - 2) / 10;

        if (iDirection == GuiDirection.HORIZONTAL)
        {
            for(int tIter = tBlockDistance; tIter < (iWidth - 2); tIter += tBlockDistance)
            {
                int tHeight = (int) (iHeight * 0.3);
                if (tIter == 5 * tBlockDistance)
                {
                    tHeight *= 2;
                }

                GuiHelper.drawColoredRect(new Rectangle(iLeft + tIter, iTop + 1, 1, tHeight), Colors.General.RED.getColor());
            }
        }
        else
        {
            for(int tIter = tBlockDistance; tIter < (iWidth - 2); tIter += tBlockDistance)
            {
                int tWidth = (int) (iWidth * 0.3);
                if (tIter == 5 * tBlockDistance)
                {
                    tWidth *= 2;
                }

                GuiHelper.drawColoredRect(new Rectangle(iLeft + 1, iTop + tIter, tWidth, 1), Colors.General.RED.getColor());
            }
        }
    }

    @Override
    public void drawBackGround(int pX, int pY) {
        GL11.glPushMatrix();
        GL11.glColor4f(iColor.getColorRedFloat(), iColor.getColorGreenFloat(), iColor.getColorBlueFloat(), iColor.getAlphaFloat());

        TextureComponent tCenterComponent = new TextureComponent(Textures.Gui.Basic.Slots.DEFAULT.getPrimaryLocation(), 1, 1, 16, 16, new UIRotation(false, false, false, 0), new Coordinate(0,0,0));

        TextureComponent[] tCornerComponents = new TextureComponent[4];
        tCornerComponents[0] = new TextureComponent(Textures.Gui.Basic.Slots.DEFAULT.getPrimaryLocation(), 0, 0, 1, 1, new UIRotation(false, false, false, 0), new Coordinate(0,0,0));
        tCornerComponents[1] = new TextureComponent(Textures.Gui.Basic.Slots.DEFAULT.getPrimaryLocation(), 17, 0, 1, 1, new UIRotation(false, false, true, 90), new Coordinate(0,0,0));
        tCornerComponents[2] = new TextureComponent(Textures.Gui.Basic.Slots.DEFAULT.getPrimaryLocation(), 17, 17, 1, 1, new UIRotation(false, false, true, 180), new Coordinate(0,0,0));
        tCornerComponents[3] = new TextureComponent(Textures.Gui.Basic.Slots.DEFAULT.getPrimaryLocation(), 0, 17, 1, 1, new UIRotation(false, false, true, 270), new Coordinate(0,0,0));

        TextureComponent[] tSideComponents = new TextureComponent[4];
        tSideComponents[0] = new TextureComponent(Textures.Gui.Basic.Slots.DEFAULT.getPrimaryLocation(), 1, 0, 16, 1, new UIRotation(false, false, false, 0), new Coordinate(0,0,0));
        tSideComponents[1] = new TextureComponent(Textures.Gui.Basic.Slots.DEFAULT.getPrimaryLocation(), 1, 17, 1, 16, new UIRotation(false, false, true, -90), new Coordinate(15,0,0));
        tSideComponents[2] = new TextureComponent(Textures.Gui.Basic.Slots.DEFAULT.getPrimaryLocation(), 1, 17, 1, 16, new UIRotation(false, false, false, 0), new Coordinate(0,15,0));
        tSideComponents[3] = new TextureComponent(Textures.Gui.Basic.Slots.DEFAULT.getPrimaryLocation(), 1, 0, 16, 1, new UIRotation(false, false, true, -90), new Coordinate(0,0,0));

        GuiHelper.drawRectangleStretched(tCenterComponent, tSideComponents, tCornerComponents, iWidth, iHeight, new Coordinate(iLeft,iTop, (int) this.zLevel));

        GL11.glColor4f(1F, 1F, 1F, 1F);
        GL11.glPopMatrix();

        if (iFluidStack  == null)
            return;

        GL11.glPushMatrix();
        Rectangle tScissorBox = null;
        if (iDirection == GuiDirection.HORIZONTAL)
            tScissorBox = new Rectangle(pX + iLeft + 1, pY + iTop + iHeight -1,(int) ((iWidth - 2) * (iFluidStack.amount / iMaxCapacity)), iHeight - 2);

        if (iDirection == GuiDirection.VERTICAL)
            tScissorBox = new Rectangle(pX + iLeft + 1, pY + iTop + iHeight -1, (iWidth - 2),(int) ((iHeight - 2) * (iFluidStack.amount / iMaxCapacity)));

        GuiHelper.enableScissor(tScissorBox);
        GuiHelper.drawFluid(iFluidStack, iLeft + 1, iTop + 1, (int) zLevel, iWidth - 2, iHeight);
        GuiHelper.disableScissor();

        GL11.glPopMatrix();
    }

    @Override
    public void drawToolTips(int pMouseX, int pMouseY) {

    }

    @Override
    public boolean handleMouseClicked(int pMouseX, int pMouseY, int pMouseButton) {
        return false;
    }
}
