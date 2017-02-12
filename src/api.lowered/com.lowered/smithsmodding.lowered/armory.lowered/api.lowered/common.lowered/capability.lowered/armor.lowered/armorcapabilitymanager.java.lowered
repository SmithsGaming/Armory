package com.smithsmodding.armory.api.common.capability.armor;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.function.BiConsumer;

/**
 * Created by marcf on 1/17/2017.
 */
public final class ArmorCapabilityManager implements ICapabilitySerializable<NBTTagCompound> {

    private final HashMap<Capability<? extends IArmorCapability>, Object> capabilities;

    public ArmorCapabilityManager() {
        this(new HashMap<>());
    }

    public ArmorCapabilityManager(HashMap<Capability<? extends IArmorCapability>, Object> capabilities) {
        this.capabilities = capabilities;
    }


    /**
     * Method to register a new Instance of a Capability.
     *
     * @param cap The capability to register it.
     */
    public void registerNewInstance(@Nonnull Capability<? extends IArmorCapability> cap) {
        this.registerCapability(cap, cap.getDefaultInstance());
    }

    /**
     * Method to register a specific Instance of a Capability.
     *
     * @param cap      The capability to register it.
     * @param instance The instance of the capability to register.
     */
    public void registerCapability(Capability<? extends IArmorCapability> cap, Object instance) {
        this.capabilities.put(cap, instance);
    }

    /**
     * Method to remove a Capability from the Dispatcher.
     *
     * @param cap The capability to remove.
     * @return The instance of the capability that was registered if any. Null if none was registered or was stored under that key.
     */
    @Nullable
    public Object removeCapability(Capability<? extends IArmorCapability> cap) {
        return capabilities.remove(cap);
    }


    /**
     * Determines if this object has support for the capability in question on the specific side.
     * The return value of this MIGHT change during runtime if this object gains or looses support
     * for a capability.
     * <p>
     * Example:
     * A Pipe getting a cover placed on one side causing it loose the Inventory attachment function for that side.
     * <p>
     * This is a light weight version of getCapability, intended for metadata uses.
     *
     * @param capability The capability to check
     * @param facing     The Side to check from:
     *                   CAN BE NULL. Null is defined to represent 'internal' or 'self'
     * @return True if this object supports the capability.
     */
    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capabilities.containsKey(capability);
    }

    /**
     * Retrieves the handler for the capability requested on the specific side.
     * The return value CAN be null if the object does not support the capability.
     * The return value CAN be the same for multiple faces.
     *
     * @param capability The capability to check
     * @param facing     The Side to check from:
     *                   CAN BE NULL. Null is defined to represent 'internal' or 'self'
     * @return The requested capability. Returns null when {@link #hasCapability(Capability, EnumFacing)} would return false.
     */
    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return (T) capabilities.get(capability);
    }

    /**
     * Getter for the Capabilities stored in this manager.
     * @return The capabilities stored in this manager.
     */
    public HashMap<Capability<? extends IArmorCapability>, Object> getCapabilities() {
        return capabilities;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound compound = new NBTTagCompound();

        capabilities.forEach(new SerializationBiConsumer(compound));

        return compound;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        capabilities.forEach(new DeSerializationBiConsumer(nbt));
    }

    private class SerializationBiConsumer<T> implements BiConsumer<Capability<T>, T> {

        private final NBTTagCompound managerCompound;

        public SerializationBiConsumer(NBTTagCompound managerCompound) {
            this.managerCompound = managerCompound;
        }

        /**
         * Performs this operation on the given arguments.
         *
         * @param tCapability the first input argument
         * @param t           the first input argument
         */
        @Override
        public void accept(Capability<T> tCapability, T t) {
            managerCompound.setTag(tCapability.getName(), tCapability.writeNBT(t, null));
        }
    }

    private class DeSerializationBiConsumer<T> implements BiConsumer<Capability<T>, T> {

        private final NBTTagCompound managerCompound;

        public DeSerializationBiConsumer(NBTTagCompound managerCompound) {
            this.managerCompound = managerCompound;
        }

        /**
         * Performs this operation on the given arguments.
         *
         * @param tCapability the first input argument
         * @param t           the first input argument
         */
        @Override
        public void accept(Capability<T> tCapability, T t) {
            if (managerCompound.hasKey(tCapability.getName())) {
                tCapability.readNBT(t, null, managerCompound.getTag(tCapability.getName()));
            }
        }
    }
}
