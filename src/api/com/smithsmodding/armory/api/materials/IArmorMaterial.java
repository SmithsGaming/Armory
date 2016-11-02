package com.smithsmodding.armory.api.materials;

import com.smithsmodding.armory.api.registries.IArmorPartRegistry;
import com.smithsmodding.smithscore.client.textures.ITextureController;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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

    @Nullable ItemStack getBaseItemStack();

    @NotNull String getType();

    String getOreDicName ();

    int getMeltingTime ();



    void setBaseDamageAbsorption(String pTargetArmorInternalName, Float pBaseDamageAbsorption);

    Float getBaseDamageAbsorption(String pTargetArmorInternalName);

    @NotNull HashMap<String, Float> getAllBaseDamageAbsorptionValues();


    void setBaseDurability(String pTargetArmorInternalName, int pBaseDurability);

    int getBaseDurability(String pTargetArmorInternalName);

    @NotNull HashMap<String, Integer> getAllBaseDurabilityValues();


    void setMaxModifiersOnPart(String pTargetArmorInternalName, int pMaxModifiers);

    int getMaxModifiersOnPart(String pTargetArmorInternalName);

    @NotNull HashMap<String, Integer> getAllMaxModifiersAmounts();


    boolean getIsBaseArmorMaterial ();

    void setIsBaseArmorMaterial(boolean value);

    float getMeltingPoint ();

    void setMeltingPoint(float meltingPoint);

    float getHeatCoefficient ();

    void setHeatCoefficient(float heatCoefficient);


    IArmorPartRegistry getPartRegistry ();

    void modifyPartState(String partId, boolean activeState);


    ITextureController getRenderInfo();

    @NotNull IArmorMaterial setRenderInfo(ITextureController newInfo);

    String getTranslationKey ();

    @NotNull IArmorMaterial setTranslationKey(String key);

    String getNameColor();
}
