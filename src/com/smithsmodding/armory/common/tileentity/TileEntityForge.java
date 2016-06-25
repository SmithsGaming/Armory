package com.smithsmodding.armory.common.tileentity;

import com.smithsmodding.armory.api.References;
import com.smithsmodding.armory.api.materials.IArmorMaterial;
import com.smithsmodding.armory.common.block.BlockForge;
import com.smithsmodding.armory.common.item.ItemHeatedItem;
import com.smithsmodding.armory.common.registry.HeatableItemRegistry;
import com.smithsmodding.armory.common.tileentity.guimanagers.TileEntityForgeGuiManager;
import com.smithsmodding.armory.common.tileentity.state.TileEntityForgeState;
import com.smithsmodding.smithscore.common.fluid.IFluidContainingEntity;
import com.smithsmodding.smithscore.common.pathfinding.IPathComponent;
import com.smithsmodding.smithscore.common.structures.IStructureComponent;
import com.smithsmodding.smithscore.common.tileentity.IBlockModelUpdatingTileEntity;
import com.smithsmodding.smithscore.util.common.FluidStackHelper;
import com.smithsmodding.smithscore.util.common.positioning.Coordinate3D;
import com.smithsmodding.smithscore.util.common.positioning.Cube;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;

/**
 * Author Orion (Created on: 23.06.2016)
 */
public class TileEntityForge extends TileEntityForgeBase<TileEntityForgeState, TileEntityForgeGuiManager> implements IFirePitComponent, IStructureComponent<TileEntityForgeState>, IFluidContainingEntity, IBlockModelUpdatingTileEntity {

    public static int INGOTSTACKS_AMOUNT = 5;
    public static int FUELSTACK_AMOUNT = 5;
    public static int INFUSIONSTACK_AMOUNT = 3;
    public static float POSITIVEHEAT = 2.625F;
    public static float NEGATIVEHEAT = 0.60F;
    public static int STRUCTURECOMPONENTADDITION = 1750;
    public static int TANKINGOTCAPACITY = 6;

    private ItemStack[] ingotStacks = new ItemStack[INGOTSTACKS_AMOUNT];
    private ItemStack[] fuelStacks = new ItemStack[FUELSTACK_AMOUNT];

    private Coordinate3D masterCoordinate;
    private LinkedHashSet<Coordinate3D> slaveCoordinates;
    private Cube structureBounds = new Cube(getPos().getX(), getPos().getY(), getPos().getZ(), 0, 0, 0);

    @Override
    public void update() {
        if (masterCoordinate == null)
            initiateAsMasterEntity();

        if (isRemote())
            return;

        super.update();

        meltIngots();
    }

    public void meltIngots() {
        for (int i = 0; i < INGOTSTACKS_AMOUNT; i++) {
            ItemStack stack = ingotStacks[i];

            if (stack == null)
                continue;

            if (!(stack.getItem() instanceof ItemHeatedItem))
                continue;

            IArmorMaterial material = HeatableItemRegistry.getInstance().getMaterialFromHeatedStack(stack);
            float stackTemp = HeatableItemRegistry.getInstance().getItemTemperature(stack);

            if (getState().getCurrentTemp() < stackTemp * 0.95 && getState().getMeltingProgess(i) > 0) {
                getState().setMeltingProgress(i, 0);
                ingotStacks[i] = null;
                continue;
            }

            if (stackTemp >= material.getMeltingPoint()) {
                if (material.getMeltingTime() <= 0 || material.getMeltingPoint() <= 0) {
                    ingotStacks[i] = null;
                    continue;
                }

                if (material.getMeltingTime() == 0) {
                    meltIngot(i);
                    continue;
                }

                if (getState().getMeltingProgess(i) < 0)
                    getState().setMeltingProgress(i, 0f);

                getState().setMeltingProgress(i, (getState().getMeltingProgess(i) + 1F / material.getMeltingTime()));

                HeatableItemRegistry.getInstance().setItemTemperature(stack, material.getMeltingPoint());
            }

            if (getState().getMeltingProgess(i) > 1F) {
                meltIngot(i);
                getState().setMeltingProgress(i, 0F);
            }
        }

        Iterator<FluidStack> iterator = getStructureData().getMoltenMetals().iterator();

        while (iterator.hasNext()) {
            FluidStack fluidStack = iterator.next();

            IArmorMaterial material = HeatableItemRegistry.getInstance().getMaterialFromMoltenStack(fluidStack);

            if (getState().getCurrentTemp() < material.getMeltingPoint() * 0.95F)
                iterator.remove();
        }
    }

