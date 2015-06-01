package com.Orion.Armory.Common.TileEntity;

import com.Orion.Armory.Common.Crafting.Anvil.AnvilRecipe;
import com.Orion.Armory.Common.Crafting.Anvil.IAnvilRecipeComponent;
import com.Orion.Armory.Common.Item.ItemHammer;
import com.Orion.Armory.Common.Item.ItemTongs;
import com.Orion.Armory.Network.Messages.MessageTileEntityArmorsAnvil;
import com.Orion.Armory.Network.Messages.MessageTileEntityFirePit;
import com.Orion.Armory.Network.NetworkManager;
import com.Orion.Armory.Util.Core.Coordinate;
import com.Orion.Armory.Util.Core.ItemStackHelper;
import com.Orion.Armory.Util.References;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.ForgeDirection;
import scala.actors.threadpool.Arrays;

import javax.naming.ldap.ExtendedRequest;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Orion
 * Created on 02.05.2015
 * 00:24
 * <p/>
 * Copyrighted according to Project specific license
 */
public class TileEntityArmorsAnvil extends TileEntityArmory implements IInventory
{
    public static int MAX_CRAFTINGSLOTS = 25;
    public static int MAX_OUTPUTSLOTS = 1;
    public static int MAX_HAMMERSLOTS = 1;
    public static int MAX_TONGSLOTS = 1;
    public static int MAX_ADDITIONALSLOTS = 3;
    public static int MAX_COOLSLOTS = 1;
    public static int MAX_SLOTS = MAX_CRAFTINGSLOTS + MAX_OUTPUTSLOTS + MAX_HAMMERSLOTS + MAX_TONGSLOTS + MAX_ADDITIONALSLOTS + MAX_COOLSLOTS;

    public ItemStack[] iCraftingStacks = new ItemStack[MAX_CRAFTINGSLOTS];
    public ItemStack[] iOutPutStacks = new ItemStack[MAX_OUTPUTSLOTS];
    public ItemStack[] iHammerStacks = new ItemStack[MAX_HAMMERSLOTS];
    public ItemStack[] iTongStacks = new ItemStack[MAX_TONGSLOTS];
    public ItemStack[] iAdditionalCraftingStacks = new ItemStack[MAX_ADDITIONALSLOTS];
    public ItemStack[] iCoolStacks = new ItemStack[MAX_COOLSLOTS];

    private int iTEExist = 0;
    public int iCraftingProgress = 0;
    private AnvilRecipe iCurrentValidRecipe;

    private static ArrayList<AnvilRecipe> iRecipes = new ArrayList<AnvilRecipe>();

    @Override
    public int getSizeInventory() {
        return MAX_CRAFTINGSLOTS + MAX_OUTPUTSLOTS + MAX_HAMMERSLOTS+ MAX_TONGSLOTS;
    }

