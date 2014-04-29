package com.Orion.Armory.Client.Models;

/*
/  AExtendedPlayerModel
/  Created by : Orion
/  Created on : 08/04/2014
*/


import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class AExtendedPlayerModel extends ModelBiped
{
    //fields
    public ModelRenderer bipedRightFoot;
    public ModelRenderer bipedLeftFoot;
    public ModelRenderer bipedWaist;

    public int heldItemLeft;
    public int heldItemRight;
    public boolean isSneak;
    public boolean aimedBow;
  
  public AExtendedPlayerModel(float pScale)
  {
      super(pScale, 0F, 64, 64);

      textureWidth = 64;
      textureHeight = 64;
    
      bipedHead = new ModelRenderer(this, 0, 0);
      bipedHead.addBox(-4F, -8F, -4F, 8, 8, 8, pScale);
      bipedHead.setRotationPoint(0F, 0F , 0F);
      bipedHead.setTextureSize(64, 64);
      bipedHead.mirror = true;
      setRotation(bipedHead, 0F, 0F, 0F);
      bipedBody = new ModelRenderer(this, 0, 16);
      bipedBody.addBox(-4F, 0F, -2F, 8, 12, 4, pScale);
      bipedBody.setRotationPoint(0F, 0F, 0F);
      bipedBody.setTextureSize(64, 64);
      bipedBody.mirror = true;
      setRotation(bipedBody, 0F, 0F, 0F);
      bipedRightArm = new ModelRenderer(this, 32, 16);
      bipedRightArm.addBox(-3F, -2F, -2F, 4, 12, 4, pScale);
      bipedRightArm.setRotationPoint(-5F, 2F, 0F);
      bipedRightArm.setTextureSize(64, 64);
      bipedRightArm.mirror = true;
      setRotation(bipedRightArm, 0F, 0F, 0F);
      bipedLeftArm = new ModelRenderer(this, 32, 0);
      bipedLeftArm.addBox(-1F, -2F, -2F, 4, 12, 4, pScale);
      bipedLeftArm.setRotationPoint(5F, 2F, 0F);
      bipedLeftArm.setTextureSize(64, 64);
      bipedLeftArm.mirror = true;
      setRotation(bipedLeftArm, 0F, 0F, 0F);
      bipedRightLeg = new ModelRenderer(this, 0, 32);
      bipedRightLeg.addBox(-2F, 0F, -2F, 4, 8, 4, pScale);
      bipedRightLeg.setRotationPoint(-2F, 12F, 0F);
      bipedRightLeg.setTextureSize(64, 64);
      bipedRightLeg.mirror = true;
      setRotation(bipedRightLeg, 0F, 0F, 0F);
      bipedRightFoot = new ModelRenderer(this, 0, 52);
      bipedRightFoot.addBox(-2F, 8F, -2F, 4, 5, 4, pScale);
      bipedRightFoot.setRotationPoint(-2F, 11F, 0F);
      bipedRightFoot.setTextureSize(64, 64);
      bipedRightFoot.mirror = true;
      setRotation(bipedRightFoot, 0F, 0F, 0F);
      bipedLeftLeg = new ModelRenderer(this, 16, 32);
      bipedLeftLeg.addBox(-2F, 0F, -2F, 4, 8, 4, pScale);
      bipedLeftLeg.setRotationPoint(2F, 12F, 0F);
      bipedLeftLeg.setTextureSize(64, 64);
      bipedLeftLeg.mirror = true;
      setRotation(bipedLeftLeg, 0F, 0F, 0F);
      bipedLeftFoot = new ModelRenderer(this, 16, 52);
      bipedLeftFoot.addBox(-2F, 8F, -2F, 4, 5, 4, pScale);
      bipedLeftFoot.setRotationPoint(2F, 11F, 0F);
      bipedLeftFoot.setTextureSize(64, 64);
      bipedLeftFoot.mirror = true;
      setRotation(bipedLeftFoot, 0F, 0F, 0F);
      bipedWaist = new ModelRenderer(this, 0,44);
      bipedWaist.addBox(-4F, 8F, -2F, 8,4,4, pScale);
      bipedWaist.setRotationPoint(0F,0F,0F);
      bipedWaist.setTextureSize(64,64);
      bipedWaist.mirror = true;
      setRotation(bipedWaist, 0F, 0F, 0F);


      this.bipedBody.addChild(bipedWaist);
      this.bipedLeftLeg.addChild(bipedLeftFoot);
      this.bipedRightLeg.addChild(bipedRightFoot);

  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
      super.render(entity, f, f1, f2, f3, f4, f5);
      setRotationAngles(f, f1, f2, f3, f4, f5, entity);
      bipedHead.render(f5);
      bipedBody.render(f5);
      bipedRightArm.render(f5);
      bipedLeftArm.render(f5);
      bipedRightLeg.render(f5);
      bipedRightFoot.render(f5);
      bipedLeftLeg.render(f5);
      bipedLeftFoot.render(f5);
      bipedWaist.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
      model.rotateAngleX = x;
      model.rotateAngleY = y;
      model.rotateAngleZ = z;
  }


    /*
    public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
    {
        this.bipedHead.rotateAngleY = par4 / (180F / (float)Math.PI);
        this.bipedHead.rotateAngleX = par5 / (180F / (float)Math.PI);
        this.bipedRightArm.rotateAngleX = MathHelper.cos(par1 * 0.6662F + (float) Math.PI) * 2.0F * par2 * 0.5F;
        this.bipedLeftArm.rotateAngleX = MathHelper.cos(par1 * 0.6662F) * 2.0F * par2 * 0.5F;
        this.bipedRightArm.rotateAngleZ = 0.0F;
        this.bipedLeftArm.rotateAngleZ = 0.0F;
        this.bipedRightLeg.rotateAngleX = MathHelper.cos(par1 * 0.6662F) * 1.4F * par2;
        this.bipedRightFoot.rotateAngleX = MathHelper.cos(par1 * 0.6662F) * 1.4F * par2;
        this.bipedLeftLeg.rotateAngleX = MathHelper.cos(par1 * 0.6662F + (float)Math.PI) * 1.4F * par2;
        this.bipedLeftFoot.rotateAngleX = MathHelper.cos(par1 * 0.6662F + (float)Math.PI) * 1.4F * par2;
        this.bipedRightLeg.rotateAngleY = 0.0F;
        this.bipedRightFoot.rotateAngleY = 0.0F;
        this.bipedLeftLeg.rotateAngleY = 0.0F;
        this.bipedLeftFoot.rotateAngleY= 0.0F;

        if (this.isRiding)
        {
            this.bipedRightArm.rotateAngleX += -((float)Math.PI / 5F);
            this.bipedLeftArm.rotateAngleX += -((float)Math.PI / 5F);
            this.bipedRightLeg.rotateAngleX = -((float)Math.PI * 2F / 5F);
            this.bipedLeftLeg.rotateAngleX = -((float)Math.PI * 2F / 5F);
            this.bipedRightLeg.rotateAngleY = ((float)Math.PI / 10F);
            this.bipedLeftLeg.rotateAngleY = -((float)Math.PI / 10F);
        }

        if (this.heldItemLeft != 0)
        {
            this.bipedLeftArm.rotateAngleX = this.bipedLeftArm.rotateAngleX * 0.5F - ((float)Math.PI / 10F) * (float)this.heldItemLeft;
        }

        if (this.heldItemRight != 0)
        {
            this.bipedRightArm.rotateAngleX = this.bipedRightArm.rotateAngleX * 0.5F - ((float)Math.PI / 10F) * (float)this.heldItemRight;
        }

        this.bipedRightArm.rotateAngleY = 0.0F;
        this.bipedLeftArm.rotateAngleY = 0.0F;
        float f6;
        float f7;

        if (this.onGround > -9990.0F)
        {
            f6 = this.onGround;
            this.bipedBody.rotateAngleY = MathHelper.sin(MathHelper.sqrt_float(f6) * (float)Math.PI * 2.0F) * 0.2F;
            this.bipedRightArm.rotationPointZ = MathHelper.sin(this.bipedBody.rotateAngleY) * 5.0F;
            this.bipedRightArm.rotationPointX = -MathHelper.cos(this.bipedBody.rotateAngleY) * 5.0F;
            this.bipedLeftArm.rotationPointZ = -MathHelper.sin(this.bipedBody.rotateAngleY) * 5.0F;
            this.bipedLeftArm.rotationPointX = MathHelper.cos(this.bipedBody.rotateAngleY) * 5.0F;
            this.bipedRightArm.rotateAngleY += this.bipedBody.rotateAngleY;
            this.bipedLeftArm.rotateAngleY += this.bipedBody.rotateAngleY;
            this.bipedLeftArm.rotateAngleX += this.bipedBody.rotateAngleY;
            f6 = 1.0F - this.onGround;
            f6 *= f6;
            f6 *= f6;
            f6 = 1.0F - f6;
            f7 = MathHelper.sin(f6 * (float)Math.PI);
            float f8 = MathHelper.sin(this.onGround * (float)Math.PI) * -(this.bipedHead.rotateAngleX - 0.7F) * 0.75F;
            this.bipedRightArm.rotateAngleX = (float)((double)this.bipedRightArm.rotateAngleX - ((double)f7 * 1.2D + (double)f8));
            this.bipedRightArm.rotateAngleY += this.bipedBody.rotateAngleY * 2.0F;
            this.bipedRightArm.rotateAngleZ = MathHelper.sin(this.onGround * (float)Math.PI) * -0.4F;
        }

        if (this.isSneak)
        {
            this.bipedBody.rotateAngleX = 0.5F;
            this.bipedWaist.rotateAngleX = 0.5F;
            this.bipedRightArm.rotateAngleX += 0.4F;
            this.bipedLeftArm.rotateAngleX += 0.4F;
            this.bipedRightLeg.rotationPointZ = 4.0F;
            this.bipedLeftLeg.rotationPointZ = 4.0F;
            this.bipedRightLeg.rotationPointY = 9.0F;
            this.bipedLeftLeg.rotationPointY = 9.0F;
            this.bipedHead.rotationPointY = 1.0F;
        }
        else
        {
            this.bipedBody.rotateAngleX = 0.0F;
            this.bipedWaist.rotateAngleX = 0.0F;
            this.bipedRightLeg.rotationPointZ = 0.1F;
            this.bipedLeftLeg.rotationPointZ = 0.1F;
            this.bipedRightLeg.rotationPointY = 12.0F;
            this.bipedLeftLeg.rotationPointY = 12.0F;
            this.bipedHead.rotationPointY = 0.0F;
        }

        this.bipedRightArm.rotateAngleZ += MathHelper.cos(par3 * 0.09F) * 0.05F + 0.05F;
        this.bipedLeftArm.rotateAngleZ -= MathHelper.cos(par3 * 0.09F) * 0.05F + 0.05F;
        this.bipedRightArm.rotateAngleX += MathHelper.sin(par3 * 0.067F) * 0.05F;
        this.bipedLeftArm.rotateAngleX -= MathHelper.sin(par3 * 0.067F) * 0.05F;

        if (this.aimedBow)
        {
            f6 = 0.0F;
            f7 = 0.0F;
            this.bipedRightArm.rotateAngleZ = 0.0F;
            this.bipedLeftArm.rotateAngleZ = 0.0F;
            this.bipedRightArm.rotateAngleY = -(0.1F - f6 * 0.6F) + this.bipedHead.rotateAngleY;
            this.bipedLeftArm.rotateAngleY = 0.1F - f6 * 0.6F + this.bipedHead.rotateAngleY + 0.4F;
            this.bipedRightArm.rotateAngleX = -((float)Math.PI / 2F) + this.bipedHead.rotateAngleX;
            this.bipedLeftArm.rotateAngleX = -((float)Math.PI / 2F) + this.bipedHead.rotateAngleX;
            this.bipedRightArm.rotateAngleX -= f6 * 1.2F - f7 * 0.4F;
            this.bipedLeftArm.rotateAngleX -= f6 * 1.2F - f7 * 0.4F;
            this.bipedRightArm.rotateAngleZ += MathHelper.cos(par3 * 0.09F) * 0.05F + 0.05F;
            this.bipedLeftArm.rotateAngleZ -= MathHelper.cos(par3 * 0.09F) * 0.05F + 0.05F;
            this.bipedRightArm.rotateAngleX += MathHelper.sin(par3 * 0.067F) * 0.05F;
            this.bipedLeftArm.rotateAngleX -= MathHelper.sin(par3 * 0.067F) * 0.05F;
        }
    }*/

}
