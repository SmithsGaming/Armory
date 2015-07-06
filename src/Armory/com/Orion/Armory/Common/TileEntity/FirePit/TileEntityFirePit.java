package com.Orion.Armory.Common.TileEntity.FirePit;
/*
/  TileEntityFirePit
/  Created by : Orion
/  Created on : 02/10/2014
*/

import com.Orion.Armory.Client.Renderer.TileEntities.FirePitTESR;
import com.Orion.Armory.Common.Factory.HeatedItemFactory;
import com.Orion.Armory.Common.Item.ItemHeatedItem;
import com.Orion.Armory.Common.Material.MaterialRegistry;
import com.Orion.Armory.Common.PathFinding.IPathComponent;
import com.Orion.Armory.Common.Registry.GeneralRegistry;
import com.Orion.Armory.Common.TileEntity.Core.Multiblock.IStructureComponent;
import com.Orion.Armory.Common.TileEntity.Core.Multiblock.IStructureData;
import com.Orion.Armory.Common.TileEntity.Core.TileEntityArmory;
import com.Orion.Armory.Network.Messages.MessageTileEntityFirePit;
import com.Orion.Armory.Network.Messages.Structure.MessageOnCreateSlaveEntity;
import com.Orion.Armory.Network.Messages.Structure.MessageOnUpdateMasterData;
import com.Orion.Armory.Network.NetworkManager;
import com.Orion.Armory.Network.StructureNetworkManager;
import com.Orion.Armory.Util.Core.Coordinate;
import com.Orion.Armory.Util.Core.NBTHelper;
import com.Orion.Armory.Util.Core.Rectangle;
import com.Orion.Armory.Util.References;
import com.sun.javaws.exceptions.InvalidArgumentException;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.HashMap;

public class TileEntityFirePit extends TileEntityArmory implements IInventory, IStructureComponent {

    public static int INGOTSTACKS_AMOUNT = 5;
    public static int FUELSTACK_AMOUNT = 5;

    public float iPositiveHeat = 0.325F;
    public float iNegativeHeat = -0.1F;
    public float iPositiveHeatTerm = 0.325F;
    public float iNegativeHeatTerm = -0.1F;

    public ItemStack[] iIngotStacks = new ItemStack[INGOTSTACKS_AMOUNT];
    public ItemStack[] iFuelStacks = new ItemStack[FUELSTACK_AMOUNT];

    //public Integer[] iFuelStackBurningTime = new Integer[FUELSTACK_AMOUNT];
    //public Integer[] iFuelStackFuelAmount = new Integer[FUELSTACK_AMOUNT];

    public float iMaxTemperature = 1500;
    public float iCurrentTemperature = 20;
    public float iLastTemperature = 20;
    public float iLastAddedHeat = 0;
    public boolean iIsBurning = false;

    IStructureComponent iMasterComponent;
    HashMap<Coordinate, IStructureComponent> iSlaveComponents;
    Rectangle iStructureBounds;
    IStructureData iData = new FirePitStructureData();

    boolean iInitialSetupPacketSend = false;
    boolean iIsSlavesInitialized = false;
    ArrayList<Coordinate> iNBTCoordinates = new ArrayList<Coordinate>();
    Coordinate iMasterLocation;

    float iHeuristicPathFindingDistance = 0;

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

        iIsBurning = pCompound.getBoolean(References.NBTTagCompoundData.TE.FirePit.CURRENTLYBURNING);
        iCurrentTemperature = pCompound.getFloat(References.NBTTagCompoundData.TE.FirePit.CURRENTTEMPERATURE);
        iMaxTemperature = pCompound.getFloat(References.NBTTagCompoundData.TE.FirePit.MAXTEMPERATURE);
        iLastAddedHeat = pCompound.getFloat(References.NBTTagCompoundData.TE.FirePit.LASTADDEDHEAT);

        if (!iInitialSetupPacketSend) {
            readStructureFromNBT(pCompound);
            iInitialSetupPacketSend = true;
        }

