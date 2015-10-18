/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.Orion.Armory.Network.Messages;

import com.Orion.Armory.Common.TileEntity.TileEntityBookBinder;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;

public class MessageTileEntityBookBinder extends MessageTileEntityArmory implements IMessage {

    public ItemStack iBindingBookStack;
    public ItemStack iBindingBluePrintStack;

    public ItemStack iResearchingTargetStack;
    public ItemStack iResearchingOutputStack;

    public ItemStack iPaperStack;

    public TileEntityBookBinder.OperationMode iOpMode = TileEntityBookBinder.OperationMode.BookBinding;

    public Float iOperationProgress = 0F;

    public MessageTileEntityBookBinder() {
        super();
    }

    public MessageTileEntityBookBinder(TileEntityBookBinder pEntity) {
        super(pEntity);

        iOpMode = pEntity.getOperationMode();

        if (iOpMode == TileEntityBookBinder.OperationMode.BookBinding) {
            iBindingBookStack = pEntity.getStackInSlot(0);
            iBindingBluePrintStack = pEntity.getStackInSlot(1);
        } else {
            iResearchingTargetStack = pEntity.getStackInSlot(0);
            iResearchingOutputStack = pEntity.getStackInSlot(2);
            iPaperStack = pEntity.getStackInSlot(1);
        }

        iOperationProgress = pEntity.getOperationProgress();

    }

    @Override
    public void fromBytes(ByteBuf buf) {
        super.fromBytes(buf);

        iOpMode = TileEntityBookBinder.OperationMode.values()[buf.readInt()];

        if (iOpMode == TileEntityBookBinder.OperationMode.BookBinding) {
            iBindingBookStack = ByteBufUtils.readItemStack(buf);
            iBindingBluePrintStack = ByteBufUtils.readItemStack(buf);
        } else {
            iResearchingTargetStack = ByteBufUtils.readItemStack(buf);
            iResearchingOutputStack = ByteBufUtils.readItemStack(buf);
            iPaperStack = ByteBufUtils.readItemStack(buf);
        }

        iOperationProgress = buf.readFloat();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        super.toBytes(buf);

        buf.writeInt(iOpMode.ordinal());

        if (iOpMode == TileEntityBookBinder.OperationMode.BookBinding) {
            ByteBufUtils.writeItemStack(buf, iBindingBookStack);
            ByteBufUtils.writeItemStack(buf, iBindingBluePrintStack);
        } else {
            ByteBufUtils.writeItemStack(buf, iResearchingTargetStack);
            ByteBufUtils.writeItemStack(buf, iResearchingOutputStack);
            ByteBufUtils.writeItemStack(buf, iPaperStack);
        }

        buf.writeFloat(iOperationProgress);
    }
}
