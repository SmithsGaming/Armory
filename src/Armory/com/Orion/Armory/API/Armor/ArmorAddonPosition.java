package com.Orion.Armory.API.Armor;
/*
*   ArmorAddonPosition
*   Created by: Orion
*   Created on: 4-7-2014
*/

public class ArmorAddonPosition {
    protected String iParentArmorID;
    protected String iInternalName = "";
    protected Integer iMaxAddonsOnPosition = 1;

    /**
     * Constructor to create a new MLAAddon position on MLA Compatible armor.
     *
     * @param pInternalName        The internal name for this position. Attention these have to be unique!
     * @param pParentArmorID       The Internalname of the MLA Armor object this position will be bound to.
     * @param pMaxAddonsOnPosition The max addons allowed on this position
     *                             <p/>
     *                             Classes you might be interested in:
     * @see MultiLayeredArmor
     * @see MLAAddon
     */
    public ArmorAddonPosition(String pInternalName, String pParentArmorID, Integer pMaxAddonsOnPosition) {
        this.iInternalName = pInternalName;
        this.iParentArmorID = pParentArmorID;
        this.iMaxAddonsOnPosition = pMaxAddonsOnPosition;
    }

    /**
     * Function to get the internal name of this Addon position
     *
     * @return The internal name of this addon position
     * <p/>
     * Classes you might be interested in:
     * @see MultiLayeredArmor
     * @see MLAAddon
     */
    public String getInternalName() {
        return this.iInternalName;
    }

    /**
     * Function to get the current MLA armor object, this position is bound to.
     *
     * @return The current MLA armor this position is bound to
     * <p/>
     * <p/>
     * Classes you might be interested in:
     * @see MultiLayeredArmor
     * @see MLAAddon
     */
    public String getParentArmorID() {
        return this.iParentArmorID;
    }

    /**
     * Function to get the max amount of MLA addons that are allowed on this position
     *
     * @return The max amount of MLA Addons that are allowed on this position
     * <p/>
     * Classes you might be interested in:
     * @see MultiLayeredArmor
     * @see MLAAddon
     */
    public Integer getMaxAddonsOnPosition() {
        return this.iMaxAddonsOnPosition;
    }

    /**
     * Function used to set the max amount of MLA addons allowed on this position
     *
     * @param pMaxAddonsOnPosition The new max amount of addons
     *                             <p/>
     *                             Classes you might be interested in:
     * @see MultiLayeredArmor
     * @see MLAAddon
     */
    public void setMaxAddonsOnPosition(Integer pMaxAddonsOnPosition) {
        this.iMaxAddonsOnPosition = pMaxAddonsOnPosition;
    }


}
