package com.Orion.Armory.Client.GUI.Components;

import com.Orion.Armory.Client.GUI.Components.Core.AbstractGUIComponent;
import com.Orion.Armory.Client.GUI.Components.Core.IComponentHost;
import com.Orion.Armory.Client.GUI.Components.ToolTips.IToolTip;
import com.Orion.Armory.Client.GUI.Components.ToolTips.StandardToolTip;
import com.Orion.Armory.Util.Client.Color.Color;
import com.Orion.Armory.Util.Client.Colors;
import com.Orion.Armory.Util.Client.CustomResource;
import com.Orion.Armory.Util.Client.GUI.GuiHelper;
import com.Orion.Armory.Util.Client.Textures;
import com.Orion.Armory.Util.Client.TranslationKeys;
import com.Orion.Armory.Util.Core.Rectangle;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

/**
 * Created by Orion
 * Created on 03.05.2015
 * 11:40
 * <p/>
 * Copyrighted according to Project specific license
 */
public class ComponentProgressBar extends AbstractGUIComponent
{
    int iCompletePartToBeRendered = 0;
    Color iForeGroundColor = Colors.DEFAULT;
    Color iBackGroundColor = Colors.DEFAULT;
    CustomResource iBackground = Textures.Gui.Basic.Components.ARROWEMPTY;
    CustomResource iForeground = Textures.Gui.Basic.Components.ARROWFULL;

    public ComponentProgressBar(IComponentHost pHost, String pInternalName, int pLeft, int pTop, Color pBackgroundColor, Color pForegroundColor) {
        this(pHost, pInternalName, pLeft, pTop, Textures.Gui.Basic.Components.ARROWEMPTY, Textures.Gui.Basic.Components.ARROWFULL, pBackgroundColor, pForegroundColor);
    }

    public ComponentProgressBar(IComponentHost pHost, String pInternalName, int pLeft, int pTop, CustomResource pBackground, CustomResource pForeground, Color pBackgroundColor, Color pForegroundColor) {
        super(pHost, pInternalName, pLeft, pTop, pBackground.getWidth(), pBackground.getHeigth());

        iForeGroundColor = pForegroundColor;
        iBackGroundColor = pBackgroundColor;

        iForeground = pForeground;
        iBackground = pBackground;
    }

    @Override
    public Rectangle getOccupiedArea() {
        return super.getOccupiedArea();
    }

    @Override
    public IComponentHost getComponentHost() {
        return super.getComponentHost();
    }

    @Override
    public Rectangle getToolTipVisibileArea() {
        return super.getOccupiedArea();
    }

    @Override
    public ArrayList<IToolTip> getToolTipLines() {
        ArrayList<IToolTip> tToolTips = new ArrayList<IToolTip>();

        tToolTips.add(new StandardToolTip(this, StatCollector.translateToLocal(TranslationKeys.GUI.Components.PROGRESSBARPROGRESS) + ": " + iHost.getProgressBarValue(this.getInternalName()) * 100 + "%"));

        return tToolTips;
    }

    @Override
    public void setToolTipVisibleArea(Rectangle pNewArea) {
        super.setToolTipVisibleArea(pNewArea);
    }

    @Override
    public void onUpdate() {
        iCompletePartToBeRendered = (int) (iHost.getProgressBarValue(this.getInternalName()) * iBackground.getWidth());
    }

    @Override
    public void drawForeGround(int pX, int pY) {
        //NOOP
    }

    @Override
    public void drawBackGround(int pX, int pY) {
        GL11.glPushMatrix();
        GL11.glColor4f(iBackGroundColor.getColorRedFloat(), iBackGroundColor.getColorGreenFloat(), iBackGroundColor.getColorBlueFloat(), iBackGroundColor.getAlphaFloat());

        GuiHelper.bindTexture(iBackground.getPrimaryLocation());
        drawTexturedModalRect(iLeft, iTop, iBackground.getU(), iBackground.getV(), iBackground.getWidth(), iBackground.getHeigth());

        GL11.glColor4f(1F, 1F, 1F, 1F);
        GL11.glPopMatrix();

        GL11.glPushMatrix();
        GL11.glColor4f(iForeGroundColor.getColorRedFloat(), iForeGroundColor.getColorGreenFloat(), iForeGroundColor.getColorBlueFloat(), iForeGroundColor.getAlphaFloat());

        GuiHelper.bindTexture(iForeground.getPrimaryLocation());
        drawTexturedModalRect(iLeft, iTop, iForeground.getU(), iForeground.getV(), iCompletePartToBeRendered, iForeground.getHeigth());

        GL11.glColor4f(1F, 1F, 1F, 1F);
        GL11.glPopMatrix();
    }

    @Override
    public boolean handleMouseClicked(int pMouseX, int pMouseY, int pMouseButton) {
        return false;
    }
}
