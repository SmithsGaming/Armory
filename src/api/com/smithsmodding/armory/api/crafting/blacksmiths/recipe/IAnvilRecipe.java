package com.smithsmodding.armory.api.crafting.blacksmiths.recipe;

import com.smithsmodding.armory.api.crafting.blacksmiths.component.IAnvilRecipeComponent;
import com.smithsmodding.smithscore.common.tileentity.IWatchableTileEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by marcf on 1/11/2017.
 */
public interface IAnvilRecipe extends IForgeRegistryEntry<IAnvilRecipe> {

    /**
     * Method used to check if the current configuration matches this recipe.
     * @param craftingSlotContents The current contents of the crafting slot.
     * @param additionalSlotContents The current contents of the additional crafting slots.
     * @param hammerUsagesLeft The usages left on the hammer.
     * @param tongsUsagesLeft The usages left on the tongs.
     * @return True when matches, false when not.
     */
    boolean matchesRecipe(@Nonnull ItemStack[] craftingSlotContents, @Nonnull ItemStack[] additionalSlotContents, int hammerUsagesLeft, int tongsUsagesLeft);

    /**
     * Called when this recipe is crafted. Used to trigger specific events.
     * @param entity The TileEntity that crafted the recipe.
     */
    void onRecipeUsed(@Nonnull IWatchableTileEntity entity);

    /**
     * Getter for the recipe Component on a given slot index.
     * @param componentIndex The slot index to get the component for
     * @return The component for the slot. Or null if none should be required.
     */
    @Nullable IAnvilRecipeComponent getComponent(int componentIndex);

    /**
     * Getter for the recipe additional Component on a given slot index.
     * @param componentIndex The slot index to get the additional component for
     * @return The additional component for the slot. Or null if none should be required.
     */
    @Nullable IAnvilRecipeComponent getAdditionalComponent(int componentIndex);

    /**
     * Setter for the recipe component on a given slot index.
     * @param slotIndex The slot index to set the component for.
     * @param component The component to set. Or null to clear.
     * @return The instance this method was called on.
     */
    @Nonnull IAnvilRecipe setCraftingSlotContent(int slotIndex, @Nullable IAnvilRecipeComponent component);

    /**
     * Setter for the recipe additional component on a given slot index.
     * @param slotIndex The slot index to set the additional component for.
     * @param component The additional component to set. Or null to clear.
     * @return The instance this method was called on.
     */
    @Nonnull IAnvilRecipe setAdditionalCraftingSlotContent(int slotIndex, @Nullable IAnvilRecipeComponent component);

    /**
     * Method to set the result of this Recipe.
     * @param result The result.
     * @return The instance this method was called on.
     */
    @Nonnull IAnvilRecipe setResult(@Nonnull ItemStack result);

    /**
     * Method to set the amount of progress that is required to complete the crafting.
     * @param newProgress The amount required
     * @return The instance this method was called on.
     */
    @Nonnull IAnvilRecipe setProgress(int newProgress);

    /**
     * Method to set the amount of usages left on the tongs in the anvil required to craft this recipe
     * @param newUsage The amount left.
     * @return The instance this method was called on.
     */
    @Nonnull IAnvilRecipe setTongUsage(int newUsage);

    /**
     * Method to set the amount of usages left on the hammer in the anvil required to craft this recipe
     * @param newUsage The amount left.
     * @return The instance this method was called on.
     */
    @Nonnull IAnvilRecipe setHammerUsage(int newUsage);

    /**
     * Method to make the recipe shapeless.
     * @return The instance this method was called on.
     */
    @Nonnull IAnvilRecipe setShapeLess();

    /**
     * Method to get the result of the recipe for the given inputs.
     * @param craftingSlotContents The standard crafting slot inputs.
     * @param additionalSlotContents The additional crafting slot inputs.
     * @return The result stack for a given input.
     * @throws IllegalArgumentException thrown when the input stacks produce nothing.
     */
    @Nonnull ItemStack getResult(@Nonnull ItemStack[] craftingSlotContents, @Nonnull ItemStack[] additionalSlotContents) throws IllegalArgumentException;

    /**
     * Getter to check if this recipe is shapeless.
     * @return True when shapeless, false when not.
     */
    boolean isShapeless();

    /**
     * Getter for all components.
     * @return An array with all components for each slot.
     */
    @Nonnull IAnvilRecipeComponent[] getComponents();

    /**
     * Getter for all additional components.
     * @return An array with all additional components for each slot.
     */
    @Nonnull IAnvilRecipeComponent[] getAdditionalComponents();

    /**
     * Method to get the amount of progress that is required to complete the crafting.
     * @return The amount of ticks required to craft this recipe.
     */
    int getProgress();

    /**
     * Method to set the amount of usages left on the hammer in the anvil required to craft this recipe
     * @return the amount of usages left on the hammer in the anvil required to craft this recipe
     */
    int getHammerUsage();

    /**
     * Method used to check whether or not this recipe requires a Hammer to be present in the Anvil.
     * @return True when a Hammer is required, false or not.
     */
    boolean getUsesHammer();

    /**
     * Method to set the amount of usages left on the tongs in the anvil required to craft this recipe
     * @return the amount of usages left on the tongs in the anvil required to craft this recipe
     */
    int getTongsUsage();

    /**
     * Method used to check whether or not this recipe requires a pair of Tongs to be present in the Anvil.
     * @return True when a pair of Tongs is required, false or not.
     */
    boolean getUsesTongs();
}
