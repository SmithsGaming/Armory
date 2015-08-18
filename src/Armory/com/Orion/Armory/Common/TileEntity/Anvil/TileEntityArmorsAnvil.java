/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.Orion.Armory.Common.TileEntity.Anvil;

import com.Orion.Armory.API.Crafting.SmithingsAnvil.AnvilRecipeRegistry;
import com.Orion.Armory.API.Crafting.SmithingsAnvil.Components.IAnvilRecipeComponent;
import com.Orion.Armory.API.Crafting.SmithingsAnvil.Recipe.AnvilRecipe;
import com.Orion.Armory.API.Knowledge.IBluePrintContainerItem;
import com.Orion.Armory.API.Knowledge.IBluePrintItem;
import com.Orion.Armory.API.Knowledge.IKnowledgedGameElement;
import com.Orion.Armory.API.Knowledge.KnowledgeEntityProperty;
import com.Orion.Armory.Common.Item.ItemHammer;
import com.Orion.Armory.Common.Item.ItemTongs;
import com.Orion.Armory.Common.TileEntity.Core.ICustomInputHandler;
import com.Orion.Armory.Common.TileEntity.Core.TileEntityArmory;
import com.Orion.Armory.Network.Messages.MessageCustomInput;
import com.Orion.Armory.Network.Messages.MessageTileEntityArmorsAnvil;
import com.Orion.Armory.Network.NetworkManager;
import com.Orion.Armory.Util.Core.ItemStackHelper;
import com.Orion.Armory.Util.References;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import java.util.*;

public class TileEntityArmorsAnvil extends TileEntityArmory implements IInventory, ICustomInputHandler
{
    public static int MAX_CRAFTINGSLOTS = 25;
    public static int MAX_OUTPUTSLOTS = 1;
    public static int MAX_HAMMERSLOTS = 1;
    public static int MAX_TONGSLOTS = 1;
    public static int MAX_ADDITIONALSLOTS = 3;
    public static int MAX_COOLSLOTS = 1;
    public static int MAX_BLUEPRINTLIBRARYSLOTS = 1;
    public static int MAX_SLOTS = MAX_CRAFTINGSLOTS + MAX_OUTPUTSLOTS + MAX_HAMMERSLOTS + MAX_TONGSLOTS + MAX_ADDITIONALSLOTS + MAX_COOLSLOTS + MAX_BLUEPRINTLIBRARYSLOTS;
    public ItemStack[] iCraftingStacks = new ItemStack[MAX_CRAFTINGSLOTS];
    public ItemStack[] iOutPutStacks = new ItemStack[MAX_OUTPUTSLOTS];
    public ItemStack[] iHammerStacks = new ItemStack[MAX_HAMMERSLOTS];
    public ItemStack[] iTongStacks = new ItemStack[MAX_TONGSLOTS];
    public ItemStack[] iAdditionalCraftingStacks = new ItemStack[MAX_ADDITIONALSLOTS];
    public ItemStack[] iCoolStacks = new ItemStack[MAX_COOLSLOTS];
    public ItemStack[] iBluePrintLibraryStacks = new ItemStack[MAX_BLUEPRINTLIBRARYSLOTS];

    public int iCraftingProgress = 0;
    private String iInputName = "";
    private int iTEExist = 0;
    private AnvilRecipe iCurrentValidRecipe;

    private HashMap<UUID, EntityPlayer> iConnectedPlayers = new HashMap<UUID, EntityPlayer>();

    @Override
    public int getSizeInventory() {
        return MAX_CRAFTINGSLOTS + MAX_OUTPUTSLOTS + MAX_HAMMERSLOTS + MAX_TONGSLOTS + MAX_BLUEPRINTLIBRARYSLOTS;
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

        if (pSlotID < MAX_ADDITIONALSLOTS)
            return iAdditionalCraftingStacks[pSlotID];

        pSlotID -= MAX_ADDITIONALSLOTS;

        if (pSlotID < MAX_COOLSLOTS)
            return iCoolStacks[pSlotID];

        pSlotID -= MAX_COOLSLOTS;

        if (pSlotID < MAX_BLUEPRINTLIBRARYSLOTS)
            return iBluePrintLibraryStacks[pSlotID];

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
    public Object getGUIComponentRelatedObject(String pComponentID) {
        if (pComponentID.equals("Gui.Anvil.Cooling.Tank"))
            return new FluidStack(FluidRegistry.WATER, 3500);

        return null;
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
            return;
        }

        pSlotID -= MAX_COOLSLOTS;

        if (pSlotID < MAX_BLUEPRINTLIBRARYSLOTS) {
            iBluePrintLibraryStacks[pSlotID] = pNewItemStack;
            iCraftingProgress = 0;
            iCurrentValidRecipe = null;
            findValidRecipe();
            return;
        }
    }

