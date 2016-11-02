package com.smithsmodding.armory.api.crafting.blacksmiths.recipe;

import com.smithsmodding.armory.api.crafting.blacksmiths.component.IAnvilRecipeComponent;
import com.smithsmodding.armory.api.util.references.ModInventories;
import com.smithsmodding.smithscore.common.tileentity.IWatchableTileEntity;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
public class AnvilRecipe {
    protected int resultAmount = 1;
    protected ItemStack result;
    private int targetProgress;
    private int hammer;
    private int tongs;
    private boolean isShapeless = false;
    @NotNull
    private IAnvilRecipeComponent[] components = new IAnvilRecipeComponent[ModInventories.TileEntityBlackSmithsAnvil.MAX_CRAFTINGSLOTS];
    @NotNull
    private IAnvilRecipeComponent[] additionals = new IAnvilRecipeComponent[ModInventories.TileEntityBlackSmithsAnvil.MAX_ADDITIONALSLOTS];
    private String Id;

    public AnvilRecipe() {
    }

    public boolean matchesRecipe(@NotNull ItemStack[] pCraftingSlotContents, @NotNull ItemStack[] pAdditionalSlotContents, int pHammerUsagesLeft, int pTongsUsagesLeft) {
        if (pHammerUsagesLeft == 0)
            pHammerUsagesLeft = 150;

        if (pTongsUsagesLeft == 0)
            pTongsUsagesLeft = 150;

        if ((hammer > 0) && (pHammerUsagesLeft) < hammer)
            return false;

        if ((tongs > 0) && (pTongsUsagesLeft < tongs))
            return false;

        if (pCraftingSlotContents.length > ModInventories.TileEntityBlackSmithsAnvil.MAX_CRAFTINGSLOTS) {
            return false;
        }

        if (pAdditionalSlotContents.length > ModInventories.TileEntityBlackSmithsAnvil.MAX_ADDITIONALSLOTS) {
            return false;
        }

        if (!isShapeless) {
            for (int tSlotID = 0; tSlotID < ModInventories.TileEntityBlackSmithsAnvil.MAX_CRAFTINGSLOTS; tSlotID++) {
                ItemStack tSlotContent = pCraftingSlotContents[tSlotID];

                if (tSlotContent != null) {
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
            for (ItemStack tStack : pCraftingSlotContents) {
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
            ItemStack tSlotContent = pAdditionalSlotContents[tSlotID];

            if (tSlotContent != null) {
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

    public void onRecipeUsed(IWatchableTileEntity entity) {
    }

    public String getInternalID() {
        return Id;
    }

    @Nullable
    public IAnvilRecipeComponent getComponent(int pComponentIndex) {
        if (pComponentIndex >= ModInventories.TileEntityBlackSmithsAnvil.MAX_CRAFTINGSLOTS) {
            return null;
        }

        return components[pComponentIndex];
    }

    @Nullable
    public IAnvilRecipeComponent getAdditionalComponent(int pComponentIndex) {
        if (pComponentIndex >= ModInventories.TileEntityBlackSmithsAnvil.MAX_ADDITIONALSLOTS) {
            return null;
        }

        return additionals[pComponentIndex];
    }

    @NotNull
    public AnvilRecipe setInternalName(String pNewInternalID) {
        this.Id = pNewInternalID;

        return this;
    }

    @Nullable
    public AnvilRecipe setCraftingSlotContent(int pSlotIndex, IAnvilRecipeComponent pComponent) {
        if (pSlotIndex >= ModInventories.TileEntityBlackSmithsAnvil.MAX_CRAFTINGSLOTS) {
            return null;
        }

        components[pSlotIndex] = pComponent;

        return this;
    }

    @Nullable
    public AnvilRecipe setAdditionalCraftingSlotContent(int pSlotIndex, IAnvilRecipeComponent pComponent) {
        if (pSlotIndex >= ModInventories.TileEntityBlackSmithsAnvil.MAX_ADDITIONALSLOTS) {
            return null;
        }

        additionals[pSlotIndex] = pComponent;

        return this;
    }

    @NotNull
    public AnvilRecipe setResult(@NotNull ItemStack pResult) {
        resultAmount = pResult.stackSize;
        result = pResult;

        return this;
    }

    @NotNull
    public AnvilRecipe setProgress(int pNewProgress) {
        targetProgress = pNewProgress;

        return this;
    }

    @NotNull
    public AnvilRecipe setTongUsage(int pNewUsage) {
        tongs = pNewUsage;

        return this;
    }

    @NotNull
    public AnvilRecipe setShapeLess() {
        isShapeless = true;
        return this;
    }

    @Nullable
    public ItemStack getResult(ItemStack[] pCraftingSlotContents, ItemStack[] pAdditionalSlotContents) {
        result.stackSize = resultAmount;
        return result;
    }

    public boolean isShapeless() {
        return isShapeless;
    }

    @NotNull
    public IAnvilRecipeComponent[] getComponents() {
        return components;
    }

    @NotNull
    public IAnvilRecipeComponent[] getAdditionalComponents() {
        return additionals;
    }

    public int getMinimumProgress() {
        return targetProgress;
    }

    public int getHammerUsage() {
        return hammer;
    }

    @NotNull
    public AnvilRecipe setHammerUsage(int pNewUsage) {
        hammer = pNewUsage;

        return this;
    }

    public boolean getUsesHammer() {
        return hammer > 0;
    }

    public int getTongsUsage() {
        return tongs;
    }

    public boolean getUsesTongs() {
        return tongs > 0;
    }
}

