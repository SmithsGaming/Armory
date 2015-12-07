package com.SmithsModding.Armory.Client.Model.Item;

import com.SmithsModding.Armory.API.Armor.MLAAddon;
import com.SmithsModding.Armory.API.Armor.MaterialDependentMLAAddon;
import com.SmithsModding.Armory.Util.Armor.ArmorNBTHelper;
import com.SmithsModding.SmithsCore.Util.Common.NBTHelper;
import com.SmithsModding.SmithsCore.Util.Common.Pair;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.client.model.IFlexibleBakedModel;
import net.minecraftforge.client.model.ISmartItemModel;
import net.minecraftforge.client.model.ItemLayerModel;
import net.minecraftforge.client.model.TRSRTransformation;

import javax.vecmath.Matrix4f;
import java.util.*;

/**
 * Created by Marc on 06.12.2015.
 */
public class BakedMultiLayeredArmorItemModel extends ItemLayerModel.BakedModel implements ISmartItemModel {

    private static final List<List<BakedQuad>> empty_face_quads;
    private static final List<BakedQuad> empty_list;

    static {
        empty_list = Collections.emptyList();
        empty_face_quads = Lists.newArrayList();
        for (int i = 0; i < 6; i++) {
            empty_face_quads.add(empty_list);
        }
    }

    protected final ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms;
    protected Pair<String, BakedComponentModel> baseLayer;
    protected HashMap<String, BakedComponentModel> parts;
    protected HashMap<String, BakedComponentModel> brokenParts;
    protected Map<String, IFlexibleBakedModel> modifierParts;

    /**
     * The length of brokenParts has to match the length of parts. If a part does not have a broken texture, the entry in
     * the array simply is null.
     */
    public BakedMultiLayeredArmorItemModel (IFlexibleBakedModel parent, Pair baseLayer, HashMap parts, HashMap brokenParts, Map modifierParts, ImmutableMap transform) {
        super((ImmutableList<BakedQuad>) parent.getGeneralQuads(), parent.getParticleTexture(), parent.getFormat(), transform);

        this.parts = parts;
        this.baseLayer = baseLayer;
        this.brokenParts = brokenParts;
        this.modifierParts = modifierParts;
        this.transforms = transform;
    }

    @Override
    public IBakedModel handleItemState (ItemStack stack) {
        NBTTagCompound baseTag = NBTHelper.getTagCompound(stack);

        if (baseTag.hasNoTags()) {
            return this;
        }

        ArrayList<MLAAddon> installedAddons = new ArrayList<MLAAddon>();
        installedAddons.addAll(ArmorNBTHelper.getAddonMap(stack).keySet());

        // get the texture for each part
        ImmutableList.Builder<BakedQuad> quads = ImmutableList.builder();

        boolean broken = ArmorNBTHelper.checkIfStackIsBroken(stack);

        for (MLAAddon addon : installedAddons) {
            String addonID = addon.getInternalName();
            String modelID = addonID;
            if (addon.isMaterialDependent()) {
                addonID = ((MaterialDependentMLAAddon) addon).getAddonInternalName();
                modelID = ((MaterialDependentMLAAddon) addon).getMaterialName();
            }

            IBakedModel partModel;
            if (baseLayer.getKey().equals(addonID)) {
                partModel = baseLayer.getValue().getModelByIdentifier(modelID);
            } else if (broken && brokenParts.containsKey(addonID) && brokenParts.get(addonID) != null) {
                partModel = brokenParts.get(addonID).getModelByIdentifier(modelID);
            } else if (parts.containsKey(addonID) && parts.get(addonID) != null) {
                partModel = parts.get(addonID).getModelByIdentifier(modelID);
            } else {
                continue;
            }

            quads.addAll(partModel.getGeneralQuads());
        }

        IFlexibleBakedModel model = new ItemLayerModel.BakedModel(quads.build(), this.getParticleTexture(), this.getFormat(), transforms);

        return model;
    }

    @Override
    public org.apache.commons.lang3.tuple.Pair<IBakedModel, Matrix4f> handlePerspective (ItemCameraTransforms.TransformType cameraTransformType) {
        return null;
    }
}
