package com.SmithsModding.Armory.Network.Messages;
/*
 *   MessageFirePitUpdate
 *   Created by: Orion
 *   Created on: 19-1-2015
 */

import com.SmithsModding.Armory.API.Structures.IStructureData;
import com.SmithsModding.Armory.Common.TileEntity.FirePit.FirePitStructureData;
import com.SmithsModding.Armory.Common.TileEntity.FirePit.TileEntityFirePit;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;

public class MessageTileEntityFirePit extends MessageTileEntityArmory implements IMessage {
    public ItemStack[] iIngotStacks = new ItemStack[TileEntityFirePit.INGOTSTACKS_AMOUNT];
    public ItemStack[] iFuelStacks = new ItemStack[TileEntityFirePit.FUELSTACK_AMOUNT];

    public float iMaxTemperature;
    public float iCurrentTemperature;
    public float iLastAddedHeat = 0;
    public boolean iIsBurning = false;

    public TileEntityFirePit iTargetTE;
    public IStructureData iData;

    public MessageTileEntityFirePit() {
    }

    public MessageTileEntityFirePit(TileEntityFirePit pEntity) {
        super(pEntity);

        this.iIngotStacks = pEntity.iIngotStacks;
        this.iFuelStacks = pEntity.iFuelStacks;
        this.iMaxTemperature = pEntity.iMaxTemperature;
        this.iCurrentTemperature = pEntity.iCurrentTemperature;
        this.iLastAddedHeat = pEntity.iLastAddedHeat;
        this.iIsBurning = pEntity.iIsBurning;
        this.iData = pEntity.getStructureRelevantData();

        this.iTargetTE = pEntity;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        super.fromBytes(buf);

        iMaxTemperature = buf.readFloat();
        iCurrentTemperature = buf.readFloat();
        iLastAddedHeat = buf.readFloat();
        iIsBurning = buf.readBoolean();

        int tStackAmount = buf.readInt();
        for (int tCurrentStack = 0; tCurrentStack < tStackAmount; tCurrentStack++) {
            int tSlotIndex = buf.readInt();
            iIngotStacks[tSlotIndex] = ByteBufUtils.readItemStack(buf);
        }

        tStackAmount = buf.readInt();
        for (int tCurrentStack = 0; tCurrentStack < tStackAmount; tCurrentStack++) {
            int tSlotIndex = buf.readInt();
            iFuelStacks[tSlotIndex] = ByteBufUtils.readItemStack(buf);
        }

        if (iData == null) {
            iData = new FirePitStructureData();
        }
        iData.fromBytes(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        super.toBytes(buf);

        buf.writeFloat(iMaxTemperature);
        buf.writeFloat(iCurrentTemperature);
        buf.writeFloat(iLastAddedHeat);
        buf.writeBoolean(iIsBurning);

        buf.writeInt(iTargetTE.getIngotAmount());
        for (int tCurrentStack = 0; tCurrentStack < TileEntityFirePit.INGOTSTACKS_AMOUNT; tCurrentStack++) {
            if (iIngotStacks[tCurrentStack] == null) {
                continue;
            }
            buf.writeInt(tCurrentStack);
            ByteBufUtils.writeItemStack(buf, iIngotStacks[tCurrentStack]);
        }

        buf.writeInt(TileEntityFirePit.FUELSTACK_AMOUNT);
        for (int tCurrentStack = 0; tCurrentStack < TileEntityFirePit.FUELSTACK_AMOUNT; tCurrentStack++) {
            buf.writeInt(tCurrentStack);
            ByteBufUtils.writeItemStack(buf, iFuelStacks[tCurrentStack]);
        }

        if (iData == null) {
            iData = new FirePitStructureData();
        }
        iData.toBytes(buf);
    }
}
