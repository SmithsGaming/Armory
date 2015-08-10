/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.Orion.Armory.Client.Renderer.Items;

import com.Orion.Armory.Client.GUI.ArmoryBaseGui;
import com.Orion.Armory.Client.Handler.ArmoryClientTickHandler;
import com.Orion.Armory.Client.Models.ModelItemSmithingsGuide;
import com.Orion.Armory.Util.References;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBook;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class ItemRendererSmithingsGuide implements IItemRenderer {

    private static int iTicksForOpenCloseAnimation = 5;
    protected final ResourceLocation iLeftTexture = new ResourceLocation(References.General.MOD_ID + ":" + "textures/items/GuideLeft.png");
    protected final ResourceLocation iRightTexture = new ResourceLocation(References.General.MOD_ID + ":" + "textures/items/GuideRight.png");
    protected final ResourceLocation iBookTexture = new ResourceLocation(References.General.MOD_ID + ":" + "textures/items/lexica.png");

    private final ModelItemSmithingsGuide iModel = new ModelItemSmithingsGuide();
    private final ModelBook iModelBook = new ModelBook();

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return (type == ItemRenderType.ENTITY && helper == ItemRendererHelper.ENTITY_BOBBING) || (type == ItemRenderType.ENTITY && helper == ItemRendererHelper.ENTITY_ROTATION);
    }

    @Override
    public void renderItem(ItemRenderType pType, ItemStack pStack, Object... pData) {
        if (pType == ItemRenderType.INVENTORY)
            renderItemInInventory(pStack, ArmoryClientTickHandler.getPartialRenderTick());
        else if (pType == ItemRenderType.ENTITY)
            renderItemInEntity(pStack, ArmoryClientTickHandler.getPartialRenderTick());
        else if (pType == ItemRenderType.EQUIPPED_FIRST_PERSON)
            renderItemInHand(pStack, ArmoryClientTickHandler.getPartialRenderTick());
        else
            renderItemInHand(pStack, ArmoryClientTickHandler.getPartialRenderTick());
    }

    private void renderItemInFirstPerson(ItemStack pStack, float pPartialTickTime) {
        GL11.glPushMatrix();

        GL11.glScalef(10F, 10F, 10F);
        GL11.glTranslatef(-0.5F, -0.25F, -0.75F);

        Minecraft.getMinecraft().renderEngine.bindTexture(iRightTexture);
        iModel.renderPart("RightSide_Cube.002");

        Minecraft.getMinecraft().renderEngine.bindTexture(iLeftTexture);
        iModel.renderPart("LeftSide_Cube.003");

        GL11.glPopMatrix();
    }

    private void renderItemInHand(ItemStack pStack, float pPartialTickTime) {
        GL11.glPushMatrix();

        Minecraft mc = Minecraft.getMinecraft();
        mc.renderEngine.bindTexture(iBookTexture);
        float opening = 0F;
        float pageFlip = 0F;

        float ticks = ArmoryClientTickHandler.getTickWithSmithingsGuideOpen();
        if (ticks > 0 && ticks < 10) {
            if (mc.currentScreen instanceof ArmoryBaseGui)
                ticks += ArmoryClientTickHandler.getPartialRenderTick();
            else ticks -= ArmoryClientTickHandler.getPartialRenderTick();
        }

        GL11.glTranslatef(0.3F + 0.02F * ticks, 0.475F + 0.01F * ticks, -0.2F - 0.01F * ticks);
        GL11.glRotatef(87.5F + ticks * 5, 0F, 1F, 0F);
        GL11.glRotatef(ticks * 2.5F, 0F, 0F, 1F);
        GL11.glScalef(0.9F, 0.9F, 0.9F);
        opening = ticks / 12F;

        float pageFlipTicks = ArmoryClientTickHandler.getPageFlipTicks();
        if (pageFlipTicks > 0)
            pageFlipTicks -= ArmoryClientTickHandler.getPartialRenderTick();

        pageFlip = pageFlipTicks / 5F;

        iModelBook.render(null, 0F, 0F, pageFlip, opening, 0F, 1F / 16F);

        GL11.glPopMatrix();
    }

    private void renderItemInEntity(ItemStack pStack, float pPartialTickTime) {
        GL11.glPushMatrix();

        Minecraft.getMinecraft().renderEngine.bindTexture(iRightTexture);
        iModel.renderPart("RightSide_Cube.002");

        Minecraft.getMinecraft().renderEngine.bindTexture(iLeftTexture);
        iModel.renderPart("LeftSide_Cube.003");

        GL11.glPopMatrix();
    }

    private void renderItemInInventory(ItemStack pStack, float pPartialTickTime) {
        GL11.glPushMatrix();

        GL11.glScalef(50F, 50f, 50F);

        renderItemInHand(pStack, pPartialTickTime);

        GL11.glPopMatrix();
    }
}
