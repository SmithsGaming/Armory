package com.smithsmodding.armory.common.tileentity;

import com.smithsmodding.armory.api.util.references.References;
import com.smithsmodding.armory.common.item.ItemHeatedItem;
import com.smithsmodding.armory.common.registry.HeatableItemRegistry;
import com.smithsmodding.armory.common.tileentity.guimanagers.TileEntityFireplaceGuiManager;
import com.smithsmodding.armory.common.tileentity.state.TileEntityFireplaceState;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntityFurnace;

/**
 * Created by Marc on 27.02.2016.
 */
public class TileEntityFireplace extends TileEntityForgeBase<TileEntityFireplaceState, TileEntityFireplaceGuiManager> {

    public static final int INGOTSLOTCOUNT = 3;
    public static final int FOODCOOKINPUTCOUNT = 1;
    public static final int FUELSLOTCOUNT = 1;
    public static final int STARTCOOKINGTEMP = 110;
    public static final int MAXCOOKINGTEMP = 635;
    public static final int CHARREDCOOKINGTEMP = MAXCOOKINGTEMP + 180;
    public static final int DESTROYEDCOOKINGTEMP = CHARREDCOOKINGTEMP + 180;
    public static final int BASESPEED = 450;
    public static final int MAXSPEED = 50;
    public static final float BASEMULTIPLIER = 1f;
    public static final float MAXMULTIPLIER = 2.5f; //BASEMULTIPLIER * (BASESPEED / MAXSPEED);
    public static final float MULTIPLIERPERDEGREE = (MAXMULTIPLIER - BASEMULTIPLIER) / (MAXCOOKINGTEMP - STARTCOOKINGTEMP);
    private static final int FOODBOOSTSTACKSIZE = 2;
    public static final int FOODCOOKOUTPUTCOUNT = FOODCOOKINPUTCOUNT * FOODBOOSTSTACKSIZE;
    public static float POSITIVEHEAT = 1.11F;
    public static float NEGATIVEHEAT = -0.15F;
    private boolean cookingShouldUpdateHeat = false;

    private ItemStack[] ingotStacks = new ItemStack[INGOTSLOTCOUNT];
    private ItemStack[] foodInputStacks = new ItemStack[FOODCOOKINPUTCOUNT];
    private ItemStack[] foodOutputStacks = new ItemStack[FOODCOOKOUTPUTCOUNT];
    private ItemStack[] fuelStacks = new ItemStack[FUELSLOTCOUNT];

    private float heatedProcentage;

    public TileEntityFireplace() {
    }

    @Override
    protected TileEntityFireplaceGuiManager getInitialGuiManager() {
        return new TileEntityFireplaceGuiManager(this);
    }

    @Override
    protected TileEntityFireplaceState getInitialState() {
        return new TileEntityFireplaceState();
    }

    /**
     * Getter for the Containers ID. Used to identify the container over the network. If this relates to TileEntities,
     * it should contain a ID and a location based ID so that multiple instances of this container matched up to
     * different TileEntities can be separated.
     *
     * @return The ID of this Container Instance.
     */
    @Override
    public String getContainerID() {
        return References.InternalNames.TileEntities.FireplaceContainer + "-" + getLocation().toString();
    }

    /**
     * Returns the number of slots in the inventory.
     */
    @Override
    public int getSizeInventory() {
        return INGOTSLOTCOUNT + FOODCOOKINPUTCOUNT + FOODCOOKOUTPUTCOUNT + FUELSLOTCOUNT;
    }

    /**
     * Returns the stack in the given slot.
     *
     * @param index
     */
    @Override
    public ItemStack getStackInSlot(int index) {
        if (index < INGOTSLOTCOUNT) {
            return ingotStacks[index];
        }

        index -= INGOTSLOTCOUNT;

        if (index < FOODCOOKINPUTCOUNT) {
            return foodInputStacks[index];
        }

        index -= FOODCOOKINPUTCOUNT;

        if (index < FOODCOOKOUTPUTCOUNT) {
            return foodOutputStacks[index];
        }

        index -= FOODCOOKOUTPUTCOUNT;

        if (index < FUELSLOTCOUNT) {
            return fuelStacks[index];
        }

        //index -= FUELSLOTCOUNT;

        return null;
    }

