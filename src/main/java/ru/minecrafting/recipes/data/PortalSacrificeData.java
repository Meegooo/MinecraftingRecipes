package ru.minecrafting.recipes.data;

import net.minecraft.entity.player.EntityPlayerMP;

import java.util.HashMap;
import java.util.Map;

public class PortalSacrificeData {
	private static Map<EntityPlayerMP, PortalSacrificeData> sacrificeMap = new HashMap<>();
	private final int portalX;
	private final int portalY;
	private final int portalZ;
	private final long sacrificedAt;

	private PortalSacrificeData(int portalX, int portalY, int portalZ) {
		this.portalX = portalX;
		this.portalY = portalY;
		this.portalZ = portalZ;
		sacrificedAt = System.currentTimeMillis();
	}

	public int getPortalX() {
		return portalX;
	}

	public int getPortalY() {
		return portalY;
	}

	public int getPortalZ() {
		return portalZ;
	}

	public long getSacrificedAt() {
		return sacrificedAt;
	}

	public static PortalSacrificeData add(EntityPlayerMP player, int x, int y, int z) {
		PortalSacrificeData data = new PortalSacrificeData(x, y, z);
		sacrificeMap.put(player, data);
		return data;
	}

	public static PortalSacrificeData get(EntityPlayerMP player) {
		return sacrificeMap.get(player);
	}

}
