package cc.isotopestudio.Decrepit;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class DecrepitListener implements Listener {

	private final HashMap<String, Integer> hunger;
	private final Decrepit plugin;

	public DecrepitListener(Decrepit plugin) {
		hunger = new HashMap<String, Integer>();
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerDeath(EntityDeathEvent event) {
		if (!(event.getEntity() instanceof Player))
			return;
		Player player = (Player) event.getEntity();
		hunger.put(player.getName(), player.getFoodLevel());
	}

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		final Player player = event.getPlayer();
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				try {
					player.setHealth(6.0);
					int n = hunger.get(player.getName()) - 6;
					player.setFoodLevel(n < 0 ? 0 : n);
					player.setSaturation(1);
					hunger.remove(player.getName());
				} catch (Exception e) {
					return;
				}
			}
		}, 1);
	}

}
