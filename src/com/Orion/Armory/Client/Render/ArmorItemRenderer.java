package com.Orion.Armory.Client.Render;
/*
*   ArmorItemRenderer
*   Created by: Orion
*   Created on: 9-4-2014
*/

import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.client.IItemRenderer;
import com.Orion.Armory.Common.Armor.ArmorCore;
import net.minecraft.util.IIcon;

public class ArmorItemRenderer implements IItemRenderer {
    //TODO: Find a example of an renderer that handles rendering items in hand (Maybe look at the toolrenderer of TC)
    private static RenderItem iRenderItem = new RenderItem();

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return type == ItemRenderType.INVENTORY;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return false;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack itemStack, Object... data)
    {
        int tRenderPasses = ((ArmorCore) itemStack.getItem()).getRenderPasses(itemStack);
        for (int tPassIter = 0; tPassIter <= tRenderPasses; tPassIter++)
        {
            IIcon tIcon = ((ArmorCore) itemStack.getItem()).getIcon(itemStack, tPassIter);
            iRenderItem.renderIcon(0,0,tIcon, 16, 16);
        }
    }
}
