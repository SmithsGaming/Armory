package com.smithsmodding.armory.common.tileentity;

import com.smithsmodding.armory.api.References;
import com.smithsmodding.armory.api.materials.IArmorMaterial;
import com.smithsmodding.armory.common.factory.HeatedItemFactory;
import com.smithsmodding.armory.common.item.ItemHeatedItem;
import com.smithsmodding.armory.common.registry.HeatableItemRegistry;
import com.smithsmodding.armory.common.tileentity.guimanagers.FireplaceGuiManager;
import com.smithsmodding.armory.common.tileentity.state.FireplaceState;
import com.smithsmodding.smithscore.common.inventory.IItemStorage;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ITickable;

/**
 * Created by Marc on 27.02.2016.
 */
public class TileEntityFireplace extends TileEntityArmory implements IItemStorage, ITickable {

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
    public static float POSITIVEHEAT = 0.925F;
    public static float NEGATIVEHEAT = -0.15F;
    private boolean cookingShouldUpdateHeat = false;

    private ItemStack[] ingotStacks = new ItemStack[INGOTSLOTCOUNT];
    private ItemStack[] foodInputStacks = new ItemStack[FOODCOOKINPUTCOUNT];
    private ItemStack[] foodOutputStacks = new ItemStack[FOODCOOKOUTPUTCOUNT];
    private ItemStack[] fuelStacks = new ItemStack[FUELSLOTCOUNT];

    private float heatedProcentage;

