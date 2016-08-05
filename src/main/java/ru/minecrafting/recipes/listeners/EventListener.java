package ru.minecrafting.recipes.listeners;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S29PacketSoundEffect;
import net.minecraft.network.play.server.S2APacketParticles;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import ru.minecrafting.recipes.entity.StaticEntityItem;
import ru.minecrafting.recipes.registers.ItemReg;
import ru.minecrafting.recipes.util.ServerSyncScheduler;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.entities.monster.EntityEldritchGuardian;
import thaumcraft.common.items.wands.ItemWandCasting;
import thaumcraft.common.items.wands.foci.ItemFocusShock;
import thaumcraft.common.lib.research.ResearchManager;
import thaumcraft.common.tiles.TileEldritchAltar;

import java.util.List;
import java.util.Random;

public class EventListener {

	Random random = new Random();

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
	public void onInteract(final PlayerInteractEvent e) {
		if (!e.world.isRemote && e.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
			TileEntity tile = e.world.getTileEntity(e.x, e.y, e.z);
			ItemStack inUse = e.entityPlayer.getHeldItem();

			if (tile instanceof TileEldritchAltar && inUse != null && inUse.getItem() instanceof ItemWandCasting) {
				TileEldritchAltar tilePortal = ((TileEldritchAltar) tile);
				if (tilePortal.getEyes() == 4) {
					if (!ResearchManager.isResearchComplete(e.entityPlayer.getDisplayName(), "ELDRITCH_KNOWLEDGE")) {

						e.entityPlayer.addChatComponentMessage(new ChatComponentTranslation("§5§o" + StatCollector.translateToLocal("mr.eldritchportal.fail")));
						ServerSyncScheduler.addTask(new ServerSyncScheduler.ScheduledTask(new Runnable() {
							@Override
							public void run() {
								e.entityPlayer.addChatComponentMessage(new ChatComponentTranslation("§5§o" + StatCollector.translateToLocal("mr.eldritchportal.trigger")));
								//Allow Eldritch Knowledge drops
								e.entityPlayer.getEntityData().setBoolean("activatedObelisk", true);
								((EntityPlayerMP) e.entityPlayer).playerNetServerHandler.sendPacket(new S29PacketSoundEffect("thaumcraft:whispers", e.x, e.y, e.z, 1, 1F));
							}
						}, 1200));

						//throw particles at nearby players.
						List<EntityPlayerMP> players = e.world.getEntitiesWithinAABB(EntityPlayerMP.class, AxisAlignedBB.getBoundingBox(e.x - 64, e.y - 64, e.z - 64, e.x + 64, e.y + 64, e.z + 64));
						for (EntityPlayerMP player : players) {
							player.playerNetServerHandler.sendPacket(new S2APacketParticles("magicCrit", e.x + 0.5F, e.y + 1.5F, e.z + 0.5F, 0.2F, 1F, 0.2F, 0.1F, 100));
							player.playerNetServerHandler.sendPacket(new S29PacketSoundEffect("thaumcraft:golemironshoot", e.x, e.y, e.z, 1, 0.7F));
						}
						ItemFocusShock.shootLightning(e.world, e.entityPlayer, e.x, e.y + 3, e.z, true);
						e.entityPlayer.attackEntityFrom(new DamageSource("Eldritch Obelisk"), ((float) (Math.random() * 5)));
						//Move the player
						Vec3 vec = e.entityPlayer.getLookVec();
						final double speed = -Math.random() - 1;
						e.entityPlayer.setVelocity(vec.xCoord * speed, vec.yCoord * speed, vec.zCoord * speed);
						((EntityPlayerMP) e.entityPlayer).playerNetServerHandler.sendPacket(new S12PacketEntityVelocity(e.entityPlayer));
					} else {
						e.setCanceled(true);
						//Drain vis
						ItemWandCasting wand = ((ItemWandCasting) inUse.getItem());
						if (
								wand.consumeVis(inUse, e.entityPlayer, Aspect.ORDER, 10000, true) &&
										wand.consumeVis(inUse, e.entityPlayer, Aspect.ENTROPY, 10000, true) &&
										wand.consumeVis(inUse, e.entityPlayer, Aspect.WATER, 10000, true) &&
										wand.consumeVis(inUse, e.entityPlayer, Aspect.EARTH, 10000, true) &&
										wand.consumeVis(inUse, e.entityPlayer, Aspect.FIRE, 10000, true) &&
										wand.consumeVis(inUse, e.entityPlayer, Aspect.AIR, 10000, true)
								) {
							//Spawn lightning and item
							e.world.addWeatherEffect(new EntityLightningBolt(e.world, e.x, e.y, e.z));
							StaticEntityItem entityItem = new StaticEntityItem(e.world, e.x + 0.5, e.y + 1.1, e.z + 0.5, new ItemStack(ItemReg.MRThaumcraft, 1, 2));
							e.world.spawnEntityInWorld(entityItem);
							//Reset eyes
							tilePortal.setEyes((byte) 0);
							e.world.markBlockForUpdate(tilePortal.xCoord, tilePortal.yCoord, tilePortal.zCoord);
							//Potion
							e.entityPlayer.addPotionEffect(new PotionEffect(9, 200, 2, true));
							e.entityPlayer.addPotionEffect(new PotionEffect(15, 200, 2, true));

							//Move the player
							Vec3 vec = e.entityPlayer.getLookVec();
							final double speed = Math.random() * 0.2 + 0.2;
							e.entityPlayer.setVelocity(vec.xCoord * speed, Math.abs(vec.yCoord * speed * 3), vec.zCoord * speed);
							((EntityPlayerMP) e.entityPlayer).playerNetServerHandler.sendPacket(new S12PacketEntityVelocity(e.entityPlayer));

							//Send sound
							List<EntityPlayerMP> players = e.world.getEntitiesWithinAABB(EntityPlayerMP.class, AxisAlignedBB.getBoundingBox(e.x - 64, e.y - 64, e.z - 64, e.x + 64, e.y + 64, e.z + 64));
							for (EntityPlayerMP player : players) {
								player.playerNetServerHandler.sendPacket(new S29PacketSoundEffect("thaumcraft:rumble", e.x, e.y, e.z, 1, 0.7F));
							}

							//Give player outer research
							Thaumcraft.proxy.getResearchManager().completeResearch(e.entityPlayer, "ENTEROUTER");
							double rand = Math.random();
							int ticks = 6000 + ((int) (rand * 6000));
							ServerSyncScheduler.addTask(new ServerSyncScheduler.ScheduledTask(new Runnable() {
								@Override
								public void run() {
									e.entityPlayer.addChatComponentMessage(new ChatComponentTranslation("§5§o" + StatCollector.translateToLocal("mr.eldritchportal.research")));

									Thaumcraft.proxy.getResearchManager().completeResearch(e.entityPlayer, "OUTERREV");
									((EntityPlayerMP) e.entityPlayer).playerNetServerHandler.sendPacket(new S29PacketSoundEffect("thaumcraft:whispers", e.x, e.y, e.z, 1, 1F));
								}
							}, ticks));
						}
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void onDropsEvent(LivingDropsEvent e) {
		if (e.entityLiving instanceof EntityEldritchGuardian) {
			DamageSource damageSource = e.source;
			if (damageSource instanceof EntityDamageSource) {
				Entity killer = damageSource.getEntity();
				if (killer instanceof EntityPlayerMP) {
					if (!ResearchManager.isResearchComplete(((EntityPlayerMP) killer).getDisplayName(), "OCULUS") &&
							ResearchManager.isResearchComplete(((EntityPlayerMP) killer).getDisplayName(), "ELDRITCHMAJOR")) {
						double i = random.nextDouble()*100;
						if (i<1+e.lootingLevel) {
							e.drops.add(new EntityItem(e.entityLiving.worldObj, e.entityLiving.posX, e.entityLiving.posY, e.entityLiving.posZ,
									new ItemStack(ConfigItems.itemEldritchObject, 1, 1)));
						}
					}
					if (killer.getEntityData().hasKey("activatedObelisk") && killer.getEntityData().getBoolean("activatedObelisk")&&
							!ResearchManager.isResearchComplete(((EntityPlayerMP) killer).getDisplayName(), "ELDRITCH_KNOWLEDGE")) {
						double i = random.nextDouble() * 1000 - e.lootingLevel * 200.0;
						if (i < 200) {
							e.drops.add(new EntityItem(e.entityLiving.worldObj, e.entityLiving.posX, e.entityLiving.posY, e.entityLiving.posZ,
									new ItemStack(ItemReg.MRThaumcraft, 1, 1)));
						}
					}
				}
			}
		}
	}
}
