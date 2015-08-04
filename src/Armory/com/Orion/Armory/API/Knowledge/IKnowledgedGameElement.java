/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.Orion.Armory.API.Knowledge;

import net.minecraft.nbt.NBTTagCompound;

public interface IKnowledgedGameElement {
    String getSaveKey();

    IKnowledgedGameElement setSaveKey(String pNewSaveKey);

    void saveToNBT(NBTTagCompound pIEEPCompound);

    void readFromNBT(NBTTagCompound pIEEPCompound);

    Float getExperienceLevel();

    IKnowledgedGameElement setExperienceLevel(Float pNewLevel);
}