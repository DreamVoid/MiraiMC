package me.dreamvoid.miraimc.nukkit;

import java.util.MissingResourceException;
import java.util.logging.Logger;

public class NukkitLogger extends Logger {
    private final NukkitPlugin plugin;
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
        this.plugin = plugin;
    }

    @Override
    public void severe(String msg) {
        plugin.getLogger().error(msg);
    }

    @Override
    public void warning(String msg) {
        plugin.getLogger().warning(msg);
    }

    @Override
    public void info(String msg) {
        plugin.getLogger().info(msg);
    }
}
