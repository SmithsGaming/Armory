/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.SmithsModding.Armory.Common.Command;

import com.SmithsModding.Armory.Util.Client.TranslationKeys;
import com.SmithsModding.Armory.Util.References;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandArmory extends CommandBase {

    private static List<CommandBase> modCommands = new ArrayList<CommandBase>();
    private static List<String> commands = new ArrayList<String>();

    static {
        modCommands.add(new CommandGiveHeated());

        modCommands.add(new CommandEnableTempDecay());

        for (CommandBase commandBase : modCommands) {
            commands.add(commandBase.getCommandName());
        }
    }

    @Override
    public String getCommandName() {
        return References.InternalNames.Commands.BASECOMMAND;
    }

    @Override
    public String getCommandUsage(ICommandSender pCommandSender) {
        return TranslationKeys.Messages.Commands.BASEUSAGE;
    }

    @Override
    public void processCommand(ICommandSender oCommandSender, String[] pArguments) {
        if (pArguments.length >= 1) {
            for (CommandBase command : modCommands) {
                if (command.getCommandName().equalsIgnoreCase(pArguments[0]) && command.canCommandSenderUseCommand(oCommandSender)) {
                    command.processCommand(oCommandSender, pArguments);
                }
            }
        }
    }

    @Override
    public List addTabCompletionOptions(ICommandSender pCommandSender, String[] pArguments) {
        if (pArguments.length == 1) {
            return getListOfStringsFromIterableMatchingLastWord(pArguments, commands);
        } else if (pArguments.length >= 2) {
            for (CommandBase command : modCommands) {
                if (command.getCommandName().equalsIgnoreCase(pArguments[0])) {
                    return command.addTabCompletionOptions(pCommandSender, Arrays.copyOfRange(pArguments, 1, pArguments.length));
                }
            }
        }

        return null;
    }

    public String[] getPlayers() {
        return MinecraftServer.getServer().getAllUsernames();
    }
}
