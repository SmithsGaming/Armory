package com.smithsmodding.armory.common.registries;

import com.smithsmodding.armory.api.armor.IMultiComponentArmor;
import com.smithsmodding.armory.api.armor.IMultiComponentArmorExtension;
import com.smithsmodding.armory.api.armor.IMultiComponentArmorExtensionPosition;
import com.smithsmodding.armory.api.heatable.IHeatableObject;
import com.smithsmodding.armory.api.heatable.IHeatableObjectType;
import com.smithsmodding.armory.api.material.anvil.IAnvilMaterial;
import com.smithsmodding.armory.api.material.armor.IAddonArmorMaterial;
import com.smithsmodding.armory.api.material.armor.ICoreArmorMaterial;
import com.smithsmodding.armory.api.material.core.RegistryMaterialWrapper;
import com.smithsmodding.armory.api.util.references.ModRegistries;
import net.minecraftforge.fml.common.registry.RegistryBuilder;

/**
 * Created by marcf on 1/14/2017.
 */
public class CommonRegistryInitializer {

    public void initialize() {
        RegistryManager.getInstance().coreArmorMaterialIForgeRegistry  = new RegistryBuilder<ICoreArmorMaterial>()
                .setName(ModRegistries.COREARMORMATERIALS)
                .setType(ICoreArmorMaterial.class)
                .setIDRange(0, 255)
                .addCallback(new RegistryManager.RegistryCallbackToEventConverter<ICoreArmorMaterial>())
                .create();

        RegistryManager.getInstance().addonArmorMaterialIForgeRegistry = new RegistryBuilder<IAddonArmorMaterial>()
                .setName(ModRegistries.ADDONARMORMATERIALS)
                .setType(IAddonArmorMaterial.class)
                .setIDRange(0,255)
                .addCallback(new RegistryManager.RegistryCallbackToEventConverter<IAddonArmorMaterial>())
                .create();

        RegistryManager.getInstance().anvilMaterialIForgeRegistry = new RegistryBuilder<IAnvilMaterial>()
                .setName(ModRegistries.ANVILMATERIAL)
                .setType(IAnvilMaterial.class)
                .setIDRange(0,255)
                .addCallback(new RegistryManager.RegistryCallbackToEventConverter<IAnvilMaterial>())
                .create();

        RegistryManager.getInstance().combinedMaterialRegistry = new RegistryBuilder<RegistryMaterialWrapper>()
                .setName(ModRegistries.COMBINEDMATERIAL)
                .setType(RegistryMaterialWrapper.class)
                .setIDRange(0,255)
                .addCallback(new RegistryManager.RegistryCallbackToEventConverter<RegistryMaterialWrapper>())
                .create();

        RegistryManager.getInstance().multiComponentArmorRegistry = new RegistryBuilder<IMultiComponentArmor>()
                .setName(ModRegistries.MULTICOMPONENTARMOR)
                .setType(IMultiComponentArmor.class)
                .setIDRange(0,255)
                .addCallback(new RegistryManager.RegistryCallbackToEventConverter<IMultiComponentArmor>())
                .create();

        RegistryManager.getInstance().multiComponentArmorExtensionPositionRegistry = new RegistryBuilder<IMultiComponentArmorExtensionPosition>()
                .setName(ModRegistries.MULTICOMPONENTARMOREXTENSIONPOSITION)
                .setType(IMultiComponentArmorExtensionPosition.class)
                .setIDRange(0,255)
                .addCallback(new RegistryManager.RegistryCallbackToEventConverter<IMultiComponentArmorExtensionPosition>())
                .create();

        RegistryManager.getInstance().multiComponentArmorExtensionRegistry = new RegistryBuilder<IMultiComponentArmorExtension>()
                .setName(ModRegistries.MULTICOMPONENTARMOREXTENSION)
                .setType(IMultiComponentArmorExtension.class)
                .setIDRange(0,255)
                .addCallback(new RegistryManager.RegistryCallbackToEventConverter<IMultiComponentArmorExtension>())
                .create();

        RegistryManager.getInstance().heatableObjectRegistry = new RegistryBuilder<IHeatableObject>()
                .setName(ModRegistries.HEATABLEOBJECT)
                .setType(IHeatableObject.class)
                .setIDRange(0,255)
                .addCallback(new RegistryManager.RegistryCallbackToEventConverter<IHeatableObject>())
                .create();

        RegistryManager.getInstance().heatableObjectTypeRegistry = new RegistryBuilder<IHeatableObjectType>()
                .setName(ModRegistries.HEATABLEOJBECTTYPE)
                .setType(IHeatableObjectType.class)
                .setIDRange(0,255)
                .addCallback(new RegistryManager.RegistryCallbackToEventConverter<IHeatableObjectType>())
                .create();
    }
}
