package com.smithsmodding.armory.common.material;
/*
*   ArmorMaterial
*   Created by: Orion
*   Created on: 6-4-2014
*/

import com.smithsmodding.armory.Armory;
import com.smithsmodding.armory.api.materials.IArmorMaterial;
import com.smithsmodding.armory.api.materials.MaterialRenderControllers;
import com.smithsmodding.armory.api.registries.IArmorPartRegistry;
import com.smithsmodding.armory.api.util.references.ModLogger;
import com.smithsmodding.armory.api.util.references.References;
import com.smithsmodding.armory.common.registry.HeatableItemRegistry;
import com.smithsmodding.armory.common.registry.MedievalAddonRegistry;
import com.smithsmodding.smithscore.client.textures.ITextureController;
import com.smithsmodding.smithscore.util.client.color.ColorSampler;
import com.smithsmodding.smithscore.util.client.color.MinecraftColor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class ArmorMaterial implements IArmorMaterial {
    private static int LASTUSEDID = 0;

    private int materialIndex;
    private String oreDictionaryIdentifier;
    private String uniqueIdentifier;
    private boolean isBaseMaterial;
    private int meltingTime;

    @NotNull
    private HashMap<String, Float> partAbsorptionRatios = new HashMap<String, Float>();
    @NotNull
    private HashMap<String, Integer> partDurabilities = new HashMap<String, Integer>();
    @NotNull
    private HashMap<String, Integer> partModifierCounts = new HashMap<String, Integer>();

    private float meltingPoint;
    private float heatCoefficient;
    @Nullable
    private ItemStack baseStack;

    private ITextureController renderInfo;
    private String translationKey;


    //Constructor
    public ArmorMaterial(String uniqueIdentifier, String oreDictionaryIdentifier, boolean isBaseMaterial, float meltingPoint, int meltingTime, float heatCoefficient, @Nullable ItemStack baseStack) {
        this.oreDictionaryIdentifier = oreDictionaryIdentifier;
        this.uniqueIdentifier = uniqueIdentifier;

        if (Armory.side == Side.CLIENT && baseStack != null) {
            MinecraftColor metalColor = ColorSampler.getColorSampleFromItemStack(baseStack);

            renderInfo = new MaterialRenderControllers.Metal(metalColor.getRGB());
        } else if (Armory.side == Side.CLIENT) {
            renderInfo = new MaterialRenderControllers.Metal(MinecraftColor.WHITE.getRGB());
        }

        this.isBaseMaterial = isBaseMaterial;
        this.meltingPoint = meltingPoint;
        this.heatCoefficient = heatCoefficient;
        this.meltingTime = meltingTime;

        this.baseStack = baseStack;
        if (baseStack != null && !HeatableItemRegistry.getInstance().isHeatable(baseStack)) {
            HeatableItemRegistry.getInstance().addBaseStack(this, baseStack);
        }

        materialIndex = LASTUSEDID;
        LASTUSEDID++;

        ModLogger.getInstance().info("Initialized material: " + this.uniqueIdentifier);
    }


    @Override
    public String getUniqueID() {
        return uniqueIdentifier;
    }

    @Override
    public int getItemDamageMaterialIndex() {
        return materialIndex;
    }

    @Override
    public void setItemDamageMaterialIndex(Integer materialIndex) {
        this.materialIndex = materialIndex;
    }

    @Nullable
    @Override
    public ItemStack getBaseItemStack() {
        return baseStack;
    }

    @Override
    public String getOreDicName() {
        return oreDictionaryIdentifier;
    }

    @NotNull
    @Override
    public String getType() {
        return References.InternalNames.Tiers.MEDIEVAL;
    }

    public int getMeltingTime() {
        return meltingTime;
    }


    public void setBaseDamageAbsorption(String pTargetArmorInternalName, Float pBaseDamageAbsorption) {
        partAbsorptionRatios.put(pTargetArmorInternalName, pBaseDamageAbsorption);
    }

    public Float getBaseDamageAbsorption(String pTargetArmorInternalName) {
        return partAbsorptionRatios.get(pTargetArmorInternalName);
    }

    @NotNull
    @Override
    public HashMap<String, Float> getAllBaseDamageAbsorptionValues() {
        return partAbsorptionRatios;
    }


    public void setBaseDurability(String pTargetArmorInternalName, int pBaseDurability) {
        partDurabilities.put(pTargetArmorInternalName, pBaseDurability);
    }

    public int getBaseDurability(String pTargetArmorInternalName) {
        if (partDurabilities.containsKey(pTargetArmorInternalName))
            return partDurabilities.get(pTargetArmorInternalName);

        return 100;
    }

    @NotNull
    @Override
    public HashMap<String, Integer> getAllBaseDurabilityValues() {
        return partDurabilities;
    }


    public void setMaxModifiersOnPart(String pTargetArmorInternalName, int pMaxModifiers) {
        partModifierCounts.put(pTargetArmorInternalName, pMaxModifiers);
    }

    public int getMaxModifiersOnPart(String pTargetArmorInternalName) {
        return partModifierCounts.get(pTargetArmorInternalName);
    }

    @NotNull
    @Override
    public HashMap<String, Integer> getAllMaxModifiersAmounts() {
        return partModifierCounts;
    }


    @Override
    public boolean getIsBaseArmorMaterial() {
        return isBaseMaterial;
    }

    @Override
    public void setIsBaseArmorMaterial(boolean value) {
        this.isBaseMaterial = value;
    }

    @Override
    public float getMeltingPoint() {
        return meltingPoint;
    }

    @Override
    public void setMeltingPoint(float meltingPoint) {
        this.meltingPoint = meltingPoint;
    }

    @Override
    public float getHeatCoefficient() {
        return heatCoefficient;
    }

    @Override
    public void setHeatCoefficient(float heatCoefficient) {
        this.heatCoefficient = heatCoefficient;
    }

    @NotNull
    @Override
    public IArmorPartRegistry getPartRegistry() {
        return MedievalAddonRegistry.getInstance();
    }

    @Override
    public void modifyPartState(String partId, boolean activeState) {
        getPartRegistry().setPartStateForMaterial(this, partId, activeState);
    }

    @Override
    public ITextureController getRenderInfo() {
        return renderInfo;
    }

    @NotNull
    @Override
    public IArmorMaterial setRenderInfo(ITextureController newInfo) {
        renderInfo = newInfo;
        return this;
    }

    @Override
    public String getTranslationKey() {
        return translationKey;
    }

    @NotNull
    @Override
    public IArmorMaterial setTranslationKey(String key) {
        translationKey = key;
        return this;
    }

    @Override
    public String getNameColor() {
        if (renderInfo == null)
            return "";

        if (renderInfo.getVertexColor() == null)
            return "";

        return renderInfo.getVertexColor().encodeColor();
    }
}
