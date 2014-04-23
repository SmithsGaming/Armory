package com.Orion.Armory.Common.Armor;

import com.Orion.Armory.Client.ArmoryResource;
import com.Orion.Armory.Common.ARegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.ISpecialArmor;

import java.util.ArrayList;

/**
 * Created by Orion on 26-3-2014
 */
public class ArmorCore extends ItemArmor implements ISpecialArmor
{
    public final int iArmorPart;
    public String iInternalName;

    //Hashmaps for storing the Icons
    public ArrayList<ArmoryResource> iResources = new ArrayList<ArmoryResource>();

    public ArmorCore(String pInternalName, int pArmorPart) {
        super(ARegistry.iArmorMaterial, 0, pArmorPart);
        this.setMaxStackSize(1);
        this.iInternalName = pInternalName;
        this.iArmorPart = pArmorPart;
        this.setCreativeTab(ARegistry.iTabArmoryArmor);
    }

    //Functions for ISpecialArmor. TODO: Needs to be implemented.
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

    //Function for registering a rescource to the object.
    public void registerResource(Integer pResourceID, ArmoryResource pResource)
    {
        iResources.add(pResourceID, pResource);
    }

    //Function called when registering the item to register the Icons.
    @Override
    public void registerIcons(IIconRegister pIconRegister)
    {
        for(ArmoryResource tCurrentResource: iResources)
        {
            tCurrentResource.addIcon(pIconRegister.registerIcon(tCurrentResource.getIconLocation()));
        }
    }

    //Function to get the IIcon for rendering Icons, The modeltexturelocation for models and the colors for combining.
    @SideOnly(Side.CLIENT)
    public ArmoryResource getResource(int pResourceID)
    {
        return iResources.get(pResourceID);
    }

    public int getRenderPasses(ItemStack pStack)
    {
        NBTTagCompound tBaseCompound = pStack.getTagCompound();
        return tBaseCompound.getInteger("RenderPasses");
    }

}
