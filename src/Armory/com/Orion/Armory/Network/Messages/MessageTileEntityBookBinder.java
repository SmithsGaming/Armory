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

    public TileEntityBookBinder.OperationMode iOpMode = TileEntityBookBinder.OperationMode.BookBinding;

    public Float iOperationProgress = 0F;

    public MessageTileEntityBookBinder() {
        super();
    }

    public MessageTileEntityBookBinder(TileEntityBookBinder pEntity) {
        super(pEntity);

        iBindingBookStack = pEntity.getStackInSlot(0);
        iBindingBluePrintStack = pEntity.getStackInSlot(1);

        iResearchingTargetStack = pEntity.getStackInSlot(2);
        iResearchingOutputStack = pEntity.getStackInSlot(3);

        iOpMode = pEntity.getOperationMode();

        iOperationProgress = pEntity.getOperationProgress();

    }

    @Override
    public void fromBytes(ByteBuf buf) {
        super.fromBytes(buf);

        iBindingBookStack = ByteBufUtils.readItemStack(buf);
        iBindingBluePrintStack = ByteBufUtils.readItemStack(buf);

        iResearchingTargetStack = ByteBufUtils.readItemStack(buf);
        iResearchingOutputStack = ByteBufUtils.readItemStack(buf);

        iOpMode = TileEntityBookBinder.OperationMode.values()[buf.readInt()];

        iOperationProgress = buf.readFloat();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        super.toBytes(buf);

        ByteBufUtils.writeItemStack(buf, iBindingBookStack);
        ByteBufUtils.writeItemStack(buf, iBindingBluePrintStack);
        ByteBufUtils.writeItemStack(buf, iResearchingTargetStack);
        ByteBufUtils.writeItemStack(buf, iResearchingOutputStack);

        buf.writeInt(iOpMode.ordinal());

        buf.writeFloat(iOperationProgress);
    }
}
