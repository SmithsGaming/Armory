/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.Orion.Armory.Client.Renderer.Items;

import com.Orion.Armory.Client.Handler.ArmoryClientTickHandler;
import com.Orion.Armory.Client.Models.ModelItemSmithingsGuide;
import com.Orion.Armory.Util.References;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class ItemRendererSmithingsGuide implements IItemRenderer {

    private static int iTicksForOpenCloseAnimation = 5;
    protected final ResourceLocation iLeftTexture = new ResourceLocation(References.General.MOD_ID + ":" + "textures/items/GuideLeft.png");
    protected final ResourceLocation iRightTexture = new ResourceLocation(References.General.MOD_ID + ":" + "textures/items/GuideRight.png");
    private final ModelItemSmithingsGuide iModel = new ModelItemSmithingsGuide();

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return false;
    }

    @Override
    public void renderItem(ItemRenderType pType, ItemStack pStack, Object... pData) {
        if (pType == ItemRenderType.INVENTORY)
            renderItemInInventory(pStack, ArmoryClientTickHandler.getPartialRenderTick());
    }

    private void renderItemInFirstPerson(ItemStack pStack, float pPartialTickTime) {

    }

    private void renderItemInHand(ItemStack pStack, float pPartialTickTime) {

    }

    private void renderItemInEntity(ItemStack pStack, float pPartialTickTime) {

    }

    private void renderItemInInventory(ItemStack pStack, float pPartialTickTime) {
        GL11.glPushMatrix();

        Minecraft.getMinecraft().renderEngine.bindTexture(iRightTexture);
        iModel.

    }
}
