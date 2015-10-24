package com.SmithsModding.Armory.Util.Client;
/*
/  SessionVars
/  Created by : Orion
/  Created on : 15/01/2015
*/

public final class SessionVars {

    private static Class iLastOpenenedLedger;

    public static Class getLastOpenenedLedger() {
        return iLastOpenenedLedger;
    }

    public static void setLastOpenenedLedger(Class pLedger) {
        iLastOpenenedLedger = pLedger;
    }
}
