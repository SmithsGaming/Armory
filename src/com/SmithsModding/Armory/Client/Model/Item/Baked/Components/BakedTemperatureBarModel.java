package com.SmithsModding.Armory.Client.Model.Item.Baked.Components;

import net.minecraft.client.resources.model.*;
import net.minecraftforge.client.model.*;

import java.util.*;

/**
 * Created by Marc on 08.12.2015.
 */
public class BakedTemperatureBarModel extends MultiTextureBakedComponentModelLayer {

    ArrayList<IBakedModel> textures = new ArrayList<IBakedModel>();

    /**
     * Creates a new Baked model from its parent for a single Component.
     *
     * @param base The models base.
     */
    public BakedTemperatureBarModel (IFlexibleBakedModel base) {
        super(base);
    }

    public void addTexture (IBakedModel texture) {
        textures.add(texture);
    }

    public IBakedModel getModel (int index) {
        return textures.get(index);
    }

    public int getModelCount () {
        return textures.size();
    }
}
