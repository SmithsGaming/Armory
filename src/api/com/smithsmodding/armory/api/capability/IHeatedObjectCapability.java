package com.smithsmodding.armory.api.capability;

import com.smithsmodding.armory.api.heatable.IHeatableObject;
import com.smithsmodding.armory.api.heatable.IHeatableObjectType;
import com.smithsmodding.armory.api.material.core.IMaterial;
import com.smithsmodding.armory.api.util.references.References;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by marcf on 1/7/2017.
 */
public interface IHeatedObjectCapability extends IHeatableObjectCapability {

    /**
     * Getter for the temperature stored in this capability.
     * @return The temperature stored in this capability.
     */
    @Nonnull
    Float getTemperature();

    /**
     * Setter for the temperature stored in this capability.
     * @param temperature the new Temperature
     * @return The instance this method was called on so that method chaining can happen.
     */
    IHeatedObjectCapability setTemperatur(@Nonnull Float temperature);

    /**
     * Method used to increase the temperature stored in this capability.
     * @param addition The amount to increase.
     */
    void increaseTemperatur(@Nonnull Float addition);

    /**
     * Method used to decrease the temperature stored in this capability.
     * @param subtraction The amount to decrease.
     */
    void subtractTemperatur(@Nonnull Float subtraction);

    /**
     * Method to get the Original stack.
     * @return The stack that turned into this HeatedObject. Or null if something else is Heated.
     */
    @Nullable
    ItemStack getOriginalStack();

    /**
     * Method to set the Original Stack that created this HeatedObject.
     * @param stack The stack that turned this into a HeatedObject. Null if something else is Heated.
     * @return The instance you called this method on.
     */
    IHeatedObjectCapability setOriginalStack(@Nullable ItemStack stack);

    /**
     * Setter for the HeatableItemType stored in this capability.
     *
     * @param object The new HeatableItemType.
     * @return The instance this method was called on so that method chaining can happen.
     */
    @Nonnull
    @Override
    IHeatedObjectCapability setObject(@Nonnull IHeatableObject object);

    /**
     * Method to set the HeatedObjectType.
     *
     * @param type The new Type of this Object.
     * @return The instance this method was called on.
     */
    @Override
    IHeatedObjectCapability setType(@Nonnull IHeatableObjectType type);

    /**
     * Setter for the Material.
     *
     * @param material The new Material contained in the Stack.
     * @return The instance of the Capability the Material is set on.
     */
    @Override
    IHeatedObjectCapability setMaterial(@Nonnull IMaterial material);

    class Impl extends IHeatableObjectCapability.Impl implements IHeatedObjectCapability {

        @Nonnull
        private Float temperature = 20F;

        @Nullable
        private ItemStack stack;

        /**
         * Getter for the temperature stored in this capability.
         *
         * @return The temperature stored in this capability.
         */
        @Nonnull
        @Override
        public Float getTemperature() {
            return temperature;
        }

        /**
         * Setter for the temperature stored in this capability.
         *
         * @param temperature the new Temperature
         * @return The instance this method was called on so that method chaining can happen.
         */
        @Override
        public IHeatedObjectCapability setTemperatur(@Nonnull Float temperature) {
            this.temperature = temperature;
            return this;
        }

        /**
         * Method used to increase the temperature stored in this capability.
         *
         * @param addition The amount to increase.
         */
        @Override
        public void increaseTemperatur(@Nonnull Float addition) {
            this.setTemperatur(getTemperature() + addition);
        }

        /**
         * Method used to decrease the temperature stored in this capability.
         *
         * @param subtraction The amount to decrease.
         */
        @Override
        public void subtractTemperatur(@Nonnull Float subtraction) {
            this.increaseTemperatur( -1f * subtraction);
        }

        /**
         * Method to get the Original stack.
         *
         * @return The stack that turned into this HeatedObject. Or null if something else is Heated.
         */
        @Nullable
        @Override
        public ItemStack getOriginalStack() {
            return stack;
        }

        /**
         * Method to set the Original Stack that created this HeatedObject.
         *
         * @param stack The stack that turned this into a HeatedObject. Null if something else is Heated.
         * @return The instance you called this method on.
         */
        @Override
        public IHeatedObjectCapability setOriginalStack(@Nullable ItemStack stack) {
            this.stack = stack;
            return this;
        }

        /**
         * Setter for the HeatableItemType stored in this capability.
         *
         * @param object The new HeatableItemType.
         * @return The instance this method was called on so that method chaining can happen.
         */
        @Nonnull
        @Override
        public IHeatedObjectCapability setObject(@Nonnull IHeatableObject object) {
            return (IHeatedObjectCapability) super.setObject(object);
        }

        /**
         * Method to set the HeatedObjectType.
         *
         * @param type The new Type of this Object.
         * @return The instance this method was called on.
         */
        @Override
        @Nonnull
        public IHeatedObjectCapability setType(@Nonnull IHeatableObjectType type) {
            return (IHeatedObjectCapability) super.setType(type);
        }

        /**
         * Setter for the Material.
         *
         * @param material The new Material contained in the Stack.
         * @return The instance of the Capability the Material is set on.
         */
        @Override
        public IHeatedObjectCapability setMaterial(@Nonnull IMaterial material) {
            return (IHeatedObjectCapability) super.setMaterial(material);
        }
    }

    class Storage implements Capability.IStorage<IHeatedObjectCapability> {

        private static final IHeatableObjectCapability.Storage heatableStorage = new IHeatableObjectCapability.Storage();

        public NBTBase writeNBT(Capability<IHeatedObjectCapability> capability, IHeatedObjectCapability instance, EnumFacing side) {
            NBTTagCompound compound = heatableStorage.write(instance);

            compound.setFloat(References.NBTTagCompoundData.HeatedObject.HEATEDTEMP, instance.getTemperature());

            if (instance.getOriginalStack() != null) {
                compound.setTag(References.NBTTagCompoundData.HeatedObject.HEATEDSTACK, instance.getOriginalStack().serializeNBT());
            }

            return compound;
        }

        @Override
        public void readNBT(Capability<IHeatedObjectCapability> capability, IHeatedObjectCapability instance, EnumFacing side, NBTBase nbt) {
            NBTTagCompound compound = (NBTTagCompound) nbt;

            heatableStorage.read(instance, compound);

            instance.setTemperatur(compound.getFloat(References.NBTTagCompoundData.HeatedObject.HEATEDTEMP));

            if (compound.hasKey(References.NBTTagCompoundData.HeatedObject.HEATEDSTACK))
                instance.setOriginalStack(new ItemStack(compound.getCompoundTag(References.NBTTagCompoundData.HeatedObject.HEATEDSTACK)));
        }
    }
}