    @Override
    public void clearInventory() {
        ingotStacks = new ItemStack[INGOTSLOTCOUNT];
        foodInputStacks = new ItemStack[FOODCOOKINPUTCOUNT];
        foodOutputStacks = new ItemStack[FOODCOOKOUTPUTCOUNT];
        fuelStacks = new ItemStack[FUELSLOTCOUNT];
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     *
     * @param index
     * @param stack
     */
    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        if (index < INGOTSLOTCOUNT) {
            ingotStacks[index] = stack;
            return;
        }

        index -= INGOTSLOTCOUNT;

        if (index < FOODCOOKINPUTCOUNT) {
            foodInputStacks[index] = stack;

            if (stack == null)
                (getState()).setCookingProgress(0);

            return;
        }

        index -= FOODCOOKINPUTCOUNT;

        if (index < FOODCOOKOUTPUTCOUNT) {
            foodOutputStacks[index] = stack;
            return;
        }

        index -= FOODCOOKOUTPUTCOUNT;

        if (index < FUELSLOTCOUNT) {
            fuelStacks[index] = stack;
            return;
        }

        //index -= FUELSLOTCOUNT;
    }

    /**
     * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended.
     */
    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public void markInventoryDirty() {
        this.markDirty();
    }

    /**
     * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot.
     *
     * @param index
     * @param stack
     */
    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        if (index < INGOTSLOTCOUNT) {
            for (int i = 0; i < FOODCOOKINPUTCOUNT; i++) {
                if (getStackInSlot(i + INGOTSLOTCOUNT) != null)
                    return false;
            }

            if (stack.getItem() instanceof ItemHeatedItem) {
                return true;
            }

            return HeatableItemRegistry.getInstance().isHeatable(stack);
        }

        index -= INGOTSLOTCOUNT;

        if (index < FOODCOOKINPUTCOUNT) {
            for (int i = 0; i < INGOTSLOTCOUNT; i++) {
                if (getStackInSlot(i) != null)
                    return false;
            }

            return FurnaceRecipes.instance().getSmeltingResult(stack).getItem() instanceof ItemFood;
        }

        index -= FOODCOOKINPUTCOUNT;

        if (index < FOODCOOKOUTPUTCOUNT) {
            return false;
        }

        index -= FOODCOOKOUTPUTCOUNT;

        if (index < FUELSLOTCOUNT) {
            return TileEntityFurnace.isItemFuel(stack);
        }

        //index -= FUELSLOTCOUNT;

