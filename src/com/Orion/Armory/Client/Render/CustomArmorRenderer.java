package com.Orion.Armory.Client.Render;
/*
*   CustomArmorRenderer
*   Created by: Orion
*   Created on: 2-4-2014
*/

import com.Orion.Armory.Client.ArmoryResource;
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
import net.minecraft.nbt.NBTTagCompound;
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

    public void doRender(EntityLivingBase par1EntityLivingBase, double x, double y, double z, Item currentArmor,ItemStack currentArmorItemStack, float partialTickTime)
    {
        try
        {
            ArmorCore ACore = (ArmorCore) currentArmor;
            int armorSlotID = ACore.iArmorPart;

            //float f2 = this.interpolateRotation(par1EntityLivingBase.prevRenderYawOffset, par1EntityLivingBase.renderYawOffset, partialTickTime);
            //float f3 = this.interpolateRotation(par1EntityLivingBase.prevRotationYawHead, par1EntityLivingBase.rotationYawHead, partialTickTime);
            float f2 = par1EntityLivingBase.renderYawOffset;
            float f3 = par1EntityLivingBase.rotationYawHead;

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

            f4 = this.handleRotationFloat(par1EntityLivingBase, partialTickTime);
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

            AExtendedPlayerModel tModel = armorSlotID == 2 ? new AExtendedPlayerModel(0.5F) : new AExtendedPlayerModel(1F);
            tModel.bipedHead.showModel = armorSlotID == 0;
            tModel.bipedBody.showModel = armorSlotID == 1;
            tModel.bipedRightArm.showModel = armorSlotID == 1;
            tModel.bipedLeftArm.showModel = armorSlotID == 1;
            tModel.bipedWaist.showModel = armorSlotID == 2;
            tModel.bipedRightLeg.showModel = armorSlotID == 2;
            tModel.bipedLeftLeg.showModel = armorSlotID == 2;
            tModel.bipedRightFoot.showModel = armorSlotID == 3;
            tModel.bipedLeftFoot.showModel = armorSlotID == 3;
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

            NBTTagCompound tRenderCompound = currentArmorItemStack.getTagCompound().getCompoundTag("RenderCompound");
            int RenderPasses = ACore.getRenderPasses(currentArmorItemStack);

            GL11.glScalef(1.5F, 1.5F, 1.5F);
            //GL11.glTranslatef(0F, -0.7F, 0F);



            for (int currentRender = 0; currentRender <= RenderPasses; currentRender++)
            {
                ArmoryResource tResource = ACore.getResource(tRenderCompound.getCompoundTag("RenderPass - " + currentArmor).getInteger("ResourceID"));

                float tRed = tResource.getColor(0) / 255.0F;
                float tGreen = tResource.getColor(1) / 255.0F;
                float tBlue = tResource.getColor(2) / 255.0F;

                GL11.glColor4f(tRed, tGreen, tBlue, 1.0F);

                this.bindTexture(new ResourceLocation(tResource.getModelLocation()));
                tModel.render(par1EntityLivingBase, f7, f6, f4, f3 - f2, f13, f5);

                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            }

            //GL11.glScalef(0F, 0F, 0F);
        }
        catch (Exception exception)
        {
            ARegistry.iLogger.error("Couldn\'t render entity", exception);
        }
    }
}
