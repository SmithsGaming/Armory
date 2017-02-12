package com.smithsmodding.armory.common.material;

import com.smithsmodding.armory.api.common.crafting.blacksmiths.component.HeatedAnvilRecipeComponent;
import com.smithsmodding.armory.api.common.crafting.blacksmiths.recipe.AnvilRecipe;
import com.smithsmodding.armory.api.common.crafting.blacksmiths.recipe.IAnvilRecipe;
import com.smithsmodding.armory.api.common.material.anvil.IAnvilMaterial;
import com.smithsmodding.armory.api.util.references.ModBlocks;
import com.smithsmodding.armory.api.util.references.ModHeatableObjects;
import com.smithsmodding.armory.api.util.references.ModHeatedObjectTypes;
import com.smithsmodding.armory.api.util.references.References;
import com.smithsmodding.smithscore.client.textures.ITextureController;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by Marc on 22.02.2016.
 */
public class MedievalAnvilMaterial extends IForgeRegistryEntry.Impl<IAnvilMaterial> implements IAnvilMaterial {
    private final String translationKey;
    private final String textFormatting;
    private final String oreDictionaryIdentifier;
    
    private final Float meltingPoint;
    private final Float vaporizingPoint;
    private final Integer meltingTime;
    private final Integer vaporizingTime;
    private final Float heatCoefficient;

    private final Integer durability;
    
    private final String textureOverrideIdentifier;

    private ITextureController renderInfo;

    public MedievalAnvilMaterial(String translationKey, String textFormatting, String oreDictionaryIdentifier, Float meltingPoint, Float vaporizingPoint, Integer meltingTime, Integer vaporizingTime, Float heatCoefficient, Integer durability) {
        this.translationKey = translationKey;
        this.textFormatting = textFormatting;
        this.oreDictionaryIdentifier = oreDictionaryIdentifier;
        this.meltingPoint = meltingPoint;
        this.vaporizingPoint = vaporizingPoint;
        this.meltingTime = meltingTime;
        this.vaporizingTime = vaporizingTime;
        this.heatCoefficient = heatCoefficient;
        this.durability = durability;

        this.textureOverrideIdentifier = oreDictionaryIdentifier;
    }


    /**
     * Method to getCreationRecipe the TranslationKey.
     *
     * @return The translation key of the Material.
     * @implNote This method only exists on the client side.
     */
    @Nonnull
    @Override
    public String getTranslationKey() {
        return translationKey;
    }

    /**
     * Method to getCreationRecipe the markup.
     *
     * @return The markup. Default is TextFormatting.Reset
     */
    @Nonnull
    @Override
    public String getTextFormatting() {
        return textFormatting;
    }


    /**
     * Method to getCreationRecipe the Identifier inside the OreDictionary for a Material.
     *
     * @return The String that identifies this material in the OreDictionary. IE: TK_ANVIL_IRON, TK_ANVIL_STONE etc.
     */
    @Nullable
    @Override
    public String getOreDictionaryIdentifier() {
        return oreDictionaryIdentifier;
    }

    /**
     * Getter for the Melting Point of this material.
     *
     * @return The melting point of this material.
     */
    @Nonnull
    @Override
    public Float getMeltingPoint() {
        return meltingPoint;
    }

    /**
     * Getter for the VaporizingPoint of this material.
     *
     * @return The vaporizing point og this material.
     */
    @Nonnull
    @Override
    public Float getVaporizingPoint() {
        return vaporizingPoint;
    }

    /**
     * Getter for the melting time of this Material
     *
     * @return The melting time of this material
     */
    @Nonnull
    @Override
    public Integer getMeltingTime() {
        return meltingTime;
    }

    /**
     * Getter for the vaporizing time of this Material
     *
     * @return The vaporizing time of this material
     */
    @Nonnull
    @Override
    public Integer getVaporizingTime() {
        return vaporizingTime;
    }

    /**
     * Method to getCreationRecipe the getDurability of the Anvil when it is made out of this Material
     *
     * @return A value bigger then 0, Integer.MaxValue means unbreakable.
     */
    @Nonnull
    @Override
    public Integer getDurability() {
        return durability;
    }

    /**
     * Method used to getCreationRecipe the RenderInfo used to change the Texture of the Model if need be.
     *
     * @return The RenderInfo used to modify the Texture of the model.
     * @implNote This method only exists on the client side.
     */
    @Nonnull
    @Override
    public ITextureController getRenderInfo() {
        return renderInfo;
    }

    /**
     * Setter for the RenderInfo used to change the Texture of the Model if need be.
     *
     * @param info The RenderInfo used to modify the Texture of the Model.
     * @return The instance this method was called on.
     * @implNote This method only exists on the client side.
     */
    @Nonnull
    @Override
    public IAnvilMaterial setRenderInfo(@Nonnull ITextureController info) {
        this.renderInfo = info;
        return this;
    }

