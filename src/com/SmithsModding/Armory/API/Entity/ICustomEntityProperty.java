/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.SmithsModding.Armory.API.Entity;

import net.minecraft.entity.Entity;
import net.minecraftforge.common.IExtendedEntityProperties;

public interface ICustomEntityProperty extends IExtendedEntityProperties {

    /**
     * Gets the Entity this KnowledgeProperty belongs to
     *
     * @return The entity.
     */
    Entity getEntity();

    /**
     * Function used to attach a new Instance of a property to a Player
     *
     * @param pEntity The entity to register to
     */
    void register(Entity pEntity);

    /**
     * Function used to get a instance of the implementing property from the given Player
     *
     * @param <T>     The type of property
     * @param pEntity The entity to retrieve the property from
     * @return A instance of T if the given player has that property or null if that player does not have that property
     */
    <T extends IExtendedEntityProperties> IExtendedEntityProperties get(Entity pEntity);

    /**
     * The given key under which this property is stored, both after death as well was in NBT Format
     *
     * @return A String with the key
     */
    String getSaveKey();

    /**
     * Function used to store a instance of this in memory and not locally on the player.
     */
    void saveProxyData();

    /**
     * Function to load a stored instance of this from memory to the local given player.
     */
    void loadProxyData();

    /**
     * Function used to sync the instance of this between Client and Server.
     */
    void syncProperties();
}
