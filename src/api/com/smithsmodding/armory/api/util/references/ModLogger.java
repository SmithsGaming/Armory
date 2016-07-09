package com.smithsmodding.armory.api.util.references;

import net.minecraftforge.fml.common.FMLLog;
import org.apache.logging.log4j.Level;

import static com.smithsmodding.armory.api.util.references.References.General;

/**
 * Author Orion (Created on: 07.07.2016)
 */
public final class ModLogger {

    private static final ModLogger instance = new ModLogger(General.MOD_ID);
    private final String modId;

    public ModLogger(String modId) {
        this.modId = modId;
    }

    public static ModLogger getInstance() {
        return instance;
    }

    public void log(Level logLevel, Object object) {
        FMLLog.log(modId, logLevel, String.valueOf(object));
    }

    public void all(Object object) {
        log(Level.ALL, object);
    }

    public void debug(Object object) {
        log(Level.DEBUG, object);
    }

    public void error(Object object) {
        log(Level.ERROR, object);
    }

    public void fatal(Object object) {
        log(Level.FATAL, object);
    }

    public void info(Object object) {
        log(Level.INFO, object);
    }

    public void off(Object object) {
        log(Level.OFF, object);
    }

    public void trace(Object object) {
        log(Level.TRACE, object);
    }

    public void warn(Object object) {
        log(Level.WARN, object);
    }

    //String based logging

    public void log(Level logLevel, String message) {
        FMLLog.log(modId, logLevel, message);
    }

    public void all(String object) {
        log(Level.ALL, object);
    }

    public void debug(String object) {
        log(Level.DEBUG, object);
    }

    public void error(String object) {
        log(Level.ERROR, object);
    }

    public void fatal(String object) {
        log(Level.FATAL, object);
    }

    public void info(String object) {
        log(Level.INFO, object);
    }

    public void off(String object) {
        log(Level.OFF, object);
    }

    public void trace(String object) {
        log(Level.TRACE, object);
    }

    public void warn(String object) {
        log(Level.WARN, object);
    }
}
