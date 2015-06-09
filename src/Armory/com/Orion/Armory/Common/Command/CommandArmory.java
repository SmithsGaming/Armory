package com.Orion.Armory.Common.Command;

import com.Orion.Armory.Util.Client.TranslationKeys;
import com.Orion.Armory.Util.References;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Orion
 * Created on 09.06.2015
 * 10:03
 * <p/>
 * Copyrighted according to Project specific license
 */
public class CommandArmory extends CommandBase {

    private static List<CommandBase> modCommands = new ArrayList<CommandBase>();
    private static List<String> commands = new ArrayList<String>();

    @Override
    public String getCommandName()
    {
        return References.InternalNames.Commands.BASECOMMAND;
    }

    @Override
    public String getCommandUsage(ICommandSender pCommandSender)
    {
        return TranslationKeys.Messages.Commands.BASEUSAGE;
    }

    @Override
    public void processCommand(ICommandSender oCommandSender, String[] pArguments)
    {
        if (pArguments.length >= 1)
        {
            for (CommandBase command : modCommands)
            {
                if (command.getCommandName().equalsIgnoreCase(pArguments[0]) && command.canCommandSenderUseCommand(oCommandSender))
                {
                    command.processCommand(oCommandSender, pArguments);
                }
            }
        }
    }

    @Override
    public List addTabCompletionOptions(ICommandSender pCommandSender, String[] pArguments)
    {
        if (pArguments.length == 1)
        {
            return getListOfStringsFromIterableMatchingLastWord(pArguments, commands);
        }
        else if (pArguments.length >= 2)
        {
            for (CommandBase command : modCommands)
            {
                if (command.getCommandName().equalsIgnoreCase(pArguments[0]))
                {
                    return command.addTabCompletionOptions(pCommandSender, pArguments);
                }
            }
        }

        return null;
    }

    static
    {
        modCommands.add(new CommandGiveHeated());

        for (CommandBase commandBase : modCommands)
        {
            commands.add(commandBase.getCommandName());
        }
    }
}
