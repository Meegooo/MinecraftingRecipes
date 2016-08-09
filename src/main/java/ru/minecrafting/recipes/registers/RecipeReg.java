package ru.minecrafting.recipes.registers;

import buildcraft.BuildCraftTransport;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import ru.minecrafting.recipes.util.LogHelper;
import tconstruct.world.TinkerWorld;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.crafting.ShapedArcaneRecipe;
import thaumcraft.api.crafting.ShapelessArcaneRecipe;
import thaumcraft.common.config.ConfigItems;

public class RecipeReg {
	public static InfusionRecipe pearl_infusion;
	public static ShapedArcaneRecipe infused_blood;
	public static ShapelessArcaneRecipe blood_cloth;
	public static ShapelessArcaneRecipe blood_ingot;
	public static IRecipe cleric_helmet;
	public static IRecipe cleric_chest;
	public static IRecipe cleric_leggings;
	public static IRecipe knight_helmet;
	public static IRecipe knight_chest;
	public static IRecipe knight_leggings;
	public static IRecipe crimson_boots;
	public static InfusionRecipe praetor_helmet;
	public static InfusionRecipe praetor_chest;
	public static InfusionRecipe praetor_leggings;
	public static InfusionRecipe crimson_sword;

	public static void registerRecipes() {
		registerVanillaRecipes();
		registerInfusionRecipes();
		registerArcaneRecipes();
	}

	private static void registerVanillaRecipes() {
		registerBuildcraftRecipes();
		GameRegistry.addShapedRecipe(new ItemStack(ItemReg.eldritchResearchNotes, 1, 42), "rrr", "rrr", "rrr", 'r', new ItemStack(ItemReg.MRThaumcraft, 1, 1));
		cleric_helmet = GameRegistry.addShapedRecipe(new ItemStack(ConfigItems.itemHelmetCultistRobe), "r r", "rrr", "rrr", 'r', new ItemStack(ItemReg.MRThaumcraft, 1, 3));
		cleric_chest = GameRegistry.addShapedRecipe(new ItemStack(ConfigItems.itemChestCultistRobe), "rrr", "r r", "r r", 'r', new ItemStack(ItemReg.MRThaumcraft, 1, 3));
		cleric_leggings = GameRegistry.addShapedRecipe(new ItemStack(ConfigItems.itemLegsCultistRobe), "rrr", "r r", 'r', new ItemStack(ItemReg.MRThaumcraft, 1, 3));
		knight_helmet = GameRegistry.addShapedRecipe(new ItemStack(ConfigItems.itemHelmetCultistPlate), "r r", "rrr", "rrr", 'r', new ItemStack(ItemReg.MRThaumcraft, 1, 4));
		knight_chest = GameRegistry.addShapedRecipe(new ItemStack(ConfigItems.itemChestCultistPlate), "rrr", "r r", "r r", 'r', new ItemStack(ItemReg.MRThaumcraft, 1, 4));
		knight_leggings = GameRegistry.addShapedRecipe(new ItemStack(ConfigItems.itemLegsCultistPlate), "rrr", "r r", 'r', new ItemStack(ItemReg.MRThaumcraft, 1, 4));
		crimson_boots = GameRegistry.addShapedRecipe(new ItemStack(ConfigItems.itemBootsCultist), "r r", "r r", 'r', new ItemStack(ItemReg.MRThaumcraft, 1, 3));

	}


