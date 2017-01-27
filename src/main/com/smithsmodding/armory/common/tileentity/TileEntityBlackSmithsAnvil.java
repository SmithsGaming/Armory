package com.smithsmodding.armory.common.tileentity;

import com.smithsmodding.armory.api.IArmoryAPI;
import com.smithsmodding.armory.api.common.crafting.blacksmiths.component.IAnvilRecipeComponent;
import com.smithsmodding.armory.api.common.crafting.blacksmiths.recipe.IAnvilRecipe;
import com.smithsmodding.armory.api.util.references.ModInventories;
import com.smithsmodding.armory.api.util.references.ModLogger;
import com.smithsmodding.armory.api.util.references.References;
import com.smithsmodding.armory.common.block.BlockBlackSmithsAnvil;
import com.smithsmodding.armory.common.crafting.blacksmiths.recipe.VanillaAnvilRecipe;
import com.smithsmodding.armory.common.tileentity.guimanagers.TileEntityBlackSmithsAnvilGuiManager;
import com.smithsmodding.armory.common.tileentity.state.TileEntityBlackSmithsAnvilState;
import com.smithsmodding.smithscore.SmithsCore;
import com.smithsmodding.smithscore.common.inventory.IItemStorage;
import com.smithsmodding.smithscore.common.tileentity.TileEntitySmithsCore;
import com.smithsmodding.smithscore.util.common.helper.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.*;

/**
 * Created by Marc on 14.02.2016.
 */
public class TileEntityBlackSmithsAnvil extends TileEntitySmithsCore<TileEntityBlackSmithsAnvilState, TileEntityBlackSmithsAnvilGuiManager> implements IItemStorage, ITickable, com.smithsmodding.smithscore.common.tileentity.IWatchableTileEntity {

    @Nonnull
    private ItemStack[] craftingStacks = new ItemStack[ModInventories.TileEntityBlackSmithsAnvil.MAX_CRAFTINGSLOTS];
    @Nonnull
    private ItemStack[] outputStacks = new ItemStack[ModInventories.TileEntityBlackSmithsAnvil.MAX_OUTPUTSLOTS];
    @Nonnull
    private ItemStack[] hammerStacks = new ItemStack[ModInventories.TileEntityBlackSmithsAnvil.MAX_HAMMERSLOTS];
    @Nonnull
    private ItemStack[] tongStacks = new ItemStack[ModInventories.TileEntityBlackSmithsAnvil.MAX_TONGSLOTS];
    @Nonnull
    private ItemStack[] additionalCraftingStacks = new ItemStack[ModInventories.TileEntityBlackSmithsAnvil.MAX_ADDITIONALSLOTS];
    @Nonnull
    private ItemStack[] coolingStacks = new ItemStack[ModInventories.TileEntityBlackSmithsAnvil.MAX_COOLSLOTS];

    public TileEntityBlackSmithsAnvil() {
        clearInventory();
    }

    @Nonnull
    @Override
    protected TileEntityBlackSmithsAnvilGuiManager getInitialGuiManager() {
        return new TileEntityBlackSmithsAnvilGuiManager(this);
    }

    @Nonnull
    @Override
    protected TileEntityBlackSmithsAnvilState getInitialState() {
        return new TileEntityBlackSmithsAnvilState();
    }

    @Nonnull
    @Override
    public String getContainerID() {
        return References.InternalNames.TileEntities.ArmorsAnvil + "-" + getLocation().toString();
    }

    @Override
    public int getSizeInventory() {
        return ModInventories.TileEntityBlackSmithsAnvil.MAX_CRAFTINGSLOTS + ModInventories.TileEntityBlackSmithsAnvil.MAX_OUTPUTSLOTS + ModInventories.TileEntityBlackSmithsAnvil.MAX_HAMMERSLOTS + ModInventories.TileEntityBlackSmithsAnvil.MAX_TONGSLOTS + ModInventories.TileEntityBlackSmithsAnvil.MAX_BLUEPRINTLIBRARYSLOTS;
    }

