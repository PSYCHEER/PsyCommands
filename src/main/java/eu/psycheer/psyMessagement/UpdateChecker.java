package eu.psycheer.psyMessagement;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.util.function.Consumer;

public class UpdateChecker {

    PsyMessagement plugin;
    int resID;

    public UpdateChecker(PsyMessagement plugin, int pluginID){
        this.plugin = plugin;
        this.resID = pluginID;
    }

    public void getVersion(final Consumer<String> consumer){
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try(
                    InputStream is = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + resID + "/~").openStream();
                    Scanner scann = new Scanner(is))
            {
                if(scann.hasNext()){
                    consumer.accept(scann.next());
                }
            } catch (IOException e) {
                plugin.getLogger().severe("Unable to check for update: " + e.getMessage());
            }
        });
    }
}
