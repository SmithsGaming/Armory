package com.smithsmodding.Armory.API.Events.Common;

import com.smithsmodding.Armory.API.Materials.*;
import com.smithsmodding.smithscore.common.events.*;
import net.minecraft.item.*;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fml.common.eventhandler.*;

/**
 * Created by marcf on 12/21/2015.
 */
@Cancelable
public class HeatableItemRegisteredEvent extends SmithsCoreEvent
{
    private final boolean oreDictionaryAddition;
    private final IArmorMaterial material;
    private final String internalType;

    private FluidStack moltenStack;
    private ItemStack heatableStack;


    public HeatableItemRegisteredEvent(IArmorMaterial material, String internalType, ItemStack stack, FluidStack moltenStack, boolean oreDictionaryAddition) {
        this.oreDictionaryAddition = oreDictionaryAddition;
        this.material = material;
        this.internalType = internalType;

        setMoltenStack(moltenStack);
        setHeatableStack(stack);
    }

    public boolean isOreDictionaryAddition() {
        return oreDictionaryAddition;
    }

    public IArmorMaterial getMaterial() {
        return material;
    }

    public String getInternalType() {
        return internalType;
    }

    public FluidStack getMoltenStack() {
        return moltenStack;
    }

    public void setMoltenStack(FluidStack moltenStack) {
        this.moltenStack = moltenStack;
    }

    public ItemStack getHeatableStack () {
        return heatableStack;
    }

    public void setHeatableStack(ItemStack heatableStack) {
        this.heatableStack = heatableStack;
    }
}
