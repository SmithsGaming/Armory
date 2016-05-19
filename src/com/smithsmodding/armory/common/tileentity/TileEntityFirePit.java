/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.smithsmodding.armory.common.tileentity;
/*
/  TileEntityFirePit
/  Created by : Orion
/  Created on : 02/10/2014
*/

import com.smithsmodding.armory.api.materials.IArmorMaterial;
import com.smithsmodding.armory.common.block.BlockFirePit;
import com.smithsmodding.armory.common.factory.HeatedItemFactory;
import com.smithsmodding.armory.common.item.ItemHeatedItem;
import com.smithsmodding.armory.common.registry.HeatableItemRegistry;
import com.smithsmodding.armory.common.tileentity.guimanagers.FirePitGuiManager;
import com.smithsmodding.armory.common.tileentity.state.FirePitState;
import com.smithsmodding.armory.util.References;
import com.smithsmodding.smithscore.common.fluid.IFluidContainingEntity;
import com.smithsmodding.smithscore.common.pathfinding.IPathComponent;
import com.smithsmodding.smithscore.common.structures.IStructureComponent;
import com.smithsmodding.smithscore.common.structures.IStructureData;
import com.smithsmodding.smithscore.common.tileentity.IBlockModelUpdatingTileEntity;
import com.smithsmodding.smithscore.util.common.positioning.Coordinate3D;
import com.smithsmodding.smithscore.util.common.positioning.Cube;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.Iterator;

public class TileEntityFirePit extends TileEntityArmory implements IInventory, ITickable, IFirePitComponent, IStructureComponent, IFluidContainingEntity, IBlockModelUpdatingTileEntity {

    public static int INGOTSTACKS_AMOUNT = 5;
    public static int FUELSTACK_AMOUNT = 5;
    public static int INFUSIONSTACK_AMOUNT = 3;
    public static float POSITIVEHEAT = 2.625F;
    public static float NEGATIVEHEAT = 0.75F;
    public static int STRUCTURECOMPONENTADDITION = 1425;

    private float positiveHeatTerm = 0.625F;
    private float negativeHeatTerm = -0.25F;
    private ItemStack[] ingotStacks = new ItemStack[INGOTSTACKS_AMOUNT];
    private ItemStack[] fuelStacks = new ItemStack[FUELSTACK_AMOUNT];

    private ArrayList<FluidStack> moltenMetals = new ArrayList<FluidStack>();

    private float heatedProcentage;

    private Coordinate3D masterCoordinate;
    private ArrayList<Coordinate3D> slaveCoordinates;
    private Cube structureBounds = new Cube(getPos().getX(), getPos().getY(), getPos().getZ(), 0, 0, 0);


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
            ItemStack returnStack = tItemStack.splitStack(pDecrAmount);

            if (tItemStack.stackSize == 0) {
                setInventorySlotContents(pSlotIndex, null);
            }

