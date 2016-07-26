package com.smithsmodding.armory.api.util.references;

import com.smithsmodding.armory.api.fluid.IMoltenMetalProvider;
import com.smithsmodding.armory.api.fluid.IMoltenMetalRequester;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import java.util.concurrent.Callable;

/**
 * Author Orion (Created on: 25.07.2016)
 */
public class ModCapabilities {

    @CapabilityInject(IMoltenMetalProvider.class)
    public static Capability<IMoltenMetalProvider> MOLTEN_METAL_PROVIDER_CAPABILITY;

    @CapabilityInject(IMoltenMetalRequester.class)
    public static Capability<IMoltenMetalRequester> MOLTEN_METAL_REQUESTER_CAPABILITY;

    static {
        CapabilityManager.INSTANCE.register(IMoltenMetalProvider.class, new Capability.IStorage<IMoltenMetalProvider>() {
            @Override
            public NBTBase writeNBT(Capability<IMoltenMetalProvider> capability, IMoltenMetalProvider instance, EnumFacing side) {
                return new NBTTagCompound();
            }

            @Override
            public void readNBT(Capability<IMoltenMetalProvider> capability, IMoltenMetalProvider instance, EnumFacing side, NBTBase nbt) {

            }
        }, new Callable<IMoltenMetalProvider>() {
            @Override
            public IMoltenMetalProvider call() throws Exception {
                return null;
            }
        });

        CapabilityManager.INSTANCE.register(IMoltenMetalRequester.class, new Capability.IStorage<IMoltenMetalRequester>() {
            @Override
            public NBTBase writeNBT(Capability<IMoltenMetalRequester> capability, IMoltenMetalRequester instance, EnumFacing side) {
                return new NBTTagCompound();
            }

            @Override
            public void readNBT(Capability<IMoltenMetalRequester> capability, IMoltenMetalRequester instance, EnumFacing side, NBTBase nbt) {

            }
        }, new Callable<IMoltenMetalRequester>() {
            @Override
            public IMoltenMetalRequester call() throws Exception {
                return null;
            }
        });
    }
}
