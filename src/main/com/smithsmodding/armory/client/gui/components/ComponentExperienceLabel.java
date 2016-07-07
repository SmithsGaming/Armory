package com.smithsmodding.armory.client.gui.components;

import com.smithsmodding.armory.util.client.Textures;
import com.smithsmodding.smithscore.client.gui.components.implementations.CoreComponent;
import com.smithsmodding.smithscore.client.gui.hosts.IGUIBasedComponentHost;
import com.smithsmodding.smithscore.client.gui.management.StandardRenderManager;
import com.smithsmodding.smithscore.client.gui.state.IGUIComponentState;
import com.smithsmodding.smithscore.util.client.color.Colors;
import com.smithsmodding.smithscore.util.client.gui.GuiHelper;
import com.smithsmodding.smithscore.util.common.positioning.Coordinate2D;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;

/**
 * Author Marc (Created on: 16.06.2016)
 */
public class ComponentExperienceLabel extends CoreComponent {

    public ComponentExperienceLabel(String uniqueID, IGUIBasedComponentHost parent, IGUIComponentState state, Coordinate2D rootAnchorPixel) {
        super(uniqueID, parent, state, rootAnchorPixel, 13 + Minecraft.getMinecraft().fontRendererObj.getStringWidth("100"), Textures.Gui.Anvil.EXPERIENCEORB.getHeight());
    }

    @Override
    public void update(int mouseX, int mouseY, float partialTickTime) {

    }

    @Override
    public void drawBackground(int mouseX, int mouseY) {
        String contents = getComponentHost().getRootManager().getLabelContents(this);
        if (contents == "") return;
        int level = Integer.parseInt(contents);

        if (level > 0) {
            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            GlStateManager.scale(0.6F, 0.6F, 0.6F);
            StandardRenderManager.pushColorOnRenderStack(Colors.Experience.ORB);
            GuiHelper.bindTexture(Textures.Gui.Anvil.EXPERIENCEORB.getPrimaryLocation());
            GuiHelper.drawTexturedModalRect(0, 0, 0, Textures.Gui.Anvil.EXPERIENCEORB.getU(), Textures.Gui.Anvil.EXPERIENCEORB.getV(), Textures.Gui.Anvil.EXPERIENCEORB.getWidth(), Textures.Gui.Anvil.EXPERIENCEORB.getHeight());
            StandardRenderManager.popColorFromRenderStack();
            GlStateManager.popMatrix();

            StandardRenderManager.pushColorOnRenderStack(Colors.Experience.TEXT);
            Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(String.valueOf(level), 13, 1, Colors.Experience.TEXT.getRGB());
            StandardRenderManager.popColorFromRenderStack();
        }
    }

    @Override
    public void drawForeground(int mouseX, int mouseY) {

    }
}
