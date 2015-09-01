/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.Orion.Armory.Client.Renderer.Items;

import com.Orion.Armory.Common.Item.ItemGuideLabel;
import com.Orion.Armory.Util.Client.Color.Color;
import com.Orion.Armory.Util.Client.GUI.GuiHelper;
import com.Orion.Armory.Util.References;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class ItemRendererGuideLabel implements IItemRenderer {


    @Override
    public boolean handleRenderType(ItemStack pItemStack, ItemRenderType pRenderType) {
        return (pItemStack.getItem() instanceof ItemGuideLabel) && (pRenderType == ItemRenderType.INVENTORY);

    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType pRenderType, ItemStack pItemStack, ItemRendererHelper pRenderHelper) {
        return false;
    }

    @Override
    public void renderItem(ItemRenderType pRenderType, ItemStack pItemStack, Object... data) {
        if (!(pItemStack.getItem() instanceof ItemGuideLabel) || !(pRenderType == ItemRenderType.INVENTORY)) {
            return;
        }

        GL11.glPushMatrix();

        GL11.glEnable(GL11.GL_ALPHA_TEST);

        Color.resetGLColor();
        GuiHelper.drawTexturedModelRectFromIcon(0, 0, 0, pItemStack.getItem().getIcon(pItemStack, 0), 16, 16);


        GL11.glPopMatrix();

        if ((pRenderType == ItemRenderType.INVENTORY) && (RenderManager.instance.renderEngine != null) && (pItemStack.getTagCompound() != null) && (pItemStack.getTagCompound().hasKey(References.NBTTagCompoundData.Item.Labels.LOGOSTACK))) {
            GL11.glPushMatrix();
            GL11.glScalef(0.5F, 0.5F, 0.5F);
            RenderItem.getInstance().renderItemIntoGUI(Minecraft.getMinecraft().fontRenderer, RenderManager.instance.renderEngine, ItemStack.loadItemStackFromNBT(pItemStack.getTagCompound().getCompoundTag(References.NBTTagCompoundData.Item.Labels.LOGOSTACK)), 0, 16);
            GL11.glPopMatrix();
        }

    }
}
