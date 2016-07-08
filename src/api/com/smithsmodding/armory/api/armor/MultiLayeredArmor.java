package com.smithsmodding.armory.api.armor;
/*
*   MultiLayeredArmor
*   Created by: Orion
*   Created on: 28-6-2014
*/

import com.smithsmodding.armory.api.references.ModArmorMaterials;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.ISpecialArmor;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class MultiLayeredArmor extends ItemArmor implements ISpecialArmor {
    //Data for the registering of MultiLayeredArmor
    protected String uniqueID = "";
    protected String baseLayerAddonPositionId = "";
    protected HashMap<String, ArmorAddonPosition> possibleAddonPositions = new HashMap<String, ArmorAddonPosition>();
    protected HashMap<String, MLAAddon> possibleAddons = new HashMap<String, MLAAddon>();

    ///#############################################Constructors########################################################
    //Standard constructor to created an ItemArmor. Sets the InternalName and stores the ArmorPart separately
    public MultiLayeredArmor(String uniqueID, String baseLayerAddonPositionId, int vanillaRenderType, EntityEquipmentSlot slot) {
        super(ModArmorMaterials.getVanillaArmorDefitinition(), vanillaRenderType, slot);
        this.uniqueID = uniqueID;
        this.baseLayerAddonPositionId = baseLayerAddonPositionId;
    }

    ///#############################################Functions for grabbing data#########################################
    //Returns the InternalName (ID as handled by the armory registry etc), which has to be unique, of this instance of a
    //MultiLayeredArmor (MLA).
    public String getUniqueID () {
        return uniqueID;
    }

    //Location of the BaseLayer on the Armor in the form of an Addon.
    public String getBaseLayerAddonPositionId() {
        return baseLayerAddonPositionId;
    }

    //Registers the Addon to this armor as its parent.
    public void registerAddon (MLAAddon pNewAddon) {
        possibleAddons.put(pNewAddon.getUniqueID(), pNewAddon);
    }

    //Returns an MLAAddon with the given ID if registered.
    public MLAAddon getAddon (String pAddonID) {
        if (!possibleAddons.containsKey(pAddonID))
            return null;

        return possibleAddons.get(pAddonID);
    }

    //Function to retrieve the complete addon list
    public ArrayList<MLAAddon> getAllowedAddons () {
        return new ArrayList<MLAAddon>(possibleAddons.values());
    }

    //Registers the AddonPostion to this armor part
    public void registerAddonPosition (ArmorAddonPosition pNewPosition) {
        possibleAddonPositions.put(pNewPosition.getInternalName(), pNewPosition);
    }

    //Function to retrieve the complete AddonPosition list
    public ArrayList<ArmorAddonPosition> getAllowedAddonPositions () {
        return new ArrayList<ArmorAddonPosition>(possibleAddonPositions.values());
    }

    ///############################################Functions for handeling the ISpecialArmorRequirements################
    //events have to be created to implement these functions.
    //Might actually leave to the implementer to implement these
    @Override
    public abstract ArmorProperties getProperties (EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot);

    @Override
    public abstract int getArmorDisplay (EntityPlayer player, ItemStack armor, int slot);

    @Override
    public abstract void damageArmor (EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot);

    @Override
    public abstract ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped _default);
}
