/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.Orion.Armory.API.Crafting.SmithingsAnvil.Recipe;

import com.Orion.Armory.API.Knowledge.IKnowledgedGameElement;
import com.Orion.Armory.Util.References;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;

import java.util.TreeMap;

public abstract class AnvilRecipeKnowledge implements IKnowledgedGameElement {

    String iSaveKey;

    Float iCurrentExperience = 0F;
    Float iMinimalExperienceLevel = 0F;
    Float iMaximalExperienceLevel = 1F;
    TreeMap<Float, String> iUntranslatedExperienceLevels = new TreeMap<Float, String>();

    @Override
    public String getSaveKey() {
        return iSaveKey;
    }

    @Override
    public IKnowledgedGameElement setSaveKey(String pNewSaveKey) {
        iSaveKey = pNewSaveKey;
        return this;
    }

    @Override
    public void saveToNBT(NBTTagCompound pIEEPCompound) {
        NBTTagCompound tRecipeCompound = new NBTTagCompound();
        tRecipeCompound.setFloat(References.InternalNames.ExtendedEntityProperties.ANVILRECIPEEXPERIENCE, iCurrentExperience);
        pIEEPCompound.setTag(getSaveKey(), tRecipeCompound);
    }

    @Override
    public void readFromNBT(NBTTagCompound pIEEPCompound) {
        NBTTagCompound tRecipeKnowlegde = pIEEPCompound.getCompoundTag(getSaveKey());
        iCurrentExperience = tRecipeKnowlegde.getFloat(References.InternalNames.ExtendedEntityProperties.ANVILRECIPEEXPERIENCE);
    }

    @Override
    public Float getExperienceLevel() {
        return iCurrentExperience;
    }

    @Override
    public IKnowledgedGameElement setExperienceLevel(Float pNewLevel) {
        iCurrentExperience = pNewLevel;
        return this;
    }

    public String getCurrentTranslatedExperienceLevel() {
        String tLastTranslation = StatCollector.translateToLocal((String) iUntranslatedExperienceLevels.values().toArray()[0]);

        for (Float tExperienceLevel : iUntranslatedExperienceLevels.keySet()) {
            if (tExperienceLevel > iCurrentExperience)
                return tLastTranslation;

            tLastTranslation = StatCollector.translateToLocal(iUntranslatedExperienceLevels.get(tExperienceLevel));
        }

        return tLastTranslation;
    }
}
