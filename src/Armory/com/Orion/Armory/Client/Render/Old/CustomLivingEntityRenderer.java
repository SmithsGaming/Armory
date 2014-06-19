package com.Orion.Armory.Client.Render.Old;
/*
*   CustomLivingEntityRenderer
*   Created by: Orion
*   Created on: 28-4-2014
*/

import com.Orion.Armory.Client.ArmoryResource;
import com.Orion.OrionsBelt.Client.Models.AExtendedPlayerModel;
import com.Orion.Armory.Common.ARegistry;
import com.Orion.Armory.Common.Armor.ArmorCore;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.common.MinecraftForge;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class CustomLivingEntityRenderer extends RendererLivingEntity
{
    protected Logger iLogger = ARegistry.iLogger;

    public CustomLivingEntityRenderer(ModelBase par1ModelBase, float par2) {
        super(par1ModelBase, par2);
        if(this.renderManager == null)
        {
            this.setRenderManager(RenderManager.instance);
        }
    }

    private float interpolateRotation(float par1, float par2, float par3)
    {
        float f3;

        for (f3 = par2 - par1; f3 < -180.0F; f3 += 360.0F)
        {
            ;
        }

        while (f3 >= 180.0F)
        {
            f3 -= 360.0F;
        }

        return par1 + par3 * f3;
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(EntityLivingBase par1EntityLivingBase, double par2, double par4, double par6, float par8, float par9)
    {
        if (MinecraftForge.EVENT_BUS.post(new RenderLivingEvent.Pre(par1EntityLivingBase, this, par2, par4, par6))) return;
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_CULL_FACE);
        this.mainModel.onGround = this.renderSwingProgress(par1EntityLivingBase, par9);

        if (this.renderPassModel != null)
        {
            this.renderPassModel.onGround = this.mainModel.onGround;
        }

        this.mainModel.isRiding = par1EntityLivingBase.isRiding();

        if (this.renderPassModel != null)
        {
            this.renderPassModel.isRiding = this.mainModel.isRiding;
        }

        this.mainModel.isChild = par1EntityLivingBase.isChild();

        if (this.renderPassModel != null)
        {
            this.renderPassModel.isChild = this.mainModel.isChild;
        }

        try
        {
            float f2 = this.interpolateRotation(par1EntityLivingBase.prevRenderYawOffset, par1EntityLivingBase.renderYawOffset, par9);
            float f3 = this.interpolateRotation(par1EntityLivingBase.prevRotationYawHead, par1EntityLivingBase.rotationYawHead, par9);
            float f4;

            if (par1EntityLivingBase.isRiding() && par1EntityLivingBase.ridingEntity instanceof EntityLivingBase)
            {
                EntityLivingBase entitylivingbase1 = (EntityLivingBase)par1EntityLivingBase.ridingEntity;
                f2 = this.interpolateRotation(entitylivingbase1.prevRenderYawOffset, entitylivingbase1.renderYawOffset, par9);
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

            float f13 = par1EntityLivingBase.prevRotationPitch + (par1EntityLivingBase.rotationPitch - par1EntityLivingBase.prevRotationPitch) * par9;
            this.renderLivingAt(par1EntityLivingBase, par2, par4, par6);
            f4 = this.handleRotationFloat(par1EntityLivingBase, par9);
            this.rotateCorpse(par1EntityLivingBase, f4, f2, par9);
            float f5 = 0.0625F;
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            GL11.glScalef(-1.0F, -1.0F, 1.0F);
            this.preRenderCallback(par1EntityLivingBase, par9);
            GL11.glTranslatef(0.0F, -24.0F * f5 - 0.0078125F, 0.0F);
            float f6 = par1EntityLivingBase.prevLimbSwingAmount + (par1EntityLivingBase.limbSwingAmount - par1EntityLivingBase.prevLimbSwingAmount) * par9;
            float f7 = par1EntityLivingBase.limbSwing - par1EntityLivingBase.limbSwingAmount * (1.0F - par9);

            if (par1EntityLivingBase.isChild())
            {
                f7 *= 3.0F;
            }

            if (f6 > 1.0F)
            {
                f6 = 1.0F;
            }

            GL11.glEnable(GL11.GL_ALPHA_TEST);
            this.mainModel.setLivingAnimations(par1EntityLivingBase, f7, f6, par9);
            this.renderModel(par1EntityLivingBase, f7, f6, f4, f3 - f2, f13, f5);
            float f8;
            int j;
            float f9;
            float f10;

            for (int i = 0; i < 4; ++i)
            {
                j = this.shouldRenderPass(par1EntityLivingBase, i, par9);
                if(j==0)
                {
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                    GL11.glPopMatrix();
                    return;
                }
                if(j==1)
                {
                    ItemStack tArmorItemStack = (ItemStack) ((EntityPlayer) par1EntityLivingBase).inventory.armorItemInSlot(3 - i);
                    ArmorCore tCore = (ArmorCore) tArmorItemStack.getItem();
                    NBTTagCompound tRenderCompound = tArmorItemStack.getTagCompound().getCompoundTag("RenderCompound");
                    Integer tRenderPasses = tCore.getRenderPasses(tArmorItemStack);

                    for(int tCurrentRenderPass = 0; tCurrentRenderPass <= tRenderPasses; tCurrentRenderPass++)
                    {
                        NBTTagCompound tCurrentRenderCompound = tRenderCompound.getCompoundTag("RenderPass - " + tCurrentRenderPass);
                        ArmoryResource tCurrentResource = tCore.getResource(tCurrentRenderCompound);
                        
                        //Getting and setting the current color.
                        float tRed = tCurrentResource.getColor(0)/255;
                        float tGreen = tCurrentResource.getColor(1)/255;
                        float tBlue = tCurrentResource.getColor(2)/255;
                        GL11.glColor3f(tRed, tGreen, tBlue);

                        this.bindTexture(new ResourceLocation(tCurrentResource.getModelLocation()));

                        renderPassModel.setLivingAnimations(par1EntityLivingBase, f7, f6, par9);
                        renderPassModel.render((Entity) par1EntityLivingBase, f7, f6, f4, f3 - f2, f13, f5);

                        GL11.glColor3f(1.0F, 1.0F, 1.0F);
                    }
                }
            }

            GL11.glDepthMask(true);
            this.renderEquippedItems(par1EntityLivingBase, par9);
            float f14 = par1EntityLivingBase.getBrightness(par9);
            j = this.getColorMultiplier(par1EntityLivingBase, f14, par9);
            OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);

            if ((j >> 24 & 255) > 0 || par1EntityLivingBase.hurtTime > 0 || par1EntityLivingBase.deathTime > 0)
            {
                GL11.glDisable(GL11.GL_TEXTURE_2D);
                GL11.glDisable(GL11.GL_ALPHA_TEST);
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                GL11.glDepthFunc(GL11.GL_EQUAL);

                if (par1EntityLivingBase.hurtTime > 0 || par1EntityLivingBase.deathTime > 0)
                {
                    GL11.glColor4f(f14, 0.0F, 0.0F, 0.4F);
                    this.mainModel.render(par1EntityLivingBase, f7, f6, f4, f3 - f2, f13, f5);

                    for (int l = 0; l < 4; ++l)
                    {
                        if (this.inheritRenderPass(par1EntityLivingBase, l, par9) >= 0)
                        {
                            GL11.glColor4f(f14, 0.0F, 0.0F, 0.4F);
                            this.renderPassModel.render(par1EntityLivingBase, f7, f6, f4, f3 - f2, f13, f5);
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
                    this.mainModel.render(par1EntityLivingBase, f7, f6, f4, f3 - f2, f13, f5);

                    for (int i1 = 0; i1 < 4; ++i1)
                    {
                        if (this.inheritRenderPass(par1EntityLivingBase, i1, par9) >= 0)
                        {
                            GL11.glColor4f(f8, f9, f15, f10);
                            this.renderPassModel.render(par1EntityLivingBase, f7, f6, f4, f3 - f2, f13, f5);
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
        this.passSpecialRender(par1EntityLivingBase, par2, par4, par6);
        MinecraftForge.EVENT_BUS.post(new RenderLivingEvent.Post(par1EntityLivingBase, this, par2, par4, par6));
    }

    /**
     * Queries whether should render the specified pass or not.
     * TODO: Apply this check onto the new NBTTag system of the armor pieces.
     */
    protected int shouldRenderPass(AbstractClientPlayer par1AbstractClientPlayer, int pArmorPart, float par3)
    {
        ItemStack itemstack = par1AbstractClientPlayer.inventory.armorItemInSlot(3 - pArmorPart);

        if (itemstack != null)
        {
            Item item = itemstack.getItem();

            if(item instanceof ArmorCore)
            {
                ArmorCore tCore = (ArmorCore)item;

                AExtendedPlayerModel tExtendedModel = pArmorPart == 2 ? new AExtendedPlayerModel(0.5F) : new AExtendedPlayerModel(1.0F);
                tExtendedModel.bipedHead.showModel = pArmorPart == 0;
                tExtendedModel.bipedBody.showModel = pArmorPart == 1;
                tExtendedModel.bipedRightArm.showModel = pArmorPart == 1;
                tExtendedModel.bipedLeftArm.showModel = pArmorPart == 1;
                tExtendedModel.bipedWaist.showModel = pArmorPart == 2;
                tExtendedModel.bipedRightLeg.showModel = pArmorPart == 2;
                tExtendedModel.bipedLeftLeg.showModel = pArmorPart == 2;
                tExtendedModel.bipedRightFoot.showModel = pArmorPart == 3;
                tExtendedModel.bipedLeftFoot.showModel = pArmorPart == 3;
                this.setRenderPassModel(tExtendedModel);

                return 1;
            }
            else
            {
                return 0;
            }
        }

        return -1;
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity var1) {
        return null;
    }
}
