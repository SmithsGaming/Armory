/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.Orion.Armory.Client.GUI.Components;

import com.Orion.Armory.Client.GUI.Components.Core.AbstractGUIComponent;
import com.Orion.Armory.Client.GUI.Components.Core.IComponentHost;
import com.Orion.Armory.Util.Client.Color.Color;
import com.Orion.Armory.Util.Client.Colors;
import com.Orion.Armory.Util.Client.GUI.GuiHelper;
import com.Orion.Armory.Util.Client.Textures;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;


public class ComponentExperienceLabel extends AbstractGUIComponent {

    int iLevelAmount = 0;

    public ComponentExperienceLabel(IComponentHost pGui, String pInternalName, int pLeft, int pTop, int pWidth, int pHeight) {
        super(pGui, pInternalName, pLeft, pTop, 16, Math.max(16, Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT));
    }

    @Override
    public void onUpdate() {
        iLevelAmount = Integer.parseInt(String.valueOf(iHost.getComponentRelatedObject(getInternalName() + ".Value")));
    }

    @Override
    public void drawForeGround(int pX, int pY) {

    }

    @Override
    public void drawBackGround(int pX, int pY) {
        //if (iLevelAmount == -1)
        //    return;

        GL11.glPushMatrix();
        GL11.glTranslatef(iLeft, iTop, 0);

        if (iLevelAmount > 0) {
            GL11.glPushMatrix();
            GL11.glScalef(0.6F, 0.6F, 0.6F);
            Colors.Experience.ORB.performGLColor();
            GuiHelper.bindTexture(Textures.Gui.Anvil.EXPERIENCEORB.getPrimaryLocation());
            GuiHelper.drawTexturedModalRect(0, 0, 0, Textures.Gui.Anvil.EXPERIENCEORB.getU(), Textures.Gui.Anvil.EXPERIENCEORB.getV(), Textures.Gui.Anvil.EXPERIENCEORB.getWidth(), Textures.Gui.Anvil.EXPERIENCEORB.getHeigth());
            Color.resetGLColor();
            GL11.glPopMatrix();

            Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(String.valueOf(iLevelAmount), 13, 1, Colors.Experience.TEXT.getColor());
            Color.resetGLColor();
        }

        GL11.glPopMatrix();
    }

    @Override
    public boolean handleMouseClicked(int pMouseX, int pMouseY, int pMouseButton) {
        return false;
    }
}
