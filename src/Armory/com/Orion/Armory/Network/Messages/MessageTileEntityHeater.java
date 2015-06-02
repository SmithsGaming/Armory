package com.Orion.Armory.Network.Messages;

import com.Orion.Armory.Common.TileEntity.TileEntityArmory;
import com.Orion.Armory.Common.TileEntity.TileEntityHeater;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;

/**
 * Created by Orion
 * Created on 24.04.2015
 * 18:39
 * <p/>
 * Copyrighted according to Project specific license
 */
public class MessageTileEntityHeater extends MessageTileEntityArmory implements IMessage {
    public ItemStack iFanStack = null;
    public int iItemInSlotTicks = 0;

    public TileEntityArmory iTargetTE;

    public MessageTileEntityHeater() {
    }

    public MessageTileEntityHeater(TileEntityHeater pEntity) {
        super(pEntity);

        this.iFanStack = pEntity.iFanStack;
        this.iItemInSlotTicks = pEntity.iItemInSlotTicks;

        this.iTargetTE = pEntity;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        super.fromBytes(buf);

        iFanStack = ByteBufUtils.readItemStack(buf);
        iItemInSlotTicks = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        super.toBytes(buf);

        ByteBufUtils.writeItemStack(buf, iFanStack);
        buf.writeInt(iItemInSlotTicks);
    }
}
