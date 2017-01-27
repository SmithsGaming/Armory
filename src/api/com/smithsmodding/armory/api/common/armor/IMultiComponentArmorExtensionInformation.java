package com.smithsmodding.armory.api.common.armor;

import javax.annotation.Nonnull;

/**
 * Created by marcf on 1/6/2017.
 */
public interface IMultiComponentArmorExtensionInformation {

    @Nonnull
    IMultiComponentArmorExtensionPosition getPosition();

    @Nonnull
    IMultiComponentArmorExtension getExtension();

    @Nonnull
    Integer getCount();

    /**
     * Setter for the Position part of the information
     * @param position The new position
     * @return An instance of IMultiComponentArmorExtensionInformation which allows chaining.
     */
    IMultiComponentArmorExtensionInformation setPosition(@Nonnull IMultiComponentArmorExtensionPosition position);

    /**
     * Setter for the Extension part of the information
     * @param extension The new extension
     * @return An instance of IMultiComponentArmorExtensionInformation which allows chaining.
     */
    IMultiComponentArmorExtensionInformation setExtension(@Nonnull IMultiComponentArmorExtension extension);

    /**
     * Setter for the Count part of the information
     * @param count The new amount of installed extensions of the Extension type.
     * @return An instance of IMultiComponentArmorExtensionInformation which allows chaining.
     * @throws IllegalArgumentException is thrown when the new count is smaller then or equal to 0 or bigger then @code{IMultiComponentArmor.getMaximalInstallationCount()}
     */
    IMultiComponentArmorExtensionInformation setCount(@Nonnull Integer count) throws IllegalArgumentException;

    class Impl implements IMultiComponentArmorExtensionInformation {

        @Nonnull
        private IMultiComponentArmorExtensionPosition position;

        @Nonnull
        private IMultiComponentArmorExtension extension;

        @Nonnull
        private Integer count;

        @Nonnull
        @Override
        public IMultiComponentArmorExtensionPosition getPosition() {
            return position;
        }

        @Nonnull
        @Override
        public IMultiComponentArmorExtension getExtension() {
            return extension;
        }

        @Nonnull
        @Override
        public Integer getCount() {
            return count;
        }

        /**
         * Setter for the Position part of the information
         *
         * @param position The new position
         * @return An instance of IMultiComponentArmorExtensionInformation which allows chaining.
         */
        @Override
        public IMultiComponentArmorExtensionInformation setPosition(@Nonnull IMultiComponentArmorExtensionPosition position) {
            this.position = position;
            return this;
        }

        /**
         * Setter for the Extension part of the information
         *
         * @param extension The new extension
         * @return An instance of IMultiComponentArmorExtensionInformation which allows chaining.
         */
        @Override
        public IMultiComponentArmorExtensionInformation setExtension(@Nonnull IMultiComponentArmorExtension extension) {
            this.extension = extension;
            return this;
        }

        /**
         * Setter for the Count part of the information
         *
         * @param count The new amount of installed extensions of the Extension type.
         * @return An instance of IMultiComponentArmorExtensionInformation which allows chaining.
         * @throws IllegalArgumentException is thrown when the new count is smaller then or equal to 0 or bigger then @code{IMultiComponentArmor.getMaximalInstallationCount()}
         */
        @Override
        public IMultiComponentArmorExtensionInformation setCount(@Nonnull Integer count) throws IllegalArgumentException {
            this.count = count;
            return this;
        }
    }
}
