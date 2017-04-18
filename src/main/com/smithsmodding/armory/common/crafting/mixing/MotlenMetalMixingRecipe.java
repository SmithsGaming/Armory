package com.smithsmodding.armory.common.crafting.mixing;

import com.smithsmodding.armory.api.IArmoryAPI;
import com.smithsmodding.armory.api.common.crafting.mixing.IMoltenMetalMixingRecipe;
import com.smithsmodding.armory.api.common.material.core.IMaterial;
import com.smithsmodding.armory.api.util.common.MaterialHelper;
import com.smithsmodding.armory.api.util.references.References;
import com.smithsmodding.armory.api.common.fluid.FluidMoltenMetal;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by marcf on 3/9/2017.
 */
public class MotlenMetalMixingRecipe extends IForgeRegistryEntry.Impl<IMoltenMetalMixingRecipe> implements IMoltenMetalMixingRecipe {

    private String oreDicLeft;
    private int amountLeft;
    
    private String oreDicRight;
    private int amountRight;
    
    private String oreDicOut;
    private int amountOut;
    
    private Integer processingTime;

    private ItemStack primarySolidInputStack;
    private ItemStack secondarySolidInputStack;
    private ItemStack tertiarySolidInputStack;

    /**
     * Method to get the processing time required for this recipe.
     *
     * @return The processing time for this recipe.
     */
    @Nonnull
    @Override
    public Integer getProcessingTime() {
        return processingTime;
    }

    /**
     * Setter for the processing time.
     *
     * @param time The new processing time.
     *
     * @return The instance this was called upon.
     */
    @Override
    public IMoltenMetalMixingRecipe setProcessingTime(@Nonnull final Integer time) {
        this.processingTime = time;
        return this;
    }

    /**
     * Method to get the primary solid input stack.
     *
     * @return The primary solid input stack.
     */
    @Override
    public ItemStack getPrimarySolidInputStack() {
        return primarySolidInputStack;
    }

    /**
     * Method used to set the primary solid input stack.
     *
     * @param primarySolidInputStack The new primary solid input stack.
     *
     * @return The primary solid input stack.
     */
    @Override
    public IMoltenMetalMixingRecipe setPrimarySolidInputStack(@Nonnull ItemStack primarySolidInputStack) {
        this.primarySolidInputStack = primarySolidInputStack;
        return this;
    }

    /**
     * Method to get the secondary solid input stack.
     *
     * @return The secondary solid input stack.
     */
    @Override
    public ItemStack getSecondarySolidInputStack() {
        return secondarySolidInputStack;
    }

    /**
     * Method used to set the secondary solid input stack.
     *
     * @param secondarySolidInputStack The new secondary solid input stack.
     *
     * @return The secondary solid input stack.
     */
    @Override
    public IMoltenMetalMixingRecipe setSecondarySolidInputStack(@Nonnull ItemStack secondarySolidInputStack) {
        this.secondarySolidInputStack = secondarySolidInputStack;
        return this;
    }

    /**
     * Method to get the tertiary solid input stack.
     *
     * @return The tertiary solid input stack.
     */
    @Override
    public ItemStack getTertiarySolidInputStack() {
        return tertiarySolidInputStack;
    }

    /**
     * Method used to set the tertiary solid input stack.
     *
     * @param tertiarySolidInputStack The new tertiary solid input stack.
     *
     * @return The tertiary solid input stack.
     */
    @Override
    public IMoltenMetalMixingRecipe setTertiarySolidInputStack(@Nonnull ItemStack tertiarySolidInputStack) {
        this.tertiarySolidInputStack = tertiarySolidInputStack;
        return this;
    }

    /**
     * Method to get the input Stack on the left side.
     *
     * @return The input stack on the left Side.
     */
    @Nonnull
    @Override
    public FluidStack getLeftInputStack() {
        IMaterial material = MaterialHelper.getMaterialFromOreDicName(oreDicLeft);

        return IArmoryAPI.Holder.getInstance().getHelpers().getFactories().getHeatedItemFactory().generateFluid(material, amountLeft);
    }

    /**
     * Setter for the left Input Stack.
     *
     * @param inputStack The new left Input stack.
     *
     * @return The instance this was called upon.
     */
    @Nonnull
    @Override
    public MotlenMetalMixingRecipe setLeftInputStack(@Nonnull FluidStack inputStack) {
        if (!(inputStack.getFluid() instanceof FluidMoltenMetal) || inputStack.tag == null || !inputStack.tag.hasKey(References.NBTTagCompoundData.Fluids.MoltenMetal.MATERIAL))
            throw new IllegalArgumentException("Unsupported Fluid.");

        oreDicLeft = IArmoryAPI.Holder.getInstance().getRegistryManager().getCombinedMaterialRegistry()
                .getValue(new ResourceLocation(inputStack.tag.getString(References.NBTTagCompoundData.Fluids.MoltenMetal.MATERIAL))).getWrapped().getOreDictionaryIdentifier();
        
        amountLeft = inputStack.amount;
        
        return this;
    }

    /**
     * Method used to set the internal data directly.
     * @param oreDicLeft The oredic name of the left fluid.
     * @param amountLeft The minimal amount of the left fluid
     * @return The instance this was called upon.
     */
    @Nonnull
    public MotlenMetalMixingRecipe setLeftInputData(@Nonnull String oreDicLeft, @Nonnull Integer amountLeft) {
        this.oreDicLeft = oreDicLeft;
        this.amountLeft = amountLeft;
        
        return this;
    }

    /**
     * Method to get the input Stack on the right side.
     *
     * @return The input stack on the right Side.
     */
    @Nonnull
    @Override
    public FluidStack getRightInputStack() {
        IMaterial material = MaterialHelper.getMaterialFromOreDicName(oreDicRight);
                
        return IArmoryAPI.Holder.getInstance().getHelpers().getFactories().getHeatedItemFactory().generateFluid(material, amountRight);
    }

