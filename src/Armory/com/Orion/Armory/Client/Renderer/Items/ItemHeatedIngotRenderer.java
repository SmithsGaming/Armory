package com.Orion.Armory.Client.Renderer.Items;
/*
 *   ItemHeatedIngotRenderer
 *   Created by: Orion
 *   Created on: 17-1-2015
 */

import com.Orion.Armory.Common.Factory.HeatedIngotFactory;
import com.Orion.Armory.Common.Item.ItemHeatedItem;
import com.Orion.Armory.Util.Client.Color;
import com.Orion.Armory.Util.Client.Colors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class ItemHeatedIngotRenderer implements IItemRenderer
{

    @Override
    public boolean handleRenderType(ItemStack pItemStack, ItemRenderType pRenderType) {
        if ((pItemStack.getItem() instanceof ItemHeatedItem) && (pRenderType == ItemRenderType.INVENTORY)) { return true; }

        return false;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType pRenderType, ItemStack pItemStack, ItemRendererHelper pRenderHelper) {
        return false;
    }

    @Override
    public void renderItem(ItemRenderType pRenderType, ItemStack pItemStack, Object... pDataArray) {
        if (!(pItemStack.getItem() instanceof ItemHeatedItem) || !(pRenderType == ItemRenderType.INVENTORY)) { return; }

        if ((pRenderType == ItemRenderType.INVENTORY) && (RenderManager.instance.renderEngine != null))
        {
            RenderItem.getInstance().renderItemIntoGUI(Minecraft.getMinecraft().fontRenderer, RenderManager.instance.renderEngine, HeatedIngotFactory.getInstance().convertToCooledIngot(pItemStack), 0,0);
            renderTemperatureBar(pItemStack);
        }
    }

    private void renderTemperatureBar(ItemStack pItemStack)
    {
        double health = pItemStack.getItem().getDurabilityForDisplay(pItemStack);
        int j1 = (int)Math.round(13.0D - health * 13.0D);
        int k = (int)Math.round(255.0D - health * 255.0D);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glDisable(GL11.GL_BLEND);
        Tessellator tessellator = Tessellator.instance;
        int i1 = Color.Combine(Colors.General.ELECTRICBLUE, Colors.General.RED, (j1 / 13)).getColor();
        int l = (255 - k) / 4 << 16 | 16128;
        this.renderQuad(tessellator, 2, 13, 13, 2, 0);
        this.renderQuad(tessellator, 2, 13, 12, 1, l);
        this.renderQuad(tessellator, 2, 13, j1, 1, i1);
        GL11.glEnable(GL11.GL_BLEND); // Forge: Disable Bled because it screws with a lot of things down the line.
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    private void renderQuad(Tessellator pTessellator, int p_77017_2_, int p_77017_3_, int p_77017_4_, int p_77017_5_, int pOpaque)
    {
        pTessellator.startDrawingQuads();
        pTessellator.setColorOpaque_I(pOpaque);
        pTessellator.addVertex((double) (p_77017_2_ + 0), (double) (p_77017_3_ + 0), 0.0D);
        pTessellator.addVertex((double) (p_77017_2_ + 0), (double) (p_77017_3_ + p_77017_5_), 0.0D);
        pTessellator.addVertex((double) (p_77017_2_ + p_77017_4_), (double) (p_77017_3_ + p_77017_5_), 0.0D);
        pTessellator.addVertex((double) (p_77017_2_ + p_77017_4_), (double) (p_77017_3_ + 0), 0.0D);
        pTessellator.draw();
    }
}
