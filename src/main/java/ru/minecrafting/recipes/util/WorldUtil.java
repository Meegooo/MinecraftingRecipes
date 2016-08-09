package ru.minecrafting.recipes.util;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.List;

public class WorldUtil {
	public static List<EntityPlayerMP> getNearbyPlayers(World world, double x, double y, double z, double range) {
		return world.getEntitiesWithinAABB(EntityPlayerMP.class, AxisAlignedBB.getBoundingBox(x - range, y - range, z - range, x + range, y + range, z + range));
	}


}
