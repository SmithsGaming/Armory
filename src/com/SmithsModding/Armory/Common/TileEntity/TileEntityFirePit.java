/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.SmithsModding.Armory.Common.TileEntity;
/*
/  TileEntityFirePit
/  Created by : Orion
/  Created on : 02/10/2014
*/


import com.SmithsModding.Armory.API.Item.*;
import com.SmithsModding.Armory.API.Materials.*;
import com.SmithsModding.Armory.*;
import com.SmithsModding.Armory.Common.Factory.*;
import com.SmithsModding.Armory.Common.Item.*;
import com.SmithsModding.Armory.Common.Material.*;
import com.SmithsModding.Armory.Common.Registry.*;
import com.SmithsModding.Armory.Common.TileEntity.GUIManagers.*;
import com.SmithsModding.Armory.Common.TileEntity.State.*;
import com.SmithsModding.Armory.Util.*;
import com.SmithsModding.SmithsCore.Common.Fluid.*;
import com.SmithsModding.SmithsCore.Common.PathFinding.*;
import com.SmithsModding.SmithsCore.Common.Structures.*;
import com.SmithsModding.SmithsCore.Network.Structure.Messages.*;
import com.SmithsModding.SmithsCore.Network.Structure.*;
import com.SmithsModding.SmithsCore.*;
import com.SmithsModding.SmithsCore.Util.Common.*;
import com.SmithsModding.SmithsCore.Util.Common.Postioning.*;
import com.google.common.base.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fml.common.network.*;

import java.util.*;
import java.util.concurrent.*;

public class TileEntityFirePit extends TileEntityArmory implements IInventory, ITickable, IFirePitComponent, IStructureComponent, IFluidContainingEntity {

    public static int INGOTSTACKS_AMOUNT = 5;
    public static int FUELSTACK_AMOUNT = 5;
    public static float POSITIVEHEAT = 2.625F;
    public static float NEGATIVEHEAT = 0.75F;
    public static int STRUCTURECOMPONENTADDITION = 1425;

    private float positiveHeatTerm = 0.625F;
    private float negativeHeatTerm = -0.25F;
    private ItemStack[] ingotStacks = new ItemStack[INGOTSTACKS_AMOUNT];
    private ItemStack[] fuelStacks = new ItemStack[FUELSTACK_AMOUNT];

    private ArrayList<FluidStack> moltenMetals = new ArrayList<FluidStack>();

    private float heatedProcentage;

    private IStructureComponent masterComponent;
    private HashMap<Coordinate3D, IStructureComponent> slaveComponents;
    private Cube structureBounds;

    private boolean slavesInitialized = false;
    private ArrayList<Coordinate3D> slaveCoordinates = new ArrayList<Coordinate3D>();
    private Coordinate3D masterCoordinate;

    /**
     * Constructor for a new FirePit
     */
    public TileEntityFirePit () {
        super(new FirePitState(), null);
        this.setManager(new FirePitGuiManager(this));
    }


    @Override
    public int getSizeInventory () {
        return INGOTSTACKS_AMOUNT + FUELSTACK_AMOUNT;
    }

    public ItemStack getStackInSlot (int pSlotID) {
        if (pSlotID < INGOTSTACKS_AMOUNT) {
            return ingotStacks[pSlotID];
        }

        if (pSlotID < INGOTSTACKS_AMOUNT + FUELSTACK_AMOUNT) {
            return fuelStacks[pSlotID - INGOTSTACKS_AMOUNT];
        }

        return null;
    }

