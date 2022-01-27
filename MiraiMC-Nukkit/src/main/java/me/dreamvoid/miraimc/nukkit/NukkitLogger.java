package me.dreamvoid.miraimc.nukkit;

import cn.nukkit.plugin.PluginLogger;
import cn.nukkit.utils.LogLevel;

import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.function.Supplier;
import java.util.logging.*;

public class NukkitLogger extends Logger {
    private final PluginLogger logger;
    /**
     * Protected method to construct a logger for a named subsystem.
     * <p>
     * The logger will be initially configured with a null Level
     * and with useParentHandlers set to true.
     *
     * @param name               A name for the logger.  This should
     *                           be a dot-separated name and should normally
     *                           be based on the package name or class name
     *                           of the subsystem, such as java.net
     *                           or javax.swing.  It may be null for anonymous Loggers.
     * @param resourceBundleName name of ResourceBundle to be used for localizing
     *                           messages for this logger.  May be null if none
     *                           of the messages require localization.
     * @throws MissingResourceException if the resourceBundleName is non-null and
     *                                  no corresponding resource can be found.
     */
    protected NukkitLogger(String name, String resourceBundleName, NukkitPlugin plugin) {
        super(name, resourceBundleName);
        this.logger = plugin.getLogger();
    }

    @Override
    public void severe(String msg) {
        logger.error(msg);
    }

    @Override
    public void warning(String msg) {
        logger.warning(msg);
    }

    @Override
    public void info(String msg) {
        logger.info(msg);
    }

    @Override
    public void setFilter(Filter newFilter) throws SecurityException {
        super.setFilter(newFilter);
    }

    @Override
    public void log(LogRecord record) {
        logger.debug("Unsupported method \"log(LogRecord record)\" is being called.");
        if(record.getLevel() != Level.INFO && record.getLevel() != Level.WARNING && record.getLevel() != Level.SEVERE){
            logger.info(record.getMessage());
        } else if(record.getLevel() == Level.SEVERE) {
            logger.error(record.getMessage());
        } else {
            logger.log(LogLevel.valueOf(record.getLevel().getName()), record.getMessage());
        }
    }

    @Override
    public void log(Level level, String msg) {
        logger.debug("Unsupported method \"log(Level level, String msg)\" is being called.");
        if(level != Level.INFO && level != Level.WARNING && level != Level.SEVERE){
            logger.info(msg);
        } else if(level == Level.SEVERE) {
            logger.error(msg);
        } else {
            logger.log(LogLevel.valueOf(level.getName()), msg);
        }
    }

    @Override
    public void log(Level level, Supplier<String> msgSupplier) {
        logger.debug("Unsupported method \"log(Level level, Supplier<String> msgSupplier)\" is being called.");
        if(level != Level.INFO && level != Level.WARNING && level != Level.SEVERE){
            logger.info(msgSupplier.get());
        } else if(level == Level.SEVERE) {
            logger.error(msgSupplier.get());
        } else {
            logger.log(LogLevel.valueOf(level.getName()), msgSupplier.get());
        }
    }

    @Override
    public void log(Level level, String msg, Object param1) {
        logger.debug("Unsupported method \"log(Level level, String msg, Object param1)\" is being called.");
        if(level != Level.INFO && level != Level.WARNING && level != Level.SEVERE){
            logger.info(msg);
        } else if(level == Level.SEVERE) {
            logger.error(msg);
        } else {
            logger.log(LogLevel.valueOf(level.getName()), msg);
        }
    }

    @Override
    public void log(Level level, String msg, Object[] params) {
        logger.debug("Unsupported method \"log(Level level, String msg, Object[] params)\" is being called.");
        if(level != Level.INFO && level != Level.WARNING && level != Level.SEVERE){
            logger.info(msg);
        } else if(level == Level.SEVERE) {
            logger.error(msg);
        } else {
            logger.log(LogLevel.valueOf(level.getName()), msg);
        }
    }

    @Override
    public void log(Level level, String msg, Throwable thrown) {
        logger.debug("Unsupported method \"log(Level level, String msg, Throwable thrown)\" is being called.");
        if(level != Level.INFO && level != Level.WARNING && level != Level.SEVERE){
            logger.info(msg, thrown);
        } else if(level == Level.SEVERE) {
            logger.error(msg,thrown);
        } else {
            logger.log(LogLevel.valueOf(level.getName()), msg,thrown);
        }
    }

    @Override
    public void log(Level level, Throwable thrown, Supplier<String> msgSupplier) {
        logger.debug("Unsupported method \"log(Level level, Throwable thrown, Supplier<String> msgSupplier)\" is being called.");
        if(level != Level.INFO && level != Level.WARNING && level != Level.SEVERE){
            logger.info(msgSupplier.get(), thrown);
        } else if(level == Level.SEVERE) {
            logger.error(msgSupplier.get(),thrown);
        } else {
            logger.log(LogLevel.valueOf(level.getName()), msgSupplier.get(),thrown);
        }
    }

