package ru.minecrafting.recipes.proxy;

import net.minecraft.world.World;
import thaumcraft.client.fx.bolt.FXLightningBolt;

public class ClientProxy extends CommonProxy {
	@Override
	public void shootBolt(World world, double x1, double y1, double z1, double x2, double y2, double z2, int duration, float multi, int speed, int type, float width) {
		FXLightningBolt bolt = new FXLightningBolt(world, x1, y1, z1, x2, y2, z2, world.rand.nextLong(), duration, multi, speed);
		bolt.defaultFractal();
		bolt.setType(type);
		bolt.setWidth(width);
		bolt.finalizeBolt();
	}
}
