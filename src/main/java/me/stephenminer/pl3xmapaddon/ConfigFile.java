package me.stephenminer.pl3xmapaddon;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ConfigFile {
    private Pl3xmapAddon plugin;
    private String file;

    public ConfigFile(Pl3xmapAddon plugin, String file) {
        this.plugin = plugin;
        this.file = file;
        saveDefaultConfig();
    }

    private FileConfiguration dataConfig = null;

    private File configFile = null;

    public void reloadConfig() {
        if (this.configFile == null)
            this.configFile = new File(this.plugin.getDataFolder(), file + ".yml");
        this.dataConfig = YamlConfiguration.loadConfiguration(this.configFile);

        InputStream defaultStream = this.plugin.getResource(file + ".yml");
        if (defaultStream != null) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
            this.dataConfig.setDefaults(defaultConfig);
        }
    }
    public FileConfiguration getConfig(){
        if (this.dataConfig == null)
            reloadConfig();

        return this.dataConfig;
    }

    public void saveConfig() {
        if (this.dataConfig == null || this.configFile == null)
            return;
        try {
            this.getConfig().save(this.configFile);
        } catch (IOException e) {
            Bukkit.broadcastMessage("COULD NOT SAVE TO CONFIG FILE: " + this.configFile);
        }
    }
    public void saveDefaultConfig(){
        if (this.configFile == null)
            this.configFile = new File(this.plugin.getDataFolder(), file + ".yml");
        if (!this.configFile.exists()){
            this.plugin.saveResource(file + ".yml", false);
        }
    }
}
