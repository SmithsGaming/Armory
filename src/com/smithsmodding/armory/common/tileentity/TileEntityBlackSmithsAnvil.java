package com.smithsmodding.armory.common.tileentity;

import com.smithsmodding.armory.Armory;
import com.smithsmodding.armory.api.crafting.blacksmiths.component.IAnvilRecipeComponent;
import com.smithsmodding.armory.api.crafting.blacksmiths.recipe.AnvilRecipe;
import com.smithsmodding.armory.api.crafting.blacksmiths.recipe.VanillaAnvilRecipe;
import com.smithsmodding.armory.common.registry.AnvilRecipeRegistry;
import com.smithsmodding.armory.common.tileentity.guimanagers.BlackSmithsAnvilGuiManager;
import com.smithsmodding.armory.common.tileentity.state.BlackSmithsAnvilState;
import com.smithsmodding.armory.util.References;
import com.smithsmodding.smithscore.SmithsCore;
import com.smithsmodding.smithscore.util.common.ItemStackHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;

import java.util.*;

/**
 * Created by Marc on 14.02.2016.
 */
public class TileEntityBlackSmithsAnvil extends TileEntityArmory implements IInventory, ITickable {
    public static int MAX_CRAFTINGSLOTS = 25;
    public static int MAX_OUTPUTSLOTS = 1;
    public static int MAX_HAMMERSLOTS = 1;
    public static int MAX_TONGSLOTS = 1;
    public static int MAX_ADDITIONALSLOTS = 3;
    public static int MAX_COOLSLOTS = 1;
    public static int MAX_BLUEPRINTLIBRARYSLOTS = 1;
    public static int MAX_SLOTS = MAX_CRAFTINGSLOTS + MAX_OUTPUTSLOTS + MAX_HAMMERSLOTS + MAX_TONGSLOTS + MAX_ADDITIONALSLOTS + MAX_COOLSLOTS + MAX_BLUEPRINTLIBRARYSLOTS;

    private ItemStack[] craftinStacks = new ItemStack[MAX_CRAFTINGSLOTS];
    private ItemStack[] outputStacks = new ItemStack[MAX_OUTPUTSLOTS];
    private ItemStack[] hammerStacks = new ItemStack[MAX_HAMMERSLOTS];
    private ItemStack[] tongStacks = new ItemStack[MAX_TONGSLOTS];
    private ItemStack[] additionalCraftingStacks = new ItemStack[MAX_ADDITIONALSLOTS];
    private ItemStack[] coolingStacks = new ItemStack[MAX_COOLSLOTS];

    private int connectedPlayerCount = 0;

    public TileEntityBlackSmithsAnvil() {
        super(new BlackSmithsAnvilState(), null);
        setManager(new BlackSmithsAnvilGuiManager(this));
    }

    @Override
    public String getContainerID() {
        return References.InternalNames.TileEntities.ArmorsAnvil + "-" + getLocation().toString();
    }

    @Override
    public int getSizeInventory() {
        return MAX_CRAFTINGSLOTS + MAX_OUTPUTSLOTS + MAX_HAMMERSLOTS + MAX_TONGSLOTS + MAX_BLUEPRINTLIBRARYSLOTS;
    }

    @Override
    public ItemStack getStackInSlot(int pSlotID) {
        if (pSlotID < MAX_CRAFTINGSLOTS)
            return craftinStacks[pSlotID];

        pSlotID -= MAX_CRAFTINGSLOTS;

        if (pSlotID < MAX_OUTPUTSLOTS)
            return outputStacks[pSlotID];

        pSlotID -= MAX_OUTPUTSLOTS;

        if (pSlotID < MAX_HAMMERSLOTS)
            return hammerStacks[pSlotID];

        pSlotID -= MAX_OUTPUTSLOTS;

        if (pSlotID < MAX_TONGSLOTS)
            return tongStacks[pSlotID];

        pSlotID -= MAX_TONGSLOTS;

        if (pSlotID < MAX_ADDITIONALSLOTS)
            return additionalCraftingStacks[pSlotID];

        pSlotID -= MAX_ADDITIONALSLOTS;

        if (pSlotID < MAX_COOLSLOTS)
            return coolingStacks[pSlotID];

        pSlotID -= MAX_COOLSLOTS;


        return null;
    }

