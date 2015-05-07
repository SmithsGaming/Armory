package com.Orion.Armory.Client.GUI.Components;

import com.Orion.Armory.Client.GUI.ArmoryBaseGui;
import com.Orion.Armory.Client.GUI.Components.Core.AbstractGUIComponent;
import com.Orion.Armory.Util.Client.Textures;
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

    public ComponentFluidTank(ArmoryBaseGui pGui, String pInternalName, int pLeft, int pTop, int pWidth, int pHeight) {
        super(pGui, pInternalName, pLeft, pTop, pWidth, pHeight);
    }

    @Override
    public void onUpdate() {

    }

    @Override
    public void drawForeGround(int pX, int pY) {
        GL11.glPushMatrix();
        GL11.glTranslatef(iLeft, iTop, 0F);

        bindTexture(Textures.Gui.Basic.Components.TANKGAUGE.getPrimaryLocation());

        int tWidthRepeated = iWidth / Textures.Gui.Basic.Components.TANKGAUGE.getWidth();
        int tWidthLeftAfterRepeating = iWidth % Textures.Gui.Basic.Components.TANKGAUGE.getWidth();

        int tHeightRepeated = iHeight / Textures.Gui.Basic.Components.TANKGAUGE.getHeigth();
        int tHeightLeftAfterRepeating = iHeight % Textures.Gui.Basic.Components.TANKGAUGE.getHeigth();



    }

    @Override
    public void drawBackGround(int pX, int pY) {

    }

    @Override
    public void drawToolTips(int pMouseX, int pMouseY) {

    }

    @Override
    public boolean handleMouseClicked(int pMouseX, int pMouseY, int pMouseButton) {
        return false;
    }
}
