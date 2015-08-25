package com.Orion.Armory.Common.Compatibility.NEI;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import com.Orion.Armory.API.Crafting.SmithingsAnvil.AnvilRecipeRegistry;
import com.Orion.Armory.API.Crafting.SmithingsAnvil.Components.IAnvilRecipeComponent;
import com.Orion.Armory.API.Crafting.SmithingsAnvil.Recipe.AnvilRecipe;
import com.Orion.Armory.API.Item.IHeatableItem;
import com.Orion.Armory.Client.GUI.Implementation.ArmorsAnvil.GuiArmorsAnvilMinimal;
import com.Orion.Armory.Common.Factory.HeatedItemFactory;
import com.Orion.Armory.Common.Item.ItemHeatedItem;
import com.Orion.Armory.Common.Registry.GeneralRegistry;
import com.Orion.Armory.Common.TileEntity.Anvil.TileEntityArmorsAnvil;
import com.Orion.Armory.Util.Client.Textures;
import com.Orion.Armory.Util.Client.TranslationKeys;
import com.Orion.Armory.Util.Core.ItemStackHelper;
import com.Orion.Armory.Util.References;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static codechicken.lib.gui.GuiDraw.changeTexture;
import static codechicken.lib.gui.GuiDraw.drawTexturedModalRect;

/**
 * Created by Orion
 * Created on 29.05.2015
 * 17:59
 * <p/>
 * Copyrighted according to Project specific license
 */
public class ArmorsAnvilNEIHandler extends TemplateRecipeHandler {

    @Override
    public void drawBackground(int recipe) {
        GL11.glPushMatrix();
        changeTexture(getGuiTexture());
        drawTexturedModalRect(2,0,0,0, 162, 140);
        GL11.glPopMatrix();
    }

