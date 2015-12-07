package com.SmithsModding.Armory.Client.Model.Item.Baked;

import com.SmithsModding.Armory.API.Armor.MultiLayeredArmor;
import com.SmithsModding.Armory.API.Item.ISingleMaterialItem;
import com.SmithsModding.Armory.API.Materials.IArmorMaterial;
import com.SmithsModding.Armory.Common.Material.MaterialRegistry;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.IFlexibleBakedModel;
import net.minecraftforge.client.model.ISmartItemModel;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Marc on 06.12.2015.
 * <p/>
 * A baked model for a component made up out of a single Material.
 */
public class BakedComponentModel extends IFlexibleBakedModel.Wrapper implements ISmartItemModel {
    //Map that contains a premapped combination of materials to models.
    protected Map<String, IFlexibleBakedModel> materializedComponents;

    /**
     * Creates a new Baked model from its parent for a single Component.
     *
     * @param base The models base.
     */
    public BakedComponentModel (IFlexibleBakedModel base) {
        super(base, base.getFormat());

        materializedComponents = new HashMap<String, IFlexibleBakedModel>(MaterialRegistry.getInstance().getArmorMaterials().size());
    }

    /**
     * Function to register a new PreBakeable model to this Model
     *
     * @param material The material to register a new model for.
     * @param model    The model to register.
     */
    public void addMaterialModel (IArmorMaterial material, IFlexibleBakedModel model) {
        materializedComponents.put(material.getInternalMaterialName(), model);
    }

    /**
     * Function used to get a Baked model from an ItemStack.
     *
     * @param stack The ItemStack to get the Model for.
     * @return The baked model.
     */
    @Override
    public IBakedModel handleItemState (ItemStack stack) {
        if (stack.getItem() instanceof MultiLayeredArmor) {
            String id = ((ISingleMaterialItem) stack.getItem()).getMaterialInternalName(stack);
            return getModelByIdentifier(id);
        }
        return this;
    }

    /**
     * Function to get a model from a MaterialID.
     *
     * @param identifier The MaterialId to get the model for.
     * @return If registered it will return the prebaked model that is registered to that material id, if not it will return this instance of a BakedComponent model.
     */
    public IFlexibleBakedModel getModelByIdentifier (String identifier) {
        IFlexibleBakedModel materialModel = materializedComponents.get(identifier);
        if (materialModel == null) {
            return this;
        }

        return materialModel;
    }
}
