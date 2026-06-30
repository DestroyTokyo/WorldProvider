package delta.cion.tokyo.worldprovider.pvp;

import net.minestom.server.entity.Entity;
import net.minestom.server.item.ItemStack;

public class CountDamage {

	public static float countDamage(ItemStack item, Entity unit) {
		return DamageBasics.getDamage(item.material());
	}

}
