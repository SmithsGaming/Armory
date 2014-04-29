package com.Orion.Armory.Client.Render;
/*
*   ArmoryPlayerRenderer
*   Created by: Orion
*   Created on: 29-4-2014
*/

import com.Orion.Armory.Client.Models.AExtendedPlayerModel;
import com.Orion.Armory.Common.ARegistry;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.common.MinecraftForge;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class ArmoryPlayerRenderer extends Render
{
    private Logger iLogger = ARegistry.iLogger;
    private ModelBiped iModelBipedMain;
    private ModelBiped iModelArmorChestplate;
    private ModelBiped iModelArmor;
    private ModelBiped iRenderPassModel;
    private ArmoryArmorRender iSpecialRenderer;
    private ArmoryVanillaArmorRender iCommonRenderer;

    public ArmoryPlayerRenderer() {
        this.shadowSize = 0.5F;
        this.iModelBipedMain = new AExtendedPlayerModel(0.0F);
        this.iModelArmorChestplate = new AExtendedPlayerModel(1.0F);
        this.iModelArmor = new AExtendedPlayerModel(0.5F);
    }

    //Method to downcast an entity (required to be a renderer):
    @Override
    public void doRender(Entity pEntity, double pRenderDataX, double pRenderDataY, double pRenderDataZ, float pUnknown, float pPartialTickTime)
    {
        this.doRender((AbstractClientPlayer) pEntity, pRenderDataX, pRenderDataY, pRenderDataZ, pUnknown, pPartialTickTime);
    }

    //Method that actually will render the player and all its attributes:
    public void doRender(AbstractClientPlayer pPlayer, double pRenderDataX, double pRenderDataY, double pRenderDataZ, float pUnknown, float pPartialTickTime)
    {
        //Notifying other mods of the rendering of a player:
        if (MinecraftForge.EVENT_BUS.post(new RenderPlayerEvent.Pre(pPlayer, (RenderPlayer) (RendererLivingEntity) (Render) this, pPartialTickTime))) return;

        //Cleaning up of the GL11 Color system:
        GL11.glColor3f(1.0F, 1.0F, 1.0F);

        //Acquiring the current hold item and changing the models depending on it:
        ItemStack tCurrentItemStack = pPlayer.inventory.getCurrentItem();
        this.iModelArmorChestplate.heldItemRight = this.iModelArmor.heldItemRight = this.iModelBipedMain.heldItemRight = tCurrentItemStack != null ? 1 : 0;

        if (tCurrentItemStack != null && pPlayer.getItemInUseCount() > 0)
        {
            EnumAction enumaction = tCurrentItemStack.getItemUseAction();

            if (enumaction == EnumAction.block)
            {
                this.iModelArmorChestplate.heldItemRight = this.iModelArmor.heldItemRight = this.iModelBipedMain.heldItemRight = 3;
            }
            else if (enumaction == EnumAction.bow)
            {
                this.iModelArmorChestplate.aimedBow = this.iModelArmor.aimedBow = this.iModelBipedMain.aimedBow = true;
            }
        }

        //Checking if the current player is sneaking and changing the models depending on it:
        this.iModelArmorChestplate.isSneak = this.iModelArmor.isSneak = this.iModelBipedMain.isSneak = pPlayer.isSneaking();

        //Taking the player offset into account:
        double tPlayerOffsetY = pRenderDataY - (double)pPlayer.yOffset;
        if (pPlayer.isSneaking() && !(pPlayer instanceof EntityPlayerSP))
        {
            tPlayerOffsetY -= 0.125D;
        }


    }

    //Required function to be a PlayerRenderer. Returns the players skin or steve.
    @Override
    protected ResourceLocation getEntityTexture(Entity pEntity) {
        return ((AbstractClientPlayer) pEntity).getLocationSkin();
    }

    //This function is used to predict the current location of the players limbs.(Cause there are more then one render tick in one game tick)
    private float interpolateRotation(float pStart, float pEnd, float pPartialTickTime)
    {
        float f3;

        for (f3 = pEnd - pStart; f3 < -180.0F; f3 += 360.0F)
        {
            ;
        }

        while (f3 >= 180.0F)
        {
            f3 -= 360.0F;
        }

        return pStart + pPartialTickTime * f3;
    }

    //Grabs the swingprocess value of the current entity.
    protected float renderSwingProgress(EntityLivingBase pLivingEntity, float pPartialTickTime)
    {
        return pLivingEntity.getSwingProgress(pPartialTickTime);
    }

    //Moves the drawing location to the correct place on the screen.
    protected void renderLivingAt(EntityLivingBase pLivingEntity, double pRenderDataX, double pRenderDataY, double pRenderDataZ)
    {
        GL11.glTranslatef((float)pRenderDataX, (float)pRenderDataY, (float)pRenderDataZ);
    }

    //Function calculates the existence of the entity between two game ticks. (As the name says, used for rotation)
    protected float handleRotationFloat(EntityLivingBase pLivingEntity, float pPartialTickTime)
    {
        return (float)pLivingEntity.ticksExisted + pPartialTickTime;
    }

    //Rotates the model (corpse) of the player after death. And also flips the models of Dinnerbone and Grumm.
    protected void rotateCorpse(AbstractClientPlayer pPlayer, float pCurrentRotation, float pYOffset, float pPartialTickTime)
    {
        if (pPlayer.isEntityAlive() && pPlayer.isPlayerSleeping())
        {
            GL11.glRotatef(pPlayer.getBedOrientationInDegrees(), 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(this.getDeathMaxRotation(pPlayer), 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(270.0F, 0.0F, 1.0F, 0.0F);
        }
        else
        {
            this.rotateCorpse((EntityLivingBase) pPlayer, pCurrentRotation, pYOffset, pPartialTickTime);
        }
    }

    protected void rotateCorpse(EntityLivingBase pLivingEntity, float pCurrentRotation, float pYOffset, float pPartialTickTime)
    {
        GL11.glRotatef(180.0F - pYOffset, 0.0F, 1.0F, 0.0F);

        if (pLivingEntity.deathTime > 0)
        {
            float f3 = ((float)pLivingEntity.deathTime + pPartialTickTime - 1.0F) / 20.0F * 1.6F;
            f3 = MathHelper.sqrt_float(f3);

            if (f3 > 1.0F)
            {
                f3 = 1.0F;
            }

            GL11.glRotatef(f3 * this.getDeathMaxRotation(pLivingEntity), 0.0F, 0.0F, 1.0F);
        }
        else
        {
            String s = EnumChatFormatting.getTextWithoutFormattingCodes(pLivingEntity.getCommandSenderName());

            if ((s.equals("Dinnerbone") || s.equals("Grumm")) && (!(pLivingEntity instanceof EntityPlayer) || !((EntityPlayer)pLivingEntity).getHideCape()))
            {
                GL11.glTranslatef(0.0F, pLivingEntity.height + 0.1F, 0.0F);
                GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
            }
        }
    }

    //Returns the maximum angle for the death rotation (Usually 90 degrees)
    protected float getDeathMaxRotation(EntityLivingBase pLivingEntity)
    {
        return 90.0F;
    }

    //Called to prepare the model for drawing
    protected void preRenderCallback(AbstractClientPlayer pAbstractPlayer, float pPartialTickTime)
    {
        float f1 = 0.9375F;
        GL11.glScalef(f1, f1, f1);
    }

    //This method takes over the function of the EntityLiving renderer that is called by the super call in the doRender function of the player renderer.
    protected void renderPlayer (EntityLivingBase pEntity, double pRenderDataX, double pRenderDataY, double pRenderDataZ, float pUnknown, float pPartialTickTime)
    {
        //Notifying other mods that a living entity is being rendered.
        if (MinecraftForge.EVENT_BUS.post(new RenderLivingEvent.Pre(pEntity,(RendererLivingEntity) (Render) this, pRenderDataX, pRenderDataY, pRenderDataZ))) return;
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_CULL_FACE);
        this.iModelBipedMain.onGround = this.renderSwingProgress(pEntity, pPartialTickTime);

        if (this.iRenderPassModel != null)
        {
            this.iRenderPassModel.onGround = this.iModelBipedMain.onGround;
        }

        this.iModelBipedMain.isRiding = pEntity.isRiding();

        if (this.iRenderPassModel != null)
        {
            this.iRenderPassModel.isRiding = this.iModelBipedMain.isRiding;
        }

        this.iModelBipedMain.isChild = pEntity.isChild();

        if (this.iRenderPassModel != null)
        {
            this.iRenderPassModel.isChild = this.iModelBipedMain.isChild;
        }

        try
        {
            float f2 = this.interpolateRotation(pEntity.prevRenderYawOffset, pEntity.renderYawOffset, pPartialTickTime);
            float f3 = this.interpolateRotation(pEntity.prevRotationYawHead, pEntity.rotationYawHead, pPartialTickTime);
            float f4;

            if (pEntity.isRiding() && pEntity.ridingEntity instanceof EntityLivingBase)
            {
                EntityLivingBase entitylivingbase1 = (EntityLivingBase)pEntity.ridingEntity;
                f2 = this.interpolateRotation(entitylivingbase1.prevRenderYawOffset, entitylivingbase1.renderYawOffset, pPartialTickTime);
                f4 = MathHelper.wrapAngleTo180_float(f3 - f2);

                if (f4 < -85.0F)
                {
                    f4 = -85.0F;
                }

                if (f4 >= 85.0F)
                {
                    f4 = 85.0F;
                }

                f2 = f3 - f4;

                if (f4 * f4 > 2500.0F)
                {
                    f2 += f4 * 0.2F;
                }
            }

            float f13 = pEntity.prevRotationPitch + (pEntity.rotationPitch - pEntity.prevRotationPitch) * pPartialTickTime;
            this.renderLivingAt(pEntity, pRenderDataX, pRenderDataY, pRenderDataZ);
            f4 = this.handleRotationFloat(pEntity, pPartialTickTime);
            this.rotateCorpse(pEntity, f4, f2, pPartialTickTime);
            float f5 = 0.0625F;
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            GL11.glScalef(-1.0F, -1.0F, 1.0F);
            this.preRenderCallback((AbstractClientPlayer) pEntity, pPartialTickTime);
            GL11.glTranslatef(0.0F, -24.0F * f5 - 0.0078125F, 0.0F);
            float f6 = pEntity.prevLimbSwingAmount + (pEntity.limbSwingAmount - pEntity.prevLimbSwingAmount) * pPartialTickTime;
            float f7 = pEntity.limbSwing - pEntity.limbSwingAmount * (1.0F - pPartialTickTime);

            if (pEntity.isChild())
            {
                f7 *= 3.0F;
            }

            if (f6 > 1.0F)
            {
                f6 = 1.0F;
            }

            GL11.glEnable(GL11.GL_ALPHA_TEST);
            this.iModelBipedMain.setLivingAnimations(pEntity, f7, f6, pPartialTickTime);
            this.renderModel(pEntity, f7, f6, f4, f3 - f2, f13, f5);
            float f8;
            int j;
            float f9;
            float f10;

            for (int i = 0; i < 4; ++i)
            {
                j = this.shouldRenderPass(pEntity, i, pPartialTickTime);

                if (j > 0)
                {
                    this.iRenderPassModel.setLivingAnimations(pEntity, f7, f6, pPartialTickTime);
                    this.iRenderPassModel.render(pEntity, f7, f6, f4, f3 - f2, f13, f5);

                    if ((j & 240) == 16)
                    {
                        this.func_82408_c(pEntity, i, pPartialTickTime);
                        this.iRenderPassModel.render(pEntity, f7, f6, f4, f3 - f2, f13, f5);
                    }

                    if ((j & 15) == 15)
                    {
                        f8 = (float)pEntity.ticksExisted + pPartialTickTime;
                        this.bindTexture(RES_ITEM_GLINT);
                        GL11.glEnable(GL11.GL_BLEND);
                        f9 = 0.5F;
                        GL11.glColor4f(f9, f9, f9, 1.0F);
                        GL11.glDepthFunc(GL11.GL_EQUAL);
                        GL11.glDepthMask(false);

                        for (int k = 0; k < 2; ++k)
                        {
                            GL11.glDisable(GL11.GL_LIGHTING);
                            f10 = 0.76F;
                            GL11.glColor4f(0.5F * f10, 0.25F * f10, 0.8F * f10, 1.0F);
                            GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
                            GL11.glMatrixMode(GL11.GL_TEXTURE);
                            GL11.glLoadIdentity();
                            float f11 = f8 * (0.001F + (float)k * 0.003F) * 20.0F;
                            float f12 = 0.33333334F;
                            GL11.glScalef(f12, f12, f12);
                            GL11.glRotatef(30.0F - (float)k * 60.0F, 0.0F, 0.0F, 1.0F);
                            GL11.glTranslatef(0.0F, f11, 0.0F);
                            GL11.glMatrixMode(GL11.GL_MODELVIEW);
                            this.iRenderPassModel.render(pEntity, f7, f6, f4, f3 - f2, f13, f5);
                        }

                        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                        GL11.glMatrixMode(GL11.GL_TEXTURE);
                        GL11.glDepthMask(true);
                        GL11.glLoadIdentity();
                        GL11.glMatrixMode(GL11.GL_MODELVIEW);
                        GL11.glEnable(GL11.GL_LIGHTING);
                        GL11.glDisable(GL11.GL_BLEND);
                        GL11.glDepthFunc(GL11.GL_LEQUAL);
                    }

                    GL11.glDisable(GL11.GL_BLEND);
                    GL11.glEnable(GL11.GL_ALPHA_TEST);
                }
            }

            GL11.glDepthMask(true);
            this.renderEquippedItems(pEntity, pPartialTickTime);
            float f14 = pEntity.getBrightness(pPartialTickTime);
            j = this.getColorMultiplier(pEntity, f14, pPartialTickTime);
            OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);

            if ((j >> 24 & 255) > 0 || pEntity.hurtTime > 0 || pEntity.deathTime > 0)
            {
                GL11.glDisable(GL11.GL_TEXTURE_2D);
                GL11.glDisable(GL11.GL_ALPHA_TEST);
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                GL11.glDepthFunc(GL11.GL_EQUAL);

                if (pEntity.hurtTime > 0 || pEntity.deathTime > 0)
                {
                    GL11.glColor4f(f14, 0.0F, 0.0F, 0.4F);
                    this.iModelBipedMain.render(pEntity, f7, f6, f4, f3 - f2, f13, f5);

                    for (int l = 0; l < 4; ++l)
                    {
                        if (this.inheritRenderPass(pEntity, l, pPartialTickTime) >= 0)
                        {
                            GL11.glColor4f(f14, 0.0F, 0.0F, 0.4F);
                            this.iRenderPassModel.render(pEntity, f7, f6, f4, f3 - f2, f13, f5);
                        }
                    }
                }

                if ((j >> 24 & 255) > 0)
                {
                    f8 = (float)(j >> 16 & 255) / 255.0F;
                    f9 = (float)(j >> 8 & 255) / 255.0F;
                    float f15 = (float)(j & 255) / 255.0F;
                    f10 = (float)(j >> 24 & 255) / 255.0F;
                    GL11.glColor4f(f8, f9, f15, f10);
                    this.iModelBipedMain.render(pEntity, f7, f6, f4, f3 - f2, f13, f5);

                    for (int i1 = 0; i1 < 4; ++i1)
                    {
                        if (this.inheritRenderPass(pEntity, i1, pPartialTickTime) >= 0)
                        {
                            GL11.glColor4f(f8, f9, f15, f10);
                            this.iRenderPassModel.render(pEntity, f7, f6, f4, f3 - f2, f13, f5);
                        }
                    }
                }

                GL11.glDepthFunc(GL11.GL_LEQUAL);
                GL11.glDisable(GL11.GL_BLEND);
                GL11.glEnable(GL11.GL_ALPHA_TEST);
                GL11.glEnable(GL11.GL_TEXTURE_2D);
            }

            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        }
        catch (Exception exception)
        {
            iLogger.error("Couldn\'t render entity", exception);
        }

        OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glPopMatrix();
        this.passSpecialRender(pEntity, pRenderDataX, pRenderDataY, pRenderDataZ);
        MinecraftForge.EVENT_BUS.post(new RenderLivingEvent.Post(pEntity,(RenderPlayer) (RendererLivingEntity) (Render) this, pRenderDataX, pRenderDataY, pRenderDataZ));
    }



}

