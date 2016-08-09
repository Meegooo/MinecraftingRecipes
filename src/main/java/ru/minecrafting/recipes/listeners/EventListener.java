package ru.minecrafting.recipes.listeners;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S29PacketSoundEffect;
import net.minecraft.network.play.server.S2APacketParticles;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import ru.minecrafting.recipes.MinecraftingRecipes;
import ru.minecrafting.recipes.data.PortalSacrificeData;
import ru.minecrafting.recipes.entity.StaticEntityItem;
import ru.minecrafting.recipes.network.PacketLightningBolt;
import ru.minecrafting.recipes.registers.ItemReg;
import ru.minecrafting.recipes.util.ServerSyncScheduler;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.entities.ITaintedMob;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.entities.golems.EntityTravelingTrunk;
import thaumcraft.common.entities.monster.EntityEldritchGuardian;
import thaumcraft.common.items.wands.ItemWandCasting;
import thaumcraft.common.lib.research.ResearchManager;
import thaumcraft.common.tiles.TileEldritchAltar;

import java.util.List;
import java.util.Random;

public class EventListener {

	final Random random = new Random();

	@SubscribeEvent
	public void onItemCrafted(PlayerEvent.ItemCraftedEvent e) {
		if (e.crafting.getItem().equals(ItemReg.MRThaumcraft) &&
				e.crafting.getItemDamage() == 0 &&
				e.craftMatrix.getInventoryName().equals("container.arcaneworkbench")) {
			ItemStack pearl4 = e.craftMatrix.getStackInSlot(4);
			if (pearl4 != null && pearl4.getItem().equals(ConfigItems.itemEldritchObject) && pearl4.getItemDamage() == 3) {
				pearl4.stackSize++;
				e.craftMatrix.setInventorySlotContents(4, pearl4);
			}
			ItemStack pearl7 = e.craftMatrix.getStackInSlot(7);
			if (pearl7 != null && pearl7.getItem().equals(ConfigItems.itemEldritchObject) && pearl7.getItemDamage() == 3) {
				pearl7.stackSize++;
				e.craftMatrix.setInventorySlotContents(7, pearl7);
			}
		}
	}

	@SubscribeEvent
	public void onKill(final LivingDeathEvent e) {
		DamageSource damageSource = e.source;
		if (damageSource instanceof EntityDamageSource) {
			Entity killer = damageSource.getEntity();
			if (killer instanceof EntityPlayerMP) {
				if (e.entity instanceof EntityGolem ||
						e.entity instanceof ITaintedMob ||
						e.entity instanceof EntityTravelingTrunk ||
						e.entity instanceof EntityEnderman ||
						e.entity instanceof EntityWitch) {
					int minX = (int) Math.floor(e.entity.posX - 4);
					int maxX = (int) Math.ceil(e.entity.posX + 4);
					int minY = (int) Math.floor(e.entity.posY - 4);
					int maxY = (int) Math.ceil(e.entity.posY + 4);
					int minZ = (int) Math.floor(e.entity.posZ - 4);
					int maxZ = (int) Math.ceil(e.entity.posZ + 4);
					for (int x = minX; x <= maxX; x++) {
						for (int y = minY; y <= maxY; y++) {
							for (int z = minZ; z <= maxZ; z++) {
								TileEntity tileEntity = e.entity.worldObj.getTileEntity(x, y, z);
								if (tileEntity instanceof TileEldritchAltar &&
										ResearchManager.isResearchComplete(((EntityPlayerMP) killer).getDisplayName(), "ELDRITCH_KNOWLEDGE")) {
									PortalSacrificeData.add((EntityPlayerMP) killer, x, y, z);
									break;
								}
							}
						}
					}
				}
			}
		}
	}

