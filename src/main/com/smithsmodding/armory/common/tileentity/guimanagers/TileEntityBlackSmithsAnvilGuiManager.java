package com.smithsmodding.armory.common.tileentity.guimanagers;

import com.smithsmodding.armory.api.util.references.References;
import com.smithsmodding.armory.common.crafting.blacksmiths.recipe.VanillaAnvilRecipe;
import com.smithsmodding.armory.common.tileentity.TileEntityBlackSmithsAnvil;
import com.smithsmodding.smithscore.client.events.gui.GuiInputEvent;
import com.smithsmodding.smithscore.client.gui.components.core.IGUIComponent;
import com.smithsmodding.smithscore.client.gui.management.TileStorageBasedGUIManager;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Marc on 14.02.2016.
 */
public class TileEntityBlackSmithsAnvilGuiManager extends TileStorageBasedGUIManager {

    TileEntityBlackSmithsAnvil anvil;

    public TileEntityBlackSmithsAnvilGuiManager(TileEntityBlackSmithsAnvil anvil) {
        this.anvil = anvil;
    }

    @Override
    public void onInput(GuiInputEvent.InputTypes types, String componentId, String input) {
        if (types != GuiInputEvent.InputTypes.TEXTCHANGED) return;

        (anvil.getState()).setItemName(input);
    }

    @Override
    public String getLabelContents(@NotNull IGUIComponent component) {
        if (component.getID().equals(References.InternalNames.GUIComponents.Anvil.TEXTBOX))
            return (anvil.getState()).getItemName();

        if (component.getID().equals(References.InternalNames.GUIComponents.Anvil.EXPERIENCELABEL)) {
            if (anvil.getCurrentRecipe() == null) return "";
            if (!(anvil.getCurrentRecipe() instanceof VanillaAnvilRecipe)) return "";

            return String.valueOf(((VanillaAnvilRecipe) anvil.getCurrentRecipe()).getRequiredLevelsPerPlayer());
        }

        return "UNKNOWN";
    }

    @Override
    public float getProgressBarValue(@NotNull IGUIComponent component) {
        if (component.getID().equals(References.InternalNames.GUIComponents.Anvil.EXTENDEDCRAFTING + ".Progress")) {
            if (anvil.getCurrentRecipe() == null) {
                return 0F;
            }

            return ((anvil.getState()).getCraftingprogress() / (float) anvil.getCurrentRecipe().getMinimumProgress());
        }

        return 0f;
    }


}
