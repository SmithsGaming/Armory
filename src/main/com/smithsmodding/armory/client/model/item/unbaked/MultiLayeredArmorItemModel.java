package com.smithsmodding.armory.client.model.item.unbaked;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.smithsmodding.armory.api.armor.IMaterialDependantMultiComponentArmorExtension;
import com.smithsmodding.armory.api.armor.IMultiComponentArmor;
import com.smithsmodding.armory.api.armor.IMultiComponentArmorExtension;
import com.smithsmodding.armory.api.util.references.ModLogger;
import com.smithsmodding.armory.client.model.item.baked.BakedMultiLayeredArmorItemModel;
import com.smithsmodding.armory.client.model.item.baked.components.BakedCoreComponentModel;
import com.smithsmodding.armory.client.model.item.baked.components.BakedSubComponentModel;
import com.smithsmodding.armory.client.model.item.unbaked.components.ArmorCoreComponentModel;
import com.smithsmodding.armory.client.model.item.unbaked.components.ArmorSubComponentModel;
import com.smithsmodding.smithscore.client.model.unbaked.ItemLayerModel;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;

import javax.annotation.Nonnull;
import java.util.HashMap;

/**
 * Created by Marc on 06.12.2015.
 */
public class MultiLayeredArmorItemModel extends ItemLayerModel {

    private final IMultiComponentArmor armor;
    private final ArmorCoreComponentModel baseLayer;
    private final HashMap<IMultiComponentArmorExtension, ArmorSubComponentModel> parts;
    private final HashMap<IMultiComponentArmorExtension, ArmorSubComponentModel> brokenParts;
    private final ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms;

    public MultiLayeredArmorItemModel(IMultiComponentArmor armor, ImmutableList<ResourceLocation> defaultTextures, ArmorCoreComponentModel baseLayer, HashMap<IMultiComponentArmorExtension, ArmorSubComponentModel> parts, HashMap<IMultiComponentArmorExtension, ArmorSubComponentModel> brokenPartBlocks, ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms) {
        super(defaultTextures);
        this.armor = armor;
        this.baseLayer = baseLayer;
        this.parts = parts;
        this.brokenParts = brokenPartBlocks;
        this.transforms = transforms;
    }

    @Nonnull
    @Override
    public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        //Get ourselfs the base model to use.
        IBakedModel base = super.bake(state, format, bakedTextureGetter);

        //Setup the maps that contain the converted baked sub models.
        BakedCoreComponentModel mappedBaseLayer;
        HashMap<IMultiComponentArmorExtension, BakedSubComponentModel> mappedParts = new HashMap<>();
        HashMap<IMultiComponentArmorExtension, BakedSubComponentModel> mappedBrokenParts = new HashMap<>();

        //Map the baselayer:
        mappedBaseLayer = baseLayer.generateBackedComponentModel(state, format, bakedTextureGetter);

        //Check every possible addon for a texture and register it accordingly
        for (IMultiComponentArmorExtension extension : armor.getPossibleExtensions()) {
            if (extension instanceof IMaterialDependantMultiComponentArmorExtension)
                extension = ((IMaterialDependantMultiComponentArmorExtension) extension).getMaterialIndependentExtension();

            if (parts.containsKey(extension)) {
                mappedParts.put(extension, parts.get(extension).generateBackedComponentModel(state, format, bakedTextureGetter));

                //If a part was found, also check for its broken counterpart.
                if (brokenParts.containsKey(extension.getItemTextureBrokenLocation())) {
                    mappedBrokenParts.put(extension, parts.get(extension.getItemTextureBrokenLocation()).generateBackedComponentModel(state, format, bakedTextureGetter));
                }
            } else {
                //For a given MLAAddon on the armor was no texture found.
                ModLogger.getInstance().error("A given armor: " + armor.getRegistryName().toString() + " has a MLAAddon: " + extension.getRegistryName().toString() + " that has no texture registered in the model. It is being skipped.");
            }
        }

        //Bake the model.
        return new BakedMultiLayeredArmorItemModel(base, mappedBaseLayer, mappedParts, mappedBrokenParts, transforms);
    }
}
