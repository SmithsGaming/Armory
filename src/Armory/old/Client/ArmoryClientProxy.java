package old.Client;

import old.Common.ArmoryCommonProxy;
import old.Common.Logic.ArmoryInitializer;
import cpw.mods.fml.relauncher.Side;

/**
 * Created by Orion on 26-3-2014.
 */
public class ArmoryClientProxy extends ArmoryCommonProxy
{
    @Override
    public void initializeArmory()
    {
        ArmoryInitializer tInitializer = new ArmoryInitializer();
        tInitializer.iInstance.Initialize(Side.CLIENT);
    }
}
