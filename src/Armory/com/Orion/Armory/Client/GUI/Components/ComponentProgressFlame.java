package com.Orion.Armory.Client.GUI.Components;

import com.Orion.Armory.Client.GUI.ArmoryBaseGui;
import com.Orion.Armory.Util.Client.Color.Color;
import com.Orion.Armory.Util.Client.GUI.GuiHelper;
import com.Orion.Armory.Util.Client.Textures;
import org.lwjgl.opengl.GL11;

/**
 * Created by Orion
 * Created on 02.06.2015
 * 01:05
 * <p/>
 * Copyrighted according to Project specific license
 */
public class ComponentProgressFlame extends ComponentProgressBar {

    public ComponentProgressFlame(ArmoryBaseGui pGui, String pInternalName, int pLeft, int pTop, Color pBackgroundColor, Color pForegroundColor) {
        super(pGui, pInternalName, pLeft, pTop, Textures.Gui.Basic.Components.FLAMEEMPTY, Textures.Gui.Basic.Components.FLAMEFULL, pBackgroundColor, pForegroundColor);
    }

    @Override
    public void onUpdate() {
        iCompletePartToBeRendered = (int) (iGui.getProgressBarValue(this.getInternalName()) * iBackground.getHeigth());
    }

    @Override
    public void drawBackGround(int pX, int pY) {
        GL11.glPushMatrix();
        GL11.glColor4f(iBackGroundColor.getColorRedFloat(), iBackGroundColor.getColorGreenFloat(), iBackGroundColor.getColorBlueFloat(), iBackGroundColor.getAlphaFloat());

        GuiHelper.bindTexture(iBackground.getPrimaryLocation());
        drawTexturedModalRect(iLeft, iTop, iBackground.getU(), iBackground.getV(), iBackground.getWidth(), iBackground.getHeigth());

        GL11.glColor4f(1F, 1F, 1F, 1F);
        GL11.glPopMatrix();

        if (iCompletePartToBeRendered == -1 * iBackground.getHeigth())
            return;

        GL11.glPushMatrix();
        GL11.glColor4f(iForeGroundColor.getColorRedFloat(), iForeGroundColor.getColorGreenFloat(), iForeGroundColor.getColorBlueFloat(), iForeGroundColor.getAlphaFloat());

        GuiHelper.bindTexture(iForeground.getPrimaryLocation());
        drawTexturedModalRect(iLeft, iTop + iCompletePartToBeRendered, iForeground.getU(), iForeground.getV() + iCompletePartToBeRendered, iForeground.getWidth(), iForeground.getHeigth() - iCompletePartToBeRendered);

        GL11.glColor4f(1F, 1F, 1F, 1F);
        GL11.glPopMatrix();
    }
}
