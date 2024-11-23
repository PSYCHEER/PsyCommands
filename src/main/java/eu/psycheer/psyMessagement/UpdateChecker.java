package eu.psycheer.psyMessagement;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import org.bukkit.Bukkit;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URI;
import java.util.Scanner;
import java.util.function.Consumer;

public class UpdateChecker {

    PsyMessagement plugin;
    private final Gson gson = new Gson();

    public UpdateChecker(PsyMessagement plugin){
        this.plugin = plugin;
    }

    public void getVersionNew(int id) {
        try {
            URL url = new URI("https://api.spigotmc.org/simple/0.2/index.php?action=getResource&id=" + id).toURL();
            try(InputStream is = url.openStream()) {
                Resource resource = gson.fromJson(new InputStreamReader(is), Resource.class);

                if (plugin.getPluginMeta().getVersion().equals(resource.version())) {
                    plugin.getLogger().warning("=================================================");
                    plugin.getLogger().info("No new version.");
                    plugin.getLogger().warning("=================================================");
                }
                else {
                    plugin.getLogger().warning("=================================================");
                    plugin.getLogger().warning("New version found " + resource.version + " !");
                    plugin.getLogger().warning("Current version " + plugin.getPluginMeta().getVersion() + " !");
                    plugin.getLogger().warning("Get it at https://modrinth.com/plugin/psymessagement");
                    plugin.getLogger().warning("=================================================");
                }
            } catch(IOException ex) {
                plugin.getLogger().warning("=================================================");
                plugin.getLogger().warning("Encountered IOException while checking for updates. " + ex.getMessage());
                plugin.getLogger().warning("=================================================");
            }
        } catch(URISyntaxException | MalformedURLException ex) {
            plugin.getLogger().warning("=================================================");
            plugin.getLogger().warning("Received invalid URL. " + ex.getMessage());
            plugin.getLogger().warning("=================================================");
        }
    }

    public record Resource(@SerializedName("current_version") String version){}
}
