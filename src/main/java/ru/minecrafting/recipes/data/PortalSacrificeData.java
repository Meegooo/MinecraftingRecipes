package ru.minecrafting.recipes.data;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.S2APacketParticles;

import java.util.HashMap;
import java.util.Iterator;
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

	public static PortalSacrificeData add(EntityPlayerMP player, int x, int y, int z) {
		PortalSacrificeData data = new PortalSacrificeData(x, y, z);
		sacrificeMap.put(player, data);
		return data;
	}

	public static PortalSacrificeData get(EntityPlayerMP player) {
		return sacrificeMap.get(player);
	}

	public static PortalSacrificeData remove(EntityPlayerMP player) {
		return sacrificeMap.remove(player);
	}

	public static void onServerTick() {
		for (Iterator<Map.Entry<EntityPlayerMP, PortalSacrificeData>> iterator = PortalSacrificeData.sacrificeMap.entrySet().iterator();
		     iterator.hasNext(); ) {
			Map.Entry<EntityPlayerMP, PortalSacrificeData> entry = iterator.next();
			PortalSacrificeData value = entry.getValue();
			if (System.currentTimeMillis() - value.sacrificedAt <= 60000) {
				entry.getKey().playerNetServerHandler.sendPacket(new S2APacketParticles("portal",
						value.portalX + 0.5F, value.portalY + 1F, value.portalZ + 0.5F, 0.2F, 0.5F, 0.2F, 0.1F, 10));
			} else
				iterator.remove();
		}
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


}
