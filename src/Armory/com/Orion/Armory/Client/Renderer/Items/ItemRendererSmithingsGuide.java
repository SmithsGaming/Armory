/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.Orion.Armory.Client.Renderer.Items;

import com.Orion.Armory.Client.Handler.ArmoryClientTickHandler;
import com.Orion.Armory.Client.Models.ModelItemSmithingsGuide;
import com.Orion.Armory.Util.Client.Color.Color;
import com.Orion.Armory.Util.Client.Colors;
import com.Orion.Armory.Util.References;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class ItemRendererSmithingsGuide implements IItemRenderer {

    public static int iTicksForOpenCloseAnimation = 5;
    protected final ResourceLocation iLeftTexture = new ResourceLocation(References.General.MOD_ID + ":" + "textures/items/GuideLeft.png");
    protected final ResourceLocation iRightTexture = new ResourceLocation(References.General.MOD_ID + ":" + "textures/items/GuideRight.png");
    private final ModelItemSmithingsGuide iModel = new ModelItemSmithingsGuide();

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
            renderItemInFirstPerson(pStack, ArmoryClientTickHandler.getPartialRenderTick());
        else
            renderItemInHand(pStack, ArmoryClientTickHandler.getPartialRenderTick());
    }

    private void renderItemInFirstPerson(ItemStack pStack, float pPartialTickTime) {
        GL11.glPushMatrix();

        GL11.glTranslatef(0.7F, 0.5F, 0.2F);

        GL11.glRotatef(80F, 1F, 0F, 0F);
        GL11.glRotatef(100F, 0F, 0F, 1F);
        GL11.glRotatef(50F, 0F, 1F, 0F);

        renderBook(pStack, pPartialTickTime);

        GL11.glPopMatrix();
    }

    private void renderItemInHand(ItemStack pStack, float pPartialTickTime) {
        GL11.glPushMatrix();

        GL11.glTranslatef(0.25F, 0.4F, 0.1F);

        GL11.glRotatef(80F, 1F, 0F, 0F);
        GL11.glRotatef(180F, 0F, 0F, 1F);
        GL11.glRotatef(50F, 0F, 1F, 0F);

        renderBook(pStack, pPartialTickTime);

        GL11.glPopMatrix();
    }

    private void renderItemInEntity(ItemStack pStack, float pPartialTickTime) {
        GL11.glPushMatrix();

        renderBook(pStack, pPartialTickTime);

        GL11.glPopMatrix();
    }

    private void renderItemInInventory(ItemStack pStack, float pPartialTickTime) {
        GL11.glPushMatrix();

        float openingAngle = 0F;
        float bookRotationAngle = 0F;
        float bookMovement = 0F;

        if (!(pStack.getTagCompound() == null)) {
            if ((pStack.getTagCompound().hasKey(References.NBTTagCompoundData.Rendering.OpenState))) {
                if (pStack.getTagCompound().getInteger(References.NBTTagCompoundData.Rendering.TicksSinceOpen) < 4) {
                    if (pStack.getTagCompound().getBoolean(References.NBTTagCompoundData.Rendering.OpenState)) {
                        openingAngle = (135F / iTicksForOpenCloseAnimation) * pStack.getTagCompound().getInteger(References.NBTTagCompoundData.Rendering.TicksSinceOpen) + (135F / iTicksForOpenCloseAnimation) * pPartialTickTime;
                        bookRotationAngle = (30F / iTicksForOpenCloseAnimation) * pStack.getTagCompound().getInteger(References.NBTTagCompoundData.Rendering.TicksSinceOpen) + (30F / iTicksForOpenCloseAnimation) * pPartialTickTime;
                    }
                } else {
                    openingAngle = 135F;
                    bookRotationAngle = 30F;
                }
            }
        }


        GL11.glTranslatef(8F, 8F, 8F);
        GL11.glScalef(20F, 20F, 20F);

        GL11.glRotatef(-90F, 0F, 1F, 0F);
        GL11.glRotatef(180F, 1F, 0F, 0F);
        GL11.glRotatef(75F, 0F, 0F, 1F);

        GL11.glPushMatrix();
        GL11.glRotatef(bookRotationAngle, 1F, 0F, 0F);

        Colors.General.RED.performGLColor();
        Minecraft.getMinecraft().renderEngine.bindTexture(iRightTexture);
        iModel.renderPart("RightSide_Cube.002");

        GL11.glTranslatef(0F, -0.1F, -0.25F);
        GL11.glRotatef(360 - openingAngle, 1F, 0F, 0F);

        Colors.General.GREEN.performGLColor();
        Minecraft.getMinecraft().renderEngine.bindTexture(iLeftTexture);
        iModel.renderPart("LeftSide_Cube.003");

        Color.resetGLColor();
        GL11.glPopMatrix();

        GL11.glPopMatrix();
    }

    private void renderBook(ItemStack pStack, float pPartialTickTime) {
        float openingAngle = 0F;
        float bookRotationAngle = 0F;

        if (!(pStack.getTagCompound() == null)) {
            if ((pStack.getTagCompound().hasKey(References.NBTTagCompoundData.Rendering.OpenState))) {
                if (pStack.getTagCompound().getBoolean(References.NBTTagCompoundData.Rendering.OpenState)) {
                    if (pStack.getTagCompound().getInteger(References.NBTTagCompoundData.Rendering.TicksSinceOpen) < 6) {
                        if (pStack.getTagCompound().getBoolean(References.NBTTagCompoundData.Rendering.OpenState)) {
                            openingAngle = (135F / iTicksForOpenCloseAnimation) * pStack.getTagCompound().getInteger(References.NBTTagCompoundData.Rendering.TicksSinceOpen) + (135F / iTicksForOpenCloseAnimation) * pPartialTickTime;
                            bookRotationAngle = (30F / iTicksForOpenCloseAnimation) * pStack.getTagCompound().getInteger(References.NBTTagCompoundData.Rendering.TicksSinceOpen) + (30F / iTicksForOpenCloseAnimation) * pPartialTickTime;
                        }
                    } else {
                        openingAngle = 135F;
                        bookRotationAngle = 30F;
                    }
                } else {
                    if (pStack.getTagCompound().getInteger(References.NBTTagCompoundData.Rendering.TicksSinceClose) > 0) {
                        if (pStack.getTagCompound().getBoolean(References.NBTTagCompoundData.Rendering.OpenState)) {
                            openingAngle = (135F / iTicksForOpenCloseAnimation) * pStack.getTagCompound().getInteger(References.NBTTagCompoundData.Rendering.TicksSinceClose) + (135F / iTicksForOpenCloseAnimation) * pPartialTickTime;
                            bookRotationAngle = (30F / iTicksForOpenCloseAnimation) * pStack.getTagCompound().getInteger(References.NBTTagCompoundData.Rendering.TicksSinceClose) + (30F / iTicksForOpenCloseAnimation) * pPartialTickTime;
                        }
                    } else {
                        openingAngle = 0F;
                        bookRotationAngle = 0F;
                    }
                }
            }
        }

        GL11.glPushMatrix();
        GL11.glRotatef(-1 * bookRotationAngle, 1F, 0F, 0F);

        Minecraft.getMinecraft().renderEngine.bindTexture(iRightTexture);
        iModel.renderPart("RightSide_Cube.002");

        GL11.glTranslatef(0F, 0.1F, -0.25F);
        GL11.glRotatef(360 - openingAngle, 1F, 0F, 0F);

        Minecraft.getMinecraft().renderEngine.bindTexture(iLeftTexture);
        iModel.renderPart("LeftSide_Cube.003");

        GL11.glPopMatrix();
    }
}
