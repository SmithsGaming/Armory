package com.SmithsModding.Armory.Client.Model.Loaders;


import com.SmithsModding.Armory.Armory;
import com.SmithsModding.Armory.Client.Model.Item.ArmorComponentModel;
import com.SmithsModding.Armory.Client.Model.Item.ComponentModel;
import com.SmithsModding.Armory.Client.Model.Item.DummyModel;
import com.SmithsModding.Armory.Client.Model.Item.MultiLayeredArmorItemModel;
import com.SmithsModding.Armory.Client.Textures.MaterializedTextureCreator;
import com.SmithsModding.Armory.Common.Material.MaterialRegistry;
import com.SmithsModding.SmithsCore.Util.Client.ModelHelper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.LoaderState;
import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Marc on 06.12.2015.
 */
public class MultiLayeredArmorModelLoader implements ICustomModelLoader {
    public static final String EXTENSION = ".MLA-Armory";

    @Override
    public boolean accepts (ResourceLocation modelLocation) {
        return modelLocation.getResourcePath().endsWith(EXTENSION); // MLA Armory extension. Foo.MLA.Armory.json
    }

    @Override
    public IModel loadModel (ResourceLocation modelLocation) throws IOException {
        if (!Loader.instance().hasReachedState(LoaderState.POSTINITIALIZATION)) {
            return DummyModel.INSTANCE;
        }

        try {
            Map<String, String> textures = ModelHelper.loadTexturesFromJson(modelLocation);
            ImmutableList.Builder<ResourceLocation> builder = ImmutableList.builder();

            ArmorComponentModel base = null;
            List<ArmorComponentModel> parts = Lists.newArrayList();
            List<ArmorComponentModel> brokenParts = Lists.newArrayList();

            for (Map.Entry<String, String> entry : textures.entrySet()) {
                String name = entry.getKey();
                try {
                    int i = 0;
                    List<ArmorComponentModel> listToAdd = new ArrayList<ArmorComponentModel>();

                    if (name.startsWith("layer")) {
                        i = Integer.valueOf(name.substring(5));
                        listToAdd = parts;
                    } else if (name.startsWith("broken")) {
                        i = Integer.valueOf(name.substring(6));
                        listToAdd = brokenParts;
                    } else if (name.startsWith("base")) {
                        if (base != null) {
                            Armory.getLogger().error("MLAModel {} has more then one base layer. Overwritting!", modelLocation);
                        }
                    }
                    // invalid entry, ignore
                    else {
                        Armory.getLogger().warn("MLAModel {} has invalid texture entry {}; Skipping layer.", modelLocation, name);
                        continue;
                    }

                    ResourceLocation location = new ResourceLocation(entry.getValue());
                    ArmorComponentModel partModel = new ArmorComponentModel(ImmutableList.of(location));

                    if (!name.startsWith("base")) {
                        while (listToAdd.size() <= i) {
                            listToAdd.add(null);
                        }
                        listToAdd.set(i, partModel);
                    } else {
                        base = partModel;
                    }


                    builder.add(location);
                } catch (NumberFormatException e) {
                    Armory.getLogger().error("MLAModel {} has invalid texture entry {}; Skipping layer.", modelLocation, name);
                }
            }

            String armorInternalName = FilenameUtils.getBaseName(modelLocation.getResourcePath());
            IModel mods = ModelLoaderRegistry.getModel(MedievalComponentModelLoader.getLocationForToolModifiers(armorInternalName));
            ComponentModel modifiers = null;

            if (mods == null || !(mods instanceof ComponentModel)) {
                Armory.getLogger().trace(
                        "MLAModel {} does not have any addons associated with it. Be sure that the Armors internal name, the Armormodel filename and the name used inside the Addon Model Definition match!",
                        modelLocation);
            } else {
                modifiers = (ComponentModel) mods;
            }

            IModel output = new MultiLayeredArmorItemModel(MaterialRegistry.getInstance().getArmor(armorInternalName), builder.build(), base, parts, brokenParts, modifiers);

            // inform the texture manager about the textures it has to process
            MaterializedTextureCreator.registerBaseTexture(builder.build());

            return output;
        } catch (IOException e) {
            Armory.getLogger().error("Could not load multimodel {}", modelLocation.toString());
        }
        return ModelLoaderRegistry.getMissingModel();
    }

    @Override
    public void onResourceManagerReload (IResourceManager resourceManager) {

    }
}