    public TileEntityFireplace() {
        super(new FireplaceState(), null);
        this.setManager(new FireplaceGuiManager(this));
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

    /**
     * Removes up to a specified number of items from an inventory slot and returns them in a new stack.
     *
     * @param index
     * @param count
     */
    @Override
    public ItemStack decrStackSize(int index, int count) {
        ItemStack tItemStack = getStackInSlot(index);
        if (tItemStack == null) {
            return tItemStack;
        }

        if (tItemStack.stackSize < count) {
            setInventorySlotContents(index, null);
        } else {
            ItemStack returnStack = tItemStack.splitStack(count);

            if (tItemStack.stackSize == 0) {
                setInventorySlotContents(index, null);
            }

            return returnStack;
        }

        return tItemStack;
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
                ((FireplaceState) getState()).setCookingProgress(0);

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
        FireplaceState state = (FireplaceState) getState();

        state.setLastTemperature(state.getCurrentTemperature());

        heatFurnace();

        if (!heatIngots())
            cookingShouldUpdateHeat = true;

        cookFood();

        cookingShouldUpdateHeat = false;

        state.setLastAddedHeat(state.getCurrentTemperature() - state.getLastTemperature());

        if (!worldObj.isRemote) {
            this.markDirty();
        }
    }

    public void heatFurnace() {

        FireplaceState tileState = (FireplaceState) getState();

        tileState.setLastAddedHeat(0F);

        if ((Float) tileState.getData(this, References.NBTTagCompoundData.TE.FirePit.FUELSTACKBURNINGTIME) < 1F) {
            for (int tFuelStackIndex = 0; tFuelStackIndex < FUELSLOTCOUNT; tFuelStackIndex++) {

                if (fuelStacks[tFuelStackIndex] == null) {
                    continue;
                }

                ItemStack tTargetedFuelStack = fuelStacks[tFuelStackIndex];

                //Check if the stack is a valid Fuel in the Furnace
                if ((tTargetedFuelStack != null) && (TileEntityFurnace.isItemFuel(tTargetedFuelStack))) {
                    --tTargetedFuelStack.stackSize;

                    tileState.setData(this, References.NBTTagCompoundData.TE.FirePit.FUELSTACKBURNINGTIME, (Float) tileState.getData(this, References.NBTTagCompoundData.TE.FirePit.FUELSTACKBURNINGTIME) + TileEntityFurnace.getItemBurnTime(tTargetedFuelStack));

                    if (tTargetedFuelStack.stackSize == 0) {
                        fuelStacks[tFuelStackIndex] = tTargetedFuelStack.getItem().getContainerItem(tTargetedFuelStack);
                    }

                }

            }

            tileState.setData(this, References.NBTTagCompoundData.TE.FirePit.FUELSTACKFUELAMOUNT, tileState.getData(this, References.NBTTagCompoundData.TE.FirePit.FUELSTACKBURNINGTIME));
        }

        if ((Float) tileState.getData(this, References.NBTTagCompoundData.TE.FirePit.FUELSTACKBURNINGTIME) >= 1F) {

            tileState.setData(this, References.NBTTagCompoundData.TE.FirePit.FUELSTACKBURNINGTIME, (Float) tileState.getData(this, References.NBTTagCompoundData.TE.FirePit.FUELSTACKBURNINGTIME) - 1F);
            tileState.setLastAddedHeat(tileState.getLastAddedHeat() + POSITIVEHEAT);
        }

        heatedProcentage = Math.round((tileState.getCurrentTemperature() / tileState.getMaxTemperature()) * 100) / 100F;
        tileState.setLastAddedHeat(tileState.getLastAddedHeat() * (1 - heatedProcentage));
    }

    public boolean heatIngots() {
        for (int i = 0; i < FOODCOOKINPUTCOUNT; i++) {
            if (getStackInSlot(i + INGOTSLOTCOUNT) != null)
                return false;
        }

        FireplaceState state = (FireplaceState) getState();

        if ((state.getLastAddedHeat() == 0F) && (state.getCurrentTemperature() <= 20F) && (getIngotAmount() == 0)) {
            return true;
        }

        state.setCurrentTemperature(state.getCurrentTemperature() + state.getLastAddedHeat());

        if (state.getCurrentTemperature() > 20F) {
            state.setCurrentTemperature(state.getCurrentTemperature() + (NEGATIVEHEAT * heatedProcentage));
        }

        for (int tIngotStackCount = 0; tIngotStackCount < INGOTSLOTCOUNT; tIngotStackCount++) {
            if (ingotStacks[tIngotStackCount] == null) {
                continue;
            }

            if ((state.getCurrentTemperature() > 20F) && !(ingotStacks[tIngotStackCount].getItem() instanceof ItemHeatedItem) && HeatableItemRegistry.getInstance().isHeatable(ingotStacks[tIngotStackCount])) {
                ingotStacks[tIngotStackCount] = HeatedItemFactory.getInstance().convertToHeatedIngot(ingotStacks[tIngotStackCount]);
            }

            ItemStack stack = ingotStacks[tIngotStackCount];
            IArmorMaterial material = HeatableItemRegistry.getInstance().getMaterialFromHeatedStack(stack);

            float tCurrentStackTemp = getItemTemperature(stack);
            float tCurrentStackCoefficient = material.getHeatCoefficient();

            float tSourceDifference = (NEGATIVEHEAT / 4) - tCurrentStackCoefficient;
            float tTargetDifference = tCurrentStackCoefficient;


            if (tCurrentStackTemp < 20F) {
                ingotStacks[tIngotStackCount] = HeatedItemFactory.getInstance().convertToCooledIngot(ingotStacks[tIngotStackCount]);
            } else if (tCurrentStackTemp <= state.getCurrentTemperature()) {
                state.setCurrentTemperature(state.getCurrentTemperature() + tSourceDifference);
                setItemTemperature(stack, getItemTemperature(stack) + tTargetDifference);
            } else if (getItemTemperature(stack) > state.getCurrentTemperature()) {
                state.setCurrentTemperature(state.getCurrentTemperature() + tTargetDifference);
                setItemTemperature(stack, getItemTemperature(stack) + tSourceDifference);
            }

        }

        return true;
    }

    public boolean cookFood() {
        FireplaceState state = (FireplaceState) getState();

        if ((state.getLastAddedHeat() == 0F) && (state.getCurrentTemperature() <= 20F) && (getFoodAmount() == 0)) {
            return false;
        }

        if (cookingShouldUpdateHeat) {
            state.setCurrentTemperature(state.getCurrentTemperature() + state.getLastAddedHeat());

            if (state.getCurrentTemperature() > 20F) {
                state.setCurrentTemperature(state.getCurrentTemperature() + (NEGATIVEHEAT * heatedProcentage));
            }
        }

        if (state.getCurrentTemperature() < STARTCOOKINGTEMP) {
            state.setCookingSpeedMultiplier(0);
            return false;
        }

        state.setCookingSpeedMultiplier((state.getCurrentTemperature() - STARTCOOKINGTEMP) * MULTIPLIERPERDEGREE + 1);

        if (state.getCookingSpeedMultiplier() < BASEMULTIPLIER)
            return false;

        if (state.getCookingSpeedMultiplier() > MAXMULTIPLIER)
            state.setCookingSpeedMultiplier(MAXMULTIPLIER);


        for (int i = 0; i < INGOTSLOTCOUNT; i++) {
            if (getStackInSlot(i) != null)
                return false;
        }

        int targetSlot = canCook();
        if (targetSlot == -1) {
            return true;
        }

        state.setCookingProgress(state.getCookingProgress() + (1f / 200f) * state.getCookingSpeedMultiplier());

        if (state.getCurrentTemperature() > 20F) {
            state.setCurrentTemperature(state.getCurrentTemperature() + (NEGATIVEHEAT * heatedProcentage));
        }

        if (state.getCookingProgress() < 1f)
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

        state.setCookingProgress(0);

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

    public int getIngotAmount() {
        int tIngotAmount = 0;

        for (int tIngotStackIndex = 0; tIngotStackIndex < INGOTSLOTCOUNT; tIngotStackIndex++) {
            if (ingotStacks[tIngotStackIndex] == null) {
                continue;
            }

            tIngotAmount++;
        }

        return tIngotAmount;
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

    public float getItemTemperature(ItemStack pItemStack) {
        if (pItemStack.getItem() instanceof ItemHeatedItem) {

            NBTTagCompound stackCompound = pItemStack.getTagCompound();

            return stackCompound.getFloat(References.NBTTagCompoundData.HeatedIngot.CURRENTTEMPERATURE);
        }

        return 20F;
    }

    public void setItemTemperature(ItemStack pItemStack, float pNewTemp) {
        if (pItemStack.getItem() instanceof ItemHeatedItem) {
            if (pNewTemp < 20F)
                pNewTemp = 20F;

            NBTTagCompound stackCompound = pItemStack.getTagCompound();

            stackCompound.setFloat(References.NBTTagCompoundData.HeatedIngot.CURRENTTEMPERATURE, pNewTemp);

            pItemStack.setTagCompound(stackCompound);
        }

    }

}
