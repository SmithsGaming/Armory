package com.smithsmodding.armory.common.material;

import com.smithsmodding.armory.api.armor.*;
import net.minecraft.util.*;

/**
 * Created by Marc on 06.12.2015.
 */
public class ChainLayer extends MaterialDependentMLAAddon {

    public ChainLayer (String pInternalName, String pParentName, String pAddonPositionID, String pMaterialID, ResourceLocation itemTextureWhole, ResourceLocation modelTextureLocation) {
        super(pInternalName, pMaterialID, pParentName, pAddonPositionID, itemTextureWhole, modelTextureLocation, 0);
    }

    @Override
    public boolean validateCrafting (String pAddonIDToCheckAgainst, boolean pInstalled) {
        return pAddonIDToCheckAgainst.contains("base");
    }
}
