package com.smithsmodding.armory.client.model.item.events;

import com.smithsmodding.armory.api.armor.*;
import com.smithsmodding.armory.*;
import com.smithsmodding.smithscore.common.events.*;
import com.smithsmodding.smithscore.util.client.*;
import net.minecraft.util.*;

import java.util.*;

/**
 * Created by Marc on 07.12.2015.
 */
public class MultiLayeredArmorModelTextureLoadEvent extends SmithsCoreEvent {

    private final MultiLayeredArmor armor;
    Map<java.lang.String, java.lang.String> additionalTextureLayers = new HashMap<java.lang.String, java.lang.String>();

    public MultiLayeredArmorModelTextureLoadEvent (MultiLayeredArmor armor) {
        this.armor = armor;
    }

    public MultiLayeredArmor getArmor () {
        return armor;
    }

    public Map<String, String> getAdditionalTextureLayers () {
        return additionalTextureLayers;
    }

    public void addAdditionalTextureLayers (ResourceLocation modelDefinitionLocation) {
        try {
            additionalTextureLayers.putAll(ModelHelper.loadTexturesFromJson(modelDefinitionLocation));
        } catch (Exception ex) {
            Armory.getLogger().error("Error while attempting to add: " + modelDefinitionLocation.toString() + " to the model definition of: " + armor.getUniqueID());
        }
    }
}
