package com.smithsmodding.armory.common.tileentity;

import com.smithsmodding.armory.api.IArmoryAPI;
import com.smithsmodding.armory.api.common.crafting.mixing.IMoltenMetalMixingRecipe;
import com.smithsmodding.armory.api.util.common.ItemStackHelper;
import com.smithsmodding.armory.api.util.references.ModCapabilities;
import com.smithsmodding.armory.api.util.references.References;
import com.smithsmodding.armory.common.tileentity.guimanagers.TileEntityMoltenMetalMixerGuiManager;
import com.smithsmodding.armory.common.tileentity.state.TileEntityMoltenMetalMixerState;
import com.smithsmodding.smithscore.common.fluid.IFluidContainingEntity;
import com.smithsmodding.smithscore.common.inventory.IItemStorage;
import com.smithsmodding.smithscore.common.tileentity.TileEntitySmithsCore;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by marcf on 2/19/2017.
 */
public class TileEntityMoltenMetalMixer extends TileEntitySmithsCore<TileEntityMoltenMetalMixerState, TileEntityMoltenMetalMixerGuiManager> implements ITickable, IFluidContainingEntity, IItemStorage {

    public static final int CONSTANT_SOLIDMIXINGSLOTS = 3;

    @Nonnull
    private final ItemStack[] solidSlots = new ItemStack[CONSTANT_SOLIDMIXINGSLOTS];

    public TileEntityMoltenMetalMixer() {
        clearInventory();
    }

    @Nonnull
    @Override
    protected TileEntityMoltenMetalMixerGuiManager getInitialGuiManager() {
        return new TileEntityMoltenMetalMixerGuiManager(this);
    }

    @Nonnull
    @Override
    protected TileEntityMoltenMetalMixerState getInitialState() {
        return new TileEntityMoltenMetalMixerState();
    }

    /**
     * Getter for the Containers ID.
     * Used to identify the container over the network.
     * If this relates to TileEntities, it should contain a ID and a location based ID so that multiple instances
     * of this container matched up to different TileEntities can be separated.
     *
     * @return The ID of this Container Instance.
     */
    @Nonnull
    @Override
    public String getContainerID() {
        return References.InternalNames.TileEntities.MoltenMetalMixer + "-" + getLocation().toString();
    }

    private IFluidTank getTankForFacingAndSide(@Nullable EnumFacing side) {
        if (side == null || side.getAxis() == EnumFacing.Axis.Y)
            return null;

        EnumFacing blockFacing = getFacing();

        if (blockFacing == null || blockFacing == side)
            return null;

        if (blockFacing.getOpposite() == side)
            return getState().getOutputTank();

        if (blockFacing.getOpposite().rotateYCCW() == side)
            return getState().getLeftTank();

        return getState().getRightTank();
    }

    /**
     * Like the old updateEntity(), except more generic.
     */
    @Override
    public void update() {
        checkIfRecipeIsStillValid();
        findValidRecipe();

        if (getState().getCurrentRecipe() != null) {
            if (getState().getCurrentProgress() >= getState().getCurrentRecipe().getProcessingTime()) {
                FluidStack left = getState().getLeftTank().drain(Integer.MAX_VALUE, false);
                FluidStack right = getState().getRightTank().drain(Integer.MAX_VALUE, false);
                FluidStack output = getState().getCurrentRecipe().getRealOutputStack(left, right);

                getState().getOutputTank().fill(output, true);
                
                FluidStack usedLeft = new FluidStack(left, getState().getCurrentRecipe().getUsageLeftInput());
                FluidStack usedRight = new FluidStack(right, getState().getCurrentRecipe().getUsageRightInput());

                getState().getLeftTank().drain(usedLeft, true);
                getState().getRightTank().drain(usedRight, true);
            } else {
                getState().setCurrentProgress(getState().getCurrentProgress() + 1);
            }
        }

        this.markDirty();
    }

    private void checkIfRecipeIsStillValid() {
        if (getState().getCurrentRecipe() == null)
            return;

        if (!getState().getLeftTank().canDrain()) {
            getState().setCurrentRecipe(null);
            return;
        }

        if (!getState().getRightTank().canDrain()) {
            getState().setCurrentRecipe(null);
            return;
        }

        IMoltenMetalMixingRecipe recipe = getState().getCurrentRecipe();

        FluidStack currentLeft = getState().getLeftTank().drain(Integer.MAX_VALUE, false);
        FluidStack currentRight = getState().getRightTank().drain(Integer.MAX_VALUE, false);
        FluidStack currentOutput = getState().getOutputTank().drain(Integer.MAX_VALUE, false);

        FluidStack newOutput = recipe.getRealOutputStack(currentLeft, currentRight);

        if (newOutput == null || (currentOutput != null && !currentOutput.isFluidEqual(newOutput))) {
            getState().setCurrentRecipe(null);
        }
    }

