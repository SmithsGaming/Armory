package com.smithsmodding.armory.client.model.item.baked.components;

import com.google.common.collect.ImmutableMap;
import com.smithsmodding.armory.api.common.material.armor.IAddonArmorMaterial;
import com.smithsmodding.armory.common.api.ArmoryAPI;
import com.smithsmodding.smithscore.util.client.ModelHelper;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.model.TRSRTransformation;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Marc on 06.12.2015.
 * <p>
 * A baked model for a component made up out of a single material.
 */
public class BakedAddonComponentModel extends BakedSubComponentModel {
    @Nonnull
    private final Overrides overrides;
    //Map that contains a premapped combination of materials to models.
    protected Map<ResourceLocation, IBakedModel> materializedComponents;

    /**
     * Creates a new Baked model from its parent for a single Component.
     *
     * @param base The models base.
     */
    public BakedAddonComponentModel(IBakedModel base) {
        super(base, ModelHelper.DEFAULT_ITEM_TRANSFORMS);

        materializedComponents = new HashMap<>(ArmoryAPI.getInstance().getRegistryManager().getAddonArmorMaterialRegistry().getValues().size());
        overrides = new Overrides(this);
    }

    public BakedAddonComponentModel(IBakedModel base, ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms) {
        super(base, transforms);

        materializedComponents = new HashMap<>(ArmoryAPI.getInstance().getRegistryManager().getAddonArmorMaterialRegistry().getValues().size());
        overrides = new Overrides(this);
    }

    /**
     * Function to register a new PreBakeable model to this model
     *
     * @param material The material to register a new model for.
     * @param model    The model to register.
     */
    public void addMaterialModel(@Nonnull IAddonArmorMaterial material, IBakedModel model) {
        materializedComponents.put(material.getRegistryName(), model);
    }

    /**
     * Function to getCreationRecipe a model from a Material.
     *
     * @param identifier The Material to getCreationRecipe the model for.
     * @return If registered it will return the prebaked model that is registered to that material id, if not it will return this instance of a BakedComponent model.
     */
    @Override
    public IBakedModel getModelByIdentifier(ResourceLocation identifier) {
        return materializedComponents.get(identifier);
    }

    @Nonnull
    @Override
    public ItemOverrideList getOverrides() {
        return overrides;
    }

    private static class Overrides extends ItemOverrideList {

        private final BakedAddonComponentModel parent;

        public Overrides(BakedAddonComponentModel parent) {
            super(new ArrayList<>());
            this.parent = parent;
        }

        @Override
        public IBakedModel handleItemState(IBakedModel originalModel, ItemStack stack, World world, EntityLivingBase entity) {
            return originalModel;
        }
    }
}
