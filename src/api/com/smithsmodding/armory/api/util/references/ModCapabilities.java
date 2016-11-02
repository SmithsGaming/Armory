package com.smithsmodding.armory.api.util.references;

import com.smithsmodding.armory.api.fluid.IMoltenMetalAcceptor;
import com.smithsmodding.armory.api.fluid.IMoltenMetalProvider;
import com.smithsmodding.smithscore.util.common.capabilities.NullFactory;
import com.smithsmodding.smithscore.util.common.capabilities.NullStorage;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;


/**
 * Author Orion (Created on: 25.07.2016)
 */
public class ModCapabilities {

    @CapabilityInject(IMoltenMetalAcceptor.class)
    public static Capability<IMoltenMetalAcceptor> MOD_MOLTENMETAL_ACCEPTOR_CAPABILITY;

    @CapabilityInject(IMoltenMetalProvider.class)
    public static Capability<IMoltenMetalProvider> MOD_MOLTENMETAL_PROVIDER_CAPABILITY;
    

    static {
        CapabilityManager.INSTANCE.register(IMoltenMetalAcceptor.class, new NullStorage<IMoltenMetalAcceptor>(), new NullFactory<IMoltenMetalAcceptor>());
        CapabilityManager.INSTANCE.register(IMoltenMetalProvider.class, new NullStorage<IMoltenMetalProvider>(), new NullFactory<IMoltenMetalProvider>());
    }

}
