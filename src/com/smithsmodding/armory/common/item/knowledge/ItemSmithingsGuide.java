/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.smithsmodding.armory.common.item.knowledge;

import com.smithsmodding.armory.api.knowledge.*;
import com.smithsmodding.armory.common.registry.GeneralRegistry;
import com.smithsmodding.armory.util.References;
import com.smithsmodding.armory.util.client.TranslationKeys;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class ItemSmithingsGuide extends Item implements IBluePrintContainerItem {

    public ItemSmithingsGuide () {
        this.setMaxStackSize(1);
        this.setCreativeTab(CreativeTabs.tabCombat);
        this.setUnlocalizedName(References.InternalNames.Items.ItemSmithingsGuide);
    }

    @Override
    public void onUpdate (ItemStack pStack, World pWorld, Entity pEntity, int pSlotIndex, boolean pSelected) {
        if (pWorld.isRemote)
            return;

        if (pStack.getTagCompound() != null) {
            if (pStack.getTagCompound().hasKey(References.NBTTagCompoundData.Rendering.TicksSinceOpen)) {
                if (pStack.getTagCompound().getInteger(References.NBTTagCompoundData.Rendering.TicksSinceOpen) < 6)
                    pStack.getTagCompound().setInteger(References.NBTTagCompoundData.Rendering.TicksSinceOpen, pStack.getTagCompound().getInteger(References.NBTTagCompoundData.Rendering.TicksSinceOpen) + 1);
            }

            if (pStack.getTagCompound().hasKey(References.NBTTagCompoundData.Rendering.TicksSinceClose)) {
                if (pStack.getTagCompound().getInteger(References.NBTTagCompoundData.Rendering.TicksSinceClose) > 0)
                    pStack.getTagCompound().setInteger(References.NBTTagCompoundData.Rendering.TicksSinceClose, pStack.getTagCompound().getInteger(References.NBTTagCompoundData.Rendering.TicksSinceClose) - 1);
            }
        }

        ArrayList<LabelledBlueprintGroup> tGroups = getBlueprintGroups(pStack);

        for (LabelledBlueprintGroup tGroup : tGroups) {
            for (ItemStack tStack : tGroup.Stacks) {
                if (tStack.getItem() instanceof IBluePrintItem) {
                    IBluePrintItem tItem = (IBluePrintItem) tStack.getItem();

                    String tBlueprintID = tItem.getBlueprintID(pStack);

                    if (tBlueprintID.equals(""))
                        return;

                    IBlueprint tBlueprint = BlueprintRegistry.getInstance().getBlueprint(tBlueprintID);

                    if (tBlueprint == null)
                        return;

                    float tNewBlueprintQuality = tItem.getBluePrintQuality(pStack) - tBlueprint.getQualityDecrementOnTick(false);

                    if (tItem.getBluePrintQuality(pStack) >= tBlueprint.getMinFloatValue() && tItem.getBluePrintQuality(pStack) <= tBlueprint.getMaxFloatValue() && tNewBlueprintQuality >= tBlueprint.getMinFloatValue() && tNewBlueprintQuality <= tBlueprint.getMaxFloatValue())
                        tItem.setBluePrintQuality(pStack, tNewBlueprintQuality);
                }
            }
        }

        writeBlueprintGroupsToStack(pStack, tGroups);
    }

    @Override
    public ItemStack onItemRightClick (ItemStack pStack, World pWorld, EntityPlayer pPlayer) {
        if (pStack.getTagCompound() == null)
            pStack.setTagCompound(new NBTTagCompound());

        if (!( pStack.getTagCompound().hasKey(References.NBTTagCompoundData.Rendering.OpenState) )) {
            pStack.getTagCompound().setBoolean(References.NBTTagCompoundData.Rendering.OpenState, true);
            pStack.getTagCompound().setInteger(References.NBTTagCompoundData.Rendering.TicksSinceOpen, 0);

            return pStack;
        } else {
            if (pStack.getTagCompound().getBoolean(References.NBTTagCompoundData.Rendering.OpenState)) {
                pStack.getTagCompound().removeTag(References.NBTTagCompoundData.Rendering.TicksSinceOpen);
                pStack.getTagCompound().setBoolean(References.NBTTagCompoundData.Rendering.OpenState, false);
                pStack.getTagCompound().setInteger(References.NBTTagCompoundData.Rendering.TicksSinceClose, 5);
            } else {
                pStack.getTagCompound().removeTag(References.NBTTagCompoundData.Rendering.TicksSinceClose);
                pStack.getTagCompound().setBoolean(References.NBTTagCompoundData.Rendering.OpenState, true);
                pStack.getTagCompound().setInteger(References.NBTTagCompoundData.Rendering.TicksSinceOpen, 0);
            }
        }

        return pStack;
    }

    @Override
    public Entity createEntity (World pWorld, Entity pThrowingEntity, ItemStack pStack) {
        if (pStack.getTagCompound() == null)
            pStack.setTagCompound(new NBTTagCompound());

        if (!( pStack.getTagCompound().hasKey(References.NBTTagCompoundData.Rendering.OpenState) )) {
            pStack.getTagCompound().setBoolean(References.NBTTagCompoundData.Rendering.OpenState, false);
            pStack.getTagCompound().setInteger(References.NBTTagCompoundData.Rendering.TicksSinceClose, 0);

            return super.createEntity(pWorld, pThrowingEntity, pStack);
        } else {
            if (pStack.getTagCompound().getBoolean(References.NBTTagCompoundData.Rendering.OpenState)) {
                pStack.getTagCompound().removeTag(References.NBTTagCompoundData.Rendering.TicksSinceOpen);
                pStack.getTagCompound().setBoolean(References.NBTTagCompoundData.Rendering.OpenState, false);
                pStack.getTagCompound().setInteger(References.NBTTagCompoundData.Rendering.TicksSinceClose, 0);
            } else {
                pStack.getTagCompound().setBoolean(References.NBTTagCompoundData.Rendering.OpenState, false);
                pStack.getTagCompound().setInteger(References.NBTTagCompoundData.Rendering.TicksSinceOpen, 0);
            }
        }
        return super.createEntity(pWorld, pThrowingEntity, pStack);
    }

    @Override
    public ArrayList<LabelledBlueprintGroup> getBlueprintGroups (ItemStack pStack) {
        ArrayList<LabelledBlueprintGroup> tGroups = new ArrayList<LabelledBlueprintGroup>();

        if (pStack.getTagCompound() == null)
            initializeContainer(pStack);

        if (!pStack.getTagCompound().hasKey(References.NBTTagCompoundData.Item.SmithingsGuide.GROUPSDATA))
            return tGroups;

        NBTTagList tGroupedNBT = pStack.getTagCompound().getTagList(References.NBTTagCompoundData.Item.SmithingsGuide.GROUPSDATA, 10);
        for (int tTagCounter = 0; tTagCounter < tGroupedNBT.tagCount(); tTagCounter++) {
            NBTTagCompound tGroupCompound = tGroupedNBT.getCompoundTagAt(tTagCounter);
            tGroups.add(new LabelledBlueprintGroup(tGroupCompound));
        }

        return tGroups;
    }

    @Override
    public void addInformation (ItemStack pStack, EntityPlayer pPlayer, List pList, boolean pAdvanced) {
        super.addInformation(pStack, pPlayer, pList, pAdvanced);

        pList.add(I18n.translateToLocal(TranslationKeys.Items.SmithingsGuide.Tooltip1) + " " + getBlueprintGroups(pStack).get(0).Stacks.size() + I18n.translateToLocal(TranslationKeys.Items.SmithingsGuide.Tooltip2));
    }

    @Override
    public void writeBlueprintGroupsToStack (ItemStack pStack, ArrayList<LabelledBlueprintGroup> pGroups) {
        if (pStack.getTagCompound() == null)
            pStack.setTagCompound(new NBTTagCompound());

        NBTTagList tGroupedNBT = new NBTTagList();
        for (LabelledBlueprintGroup tGroup : pGroups) {
            tGroupedNBT.appendTag(tGroup.writeToCompound());
        }

        pStack.getTagCompound().setTag(References.NBTTagCompoundData.Item.SmithingsGuide.GROUPSDATA, tGroupedNBT);
    }

    @Override
    public void getSubItems (Item pItem, CreativeTabs pTab, List pItems) {
        super.getSubItems(pItem, pTab, pItems);

        ItemStack tGuideStack = new ItemStack(GeneralRegistry.Items.guide);
        initializeContainer(tGuideStack);

        pItems.add(tGuideStack);

        /*ItemStack tCheatStack = new ItemStack(GeneralRegistry.Items.guide);
        initializeContainer(tCheatStack);

        ArrayList<ItemStack> tBlueprints = new ArrayList<ItemStack>();
        GeneralRegistry.Items.blueprint.getSubItems(null, null, tBlueprints);

        ArrayList<LabelledBlueprintGroup> tGroups = getBlueprintGroups(tCheatStack);
        tGroups.get(0).Stacks = tBlueprints;

        writeBlueprintGroupsToStack(tCheatStack, tGroups);

        pItems.add(tCheatStack);*/
    }

    @Override
    public boolean getHasSubtypes () {
        return true;
    }

    @Override
    public void initializeContainer (ItemStack pStack) {
        LabelledBlueprintGroup tEmptyGroup = new LabelledBlueprintGroup();
        ArrayList<LabelledBlueprintGroup> tGroups = new ArrayList<LabelledBlueprintGroup>();
        tGroups.add(tEmptyGroup);

        writeBlueprintGroupsToStack(pStack, tGroups);
    }

}
