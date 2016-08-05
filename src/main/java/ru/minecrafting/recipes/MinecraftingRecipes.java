package ru.minecrafting.recipes;

import buildcraft.BuildCraftTransport;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import ru.minecrafting.recipes.entity.StaticEntityItem;
import ru.minecrafting.recipes.listeners.EventListener;
import ru.minecrafting.recipes.registers.AspectReg;
import ru.minecrafting.recipes.registers.ItemReg;
import ru.minecrafting.recipes.registers.RecipeReg;
import ru.minecrafting.recipes.registers.ResearchReg;
import ru.minecrafting.recipes.util.LogHelper;
import ru.minecrafting.recipes.util.ServerSyncScheduler;

@Mod(modid = ModInfo.MODID, version = ModInfo.VERSION, name = ModInfo.MOD_NAME, dependencies = "required-after:BuildCraft|Transport@6.4.3; required-after:Thaumcraft@4.2.3.5")
public class MinecraftingRecipes {

	@Mod.Instance(ModInfo.MODID)
	public static MinecraftingRecipes instance;

	public static MinecraftingRecipes getMod() {
		return instance;
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ItemReg.registerItems();
		EntityRegistry.registerModEntity(StaticEntityItem.class, "ItemStatic", 151, MinecraftingRecipes.getMod(), 64, 20, true);
	}

    @EventHandler
    public void init(FMLInitializationEvent event) {
	    MinecraftForge.EVENT_BUS.register(new EventListener());
	    FMLCommonHandler.instance().bus().register(new EventListener());
	    FMLCommonHandler.instance().bus().register(new ServerSyncScheduler());
        registerBuildcraftRecipes();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
	    RecipeReg.registerRecipes();
	    ResearchReg.registerResearch();
	    AspectReg.registerAspects();
    }

    private void registerBuildcraftRecipes() {
	    OreDictionary.registerOre("gravel", new ItemStack(Blocks.gravel));
	    String[] ingredients = new String[10];
	    Item[] results = new Item[10];

	    ingredients[0] = "plankWood";
	    ingredients[1] = "cobblestone";
	    ingredients[2] = "stone";
	    ingredients[3] = "gemDiamond";
	    ingredients[4] = "ingotGold";
	    ingredients[5] = "ingotIron";
	    ingredients[6] = "gemQuartz";
	    ingredients[7] = "cobblestone";
	    ingredients[8] = "gemEmerald";
	    ingredients[9] = "sandstone";

	    results[0] = BuildCraftTransport.pipePowerWood;
	    results[1] = BuildCraftTransport.pipePowerCobblestone;
	    results[2] = BuildCraftTransport.pipePowerStone;
	    results[3] = BuildCraftTransport.pipePowerDiamond;
	    results[4] = BuildCraftTransport.pipePowerGold;
	    results[5] = BuildCraftTransport.pipePowerIron;
	    results[6] = BuildCraftTransport.pipePowerQuartz;
	    results[7] = BuildCraftTransport.pipeStructureCobblestone;
	    results[8] = BuildCraftTransport.pipePowerEmerald;
	    results[9] = BuildCraftTransport.pipePowerSandstone;



	    try {
		    if (ingredients.length != results.length) {
			    throw new ArrayIndexOutOfBoundsException();
		    }
		    for(int n = 0; n < results.length; n++) {
			    String modifier;
			    String name;

			    name = results[n].getUnlocalizedName();
			    if (name.contains("Power"))
			    {
				    modifier = "dustRedstone";
			    }else if(name.contains("Structure"))
			    {
				    modifier = "gravel";
			    }else
			    {
				    modifier = null;
			    }


			    GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(results[n], 8), "rrr", "igi", "rrr", 'r', modifier, 'i', ingredients[n], 'g', "blockGlass"));
		    }
	    }catch (ArrayIndexOutOfBoundsException exception) {
		    LogHelper.error("Cannot register recipes");
		    exception.printStackTrace();
	    }
    }
}
