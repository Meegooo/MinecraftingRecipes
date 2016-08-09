package ru.minecrafting.recipes.network;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import ru.minecrafting.recipes.MinecraftingRecipes;

public class PacketLightningBolt implements IMessage {
	private String world;
	private double x1;
	private double y1;
	private double z1;
	private double x2;
	private double y2;
	private double z2;
	private int duration;
	private float multi;
	private int speed;
	private int type;
	private float width;

	public PacketLightningBolt() {

	}

	public PacketLightningBolt(String world, double x1, double y1, double z1, double x2, double y2, double z2, int duration, float multi, int speed, int type, float width) {
		this.world = world;
		this.x1 = x1;
		this.y1 = y1;
		this.z1 = z1;
		this.x2 = x2;
		this.y2 = y2;
		this.z2 = z2;
		this.duration = duration;
		this.multi = multi;
		this.speed = speed;
		this.type = type;
		this.width = width;
	}


	@Override
	public void fromBytes(ByteBuf buf) {
		world = ByteBufUtils.readUTF8String(buf);
		x1 = buf.readDouble();
		y1 = buf.readDouble();
		z1 = buf.readDouble();
		x2 = buf.readDouble();
		y2 = buf.readDouble();
		z2 = buf.readDouble();
		duration = buf.readInt();
		multi = buf.readFloat();
		speed = buf.readInt();
		type = buf.readInt();
		width = buf.readFloat();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, world);
		buf.writeDouble(x1).writeDouble(y1).writeDouble(z1).
				writeDouble(x2).writeDouble(y2).writeDouble(z2).
				writeInt(duration).writeFloat(multi).writeInt(speed).writeInt(type).writeFloat(width);
	}

	public static class Handler implements IMessageHandler<PacketLightningBolt, IMessage> {
		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(PacketLightningBolt m, MessageContext ctx) {
			MinecraftingRecipes.getProxy().shootBolt(Minecraft.getMinecraft().theWorld,
					m.x1, m.y1, m.z1, m.x2, m.y2, m.z2,
					m.duration, m.multi, m.speed, m.type, m.width);
			return null;
		}
	}
}
