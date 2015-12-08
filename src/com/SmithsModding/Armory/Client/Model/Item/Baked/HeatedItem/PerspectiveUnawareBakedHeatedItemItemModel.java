package com.SmithsModding.Armory.Client.Model.Item.Baked.HeatedItem;

import com.google.common.collect.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.resources.model.*;
import net.minecraftforge.client.model.*;
import org.apache.commons.lang3.tuple.*;

import javax.vecmath.*;
import java.util.*;

/**
 * Created by Marc on 08.12.2015.
 */
public class PerspectiveUnawareBakedHeatedItemItemModel extends ItemLayerModel.BakedModel {

    private boolean inventory = false;
    private PerspectiveDependentBakedHeatedItemItemModel standard;
    private HashMap<ItemCameraTransforms.TransformType, PerspectiveDependentBakedHeatedItemItemModel> modelHashMap = new HashMap<ItemCameraTransforms.TransformType, PerspectiveDependentBakedHeatedItemItemModel>();

    public PerspectiveUnawareBakedHeatedItemItemModel (ImmutableList<BakedQuad> quads, TextureAtlasSprite particle, VertexFormat format, PerspectiveDependentBakedHeatedItemItemModel standard) {
        super(quads, particle, format);

        this.standard = standard;
    }

    public void setInventoryMode (boolean mode) {
        inventory = mode;
    }

    public void registerModel (ItemCameraTransforms.TransformType cameraTransforms, PerspectiveDependentBakedHeatedItemItemModel model) {
        modelHashMap.put(cameraTransforms, model);
    }

    public PerspectiveDependentBakedHeatedItemItemModel getModel (ItemCameraTransforms.TransformType cameraTransforms) {
        return modelHashMap.get(cameraTransforms);
    }

    @Override
    public Pair<IBakedModel, Matrix4f> handlePerspective (ItemCameraTransforms.TransformType cameraTransformType) {
        if (inventory) {
            return getModel(ItemCameraTransforms.TransformType.GUI).handlePerspective(cameraTransformType);
        }

        return standard.handlePerspective(cameraTransformType);
    }
}
