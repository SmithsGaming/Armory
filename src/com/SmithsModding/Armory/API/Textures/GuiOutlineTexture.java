package com.SmithsModding.Armory.API.Textures;

/*
  A BIG NOTE UPFRONT. Due to the similarities between TiC ToolSystem and Armories Armor system this is a near repackage.
  Most of this code falls under their license, although some changes are made to fit the system in with Armories used
  of Wrapper classes instead of direct access.
 */

import net.minecraft.client.renderer.texture.TextureAtlasSprite;

public class GuiOutlineTexture extends ExtraUtilityTexture {

    public GuiOutlineTexture (String baseTextureLocation, String spriteName) {
        super(baseTextureLocation, spriteName);
    }

    public GuiOutlineTexture (TextureAtlasSprite baseTexture, String spriteName) {
        super(baseTexture, spriteName);
    }

    @Override
    protected int colorPixel (int pixel, int mipmap, int pxCoord) {
        if (!trans[pxCoord]) {
            if (edge[pxCoord]) {
                return compose(50, 50, 50, 255);
            } else {
                return 0;
            }
        }

        return pixel;
    }
}
