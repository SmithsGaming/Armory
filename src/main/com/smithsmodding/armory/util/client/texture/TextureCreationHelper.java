package com.smithsmodding.armory.util.client.texture;

import com.smithsmodding.armory.api.client.model.renderinfo.IRenderInfoProvider;
import com.smithsmodding.smithscore.client.textures.AbstractColoredTexture;
import com.smithsmodding.smithscore.util.client.ResourceHelper;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by marcf on 1/5/2017.
 */
public class TextureCreationHelper {

    /**
     * Method to create a modified texture given the RenderInfoProvider.
     *
     * @param renderInfoProvider The provider that provides the renderinfo that manipulates the basetexture
     * @param textureIdentifier The identifier for the new texture. Appended to the baseTexture and the creationIdentifier.
     * @param baseTexture The address of the base texture to modify
     * @param base The sprite data of the base texture.
     * @param map The TextureMap to register the texture to.
     * @param creationIdentifier A Identifier of whom created the texture. Null results in no Identifier and seperator being added.
     *
     * @return A modified version of the base Texture.
     */
    public static TextureAtlasSprite createTexture(@Nonnull IRenderInfoProvider renderInfoProvider, @Nonnull String textureIdentifier, @Nonnull ResourceLocation baseTexture, @Nonnull TextureAtlasSprite base, @Nonnull TextureMap map, @Nullable String creationIdentifier) {
        creationIdentifier = creationIdentifier == null ? "" : "_" + creationIdentifier;
        String location = baseTexture.toString() + creationIdentifier + "_" + textureIdentifier;
        TextureAtlasSprite sprite;

        if (ResourceHelper.exists(location)) {
            sprite = map.registerSprite(new ResourceLocation(location));
        } else {
            // material does not need a special generated texture
            if (renderInfoProvider.getRenderInfo() == null) {
                return null;
            }

            TextureAtlasSprite matBase = base;

            // different base texture?
            if (renderInfoProvider.getRenderInfo().getTextureSuffix() != null) {
                String loc2 = baseTexture.toString() + creationIdentifier + "_" + renderInfoProvider.getRenderInfo().getTextureSuffix();
                TextureAtlasSprite base2 = map.getTextureExtry(loc2);
                // can we manually load it?
                if (base2 == null && ResourceHelper.exists(loc2)) {
                    base2 = new AbstractColoredTexture(loc2, loc2) {
                        @Override
                        protected int colorPixel(int pixel, int mipmap, int pxCoord) {
                            return pixel;
                        }
                    };

                    // save in the map so it's getting reused by the others and is available
                    map.setTextureEntry(base2);
                }
                if (base2 != null) {
                    matBase = base2;
                }
            }

            sprite = renderInfoProvider.getRenderInfo().getTexture(matBase, location);
        }

        // stitch new textures
        if (sprite != null && renderInfoProvider.getRenderInfo().isStitched()) {
            map.setTextureEntry(sprite);
        }

        return sprite;
    }

}
