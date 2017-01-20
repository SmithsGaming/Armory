package com.smithsmodding.armory.api.capability.armor;

import javax.annotation.Nonnull;

/**
 * Created by marcf on 1/15/2017.
 */
public interface IValueContainingCapability<T, V> extends IArmorCapability {

    /**
     * Method to get the value stored in this capability;
     * @return The value stored.
     */
    @Nonnull
    V getValue();

    /**
     * Method to set the value stored in this capability;
     * @param value The new value
     * @return The instance this method was called on.
     */
    @Nonnull
    T setValue(@Nonnull V value);

    class Impl<T> implements IValueContainingCapability<Impl<T>, T> {

        T value;


        /**
         * Method to get the value stored in this capability;
         * @return The value stored.
         */
        @Nonnull
        @Override
        public T getValue() {
            return value;
        }

        /**
         * Method to set the value stored in this capability;
         * @param value The new value
         * @return The instance this method was called on.
         */
        @Nonnull
        @Override
        public Impl<T> setValue(@Nonnull T value) {
            this.value = value;
            return this;
        }
    }
}
