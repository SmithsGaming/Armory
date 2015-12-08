package com.SmithsModding.Armory.Client.Model.Item.Unbaked;

import com.SmithsModding.Armory.API.Armor.MLAAddon;
import com.SmithsModding.Armory.API.Armor.MaterialDependentMLAAddon;
import com.SmithsModding.Armory.API.Armor.MultiLayeredArmor;
import com.SmithsModding.Armory.Armory;
import com.SmithsModding.Armory.Client.Model.Item.Baked.BakedComponentModel;
import com.SmithsModding.Armory.Client.Model.Item.Baked.BakedMultiLayeredArmorItemModel;
import com.SmithsModding.Armory.Client.Model.Item.Unbaked.Components.ArmorComponentModel;
import com.SmithsModding.SmithsCore.Util.Client.ModelHelper;
import com.SmithsModding.SmithsCore.Util.Common.Pair;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IFlexibleBakedModel;
import net.minecraftforge.client.model.IModelState;
import net.minecraftforge.client.model.ItemLayerModel;
import net.minecraftforge.client.model.TRSRTransformation;

import java.util.Collection;
import java.util.HashMap;

/**
 * Created by Marc on 06.12.2015.
 */
public class MultiLayeredArmorItemModel extends ItemLayerModel {

    private final MultiLayeredArmor armor;
    private final ArmorComponentModel baseLayer;
    private final HashMap<String, ArmorComponentModel> parts;
    private final HashMap<String, ArmorComponentModel> brokenParts;

    public MultiLayeredArmorItemModel (MultiLayeredArmor armor, ImmutableList<ResourceLocation> defaultTextures, ArmorComponentModel baseLayer, HashMap<String, ArmorComponentModel> parts, HashMap<String, ArmorComponentModel> brokenPartBlocks) {
        super(defaultTextures);
        this.armor = armor;
        this.baseLayer = baseLayer;
        this.parts = parts;
        this.brokenParts = brokenPartBlocks;
    }

    @Override
    public Collection<ResourceLocation> getDependencies () {
        return ImmutableList.of();
    }

    @Override
    public IFlexibleBakedModel bake (IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        //Get ourselfs the base model to use.
        IFlexibleBakedModel base = super.bake(state, format, bakedTextureGetter);

        //Setup the maps that contain the converted baked sub models.
        Pair<String, BakedComponentModel> mappedBaseLayer = null;
        HashMap<String, BakedComponentModel> mappedParts = new HashMap<String, BakedComponentModel>();
        HashMap<String, BakedComponentModel> mappedBrokenParts = new HashMap<String, BakedComponentModel>();

        //Check every possible addon for a texture and register it accordingly
        for (final MLAAddon addon : armor.getAllowedAddons()) {
            String addonID = addon.getUniqueID();
            if (addon.isMaterialDependent()) {
                addonID = ((MaterialDependentMLAAddon) addon).getMaterialIndependentID();
            }

            if (addon.getItemWholeTextureLocation().equals(baseLayer.getTexture()) && mappedBaseLayer == null) {
                mappedBaseLayer = new Pair<String, BakedComponentModel>(addonID, baseLayer.generateBackedComponentModel(state, format, bakedTextureGetter));
            } else if (parts.containsKey(addon.getItemWholeTextureLocation().toString())) {
                mappedParts.put(addonID, parts.get(addon.getItemWholeTextureLocation().toString()).generateBackedComponentModel(state, format, bakedTextureGetter));

                //If a part was found, also check for its broken counterpart.
                if (brokenParts.containsKey(addon.getItemBrokenTextureLocation().toString())) {
                    mappedBrokenParts.put(addonID, parts.get(addon.getItemBrokenTextureLocation().toString()).generateBackedComponentModel(state, format, bakedTextureGetter));
                }
            } else if (!addon.getItemWholeTextureLocation().equals(baseLayer.getTexture())) {
                //For a given MLAAddon on the armor was no texture found.
                Armory.getLogger().error("A given Armor: " + armor.getUniqueID() + " has a MLAAddon: " + addon.getUniqueID() + " that has no texture registered in the model. It is being skipped.");
            }
        }

        ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms = ModelHelper.getTransformsFromState(state, this);

        //Bake the model.
        return new BakedMultiLayeredArmorItemModel(base, mappedBaseLayer, mappedParts, mappedBrokenParts, transforms);
    }

    @Override
    public IModelState getDefaultState () {
        return ModelHelper.DEFAULT_TOOL_STATE;
    }
}
