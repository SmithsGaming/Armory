package com.smithsmodding.armory.api.common.crafting.blacksmiths.recipe;

import com.smithsmodding.armory.api.common.crafting.blacksmiths.component.IAnvilRecipeComponent;
import com.smithsmodding.armory.api.util.references.ModInventories;
import com.smithsmodding.smithscore.common.tileentity.IWatchableTileEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Created by Orion
 * Created on 02.05.2015
 * 12:57
 *
 * Copyrighted according to Project specific license
 */
public class AnvilRecipe extends IForgeRegistryEntry.Impl<IAnvilRecipe> implements IAnvilRecipe {

    protected int resultAmount = 1;

    @Nonnull
    protected ItemStack result;

    private int targetProgress;
    private int hammer;
    private int tongs;
    private boolean isShapeless = false;

    @NotNull
    private IAnvilRecipeComponent[] components = new IAnvilRecipeComponent[ModInventories.TileEntityBlackSmithsAnvil.MAX_CRAFTINGSLOTS];
    @NotNull
    private IAnvilRecipeComponent[] additionals = new IAnvilRecipeComponent[ModInventories.TileEntityBlackSmithsAnvil.MAX_ADDITIONALSLOTS];

    public AnvilRecipe() {
    }

    @Override
    public boolean matchesRecipe(@NotNull ItemStack[] craftingSlotContents, @NotNull ItemStack[] additionalSlotContents, int hammerUsagesLeft, int tongsUsagesLeft) {
        if (hammerUsagesLeft == 0)
            hammerUsagesLeft = 150;

        if (tongsUsagesLeft == 0)
            tongsUsagesLeft = 150;

        if ((hammer > 0) && (hammerUsagesLeft) < hammer)
            return false;

        if ((tongs > 0) && (tongsUsagesLeft < tongs))
            return false;

        if (craftingSlotContents.length > ModInventories.TileEntityBlackSmithsAnvil.MAX_CRAFTINGSLOTS) {
            return false;
        }

        if (additionalSlotContents.length > ModInventories.TileEntityBlackSmithsAnvil.MAX_ADDITIONALSLOTS) {
            return false;
        }

        if (!isShapeless) {
            for (int tSlotID = 0; tSlotID < ModInventories.TileEntityBlackSmithsAnvil.MAX_CRAFTINGSLOTS; tSlotID++) {
                ItemStack tSlotContent = craftingSlotContents[tSlotID];

                if (!tSlotContent.isEmpty()) {
                    if (components[tSlotID] == null) {
                        return false;
                    } else if (!components[tSlotID].isValidComponentForSlot(tSlotContent)) {
                        return false;
                    }
                } else if (components[tSlotID] != null) {
                    return false;
                }
            }
        } else {
            ArrayList<IAnvilRecipeComponent> tComponentList = new ArrayList<IAnvilRecipeComponent>(Arrays.asList(components.clone()));
            for (ItemStack tStack : craftingSlotContents) {
                boolean tFoundComponent = false;

                if (tStack == null) {
                    continue;
                }

                Iterator<IAnvilRecipeComponent> tIter = tComponentList.iterator();
                while (tIter.hasNext() && !tFoundComponent) {
                    IAnvilRecipeComponent tComponent = tIter.next();

                    if (tComponent != null) {
                        if (tComponent.isValidComponentForSlot(tStack)) {
                            tIter.remove();
                            tFoundComponent = true;
                        }
                    }
                }

                if (!tFoundComponent)
                    return false;
                else
                    continue;
            }

            for (IAnvilRecipeComponent tComponent : tComponentList)

            {
                if (tComponent != null)
                    return false;
            }
        }

        for (int tSlotID = 0; tSlotID < ModInventories.TileEntityBlackSmithsAnvil.MAX_ADDITIONALSLOTS; tSlotID++) {
            ItemStack tSlotContent = additionalSlotContents[tSlotID];

            if (!tSlotContent.isEmpty()) {
                if (additionals[tSlotID] == null) {
                    return false;
                } else if (!additionals[tSlotID].isValidComponentForSlot(tSlotContent)) {
                    return false;
                }
            } else if (additionals[tSlotID] != null) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void onRecipeUsed(IWatchableTileEntity entity) {
    }

    @Override
    @Nullable
    public IAnvilRecipeComponent getComponent(int componentIndex) {
        if (componentIndex >= ModInventories.TileEntityBlackSmithsAnvil.MAX_CRAFTINGSLOTS) {
            return null;
        }

        return components[componentIndex];
    }

    @Override
    @Nullable
    public IAnvilRecipeComponent getAdditionalComponent(int componentIndex) {
        if (componentIndex >= ModInventories.TileEntityBlackSmithsAnvil.MAX_ADDITIONALSLOTS) {
            return null;
        }

        return additionals[componentIndex];
    }

    @Override
    @Nullable
    public IAnvilRecipe setCraftingSlotContent(int slotIndex, IAnvilRecipeComponent component) {
        if (slotIndex >= ModInventories.TileEntityBlackSmithsAnvil.MAX_CRAFTINGSLOTS) {
            return null;
        }

        components[slotIndex] = component;

        return this;
    }

    @Override
    @Nullable
    public IAnvilRecipe setAdditionalCraftingSlotContent(int slotIndex, IAnvilRecipeComponent component) {
        if (slotIndex >= ModInventories.TileEntityBlackSmithsAnvil.MAX_ADDITIONALSLOTS) {
            return null;
        }

        additionals[slotIndex] = component;

        return this;
    }

    @Override
    @NotNull
    public IAnvilRecipe setResult(@NotNull ItemStack result) {
        resultAmount = result.getCount();
        this.result = result;

        return this;
    }

    @Override
    @NotNull
    public IAnvilRecipe setProgress(int newProgress) {
        targetProgress = newProgress;

        return this;
    }

    @Override
    @NotNull
    public IAnvilRecipe setTongUsage(int newUsage) {
        tongs = newUsage;

        return this;
    }

    @Override
    @NotNull
    public IAnvilRecipe setShapeLess() {
        isShapeless = true;
        return this;
    }

    @Override
    @Nullable
    public ItemStack getResult(ItemStack[] craftingSlotContents, ItemStack[] additionalSlotContents) {
        result.setCount(resultAmount);
        return result.copy();
    }

    @Override
    public boolean isShapeless() {
        return isShapeless;
    }

    @Override
    @NotNull
    public IAnvilRecipeComponent[] getComponents() {
        return components;
    }

    @Override
    @NotNull
    public IAnvilRecipeComponent[] getAdditionalComponents() {
        return additionals;
    }

    @Override
    public int getProgress() {
        return targetProgress;
    }

    @Override
    public int getHammerUsage() {
        return hammer;
    }

    @Override
    @NotNull
    public IAnvilRecipe setHammerUsage(int newUsage) {
        hammer = newUsage;

        return this;
    }

    @Override
    public boolean getUsesHammer() {
        return hammer > 0;
    }

    @Override
    public int getTongsUsage() {
        return tongs;
    }

    @Override
    public boolean getUsesTongs() {
        return tongs > 0;
    }
}

