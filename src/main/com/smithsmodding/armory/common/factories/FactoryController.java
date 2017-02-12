package com.smithsmodding.armory.common.factories;

import com.smithsmodding.armory.api.factories.IFactoryController;
import com.smithsmodding.armory.api.factories.IHeatedItemFactory;
import com.smithsmodding.armory.api.factories.IMLAFactory;

/**
 * Created by marcf on 1/12/2017.
 */
public final class FactoryController implements IFactoryController {

    private static final IFactoryController INSTANCE = new FactoryController();

    public static IFactoryController getInstance(){
        return INSTANCE;
    }

    private FactoryController () {}

    @Override
    public IHeatedItemFactory getHeatedItemFactory() {
        return HeatedItemFactory.getInstance();
    }

    @Override
    public IMLAFactory getMLAFactory() {
        return ArmorFactory.getInstance();
    }
}
