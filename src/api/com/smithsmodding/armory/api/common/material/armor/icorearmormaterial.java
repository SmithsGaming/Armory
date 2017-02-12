package com.smithsmodding.armory.api.common.material.armor;

import com.smithsmodding.armory.api.common.armor.IMultiComponentArmor;
import com.smithsmodding.armory.api.common.capability.armor.IArmorCapability;
import com.smithsmodding.armory.api.common.material.core.IMaterial;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nonnull;
import java.util.HashMap;

/**
 * Created by marcf on 1/2/2017.
 */
public interface ICoreArmorMaterial extends IMaterial<ICoreArmorMaterial> {

    /**
     * Method to getCreationRecipe the BaseDurability of a piece of armor made out of this material.
     * @param armor The armor to getCreationRecipe the base durability for.
     * @return The durability of a piece of armor made out of this material.
     */
    @Nonnull
    Integer getBaseDurabilityForArmor(@Nonnull IMultiComponentArmor armor);

    /**
     * Method to getCreationRecipe all the default capabilities this ArmorMaterial provides.
     * @return All the default capabilities this ArmorMaterial provides.
     */
    @Nonnull
    HashMap<Capability<? extends IArmorCapability>, Object> getOverrideCoreMaterialCapabilities(IMultiComponentArmor armor);
}
