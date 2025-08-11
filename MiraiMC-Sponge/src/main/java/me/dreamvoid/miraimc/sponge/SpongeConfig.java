package me.dreamvoid.miraimc.sponge;

import me.dreamvoid.miraimc.api.MiraiMC;
import me.dreamvoid.miraimc.interfaces.PluginConfig;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.HashMap;

public class SpongeConfig extends PluginConfig {
    private final SpongePlugin plugin;
    private static HashMap<String, Object> map;

    protected SpongeConfig(SpongePlugin plugin){
        this.plugin = plugin;
        PluginDir = plugin.getDataFolder();
    }

    @Override
    protected String getString(String path, String defaults){
        String[] args = path.split("\\.");
        HashMap<String, Object> maps = map;

        for(int i = 0; i < args.length; i++){
            if(i != args.length - 1) {
                Object o = maps.get(args[i]);

                if(o == null){
                    return defaults;
                }

                if(o instanceof HashMap<?, ?>){
                    //noinspection unchecked
                    maps = (HashMap<String, Object>) o;
                }
            } else {
                Object o = maps.get(args[i]);
                if(o instanceof String) {
                    return (String) o;
                } else if(o == null){
                    return defaults;
                } else {
                    return String.valueOf(o);
                }
            }
        }
        return defaults;
    }

    @Override
    protected int getInt(String path, int defaults){
        String[] args = path.split("\\.");
        HashMap<String, Object> maps = map;

        for(int i = 0; i < args.length; i++){
            if(i != args.length - 1) {
                Object o = maps.get(args[i]);

                if(o == null){
                    return defaults;
                }

                if(o instanceof HashMap<?, ?>){
                    //noinspection unchecked
                    maps = (HashMap<String, Object>) o;
                }
            } else {
                Object o = maps.get(args[i]);
                if(o instanceof Integer){
                    return (int) o;
                } else if (o instanceof String) {
                    return Integer.parseInt((String) o);
                } else if(o == null){
                    return defaults;
                } else {
                    return Integer.parseInt(String.valueOf(o));
                }
            }
        }
        return defaults;
    }

    @Override
    protected long getLong(String path, long defaults){
        String[] args = path.split("\\.");
        HashMap<String, Object> maps = map;

        for(int i = 0; i < args.length; i++){
            if(i != args.length - 1) {
                Object o = maps.get(args[i]);

                if(o == null){
                    return defaults;
                }

                if(o instanceof HashMap<?, ?>){
                    //noinspection unchecked
                    maps = (HashMap<String, Object>) o;
                }
            } else {
                Object o = maps.get(args[i]);
                if(o instanceof Long) {
                    return (long) o;
                } else if(o instanceof String) {
                    return Long.parseLong((String) o);
                } else if(o == null){
                    return defaults;
                } else {
                    return Long.parseLong(String.valueOf(o));
                }
            }
        }
        return defaults;
    }

    @Override
    protected boolean getBoolean(String path, boolean defaults){
        String[] args = path.split("\\.");
        HashMap<String, Object> maps = map;

        for(int i = 0; i < args.length; i++){
            if(i != args.length - 1) {
                Object o = maps.get(args[i]);

                if(o == null){
                    return defaults;
                }

                if(o instanceof HashMap<?, ?>){
                    //noinspection unchecked
                    maps = (HashMap<String, Object>) o;
                }
            } else {
                Object o = maps.get(args[i]);
                if(o instanceof Boolean){
                    return (boolean) o;
                }  else if(o instanceof String) {
                    return Boolean.parseBoolean((String) o);
                } else if(o == null){
                    return defaults;
                } else {
                    return Boolean.parseBoolean(String.valueOf(o));
                }
            }
        }
        return defaults;
    }

    @Override
    protected void saveDefaultConfig() throws IOException {
        if(!MiraiMC.getConfig().PluginDir.exists() && !MiraiMC.getConfig().PluginDir.mkdirs()) throw new RuntimeException("Failed to create data folder!");
        File file = new File(plugin.getDataFolder(), "config.yml");
        if (!file.exists()) {
            try (InputStream is = plugin.getClass().getResourceAsStream("/config.yml")) {
                assert is != null;
                Files.copy(is, file.toPath());
            }
        }

        Yaml yaml = new Yaml();
        //noinspection unchecked
        map = yaml.loadAs(Files.readString(file.toPath()), HashMap.class);
    }
}
