package com.SmithsModding.Armory.Client.GUI.Components;

import com.SmithsModding.Armory.Client.GUI.Components.Core.IComponentHost;
import com.SmithsModding.Armory.Client.GUI.Components.ToolTips.IToolTip;
import com.SmithsModding.Armory.Client.GUI.Components.ToolTips.StandardToolTip;
import com.SmithsModding.Armory.Util.Client.Color.Color;
import com.SmithsModding.Armory.Util.Client.GUI.GuiHelper;
import com.SmithsModding.Armory.Util.Client.Textures;
import com.SmithsModding.Armory.Util.Client.TranslationKeys;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

/**
 * Created by Orion
 * Created on 02.06.2015
 * 01:05
 * <p/>
 * Copyrighted according to Project specific license
 */
public class ComponentProgressFlame extends ComponentProgressBar {

    public ComponentProgressFlame(IComponentHost pHost, String pInternalName, int pLeft, int pTop, Color pBackgroundColor, Color pForegroundColor) {
        super(pHost, pInternalName, pLeft, pTop, Textures.Gui.Basic.Components.FLAMEEMPTY, Textures.Gui.Basic.Components.FLAMEFULL, pBackgroundColor, pForegroundColor);
    }

    @Override
    public void onUpdate() {
        iCompletePartToBeRendered = (int) (iHost.getProgressBarValue(this.getInternalName()) * iBackground.getHeight());
    }

    @Override
    public ArrayList<IToolTip> getToolTipLines() {
        ArrayList<IToolTip> tToolTips = new ArrayList<IToolTip>();

        float tProgress = iHost.getProgressBarValue(this.getInternalName());
        if (tProgress < 0) {
            tToolTips.add(new StandardToolTip(this, StatCollector.translateToLocal(TranslationKeys.GUI.Components.PROGRESSFLAMEFUELLEFT) + ": 0%"));
        } else {
            tToolTips.add(new StandardToolTip(this, StatCollector.translateToLocal(TranslationKeys.GUI.Components.PROGRESSFLAMEFUELLEFT) + ": " + Math.round((1 - tProgress) * 100) + "%"));
        }

        return tToolTips;
    }

    @Override
    public void drawBackGround(int pX, int pY) {
        GL11.glPushMatrix();
        GL11.glColor4f(iBackGroundColor.getColorRedFloat(), iBackGroundColor.getColorGreenFloat(), iBackGroundColor.getColorBlueFloat(), iBackGroundColor.getAlphaFloat());

        GuiHelper.bindTexture(iBackground.getPrimaryLocation());
        drawTexturedModalRect(iLeft, iTop, iBackground.getU(), iBackground.getV(), iBackground.getWidth(), iBackground.getHeight());

        GL11.glColor4f(1F, 1F, 1F, 1F);
        GL11.glPopMatrix();

        if (iCompletePartToBeRendered == -1 * iBackground.getHeight())
            return;

        GL11.glPushMatrix();
        GL11.glColor4f(iForeGroundColor.getColorRedFloat(), iForeGroundColor.getColorGreenFloat(), iForeGroundColor.getColorBlueFloat(), iForeGroundColor.getAlphaFloat());

        GuiHelper.bindTexture(iForeground.getPrimaryLocation());
        drawTexturedModalRect(iLeft, iTop + iCompletePartToBeRendered, iForeground.getU(), iForeground.getV() + iCompletePartToBeRendered, iForeground.getWidth(), iForeground.getHeight() - iCompletePartToBeRendered);

        GL11.glColor4f(1F, 1F, 1F, 1F);
        GL11.glPopMatrix();
    }
}
