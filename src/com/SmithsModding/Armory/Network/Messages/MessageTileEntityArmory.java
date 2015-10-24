package com.SmithsModding.Armory.Network.Messages;
/*
 *   MessageTileEntityArmory
 *   Created by: Orion
 *   Created on: 19-1-2015
 */

import com.SmithsModding.Armory.Common.TileEntity.Core.TileEntityArmory;
import com.SmithsModding.Armory.Util.Core.ForgeDirectionHelper;
import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class MessageTileEntityArmory {
    public String iName;
    public ForgeDirection iCurrentDirection;
    public int xCoord, yCoord, zCoord;

    public MessageTileEntityArmory(TileEntityArmory pEntity) {
        this.iName = pEntity.getDisplayName();
        this.iCurrentDirection = pEntity.getDirection();
        this.xCoord = pEntity.xCoord;
        this.yCoord = pEntity.yCoord;
        this.zCoord = pEntity.zCoord;
    }

    public MessageTileEntityArmory() {
    }

    public void fromBytes(ByteBuf buf) {
        xCoord = buf.readInt();
        yCoord = buf.readInt();
        zCoord = buf.readInt();

        iName = ByteBufUtils.readUTF8String(buf);
        iCurrentDirection = ForgeDirection.getOrientation(buf.readInt());
    }

    public void toBytes(ByteBuf buf) {
        buf.writeInt(xCoord);
        buf.writeInt(yCoord);
        buf.writeInt(zCoord);

        ByteBufUtils.writeUTF8String(buf, iName);

        buf.writeInt(ForgeDirectionHelper.ConvertToInt(iCurrentDirection));
    }
}
