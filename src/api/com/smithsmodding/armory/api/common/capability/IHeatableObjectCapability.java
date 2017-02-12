package com.smithsmodding.armory.api.common.capability;

import com.smithsmodding.armory.api.IArmoryAPI;
import com.smithsmodding.armory.api.common.heatable.IHeatableObject;
import com.smithsmodding.armory.api.common.heatable.IHeatedObjectType;
import com.smithsmodding.armory.api.common.material.core.IMaterial;
import com.smithsmodding.armory.api.util.references.References;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nonnull;

/**
 * Created by marcf on 1/11/2017.
 */
public interface IHeatableObjectCapability extends IMaterializedStackCapability {
    /**
     * Getter for the HeatableItemType that this capability holds
     * @return The currently held HeatableItemType in this capabiity
     */
    @Nonnull
    IHeatableObject getObject();

    /**
     * Setter for the HeatableItemType stored in this capability.
     * @param object The new HeatableItemType.
     * @return The instance this method was called on so that method chaining can happen.
     */
    @Nonnull
    IHeatableObjectCapability setObject(@Nonnull IHeatableObject object);

    /**
     * Method to getCreationRecipe the HeatedType.
     * @return The Type that this HeatedObject is.
     */
    @Nonnull
    IHeatedObjectType getType();

    /**
     * Method to set the HeatedObjectType.
     * @param type The new Type of this Object.
     * @return The instance this method was called on.
     */
    IHeatableObjectCapability setType(@Nonnull IHeatedObjectType type);

    /**
     * Setter for the Material.
     *
     * @param material The new Material contained in the Stack.
     * @return The instance of the Capability the Material is set on.
     */
    @Override
    IHeatableObjectCapability setMaterial(@Nonnull IMaterial material);

    class Impl extends IMaterializedStackCapability.Impl implements IHeatableObjectCapability {

        @Nonnull
        private IHeatableObject object;

        @Nonnull
        private IHeatedObjectType type;

        /**
         * Getter for the HeatableItemType that this capability holds
         *
         * @return The currently held HeatableItemType in this capabiity
         */
        @Nonnull
        @Override
        public IHeatableObject getObject() {
            return object;
        }

        /**
         * Setter for the HeatableItemType stored in this capability.
         *
         * @param object The new HeatableItemType.
         * @return The instance this method was called on so that method chaining can happen.
         */
        @Nonnull
        @Override
        public IHeatableObjectCapability setObject(@Nonnull IHeatableObject object) {
            this.object = object;
            return this;
        }

        /**
         * Method to getCreationRecipe the HeatedType.
         *
         * @return The Type that this HeatedObject is.
         */
        @Nonnull
        @Override
        public IHeatedObjectType getType() {
            return type;
        }

        /**
         * Method to set the HeatedObjectType.
         *
         * @param type The new Type of this Object.
         * @return The instance this method was called on.
         */
        @Override
        public IHeatableObjectCapability setType(@Nonnull IHeatedObjectType type) {
            this.type = type;
            return this;
        }

        /**
         * Setter for the Material.
         *
         * @param material The new Material contained in the Stack.
         * @return The instance of the Capability the Material is set on.
         */
        @Override
        public IHeatableObjectCapability setMaterial(@Nonnull IMaterial material) {
            return (IHeatableObjectCapability) super.setMaterial(material);
        }
    }

    class Storage implements Capability.IStorage<IHeatableObjectCapability> {

        private static final IMaterializedStackCapability.Storage matStorage = new IMaterializedStackCapability.Storage();

        @Override
        public NBTBase writeNBT(Capability<IHeatableObjectCapability> capability, IHeatableObjectCapability instance, EnumFacing side) {
            return write(instance);
        }

        @Override
        public void readNBT(Capability<IHeatableObjectCapability> capability, IHeatableObjectCapability instance, EnumFacing side, NBTBase nbt) {
            read(instance, (NBTTagCompound) nbt);
        }

        public NBTTagCompound write(IHeatableObjectCapability instance) {
            NBTTagCompound compound = matStorage.write(instance);

            compound.setString(References.NBTTagCompoundData.HeatedObject.HEATEDOBJECT, instance.getObject().getRegistryName().toString());
            compound.setString(References.NBTTagCompoundData.HeatedObject.HEATEDTYPE, instance.getType().getRegistryName().toString());

            return compound;
        }

        public void read(IHeatableObjectCapability instance, NBTTagCompound compound) {
            matStorage.read(compound, instance);

            instance.setObject(IArmoryAPI.Holder.getInstance().getRegistryManager().getHeatableObjectRegistry().getValue(new ResourceLocation(compound.getString(References.NBTTagCompoundData.HeatedObject.HEATEDOBJECT))));
            instance.setType(IArmoryAPI.Holder.getInstance().getRegistryManager().getHeatableObjectTypeRegistry().getValue(new ResourceLocation(compound.getString(References.NBTTagCompoundData.HeatedObject.HEATEDTYPE))));
        }
    }
}
