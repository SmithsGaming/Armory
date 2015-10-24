package com.SmithsModding.Armory.Common.Command;

import com.SmithsModding.Armory.Common.Config.ArmoryConfig;
import com.SmithsModding.Armory.Util.Client.TranslationKeys;
import com.SmithsModding.Armory.Util.References;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;

/**
 * Created by marcf on 9/28/2015.
 */
public class CommandEnableTempDecay extends CommandBase {
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public String getCommandName() {
        return References.InternalNames.Commands.ENABLEDECAY;
    }

    @Override
    public String getCommandUsage(ICommandSender pSender) {
        return TranslationKeys.Messages.Commands.TEMPDECAYUSAGE;
    }

    @Override
    public void processCommand(ICommandSender pSender, String[] pArg) {
        if (pArg.length != 2)
            throw new WrongUsageException(TranslationKeys.Messages.Commands.TEMPDECAYUSAGE);

        ArmoryConfig.enableTemperatureDecay = parseBoolean(pSender, pArg[1]);
    }


    protected String[] getPlayers() {
        return MinecraftServer.getServer().getAllUsernames();
    }
}
