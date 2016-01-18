package com.smithsmodding.armory.client.model.Entity;
/*
 *   ModelAExtendenBiped
 *   Created by: Orion
 *   Created on: 23-9-2014
 */

import com.smithsmodding.armory.api.armor.*;
import com.smithsmodding.armory.api.materials.*;
import com.smithsmodding.armory.common.material.*;
import com.smithsmodding.armory.util.Armor.*;
import com.smithsmodding.smithscore.util.client.color.*;
import net.minecraft.client.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import org.lwjgl.opengl.*;

import java.util.*;

public class ModelExtendedBiped extends ModelBiped {
    public ModelRenderer bipedRightFoot;
    public ModelRenderer bipedLeftFoot;
    public ModelRenderer bipedWaist;

    private ItemStack iStackToBeRendered;

    private float scaleFactor;

    public ModelExtendedBiped (float pScaleFactor, ItemStack pStackToBeRendered) {
        textureWidth = 64;
        textureHeight = 64;

        bipedHead = new ModelRenderer(this, 0, 0);
        bipedHead.addBox(-4F, -8F, -4F, 8, 8, 8, pScaleFactor);
        bipedHead.setRotationPoint(0F, 0F, 0F);
        bipedHead.setTextureSize(64, 32);
        bipedHead.mirror = true;
        setRotation(bipedHead, 0F, 0F, 0F);
        bipedBody = new ModelRenderer(this, 0, 16);
        bipedBody.addBox(-4F, 0F, -2F, 8, 12, 4, pScaleFactor);
        bipedBody.setRotationPoint(0F, 0F, 0F);
        bipedBody.setTextureSize(64, 32);
        bipedBody.mirror = true;
        setRotation(bipedBody, 0F, 0F, 0F);
        bipedRightArm = new ModelRenderer(this, 32, 16);
        bipedRightArm.addBox(-3F, -2F, -2F, 4, 12, 4, pScaleFactor);
        bipedRightArm.setRotationPoint(-5F, 2F, 0F);
        bipedRightArm.setTextureSize(64, 32);
        bipedRightArm.mirror = true;
        setRotation(bipedRightArm, 0F, 0F, 0F);
        bipedLeftArm = new ModelRenderer(this, 32, 0);
        bipedLeftArm.addBox(-1F, -2F, -2F, 4, 12, 4, pScaleFactor);
        bipedLeftArm.setRotationPoint(5F, 2F, 0F);
        bipedLeftArm.setTextureSize(64, 32);
        bipedLeftArm.mirror = true;
        setRotation(bipedLeftArm, 0F, 0F, 0F);
        bipedRightLeg = new ModelRenderer(this, 0, 32);
        bipedRightLeg.addBox(-2F, 0F, -2F, 4, 8, 4, pScaleFactor);
        bipedRightLeg.setRotationPoint(-2F, 12F, 0F);
        bipedRightLeg.setTextureSize(64, 32);
        bipedRightLeg.mirror = true;
        setRotation(bipedRightLeg, 0F, 0F, 0F);
        bipedRightFoot = new ModelRenderer(this, 16, 52);
        bipedRightFoot.addBox(-2F, 8F, -2F, 4, 4, 4, pScaleFactor);
        bipedRightFoot.setRotationPoint(-2F, 12F, 0F);
        bipedRightFoot.setTextureSize(64, 32);
        bipedRightFoot.mirror = true;
        setRotation(bipedRightFoot, 0F, 0F, 0F);
        bipedLeftLeg = new ModelRenderer(this, 16, 32);
        bipedLeftLeg.addBox(-2F, 0F, -2F, 4, 8, 4, pScaleFactor);
        bipedLeftLeg.setRotationPoint(2F, 12F, 0F);
        bipedLeftLeg.setTextureSize(64, 32);
        bipedLeftLeg.mirror = true;
        setRotation(bipedLeftLeg, 0F, 0F, 0F);
        bipedLeftFoot = new ModelRenderer(this, 0, 52);
        bipedLeftFoot.addBox(-2F, 8F, -2F, 4, 4, 4, pScaleFactor);
        bipedLeftFoot.setRotationPoint(2F, 12F, 0F);
        bipedLeftFoot.setTextureSize(64, 32);
        bipedLeftFoot.mirror = true;
        setRotation(bipedLeftFoot, 0F, 0F, 0F);
        bipedWaist = new ModelRenderer(this, 0, 44);
        bipedWaist.addBox(-4F, 8F, -2F, 8, 4, 4, pScaleFactor);
        bipedWaist.setRotationPoint(0F, 0F, 0F);
        bipedWaist.setTextureSize(64, 32);
        bipedWaist.mirror = true;
        setRotation(bipedWaist, 0F, 0F, 0F);

        this.iStackToBeRendered = pStackToBeRendered;

        this.scaleFactor = pScaleFactor;
    }

