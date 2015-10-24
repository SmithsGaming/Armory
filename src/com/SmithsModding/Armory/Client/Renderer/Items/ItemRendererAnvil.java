package com.SmithsModding.Armory.Client.Renderer.Items;

import com.SmithsModding.Armory.Client.Models.ModelAnvil;
import com.SmithsModding.Armory.Util.References;
import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

/**
 * Created by Orion
 * Created on 02.05.2015
 * 00:30
 * <p/>
 * Copyrighted according to Project specific license
 */
public class ItemRendererAnvil implements IItemRenderer {
    public final ModelAnvil iModel = new ModelAnvil();
    protected final ResourceLocation iAnvilTexture = new ResourceLocation(References.General.MOD_ID + ":" + "textures/blocks/AnvilTextured.png");

    @Override
    public boolean handleRenderType(ItemStack itemStack, IItemRenderer.ItemRenderType itemRenderType) {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(IItemRenderer.ItemRenderType itemRenderType, ItemStack itemStack, IItemRenderer.ItemRendererHelper itemRendererHelper) {
        return true;
    }

    @Override
    public void renderItem(IItemRenderer.ItemRenderType itemRenderType, ItemStack itemStack, Object... data) {
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
            GL11.glRotatef(90F, 0F, 0F, 1F);
            GL11.glRotatef(90F, 1F, 0F, 0F);
            GL11.glRotatef(-90F, 0F, 1F, 0F);
        } else {
            GL11.glTranslatef(pX, pY, pZ - 0.5F);
            GL11.glRotatef(90F, 0F, 0F, 1F);
            GL11.glRotatef(90F, 1F, 0F, 0F);
            GL11.glRotatef(-90F, 0F, 1F, 0F);
        }

        // Bind texture
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(iAnvilTexture);
        iModel.renderAll();

        GL11.glPopMatrix();
    }
}
