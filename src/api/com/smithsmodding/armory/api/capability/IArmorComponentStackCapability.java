package com.smithsmodding.armory.api.capability;

import com.smithsmodding.armory.api.armor.IMultiComponentArmorExtension;

import javax.annotation.Nonnull;

/**
 * Created by marcf on 1/8/2017.
 */
public interface IArmorComponentStackCapability {

    /**
     * Method to get the Extension stored in the Capability.
     * @return The extension stored in the Stack.
     */
    @Nonnull
    IMultiComponentArmorExtension getExtension();

    /**
     * Method to set the Extension stored in the Capability.
     * @param extension The new Extension stored in the Capability.
     * @return The instance of which this method was called.
     */
    IArmorComponentStackCapability setExtension(@Nonnull IMultiComponentArmorExtension extension);

    class Impl implements IArmorComponentStackCapability {

        @Nonnull
        private IMultiComponentArmorExtension extension;

        /**
         * Method to get the Extension stored in the Capability.
         *
         * @return The extension stored in the Stack.
         */
        @Nonnull
        @Override
        public IMultiComponentArmorExtension getExtension() {
            return extension;
        }

        /**
         * Method to set the Extension stored in the Capability.
         *
         * @param extension The new Extension stored in the Capability.
         * @return The instance of which this method was called.
         */
        @Override
        public IArmorComponentStackCapability setExtension(@Nonnull IMultiComponentArmorExtension extension) {
            this.extension = extension;
            return this;
        }
    }
}
