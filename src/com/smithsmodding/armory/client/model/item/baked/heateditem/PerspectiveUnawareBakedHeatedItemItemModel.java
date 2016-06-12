package com.smithsmodding.armory.client.model.item.baked.heateditem;

import com.smithsmodding.smithscore.client.model.baked.BakedWrappedModel;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraftforge.client.model.IPerspectiveAwareModel;
import org.apache.commons.lang3.tuple.Pair;

import javax.vecmath.Matrix4f;
import java.util.HashMap;

/**
 * Created by Marc on 08.12.2015.
 */
public class PerspectiveUnawareBakedHeatedItemItemModel extends BakedWrappedModel implements IPerspectiveAwareModel {

    private boolean inventory = false;

    private HashMap<ItemCameraTransforms.TransformType, IPerspectiveAwareModel> modelHashMap = new HashMap<ItemCameraTransforms.TransformType, IPerspectiveAwareModel>();

    public PerspectiveUnawareBakedHeatedItemItemModel(IBakedModel standard) {
        super(standard);
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
    public Pair<? extends IBakedModel, Matrix4f> handlePerspective(ItemCameraTransforms.TransformType cameraTransformType) {
        if (cameraTransformType == ItemCameraTransforms.TransformType.GUI && getModel(ItemCameraTransforms.TransformType.GUI) instanceof IPerspectiveAwareModel) {
            return getModel(ItemCameraTransforms.TransformType.GUI).handlePerspective(cameraTransformType);
        }

        if (getParentModel() instanceof IPerspectiveAwareModel)
            return ((IPerspectiveAwareModel) getParentModel()).handlePerspective(cameraTransformType);

        return Pair.of(this, null);
    }

}
