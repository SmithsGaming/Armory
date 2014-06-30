package old.Common;


import old.Common.Logic.ArmoryInitializer;
import cpw.mods.fml.relauncher.Side;

/**
 * Created by Orion on 26-4-2014
 *
 * old.Common proxy for Armory
 */
public class ArmoryCommonProxy
{
    public void initializeArmory()
    {
        ArmoryInitializer tInitializer = new ArmoryInitializer();
        tInitializer.iInstance.Initialize(Side.SERVER);
    }
}
