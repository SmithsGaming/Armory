package com.smithsmodding.armory.common.registries;

import com.google.common.collect.BiMap;
import com.smithsmodding.armory.Armory;
import com.smithsmodding.armory.api.IArmoryAPI;
import com.smithsmodding.armory.api.client.textures.creation.ICreationController;
import com.smithsmodding.armory.api.common.armor.IMultiComponentArmor;
import com.smithsmodding.armory.api.common.armor.IMultiComponentArmorExtension;
import com.smithsmodding.armory.api.common.armor.IMultiComponentArmorExtensionPosition;
import com.smithsmodding.armory.api.common.crafting.blacksmiths.recipe.IAnvilRecipe;
import com.smithsmodding.armory.api.common.crafting.mixing.IMoltenMetalMixingRecipe;
import com.smithsmodding.armory.api.common.events.common.material.RegisterMaterialEvent;
import com.smithsmodding.armory.api.common.heatable.IHeatableObject;
import com.smithsmodding.armory.api.common.heatable.IHeatedObjectType;
import com.smithsmodding.armory.api.common.initialization.IInitializationComponent;
import com.smithsmodding.armory.api.common.material.anvil.IAnvilMaterial;
import com.smithsmodding.armory.api.common.material.armor.IAddonArmorMaterial;
import com.smithsmodding.armory.api.common.material.armor.ICoreArmorMaterial;
import com.smithsmodding.armory.api.common.material.core.RegistryMaterialWrapper;
import com.smithsmodding.armory.api.common.registries.IRegistryManager;
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
 * The central registry manager for Armory.
 */
public final class RegistryManager implements IRegistryManager {

    @Nonnull
    private static final RegistryManager INSTANCE = new RegistryManager();

    @Nonnull
    IForgeRegistry<ICoreArmorMaterial> coreArmorMaterialIForgeRegistry = null;

    @Nonnull
    IForgeRegistry<IAddonArmorMaterial> addonArmorMaterialIForgeRegistry = null;

    @Nonnull
    IForgeRegistry<IAnvilMaterial> anvilMaterialIForgeRegistry = null;

    @Nonnull
    IForgeRegistry<RegistryMaterialWrapper> combinedMaterialRegistry = null;

    @Nonnull
    IForgeRegistry<IMultiComponentArmor> multiComponentArmorRegistry = null;

    @Nonnull
    IForgeRegistry<IMultiComponentArmorExtensionPosition> multiComponentArmorExtensionPositionRegistry = null;

    @Nonnull
    IForgeRegistry<IMultiComponentArmorExtension> multiComponentArmorExtensionRegistry = null;

    @Nonnull
    IForgeRegistry<IHeatableObject> heatableObjectRegistry = null;

    @Nonnull
    IForgeRegistry<IHeatedObjectType> heatableObjectTypeRegistry = null;

    @Nonnull
    IForgeRegistry<IAnvilRecipe> anvilRecipeRegistry = null;

    @Nonnull
    IForgeRegistry<IMoltenMetalMixingRecipe> moltenMetalMixingRecipesRegistry = null;

    @Nonnull
    IForgeRegistry<IInitializationComponent> initializationComponentRegistry = null;

    @Nonnull
    IForgeRegistry<ICreationController> textureCreationControllerRegistry = null;

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
     * Getter for the {@link ICoreArmorMaterial} Registry. Holds all registered {@link ICoreArmorMaterial}. Managed by FML, as it is an instance of {@link IForgeRegistry}
     *
     * @return The {@link ICoreArmorMaterial} Registry.
     */
    @Nonnull
    @Override
    public IForgeRegistry<ICoreArmorMaterial> getCoreMaterialRegistry() {
        return coreArmorMaterialIForgeRegistry;
    }

    /**
     * Getter for the {@link IAddonArmorMaterial} Registry. Holds all registered {@link IAddonArmorMaterial}. Managed by FML, as it is an instance of {@link IForgeRegistry}
     *
     * @return The {@link IAddonArmorMaterial} Registry.
     */
    @Nonnull
    @Override
    public IForgeRegistry<IAddonArmorMaterial> getAddonArmorMaterialRegistry() {
        return addonArmorMaterialIForgeRegistry;
    }

    /**
     * Getter for the {@link IAnvilMaterial} Registry. Holds all registered {@link IAnvilMaterial}. Managed by FML, as it is an instance of {@link IForgeRegistry}
     *
     * @return The {@link IAnvilMaterial} Registry.
     */
    @Nonnull
    @Override
    public IForgeRegistry<IAnvilMaterial> getAnvilMaterialRegistry() {
        return anvilMaterialIForgeRegistry;
    }

    /**
     * Getter for the {@link RegistryMaterialWrapper} Registry. Holds all registered {@link RegistryMaterialWrapper}. Managed by FML, as it is an instance of {@link IForgeRegistry}
     *
     * @return The {@link RegistryMaterialWrapper} Registry.
     */
    @Nonnull
    @Override
    public IForgeRegistry<RegistryMaterialWrapper> getCombinedMaterialRegistry() {
        return combinedMaterialRegistry;
    }

    /**
     * Getter for the {@link IMultiComponentArmorExtensionPosition} Registry. Holds all registered {@link IMultiComponentArmorExtensionPosition}. Managed by FML, as it is an instance of {@link IForgeRegistry}
     *
     * @return The {@link IMultiComponentArmorExtensionPosition} Registry.
     */
    @Nonnull
    @Override
    public IForgeRegistry<IMultiComponentArmorExtensionPosition> getMultiComponentArmorExtensionPositionRegistry() {
        return multiComponentArmorExtensionPositionRegistry;
    }

