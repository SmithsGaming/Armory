package com.Orion.Armory.Client.Renderer.Items;

import com.Orion.Armory.Client.Models.ModelFirePit;
import com.Orion.Armory.Client.Models.ModelHeater;
import com.Orion.Armory.Util.References;
import com.sun.javafx.sg.prism.NodeEffectInput;
import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

/**
 * Created by Orion
 * Created on 23.04.2015
 * 13:55
 * <p/>
 * Copyrighted according to Project specific license
 */
public class ItemRendererHeater implements IItemRenderer {
    protected final ResourceLocation iHeaterTexture = new ResourceLocation(References.General.MOD_ID + ":" + "textures/blocks/HeaterHull.png");
    protected final ResourceLocation iFanTexture = new ResourceLocation(References.General.MOD_ID + ":" + "textures/blocks/FanIron.png");

    private final ModelHeater iModelHeater;

    public ItemRendererHeater() {
        iModelHeater = new ModelHeater();
    }

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
                renderHeater(-0.5F, -0.38F, 0.5F, itemRenderType);
                return;
            }
            case EQUIPPED: {
                renderHeater(0.0F, 0.0F, 1.0F, itemRenderType);
                return;
            }
            case EQUIPPED_FIRST_PERSON: {
                renderHeater(0.0F, 0.0F, 1.0F, itemRenderType);
                return;
            }
            case INVENTORY: {
                renderHeater(-1.0F, -0.9F, 0.0F, itemRenderType);
                return;
            }
            default:
        }
    }

    private void renderHeater(float pX, float pY, float pZ, ItemRenderType pRenderType)
    {
        GL11.glPushMatrix();

        // Scale, Translate, Rotate
        GL11.glScalef(1F, 1F, 1F);

        if (pRenderType == ItemRenderType.INVENTORY)
        {
            GL11.glTranslatef(pX + 1, pY + 1, pZ);
            GL11.glRotatef(90F, 0F, 1F, 0F);
        }
        else
        {
            GL11.glTranslatef(pX, pY + 1, pZ);
        }

        // Bind texture
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(iHeaterTexture);

        // Render
        iModelHeater.renderPart("Hull_Cube");

        // Bind texture
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(iFanTexture);

        // Render
        iModelHeater.renderPart("Fan_Cylinder");

        GL11.glPopMatrix();
    }
}
