package com.smithsmodding.armory.client.model.block.events;

import com.smithsmodding.armory.*;
import com.smithsmodding.armory.api.armor.*;
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

    Map<String, String> additionalTextureLayers = new HashMap<java.lang.String, java.lang.String>();

    public Map<String, String> getAdditionalTextureLayers () {
        return additionalTextureLayers;
    }

    public void addAdditionalTextureLayers (ResourceLocation modelDefinitionLocation) {
        try {
            additionalTextureLayers.putAll(ModelHelper.loadTexturesFromJson(modelDefinitionLocation));
        } catch (Exception ex) {
            Armory.getLogger().error("Error while attempting to add: " + modelDefinitionLocation.toString() + " to the model definition of: " + GeneralRegistry.Blocks.blockBlackSmithsAnvil.getUnlocalizedName());
        }
    }

}
