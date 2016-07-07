package com.smithsmodding.armory.client.model.loaders;

import com.google.common.collect.ImmutableMap;
import com.smithsmodding.armory.api.materials.IAnvilMaterial;
import com.smithsmodding.armory.api.events.client.model.block.BlackSmithsAnvilModelTextureLoadEvent;
import com.smithsmodding.armory.api.model.deserializers.definition.AnvilModelDefinition;
import com.smithsmodding.armory.api.references.ModLogger;
import com.smithsmodding.armory.client.model.block.unbaked.BlackSmithsAnvilModel;
import com.smithsmodding.armory.common.registry.AnvilMaterialRegistry;
import com.smithsmodding.smithscore.client.model.unbaked.DummyModel;
import com.smithsmodding.smithscore.util.client.ModelHelper;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.model.obj.OBJModel;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.LoaderState;

import javax.vecmath.Vector4f;
import java.io.IOException;

/**
 * Created by Marc on 22.02.2016.
 */
public class AnvilModelLoader implements ICustomModelLoader {

    public static final String EXTENSION = ".Anvil-armory";

    @Override
    public boolean accepts (ResourceLocation modelLocation) {
        return modelLocation.getResourcePath().endsWith(EXTENSION); // Anvil armory extension. Foo.Anvil-armory.json
    }

    @Override
    public IModel loadModel (ResourceLocation modelLocation) throws IOException {
        if (!Loader.instance().hasReachedState(LoaderState.POSTINITIALIZATION)) {
            return DummyModel.INSTANCE;
        }

        try {
            AnvilModelDefinition modelDefinition = AnvilModelDefinition.loadModel(modelLocation);

            IModel objModel = ModelLoaderRegistry.getModel(new ResourceLocation(modelDefinition.getModelPath()));

            BlackSmithsAnvilModelTextureLoadEvent event = new BlackSmithsAnvilModelTextureLoadEvent();
            event.PostClient();

            modelDefinition.getTextureTopPaths().putAll(event.getAdditionalTopTextureLayers());
            modelDefinition.getTextureBottomPaths().putAll(event.getAdditionalBottomTextureLayers());

            BlackSmithsAnvilModel model = new BlackSmithsAnvilModel(objModel);

            for(IAnvilMaterial material : AnvilMaterialRegistry.getInstance().getAllRegisteredAnvilMaterials().values())
            {
                if (modelDefinition.getTextureTopPaths().containsKey(material.getID()) || modelDefinition.getTextureBottomPaths().containsKey(material.getID()))
                {
                    ImmutableMap.Builder<String, String> builder = new ImmutableMap.Builder<>();

                    if (modelDefinition.getTextureTopPaths().containsKey(material.getID()))
                        builder.put("#Anvil", modelDefinition.getTextureTopPaths().get(material.getID()));

                    if (modelDefinition.getTextureBottomPaths().containsKey(material.getID()))
                        builder.put("#Bottom", modelDefinition.getTextureBottomPaths().get(material.getID()));


                    model.registerNewMaterializedModel(((OBJModel) objModel).retexture(builder.build()), material.getID());
                }
                else
                {
                    OBJModel newModel = (OBJModel) ModelHelper.forceLoadOBJModel(new ResourceLocation(modelDefinition.getModelPath()));

                    OBJModel.Material materialOBJ = newModel.getMatLib().getMaterial("Anvil");

                    Vector4f colorVec = new Vector4f();
                    colorVec.w = 1F;
                    colorVec.x = material.getRenderInfo().getVertexColor().getRedFloat();
                    colorVec.y = material.getRenderInfo().getVertexColor().getGreenFloat();
                    colorVec.z = material.getRenderInfo().getVertexColor().getBlueFloat();

                    materialOBJ.setColor(colorVec);

                    model.registerNewMaterializedModel(newModel, material.getID());
                }
            }

            return model;
        } catch (Exception e) {
            ModLogger.getInstance().error(String.format("Could not load Anvil-Model {}", modelLocation.toString()));
        }

        //If all fails return a Missing model.
        return ModelLoaderRegistry.getMissingModel();

    }

    @Override
    public void onResourceManagerReload (IResourceManager resourceManager) {

    }

}
