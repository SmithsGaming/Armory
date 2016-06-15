package com.smithsmodding.armory.api.crafting.blacksmiths.recipe;

import com.smithsmodding.armory.api.crafting.blacksmiths.component.IAnvilRecipeComponent;
import com.smithsmodding.armory.common.tileentity.TileEntityBlackSmithsAnvil;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Created by Orion
 * Created on 02.05.2015
 * 12:57
 * <p/>
 * Copyrighted according to Project specific license
 */
public class AnvilRecipe {
    protected int iResultAmount = 1;
    protected ItemStack iResult;
    private int iTargetProgress;
    private int iHammerUsage;
    private int iTongUsage;
    private String iKnowledgeName;
    private String iBlueprintName;
    private boolean iIsShapeLess = false;
    private IAnvilRecipeComponent[] iComponents = new IAnvilRecipeComponent[TileEntityBlackSmithsAnvil.MAX_CRAFTINGSLOTS];
    private IAnvilRecipeComponent[] iAdditionalComponents = new IAnvilRecipeComponent[TileEntityBlackSmithsAnvil.MAX_ADDITIONALSLOTS];
    private String iInternalID;

    public AnvilRecipe() {
    }

    public boolean matchesRecipe(ItemStack[] pCraftingSlotContents, ItemStack[] pAdditionalSlotContents, int pHammerUsagesLeft, int pTongsUsagesLeft) {
        if (pHammerUsagesLeft == 0)
            pHammerUsagesLeft = 150;

        if (pTongsUsagesLeft == 0)
            pTongsUsagesLeft = 150;

        if ((iHammerUsage > 0) && (pHammerUsagesLeft) < iHammerUsage)
            return false;

        if ((iTongUsage > 0) && (pTongsUsagesLeft < iTongUsage))
            return false;

        if (pCraftingSlotContents.length > TileEntityBlackSmithsAnvil.MAX_CRAFTINGSLOTS) {
            return false;
        }

        if (pAdditionalSlotContents.length > TileEntityBlackSmithsAnvil.MAX_ADDITIONALSLOTS) {
            return false;
        }

        if (!iIsShapeLess) {
            for (int tSlotID = 0; tSlotID < TileEntityBlackSmithsAnvil.MAX_CRAFTINGSLOTS; tSlotID++) {
                ItemStack tSlotContent = pCraftingSlotContents[tSlotID];

                if (tSlotContent != null) {
                    if (iComponents[tSlotID] == null) {
                        return false;
                    } else if (!iComponents[tSlotID].isValidComponentForSlot(tSlotContent)) {
                        return false;
                    }
                } else if (iComponents[tSlotID] != null) {
                    return false;
                }
            }
        } else {
            ArrayList<IAnvilRecipeComponent> tComponentList = new ArrayList<IAnvilRecipeComponent>(Arrays.asList(iComponents.clone()));
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

        for (int tSlotID = 0; tSlotID < TileEntityBlackSmithsAnvil.MAX_ADDITIONALSLOTS; tSlotID++) {
            ItemStack tSlotContent = pAdditionalSlotContents[tSlotID];

            if (tSlotContent != null) {
                if (iAdditionalComponents[tSlotID] == null) {
                    return false;
                } else if (!iAdditionalComponents[tSlotID].isValidComponentForSlot(tSlotContent)) {
                    return false;
                }
            } else if (iAdditionalComponents[tSlotID] != null) {
                return false;
            }
        }

        return true;
    }

    public void onRecipeUsed(TileEntityBlackSmithsAnvil pEntity) {
    }

    public String getInternalID() {
        return iInternalID;
    }

    public IAnvilRecipeComponent getComponent(int pComponentIndex) {
        if (pComponentIndex >= TileEntityBlackSmithsAnvil.MAX_CRAFTINGSLOTS) {
            return null;
        }

        return iComponents[pComponentIndex];
    }

    public IAnvilRecipeComponent getAdditionalComponent(int pComponentIndex) {
        if (pComponentIndex >= TileEntityBlackSmithsAnvil.MAX_ADDITIONALSLOTS) {
            return null;
        }

        return iAdditionalComponents[pComponentIndex];
    }

    public AnvilRecipe setInternalName(String pNewInternalID) {
        this.iInternalID = pNewInternalID;

        return this;
    }

    public AnvilRecipe setCraftingSlotContent(int pSlotIndex, IAnvilRecipeComponent pComponent) {
        if (pSlotIndex >= TileEntityBlackSmithsAnvil.MAX_CRAFTINGSLOTS) {
            return null;
        }

        iComponents[pSlotIndex] = pComponent;

        return this;
    }

    public AnvilRecipe setAdditionalCraftingSlotContent(int pSlotIndex, IAnvilRecipeComponent pComponent) {
        if (pSlotIndex >= TileEntityBlackSmithsAnvil.MAX_ADDITIONALSLOTS) {
            return null;
        }

        iAdditionalComponents[pSlotIndex] = pComponent;

        return this;
    }

    public AnvilRecipe setResult(ItemStack pResult) {
        iResultAmount = pResult.stackSize;
        iResult = pResult;

        return this;
    }

    public AnvilRecipe setProgress(int pNewProgress) {
        iTargetProgress = pNewProgress;

        return this;
    }

    public AnvilRecipe setTongUsage(int pNewUsage) {
        iTongUsage = pNewUsage;

        return this;
    }

    public AnvilRecipe setShapeLess() {
        iIsShapeLess = true;
        return this;
    }

    public AnvilRecipe setBluePrintName(String pName) {
        iBlueprintName = pName;
        return this;
    }

    public ItemStack getResult(ItemStack[] pCraftingSlotContents, ItemStack[] pAdditionalSlotContents) {
        iResult.stackSize = iResultAmount;
        return iResult;
    }

    public boolean isShapeless() {
        return iIsShapeLess;
    }

    public IAnvilRecipeComponent[] getComponents() {
        return iComponents;
    }

    public IAnvilRecipeComponent[] getAdditionalComponents() {
        return iAdditionalComponents;
    }

    public int getMinimumProgress() {
        return iTargetProgress;
    }

    public int getHammerUsage() {
        return iHammerUsage;
    }

    public AnvilRecipe setHammerUsage(int pNewUsage) {
        iHammerUsage = pNewUsage;

        return this;
    }

    public boolean getUsesHammer() {
        return iHammerUsage > 0;
    }

    public int getTongsUsage() {
        return iTongUsage;
    }

    public boolean getUsesTongs() {
        return iTongUsage > 0;
    }
}

