package com.Orion.Armory.Network.Messages.Config;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

/**
 * Created by Orion
 * Created on 09.06.2015
 * 17:30
 * <p/>
 * Copyrighted according to Project specific license
 */
public class MessageMaterialPropertyValue implements IMessage
{

    public String iMaterialName;
    public String iPropertyName;
    public String[] iParameterTypes;

    public Object[] iServerSidedValue;

    public MessageMaterialPropertyValue(String pMaterialName, String pPropertyName, String[] pParameterTypes, Object[] pServerSidedValue)
    {
        iMaterialName = pMaterialName;
        iPropertyName = pPropertyName;
        iParameterTypes = pParameterTypes;

        iServerSidedValue = pServerSidedValue;
    }

    public MessageMaterialPropertyValue() {}

    @Override
    public void fromBytes(ByteBuf buf) {
        iMaterialName = ByteBufUtils.readUTF8String(buf);
        iPropertyName = ByteBufUtils.readUTF8String(buf);

        int tParaArrayLength = buf.readInt();

        iParameterTypes = new String[tParaArrayLength];

        for (int tPara = 0; tPara < tParaArrayLength; tPara ++) {
            iParameterTypes[tPara] = ByteBufUtils.readUTF8String(buf);
        }

        iServerSidedValue = new Object[tParaArrayLength];

        for (int tPara = 0; tPara < tParaArrayLength; tPara ++) {
            if (iParameterTypes[tPara].equals("String"))
                iServerSidedValue[tPara] = ByteBufUtils.readUTF8String(buf);
            else if(iParameterTypes[tPara].equals("Float"))
                iServerSidedValue[tPara] = buf.readFloat();
            else if(iParameterTypes[tPara].equals("Boolean"))
                iServerSidedValue[tPara] = buf.readBoolean();
            else if(iParameterTypes[tPara].equals("Integer"))
                iServerSidedValue[tPara] = buf.readInt();
            else
                iServerSidedValue[tPara] = null;
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, iMaterialName);
        ByteBufUtils.writeUTF8String(buf, iPropertyName);

        buf.writeInt(iServerSidedValue.length);

        for (String tType : iParameterTypes)
        {
            ByteBufUtils.writeUTF8String(buf, tType);
        }

        for (int tPara = 0; tPara < iParameterTypes.length; tPara ++)
        {
            if (iParameterTypes[tPara].equals("String"))
                ByteBufUtils.writeUTF8String(buf, (String) iServerSidedValue[tPara]);
            else if(iParameterTypes[tPara].equals("Float"))
                buf.writeFloat((Float) iServerSidedValue[tPara]);
            else if(iParameterTypes[tPara].equals("Boolean"))
                buf.writeBoolean((Boolean) iServerSidedValue[tPara]);
            else if(iParameterTypes[tPara].equals("Integer"))
                buf.writeInt((Integer) iServerSidedValue[tPara]);
        }
    }
}
