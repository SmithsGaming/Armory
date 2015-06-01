package com.Orion.Armory.API.Armor;
/*
/  MLAAddon
/  Created by : Orion
/  Created on : 03/07/2014
*/


import com.Orion.Armory.Util.Client.CustomResource;

import java.security.InvalidParameterException;

public abstract class MLAAddon {
    protected String iInternalName = "";
    protected String iAddonPositionID;
    protected Integer iMaxInstalledAmount = 1;
    protected String iParentName = "";
    protected CustomResource iResource;

    /**
     * Standard constructor sets the Internal Name and the position of the Addon on the armor
     *
     * @param pInternalName    The internal name of the addon
     * @param pParentName      The MLA Armor this addon should be registered to.
     * @param pAddonPositionID The position of the addon on the MLA armor.
     *                         <p/>
     *                         Classes you might be interested in:
     * @see MultiLayeredArmor
     * @see ArmorAddonPosition
     */
    public MLAAddon(String pInternalName, String pParentName, String pAddonPositionID) {
        this(pInternalName, pParentName, pAddonPositionID, 1);
    }

    /**
     * Standard constructor used to create Addons which support multiple of them selves on a single position
     *
     * @param pInternalName       The internal name of the addon
     * @param pParentName         The MLA Armor this addon should be registered to.
     * @param pAddonPositionID    The position of the addon on the MLA armor.
     * @param pMaxInstalledAmount The max amount of addons that are allowed on a single position
     * @throws InvalidParameterException Will be thrown if more than the allowed max are passed as parameter.
     */
    public MLAAddon(String pInternalName, String pParentName, String pAddonPositionID, Integer pMaxInstalledAmount) {
        this.iInternalName = pInternalName;
        this.iParentName = pParentName;
        this.iAddonPositionID = pAddonPositionID;
        this.iMaxInstalledAmount = pMaxInstalledAmount;
    }

    /**
     * Function to get the internal name
     *
     * @return The internal name of the addon
     * <p/>
     * Classes you might be interested in:
     * @see MultiLayeredArmor
     * @see ArmorAddonPosition
     */
    public String getInternalName() {
        return this.iInternalName;
    }

    /**
     * Function to get the InternalName of the ItemArmor this addon is registered to
     *
     * @return The InternalName of the ItemArmor this addon is registered to.
     * <p/>
     * Classes that might interest you:
     * @see MultiLayeredArmor
     */
    public String getParentName() {
        return this.iParentName;
    }

    /**
     * Function to get the positionid of the addon on the MLA armor
     *
     * @return The position of the addon on the armor
     * <p/>
     * Classes you might be interested in:
     * @see MultiLayeredArmor
     * @see ArmorAddonPosition
     */
    public String getAddonPositionID() {
        return this.iAddonPositionID;
    }

    public Integer getMaxInstalledAmount() {
        return this.iMaxInstalledAmount;
    }


    //###################Methods used to ensure that the resource management is overal the same#########################

    /**
     * Function to get the current resource representing this addon
     *
     * @return The resource representing this Addon
     */
    public CustomResource getResource() {
        return this.iResource;
    }

    /**
     * Function to set the current resource representing this addon
     *
     * @param pNewResource The new resource that should represent this addon.
     */
    public void setResource(CustomResource pNewResource) {
        this.iResource = pNewResource;
    }

    //##################Functions used to configure most of the crafting logic##########################################

    /**
     * Function to be implemented by programmer that vaildates the crafting.
     *
     * @param pAddonIDToCheckAgainst The Addon ID that is already on the Armor, or that is being put on the armor. You need to allow (return true) or cancel the addition (return false)
     *                               Do this by comparing the ID given in this parameter anyway you like and returning the proper bool value.
     * @return A Bool that tells the factory to either allow(true) or disallow(false) the crafting.
     */
    public abstract boolean validateCrafting(String pAddonIDToCheckAgainst, boolean pInstalled);
}
