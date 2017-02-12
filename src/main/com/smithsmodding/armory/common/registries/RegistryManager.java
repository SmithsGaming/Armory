package com.smithsmodding.armory.common.registries;

import com.google.common.collect.BiMap;
import com.smithsmodding.armory.api.IArmoryAPI;
import com.smithsmodding.armory.api.armor.IMultiComponentArmor;
import com.smithsmodding.armory.api.armor.IMultiComponentArmorExtension;
import com.smithsmodding.armory.api.armor.IMultiComponentArmorExtensionPosition;
import com.smithsmodding.armory.api.events.common.material.RegisterMaterialEvent;
import com.smithsmodding.armory.api.heatable.IHeatableObject;
import com.smithsmodding.armory.api.heatable.IHeatableObjectType;
import com.smithsmodding.armory.api.material.armor.IAddonArmorMaterial;
import com.smithsmodding.armory.api.material.anvil.IAnvilMaterial;
import com.smithsmodding.armory.api.material.armor.ICoreArmorMaterial;
import com.smithsmodding.armory.api.material.core.IMaterial;
import com.smithsmodding.armory.api.material.core.RegistryMaterialWrapper;
import com.smithsmodding.armory.api.registries.IRegistryManager;
import com.smithsmodding.armory.api.textures.creation.ICreationController;
import com.smithsmodding.armory.api.util.references.References;
import com.smithsmodding.smithscore.common.events.SmithsCoreRegistryEvent;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.IForgeRegistry;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.Map;

/**
 * Created by marcf on 1/2/2017.
 */
public final class RegistryManager implements IRegistryManager {

    @Nonnull
    private static final RegistryManager INSTANCE = new RegistryManager();

    @Nonnull
    protected IForgeRegistry<ICoreArmorMaterial> coreArmorMaterialIForgeRegistry;

    @Nonnull
    protected IForgeRegistry<IAddonArmorMaterial> addonArmorMaterialIForgeRegistry;

    @Nonnull
    protected IForgeRegistry<IAnvilMaterial> anvilMaterialIForgeRegistry;

    @Nonnull
    protected IForgeRegistry<RegistryMaterialWrapper> combinedMaterialRegistry;

    @Nonnull
    protected IForgeRegistry<IMultiComponentArmor> multiComponentArmorRegistry;

    @Nonnull
    protected IForgeRegistry<IMultiComponentArmorExtensionPosition> multiComponentArmorExtensionPositionRegistry;

    @Nonnull
    protected IForgeRegistry<IMultiComponentArmorExtension> multiComponentArmorExtensionRegistry;

    @Nonnull
    protected IForgeRegistry<IHeatableObject> heatableObjectRegistry;

    @Nonnull
    protected IForgeRegistry<IHeatableObjectType> heatableObjectTypeRegistry;

    @Nonnull
    protected IForgeRegistry<ICreationController> textureCreationControllerRegistry;

    /**
     * Getter for the current Instance of the RegistryManager
     * @return The current RegistryManager.
     */
    @Nonnull
    public static RegistryManager getInstance() {
        return INSTANCE;
    }

    private RegistryManager() {
    }

    /**
     * Getter for the @code{ICoreArmoryMaterial} Registry. Holds all registered @code{ICoreArmoryMaterial}. Managed by FML, as it is an instance of @code{IForgeRegistry}
     *
     * @return The @code{ICoreArmoryMaterial} Registry.
     */
    @Nonnull
    @Override
    public IForgeRegistry<ICoreArmorMaterial> getCoreMaterialRegistry() {
        return coreArmorMaterialIForgeRegistry;
    }

    /**
     * Getter for the @code{IAddonArmoryMaterial} Registry. Holds all registered @code{IAddonArmoryMaterial}. Managed by FML, as it is an instance of @code{IForgeRegistry}
     *
     * @return The @code{IAddonArmoryMaterial} Registry.
     */
    @Nonnull
    @Override
    public IForgeRegistry<IAddonArmorMaterial> getAddonArmorMaterialRegistry() {
        return addonArmorMaterialIForgeRegistry;
    }

