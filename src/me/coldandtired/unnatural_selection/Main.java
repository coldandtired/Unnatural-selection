package me.coldandtired.unnatural_selection;

import org.bukkit.Material;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener
{
	@Override
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(this, this);
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEntityEvent event)
	{
		if (!(event.getRightClicked() instanceof Animals)) return;
		
		Player p = event.getPlayer();
		
		if (p.hasPermission("unnatural_selection.can_breed")) return;

		Animals mob = (Animals)event.getRightClicked();
		Material m = p.getItemInHand().getType();
		
		boolean block = false;
		if (mob instanceof Wolf)
		{			
			if (((Wolf)mob).isTamed())
			{
				if (m == Material.RAW_BEEF || m == Material.RAW_CHICKEN || m == Material.RAW_FISH
						|| m == Material.COOKED_BEEF || m == Material.COOKED_CHICKEN ||
						m == Material.COOKED_FISH) block = true;
			}
		}
		else if (mob instanceof Ocelot)
		{
			if (m == Material.RAW_FISH) block = true;
		}
		else if (m == Material.WHEAT) block = true;
		
		if (block)
		{
			mob.setBreed(false);
			event.setCancelled(true);
		}
	}
}