    @Override
    public ItemStack getStackInSlot(int pSlotID) {
        if (pSlotID < MAX_CRAFTINGSLOTS)
            return iCraftingStacks[pSlotID];

        pSlotID -= MAX_CRAFTINGSLOTS;

        if (pSlotID < MAX_OUTPUTSLOTS)
            return iOutPutStacks[pSlotID];

        pSlotID -= MAX_OUTPUTSLOTS;

        if (pSlotID < MAX_HAMMERSLOTS)
            return iHammerStacks[pSlotID];

        pSlotID -= MAX_OUTPUTSLOTS;

        if (pSlotID < MAX_TONGSLOTS)
            return iTongStacks[pSlotID];

        pSlotID -= MAX_TONGSLOTS;

        if (pSlotID > MAX_ADDITIONALSLOTS)
            return iAdditionalCraftingStacks[pSlotID];

        pSlotID -= MAX_ADDITIONALSLOTS;

        if (pSlotID < MAX_COOLSLOTS)
            return iCoolStacks[pSlotID];

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
    public void setInventorySlotContents(int pSlotID, ItemStack pNewItemStack) {
        if (pSlotID < 0)
            return;

        if (pSlotID < MAX_CRAFTINGSLOTS)
        {
            iCraftingStacks[pSlotID] = pNewItemStack;
            iCraftingProgress = 0;
            iCurrentValidRecipe = null;
            findValidRecipe();
            return;
        }


        pSlotID -= MAX_CRAFTINGSLOTS;

        if (pSlotID < MAX_OUTPUTSLOTS)
        {
            iOutPutStacks[0] = pNewItemStack;
            return;
        }

        pSlotID -= MAX_OUTPUTSLOTS;

        if (pSlotID < MAX_HAMMERSLOTS) {
            iHammerStacks[pSlotID] = pNewItemStack;
            iCraftingProgress = 0;
            iCurrentValidRecipe = null;
            findValidRecipe();
            return;
        }

        pSlotID -= MAX_OUTPUTSLOTS;

        if (pSlotID < MAX_TONGSLOTS) {
            iTongStacks[pSlotID] = pNewItemStack;
            iCraftingProgress = 0;
            iCurrentValidRecipe = null;
            findValidRecipe();
            return;
        }

        pSlotID -= MAX_TONGSLOTS;

        if (pSlotID < MAX_ADDITIONALSLOTS)
        {
            iAdditionalCraftingStacks[pSlotID] = pNewItemStack;
            iCraftingProgress = 0;
            iCurrentValidRecipe = null;
            findValidRecipe();
            return;
        }

        pSlotID -= MAX_ADDITIONALSLOTS;

        if (pSlotID < MAX_COOLSLOTS){
            iCoolStacks[pSlotID] = pNewItemStack;
            iCraftingProgress = 0;
            iCurrentValidRecipe = null;
            findValidRecipe();
            return;
        }
    }

    @Override
    public String getInventoryName() {
        return this.hasCustomInventoryName() ? this.iName : StatCollector.translateToLocal(References.InternalNames.Blocks.ArmorsAnvil);
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
    public boolean isItemValidForSlot(int pSlotID, ItemStack pTargetStack) {
        if (pSlotID < MAX_CRAFTINGSLOTS)
            return true;

        pSlotID -= MAX_CRAFTINGSLOTS;

        if (pSlotID < MAX_OUTPUTSLOTS)
            return false;

        pSlotID -= MAX_OUTPUTSLOTS;

        if (pSlotID < MAX_HAMMERSLOTS)
            return (pTargetStack.getItem() instanceof ItemHammer);

        pSlotID -= MAX_OUTPUTSLOTS;

        if (pSlotID < MAX_TONGSLOTS)
            return (pTargetStack.getItem() instanceof ItemTongs);

        return false;
    }

    @Override
    public void readFromNBT(NBTTagCompound pCompound)
    {
        super.readFromNBT(pCompound);

        NBTTagList tCraftingList = pCompound.getTagList(References.NBTTagCompoundData.TE.Anvil.CRAFTINGSTACKS, 10);
        for (int tStack = 0; tStack < tCraftingList.tagCount(); tStack++)
        {
            NBTTagCompound tStackCompound = tCraftingList.getCompoundTagAt(tStack);
            Integer tSlotIndex = tStackCompound.getInteger(References.NBTTagCompoundData.TE.Basic.SLOT);

            iCraftingStacks[tSlotIndex] = ItemStack.loadItemStackFromNBT(tStackCompound);
        }

        NBTTagList tOutputList = pCompound.getTagList(References.NBTTagCompoundData.TE.Anvil.OUTPUTSTACKS, 10);
        for (int tStack = 0; tStack < tOutputList.tagCount(); tStack++)
        {
            NBTTagCompound tStackCompound = tOutputList.getCompoundTagAt(tStack);
            Integer tSlotIndex = tStackCompound.getInteger(References.NBTTagCompoundData.TE.Basic.SLOT);

            iOutPutStacks[tSlotIndex] = ItemStack.loadItemStackFromNBT(tStackCompound);
        }

        NBTTagList tHammerList = pCompound.getTagList(References.NBTTagCompoundData.TE.Anvil.HAMMERSTACKS, 10);
        for (int tStack = 0; tStack < tHammerList.tagCount(); tStack++)
        {
            NBTTagCompound tStackCompound = tHammerList.getCompoundTagAt(tStack);
            Integer tSlotIndex = tStackCompound.getInteger(References.NBTTagCompoundData.TE.Basic.SLOT);

            iHammerStacks[tSlotIndex] = ItemStack.loadItemStackFromNBT(tStackCompound);
        }

        NBTTagList tTongList = pCompound.getTagList(References.NBTTagCompoundData.TE.Anvil.TONGSTACKS, 10);
        for (int tStack = 0; tStack < tTongList.tagCount(); tStack++)
        {
            NBTTagCompound tStackCompound = tTongList.getCompoundTagAt(tStack);
            Integer tSlotIndex = tStackCompound.getInteger(References.NBTTagCompoundData.TE.Basic.SLOT);

            iTongStacks[tSlotIndex] = ItemStack.loadItemStackFromNBT(tStackCompound);
        }

        NBTTagList tAdditionalCraftingList = pCompound.getTagList(References.NBTTagCompoundData.TE.Anvil.ADDITIONALSTACKS, 10);
        for (int tStack = 0; tStack < tAdditionalCraftingList.tagCount(); tStack++)
        {
            NBTTagCompound tStackCompound = tAdditionalCraftingList.getCompoundTagAt(tStack);
            Integer tSlotIndex = tStackCompound.getInteger(References.NBTTagCompoundData.TE.Basic.SLOT);

            iAdditionalCraftingStacks[tSlotIndex] = ItemStack.loadItemStackFromNBT(tStackCompound);
        }

        NBTTagList tCoolList = pCompound.getTagList(References.NBTTagCompoundData.TE.Anvil.COOLSTACKS, 10);
        for (int tStack = 0; tStack < tCoolList.tagCount(); tStack++)
        {
            NBTTagCompound tStackCompound = tCoolList.getCompoundTagAt(tStack);
            Integer tSlotIndex = tStackCompound.getInteger(References.NBTTagCompoundData.TE.Basic.SLOT);

            iCoolStacks[tSlotIndex] = ItemStack.loadItemStackFromNBT(tStackCompound);
        }

        iCraftingProgress = pCompound.getInteger(References.NBTTagCompoundData.TE.Anvil.CRAFTINGPROGRESS);

        findValidRecipe();
    }

    @Override
    public void writeToNBT(NBTTagCompound pCompound)
    {
        super.writeToNBT(pCompound);

        NBTTagList tCraftingList = new NBTTagList();
        for(int tStack = 0; tStack < MAX_CRAFTINGSLOTS; tStack++)
        {
            if (iCraftingStacks[tStack] == null)
            {
                continue;
            }

            NBTTagCompound tIngotCompound = new NBTTagCompound();
            tIngotCompound.setInteger(References.NBTTagCompoundData.TE.Basic.SLOT, tStack);
            iCraftingStacks[tStack].writeToNBT(tIngotCompound);
            tCraftingList.appendTag(tIngotCompound);
        }
        pCompound.setTag(References.NBTTagCompoundData.TE.Anvil.CRAFTINGSTACKS, tCraftingList);

        NBTTagList tOutputList = new NBTTagList();
        for(int tStack = 0; tStack < MAX_OUTPUTSLOTS; tStack++)
        {
            if (iOutPutStacks[tStack] == null)
            {
                continue;
            }

            NBTTagCompound tIngotCompound = new NBTTagCompound();
            tIngotCompound.setInteger(References.NBTTagCompoundData.TE.Basic.SLOT, tStack);
            iOutPutStacks[tStack].writeToNBT(tIngotCompound);
            tOutputList.appendTag(tIngotCompound);
        }
        pCompound.setTag(References.NBTTagCompoundData.TE.Anvil.OUTPUTSTACKS, tOutputList);

        NBTTagList tHammerList = new NBTTagList();
        for(int tStack = 0; tStack < MAX_HAMMERSLOTS; tStack++)
        {
            if (iHammerStacks[tStack] == null)
            {
                continue;
            }

            NBTTagCompound tIngotCompound = new NBTTagCompound();
            tIngotCompound.setInteger(References.NBTTagCompoundData.TE.Basic.SLOT, tStack);
            iHammerStacks[tStack].writeToNBT(tIngotCompound);
            tHammerList.appendTag(tIngotCompound);
        }
        pCompound.setTag(References.NBTTagCompoundData.TE.Anvil.HAMMERSTACKS, tHammerList);

        NBTTagList tTongList = new NBTTagList();
        for(int tStack = 0; tStack < MAX_TONGSLOTS; tStack++)
        {
            if (iTongStacks[tStack] == null)
            {
                continue;
            }

            NBTTagCompound tIngotCompound = new NBTTagCompound();
            tIngotCompound.setInteger(References.NBTTagCompoundData.TE.Basic.SLOT, tStack);
            iTongStacks[tStack].writeToNBT(tIngotCompound);
            tTongList.appendTag(tIngotCompound);
        }
        pCompound.setTag(References.NBTTagCompoundData.TE.Anvil.TONGSTACKS, tTongList);

        NBTTagList tAdditionalList = new NBTTagList();
        for(int tStack = 0; tStack < MAX_ADDITIONALSLOTS; tStack++)
        {
            if (iAdditionalCraftingStacks[tStack] == null)
            {
                continue;
            }

            NBTTagCompound tIngotCompound = new NBTTagCompound();
            tIngotCompound.setInteger(References.NBTTagCompoundData.TE.Basic.SLOT, tStack);
            iAdditionalCraftingStacks[tStack].writeToNBT(tIngotCompound);
            tAdditionalList.appendTag(tIngotCompound);
        }
        pCompound.setTag(References.NBTTagCompoundData.TE.Anvil.ADDITIONALSTACKS, tAdditionalList);

        NBTTagList tCoolingList = new NBTTagList();
        for(int tStack = 0; tStack < MAX_COOLSLOTS; tStack++)
        {
            if (iCoolStacks[tStack] == null)
            {
                continue;
            }

            NBTTagCompound tIngotCompound = new NBTTagCompound();
            tIngotCompound.setInteger(References.NBTTagCompoundData.TE.Basic.SLOT, tStack);
            iCoolStacks[tStack].writeToNBT(tIngotCompound);
            tCoolingList.appendTag(tIngotCompound);
        }
        pCompound.setTag(References.NBTTagCompoundData.TE.Anvil.COOLSTACKS, tCoolingList);

        pCompound.setInteger(References.NBTTagCompoundData.TE.Anvil.CRAFTINGPROGRESS, iCraftingProgress);
    }

    @Override
    public void updateEntity()
    {
        boolean tUpdated = false;

        if (iCurrentValidRecipe != null) {
            iTEExist++;
            if (iTEExist == 20) {
                iCraftingProgress++;
                iTEExist = 0;
            }


            if ((iCraftingProgress == iCurrentValidRecipe.iTargetProgress) && !worldObj.isRemote) {
                if (iOutPutStacks[0] != null) {
                    iOutPutStacks[0].stackSize += iCurrentValidRecipe.getResult(iCraftingStacks, iAdditionalCraftingStacks).stackSize;

                } else {
                    iOutPutStacks[0] = iCurrentValidRecipe.getResult(iCraftingStacks, iAdditionalCraftingStacks);
                }

                ProcessPerformedCrafting();
                iCurrentValidRecipe = null;
                iCraftingProgress = 0;

                tUpdated = true;

            }

            if (!worldObj.isRemote && tUpdated)
            {
                markDirty();
            }
        }
    }

    @Override
    public float getProgressBarValue(String pProgressBarID) {
        if (pProgressBarID.equals("Gui.Anvil.ExtendedCrafting.Progress.Arrow.1"))
        {
            if (iCurrentValidRecipe == null)
            {
                return 0F;
            }

            return ((float)iCraftingProgress / (float) iCurrentValidRecipe.iTargetProgress);
        }

        return 1F;
    }

    public static void addRecipe(AnvilRecipe pNewRecipe)
    {
        iRecipes.add(pNewRecipe);
    }

    public static ArrayList<AnvilRecipe> getRecipes() { return iRecipes; }

    public void findValidRecipe()
    {
        int tHammerUsagesLeft = -1;
        int tTongUsagesLeft = -1;

        if (iHammerStacks[0] != null)
            tHammerUsagesLeft = iHammerStacks[0].getItemDamage();

        if (iTongStacks[0] != null)
            tTongUsagesLeft = iTongStacks[0].getItemDamage();

        for(AnvilRecipe tRecipe : iRecipes)
        {
            if(tRecipe.matchesRecipe(iCraftingStacks, iAdditionalCraftingStacks, tHammerUsagesLeft, tTongUsagesLeft))
            {
                if (iOutPutStacks[0] != null)
                {
                    if ((tRecipe.getResult(iCraftingStacks, iAdditionalCraftingStacks).stackSize + iOutPutStacks[0].stackSize) <= iOutPutStacks[0].getMaxStackSize())
                    {
                        iCurrentValidRecipe = tRecipe;
                        return;
                    }
                }
                else
                {
                    iCurrentValidRecipe = tRecipe;
                    return;
                }
            }
        }

        iCurrentValidRecipe = null;
    }

    public void ProcessPerformedCrafting() {
        if (iCurrentValidRecipe == null) {
            return;
        }

        if (iCurrentValidRecipe.iIsShapeLess)
        {
            ArrayList<IAnvilRecipeComponent> tComponentList = new ArrayList<IAnvilRecipeComponent>(Arrays.asList(iCurrentValidRecipe.iComponents.clone()));
            for (int tSlotIndex = 0; tSlotIndex < MAX_CRAFTINGSLOTS; tSlotIndex++) {
                if (iCraftingStacks[tSlotIndex] != null)
                {
                    boolean tProcessedStack = false;

                    Iterator<IAnvilRecipeComponent> tIter = tComponentList.iterator();
                    while (tIter.hasNext() && !tProcessedStack) {
                        IAnvilRecipeComponent tComponent = tIter.next();

                        if (tComponent != null) {
                            iCraftingStacks[tSlotIndex].stackSize = tComponent.getResultingStackSizeForComponent(iCraftingStacks[tSlotIndex]);
                            if (iCraftingStacks[tSlotIndex].stackSize == 0)
                            {
                                iCraftingStacks[tSlotIndex] = null;
                            }

                            tProcessedStack = true;
                        }
                    }
                }
            }
        }
        else {
            for (int tSlotIndex = 0; tSlotIndex < MAX_CRAFTINGSLOTS; tSlotIndex++) {
                if (iCraftingStacks[tSlotIndex] == null)
                    continue;

                IAnvilRecipeComponent tTargetComponent = iCurrentValidRecipe.getComponent(tSlotIndex);
                if (tTargetComponent == null)
                    continue;

                iCraftingStacks[tSlotIndex].stackSize = tTargetComponent.getResultingStackSizeForComponent(iCraftingStacks[tSlotIndex]);
                if (iCraftingStacks[tSlotIndex].stackSize < 1)
                    iCraftingStacks[tSlotIndex] = null;
            }
        }

        for (int tSlotIndex = 0; tSlotIndex < MAX_ADDITIONALSLOTS; tSlotIndex++) {
            if (iAdditionalCraftingStacks[tSlotIndex] == null)
                continue;

            IAnvilRecipeComponent tTargetComponent = iCurrentValidRecipe.getAdditionalComponent(tSlotIndex);
            if (tTargetComponent == null)
                continue;

            iAdditionalCraftingStacks[tSlotIndex].stackSize = tTargetComponent.getResultingStackSizeForComponent(iAdditionalCraftingStacks[tSlotIndex]);
            if (iAdditionalCraftingStacks[tSlotIndex].stackSize < 1)
                iAdditionalCraftingStacks[tSlotIndex] = null;
        }

        if (iCurrentValidRecipe.iHammerUsage > 0) {
            if (iHammerStacks[0].isItemDamaged() == false) {
                iHammerStacks[0].setItemDamage(150);
            }
            iHammerStacks[0].setItemDamage(iHammerStacks[0].getItemDamage() - iCurrentValidRecipe.iHammerUsage);
            if (iHammerStacks[0].getItemDamage() == 0) {
                iHammerStacks[0] = null;
            }
        }

        if (iCurrentValidRecipe.iTongUsage > 0) {
            if (iTongStacks[0].isItemDamaged() == false) {
                iTongStacks[0].setItemDamage(150);
            }
            iTongStacks[0].setItemDamage(iTongStacks[0].getItemDamage() - iCurrentValidRecipe.iTongUsage);
            if (iTongStacks[0].getItemDamage() == 0) {
                iTongStacks[0] = null;
            }
        }
    }

    public AnvilState getCurrentState()
    {
        boolean tFoundCoolingBasin = false;
        boolean tFoundHelperRack = false;
        if (iCurrentDirection == ForgeDirection.NORTH || iCurrentDirection == ForgeDirection.SOUTH)
        {
            TileEntity tLeftTE = worldObj.getTileEntity(xCoord + ForgeDirection.EAST.offsetX, yCoord + ForgeDirection.EAST.offsetY, zCoord + ForgeDirection.EAST.offsetZ);
            TileEntity tRightTE = worldObj.getTileEntity(xCoord + ForgeDirection.WEST.offsetX, yCoord + ForgeDirection.WEST.offsetY, zCoord + ForgeDirection.WEST.offsetZ);

            if (tLeftTE != null)
            {
                if (tLeftTE instanceof TileEntityCoolingBasin)
                {
                    tFoundCoolingBasin = true;
                }

                if (tLeftTE instanceof  TileEntityArmorsRack)
                {
                    tFoundHelperRack = true;
                }
            }

            if (tRightTE != null)
            {
                if (tRightTE instanceof TileEntityCoolingBasin)
                {
                    tFoundCoolingBasin = true;
                }

                if (tRightTE instanceof TileEntityArmorsRack)
                {
                    tFoundHelperRack = true;
                }
            }
        }
        else
        {
            TileEntity tLeftTE = worldObj.getTileEntity(xCoord + ForgeDirection.NORTH.offsetX, yCoord + ForgeDirection.NORTH.offsetY, zCoord + ForgeDirection.NORTH.offsetZ);
            TileEntity tRightTE = worldObj.getTileEntity(xCoord + ForgeDirection.SOUTH.offsetX, yCoord + ForgeDirection.SOUTH.offsetY, zCoord + ForgeDirection.SOUTH.offsetZ);

            if (tLeftTE != null) {
                if (tLeftTE instanceof TileEntityCoolingBasin) {
                    tFoundCoolingBasin = true;
                }

                if (tLeftTE instanceof TileEntityArmorsRack) {
                    tFoundHelperRack = true;
                }
            }

            if (tRightTE != null)
            {
                if (tRightTE instanceof TileEntityCoolingBasin) {
                    tFoundCoolingBasin = true;
                }

                if (tRightTE instanceof TileEntityArmorsRack) {
                    tFoundHelperRack = true;
                }
            }
        }

        if (!tFoundCoolingBasin && !tFoundHelperRack)
            return AnvilState.Minimal;

        if (tFoundCoolingBasin && !tFoundHelperRack)
            return AnvilState.Cooling;

        if (!tFoundCoolingBasin && tFoundHelperRack)
            return AnvilState.Extended;

        return AnvilState.Standard;
    }

    public TileEntityCoolingBasin getCoolingBasin()
    {
        if (iCurrentDirection == ForgeDirection.NORTH || iCurrentDirection == ForgeDirection.SOUTH)
        {
            TileEntity tLeftTE = worldObj.getTileEntity(xCoord + ForgeDirection.EAST.offsetX, yCoord + ForgeDirection.EAST.offsetY, zCoord + ForgeDirection.EAST.offsetZ);
            TileEntity tRightTE = worldObj.getTileEntity(xCoord + ForgeDirection.WEST.offsetX, yCoord + ForgeDirection.WEST.offsetY, zCoord + ForgeDirection.WEST.offsetZ);

            if (tLeftTE != null) {
                if (tLeftTE instanceof TileEntityCoolingBasin) {
                    return (TileEntityCoolingBasin) tLeftTE;
                }
            }

            if (tRightTE != null)
            {
                if (tRightTE instanceof TileEntityCoolingBasin) {
                    return (TileEntityCoolingBasin) tRightTE;
                }
            }
        }
        else
        {
            TileEntity tLeftTE = worldObj.getTileEntity(xCoord + ForgeDirection.NORTH.offsetX, yCoord + ForgeDirection.NORTH.offsetY, zCoord + ForgeDirection.NORTH.offsetZ);
            TileEntity tRightTE = worldObj.getTileEntity(xCoord + ForgeDirection.SOUTH.offsetX, yCoord + ForgeDirection.SOUTH.offsetY, zCoord + ForgeDirection.SOUTH.offsetZ);

            if (tLeftTE != null) {
                if (tLeftTE instanceof TileEntityCoolingBasin) {
                    return (TileEntityCoolingBasin) tLeftTE;
                }
            }

            if (tRightTE != null)
            {
                if (tRightTE instanceof TileEntityCoolingBasin) {
                    return (TileEntityCoolingBasin) tRightTE;
                }
            }
        }

        return null;
    }

    public TileEntityArmorsRack getRack()
    {
        if (iCurrentDirection == ForgeDirection.NORTH || iCurrentDirection == ForgeDirection.SOUTH)
        {
            TileEntity tLeftTE = worldObj.getTileEntity(xCoord + ForgeDirection.EAST.offsetX, yCoord + ForgeDirection.EAST.offsetY, zCoord + ForgeDirection.EAST.offsetZ);
            TileEntity tRightTE = worldObj.getTileEntity(xCoord + ForgeDirection.WEST.offsetX, yCoord + ForgeDirection.WEST.offsetY, zCoord + ForgeDirection.WEST.offsetZ);

            if (tLeftTE != null) {
                if (tLeftTE instanceof TileEntityArmorsRack) {
                    return (TileEntityArmorsRack) tLeftTE;
                }
            }

            if (tRightTE != null)
            {
                if (tRightTE instanceof TileEntityArmorsRack) {
                    return (TileEntityArmorsRack) tRightTE;
                }
            }
        }
        else
        {
            TileEntity tLeftTE = worldObj.getTileEntity(xCoord + ForgeDirection.NORTH.offsetX, yCoord + ForgeDirection.NORTH.offsetY, zCoord + ForgeDirection.NORTH.offsetZ);
            TileEntity tRightTE = worldObj.getTileEntity(xCoord + ForgeDirection.SOUTH.offsetX, yCoord + ForgeDirection.SOUTH.offsetY, zCoord + ForgeDirection.SOUTH.offsetZ);

            if (tLeftTE != null) {
                if (tLeftTE instanceof TileEntityArmorsRack) {
                    return (TileEntityArmorsRack) tLeftTE;
                }
            }

            if (tRightTE != null)
            {
                if (tRightTE instanceof TileEntityArmorsRack) {
                    return (TileEntityArmorsRack) tRightTE;
                }
            }
        }

        return null;
    }

    @Override
    public void markDirty()
    {
        NetworkManager.INSTANCE.sendToAllAround(new MessageTileEntityArmorsAnvil(this), new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId,(double) this.xCoord,(double) this.yCoord,(double) this.zCoord, 128));

        super.markDirty();
        worldObj.func_147451_t(xCoord, yCoord, zCoord);
    }

    @Override
    public Packet getDescriptionPacket()
    {
        return NetworkManager.INSTANCE.getPacketFrom(new MessageTileEntityArmorsAnvil(this));
    }

    public enum AnvilState
    {
        Minimal ,
        Extended,
        Cooling,
        Standard
    }
}
