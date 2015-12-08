package com.SmithsModding.Armory.Client.Model.Item.Baked.HeatedItem;

import com.SmithsModding.Armory.Client.Model.Item.Baked.Components.*;
import com.SmithsModding.Armory.Common.Factory.*;
import com.SmithsModding.Armory.Common.Item.*;
import com.google.common.collect.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.resources.model.*;
import net.minecraft.item.*;
import net.minecraftforge.client.model.*;

import java.util.*;

/**
 * Created by Marc on 08.12.2015.
 */
public class BakedHeatedItemModel extends ItemLayerModel.BakedModel implements ISmartItemModel {
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
    protected BakedTemperatureBarModel gaugeDisplay;

    /**
     * The length of brokenParts has to match the length of parts. If a part does not have a broken texture, the entry
     * in the array simply is null.
     */
    public BakedHeatedItemModel (IFlexibleBakedModel parent, BakedTemperatureBarModel gaugeDislay, ImmutableMap transform) {
        super((ImmutableList<BakedQuad>) parent.getGeneralQuads(), parent.getParticleTexture(), parent.getFormat(), transform);

        this.gaugeDisplay = gaugeDislay;
        this.transforms = transform;
    }

    @Override
    public IBakedModel handleItemState (ItemStack stack) {
        // get the texture for each part
        ImmutableList.Builder<BakedQuad> quads = ImmutableList.builder();

        if (!( stack.getItem() instanceof ItemHeatedItem )) {
            return Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getItemModel(stack);
        }

        ItemStack cooledStack = HeatedItemFactory.getInstance().convertToCooledIngot(stack);
        IBakedModel original = Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getItemModel(stack);
        quads.addAll(original.getGeneralQuads());

        PerspectiveUnawareBakedHeatedItemItemModel combinedModel = new PerspectiveUnawareBakedHeatedItemItemModel(quads.build(), original.getParticleTexture(), getFormat(), new PerspectiveDependentBakedHeatedItemItemModel(quads.build(), original.getParticleTexture(), getFormat()));

        ItemHeatedItem item = (ItemHeatedItem) stack.getItem();
        int barIndex = (int) ( item.getDurabilityForDisplay(stack) * gaugeDisplay.getModelCount() );

        quads.addAll(gaugeDisplay.getModel(barIndex).getGeneralQuads());

        PerspectiveDependentBakedHeatedItemItemModel guiModel = new PerspectiveDependentBakedHeatedItemItemModel(quads.build(), original.getParticleTexture(), getFormat());

        combinedModel.registerModel(ItemCameraTransforms.TransformType.GUI, guiModel);

        return combinedModel;
    }
}

