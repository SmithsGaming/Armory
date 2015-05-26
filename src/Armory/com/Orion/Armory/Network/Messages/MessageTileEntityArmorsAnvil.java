package com.Orion.Armory.Network.Messages;

import com.Orion.Armory.Common.TileEntity.TileEntityArmorsAnvil;
import com.Orion.Armory.Common.TileEntity.TileEntityFirePit;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import tconstruct.mechworks.logic.TileEntityLandmine;

/**
 * Created by Orion
 * Created on 03.05.2015
 * 16:13
 * <p/>
 * Copyrighted according to Project specific license
 */
public class MessageTileEntityArmorsAnvil extends MessageTileEntityArmory implements IMessage
{
    public ItemStack[] iCraftingStacks = new ItemStack[TileEntityArmorsAnvil.MAX_CRAFTINGSLOTS];
    public ItemStack[] iOutPutStacks = new ItemStack[TileEntityArmorsAnvil.MAX_OUTPUTSLOTS];
    public ItemStack[] iHammerStacks = new ItemStack[TileEntityArmorsAnvil.MAX_HAMMERSLOTS];
    public ItemStack[] iTongStacks = new ItemStack[TileEntityArmorsAnvil.MAX_TONGSLOTS];
    public ItemStack[] iAdditionalCraftingStacks = new ItemStack[TileEntityArmorsAnvil.MAX_ADDITIONALSLOTS];
    public ItemStack[] iCoolStacks = new ItemStack[TileEntityArmorsAnvil.MAX_COOLSLOTS];

    public int iCraftingProgress = 0;

    public MessageTileEntityArmorsAnvil(TileEntityArmorsAnvil pTargetTE)
    {
        super(pTargetTE);

        iCraftingStacks = pTargetTE.iCraftingStacks;
        iOutPutStacks = pTargetTE.iOutPutStacks;
        iHammerStacks = pTargetTE.iHammerStacks;
        iTongStacks = pTargetTE.iTongStacks;
        iAdditionalCraftingStacks = pTargetTE.iAdditionalCraftingStacks;
        iCoolStacks = pTargetTE.iAdditionalCraftingStacks;

        iCraftingProgress = pTargetTE.iCraftingProgress;
    }

    public MessageTileEntityArmorsAnvil() {}


