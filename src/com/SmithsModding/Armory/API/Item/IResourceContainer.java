package com.SmithsModding.Armory.API.Item;

import com.SmithsModding.Armory.Util.Client.CustomResource;

/**
 * Created by Orion
 * Created on 01.06.2015
 * 13:06
 * <p/>
 * Copyrighted according to Project specific license
 */
public interface IResourceContainer {
    //Function for registering a new resource the Item may need to render it
    void registerResource(CustomResource pResource);

    //Returns the resource (if registered, else null) depending on the given InternalName (which is registered as its ID)
    CustomResource getResource(String pResourceID);
}