            return returnStack;
        }

        return tItemStack;
    }

    @Override
    public ItemStack removeStackFromSlot (int index) {
        ItemStack current = getStackInSlot(index);
        setInventorySlotContents(index, null);
        return current;
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
    public void update () {
        if (masterCoordinate == null)
            initiateAsMasterEntity();

        FirePitState structureData = (FirePitState) getStructureData();
        FirePitState state = (FirePitState) getState();

        if (structureData == null)
            return;

        state.setLastTemperature(state.getCurrentTemperature());
        structureData.setBurning(false);

        heatFurnace();

        structureData.setBurning(( (Float) structureData.getData(this, References.NBTTagCompoundData.TE.FirePit.FUELSTACKBURNINGTIME) >= 1F ));

        heatIngots();

        meltIngots();

        state.setLastAddedHeat(state.getCurrentTemperature() - state.getLastTemperature());

        if (!worldObj.isRemote) {
            this.markDirty();

            queBlockModelUpdateOnClients();
        }
    }

    /**
     * Called from Chunk.setBlockIDWithMetadata, determines if this tile entity should be re-created when the ID, or
     * Metadata changes. Use with caution as this will leave straggler TileEntities, or create conflicts with other
     * TileEntities if not used properly.
     *
     * @param world    Current world
     * @param pos      Tile's world position
     * @param oldState
     * @param newSate  @return True to remove the old tile entity, false to keep it in tact {and create a new one if the
     *                 new values specify to}
     */
    @Override
    public boolean shouldRefresh (World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
        return oldState.getBlock() != newSate.getBlock();
    }

    public void heatFurnace () {
        calculateHeatTerms();

        FirePitState structureState = (FirePitState) getStructureData();
        FirePitState tileState = (FirePitState) getState();

        tileState.setLastAddedHeat(0F);

        if ((Float) structureState.getData(this, References.NBTTagCompoundData.TE.FirePit.FUELSTACKBURNINGTIME) < 1F) {
            for (int tFuelStackIndex = 0; tFuelStackIndex < FUELSTACK_AMOUNT; tFuelStackIndex++) {

                if (fuelStacks[tFuelStackIndex] == null) {
                    continue;
                }

                ItemStack tTargetedFuelStack = fuelStacks[tFuelStackIndex];

                //Check if the stack is a valid Fuel in the Furnace
                if (( tTargetedFuelStack != null ) && ( TileEntityFurnace.isItemFuel(tTargetedFuelStack) )) {
                    --tTargetedFuelStack.stackSize;

                    structureState.setData(this, References.NBTTagCompoundData.TE.FirePit.FUELSTACKBURNINGTIME, (Float) structureState.getData(this, References.NBTTagCompoundData.TE.FirePit.FUELSTACKBURNINGTIME) + TileEntityFurnace.getItemBurnTime(tTargetedFuelStack));

                    if (tTargetedFuelStack.stackSize == 0) {
                        fuelStacks[tFuelStackIndex] = tTargetedFuelStack.getItem().getContainerItem(tTargetedFuelStack);
                    }

                }

            }

            structureState.setData(this, References.NBTTagCompoundData.TE.FirePit.FUELSTACKFUELAMOUNT, structureState.getData(this, References.NBTTagCompoundData.TE.FirePit.FUELSTACKBURNINGTIME));
        }

        if ((Float) structureState.getData(this, References.NBTTagCompoundData.TE.FirePit.FUELSTACKBURNINGTIME) >= 1F) {

            structureState.setData(this, References.NBTTagCompoundData.TE.FirePit.FUELSTACKBURNINGTIME, (Float) structureState.getData(this, References.NBTTagCompoundData.TE.FirePit.FUELSTACKBURNINGTIME) - 1F);
            tileState.setLastAddedHeat(tileState.getLastAddedHeat() + positiveHeatTerm);
        }

        heatedProcentage = Math.round(( tileState.getCurrentTemperature() / tileState.getMaxTemperature() ) * 100) / 100F;
        tileState.setLastAddedHeat(tileState.getLastAddedHeat() * ( 1 - heatedProcentage ));
    }

    private void calculateHeatTerms () {
        FirePitState state = (FirePitState) getState();

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
        FirePitState state = (FirePitState) getState();

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
                setItemTemperature(stack, getItemTemperature(stack) + tSourceDifference);
            }

        }
    }

    public float getItemTemperature (ItemStack pItemStack) {
        if (pItemStack.getItem() instanceof ItemHeatedItem) {

            NBTTagCompound stackCompound = pItemStack.getTagCompound();

            return stackCompound.getFloat(References.NBTTagCompoundData.HeatedIngot.CURRENTTEMPERATURE);
        }

        return 20F;
    }

    public void setItemTemperature (ItemStack pItemStack, float pNewTemp) {
        if (pItemStack.getItem() instanceof ItemHeatedItem) {
            if (pNewTemp < 20F)
                pNewTemp = 20F;

            NBTTagCompound stackCompound = pItemStack.getTagCompound();

            stackCompound.setFloat(References.NBTTagCompoundData.HeatedIngot.CURRENTTEMPERATURE, pNewTemp);

            pItemStack.setTagCompound(stackCompound);
        }

    }


    public void meltIngots () {
        FirePitState state = (FirePitState) getState();

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
                if (material.getMeltingTime() < 0 || material.getMeltingPoint() < 0) {
                    ingotStacks[i] = null;
                    continue;
                }

                if (material.getMeltingTime() == 0) {
                    meltIngot(i);
                    continue;
                }

                if (state.getMeltingProgess(i) < 0)
                    state.setMeltingProgress(i, 0f);

                state.setMeltingProgress(i, ( state.getMeltingProgess(i) * material.getMeltingTime() ) + 1F / material.getMeltingTime());

                setItemTemperature(stack, material.getMeltingPoint());
            }

            if (state.getMeltingProgess(i) > 1F) {
                meltIngot(i);
                state.setMeltingProgress(i, 0F);
            }
        }

        Iterator<FluidStack> iterator = moltenMetals.iterator();

        while (iterator.hasNext()) {
            FluidStack fluidStack = iterator.next();

            IArmorMaterial material = HeatableItemRegistry.getInstance().getMaterialFromMoltenStack(fluidStack);

            if (state.getCurrentTemperature() < material.getMeltingPoint() * 0.95F)
                iterator.remove();
        }
    }

    private void meltIngot (int stackIndex) {
        ItemStack stack = ingotStacks[stackIndex];
        IArmorMaterial material = HeatableItemRegistry.getInstance().getMaterialFromHeatedStack(stack);

        ingotStacks[stackIndex] = null;

        NBTTagCompound fluidCompound = new NBTTagCompound();
        fluidCompound.setString(References.NBTTagCompoundData.Fluids.MoltenMetal.MATERIAL, material.getUniqueID());

        addFluidToTheTop(HeatableItemRegistry.getInstance().getMoltenStack(stack));
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
    public void queBlockModelUpdateOnClients () {
        if (isSlaved())
            return;


        if (this.shouldUpdateBlock()) {
            this.onUpdateBlock();
        }

        for (Coordinate3D slaveLocation : slaveCoordinates) {
            IBlockModelUpdatingTileEntity tileEntity = (IBlockModelUpdatingTileEntity) getWorld().getTileEntity(slaveLocation.toBlockPos());

            if (tileEntity.shouldUpdateBlock()) {
                tileEntity.onUpdateBlock();
            }
        }
    }



    @Override
    public Coordinate3D getLocation () {
        return new Coordinate3D(getPos());
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

            if (!( (IStructureComponent) tNeighborEntity ).getStructureTypeUniqueID().equals(getStructureTypeUniqueID()))
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

    @Override
    public int getTankSize () {
        int sum = 0;

        for(FluidStack stack : getAllFluids())
            sum += stack.amount;

        return sum;
    }

    @Override
    public boolean shouldUpdateBlock () {
        IBlockState blockState = getWorld().getBlockState(pos);

        if (!( blockState.getBlock() instanceof BlockFirePit ))
            return false;

        return blockState.getValue(BlockFirePit.BURNING) != ( (FirePitState) getStructureData() ).isBurning();
    }

    @Override
    public void onUpdateBlock () {
        if (!worldObj.isRemote) {
            return;
        }

        BlockFirePit.setBurningState(( (FirePitState) getStructureData() ).isBurning(), getWorld(), getPos());
    }

    @Override
    public String getStructureTypeUniqueID () {
        return References.InternalNames.TileEntities.Structures.FirePit;
    }

    @Override
    public Cube getStructureBoundingBox () {
        if (isSlaved())
            return ( (IStructureComponent) getWorld().getTileEntity(getMasterLocation().toBlockPos()) ).getStructureBoundingBox();

        return structureBounds;
    }

    @Override
    public boolean countsAsConnectingComponent () {
        return true;
    }

    @Override
    public IStructureData getStructureData () {
        if (!isSlaved())
            return (IStructureData) getState();

        if (getWorld().getTileEntity(masterCoordinate.toBlockPos()) == null)
            return null;

        return ( (IStructureComponent) getWorld().getTileEntity(masterCoordinate.toBlockPos()) ).getStructureData();
    }

    @Override
    public void initiateAsMasterEntity () {
        masterCoordinate = getLocation();
        slaveCoordinates = new ArrayList<Coordinate3D>();
        structureBounds = new Cube(getPos().getX(), getPos().getY(), getPos().getZ(), 0, 0, 0);

        BlockFirePit.setMasterState(true, getWorld(), getPos());

        queBlockModelUpdateOnClients();
    }

    @Override
    public void initiateAsSlaveEntity (Coordinate3D masterLocation) {
        masterCoordinate = masterLocation;
        slaveCoordinates = new ArrayList<Coordinate3D>();
        structureBounds = new Cube(getPos().getX(), getPos().getY(), getPos().getZ(), 0, 0, 0);

        BlockFirePit.setMasterState(false, getWorld(), getPos());
    }

    @Override
    public ArrayList<Coordinate3D> getSlaveCoordinates () {
        if (isSlaved())
            return ( (IStructureComponent) getWorld().getTileEntity(getMasterLocation().toBlockPos()) ).getSlaveCoordinates();

        return slaveCoordinates;
    }

    @Override
    public void setSlaveCoordinates (ArrayList<Coordinate3D> newSlaveCoordinates) {
        if (isSlaved()) {
            return;
        }

        slaveCoordinates = newSlaveCoordinates;
        rebuildBoundingBox();
    }

    @Override
    public void registerNewSlave (Coordinate3D newSlaveLocation) {
        if (isSlaved()) {
            ( (IStructureComponent) getWorld().getTileEntity(getMasterLocation().toBlockPos()) ).registerNewSlave(newSlaveLocation);
        }

        slaveCoordinates.add(newSlaveLocation);

        getStructureBoundingBox().IncludeCoordinate(newSlaveLocation);

        queBlockModelUpdateOnClients();
    }

    @Override
    public void removeSlave (Coordinate3D slaveLocation) {
        if (isSlaved()) {
            ( (IStructureComponent) getWorld().getTileEntity(getMasterLocation().toBlockPos()) ).removeSlave(slaveLocation);
        }

        slaveCoordinates.remove(slaveLocation);
        rebuildBoundingBox();

        queBlockModelUpdateOnClients();
    }

    @Override
    public boolean isSlaved () {
        if (masterCoordinate == null || getLocation() == null)
            return false;

        return !masterCoordinate.equals(getLocation());
    }

    @Override
    public float getDistanceToMasterEntity () {
        return getLocation().getDistanceTo(getMasterLocation());
    }

    @Override
    public Coordinate3D getMasterLocation () {
        return masterCoordinate;
    }

    @Override
    public void setMasterLocation (Coordinate3D newMasterLocation) {
        masterCoordinate = newMasterLocation;
    }

    private void rebuildBoundingBox () {
        structureBounds = new Cube(getPos().getX(), getPos().getY(), getPos().getZ(), 0, 0, 0);

        for (Coordinate3D coordinate3D : slaveCoordinates)
            structureBounds.IncludeCoordinate(coordinate3D);
    }
}

