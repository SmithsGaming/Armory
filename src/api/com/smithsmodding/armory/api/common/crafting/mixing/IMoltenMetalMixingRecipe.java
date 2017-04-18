package com.smithsmodding.armory.api.common.crafting.mixing;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Interface describing a recipe for molten metal mixing.
 */
public interface IMoltenMetalMixingRecipe extends IForgeRegistryEntry<IMoltenMetalMixingRecipe> {

    /**
     * Method to get the processing time required for this recipe.
     * @return The processing time for this recipe.
     */
    @Nonnull
    Integer getProcessingTime();

    /**
     * Setter for the processing time.
     * @param time The new processing time.
     * @return The instance this was called upon.
     */
    IMoltenMetalMixingRecipe setProcessingTime(@Nonnull Integer time);

    /**
     * Method to get the primary solid input stack.
     * @return The primary solid input stack.
     */
    ItemStack getPrimarySolidInputStack();

    /**
     * Method used to set the primary solid input stack.
     * @param primarySolidInputStack The new primary solid input stack.
     * @return The primary solid input stack.
     */
    IMoltenMetalMixingRecipe setPrimarySolidInputStack(@Nonnull ItemStack primarySolidInputStack);

    /**
     * Method to get the secondary solid input stack.
     * @return The secondary solid input stack.
     */
    ItemStack getSecondarySolidInputStack();

    /**
     * Method used to set the secondary solid input stack.
     * @param secondarySolidInputStack The new secondary solid input stack.
     * @return The secondary solid input stack.
     */
    IMoltenMetalMixingRecipe setSecondarySolidInputStack(@Nonnull ItemStack secondarySolidInputStack);

    /**
     * Method to get the tertiary solid input stack.
     * @return The tertiary solid input stack.
     */
    ItemStack getTertiarySolidInputStack();

    /**
     * Method used to set the tertiary solid input stack.
     * @param tertiarySolidInputStack The new tertiary solid input stack.
     * @return The tertiary solid input stack.
     */
    IMoltenMetalMixingRecipe setTertiarySolidInputStack(@Nonnull ItemStack tertiarySolidInputStack);
    
    /**
     * Method to get the input Stack on the left side.
     * @return The input stack on the left Side.
     */
    @Nonnull
    FluidStack getLeftInputStack();

    /**
     * Setter for the left Input Stack.
     * @param inputStack  The new left Input stack.
     * @return The instance this was called upon.
     */
    @Nonnull
    IMoltenMetalMixingRecipe setLeftInputStack(@Nonnull FluidStack inputStack);
    
    /**
     * Method to get the input Stack on the right side.
     * @return The input stack on the right Side.
     */
    @Nonnull
    FluidStack getRightInputStack();

    /**
     * Setter for the right Input Stack.
     * @param inputStack  The new right Input stack.
     * @return The instance this was called upon.
     */
    @Nonnull
    IMoltenMetalMixingRecipe setRightInputStack(@Nonnull FluidStack inputStack);
    
    /**
     * Method to get the exemplary output Stack, used for things like JEI.
     * @return The exemplary output Stack.
     */
    @Nonnull
    FluidStack getExemplaryOutputStack();

    /**
     * Setter for the exemplary output Stack.
     * @param outputStack  The new exemplary output stack.
     * @return The instance this was called upon.
     */
    @Nonnull
    IMoltenMetalMixingRecipe setExemplaryOutputStack(@Nonnull FluidStack outputStack);
    
    /**
     * Method to get the real output stack for a given input.
     * @param leftInput The left input Stack.
     * @param rightInput The right input Stack.
     * @return null when the recipe is invalid, or the result if it is valid.
     */
    @Nullable
    FluidStack getRealOutputStack(@Nonnull FluidStack leftInput, @Nonnull FluidStack rightInput);

    /**
     * Method gets called after production to determine how much of the left input to use.
     * @return how much of the left input to use
     */
    @Nonnull
    Integer getUsageLeftInput();

    /**
     * Method gets called after production to determine how much of the right input to use.
     * @return how much of the right input to use
     */
    @Nonnull
    Integer getUsageRightInput();

}
