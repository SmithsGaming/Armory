package com.Orion.Armory.Client.Render.Old;
/*
/  CustomPlayerRenderer
/  Created by : Orion
/  Created on : 29/04/2014
*/

import com.Orion.Armory.Client.Models.AExtendedPlayerModel;
import com.Orion.Armory.Common.Armor.ArmorCore;
import net.minecraft.block.Block;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.opengl.GL11;

import static net.minecraftforge.client.IItemRenderer.ItemRenderType.EQUIPPED;
import static net.minecraftforge.client.IItemRenderer.ItemRendererHelper.BLOCK_3D;

//TODO: Create a system that enables the checking if this renderer needs to be used or the original one.
//TODO: Remove all Forge events.

public class CustomPlayerRenderer extends RenderPlayer
{
    private static final ResourceLocation steveTextures = new ResourceLocation("textures/entity/steve.png");
    private ModelBiped modelBipedMain;
    private ModelBiped modelArmorChestplate;
    private ModelBiped modelArmor;
    private static final String __OBFID = "CL_00001020";
    private static CustomLivingEntityRenderer iCustumLivingEntityRenderer;
    private static RenderPlayer iPlayerRenderer;

    public CustomPlayerRenderer()
    {
        this.mainModel = new ModelBiped(0.0F);
        this.shadowSize = 0.5F;
        this.modelBipedMain = (ModelBiped)this.mainModel;
        this.modelArmorChestplate = new AExtendedPlayerModel(1.0F);
        this.modelArmor = new AExtendedPlayerModel(0.5F);

        iCustumLivingEntityRenderer = new CustomLivingEntityRenderer(this.mainModel, this.shadowSize);
        iPlayerRenderer = new RenderPlayer();

        if (this.renderManager == null)
        {
            this.setRenderManager(RenderManager.instance);
        }
    }

    //No clue what this function does, or if it is even needed.
    //Leaving it in, cause i donnot think i can take it out. Will need to reverse engineer what it does!
    protected void func_82408_c(AbstractClientPlayer par1AbstractClientPlayer, int par2, float par3)
    {
        ItemStack itemstack = par1AbstractClientPlayer.inventory.armorItemInSlot(3 - par2);

        if (itemstack != null)
        {
            Item item = itemstack.getItem();

            if (item instanceof ItemArmor)
            {
                this.bindTexture(RenderBiped.getArmorResource(par1AbstractClientPlayer, itemstack, par2, "overlay"));
                GL11.glColor3f(1.0F, 1.0F, 1.0F);
            }
        }
    }

