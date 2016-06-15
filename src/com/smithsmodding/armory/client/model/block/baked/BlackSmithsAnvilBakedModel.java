package com.smithsmodding.armory.client.model.block.baked;

import com.smithsmodding.armory.common.block.BlockBlackSmithsAnvil;
import com.smithsmodding.armory.util.References;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.property.IExtendedBlockState;

import java.util.HashMap;

/**
 * Created by Marc on 22.02.2016.
 */
public class BlackSmithsAnvilBakedModel extends ISmartBlockModel.Wrapper implements ISmartItemModel {
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
        if (((IExtendedBlockState) state).getValue(BlockBlackSmithsAnvil.PROPERTY_ANVIL_MATERIAL) == null)
            return parent;

        return bakedModelHashMap.get(((IExtendedBlockState) state).getValue(BlockBlackSmithsAnvil.PROPERTY_ANVIL_MATERIAL));
    }

    @Override
    public IBakedModel handleItemState (ItemStack stack) {
        if (stack.getTagCompound() == null)
            return bakedModelHashMap.get(References.InternalNames.Materials.Anvil.IRON);

        return bakedModelHashMap.get(stack.getTagCompound().getString(References.NBTTagCompoundData.TE.Anvil.MATERIAL));
    }
}
