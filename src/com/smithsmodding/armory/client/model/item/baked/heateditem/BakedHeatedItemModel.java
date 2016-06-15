package com.smithsmodding.armory.client.model.item.baked.heateditem;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.smithsmodding.armory.client.model.item.baked.components.BakedTemperatureBarModel;
import com.smithsmodding.armory.common.factory.HeatedItemFactory;
import com.smithsmodding.armory.common.item.ItemHeatedItem;
import com.smithsmodding.smithscore.client.model.baked.BakedWrappedModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Marc on 08.12.2015.
 */
public class BakedHeatedItemModel extends BakedWrappedModel {
    private static final List<List<BakedQuad>> empty_face_quads;
    private static final List<BakedQuad> empty_list;

    static {
        empty_list = Collections.emptyList();
        empty_face_quads = Lists.newArrayList();
        for (int i = 0; i < 6; i++) {
            empty_face_quads.add(empty_list);
        }
    }

    private final Overrides overrides;
    protected BakedTemperatureBarModel gaugeDisplay;
    protected BakedTemperatureBarModel gaugeDisplayTurned;

    /**
     * The length of brokenParts has to match the length of parts. If a part does not have a broken texture, the entry
     * in the array simply is null.
     */
    public BakedHeatedItemModel(IBakedModel parent, BakedTemperatureBarModel gaugeDislay, BakedTemperatureBarModel gaugeDisplayTurned) {
        super(parent);

        this.gaugeDisplayTurned = gaugeDisplayTurned;
        this.gaugeDisplay = gaugeDislay;

        overrides = new Overrides(this);
    }

    @Override
    public ItemOverrideList getOverrides() {
        return overrides;
    }

    private static class Overrides extends ItemOverrideList {

        private final BakedHeatedItemModel parent;

        public Overrides(BakedHeatedItemModel parent) {
            super(new ArrayList<>());
            this.parent = parent;
        }

        @Override
        public IBakedModel handleItemState(IBakedModel originalModel, ItemStack stack, World world, EntityLivingBase entity) {
            // get the texture for each part
            ImmutableList.Builder<BakedQuad> quads = ImmutableList.builder();

            if (!(stack.getItem() instanceof ItemHeatedItem)) {
                return Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getItemModel(stack);
            }

            ItemStack cooledStack = HeatedItemFactory.getInstance().convertToCooledIngot(stack);
            IBakedModel original = Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getItemModel(cooledStack);

            ItemHeatedItem item = (ItemHeatedItem) stack.getItem();
            int barIndex = (int) (item.getDurabilityForDisplay(stack) * (parent.gaugeDisplay.getModelCount() - 1));

            if (cooledStack.getItem() instanceof ItemBlock) {
                quads.addAll(new ArrayList<>(parent.gaugeDisplayTurned.getModel(barIndex).getQuads(null, null, 0)));
            } else {
                quads.addAll(new ArrayList<>(parent.gaugeDisplay.getModel(barIndex).getQuads(null, null, 0)));
            }

            //TODO: Add the new QUADS Again!.

            PerspectiveUnawareBakedHeatedItemItemModel combinedModel = new PerspectiveUnawareBakedHeatedItemItemModel(original);

            PerspectiveDependentBakedHeatedItemItemModel guiModel = new PerspectiveDependentBakedHeatedItemItemModel(original, quads.build());

            combinedModel.registerModel(ItemCameraTransforms.TransformType.GUI, guiModel);

            return combinedModel;
        }
    }
}

