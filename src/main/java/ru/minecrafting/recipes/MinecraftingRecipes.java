package ru.minecrafting.recipes;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.relauncher.Side;
import net.minecraftforge.common.MinecraftForge;
import ru.minecrafting.recipes.entity.StaticEntityItem;
import ru.minecrafting.recipes.listeners.EventListener;
import ru.minecrafting.recipes.network.PacketLightningBolt;
import ru.minecrafting.recipes.proxy.CommonProxy;
import ru.minecrafting.recipes.registers.AspectReg;
import ru.minecrafting.recipes.registers.ItemReg;
import ru.minecrafting.recipes.registers.RecipeReg;
import ru.minecrafting.recipes.registers.ResearchReg;
import ru.minecrafting.recipes.util.ServerSyncScheduler;

@Mod(modid = ModInfo.MODID, version = ModInfo.VERSION, name = ModInfo.MOD_NAME,
		dependencies = "required-after:BuildCraft|Transport@6.4.3; required-after:Thaumcraft@4.2.3.5")
public class MinecraftingRecipes {

	@Mod.Instance(ModInfo.MODID)
	public static MinecraftingRecipes instance;
	@SidedProxy(
			clientSide = "ru.minecrafting.recipes.proxy.ClientProxy",
			serverSide = "ru.minecrafting.recipes.proxy.CommonProxy"
	)
	private static CommonProxy proxy;
	private static SimpleNetworkWrapper network;


	public static SimpleNetworkWrapper getNetwork() {
		return network;
	}

	public static CommonProxy getProxy() {
		return proxy;
	}

	public static MinecraftingRecipes getMod() {
		return instance;
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		network = NetworkRegistry.INSTANCE.newSimpleChannel("MinecraftingRecipes");
		network.registerMessage(PacketLightningBolt.Handler.class, PacketLightningBolt.class, 0, Side.CLIENT);
		ItemReg.registerItems();
		EntityRegistry.registerModEntity(StaticEntityItem.class, "ItemStatic", 151, MinecraftingRecipes.getMod(), 64, 20, true);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(new EventListener());
		FMLCommonHandler.instance().bus().register(new EventListener());
		FMLCommonHandler.instance().bus().register(new ServerSyncScheduler());
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		RecipeReg.registerRecipes();
		ResearchReg.registerResearch();
		AspectReg.registerAspects();
	}


}
