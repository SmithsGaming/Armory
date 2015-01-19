package com.Orion.Armory.Network.Messages;
/*
 *   MessageFirePitUpdate
 *   Created by: Orion
 *   Created on: 19-1-2015
 */

import com.Orion.Armory.Common.TileEntity.TileEntityArmory;
import com.Orion.Armory.Common.TileEntity.TileEntityFirePit;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class MessageTileEntityFirePit extends MessageTileEntityArmory implements IMessage, IMessageHandler<MessageTileEntityFirePit, IMessage>
{
    protected ItemStack[] iIngotStacks = new ItemStack[TileEntityFirePit.INGOTSTACKS_AMOUNT];
    protected ItemStack[] iFuelStacks = new ItemStack[TileEntityFirePit.FUELSTACK_AMOUNT];

    protected Integer[] iFuelStackBurningTime = new Integer[TileEntityFirePit.FUELSTACK_AMOUNT];
    protected Integer[] iFuelStackFuelAmount = new Integer[TileEntityFirePit.FUELSTACK_AMOUNT];

    protected float iMaxTemperature = 1500;
    protected float iCurrentTemperature = 20;
    protected float iLastAddedHeat = 0;
    protected boolean iIsBurning = false;

    protected TileEntityFirePit iTargetTE;

    public MessageTileEntityFirePit() {}

    public MessageTileEntityFirePit(TileEntityFirePit pEntity)
    {
        super(pEntity);

        this.iIngotStacks = pEntity.iIngotStacks;
        this.iFuelStacks = pEntity.iFuelStacks;
        this.iFuelStackBurningTime = pEntity.iFuelStackBurningTime;
        this.iFuelStackFuelAmount = pEntity.iFuelStackFuelAmount;
        this.iMaxTemperature = pEntity.iMaxTemperature;
        this.iCurrentTemperature = pEntity.iCurrentTemperature;
        this.iLastAddedHeat = pEntity.iLastAddedHeat;
        this.iIsBurning = pEntity.iIsBurning;

        this.iTargetTE = pEntity;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        super.fromBytes(buf);

        iMaxTemperature = (float) buf.readDouble();
        iCurrentTemperature = (float) buf.readDouble();
        iLastAddedHeat = (float) buf.readDouble();
        iIsBurning = buf.readBoolean();

        int tStackAmount  = buf.readInt();
        for(int tCurrentStack = 0; tCurrentStack < tStackAmount; tCurrentStack++)
        {
            int tSlotIndex = buf.readInt();
            iIngotStacks[tSlotIndex] = ByteBufUtils.readItemStack(buf);
        }

        tStackAmount = buf.readInt();
        for(int tCurrentStack = 0; tCurrentStack < tStackAmount; tCurrentStack++)
        {
            int tSlotIndex = buf.readInt();
            iFuelStacks[tSlotIndex] = ByteBufUtils.readItemStack(buf);
            iFuelStackBurningTime[tSlotIndex] = buf.readInt();
            iFuelStackFuelAmount[tSlotIndex] = buf.readInt();
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        super.toBytes(buf);

        buf.writeDouble(iMaxTemperature);
        buf.writeDouble(iCurrentTemperature);
        buf.writeDouble(iLastAddedHeat);
        buf.writeBoolean(iIsBurning);

        buf.writeInt(iTargetTE.getIngotAmount());
        for(int tCurrentStack = 0; tCurrentStack < TileEntityFirePit.INGOTSTACKS_AMOUNT; tCurrentStack++)
        {
            if (iIngotStacks[tCurrentStack] == null) { continue; }
            buf.writeInt(tCurrentStack);
            ByteBufUtils.writeItemStack(buf, iIngotStacks[tCurrentStack]);
        }

        buf.writeInt(TileEntityFirePit.FUELSTACK_AMOUNT);
        for(int tCurrentStack = 0; tCurrentStack < TileEntityFirePit.FUELSTACK_AMOUNT; tCurrentStack++)
        {
            if (iFuelStacks[tCurrentStack] == null)
            {
                buf.writeInt(tCurrentStack);
                ByteBufUtils.writeItemStack(buf, iFuelStacks[tCurrentStack]);
                buf.writeInt(0);
                buf.writeInt(0);
            }
            buf.writeInt(tCurrentStack);
            ByteBufUtils.writeItemStack(buf, iFuelStacks[tCurrentStack]);

            if (iFuelStackBurningTime[tCurrentStack] == null)
            {
                buf.writeInt(0);
            }
            else
            {
                buf.writeInt(iFuelStackBurningTime[tCurrentStack]);
            }

            if( iFuelStackFuelAmount[tCurrentStack] == null)
            {
                buf.writeInt(0);
            }
            else
            {
                buf.writeInt(iFuelStackFuelAmount[tCurrentStack]);
            }
        }
    }

    @Override
    public IMessage onMessage(MessageTileEntityFirePit message, MessageContext ctx) {
        super.onMessage(message, ctx);

        TileEntity tEntity = FMLClientHandler.instance().getClient().theWorld.getTileEntity(message.xCoord, message.yCoord, message.zCoord);
        if (tEntity instanceof TileEntityArmory)
        {
            ((TileEntityFirePit) tEntity).iIngotStacks = iIngotStacks;
            ((TileEntityFirePit) tEntity).iFuelStacks = iFuelStacks;
            ((TileEntityFirePit) tEntity).iFuelStackBurningTime = iFuelStackBurningTime;
            ((TileEntityFirePit) tEntity).iFuelStackFuelAmount = iFuelStackFuelAmount;
            ((TileEntityFirePit) tEntity).iMaxTemperature = iMaxTemperature;
            ((TileEntityFirePit) tEntity).iCurrentTemperature = iCurrentTemperature;
            ((TileEntityFirePit) tEntity).iLastAddedHeat = iLastAddedHeat;
            ((TileEntityFirePit) tEntity).iIsBurning = iIsBurning;
        }

        return null;
    }
}
