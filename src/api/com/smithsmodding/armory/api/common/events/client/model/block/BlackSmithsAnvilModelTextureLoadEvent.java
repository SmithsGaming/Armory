package com.smithsmodding.armory.api.common.events.client.model.block;

import com.smithsmodding.armory.api.client.model.deserializers.definition.AnvilModelDefinition;
import com.smithsmodding.armory.api.util.references.ModBlocks;
import com.smithsmodding.armory.api.util.references.ModLogger;
import com.smithsmodding.smithscore.common.events.SmithsCoreEvent;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Marc on 22.02.2016.
 */
public class BlackSmithsAnvilModelTextureLoadEvent extends SmithsCoreEvent
{

    @NotNull Map<ResourceLocation, String> additionalTopTextureLayers = new HashMap<>();
    @NotNull Map<ResourceLocation, String> additionalBottomTextureLayers = new HashMap<>();

    @NotNull
    public Map<ResourceLocation, String> getAdditionalTopTextureLayers () {
        return additionalTopTextureLayers;
    }

    @NotNull
    public Map<ResourceLocation, String> getAdditionalBottomTextureLayers () {
        return additionalBottomTextureLayers;
    }

    public void addAdditionalTopTextureLayers(@NotNull ResourceLocation modelDefinitionLocation) {
        try {
            additionalTopTextureLayers.putAll(AnvilModelDefinition.loadModelTexturesForTop(modelDefinitionLocation));
        } catch (Exception ex) {
            ModLogger.getInstance().error("Error while attempting to add: " + modelDefinitionLocation.toString() + " to the model definition of: " + ModBlocks.BL_ANVIL.getUnlocalizedName());
        }
    }

    public void addAdditionalBottomTextureLayers(@NotNull ResourceLocation modelDefinitionLocation) {
        try {
            additionalBottomTextureLayers.putAll(AnvilModelDefinition.loadModelTexturesForBottom(modelDefinitionLocation));
        } catch (Exception ex) {
            ModLogger.getInstance().error("Error while attempting to add: " + modelDefinitionLocation.toString() + " to the model definition of: " + ModBlocks.BL_ANVIL.getUnlocalizedName());
        }
    }

}