    private static void renderLayer (Entity entity, float f, float f1, float f2, float f3, float f4, float f5, ModelExtendedBiped layerModel, MLAAddon layer, MultiLayeredArmor armor) {
        layerModel.setRotationAngles(f, f1, f2, f3, f4, f5, entity);

        layerModel.isChild = ( (EntityLivingBase) entity ).isChild();

        ResourceLocation modelLocation = layer.getModelTextureLocation();
        Minecraft.getMinecraft().renderEngine.bindTexture(modelLocation);

        handleArmorType(armor.getArmorIndex(), layerModel);

        if (layer.isMaterialDependent()) {
            IArmorMaterial material = MaterialRegistry.getInstance().getMaterial(( (MaterialDependentMLAAddon) layer ).getUniqueMaterialID());
            material.getRenderInfo().getVertexColor().performOpenGLColoring();
        }

        if (layerModel.isChild) {
            float f6 = 2.0F;
            GL11.glPushMatrix();
            GL11.glScalef(1.5F / f6, 1.5F / f6, 1.5F / f6);
            GL11.glTranslatef(0.0F, 16.0F * f5, 0.0F);
            layerModel.bipedHead.render(f5);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glScalef(1.0F / f6, 1.0F / f6, 1.0F / f6);
            GL11.glTranslatef(0.0F, 24.0F * f5, 0.0F);
            layerModel.bipedBody.render(f5);
            layerModel.bipedWaist.render(f5);
            layerModel.bipedRightArm.render(f5);
            layerModel.bipedLeftArm.render(f5);
            layerModel.bipedRightLeg.render(f5);
            layerModel.bipedRightFoot.render(f5);
            layerModel.bipedLeftLeg.render(f5);
            layerModel.bipedLeftFoot.render(f5);
            GL11.glPopMatrix();
        } else {
            layerModel.bipedHead.render(f5);
            layerModel.bipedWaist.render(f5);
            layerModel.bipedBody.render(f5);
            layerModel.bipedRightArm.render(f5);
            layerModel.bipedLeftArm.render(f5);
            layerModel.bipedRightLeg.render(f5);
            layerModel.bipedRightFoot.render(f5);
            layerModel.bipedLeftLeg.render(f5);
            layerModel.bipedLeftFoot.render(f5);
        }

        if (layer.isMaterialDependent())
            MinecraftColor.resetOpenGLColoring();
    }

