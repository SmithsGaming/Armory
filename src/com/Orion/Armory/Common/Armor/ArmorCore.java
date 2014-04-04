package com.Orion.Armory.Common.Armor;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraftforge.common.ISpecialArmor;
import tconstruct.library.TConstructRegistry;

import java.security.InvalidParameterException;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Orion on 26-3-2014
 */
public class ArmorCore extends ItemArmor implements ISpecialArmor
{
    public final int armorPart;
    public IIcon emptyIcon = null;
    public String[] iconLocations;
    public String[] modelTextureLocations;
    public IIcon[] iconTextures;
    /*
     * ArmorPart: From top to bottom 0 tp 3 (Helmet = 1, Shoes = 3)
     */
    public ArmorCore(ArmorMaterial material, int ArmorPart) {
        super(material, 0, ArmorPart);
        this.setMaxStackSize(1);
        this.armorPart = ArmorPart;
        this.setCreativeTab(TConstructRegistry.toolTab);
    }

    @Override
    public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot) {
        return null;
    }

    @Override
    public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
        return 0;
    }

    @Override
    public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot) {
        return;
    }

    @SideOnly(Side.CLIENT)
    public String addIconToIconTextures(int renderPass, boolean emptyLayer, String texture, String modelTexture)
    {
        if (iconTextures[renderPass] != null)
        {
            throw new InvalidParameterException("The given renderPass already has a Icon connected!");
        }
        if (emptyLayer == true)
        {
            iconTextures[renderPass] = emptyIcon;
            return "";
        }
        iconLocations[renderPass] = texture;
        modelTextureLocations[renderPass] = modelTexture;
        return texture;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses()
    {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(ItemStack stack, int renderPass)
    {
        if (renderPass >(iconTextures.length))
        {
            throw new IndexOutOfBoundsException("To much renderpasses for the given armor! Please register a IIcon for this layer!");
        }

        return iconTextures[renderPass];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderPasses(int metadata)
    {
        return iconTextures.length;
    }

    @Override
    public void registerIcons (IIconRegister iconRegister)
    {
        iconTextures = new IIcon[iconLocations.length];

        for (int i=1; i<=iconLocations.length; i++)
        {
            IIcon currentIcon;
            currentIcon = iconRegister.registerIcon(iconLocations[i-1]);

            iconTextures[i-1] = currentIcon;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
    {
        if (par1ItemStack.stackTagCompound == null)
        {
            par1ItemStack.stackTagCompound = new NBTTagCompound();
        }

        par1ItemStack.stackTagCompound.setInteger("currentRenderPass", par2);
        return 16777215;
    }

    @SideOnly(Side.CLIENT)
    public String getArmorTextureLocation(int RenderPass)
    {
        return modelTextureLocations[RenderPass];
    }

}
