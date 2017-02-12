package com.smithsmodding.armory.common.anvil;

import com.smithsmodding.armory.api.crafting.blacksmiths.recipe.AnvilRecipe;
import com.smithsmodding.armory.api.materials.IAnvilMaterial;
import com.smithsmodding.armory.api.util.references.ModBlocks;
import com.smithsmodding.armory.api.util.references.References;
import com.smithsmodding.armory.common.crafting.blacksmiths.component.HeatedAnvilRecipeComponent;
import com.smithsmodding.armory.common.registry.MaterialRegistry;
import com.smithsmodding.smithscore.client.textures.ITextureController;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by Marc on 22.02.2016.
 */
public class AnvilMaterial implements IAnvilMaterial {

    String id;
    int durability;
    String nameTranslationKey;
    @Nullable String translatedDisplayNameColor;
    ITextureController info;

    public AnvilMaterial(String id, int durability, String nameTranslationKey) {
        this.id = id;
        this.durability = durability;
        this.nameTranslationKey = nameTranslationKey;
        translatedDisplayNameColor = null;
    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public int durability() {
        return durability;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String translatedDisplayName() {
        return I18n.format(nameTranslationKey);
    }

    @Nullable
    @Override
    @SideOnly(Side.CLIENT)
    public String translatedDisplayNameColor() {
        if (translatedDisplayNameColor == null)
            translatedDisplayNameColor = getRenderInfo().getVertexColor().encodeColor();

        return translatedDisplayNameColor;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ITextureController getRenderInfo() {
        return info;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void setRenderInfo(ITextureController info) {
        this.info = info;
    }

    @Nullable
    @Override
    public AnvilRecipe getRecipeForAnvil() {
        if (id.equals(References.InternalNames.Materials.Anvil.STONE))
            return null;

        return generateRecipeForMaterialId(id);
    }

    @NotNull
    private AnvilRecipe generateRecipeForMaterialId(@NotNull String materialId) {
        ItemStack stack = new ItemStack(Item.getItemFromBlock(ModBlocks.blockBlackSmithsAnvil));

        NBTTagCompound compound = new NBTTagCompound();
        compound.setString(References.NBTTagCompoundData.TE.Anvil.MATERIAL, materialId);

        stack.setTagCompound(compound);
        return new AnvilRecipe()
                .setCraftingSlotContent(1, (new HeatedAnvilRecipeComponent(materialId, References.InternalNames.HeatedItemTypes.PLATE, MaterialRegistry.getInstance().getMaterial(materialId).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(materialId).getMeltingPoint() * 0.5F * 0.95F)))
                .setCraftingSlotContent(2, (new HeatedAnvilRecipeComponent(materialId, References.InternalNames.HeatedItemTypes.PLATE, MaterialRegistry.getInstance().getMaterial(materialId).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(materialId).getMeltingPoint() * 0.5F * 0.95F)))
                .setCraftingSlotContent(3, (new HeatedAnvilRecipeComponent(materialId, References.InternalNames.HeatedItemTypes.PLATE, MaterialRegistry.getInstance().getMaterial(materialId).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(materialId).getMeltingPoint() * 0.5F * 0.95F)))
                .setCraftingSlotContent(5, (new HeatedAnvilRecipeComponent(materialId, References.InternalNames.HeatedItemTypes.INGOT, MaterialRegistry.getInstance().getMaterial(materialId).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(materialId).getMeltingPoint() * 0.5F * 0.95F)))
                .setCraftingSlotContent(6, (new HeatedAnvilRecipeComponent(materialId, References.InternalNames.HeatedItemTypes.BLOCK, MaterialRegistry.getInstance().getMaterial(materialId).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(materialId).getMeltingPoint() * 0.5F * 0.95F)))
                .setCraftingSlotContent(7, (new HeatedAnvilRecipeComponent(materialId, References.InternalNames.HeatedItemTypes.BLOCK, MaterialRegistry.getInstance().getMaterial(materialId).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(materialId).getMeltingPoint() * 0.5F * 0.95F)))
                .setCraftingSlotContent(8, (new HeatedAnvilRecipeComponent(materialId, References.InternalNames.HeatedItemTypes.BLOCK, MaterialRegistry.getInstance().getMaterial(materialId).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(materialId).getMeltingPoint() * 0.5F * 0.95F)))
                .setCraftingSlotContent(9, (new HeatedAnvilRecipeComponent(materialId, References.InternalNames.HeatedItemTypes.INGOT, MaterialRegistry.getInstance().getMaterial(materialId).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(materialId).getMeltingPoint() * 0.5F * 0.95F)))
                .setCraftingSlotContent(12, (new HeatedAnvilRecipeComponent(materialId, References.InternalNames.HeatedItemTypes.INGOT, MaterialRegistry.getInstance().getMaterial(materialId).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(materialId).getMeltingPoint() * 0.5F * 0.95F)))
                .setCraftingSlotContent(16, (new HeatedAnvilRecipeComponent(materialId, References.InternalNames.HeatedItemTypes.PLATE, MaterialRegistry.getInstance().getMaterial(materialId).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(materialId).getMeltingPoint() * 0.5F * 0.95F)))
                .setCraftingSlotContent(17, (new HeatedAnvilRecipeComponent(materialId, References.InternalNames.HeatedItemTypes.INGOT, MaterialRegistry.getInstance().getMaterial(materialId).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(materialId).getMeltingPoint() * 0.5F * 0.95F)))
                .setCraftingSlotContent(18, (new HeatedAnvilRecipeComponent(materialId, References.InternalNames.HeatedItemTypes.PLATE, MaterialRegistry.getInstance().getMaterial(materialId).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(materialId).getMeltingPoint() * 0.5F * 0.95F)))
                .setCraftingSlotContent(20, (new HeatedAnvilRecipeComponent(materialId, References.InternalNames.HeatedItemTypes.INGOT, MaterialRegistry.getInstance().getMaterial(materialId).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(materialId).getMeltingPoint() * 0.5F * 0.95F)))
                .setCraftingSlotContent(21, (new HeatedAnvilRecipeComponent(materialId, References.InternalNames.HeatedItemTypes.BLOCK, MaterialRegistry.getInstance().getMaterial(materialId).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(materialId).getMeltingPoint() * 0.5F * 0.95F)))
                .setCraftingSlotContent(22, (new HeatedAnvilRecipeComponent(materialId, References.InternalNames.HeatedItemTypes.BLOCK, MaterialRegistry.getInstance().getMaterial(materialId).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(materialId).getMeltingPoint() * 0.5F * 0.95F)))
                .setCraftingSlotContent(23, (new HeatedAnvilRecipeComponent(materialId, References.InternalNames.HeatedItemTypes.BLOCK, MaterialRegistry.getInstance().getMaterial(materialId).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(materialId).getMeltingPoint() * 0.5F * 0.95F)))
                .setCraftingSlotContent(24, (new HeatedAnvilRecipeComponent(materialId, References.InternalNames.HeatedItemTypes.INGOT, MaterialRegistry.getInstance().getMaterial(materialId).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(materialId).getMeltingPoint() * 0.5F * 0.95F)))
                .setProgress(25).setResult(stack).setHammerUsage(30);
    }


}
