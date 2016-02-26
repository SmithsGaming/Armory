package com.smithsmodding.armory.client.model.block.events;

import com.smithsmodding.armory.*;
import com.smithsmodding.armory.api.armor.*;
import com.smithsmodding.armory.client.model.loaders.*;
import com.smithsmodding.armory.common.registry.*;
import com.smithsmodding.smithscore.common.events.*;
import com.smithsmodding.smithscore.util.client.*;
import net.minecraft.util.*;

import java.util.*;

/**
 * Created by Marc on 22.02.2016.
 */
public class BlackSmithsAnvilModelTextureLoadEvent extends SmithsCoreEvent
{

    Map<String, String> additionalTopTextureLayers = new HashMap<>();
    Map<String, String> additionalBottomTextureLayers = new HashMap<>();

    public Map<String, String> getAdditionalTopTextureLayers () {
        return additionalTopTextureLayers;
    }

    public Map<String, String> getAdditionalBottomTextureLayers () {
        return additionalBottomTextureLayers;
    }

    public void addAdditionalTopTextureLayers (ResourceLocation modelDefinitionLocation) {
        try {
            additionalTopTextureLayers.putAll(AnvilModelLoader.AnvilModelDefinition.loadModelTexturesForTop(modelDefinitionLocation));
        } catch (Exception ex) {
            Armory.getLogger().error("Error while attempting to add: " + modelDefinitionLocation.toString() + " to the model definition of: " + GeneralRegistry.Blocks.blockBlackSmithsAnvil.getUnlocalizedName());
        }
    }

    public void addAdditionalBottomTextureLayers (ResourceLocation modelDefinitionLocation) {
        try {
            additionalBottomTextureLayers.putAll(AnvilModelLoader.AnvilModelDefinition.loadModelTexturesForBottom(modelDefinitionLocation));
        } catch (Exception ex) {
            Armory.getLogger().error("Error while attempting to add: " + modelDefinitionLocation.toString() + " to the model definition of: " + GeneralRegistry.Blocks.blockBlackSmithsAnvil.getUnlocalizedName());
        }
    }

}