    @Override
    public ItemStack decrStackSize(int pSlotIndex, int pDecrAmount) {
        ItemStack tItemStack = getStackInSlot(pSlotIndex);
        if (tItemStack == null) {
            return tItemStack;
        }
        if (tItemStack.stackSize < pDecrAmount) {
            setInventorySlotContents(pSlotIndex, null);
        } else {
            tItemStack = tItemStack.splitStack(pDecrAmount);
            if (tItemStack.stackSize == 0) {
                setInventorySlotContents(pSlotIndex, null);
            }
        }

        return tItemStack;
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        ItemStack stack = getStackInSlot(index);
        setInventorySlotContents(index, null);
        return stack;
    }

    /*
    @Override
    public Object getGUIComponentRelatedObject(String pComponentID) {
        if (pComponentID.equals("Gui.Anvil.Cooling.Tank"))
            return new FluidStack(FluidRegistry.WATER, 3500);

        if (pComponentID.equals(References.InternalNames.GUIComponents.Anvil.EXPERIENCELABEL + ".Value")) {
            if (currentRecipe == null)
                return -1;

            if (!(currentRecipe instanceof VanillaAnvilRecipe))
                return -1;

            return ((VanillaAnvilRecipe) currentRecipe).getRequiredLevelsPerPlayer();
        }

        return null;
    }
    */

    @Override
    public void setInventorySlotContents(int pSlotID, ItemStack pNewItemStack) {
        if (pSlotID < 0)
            return;

        if (pSlotID < MAX_CRAFTINGSLOTS) {
            craftinStacks[pSlotID] = pNewItemStack;
            ((BlackSmithsAnvilState) getState()).setCraftingprogress(0);
            findValidRecipe();
            return;
        }


        pSlotID -= MAX_CRAFTINGSLOTS;

        if (pSlotID < MAX_OUTPUTSLOTS) {
            outputStacks[0] = pNewItemStack;
            return;
        }

        pSlotID -= MAX_OUTPUTSLOTS;

        if (pSlotID < MAX_HAMMERSLOTS) {
            hammerStacks[pSlotID] = pNewItemStack;
            ((BlackSmithsAnvilState) getState()).setCraftingprogress(0);
            findValidRecipe();
            return;
        }

        pSlotID -= MAX_OUTPUTSLOTS;

        if (pSlotID < MAX_TONGSLOTS) {
            tongStacks[pSlotID] = pNewItemStack;
            ((BlackSmithsAnvilState) getState()).setCraftingprogress(0);
            findValidRecipe();
            return;
        }

        pSlotID -= MAX_TONGSLOTS;

        if (pSlotID < MAX_ADDITIONALSLOTS) {
            additionalCraftingStacks[pSlotID] = pNewItemStack;
            ((BlackSmithsAnvilState) getState()).setCraftingprogress(0);
            findValidRecipe();
            return;
        }

        pSlotID -= MAX_ADDITIONALSLOTS;

        if (pSlotID < MAX_COOLSLOTS) {
            coolingStacks[pSlotID] = pNewItemStack;
            ((BlackSmithsAnvilState) getState()).setCraftingprogress(0);
            return;
        }

        pSlotID -= MAX_COOLSLOTS;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer pPlayer) {
        return true;
    }

    @Override
    public void openInventory(EntityPlayer player) {
        //NOOP Tracked by SmithsCore
    }

    @Override
    public void closeInventory(EntityPlayer player) {
        //NOOP Tracked by SmithsCore
    }

