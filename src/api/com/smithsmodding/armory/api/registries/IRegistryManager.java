package com.smithsmodding.armory.api.registries;

import com.smithsmodding.armory.api.armor.IMultiComponentArmor;
import com.smithsmodding.armory.api.armor.IMultiComponentArmorExtension;
import com.smithsmodding.armory.api.armor.IMultiComponentArmorExtensionPosition;
import com.smithsmodding.armory.api.heatable.IHeatableObject;
import com.smithsmodding.armory.api.heatable.IHeatableObjectType;
import com.smithsmodding.armory.api.material.armor.IAddonArmorMaterial;
import com.smithsmodding.armory.api.material.anvil.IAnvilMaterial;
import com.smithsmodding.armory.api.material.armor.ICoreArmorMaterial;
import com.smithsmodding.armory.api.material.core.RegistryMaterialWrapper;
import com.smithsmodding.armory.api.textures.creation.ICreationController;
import net.minecraftforge.fml.common.registry.IForgeRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

/**
 * Created by marcf on 1/2/2017.
 */
public interface IRegistryManager {

    /**
     * Getter for the @code{ICoreArmoryMaterial} Registry. Holds all registered @code{ICoreArmoryMaterial}. Managed by FML, as it is an instance of @code{IForgeRegistry}
     * @return The @code{ICoreArmoryMaterial} Registry.
     */
    @Nonnull
    IForgeRegistry<ICoreArmorMaterial> getCoreMaterialRegistry();

    /**
     * Getter for the @code{IAddonArmoryMaterial} Registry. Holds all registered @code{IAddonArmoryMaterial}. Managed by FML, as it is an instance of @code{IForgeRegistry}
     * @return The @code{IAddonArmoryMaterial} Registry.
     */
    @Nonnull
    IForgeRegistry<IAddonArmorMaterial> getAddonArmorMaterialRegistry();

    /**
     * Getter for the @code{IAnvilMaterial} Registry. Holds all registered @code{IAnvilMaterial}. Managed by FML, as it is an instance of @code{IForgeRegistry}
     * @return The @code{IAnvilMaterial} Registry.
     */
    @Nonnull
    IForgeRegistry<IAnvilMaterial> getAnvilMaterialRegistry();

    /**
     * Getter for the @code{RegistryWrapper<IMaterial>} Registry. Holds all registered @code{RegistryWrapper<IMaterial>}. Managed by FML, as it is an instance of @code{IForgeRegistry}
     * @return The @code{RegistryWrapper<IMaterial>} Registry.
     */
    @Nonnull
    IForgeRegistry<RegistryMaterialWrapper> getCombinedMaterialRegistry();

    /**
     * Getter for the @code{IMultiComponentArmor} Registry. Holds all registered @code{IMultiComponentArmor}. Managed by FML, as it is an instance of @code{IForgeRegistry}
     * @return The @code{IMultiComponentArmor} Registry.
     */
    @Nonnull
    IForgeRegistry<IMultiComponentArmor> getMultiComponentArmorRegistry();

    /**
     * Getter for the @code{IMultiComponentArmorExtensionPosition} Registry. Holds all registered @code{IMultiComponentArmorExtensionPosition}. Managed by FML, as it is an instance of @code{IForgeRegistry}
     * @return The @code{IMultiComponentArmorExtensionPosition} Registry.
     */
    @Nonnull
    IForgeRegistry<IMultiComponentArmorExtensionPosition> getMultiComponentArmorExtensionPositionRegistry();

    /**
     * Getter for the @code{IMultiComponentArmorExtension} Registry. Holds all registered @code{IMultiComponentArmorExtension}. Managed by FML, as it is an instance of @code{IForgeRegistry}
     * @return The @code{IMultiComponentArmorExtension} Registry.
     */
    @Nonnull
    IForgeRegistry<IMultiComponentArmorExtension> getMultiComponentArmorExtensionRegistry();

    /**
     * Getter for the @code{IHeatableObject} Registry. Holds all registered @code{IHeatableObject}. Managed by FML, as it is an instance of @code{IForgeRegistry}
     * @return The @code{IHeatableObject} Registry.
     */
    @Nonnull
    IForgeRegistry<IHeatableObject> getHeatableObjectRegistry();

    /**
     * Getter for the @code{IHeatableObject} Registry. Holds all registered @code{IHeatableObject}. Managed by FML, as it is an instance of @code{IForgeRegistry}
     * @return The @code{IHeatableObject} Registry.
     */
    @Nonnull
    IForgeRegistry<IHeatableObjectType> getHeatableObjectTypeRegistry();

    /**
     * Getter for the @code{ICreationController} Registry. Holds all registered @code{ICreationController}. Managed by FML, as it is an instance of @code{IForgeRegistry}
     * @return The @code{ICreationController} Registry.
     *
     * @implNote This method only exists on the Client.
     */
    @SideOnly(Side.CLIENT)
    @Nonnull
    IForgeRegistry<ICreationController> getTextureCreationControllerRegistry();

}
