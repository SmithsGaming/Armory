package com.smithsmodding.armory.api.events.client.model.block;

import com.smithsmodding.armory.api.model.deserializers.definition.AnvilModelDefinition;
import com.smithsmodding.armory.api.util.references.ModBlocks;
import com.smithsmodding.armory.api.util.references.ModLogger;
import com.smithsmodding.smithscore.common.events.SmithsCoreEvent;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

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
            additionalTopTextureLayers.putAll(AnvilModelDefinition.loadModelTexturesForTop(modelDefinitionLocation));
        } catch (Exception ex) {
            ModLogger.getInstance().error("Error while attempting to add: " + modelDefinitionLocation.toString() + " to the model definition of: " + ModBlocks.blockBlackSmithsAnvil.getUnlocalizedName());
        }
    }

    public void addAdditionalBottomTextureLayers (ResourceLocation modelDefinitionLocation) {
        try {
            additionalBottomTextureLayers.putAll(AnvilModelDefinition.loadModelTexturesForBottom(modelDefinitionLocation));
        } catch (Exception ex) {
            ModLogger.getInstance().error("Error while attempting to add: " + modelDefinitionLocation.toString() + " to the model definition of: " + ModBlocks.blockBlackSmithsAnvil.getUnlocalizedName());
        }
    }

}
