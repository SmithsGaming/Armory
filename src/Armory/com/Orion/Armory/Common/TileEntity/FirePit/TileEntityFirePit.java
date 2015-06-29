package com.Orion.Armory.Common.TileEntity.FirePit;
/*
/  TileEntityFirePit
/  Created by : Orion
/  Created on : 02/10/2014
*/

import com.Orion.Armory.Common.Factory.HeatedItemFactory;
import com.Orion.Armory.Common.Item.ItemHeatedItem;
import com.Orion.Armory.Common.Material.MaterialRegistry;
import com.Orion.Armory.Common.TileEntity.Core.TileEntityArmory;
import com.Orion.Armory.Network.Messages.MessageTileEntityFirePit;
import com.Orion.Armory.Network.NetworkManager;
import com.Orion.Armory.Util.References;
import com.sun.org.apache.bcel.internal.generic.IINC;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.ForgeDirection;
import scala.tools.nsc.typechecker.Macros;

import java.util.ArrayList;
import java.util.HashMap;

public class TileEntityFirePit extends TileEntityArmory implements IInventory {

    public static int INGOTSTACKS_AMOUNT = 5;
    public static int FUELSTACK_AMOUNT = 5;

    public float iPositiveHeat = 0.325F;
    public float iNegativeHeat = -0.1F;
    public float iPositiveHeatTerm = 0.325F;
    public float iNegativeHeatTerm = -0.1F;

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
        return this.hasCustomInventoryName() ? this.getDisplayName() : StatCollector.translateToLocal(References.InternalNames.Blocks.FirePit);
    }

    @Override
    public boolean hasCustomInventoryName() {
        return ((this.getDisplayName().length() > 0) && this.getDisplayName().isEmpty() == false);
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
            if (pItemStack.getItem() instanceof ItemHeatedItem)
            {
               return true;
            }

            return HeatedItemFactory.iInstance.isHeatable(pItemStack);
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
            NBTTagCompound iFuelCompound = new NBTTagCompound();
            iFuelCompound.setInteger(References.NBTTagCompoundData.TE.Basic.SLOT, tStack);
            try {
                iFuelCompound.setInteger(References.NBTTagCompoundData.TE.FirePit.FUELSTACKBURNINGTIME, iFuelStackBurningTime[tStack]);
                iFuelCompound.setInteger(References.NBTTagCompoundData.TE.FirePit.FUELSTACKFUELAMOUNT, iFuelStackFuelAmount[tStack]);
            }
            catch(Exception ex)
            {
                iFuelCompound.setInteger(References.NBTTagCompoundData.TE.FirePit.FUELSTACKBURNINGTIME, -1);
                iFuelCompound.setInteger(References.NBTTagCompoundData.TE.FirePit.FUELSTACKFUELAMOUNT, -1);
            }

            if (iFuelStacks[tStack] != null)
            {
                iFuelStacks[tStack].writeToNBT(iFuelCompound);
            }

            tFuelList.appendTag(iFuelCompound);
        }
        pCompound.setTag(References.NBTTagCompoundData.TE.FirePit.FUELITEMSTACKS, tFuelList);

        pCompound.setBoolean(References.NBTTagCompoundData.TE.FirePit.CURRENTLYBURNING, iIsBurning);
        pCompound.setFloat(References.NBTTagCompoundData.TE.FirePit.CURRENTTEMPERATURE, iCurrentTemperature);
        pCompound.setFloat(References.NBTTagCompoundData.TE.FirePit.MAXTEMPERATURE, iMaxTemperature);
        pCompound.setFloat(References.NBTTagCompoundData.TE.FirePit.LASTADDEDHEAT, iLastAddedHeat);
    }




    @Override
    public float getProgressBarValue(String pProgressBarID) {
        int tSlotIndex = -1;

        if (pProgressBarID.equals(References.InternalNames.GUIComponents.FirePit.FLAMEONE))
            tSlotIndex = 0;
        else if (pProgressBarID.equals(References.InternalNames.GUIComponents.FirePit.FLAMETWO))
            tSlotIndex = 1;
        else if (pProgressBarID.equals(References.InternalNames.GUIComponents.FirePit.FLAMETHREE))
            tSlotIndex = 2;
        else if (pProgressBarID.equals(References.InternalNames.GUIComponents.FirePit.FLAMEFOUR))
            tSlotIndex = 3;
        else if (pProgressBarID.equals(References.InternalNames.GUIComponents.FirePit.FLAMEFIVE))
            tSlotIndex = 4;

        if (tSlotIndex == -1)
            return -1F;

        if ((iFuelStackBurningTime[tSlotIndex] == 0) || (iFuelStackFuelAmount[tSlotIndex] == 0) || (iFuelStackBurningTime[tSlotIndex] < 0) || (iFuelStackFuelAmount[tSlotIndex] < 0))
            return -1F;

        return ((float) iFuelStackBurningTime[tSlotIndex] / (float) iFuelStackFuelAmount[tSlotIndex]);
    }





    @Override
    public void updateEntity()
    {
        iLastTemperature = iCurrentTemperature;
        iIsBurning = false;

        heatFurnace();

        iIsBurning = false;
        for(int tStack = 0; tStack < FUELSTACK_AMOUNT; tStack++)
        {
            if (iFuelStackBurningTime[tStack] > -1)
            {
                iIsBurning = true;
                break;
            }
        }

        heatIngots();

        iLastAddedHeat = iCurrentTemperature - iLastTemperature;

        if (!worldObj.isRemote)
        {
            markDirty();
        }
    }

    public void heatFurnace()
    {
        calculateHeatTerms();

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

    private void calculateHeatTerms()
    {
        float tTotalMaxTemp = 1500F;
        float tTotalHeatTerm = iPositiveHeat;
        
        if (getDirection() != ForgeDirection.NORTH)
        {
            TileEntity tHeater = worldObj.getTileEntity(xCoord + ForgeDirection.NORTH.offsetX, yCoord, zCoord + ForgeDirection.NORTH.offsetZ);
            if (tHeater instanceof TileEntityHeater)
            {
                if (((TileEntityHeater) tHeater).getDirection() == ForgeDirection.NORTH)
                {
                    if (((TileEntityHeater) tHeater).tryDamageFan(1)) {
                        tTotalHeatTerm += 0.1F;
                        tTotalMaxTemp += 250F;
                    }
                }
            }
        }

        if (getDirection() != ForgeDirection.EAST)
        {
            TileEntity tHeater = worldObj.getTileEntity(xCoord + ForgeDirection.EAST.offsetX, yCoord, zCoord + ForgeDirection.EAST.offsetZ);
            if (tHeater instanceof TileEntityHeater)
            {
                if (((TileEntityHeater) tHeater).getDirection() == ForgeDirection.EAST)
                {
                    if (((TileEntityHeater) tHeater).tryDamageFan(1)) {
                        tTotalHeatTerm += 0.1F;
                        tTotalMaxTemp += 250F;
                    }
                }
            }
        }
        
        if (getDirection() != ForgeDirection.SOUTH)
        {
            TileEntity tHeater = worldObj.getTileEntity(xCoord + ForgeDirection.SOUTH.offsetX, yCoord, zCoord + ForgeDirection.SOUTH.offsetZ);
            if (tHeater instanceof TileEntityHeater)
            {
                if (((TileEntityHeater) tHeater).getDirection() == ForgeDirection.SOUTH)
                {
                    if (((TileEntityHeater) tHeater).tryDamageFan(1)) {
                        tTotalHeatTerm += 0.1F;
                        tTotalMaxTemp += 250F;
                    }
                }
            }
        }
        
        if (getDirection() != ForgeDirection.WEST)
        {
            TileEntity tHeater = worldObj.getTileEntity(xCoord + ForgeDirection.WEST.offsetX, yCoord, zCoord + ForgeDirection.WEST.offsetZ);
            if (tHeater instanceof TileEntityHeater)
            {
                if (((TileEntityHeater) tHeater).getDirection() == ForgeDirection.WEST)
                {
                    if (((TileEntityHeater) tHeater).tryDamageFan(1)) {
                        tTotalHeatTerm += 0.1F;
                        tTotalMaxTemp += 250F;
                    }
                }
            }
        }

        if (getDirection() == ForgeDirection.NORTH)
        {
            TileEntity tFirePitLeft = worldObj.getTileEntity(xCoord + ForgeDirection.WEST.offsetX, yCoord, zCoord + ForgeDirection.WEST.offsetZ);
            TileEntity tFirePitRight = worldObj.getTileEntity(xCoord + ForgeDirection.EAST.offsetX, yCoord, zCoord + ForgeDirection.EAST.offsetZ);
            if (tFirePitLeft instanceof TileEntityFirePit)
            {
                if (((TileEntityFirePit) tFirePitLeft).isBurning() && ((TileEntityFirePit) tFirePitLeft).getDirection() == getDirection())
                {
                    tTotalHeatTerm += 0.4F;
                    tTotalMaxTemp += 600F;
                }
            }

            if (tFirePitRight instanceof TileEntityFirePit)
            {
                if (((TileEntityFirePit) tFirePitRight).isBurning() && ((TileEntityFirePit) tFirePitRight).getDirection() == getDirection())
                {
                    tTotalHeatTerm += 0.4F;
                    tTotalMaxTemp += 600F;
                }
            }
        }

        if (getDirection() == ForgeDirection.SOUTH)
        {
            TileEntity tFirePitLeft = worldObj.getTileEntity(xCoord + ForgeDirection.WEST.offsetX, yCoord, zCoord + ForgeDirection.WEST.offsetZ);
            TileEntity tFirePitRight = worldObj.getTileEntity(xCoord + ForgeDirection.EAST.offsetX, yCoord, zCoord + ForgeDirection.EAST.offsetZ);
            if (tFirePitLeft instanceof TileEntityFirePit)
            {
                if (((TileEntityFirePit) tFirePitLeft).isBurning() && ((TileEntityFirePit) tFirePitLeft).getDirection() == getDirection())
                {
                    tTotalHeatTerm += 0.4F;
                    tTotalMaxTemp += 600F;
                }
            }

            if (tFirePitRight instanceof TileEntityFirePit)
            {
                if (((TileEntityFirePit) tFirePitRight).isBurning() && ((TileEntityFirePit) tFirePitRight).getDirection() == getDirection())
                {
                    tTotalHeatTerm += 0.4F;
                    tTotalMaxTemp += 600F;
                }
            }
        }

        if (getDirection() == ForgeDirection.WEST)
        {
            TileEntity tFirePitLeft = worldObj.getTileEntity(xCoord + ForgeDirection.SOUTH.offsetX, yCoord, zCoord + ForgeDirection.SOUTH.offsetZ);
            TileEntity tFirePitRight = worldObj.getTileEntity(xCoord + ForgeDirection.NORTH.offsetX, yCoord, zCoord + ForgeDirection.NORTH.offsetZ);
            if (tFirePitLeft instanceof TileEntityFirePit)
            {
                if (((TileEntityFirePit) tFirePitLeft).isBurning() && ((TileEntityFirePit) tFirePitLeft).getDirection() == getDirection())
                {
                    tTotalHeatTerm += 0.4F;
                    tTotalMaxTemp += 600F;
                }
            }

            if (tFirePitRight instanceof TileEntityFirePit)
            {
                if (((TileEntityFirePit) tFirePitRight).isBurning() && ((TileEntityFirePit) tFirePitRight).getDirection() == getDirection())
                {
                    tTotalHeatTerm += 0.4F;
                    tTotalMaxTemp += 600F;
                }
            }
        }

        if (getDirection() == ForgeDirection.EAST)
        {
            TileEntity tFirePitLeft = worldObj.getTileEntity(xCoord + ForgeDirection.NORTH.offsetX, yCoord, zCoord + ForgeDirection.NORTH.offsetZ);
            TileEntity tFirePitRight = worldObj.getTileEntity(xCoord + ForgeDirection.SOUTH.offsetX, yCoord, zCoord + ForgeDirection.SOUTH.offsetZ);
            if (tFirePitLeft instanceof TileEntityFirePit)
            {
                if (((TileEntityFirePit) tFirePitLeft).isBurning() && ((TileEntityFirePit) tFirePitLeft).getDirection() == getDirection())
                {
                    tTotalHeatTerm += 0.4F;
                    tTotalMaxTemp += 600F;
                }
            }

            if (tFirePitRight instanceof TileEntityFirePit)
            {
                if (((TileEntityFirePit) tFirePitRight).isBurning() && ((TileEntityFirePit) tFirePitRight).getDirection() == getDirection())
                {
                    tTotalHeatTerm += 0.4F;
                    tTotalMaxTemp += 600F;
                }
            }
        }

        TileEntity tBelow1 =  worldObj.getTileEntity(xCoord, yCoord - 1, zCoord);
        TileEntity tBelow2 =  worldObj.getTileEntity(xCoord, yCoord - 2, zCoord);
        if (tBelow1 instanceof TileEntityFirePit)
        {
            if (((TileEntityFirePit) tBelow1).isBurning() && ((TileEntityFirePit) tBelow1).getDirection() == getDirection())
            {
                tTotalHeatTerm += 0.55F;
                tTotalMaxTemp += 750F;
            }
        }

        if (tBelow2 instanceof TileEntityFirePit)
        {
            if (((TileEntityFirePit) tBelow2).isBurning() && ((TileEntityFirePit) tBelow2).getDirection() == getDirection())
            {
                tTotalHeatTerm += 0.8F;
                tTotalMaxTemp += 900F;
            }
        }


        iPositiveHeatTerm = tTotalHeatTerm;
        iNegativeHeatTerm = iNegativeHeat;
        iMaxTemperature = tTotalMaxTemp;
    }
        
    public void heatIngots()
    {
        if ((iLastAddedHeat == 0F) && (iCurrentTemperature <= 20F) && (getIngotAmount() == 0)) {
            return;
        }

        iCurrentTemperature += iLastAddedHeat;

        if (iCurrentTemperature > 20F)
        {
            iCurrentTemperature += iNegativeHeatTerm;
        }

        for (int tIngotStackCount = 0; tIngotStackCount < INGOTSTACKS_AMOUNT; tIngotStackCount ++) {
            if (iIngotStacks[tIngotStackCount] == null) {
                continue;
            }

            if ((iCurrentTemperature > 20F) && !(iIngotStacks[tIngotStackCount].getItem() instanceof ItemHeatedItem) && HeatedItemFactory.getInstance().isHeatable(iIngotStacks[tIngotStackCount]))
            {
                iIngotStacks[tIngotStackCount] = HeatedItemFactory.getInstance().convertToHeatedIngot(iIngotStacks[tIngotStackCount]);
            }

            float tCurrentStackTemp = ItemHeatedItem.getItemTemperature(iIngotStacks[tIngotStackCount]);
            float tCurrentStackCoefficient = MaterialRegistry.getInstance().getMaterial(HeatedItemFactory.getInstance().getMaterialIDFromItemStack(iIngotStacks[tIngotStackCount])).getHeatCoefficient();

            float tSourceDifference = iNegativeHeatTerm - tCurrentStackCoefficient;
            float tTargetDifference = -1 * tSourceDifference + iNegativeHeatTerm;


            if (tCurrentStackTemp < 20F) {
                iIngotStacks[tIngotStackCount] = HeatedItemFactory.getInstance().convertToCooledIngot(iIngotStacks[tIngotStackCount]);
            } else if (tCurrentStackTemp <= iCurrentTemperature) {
                iCurrentTemperature += tSourceDifference;
                ItemHeatedItem.setItemTemperature(iIngotStacks[tIngotStackCount], ItemHeatedItem.getItemTemperature(iIngotStacks[tIngotStackCount]) + tTargetDifference);

                float tMeltingPoint = HeatedItemFactory.getInstance().getMeltingPointFromMaterial(iIngotStacks[tIngotStackCount]);
                if (tCurrentStackTemp > tMeltingPoint)
                {
                    iIngotStacks[tIngotStackCount] = null;
                }
            } else if (ItemHeatedItem.getItemTemperature(iIngotStacks[tIngotStackCount]) > iCurrentTemperature) {
                iCurrentTemperature += tTargetDifference;
                ItemHeatedItem.setItemTemperature(iIngotStacks[tIngotStackCount], ItemHeatedItem.getItemTemperature(iIngotStacks[tIngotStackCount]) + tSourceDifference);

                float tMeltingPoint = HeatedItemFactory.getInstance().getMeltingPointFromMaterial(iIngotStacks[tIngotStackCount]);
                if (tCurrentStackTemp > tMeltingPoint) {
                    iIngotStacks[tIngotStackCount] = null;
                }
            }
        }
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
