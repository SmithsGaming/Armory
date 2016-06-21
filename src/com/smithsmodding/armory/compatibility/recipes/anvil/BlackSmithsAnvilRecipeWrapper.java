package com.smithsmodding.armory.compatibility.recipes.anvil;

import com.google.common.collect.ImmutableList;
import com.smithsmodding.armory.api.crafting.blacksmiths.component.IAnvilRecipeComponent;
import com.smithsmodding.armory.api.crafting.blacksmiths.recipe.AnvilRecipe;
import com.smithsmodding.armory.common.tileentity.TileEntityBlackSmithsAnvil;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;

/**
 * Author Orion (Created on: 21.06.2016)
 */
public class BlackSmithsAnvilRecipeWrapper extends BlankRecipeWrapper {

    int hammerUsage = -1;
    int tongUsage = -1;
    private ItemStack[] inputs;
    private ItemStack[] additionalStacks;
    private ItemStack output;

    public BlackSmithsAnvilRecipeWrapper(AnvilRecipe recipe) {
        inputs = new ItemStack[TileEntityBlackSmithsAnvil.MAX_CRAFTINGSLOTS];
        for (int i = 0; i < TileEntityBlackSmithsAnvil.MAX_CRAFTINGSLOTS; i++) {
            IAnvilRecipeComponent component = recipe.getComponent(i);
            if (component == null)
                continue;

            inputs[i] = component.getComponentTargetStack();
        }

        additionalStacks = new ItemStack[TileEntityBlackSmithsAnvil.MAX_ADDITIONALSLOTS];
        for (int i = 0; i < TileEntityBlackSmithsAnvil.MAX_ADDITIONALSLOTS; i++) {
            IAnvilRecipeComponent component = recipe.getAdditionalComponent(i);
            if (component == null)
                continue;

            additionalStacks[i] = component.getComponentTargetStack();
        }

        output = recipe.getResult(inputs, additionalStacks);

        if (output == null)
            throw new IllegalArgumentException("Given recipe has no output!");

        if (recipe.getUsesHammer())
            hammerUsage = recipe.getHammerUsage();

        if (recipe.getUsesTongs())
            tongUsage = recipe.getTongsUsage();
    }

    @Nonnull
    @Override
    public List<ItemStack> getInputs() {
        return Arrays.asList(inputs);
    }

    @Nonnull
    @Override
    public List<ItemStack> getOutputs() {
        return ImmutableList.of(output);
    }

    public List<ItemStack> getAdditionalStacks() {
        return Arrays.asList(additionalStacks);
    }

    public int getHammerUsage() {
        return hammerUsage;
    }

    public int getTongUsage() {
        return tongUsage;
    }
}
