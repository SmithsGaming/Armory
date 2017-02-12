package com.smithsmodding.armory.common.tileentity;

import com.smithsmodding.armory.api.materials.IArmorMaterial;
import com.smithsmodding.armory.common.factory.HeatedItemFactory;
import com.smithsmodding.armory.common.item.ItemHeatedItem;
import com.smithsmodding.armory.common.registry.HeatableItemRegistry;
import com.smithsmodding.armory.common.tileentity.guimanagers.TileEntityForgeBaseGuiManager;
import com.smithsmodding.armory.common.tileentity.state.IForgeFuelDataContainer;
import com.smithsmodding.armory.common.tileentity.state.TileEntityForgeBaseState;
import com.smithsmodding.smithscore.common.inventory.IItemStorage;
import com.smithsmodding.smithscore.common.tileentity.TileEntitySmithsCore;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ITickable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Author Orion (Created on: 23.06.2016)
 */
public abstract class TileEntityForgeBase<S extends TileEntityForgeBaseState, G extends TileEntityForgeBaseGuiManager> extends TileEntitySmithsCore<S, G> implements IItemStorage, ITickable {

    @Override
    public void update() {
        if (isRemote())
            return;

        IForgeFuelDataContainer fuelData = getFuelData();
        S localData = getState();

        if (fuelData == null)
            return;

        localData.setLastTemp(localData.getCurrentTemp());
        fuelData.setBurning(false);

        heatFurnace(fuelData, localData);

        fuelData.setBurning(fuelData.getBurningTicksLeftOnCurrentFuel() >= 1F);

        heatIngots(fuelData, localData);

        localData.setLastChange(localData.getCurrentTemp() - localData.getLastTemp());

        if (!worldObj.isRemote) {
            this.markDirty();
        }
    }

    @Nullable
    public abstract IForgeFuelDataContainer getFuelData();

    public void heatFurnace(@NotNull IForgeFuelDataContainer fuelData, @NotNull S localData) {
        calculateHeatTerms(localData);

        localData.setLastChange(0F);

        if (fuelData.getBurningTicksLeftOnCurrentFuel() >= 1F) {
            fuelData.changeBurningTicksLeftOnCurrentFuel(-1);
            localData.addLastPositiveHeatTermToChange();
        }

        if (fuelData.getBurningTicksLeftOnCurrentFuel() < 1F) {
            fuelData.setTotalBurningTicksOnCurrentFuel(0);

            for (int fuelStackIndex = 0; fuelStackIndex < getFuelStackAmount(); fuelStackIndex++) {

                if (getFuelStack(fuelStackIndex) == null) {
                    continue;
                }

                ItemStack fuelStack = getFuelStack(fuelStackIndex);

                //Check if the stack is a valid Fuel in the Furnace
                if ((fuelStack != null) && (TileEntityFurnace.isItemFuel(fuelStack))) {
                    --fuelStack.stackSize;

                    fuelData.changeTotalBurningTicksOnCurrentFuel(TileEntityFurnace.getItemBurnTime(fuelStack));

                    if (fuelStack.stackSize == 0) {
                        setFuelStack(fuelStackIndex, fuelStack.getItem().getContainerItem(fuelStack));
                    }

                }

            }

            fuelData.resetBurningTicksLeftOnCurrentFuel();
        }

        localData.setLastChange(localData.getLastChange() * (1 - localData.getHeatedPercentage()));
    }

    public boolean heatIngots(IForgeFuelDataContainer fuelData, @NotNull S localData) {

        if ((localData.getLastChange() == 0F) && (localData.getCurrentTemp() <= 20F) || (getInsertedIngotAmount() == 0)) {
            return true;
        }

        localData.addLastChangeToCurrentTemp();

        if (localData.getCurrentTemp() > 20F) {
            localData.setCurrentTemp(localData.getCurrentTemp() + (localData.getLastNegativeTerm() * localData.getHeatedPercentage()));
        }

        for (int ingotStackIndex = 0; ingotStackIndex < getTotalPossibleIngotAmount(); ingotStackIndex++) {
            if (getIngotStack(ingotStackIndex) == null) {
                continue;
            }

            if ((localData.getCurrentTemp() > 20F) && !(getIngotStack(ingotStackIndex).getItem() instanceof ItemHeatedItem) && HeatableItemRegistry.getInstance().isHeatable(getIngotStack(ingotStackIndex))) {
                setIngotStack(ingotStackIndex, HeatedItemFactory.getInstance().convertToHeatedIngot(getIngotStack(ingotStackIndex)));
            }

            ItemStack stack = getIngotStack(ingotStackIndex);
            IArmorMaterial material = HeatableItemRegistry.getInstance().getMaterialFromHeatedStack(stack);

            float tCurrentStackTemp = HeatableItemRegistry.getInstance().getItemTemperature(stack);
            float tCurrentStackCoefficient = material.getHeatCoefficient();

            float tSourceDifference = (localData.getLastNegativeTerm() / getSourceEfficiencyIndex()) - tCurrentStackCoefficient;
            float tTargetDifference = tCurrentStackCoefficient;


            if (tCurrentStackTemp < 20F) {
                setIngotStack(ingotStackIndex, HeatedItemFactory.getInstance().convertToCooledIngot(stack));
            } else if (tCurrentStackTemp <= localData.getCurrentTemp()) {
                localData.setCurrentTemp(localData.getCurrentTemp() + tSourceDifference);
                HeatableItemRegistry.getInstance().setItemTemperature(stack, HeatableItemRegistry.getInstance().getItemTemperature(stack) + tTargetDifference);
            } else if (HeatableItemRegistry.getInstance().getItemTemperature(stack) > localData.getCurrentTemp()) {
                localData.setCurrentTemp(localData.getCurrentTemp() + tTargetDifference);
                HeatableItemRegistry.getInstance().setItemTemperature(stack, HeatableItemRegistry.getInstance().getItemTemperature(stack) + tSourceDifference);
            }

        }

        return true;
    }

    protected int getInsertedIngotAmount() {
        int amount = 0;

        for (int index = 0; index < getTotalPossibleIngotAmount(); index++) {
            if (getIngotStack(index) == null) {
                continue;
            }

            amount++;
        }

        return amount;
    }

    protected abstract void calculateHeatTerms(S localData);

    protected abstract int getFuelStackAmount();

    protected abstract ItemStack getFuelStack(int fuelStackIndex);

    protected abstract void setFuelStack(int fuelStackIndex, ItemStack fuelStack);

    protected abstract int getTotalPossibleIngotAmount();

    protected abstract ItemStack getIngotStack(int ingotStackIndex);

    protected abstract void setIngotStack(int ingotStackIndex, ItemStack ingotStack);

    protected abstract float getSourceEfficiencyIndex();

    protected void onFuelFound() {
    }

    protected void onFuelLost() {
    }

    @Override
    public ItemStack decrStackSize(int index, int amount) {
        ItemStack stack = getStackInSlot(index);
        if (stack == null) {
            return stack;
        }

        if (stack.stackSize < amount) {
            setInventorySlotContents(index, null);
        } else {
            ItemStack returnStack = stack.splitStack(amount);

            if (stack.stackSize == 0) {
                setInventorySlotContents(index, null);
            }

            return returnStack;
        }

        return stack;
    }
}
