package antidrop.towars.vn.GUI;

import org.anhcraft.spaciouslib.inventory.CenterPosition;
import org.anhcraft.spaciouslib.inventory.ClickableItemHandler;
import org.anhcraft.spaciouslib.inventory.InventoryManager;
import org.anhcraft.spaciouslib.inventory.ItemManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import antidrop.towars.vn.Main;
import antidrop.towars.vn.Listeners.AntiDrop;

public class EDroptoggleGUI {

	public static void getGUI(Player p) {
		InventoryManager inv = new InventoryManager(Main.c.getString("gui.title"), 9);
		inv.fill(new ItemStack(Material.STAINED_GLASS_PANE));

		inv.set(CenterPosition.CENTER_2_A,
				new ItemManager(Main.c.getString("gui.enatitle"), Material.WOOL, 1, (short) 5)
						.setLores(Main.c.getStringList("gui.enalore")).getItem(),
				new ClickableItemHandler() {

					@Override
					public void run(Player p, ItemStack item, ClickType click, int slot, InventoryAction action,
							Inventory inventory) {
						AntiDrop.disabledPlayers.add(p.getUniqueId());
						Main.chat.sendPlayer(Main.c.getString("toggle-enabled"), p);
						p.closeInventory();
					}
				});

		inv.set(CenterPosition.CENTER_2_B,
				new ItemManager(Main.c.getString("gui.distitle"), Material.WOOL, 1, (short) 14)
						.setLores(Main.c.getStringList("gui.dislore")).getItem(),
				new ClickableItemHandler() {

					@Override
					public void run(Player p, ItemStack item, ClickType click, int slot, InventoryAction action,
							Inventory inventory) {
						AntiDrop.disabledPlayers.remove(p.getUniqueId());
						Main.chat.sendPlayer(Main.c.getString("toggle-disabled"), p);
						p.closeInventory();
					}
				});

		inv.open(p);
	}
}