    private void meltIngot(int stackIndex) {
        ItemStack stack = ingotStacks[stackIndex];
        IArmorMaterial material = HeatableItemRegistry.getInstance().getMaterialFromHeatedStack(stack);

        ingotStacks[stackIndex] = null;

        NBTTagCompound fluidCompound = new NBTTagCompound();
        fluidCompound.setString(References.NBTTagCompoundData.Fluids.MoltenMetal.MATERIAL, material.getUniqueID());

        addFluid(HeatableItemRegistry.getInstance().getMoltenStack(stack));
    }

    @Override
    public float getPositiveInflunce() {
        return POSITIVEHEAT;
    }

    @Override
    public float getNegativeInfluece() {
        return NEGATIVEHEAT;
    }

    @Override
    public int getMaxTempInfluence() {
        return STRUCTURECOMPONENTADDITION;
    }

    @Override
    public boolean canInfluenceTE(Coordinate3D coordinate) {
        return (getPos().getY() == coordinate.getYComponent());
    }

    @Override
    protected TileEntityForgeState getHeatData() {
        return getStructureData();
    }

    @Override
    protected void calculateHeatTerms(TileEntityForgeState localData) {
        localData.setMaxTemp(2750);
        localData.setLastPositiveTerm(POSITIVEHEAT);
        localData.setLastNegativeTerm(NEGATIVEHEAT);

        for (EnumFacing direction : EnumFacing.values()) {
            TileEntity entity = worldObj.getTileEntity(getPos().add(direction.getDirectionVec()));
            if (entity instanceof IFirePitComponent) {
                if (((IFirePitComponent) entity).canInfluenceTE(getLocation())) {
                    localData.changeLastPositiveTerm(((IFirePitComponent) entity).getPositiveInflunce());
                    localData.changeLastNegativeTerm(((IFirePitComponent) entity).getNegativeInfluece());

                    localData.changeMaxTemp(((IFirePitComponent) entity).getMaxTempInfluence());
                }
            }
        }
    }

    @Override
    protected int getFuelStackAmount() {
        return FUELSTACK_AMOUNT;
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
        return INGOTSTACKS_AMOUNT;
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
        return 4f;
    }

    @Override
    public ArrayList<FluidStack> getAllFluids() {
        return getStructureData().getMoltenMetals();
    }

    @Override
    public void setAllFluids(ArrayList<FluidStack> stacks) {
        getStructureData().setMoltenMetals(stacks);
    }

    @Override
    public FluidStack removeFirstFluid() {
        return getStructureData().getMoltenMetals().remove(0);
    }

    @Override
    public FluidStack removeLastFluid() {
        return getStructureData().getMoltenMetals().remove(getStructureData().getMoltenMetals().size() - 1);
    }

    @Override
    public void addFluidToTheBottom(FluidStack stack) {
        int amount = getTankContentsVolume();
        if (amount + stack.amount > getTankSize())
            stack.amount = getTankSize() - amount;

        getStructureData().getMoltenMetals().add(stack);
    }

    @Override
    public void addFluidToTheTop(FluidStack stack) {
        int amount = getTankContentsVolume();
        if (amount + stack.amount > getTankSize())
            stack.amount = getTankSize() - amount;

        getStructureData().getMoltenMetals().add(0, stack);
    }

    @Override
    public void addFluid(FluidStack stack) {
        int amount = getTankContentsVolume();
        if (amount + stack.amount > getTankSize())
            stack.amount = getTankSize() - amount;

        for (FluidStack fluidStack : getStructureData().getMoltenMetals()) {
            if (FluidStackHelper.equalsIgnoreStackSize(stack, fluidStack)) {
                fluidStack.amount += stack.amount;
                return;
            }
        }

        addFluidToTheTop(stack);
    }