    @Override
    public boolean isItemValidForSlot(int pSlotID, ItemStack pTargetStack) {
        if (pSlotID < MAX_CRAFTINGSLOTS)
            return true;

        pSlotID -= MAX_CRAFTINGSLOTS;

        if (pSlotID < MAX_OUTPUTSLOTS)
            return false;

        pSlotID -= MAX_OUTPUTSLOTS;

        if (pSlotID < MAX_HAMMERSLOTS)
            //return (pTargetStack.getItem() instanceof ItemHammer);
            return false;

        pSlotID -= MAX_OUTPUTSLOTS;

        if (pSlotID < MAX_TONGSLOTS)
            //return (pTargetStack.getItem() instanceof ItemTongs);
            return false;


        pSlotID -= MAX_TONGSLOTS;

        if (pSlotID > MAX_ADDITIONALSLOTS)
            return false;

        pSlotID -= MAX_ADDITIONALSLOTS;

        if (pSlotID < MAX_COOLSLOTS)
            return false;

        pSlotID -= MAX_COOLSLOTS;

        if (pSlotID < MAX_BLUEPRINTLIBRARYSLOTS)
            return false;

        return false;
    }

    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) {

    }

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public void clear() {

    }

    @Override
    public void update() {
        boolean tUpdated = false;

        if (getCurrentRecipe() != null) {
            ((BlackSmithsAnvilState) getState()).setCraftingprogress(((BlackSmithsAnvilState) getState()).getCraftingprogress() + (1f / 20f));

            if ((((BlackSmithsAnvilState) getState()).getCraftingprogress() >= getCurrentRecipe().getMinimumProgress()) && !worldObj.isRemote) {
                if (outputStacks[0] != null) {
                    outputStacks[0].stackSize += getCurrentRecipe().getResult(craftinStacks, additionalCraftingStacks).stackSize;
                } else {
                    outputStacks[0] = getCurrentRecipe().getResult(craftinStacks, additionalCraftingStacks);
                    if (!((BlackSmithsAnvilState) getState()).getItemName().equals("")) {
                        outputStacks[0].getTagCompound().setString(References.NBTTagCompoundData.CustomName, ((BlackSmithsAnvilState) getState()).getItemName());
                    }
                }

                ProcessPerformedCrafting();
                setCurrentRecipe(null);
                ((BlackSmithsAnvilState) getState()).setCraftingprogress(0);

                tUpdated = true;

            }

            if (!worldObj.isRemote && tUpdated) {
                markDirty();
            }
        }
    }


    public void findValidRecipe() {
        if (((BlackSmithsAnvilState) getState()).isProcessingCraftingResult())
            return;

        setCurrentRecipe(null);

        if (SmithsCore.isInDevenvironment()) {
            Armory.getLogger().info("Checking Recipe for:");
            for (ItemStack tStack : craftinStacks) {
                Armory.getLogger().info("   " + ItemStackHelper.toString(tStack));
            }
        }

        int tHammerUsagesLeft = -1;
        int tTongUsagesLeft = -1;

        if (hammerStacks[0] != null)
            tHammerUsagesLeft = hammerStacks[0].getItemDamage();

        if (tongStacks[0] != null)
            tTongUsagesLeft = tongStacks[0].getItemDamage();

        for (AnvilRecipe tRecipe : AnvilRecipeRegistry.getInstance().getRecipes().values()) {
            if (tRecipe.matchesRecipe(craftinStacks, additionalCraftingStacks, tHammerUsagesLeft, tTongUsagesLeft)) {
                if (outputStacks[0] != null) {
                    ItemStack tResultStack = tRecipe.getResult(craftinStacks, additionalCraftingStacks);

                    if (!ItemStackHelper.equalsIgnoreStackSize(tResultStack, outputStacks[0]))
                        continue;

                    if ((tResultStack.stackSize + outputStacks[0].stackSize) <= outputStacks[0].getMaxStackSize()) {
                        setCurrentRecipe(tRecipe);
                        return;
                    }
                } else {
                    setCurrentRecipe(tRecipe);
                    return;
                }
            }
        }

        if (craftinStacks[11] != null) {
            for (int tCraftingStack = 0; tCraftingStack < MAX_CRAFTINGSLOTS; tCraftingStack++) {
                if (tCraftingStack == 11 || tCraftingStack == 13) {
                    continue;
                }

                if (craftinStacks[tCraftingStack] != null) {
                    setCurrentRecipe(null);
                    return;
                }
            }

            for (ItemStack tStack : additionalCraftingStacks) {
                if (tStack != null) {
                    setCurrentRecipe(null);
                    return;
                }
            }


            VanillaAnvilRecipe tRecipe = new VanillaAnvilRecipe(this);
            if (tRecipe.matchesRecipe(craftinStacks, additionalCraftingStacks, tHammerUsagesLeft, tTongUsagesLeft)) {
                setCurrentRecipe(tRecipe);
                return;
            }
        }

        setCurrentRecipe(null);
    }

    public AnvilRecipe getCurrentRecipe() {
        if (((BlackSmithsAnvilState) getState()).getRecipeId() == "")
            return null;

        return AnvilRecipeRegistry.getInstance().getRecipe(((BlackSmithsAnvilState) getState()).getRecipeId());
    }

    public void setCurrentRecipe(AnvilRecipe recipe) {
        ((BlackSmithsAnvilState) getState()).setRecipeId(AnvilRecipeRegistry.getInstance().getID(recipe));
    }

    public void ProcessPerformedCrafting() {
        if (getCurrentRecipe() == null) {
            return;
        }

        ((BlackSmithsAnvilState) getState()).setProcessingCraftingResult(true);

        if (getCurrentRecipe() instanceof VanillaAnvilRecipe) {
            ((VanillaAnvilRecipe) getCurrentRecipe()).ProcessPerformedCrafting();
        } else if (getCurrentRecipe().isShapeless()) {
            ArrayList<IAnvilRecipeComponent> tComponentList = new ArrayList<IAnvilRecipeComponent>(Arrays.asList(getCurrentRecipe().getComponents().clone()));
            for (int tSlotIndex = 0; tSlotIndex < MAX_CRAFTINGSLOTS; tSlotIndex++) {
                if (craftinStacks[tSlotIndex] != null) {
                    boolean tProcessedStack = false;

                    Iterator<IAnvilRecipeComponent> tIter = tComponentList.iterator();
                    while (tIter.hasNext() && !tProcessedStack) {
                        IAnvilRecipeComponent tComponent = tIter.next();

                        if (tComponent != null) {
                            craftinStacks[tSlotIndex].stackSize = tComponent.getResultingStackSizeForComponent(craftinStacks[tSlotIndex]);
                            if (craftinStacks[tSlotIndex].stackSize == 0) {
                                craftinStacks[tSlotIndex] = null;
                            }

                            tProcessedStack = true;
                        }
                    }
                }
            }
        } else {
            for (int tSlotIndex = 0; tSlotIndex < MAX_CRAFTINGSLOTS; tSlotIndex++) {
                if (craftinStacks[tSlotIndex] == null)
                    continue;

                IAnvilRecipeComponent tTargetComponent = getCurrentRecipe().getComponent(tSlotIndex);
                if (tTargetComponent == null)
                    continue;

                craftinStacks[tSlotIndex].stackSize = tTargetComponent.getResultingStackSizeForComponent(craftinStacks[tSlotIndex]);
                if (craftinStacks[tSlotIndex].stackSize < 1)
                    craftinStacks[tSlotIndex] = null;
            }
        }

        for (int tSlotIndex = 0; tSlotIndex < MAX_ADDITIONALSLOTS; tSlotIndex++) {
            if (additionalCraftingStacks[tSlotIndex] == null)
                continue;

            IAnvilRecipeComponent tTargetComponent = getCurrentRecipe().getAdditionalComponent(tSlotIndex);
            if (tTargetComponent == null)
                continue;

            additionalCraftingStacks[tSlotIndex].stackSize = tTargetComponent.getResultingStackSizeForComponent(additionalCraftingStacks[tSlotIndex]);
            if (additionalCraftingStacks[tSlotIndex].stackSize < 1)
                additionalCraftingStacks[tSlotIndex] = null;
        }

        if (getCurrentRecipe().getUsesHammer()) {
            if (hammerStacks[0].isItemDamaged() == false) {
                hammerStacks[0].setItemDamage(150);
            }
            hammerStacks[0].setItemDamage(hammerStacks[0].getItemDamage() - getCurrentRecipe().getHammerUsage());
            if (hammerStacks[0].getItemDamage() == 0) {
                hammerStacks[0] = null;
            }
        }

        if (getCurrentRecipe().getUsesTongs()) {
            if (tongStacks[0].isItemDamaged() == false) {
                tongStacks[0].setItemDamage(150);
            }
            tongStacks[0].setItemDamage(tongStacks[0].getItemDamage() - getCurrentRecipe().getTongsUsage());
            if (tongStacks[0].getItemDamage() == 0) {
                tongStacks[0] = null;
            }
        }

        getCurrentRecipe().onRecipeUsed(this);

        ((BlackSmithsAnvilState) getState()).setProcessingCraftingResult(false);
    }

    public AnvilType getCurrentAnvilType() {
        boolean DEBUG = false;
        if (DEBUG)
            return AnvilType.Standard;

        boolean tFoundCoolingBasin = false;
        boolean tFoundHelperRack = false;
        if (getDirection() == EnumFacing.NORTH || getDirection() == EnumFacing.SOUTH) {
            TileEntity tLeftTE = worldObj.getTileEntity(getPos().offset(EnumFacing.EAST));
            TileEntity tRightTE = worldObj.getTileEntity(getPos().offset(EnumFacing.WEST));

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
            TileEntity tLeftTE = worldObj.getTileEntity(getPos().offset(EnumFacing.NORTH));
            TileEntity tRightTE = worldObj.getTileEntity(getPos().offset(EnumFacing.SOUTH));

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
    public Collection<UUID> getWatchingPlayers() {
        return ((BlackSmithsAnvilGuiManager) getManager()).getWatchingPlayers();
    }

    //TODO: FIX ME!
    public int getConnectedPlayerCount() {
        if (worldObj.isRemote) {
            return connectedPlayerCount;
        } else {
            return getWatchingPlayers().size();
        }
    }

    public enum AnvilType {
        Minimal,
        Extended,
        Cooling,
        Standard
    }

}