    @Override
    public void logp(Level level, String sourceClass, String sourceMethod, String msg) {
        logger.debug("Unsupported method \"logp(Level level, String sourceClass, String sourceMethod, String msg)\" is being called.");
        if(level != Level.INFO && level != Level.WARNING && level != Level.SEVERE){
            logger.info(msg);
        } else logger.log(LogLevel.valueOf(level.getName()), msg);
    }

    @Override
    public void logp(Level level, String sourceClass, String sourceMethod, Supplier<String> msgSupplier) {
        logger.debug("Unsupported method \"logp(Level level, String sourceClass, String sourceMethod, Supplier<String> msgSupplier)\" is being called.");
        if(level != Level.INFO && level != Level.WARNING && level != Level.SEVERE){
            logger.info(msgSupplier.get());
        } else logger.log(LogLevel.valueOf(level.getName()), msgSupplier.get());
    }

    @Override
    public void logp(Level level, String sourceClass, String sourceMethod, String msg, Object param1) {
        logger.debug("Unsupported method \"logp(Level level, String sourceClass, String sourceMethod, String msg, Object param1)\" is being called.");
        if(level != Level.INFO && level != Level.WARNING && level != Level.SEVERE){
            logger.info(msg);
        } else logger.log(LogLevel.valueOf(level.getName()), msg);
    }

    @Override
    public void logp(Level level, String sourceClass, String sourceMethod, String msg, Object[] params) {
        logger.debug("Unsupported method \"logp(Level level, String sourceClass, String sourceMethod, String msg, Object[] params)\" is being called.");
        if(level != Level.INFO && level != Level.WARNING && level != Level.SEVERE){
            logger.info(msg);
        } else logger.log(LogLevel.valueOf(level.getName()), msg);
    }

    @Override
    public void logp(Level level, String sourceClass, String sourceMethod, String msg, Throwable thrown) {
        logger.debug("Unsupported method \"logp(Level level, String sourceClass, String sourceMethod, String msg, Throwable thrown)\" is being called.");
        if(level != Level.INFO && level != Level.WARNING && level != Level.SEVERE){
            logger.info(msg, thrown);
        } else logger.log(LogLevel.valueOf(level.getName()), msg, thrown);
    }

    @Override
    public void logp(Level level, String sourceClass, String sourceMethod, Throwable thrown, Supplier<String> msgSupplier) {
        logger.debug("Unsupported method \"logp(Level level, String sourceClass, String sourceMethod, Throwable thrown, Supplier<String> msgSupplier)\" is being called.");
        if(level != Level.INFO && level != Level.WARNING && level != Level.SEVERE){
            logger.info(msgSupplier.get(), thrown);
        } else logger.log(LogLevel.valueOf(level.getName()), msgSupplier.get(), thrown);
    }

    @Override
    @Deprecated
    public void logrb(Level level, String sourceClass, String sourceMethod, String bundleName, String msg) {
        logger.debug("Unsupported method \"logrb(Level level, String sourceClass, String sourceMethod, String bundleName, String msg)\" is being called.");
        if(level != Level.INFO && level != Level.WARNING && level != Level.SEVERE){
            logger.info(msg);
        } else logger.log(LogLevel.valueOf(level.getName()), msg);
    }

    @Override
    @Deprecated
    public void logrb(Level level, String sourceClass, String sourceMethod, String bundleName, String msg, Object param1) {
        logger.debug("Unsupported method \"logrb(Level level, String sourceClass, String sourceMethod, String bundleName, String msg, Object param1)\" is being called.");
        if(level != Level.INFO && level != Level.WARNING && level != Level.SEVERE){
            logger.info(msg);
        } else logger.log(LogLevel.valueOf(level.getName()), msg);
    }

    @Override
    @Deprecated
    public void logrb(Level level, String sourceClass, String sourceMethod, String bundleName, String msg, Object[] params) {
        logger.debug("Unsupported method \"logrb(Level level, String sourceClass, String sourceMethod, String bundleName, String msg, Object[] params)\" is being called.");
        if(level != Level.INFO && level != Level.WARNING && level != Level.SEVERE){
            logger.info(msg);
        } else logger.log(LogLevel.valueOf(level.getName()), msg);
    }

    @Override
    public void logrb(Level level, String sourceClass, String sourceMethod, ResourceBundle bundle, String msg, Object... params) {
        logger.debug("Unsupported method \"logrb(Level level, String sourceClass, String sourceMethod, ResourceBundle bundle, String msg, Object... params)\" is being called.");
        if(level != Level.INFO && level != Level.WARNING && level != Level.SEVERE){
            logger.info(msg);
        } else logger.log(LogLevel.valueOf(level.getName()), msg);
    }