    /**
     * Getter for the {@link IMultiComponentArmorExtension} Registry. Holds all registered {@link IMultiComponentArmorExtension}. Managed by FML, as it is an instance of {@link IForgeRegistry}
     *
     * @return The {@link IMultiComponentArmorExtension} Registry.
     */
    @Nonnull
    @Override
    public IForgeRegistry<IMultiComponentArmorExtension> getMultiComponentArmorExtensionRegistry() {
        return multiComponentArmorExtensionRegistry;
    }

    /**
     * Getter for the {@link IMultiComponentArmor} Registry. Holds all registered {@link IMultiComponentArmor}. Managed by FML, as it is an instance of {@link IForgeRegistry}
     *
     * @return The {@link IMultiComponentArmor} Registry.
     */
    @Nonnull
    @Override
    public IForgeRegistry<IMultiComponentArmor> getMultiComponentArmorRegistry() {
        return multiComponentArmorRegistry;
    }


    /**
     * Getter for the {@link IHeatableObject} Registry. Holds all registered {@link IHeatableObject}. Managed by FML, as it is an instance of {@link IForgeRegistry}
     *
     * @return The {@link IHeatableObject} Registry.
     */
    @Nonnull
    @Override
    public IForgeRegistry<IHeatableObject> getHeatableObjectRegistry() {
        return heatableObjectRegistry;
    }

    /**
     * Getter for the {@link IHeatableObject} Registry. Holds all registered {@link IHeatableObject}. Managed by FML, as it is an instance of {@link IForgeRegistry}
     *
     * @return The {@link IHeatableObject} Registry.
     */
    @Nonnull
    @Override
    public IForgeRegistry<IHeatedObjectType> getHeatableObjectTypeRegistry() {
        return heatableObjectTypeRegistry;
    }

    /**
     * Getter for the {@link IAnvilRecipe} Registry. Holds all registered {@link IAnvilRecipe}. Managed by FML, as it is an instance of {@link IForgeRegistry}
     *
     * @return The {@link IAnvilRecipe} Registry.
     */
    @Nonnull
    @Override
    public IForgeRegistry<IAnvilRecipe> getAnvilRecipeRegistry() {
        return anvilRecipeRegistry;
    }

    /**
     * Getter for the {@link IMoltenMetalMixingRecipe} Registry. Holds all registered {@link IMoltenMetalMixingRecipe}. Managed by FML, as it is an instance of {@link IForgeRegistry}
     *
     * @return The {@link IMoltenMetalMixingRecipe} Registry.
     */
    @Nonnull
    @Override
    public IForgeRegistry<IMoltenMetalMixingRecipe> getMoltenMetalMixingRecipeRegistry() {
        return moltenMetalMixingRecipesRegistry;
    }

    /**
     * Getter for the {@link IInitializationComponent} Registry. Holds all registered {@link IInitializationComponent}. Managed by FML, as it is an instance of {@link IForgeRegistry}
     * @return The {@link IInitializationComponent} Registry.
     */
    @Nonnull
    public IForgeRegistry<IInitializationComponent> getInitializationComponentRegistry() {
        return initializationComponentRegistry;
    }

    /**
     * Getter for the {@link ICreationController} Registry. Holds all registered {@link ICreationController}. Managed by FML, as it is an instance of {@link IForgeRegistry}
     *
     * @return The {@link ICreationController} Registry.
     */
    @Nonnull
    @Override
    @SideOnly(Side.CLIENT)
    public IForgeRegistry<ICreationController> getTextureCreationControllerRegistry() {
        return textureCreationControllerRegistry;
    }

    @Mod.EventBusSubscriber() //modid = (References.General.MOD_ID)
    public static final class RegistryManagerRegistryRegistrationHandler {

        private RegistryManagerRegistryRegistrationHandler() {}

        /**
         * Eventhandler for the Registration of the Registries in the RegistryManager.
         * @param registerEvent The Event indicating that new registries can be registered.
         */
        @SubscribeEvent
        public static void handle(RegistryEvent.NewRegistry registerEvent) {
            Armory.registryInitializer.initialize();
        }
    }

    @Mod.EventBusSubscriber() //modid = (References.General.MOD_ID)
    public static final class RegistryManagerRegisterObjectsHandler {

        private RegistryManagerRegisterObjectsHandler(){}

        @SubscribeEvent
        public static void handleCore(RegistryEvent.Register<ICoreArmorMaterial> event) {
            new RegisterMaterialEvent<>(event.getRegistry()).PostCommon();
        }

        @SubscribeEvent
        public static void handleAddon(RegistryEvent.Register<IAddonArmorMaterial> event) {
            new RegisterMaterialEvent<>(event.getRegistry()).PostCommon();
        }

        @SubscribeEvent
        public static void handleAnvil(RegistryEvent.Register<IAnvilMaterial> event) {
            new RegisterMaterialEvent<>(event.getRegistry()).PostCommon();
        }

        @SubscribeEvent
        public static void handleCombined(RegistryEvent.Register<RegistryMaterialWrapper> event) {
            for (ICoreArmorMaterial material : IArmoryAPI.Holder.getInstance().getRegistryManager().getCoreMaterialRegistry()) {
                event.getRegistry().register(new RegistryMaterialWrapper(material));
            }

            for (IAddonArmorMaterial material : IArmoryAPI.Holder.getInstance().getRegistryManager().getAddonArmorMaterialRegistry()) {
                event.getRegistry().register(new RegistryMaterialWrapper(material));
            }

            for (IAnvilMaterial material : IArmoryAPI.Holder.getInstance().getRegistryManager().getAnvilMaterialRegistry()) {
                event.getRegistry().register(new RegistryMaterialWrapper(material));
            }
        }
    }

    /**
     * Class used to convert callback, coming from the individual Registries, into Events posted on the SC Common bus-
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