	/* How this works:
		1. Player has to know how to open the portal, altar has to have 4 eyes and player has to have vis in the wand for anything to happen here
		2. Then, if player doesn't know how to open the portal correctly (aka doesn't have eldritch knowledge), we do fail routine
		3. If player knows how to open the portal correctly, but didn't do the sacrifice, we do fail routine
		4. If player knows how to open the portal correctly and did sacrifice, we do success routine.
	 */
	@SuppressWarnings("unchecked")
	@SubscribeEvent
	public void onInteract(final PlayerInteractEvent e) {
		if (!e.world.isRemote && e.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
			TileEntity tile = e.world.getTileEntity(e.x, e.y, e.z);
			ItemStack inUse = e.entityPlayer.getHeldItem();

			if (tile instanceof TileEldritchAltar && inUse != null && inUse.getItem() instanceof ItemWandCasting) {
				ItemWandCasting wand = ((ItemWandCasting) inUse.getItem());
				TileEldritchAltar tilePortal = ((TileEldritchAltar) tile);
				//1.1. Player has to know how to open the portal, altar has to have 4 eyes.
				if (tilePortal.getEyes() == 4 &&
						ResearchManager.isResearchComplete(e.entityPlayer.getDisplayName(), "OCULUS")) {
					PortalSacrificeData sacrificeData = PortalSacrificeData.get((EntityPlayerMP) e.entityPlayer);
					//4. Player knows how to open the portal correctly and did the sacrifice
					if (ResearchManager.isResearchComplete(e.entityPlayer.getDisplayName(), "ELDRITCH_KNOWLEDGE") &&
							sacrificeData != null &&
							sacrificeData.getPortalX() == tile.xCoord &&
							sacrificeData.getPortalY() == tile.yCoord &&
							sacrificeData.getPortalZ() == tile.zCoord) {
						//1.2. If player has all the vis, do the success routine
						if (wand.consumeAllVis(inUse, e.entityPlayer,
								new AspectList().add(Aspect.ORDER, 10000).add(Aspect.ENTROPY, 10000).add(Aspect.WATER, 10000)
										.add(Aspect.EARTH, 10000).add(Aspect.FIRE, 10000).add(Aspect.AIR, 10000),
								true, true)) {
							//We don't open the portal
							e.setCanceled(true);
							//Spawn lightning and item
							e.world.addWeatherEffect(new EntityLightningBolt(e.world, e.x, e.y, e.z));
							StaticEntityItem entityItem = new StaticEntityItem(e.world, e.x + 0.5, e.y + 1.1, e.z + 0.5, new ItemStack(ItemReg.MRThaumcraft, 1, 2));
							e.world.spawnEntityInWorld(entityItem);
							//Reset eyes
							tilePortal.setEyes((byte) 0);
							e.world.markBlockForUpdate(tilePortal.xCoord, tilePortal.yCoord, tilePortal.zCoord);

							//Move the player
							Vec3 vec = e.entityPlayer.getLookVec();
							final double speed = -2.5 + Math.sqrt(e.entityPlayer.getDistance(e.x + 0.5, e.y + 0.5, e.z + 0.5));
							((EntityPlayerMP) e.entityPlayer).motionX = vec.xCoord * speed;
							((EntityPlayerMP) e.entityPlayer).motionY = -0.3 * speed;
							((EntityPlayerMP) e.entityPlayer).motionZ = vec.zCoord * speed;
							((EntityPlayerMP) e.entityPlayer).playerNetServerHandler.sendPacket(new S12PacketEntityVelocity(e.entityPlayer));

							//Send sound
							List<EntityPlayerMP> players = e.world.getEntitiesWithinAABB(EntityPlayerMP.class, AxisAlignedBB.getBoundingBox(e.x - 64, e.y - 64, e.z - 64, e.x + 64, e.y + 64, e.z + 64));
							for (EntityPlayerMP player : players) {
								player.playerNetServerHandler.sendPacket(new S29PacketSoundEffect("thaumcraft:rumble", e.x, e.y, e.z, 1, 0.7F));
							}

							//Give player outer research
							if (!ResearchManager.isResearchComplete(e.entityPlayer.getDisplayName(), "ENDEROUTER"))
								Thaumcraft.proxy.getResearchManager().completeResearch(e.entityPlayer, "ENTEROUTER");
							if (!ResearchManager.isResearchComplete(e.entityPlayer.getDisplayName(), "OUTERREV")) {
								double rand = Math.random();
								int ticks = 6000 + ((int) (rand * 6000));
								ServerSyncScheduler.addTask(new ServerSyncScheduler.ScheduledTask(new Runnable() {
									@Override
									public void run() {
										if (!ResearchManager.isResearchComplete(e.entityPlayer.getDisplayName(), "OUTERREV")) {
											e.entityPlayer.addChatComponentMessage(new ChatComponentTranslation("§5§o" + StatCollector.translateToLocal("mr.eldritchportal.research")));
											Thaumcraft.proxy.getResearchManager().completeResearch(e.entityPlayer, "OUTERREV");
											((EntityPlayerMP) e.entityPlayer).playerNetServerHandler.sendPacket(new S29PacketSoundEffect("thaumcraft:whispers", e.x, e.y, e.z, 1, 1F));
										}
									}
								}, ticks));
							}

							//Stop particles
							PortalSacrificeData.remove((EntityPlayerMP) e.entityPlayer);
						}
						return;
					}

					//If not all conditions are satisfied.
					//1.2 We check for vis
					if (wand.consumeAllVis(inUse, e.entityPlayer,
							new AspectList().add(Aspect.ORDER, 10000).add(Aspect.ENTROPY, 10000).add(Aspect.WATER, 10000)
									.add(Aspect.EARTH, 10000).add(Aspect.FIRE, 10000).add(Aspect.AIR, 10000),
							false, true)) {
						//We cancel the default logic
						e.setCanceled(true);
						//Tell player, that he failed
						e.entityPlayer.addChatComponentMessage(new ChatComponentTranslation("§5§o" + StatCollector.translateToLocal("mr.eldritchportal.fail")));
						//If he never clicked the obelisk, we allow knowledge drops
						if (!(e.entityPlayer.getEntityData().hasKey("activatedObelisk") && e.entityPlayer.getEntityData().getBoolean("activatedObelisk"))) {
							ServerSyncScheduler.addTask(new ServerSyncScheduler.ScheduledTask(new Runnable() {
								@Override
								public void run() {
									e.entityPlayer.addChatComponentMessage(new ChatComponentTranslation("§5§o" + StatCollector.translateToLocal("mr.eldritchportal.trigger")));
									((EntityPlayerMP) e.entityPlayer).playerNetServerHandler.sendPacket(new S29PacketSoundEffect("thaumcraft:whispers", e.x, e.y, e.z, 1, 1F));
								}
							}, 1200));
							e.entityPlayer.getEntityData().setBoolean("activatedObelisk", true);
						}

						//Damage the player
						e.entityPlayer.attackEntityFrom(new DamageSource("Eldritch Obelisk"), ((float) (Math.random() * 5)));

						//Move the player
						Vec3 vec = e.entityPlayer.getLookVec();
						final double speed = -3 + Math.sqrt(e.entityPlayer.getDistance(e.x + 0.5, e.y + 0.5, e.z + 0.5));
						((EntityPlayerMP) e.entityPlayer).motionX = vec.xCoord * speed;
						((EntityPlayerMP) e.entityPlayer).motionY = -0.4 * speed;
						((EntityPlayerMP) e.entityPlayer).motionZ = vec.zCoord * speed;
						((EntityPlayerMP) e.entityPlayer).playerNetServerHandler.sendPacket(new S12PacketEntityVelocity(e.entityPlayer));

						//Drop the eyes
						tilePortal.setEyes((byte) 0);
						e.world.markBlockForUpdate(tilePortal.xCoord, tilePortal.yCoord, tilePortal.zCoord);
						EntityItem item1 = new EntityItem(e.world, e.x, e.y + 0.2, e.z + 1.5, new ItemStack(ConfigItems.itemEldritchObject, 1, 0));
						EntityItem item2 = new EntityItem(e.world, e.x, e.y + 0.2, e.z - 0.5, new ItemStack(ConfigItems.itemEldritchObject, 1, 0));
						EntityItem item3 = new EntityItem(e.world, e.x + 1.5, e.y + 0.2, e.z, new ItemStack(ConfigItems.itemEldritchObject, 1, 0));
						EntityItem item4 = new EntityItem(e.world, e.x - 0.5, e.y + 0.2, e.z, new ItemStack(ConfigItems.itemEldritchObject, 1, 0));
						e.world.spawnEntityInWorld(item1);
						e.world.spawnEntityInWorld(item2);
						e.world.spawnEntityInWorld(item3);
						e.world.spawnEntityInWorld(item4);
						item1.motionX = 0;
						item1.motionY = 0.25;
						item1.motionZ = 0.25;
						item2.motionX = 0;
						item2.motionY = 0.25;
						item2.motionZ = -0.25;
						item3.motionX = 0.25;
						item3.motionY = 0.25;
						item3.motionZ = 0;
						item4.motionX = -0.25;
						item4.motionY = 0.25;
						item4.motionZ = 0;
						item1.velocityChanged = true;
						item2.velocityChanged = true;
						item3.velocityChanged = true;
						item4.velocityChanged = true;

						//throw particles and move items for nearby players.
						List<EntityPlayerMP> players = e.world.getEntitiesWithinAABB(EntityPlayerMP.class, AxisAlignedBB.getBoundingBox(e.x - 64, e.y - 64, e.z - 64, e.x + 64, e.y + 64, e.z + 64));
						for (EntityPlayerMP player : players) {
							player.playerNetServerHandler.sendPacket(new S2APacketParticles("magicCrit", e.x + 0.5F, e.y + 1.5F, e.z + 0.5F, 0.2F, 1F, 0.2F, 0.1F, 100));
							player.playerNetServerHandler.sendPacket(new S29PacketSoundEffect("thaumcraft:golemironshoot", e.x, e.y, e.z, 1, 0.7F));
							player.playerNetServerHandler.sendPacket(new S12PacketEntityVelocity(item1));
							player.playerNetServerHandler.sendPacket(new S12PacketEntityVelocity(item2));
							player.playerNetServerHandler.sendPacket(new S12PacketEntityVelocity(item3));
							player.playerNetServerHandler.sendPacket(new S12PacketEntityVelocity(item4));
							MinecraftingRecipes.getNetwork().sendTo(new PacketLightningBolt(e.world.provider.getDimensionName(),
											e.x + 0.5, e.y + 2.1, e.z + 0.5,
											e.entityPlayer.posX, e.entityPlayer.posY + 1, e.entityPlayer.posZ,
											8, 1, 3, 5, 0.1F),
									player);
						}
					}
				}
			}
		}

	}

