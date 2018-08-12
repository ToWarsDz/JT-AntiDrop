package antidrop.towars.vn.Listeners;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.anhcraft.spaciouslib.protocol.ActionBar;
import org.anhcraft.spaciouslib.utils.GameVersion;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.scheduler.BukkitScheduler;

import antidrop.towars.vn.Main;

public class AntiDrop implements Listener {

	public static List<UUID> disabledPlayers = new ArrayList<UUID>();
	public static BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
	public static HashMap<UUID, Integer> players = new HashMap<UUID, Integer>();

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onAntiDropitem(PlayerDropItemEvent e) {
		Player p = e.getPlayer();
		UUID uuid = p.getUniqueId();

		if ((!disabledPlayers.contains(uuid)) && (p.hasPermission("antidrop.use"))) {
			if (!players.containsKey(uuid)) {
				e.setCancelled(true);
				sendall(p, Main.c.getString("xacnhan-vut"), TypeEnum.valueOf(Main.c.getString("ChatType")));
				int id = scheduler.scheduleSyncDelayedTask(Main.plugin, new Runnable() {
					public void run() {
						AntiDrop.players.remove(uuid);
					}
				}, Main.c.getInt("drops-delay"));
				players.put(uuid, Integer.valueOf(id));
			} else {
				scheduler.cancelTask(((Integer) players.get(uuid)).intValue());
				players.remove(uuid);
				sendall(p, Main.c.getString("drops-message"), TypeEnum.valueOf(Main.c.getString("ChatType")));
				if (Main.c.getBoolean("sound-enable")) {
					if (GameVersion.is1_9Above()) {
						p.playSound(p.getLocation(), Sound.valueOf(Main.c.getString("sound.other")), 4f, 5f);
					} else {
						p.playSound(p.getLocation(), Sound.valueOf(Main.c.getString("sound.v1.8")), 4f, 5f);
					}
				}
			}
		}
	}

	private void sendall(Player p, String message, TypeEnum type) {
		switch (type) {
		case MESSAGE:
			Main.chat.sendPlayerNoPrefix(message, p);
			break;
		case ACTIONBAR:
			ActionBar.create(message);
			break;
		default:
			Main.chat.sendPlayer(message, p);
			break;
		}
	}
}