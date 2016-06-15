package com.smithsmodding.armory.client.model.item.baked.components;

import com.google.common.collect.ImmutableMap;
import com.smithsmodding.armory.api.item.ISingleMaterialItem;
import com.smithsmodding.armory.api.materials.IArmorMaterial;
import com.smithsmodding.armory.common.material.MaterialRegistry;
import com.smithsmodding.smithscore.client.model.baked.BakedWrappedModel;
import com.smithsmodding.smithscore.client.model.unbaked.DummyModel;
import com.smithsmodding.smithscore.util.client.ModelHelper;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.model.TRSRTransformation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Marc on 06.12.2015.
 * <p/>
 * A baked model for a component made up out of a single material.
 */
public class BakedSubComponentModel extends BakedWrappedModel.PerspectiveAware {
    private final Overrides overrides;
    //Map that contains a premapped combination of materials to models.
    protected Map<String, IBakedModel> materializedComponents;

    /**
     * Creates a new Baked model from its parent for a single Component.
     *
     * @param base The models base.
     */
    public BakedSubComponentModel(IBakedModel base) {
        super(base, ModelHelper.DEFAULT_ITEM_TRANSFORMS);

        materializedComponents = new HashMap<String, IBakedModel>(MaterialRegistry.getInstance().getArmorMaterials().size());
        overrides = new Overrides(this);
    }

    public BakedSubComponentModel(IBakedModel base, ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms) {
        super(base, transforms);

        materializedComponents = new HashMap<String, IBakedModel>(MaterialRegistry.getInstance().getArmorMaterials().size());
        overrides = new Overrides(this);
    }

    /**
     * Function to register a new PreBakeable model to this model
     *
     * @param material The material to register a new model for.
     * @param model    The model to register.
     */
    public void addMaterialModel(IArmorMaterial material, IBakedModel model) {
        materializedComponents.put(material.getUniqueID(), model);
    }

    /**
     * Function to get a model from a MaterialID.
     *
     * @param identifier The MaterialId to get the model for.
     * @return If registered it will return the prebaked model that is registered to that material id, if not it will return this instance of a BakedComponent model.
     */
    public IBakedModel getModelByIdentifier(String identifier) {
        IBakedModel materialModel = materializedComponents.get(identifier);
        if (materialModel == null) {
            return DummyModel.BAKED_MODEL;
        }

        return materialModel;
    }

    @Override
    public ItemOverrideList getOverrides() {
        return overrides;
    }

    private static class Overrides extends ItemOverrideList {

        private final BakedSubComponentModel parent;

        public Overrides(BakedSubComponentModel parent) {
            super(new ArrayList<>());
            this.parent = parent;
        }

        @Override
        public IBakedModel handleItemState(IBakedModel originalModel, ItemStack stack, World world, EntityLivingBase entity) {
            if (stack.getItem() instanceof ISingleMaterialItem) {
                String id = ((ISingleMaterialItem) stack.getItem()).getMaterialInternalName(stack);
                return parent.getModelByIdentifier(id);
            }
            return originalModel;
        }
    }
}
