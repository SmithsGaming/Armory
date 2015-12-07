package com.SmithsModding.Armory.Client.Model.Loaders;

import com.SmithsModding.Armory.API.Armor.MLAAddon;
import com.SmithsModding.Armory.Armory;
import com.SmithsModding.Armory.Client.Model.Item.ComponentModel;
import com.SmithsModding.Armory.Client.Textures.MaterializedTextureCreator;
import com.SmithsModding.Armory.Common.Addons.MedievalAddonRegistry;
import com.SmithsModding.Armory.Util.References;
import com.SmithsModding.SmithsCore.Util.Client.ModelHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonParseException;
import gnu.trove.map.hash.THashMap;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Marc on 06.12.2015.
 * <p/>
 * Modification of the ModifierModelLoader of TinkersConstruct
 */
public class MedievalComponentModelLoader implements ICustomModelLoader {

    private static final String defaultName = "default";
    public static String EXTENSION = ".MedComp-Armory";
    // holds additional json files that shall be loaded for a specific modifier
    protected Map<String, List<ResourceLocation>> locations = Maps.newHashMap();
    protected Map<String, Map<String, String>> cache;

    public static ResourceLocation getLocationForToolModifiers (String toolName) {
        return new ResourceLocation(References.General.MOD_ID.toLowerCase(Locale.US), "modifiers/" + toolName + MedievalComponentModelLoader.EXTENSION);
    }

    public void registerModifierFile (String modifier, ResourceLocation location) {
        List<ResourceLocation> files = locations.get(modifier);
        if (files == null) {
            files = Lists.newLinkedList();
            locations.put(modifier, files);
        }

        files.add(location);
    }

    @Override
    public boolean accepts (ResourceLocation modelLocation) {
        return modelLocation.getResourcePath().endsWith(EXTENSION); // Medieval Component extension. Foo.mod.json
    }

    @Override
    public IModel loadModel (ResourceLocation modelLocation) {
        // this function is actually getting called on a PER TOOL basis, not per modifier
        // we therefore need to look through all modifiers to construct a model containing all modifiers for that tool

        int start = modelLocation.getResourcePath().lastIndexOf('/');
        String toolname = modelLocation.getResourcePath().substring(start < 0 ? 0 : start + 1,
                modelLocation.getResourcePath().length() - EXTENSION
                        .length());
        toolname = toolname.toLowerCase(Locale.US);

        // we only load once. Without cache we'd have to load ALL modifier files again for each tool!
        if (cache == null) {
            cache = new THashMap<String, Map<String, String>>();
            loadFilesIntoCache();
        }

        if (!cache.containsKey(toolname)) {
            return ModelLoaderRegistry.getMissingModel();
        }

        ComponentModel model = new ComponentModel();

        // generate the modelblocks for each entry
        for (Map.Entry<String, String> entry : cache.get(toolname).entrySet()) {
            // check if the modifier actually exists in the game so we don't load unnecessary textures
            MLAAddon addon = MedievalAddonRegistry.getInstance().getUpgrade(entry.getKey());

            if (addon == null) {
                Armory.getLogger().debug("Removing texture {} for modifier {}: No modifier present for texture",
                        entry.getValue(), entry.getKey());
                continue;
            }

            // using the String from the modifier means an == check succeeds and fixes lowercasing from the loading from files
            model.addModelForModifier(addon.getInternalName(), entry.getValue());

            // register per-material modifiers for texture creation

            MaterializedTextureCreator.registerBaseTexture(new ResourceLocation(entry.getValue()));
        }

        return model;
    }

    @Override
    public void onResourceManagerReload (IResourceManager resourceManager) {
        // goodbye, my dear data. You'll be...loaded again
        cache = null;
    }

    private void loadFilesIntoCache () {
        cache.put(defaultName, new THashMap<String, String>());

        // loop through all knows modifier-model-files
        for (Map.Entry<String, List<ResourceLocation>> entry : locations.entrySet()) {
            String modifier = entry.getKey();
            List<ResourceLocation> modLocations = entry.getValue();
            for (ResourceLocation location : modLocations) {
                try {
                    // load the entries in the json file
                    Map<String, String> textureEntries = ModelHelper.loadTexturesFromJson(location);

                    // save them in the cache
                    for (Map.Entry<String, String> textureEntry : textureEntries.entrySet()) {
                        String tool = textureEntry.getKey().toLowerCase(Locale.US);
                        String texture = textureEntry.getValue();

                        if (!cache.containsKey(tool)) {
                            cache.put(tool, new THashMap<String, String>());
                        }
                        // we don't allow overriding
                        if (!cache.get(tool).containsKey(modifier)) {
                            cache.get(tool).put(modifier, texture);
                        }
                    }
                } catch (IOException e) {
                    Armory.getLogger().error("Cannot load modifier-model {}", entry.getValue());
                } catch (JsonParseException e) {
                    Armory.getLogger().error("Cannot load modifier-model {}", entry.getValue());
                    throw e;
                }
            }

            if (!cache.get(defaultName).containsKey(modifier)) {
                throw new IllegalArgumentException(String.format("%s Modifiers model does not contain a default-entry!", modifier));
            }
        }

        Map<String, String> defaults = cache.get(defaultName);

        // fill in defaults where models are missing
        Iterator<Map.Entry<String, Map<String, String>>> toolEntryIter = cache.entrySet().iterator();
// todo: change this to iterate over all registered tools instead?
        while (toolEntryIter.hasNext()) {
            Map.Entry<String, Map<String, String>> toolEntry = toolEntryIter.next();
            //String tool = toolEntry.getKey();
            Map<String, String> textures = toolEntry.getValue();

            for (Map.Entry<String, String> defaultEntry : defaults.entrySet()) {
                // check if the tool has an entry for this modifier, otherwise fill in default
                if (!textures.containsKey(defaultEntry.getKey())) {
                    Armory.getLogger().debug("Filling in default for modifier {} on tool {}", defaultEntry.getKey(), toolEntry.getKey());
                    textures.put(defaultEntry.getKey(), defaultEntry.getValue());
                }
            }
        }
    }
}
