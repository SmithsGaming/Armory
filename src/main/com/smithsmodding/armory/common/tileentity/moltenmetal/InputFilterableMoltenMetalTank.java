package com.smithsmodding.armory.common.tileentity.moltenmetal;

import net.minecraftforge.fluids.FluidStack;

import java.util.List;
import java.util.function.Function;

/**
 * Created by marcf on 2/19/2017.
 */
public class InputFilterableMoltenMetalTank extends MoltenMetalTank {

    private final Function<FluidStack, Boolean> inputPredicate;
    
    public InputFilterableMoltenMetalTank(int capacity, Function<FluidStack, Boolean> inputPredicate) {
        super(capacity, 1);
        this.inputPredicate = inputPredicate;
    }

    public InputFilterableMoltenMetalTank(int capacity, Function<FluidStack, Boolean> inputPredicate, FluidStack... fluidStacks) {
        super(capacity, 1, fluidStacks);
        this.inputPredicate = inputPredicate;
    }

    public InputFilterableMoltenMetalTank(int capacity, List<FluidStack> fluidStacks, Function<FluidStack, Boolean> inputPredicate) {
        super(capacity, 1, fluidStacks);
        this.inputPredicate = inputPredicate;
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {
        if (!inputPredicate.apply(resource))
            return 0;

        return super.fill(resource, doFill);
    }
}
