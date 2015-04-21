package com.Orion.Armory.Common.TileEntity;
/*
/  TileEntityFirePit
/  Created by : Orion
/  Created on : 02/10/2014
*/

import com.Orion.Armory.Common.Factory.HeatedIngotFactory;
import com.Orion.Armory.Common.Item.ItemHeatedIngot;
import com.Orion.Armory.Common.Registry.GeneralRegistry;
import com.Orion.Armory.Network.Messages.MessageTileEntityFirePit;
import com.Orion.Armory.Network.NetworkManager;
import com.Orion.Armory.Util.References;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.StatCollector;

public class TileEntityFirePit extends TileEntityArmory implements IInventory {

    public float iPositiveHeat = 0.325F;
    public float iNegativeHeat = -0.1F;

    public float iPositiveHeatTerm = 0.325F;
    public float iNegativeHeatTerm = -0.1F;

    public static int INGOTSTACKS_AMOUNT = 5;
    public static int FUELSTACK_AMOUNT = 5;

    public ItemStack[] iIngotStacks = new ItemStack[INGOTSTACKS_AMOUNT];
    public ItemStack[] iFuelStacks = new ItemStack[FUELSTACK_AMOUNT];

    public Integer[] iFuelStackBurningTime = new Integer[FUELSTACK_AMOUNT];
    public Integer[] iFuelStackFuelAmount = new Integer[FUELSTACK_AMOUNT];

    public float iMaxTemperature = 1500;
    public float iCurrentTemperature = 20;
    public float iLastTemperature = 20;
    public float iLastAddedHeat = 0;
    public boolean iIsBurning = false;

    public TileEntityFirePit()
    {

    }

    @Override
    public int getSizeInventory() {
        return INGOTSTACKS_AMOUNT + FUELSTACK_AMOUNT;
    }

    public ItemStack getStackInSlot(int pSlotID) {
        if (pSlotID < INGOTSTACKS_AMOUNT)
        {
            return iIngotStacks[pSlotID];
        }

        if (pSlotID < INGOTSTACKS_AMOUNT + FUELSTACK_AMOUNT)
        {
            return iFuelStacks[pSlotID - INGOTSTACKS_AMOUNT];
        }

        return null;
    }

    @Override
    public ItemStack decrStackSize(int pSlotIndex, int pDecrAmount) {
        ItemStack tItemStack = getStackInSlot(pSlotIndex);
        if (tItemStack == null) { return tItemStack; }
        if (tItemStack.stackSize < pDecrAmount) { setInventorySlotContents(pSlotIndex, null); }
        else
        {
            tItemStack = tItemStack.splitStack(pDecrAmount);
            if (tItemStack.stackSize == 0) { setInventorySlotContents(pSlotIndex, null); }
        }

        return tItemStack;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int pSlotIndex) {
        ItemStack tItemStack = getStackInSlot(pSlotIndex);
        if (tItemStack != null)
        {
            setInventorySlotContents(pSlotIndex, null);
        }

        return tItemStack;
    }

    @Override
    public void setInventorySlotContents(int pSlotIndex, ItemStack pNewItemStack) {
        if (pSlotIndex < INGOTSTACKS_AMOUNT)
        {
            ItemStack pSettingStack = null;
            if (pNewItemStack != null) {
                 pSettingStack = pNewItemStack.copy();
                pSettingStack.stackSize = 1;
            }

            iIngotStacks[pSlotIndex] = pSettingStack;
            if (pNewItemStack != null && pNewItemStack.stackSize > 1)
            {
                --pNewItemStack.stackSize;
            }
        }
        else if (pSlotIndex < INGOTSTACKS_AMOUNT + FUELSTACK_AMOUNT)
        {
            iFuelStacks[pSlotIndex - INGOTSTACKS_AMOUNT] = pNewItemStack;
        }
    }

    @Override
    public String getInventoryName() {
        return this.hasCustomInventoryName() ? this.iName : StatCollector.translateToLocal(References.InternalNames.Blocks.FirePit);
    }

