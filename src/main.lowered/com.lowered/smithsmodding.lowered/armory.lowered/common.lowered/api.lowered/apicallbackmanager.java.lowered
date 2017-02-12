package com.smithsmodding.armory.common.api;

import com.smithsmodding.armory.api.IArmoryAPI;
import com.smithsmodding.armory.api.util.references.ModLogger;

import javax.annotation.Nonnull;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Created by marcf on 1/24/2017.
 */
public class APICallbackManager {

    @Nonnull
    private static final HashMap<String, String> requestedAPICallbacks = new HashMap<>();

    @Nonnull
    public static HashMap<String, String> getRequestedAPICallbacks() {
        return requestedAPICallbacks;
    }

    public static void registerAPICallback(String method, String modId) {
        requestedAPICallbacks.put(method, modId);
    }

    public static void callAPIRequests() {
        ModLogger.getInstance().info("Notifying requesters of API init completion.");
        for (String s : getRequestedAPICallbacks().keySet()) {
            callbackRegistration(s, getRequestedAPICallbacks().get(s));
        }
    }

    private static void callbackRegistration(@Nonnull String method, String modname) {
        String[] splitName = method.split("\\.");
        String methodName = splitName[splitName.length - 1];
        String className = method.substring(0, method.length() - methodName.length() - 1);
        ModLogger.getInstance().info(String.format("Trying to reflect %s %s to call the API Callback", className, methodName));
        try {
            Class reflectClass = Class.forName(className);
            Method reflectMethod = reflectClass.getDeclaredMethod(methodName, IArmoryAPI.class);
            reflectMethod.invoke(null, (IArmoryAPI) ArmoryAPI.getInstance());
            ModLogger.getInstance().info(String.format("Success in registering %s", modname));
        } catch (ClassNotFoundException e) {
            ModLogger.getInstance().warn(String.format("Could not find class %s", className));
        } catch (NoSuchMethodException e) {
            ModLogger.getInstance().warn(String.format("Could not find method %s", methodName));
        } catch (Exception e) {
            ModLogger.getInstance().warn(String.format("Exception while trying to access the method : %s", e.toString()));
        }
    }
}
