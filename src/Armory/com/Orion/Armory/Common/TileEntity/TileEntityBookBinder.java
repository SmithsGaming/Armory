/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.Orion.Armory.Common.TileEntity;

import com.Orion.Armory.API.Knowledge.IBluePrintContainerItem;
import com.Orion.Armory.API.Knowledge.IBluePrintItem;
import com.Orion.Armory.API.Knowledge.IBlueprint;
import com.Orion.Armory.Common.Item.Knowledge.LabelledBlueprintGroup;
import com.Orion.Armory.Common.Registry.GeneralRegistry;
import com.Orion.Armory.Common.TileEntity.Core.ICustomInputHandler;
import com.Orion.Armory.Common.TileEntity.Core.TileEntityArmory;
import com.Orion.Armory.Network.Messages.MessageTileEntityBookBinder;
import com.Orion.Armory.Network.NetworkManager;
import com.Orion.Armory.Util.References;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.util.StatCollector;

import java.util.ArrayList;

public class TileEntityBookBinder extends TileEntityArmory implements IInventory, ICustomInputHandler {

    ItemStack iBindingBookStack;
    ItemStack iBindingBluePrintStack;

    ItemStack iResearchingTargetStack;
    ItemStack iResearchingOutputStack;

    ItemStack iResearchFuelstack;
    ItemStack iResearchHammerStack;
    ItemStack iResearchTongsStack;

    ItemStack iPaperStack;
    ItemStack iFeatherStack;
    ItemStack iInkPotStack;

    OperationMode iOpMode = OperationMode.BookBinding;

    Float iOperationProgress = 0F;

    @Override
    public float getProgressBarValue(String pProgressBarID) {
        return iOperationProgress;
    }

    @Override
    public int getSizeInventory() {
        return 4;
    }

    @Override
    public void updateEntity() {
        super.updateEntity();

        if (worldObj.isRemote)
            return;

        boolean tSendUpdate = false;

        if (iOpMode == OperationMode.BookBinding) {
            if (iOperationProgress > 0F) {
                tSendUpdate = true;
                iOperationProgress += 0.1F;

                if (iOperationProgress > 2F) {
                    GeneralRegistry.iLogger.info("Merging blueprint into Guide!");

                    IBluePrintContainerItem tGuide = (IBluePrintContainerItem) iBindingBookStack.getItem();
                    ArrayList<LabelledBlueprintGroup> tGroups = tGuide.getBlueprintGroups(iBindingBookStack);

                    tGroups.get(0).Stacks.add(iBindingBluePrintStack);

                    tGuide.writeBlueprintGroupsToStack(iBindingBookStack, tGroups);

                    iBindingBluePrintStack = null;
                    iOperationProgress = 0F;
                }
            } else {
                if (iBindingBookStack != null && iBindingBluePrintStack != null) {
                    if (!checkIfGuideContainsBlueprint(iBindingBookStack, iBindingBluePrintStack)) {
                        tSendUpdate = true;
                        iOperationProgress += 0.1F;
                    } else {
                        tSendUpdate = true;
                        iOperationProgress = 0F;
                    }
                } else {
                    tSendUpdate = true;
                    iOperationProgress = 0F;
                }
            }
        }

        if (tSendUpdate)
            this.markDirty();
    }

    @Override
    public ItemStack getStackInSlot(int pSlotIndex) {
        switch (pSlotIndex) {
            case 0:
                return iBindingBookStack;
            case 1:
                return iBindingBluePrintStack;
            case 2:
                return iResearchingTargetStack;
            case 3:
                return iResearchingOutputStack;
            default:
                return null;
        }
    }

