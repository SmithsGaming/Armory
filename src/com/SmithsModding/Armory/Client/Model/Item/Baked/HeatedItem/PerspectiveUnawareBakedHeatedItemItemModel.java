package com.smithsmodding.Armory.Client.Model.Item.Baked.HeatedItem;

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
public class PerspectiveUnawareBakedHeatedItemItemModel extends ItemLayerModel.BakedModel implements IPerspectiveAwareModel {

    private boolean inventory = false;
    private IBakedModel standard;

    private HashMap<ItemCameraTransforms.TransformType, IPerspectiveAwareModel> modelHashMap = new HashMap<ItemCameraTransforms.TransformType, IPerspectiveAwareModel>();

    public PerspectiveUnawareBakedHeatedItemItemModel (ImmutableList<BakedQuad> quads, TextureAtlasSprite particle, VertexFormat format, IBakedModel standard) {
        super(quads, particle, format);

        this.standard = standard;
    }

    public void setInventoryMode (boolean mode) {
        inventory = mode;
    }

    public void registerModel (ItemCameraTransforms.TransformType cameraTransforms, IPerspectiveAwareModel model) {
        modelHashMap.put(cameraTransforms, model);
    }

    public IPerspectiveAwareModel getModel (ItemCameraTransforms.TransformType cameraTransforms) {
        return modelHashMap.get(cameraTransforms);
    }

    @Override
    public Pair<? extends IFlexibleBakedModel, Matrix4f> handlePerspective (ItemCameraTransforms.TransformType cameraTransformType) {
        if (cameraTransformType == ItemCameraTransforms.TransformType.GUI && getModel(ItemCameraTransforms.TransformType.GUI) instanceof IPerspectiveAwareModel) {
            return getModel(ItemCameraTransforms.TransformType.GUI).handlePerspective(cameraTransformType);
        }

        if (standard instanceof IPerspectiveAwareModel)
            return ( (IPerspectiveAwareModel) standard ).handlePerspective(cameraTransformType);

        return Pair.of(this, null);
    }

    @Override
    public boolean isAmbientOcclusion () {
        return standard.isAmbientOcclusion();
    }

    @Override
    public boolean isGui3d () {
        return standard.isGui3d();
    }

    @Override
    public boolean isBuiltInRenderer () {
        return standard.isBuiltInRenderer();
    }

    @Override
    public TextureAtlasSprite getParticleTexture () {
        return standard.getParticleTexture();
    }

    @Override
    public ItemCameraTransforms getItemCameraTransforms () {
        return standard.getItemCameraTransforms();
    }

    @Override
    public List<BakedQuad> getFaceQuads (EnumFacing side) {
        return standard.getFaceQuads(side);
    }

    @Override
    public List<BakedQuad> getGeneralQuads () {
        return standard.getGeneralQuads();
    }

    @Override
    public VertexFormat getFormat () {
        return super.getFormat();
    }
}
