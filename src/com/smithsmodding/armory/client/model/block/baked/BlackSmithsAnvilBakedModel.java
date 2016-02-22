package com.smithsmodding.armory.client.model.block.baked;

import net.minecraft.block.state.*;
import net.minecraft.client.resources.model.*;
import net.minecraftforge.client.model.*;

import java.util.*;

/**
 * Created by Marc on 22.02.2016.
 */
public class BlackSmithsAnvilBakedModel extends ISmartBlockModel.Wrapper {

    HashMap<String, IFlexibleBakedModel> bakedModelHashMap = new HashMap<>();

    public BlackSmithsAnvilBakedModel (IFlexibleBakedModel parent) {
        super(parent);
    }

    public void registerBakedModel(IFlexibleBakedModel model, String materialID)
    {
        bakedModelHashMap.put(materialID, model);
    }

    @Override
    public IBakedModel handleBlockState (IBlockState state) {
        return parent;
    }
}
