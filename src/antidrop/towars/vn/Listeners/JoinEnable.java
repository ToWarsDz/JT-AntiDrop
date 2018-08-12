package antidrop.towars.vn.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import antidrop.towars.vn.Main;

public class JoinEnable implements Listener {

	@EventHandler
	public void a(PlayerJoinEvent e) {
		if (Main.c.getBoolean("Join-Xacnhan-Enable")) {
			AntiDrop.disabledPlayers.remove(e.getPlayer().getUniqueId());
		}
	}
}