    @Override
    public int getTankSize() {
        return (getSlaveCoordinates().size() + 1) * (References.General.FLUID_INGOT * TANKINGOTCAPACITY);
    }

    @Override
    public int getTankContentsVolume() {
        int amount = 0;
        for (FluidStack stack : getAllFluids()) {
            amount += stack.amount;
        }

        return amount;
    }

    @Override
    public String getContainerID() {
        return References.InternalNames.TileEntities.ForgeContainer + "-" + getLocation().toString();
    }

    @Override
    public int getSizeInventory() {
        return INGOTSTACKS_AMOUNT + FUELSTACK_AMOUNT;
    }

    @Override
    public ItemStack getStackInSlot(int slotIndex) {
        if (slotIndex < INGOTSTACKS_AMOUNT) {
            return ingotStacks[slotIndex];
        }

        if (slotIndex < INGOTSTACKS_AMOUNT + FUELSTACK_AMOUNT) {
            return fuelStacks[slotIndex - INGOTSTACKS_AMOUNT];
        }

        return null;
    }

    @Override
    public void clearInventory() {
        ingotStacks = new ItemStack[INGOTSTACKS_AMOUNT];
        fuelStacks = new ItemStack[FUELSTACK_AMOUNT];
    }

    @Override
    public void setInventorySlotContents(int slotIndex, ItemStack stack) {
        if (slotIndex < INGOTSTACKS_AMOUNT) {
            ItemStack settingStack = null;
            if (stack != null) {
                settingStack = stack.copy();
                settingStack.stackSize = 1;
            }

            ingotStacks[slotIndex] = settingStack;
            if (stack != null && stack.stackSize > 1) {
                --stack.stackSize;
            }
        } else if (slotIndex < INGOTSTACKS_AMOUNT + FUELSTACK_AMOUNT) {
            fuelStacks[slotIndex - INGOTSTACKS_AMOUNT] = stack;
        }
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isItemValidForSlot(int slotIndex, ItemStack stack) {
        if (slotIndex < INGOTSTACKS_AMOUNT) {
            if (stack.getItem() instanceof ItemHeatedItem) {
                return true;
            }

            return HeatableItemRegistry.getInstance().isHeatable(stack);
        } else if (slotIndex < INGOTSTACKS_AMOUNT + FUELSTACK_AMOUNT) {
            return TileEntityFurnace.isItemFuel(stack);
        }

        return false;
    }

    @Override
    public String getStructureTypeUniqueID() {
        return References.InternalNames.TileEntities.Structures.Forge;
    }

    @Override
    public Cube getStructureBoundingBox() {
        if (isSlaved())
            return ((IStructureComponent) getWorld().getTileEntity(getMasterLocation().toBlockPos())).getStructureBoundingBox();

        return structureBounds;
    }

    @Override
    public boolean countsAsConnectingComponent() {
        return true;
    }

    @Override
    public TileEntityForgeState getStructureData() {
        if (isSlaved())
            return ((IStructureComponent<TileEntityForgeState>) getWorld().getTileEntity(getMasterLocation().toBlockPos())).getStructureData();

        return getState();
    }

    @Override
    public void initiateAsMasterEntity() {
        masterCoordinate = getLocation();
        slaveCoordinates = new LinkedHashSet<>();
        structureBounds = new Cube(getPos().getX(), getPos().getY(), getPos().getZ(), 0, 0, 0);

        BlockForge.setMasterState(true, getWorld(), getPos());

        queBlockModelUpdateOnClients();
    }

    @Override
    public void initiateAsSlaveEntity(Coordinate3D masterLocation) {
        masterCoordinate = masterLocation;
        slaveCoordinates = new LinkedHashSet<>();
        structureBounds = new Cube(getPos().getX(), getPos().getY(), getPos().getZ(), 0, 0, 0);

        if (getWorld() != null)
            if (getWorld().getTileEntity(masterLocation.toBlockPos()) != null)
                ((IStructureComponent) getWorld().getTileEntity(masterLocation.toBlockPos())).registerNewSlave(getLocation());

        BlockForge.setMasterState(false, getWorld(), getPos());
    }

    @Override
    public LinkedHashSet<Coordinate3D> getSlaveCoordinates() {
        if (isSlaved())
            return ((IStructureComponent) getWorld().getTileEntity(getMasterLocation().toBlockPos())).getSlaveCoordinates();

        return slaveCoordinates;
    }

    @Override
    public void setSlaveCoordinates(LinkedHashSet<Coordinate3D> newSlaveCoordinates) {
        if (isSlaved()) {
            return;
        }

        slaveCoordinates = newSlaveCoordinates;
        rebuildBoundingBox();
    }

    @Override
    public void registerNewSlave(Coordinate3D newSlaveLocation) {
        if (isSlaved()) {
            ((IStructureComponent) getWorld().getTileEntity(getMasterLocation().toBlockPos())).registerNewSlave(newSlaveLocation);
        }

        slaveCoordinates.add(newSlaveLocation);

        getStructureBoundingBox().IncludeCoordinate(newSlaveLocation);

        queBlockModelUpdateOnClients();
    }

    @Override
    public void removeSlave(Coordinate3D slaveLocation) {
        if (isSlaved()) {
            ((IStructureComponent) getWorld().getTileEntity(getMasterLocation().toBlockPos())).removeSlave(slaveLocation);
        }

        slaveCoordinates.remove(slaveLocation);
        rebuildBoundingBox();

        queBlockModelUpdateOnClients();
    }

    @Override
    public boolean isSlaved() {
        if (masterCoordinate == null || getLocation() == null)
            return false;

        if (getWorld() != null && getWorld().getTileEntity(getMasterLocation().toBlockPos()) == null) {
            initiateAsMasterEntity();
            return false;
        }

        return !masterCoordinate.equals(getLocation());
    }

    @Override
    public float getDistanceToMasterEntity() {
        return getLocation().getDistanceTo(getMasterLocation());
    }

    @Override
    public Coordinate3D getMasterLocation() {
        return masterCoordinate;
    }

    @Override
    public void setMasterLocation(Coordinate3D newMasterLocation) {
        masterCoordinate = newMasterLocation;
    }

    private void rebuildBoundingBox() {
        structureBounds = new Cube(getPos().getX(), getPos().getY(), getPos().getZ(), 0, 0, 0);

        for (Coordinate3D coordinate3D : slaveCoordinates)
            structureBounds.IncludeCoordinate(coordinate3D);
    }

    @Override
    public ArrayList<IPathComponent> getValidPathableNeighborComponents() {
        ArrayList<IPathComponent> pathComponentArrayList = new ArrayList<>();

        for (EnumFacing facing : EnumFacing.values()) {
            if ((facing == EnumFacing.UP) || (facing == EnumFacing.DOWN))
                continue;

            TileEntity tNeighborEntity = getWorld().getTileEntity(getLocation().moveCoordinate(facing, 1).toBlockPos());
            if (tNeighborEntity == null)
                continue;

            if (!(tNeighborEntity instanceof IStructureComponent))
                continue;

            if (!((IStructureComponent) tNeighborEntity).getStructureTypeUniqueID().equals(getStructureTypeUniqueID()))
                continue;

            if (!((IStructureComponent) tNeighborEntity).countsAsConnectingComponent())
                continue;

            pathComponentArrayList.add((IPathComponent) tNeighborEntity);
        }

        return pathComponentArrayList;

    }

    @Override
    public void queBlockModelUpdateOnClients() {
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
    public boolean shouldUpdateBlock() {
        IBlockState blockState = getWorld().getBlockState(pos);

        if (!(blockState.getBlock() instanceof BlockForge))
            return false;

        return blockState.getValue(BlockForge.BURNING) != (getStructureData()).isBurning();
    }

    @Override
    public void onUpdateBlock() {
        if (!worldObj.isRemote) {
            return;
        }

        BlockForge.setBurningState((getStructureData()).isBurning(), getWorld(), getPos());
    }

    @Override
    protected TileEntityForgeGuiManager getInitialGuiManager() {
        return new TileEntityForgeGuiManager(this);
    }

    @Override
    protected TileEntityForgeState getInitialState() {
        return new TileEntityForgeState();
    }
}
