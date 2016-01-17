package com.smithsmodding.Armory.API.Armor;

import net.minecraft.util.*;

/**
 * Created by Marc on 07.12.2015.
 */
public abstract class MaterialDependentMLAAddon extends MLAAddon {
    String uniqueMaterialID;
    String materialIndependentID;

    public MaterialDependentMLAAddon (String uniqueID, String uniqueMaterialID, String uniqueArmorID, String addonPositionID, ResourceLocation itemWholeTextureLocation, ResourceLocation modelTextureLocation, int layerPriority) {
        this(uniqueID, uniqueMaterialID, uniqueArmorID, addonPositionID, 1, itemWholeTextureLocation, modelTextureLocation, layerPriority);
    }

    public MaterialDependentMLAAddon (String uniqueID, String uniqueMaterialID, String uniqueArmorID, String addonPositionID, Integer maximumInstalledAmount, ResourceLocation itemWholeTextureLocation, ResourceLocation modelTextureLocation, int layerPriority) {
        super(uniqueID + "-" + uniqueMaterialID, uniqueArmorID, addonPositionID, maximumInstalledAmount, itemWholeTextureLocation, modelTextureLocation, layerPriority);

        this.materialIndependentID = uniqueID;
        this.uniqueMaterialID = uniqueMaterialID;
    }

    @Override
    public boolean isMaterialDependent () {
        return true;
    }

    public String getUniqueMaterialID () {
        return uniqueMaterialID;
    }

    public String getMaterialIndependentID () {
        return materialIndependentID;
    }
}
