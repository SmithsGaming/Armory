package com.smithsmodding.armory.api.materials;

import com.smithsmodding.armory.api.crafting.blacksmiths.recipe.AnvilRecipe;
import com.smithsmodding.smithscore.client.textures.ITextureController;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.Nullable;

/**
 * Created by Marc on 22.02.2016.
 */
public interface IAnvilMaterial
{
    String getID();

    int durability();

    @SideOnly(Side.CLIENT)
    String translatedDisplayName();

    @Nullable
    @SideOnly(Side.CLIENT)
    String translatedDisplayNameColor();

    @SideOnly(Side.CLIENT)
    ITextureController getRenderInfo();

    @SideOnly(Side.CLIENT)
    void setRenderInfo(ITextureController info);

    @Nullable AnvilRecipe getRecipeForAnvil();
}