    @Override
    public void entering(String sourceClass, String sourceMethod) {
        super.entering(sourceClass, sourceMethod);
    }

    @Override
    public void entering(String sourceClass, String sourceMethod, Object param1) {
        super.entering(sourceClass, sourceMethod, param1);
    }

    @Override
    public void entering(String sourceClass, String sourceMethod, Object[] params) {
        super.entering(sourceClass, sourceMethod, params);
    }

    @Override
    public void exiting(String sourceClass, String sourceMethod) {
        super.exiting(sourceClass, sourceMethod);
    }

    @Override
    public void exiting(String sourceClass, String sourceMethod, Object result) {
        super.exiting(sourceClass, sourceMethod, result);
    }

    @Override
    public void throwing(String sourceClass, String sourceMethod, Throwable thrown) {
        super.throwing(sourceClass, sourceMethod, thrown);
    }

    @Override
    public void config(String msg) {
        logger.debug("Unsupported method \"config(String msg)\" is being called.");
        logger.debug(msg);
    }

    @Override
    public void fine(String msg) {
        logger.debug("Unsupported method \"fine(String msg)\" is being called.");
        logger.debug(msg);
    }

    @Override
    public void finer(String msg) {
        logger.debug("Unsupported method \"finer(String msg)\" is being called.");
        logger.debug(msg);
    }

    @Override
    public void finest(String msg) {
        logger.debug("Unsupported method \"finest(String msg)\" is being called.");
        logger.debug(msg);
    }

    @Override
    public void severe(Supplier<String> msgSupplier) {
        logger.error(msgSupplier.get());
    }

    @Override
    public void warning(Supplier<String> msgSupplier) {
        logger.warning(msgSupplier.get());
    }

    @Override
    public void info(Supplier<String> msgSupplier) {
        logger.info(msgSupplier.get());
    }

    @Override
    public void config(Supplier<String> msgSupplier) {
        logger.debug("Unsupported method \"config(Supplier<String> msgSupplier)\" is being called.");
        logger.debug(msgSupplier.get());
    }

    @Override
    public void fine(Supplier<String> msgSupplier) {
        logger.debug("Unsupported method \"fine(Supplier<String> msgSupplier)\" is being called.");
        logger.debug(msgSupplier.get());
    }

    @Override
    public void finer(Supplier<String> msgSupplier) {
        logger.debug("Unsupported method \"finer(Supplier<String> msgSupplier)\" is being called.");
        logger.debug(msgSupplier.get());
    }

    @Override
    public void finest(Supplier<String> msgSupplier) {
        logger.debug("Unsupported method \"finest(Supplier<String> msgSupplier)\" is being called.");
        logger.debug(msgSupplier.get());
    }

    @Override
    public void setLevel(Level newLevel) throws SecurityException {
        super.setLevel(newLevel);
    }

    @Override
    public boolean isLoggable(Level level) {
        return super.isLoggable(level);
    }

    @Override
    public void addHandler(Handler handler) throws SecurityException {
        super.addHandler(handler);
    }

    @Override
    public void removeHandler(Handler handler) throws SecurityException {
        super.removeHandler(handler);
    }

    @Override
    public void setUseParentHandlers(boolean useParentHandlers) {
        super.setUseParentHandlers(useParentHandlers);
    }

    @Override
    public void setResourceBundle(ResourceBundle bundle) {
        super.setResourceBundle(bundle);
    }

    @Override
    public void setParent(Logger parent) {
        super.setParent(parent);
    }

    @Override
    @Deprecated
    public void logrb(Level level, String sourceClass, String sourceMethod, String bundleName, String msg, Throwable thrown) {
        logger.debug("Unsupported method \"logrb(Level level, String sourceClass, String sourceMethod, String bundleName, String msg, Throwable thrown)\" is being called.");
        if(level != Level.INFO && level != Level.WARNING && level != Level.SEVERE){
            logger.info(msg, thrown);
        } else logger.log(LogLevel.valueOf(level.getName()), msg, thrown);
    }

    @Override
    public void logrb(Level level, String sourceClass, String sourceMethod, ResourceBundle bundle, String msg, Throwable thrown) {
        logger.debug("Unsupported method \"logrb(Level level, String sourceClass, String sourceMethod, ResourceBundle bundle, String msg, Throwable thrown)\" is being called.");
        if(level != Level.INFO && level != Level.WARNING && level != Level.SEVERE){
            logger.info(msg, thrown);
        } else logger.log(LogLevel.valueOf(level.getName()), msg, thrown);
    }
}