    @Override
    public boolean hasCustomInventoryName() {
        return ((this.iName.length() > 0) && this.iName.isEmpty() == false);
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
    public void openInventory() {
        //No animation and definitely no cat on top of this nice puppy
    }

    @Override
    public void closeInventory() {
        //NOOP
    }

    @Override
    public boolean isItemValidForSlot(int pSlotIndex, ItemStack pItemStack) {
        if (pSlotIndex < INGOTSTACKS_AMOUNT)
        {
            if (pItemStack.getItem() instanceof ItemHeatedIngot)
            {
               return true;
            }

            return HeatedIngotFactory.iInstance.isHeatable(pItemStack);
        }
        else if( pSlotIndex < INGOTSTACKS_AMOUNT + FUELSTACK_AMOUNT)
        {
            return TileEntityFurnace.isItemFuel(pItemStack);
        }

        return false;
    }

    @Override
    public void readFromNBT(NBTTagCompound pCompound)
    {
        super.readFromNBT(pCompound);

        NBTTagList tIngotList = pCompound.getTagList(References.NBTTagCompoundData.TE.FirePit.INGOTITEMSTACKS, 10);
        for (int tStack = 0; tStack < tIngotList.tagCount(); tStack++)
        {
            NBTTagCompound tStackCompound = tIngotList.getCompoundTagAt(tStack);
            Integer tSlotIndex = tStackCompound.getInteger(References.NBTTagCompoundData.TE.Basic.SLOT);

            iIngotStacks[tSlotIndex] = ItemStack.loadItemStackFromNBT(tStackCompound);
        }

        NBTTagList tFuelList = pCompound.getTagList(References.NBTTagCompoundData.TE.FirePit.FUELITEMSTACKS, 10);
        for (int tStack = 0; tStack < tFuelList.tagCount(); tStack++)
        {
            NBTTagCompound tStackCompound = tFuelList.getCompoundTagAt(tStack);
            Integer tSlotIndex = tStackCompound.getInteger(References.NBTTagCompoundData.TE.Basic.SLOT);

            iFuelStackBurningTime[tSlotIndex] = tStackCompound.getInteger(References.NBTTagCompoundData.TE.FirePit.FUELSTACKBURNINGTIME);
            iFuelStackFuelAmount[tSlotIndex] = tStackCompound.getInteger(References.NBTTagCompoundData.TE.FirePit.FUELSTACKFUELAMOUNT);

            iFuelStacks[tSlotIndex] = ItemStack.loadItemStackFromNBT(tStackCompound);
        }

        iIsBurning = pCompound.getBoolean(References.NBTTagCompoundData.TE.FirePit.CURRENTLYBURNING);
        iCurrentTemperature = pCompound.getFloat(References.NBTTagCompoundData.TE.FirePit.CURRENTTEMPERATURE);
        iMaxTemperature = pCompound.getFloat(References.NBTTagCompoundData.TE.FirePit.MAXTEMPERATURE);
        iLastAddedHeat = pCompound.getFloat(References.NBTTagCompoundData.TE.FirePit.LASTADDEDHEAT);
    }

    @Override
    public void writeToNBT(NBTTagCompound pCompound)
    {
        super.writeToNBT(pCompound);

        NBTTagList tIngotList = new NBTTagList();
        for(int tStack = 0; tStack < INGOTSTACKS_AMOUNT; tStack++)
        {
            if (iIngotStacks[tStack] == null)
            {
                continue;
            }

            NBTTagCompound tIngotCompound = new NBTTagCompound();
            tIngotCompound.setInteger(References.NBTTagCompoundData.TE.Basic.SLOT, tStack);
            iIngotStacks[tStack].writeToNBT(tIngotCompound);
            tIngotList.appendTag(tIngotCompound);
        }
        pCompound.setTag(References.NBTTagCompoundData.TE.FirePit.INGOTITEMSTACKS, tIngotList);

        NBTTagList tFuelList = new NBTTagList();
        for(int tStack = 0; tStack < FUELSTACK_AMOUNT; tStack++)
        {
            if (iFuelStacks[tStack] == null)
            {
                continue;
            }

            NBTTagCompound iFuelCompound = new NBTTagCompound();
            iFuelCompound.setInteger(References.NBTTagCompoundData.TE.Basic.SLOT, tStack);
            try {
                iFuelCompound.setInteger(References.NBTTagCompoundData.TE.FirePit.FUELSTACKBURNINGTIME, iFuelStackBurningTime[tStack]);
                iFuelCompound.setInteger(References.NBTTagCompoundData.TE.FirePit.FUELSTACKFUELAMOUNT, iFuelStackFuelAmount[tStack]);
            }
            catch(Exception ex)
            {
                iFuelCompound.setInteger(References.NBTTagCompoundData.TE.FirePit.FUELSTACKBURNINGTIME, 0);
                iFuelCompound.setInteger(References.NBTTagCompoundData.TE.FirePit.FUELSTACKFUELAMOUNT, 0);
            }
            iFuelStacks[tStack].writeToNBT(iFuelCompound);
            tFuelList.appendTag(iFuelCompound);
        }
        pCompound.setTag(References.NBTTagCompoundData.TE.FirePit.FUELITEMSTACKS, tFuelList);

        pCompound.setBoolean(References.NBTTagCompoundData.TE.FirePit.CURRENTLYBURNING, iIsBurning);
        pCompound.setFloat(References.NBTTagCompoundData.TE.FirePit.CURRENTTEMPERATURE, iCurrentTemperature);
        pCompound.setFloat(References.NBTTagCompoundData.TE.FirePit.MAXTEMPERATURE, iMaxTemperature);
        pCompound.setFloat(References.NBTTagCompoundData.TE.FirePit.LASTADDEDHEAT, iLastAddedHeat);
    }

    //TODO: Implement new Heatexchange mechanism!
    @Override
    public void updateEntity()
    {
        if (worldObj.isRemote)
        {
            return;
        }

        iLastTemperature = iCurrentTemperature;
        iIsBurning = false;

        heatFurnace();

        if (iLastAddedHeat > 0F)
        {
            iIsBurning = true;
        }

        heatIngots();

        iLastAddedHeat = iCurrentTemperature - iLastTemperature;

        if (iLastAddedHeat != 0F)
        {
            markDirty();
        }
    }

    public void heatFurnace() {

        iPositiveHeatTerm = iPositiveHeat;
        iNegativeHeatTerm = iNegativeHeat;

        iLastAddedHeat = 0F;

        for (int tFuelStackIndex = 0; tFuelStackIndex < FUELSTACK_AMOUNT; tFuelStackIndex++) {
            if (iFuelStackBurningTime[tFuelStackIndex] == null)
            {
                iFuelStackBurningTime[tFuelStackIndex] = -1;
            }

            if (iFuelStackBurningTime[tFuelStackIndex] == -1)
            {
                if (iFuelStacks[tFuelStackIndex] == null)
                {
                    continue;
                }

                ItemStack tTargetedFuelStack = iFuelStacks[tFuelStackIndex];

                //Check if the stack is a valid Fuel in the Furnace
                if ((tTargetedFuelStack != null) && (TileEntityFurnace.isItemFuel(tTargetedFuelStack))) {

                    //Update the Fuelstack if a new piece of fuel is needed.
                    if (iFuelStackBurningTime[tFuelStackIndex] == -1) {
                        --tTargetedFuelStack.stackSize;

                        iFuelStackBurningTime[tFuelStackIndex] = 0;

                        iFuelStackFuelAmount[tFuelStackIndex] = TileEntityFurnace.getItemBurnTime(tTargetedFuelStack);

                        if (tTargetedFuelStack.stackSize == 0) {
                            iFuelStacks[tFuelStackIndex] = tTargetedFuelStack.getItem().getContainerItem(tTargetedFuelStack);
                        }
                    }

                }
            }

            if (iFuelStackBurningTime[tFuelStackIndex] > -1)
            {

                ++iFuelStackBurningTime[tFuelStackIndex];

                //Process depleted fuelstack
                if (iFuelStackBurningTime[tFuelStackIndex].intValue() == iFuelStackFuelAmount[tFuelStackIndex].intValue()) {
                    iFuelStackBurningTime[tFuelStackIndex] = -1;
                }

                iLastAddedHeat += iPositiveHeatTerm;
            }
        }

        iLastAddedHeat *=  (1-(iCurrentTemperature / iMaxTemperature))    ;
    }

    public void heatIngots()
    {
        if ((iLastAddedHeat == 0F) && (iCurrentTemperature == 20F)) {return;}

        iCurrentTemperature += iLastAddedHeat;

        for (int tIngotStackCount = 0; tIngotStackCount < INGOTSTACKS_AMOUNT; tIngotStackCount ++) {
            if (iIngotStacks[tIngotStackCount] == null) {
                continue;
            }

            float tCurrentStackTemp = ItemHeatedIngot.getItemTemperature(iIngotStacks[tIngotStackCount]);
            float tCurrentStackCoefficient = GeneralRegistry.getInstance().getHeatCoefficient(HeatedIngotFactory.getInstance().getMaterialIDFromItemStack(iIngotStacks[tIngotStackCount]));

            float tSourceDifference = iNegativeHeatTerm - tCurrentStackCoefficient;
            float tTargetDifference = -1 * tSourceDifference + iNegativeHeatTerm;

            if (tCurrentStackTemp <= iCurrentTemperature) {
                iCurrentTemperature += tSourceDifference;
                ItemHeatedIngot.setItemTemperature(iIngotStacks[tIngotStackCount], ItemHeatedIngot.getItemTemperature(iIngotStacks[tIngotStackCount]) + tTargetDifference);
            } else if (ItemHeatedIngot.getItemTemperature(iIngotStacks[tIngotStackCount]) > iCurrentTemperature) {
                if (tCurrentStackTemp <= 20F)
                {
                    //Items cannot cool any further then 20 degrees
                    continue;
                }

                iCurrentTemperature += tTargetDifference;
                ItemHeatedIngot.setItemTemperature(iIngotStacks[tIngotStackCount], ItemHeatedIngot.getItemTemperature(iIngotStacks[tIngotStackCount]) + tSourceDifference);
            }
        }
    }


    public int getFuelAmount()
    {
        int tFuelAmount = 0;

        for (int tFuelStackIndex = 0; tFuelStackIndex < FUELSTACK_AMOUNT; tFuelStackIndex++)
        {
            if (iIngotStacks[tFuelStackIndex] == null)
            {
                continue;
            }

            tFuelAmount++;
        }

        return tFuelAmount;
    }

    public int getIngotAmount()
    {
        int tIngotAmount = 0;

        for (int tIngotStackIndex = 0; tIngotStackIndex < INGOTSTACKS_AMOUNT; tIngotStackIndex++)
        {
            if (iIngotStacks[tIngotStackIndex] == null)
            {
                continue;
            }

            tIngotAmount++;
        }

        return tIngotAmount;
    }

    public int getHeatableIngotAmount()
    {
        int tIngotAmount = 0;

        for (int tIngotStackIndex = 0; tIngotStackIndex < INGOTSTACKS_AMOUNT; tIngotStackIndex++)
        {
            if (iIngotStacks[tIngotStackIndex] == null)
            {
                continue;
            }

            if(ItemHeatedIngot.getItemTemperature(iIngotStacks[tIngotStackIndex]) < iMaxTemperature)
            {
                tIngotAmount++;
            }
        }

        return tIngotAmount;
    }

    public boolean isBurning() {return iIsBurning; }

    @Override
    public void markDirty()
    {
        NetworkManager.INSTANCE.sendToAllAround(new MessageTileEntityFirePit(this), new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId,(double) this.xCoord,(double) this.yCoord,(double) this.zCoord, 128));
        //worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);

        super.markDirty();
        worldObj.func_147451_t(xCoord, yCoord, zCoord);
    }

    @Override
    public Packet getDescriptionPacket()
    {
        return NetworkManager.INSTANCE.getPacketFrom(new MessageTileEntityFirePit(this));
    }
}
