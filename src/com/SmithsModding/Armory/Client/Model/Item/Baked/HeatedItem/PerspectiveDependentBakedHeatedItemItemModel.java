package com.SmithsModding.Armory.Client.Model.Item.Baked.HeatedItem;

import com.google.common.collect.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraftforge.client.model.*;

/**
 * Created by Marc on 08.12.2015.
 */
public class PerspectiveDependentBakedHeatedItemItemModel extends ItemLayerModel.BakedModel {

    public PerspectiveDependentBakedHeatedItemItemModel (ImmutableList<BakedQuad> quads, TextureAtlasSprite particle, VertexFormat format) {
        super(quads, particle, format);
    }

}
