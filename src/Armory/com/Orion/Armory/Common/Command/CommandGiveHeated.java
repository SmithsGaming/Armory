package com.Orion.Armory.Common.Command;

import com.Orion.Armory.API.Materials.IArmorMaterial;
import com.Orion.Armory.Common.Factory.HeatedItemFactory;
import com.Orion.Armory.Common.Material.MaterialRegistry;
import com.Orion.Armory.Util.Client.TranslationKeys;
import com.Orion.Armory.Util.References;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.command.server.CommandBanIp;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Orion
 * Created on 09.06.2015
 * 10:12
 * <p/>
 * Copyrighted according to Project specific license
 */
public class CommandGiveHeated extends CommandBase
{
    @Override
    public String getCommandName()
    {
        return References.InternalNames.Commands.GIVEHEATED;
    }

    @Override
    public int getRequiredPermissionLevel()
    {
        return 2;
    }

    @Override
    public String getCommandUsage(ICommandSender pCommandSender)
    {
        return TranslationKeys.Messages.Commands.GIVEHEATEDUSAGE;
    }

    @Override
    public void processCommand(ICommandSender pCommandSender, String[] pArguments)
    {
        if (pArguments.length <= 4)
        {
            throw new WrongUsageException(TranslationKeys.Messages.Commands.GIVEHEATEDUSAGE);
        }
        else
        {
            try
            {
                ItemStack tResultStack = HeatedItemFactory.getInstance().generateHeatedItem(pArguments[2], pArguments[3], Float.parseFloat(pArguments[4]));

                if (tResultStack == null)
                {
                    throw new WrongUsageException(TranslationKeys.Messages.Commands.GIVEHEATEDUSAGE);
                }

                EntityPlayerMP tPlayer = getPlayer(pCommandSender, pArguments[1]);

                EntityItem tEntity = tPlayer.dropPlayerItemWithRandomChoice(tResultStack, false);
                tEntity.delayBeforeCanPickup = 0;
                tEntity.func_145797_a(tPlayer.getCommandSenderName());
            }
            catch (Exception exception)
            {
                throw new WrongUsageException(TranslationKeys.Messages.Commands.GIVEHEATEDUSAGE);
            }
        }
    }

    @Override
    public List addTabCompletionOptions(ICommandSender pCommandSender, String[] pArguments)
    {
        if (pArguments.length == 3)
        {
            return getMaterialCompletionOptions("");
        }
        else if (pArguments.length == 4)
        {
            if (MaterialRegistry.getInstance().getMaterial(pArguments[1]) == null)
                return getMaterialCompletionOptions(pArguments[1]);

            return getTypeCompletionOptions("");
        }
        else if (pArguments.length == 5)
        {
            if (!HeatedItemFactory.getInstance().getAllMappedTypes().contains(pArguments[2]))
                return getTypeCompletionOptions(pArguments[2]);

            return null;
        }

        return null;
    }

    private List getMaterialCompletionOptions(String pMaterialStart)
    {
        ArrayList<String> tTabCompletionOptions = new ArrayList<String>();

        for (IArmorMaterial tMaterial : MaterialRegistry.getInstance().getArmorMaterials().values())
        {
            if ((pMaterialStart.equals("")) || (tMaterial.getInternalMaterialName().contains(pMaterialStart)))
            {
                tTabCompletionOptions.add(tMaterial.getInternalMaterialName());
            }
        }

        return tTabCompletionOptions;
    }

    private List getTypeCompletionOptions(String pTypeStart)
    {
        ArrayList<String> tTabCompletionOptions = new ArrayList<String>();

        for (String tType : HeatedItemFactory.getInstance().getAllMappedTypes())
        {
            if (((pTypeStart.equals("")) || (tType.contains(pTypeStart))) && !tTabCompletionOptions.contains(tType))
            {
                tTabCompletionOptions.add(tType);
            }
        }

        return tTabCompletionOptions;
    }
}
