package com.Orion.Armory.Common.Items;
/*
*   ARecipeItem
*   Created by: Orion
*   Created on: 8-4-2014
*/

import com.Orion.Armory.Common.ARegistry;
import mantle.items.abstracts.CraftingItem;
import net.minecraft.item.Item;

public class ARecipeItem extends CraftingItem
{
    public String[] iUnlocalisedNames = new String[] {"Armory.Crafting.BreathingHelp", "Armory.Crafting.Lamp", "Armory.Crafting.AquaMotor", "Armory.Crafting.Nails", "Armory.Crafting.FeatherSet", "Armory.Crafting.Wings"};
    public String[] iTextureNames = new String[] {"Breathing_Help", "Lamp", "Aqua_Motor", "Lamp", "Nails", "Feather_Set", "Wings"};
    public String iTextureFolder = "crafting/";


    public ARecipeItem() {
        super(new String[] {"Armory.Crafting.BreathingHelp", "Armory.Crafting.Lamp", "Armory.Crafting.AquaMotor", "Armory.Crafting.Nails", "Armory.Crafting.FeatherSet", "Armory.Crafting.Wings"}, new String[] {"Breathing_Help", "Lamp", "Aqua_Motor", "Lamp", "Nails", "Feather_Set", "Wings"}, "crafting/", "tconstruct-armory", ARegistry.iTabArmoryComponents);
    }
}