    @Override
    public ItemStack decrStackSize(int pSlotIndex, int pDecrAmount) {
        ItemStack tItemStack = getStackInSlot(pSlotIndex);
        if (tItemStack == null) {
            return null;
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
    public ItemStack getStackInSlotOnClosing(int pSlotIndex) {
        return getStackInSlot(pSlotIndex);
    }

    @Override
    public void setInventorySlotContents(int pSlotIndex, ItemStack pStack) {
        iOperationProgress = 0F;

        switch (pSlotIndex) {
            case 0:
                iBindingBookStack = pStack;
                break;
            case 1:
                iBindingBluePrintStack = pStack;
                break;
            case 2:
                iResearchingTargetStack = pStack;
                break;
            case 3:
                iResearchingOutputStack = pStack;
                break;
            default:
                break;
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
        return 1;
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
        switch (pSlotIndex) {
            case 0:
                return pItemStack.getItem() instanceof IBluePrintContainerItem;
            case 1:
                return pItemStack.getItem() instanceof IBluePrintItem;
            case 2:
                return true;
            case 3:
                return false;
            default:
                return false;
        }
    }

    @Override
    public Object getGUIComponentRelatedObject(String pComponentID) {
        return null;
    }

    @Override
    public void HandleCustomInput(String pInputID, String pInput) {

    }


    public boolean checkIfGuideContainsBlueprint(ItemStack pBlueprintStack, ItemStack pComparedItemStack) {
        for (LabelledBlueprintGroup tGroup : ((IBluePrintContainerItem) pBlueprintStack.getItem()).getBlueprintGroups(pBlueprintStack)) {
            if (checkIfGroupContainsBlueprint(tGroup, pComparedItemStack))
                return true;
        }

        return false;
    }

    public boolean checkIfGuideContainsBlueprint(ItemStack pBlueprintStack, IBlueprint pBlueprint) {
        for (LabelledBlueprintGroup tGroup : ((IBluePrintContainerItem) pBlueprintStack.getItem()).getBlueprintGroups(pBlueprintStack)) {
            if (checkIfGroupContainsBlueprint(tGroup, pBlueprint))
                return true;
        }

        return false;
    }

    public boolean checkIfGuideContainsBlueprint(ItemStack pBlueprintStack, String pBluePrintID) {
        for (LabelledBlueprintGroup tGroup : ((IBluePrintContainerItem) pBlueprintStack.getItem()).getBlueprintGroups(pBlueprintStack)) {
            if (checkIfGroupContainsBlueprint(tGroup, pBluePrintID))
                return true;
        }

        return false;
    }

    private boolean checkIfGroupContainsBlueprint(LabelledBlueprintGroup pGroup, ItemStack pComparedItemStack) {
        return checkIfGroupContainsBlueprint(pGroup, ((IBluePrintItem) pComparedItemStack.getItem()).getBlueprintID(pComparedItemStack));
    }

    private boolean checkIfGroupContainsBlueprint(LabelledBlueprintGroup pGroup, IBlueprint pBlueprint) {
        return checkIfGroupContainsBlueprint(pGroup, pBlueprint.getID());
    }

    private boolean checkIfGroupContainsBlueprint(LabelledBlueprintGroup pGroup, String pBlueprintID) {
        for (ItemStack tStoredBlueprintStack : pGroup.Stacks) {
            if (((IBluePrintItem) tStoredBlueprintStack.getItem()).getBlueprintID(tStoredBlueprintStack).equals(pBlueprintID))
                return true;
        }

        return false;
    }

    public float getOperationProgress() {
        return iOperationProgress;
    }

    public void setOperationProgress(float pNewValue) {
        iOperationProgress = pNewValue;
    }

    public OperationMode getOperationMode() {
        return iOpMode;
    }

    public void setOperationMode(OperationMode pNewMode) {
        iOpMode = pNewMode;
    }

    @Override
    public void markDirty() {
        if (!worldObj.isRemote) {
            NetworkManager.INSTANCE.sendToAllAround(new MessageTileEntityBookBinder(this), new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId, (double) this.xCoord, (double) this.yCoord, (double) this.zCoord, 128));
        }

        super.markDirty();
        worldObj.func_147451_t(xCoord, yCoord, zCoord);
    }

    @Override
    public Packet getDescriptionPacket() {
        return NetworkManager.INSTANCE.getPacketFrom(new MessageTileEntityBookBinder(this));
    }

    public enum OperationMode {
        BookBinding,
        Research
    }
}
