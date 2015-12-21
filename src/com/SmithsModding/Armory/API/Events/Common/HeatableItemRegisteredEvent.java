package com.SmithsModding.Armory.API.Events.Common;

import com.SmithsModding.Armory.API.Materials.IArmorMaterial;
import com.SmithsModding.SmithsCore.Common.Event.SmithsCoreEvent;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

/**
 * Created by marcf on 12/21/2015.
 */
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

    public ItemStack getHeatableStack() {
        return heatableStack;
    }

    public void setMoltenStack(FluidStack moltenStack) {
        this.moltenStack = moltenStack;
    }

    public void setHeatableStack(ItemStack heatableStack) {
        this.heatableStack = heatableStack;
    }
}