    private void findValidRecipe() {
        if (getState().getCurrentRecipe() != null)
            return;

        if (!getState().getLeftTank().canDrain())
            return;

        if (!getState().getRightTank().canDrain())
            return;

        FluidStack currentLeft = getState().getLeftTank().drain(Integer.MAX_VALUE, false);
        FluidStack currentRight = getState().getRightTank().drain(Integer.MAX_VALUE, false);
        
        for(IMoltenMetalMixingRecipe currentRecipe : IArmoryAPI.Holder.getInstance().getRegistryManager().getMoltenMetalMixingRecipeRegistry()) {
            FluidStack output = currentRecipe.getRealOutputStack(currentLeft, currentRight);

            if (output == null)
                continue;

            if (getState().getOutputTank().fill(output, false) == 0)
                continue;

            getState().setCurrentRecipe(currentRecipe);
            return;
        }

        getState().setCurrentRecipe(null);
    }

    @Override
    public IFluidTank getTankForSide(@Nullable EnumFacing side) {
        return getTankForFacingAndSide(side);
    }

    @Override
    public int getTotalTankSizeOnSide(@Nullable EnumFacing side) {
        if (getTankForFacingAndSide(side) == null)
            return 0;

        return getTankForFacingAndSide(side).getCapacity();
    }

    @Override
    public int getTankContentsVolumeOnSide(@Nullable EnumFacing side) {
        if (getTankForFacingAndSide(side) == null)
            return 0;

        return getTankForFacingAndSide(side).getFluidAmount();
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, EnumFacing facing) {
        if (getTankForFacingAndSide(facing) == null) return super.hasCapability(capability, facing);

        if (capability == ModCapabilities.MOD_MOLTENMETAL_PROVIDER_CAPABILITY || (capability == ModCapabilities.MOD_MOLTENMETAL_ACCEPTOR_CAPABILITY && facing != getFacing().getOpposite()))
            return true;

        return false;
    }

    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, EnumFacing facing) {
        if (getTankForFacingAndSide(facing) == null) return super.getCapability(capability, facing);

        if (capability == ModCapabilities.MOD_MOLTENMETAL_PROVIDER_CAPABILITY || (capability == ModCapabilities.MOD_MOLTENMETAL_ACCEPTOR_CAPABILITY && facing != getFacing().getOpposite()))
            return (T) getTankForFacingAndSide(facing);

        return null;
    }

    /**
     * Returns true if the Inventory is Empty.
     */
    @Override
    public boolean isEmpty() {
        return false;
    }

    /**
     * Returns the number of slots in the inventory.
     */
    @Override
    public int getSizeInventory() {
        return CONSTANT_SOLIDMIXINGSLOTS;
    }

    /**
     * Returns the stack in the given slot.
     *
     * @param index
     */
    @Nonnull
    @Override
    public ItemStack getStackInSlot(int index) {
        return ItemStack.EMPTY;
    }

    /**
     * Removes up to a specified number of items from an inventory slot and returns them in a new stack.
     *
     * @param index
     * @param count
     */
    @Nonnull
    @Override
    public ItemStack decrStackSize(int index, int count) {
        return ItemStack.EMPTY;
    }

    /**
     * Method to clear the complete inventory.
     */
    @Override
    public void clearInventory() {
        ItemStackHelper.InitializeItemStackArray(solidSlots);
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     *
     * @param index
     * @param stack
     */
    @Override
    public void setInventorySlotContents(int index, @Nonnull ItemStack stack) {
        solidSlots[index] = stack;
    }

    /**
     * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended.
     */
    @Override
    public int getInventoryStackLimit() {
        return ItemStackHelper.CONSTANT_ITEMSTACK_DEFAULT_MAX;
    }

    /**
     * For tile entities, ensures the chunk containing the tile entity is saved to disk later - the game won't think it
     * hasn't changed and skip it.
     */
    @Override
    public void markInventoryDirty() {
        this.markDirty();
    }

    /**
     * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot.
     *
     * @param index
     * @param stack
     */
    @Override
    public boolean isItemValidForSlot(int index, @Nonnull ItemStack stack) {
        IArmoryAPI.Holder.getInstance().getRegistryManager().getMoltenMetalMixingRecipeRegistry().forEach(recipe -> {
            if (ItemStackU)
        });

        return false;
    }

    @Nonnull
    public EnumFacing getFacing() {
        return getState().getFacing();
    }

    public void setFacing(@Nonnull EnumFacing facing) {
        this.getState().setFacing(facing);
    }
}