    /**
     * Returns true if the Inventory is Empty.
     */
    @Override
    public boolean isEmpty() {
        for(int i = 0; i < getSizeInventory(); i++) {
            if (!getStackInSlot(i).isEmpty()) {
                return false;
            }
        }

        return true;
    }

    @Nullable
    @Override
    public ItemStack getStackInSlot(int pSlotID) {
        if (pSlotID < ModInventories.TileEntityBlackSmithsAnvil.MAX_CRAFTINGSLOTS)
            return craftingStacks[pSlotID];

        pSlotID -= ModInventories.TileEntityBlackSmithsAnvil.MAX_CRAFTINGSLOTS;

        if (pSlotID < ModInventories.TileEntityBlackSmithsAnvil.MAX_OUTPUTSLOTS)
            return outputStacks[pSlotID];

        pSlotID -= ModInventories.TileEntityBlackSmithsAnvil.MAX_OUTPUTSLOTS;

        if (pSlotID < ModInventories.TileEntityBlackSmithsAnvil.MAX_HAMMERSLOTS)
            return hammerStacks[pSlotID];

        pSlotID -= ModInventories.TileEntityBlackSmithsAnvil.MAX_OUTPUTSLOTS;

        if (pSlotID < ModInventories.TileEntityBlackSmithsAnvil.MAX_TONGSLOTS)
            return tongStacks[pSlotID];

        pSlotID -= ModInventories.TileEntityBlackSmithsAnvil.MAX_TONGSLOTS;

        if (pSlotID < ModInventories.TileEntityBlackSmithsAnvil.MAX_ADDITIONALSLOTS)
            return additionalCraftingStacks[pSlotID];

        pSlotID -= ModInventories.TileEntityBlackSmithsAnvil.MAX_ADDITIONALSLOTS;

        if (pSlotID < ModInventories.TileEntityBlackSmithsAnvil.MAX_COOLSLOTS)
            return coolingStacks[pSlotID];

        pSlotID -= ModInventories.TileEntityBlackSmithsAnvil.MAX_COOLSLOTS;


        return ItemStack.EMPTY;
    }

    @Nonnull
    @Override
    public ItemStack decrStackSize(int pSlotIndex, int pDecrAmount) {
        ItemStack tItemStack = getStackInSlot(pSlotIndex);
        if (tItemStack.isEmpty()) {
            return tItemStack;
        }
        if (tItemStack.getCount() < pDecrAmount) {
            setInventorySlotContents(pSlotIndex, ItemStack.EMPTY);
        } else {
            tItemStack = tItemStack.splitStack(pDecrAmount);
            if (tItemStack.getCount() == 0 || tItemStack.isEmpty()) {
                setInventorySlotContents(pSlotIndex, ItemStack.EMPTY);
            }
        }

        return tItemStack;
    }

    @Override
    public void clearInventory() {
        com.smithsmodding.armory.util.ItemStackHelper.InitializeItemStackArray(craftingStacks);
        com.smithsmodding.armory.util.ItemStackHelper.InitializeItemStackArray(outputStacks);
        com.smithsmodding.armory.util.ItemStackHelper.InitializeItemStackArray(hammerStacks);
        com.smithsmodding.armory.util.ItemStackHelper.InitializeItemStackArray(tongStacks);
        com.smithsmodding.armory.util.ItemStackHelper.InitializeItemStackArray(additionalCraftingStacks);
        com.smithsmodding.armory.util.ItemStackHelper.InitializeItemStackArray(coolingStacks);
    }

