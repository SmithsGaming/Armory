package com.smithsmodding.armory.client.textures.creators;

import com.smithsmodding.armory.api.common.material.armor.ICoreArmorMaterial;
import com.smithsmodding.armory.api.client.textures.creation.ICreationController;
import com.smithsmodding.armory.api.util.references.ModLogger;
import com.smithsmodding.armory.common.api.ArmoryAPI;
import com.smithsmodding.armory.util.client.texture.TextureCreationHelper;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by marcf on 1/5/2017.
 */
public class CoreTextureCreator extends IForgeRegistryEntry.Impl<ICreationController>  implements ICreationController {

    public static final String CORETEXTUREIDENTIFIER = "Core";

    /**
     * Method to create a materialized texture. It is called from the MaterializedTextureCreator to create the texture in the Map
     *
     * @param map          The TextureMap to register the textures to.
     * @param baseTexture  The baseTexture to manipulate
     * @param buildSprites A List of textures already created. The upper Map holds te baseTexture as key and the lower map the material name as key.
     */
    @Override
    public void createMaterializedTextures(@Nonnull TextureMap map, @Nonnull ResourceLocation baseTexture, @Nonnull Map<ResourceLocation, Map<ResourceLocation, TextureAtlasSprite>> buildSprites) {
        if (!buildSprites.containsKey(baseTexture))
            buildSprites.put(baseTexture, new HashMap<>());

        //Grabbing the base texture so that we can color a copy.
        TextureAtlasSprite base = map.getTextureExtry(baseTexture.toString());
        if (base == null) {
            //A missing texture does not need coloring. Skipping.
            ModLogger.getInstance().error("Missing base texture for Core coloring: " + baseTexture.toString());
            return;
        }

        Map<ResourceLocation, TextureAtlasSprite> coloredSprites = buildSprites.get(baseTexture);
        for (ICoreArmorMaterial material : ArmoryAPI.getInstance().getRegistryManager().getCoreMaterialRegistry()) {
            TextureAtlasSprite sprite =  TextureCreationHelper.createTexture(material, material.getTextureOverrideIdentifier(), baseTexture, base, map, CORETEXTUREIDENTIFIER);
            if (sprite != null) {
                coloredSprites.put(material.getRegistryName(), sprite);
            }
        }
    }


}
