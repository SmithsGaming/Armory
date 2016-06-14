package com.smithsmodding.armory.common.tileentity.guimanagers;

import com.smithsmodding.armory.api.crafting.blacksmiths.recipe.VanillaAnvilRecipe;
import com.smithsmodding.armory.common.tileentity.TileEntityBlackSmithsAnvil;
import com.smithsmodding.armory.common.tileentity.state.BlackSmithsAnvilState;
import com.smithsmodding.armory.util.References;
import com.smithsmodding.smithscore.client.events.gui.GuiInputEvent;
import com.smithsmodding.smithscore.client.gui.components.core.IGUIComponent;
import com.smithsmodding.smithscore.client.gui.management.TileStorageBasedGUIManager;

/**
 * Created by Marc on 14.02.2016.
 */
public class BlackSmithsAnvilGuiManager extends TileStorageBasedGUIManager
{

    TileEntityBlackSmithsAnvil anvil;

    public BlackSmithsAnvilGuiManager(TileEntityBlackSmithsAnvil anvil)
    {
        this.anvil = anvil;
    }

    @Override
    public void onInput(GuiInputEvent.InputTypes types, String componentId, String input) {
        if (types != GuiInputEvent.InputTypes.TEXTCHANGED) return;

        ((BlackSmithsAnvilState) anvil.getState()).setItemName(input);
    }

    @Override
    public String getLabelContents(IGUIComponent component) {
        if (component.getID().equals(References.InternalNames.GUIComponents.Anvil.TEXTBOX))
            return ((BlackSmithsAnvilState) anvil.getState()).getItemName();

        if (component.getID().equals(References.InternalNames.GUIComponents.Anvil.EXPERIENCELABEL)) {
            if (anvil.getCurrentRecipe() == null) return "";
            if (!(anvil.getCurrentRecipe() instanceof VanillaAnvilRecipe)) return "";

            return String.valueOf(((VanillaAnvilRecipe) anvil.getCurrentRecipe()).getRequiredLevelsPerPlayer());
        }

        return "UNKNOWN";
    }

    @Override
    public float getProgressBarValue(IGUIComponent component) {
        if (component.getID().equals(References.InternalNames.GUIComponents.Anvil.CRAFTINGPROGRESS)) {
            if (anvil.getCurrentRecipe() == null) {
                return 0F;
            }

            return (((BlackSmithsAnvilState) anvil.getState()).getCraftingprogress() / (float) anvil.getCurrentRecipe().getMinimumProgress());
        }

        return 0f;
    }



}
