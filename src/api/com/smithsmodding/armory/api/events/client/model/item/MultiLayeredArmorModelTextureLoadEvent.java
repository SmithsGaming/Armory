package com.smithsmodding.armory.api.events.client.model.item;

import com.smithsmodding.armory.api.armor.MultiLayeredArmor;
import com.smithsmodding.armory.api.model.deserializers.MultiLayeredArmorModelDeserializer;
import com.smithsmodding.armory.api.model.deserializers.definition.MultiLayeredArmorModelDefinition;
import com.smithsmodding.armory.api.util.references.ModLogger;
import com.smithsmodding.smithscore.common.events.SmithsCoreEvent;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marc on 07.12.2015.
 */
public class MultiLayeredArmorModelTextureLoadEvent extends SmithsCoreEvent {

    private final MultiLayeredArmor armor;
    List<MultiLayeredArmorModelDefinition> additionalTextureDefinitions = new ArrayList<>();

    public MultiLayeredArmorModelTextureLoadEvent (MultiLayeredArmor armor) {
        this.armor = armor;
    }

    public MultiLayeredArmor getArmor () {
        return armor;
    }

    public List<MultiLayeredArmorModelDefinition> getAdditionalTextureLayers() {
        return additionalTextureDefinitions;
    }

    public void addAdditionalTextureLayers (ResourceLocation modelDefinitionLocation) {
        try {
            additionalTextureDefinitions.add(MultiLayeredArmorModelDeserializer.instance.deserialize(modelDefinitionLocation));
        } catch (Exception ex) {
            ModLogger.getInstance().error("Error while attempting to add: " + modelDefinitionLocation.toString() + " to the model definition of: " + armor.getUniqueID());
        }
    }
}
