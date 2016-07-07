package com.smithsmodding.armory.common.tileentity;

import com.smithsmodding.armory.api.references.References;
import com.smithsmodding.armory.api.materials.IArmorMaterial;
import com.smithsmodding.armory.common.block.BlockForge;
import com.smithsmodding.armory.common.item.ItemHeatedItem;
import com.smithsmodding.armory.common.registry.HeatableItemRegistry;
import com.smithsmodding.armory.common.structure.forge.StructureForge;
import com.smithsmodding.armory.common.tileentity.guimanagers.TileEntityForgeGuiManager;
import com.smithsmodding.armory.common.tileentity.state.IForgeFuelDataContainer;
import com.smithsmodding.armory.common.tileentity.state.TileEntityForgeState;
import com.smithsmodding.smithscore.common.events.structure.StructureEvent;
import com.smithsmodding.smithscore.common.fluid.IFluidContainingEntity;
import com.smithsmodding.smithscore.common.pathfinding.IPathComponent;
import com.smithsmodding.smithscore.common.structures.IStructurePart;
import com.smithsmodding.smithscore.common.structures.StructureRegistry;
import com.smithsmodding.smithscore.common.tileentity.IBlockModelUpdatingTileEntity;
import com.smithsmodding.smithscore.util.common.FluidStackHelper;
import com.smithsmodding.smithscore.util.common.positioning.Coordinate3D;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Author Orion (Created on: 23.06.2016)
 */
public class TileEntityForge extends TileEntityForgeBase<TileEntityForgeState, TileEntityForgeGuiManager> implements IFirePitComponent, IStructurePart<StructureForge>, IFluidContainingEntity, IBlockModelUpdatingTileEntity {

    public static int INGOTSTACKS_AMOUNT = 5;
    public static int FUELSTACK_AMOUNT = 5;
    public static int INFUSIONSTACK_AMOUNT = 3;
    public static float POSITIVEHEAT = 2.625F;
    public static float NEGATIVEHEAT = 0.60F;
    public static int STRUCTURECOMPONENTADDITION = 1750;
    public static int TANKINGOTCAPACITY = 6;

    private ItemStack[] ingotStacks = new ItemStack[INGOTSTACKS_AMOUNT];
    private ItemStack[] fuelStacks = new ItemStack[FUELSTACK_AMOUNT];

    private Coordinate3D masterCoordinate = new Coordinate3D(0, 0, 0);

    @Override
    public void update() {
        if (isRemote())
            return;

        super.update();

        meltIngots();

        markDirty();
    }

    @Override
    public void markDirty() {
        if (isRemote())
            return;

        super.markDirty();

        new StructureEvent.Updated(getStructure(), getWorld().provider.getDimension()).PostCommon();
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

        if (getStructure() == null)
            return;

        Iterator<FluidStack> iterator = getStructure().getData().getMoltenMetals().iterator();

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
    public IForgeFuelDataContainer getFuelData() {
        if (getStructure() == null)
            return null;

        return getStructure().getData();
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
        if (getStructure() == null)
            return new ArrayList<>();


        return getStructure().getData().getMoltenMetals();
    }

    @Override
    public void setAllFluids(ArrayList<FluidStack> stacks) {
        if (getStructure() == null)
            return;

        getStructure().getData().setMoltenMetals(stacks);
    }

    @Override
    public FluidStack removeFirstFluid() {
        if (getStructure() == null)
            return null;

        return getStructure().getData().getMoltenMetals().remove(0);
    }

    @Override
    public FluidStack removeLastFluid() {
        if (getStructure() == null)
            return null;

        return getStructure().getData().getMoltenMetals().remove(getStructure().getData().getMoltenMetals().size() - 1);
    }

    @Override
    public void addFluidToTheBottom(FluidStack stack) {
        int amount = getTankContentsVolume();
        if (amount + stack.amount > getTankSize())
            stack.amount = getTankSize() - amount;

        if (getStructure() == null)
            return;

        getStructure().getData().getMoltenMetals().add(stack);
    }

    @Override
    public void addFluidToTheTop(FluidStack stack) {
        int amount = getTankContentsVolume();
        if (amount + stack.amount > getTankSize())
            stack.amount = getTankSize() - amount;

        if (getStructure() == null)
            return;

        getStructure().getData().getMoltenMetals().add(0, stack);
    }

    @Override
    public void addFluid(FluidStack stack) {
        int amount = getTankContentsVolume();
        if (amount + stack.amount > getTankSize())
            stack.amount = getTankSize() - amount;

        if (getStructure() == null)
            return;

        for (FluidStack fluidStack : getStructure().getData().getMoltenMetals()) {
            if (FluidStackHelper.equalsIgnoreStackSize(stack, fluidStack)) {
                fluidStack.amount += stack.amount;
                return;
            }
        }

        addFluidToTheTop(stack);
    }

    @Override
    public int getTankSize() {
        return (getStructure().getPartLocations().size() + 1) * (References.General.FLUID_INGOT * TANKINGOTCAPACITY);
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
    protected NBTBase writeFluidsToCompound() {
        return new NBTTagCompound();
    }

    @Override
    protected void readFluidsFromCompound(NBTBase inventoryCompound) {
        return;
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
    public ArrayList<IPathComponent> getValidPathableNeighborComponents() {
        ArrayList<IPathComponent> pathComponentArrayList = new ArrayList<>();

        for (EnumFacing facing : EnumFacing.values()) {
            if ((facing == EnumFacing.UP) || (facing == EnumFacing.DOWN))
                continue;

            TileEntity tNeighborEntity = getWorld().getTileEntity(getLocation().moveCoordinate(facing, 1).toBlockPos());
            if (tNeighborEntity == null)
                continue;

            if (!(tNeighborEntity instanceof IStructurePart))
                continue;

            if (((IStructurePart) tNeighborEntity).getStructure() == null)
                continue;

            pathComponentArrayList.add((IPathComponent) tNeighborEntity);
        }

        return pathComponentArrayList;

    }

    @Override
    public void queBlockModelUpdateOnClients() {
        if (!getStructure().getMasterLocation().equals(getLocation()))
            return;

        if (this.shouldUpdateBlock()) {
            this.onUpdateBlock();
        }

        for (Coordinate3D slaveLocation : getStructure().getPartLocations()) {
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

        return blockState.getValue(BlockForge.BURNING) != (getStructure().getData()).isBurning();
    }

    @Override
    public void onUpdateBlock() {
        if (!worldObj.isRemote) {
            return;
        }

        BlockForge.setBurningState((getStructure().getData()).isBurning(), getWorld(), getPos());
    }

    @Override
    protected TileEntityForgeGuiManager getInitialGuiManager() {
        return new TileEntityForgeGuiManager(this);
    }

    @Override
    protected TileEntityForgeState getInitialState() {
        return new TileEntityForgeState();
    }

    @Override
    public Class<StructureForge> getStructureType() {
        return StructureForge.class;
    }

    @Override
    public StructureForge getStructure() {
        return (StructureForge) StructureRegistry.getInstance().getStructure(getWorld().provider.getDimension(), masterCoordinate);
    }

    @Override
    public void setStructure(StructureForge structure) {
        this.masterCoordinate = structure.getMasterLocation();
    }
}