	private static void registerInfusionRecipes() {
		pearl_infusion = ThaumcraftApi.addInfusionCraftingRecipe("PEARL_CREATION", new ItemStack(ConfigItems.itemEldritchObject, 1, 3),
				4, new AspectList().add(Aspect.ORDER, 8).add(Aspect.ELDRITCH, 16).add(Aspect.ENERGY, 32).add(Aspect.MAGIC, 64).add(Aspect.ENTROPY, 8),
				new ItemStack(ItemReg.MRThaumcraft, 1, 2),
				new ItemStack[]{
						new ItemStack(Items.nether_star, 1),
						new ItemStack(ConfigItems.itemResource, 1, 14),
						new ItemStack(Items.ender_pearl, 1),
						new ItemStack(ConfigItems.itemResource, 1, 14),
						new ItemStack(ConfigItems.itemResource, 1, 1),
						new ItemStack(ConfigItems.itemResource, 1, 14),
						new ItemStack(Items.ender_pearl, 1),
						new ItemStack(ConfigItems.itemResource, 1, 14)
				});
		praetor_helmet = ThaumcraftApi.addInfusionCraftingRecipe("CRIMSON_PRAETOR_ARMOR", new ItemStack(ConfigItems.itemHelmetCultistLeaderPlate),
				2, new AspectList().add(Aspect.CLOTH, 8).add(Aspect.ELDRITCH, 16).add(Aspect.ARMOR, 16).add(Aspect.METAL, 8),
				new ItemStack(ConfigItems.itemHelmetCultistPlate),
				new ItemStack[]{
						new ItemStack(ItemReg.MRThaumcraft, 1, 4),
						new ItemStack(ConfigItems.itemResource, 1, 17),
						new ItemStack(ItemReg.MRThaumcraft, 1, 3),
						new ItemStack(ConfigItems.itemResource, 1, 17)
				});
		praetor_chest = ThaumcraftApi.addInfusionCraftingRecipe("CRIMSON_PRAETOR_ARMOR", new ItemStack(ConfigItems.itemChestCultistLeaderPlate),
				2, new AspectList().add(Aspect.CLOTH, 8).add(Aspect.ELDRITCH, 16).add(Aspect.ARMOR, 16).add(Aspect.METAL, 8),
				new ItemStack(ConfigItems.itemChestCultistPlate),
				new ItemStack[]{
						new ItemStack(ItemReg.MRThaumcraft, 1, 4),
						new ItemStack(ConfigItems.itemResource, 1, 17),
						new ItemStack(ItemReg.MRThaumcraft, 1, 3),
						new ItemStack(ConfigItems.itemResource, 1, 17)
				});
		praetor_leggings = ThaumcraftApi.addInfusionCraftingRecipe("CRIMSON_PRAETOR_ARMOR", new ItemStack(ConfigItems.itemLegsCultistLeaderPlate),
				2, new AspectList().add(Aspect.CLOTH, 8).add(Aspect.ELDRITCH, 16).add(Aspect.ARMOR, 16).add(Aspect.METAL, 8),
				new ItemStack(ConfigItems.itemLegsCultistPlate),
				new ItemStack[]{
						new ItemStack(ItemReg.MRThaumcraft, 1, 4),
						new ItemStack(ConfigItems.itemResource, 1, 17),
						new ItemStack(ItemReg.MRThaumcraft, 1, 3),
						new ItemStack(ConfigItems.itemResource, 1, 17)
				});

		crimson_sword = ThaumcraftApi.addInfusionCraftingRecipe("CRIMSON_BLADE", new ItemStack(ConfigItems.itemSwordCrimson),
				5, new AspectList().add(Aspect.WEAPON, 32).add(Aspect.ELDRITCH, 16).add(Aspect.TRAP, 8).add(Aspect.DARKNESS, 8).add(Aspect.POISON, 8),
				new ItemStack(ConfigItems.itemSwordVoid),
				new ItemStack[]{
						new ItemStack(Items.quartz, 1),
						new ItemStack(ItemReg.MRThaumcraft, 1, 0),
						new ItemStack(ItemReg.MRThaumcraft, 1, 4),
						new ItemStack(Items.quartz, 1),
						new ItemStack(ItemReg.MRThaumcraft, 1, 4),
						new ItemStack(ItemReg.MRThaumcraft, 1, 0)
				});

	}

	private static void registerArcaneRecipes() {
		infused_blood = ThaumcraftApi.addArcaneCraftingRecipe("INFUSED_BLOOD", new ItemStack(ItemReg.MRThaumcraft, 1, 0),
				new AspectList().add(Aspect.ORDER, 6).add(Aspect.ENTROPY, 6).add(Aspect.EARTH, 6).add(Aspect.WATER, 6).add(Aspect.FIRE, 6).add(Aspect.AIR, 6),
				"sbs", " p ", 'p', new ItemStack(ConfigItems.itemEldritchObject, 1, 3), 'b', new ItemStack(TinkerWorld.strangeFood, 1, 1), 's', new ItemStack(ConfigItems.itemResource, 1, 14));

		blood_cloth = ThaumcraftApi.addShapelessArcaneCraftingRecipe("CRIMSON_CLERIC_ARMOR", new ItemStack(ItemReg.MRThaumcraft, 1, 3),
				new AspectList().add(Aspect.ORDER, 4).add(Aspect.ENTROPY, 4).add(Aspect.WATER, 4).add(Aspect.AIR, 8),
				new ItemStack(ConfigItems.itemResource, 1, 7), new ItemStack(ItemReg.MRThaumcraft, 1, 0));

		blood_ingot = ThaumcraftApi.addShapelessArcaneCraftingRecipe("CRIMSON_KNIGHT_ARMOR", new ItemStack(ItemReg.MRThaumcraft, 1, 4),
				new AspectList().add(Aspect.ORDER, 4).add(Aspect.ENTROPY, 4).add(Aspect.EARTH, 8).add(Aspect.WATER, 4),
				new ItemStack(ConfigItems.itemResource, 1, 2), new ItemStack(ItemReg.MRThaumcraft, 1, 0));
	}

	private static void registerBuildcraftRecipes() {
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
			if (ingredients.length != results.length)
				throw new ArrayIndexOutOfBoundsException();
			for (int n = 0; n < results.length; n++) {
				String modifier;
				String name;

				name = results[n].getUnlocalizedName();
				if (name.contains("Power")) {
					modifier = "dustRedstone";
				} else if (name.contains("Structure")) {
					modifier = "gravel";
				} else {
					modifier = null;
				}


				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(results[n], 8), "rrr", "igi", "rrr", 'r', modifier, 'i', ingredients[n], 'g', "blockGlass"));
			}
		} catch (ArrayIndexOutOfBoundsException exception) {
			LogHelper.error("Cannot register recipes");
			exception.printStackTrace();
		}
	}
}
