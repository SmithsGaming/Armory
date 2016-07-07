package com.smithsmodding.armory.api.armor;
/*
*   ArmorAddonPosition
*   Created by: Orion
*   Created on: 4-7-2014
*/

public final class ArmorAddonPosition {
    protected String parentArmorId;
    protected String internalName = "";
    protected Integer maxAddonsOnPosition = 1;

    /**
     * Constructor to create a new MLAAddon position on MLA Compatible armor.
     *
     * @param pInternalName        The internal name for this position. Attention these have to be unique!
     * @param pParentArmorID       The Internalname of the MLA armor object this position will be bound to.
     * @param pMaxAddonsOnPosition The max addons allowed on this position
     *
     *                             Classes you might be interested in:
     * @see MultiLayeredArmor
     * @see MLAAddon
     */
    public ArmorAddonPosition(String pInternalName, String pParentArmorID, Integer pMaxAddonsOnPosition) {
        this.internalName = pInternalName;
        this.parentArmorId = pParentArmorID;
        this.maxAddonsOnPosition = pMaxAddonsOnPosition;
    }

    /**
     * Function to get the internal name of this Addon position
     *
     * @return The internal name of this addon position
     *
     * Classes you might be interested in:
     * @see MultiLayeredArmor
     * @see MLAAddon
     */
    public String getInternalName() {
        return this.internalName;
    }

    /**
     * Function to get the current MLA armor object, this position is bound to.
     *
     * @return The current MLA armor this position is bound to
     *
     *
     * Classes you might be interested in:
     * @see MultiLayeredArmor
     * @see MLAAddon
     */
    public String getParentArmorID() {
        return this.parentArmorId;
    }

    /**
     * Function to get the max amount of MLA addons that are allowed on this position
     *
     * @return The max amount of MLA addons that are allowed on this position
     *
     * Classes you might be interested in:
     * @see MultiLayeredArmor
     * @see MLAAddon
     */
    public Integer getMaxAddonsOnPosition() {
        return this.maxAddonsOnPosition;
    }

    /**
     * Function used to set the max amount of MLA addons allowed on this position
     *
     * @param pMaxAddonsOnPosition The new max amount of addons
     *
     *                             Classes you might be interested in:
     * @see MultiLayeredArmor
     * @see MLAAddon
     */
    public void setMaxAddonsOnPosition(Integer pMaxAddonsOnPosition) {
        this.maxAddonsOnPosition = pMaxAddonsOnPosition;
    }


}
