package com.smithsmodding.armory.client.model.item.baked.heateditem;

import com.google.common.collect.ImmutableList;
import com.smithsmodding.smithscore.client.model.baked.BakedWrappedModel;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.model.IPerspectiveAwareModel;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.vecmath.Matrix4f;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marc on 08.12.2015.
 */
public class PerspectiveDependentBakedHeatedItemItemModel extends BakedWrappedModel implements IPerspectiveAwareModel {

    private final ImmutableList<BakedQuad> gaugeQuads;

    public PerspectiveDependentBakedHeatedItemItemModel(IBakedModel parent, ImmutableList<BakedQuad> gaugeQuads) {
        super(parent);
        this.gaugeQuads = gaugeQuads;
    }

    @Nonnull
    @Override
    public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {
        List<BakedQuad> result = new ArrayList<>(getParentModel().getQuads(state, side, rand));
        result.addAll(gaugeQuads);

        return result;
    }

    @Nonnull
    @Override
    public Pair<? extends IBakedModel, Matrix4f> handlePerspective(ItemCameraTransforms.TransformType cameraTransformType) {
        if (getParentModel() instanceof IPerspectiveAwareModel) {
            return Pair.of(this, ((IPerspectiveAwareModel) getParentModel()).handlePerspective(cameraTransformType).getRight());
        }

        return Pair.of(this, null);
    }
}