        return false;
    }

    /**
     * Like the old updateEntity(), except more generic.
     */
    @Override
    public void update() {
        TileEntityFireplaceState localData = getState();

        localData.setLastTemp(localData.getCurrentTemp());
        localData.setBurning(false);

        heatFurnace(localData, localData);

        localData.setBurning(localData.getBurningTicksLeftOnCurrentFuel() >= 1F);

        if (!heatIngots(localData, localData))
            cookingShouldUpdateHeat = true;

        cookFood();

        cookingShouldUpdateHeat = false;

        localData.setLastChange(localData.getCurrentTemp() - localData.getLastTemp());

        if (!worldObj.isRemote) {
            this.markDirty();
        }
    }

    @Override
    public TileEntityFireplaceState getFuelData() {
        return getState();
    }

    @Override
    protected void calculateHeatTerms(TileEntityFireplaceState localData) {
        localData.setMaxTemp(2150f);
        localData.setLastNegativeTerm(NEGATIVEHEAT);
        localData.setLastPositiveTerm(POSITIVEHEAT);
    }

    @Override
    protected int getFuelStackAmount() {
        return FUELSLOTCOUNT;
    }

    @Override
    protected ItemStack getFuelStack(int fuelStackIndex) {
        return fuelStacks[fuelStackIndex];
    }

    @Override
    protected void setFuelStack(int fuelStackIndex, ItemStack fuelStack) {
        fuelStacks[fuelStackIndex] = fuelStack;
    }

    @Override
    protected int getTotalPossibleIngotAmount() {
        return INGOTSLOTCOUNT;
    }

    @Override
    protected ItemStack getIngotStack(int ingotStackIndex) {
        return ingotStacks[ingotStackIndex];
    }

    @Override
    protected void setIngotStack(int ingotStackIndex, ItemStack ingotStack) {
        ingotStacks[ingotStackIndex] = ingotStack;
    }

    @Override
    protected float getSourceEfficiencyIndex() {
        return 10;
    }

    public boolean cookFood() {
        if ((getState().getLastChange() == 0F) && (getState().getCurrentTemp() <= 20F) && (getFoodAmount() == 0)) {
            return false;
        }

        if (cookingShouldUpdateHeat) {
            getState().addLastChangeToCurrentTemp();

            if (getState().getCurrentTemp() > 20F) {
                getState().setCurrentTemp(getState().getCurrentTemp() + (getState().getLastNegativeTerm() * getState().getHeatedPercentage()));
            }
        }

        if (getState().getCurrentTemp() < STARTCOOKINGTEMP) {
            getState().setCookingSpeedMultiplier(0);
            return false;
        }

        getState().setCookingSpeedMultiplier((getState().getCurrentTemp() - STARTCOOKINGTEMP) * MULTIPLIERPERDEGREE + 1);

        if (getState().getCookingSpeedMultiplier() < BASEMULTIPLIER)
            return false;

        if (getState().getCookingSpeedMultiplier() > MAXMULTIPLIER)
            getState().setCookingSpeedMultiplier(MAXMULTIPLIER);


        for (int i = 0; i < INGOTSLOTCOUNT; i++) {
            if (getStackInSlot(i) != null)
                return false;
        }

        int targetSlot = canCook();
        if (targetSlot == -1) {
            return true;
        }

        getState().setCookingProgress(getState().getCookingProgress() + (1f / 200f) * getState().getCookingSpeedMultiplier());

        if (getState().getCurrentTemp() > 20F) {
            getState().setCurrentTemp(getState().getCurrentTemp() + (getState().getLastNegativeTerm() * getState().getHeatedPercentage()));
        }

        if (getState().getCookingProgress() < 1f)
            return true;

        //Add the items twice.
        for (int i = 0; i < 2; i++) {
            targetSlot = canCook();
            if (targetSlot == -1) {
                break;
            }

            ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(this.foodInputStacks[0]);

            if (this.foodOutputStacks[targetSlot] == null) {
                this.foodOutputStacks[targetSlot] = itemstack.copy();
            } else if (this.foodOutputStacks[targetSlot].getItem() == itemstack.getItem()) {
                this.foodOutputStacks[targetSlot].stackSize += itemstack.stackSize; // Forge BugFix: Results may have multiple items
            }
        }

        --this.foodInputStacks[0].stackSize;

        if (this.foodInputStacks[0].stackSize <= 0) {
            this.foodInputStacks[0] = null;
        }

        getState().setCookingProgress(0);

        return false;
    }

    private int canCook() {
        if (this.foodInputStacks[0] == null) {
            return -1;
        } else {
            ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(this.foodInputStacks[0]);
            if (itemstack == null) return -1;
            if (!(itemstack.getItem() instanceof ItemFood)) return -1;
            if (this.foodOutputStacks[0] == null) return 0;
            if (!this.foodOutputStacks[0].isItemEqual(itemstack)) {
                if (this.foodOutputStacks[1] == null) return 1;
                if (!this.foodOutputStacks[1].isItemEqual(itemstack)) return -1;
                int result = foodOutputStacks[1].stackSize + itemstack.stackSize;
                if (!(result <= getInventoryStackLimit() && result <= this.foodOutputStacks[0].getMaxStackSize())) {
                    return -1;
                }

                return 1;
            }
            int result = foodOutputStacks[0].stackSize + itemstack.stackSize;
            if (!(result <= getInventoryStackLimit() && result <= this.foodOutputStacks[0].getMaxStackSize())) {
                if (this.foodOutputStacks[1] == null) return 1;
                if (!this.foodOutputStacks[1].isItemEqual(itemstack)) return -1;
                result = foodOutputStacks[1].stackSize + itemstack.stackSize;
                if (!(result <= getInventoryStackLimit() && result <= this.foodOutputStacks[0].getMaxStackSize())) {
                    return -1;
                }

                return 1;
            }

            return 0;
        }
    }

    public int getFoodAmount() {
        int foodAmount = 0;

        for (int foodIndex = 0; foodIndex < FOODCOOKINPUTCOUNT; foodIndex++) {
            if (foodInputStacks[foodIndex] == null) {
                continue;
            }

            foodAmount++;
        }

        return foodAmount;
    }
}
