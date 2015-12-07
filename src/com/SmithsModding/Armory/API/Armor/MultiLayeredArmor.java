package com.SmithsModding.Armory.API.Armor;
/*
*   MultiLayeredArmor
*   Created by: Orion
*   Created on: 28-6-2014
*/

import com.SmithsModding.Armory.Util.References;
import com.SmithsModding.SmithsCore.Util.Client.CustomResource;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.common.util.Constants;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class MultiLayeredArmor extends ItemArmor implements ISpecialArmor {
    ///Values needed for the Vanilla renderer
    //Stores the MaxRenderPasses a Armorpiece of this kind needs to render all its components
    public int iMaxRenderPasses;
    //Hashmaps for storing the Resources needed for rendering.
    //The CustomResource object stores both the Colors, Icons and TileEntities-textures
    public HashMap<String, CustomResource> iResources = new HashMap<String, CustomResource>();

    //Data for the registering of MultiLayeredArmor
    protected String iInternalName = "";
    protected Integer iArmorPart = -1;
    protected HashMap<String, ArmorAddonPosition> iPossibleAddonPositions = new HashMap<String, ArmorAddonPosition>();
    protected HashMap<String, MLAAddon> iPossibleAddons = new HashMap<String, MLAAddon>();

    ///#############################################Constructors########################################################
    //Standard constructor to created an ItemArmor. Sets the InternalName and stores the ArmorPart separately
    public MultiLayeredArmor(String pInternalName, ArmorMaterial pMaterial, int pRenderIndex, int pArmorPart, int pMaxRenderPasses) {
        super(pMaterial, pRenderIndex, pArmorPart);
        this.iInternalName = pInternalName;
        this.iArmorPart = pArmorPart;
        this.iMaxRenderPasses = pMaxRenderPasses;
    }

    ///#############################################Functions for grabbing data#########################################
    //Returns the InternalName (ID as handled by the Armory Registry etc), which has to be unique, of this instance of a
    //MultiLayeredArmor (MLA).
    public String getInternalName() {
        return iInternalName;
    }

    //Returns the ArmorPart of the current instance of a MLA. See ItemArmor for more details.
    public Integer getArmorPart() {
        return iArmorPart;
    }

    //Registers the Addon to this Armor as its parent.
    public void registerAddon(MLAAddon pNewAddon) {
        iPossibleAddons.put(pNewAddon.getInternalName(), pNewAddon);
    }

    //Returns an MLAAddon with the given ID if registered.
    public MLAAddon getAddon(String pAddonID) {
        if (!iPossibleAddons.containsKey(pAddonID))
            return null;

        return iPossibleAddons.get(pAddonID);
    }

    //Function to retrieve the complete addon list
    public ArrayList<MLAAddon> getAllowedAddons() {
        return new ArrayList<MLAAddon>(iPossibleAddons.values());
    }

    //Registers the AddonPostion to this armor part
    public void registerAddonPosition(ArmorAddonPosition pNewPosition) {
        iPossibleAddonPositions.put(pNewPosition.getInternalName(), pNewPosition);
    }

    //Function to retrieve the complete AddonPosition list
    public ArrayList<ArmorAddonPosition> getAllowedPositions() {
        return new ArrayList<ArmorAddonPosition>(iPossibleAddonPositions.values());
    }

    ///############################################Functions for handeling the ISpecialArmorRequirements################
    //Events have to be created to implement these functions.
    //Might actually leave to the implementer to implement these
    @Override
    public abstract ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot);

    @Override
    public abstract int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot);

    @Override
    public abstract void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot);

    ///#############################################Vanilla functions###################################################

    //Function that tells the renderer to use a certain color
    @Override
    public int getColorFromItemStack(ItemStack pStack, int pRenderPass) {
        if (pRenderPass < this.getRenderPasses(pStack)) {
            return this.getResource(pStack, pRenderPass).getColor().getRGB();
        }

        return 16777215;
    }

    ///#############################################Resource control functions##########################################
    //Function for registering a new resource the Item may need to render it
    public void registerResource(CustomResource pResource) {
        iResources.put(pResource.getInternalName(), pResource);
    }

    //Returns the resource (if registered, else null) depending on the given InternalName (which is registered as its ID)
    public CustomResource getResource(String pResourceID) {
        return iResources.get(pResourceID);
    }

    //Returns the resource (if registered, else null) depending on the given ItemStack and RenderPass needed.
    //Extracts the NBTTagCompound for the given RenderPass out of the NBTTag of the ItemStack and
    //passes it to this.getResource(RenderPassNBTTagCompound)
    public CustomResource getResource(ItemStack pStack, int pRenderPass) {
        NBTTagCompound tRenderCompound = pStack.getTagCompound().getCompoundTag(References.NBTTagCompoundData.RenderCompound);
        NBTTagList tResourceList = tRenderCompound.getTagList(References.NBTTagCompoundData.Rendering.ResourceIDs, Constants.NBT.TAG_STRING);

        CustomResource tResource = this.iResources.get(tResourceList.getStringTagAt(pRenderPass));

        return tResource;
    }

    //Function to get the amount of renderpasses for the given ItemStack, used in rendering the model and while determining which Icon to return in the Vanilla item renderer.
    public int getRenderPasses(ItemStack pStack) {
        NBTTagCompound tRenderCompound = pStack.getTagCompound().getCompoundTag(References.NBTTagCompoundData.RenderCompound);
        return tRenderCompound.getInteger(References.NBTTagCompoundData.Rendering.MaxRenderPasses);
    }

    //Function to set the amount of RenderPasses. Used in the vanilla rendering of the itemstack.
    //On normal use this is not used as the system uses a value set by the constructor
    //Someone use this function to in -or decrement the amount of renderpasses
    //Example. If you register more Modifiers and or Upgrades to Armory, you might need to change this value.
    public void setMaxRenderPasses(int pMaxRenderPasses) {
        this.iMaxRenderPasses = pMaxRenderPasses;
    }
}