	private Vec3 getVecFromPlayer(EntityPlayer living) {
		double px = living.posX;
		double py = living.posY + 1;
		double pz = living.posZ;
		return Vec3.createVectorHelper(px, py, pz);

	}


	@SubscribeEvent
	public void onDropsEvent(LivingDropsEvent e) {
		if (e.entityLiving instanceof EntityEldritchGuardian) {
			DamageSource damageSource = e.source;
			if (damageSource instanceof EntityDamageSource) {
				Entity killer = damageSource.getEntity();
				if (killer instanceof EntityPlayerMP) {
					//If we should drop crimson rites
					if (!ResearchManager.isResearchComplete(((EntityPlayerMP) killer).getDisplayName(), "CRIMSON") &&
							ResearchManager.isResearchComplete(((EntityPlayerMP) killer).getDisplayName(), "ELDRITCHMAJOR")) {
						double i = random.nextDouble() * 20;
						if (i < 1 + e.lootingLevel) {
							e.drops.add(new EntityItem(e.entityLiving.worldObj, e.entityLiving.posX, e.entityLiving.posY, e.entityLiving.posZ,
									new ItemStack(ConfigItems.itemEldritchObject, 1, 1)));
						}
					}
					//If we should drop knowledge fragments
					if (killer.getEntityData().hasKey("activatedObelisk") && killer.getEntityData().getBoolean("activatedObelisk") &&
							!ResearchManager.isResearchComplete(((EntityPlayerMP) killer).getDisplayName(), "ELDRITCH_KNOWLEDGE")) {
						double i = random.nextDouble() * 600 - e.lootingLevel * 200.0;
						if (i < 200) {
							e.drops.add(new EntityItem(e.entityLiving.worldObj, e.entityLiving.posX, e.entityLiving.posY, e.entityLiving.posZ,
									new ItemStack(ItemReg.MRThaumcraft, 1, 1)));
						}
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void onServerTick(TickEvent.ServerTickEvent event) {
		PortalSacrificeData.onServerTick();
	}
}
