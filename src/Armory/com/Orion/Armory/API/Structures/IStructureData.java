package com.Orion.Armory.API.Structures;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by Orion
 * Created on 04.07.2015
 * 20:10
 * <p/>
 * Copyrighted according to Project specific license
 */
public interface IStructureData
{

    Object getData(IStructureComponent pRequestingComponent, String pPropertyType);

    void setData(IStructureComponent pSendingComponent, String pPropertyType, Object pData);

    void writeToNBT(NBTTagCompound pTagCompound);

    void readFromNBT(NBTTagCompound pTagCompound);

    void fromBytes(ByteBuf pBuf);

    void toBytes(ByteBuf pBuf);
}