    /**
     * Method to getCreationRecipe the Override for the texture.
     * Has to be supplied so that resourcepack makers can override the behaviour if they fell the need to do it.
     *
     * @return The override identifier for overloading the programmatic behaviour of the RenderInfo.
     * @implNote This method only exists on the Client Side.
     */
    @Nonnull
    @Override
    public String getTextureOverrideIdentifier() {
        return textureOverrideIdentifier;
    }

    @Nonnull
    @Override
    public Float getHeatCoefficient() {
        return heatCoefficient;
    }

    /**
     * The recipe that is used to craft an Anvil with this as a Main material.
     *
     * @return The recipe for the anvil with this as a main Material.
     */
    @Nullable
    @Override
    public IAnvilRecipe getRecipeForAnvil() {
        ItemStack stack = new ItemStack(Item.getItemFromBlock(ModBlocks.blockBlackSmithsAnvil));

        NBTTagCompound compound = new NBTTagCompound();
        compound.setString(References.NBTTagCompoundData.TE.Anvil.MATERIAL, getRegistryName().toString());

        stack.setTagCompound(compound);
        return new AnvilRecipe()
                .setCraftingSlotContent(1, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.PLATE, ModHeatableObjects.ITEMSTACK, this, getMeltingPoint() * 0.5F * 0.85F, getMeltingPoint() * 0.5F * 0.95F)))
                .setCraftingSlotContent(2, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.PLATE, ModHeatableObjects.ITEMSTACK, this, getMeltingPoint() * 0.5F * 0.85F, getMeltingPoint() * 0.5F * 0.95F)))
                .setCraftingSlotContent(3, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.PLATE, ModHeatableObjects.ITEMSTACK, this, getMeltingPoint() * 0.5F * 0.85F, getMeltingPoint() * 0.5F * 0.95F)))
                .setCraftingSlotContent(5, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.INGOT, ModHeatableObjects.ITEMSTACK, this, getMeltingPoint() * 0.5F * 0.85F, getMeltingPoint() * 0.5F * 0.95F)))
                .setCraftingSlotContent(6, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.BLOCK, ModHeatableObjects.ITEMSTACK, this, getMeltingPoint() * 0.5F * 0.85F, getMeltingPoint() * 0.5F * 0.95F)))
                .setCraftingSlotContent(7, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.BLOCK, ModHeatableObjects.ITEMSTACK, this, getMeltingPoint() * 0.5F * 0.85F, getMeltingPoint() * 0.5F * 0.95F)))
                .setCraftingSlotContent(8, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.BLOCK, ModHeatableObjects.ITEMSTACK, this, getMeltingPoint() * 0.5F * 0.85F, getMeltingPoint() * 0.5F * 0.95F)))
                .setCraftingSlotContent(9, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.INGOT, ModHeatableObjects.ITEMSTACK, this, getMeltingPoint() * 0.5F * 0.85F, getMeltingPoint() * 0.5F * 0.95F)))
                .setCraftingSlotContent(12, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.INGOT, ModHeatableObjects.ITEMSTACK, this, getMeltingPoint() * 0.5F * 0.85F, getMeltingPoint() * 0.5F * 0.95F)))
                .setCraftingSlotContent(16, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.PLATE, ModHeatableObjects.ITEMSTACK, this, getMeltingPoint() * 0.5F * 0.85F, getMeltingPoint() * 0.5F * 0.95F)))
                .setCraftingSlotContent(17, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.INGOT, ModHeatableObjects.ITEMSTACK, this, getMeltingPoint() * 0.5F * 0.85F, getMeltingPoint() * 0.5F * 0.95F)))
                .setCraftingSlotContent(18, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.PLATE, ModHeatableObjects.ITEMSTACK, this, getMeltingPoint() * 0.5F * 0.85F, getMeltingPoint() * 0.5F * 0.95F)))
                .setCraftingSlotContent(20, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.INGOT, ModHeatableObjects.ITEMSTACK, this, getMeltingPoint() * 0.5F * 0.85F, getMeltingPoint() * 0.5F * 0.95F)))
                .setCraftingSlotContent(21, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.BLOCK, ModHeatableObjects.ITEMSTACK, this, getMeltingPoint() * 0.5F * 0.85F, getMeltingPoint() * 0.5F * 0.95F)))
                .setCraftingSlotContent(22, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.BLOCK, ModHeatableObjects.ITEMSTACK, this, getMeltingPoint() * 0.5F * 0.85F, getMeltingPoint() * 0.5F * 0.95F)))
                .setCraftingSlotContent(23, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.BLOCK, ModHeatableObjects.ITEMSTACK, this, getMeltingPoint() * 0.5F * 0.85F, getMeltingPoint() * 0.5F * 0.95F)))
                .setCraftingSlotContent(24, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.INGOT, ModHeatableObjects.ITEMSTACK, this, getMeltingPoint() * 0.5F * 0.85F, getMeltingPoint() * 0.5F * 0.95F)))
                .setProgress(25).setResult(stack).setHammerUsage(30);
    }
}
