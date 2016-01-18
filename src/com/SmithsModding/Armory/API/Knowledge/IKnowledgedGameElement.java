/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.smithsmodding.armory.api.knowledge;

import net.minecraft.nbt.*;

public interface IKnowledgedGameElement {
    String getSaveKey ();

    IKnowledgedGameElement setSaveKey (String pNewSaveKey);

    void saveToNBT (NBTTagCompound pIEEPCompound);

    void readFromNBT (NBTTagCompound pIEEPCompound);

    Float getExperienceLevel ();

    IKnowledgedGameElement setExperienceLevel (Float pNewLevel);
}
