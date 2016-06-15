package com.smithsmodding.armory.client.model.item.baked.heateditem;

import com.google.common.collect.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.resources.model.*;
import net.minecraft.util.*;
import net.minecraftforge.client.model.*;
import org.apache.commons.lang3.tuple.*;

import javax.vecmath.Matrix4f;
import java.util.*;

/**
 * Created by Marc on 08.12.2015.
 */
public class PerspectiveDependentBakedHeatedItemItemModel extends ItemLayerModel.BakedModel implements IPerspectiveAwareModel {

    public IBakedModel parent;

    public PerspectiveDependentBakedHeatedItemItemModel (ImmutableList<BakedQuad> quads, TextureAtlasSprite particle, VertexFormat format, IBakedModel parent) {
        super(quads, particle, format);

        this.parent = parent;
    }

    @Override
    public boolean isAmbientOcclusion () {
        return parent.isAmbientOcclusion();
    }

    @Override
    public boolean isGui3d () {
        return parent.isGui3d();
    }

    @Override
    public boolean isBuiltInRenderer () {
        return parent.isBuiltInRenderer();
    }

    @Override
    public TextureAtlasSprite getParticleTexture () {
        return parent.getParticleTexture();
    }

    @Override
    public ItemCameraTransforms getItemCameraTransforms () {
        return parent.getItemCameraTransforms();
    }

    @Override
    public List<BakedQuad> getFaceQuads (EnumFacing side) {
        return parent.getFaceQuads(side);
    }

    @Override
    public Pair<? extends IFlexibleBakedModel, Matrix4f> handlePerspective (ItemCameraTransforms.TransformType cameraTransformType) {
        if (parent instanceof IPerspectiveAwareModel) {
            return Pair.of(this, ( (IPerspectiveAwareModel) parent ).handlePerspective(cameraTransformType).getRight());
        }

        return Pair.of(this, null);
    }
}
