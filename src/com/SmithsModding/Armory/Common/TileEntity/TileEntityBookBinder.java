/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.SmithsModding.Armory.Common.TileEntity;

import com.SmithsModding.Armory.API.Knowledge.*;
import com.SmithsModding.Armory.Common.Item.Knowledge.LabelledBlueprintGroup;
import com.SmithsModding.Armory.Common.Knowledge.Research.ResearchFailedTreeComponent;
import com.SmithsModding.Armory.Common.Registry.GeneralRegistry;
import com.SmithsModding.Armory.Common.TileEntity.Core.ICustomInputHandler;
import com.SmithsModding.Armory.Common.TileEntity.Core.TileEntityArmory;
import com.SmithsModding.Armory.Network.Messages.MessageTileEntityBookBinder;
import com.SmithsModding.Armory.Network.NetworkManager;
import com.SmithsModding.Armory.Util.Core.ItemStackHelper;
import com.SmithsModding.Armory.Util.References;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.util.StatCollector;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;

public class TileEntityBookBinder extends TileEntityArmory implements IInventory, ICustomInputHandler {

    public ItemStack iBindingBookStack;
    public ItemStack iBindingBluePrintStack;

    public ItemStack iResearchingTargetStack;
    public ItemStack iResearchingOutputStack;

    public ItemStack iPaperStack;
    public ItemStack iFeatherStack;
    public ItemStack iInkPotStack;

    IResearchTreeComponent iCurrentActiveResearchRoot = null;
    ArrayList<IResearchTreeComponent> iCurrentActiveResearchTree = new ArrayList<IResearchTreeComponent>();

    OperationMode iOpMode = OperationMode.BookBinding;

    EntityPlayer iUsingPlayer = null;

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
    public void saveInventory(NBTTagCompound pCompound) {
        pCompound.setInteger(References.NBTTagCompoundData.TE.BookBinder.MODE, iOpMode.ordinal());

        super.saveInventory(pCompound);
    }

