package com.Orion.Armory.Common.Material;
/*
 *   MaterialRegistry
 *   Created by: Orion
 *   Created on: 24-9-2014
 */

import com.Orion.Armory.API.Armor.MLAAddon;
import com.Orion.Armory.API.Armor.MultiLayeredArmor;
import com.Orion.Armory.API.Materials.IArmorMaterial;
import com.Orion.Armory.API.Registries.IMaterialRegistry;
import com.Orion.Armory.Common.Registry.GeneralRegistry;

import java.util.HashMap;

public class MaterialRegistry implements IMaterialRegistry{
    protected static MaterialRegistry iInstance;
    protected static GeneralRegistry iGeneralRegistry = GeneralRegistry.getInstance();

    //Hashmap for storing all the materials
    protected HashMap<String, IArmorMaterial> iArmorMaterials = new HashMap<String, IArmorMaterial>();
    //Hashmap for storing all the basic armor mappings
    protected HashMap<String, MultiLayeredArmor> iArmorMappings = new HashMap<String, MultiLayeredArmor>();

    public static MaterialRegistry getInstance()
    {
        if (iInstance == null)
        {
            iInstance = new MaterialRegistry();
        }

        return iInstance;
    }

    //ArmorMappings
    public HashMap<String, MultiLayeredArmor> getAllRegisteredArmors()
    {
        return iArmorMappings;
    }

    public void registerNewArmor(MultiLayeredArmor pArmor)
    {
        iArmorMappings.put(pArmor.getInternalName(), pArmor);
    }

    public MultiLayeredArmor getArmor(String pInternalName)
    {
        return iArmorMappings.get(pInternalName);
    }

    //ArmorMaterials
    public HashMap<String, IArmorMaterial> getArmorMaterials()
    {
        return iArmorMaterials;
    }

    @Override
    public void setAllArmorMaterials(HashMap<String, IArmorMaterial> pNewMaterials) {
        iArmorMaterials = pNewMaterials;
    }

    public void registerMaterial(IArmorMaterial pMaterial)
    {
        iArmorMaterials.put(pMaterial.getInternalMaterialName(), pMaterial);
    }

    public IArmorMaterial getMaterial(String pInternalName)
    {
        return iArmorMaterials.get(pInternalName);
    }

    public void changeUpgradeStateOnMaterial(String pMaterialInternalName, String pUpgradeInternalName, boolean pPartState)
    {
        iArmorMaterials.get(pMaterialInternalName).modifyPartState(pUpgradeInternalName, pPartState);
    }
}


/*

 */
