/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.SmithsModding.Armory.Client.Renderer.Items;

import com.SmithsModding.Armory.Client.Handler.ArmoryClientTickHandler;
import com.SmithsModding.Armory.Client.Models.ModelItemSmithingsGuide;
import com.SmithsModding.Armory.Util.References;
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
        GL11.glRotatef(-60F, 1F, 0F, 0F);

        renderBook(pStack, pPartialTickTime);

        GL11.glPopMatrix();
    }

    private void renderItemInHand(ItemStack pStack, float pPartialTickTime) {
        GL11.glPushMatrix();

        GL11.glTranslatef(0.50F, 0.4F, 0.1F);

        GL11.glRotatef(80F, 1F, 0F, 0F);
        GL11.glRotatef(100F, 0F, 0F, 1F);
        GL11.glRotatef(50F, 0F, 1F, 0F);
        GL11.glRotatef(-90F, 1F, 0F, 0F);

        renderBook(pStack, pPartialTickTime);

        GL11.glPopMatrix();
    }

    private void renderItemInEntity(ItemStack pStack, float pPartialTickTime) {
        GL11.glPushMatrix();

        renderBook(pStack, pPartialTickTime);

        GL11.glPopMatrix();
    }

    private void renderItemInInventory(ItemStack pStack, float pPartialTickTime) {
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

                        if (openingAngle > 135F)
                            openingAngle = 135F;

                        if (openingAngle < 0F)
                            openingAngle = 0F;

                        if (bookRotationAngle > 30F)
                            bookRotationAngle = 30F;

                        if (bookRotationAngle < 0F)
                            bookRotationAngle = 0F;
                    } else {
                        openingAngle = 135F;
                        bookRotationAngle = 30F;
                    }
                } else {
                    if (pStack.getTagCompound().getInteger(References.NBTTagCompoundData.Rendering.TicksSinceClose) > 0) {
                        if (!pStack.getTagCompound().getBoolean(References.NBTTagCompoundData.Rendering.OpenState)) {
                            openingAngle = (135F / iTicksForOpenCloseAnimation) * pStack.getTagCompound().getInteger(References.NBTTagCompoundData.Rendering.TicksSinceClose) - (135F / iTicksForOpenCloseAnimation) * pPartialTickTime;
                            bookRotationAngle = (30F / iTicksForOpenCloseAnimation) * pStack.getTagCompound().getInteger(References.NBTTagCompoundData.Rendering.TicksSinceClose) - (30F / iTicksForOpenCloseAnimation) * pPartialTickTime;
                        }

                        if (openingAngle > 135F)
                            openingAngle = 135F;

                        if (openingAngle < 0F)
                            openingAngle = 0F;

                        if (bookRotationAngle > 30F)
                            bookRotationAngle = 30F;

                        if (bookRotationAngle < 0F)
                            bookRotationAngle = 0F;
                    } else {
                        openingAngle = 0F;
                        bookRotationAngle = 0F;
                    }
                }
            }
        }

        float horizontalBookMovement = (float) ((openingAngle / 135) * 3.5);


        GL11.glPushMatrix();

        GL11.glTranslatef(3.5F + horizontalBookMovement, 8F, 4F);
        GL11.glScalef(17F, 20F, 20F);

        GL11.glRotatef(-90F, 1F, 0F, 0F);
        GL11.glRotatef(90F, 0F, 1F, 0F);

        GL11.glRotatef(-bookRotationAngle / 2, 1F, 0F, 0F);

        GL11.glPushMatrix();

        Minecraft.getMinecraft().renderEngine.bindTexture(iRightTexture);
        iModel.renderPart("RightSide_Cube.002");

        GL11.glRotatef(180F, 0F, 0F, 1F);
        GL11.glTranslatef(0F, 0.2F, 0F);
        GL11.glRotatef(openingAngle, 1F, 0F, 0F);

        Minecraft.getMinecraft().renderEngine.bindTexture(iLeftTexture);
        iModel.renderPart("RightSide_Cube.002");

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

                        if (openingAngle > 135F)
                            openingAngle = 135F;

                        if (openingAngle < 0F)
                            openingAngle = 0F;

                        if (bookRotationAngle > 30F)
                            bookRotationAngle = 30F;

                        if (bookRotationAngle < 0F)
                            bookRotationAngle = 0F;
                    } else {
                        openingAngle = 135F;
                        bookRotationAngle = 30F;
                    }
                } else {
                    if (pStack.getTagCompound().getInteger(References.NBTTagCompoundData.Rendering.TicksSinceClose) > 0) {
                        if (!pStack.getTagCompound().getBoolean(References.NBTTagCompoundData.Rendering.OpenState)) {
                            openingAngle = (135F / iTicksForOpenCloseAnimation) * pStack.getTagCompound().getInteger(References.NBTTagCompoundData.Rendering.TicksSinceClose) - (135F / iTicksForOpenCloseAnimation) * pPartialTickTime;
                            bookRotationAngle = (30F / iTicksForOpenCloseAnimation) * pStack.getTagCompound().getInteger(References.NBTTagCompoundData.Rendering.TicksSinceClose) - (30F / iTicksForOpenCloseAnimation) * pPartialTickTime;
                        }

                        if (openingAngle > 135F)
                            openingAngle = 135F;

                        if (openingAngle < 0F)
                            openingAngle = 0F;

                        if (bookRotationAngle > 30F)
                            bookRotationAngle = 30F;

                        if (bookRotationAngle < 0F)
                            bookRotationAngle = 0F;
                    } else {
                        openingAngle = 0F;
                        bookRotationAngle = 0F;
                    }
                }
            }
        }


        GL11.glPushMatrix();
        GL11.glRotatef(-bookRotationAngle, 1F, 0F, 0F);

        Minecraft.getMinecraft().renderEngine.bindTexture(iRightTexture);
        iModel.renderPart("RightSide_Cube.002");

        GL11.glRotatef(180F, 0F, 0F, 1F);
        GL11.glRotatef(openingAngle, 1F, 0F, 0F);

        Minecraft.getMinecraft().renderEngine.bindTexture(iLeftTexture);
        iModel.renderPart("RightSide_Cube.002");

        GL11.glPopMatrix();
    }
}
