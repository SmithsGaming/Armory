package com.Orion.Armory.Client.Render;
/*
*   CustomArmorRenderer
*   Created by: Orion
*   Created on: 2-4-2014
*/

import com.Orion.Armory.Client.Models.AExtendedPlayerModel;
import com.Orion.Armory.Common.ARegistry;
import com.Orion.Armory.Common.Armor.ArmorCore;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class CustomArmorRenderer extends RendererLivingEntity
{
    public CustomArmorRenderer()
    {
        super(new AExtendedPlayerModel(0F), 0F);
        if(this.renderManager == null)
        {
            this.setRenderManager(RenderManager.instance);
        }
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity var1) {
        return null;
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

    public void doRender(EntityLivingBase par1EntityLivingBase, double x, double y, double z, Item currentArmor,ItemStack currentArmorItemStack, int armorSlotID)
    {
        try
        {
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

            AExtendedPlayerModel tModel = armorSlotID == 2 ? new AExtendedPlayerModel(0.5F) : new AExtendedPlayerModel(1.0F);
            tModel.head.showModel = armorSlotID == 0;
            tModel.body.showModel = armorSlotID == 1 || armorSlotID == 2;
            tModel.rightarm.showModel = armorSlotID == 1;
            tModel.leftarm.showModel = armorSlotID == 1;
            tModel.rightleg.showModel = armorSlotID == 2 || armorSlotID == 3;
            tModel.leftleg.showModel = armorSlotID == 2 || armorSlotID == 3;
            tModel.onGround = this.mainModel.onGround;
            tModel.isRiding = this.mainModel.isRiding;
            tModel.isChild = this.mainModel.isChild;

            tModel.isSneak = par1EntityLivingBase.isSneaking();

            ItemStack itemstack = ((AbstractClientPlayer) par1EntityLivingBase).inventory.getCurrentItem();
            tModel.heldItemRight = itemstack != null ? 1 : 0;

            if (itemstack != null && ((AbstractClientPlayer) par1EntityLivingBase).getItemInUseCount() > 0)
            {
                EnumAction enumaction = itemstack.getItemUseAction();

                if (enumaction == EnumAction.block)
                {
                    tModel.heldItemRight = 3;
                }
                else if (enumaction == EnumAction.bow)
                {
                    tModel.aimedBow = true;
                }
            }

            GL11.glScalef(2.0F, 2.0F, 2.0F);
            GL11.glTranslatef(0F, -0.75F, 0F);

            int renderAmount = currentArmor.getRenderPasses(0);
            ArmorCore ACore = (ArmorCore) currentArmor;
            for (int currentRender = 1; currentRender <= renderAmount; currentRender++)
            {
                this.bindTexture(new ResourceLocation(ACore.getArmorTextureLocation(currentArmorItemStack, currentRender)));
                tModel.render(par1EntityLivingBase, f7, f6, f4, f3 - f2, f13, f5);
            }
        }
        catch (Exception exception)
        {
            ARegistry.iLogger.error("Couldn\'t render entity", exception);
        }
    }
}
