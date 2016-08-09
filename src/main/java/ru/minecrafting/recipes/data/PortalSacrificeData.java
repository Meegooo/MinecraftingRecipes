package ru.minecrafting.recipes.data;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.S2APacketParticles;
import net.minecraft.world.World;
import ru.minecrafting.recipes.util.WorldUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PortalSacrificeData {
	private static final List<PortalSacrificeData> sacrificeMap = new ArrayList<>();
	private final World world;
	private final int portalX;
	private final int portalY;
	private final int portalZ;
	private final long sacrificedAt;

	private PortalSacrificeData(World world, int portalX, int portalY, int portalZ) {
		this.world = world;
		this.portalX = portalX;
		this.portalY = portalY;
		this.portalZ = portalZ;
		sacrificedAt = System.currentTimeMillis();
	}

	public static PortalSacrificeData add(World world, int x, int y, int z) {
		PortalSacrificeData data = new PortalSacrificeData(world, x, y, z);
		sacrificeMap.add(data);
		return data;
	}

	public static List<PortalSacrificeData> getCopy() {
		return new ArrayList<>(sacrificeMap);
	}

	public static boolean remove(PortalSacrificeData data) {
		return sacrificeMap.remove(data);
	}

	public static void onServerTick() {
		for (Iterator<PortalSacrificeData> iterator = PortalSacrificeData.sacrificeMap.iterator();
		     iterator.hasNext(); ) {
			PortalSacrificeData value = iterator.next();
			int passed = (int) (System.currentTimeMillis() - value.sacrificedAt);
			final int time = 20000;
			if (passed <= time) {
				List<EntityPlayerMP> nearbyPlayers = WorldUtil.getNearbyPlayers(value.world, value.portalX, value.portalY, value.portalZ, 64);
				for (EntityPlayerMP nearbyPlayer : nearbyPlayers) {
					nearbyPlayer.playerNetServerHandler.sendPacket(new S2APacketParticles("portal",
							value.portalX + 0.5F, value.portalY + 1F, value.portalZ + 0.5F, 0.2F, 0.5F, 0.2F, 0.1F, 10 - passed / (time / 10)));
				}
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