    /**
     * Getter for the @code{IAnvilMaterial} Registry. Holds all registered @code{IAnvilMaterial}. Managed by FML, as it is an instance of @code{IForgeRegistry}
     *
     * @return The @code{IAnvilMaterial} Registry.
     */
    @Nonnull
    @Override
    public IForgeRegistry<IAnvilMaterial> getAnvilMaterialRegistry() {
        return anvilMaterialIForgeRegistry;
    }

    /**
     * Getter for the @code{RegistryWrapper<IMaterial>} Registry. Holds all registered @code{RegistryWrapper<IMaterial>}. Managed by FML, as it is an instance of @code{IForgeRegistry}
     *
     * @return The @code{RegistryWrapper<IMaterial>} Registry.
     */
    @Nonnull
    @Override
    public IForgeRegistry<RegistryMaterialWrapper> getCombinedMaterialRegistry() {
        return combinedMaterialRegistry;
    }

    /**
     * Getter for the @code{IMultiComponentArmor} Registry. Holds all registered @code{IMultiComponentArmor}. Managed by FML, as it is an instance of @code{IForgeRegistry}
     *
     * @return The @code{IMultiComponentArmor} Registry.
     */
    @Nonnull
    @Override
    public IForgeRegistry<IMultiComponentArmor> getMultiComponentArmorRegistry() {
        return multiComponentArmorRegistry;
    }

    /**
     * Getter for the @code{IMultiComponentArmorExtensionPosition} Registry. Holds all registered @code{IMultiComponentArmorExtensionPosition}. Managed by FML, as it is an instance of @code{IForgeRegistry}
     *
     * @return The @code{IMultiComponentArmorExtensionPosition} Registry.
     */
    @Nonnull
    @Override
    public IForgeRegistry<IMultiComponentArmorExtensionPosition> getMultiComponentArmorExtensionPositionRegistry() {
        return multiComponentArmorExtensionPositionRegistry;
    }

    /**
     * Getter for the @code{IMultiComponentArmorExtension} Registry. Holds all registered @code{IMultiComponentArmorExtension}. Managed by FML, as it is an instance of @code{IForgeRegistry}
     *
     * @return The @code{IMultiComponentArmorExtension} Registry.
     */
    @Nonnull
    @Override
    public IForgeRegistry<IMultiComponentArmorExtension> getMultiComponentArmorExtensionRegistry() {
        return multiComponentArmorExtensionRegistry;
    }

    /**
     * Getter for the @code{IHeatableObject} Registry. Holds all registered @code{IHeatableObject}. Managed by FML, as it is an instance of @code{IForgeRegistry}
     *
     * @return The @code{IHeatableObject} Registry.
     */
    @Nonnull
    @Override
    public IForgeRegistry<IHeatableObject> getHeatableObjectRegistry() {
        return heatableObjectRegistry;
    }

    /**
     * Getter for the @code{IHeatableObject} Registry. Holds all registered @code{IHeatableObject}. Managed by FML, as it is an instance of @code{IForgeRegistry}
     *
     * @return The @code{IHeatableObject} Registry.
     */
    @Nonnull
    @Override
    public IForgeRegistry<IHeatableObjectType> getHeatableObjectTypeRegistry() {
        return heatableObjectTypeRegistry;
    }

    /**
     * Getter for the @code{ICreationController} Registry. Holds all registered @code{ICreationController}. Managed by FML, as it is an instance of @code{IForgeRegistry}
     *
     * @return The @code{ICreationController} Registry.
     * @implNote This method only exists on the Client.
     */
    @Nonnull
    @Override
    @SideOnly(Side.CLIENT)
    public IForgeRegistry<ICreationController> getTextureCreationControllerRegistry() {
        return textureCreationControllerRegistry;
    }

    @Mod.EventBusSubscriber(modid = (References.General.MOD_ID))
    public static final class RegistryManagerRegistryRegistrationHandler {

        private RegistryManagerRegistryRegistrationHandler() {}