    @Override
    public void setInventorySlotContents(int pSlotID, ItemStack pNewItemStack) {
        if (pSlotID < 0)
            return;

        if (pNewItemStack == null)
            pNewItemStack = ItemStack.EMPTY;

        if (pSlotID < ModInventories.TileEntityBlackSmithsAnvil.MAX_CRAFTINGSLOTS) {
            craftingStacks[pSlotID] = pNewItemStack;
            if (isLoadingFromNBT())
                return;

            (getState()).setCraftingprogress(0);
            findValidRecipe();
            return;
        }


        pSlotID -= ModInventories.TileEntityBlackSmithsAnvil.MAX_CRAFTINGSLOTS;

        if (pSlotID < ModInventories.TileEntityBlackSmithsAnvil.MAX_OUTPUTSLOTS) {
            outputStacks[0] = pNewItemStack;
            return;
        }

        pSlotID -= ModInventories.TileEntityBlackSmithsAnvil.MAX_OUTPUTSLOTS;

        if (pSlotID < ModInventories.TileEntityBlackSmithsAnvil.MAX_HAMMERSLOTS) {
            hammerStacks[pSlotID] = pNewItemStack;
            if (isLoadingFromNBT())
                return;

            (getState()).setCraftingprogress(0);
            findValidRecipe();
            return;
        }

        pSlotID -= ModInventories.TileEntityBlackSmithsAnvil.MAX_OUTPUTSLOTS;

        if (pSlotID < ModInventories.TileEntityBlackSmithsAnvil.MAX_TONGSLOTS) {
            tongStacks[pSlotID] = pNewItemStack;
            if (isLoadingFromNBT())
                return;

            (getState()).setCraftingprogress(0);
            findValidRecipe();
            return;
        }

        pSlotID -= ModInventories.TileEntityBlackSmithsAnvil.MAX_TONGSLOTS;

        if (pSlotID < ModInventories.TileEntityBlackSmithsAnvil.MAX_ADDITIONALSLOTS) {
            additionalCraftingStacks[pSlotID] = pNewItemStack;
            if (isLoadingFromNBT())
                return;

            (getState()).setCraftingprogress(0);
            findValidRecipe();
            return;
        }

        pSlotID -= ModInventories.TileEntityBlackSmithsAnvil.MAX_ADDITIONALSLOTS;

        if (pSlotID < ModInventories.TileEntityBlackSmithsAnvil.MAX_COOLSLOTS) {
            coolingStacks[pSlotID] = pNewItemStack;
            if (isLoadingFromNBT())
                return;

            (getState()).setCraftingprogress(0);
            return;
        }

        pSlotID -= ModInventories.TileEntityBlackSmithsAnvil.MAX_COOLSLOTS;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public void markInventoryDirty() {
        this.markDirty();
    }

    @Override
    public boolean isItemValidForSlot(int pSlotID, ItemStack pTargetStack) {
        if (pSlotID < ModInventories.TileEntityBlackSmithsAnvil.MAX_CRAFTINGSLOTS)
            return true;

        pSlotID -= ModInventories.TileEntityBlackSmithsAnvil.MAX_CRAFTINGSLOTS;

        if (pSlotID < ModInventories.TileEntityBlackSmithsAnvil.MAX_OUTPUTSLOTS)
            return false;

        pSlotID -= ModInventories.TileEntityBlackSmithsAnvil.MAX_OUTPUTSLOTS;

        if (pSlotID < ModInventories.TileEntityBlackSmithsAnvil.MAX_HAMMERSLOTS)
            //return (pTargetStack.getObject() instanceof ItemHammer);
            return false;

        pSlotID -= ModInventories.TileEntityBlackSmithsAnvil.MAX_OUTPUTSLOTS;

        if (pSlotID < ModInventories.TileEntityBlackSmithsAnvil.MAX_TONGSLOTS)
            //return (pTargetStack.getObject() instanceof ItemTongs);
            return false;


        pSlotID -= ModInventories.TileEntityBlackSmithsAnvil.MAX_TONGSLOTS;

        if (pSlotID > ModInventories.TileEntityBlackSmithsAnvil.MAX_ADDITIONALSLOTS)
            return false;

        pSlotID -= ModInventories.TileEntityBlackSmithsAnvil.MAX_ADDITIONALSLOTS;

        if (pSlotID < ModInventories.TileEntityBlackSmithsAnvil.MAX_COOLSLOTS)
            return false;

        pSlotID -= ModInventories.TileEntityBlackSmithsAnvil.MAX_COOLSLOTS;

        if (pSlotID < ModInventories.TileEntityBlackSmithsAnvil.MAX_BLUEPRINTLIBRARYSLOTS)
            return false;

        return false;
    }

    @Override
    public void update() {
        boolean tUpdated = false;

        if (getCurrentRecipe() != null) {
            (getState()).setCraftingprogress((getState()).getCraftingprogress() + (1f / 20f));

            if (((getState()).getCraftingprogress() >= getCurrentRecipe().getProgress()) && !getWorld().isRemote) {
                if (outputStacks[0].isEmpty()) {
                    outputStacks[0].grow(getCurrentRecipe().getResult(craftingStacks, additionalCraftingStacks).getCount());
                } else {
                    outputStacks[0] = getCurrentRecipe().getResult(craftingStacks, additionalCraftingStacks);
                    if (!(getState()).getItemName().equals("")) {
                        outputStacks[0].getTagCompound().setString(References.NBTTagCompoundData.CustomName, (getState()).getItemName());
                    }
                }

                ProcessPerformedCrafting();
                setCurrentRecipe(null);
                (getState()).setCraftingprogress(0);

                tUpdated = true;

            }
        }

        if (!getWorld().isRemote) {
            markDirty();
        }
    }


    public void findValidRecipe() {
        if ((getState()).isProcessingCraftingResult())
            return;

        setCurrentRecipe(null);

        if (SmithsCore.isInDevenvironment()) {
            ModLogger.getInstance().info("Checking Recipe for:");
            for (ItemStack tStack : craftingStacks) {
                ModLogger.getInstance().info("   " + ItemStackHelper.toString(tStack));
            }
        }

        int tHammerUsagesLeft = -1;
        int tTongUsagesLeft = -1;

        if (hammerStacks[0] != null)
            tHammerUsagesLeft = hammerStacks[0].getItemDamage();

        if (tongStacks[0] != null)
            tTongUsagesLeft = tongStacks[0].getItemDamage();

        for (IAnvilRecipe tRecipe : IArmoryAPI.Holder.getInstance().getRegistryManager().getAnvilRecipeRegistry()) {
            if (tRecipe.matchesRecipe(craftingStacks, additionalCraftingStacks, tHammerUsagesLeft, tTongUsagesLeft)) {
                if (outputStacks[0] != null) {
                    ItemStack tResultStack = tRecipe.getResult(craftingStacks, additionalCraftingStacks);

                    if (!ItemStackHelper.equalsIgnoreStackSize(tResultStack, outputStacks[0]))
                        continue;

                    if ((tResultStack.getCount() + outputStacks[0].getCount()) <= outputStacks[0].getMaxStackSize()) {
                        setCurrentRecipe(tRecipe);
                        return;
                    }
                } else {
                    setCurrentRecipe(tRecipe);
                    return;
                }
            }
        }

        if (!craftingStacks[11].isEmpty()) {
            for (int tCraftingStack = 0; tCraftingStack < ModInventories.TileEntityBlackSmithsAnvil.MAX_CRAFTINGSLOTS; tCraftingStack++) {
                if (tCraftingStack == 11 || tCraftingStack == 13) {
                    continue;
                }

                if (!craftingStacks[tCraftingStack].isEmpty()) {
                    setCurrentRecipe(null);
                    return;
                }
            }

            for (ItemStack tStack : additionalCraftingStacks) {
                if (!tStack.isEmpty()) {
                    setCurrentRecipe(null);
                    return;
                }
            }


            VanillaAnvilRecipe tRecipe = new VanillaAnvilRecipe(this);
            if (tRecipe.matchesRecipe(craftingStacks, additionalCraftingStacks, tHammerUsagesLeft, tTongUsagesLeft)) {
                setCurrentRecipe(tRecipe);
                return;
            }
        }

        setCurrentRecipe(null);
    }

    @Nullable
    public IAnvilRecipe getCurrentRecipe() {
        return (getState()).getRecipe();
    }

    public void setCurrentRecipe(IAnvilRecipe recipe) {
        (getState()).setRecipe(recipe);
    }

    public void ProcessPerformedCrafting() {
        if (getCurrentRecipe() == null) {
            return;
        }

        (getState()).setProcessingCraftingResult(true);

        if (getCurrentRecipe() instanceof VanillaAnvilRecipe) {
            ((VanillaAnvilRecipe) getCurrentRecipe()).ProcessPerformedCrafting();
        } else if (getCurrentRecipe().isShapeless()) {
            ArrayList<IAnvilRecipeComponent> tComponentList = new ArrayList<>(Arrays.asList(getCurrentRecipe().getComponents().clone()));
            for (int tSlotIndex = 0; tSlotIndex < ModInventories.TileEntityBlackSmithsAnvil.MAX_CRAFTINGSLOTS; tSlotIndex++) {
                if (craftingStacks[tSlotIndex] != null) {
                    boolean tProcessedStack = false;

                    Iterator<IAnvilRecipeComponent> tIter = tComponentList.iterator();
                    while (tIter.hasNext() && !tProcessedStack) {
                        IAnvilRecipeComponent tComponent = tIter.next();

                        if (tComponent != null) {
                            craftingStacks[tSlotIndex].setCount(tComponent.getResultingStackSizeForComponent(craftingStacks[tSlotIndex]));
                            if (craftingStacks[tSlotIndex].getCount() == 0 || craftingStacks[tSlotIndex].isEmpty()) {
                                craftingStacks[tSlotIndex] = ItemStack.EMPTY;
                            }

                            tProcessedStack = true;
                        }
                    }
                }
            }
        } else {
            for (int tSlotIndex = 0; tSlotIndex < ModInventories.TileEntityBlackSmithsAnvil.MAX_CRAFTINGSLOTS; tSlotIndex++) {
                if (craftingStacks[tSlotIndex].isEmpty())
                    continue;

                IAnvilRecipeComponent tTargetComponent = getCurrentRecipe().getComponent(tSlotIndex);
                if (tTargetComponent == null)
                    continue;

                craftingStacks[tSlotIndex].setCount(tTargetComponent.getResultingStackSizeForComponent(craftingStacks[tSlotIndex]));
                if (craftingStacks[tSlotIndex].getCount() < 1 || craftingStacks[tSlotIndex].isEmpty())
                    craftingStacks[tSlotIndex] = ItemStack.EMPTY;
            }
        }

        for (int tSlotIndex = 0; tSlotIndex < ModInventories.TileEntityBlackSmithsAnvil.MAX_ADDITIONALSLOTS; tSlotIndex++) {
            if (additionalCraftingStacks[tSlotIndex].isEmpty())
                continue;

            IAnvilRecipeComponent tTargetComponent = getCurrentRecipe().getAdditionalComponent(tSlotIndex);
            if (tTargetComponent == null)
                continue;

            additionalCraftingStacks[tSlotIndex].setCount(tTargetComponent.getResultingStackSizeForComponent(additionalCraftingStacks[tSlotIndex]));
            if (additionalCraftingStacks[tSlotIndex].getCount() < 1 || additionalCraftingStacks[tSlotIndex].isEmpty())
                additionalCraftingStacks[tSlotIndex] = ItemStack.EMPTY;
        }

        if (getCurrentRecipe().getUsesHammer()) {
            if (hammerStacks[0].isItemDamaged() == false) {
                hammerStacks[0].setItemDamage(150);
            }
            hammerStacks[0].setItemDamage(hammerStacks[0].getItemDamage() - getCurrentRecipe().getHammerUsage());
            if (hammerStacks[0].getItemDamage() == 0) {
                hammerStacks[0] = ItemStack.EMPTY;
            }
        }

        if (getCurrentRecipe().getUsesTongs()) {
            if (tongStacks[0].isItemDamaged() == false) {
                tongStacks[0].setItemDamage(150);
            }
            tongStacks[0].setItemDamage(tongStacks[0].getItemDamage() - getCurrentRecipe().getTongsUsage());
            if (tongStacks[0].getItemDamage() == 0) {
                tongStacks[0] = ItemStack.EMPTY;
            }
        }

        getCurrentRecipe().onRecipeUsed(this);

        (getState()).setProcessingCraftingResult(false);
    }

    @Nonnull
    public AnvilType getCurrentAnvilType() {
        boolean DEBUG = false;
        if (DEBUG)
            return AnvilType.Standard;

        boolean tFoundCoolingBasin = false;
        boolean tFoundHelperRack = false;
        if (getWorld().getBlockState(getPos()).getValue(BlockBlackSmithsAnvil.FACING) == EnumFacing.NORTH || getWorld().getBlockState(getPos()).getValue(BlockBlackSmithsAnvil.FACING) == EnumFacing.SOUTH) {
            TileEntity tLeftTE = getWorld().getTileEntity(getPos().offset(EnumFacing.EAST));
            TileEntity tRightTE = getWorld().getTileEntity(getPos().offset(EnumFacing.WEST));

            /*
            TODO: When the updgrade system for the Anviul comes into play uncomment this.
            if (tLeftTE != null) {
                if (tLeftTE instanceof TileEntityCoolingBasin) {
                    tFoundCoolingBasin = true;
                }

                if (tLeftTE instanceof TileEntityArmorsRack) {
                    tFoundHelperRack = true;
                }
            }

            if (tRightTE != null) {
                if (tRightTE instanceof TileEntityCoolingBasin) {
                    tFoundCoolingBasin = true;
                }

                if (tRightTE instanceof TileEntityArmorsRack) {
                    tFoundHelperRack = true;
                }
            }
            */
        } else {
            TileEntity tLeftTE = getWorld().getTileEntity(getPos().offset(EnumFacing.NORTH));
            TileEntity tRightTE = getWorld().getTileEntity(getPos().offset(EnumFacing.SOUTH));

            /*
            TODO: When the updgrade system for the Anviul comes into play uncomment this.
            if (tLeftTE != null) {
                if (tLeftTE instanceof TileEntityCoolingBasin) {
                    tFoundCoolingBasin = true;
                }

                if (tLeftTE instanceof TileEntityArmorsRack) {
                    tFoundHelperRack = true;
                }
            }

            if (tRightTE != null) {
                if (tRightTE instanceof TileEntityCoolingBasin) {
                    tFoundCoolingBasin = true;
                }

                if (tRightTE instanceof TileEntityArmorsRack) {
                    tFoundHelperRack = true;
                }
            }
            */
        }

        if (!tFoundCoolingBasin && !tFoundHelperRack)
            return AnvilType.Minimal;

        if (tFoundCoolingBasin && !tFoundHelperRack)
            return AnvilType.Cooling;

        if (!tFoundCoolingBasin && tFoundHelperRack)
            return AnvilType.Extended;

        return AnvilType.Standard;
    }

    //TODO: FIX ME!
    @Override
    public Collection<UUID> getWatchingPlayers() {
        return (getManager()).getWatchingPlayers();
    }

    //TODO: FIX ME!
    @Override
    public int getConnectedPlayerCount() {
        return getWatchingPlayers().size();
    }

    public enum AnvilType {
        Minimal,
        Extended,
        Cooling,
        Standard
    }

}
