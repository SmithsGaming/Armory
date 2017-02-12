package com.smithsmodding.armory.api.common.capability;

import com.smithsmodding.armory.api.IArmoryAPI;
import com.smithsmodding.armory.api.common.material.core.IMaterial;
import com.smithsmodding.armory.api.util.references.References;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nonnull;

/**
 * Created by marcf on 1/6/2017.
 */
public interface IMaterializedStackCapability {

    /**
     * Getter for the Material.
     * @return the instance of the Material that the stack holds
     */
    @Nonnull
    IMaterial getMaterial();

    /**
     * Setter for the Material.
     * @param material The new Material contained in the Stack.
     * @return The instance of the Capability the Material is set on.
     */
    IMaterializedStackCapability setMaterial(@Nonnull IMaterial material);

    class Impl implements IMaterializedStackCapability {

        @Nonnull
        private IMaterial material;


        /**
         * Getter for the Material.
         *
         * @return the instance of the Material that the stack holds
         */
        @Nonnull
        @Override
        public IMaterial getMaterial() {
            return material;
        }

        /**
         * Setter for the Material.
         *
         * @param material The new Material contained in the Stack.
         * @return The instance of the Capability the Material is set on.
         */
        @Override
        public IMaterializedStackCapability setMaterial(@Nonnull IMaterial material) {
            this.material = material;
            return this;
        }
    }

    class Storage implements Capability.IStorage<IMaterializedStackCapability>{
        @Override
        public NBTBase writeNBT(Capability<IMaterializedStackCapability> capability, IMaterializedStackCapability instance, EnumFacing side) {
            return write(instance);
        }

        @Override
        public void readNBT(Capability<IMaterializedStackCapability> capability, IMaterializedStackCapability instance, EnumFacing side, NBTBase nbt) {
            read((NBTTagCompound) nbt,instance);
        }

        public NBTTagCompound write(IMaterializedStackCapability instance) {
            NBTTagCompound compound = new NBTTagCompound();

            compound.setString(References.NBTTagCompoundData.MaterializedStack.MATERIAL, instance.getMaterial().getRegistryName().toString());

            return compound;
        }

        public void read(NBTTagCompound compound, IMaterializedStackCapability instance) {
            instance.setMaterial(IArmoryAPI.Holder.getInstance().getRegistryManager().getCombinedMaterialRegistry().getValue(new ResourceLocation(compound.getString(References.NBTTagCompoundData.MaterializedStack.MATERIAL))).getWrapped());
        }
    }
}
