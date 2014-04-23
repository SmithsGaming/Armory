package com.Orion.Armory.Client.Render;
/*
*   ArmorItemRenderer
*   Created by: Orion
*   Created on: 9-4-2014
*/

import com.Orion.Armory.Client.ArmoryResource;
import com.Orion.Armory.Common.Armor.ArmorCore;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

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
            ArmoryResource tResource = ((ArmorCore) itemStack.getItem()).getResource(tPassIter);
            IIcon tIcon = tResource.getIcon();
            float tRed = tResource.getColor(0) / 255.0F;
            float tGreen = tResource.getColor(1) / 255.0F;
            float tBlue = tResource.getColor(2) / 255.0F;

            GL11.glColor4f(tRed, tGreen, tBlue, 1.0F);
            iRenderItem.renderIcon(0,0,tIcon, 16, 16);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }
}
