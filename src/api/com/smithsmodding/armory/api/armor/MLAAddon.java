package com.smithsmodding.armory.api.armor;
/*
/  MLAAddon
/  Created by : Orion
/  Created on : 03/07/2014
*/


import net.minecraft.util.ResourceLocation;

public abstract class MLAAddon implements ILayeredArmorLayer {
    protected String uniqueID = "";
    protected String addonPositionID;
    protected String uniqueArmorID = "";

    protected Integer maximumInstalledAmount = 1;

    protected ResourceLocation itemWholeTextureLocation;
    protected ResourceLocation itemBrokenTextureLocation;
    protected ResourceLocation modelTextureLocation;

    protected int layerPriority;

    /**
     * Standard constructor sets the Internal Name and the position of the Addon on the armor
     *
     * @param uniqueID    The internal name of the addon
     * @param uniqueArmorID      The MLA armor this addon should be registered to.
     * @param addonPositionID The position of the addon on the MLA armor.
     *
     *                         Classes you might be interested in:
     * @see MultiLayeredArmor
     * @see ArmorAddonPosition
     */
    public MLAAddon (String uniqueID, String uniqueArmorID, String addonPositionID, ResourceLocation itemWholeTextureLocation, ResourceLocation modelTextureLocation, int layerPriority) {
        this(uniqueID, uniqueArmorID, addonPositionID, 1, itemWholeTextureLocation, modelTextureLocation, layerPriority);
    }

    /**
     * Standard constructor used to create addons which support multiple of them selves on a single position
     *
     * @param uniqueID       The internal name of the addon
     * @param uniqueArmorID         The MLA armor this addon should be registered to.
     * @param addonPositionID    The position of the addon on the MLA armor.
     * @param maximumInstalledAmount The max amount of addons that are allowed on a single position
     */
    public MLAAddon (String uniqueID, String uniqueArmorID, String addonPositionID, Integer maximumInstalledAmount, ResourceLocation itemWholeTextureLocation, ResourceLocation modelTextureLocation, int layerPriority) {
        this.uniqueID = uniqueID;
        this.uniqueArmorID = uniqueArmorID;
        this.addonPositionID = addonPositionID;
        this.maximumInstalledAmount = maximumInstalledAmount;
        this.itemWholeTextureLocation = itemWholeTextureLocation;
        itemBrokenTextureLocation = this.itemWholeTextureLocation;
        this.modelTextureLocation = modelTextureLocation;
        this.layerPriority = layerPriority;
    }

    @Override
    public boolean isMaterialDependent () {
        return false;
    }

    @Override
    public ResourceLocation getItemWholeTextureLocation () {
        return itemWholeTextureLocation;
    }

    @Override
    public ResourceLocation getItemBrokenTextureLocation () {
        return itemBrokenTextureLocation;
    }

    @Override
    public ResourceLocation getModelTextureLocation () {
        return modelTextureLocation;
    }

    /**
     * Function to get the internal name
     *
     * @return The internal name of the addon
     *
     * Classes you might be interested in:
     * @see MultiLayeredArmor
     * @see ArmorAddonPosition
     */
    public String getUniqueID () {
        return this.uniqueID;
    }

    /**
     * Function to get the InternalName of the ItemArmor this addon is registered to
     *
     * @return The InternalName of the ItemArmor this addon is registered to.
     *
     * Classes that might interest you:
     * @see MultiLayeredArmor
     */
    public String getUniqueArmorID () {
        return this.uniqueArmorID;
    }

    /**
     * Function to get the positionid of the addon on the MLA armor
     *
     * @return The position of the addon on the armor
     *
     * Classes you might be interested in:
     * @see MultiLayeredArmor
     * @see ArmorAddonPosition
     */
    public String getAddonPositionID() {
        return this.addonPositionID;
    }

    /**
     * Method to get the maximum amount of installed instances of this Addon, on a single ItemStack.
     *
     * @return The maximum amounr of installed instances of this Addon.
     */
    public Integer getMaxInstalledAmount() {
        return this.maximumInstalledAmount;
    }

    /**
     * Method to get the priority in the rendering of this layer.
     *
     * @return The higher the better.
     */
    public int getLayerPriority () {
        return layerPriority;
    }

    public abstract String getDisplayName();


    //##################Functions used to configure most of the crafting logic##########################################

    /**
     * Function to be implemented by programmer that vaildates the crafting.
     *
     * @param pAddonIDToCheckAgainst The Addon ID that is already on the armor, or that is being put on the armor. You need to allow (return true) or cancel the addition (return false)
     *                               Do this by comparing the ID given in this parameter anyway you like and returning the proper bool value.
     * @return A Bool that tells the factory to either allow(true) or disallow(false) the crafting.
     */
    public abstract boolean validateCrafting(String pAddonIDToCheckAgainst, boolean pInstalled);
}
