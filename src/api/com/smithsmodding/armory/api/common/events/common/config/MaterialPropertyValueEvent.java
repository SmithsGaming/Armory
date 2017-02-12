package com.smithsmodding.armory.api.common.events.common.config;

import com.smithsmodding.smithscore.common.events.network.StandardNetworkableEvent;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Orion
 * Created on 09.06.2015
 * 17:30
 *
 * Copyrighted according to Project specific license
 */
public class MaterialPropertyValueEvent extends StandardNetworkableEvent {

    private String materialName;
    private String propertyName;
    private String[] parameterTypes;

    private Object[] serverSidedValue;

    public MaterialPropertyValueEvent(String pMaterialName, String pPropertyName, String[] pParameterTypes, Object[] pServerSidedValue) {
        materialName = pMaterialName;
        propertyName = pPropertyName;
        parameterTypes = pParameterTypes;

        serverSidedValue = pServerSidedValue;
    }

    public MaterialPropertyValueEvent() {
    }

    public String getMaterialName() {
        return materialName;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public String[] getParameterTypes() {
        return parameterTypes;
    }

    public Object[] getServerSidedValue() {
        return serverSidedValue;
    }

    @Override
    public void readFromMessageBuffer(@NotNull ByteBuf buf) {
        materialName = ByteBufUtils.readUTF8String(buf);
        propertyName = ByteBufUtils.readUTF8String(buf);

        int count = buf.readInt();

        parameterTypes = new String[count];

        for (int index = 0; index < count; index++) {
            parameterTypes[index] = ByteBufUtils.readUTF8String(buf);
        }

        serverSidedValue = new Object[count];

        for (int index = 0; index < count; index++) {
            if (parameterTypes[index].equals("String"))
                serverSidedValue[index] = ByteBufUtils.readUTF8String(buf);
            else if (parameterTypes[index].equals("Float"))
                serverSidedValue[index] = buf.readFloat();
            else if (parameterTypes[index].equals("Boolean"))
                serverSidedValue[index] = buf.readBoolean();
            else if (parameterTypes[index].equals("Integer"))
                serverSidedValue[index] = buf.readInt();
            else
                serverSidedValue[index] = null;
        }
    }

    @Override
    public void writeToMessageBuffer(@NotNull ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, materialName);
        ByteBufUtils.writeUTF8String(buf, propertyName);

        buf.writeInt(serverSidedValue.length);

        for (String type : parameterTypes) {
            ByteBufUtils.writeUTF8String(buf, type);
        }

        for (int index = 0; index < parameterTypes.length; index++) {
            if (parameterTypes[index].equals("String"))
                ByteBufUtils.writeUTF8String(buf, (String) serverSidedValue[index]);
            else if (parameterTypes[index].equals("Float"))
                buf.writeFloat((Float) serverSidedValue[index]);
            else if (parameterTypes[index].equals("Boolean"))
                buf.writeBoolean((Boolean) serverSidedValue[index]);
            else if (parameterTypes[index].equals("Integer"))
                buf.writeInt((Integer) serverSidedValue[index]);
        }
    }
}
