package com.smithsmodding.armory.compatibility.recipes.anvil;

import com.smithsmodding.armory.api.References;
import com.smithsmodding.armory.common.registry.GeneralRegistry;
import com.smithsmodding.armory.common.tileentity.TileEntityBlackSmithsAnvil;
import com.smithsmodding.armory.compatibility.JEICompatMod;
import com.smithsmodding.armory.util.client.Textures;
import com.smithsmodding.armory.util.client.TranslationKeys;
import com.smithsmodding.smithscore.util.client.CustomResource;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

/**
 * Author Orion (Created on: 21.06.2016)
 */
public class BlacksmithsAnvilRecipeCategory implements IRecipeCategory {

    private static final CustomResource RESOURCE = Textures.Gui.Compatibility.JEI.ArmorsAnvil.GUI;

    private static final String LOCALENAME = I18n.format(TranslationKeys.Gui.JEI.AnvilRecipeName);
    private static final IDrawable BACKGROUND = JEICompatMod.getJeiHelpers().getGuiHelper().createDrawable(new ResourceLocation(RESOURCE.getPrimaryLocation()), RESOURCE.getU(), RESOURCE.getV(), RESOURCE.getWidth(), RESOURCE.getHeight());

    @Nonnull
    @Override
    public String getUid() {
        return References.Compatibility.JEI.RecipeTypes.ANVIL;
    }

    @Nonnull
    @Override
    public String getTitle() {
        return LOCALENAME;
    }

    @Nonnull
    @Override
    public IDrawable getBackground() {
        return BACKGROUND;
    }

    @Override
    public void drawExtras(@Nonnull Minecraft minecraft) {

    }

    @Override
    public void drawAnimations(@Nonnull Minecraft minecraft) {

    }

    @Override
    public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull IRecipeWrapper recipeWrapper) {
        for (int i = 0; i < TileEntityBlackSmithsAnvil.MAX_CRAFTINGSLOTS; i++) {
            int row = i / 5;
            int column = i % 5;

            recipeLayout.getItemStacks().init(i, true, 7 + column * 18, 7 + row * 18);
        }

        recipeLayout.getItemStacks().init(TileEntityBlackSmithsAnvil.MAX_CRAFTINGSLOTS, false, 137, 43);

        recipeLayout.getItemStacks().init(TileEntityBlackSmithsAnvil.MAX_CRAFTINGSLOTS + TileEntityBlackSmithsAnvil.MAX_OUTPUTSLOTS, true, 137, 7);
        recipeLayout.getItemStacks().init(TileEntityBlackSmithsAnvil.MAX_CRAFTINGSLOTS + TileEntityBlackSmithsAnvil.MAX_OUTPUTSLOTS + TileEntityBlackSmithsAnvil.MAX_HAMMERSLOTS, true, 137, 79);

        for (int i = 0; i < TileEntityBlackSmithsAnvil.MAX_ADDITIONALSLOTS; i++) {
            recipeLayout.getItemStacks().init(TileEntityBlackSmithsAnvil.MAX_CRAFTINGSLOTS + TileEntityBlackSmithsAnvil.MAX_OUTPUTSLOTS + TileEntityBlackSmithsAnvil.MAX_HAMMERSLOTS + TileEntityBlackSmithsAnvil.MAX_TONGSLOTS + i, true, 51 + i * 22, 108);
        }

        if (recipeWrapper instanceof BlackSmithsAnvilRecipeWrapper) {
            BlackSmithsAnvilRecipeWrapper wrapper = (BlackSmithsAnvilRecipeWrapper) recipeWrapper;
            for (int i = 0; i < TileEntityBlackSmithsAnvil.MAX_CRAFTINGSLOTS; i++) {
                recipeLayout.getItemStacks().set(i, wrapper.getInputs().get(i));
            }

            recipeLayout.getItemStacks().set(TileEntityBlackSmithsAnvil.MAX_CRAFTINGSLOTS, wrapper.getOutputs().get(0));

            if (wrapper.hammerUsage > 0)
                recipeLayout.getItemStacks().set(TileEntityBlackSmithsAnvil.MAX_CRAFTINGSLOTS + TileEntityBlackSmithsAnvil.MAX_OUTPUTSLOTS, new ItemStack(GeneralRegistry.Items.hammer, wrapper.getHammerUsage()));

            if (wrapper.tongUsage > 0)
                recipeLayout.getItemStacks().set(TileEntityBlackSmithsAnvil.MAX_CRAFTINGSLOTS + TileEntityBlackSmithsAnvil.MAX_OUTPUTSLOTS + TileEntityBlackSmithsAnvil.MAX_HAMMERSLOTS, new ItemStack(GeneralRegistry.Items.tongs, wrapper.getTongUsage()));

            for (int i = 0; i < TileEntityBlackSmithsAnvil.MAX_ADDITIONALSLOTS; i++) {
                recipeLayout.getItemStacks().set(TileEntityBlackSmithsAnvil.MAX_CRAFTINGSLOTS + TileEntityBlackSmithsAnvil.MAX_OUTPUTSLOTS + TileEntityBlackSmithsAnvil.MAX_HAMMERSLOTS + TileEntityBlackSmithsAnvil.MAX_TONGSLOTS + i, wrapper.getAdditionalStacks().get(i));
            }
        }
    }
}
