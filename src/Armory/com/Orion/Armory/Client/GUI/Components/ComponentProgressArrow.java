package com.Orion.Armory.Client.GUI.Components;

import com.Orion.Armory.Client.GUI.ArmoryBaseGui;
import com.Orion.Armory.Client.GUI.Components.Core.AbstractGUIComponent;
import com.Orion.Armory.Util.Client.Color;
import com.Orion.Armory.Util.Client.Colors;
import com.Orion.Armory.Util.Client.Textures;
import com.Orion.Armory.Util.References;
import org.lwjgl.opengl.GL11;

/**
 * Created by Orion
 * Created on 03.05.2015
 * 11:40
 * <p/>
 * Copyrighted according to Project specific license
 */
public class ComponentProgressArrow extends AbstractGUIComponent
{
    int iCompletePartToBeRendered = 0;
    Color iForeGroundColor = Colors.DEFAULT;
    Color iBackGroundColor = Colors.DEFAULT;

    public ComponentProgressArrow(ArmoryBaseGui pGui, String pInternalName, int pLeft, int pTop, Color pForeground, Color pBackground) {
        super(pGui, pInternalName, pLeft, pTop, Textures.Gui.Basic.Components.ARROWEMPTY.getWidth(), Textures.Gui.Basic.Components.ARROWEMPTY.getHeigth());

        iForeGroundColor = pForeground;
        iBackGroundColor = pBackground;
    }

    @Override
    public void onUpdate() {
        iCompletePartToBeRendered = (int) iGui.getProgressBarValue(References.InternalNames.GUIComponents.Anvil.CRAFTINGPROGRESS) * Textures.Gui.Basic.Components.ARROWFULL.getWidth();
    }

    @Override
    public void drawForeGround(int pX, int pY) {
        //NOOP
    }

    @Override
    public void drawBackGround(int pX, int pY) {
        GL11.glPushMatrix();
        GL11.glColor4f(iBackGroundColor.getColorRedFloat(), iBackGroundColor.getColorGreenFloat(), iBackGroundColor.getColorBlueFloat(), iBackGroundColor.getAlphaFloat());

        bindTexture(Textures.Gui.Basic.Components.ARROWEMPTY.getPrimaryLocation());
        drawTexturedModalRect(iLeft, iTop, Textures.Gui.Basic.Components.ARROWEMPTY.getU(), Textures.Gui.Basic.Components.ARROWEMPTY.getV(), Textures.Gui.Basic.Components.ARROWEMPTY.getWidth(), Textures.Gui.Basic.Components.ARROWEMPTY.getHeigth());

        GL11.glColor4f(1F, 1F, 1F, 1F);
        GL11.glPopMatrix();

        GL11.glPushMatrix();
        GL11.glColor4f(iForeGroundColor.getColorRedFloat(), iForeGroundColor.getColorGreenFloat(), iForeGroundColor.getColorBlueFloat(), iForeGroundColor.getAlphaFloat());

        bindTexture(Textures.Gui.Basic.Components.ARROWFULL.getPrimaryLocation());
        drawTexturedModalRect(iLeft, iTop, Textures.Gui.Basic.Components.ARROWFULL.getU(), Textures.Gui.Basic.Components.ARROWFULL.getV(), iCompletePartToBeRendered, Textures.Gui.Basic.Components.ARROWFULL.getHeigth());

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