    private static void handleArmorType (int armorIndex, ModelExtendedBiped model) {
        switch (armorIndex) {
            case 0:
                model.bipedHead.showModel = true;
                model.bipedBody.showModel = model.bipedRightArm.showModel = model.bipedLeftArm.showModel = false;
                model.bipedWaist.showModel = model.bipedRightLeg.showModel = model.bipedLeftLeg.showModel = false;
                model.bipedRightFoot.showModel = model.bipedLeftFoot.showModel = false;
                break;
            case 1:
                model.bipedHead.showModel = false;
                model.bipedBody.showModel = model.bipedRightArm.showModel = model.bipedLeftArm.showModel = true;
                model.bipedWaist.showModel = model.bipedRightLeg.showModel = model.bipedLeftLeg.showModel = false;
                model.bipedRightFoot.showModel = model.bipedLeftFoot.showModel = false;
                break;
            case 2:
                model.bipedHead.showModel = false;
                model.bipedBody.showModel = model.bipedRightArm.showModel = model.bipedLeftArm.showModel = false;
                model.bipedWaist.showModel = model.bipedRightLeg.showModel = model.bipedLeftLeg.showModel = true;
                model.bipedRightFoot.showModel = model.bipedLeftFoot.showModel = false;
                break;
            case 3:
                model.bipedHead.showModel = false;
                model.bipedBody.showModel = model.bipedRightArm.showModel = model.bipedLeftArm.showModel = false;
                model.bipedWaist.showModel = model.bipedRightLeg.showModel = model.bipedLeftLeg.showModel = false;
                model.bipedRightFoot.showModel = model.bipedLeftFoot.showModel = true;
                break;
            default:
                model.bipedHead.showModel = false;
                model.bipedBody.showModel = model.bipedRightArm.showModel = model.bipedLeftArm.showModel = false;
                model.bipedWaist.showModel = model.bipedRightLeg.showModel = model.bipedLeftLeg.showModel = false;
                model.bipedRightFoot.showModel = model.bipedLeftFoot.showModel = false;
                break;
        }
    }

    private void setRotation (ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    @Override
    public void setRotationAngles (float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity) {
        super.setRotationAngles(par1, par2, par2, par4, par5, par6, par7Entity);

        bipedRightFoot.rotateAngleX = bipedRightLeg.rotateAngleX;
        bipedRightFoot.rotateAngleY = bipedRightLeg.rotateAngleY;
        bipedRightFoot.rotationPointZ = bipedRightLeg.rotationPointZ;
        bipedRightFoot.rotationPointY = bipedRightLeg.rotationPointY;

        bipedLeftFoot.rotateAngleX = bipedLeftLeg.rotateAngleX;
        bipedLeftFoot.rotateAngleY = bipedLeftLeg.rotateAngleY;
        bipedLeftFoot.rotationPointZ = bipedLeftLeg.rotationPointZ;
        bipedLeftFoot.rotationPointY = bipedLeftLeg.rotationPointY;

        bipedWaist.rotateAngleX = bipedBody.rotateAngleX;
        bipedWaist.rotateAngleY = bipedBody.rotateAngleY;

        if (par7Entity.isSneaking()) {
            this.bipedBody.rotateAngleX = 0.5F;
            this.bipedRightArm.rotateAngleX += 0.4F;
            this.bipedLeftArm.rotateAngleX += 0.4F;
            this.bipedRightLeg.rotationPointZ = 4.0F;
            this.bipedLeftLeg.rotationPointZ = 4.0F;
            this.bipedRightLeg.rotationPointY = 9.0F;
            this.bipedLeftLeg.rotationPointY = 9.0F;
            this.bipedRightFoot.rotationPointZ = this.bipedRightLeg.rotationPointZ;
            this.bipedRightFoot.rotationPointX = this.bipedRightLeg.rotationPointX;
            this.bipedLeftFoot.rotationPointZ = this.bipedLeftLeg.rotationPointZ;
            this.bipedLeftFoot.rotationPointX = this.bipedLeftLeg.rotationPointX;
            this.bipedHead.rotationPointY = 1.0F;
            this.bipedHeadwear.rotationPointY = 1.0F;
        }
    }

    @Override
    public void render (Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        MultiLayeredArmor tArmor = (MultiLayeredArmor) iStackToBeRendered.getItem();

        ArrayList<MLAAddon> installedAddons = new ArrayList<MLAAddon>();
        installedAddons.addAll(ArmorNBTHelper.getAddonMap(iStackToBeRendered).keySet());

        //Sort the list based on priority.
        Collections.sort(installedAddons, new Comparator<MLAAddon>() {
            @Override
            public int compare (MLAAddon o1, MLAAddon o2) {
                return Integer.compare(o1.getLayerPriority(), o2.getLayerPriority());
            }
        });

        for (MLAAddon addon : installedAddons) {
            renderLayer(entity, f, f1, f2, f3, f4, f5, new ModelExtendedBiped(scaleFactor, iStackToBeRendered), addon, tArmor);
            scaleFactor += 0.001F;
        }

    }

}

