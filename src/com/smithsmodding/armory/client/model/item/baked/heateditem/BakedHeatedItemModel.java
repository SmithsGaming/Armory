package com.smithsmodding.armory.client.model.item.baked.heateditem;

import com.google.common.collect.*;
import com.smithsmodding.armory.client.model.item.baked.components.*;
import com.smithsmodding.armory.common.factory.*;
import com.smithsmodding.armory.common.item.*;
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

    protected BakedTemperatureBarModel gaugeDisplay;
    protected BakedTemperatureBarModel gaugeDisplayTurned;

    /**
     * The length of brokenParts has to match the length of parts. If a part does not have a broken texture, the entry
     * in the array simply is null.
     */
    public BakedHeatedItemModel (IFlexibleBakedModel parent, BakedTemperatureBarModel gaugeDislay, BakedTemperatureBarModel gaugeDisplayTurned) {
        super((ImmutableList<BakedQuad>) parent.getGeneralQuads(), parent.getParticleTexture(), parent.getFormat());

        this.gaugeDisplayTurned = gaugeDisplayTurned;
        this.gaugeDisplay = gaugeDislay;

    }

    @Override
    public IBakedModel handleItemState (ItemStack stack) {
        // get the texture for each part
        ImmutableList.Builder<BakedQuad> quads = ImmutableList.builder();

        if (!( stack.getItem() instanceof ItemHeatedItem )) {
            return Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getItemModel(stack);
        }

        ItemStack cooledStack = HeatedItemFactory.getInstance().convertToCooledIngot(stack);
        IBakedModel original = Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getItemModel(cooledStack);

        ItemHeatedItem item = (ItemHeatedItem) stack.getItem();
        int barIndex = (int) ( item.getDurabilityForDisplay(stack) * ( gaugeDisplay.getModelCount() - 1 ) );

        if (cooledStack.getItem() instanceof ItemBlock) {
            quads.addAll(new ArrayList<BakedQuad>(gaugeDisplayTurned.getModel(barIndex).getGeneralQuads()));
        } else {
            quads.addAll(new ArrayList<BakedQuad>(original.getGeneralQuads()));
            quads.addAll(new ArrayList<BakedQuad>(gaugeDisplay.getModel(barIndex).getGeneralQuads()));
        }

        PerspectiveUnawareBakedHeatedItemItemModel combinedModel = new PerspectiveUnawareBakedHeatedItemItemModel(quads.build(), original.getParticleTexture(), getFormat(), original);

        PerspectiveDependentBakedHeatedItemItemModel guiModel = new PerspectiveDependentBakedHeatedItemItemModel(quads.build(), original.getParticleTexture(), getFormat(), original);

        combinedModel.registerModel(ItemCameraTransforms.TransformType.GUI, guiModel);

        return combinedModel;
    }
}

