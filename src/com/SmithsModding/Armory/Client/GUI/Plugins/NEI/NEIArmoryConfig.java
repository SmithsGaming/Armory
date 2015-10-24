package com.SmithsModding.Armory.Client.GUI.Plugins.NEI;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import com.SmithsModding.Armory.Common.Registry.GeneralRegistry;
import com.SmithsModding.Armory.Util.References;

/**
 * Created by Orion
 * Created on 22.04.2015
 * 21:41
 * <p/>
 * Copyrighted according to Project specific license
 */
public class NEIArmoryConfig implements IConfigureNEI {
    @Override
    public void loadConfig() {
        GeneralRegistry.iLogger.info("Initializing NEI Compatibility.");
        API.registerNEIGuiHandler(new NEIGuiTabHandler());
    }

    @Override
    public String getName() {
        return References.General.MOD_ID + " NEI - GUI Handler";
    }

    @Override
    public String getVersion() {
        return References.General.MC_VERSION + "-" + References.General.VERSION;
    }
}