        /**
         * Eventhandler for the Registration of the Registries in the RegistryManager.
         * @param registerEvent The Event indicating that new registries can be registered.
         */
        @SubscribeEvent
        public static void handle(RegistryEvent.NewRegistry registerEvent) {

        }
    }

    @Mod.EventBusSubscriber(modid = (References.General.MOD_ID))
    public static final class RegistryManagerRegisterObjectsHandler {

        private RegistryManagerRegisterObjectsHandler(){}

        @SubscribeEvent
        public static void handleCore(RegistryEvent.Register<ICoreArmorMaterial> event) {
            new RegisterMaterialEvent<>(event.getRegistry()).PostCommon();

            for (IMaterial material : event.getRegistry()) {
                IArmoryAPI.Holder.getInstance().getRegistryManager().getCombinedMaterialRegistry().register(new RegistryMaterialWrapper(material));
            }
        }

        @SubscribeEvent
        public static void handleAddon(RegistryEvent.Register<IAddonArmorMaterial> event) {
            new RegisterMaterialEvent<>(event.getRegistry()).PostCommon();

            for (IMaterial material : event.getRegistry()) {
                IArmoryAPI.Holder.getInstance().getRegistryManager().getCombinedMaterialRegistry().register(new RegistryMaterialWrapper(material));
            }
        }

        @SubscribeEvent
        public static void handleAnvil(RegistryEvent.Register<IAnvilMaterial> event) {
            new RegisterMaterialEvent<>(event.getRegistry()).PostCommon();

            for (IMaterial material : event.getRegistry()) {
                IArmoryAPI.Holder.getInstance().getRegistryManager().getCombinedMaterialRegistry().register(new RegistryMaterialWrapper(material));
            }
        }
    }

    /**
     * Class used to convert callbacks, coming from the individual Registries, into Events posted on the SC Common bus-
     * @param <V> The Registry Entry type.
     */
    public static final class RegistryCallbackToEventConverter<V extends IForgeRegistryEntry<V>> implements
            IForgeRegistry.AddCallback<V>,
            IForgeRegistry.ClearCallback<V>,
            IForgeRegistry.CreateCallback<V>,
            IForgeRegistry.SubstitutionCallback<V> {

        /**
         * AddCallback. Called when a Entry is added to the registry.
         * @param obj The object that was added.
         * @param id The id it received
         * @param slaveset The slaves the registry has.
         */
        @Override
        public void onAdd(V obj, int id, Map<ResourceLocation, ?> slaveset) {
            new SmithsCoreRegistryEvent.Add<V>(obj, id, slaveset).PostCommon();
        }

        /**
         * ClearCallback. Called when the registry is cleared. For example on Client to server sync.
         * @param is The registry that is being cleared.
         * @param slaveset The slaves that the registry has.
         */
        @Override
        public void onClear(IForgeRegistry<V> is, Map<ResourceLocation, ?> slaveset) {
            new SmithsCoreRegistryEvent.Clear<V>(slaveset, is).PostCommon();
        }

        /**
         * CreateCallback. Called when the building process of a Registry has been completed.
         * @param slaveset The slaves that the registry has.
         * @param registries A list of all active registries.
         */
        @Override
        public void onCreate(Map<ResourceLocation, ?> slaveset, BiMap<ResourceLocation, ? extends IForgeRegistry<?>> registries) {
            new SmithsCoreRegistryEvent.Create<V>(slaveset, registries).PostCommon();
        }

        /**
         * SubstitutionCallback. Called when a Substitution of an already registry object occurs.
         * @param slaveset The slaves that the registry has.
         * @param original The original that is being replaced.
         * @param replacement The replacement-
         * @param name The registryname that indicates them both.
         */
        @Override
        public void onSubstituteActivated(Map<ResourceLocation, ?> slaveset, V original, V replacement, ResourceLocation name) {
            new SmithsCoreRegistryEvent.Substitute<V>(slaveset, original, replacement, name).PostCommon();
        }
    }

}