    /**
     * Setter for the right Input Stack.
     *
     * @param inputStack The new right Input stack.
     *
     * @return The instance this was called upon.
     */
    @Nonnull
    @Override
    public MotlenMetalMixingRecipe setRightInputStack(@Nonnull FluidStack inputStack) {
        if (!(inputStack.getFluid() instanceof FluidMoltenMetal) || inputStack.tag == null || !inputStack.tag.hasKey(References.NBTTagCompoundData.Fluids.MoltenMetal.MATERIAL))
            throw new IllegalArgumentException("Unsupported Fluid.");

        oreDicRight = IArmoryAPI.Holder.getInstance().getRegistryManager().getCombinedMaterialRegistry()
                .getValue(new ResourceLocation(inputStack.tag.getString(References.NBTTagCompoundData.Fluids.MoltenMetal.MATERIAL))).getWrapped().getOreDictionaryIdentifier();

        amountRight = inputStack.amount;
        
        return this;
    }

    /**
     * Method used to set the internal data directly.
     * @param oreDicRight The oredic name of the right fluid.
     * @param amountRight The minimal amount of the right fluid
     * @return The instance this was called upon.
     */
    @Nonnull
    public MotlenMetalMixingRecipe setRightInputData(@Nonnull String oreDicRight, @Nonnull Integer amountRight) {
        this.oreDicRight = oreDicRight;
        this.amountRight = amountRight;

        return this;
    }


    /**
     * Method to get the exemplary output Stack, used for things like JEI.
     *
     * @return The exemplary output Stack.
     */
    @Nonnull
    @Override
    public FluidStack getExemplaryOutputStack() {
        IMaterial material = MaterialHelper.getMaterialFromOreDicName(oreDicOut);

        return IArmoryAPI.Holder.getInstance().getHelpers().getFactories().getHeatedItemFactory().generateFluid(material, amountOut);
    }

    /**
     * Setter for the exemplary output Stack.
     *
     * @param outputStack The new exemplary output stack.
     *
     * @return The instance this was called upon.
     */
    @Nonnull
    @Override
    public MotlenMetalMixingRecipe setExemplaryOutputStack(@Nonnull FluidStack outputStack) {
        if (!(outputStack.getFluid() instanceof FluidMoltenMetal) || outputStack.tag == null || !outputStack.tag.hasKey(References.NBTTagCompoundData.Fluids.MoltenMetal.MATERIAL))
            throw new IllegalArgumentException("Unsupported Fluid.");

        oreDicOut = IArmoryAPI.Holder.getInstance().getRegistryManager().getCombinedMaterialRegistry()
                .getValue(new ResourceLocation(outputStack.tag.getString(References.NBTTagCompoundData.Fluids.MoltenMetal.MATERIAL))).getWrapped().getOreDictionaryIdentifier();

        amountOut = outputStack.amount;

        return this;
    }

    /**
     * Method used to set the internal data directly.
     * @param oreDicOutput The oredic name of the output fluid.
     * @param amountOutput The minimal amount of the output fluid
     * @return The instance this was called upon.
     */
    @Nonnull
    public MotlenMetalMixingRecipe setExamplaryOutputData(@Nonnull String oreDicOutput, @Nonnull Integer amountOutput) {
        this.oreDicOut = oreDicOutput;
        this.amountOut = amountOutput;

        return this;
    }


    /**
     * Method to get the real output stack for a given input.
     *
     * @param leftInput  The left input Stack.
     * @param rightInput The right input Stack.
     *
     * @return null when the recipe is invalid, or the result if it is valid.
     */
    @Nullable
    @Override
    public FluidStack getRealOutputStack(@Nonnull FluidStack leftInput, @Nonnull FluidStack rightInput) {
        //Check left:
        if (!(leftInput.getFluid() instanceof FluidMoltenMetal) || leftInput.tag == null || !leftInput.tag.hasKey(References.NBTTagCompoundData.Fluids.MoltenMetal.MATERIAL))
            return null;

        //Check right:
        if (!(rightInput.getFluid() instanceof FluidMoltenMetal) || rightInput.tag == null || !rightInput.tag.hasKey(References.NBTTagCompoundData.Fluids.MoltenMetal.MATERIAL))
            return null;

        if (!IArmoryAPI.Holder.getInstance().getRegistryManager().getCombinedMaterialRegistry()
                .getValue(new ResourceLocation(leftInput.tag.getString(References.NBTTagCompoundData.Fluids.MoltenMetal.MATERIAL)))
                .getWrapped().getOreDictionaryIdentifier().equals(oreDicLeft) || leftInput.amount < amountLeft)
            return null;

        if (!IArmoryAPI.Holder.getInstance().getRegistryManager().getCombinedMaterialRegistry()
                .getValue(new ResourceLocation(rightInput.tag.getString(References.NBTTagCompoundData.Fluids.MoltenMetal.MATERIAL)))
                .getWrapped().getOreDictionaryIdentifier().equals(oreDicRight) || rightInput.amount < amountRight)
            return null;
        
        return new FluidStack(getExemplaryOutputStack(), getExemplaryOutputStack().amount);
    }

    /**
     * Method gets called after production to determine how much of the left input to use.
     *
     * @return how much of the left input to use
     */
    @Nonnull
    @Override
    public Integer getUsageLeftInput() {
        return amountLeft;
    }

    /**
     * Method gets called after production to determine how much of the right input to use.
     *
     * @return how much of the right input to use
     */
    @Nonnull
    @Override
    public Integer getUsageRightInput() {
        return amountRight;
    }
}
