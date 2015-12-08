package com.SmithsModding.Armory.API.Armor;
/*
*   MultiLayeredArmor
*   Created by: Orion
*   Created on: 28-6-2014
*/

import com.SmithsModding.Armory.Client.Model.Entity.ModelExtendedBiped;
import com.SmithsModding.Armory.Common.Registry.GeneralRegistry;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class MultiLayeredArmor extends ItemArmor implements ISpecialArmor {
    //Data for the registering of MultiLayeredArmor
    protected String uniqueID = "";
    protected Integer armorIndex = -1;
    protected HashMap<String, ArmorAddonPosition> possibleAddonPositions = new HashMap<String, ArmorAddonPosition>();
    protected HashMap<String, MLAAddon> possibleAddons = new HashMap<String, MLAAddon>();

    ///#############################################Constructors########################################################
    //Standard constructor to created an ItemArmor. Sets the InternalName and stores the ArmorPart separately
    public MultiLayeredArmor (String uniqueID, int vanillaRenderType, int armorIndex) {
        super(GeneralRegistry.getVanillaArmorDefitinition(), vanillaRenderType, armorIndex);
        this.uniqueID = uniqueID;
        this.armorIndex = armorIndex;
    }

    ///#############################################Functions for grabbing data#########################################
    //Returns the InternalName (ID as handled by the Armory Registry etc), which has to be unique, of this instance of a
    //MultiLayeredArmor (MLA).
    public String getUniqueID () {
        return uniqueID;
    }

    //Returns the ArmorPart of the current instance of a MLA. See ItemArmor for more details.
    public Integer getArmorIndex () {
        return armorIndex;
    }

    //Registers the Addon to this Armor as its parent.
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
    //Events have to be created to implement these functions.
    //Might actually leave to the implementer to implement these
    @Override
    public abstract ArmorProperties getProperties (EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot);

    @Override
    public abstract int getArmorDisplay (EntityPlayer player, ItemStack armor, int slot);

    @Override
    public abstract void damageArmor (EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot);

    @SideOnly(Side.CLIENT)
    @Override
    public ModelBiped getArmorModel (EntityLivingBase pEntityLiving, ItemStack pItemStack, int pArmorSlot) {
        ModelExtendedBiped tModel = new ModelExtendedBiped(1F, pItemStack);

        switch (this.armorIndex) {
            case 0:
                tModel = new ModelExtendedBiped(1.5F, pItemStack);
                break;
            case 3:
                tModel = new ModelExtendedBiped(1.5F, pItemStack);
                break;
            default:
                break;
        }

        return tModel;
    }
}
