package com.smithsmodding.armory.client.model.item.unbaked;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.smithsmodding.armory.Armory;
import com.smithsmodding.armory.api.armor.MLAAddon;
import com.smithsmodding.armory.api.armor.MaterialDependentMLAAddon;
import com.smithsmodding.armory.api.armor.MultiLayeredArmor;
import com.smithsmodding.armory.api.references.ModLogger;
import com.smithsmodding.armory.client.model.item.baked.BakedMultiLayeredArmorItemModel;
import com.smithsmodding.armory.client.model.item.baked.components.BakedSubComponentModel;
import com.smithsmodding.armory.client.model.item.unbaked.components.ArmorSubComponentModel;
import com.smithsmodding.smithscore.client.model.unbaked.ItemLayerModel;
import com.smithsmodding.smithscore.util.common.Pair;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;

import java.util.HashMap;

/**
 * Created by Marc on 06.12.2015.
 */
public class MultiLayeredArmorItemModel extends ItemLayerModel {

    private final MultiLayeredArmor armor;
    private final ArmorSubComponentModel baseLayer;
    private final HashMap<String, ArmorSubComponentModel> parts;
    private final HashMap<String, ArmorSubComponentModel> brokenParts;
    private final ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms;

    public MultiLayeredArmorItemModel(MultiLayeredArmor armor, ImmutableList<ResourceLocation> defaultTextures, ArmorSubComponentModel baseLayer, HashMap<String, ArmorSubComponentModel> parts, HashMap<String, ArmorSubComponentModel> brokenPartBlocks, ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms) {
        super(defaultTextures);
        this.armor = armor;
        this.baseLayer = baseLayer;
        this.parts = parts;
        this.brokenParts = brokenPartBlocks;
        this.transforms = transforms;
    }

    @Override
    public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        //Get ourselfs the base model to use.
        IBakedModel base = super.bake(state, format, bakedTextureGetter);

        //Setup the maps that contain the converted baked sub models.
        Pair<String, BakedSubComponentModel> mappedBaseLayer = null;
        HashMap<String, BakedSubComponentModel> mappedParts = new HashMap<String, BakedSubComponentModel>();
        HashMap<String, BakedSubComponentModel> mappedBrokenParts = new HashMap<String, BakedSubComponentModel>();

        //Check every possible addon for a texture and register it accordingly
        for (final MLAAddon addon : armor.getAllowedAddons()) {
            String addonID = addon.getUniqueID();
            if (addon.isMaterialDependent()) {
                addonID = ((MaterialDependentMLAAddon) addon).getMaterialIndependentID();
            }

            if (addon.getItemWholeTextureLocation().equals(baseLayer.getTexture()) && mappedBaseLayer == null) {
                mappedBaseLayer = new Pair<String, BakedSubComponentModel>(addonID, baseLayer.generateBackedComponentModel(state, format, bakedTextureGetter));
            } else if (parts.containsKey(addon.getItemWholeTextureLocation().toString())) {
                mappedParts.put(addonID, parts.get(addon.getItemWholeTextureLocation().toString()).generateBackedComponentModel(state, format, bakedTextureGetter));

                //If a part was found, also check for its broken counterpart.
                if (brokenParts.containsKey(addon.getItemBrokenTextureLocation().toString())) {
                    mappedBrokenParts.put(addonID, parts.get(addon.getItemBrokenTextureLocation().toString()).generateBackedComponentModel(state, format, bakedTextureGetter));
                }
            } else if (!addon.getItemWholeTextureLocation().equals(baseLayer.getTexture())) {
                //For a given MLAAddon on the armor was no texture found.
                ModLogger.getInstance().error("A given armor: " + armor.getUniqueID() + " has a MLAAddon: " + addon.getUniqueID() + " that has no texture registered in the model. It is being skipped.");
            }
        }

        //Bake the model.
        return new BakedMultiLayeredArmorItemModel(base, mappedBaseLayer, mappedParts, mappedBrokenParts, transforms);
    }
}
