package com.Orion.Armory.Client.Renderer.Items;
/*
 *   ItemRendererFirePit
 *   Created by: Orion
 *   Created on: 10-10-2014
 */

import com.Orion.Armory.Client.Models.ModelFirePit;
import com.Orion.Armory.Util.References;
import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class ItemRendererFirePit implements IItemRenderer
{
    protected final ResourceLocation iOffTexture = new ResourceLocation(References.General.MOD_ID + ":" + "textures/blocks/FirePitTextureOff.png");
    private final ModelFirePit iModelFirePit;

    public ItemRendererFirePit()
    {
        iModelFirePit = new ModelFirePit();
    }

    @Override
    public boolean handleRenderType(ItemStack itemStack, ItemRenderType itemRenderType)
    {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType itemRenderType, ItemStack itemStack, ItemRendererHelper itemRendererHelper)
    {
        return true;
    }

    @Override
    public void renderItem(ItemRenderType itemRenderType, ItemStack itemStack, Object... data)
    {
        switch (itemRenderType)
        {
            case ENTITY:
            {
                renderFirePit(-0.5F, -0.38F, 0.5F);
                return;
            }
            case EQUIPPED:
            {
                renderFirePit(0.0F, 0.0F, 1.0F);
                return;
            }
            case EQUIPPED_FIRST_PERSON:
            {
                renderFirePit(0.0F, 0.0F, 1.0F);
                return;
            }
            case INVENTORY:
            {
                renderFirePit(-1.0F, -0.9F, 0.0F);
                return;
            }
            default:
        }
    }

    private void renderFirePit(float x, float y, float z)
    {
        GL11.glPushMatrix();

        // Scale, Translate, Rotate
        GL11.glScalef(1F, 1F, 1F);
        GL11.glTranslatef(x, y, z - 1F);

        // Bind texture
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(iOffTexture);

        // Render
        iModelFirePit.renderPart("Furnace_Cube");

        GL11.glPopMatrix();
    }
}
