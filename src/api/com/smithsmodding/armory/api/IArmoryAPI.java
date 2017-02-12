package com.smithsmodding.armory.api;

import com.smithsmodding.armory.api.helpers.IArmoryHelpers;
import com.smithsmodding.armory.api.registries.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by Marc on 22.02.2016.
 */
public interface IArmoryAPI
{



    /**
     * Getter for the RegistryManager.
     * @return The RegistryManager that is currently active.
     */
    @Nonnull
    IRegistryManager getRegistryManager();

//
//    IAnvilRecipeRegistry getAnvilRecipeRegistry();
//
//    IHeatableItemRegistry getHeatableItemRegistry();
//
//    IArmorPartRegistry getMedievalArmorPartRegistry();
//
//    IMaterialRegistry getArmorMaterialRegistry();
//
//    IArmorRegistry getArmorRegistry();
//
    @Nonnull
    IArmoryHelpers getHelpers();

    class Holder {

        @Nullable
        private static IArmoryAPI INSTANCE;

        /**
         * Getter for the current API Instance.
         * @return The current instance of the ArmoryAPI
         * @throws IllegalStateException thrown when the API has not been initialized
         */
        @Nullable
        public static IArmoryAPI getInstance() throws IllegalStateException {
            if (INSTANCE == null)
                throw new IllegalStateException();

            return INSTANCE;
        }

        public static void setInstance(@Nonnull IArmoryAPI api) {
            INSTANCE = api;
        }
    }
}
