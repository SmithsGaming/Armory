/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.Orion.Armory.Client.Renderer.Items;

import com.Orion.Armory.Client.Models.ModelBookBinder;
import com.Orion.Armory.Util.References;
import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class ItemRendererBookBinder implements IItemRenderer {
    public final ModelBookBinder iModelBookBinder = new ModelBookBinder();
    protected final ResourceLocation iBookBinderTexture = new ResourceLocation(References.General.MOD_ID + ":" + "/textures/blocks/BookBinder/BookBinder.png");

    @Override
    public boolean handleRenderType(ItemStack itemStack, ItemRenderType itemRenderType) {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType itemRenderType, ItemStack itemStack, ItemRendererHelper itemRendererHelper) {
        return true;
    }

    @Override
    public void renderItem(ItemRenderType itemRenderType, ItemStack itemStack, Object... data) {
        switch (itemRenderType) {
            case ENTITY: {
                renderAnvil(-0.5F, -0.38F, 0.5F, itemRenderType);
                return;
            }
            case EQUIPPED: {
                renderAnvil(0.0F, 0.0F, 1.0F, itemRenderType);
                return;
            }
            case EQUIPPED_FIRST_PERSON: {
                renderAnvil(0.0F, 0.0F, 1.0F, itemRenderType);
                return;
            }
            case INVENTORY: {
                renderAnvil(-1.0F, -0.9F, 0.0F, itemRenderType);
                return;
            }
            default:
        }
    }

    private void renderAnvil(float pX, float pY, float pZ, ItemRenderType pRenderType) {
        GL11.glPushMatrix();

        // Scale, Translate, Rotate
        GL11.glScalef(1F, 1F, 1F);

        if (pRenderType == ItemRenderType.INVENTORY) {
            GL11.glTranslatef(pX + 1, pY + 0.5F, pZ);
        } else {
            GL11.glTranslatef(pX, pY, pZ - 0.5F);
        }

        // Bind texture
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(iBookBinderTexture);
        iModelBookBinder.renderPart("BookHolder_Cube");

        GL11.glPopMatrix();
    }
}