    /*\
    Renders the player and uses the custom armor renderer.
     */
    public void doRender(AbstractClientPlayer par1AbstractClientPlayer, double par2, double par4, double par6, float par8, float par9)
    {
        //Check if the player is using Armory armor, else pass to original Renderer.
        boolean iWearing = true;
        for (int iIter = 0; iIter<=3; iIter ++)
        {
            if (!(par1AbstractClientPlayer.inventory.armorItemInSlot(3-iIter).getItem() instanceof ArmorCore))
            {
                iWearing = false;
            }
        }

        if (!iWearing)
        {
            iPlayerRenderer.doRender(par1AbstractClientPlayer, par2, par4, par6, par8, par9);
        }


        GL11.glColor3f(1.0F, 1.0F, 1.0F);
        ItemStack itemstack = par1AbstractClientPlayer.inventory.getCurrentItem();
        this.modelArmorChestplate.heldItemRight = this.modelArmor.heldItemRight = this.modelBipedMain.heldItemRight = itemstack != null ? 1 : 0;

        if (itemstack != null && par1AbstractClientPlayer.getItemInUseCount() > 0)
        {
            EnumAction enumaction = itemstack.getItemUseAction();

            if (enumaction == EnumAction.block)
            {
                this.modelArmorChestplate.heldItemRight = this.modelArmor.heldItemRight = this.modelBipedMain.heldItemRight = 3;
            }
            else if (enumaction == EnumAction.bow)
            {
                this.modelArmorChestplate.aimedBow = this.modelArmor.aimedBow = this.modelBipedMain.aimedBow = true;
            }
        }

        this.modelArmorChestplate.isSneak = this.modelArmor.isSneak = this.modelBipedMain.isSneak = par1AbstractClientPlayer.isSneaking();
        double d3 = par4 - (double)par1AbstractClientPlayer.yOffset;

        if (par1AbstractClientPlayer.isSneaking() && !(par1AbstractClientPlayer instanceof EntityPlayerSP))
        {
            d3 -= 0.125D;
        }

        //TODO: The following function needs to be rewritten! (Replace with custom living entityrenderer?)
        iCustumLivingEntityRenderer.doRender((EntityLivingBase) par1AbstractClientPlayer, par2, par4, par6, par8, par9);

        this.modelArmorChestplate.aimedBow = this.modelArmor.aimedBow = this.modelBipedMain.aimedBow = false;
        this.modelArmorChestplate.isSneak = this.modelArmor.isSneak = this.modelBipedMain.isSneak = false;
        this.modelArmorChestplate.heldItemRight = this.modelArmor.heldItemRight = this.modelBipedMain.heldItemRight = 0;
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(AbstractClientPlayer par1AbstractClientPlayer)
    {
        return par1AbstractClientPlayer.getLocationSkin();
    }


    protected void renderEquippedItems(AbstractClientPlayer par1AbstractClientPlayer, float par2)
    {
        RenderPlayerEvent.Specials.Pre event = new RenderPlayerEvent.Specials.Pre(par1AbstractClientPlayer, this, par2);
        if (MinecraftForge.EVENT_BUS.post(event))
        {
            return;
        }

        GL11.glColor3f(1.0F, 1.0F, 1.0F);
        super.renderEquippedItems(par1AbstractClientPlayer, par2);
        super.renderArrowsStuckInEntity(par1AbstractClientPlayer, par2);
        ItemStack itemstack = par1AbstractClientPlayer.inventory.armorItemInSlot(3);

        if (itemstack != null && event.renderHelmet)
        {
            GL11.glPushMatrix();
            this.modelBipedMain.bipedHead.postRender(0.0625F);
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

                this.renderManager.itemRenderer.renderItem(par1AbstractClientPlayer, itemstack, 0);
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

        if (par1AbstractClientPlayer.getCommandSenderName().equals("deadmau5") && par1AbstractClientPlayer.getTextureSkin().isTextureUploaded())
        {
            this.bindTexture(par1AbstractClientPlayer.getLocationSkin());

            for (int j = 0; j < 2; ++j)
            {
                float f10 = par1AbstractClientPlayer.prevRotationYaw + (par1AbstractClientPlayer.rotationYaw - par1AbstractClientPlayer.prevRotationYaw) * par2 - (par1AbstractClientPlayer.prevRenderYawOffset + (par1AbstractClientPlayer.renderYawOffset - par1AbstractClientPlayer.prevRenderYawOffset) * par2);
                float f2 = par1AbstractClientPlayer.prevRotationPitch + (par1AbstractClientPlayer.rotationPitch - par1AbstractClientPlayer.prevRotationPitch) * par2;
                GL11.glPushMatrix();
                GL11.glRotatef(f10, 0.0F, 1.0F, 0.0F);
                GL11.glRotatef(f2, 1.0F, 0.0F, 0.0F);
                GL11.glTranslatef(0.375F * (float)(j * 2 - 1), 0.0F, 0.0F);
                GL11.glTranslatef(0.0F, -0.375F, 0.0F);
                GL11.glRotatef(-f2, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(-f10, 0.0F, 1.0F, 0.0F);
                f3 = 1.3333334F;
                GL11.glScalef(f3, f3, f3);
                this.modelBipedMain.renderEars(0.0625F);
                GL11.glPopMatrix();
            }
        }

        boolean flag = par1AbstractClientPlayer.getTextureCape().isTextureUploaded();
        flag = event.renderCape && flag;
        float f5;

        if (flag && !par1AbstractClientPlayer.isInvisible() && !par1AbstractClientPlayer.getHideCape())
        {
            this.bindTexture(par1AbstractClientPlayer.getLocationCape());
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0F, 0.0F, 0.125F);
            double d3 = par1AbstractClientPlayer.field_71091_bM + (par1AbstractClientPlayer.field_71094_bP - par1AbstractClientPlayer.field_71091_bM) * (double)par2 - (par1AbstractClientPlayer.prevPosX + (par1AbstractClientPlayer.posX - par1AbstractClientPlayer.prevPosX) * (double)par2);
            double d4 = par1AbstractClientPlayer.field_71096_bN + (par1AbstractClientPlayer.field_71095_bQ - par1AbstractClientPlayer.field_71096_bN) * (double)par2 - (par1AbstractClientPlayer.prevPosY + (par1AbstractClientPlayer.posY - par1AbstractClientPlayer.prevPosY) * (double)par2);
            double d0 = par1AbstractClientPlayer.field_71097_bO + (par1AbstractClientPlayer.field_71085_bR - par1AbstractClientPlayer.field_71097_bO) * (double)par2 - (par1AbstractClientPlayer.prevPosZ + (par1AbstractClientPlayer.posZ - par1AbstractClientPlayer.prevPosZ) * (double)par2);
            f5 = par1AbstractClientPlayer.prevRenderYawOffset + (par1AbstractClientPlayer.renderYawOffset - par1AbstractClientPlayer.prevRenderYawOffset) * par2;
            double d1 = (double) MathHelper.sin(f5 * (float) Math.PI / 180.0F);
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

            float f9 = par1AbstractClientPlayer.prevCameraYaw + (par1AbstractClientPlayer.cameraYaw - par1AbstractClientPlayer.prevCameraYaw) * par2;
            f6 += MathHelper.sin((par1AbstractClientPlayer.prevDistanceWalkedModified + (par1AbstractClientPlayer.distanceWalkedModified - par1AbstractClientPlayer.prevDistanceWalkedModified) * par2) * 6.0F) * 32.0F * f9;

            if (par1AbstractClientPlayer.isSneaking())
            {
                f6 += 25.0F;
            }

            GL11.glRotatef(6.0F + f7 / 2.0F + f6, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(f8 / 2.0F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(-f8 / 2.0F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
            this.modelBipedMain.renderCloak(0.0625F);
            GL11.glPopMatrix();
        }

        ItemStack itemstack1 = par1AbstractClientPlayer.inventory.getCurrentItem();

        if (itemstack1 != null && event.renderItem)
        {
            GL11.glPushMatrix();
            this.modelBipedMain.bipedRightArm.postRender(0.0625F);
            GL11.glTranslatef(-0.0625F, 0.4375F, 0.0625F);

            if (par1AbstractClientPlayer.fishEntity != null)
            {
                itemstack1 = new ItemStack(Items.stick);
            }

            EnumAction enumaction = null;

            if (par1AbstractClientPlayer.getItemInUseCount() > 0)
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

                if (par1AbstractClientPlayer.getItemInUseCount() > 0 && enumaction == EnumAction.block)
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
                    this.renderManager.itemRenderer.renderItem(par1AbstractClientPlayer, itemstack1, k);
                }
            }
            else
            {
                k = itemstack1.getItem().getColorFromItemStack(itemstack1, 0);
                float f11 = (float)(k >> 16 & 255) / 255.0F;
                f12 = (float)(k >> 8 & 255) / 255.0F;
                f4 = (float)(k & 255) / 255.0F;
                GL11.glColor4f(f11, f12, f4, 1.0F);
                this.renderManager.itemRenderer.renderItem(par1AbstractClientPlayer, itemstack1, 0);
            }

            GL11.glPopMatrix();
        }
        MinecraftForge.EVENT_BUS.post(new RenderPlayerEvent.Specials.Post(par1AbstractClientPlayer, this, par2));
    }

    /**
     * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
     * entityLiving, partialTickTime
     */
    protected void preRenderCallback(AbstractClientPlayer par1AbstractClientPlayer, float par2)
    {
        float f1 = 0.9375F;
        GL11.glScalef(f1, f1, f1);
    }

    protected void func_96449_a(AbstractClientPlayer par1AbstractClientPlayer, double par2, double par4, double par6, String par8Str, float par9, double par10)
    {
        if (par10 < 100.0D)
        {
            Scoreboard scoreboard = par1AbstractClientPlayer.getWorldScoreboard();
            ScoreObjective scoreobjective = scoreboard.func_96539_a(2);

            if (scoreobjective != null)
            {
                Score score = scoreboard.func_96529_a(par1AbstractClientPlayer.getCommandSenderName(), scoreobjective);

                if (par1AbstractClientPlayer.isPlayerSleeping())
                {
                    this.func_147906_a(par1AbstractClientPlayer, score.getScorePoints() + " " + scoreobjective.getDisplayName(), par2, par4 - 1.5D, par6, 64);
                }
                else
                {
                    this.func_147906_a(par1AbstractClientPlayer, score.getScorePoints() + " " + scoreobjective.getDisplayName(), par2, par4, par6, 64);
                }

                par4 += (double)((float)this.getFontRendererFromRenderManager().FONT_HEIGHT * 1.15F * par9);
            }
        }

        super.func_96449_a(par1AbstractClientPlayer, par2, par4, par6, par8Str, par9, par10);
    }

    public void renderFirstPersonArm(EntityPlayer par1EntityPlayer)
    {
        float f = 1.0F;
        GL11.glColor3f(f, f, f);
        this.modelBipedMain.onGround = 0.0F;
        this.modelBipedMain.setRotationAngles(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, par1EntityPlayer);
        this.modelBipedMain.bipedRightArm.render(0.0625F);
    }

    /**
     * Sets a simple glTranslate on a LivingEntity.
     */
    protected void renderLivingAt(AbstractClientPlayer par1AbstractClientPlayer, double par2, double par4, double par6)
    {
        if (par1AbstractClientPlayer.isEntityAlive() && par1AbstractClientPlayer.isPlayerSleeping())
        {
            super.renderLivingAt(par1AbstractClientPlayer, par2 + (double)par1AbstractClientPlayer.field_71079_bU, par4 + (double)par1AbstractClientPlayer.field_71082_cx, par6 + (double)par1AbstractClientPlayer.field_71089_bV);
        }
        else
        {
            super.renderLivingAt(par1AbstractClientPlayer, par2, par4, par6);
        }
    }

    protected void rotateCorpse(AbstractClientPlayer par1AbstractClientPlayer, float par2, float par3, float par4)
    {
        if (par1AbstractClientPlayer.isEntityAlive() && par1AbstractClientPlayer.isPlayerSleeping())
        {
            GL11.glRotatef(par1AbstractClientPlayer.getBedOrientationInDegrees(), 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(this.getDeathMaxRotation(par1AbstractClientPlayer), 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(270.0F, 0.0F, 1.0F, 0.0F);
        }
        else
        {
            super.rotateCorpse(par1AbstractClientPlayer, par2, par3, par4);
        }
    }

    protected void func_96449_a(EntityLivingBase par1EntityLivingBase, double par2, double par4, double par6, String par8Str, float par9, double par10)
    {
        this.func_96449_a((AbstractClientPlayer)par1EntityLivingBase, par2, par4, par6, par8Str, par9, par10);
    }

    /**
     * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
     * entityLiving, partialTickTime
     */
    protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2)
    {
        this.preRenderCallback((AbstractClientPlayer)par1EntityLivingBase, par2);
    }

    protected void func_82408_c(EntityLivingBase par1EntityLivingBase, int par2, float par3)
    {
        this.func_82408_c((AbstractClientPlayer)par1EntityLivingBase, par2, par3);
    }

    /**
     * Queries whether should render the specified pass or not.
     */
    protected int shouldRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3)
    {
        return iCustumLivingEntityRenderer.shouldRenderPass((AbstractClientPlayer)par1EntityLivingBase, par2, par3);
    }

    protected void renderEquippedItems(EntityLivingBase par1EntityLivingBase, float par2)
    {
        this.renderEquippedItems((AbstractClientPlayer)par1EntityLivingBase, par2);
    }

    protected void rotateCorpse(EntityLivingBase par1EntityLivingBase, float par2, float par3, float par4)
    {
        this.rotateCorpse((AbstractClientPlayer)par1EntityLivingBase, par2, par3, par4);
    }

    /**
     * Sets a simple glTranslate on a LivingEntity.
     */
    protected void renderLivingAt(EntityLivingBase par1EntityLivingBase, double par2, double par4, double par6)
    {
        this.renderLivingAt((AbstractClientPlayer)par1EntityLivingBase, par2, par4, par6);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(EntityLivingBase par1EntityLivingBase, double par2, double par4, double par6, float par8, float par9)
    {
        this.doRender((AbstractClientPlayer)par1EntityLivingBase, par2, par4, par6, par8, par9);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(Entity par1Entity)
    {
        return this.getEntityTexture((AbstractClientPlayer)par1Entity);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
    {
        this.doRender((AbstractClientPlayer)par1Entity, par2, par4, par6, par8, par9);
    }
}