    @Override
    public void fromBytes(ByteBuf buf) {
        super.fromBytes(buf);

        iCraftingProgress = buf.readInt();

        int tCraftingStackAmount  = buf.readInt();
        for(int tCurrentStack = 0; tCurrentStack < tCraftingStackAmount; tCurrentStack++)
        {
            int tSlotIndex = buf.readInt();
            iCraftingStacks[tSlotIndex] = ByteBufUtils.readItemStack(buf);
        }

        int tOutputStackAmount  = buf.readInt();
        for(int tCurrentStack = 0; tCurrentStack < tOutputStackAmount; tCurrentStack++)
        {
            int tSlotIndex = buf.readInt();
            iOutPutStacks[tSlotIndex] = ByteBufUtils.readItemStack(buf);
        }


        int tHammerStackAmount  = buf.readInt();
        for(int tCurrentStack = 0; tCurrentStack < tHammerStackAmount; tCurrentStack++)
        {
            int tSlotIndex = buf.readInt();
            iHammerStacks[tSlotIndex] = ByteBufUtils.readItemStack(buf);
        }

        int tTongStackAmount  = buf.readInt();
        for(int tCurrentStack = 0; tCurrentStack < tTongStackAmount; tCurrentStack++)
        {
            int tSlotIndex = buf.readInt();
            iTongStacks[tSlotIndex] = ByteBufUtils.readItemStack(buf);
        }

        int tAdditionalStacksAmount  = buf.readInt();
        for(int tCurrentStack = 0; tCurrentStack < tAdditionalStacksAmount; tCurrentStack++)
        {
            int tSlotIndex = buf.readInt();
            iAdditionalCraftingStacks[tSlotIndex] = ByteBufUtils.readItemStack(buf);
        }

        int tCoolingStackAmount  = buf.readInt();
        for(int tCurrentStack = 0; tCurrentStack < tCoolingStackAmount; tCurrentStack++)
        {
            int tSlotIndex = buf.readInt();
            iCoolStacks[tSlotIndex] = ByteBufUtils.readItemStack(buf);
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        super.toBytes(buf);

        buf.writeInt(iCraftingProgress);

        buf.writeInt(getCraftingStackAmount());
        for (int tCurrentStack = 0; tCurrentStack < TileEntityArmorsAnvil.MAX_CRAFTINGSLOTS; tCurrentStack++) {
            if (iCraftingStacks[tCurrentStack] == null) {
                continue;
            }
            buf.writeInt(tCurrentStack);
            ByteBufUtils.writeItemStack(buf, iCraftingStacks[tCurrentStack]);
        }

        buf.writeInt(getOutputStackAmount());
        for (int tCurrentStack = 0; tCurrentStack < TileEntityArmorsAnvil.MAX_OUTPUTSLOTS; tCurrentStack++) {
            if (iOutPutStacks[tCurrentStack] == null) {
                continue;
            }
            buf.writeInt(tCurrentStack);
            ByteBufUtils.writeItemStack(buf, iOutPutStacks[tCurrentStack]);
        }

        buf.writeInt(getHammerStackAmount());
        for (int tCurrentStack = 0; tCurrentStack < TileEntityArmorsAnvil.MAX_HAMMERSLOTS; tCurrentStack++) {
            if (iHammerStacks[tCurrentStack] == null) {
                continue;
            }
            buf.writeInt(tCurrentStack);
            ByteBufUtils.writeItemStack(buf, iHammerStacks[tCurrentStack]);
        }

        buf.writeInt(getTongStackAmount());
        for (int tCurrentStack = 0; tCurrentStack < TileEntityArmorsAnvil.MAX_TONGSLOTS; tCurrentStack++) {
            if (iTongStacks[tCurrentStack] == null) {
                continue;
            }
            buf.writeInt(tCurrentStack);
            ByteBufUtils.writeItemStack(buf, iTongStacks[tCurrentStack]);
        }

        buf.writeInt(getAdditionalCraftingStackAmount());
        for (int tCurrentStack = 0; tCurrentStack < TileEntityArmorsAnvil.MAX_ADDITIONALSLOTS; tCurrentStack++) {
            if (iAdditionalCraftingStacks[tCurrentStack] == null) {
                continue;
            }
            buf.writeInt(tCurrentStack);
            ByteBufUtils.writeItemStack(buf, iAdditionalCraftingStacks[tCurrentStack]);
        }

        buf.writeInt(getCoolingStackAmount());
        for (int tCurrentStack = 0; tCurrentStack < TileEntityArmorsAnvil.MAX_COOLSLOTS; tCurrentStack++) {
            if (iCoolStacks[tCurrentStack] == null) {
                continue;
            }
            buf.writeInt(tCurrentStack);
            ByteBufUtils.writeItemStack(buf, iCoolStacks[tCurrentStack]);
        }
    }


    public int getCraftingStackAmount()
    {
        int tCount = 0;
        for(ItemStack tStack: iCraftingStacks)
        {
            if (tStack != null)
            {
                tCount++;
            }
        }

        return tCount;
    }

    public int getOutputStackAmount()
    {
        int tCount = 0;
        for(ItemStack tStack: iOutPutStacks)
        {
            if (tStack != null)
            {
                tCount++;
            }
        }

        return tCount;
    }

    public int getHammerStackAmount()
    {
        int tCount = 0;
        for(ItemStack tStack: iHammerStacks)
        {
            if (tStack != null)
            {
                tCount++;
            }
        }

        return tCount;
    }
    public int getTongStackAmount()
    {
        int tCount = 0;
        for(ItemStack tStack: iTongStacks)
        {
            if (tStack != null)
            {
                tCount++;
            }
        }

        return tCount;
    }

    public int getAdditionalCraftingStackAmount()
    {
        int tCount = 0;
        for(ItemStack tStack: iAdditionalCraftingStacks)
        {
            if (tStack != null)
            {
                tCount++;
            }
        }

        return tCount;
    }

    public int getCoolingStackAmount()
    {
        int tCount = 0;
        for(ItemStack tStack: iCoolStacks)
        {
            if (tStack != null)
            {
                tCount++;
            }
        }

        return tCount;
    }


}


