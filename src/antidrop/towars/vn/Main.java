package antidrop.towars.vn;

import java.io.File;
import java.io.IOException;

import org.anhcraft.spaciouslib.command.CommandBuilder;
import org.anhcraft.spaciouslib.command.CommandRunnable;
import org.anhcraft.spaciouslib.command.SubCommandBuilder;
import org.anhcraft.spaciouslib.io.DirectoryManager;
import org.anhcraft.spaciouslib.io.FileManager;
import org.anhcraft.spaciouslib.utils.Chat;
import org.apache.commons.io.IOUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import antidrop.towars.vn.GUI.EDroptoggleGUI;
import antidrop.towars.vn.Listeners.AntiDrop;
import antidrop.towars.vn.Listeners.JoinEnable;

public class Main extends JavaPlugin {

	public static Main plugin;
	public static FileConfiguration c;
	public static Chat chat;

	public void onEnable() {
		plugin = this;

		chat = new Chat("&7[&6JT-AntiDrop&7] ");
		PluginManager pluginManager = getServer().getPluginManager();
		pluginManager.registerEvents(new AntiDrop(), this);
		pluginManager.registerEvents(new JoinEnable(), this);

		try {
			new CommandBuilder("antidrop", new CommandRunnable() {

				@Override
				public void run(CommandBuilder cmd, SubCommandBuilder subcmd, CommandSender sender, String[] args,
						String value) {
					for (SubCommandBuilder sb : cmd.getSubCommands()) {
						sender.sendMessage(cmd.getCommandAsString(sb, true));
					}
				}
			}).addSubCommand(new SubCommandBuilder("reload", "&aTải lại config", new CommandRunnable() {

				@Override
				public void run(CommandBuilder cmd, SubCommandBuilder subcmd, CommandSender sender, String[] args,
						String value) {
					if (!sender.hasPermission("antidrop.reload")) {
						chat.sendSender(Main.c.getString("no-permission"), sender);
						return;
					}
					reloadConfigs();
					Main.chat.sendSender(Main.c.getString("reload-message"), sender);
				}
			})).addSubCommand(
					new SubCommandBuilder("toggle", "&aBật tắt chế độ xác nhận vứt vật phẩm", new CommandRunnable() {

						@Override
						public void run(CommandBuilder cmd, SubCommandBuilder subcmd, CommandSender sender,
								String[] args, String value) {
							if (sender instanceof Player) {
								EDroptoggleGUI.getGUI((Player) sender);
							}
						}
					})).buildExecutor(this).clone("ad").buildExecutor(this);
		} catch (Exception e) {
			e.printStackTrace();
		}

		new DirectoryManager("plugins/JT-AntiDrop/").mkdirs();
		try {
			new FileManager("plugins/JT-AntiDrop/config.yml")
					.initFile(IOUtils.toByteArray(getClass().getResourceAsStream("/config.yml")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		reloadConfigs();
	}

	public static void reloadConfigs() {
		c = YamlConfiguration.loadConfiguration(new File("plugins/JT-AntiDrop/config.yml"));
	}

	public void onDisable() {
	}
}