package com.Orion.Armory.Client.Render;
/*
*   CustomArmorRenderer
*   Created by: Orion
*   Created on: 2-4-2014
*/

import com.Orion.Armory.Common.Armor.ArmorCore;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class CustomArmorRenderer extends RenderPlayer
{
    private static final Logger logger = LogManager.getLogger();
    public CustomArmorRenderer()
    {
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


    public void renderArmorPiece(EntityLivingBase par1EntityLivingBase, double x, double y, double z, Item currentArmor,ItemStack currentArmorItemStack, int armorSlotID)
    {
        //try
        //{
            float f2 = this.interpolateRotation(par1EntityLivingBase.prevRenderYawOffset, par1EntityLivingBase.renderYawOffset, 0);
            float f3 = this.interpolateRotation(par1EntityLivingBase.prevRotationYawHead, par1EntityLivingBase.rotationYawHead, 0);
            float f4;

            if (par1EntityLivingBase.isRiding() && par1EntityLivingBase.ridingEntity instanceof EntityLivingBase)
            {
                EntityLivingBase entitylivingbase1 = (EntityLivingBase)par1EntityLivingBase.ridingEntity;
                f2 = this.interpolateRotation(entitylivingbase1.prevRenderYawOffset, entitylivingbase1.renderYawOffset, 0);
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

            float f13 = par1EntityLivingBase.prevRotationPitch + par1EntityLivingBase.rotationPitch;

            f4 = this.handleRotationFloat(par1EntityLivingBase, 0);
            float f5 = 0.0625F;
            float f6 = par1EntityLivingBase.prevLimbSwingAmount + (par1EntityLivingBase.limbSwingAmount - par1EntityLivingBase.prevLimbSwingAmount);
            float f7 = par1EntityLivingBase.limbSwing - par1EntityLivingBase.limbSwingAmount * (1.0F);

            if (par1EntityLivingBase.isChild())
            {
                f7 *= 3.0F;
            }

            if (f6 > 1.0F)
            {
                f6 = 1.0F;
            }

            ModelBiped modelbiped = armorSlotID == 2 ? new ModelBiped(0.5F) : new ModelBiped(1.0F);
            modelbiped.bipedHead.showModel = armorSlotID == 0;
            modelbiped.bipedHeadwear.showModel = armorSlotID == 0;
            modelbiped.bipedBody.showModel = armorSlotID == 1 || armorSlotID == 2;
            modelbiped.bipedRightArm.showModel = armorSlotID == 1;
            modelbiped.bipedLeftArm.showModel = armorSlotID == 1;
            modelbiped.bipedRightLeg.showModel = armorSlotID == 2 || armorSlotID == 3;
            modelbiped.bipedLeftLeg.showModel = armorSlotID == 2 || armorSlotID == 3;
            modelbiped = ForgeHooksClient.getArmorModel((AbstractClientPlayer) par1EntityLivingBase, currentArmorItemStack, armorSlotID, modelbiped);
            this.setRenderPassModel(modelbiped);
            modelbiped.onGround = this.mainModel.onGround;
            modelbiped.isRiding = this.mainModel.isRiding;
            modelbiped.isChild = this.mainModel.isChild;

            GL11.glScalef(2.0F, 2.0F, 2.0F);
            GL11.glTranslatef(0F, -0.75F, 0F);

            int renderAmount = currentArmor.getRenderPasses(0);
            ArmorCore ACore = (ArmorCore) currentArmor;
            for (int currentRender = 1; currentRender <= renderAmount; currentRender++)
            {
                this.bindTexture(new ResourceLocation(ACore.getArmorTextureLocation(currentArmorItemStack, currentRender)));
                this.renderPassModel.setLivingAnimations(par1EntityLivingBase, f7, f6, 0);
                this.renderPassModel.render(par1EntityLivingBase, f7, f6, f4, f3 - f2, f13, f5);
            }
        //}
        //catch (Exception exception)
        //{
        //    logger.error("Couldn\'t render entity", exception);
        //}
    }
}
