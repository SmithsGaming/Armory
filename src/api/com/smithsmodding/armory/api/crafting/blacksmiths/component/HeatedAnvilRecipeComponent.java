package com.smithsmodding.armory.api.crafting.blacksmiths.component;

import com.smithsmodding.armory.api.IArmoryAPI;
import com.smithsmodding.armory.api.capability.IHeatableObjectCapability;
import com.smithsmodding.armory.api.capability.IHeatedObjectCapability;
import com.smithsmodding.armory.api.heatable.IHeatableObject;
import com.smithsmodding.armory.api.heatable.IHeatableObjectType;
import com.smithsmodding.armory.api.material.core.IMaterial;
import com.smithsmodding.armory.api.util.references.ModCapabilities;
import com.smithsmodding.armory.api.util.references.ModLogger;
import com.smithsmodding.smithscore.util.common.helper.ItemStackHelper;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by Orion
 * Created on 02.05.2015
 * 12:58
 * <p>
 * Copyrighted according to Project specific license
 */
public class HeatedAnvilRecipeComponent implements IAnvilRecipeComponent {

    private final float minTemp;
    private final float maxTemp;

    private IHeatableObjectType type;
    private IHeatableObject object;
    private IMaterial material;

    public HeatedAnvilRecipeComponent(IHeatableObjectType type, IHeatableObject object, IMaterial material, float minTemp, float maxTemp) {
        this.type = type;
        this.object = object;
        this.material = material;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
    }


    @Nullable
    @Override
    public ItemStack getComponentTargetStack() {
        return IArmoryAPI.Holder.getInstance().getHelpers().getFactories().getHeatedItemFactory().generateHeatedItemFromMaterial(material, object, type, ((minTemp + maxTemp) / 2));
    }

    @Nonnull
    @Override
    public HeatedAnvilRecipeComponent setComponentTargetStack(@Nonnull ItemStack targetStack) {
        if (!targetStack.hasCapability(ModCapabilities.MOD_HEATABLEOBJECT_CAPABILIT, null)) {
            ModLogger.getInstance().error("Tried to register recipe with a non heatable Item." + ItemStackHelper.toString(targetStack));
        }

        IHeatableObjectCapability heatableObjectCapability = targetStack.getCapability(ModCapabilities.MOD_HEATABLEOBJECT_CAPABILIT, null);
        material = heatableObjectCapability.getMaterial();
        object = heatableObjectCapability.getObject();
        type = heatableObjectCapability.getType();

        return this;
    }

    @Override
    public int getResultingStackSizeForComponent(ItemStack pComponentStack) {
        return 0;
    }

    @Nonnull
    @Override
    public HeatedAnvilRecipeComponent setComponentStackUsage(int pNewUsage) {
        return this;
    }

    @Override
    public boolean isValidComponentForSlot(@Nullable ItemStack pComparedItemStack) {
        if (pComparedItemStack == null)
            return false;

        if (!pComparedItemStack.hasCapability(ModCapabilities.MOD_HEATEDOBJECT_CAPABILITY, null)) {
            return false;
        }

        IHeatedObjectCapability heatedObjectCapability = pComparedItemStack.getCapability(ModCapabilities.MOD_HEATEDOBJECT_CAPABILITY, null);

        return heatedObjectCapability.getMaterial() == material && heatedObjectCapability.getObject() == object && heatedObjectCapability.getType() == type && ((minTemp <= heatedObjectCapability.getTemperature()) && (maxTemp >= heatedObjectCapability.getTemperature()));
    }
}
