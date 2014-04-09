package com.Orion.Armory.Client.Render;
/*
*   ArmorItemRenderer
*   Created by: Orion
*   Created on: 9-4-2014
*/

import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ArmorItemRenderer implements IItemRenderer {
    //TODO: Create a special IItemRenderer that allows rendering of items with getRenderPass(ItemStack) as values to enable the gettings of the amount of passes that are needed from the NBT Tag.

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return type == ItemRenderType.INVENTORY;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return false;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {

    }
}
