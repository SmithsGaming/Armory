package com.Orion.Armory.Client.Render;
/*
*   ArmoryPlayerRenderer
*   Created by: Orion
*   Created on: 29-4-2014
*/

/*
import com.Orion.OrionsBelt.Client.Models.AExtendedPlayerModel;
import com.Orion.Armory.Common.ARegistry;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.common.MinecraftForge;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import java.util.Random;

import static net.minecraftforge.client.IItemRenderer.ItemRenderType.EQUIPPED;
import static net.minecraftforge.client.IItemRenderer.ItemRendererHelper.BLOCK_3D;
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
    //->Player
    protected void renderLivingAt(AbstractClientPlayer pPlayer, double pRenderDataX, double pRenderDataY, double pRenderDataZ)
    {
        if (pPlayer.isEntityAlive() && pPlayer.isPlayerSleeping())
        {
            this.renderLivingAt((EntityLivingBase) pPlayer, pRenderDataX + (double) pPlayer.field_71079_bU, pRenderDataY + (double) pPlayer.field_71082_cx, pRenderDataZ + (double) pPlayer.field_71089_bV);
        }
        else
        {
            this.renderLivingAt((EntityLivingBase) pPlayer, pRenderDataX, pRenderDataY, pRenderDataZ);
        }
    }

    //LivingEntity
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
    //-> Player
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

    //->LivingEntity
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
    //-> Player
    protected void preRenderCallback(AbstractClientPlayer pAbstractPlayer, float pPartialTickTime)
    {
        float f1 = 0.9375F;
        GL11.glScalef(f1, f1, f1);
    }

    //Renders the actual player model
    protected void renderModel(EntityLivingBase pLivingEntity, float pSwingRotation, float pPreviousSwingRotation, float pRotationFloat, float pRenderDataY, float pRotationPitch, float pUnknown)
    {
        this.bindEntityTexture(pLivingEntity);

        if (!pLivingEntity.isInvisible())
        {
            this.iModelBipedMain.render(pLivingEntity, pSwingRotation, pPreviousSwingRotation, pRotationFloat, pRenderDataY, pRotationPitch, pUnknown);
        }
        else if (!pLivingEntity.isInvisibleToPlayer(Minecraft.getMinecraft().thePlayer))
        {
            GL11.glPushMatrix();
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.15F);
            GL11.glDepthMask(false);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glAlphaFunc(GL11.GL_GREATER, 0.003921569F);
            this.iModelBipedMain.render(pLivingEntity, pSwingRotation, pPreviousSwingRotation, pRotationFloat, pRenderDataY, pRotationPitch, pUnknown);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
            GL11.glPopMatrix();
            GL11.glDepthMask(true);
        }
        else
        {
            this.iModelBipedMain.setRotationAngles(pSwingRotation, pPreviousSwingRotation, pRotationFloat, pRenderDataY, pRotationPitch, pUnknown, pLivingEntity);
        }
    }

    //Renders the equipped item on the model
    //-> Player
    protected void renderEquippedItems(AbstractClientPlayer pPlayer, float pPartialTickTime)
    {
        RenderPlayerEvent.Specials.Pre event = new RenderPlayerEvent.Specials.Pre(pPlayer,(RenderPlayer) (RendererLivingEntity) (Render) this, pPartialTickTime);
        if (MinecraftForge.EVENT_BUS.post(event))
        {
            return;
        }

        GL11.glColor3f(1.0F, 1.0F, 1.0F);
        this.renderArrowsStuckInEntity(pPlayer, pPartialTickTime);
        ItemStack itemstack = pPlayer.inventory.armorItemInSlot(3);

        if (itemstack != null && event.renderHelmet)
        {
            GL11.glPushMatrix();
            this.iModelBipedMain.bipedHead.postRender(0.0625F);
            float f1;

            if (itemstack.getItem() instanceof ItemBlock)
            {
                IItemRenderer customRenderer = MinecraftForgeClient.getItemRenderer(itemstack, EQUIPPED);
                boolean is3D = (customRenderer != null && customRenderer.shouldUseRenderHelper(EQUIPPED, itemstack, BLOCK_3D));

                if (is3D || RenderBlocks.renderItemIn3d(Block.getBlockFromItem(itemstack.getItem()).getRenderType()))
                {
                    f1 = 0.625F;
                    GL11.glTranslatef(0.0F, -0.25F, 0.0F);
                    GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
                    GL11.glScalef(f1, -f1, -f1);
                }

                this.renderManager.itemRenderer.renderItem(pPlayer, itemstack, 0);
            }
            else if (itemstack.getItem() == Items.skull)
            {
                f1 = 1.0625F;
                GL11.glScalef(f1, -f1, -f1);
                String s = "";

                if (itemstack.hasTagCompound() && itemstack.getTagCompound().hasKey("SkullOwner", 8))
                {
                    s = itemstack.getTagCompound().getString("SkullOwner");
                }

                TileEntitySkullRenderer.field_147536_b.func_147530_a(-0.5F, 0.0F, -0.5F, 1, 180.0F, itemstack.getItemDamage(), s);
            }

            GL11.glPopMatrix();
        }

        float f3;

        if (pPlayer.getCommandSenderName().equals("deadmau5") && pPlayer.getTextureSkin().isTextureUploaded())
        {
            this.bindTexture(pPlayer.getLocationSkin());

            for (int j = 0; j < 2; ++j)
            {
                float f10 = pPlayer.prevRotationYaw + (pPlayer.rotationYaw - pPlayer.prevRotationYaw) * pPartialTickTime - (pPlayer.prevRenderYawOffset + (pPlayer.renderYawOffset - pPlayer.prevRenderYawOffset) * pPartialTickTime);
                float f2 = pPlayer.prevRotationPitch + (pPlayer.rotationPitch - pPlayer.prevRotationPitch) * pPartialTickTime;
                GL11.glPushMatrix();
                GL11.glRotatef(f10, 0.0F, 1.0F, 0.0F);
                GL11.glRotatef(f2, 1.0F, 0.0F, 0.0F);
                GL11.glTranslatef(0.375F * (float)(j * 2 - 1), 0.0F, 0.0F);
                GL11.glTranslatef(0.0F, -0.375F, 0.0F);
                GL11.glRotatef(-f2, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(-f10, 0.0F, 1.0F, 0.0F);
                f3 = 1.3333334F;
                GL11.glScalef(f3, f3, f3);
                this.iModelBipedMain.renderEars(0.0625F);
                GL11.glPopMatrix();
            }
        }

        boolean flag = pPlayer.getTextureCape().isTextureUploaded();
        flag = event.renderCape && flag;
        float f5;

        if (flag && !pPlayer.isInvisible() && !pPlayer.getHideCape())
        {
            this.bindTexture(pPlayer.getLocationCape());
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0F, 0.0F, 0.125F);
            double d3 = pPlayer.field_71091_bM + (pPlayer.field_71094_bP - pPlayer.field_71091_bM) * (double)pPartialTickTime - (pPlayer.prevPosX + (pPlayer.posX - pPlayer.prevPosX) * (double)pPartialTickTime);
            double d4 = pPlayer.field_71096_bN + (pPlayer.field_71095_bQ - pPlayer.field_71096_bN) * (double)pPartialTickTime - (pPlayer.prevPosY + (pPlayer.posY - pPlayer.prevPosY) * (double)pPartialTickTime);
            double d0 = pPlayer.field_71097_bO + (pPlayer.field_71085_bR - pPlayer.field_71097_bO) * (double)pPartialTickTime - (pPlayer.prevPosZ + (pPlayer.posZ - pPlayer.prevPosZ) * (double)pPartialTickTime);
            f5 = pPlayer.prevRenderYawOffset + (pPlayer.renderYawOffset - pPlayer.prevRenderYawOffset) * pPartialTickTime;
            double d1 = (double)MathHelper.sin(f5 * (float)Math.PI / 180.0F);
            double d2 = (double)(-MathHelper.cos(f5 * (float)Math.PI / 180.0F));
            float f6 = (float)d4 * 10.0F;

            if (f6 < -6.0F)
            {
                f6 = -6.0F;
            }

            if (f6 > 32.0F)
            {
                f6 = 32.0F;
            }

            float f7 = (float)(d3 * d1 + d0 * d2) * 100.0F;
            float f8 = (float)(d3 * d2 - d0 * d1) * 100.0F;

            if (f7 < 0.0F)
            {
                f7 = 0.0F;
            }

            float f9 = pPlayer.prevCameraYaw + (pPlayer.cameraYaw - pPlayer.prevCameraYaw) * pPartialTickTime;
            f6 += MathHelper.sin((pPlayer.prevDistanceWalkedModified + (pPlayer.distanceWalkedModified - pPlayer.prevDistanceWalkedModified) * pPartialTickTime) * 6.0F) * 32.0F * f9;

            if (pPlayer.isSneaking())
            {
                f6 += 25.0F;
            }

            GL11.glRotatef(6.0F + f7 / 2.0F + f6, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(f8 / 2.0F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(-f8 / 2.0F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
            this.iModelBipedMain.renderCloak(0.0625F);
            GL11.glPopMatrix();
        }

        ItemStack itemstack1 = pPlayer.inventory.getCurrentItem();

        if (itemstack1 != null && event.renderItem)
        {
            GL11.glPushMatrix();
            this.iModelBipedMain.bipedRightArm.postRender(0.0625F);
            GL11.glTranslatef(-0.0625F, 0.4375F, 0.0625F);

            if (pPlayer.fishEntity != null)
            {
                itemstack1 = new ItemStack(Items.stick);
            }

            EnumAction enumaction = null;

            if (pPlayer.getItemInUseCount() > 0)
            {
                enumaction = itemstack1.getItemUseAction();
            }

            IItemRenderer customRenderer = MinecraftForgeClient.getItemRenderer(itemstack1, EQUIPPED);
            boolean is3D = (customRenderer != null && customRenderer.shouldUseRenderHelper(EQUIPPED, itemstack1, BLOCK_3D));

            if (is3D || itemstack1.getItem() instanceof ItemBlock && RenderBlocks.renderItemIn3d(Block.getBlockFromItem(itemstack1.getItem()).getRenderType()))
            {
                f3 = 0.5F;
                GL11.glTranslatef(0.0F, 0.1875F, -0.3125F);
                f3 *= 0.75F;
                GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
                GL11.glScalef(-f3, -f3, f3);
            }
            else if (itemstack1.getItem() == Items.bow)
            {
                f3 = 0.625F;
                GL11.glTranslatef(0.0F, 0.125F, 0.3125F);
                GL11.glRotatef(-20.0F, 0.0F, 1.0F, 0.0F);
                GL11.glScalef(f3, -f3, f3);
                GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
            }
            else if (itemstack1.getItem().isFull3D())
            {
                f3 = 0.625F;

                if (itemstack1.getItem().shouldRotateAroundWhenRendering())
                {
                    GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
                    GL11.glTranslatef(0.0F, -0.125F, 0.0F);
                }

                if (pPlayer.getItemInUseCount() > 0 && enumaction == EnumAction.block)
                {
                    GL11.glTranslatef(0.05F, 0.0F, -0.1F);
                    GL11.glRotatef(-50.0F, 0.0F, 1.0F, 0.0F);
                    GL11.glRotatef(-10.0F, 1.0F, 0.0F, 0.0F);
                    GL11.glRotatef(-60.0F, 0.0F, 0.0F, 1.0F);
                }

                GL11.glTranslatef(0.0F, 0.1875F, 0.0F);
                GL11.glScalef(f3, -f3, f3);
                GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
            }
            else
            {
                f3 = 0.375F;
                GL11.glTranslatef(0.25F, 0.1875F, -0.1875F);
                GL11.glScalef(f3, f3, f3);
                GL11.glRotatef(60.0F, 0.0F, 0.0F, 1.0F);
                GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(20.0F, 0.0F, 0.0F, 1.0F);
            }

            float f4;
            float f12;
            int k;

            if (itemstack1.getItem().requiresMultipleRenderPasses())
            {
                for (k = 0; k <= itemstack1.getItem().getRenderPasses(itemstack1.getItemDamage()); ++k)
                {
                    int i = itemstack1.getItem().getColorFromItemStack(itemstack1, k);
                    f12 = (float)(i >> 16 & 255) / 255.0F;
                    f4 = (float)(i >> 8 & 255) / 255.0F;
                    f5 = (float)(i & 255) / 255.0F;
                    GL11.glColor4f(f12, f4, f5, 1.0F);
                    this.renderManager.itemRenderer.renderItem(pPlayer, itemstack1, k);
                }
            }
            else
            {
                k = itemstack1.getItem().getColorFromItemStack(itemstack1, 0);
                float f11 = (float)(k >> 16 & 255) / 255.0F;
                f12 = (float)(k >> 8 & 255) / 255.0F;
                f4 = (float)(k & 255) / 255.0F;
                GL11.glColor4f(f11, f12, f4, 1.0F);
                this.renderManager.itemRenderer.renderItem(pPlayer, itemstack1, 0);
            }

            GL11.glPopMatrix();
        }
        MinecraftForge.EVENT_BUS.post(new RenderPlayerEvent.Specials.Post(pPlayer,(RenderPlayer) (RendererLivingEntity) (Render) this, pPartialTickTime));
    }

    //Renders the arrows which are stuck in an entity
    //-> Living Entity
    protected void renderArrowsStuckInEntity(EntityLivingBase pLivingBase, float pPartialTickTime)
    {
        int i = pLivingBase.getArrowCountInEntity();

        if (i > 0)
        {
            EntityArrow entityarrow = new EntityArrow(pLivingBase.worldObj, pLivingBase.posX, pLivingBase.posY, pLivingBase.posZ);
            Random random = new Random((long)pLivingBase.getEntityId());
            RenderHelper.disableStandardItemLighting();

            for (int j = 0; j < i; ++j)
            {
                GL11.glPushMatrix();
                ModelRenderer modelrenderer = this.iModelBipedMain.getRandomModelBox(random);
                ModelBox modelbox = (ModelBox)modelrenderer.cubeList.get(random.nextInt(modelrenderer.cubeList.size()));
                modelrenderer.postRender(0.0625F);
                float f1 = random.nextFloat();
                float f2 = random.nextFloat();
                float f3 = random.nextFloat();
                float f4 = (modelbox.posX1 + (modelbox.posX2 - modelbox.posX1) * f1) / 16.0F;
                float f5 = (modelbox.posY1 + (modelbox.posY2 - modelbox.posY1) * f2) / 16.0F;
                float f6 = (modelbox.posZ1 + (modelbox.posZ2 - modelbox.posZ1) * f3) / 16.0F;
                GL11.glTranslatef(f4, f5, f6);
                f1 = f1 * 2.0F - 1.0F;
                f2 = f2 * 2.0F - 1.0F;
                f3 = f3 * 2.0F - 1.0F;
                f1 *= -1.0F;
                f2 *= -1.0F;
                f3 *= -1.0F;
                float f7 = MathHelper.sqrt_float(f1 * f1 + f3 * f3);
                entityarrow.prevRotationYaw = entityarrow.rotationYaw = (float)(Math.atan2((double)f1, (double)f3) * 180.0D / Math.PI);
                entityarrow.prevRotationPitch = entityarrow.rotationPitch = (float)(Math.atan2((double)f2, (double)f7) * 180.0D / Math.PI);
                double d0 = 0.0D;
                double d1 = 0.0D;
                double d2 = 0.0D;
                float f8 = 0.0F;
                this.renderManager.renderEntityWithPosYaw(entityarrow, d0, d1, d2, f8, pPartialTickTime);
                GL11.glPopMatrix();
            }

            RenderHelper.enableStandardItemLighting();
        }
    }

    //Sets the color overlay for entities
    protected int getColorMultiplier(EntityLivingBase pLivingBase, float pBrightness, float pPartialTickTime)
    {
        return 0;
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
            this.renderLivingAt((AbstractClientPlayer)pEntity, pRenderDataX, pRenderDataY, pRenderDataZ);
            f4 = this.handleRotationFloat(pEntity, pPartialTickTime);
            this.rotateCorpse((AbstractClientPlayer)pEntity, f4, f2, pPartialTickTime);
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

 /*
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
*//*
            GL11.glDepthMask(true);
            this.renderEquippedItems((AbstractClientPlayer) pEntity, pPartialTickTime);
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
                        /*if (this.inheritRenderPass(pEntity, l, pPartialTickTime) >= 0)
                        {
                            GL11.glColor4f(f14, 0.0F, 0.0F, 0.4F);
                            this.iRenderPassModel.render(pEntity, f7, f6, f4, f3 - f2, f13, f5);
                        }*/
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
                      /*  if (this.inheritRenderPass(pEntity, i1, pPartialTickTime) >= 0)
                        {
                            GL11.glColor4f(f8, f9, f15, f10);
                            this.iRenderPassModel.render(pEntity, f7, f6, f4, f3 - f2, f13, f5);
                        }*/
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
        //this.passSpecialRender(pEntity, pRenderDataX, pRenderDataY, pRenderDataZ);
        MinecraftForge.EVENT_BUS.post(new RenderLivingEvent.Post(pEntity,(RenderPlayer) (RendererLivingEntity) (Render) this, pRenderDataX, pRenderDataY, pRenderDataZ));
    }



}

*/