    @Override
    public ItemStack decrStackSize (int pSlotIndex, int pDecrAmount) {
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
    public ItemStack removeStackFromSlot (int index) {
        return null;
    }

    @Override
    public void setInventorySlotContents (int pSlotIndex, ItemStack pNewItemStack) {
        if (pSlotIndex < INGOTSTACKS_AMOUNT) {
            ItemStack pSettingStack = null;
            if (pNewItemStack != null) {
                pSettingStack = pNewItemStack.copy();
                pSettingStack.stackSize = 1;
            }

            ingotStacks[pSlotIndex] = pSettingStack;
            if (pNewItemStack != null && pNewItemStack.stackSize > 1) {
                --pNewItemStack.stackSize;
            }
        } else if (pSlotIndex < INGOTSTACKS_AMOUNT + FUELSTACK_AMOUNT) {
            fuelStacks[pSlotIndex - INGOTSTACKS_AMOUNT] = pNewItemStack;
        }
    }

    @Override
    public String getName () {
        return this.hasCustomName() ? super.getName() : StatCollector.translateToLocal(References.InternalNames.Blocks.FirePit);
    }

    @Override
    public boolean hasCustomName () {
        return ( ( super.getName().length() > 0 ) && !super.getName().isEmpty() );
    }

    @Override
    public int getInventoryStackLimit () {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer (EntityPlayer pPlayer) {
        return true;
    }


    @Override
    public void openInventory (EntityPlayer player) {
        //No animation and definitely no cat on top of this nice puppy
    }

    @Override
    public void closeInventory (EntityPlayer player) {
        //NOOP
    }


    @Override
    public boolean isItemValidForSlot (int pSlotIndex, ItemStack pItemStack) {
        if (pSlotIndex < INGOTSTACKS_AMOUNT) {
            if (pItemStack.getItem() instanceof ItemHeatedItem) {
                return true;
            }

            return HeatableItemRegistry.getInstance().isHeatable(pItemStack);
        } else if (pSlotIndex < INGOTSTACKS_AMOUNT + FUELSTACK_AMOUNT) {
            return TileEntityFurnace.isItemFuel(pItemStack);
        }

        return false;
    }

    @Override
    public int getField (int id) {
        return 0;  //Custom GUI Sytstem -> Not needed
    }

    @Override
    public void setField (int id, int value) {
        //NOOP
    }

    @Override
    public int getFieldCount () {
        return 0;
    }

    @Override
    public void clear () {
        ingotStacks = new ItemStack[5];
        fuelStacks = new ItemStack[5];
    }


    @Override
    public void readFromNBT (NBTTagCompound compound) {
        super.readFromNBT(compound);

        readStructureFromNBT(compound);
    }

    @Override
    public void writeToNBT (NBTTagCompound compound) {
        super.writeToNBT(compound);

        writeStructureToNBT(compound);
    }

    @Override
    public void update () {
        Stopwatch updateWatch = Stopwatch.createStarted();
        Stopwatch operationWatch = Stopwatch.createStarted();

        long structureRegenTimeInMs = 0;
        long structureHeatFurnaceTimeInMs = 0;
        long structureHeatIngotsTimeInMs = 0;
        long structureMeltIngotsTimeInMs = 0;
        long structureUpdateTimeInMs = 0;

        if (( ( masterCoordinate != null ) && ( masterComponent == null ) ) || ( !slavesInitialized )) {
            regenStructure();
            slavesInitialized = true;

            structureRegenTimeInMs = operationWatch.elapsed(TimeUnit.MILLISECONDS);
            operationWatch = operationWatch.reset();
            operationWatch.start();
        }

        FirePitState state = (FirePitState) getStructureRelevantData();

        state.setLastTemperature(state.getCurrentTemperature());
        state.setBurning(false);

        heatFurnace();

        structureHeatFurnaceTimeInMs = operationWatch.elapsed(TimeUnit.MILLISECONDS);
        operationWatch = operationWatch.reset();
        operationWatch.start();

        state.setBurning(( (Float) state.getData(this, References.NBTTagCompoundData.TE.FirePit.FUELSTACKBURNINGTIME) >= 1F ));

        heatIngots();

        structureHeatIngotsTimeInMs = operationWatch.elapsed(TimeUnit.MILLISECONDS);
        operationWatch = operationWatch.reset();
        operationWatch.start();

        meltIngots();

        structureMeltIngotsTimeInMs = operationWatch.elapsed(TimeUnit.MILLISECONDS);
        operationWatch = operationWatch.reset();
        operationWatch.start();

        state.setLastAddedHeat(state.getCurrentTemperature() - state.getLastTemperature());

        if (!worldObj.isRemote) {
            this.markDirty();

            for (Coordinate3D coordinate : slaveCoordinates) {
                if (coordinate.equals(this.getLocation()))
                    continue;

                worldObj.markBlockForUpdate(coordinate.toBlockPos());
            }
        }

        structureUpdateTimeInMs = operationWatch.elapsed(TimeUnit.MILLISECONDS);
        operationWatch = operationWatch.reset();
        operationWatch.start();

        if (updateWatch.elapsed(TimeUnit.MILLISECONDS) > (50) && SmithsCore.isInDevenvironment()) {
            Armory.getLogger().info("TICK Took extremely long: " + updateWatch.elapsed(TimeUnit.MILLISECONDS) + " ms!");
            Armory.getLogger().info("   -> Structure regen time:  " + structureRegenTimeInMs + " ms!");
            Armory.getLogger().info("   -> Furnace heat up time:  " + structureHeatFurnaceTimeInMs + " ms!");
            Armory.getLogger().info("   -> Ingot heat up time:    " + structureHeatIngotsTimeInMs + " ms!");
            Armory.getLogger().info("   -> Ingot melt time:       " + structureMeltIngotsTimeInMs + " ms!");
            Armory.getLogger().info("   -> Structure update time: " + structureUpdateTimeInMs + " ms!");
        }
        else if(SmithsCore.isInDevenvironment() && false)
        {
            if(isSlaved())
            {
                Armory.getLogger().info("Running Tick on slave: " + pos.toString() + " took: " + updateWatch.elapsed(TimeUnit.MILLISECONDS) + " ms!");
            }
            else
            {
                Armory.getLogger().info("Running Tick on master:" + pos.toString() + " took: " + updateWatch.elapsed(TimeUnit.MILLISECONDS) + " ms!");
            }
        }

        operationWatch.stop();
        updateWatch.stop();
    }

    public void heatFurnace () {
        calculateHeatTerms();

        FirePitState state = (FirePitState) getStructureRelevantData();

        state.setLastAddedHeat(0F);

        IStructureData tData = getStructureRelevantData();

        if ((Float) tData.getData(this, References.NBTTagCompoundData.TE.FirePit.FUELSTACKBURNINGTIME) < 1F) {
            for (int tFuelStackIndex = 0; tFuelStackIndex < FUELSTACK_AMOUNT; tFuelStackIndex++) {

                if (fuelStacks[tFuelStackIndex] == null) {
                    continue;
                }

                ItemStack tTargetedFuelStack = fuelStacks[tFuelStackIndex];

                //Check if the stack is a valid Fuel in the Furnace
                if (( tTargetedFuelStack != null ) && ( TileEntityFurnace.isItemFuel(tTargetedFuelStack) )) {
                    --tTargetedFuelStack.stackSize;

                    tData.setData(this, References.NBTTagCompoundData.TE.FirePit.FUELSTACKBURNINGTIME, (Float) tData.getData(this, References.NBTTagCompoundData.TE.FirePit.FUELSTACKBURNINGTIME) + TileEntityFurnace.getItemBurnTime(tTargetedFuelStack));

                    if (tTargetedFuelStack.stackSize == 0) {
                        fuelStacks[tFuelStackIndex] = tTargetedFuelStack.getItem().getContainerItem(tTargetedFuelStack);
                    }

                }

            }

            tData.setData(this, References.NBTTagCompoundData.TE.FirePit.FUELSTACKFUELAMOUNT, tData.getData(this, References.NBTTagCompoundData.TE.FirePit.FUELSTACKBURNINGTIME));
        }

        if ((Float) tData.getData(this, References.NBTTagCompoundData.TE.FirePit.FUELSTACKBURNINGTIME) >= 1F) {

            tData.setData(this, References.NBTTagCompoundData.TE.FirePit.FUELSTACKBURNINGTIME, (Float) tData.getData(this, References.NBTTagCompoundData.TE.FirePit.FUELSTACKBURNINGTIME) - 1F);
            state.setLastAddedHeat(state.getLastAddedHeat() + positiveHeatTerm);
        }

        heatedProcentage = Math.round(( state.getCurrentTemperature() / state.getMaxTemperature() ) * 100) / 100F;
        state.setLastAddedHeat(state.getLastAddedHeat() * ( 1 - heatedProcentage ));
    }

    private void calculateHeatTerms () {
        FirePitState state = (FirePitState) getStructureRelevantData();

        state.setMaxTemperature(1500);
        positiveHeatTerm = POSITIVEHEAT;
        negativeHeatTerm = -NEGATIVEHEAT;

        for (EnumFacing tDirection : EnumFacing.values()) {
            TileEntity tEntity = worldObj.getTileEntity(getPos().add(tDirection.getDirectionVec()));
            if (tEntity instanceof IFirePitComponent) {
                if (( (IFirePitComponent) tEntity ).canInfluenceTE(getLocation())) {
                    positiveHeatTerm += ( (IFirePitComponent) tEntity ).getPositiveInflunce();
                    negativeHeatTerm -= ( (IFirePitComponent) tEntity ).getNegativeInfluece();
                    state.setMaxTemperature(state.getMaxTemperature() + ( (IFirePitComponent) tEntity ).getMaxTempInfluence());
                }
            }
        }
    }

    public void heatIngots () {
        FirePitState state = (FirePitState) getStructureRelevantData();

        if (( state.getLastAddedHeat() == 0F ) && ( state.getCurrentTemperature() <= 20F ) && ( getIngotAmount() == 0 )) {
            return;
        }


        state.setCurrentTemperature(state.getCurrentTemperature() + state.getLastAddedHeat());

        if (state.getCurrentTemperature() > 20F) {
            state.setCurrentTemperature(state.getCurrentTemperature() + ( negativeHeatTerm * heatedProcentage ));
        }

        for (int tIngotStackCount = 0; tIngotStackCount < INGOTSTACKS_AMOUNT; tIngotStackCount++) {
            if (ingotStacks[tIngotStackCount] == null) {
                continue;
            }

            if (( state.getCurrentTemperature() > 20F ) && !( ingotStacks[tIngotStackCount].getItem() instanceof ItemHeatedItem ) && HeatableItemRegistry.getInstance().isHeatable(ingotStacks[tIngotStackCount])) {
                ingotStacks[tIngotStackCount] = HeatedItemFactory.getInstance().convertToHeatedIngot(ingotStacks[tIngotStackCount]);
            }

            ItemStack stack = ingotStacks[tIngotStackCount];
            IArmorMaterial material = HeatableItemRegistry.getInstance().getMaterialFromHeatedStack(stack);

            float tCurrentStackTemp = getItemTemperature(stack);
            float tCurrentStackCoefficient = material.getHeatCoefficient();

            float tSourceDifference = negativeHeatTerm - tCurrentStackCoefficient;
            float tTargetDifference = -1 * tSourceDifference + negativeHeatTerm;


            if (tCurrentStackTemp < 20F) {
                ingotStacks[tIngotStackCount] = HeatedItemFactory.getInstance().convertToCooledIngot(ingotStacks[tIngotStackCount]);
            } else if (tCurrentStackTemp <= state.getCurrentTemperature()) {
                state.setCurrentTemperature(state.getCurrentTemperature() + tSourceDifference);
                setItemTemperature(stack, getItemTemperature(stack) + tTargetDifference);
            } else if (getItemTemperature(stack) > state.getCurrentTemperature()) {
                state.setCurrentTemperature(state.getCurrentTemperature() + tTargetDifference);
                //setItemTemperature(stack, getItemTemperature(stack) + tSourceDifference);
            }

        }
    }

    public float getItemTemperature (ItemStack pItemStack) {
        if (pItemStack.getItem() instanceof ItemHeatedItem) {

            NBTTagCompound stackCompound = NBTHelper.getTagCompound(pItemStack);

            return stackCompound.getFloat(References.NBTTagCompoundData.HeatedIngot.CURRENTTEMPERATURE);
        }


        return 20F;
    }

    public void setItemTemperature (ItemStack pItemStack, float pNewTemp) {
        if (pItemStack.getItem() instanceof ItemHeatedItem) {
            if (pNewTemp < 20F)
                pNewTemp = 20F;

            NBTTagCompound stackCompound = NBTHelper.getTagCompound(pItemStack);

            stackCompound.setFloat(References.NBTTagCompoundData.HeatedIngot.CURRENTTEMPERATURE, pNewTemp);

            pItemStack.setTagCompound(stackCompound);
        }
    }


    public void meltIngots () {
        FirePitState state = (FirePitState) getStructureRelevantData();

        for (int i = 0; i < INGOTSTACKS_AMOUNT; i++) {
            ItemStack stack = ingotStacks[i];

            if (stack == null)
                continue;

            if (!( stack.getItem() instanceof ItemHeatedItem ))
                continue;

            IArmorMaterial material = HeatableItemRegistry.getInstance().getMaterialFromHeatedStack(stack);
            float stackTemp = HeatableItemRegistry.getInstance().getItemTemperature(stack);

            if (state.getCurrentTemperature() < stackTemp * 0.95 && state.getMeltingProgess(i) > -1) {
                ingotStacks[i] = null;
                continue;
            }

            if (stackTemp >= material.getMeltingPoint()) {
                if (material.getMeltingTime() < 0 || material.getMeltingTime() < 0) {
                    ingotStacks[i] = null;
                    continue;
                }

                if (material.getMeltingTime() == 0) {
                    meltIngot(i);
                    continue;
                }

                if (state.getMeltingProgess(i) < 0)
                    state.setMeltingProgress(i, 0f);

                state.setMeltingProgress(i, state.getMeltingProgess(i) + 1F / material.getMeltingTime());

                setItemTemperature(stack, material.getMeltingPoint());
            }

            if (state.getMeltingProgess(i) > 1F) {
                meltIngot(i);
            }
        }

        Iterator<FluidStack> iterator = moltenMetals.iterator();

        while (iterator.hasNext()) {
            FluidStack fluidStack = iterator.next();

            IArmorMaterial material = MaterialRegistry.getInstance().getMaterial(fluidStack.tag.getString(References.NBTTagCompoundData.Fluids.MoltenMetal.MATERIAL));

            if (state.getCurrentTemperature() < material.getMeltingPoint())
                iterator.remove();
        }
    }

    private void meltIngot (int stackIndex) {
        ItemStack stack = ingotStacks[stackIndex];
        IArmorMaterial material = HeatableItemRegistry.getInstance().getMaterialFromHeatedStack(stack);

        ingotStacks[stackIndex] = null;

        NBTTagCompound fluidCompound = new NBTTagCompound();
        fluidCompound.setString(References.NBTTagCompoundData.Fluids.MoltenMetal.MATERIAL, material.getUniqueID());

        int amount = References.General.FLUID_INGOT;

        if (HeatedItemFactory.getInstance().convertToCooledIngot(stack).getItem() instanceof IHeatableItem) {
            amount = ( (IHeatableItem) HeatedItemFactory.getInstance().convertToCooledIngot(stack).getItem() ).getMoltenMilibucket();
        }

        addFluidToTheTop(new FluidStack(GeneralRegistry.Fluids.moltenMetal, amount, fluidCompound));
    }

    public int getIngotAmount () {
        int tIngotAmount = 0;

        for (int tIngotStackIndex = 0; tIngotStackIndex < INGOTSTACKS_AMOUNT; tIngotStackIndex++) {
            if (ingotStacks[tIngotStackIndex] == null) {
                continue;
            }

            tIngotAmount++;
        }

        return tIngotAmount;
    }

    @Override
    public String getStructureType () {
        return References.InternalNames.TileEntities.Structures.FirePit;
    }

    @Override
    public HashMap<Coordinate3D, IStructureComponent> getSlaveEntities () {
        return slaveComponents;
    }

    @Override
    public void registerNewSlave (TileEntity pNewSlaveEntity) {
        if (!( pNewSlaveEntity instanceof IStructureComponent ))
            return;

        if (!( ( (IStructureComponent) pNewSlaveEntity ).getStructureType().equals(this.getStructureType()) ))
            return;

        if (slaveComponents.containsKey(new Coordinate3D(pNewSlaveEntity.getPos()))) {
            return;
        }

        slaveComponents.put(new Coordinate3D(pNewSlaveEntity.getPos()), (IStructureComponent) pNewSlaveEntity);

        if (structureBounds == null) {
            structureBounds = new Cube(getPos().getX(), getPos().getY() + 1, getPos().getZ(), 0, 0, 0);
        }

        if (!structureBounds.ContainsCoordinate(( (IStructureComponent) pNewSlaveEntity ).getLocation())) {
            structureBounds.IncludeCoordinate(new Coordinate3D(pNewSlaveEntity.getPos()));
        }
    }

    @Override
    public void removeSlave (Coordinate3D pSlaveCoordinate) {
        slaveComponents.remove(pSlaveCoordinate);
    }

    @Override
    public Cube getStructureSpace () {
        if (isSlaved())
            return getMasterEntity().getStructureSpace();

        return structureBounds;
    }

    @Override
    public void initiateAsMasterEntity () {
        structureBounds = new Cube(getPos().getX(), getPos().getY() + 1, getPos().getZ(), 0, 0, 0);
        masterComponent = null;
        masterCoordinate = null;
        slaveComponents = new HashMap<Coordinate3D, IStructureComponent>();

        //iData = new FirePitStructureData();
    }

    @Override
    public IStructureData getStructureRelevantData () {
        if (isSlaved())
            return getMasterEntity().getStructureRelevantData();


        return (IStructureData) getState();
    }

    @Override
    public void setStructureData (IStructureData pNewData) {
        if (isSlaved()) {
            getMasterEntity().setStructureData(pNewData);
            return;
        }

        setState(pNewData);
    }


    @Override
    public float getDistanceToMasterEntity () {
        if (!isSlaved())
            return 0;

        TileEntityFirePit tMasterEntity = (TileEntityFirePit) getMasterEntity();

        return getLocation().getDistanceTo(tMasterEntity.getLocation());
    }

    @Override
    public boolean isSlaved () {
        return masterComponent != null;
    }

    @Override
    public IStructureComponent getMasterEntity () {
        if (masterComponent == null)
            return this;

        return masterComponent;
    }

    @Override
    public void initiateAsSlaveEntity (IStructureComponent pMasterEntity) {
        masterComponent = pMasterEntity;
    }

    @Override
    public boolean countsAsConnectingComponent () {
        return true;
    }


    @Override
    public void writeStructureToNBT (NBTTagCompound pTileEntityCompound) {
        NBTTagCompound tStructureCompound = new NBTTagCompound();
        tStructureCompound.setBoolean(References.NBTTagCompoundData.TE.Basic.Structures.ISSLAVE, isSlaved());

        if (!isSlaved()) {
            NBTTagList tSlaveList = new NBTTagList();
            for (Coordinate3D tSlaveCoord : slaveComponents.keySet()) {
                NBTTagCompound tCoordinateCompound = NBTHelper.writeCoordinate3DToNBT(tSlaveCoord);
                tSlaveList.appendTag(tCoordinateCompound);
            }

            tStructureCompound.setTag(References.NBTTagCompoundData.TE.Basic.Structures.COORDINATES, tSlaveList);
        } else {
            tStructureCompound.setTag(References.NBTTagCompoundData.TE.Basic.Structures.MASTERTE, NBTHelper.writeCoordinate3DToNBT(masterComponent.getLocation()));
        }

        pTileEntityCompound.setTag(References.NBTTagCompoundData.TE.Basic.STRUCTUREDATA, tStructureCompound);
    }

    @Override
    public void readStructureFromNBT (NBTTagCompound pTileEntityCompound) {
        NBTTagCompound tStructureCompound = pTileEntityCompound.getCompoundTag(References.NBTTagCompoundData.TE.Basic.STRUCTUREDATA);

        if (tStructureCompound.getBoolean(References.NBTTagCompoundData.TE.Basic.Structures.ISSLAVE)) {
            masterCoordinate = NBTHelper.readCoordinate3DFromNBT(tStructureCompound.getCompoundTag(References.NBTTagCompoundData.TE.Basic.Structures.MASTERTE));
            return;
        }

        initiateAsMasterEntity();
        NBTTagList tSlaveList = tStructureCompound.getTagList(References.NBTTagCompoundData.TE.Basic.Structures.COORDINATES, 10);
        for (int tTagIndex = 0; tTagIndex < tSlaveList.tagCount(); tTagIndex++) {
            Coordinate3D tSlaveCoordinate = NBTHelper.readCoordinate3DFromNBT(tSlaveList.getCompoundTagAt(tTagIndex));

            if (slaveComponents.containsKey(tSlaveCoordinate))
                continue;

            if (slavesInitialized) {
                registerNewSlave(worldObj.getTileEntity(tSlaveCoordinate.toBlockPos()));
            } else {
                slaveCoordinates.add(tSlaveCoordinate);
            }
        }
    }

    @Override
    public Coordinate3D getLocation () {
        return new Coordinate3D(getPos());
    }

    public void regenStructure () {
        if (worldObj.isRemote)
            return;

        if (masterComponent != null)
            return;

        if (masterCoordinate != null) {
            TileEntity tMasterEntity = worldObj.getTileEntity(masterCoordinate.toBlockPos());
            if (tMasterEntity == null)
                return;

            initiateAsSlaveEntity((IStructureComponent) tMasterEntity);
            StructureNetworkManager.getInstance().sendToAllAround(new MessageOnCreateSlaveEntity(this, (IStructureComponent) tMasterEntity), new NetworkRegistry.TargetPoint(worldObj.provider.getDimensionId(), (double) this.getPos().getX(), (double) this.getPos().getY(), (double) this.getPos().getZ(), 128));
            return;
        }

        initiateAsMasterEntity();

        for (Coordinate3D tStoredCoordinate : slaveCoordinates) {
            TileEntity tSlaveEntity = worldObj.getTileEntity(tStoredCoordinate.toBlockPos());

            if (tSlaveEntity == null) {
                Armory.getLogger().error("Failed to reregister a TE from NBT. No TE exists on the stored coordinates!");
                continue;
            }

            ( (IStructureComponent) tSlaveEntity ).initiateAsSlaveEntity(this);
            try {
                this.registerNewSlave(tSlaveEntity);
                StructureNetworkManager.getInstance().sendToAllAround(new MessageOnCreateSlaveEntity((IStructureComponent) tSlaveEntity, this), new NetworkRegistry.TargetPoint(tSlaveEntity.getWorld().provider.getDimensionId(), (double) tSlaveEntity.getPos().getX(), (double) tSlaveEntity.getPos().getY(), (double) tSlaveEntity.getPos().getZ(), 128));
            } catch (Exception IAEx) {
                continue;
            }
        }
        StructureNetworkManager.getInstance().sendToAllAround(new MessageOnUpdateMasterData(this), new NetworkRegistry.TargetPoint(getWorld().provider.getDimensionId(), (double) getPos().getX(), (double) getPos().getY(), (double) getPos().getZ(), 128));
    }


    @Override
    public ArrayList<IPathComponent> getValidPathableNeighborComponents () {
        ArrayList<IPathComponent> tPathableComponents = new ArrayList<IPathComponent>();

        for (EnumFacing tNeigborDirection : EnumFacing.HORIZONTALS) {
            TileEntity tNeighborEntity = worldObj.getTileEntity(getPos().add(tNeigborDirection.getDirectionVec()));
            if (tNeighborEntity == null)
                continue;

            if (!( tNeighborEntity instanceof IStructureComponent ))
                continue;

            if (!( (IStructureComponent) tNeighborEntity ).getStructureType().equals(getStructureType()))
                continue;

            if (!( (IStructureComponent) tNeighborEntity ).countsAsConnectingComponent())
                continue;

            tPathableComponents.add((IPathComponent) tNeighborEntity);
        }

        return tPathableComponents;
    }

    @Override
    public float getPositiveInflunce () {
        return POSITIVEHEAT;
    }

    @Override
    public float getNegativeInfluece () {
        return NEGATIVEHEAT;
    }

    @Override
    public int getMaxTempInfluence () {
        return STRUCTURECOMPONENTADDITION;
    }

    @Override
    public boolean canInfluenceTE (Coordinate3D tTECoordinate) {
        return ( getPos().getY() == tTECoordinate.getYComponent() );
    }

    @Override
    public String getContainerID () {
        return References.InternalNames.TileEntities.FirePitContainer;
    }

    @Override
    public ArrayList<FluidStack> getAllFluids () {
        return moltenMetals;
    }

    @Override
    public void setAllFluids (ArrayList<FluidStack> stacks) {
        moltenMetals = stacks;
    }

    @Override
    public FluidStack removeFirstFluid () {
        return moltenMetals.remove(0);
    }

    @Override
    public FluidStack removeLastFluid () {
        return moltenMetals.remove(moltenMetals.size() - 1);
    }

    @Override
    public void addFluidToTheBottom (FluidStack stack) {
        moltenMetals.add(0, stack);
    }

    @Override
    public void addFluidToTheTop (FluidStack stack) {
        moltenMetals.add(stack);
    }
}
