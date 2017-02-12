package com.smithsmodding.armory.api.util.references;

import net.minecraftforge.fml.common.FMLLog;
import org.apache.logging.log4j.Level;
import org.jetbrains.annotations.NotNull;

import static com.smithsmodding.armory.api.util.references.References.General;

/**
 * Author Orion (Created on: 07.07.2016)
 */
public final class ModLogger {

    private static final ModLogger instance = new ModLogger(General.MOD_ID, "Armory-Main");
    private final String loggerName;
    private final String modId;

    public ModLogger(String modId, String loggerName) {
        this.modId = modId;
        this.loggerName = loggerName;
    }

    @NotNull
    public static ModLogger getInstance() {
        return instance;
    }

    public void log(Level logLevel, Object object) {
        FMLLog.log(modId, logLevel,"[" + loggerName +"]-" + String.valueOf(object));
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

    public void log(Level logLevel, @NotNull String message) {
        FMLLog.log(modId, logLevel,"[" + loggerName +"]-" + message);
    }

    public void all(@NotNull String object) {
        log(Level.ALL, object);
    }

    public void debug(@NotNull String object) {
        log(Level.DEBUG, object);
    }

    public void error(@NotNull String object) {
        log(Level.ERROR, object);
    }

    public void fatal(@NotNull String object) {
        log(Level.FATAL, object);
    }

    public void info(@NotNull String object) {
        log(Level.INFO, object);
    }

    public void off(@NotNull String object) {
        log(Level.OFF, object);
    }

    public void trace(@NotNull String object) {
        log(Level.TRACE, object);
    }

    public void warn(@NotNull String object) {
        log(Level.WARN, object);
    }
}
