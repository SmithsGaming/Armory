package com.smithsmodding.armory.client.model.item.events;

import com.smithsmodding.armory.Armory;
import com.smithsmodding.armory.api.armor.MultiLayeredArmor;
import com.smithsmodding.armory.client.model.deserializers.MultiLayeredArmorModelDeserializer;
import com.smithsmodding.armory.client.model.deserializers.definition.MultiLayeredArmorModelDefinition;
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
            Armory.getLogger().error("Error while attempting to add: " + modelDefinitionLocation.toString() + " to the model definition of: " + armor.getUniqueID());
        }
    }
}
