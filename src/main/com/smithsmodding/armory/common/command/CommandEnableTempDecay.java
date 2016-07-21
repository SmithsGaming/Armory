package com.smithsmodding.armory.common.command;

import com.smithsmodding.armory.api.util.client.TranslationKeys;
import com.smithsmodding.armory.api.util.references.References;
import com.smithsmodding.armory.common.config.ArmoryConfig;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
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
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length != 1)
            throw new WrongUsageException(TranslationKeys.Messages.Commands.TEMPDECAYUSAGE);

        ArmoryConfig.enableTemperatureDecay = parseBoolean(args[0]);
    }
}
