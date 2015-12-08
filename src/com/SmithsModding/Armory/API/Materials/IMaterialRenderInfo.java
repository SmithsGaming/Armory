package com.SmithsModding.Armory.API.Materials;

import com.SmithsModding.Armory.API.Textures.*;
import com.SmithsModding.SmithsCore.Util.Client.Color.MinecraftColor;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/*
  A BIG NOTE UPFRONT. Due to the similarities between TiC ToolSystem and Armories Armor system this is a near repackage.
  Most of this code falls under their license, although some changes are made to fit the system in with Armories used
  of Wrapper classes instead of direct access.
 */

/**
 * Determines the type of texture used for rendering a specific material
 */
@SideOnly(Side.CLIENT)
public interface IMaterialRenderInfo {

    /**
     * Function to get teh Texture based of the baseTexture and the location.
     * Loads the location and generates a Sprite.
     *
     * @param baseTexture The base texture.
     * @param location    The location of the new texture.
     * @return A modified verion of the base texture.
     */
    TextureAtlasSprite getTexture (TextureAtlasSprite baseTexture, String location);

    /**
     * Indicates if the texture has been stitched or not. The Texture creator will stitch it if false is returned.
     *
     * @return True when the texture is already stitched.
     */
    boolean isStitched ();

    /**
     * Indicates if vertex coloring is used during the creation of the modified texture´.
     *
     * @return True when VertexColoring is used, false when not.
     */
    boolean useVertexColoring ();

    /**
     * The color in which the material should be rendered.
     *
     * @return A MinecraftColor instance that shows which color the material has.
     */
    MinecraftColor getVertexColor ();

    /**
     * A special suffix for the texture.
     *
     * @return "" When no suffix exists or a suffix.
     */
    String getTextureSuffix ();

    /**
     * Function used to set the suffix. Returns the instance the method was called on.
     *
     * @param suffix The new Suffix.
     * @return The instance this method was called on, used for method chaining.
     */
    IMaterialRenderInfo setTextureSuffix (String suffix);

    /**
     * Abstract core implementation of the RenderInfo.
     */
    abstract class AbstractMaterialRenderInfo implements IMaterialRenderInfo {
        private String suffix;

        @Override
        public boolean isStitched () {
            return true;
        }

        @Override
        public boolean useVertexColoring () {
            return false;
        }

        @Override
        public MinecraftColor getVertexColor () {
            return new MinecraftColor(MinecraftColor.white);
        }

        @Override
        public String getTextureSuffix () {
            return suffix;
        }

        @Override
        public IMaterialRenderInfo setTextureSuffix (String suffix) {
            this.suffix = suffix;
            return this;
        }
    }

    /**
     * Does not actually generate a new texture. Used for vertex-coloring in the model generation
     * Safes VRAM, so we use vertex colors instead of creating new data.
     */
    class Default extends AbstractMaterialRenderInfo {
        public final MinecraftColor color;

        public Default (MinecraftColor color) {
            this.color = color;
        }

        @Override
        public TextureAtlasSprite getTexture (TextureAtlasSprite baseTexture, String location) {
            return baseTexture;
        }

        @Override
        public boolean isStitched () {
            return false;
        }

        @Override
        public boolean useVertexColoring () {
            return true;
        }

        @Override
        public MinecraftColor getVertexColor () {
            return color;
        }
    }

    /**
     * Colors the texture of the tool with the material color
     */
    class MultiColor extends AbstractMaterialRenderInfo {

        // colors to be used
        protected final int low, mid, high;

        public MultiColor (int low, int mid, int high) {
            this.low = low;
            this.mid = mid;
            this.high = high;
        }

        @Override
        public MinecraftColor getVertexColor () {
            return new MinecraftColor(mid);
        }

        @Override
        public TextureAtlasSprite getTexture (TextureAtlasSprite baseTexture, String location) {
            return new SimpleColoredTexture(low, mid, high, baseTexture, location);
        }
    }

    class InverseMultiColor extends MultiColor {

        public InverseMultiColor (int low, int mid, int high) {
            super(low, mid, high);
        }

        @Override
        public TextureAtlasSprite getTexture (TextureAtlasSprite baseTexture, String location) {
            return new InverseColoredTexture(low, mid, high, baseTexture, location);
        }
    }

    class Metal extends AbstractMaterialRenderInfo {
        public int color;
        protected float shinyness;
        protected float brightness;
        protected float hueshift;

        public Metal (int color, float shinyness, float brightness, float hueshift) {
            this.color = color;
            this.shinyness = shinyness;
            this.brightness = brightness;
            this.hueshift = hueshift;
        }

        public Metal (int color) {
            this(color, 0.4f, 0.4f, 0.1f);
        }


        @Override
        public TextureAtlasSprite getTexture (TextureAtlasSprite baseTexture, String location) {
            return new MetalColoredTexture(baseTexture, location, color, shinyness, brightness, hueshift);
        }
    }

    /**
     * Uses a (block) texture instead of a color to create the texture
     */
    class BlockTexture extends AbstractMaterialRenderInfo {

        protected String texturePath;
        protected Block block;

        public BlockTexture (String texturePath) {
            this.texturePath = texturePath;
        }

        @Override
        public TextureAtlasSprite getTexture (TextureAtlasSprite baseTexture, String location) {
            TextureAtlasSprite blockTexture = Minecraft.getMinecraft().getTextureMapBlocks().getTextureExtry(texturePath);

            if (blockTexture == null) {
                blockTexture = Minecraft.getMinecraft().getTextureMapBlocks().getMissingSprite();
            }

            TextureColoredTexture sprite = new TextureColoredTexture(blockTexture, baseTexture, location);
            sprite.stencil = false;
            return sprite;
        }
    }


    /**
     * Creates an animated texture from an animated base texture. USE WITH CAUTION.
     * ACTUALLY ONLY USE THIS IF YOU KNOW EXACTLY WHAT YOU'RE DOING.
     */
    class AnimatedTexture extends AbstractMaterialRenderInfo {

        protected String texturePath;

        public AnimatedTexture (String texturePath) {
            this.texturePath = texturePath;
        }

        @Override
        public TextureAtlasSprite getTexture (TextureAtlasSprite baseTexture, String location) {
            TextureAtlasSprite blockTexture = Minecraft.getMinecraft().getTextureMapBlocks().getTextureExtry(texturePath);

            if (blockTexture == null) {
                blockTexture = Minecraft.getMinecraft().getTextureMapBlocks().getMissingSprite();
            }

            TextureColoredTexture sprite = new AnimatedColoredTexture(blockTexture, baseTexture, location);
            return sprite;
        }
    }

}
