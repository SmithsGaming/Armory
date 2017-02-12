package com.smithsmodding.armory.api.client.render.armor;

import com.smithsmodding.armory.api.client.armor.IInWorldRenderableArmorComponent;
import com.smithsmodding.armory.api.common.armor.IMultiComponentArmor;
import com.smithsmodding.armory.api.util.client.ModelTransforms;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

/**
 * Created by Iddo on 8/6/2016.
 */
public class BodyArmorPartRenderer {

    private static final ModelBiped modelBiped = new ModelBiped(1);

    public static void render(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale, IInWorldRenderableArmorComponent armorPart, IBakedModel model, ItemStack itemStack) {
        modelBiped.setRotationAngles(limbSwing,limbSwingAmount,ageInTicks,netHeadYaw,headPitch,scale,entitylivingbaseIn);

        GlStateManager.pushMatrix();
        if (entitylivingbaseIn.isSneaking()) {
            GlStateManager.translate(0.0F, 0.2F, 0.0F);
        }

        armorPart.getRendererForArmor().postRender(0.0625f);

        ModelTransforms transforms = armorPart.getRenderTransforms();

        //fitting block to the body
        GlStateManager.translate(transforms.getRotationPointX(), transforms.getRotationPointY(), transforms.getRotationPointZ());
        if (transforms.getRotateAngleZ() != 0.0F) {
            GlStateManager.rotate(transforms.getRotateAngleZ() * (180F / (float) Math.PI), 0.0F, 0.0F, 1.0F);
        }

        if (transforms.getRotateAngleY() != 0.0F) {
            GlStateManager.rotate(transforms.getRotateAngleY() * (180F / (float) Math.PI), 0.0F, 1.0F, 0.0F);
        }

        if (transforms.getRotateAngleX() != 0.0F) {
            GlStateManager.rotate(transforms.getRotateAngleX() * (180F / (float) Math.PI), 1.0F, 0.0F, 0.0F);
        }

        GlStateManager.translate(transforms.getOffsetX(), transforms.getOffsetZ(), transforms.getOffsetZ());
        GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.scale(transforms.getBaseScale(), -transforms.getBaseScale(), -transforms.getBaseScale());


        Minecraft.getMinecraft().getRenderItem().renderItem(new ItemStack(Items.APPLE),model);
        //minecraft.getRenderItem().renderItem(new ItemStack(Items.APPLE), ItemCameraTransforms.TransformType.NONE);
        //Minecraft.getMinecraft().getRenderItem().renderItem(itemStack,model);

        GlStateManager.popMatrix();
    }



    /*public static ModelTransforms getTransformForPart(ModelType type){
        ModelTransforms t=new ModelTransforms();
        switch (type){

            case HAND_RIGHT:
                t.offsetX=-0.0625F;
                t.baseScale=0.3751F;
                break;
            case HAND_LEFT:
                t.offsetX=0.0625F;
                t.baseScale=0.3751F;
                break;
            case HEAD:
                t.offsetY=-0.25F;
                t.baseScale = 0.625F;
                break;
            case BODY:
                t.offsetY=-0.25F;
                break;
            case LEG_RIGHT:
                break;
            case LEG_LEFT:
                break;
            case BOOT_RIGHT:
                break;
            case BOOT_LEFT:
                break;
        }
        return t;
    }*/


/*
    public ModelRenderer getBiped(ModelType type) {
        switch (type) {

            case HAND_RIGHT:
                return modelBiped.bipedRightArm;
            case HAND_LEFT:
                return modelBiped.bipedLeftArm;
            case HEAD:
                return modelBiped.bipedHead;
            case BODY:
                break;
            case LEG_RIGHT:
                break;
            case LEG_LEFT:
                break;
            case BOOT_RIGHT:
                break;
            case BOOT_LEFT:
                break;
            default:
                return modelBiped.bipedHead;
        }
        return modelBiped.bipedBody;
    }*/

}
