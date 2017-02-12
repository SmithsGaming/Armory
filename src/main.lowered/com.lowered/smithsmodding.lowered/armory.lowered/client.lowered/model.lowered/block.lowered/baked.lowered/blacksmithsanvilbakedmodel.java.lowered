package com.smithsmodding.armory.client.model.block.baked;

import com.smithsmodding.armory.api.IArmoryAPI;
import com.smithsmodding.armory.api.common.material.anvil.IAnvilMaterial;
import com.smithsmodding.armory.api.util.references.ModMaterials;
import com.smithsmodding.armory.api.util.references.References;
import com.smithsmodding.armory.common.block.BlockBlackSmithsAnvil;
import com.smithsmodding.smithscore.client.model.baked.BakedWrappedModel;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.property.IExtendedBlockState;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Marc on 22.02.2016.
 */
public class BlackSmithsAnvilBakedModel extends BakedWrappedModel {
    @Nonnull
    private final Overrides overrides;
    @Nonnull
    private HashMap<IAnvilMaterial, IBakedModel> bakedModelHashMap = new HashMap<>();

    public BlackSmithsAnvilBakedModel(IBakedModel parent) {
        super(parent);
        overrides = new Overrides(this);
    }

    public void registerBakedModel(IAnvilMaterial material, IBakedModel model) {
        bakedModelHashMap.put(material, model);
    }

    @Nonnull
    @Override
    public List<BakedQuad> getQuads(@Nonnull IBlockState state, EnumFacing side, long rand) {
        if (((IExtendedBlockState) state).getValue(BlockBlackSmithsAnvil.PROPERTY_ANVIL_MATERIAL) == null)
            return getParentModel().getQuads(state, side, rand);

        IAnvilMaterial material = IArmoryAPI.Holder.getInstance().getRegistryManager().getAnvilMaterialRegistry().getValue(new ResourceLocation(((IExtendedBlockState) state).getValue(BlockBlackSmithsAnvil.PROPERTY_ANVIL_MATERIAL)));

        return bakedModelHashMap.get(material).getQuads(state, side, rand);
    }

    @Nonnull
    @Override
    public ItemOverrideList getOverrides() {
        return overrides;
    }

    private static class Overrides extends ItemOverrideList {

        private final BlackSmithsAnvilBakedModel parent;

        private Overrides(BlackSmithsAnvilBakedModel parent) {
            super(new ArrayList<>());
            this.parent = parent;
        }

        @Override
        public IBakedModel handleItemState(IBakedModel originalModel, ItemStack stack, World world, EntityLivingBase entity) {
            if (stack.getTagCompound() == null)
                return parent.bakedModelHashMap.get(ModMaterials.Anvil.IRON);

            IAnvilMaterial material = IArmoryAPI.Holder.getInstance().getRegistryManager().getAnvilMaterialRegistry().getValue(new ResourceLocation(stack.getTagCompound().getString(References.NBTTagCompoundData.TE.Anvil.MATERIAL)));

            return parent.bakedModelHashMap.get(material);
        }
    }
}
