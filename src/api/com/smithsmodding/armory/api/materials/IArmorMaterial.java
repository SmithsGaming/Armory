package com.smithsmodding.armory.api.materials;

import com.smithsmodding.armory.api.registries.IArmorPartRegistry;
import com.smithsmodding.smithscore.client.textures.ITextureController;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

import java.util.HashMap;

/**
 * Created by Orion
 * Created on 01.06.2015
 * 11:12
 *
 * Copyrighted according to Project specific license
 */
public interface IArmorMaterial {


    String getUniqueID ();

    int getItemDamageMaterialIndex ();

    void setItemDamageMaterialIndex(Integer materialIndex);

    ItemStack getBaseItemStack ();

    String getType ();

    String getOreDicName ();

    int getMeltingTime ();



    void setBaseDamageAbsorption(String pTargetArmorInternalName, Float pBaseDamageAbsorption);

    Float getBaseDamageAbsorption(String pTargetArmorInternalName);

    HashMap<String, Float> getAllBaseDamageAbsorptionValues ();


    void setBaseDurability(String pTargetArmorInternalName, int pBaseDurability);

    int getBaseDurability(String pTargetArmorInternalName);

    HashMap<String, Integer> getAllBaseDurabilityValues();


    void setMaxModifiersOnPart(String pTargetArmorInternalName, int pMaxModifiers);

    int getMaxModifiersOnPart(String pTargetArmorInternalName);

    HashMap<String, Integer> getAllMaxModifiersAmounts();


    boolean getIsBaseArmorMaterial ();

    void setIsBaseArmorMaterial(boolean value);

    float getMeltingPoint ();

    void setMeltingPoint(float meltingPoint);

    float getHeatCoefficient ();

    void setHeatCoefficient(float heatCoefficient);


    IArmorPartRegistry getPartRegistry ();

    void modifyPartState(String partId, boolean activeState);


    ITextureController getRenderInfo();

    IArmorMaterial setRenderInfo(ITextureController newInfo);

    String getTranslationKey ();

    IArmorMaterial setTranslationKey (String key);

    TextFormatting getNameColor();
}
