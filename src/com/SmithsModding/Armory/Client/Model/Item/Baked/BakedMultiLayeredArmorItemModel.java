package com.smithsmodding.Armory.Client.Model.Item.Baked;

import com.google.common.collect.*;
import com.smithsmodding.Armory.API.Armor.*;
import com.smithsmodding.Armory.Client.Model.Item.Baked.Components.*;
import com.smithsmodding.Armory.Util.Armor.*;
import com.smithsmodding.smithscore.util.common.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.resources.model.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraftforge.client.model.*;

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

    protected Pair<String, BakedComponentModel> baseLayer;
    protected HashMap<String, BakedComponentModel> parts;
    protected HashMap<String, BakedComponentModel> brokenParts;

    /**
     * The length of brokenParts has to match the length of parts. If a part does not have a broken texture, the entry in
     * the array simply is null.
     */
    public BakedMultiLayeredArmorItemModel (IFlexibleBakedModel parent, Pair baseLayer, HashMap parts, HashMap brokenParts) {
        super((ImmutableList<BakedQuad>) parent.getGeneralQuads(), parent.getParticleTexture(), parent.getFormat());

        this.parts = parts;
        this.baseLayer = baseLayer;
        this.brokenParts = brokenParts;
    }

    @Override
    public IBakedModel handleItemState (ItemStack stack) {
        NBTTagCompound baseTag = NBTHelper.getTagCompound(stack);

        if (baseTag.hasNoTags()) {
            return this;
        }

        ArrayList<MLAAddon> installedAddons = new ArrayList<MLAAddon>();
        installedAddons.addAll(ArmorNBTHelper.getAddonMap(stack).keySet());

        //Sort the list based on priority.
        Collections.sort(installedAddons, new Comparator<MLAAddon>() {
            @Override
            public int compare (MLAAddon o1, MLAAddon o2) {
                return Integer.compare(o1.getLayerPriority(), o2.getLayerPriority());
            }
        });

        // get the texture for each part
        ImmutableList.Builder<BakedQuad> quads = ImmutableList.builder();

        boolean broken = ArmorNBTHelper.checkIfStackIsBroken(stack);

        for (MLAAddon addon : installedAddons) {
            String addonID = addon.getUniqueID();
            String modelID = addonID;
            if (addon.isMaterialDependent()) {
                addonID = ((MaterialDependentMLAAddon) addon).getMaterialIndependentID();
                modelID = ((MaterialDependentMLAAddon) addon).getUniqueMaterialID();
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

        IFlexibleBakedModel model = new ItemLayerModel.BakedModel(quads.build(), this.getParticleTexture(), this.getFormat());

        return model;
    }
}
