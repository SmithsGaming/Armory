package com.SmithsModding.Armory.Client.Model.Item;

import com.SmithsModding.Armory.API.Armor.MLAAddon;
import com.SmithsModding.Armory.API.Armor.MaterialDependentMLAAddon;
import com.SmithsModding.Armory.API.Armor.MultiLayeredArmor;
import com.SmithsModding.Armory.Armory;
import com.SmithsModding.SmithsCore.Util.Client.ModelHelper;
import com.SmithsModding.SmithsCore.Util.Common.Pair;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import gnu.trove.map.hash.THashMap;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IFlexibleBakedModel;
import net.minecraftforge.client.model.IModelState;
import net.minecraftforge.client.model.ItemLayerModel;
import net.minecraftforge.client.model.TRSRTransformation;

import java.util.*;

/**
 * Created by Marc on 06.12.2015.
 */
public class MultiLayeredArmorItemModel extends ItemLayerModel {

    private final MultiLayeredArmor armor;
    private final ArmorComponentModel baseLayer;
    private final List<ArmorComponentModel> partBlocks;
    private final List<ArmorComponentModel> brokenPartBlocks;
    private final ComponentModel modifiers;

    public MultiLayeredArmorItemModel (MultiLayeredArmor armor, ImmutableList<ResourceLocation> defaultTextures, ArmorComponentModel baseLayer, List<ArmorComponentModel> parts, List<ArmorComponentModel> brokenPartBlocks,
                                       ComponentModel modifiers) {
        super(defaultTextures);
        this.armor = armor;
        this.baseLayer = baseLayer;
        this.partBlocks = parts;
        this.brokenPartBlocks = brokenPartBlocks;
        this.modifiers = modifiers;
    }

    @Override
    public Collection<ResourceLocation> getDependencies () {
        return ImmutableList.of();
    }

    @Override
    public Collection<ResourceLocation> getTextures () {
        ImmutableSet.Builder<ResourceLocation> builder = ImmutableSet.builder();

        builder.addAll(super.getTextures());

        // modifier textures
        if (modifiers != null) {
            builder.addAll(modifiers.getTextures());
        }

        return builder.build();
    }

    @Override
    public IFlexibleBakedModel bake (IModelState state, VertexFormat format,
                                     Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        IFlexibleBakedModel base = super.bake(state, format, bakedTextureGetter);

        BakedComponentModel[] partModels = new BakedComponentModel[partBlocks.size()];
        BakedComponentModel[] brokenPartModels = new BakedComponentModel[partBlocks.size()]; // has to be same size

        Pair<String, BakedComponentModel> mappedBaseLayer = null;
        HashMap<String, BakedComponentModel> mappedParts = new HashMap<String, BakedComponentModel>();
        HashMap<String, BakedComponentModel> mappedBrokenParts = new HashMap<String, BakedComponentModel>();

        // we build simple models for the parts, so we can extract the UV information AND have depth
        for (int i = 0; i < partBlocks.size(); i++) {
            MLAAddon hostAddon = null;
            ArmorComponentModel m = partBlocks.get(i);

            for (final MLAAddon addon : armor.getAllowedAddons()) {
                if (addon.getModelTextureLocation().equals(m.getTexture())) {
                    hostAddon = addon;
                    break;
                } else if (addon.getModelTextureLocation().equals(baseLayer.getTexture()) && mappedBaseLayer == null) {
                    final BakedComponentModel bakedBaseLayer = baseLayer.generateBackedComponentModel(state, format, bakedTextureGetter);

                    String addonID = addon.getInternalName();
                    if (addon.isMaterialDependent()) {
                        addonID = ((MaterialDependentMLAAddon) addon).getAddonInternalName();
                    }

                    mappedBaseLayer = new Pair<String, BakedComponentModel>(addonID, baseLayer.generateBackedComponentModel(state, format, bakedTextureGetter));
                }
            }

            if (hostAddon == null) {
                Armory.getLogger().error("The given partsBlock in the JSON Model Definition is not matchable to a MLAAddon registered to the MLA");
                continue;
            }

            String addonID = hostAddon.getInternalName();
            if (hostAddon.isMaterialDependent()) {
                addonID = ((MaterialDependentMLAAddon) hostAddon).getAddonInternalName();
            }

            mappedParts.put(addonID, m.generateBackedComponentModel(state, format, bakedTextureGetter));

        }

        for (int i = 0; i < brokenPartBlocks.size(); i++) {
            if (brokenPartBlocks.get(i) != null) {
                MLAAddon hostAddon = null;

                ArrayList<ResourceLocation> resources = new ArrayList<ResourceLocation>();
                resources.addAll(brokenPartBlocks.get(i).getTextures());

                for (MLAAddon addon : armor.getAllowedAddons()) {
                    if (addon.getModelBrokenTextureLocation().equals(resources.get(0))) {
                        hostAddon = addon;
                        break;
                    }
                }

                if (hostAddon == null) {
                    Armory.getLogger().error("The given brokenPartsBlock in the JSON Model Definition is not matchable to a MLAAddon registered to the MLA");
                    continue;
                }

                String addonID = hostAddon.getInternalName();
                if (hostAddon.isMaterialDependent()) {
                    addonID = ((MaterialDependentMLAAddon) hostAddon).getAddonInternalName();
                }
                mappedBrokenParts.put(addonID, brokenPartBlocks.get(i).generateBackedComponentModel(state, format, bakedTextureGetter));
            }
        }

        Map<String, IFlexibleBakedModel> modifierModels;
        if (modifiers != null) {
            modifierModels = modifiers.bakeModels(state, format, bakedTextureGetter);
        } else {
            modifierModels = new THashMap<String, IFlexibleBakedModel>();
        }

        ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms = ModelHelper.getTransformsFromState(state, this);

        return new BakedMultiLayeredArmorItemModel(base, mappedBaseLayer, mappedParts, mappedBrokenParts, modifierModels, transforms);
    }

    @Override
    public IModelState getDefaultState () {
        return ModelHelper.DEFAULT_TOOL_STATE;
    }
}