    @Override
    public void readInventory(NBTTagCompound pCompound) {
        iOpMode = OperationMode.values()[pCompound.getInteger(References.NBTTagCompoundData.TE.BookBinder.MODE)];

        super.readInventory(pCompound);
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
        if (getOperationMode() == OperationMode.BookBinding) {
            switch (pSlotIndex) {
                case 0:
                    return iBindingBookStack;
                case 1:
                    return iBindingBluePrintStack;
                default:
                    return null;
            }
        } else {
            switch (pSlotIndex) {
                case 0:
                    return iResearchingTargetStack;
                case 1:
                    return iPaperStack;
                case 2:
                    return iResearchingOutputStack;
                default:
                    return null;
            }
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

        if (iOpMode == OperationMode.BookBinding) {
            switch (pSlotIndex) {
                case 0:
                    iBindingBookStack = pStack;
                    break;
                case 1:
                    iBindingBluePrintStack = pStack;
                    break;
                default:
                    break;
            }
        } else {
            switch (pSlotIndex) {
                case 0:
                    iResearchingTargetStack = pStack;
                    if (iResearchingTargetStack != null)
                        HandleCustomInput(References.InternalNames.InputHandlers.BookBinder.INPUTSWITCH, "");
                    break;
                case 1:
                    iPaperStack = pStack;
                    if (iPaperStack == null) {
                        iCurrentActiveResearchTree.clear();
                        iCurrentActiveResearchRoot = null;
                    } else {
                        if (iResearchingTargetStack != null) {
                            HandleCustomInput(References.InternalNames.InputHandlers.BookBinder.INPUTSWITCH, "");
                        }
                    }
                    break;
                case 2:
                    iResearchingOutputStack = pStack;
                    if (iResearchingOutputStack == null)
                        iCurrentActiveResearchTree.clear();
                    break;
                default:
                    break;
            }
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
    public boolean isItemValidForSlot(int pSlotIndex, ItemStack pItemStack) {
        if (iOpMode == OperationMode.BookBinding) {
            switch (pSlotIndex) {
                case 0:
                    return pItemStack.getItem() instanceof IBluePrintContainerItem;
                case 1:
                    return pItemStack.getItem() instanceof IBluePrintItem;
                default:
                    return false;
            }
        } else {
            switch (pSlotIndex) {
                case 0:
                    return true;
                case 1:
                    if (pItemStack.getItem().getUnlocalizedName().equals("item.paper")) return true;

                    for (int tOreID : OreDictionary.getOreIDs(pItemStack)) {
                        GeneralRegistry.iLogger.info(OreDictionary.getOreName(tOreID));
                        if (OreDictionary.getOreName(tOreID).equals("paper")) return true;
                    }

                    return false;
                case 2:
                    return false;
                default:
                    return false;
            }
        }
    }

    @Override
    public Object getGUIComponentRelatedObject(String pComponentID) {
        if (pComponentID.equals(References.InternalNames.GUIComponents.BookBinder.TabResearch.RESEARCHHISTORY)) {
            return iCurrentActiveResearchTree;
        }

        if (pComponentID.contains("enabled") && pComponentID.contains("TabResearchStation.Button.")) {
            return iPaperStack != null && iResearchingTargetStack != null;
        }

        return null;
    }

    @Override
    public void HandleCustomInput(String pInputID, String pInput) {
        if (pInputID.equals(References.InternalNames.InputHandlers.Components.TABCHANGED)) {
            iOpMode = OperationMode.values()[Integer.parseInt(pInput)];
            GeneralRegistry.iLogger.info("Settings Operations Mode to: " + iOpMode.name());
            return;
        }

        if (pInputID.contains("Research")) {
            GeneralRegistry.iLogger.info("Handling Input for: " + pInputID + " for target stack: " + ItemStackHelper.toString(iResearchingTargetStack));

            if (iCurrentActiveResearchRoot == null && iCurrentActiveResearchTree.size() > 0) return;

            if (iCurrentActiveResearchTree.size() > 0) {
                IResearchTreeComponent tLastComponent = iCurrentActiveResearchTree.get(iCurrentActiveResearchTree.size() - 1);
                IResearchTreeComponent tNextComponentWithInput = tLastComponent.getFollowupComponent(iResearchingTargetStack, pInputID, iUsingPlayer);

                if (tNextComponentWithInput == null) {
                    iCurrentActiveResearchRoot = null;
                    iCurrentActiveResearchTree.clear();
                    iCurrentActiveResearchTree.add(new ResearchFailedTreeComponent());
                    return;
                }

                iCurrentActiveResearchTree.add(tNextComponentWithInput);

                if (tNextComponentWithInput.getFollowupTreeComponent().size() == 1) {
                    if (((IResearchTreeComponent) tNextComponentWithInput.getFollowupTreeComponent().values().toArray()[0]).isFinalComponentInBranch()) {
                        iResearchingOutputStack = ((IResearchTreeComponent) tNextComponentWithInput.getFollowupTreeComponent().values().toArray()[0]).getBranchResult(iUsingPlayer);
                        iCurrentActiveResearchRoot = null;
                        GeneralRegistry.iLogger.info("Finished Research for: " + ItemStackHelper.toString(iResearchingOutputStack));
                    }
                }
            } else if (iCurrentActiveResearchRoot != null) {
                iCurrentActiveResearchTree.add(iCurrentActiveResearchRoot.getFollowupComponent(iResearchingTargetStack, pInputID, iUsingPlayer));

                if (iCurrentActiveResearchTree.get(0) == null) {
                    iCurrentActiveResearchRoot = null;
                    iCurrentActiveResearchTree.clear();
                    iCurrentActiveResearchTree.add(new ResearchFailedTreeComponent());
                    return;
                }

                if (iCurrentActiveResearchTree.get(0).getFollowupTreeComponent().size() == 1) {
                    if (((IResearchTreeComponent) iCurrentActiveResearchTree.get(0).getFollowupTreeComponent().values().toArray()[0]).isFinalComponentInBranch()) {
                        iResearchingOutputStack = ((IResearchTreeComponent) iCurrentActiveResearchTree.get(0).getFollowupTreeComponent().values().toArray()[0]).getBranchResult(iUsingPlayer);
                        iCurrentActiveResearchRoot = null;
                        GeneralRegistry.iLogger.info("Finished Research for: " + ItemStackHelper.toString(iResearchingOutputStack));
                    }
                }
            } else {
                iCurrentActiveResearchRoot = KnowledgeRegistry.getInstance().getRootElement(iResearchingTargetStack, pInputID, iUsingPlayer);
                iCurrentActiveResearchTree.add(iCurrentActiveResearchRoot);

                if (iCurrentActiveResearchRoot == null) {
                    iCurrentActiveResearchRoot = null;
                    iCurrentActiveResearchTree.clear();
                    iCurrentActiveResearchTree.add(new ResearchFailedTreeComponent());
                    return;
                }

                if (iCurrentActiveResearchTree.get(0).getFollowupTreeComponent().size() == 1) {
                    if (((IResearchTreeComponent) iCurrentActiveResearchTree.get(0).getFollowupTreeComponent().values().toArray()[0]).isFinalComponentInBranch()) {
                        iResearchingOutputStack = ((IResearchTreeComponent) iCurrentActiveResearchTree.get(0).getFollowupTreeComponent().values().toArray()[0]).getBranchResult(iUsingPlayer);
                        iCurrentActiveResearchRoot = null;
                        GeneralRegistry.iLogger.info("Finished Research for: " + ItemStackHelper.toString(iResearchingOutputStack));
                    }
                }
            }

            return;
        }


    }

    public void validateStacks() {
        if (!(iBindingBluePrintStack.getItem() instanceof IBluePrintItem)) {
            GeneralRegistry.iLogger.error("The Blueprint stack in a Binder was not valid: " + ItemStackHelper.toString(iBindingBluePrintStack) + ". Resetting!");
            iBindingBluePrintStack = null;
        }

        if (!(iBindingBookStack.getItem() instanceof IBluePrintContainerItem)) {
            GeneralRegistry.iLogger.error("The Guide stack in a Binder was not valid: " + ItemStackHelper.toString(iBindingBookStack) + ". Resetting!");
            iBindingBluePrintStack = null;
        }

        if (!(iBindingBluePrintStack.getItem() instanceof IBluePrintItem)) {
            GeneralRegistry.iLogger.error("The Blueprint stack in a Binder was not valid: " + ItemStackHelper.toString(iBindingBluePrintStack) + ". Resetting!");
            iBindingBluePrintStack = null;
        }
    }

    public boolean checkIfGuideContainsBlueprint(ItemStack pBlueprintStack, ItemStack pComparedItemStack) {
        if (!(pBlueprintStack.getItem() instanceof IBluePrintContainerItem))
            return false;

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