    @Override
    public void loadTransferRects() {
        transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(17, 7, 16, 16), getRecipeID()));
    }

    @Override
    public int recipiesPerPage() {
        return 1;
    }

    public String getRecipeID()
    {
        return References.General.MOD_ID + ":" + References.InternalNames.Blocks.ArmorsAnvil;
    }

    @Override
    public Class<? extends GuiContainer> getGuiClass() {
        return GuiArmorsAnvilMinimal.class;
    }

    @Override
    public String getGuiTexture() {
        return Textures.Gui.Compatibility.NEI.ArmorsAnvil.GUI.getPrimaryLocation();
    }

    @Override
    public String getRecipeName() {
        return StatCollector.translateToLocal(TranslationKeys.GUI.NEI.AnvilRecipeName);
    }

    @Override
    public void loadCraftingRecipes(ItemStack result) {
        if (result.getItem() instanceof ItemHeatedItem) {
            loadCraftingRecipes(HeatedItemFactory.getInstance().convertToCooledIngot(result));
            return;
        }

        for (AnvilRecipe tOriginalRecipe : AnvilRecipeRegistry.getInstance().getRecipes().values()) {
            ItemStack[] tCraftingInput = new ItemStack[TileEntityArmorsAnvil.MAX_CRAFTINGSLOTS];
            ItemStack[] tAdditionalCraftingInput = new ItemStack[TileEntityArmorsAnvil.MAX_ADDITIONALSLOTS];

            for (int tComponentIndex = 0; tComponentIndex < TileEntityArmorsAnvil.MAX_CRAFTINGSLOTS; tComponentIndex++) {
                IAnvilRecipeComponent tComponent = tOriginalRecipe.getComponent(tComponentIndex);

                if (tComponent == null)
                    continue;

                tCraftingInput[tComponentIndex] = tComponent.getComponentTargetStack();
            }

            for (int tComponentIndex = 0; tComponentIndex < TileEntityArmorsAnvil.MAX_ADDITIONALSLOTS; tComponentIndex++) {
                IAnvilRecipeComponent tComponent = tOriginalRecipe.getAdditionalComponent(tComponentIndex);

                if (tComponent == null)
                    continue;

                tAdditionalCraftingInput[tComponentIndex] = tComponent.getComponentTargetStack();
            }

            ItemStack tResultStack = tOriginalRecipe.getResult(tCraftingInput, tAdditionalCraftingInput);
            if (tResultStack.getItem() instanceof ItemHeatedItem)
            {
                if (ItemHeatedItem.areStacksEqualExceptTemp(tResultStack, result))
                {
                    arecipes.add(new CachedAnvilRecipe(tOriginalRecipe));
                    return;
                }
            }
            else
            {
                if (ItemStackHelper.equalsIgnoreStackSize(tResultStack, result))
                {
                    arecipes.add(new CachedAnvilRecipe(tOriginalRecipe));
                    return;
                }
            }
        }
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results) {
        if (outputId.equals(getRecipeID())) {
            for (AnvilRecipe recipe : AnvilRecipeRegistry.getInstance().getRecipes().values()) {
                arecipes.add(new CachedAnvilRecipe(recipe));
            }
        } else {
            super.loadCraftingRecipes(outputId, results);
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack pIngredient)
    {
        for (AnvilRecipe tOriginalRecipe : AnvilRecipeRegistry.getInstance().getRecipes().values())
        {
            validateUsageRecipe(tOriginalRecipe, pIngredient);
        }
    }

    private void validateUsageRecipe(AnvilRecipe pOriginalRecipe, ItemStack pIngredient)
    {
        for (int tComponentIndex = 0; tComponentIndex < TileEntityArmorsAnvil.MAX_CRAFTINGSLOTS; tComponentIndex++) {
            IAnvilRecipeComponent tComponent = pOriginalRecipe.getComponent(tComponentIndex);

            if (tComponent == null)
                continue;

            if (tComponent.getComponentTargetStack() == null)
                continue;

            ItemStack tComponentStack = tComponent.getComponentTargetStack();

            if (tComponentStack.getItem() instanceof ItemHeatedItem)
            {
                if (pIngredient.getItem() instanceof IHeatableItem) {
                    if (ItemHeatedItem.areStacksEqualExceptTemp(tComponent.getComponentTargetStack(), HeatedItemFactory.getInstance().convertToHeatedIngot(pIngredient))) {
                        arecipes.add(new CachedAnvilRecipe(pOriginalRecipe));
                        return;
                    }
                } else if (pIngredient.getItem() instanceof ItemHeatedItem)
                {
                    if (ItemHeatedItem.areStacksEqualExceptTemp(tComponent.getComponentTargetStack(), pIngredient)) {
                        arecipes.add(new CachedAnvilRecipe(pOriginalRecipe));
                        return;
                    }
                }
            }
            else
            {
                if (ItemStackHelper.equalsIgnoreStackSize(tComponent.getComponentTargetStack(), pIngredient))
                {
                    arecipes.add(new CachedAnvilRecipe(pOriginalRecipe));
                    return;
                }
            }
        }

        for (int tComponentIndex = 0; tComponentIndex < TileEntityArmorsAnvil.MAX_ADDITIONALSLOTS; tComponentIndex++) {
            IAnvilRecipeComponent tComponent = pOriginalRecipe.getAdditionalComponent(tComponentIndex);

            if (tComponent == null)
                continue;

            if (ItemStackHelper.equalsIgnoreStackSize(tComponent.getComponentTargetStack(), pIngredient))
            {
                arecipes.add(new CachedAnvilRecipe(pOriginalRecipe));
                return;
            }
        }
    }

    public class CachedAnvilRecipe extends CachedRecipe {

        PositionedStack iOutput;
        ArrayList<PositionedStack> iInputs = new ArrayList<PositionedStack>();

        public CachedAnvilRecipe(AnvilRecipe pOriginalRecipe) {
            ItemStack[] tCraftingInput = new ItemStack[TileEntityArmorsAnvil.MAX_CRAFTINGSLOTS];
            ItemStack[] tAdditionalCraftingInput = new ItemStack[TileEntityArmorsAnvil.MAX_ADDITIONALSLOTS];

            for (int tComponentIndex = 0; tComponentIndex < TileEntityArmorsAnvil.MAX_CRAFTINGSLOTS; tComponentIndex++) {
                IAnvilRecipeComponent tComponent = pOriginalRecipe.getComponent(tComponentIndex);

                if (tComponent == null)
                    continue;

                int tXCoord = 10 + ((tComponentIndex % 5) * 18);
                int tYCoord = 8 + ((tComponentIndex / 5) * 18);

                ItemStack tComponentStack = tComponent.getComponentTargetStack();
                if (tComponentStack == null)
                    return;

                iInputs.add(new PositionedStack(tComponentStack, tXCoord, tYCoord));
                tCraftingInput[tComponentIndex] = tComponent.getComponentTargetStack();
            }

            if (pOriginalRecipe.getUsesHammer()) {
                iInputs.add(new PositionedStack(new ItemStack(GeneralRegistry.Items.iHammer, 1, 150), 140, 8));
            }

            if (pOriginalRecipe.getUsesTongs()) {
                iInputs.add(new PositionedStack(new ItemStack(GeneralRegistry.Items.iTongs, 1, 150), 140, 80));
            }

            for (int tComponentIndex = 0; tComponentIndex < TileEntityArmorsAnvil.MAX_ADDITIONALSLOTS; tComponentIndex++) {
                IAnvilRecipeComponent tComponent = pOriginalRecipe.getAdditionalComponent(tComponentIndex);

                if (tComponent == null)
                    continue;

                int tXCoord = 185 + ((tComponentIndex % 3) * 18);

                ItemStack tComponentStack = tComponent.getComponentTargetStack();
                if (tComponentStack == null)
                    return;

                iInputs.add(new PositionedStack(tComponentStack, tXCoord, 22));
                tAdditionalCraftingInput[tComponentIndex] = tComponent.getComponentTargetStack();
            }

            iOutput = new PositionedStack(pOriginalRecipe.getResult(tCraftingInput, tAdditionalCraftingInput), 140, 44);
        }

        @Override
        public List<PositionedStack> getIngredients() {
            return iInputs;
        }

        @Override
        public PositionedStack getResult() {
            return iOutput;
        }
    }

}
