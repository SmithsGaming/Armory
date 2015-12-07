package com.SmithsModding.Armory.Common;


import com.SmithsModding.Armory.Armory;
import com.SmithsModding.Armory.Common.Logic.ArmoryInitializer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

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

    }

    public EntityPlayer getPlayer(MessageContext pContext) {
        return pContext.getServerHandler().playerEntity;
    }

}
