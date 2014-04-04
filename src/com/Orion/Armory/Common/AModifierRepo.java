package com.Orion.Armory.Common;

import com.Orion.Armory.Common.Armor.ArmorModifier;
//import tconstruct.library.armor.ArmorModTypeFilter;

/**
 * Created by Marc on 29-3-2014.
 */
public class AModifierRepo
{
    // Helmet modifiers;
    public static ArmorModifier helmetAquaBreathing; //(1x; aquaCrystal or electric circuit etc)
    public static ArmorModifier helmetNightSight; //(1x; glowstone)
    public static ArmorModifier helmetAquaAffinity; //(100x; Redstone)
    public static ArmorModifier helmetAutoRepair; //(5x; Ball of moss; does not work with electricity)
    public static ArmorModifier helmetReinforced; //(1x; ObsidianPlate)

    // Chestplate modifiers;
    public static ArmorModifier chestplateStrength; //(100x; sugar)
    public static ArmorModifier chestplateHaste; //(100x; redstone per level)
    public static ArmorModifier chestPlateReinforced; //(2x; obsidian plate)
    public static ArmorModifier chestplateAutoRepair; //(5x; Ball of moss; does not work with electricity)
}