        iData.readFromNBT(pCompound);
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

        pCompound.setBoolean(References.NBTTagCompoundData.TE.FirePit.CURRENTLYBURNING, iIsBurning);
        pCompound.setFloat(References.NBTTagCompoundData.TE.FirePit.CURRENTTEMPERATURE, iCurrentTemperature);
        pCompound.setFloat(References.NBTTagCompoundData.TE.FirePit.MAXTEMPERATURE, iMaxTemperature);
        pCompound.setFloat(References.NBTTagCompoundData.TE.FirePit.LASTADDEDHEAT, iLastAddedHeat);

        writeStructureToNBT(pCompound);
        iData.writeToNBT(pCompound);
    }


    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        if (isSlaved())
            return super.getRenderBoundingBox();

        if (iStructureBounds == null)
            return super.getRenderBoundingBox();

        //return iStructureBounds.toBoundingBox();
        return super.getRenderBoundingBox();
    }

    @Override
    public float getProgressBarValue(String pProgressBarID) {
        IStructureData tData = getStructureRelevantData();
        if ((Float) tData.getData(this, References.NBTTagCompoundData.TE.FirePit.FUELSTACKBURNINGTIME) <  1F)
            return -1F;

        return 1 - ((Float) tData.getData(this, References.NBTTagCompoundData.TE.FirePit.FUELSTACKBURNINGTIME) / (Float) tData.getData(this, References.NBTTagCompoundData.TE.FirePit.FUELSTACKFUELAMOUNT));
    }





    @Override
    public void updateEntity()
    {
        if (((iMasterLocation != null) && (iMasterComponent == null)) || (!iIsSlavesInitialized))
        {
            regenStructure();
            iIsSlavesInitialized = true;
        }

        IStructureData tData = getStructureRelevantData();

        iLastTemperature = iCurrentTemperature;
        iIsBurning = false;

        heatFurnace();

        iIsBurning = ((Float) tData.getData(this, References.NBTTagCompoundData.TE.FirePit.FUELSTACKBURNINGTIME) >= 1F);

        heatIngots();

        iLastAddedHeat = iCurrentTemperature - iLastTemperature;

        if (!worldObj.isRemote)
        {
            this.markDirty();
        }
    }

    public void heatFurnace()
    {
        calculateHeatTerms();

        iLastAddedHeat = 0F;

        IStructureData tData = getStructureRelevantData();

        if ((Float) tData.getData(this, References.NBTTagCompoundData.TE.FirePit.FUELSTACKBURNINGTIME) < 1F)
        {
            for (int tFuelStackIndex = 0; tFuelStackIndex < FUELSTACK_AMOUNT; tFuelStackIndex++) {

                if (iFuelStacks[tFuelStackIndex] == null)
                {
                    continue;
                }

                ItemStack tTargetedFuelStack = iFuelStacks[tFuelStackIndex];

                //Check if the stack is a valid Fuel in the Furnace
                if ((tTargetedFuelStack != null) && (TileEntityFurnace.isItemFuel(tTargetedFuelStack))) {
                    --tTargetedFuelStack.stackSize;

                    tData.setData(this, References.NBTTagCompoundData.TE.FirePit.FUELSTACKBURNINGTIME, (Float) tData.getData(this, References.NBTTagCompoundData.TE.FirePit.FUELSTACKBURNINGTIME) + TileEntityFurnace.getItemBurnTime(tTargetedFuelStack));

                    if (tTargetedFuelStack.stackSize == 0) {
                        iFuelStacks[tFuelStackIndex] = tTargetedFuelStack.getItem().getContainerItem(tTargetedFuelStack);
                    }

                }

            }

            tData.setData(this, References.NBTTagCompoundData.TE.FirePit.FUELSTACKFUELAMOUNT, tData.getData(this, References.NBTTagCompoundData.TE.FirePit.FUELSTACKBURNINGTIME));
        }

        if ((Float) tData.getData(this, References.NBTTagCompoundData.TE.FirePit.FUELSTACKBURNINGTIME) >= 1F)
        {

            tData.setData(this, References.NBTTagCompoundData.TE.FirePit.FUELSTACKBURNINGTIME, (Object) ( (Float) tData.getData(this, References.NBTTagCompoundData.TE.FirePit.FUELSTACKBURNINGTIME) - 1F));
            iLastAddedHeat += iPositiveHeatTerm;
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
        //NetworkManager.INSTANCE.sendToAllAround(new MessageTileEntityFirePit(this), new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId,(double) this.xCoord,(double) this.yCoord,(double) this.zCoord, 128));
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);

        super.markDirty();
        worldObj.func_147451_t(xCoord, yCoord, zCoord);
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound syncData = new NBTTagCompound();
        this.writeToNBT(syncData);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, syncData);
    }

    @Override
    public void onDataPacket(net.minecraft.network.NetworkManager net, S35PacketUpdateTileEntity pkt) {
        this.readFromNBT(pkt.func_148857_g());
    }



    @Override
    public String getStructureType() {
        return References.InternalNames.TileEntities.Structures.FirePit;
    }

    @Override
    public HashMap<Coordinate, IStructureComponent> getSlaveEntities() {
        return iSlaveComponents;
    }

    @Override
    public void registerNewSlave(TileEntity pNewSlaveEntity) throws InvalidArgumentException {
        if (!(pNewSlaveEntity instanceof IStructureComponent))
            throw new InvalidArgumentException(new String[]{"The given TE is not a Slaveble type." + pNewSlaveEntity.xCoord + "-" + pNewSlaveEntity.yCoord + "-" + pNewSlaveEntity.zCoord});

        if (!(((IStructureComponent) pNewSlaveEntity).getStructureType().equals(this.getStructureType())))
            throw new InvalidArgumentException(new String[]{"The given TE is not of the correct structure type!" + pNewSlaveEntity.xCoord + "-" + pNewSlaveEntity.yCoord + "-" + pNewSlaveEntity.zCoord});

        if (iSlaveComponents.containsKey(new Coordinate(pNewSlaveEntity.xCoord, pNewSlaveEntity.yCoord, pNewSlaveEntity.zCoord))) {
            throw new InvalidArgumentException(new String[]{"There is already a TE registered to that Coordinate" + pNewSlaveEntity.xCoord + "-" + pNewSlaveEntity.yCoord + "-" + pNewSlaveEntity.zCoord});
        }

        iSlaveComponents.put(new Coordinate(pNewSlaveEntity.xCoord, pNewSlaveEntity.yCoord, pNewSlaveEntity.zCoord), (IStructureComponent) pNewSlaveEntity);

        if (iStructureBounds == null)
        {
            iStructureBounds = new Rectangle(xCoord, yCoord, zCoord, 0,0);
        }

        if (!iStructureBounds.contains(pNewSlaveEntity.xCoord, pNewSlaveEntity.yCoord))
        {
            iStructureBounds.includeHorizontal(new Coordinate(pNewSlaveEntity.xCoord, pNewSlaveEntity.yCoord, pNewSlaveEntity.zCoord));
        }
    }

    @Override
    public void removeSlave(Coordinate pSlaveCoordinate) {
        iSlaveComponents.remove(pSlaveCoordinate);
    }

    @Override
    public Rectangle getStructureSpace() {
        if (isSlaved())
            return getMasterEntity().getStructureSpace();

        return iStructureBounds;
    }

    @Override
    public void initiateAsMasterEntity() {
        iStructureBounds = new Rectangle(this.xCoord, this.yCoord, this.zCoord, 0, 0);
        iMasterComponent = null;
        iMasterLocation = null;
        iSlaveComponents = new HashMap<Coordinate, IStructureComponent>();
        iData = new FirePitStructureData();
    }

    @Override
    public IStructureData getStructureRelevantData() {
        if (isSlaved())
            return getMasterEntity().getStructureRelevantData();

        return iData;
    }

    @Override
    public void setStructureData(IStructureData pNewData) {
        if (isSlaved())
        {
            getMasterEntity().setStructureData(pNewData);
            return;
        }

        iData = pNewData;
    }


    @Override
    public float getDistanceToMasterEntity() {
        if (!isSlaved())
            return 0;

        TileEntityFirePit tMasterEntity = (TileEntityFirePit) getMasterEntity();

        return (float) Math.sqrt(Math.pow(tMasterEntity.xCoord, 2) + Math.pow(tMasterEntity.zCoord, 2));
    }

    @Override
    public boolean isSlaved() {
        return iMasterComponent != null;
    }

    @Override
    public IStructureComponent getMasterEntity() {
        if (iMasterComponent == null)
            return this;

        return iMasterComponent;
    }

    @Override
    public void initiateAsSlaveEntity(IStructureComponent pMasterEntity) {
        iMasterComponent = pMasterEntity;
    }

    @Override
    public boolean countsAsConnectingComponent() {
        return true;
    }



    @Override
    public void writeStructureToNBT(NBTTagCompound pTileEntityCompound) {
        NBTTagCompound tStructureCompound = new NBTTagCompound();
        tStructureCompound.setBoolean(References.NBTTagCompoundData.TE.Basic.Structures.ISSLAVE, isSlaved());

        if (!isSlaved())
        {
            NBTTagList tSlaveList = new NBTTagList();
            for (Coordinate tSlaveCoord : iSlaveComponents.keySet())
            {
                NBTTagCompound tCoordinateCompound = NBTHelper.writeCoordinateToNBT(tSlaveCoord);
                tSlaveList.appendTag(tCoordinateCompound);
            }

            tStructureCompound.setTag(References.NBTTagCompoundData.TE.Basic.Structures.COORDINATES, tSlaveList);
        }
        else
        {
            tStructureCompound.setTag(References.NBTTagCompoundData.TE.Basic.Structures.MASTERTE, NBTHelper.writeCoordinateToNBT(iMasterComponent.getLocation()));
        }

        pTileEntityCompound.setTag(References.NBTTagCompoundData.TE.Basic.STRUCTUREDATA, tStructureCompound);
    }

    @Override
    public void readStructureFromNBT(NBTTagCompound pTileEntityCompound) {
        NBTTagCompound tStructureCompound = pTileEntityCompound.getCompoundTag(References.NBTTagCompoundData.TE.Basic.STRUCTUREDATA);

        if (tStructureCompound.getBoolean(References.NBTTagCompoundData.TE.Basic.Structures.ISSLAVE))
        {
            iMasterLocation = NBTHelper.readCoordinateFromNBT(tStructureCompound.getCompoundTag(References.NBTTagCompoundData.TE.Basic.Structures.MASTERTE));
            return;
        }

        initiateAsMasterEntity();
        NBTTagList tSlaveList = tStructureCompound.getTagList(References.NBTTagCompoundData.TE.Basic.Structures.COORDINATES, 10);
        for (int tTagIndex = 0; tTagIndex < tSlaveList.tagCount(); tTagIndex++) {
            Coordinate tSlaveCoordinate = NBTHelper.readCoordinateFromNBT(tSlaveList.getCompoundTagAt(tTagIndex));

            if (iSlaveComponents.containsKey(tSlaveCoordinate))
                continue;

            if (iIsSlavesInitialized)
            {
                try {
                    registerNewSlave(worldObj.getTileEntity(tSlaveCoordinate.getXComponent(), tSlaveCoordinate.getYComponent(), tSlaveCoordinate.getZComponent()));
                } catch (InvalidArgumentException e) {
                    GeneralRegistry.iLogger.error("Failed to create a Master Slave link from NBTSync", e);
                }
            }
            else
            {
                iNBTCoordinates.add(tSlaveCoordinate);
            }
        }
    }

    @Override
    public Coordinate getLocation() {
        return new Coordinate(this.xCoord, this.yCoord, this.zCoord);
    }

    @Override
    public TileEntitySpecialRenderer getRenderer(IStructureComponent pComponentToBeRendered) {
        return new FirePitTESR();
    }



    public void regenStructure()
    {
        if (iMasterComponent != null)
            return;

        if (iMasterLocation != null)
        {
            TileEntity tMasterEntity = worldObj.getTileEntity(iMasterLocation.getXComponent(), iMasterLocation.getYComponent(), iMasterLocation.getZComponent());
            if (tMasterEntity == null)
                return;

            initiateAsSlaveEntity((IStructureComponent) tMasterEntity);
            StructureNetworkManager.INSTANCE.sendToAllAround(new MessageOnCreateSlaveEntity(this, (IStructureComponent) tMasterEntity), new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId,(double) this.xCoord,(double) this.yCoord,(double) this.zCoord, 128));
            return;
        }

        initiateAsMasterEntity();

        for(Coordinate tStoredCoordinate : iNBTCoordinates)
        {
            TileEntity tSlaveEntity = worldObj.getTileEntity(tStoredCoordinate.getXComponent(), tStoredCoordinate.getYComponent(), tStoredCoordinate.getZComponent());

            if (tSlaveEntity == null)
            {
                GeneralRegistry.iLogger.error("Failed to reregister a TE from NBT. No TE exists on the stored coordinates!");
                continue;
            }

            ((IStructureComponent) tSlaveEntity).initiateAsSlaveEntity(this);
            try{
                this.registerNewSlave(tSlaveEntity);
                StructureNetworkManager.INSTANCE.sendToAllAround(new MessageOnCreateSlaveEntity((IStructureComponent) tSlaveEntity, this), new NetworkRegistry.TargetPoint(tSlaveEntity.getWorldObj().provider.dimensionId, (double) tSlaveEntity.xCoord, (double) tSlaveEntity.yCoord, (double) tSlaveEntity.zCoord, 128));
            }
            catch(Exception IAEx)
            {
                continue;
            }
        }
        StructureNetworkManager.INSTANCE.sendToAllAround(new MessageOnUpdateMasterData(this), new NetworkRegistry.TargetPoint(getWorldObj().provider.dimensionId, (double) xCoord, (double) yCoord, (double) zCoord, 128));
    }



    @Override
    public float getHeuristicDistance() {
        return iHeuristicPathFindingDistance;
    }

    @Override
    public void setHeuristicDistance(float pNewDistance) {
        iHeuristicPathFindingDistance = pNewDistance;
    }



    @Override
    public ArrayList<IPathComponent> getValidPathableNeighborComponents() {
        ArrayList<IPathComponent> tPathableComponents = new ArrayList<IPathComponent>();

        for(ForgeDirection tNeigborDirection : ForgeDirection.values())
        {
            if ((tNeigborDirection == ForgeDirection.UP) || (tNeigborDirection == ForgeDirection.DOWN) || (tNeigborDirection == ForgeDirection.UNKNOWN))
                continue;

            TileEntity tNeighborEntity = getWorldObj().getTileEntity(xCoord + tNeigborDirection.offsetX, yCoord + tNeigborDirection.offsetY, zCoord + tNeigborDirection.offsetZ);
            if (tNeighborEntity == null)
                continue;

            if (!(tNeighborEntity instanceof IStructureComponent))
                continue;

            if (!((IStructureComponent) tNeighborEntity).getStructureType().equals(getStructureType()))
                continue;

            if (!((IStructureComponent) tNeighborEntity).countsAsConnectingComponent())
                continue;

            tPathableComponents.add((IPathComponent) tNeighborEntity);
        }

        return tPathableComponents;
    }


    @Override
    public int compareTo(IPathComponent pComponent) {
        if (pComponent.getHeuristicDistance() > getHeuristicDistance())
            return -1;

        if (pComponent.getHeuristicDistance() < getHeuristicDistance())
            return 1;

        return 0;
    }
}



/*



 */