    @Override
    public String getInventoryName() {
        return this.hasCustomInventoryName() ? this.getDisplayName() : StatCollector.translateToLocal(References.InternalNames.Blocks.ArmorsAnvil);
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


        pSlotID -= MAX_TONGSLOTS;

        if (pSlotID > MAX_ADDITIONALSLOTS)
            return false;

        pSlotID -= MAX_ADDITIONALSLOTS;

        if (pSlotID < MAX_COOLSLOTS)
            return false;

        pSlotID -= MAX_COOLSLOTS;

        if (pSlotID < MAX_BLUEPRINTLIBRARYSLOTS)
            return false;

        return false;
    }

    @Override
    public void readFromNBT(NBTTagCompound pCompound)
    {
        super.readFromNBT(pCompound);

        iCraftingProgress = pCompound.getInteger(References.NBTTagCompoundData.TE.Anvil.CRAFTINGPROGRESS);

        findValidRecipe();
    }

    @Override
    public void writeToNBT(NBTTagCompound pCompound)
    {
        super.writeToNBT(pCompound);

        pCompound.setInteger(References.NBTTagCompoundData.TE.Anvil.CRAFTINGPROGRESS, iCraftingProgress);
    }

    @Override
    public void handleGuiComponentUpdate(String pInputID, String pInput) {
        NetworkManager.INSTANCE.sendToServer(new MessageCustomInput(pInputID, pInput));
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


            if ((iCraftingProgress == iCurrentValidRecipe.getMinimumProgress()) && !worldObj.isRemote) {
                if (iOutPutStacks[0] != null) {
                    iOutPutStacks[0].stackSize += iCurrentValidRecipe.getResult(iCraftingStacks, iAdditionalCraftingStacks).stackSize;
                } else {
                    iOutPutStacks[0] = iCurrentValidRecipe.getResult(iCraftingStacks, iAdditionalCraftingStacks);
                    if (!iInputName.equals("")) {
                        iOutPutStacks[0].getTagCompound().setString(References.NBTTagCompoundData.CustomName, iInputName);
                    }
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

            return ((float) iCraftingProgress / (float) iCurrentValidRecipe.getMinimumProgress());
        }

        return 1F;
    }

    public void findValidRecipe()
    {
        int tHammerUsagesLeft = -1;
        int tTongUsagesLeft = -1;

        if (iHammerStacks[0] != null)
            tHammerUsagesLeft = iHammerStacks[0].getItemDamage();

        if (iTongStacks[0] != null)
            tTongUsagesLeft = iTongStacks[0].getItemDamage();

        for (AnvilRecipe tRecipe : AnvilRecipeRegistry.getInstance().getRecipes().values()) {
            if (tRecipe.matchesRecipe(iCraftingStacks, iAdditionalCraftingStacks, tHammerUsagesLeft, tTongUsagesLeft)) {
                if (iOutPutStacks[0] != null) {
                    ItemStack tResultStack = tRecipe.getResult(iCraftingStacks, iAdditionalCraftingStacks);

                    if (!ItemStackHelper.equalsIgnoreStackSize(tResultStack, iOutPutStacks[0]))
                        continue;

                    if ((tResultStack.stackSize + iOutPutStacks[0].stackSize) <= iOutPutStacks[0].getMaxStackSize()) {
                        if (tRecipe.getRequiresBlueprint()) {
                            if (iBluePrintLibraryStacks[0] == null)
                                continue;

                            if (!(iBluePrintLibraryStacks[0].getItem() instanceof IBluePrintContainerItem))
                                continue;

                            ArrayList<ItemStack> tBluePrints = ((IBluePrintContainerItem) iBluePrintLibraryStacks[0].getItem()).getStoredBluePrints(iBluePrintLibraryStacks[0]);

                            for (ItemStack tBluePrintStack : tBluePrints) {
                                IBluePrintItem tBluePrint = (IBluePrintItem) tBluePrintStack.getItem();

                                if (tRecipe.getBlueprintName().equals(tBluePrint.getBlueprintID(tBluePrintStack)) && tBluePrint.getBluePrintQuality(tBluePrintStack) > 0F) {
                                    if (tRecipe.getRequiresKnowledge() && getAverageKnowledgeFloatValue(tRecipe.getKnowledgeName(), true) > 0F) {
                                        iCurrentValidRecipe = tRecipe;
                                        return;
                                    } else if (!tRecipe.getRequiresKnowledge()) {
                                        iCurrentValidRecipe = tRecipe;
                                        return;
                                    }

                                }
                            }
                        } else if (!tRecipe.getRequiresBlueprint()) {
                            iCurrentValidRecipe = tRecipe;
                            return;
                        }
                    }
                } else {
                    if (tRecipe.getRequiresBlueprint()) {
                        if (iBluePrintLibraryStacks[0] == null)
                            continue;

                        if (!(iBluePrintLibraryStacks[0].getItem() instanceof IBluePrintContainerItem))
                            continue;

                        ArrayList<ItemStack> tBluePrints = ((IBluePrintContainerItem) iBluePrintLibraryStacks[0].getItem()).getStoredBluePrints(iBluePrintLibraryStacks[0]);

                        for (ItemStack tBluePrintStack : tBluePrints) {
                            IBluePrintItem tBluePrint = (IBluePrintItem) tBluePrintStack.getItem();

                            if (tRecipe.getBlueprintName().equals(tBluePrint.getBlueprintID(tBluePrintStack)) && tBluePrint.getBluePrintQuality(tBluePrintStack) > 0F) {
                                if (tRecipe.getRequiresKnowledge() && getAverageKnowledgeFloatValue(tRecipe.getKnowledgeName(), true) > 0F) {
                                    iCurrentValidRecipe = tRecipe;
                                    return;
                                } else if (!tRecipe.getRequiresKnowledge()) {
                                    iCurrentValidRecipe = tRecipe;
                                    return;
                                }

                            }
                        }
                    } else if (!tRecipe.getRequiresBlueprint()) {
                        iCurrentValidRecipe = tRecipe;
                        return;
                    }
                }
            }
        }

        iCurrentValidRecipe = null;
    }

    public void ProcessPerformedCrafting() {
        if (iCurrentValidRecipe == null) {
            return;
        }

        if (iCurrentValidRecipe.getUsesHammer())
        {
            ArrayList<IAnvilRecipeComponent> tComponentList = new ArrayList<IAnvilRecipeComponent>(Arrays.asList(iCurrentValidRecipe.getComponents().clone()));
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

        if (iCurrentValidRecipe.getUsesHammer()) {
            if (iHammerStacks[0].isItemDamaged() == false) {
                iHammerStacks[0].setItemDamage(150);
            }
            iHammerStacks[0].setItemDamage(iHammerStacks[0].getItemDamage() - iCurrentValidRecipe.getHammerUsage());
            if (iHammerStacks[0].getItemDamage() == 0) {
                iHammerStacks[0] = null;
            }
        }

        if (iCurrentValidRecipe.getUsesTongs()) {
            if (iTongStacks[0].isItemDamaged() == false) {
                iTongStacks[0].setItemDamage(150);
            }
            iTongStacks[0].setItemDamage(iTongStacks[0].getItemDamage() - iCurrentValidRecipe.getTongsUsage());
            if (iTongStacks[0].getItemDamage() == 0) {
                iTongStacks[0] = null;
            }
        }
    }

    public AnvilState getCurrentState()
    {
        boolean DEBUG = false;
        if(DEBUG)
            return AnvilState.Standard;

        boolean tFoundCoolingBasin = false;
        boolean tFoundHelperRack = false;
        if (getDirection() == ForgeDirection.NORTH || getDirection() == ForgeDirection.SOUTH)
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
        if (getDirection() == ForgeDirection.NORTH || getDirection() == ForgeDirection.SOUTH)
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
        if (getDirection() == ForgeDirection.NORTH || getDirection() == ForgeDirection.SOUTH)
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

    public void registerNewWatchingPlayer(UUID pPlayerID) {
        if (!iConnectedPlayers.containsKey(pPlayerID)) {
            EntityPlayer pPlayer = getWorldObj().func_152378_a(pPlayerID);
            iConnectedPlayers.put(pPlayerID, pPlayer);
        }
    }

    public void removeWatchingPlayer(UUID pPlayerID) {
        if (iConnectedPlayers.containsKey(pPlayerID))
            iConnectedPlayers.remove(pPlayerID);
    }

    public float getAverageKnowledgeFloatValue(String pKnowledgeID, boolean pUseUnknownPlayers) {
        float tTotalKnowledgeValue = 0F;
        int tTotalPlayerCount = 0;

        for (EntityPlayer pPlayer : iConnectedPlayers.values()) {
            KnowledgeEntityProperty tKnowledgeCollection = (new KnowledgeEntityProperty()).get(pPlayer);
            if (tKnowledgeCollection == null) {
                if (pUseUnknownPlayers) {
                    tTotalPlayerCount++;
                }

                continue;
            }

            IKnowledgedGameElement tKnowledge = tKnowledgeCollection.getKnowledge(pKnowledgeID);

            if ((tKnowledge == null || (tKnowledge != null && tKnowledge.getExperienceLevel() == 0F)) && pUseUnknownPlayers) {
                tTotalPlayerCount++;
                continue;
            }

            if ((tKnowledge != null && tKnowledge.getExperienceLevel() > 0F)) {
                tTotalKnowledgeValue += tKnowledge.getExperienceLevel();
                tTotalPlayerCount++;
            }
        }

        if (tTotalPlayerCount == 0)
            return 0F;

        return tTotalKnowledgeValue / tTotalPlayerCount;
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

    @Override
    public void HandleCustomInput(String pInputID, String pInput) {
        if (pInputID.equals(References.InternalNames.InputHandlers.Anvil.ITEMNAME)) {
            iInputName = pInput;
        } else if (pInputID.equals(References.InternalNames.InputHandlers.Anvil.PLAYEROPENGUI)) {
            registerNewWatchingPlayer(UUID.fromString(pInput));
        } else if (pInputID.equals(References.InternalNames.InputHandlers.Anvil.PLAYERCLOSEGUI)) {
            removeWatchingPlayer(UUID.fromString(pInput));
        }
    }

    public enum AnvilState
    {
        Minimal ,
        Extended,
        Cooling,
        Standard
    }
}
