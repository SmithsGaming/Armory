package com.Orion.Armory.Client.Models;

/*
/  AExtendedPlayerModel (Created with techne, just modified the setRotationAngles and added some parameters for players.)
/  Created by : Orion
/  Created on : 08/04/2014
*/


import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

class AExtendedPlayerModel extends ModelBase
{
    //fields
    public ModelRenderer head;
    public ModelRenderer body;
    public ModelRenderer rightarm;
    public ModelRenderer leftarm;
    public ModelRenderer rightleg;
    public ModelRenderer rightfoot;
    public ModelRenderer leftleg;
    public ModelRenderer leftfoot;

    public int heldItemLeft;
    public int heldItemRight;
    public boolean isSneak;
    public boolean aimedBow;
  
  public AExtendedPlayerModel(float pScale)
  {
      textureWidth = 64;
      textureHeight = 64;
    
      head = new ModelRenderer(this, 0, 0);
      head.addBox(-4F, -8F, -4F, 8, 8, 8, pScale);
      head.setRotationPoint(0F, 0F , 0F);
      head.setTextureSize(64, 64);
      head.mirror = true;
      setRotation(head, 0F, 0F, 0F);
      body = new ModelRenderer(this, 0, 16);
      body.addBox(-4F, 0F, -2F, 8, 12, 4, pScale);
      body.setRotationPoint(0F, 0F, 0F);
      body.setTextureSize(64, 64);
      body.mirror = true;
      setRotation(body, 0F, 0F, 0F);
      rightarm = new ModelRenderer(this, 32, 16);
      rightarm.addBox(-3F, -2F, -2F, 4, 12, 4, pScale);
      rightarm.setRotationPoint(-5F, 2F, 0F);
      rightarm.setTextureSize(64, 64);
      rightarm.mirror = true;
      setRotation(rightarm, 0F, 0F, 0F);
      leftarm = new ModelRenderer(this, 32, 0);
      leftarm.addBox(-1F, -2F, -2F, 4, 12, 4, pScale);
      leftarm.setRotationPoint(5F, 2F, 0F);
      leftarm.setTextureSize(64, 64);
      leftarm.mirror = true;
      setRotation(leftarm, 0F, 0F, 0F);
      rightleg = new ModelRenderer(this, 0, 32);
      rightleg.addBox(-2F, 0F, -2F, 4, 8, 4, pScale);
      rightleg.setRotationPoint(-2F, 12F, 0F);
      rightleg.setTextureSize(64, 64);
      rightleg.mirror = true;
      setRotation(rightleg, 0F, 0F, 0F);
      rightfoot = new ModelRenderer(this, 32, 40);
      rightfoot.addBox(-2F, 8F, -2F, 4, 4, 4, pScale);
      rightfoot.setRotationPoint(-2F, 12F, 0F);
      rightfoot.setTextureSize(64, 64);
      rightfoot.mirror = true;
      setRotation(rightfoot, 0F, 0F, 0F);
      leftleg = new ModelRenderer(this, 16, 32);
      leftleg.addBox(-2F, 0F, -2F, 4, 8, 4, pScale);
      leftleg.setRotationPoint(2F, 12F, 0F);
      leftleg.setTextureSize(64, 64);
      leftleg.mirror = true;
      setRotation(leftleg, 0F, 0F, 0F);
      leftfoot = new ModelRenderer(this, 32, 32);
      leftfoot.addBox(-2F, 8F, -2F, 4, 4, 4, pScale);
      leftfoot.setRotationPoint(2F, 12F, 0F);
      leftfoot.setTextureSize(64, 64);
      leftfoot.mirror = true;
      setRotation(leftfoot, 0F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
      super.render(entity, f, f1, f2, f3, f4, f5);
      setRotationAngles(f, f1, f2, f3, f4, f5, entity);
      head.render(f5);
      body.render(f5);
      rightarm.render(f5);
      leftarm.render(f5);
      rightleg.render(f5);
      rightfoot.render(f5);
      leftleg.render(f5);
      leftfoot.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
      model.rotateAngleX = x;
      model.rotateAngleY = y;
      model.rotateAngleZ = z;
  }

    public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
    {
        this.head.rotateAngleY = par4 / (180F / (float)Math.PI);
        this.head.rotateAngleX = par5 / (180F / (float)Math.PI);
        this.rightarm.rotateAngleX = MathHelper.cos(par1 * 0.6662F + (float) Math.PI) * 2.0F * par2 * 0.5F;
        this.leftarm.rotateAngleX = MathHelper.cos(par1 * 0.6662F) * 2.0F * par2 * 0.5F;
        this.rightarm.rotateAngleZ = 0.0F;
        this.leftarm.rotateAngleZ = 0.0F;
        this.rightleg.rotateAngleX = MathHelper.cos(par1 * 0.6662F) * 1.4F * par2;
        this.rightfoot.rotateAngleX = MathHelper.cos(par1 * 0.6662F) * 1.4F * par2;
        this.leftleg.rotateAngleX = MathHelper.cos(par1 * 0.6662F + (float)Math.PI) * 1.4F * par2;
        this.leftfoot.rotateAngleX = MathHelper.cos(par1 * 0.6662F + (float)Math.PI) * 1.4F * par2;
        this.rightleg.rotateAngleY = 0.0F;
        this.rightfoot.rotateAngleY = 0.0F;
        this.leftleg.rotateAngleY = 0.0F;
        this.leftfoot.rotateAngleY= 0.0F;

        if (this.isRiding)
        {
            this.rightarm.rotateAngleX += -((float)Math.PI / 5F);
            this.leftarm.rotateAngleX += -((float)Math.PI / 5F);
            this.rightleg.rotateAngleX = -((float)Math.PI * 2F / 5F);
            this.leftleg.rotateAngleX = -((float)Math.PI * 2F / 5F);
            this.rightleg.rotateAngleY = ((float)Math.PI / 10F);
            this.leftleg.rotateAngleY = -((float)Math.PI / 10F);
        }

        if (this.heldItemLeft != 0)
        {
            this.leftarm.rotateAngleX = this.leftarm.rotateAngleX * 0.5F - ((float)Math.PI / 10F) * (float)this.heldItemLeft;
        }

        if (this.heldItemRight != 0)
        {
            this.rightarm.rotateAngleX = this.rightarm.rotateAngleX * 0.5F - ((float)Math.PI / 10F) * (float)this.heldItemRight;
        }

        this.rightarm.rotateAngleY = 0.0F;
        this.leftarm.rotateAngleY = 0.0F;
        float f6;
        float f7;

        if (this.onGround > -9990.0F)
        {
            f6 = this.onGround;
            this.body.rotateAngleY = MathHelper.sin(MathHelper.sqrt_float(f6) * (float)Math.PI * 2.0F) * 0.2F;
            this.rightarm.rotationPointZ = MathHelper.sin(this.body.rotateAngleY) * 5.0F;
            this.rightarm.rotationPointX = -MathHelper.cos(this.body.rotateAngleY) * 5.0F;
            this.leftarm.rotationPointZ = -MathHelper.sin(this.body.rotateAngleY) * 5.0F;
            this.leftarm.rotationPointX = MathHelper.cos(this.body.rotateAngleY) * 5.0F;
            this.rightarm.rotateAngleY += this.body.rotateAngleY;
            this.leftarm.rotateAngleY += this.body.rotateAngleY;
            this.leftarm.rotateAngleX += this.body.rotateAngleY;
            f6 = 1.0F - this.onGround;
            f6 *= f6;
            f6 *= f6;
            f6 = 1.0F - f6;
            f7 = MathHelper.sin(f6 * (float)Math.PI);
            float f8 = MathHelper.sin(this.onGround * (float)Math.PI) * -(this.head.rotateAngleX - 0.7F) * 0.75F;
            this.rightarm.rotateAngleX = (float)((double)this.rightarm.rotateAngleX - ((double)f7 * 1.2D + (double)f8));
            this.rightarm.rotateAngleY += this.body.rotateAngleY * 2.0F;
            this.rightarm.rotateAngleZ = MathHelper.sin(this.onGround * (float)Math.PI) * -0.4F;
        }

        if (this.isSneak)
        {
            this.body.rotateAngleX = 0.5F;
            this.rightarm.rotateAngleX += 0.4F;
            this.leftarm.rotateAngleX += 0.4F;
            this.rightleg.rotationPointZ = 4.0F;
            this.leftleg.rotationPointZ = 4.0F;
            this.rightleg.rotationPointY = 9.0F;
            this.leftleg.rotationPointY = 9.0F;
            this.head.rotationPointY = 1.0F;
        }
        else
        {
            this.body.rotateAngleX = 0.0F;
            this.rightleg.rotationPointZ = 0.1F;
            this.leftleg.rotationPointZ = 0.1F;
            this.rightleg.rotationPointY = 12.0F;
            this.leftleg.rotationPointY = 12.0F;
            this.head.rotationPointY = 0.0F;
        }

        this.rightarm.rotateAngleZ += MathHelper.cos(par3 * 0.09F) * 0.05F + 0.05F;
        this.leftarm.rotateAngleZ -= MathHelper.cos(par3 * 0.09F) * 0.05F + 0.05F;
        this.rightarm.rotateAngleX += MathHelper.sin(par3 * 0.067F) * 0.05F;
        this.leftarm.rotateAngleX -= MathHelper.sin(par3 * 0.067F) * 0.05F;

        if (this.aimedBow)
        {
            f6 = 0.0F;
            f7 = 0.0F;
            this.rightarm.rotateAngleZ = 0.0F;
            this.leftarm.rotateAngleZ = 0.0F;
            this.rightarm.rotateAngleY = -(0.1F - f6 * 0.6F) + this.head.rotateAngleY;
            this.leftarm.rotateAngleY = 0.1F - f6 * 0.6F + this.head.rotateAngleY + 0.4F;
            this.rightarm.rotateAngleX = -((float)Math.PI / 2F) + this.head.rotateAngleX;
            this.leftarm.rotateAngleX = -((float)Math.PI / 2F) + this.head.rotateAngleX;
            this.rightarm.rotateAngleX -= f6 * 1.2F - f7 * 0.4F;
            this.leftarm.rotateAngleX -= f6 * 1.2F - f7 * 0.4F;
            this.rightarm.rotateAngleZ += MathHelper.cos(par3 * 0.09F) * 0.05F + 0.05F;
            this.leftarm.rotateAngleZ -= MathHelper.cos(par3 * 0.09F) * 0.05F + 0.05F;
            this.rightarm.rotateAngleX += MathHelper.sin(par3 * 0.067F) * 0.05F;
            this.leftarm.rotateAngleX -= MathHelper.sin(par3 * 0.067F) * 0.05F;
        }
    }

}
