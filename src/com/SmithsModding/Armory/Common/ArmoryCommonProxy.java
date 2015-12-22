package com.SmithsModding.Armory.Common;


import com.SmithsModding.Armory.*;
import com.SmithsModding.Armory.Common.Handlers.*;
import com.SmithsModding.Armory.Common.Logic.*;
import net.minecraft.entity.player.*;
import net.minecraftforge.fml.common.network.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import net.minecraftforge.fml.relauncher.*;

/**
 * Created by Orion on 26-4-2014
 * <p/>
 * com.Orion.Armory.Common proxy for Armory
 */
public class ArmoryCommonProxy {
    public void preInitializeArmory() {
        Armory.side = Side.SERVER;
    }

    public void initializeArmory() {
        ArmoryInitializer.InitializeServer();
    }

    public void registerEventHandlers() {
        NetworkRegistry.INSTANCE.registerGuiHandler(Armory.instance, new GuiHandler());
    }

    public EntityPlayer getPlayer(MessageContext pContext) {
        return pContext.getServerHandler().playerEntity;
    }

}
