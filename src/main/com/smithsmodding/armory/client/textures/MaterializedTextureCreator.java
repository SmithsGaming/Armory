package com.smithsmodding.armory.client.textures;

import com.google.common.collect.Maps;
import com.smithsmodding.armory.api.client.textures.creation.ICreationController;
import com.smithsmodding.armory.api.client.textures.types.GuiOutlineTexture;
import com.smithsmodding.armory.api.common.armor.IMultiComponentArmor;
import com.smithsmodding.armory.api.common.capability.armor.IArmorCapability;
import com.smithsmodding.armory.api.common.material.armor.ICoreArmorMaterial;
import com.smithsmodding.armory.api.common.material.client.MaterialRenderControllers;
import com.smithsmodding.armory.api.util.references.ModLogger;
import com.smithsmodding.armory.common.api.ArmoryAPI;
import com.smithsmodding.armory.common.material.MedievalCoreArmorMaterial;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.LoaderState;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import javax.annotation.Nonnull;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Marc on 06.12.2015.
 * <p>
 * TextureManager used to handle grayscale textures and color them for each material.
 * Modelled after parts of the TinkersConstruct CustomTextureCreator.
 */
public class MaterializedTextureCreator implements IResourceManagerReloadListener {

    @Nullable
    private static final ICoreArmorMaterial guiMaterial;
    //Variable containing the location of all grayscale base textures.
    @Nonnull
    private static ArrayList<ResourceLocation> baseTextures = new ArrayList<ResourceLocation>();
    //Variable that holds the colored end textures when the Creator has reloaded
    @Nonnull
    private static Map<ResourceLocation, Map<ResourceLocation, TextureAtlasSprite>> buildSprites = Maps.newHashMap();

    //Initializes the dummy gui material with a proper set of render info.
    static {
        guiMaterial = (ICoreArmorMaterial) new MedievalCoreArmorMaterial("", "", "", 0F,0F,0,0,0F) {
            /**
             * Method to getCreationRecipe the BaseDurability of a piece of armor made out of this material.
             *
             * @param armor The armor to getCreationRecipe the base durability for.
             * @return The durability of a piece of armor made out of this material.
             */
            @Nonnull
            @Override
            public Integer getBaseDurabilityForArmor(@Nonnull IMultiComponentArmor armor) {
                return 0;
            }

            /**
             * Method to getCreationRecipe all the default capabilities this ArmorMaterial provides.
             *
             * @param armor
             * @return All the default capabilities this ArmorMaterial provides.
             */
            @Nonnull
            @Override
            public HashMap<Capability<? extends IArmorCapability>, Object> getOverrideCoreMaterialCapabilities(IMultiComponentArmor armor) {
                return new HashMap<>();
            }
        };
        guiMaterial.setRenderInfo(new MaterialRenderControllers.AbstractMaterialTextureController() {
            @Nonnull
            @Override
            public TextureAtlasSprite getTexture(@Nonnull TextureAtlasSprite baseTexture, String location) {
                return new GuiOutlineTexture(baseTexture, location);
            }
        });
    }

    /**
     * method to register a new GrayScale texture to the Creator.
     *
     * @param location The location of the Texture.
     */
    public static void registerBaseTexture(ResourceLocation location) {
        baseTextures.add(location);
    }

    /**
     * method to register more then one GrayScale texture to the Creator.
     *
     * @param locations The location of the textures to register.
     */
    public static void registerBaseTexture(@Nonnull Collection<ResourceLocation> locations) {
        baseTextures.addAll(locations);
    }

    /**
     * method to getCreationRecipe the builded textures.
     *
     * @return A map containing all the colored textures using the base texture and the materialname as keys.
     */
    @Nonnull
    public static Map<ResourceLocation, Map<ResourceLocation, TextureAtlasSprite>> getBuildSprites() {
        return buildSprites;
    }

    /**
     * Actual construction method is called from the ForgeEvent system.
     * This method kicks the creation of the textures of and provided a map to put the textures in.
     *
     * @param event The events fired before the TextureSheet is stitched. TextureStitchEvent.Pre instance.
     */
    @SubscribeEvent(priority = EventPriority.LOW)
    public void createCustomTextures(@Nonnull TextureStitchEvent.Pre event) {
        //Only run the creation once, after all mods have been loaded.
        if (!Loader.instance().hasReachedState(LoaderState.POSTINITIALIZATION)) {
            return;
        }

        //Function is called so that all textures can be created.
        createMaterialTextures(event.getMap());
    }

    /**
     * Method used to create textures that are material dependend based on the given baseTextures list.
     *
     * @param map The map to register the textures to.
     */
    public void createMaterialTextures(@Nonnull TextureMap map) {
        for (ResourceLocation baseTexture : baseTextures) {
            //NO Reason doing something twice!
            if (buildSprites.containsKey(baseTexture.toString()))
                continue;

            if (baseTexture.toString().equals("minecraft:missingno")) {
                //A missing texture does not need coloring. Skipping.
                continue;
            }

            for(ICreationController controller : ArmoryAPI.getInstance().getRegistryManager().getTextureCreationControllerRegistry()) {
                ModLogger.getInstance().info("Creating textures for: " + baseTexture.toString() + " with: " + controller.getRegistryName().toString());
                controller.createMaterializedTextures(map, baseTexture, buildSprites);
            }
        }
    }

    @SubscribeEvent
    public void postTextureStitch(TextureStitchEvent.Post e) throws Exception
    {
        TextureMap map = e.getMap();
        String name = map.getBasePath().replace('/', '_');
        int mip = map.getMipmapLevels();

        if (mip != 0)
            return;

        saveGlTexture(name, map.getGlTextureId(), mip);
    }

    public static void saveGlTexture(String name, int textureId, int mipmapLevels) {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);

        GL11.glPixelStorei(GL11.GL_PACK_ALIGNMENT, 1);
        GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);

        for (int level = 0; level <= mipmapLevels; level++) {
            int width = GL11.glGetTexLevelParameteri(GL11.GL_TEXTURE_2D, level, GL11.GL_TEXTURE_WIDTH);
            int height = GL11.glGetTexLevelParameteri(GL11.GL_TEXTURE_2D, level, GL11.GL_TEXTURE_HEIGHT);
            int size = width * height;

            BufferedImage bufferedimage = new BufferedImage(width, height, 2);
            File output = new File("texture_atlas_dump_" + name + "_mipmap_" + level + ".png");

            IntBuffer buffer = BufferUtils.createIntBuffer(size);
            int[] data = new int[size];

            GL11.glGetTexImage(GL11.GL_TEXTURE_2D, level, GL12.GL_BGRA, GL12.GL_UNSIGNED_INT_8_8_8_8_REV, buffer);
            buffer.get(data);
            bufferedimage.setRGB(0, 0, width, height, data, 0, width);

            try {
                ImageIO.write(bufferedimage, "png", output);
                FMLLog.info("[TextureDump] Exported png to: %s", output.getAbsolutePath());
            } catch (IOException ioexception) {
                FMLLog.info("[TextureDump] Unable to write: ", ioexception);
            }
        }
    }

        /**
         * Method called when the resource manager reloads.
         * Clears all the sprites.
         *
         * @param resourceManager The resource manager that reloaded.
         */
    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {
        baseTextures.clear();
        buildSprites.values().forEach(Map::clear);
        buildSprites.clear();
    }
}
