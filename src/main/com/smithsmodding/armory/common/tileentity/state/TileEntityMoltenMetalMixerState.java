package com.smithsmodding.armory.common.tileentity.state;

import com.smithsmodding.armory.api.IArmoryAPI;
import com.smithsmodding.armory.api.common.crafting.mixing.IMoltenMetalMixingRecipe;
import com.smithsmodding.armory.api.util.references.References;
import com.smithsmodding.armory.common.tileentity.moltenmetal.InputFilterableMoltenMetalTank;
import com.smithsmodding.armory.common.tileentity.moltenmetal.MoltenMetalTank;
import com.smithsmodding.smithscore.common.tileentity.TileEntitySmithsCore;
import com.smithsmodding.smithscore.common.tileentity.state.ITileEntityState;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

import static com.smithsmodding.armory.api.util.references.References.NBTTagCompoundData.TE.MoltenMetalMixer.*;


/**
 * Author Orion (Created on: 26.07.2016)
 */
public class TileEntityMoltenMetalMixerState implements ITileEntityState {

    @Nullable
    private IMoltenMetalMixingRecipe currentRecipe;

    @Nonnull
    private Integer currentProgress;

    @Nonnull
    private final InputFilterableMoltenMetalTank leftTank = new InputFilterableMoltenMetalTank(3 * References.General.FLUID_INGOT, this::canAcceptFluids);

    @Nonnull
    private final InputFilterableMoltenMetalTank rightTank = new InputFilterableMoltenMetalTank(3 * References.General.FLUID_INGOT, this::canAcceptFluids);

    @Nonnull
    private final MoltenMetalTank outputTank = new MoltenMetalTank(3 * References.General.FLUID_INGOT, 1);

    @Nonnull
    private EnumFacing facing = EnumFacing.NORTH;

    @Override
    public void onStateCreated(TileEntitySmithsCore tileEntitySmithsCore) {

    }

    @Override
    public void onStateUpdated() {

    }

    @Override
    public void onStateDestroyed() {

    }

    @Override
    public boolean requiresNBTStorage() {
        return true;
    }

    @Override
    public void readFromNBTTagCompound(NBTBase stateData) {
        if (!(stateData instanceof NBTTagCompound))
            return;

        NBTTagCompound compound = (NBTTagCompound) stateData;

        if (compound.hasKey(CURRENTRECIPE) && IArmoryAPI.Holder.getInstance().getRegistryManager().getMoltenMetalMixingRecipeRegistry().containsKey(new ResourceLocation(compound.getString(CURRENTRECIPE))))
            setCurrentRecipe(IArmoryAPI.Holder.getInstance().getRegistryManager().getMoltenMetalMixingRecipeRegistry().getValue(new ResourceLocation(compound.getString(CURRENTRECIPE))));
        else
            setCurrentRecipe(null);

        if (compound.hasKey(CURRENTPROGRESS) && getCurrentRecipe() != null)
            setCurrentProgress(compound.getInteger(CURRENTPROGRESS));
        else
            setCurrentProgress(0);

        setFacing(EnumFacing.getHorizontal(((NBTTagCompound) stateData).getInteger(FACING)));

    }

    @Nullable
    @Override
    public NBTBase writeToNBTTagCompound() {
        NBTTagCompound compound = new NBTTagCompound();

        if (getCurrentRecipe() != null) {
            compound.setString(CURRENTRECIPE, getCurrentRecipe().getRegistryName().toString());
            compound.setInteger(CURRENTPROGRESS, getCurrentProgress());
        }

        compound.setInteger(FACING, getFacing().getHorizontalIndex());

        return compound;
    }

    @Override
    public boolean requiresSynchronization() {
        return true;
    }

    @Override
    public void readFromSynchronizationCompound(NBTBase stateData) {
        readFromNBTTagCompound(stateData);
    }

    @Nullable
    @Override
    public NBTBase writeToSynchronizationCompound() {
        return writeToNBTTagCompound();
    }

    @Nullable
    public IMoltenMetalMixingRecipe getCurrentRecipe() {
        return currentRecipe;
    }

    @Nullable
    public void setCurrentRecipe(@Nullable IMoltenMetalMixingRecipe currentRecipe) {
        this.currentRecipe = currentRecipe;
        this.currentProgress = 0;
    }

    @Nonnull
    public Integer getCurrentProgress() {
        return currentProgress;
    }

    public void setCurrentProgress(@Nonnull Integer currentProgress) {
        this.currentProgress = currentProgress;
    }

    @Nonnull
    public InputFilterableMoltenMetalTank getLeftTank() {
        return leftTank;
    }

    @Nonnull
    public InputFilterableMoltenMetalTank getRightTank() {
        return rightTank;
    }

    @Nonnull
    public MoltenMetalTank getOutputTank() {
        return outputTank;
    }

    public Boolean canAcceptFluids(@Nullable FluidStack stack) {
        if (stack == null)
            return Boolean.FALSE;

        return getCurrentRecipe() == null;
    }

    @Nonnull
    public EnumFacing getFacing() {
        return facing;
    }

    public void setFacing(@Nonnull EnumFacing facing) {
        this.facing = facing;
    }
}
