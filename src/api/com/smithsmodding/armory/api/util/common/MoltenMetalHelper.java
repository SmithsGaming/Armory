package com.smithsmodding.armory.api.util.common;

import com.smithsmodding.armory.api.fluid.IMoltenMetalProvider;
import com.smithsmodding.armory.api.fluid.IMoltenMetalRequester;
import net.minecraftforge.fluids.FluidStack;

/**
 * Author Orion (Created on: 05.08.2016)
 */
public class MoltenMetalHelper {

    public static void transferMaxAmount(IMoltenMetalProvider provider, IMoltenMetalRequester requester) {
        transferAmount(provider, requester, Integer.MAX_VALUE);
    }

    public static void transferAmount(IMoltenMetalProvider provider, IMoltenMetalRequester requester, int maxAmount) {
        FluidStack simmedDrain = provider.drainNext(maxAmount, false);
        if (simmedDrain == null)
            return;

        int simmedUsage = requester.fillNext(simmedDrain, false);

        if (simmedDrain.amount < simmedUsage)
            simmedUsage = simmedDrain.amount;
        if (simmedUsage < simmedDrain.amount)
            simmedDrain.amount = simmedUsage;

        provider.drainNext(simmedUsage, true);
        requester.fillNext(simmedDrain, true);
    }
}
