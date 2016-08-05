package ru.minecrafting.recipes.registers;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import tconstruct.armor.TinkerArmor;
import tconstruct.world.TinkerWorld;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.crafting.ShapedArcaneRecipe;
import thaumcraft.api.crafting.ShapelessArcaneRecipe;
import thaumcraft.common.config.Config;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.container.ContainerArcaneWorkbench;

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
				4, new AspectList().add(Aspect.ORDER, 16).add(Aspect.ELDRITCH, 32).add(Aspect.GREED, 24).add(Aspect.MAGIC, 64),
				new ItemStack(ItemReg.MRThaumcraft, 1, 2),
				new ItemStack[]{
						new ItemStack(Items.nether_star, 1),
						new ItemStack(Items.ender_pearl, 1),
						new ItemStack(Items.ender_pearl, 1),
						new ItemStack(ConfigItems.itemResource, 1, 14),
						new ItemStack(ConfigItems.itemResource, 1, 14),
						new ItemStack(ConfigItems.itemResource, 1, 14),
						new ItemStack(ConfigItems.itemResource, 1, 14),
						new ItemStack(ConfigItems.itemResource, 1, 1)
				});
		praetor_helmet = ThaumcraftApi.addInfusionCraftingRecipe("CRIMSON_PRAETOR_ARMOR", new ItemStack(ConfigItems.itemHelmetCultistLeaderPlate),
				2, new AspectList().add(Aspect.CLOTH, 8).add(Aspect.ELDRITCH, 16).add(Aspect.ARMOR, 16).add(Aspect.METAL, 8),
				new ItemStack(ConfigItems.itemHelmetCultistPlate),
				new ItemStack[] {
						new ItemStack(ItemReg.MRThaumcraft, 1, 4),
						new ItemStack(ItemReg.MRThaumcraft, 1, 3),
						new ItemStack(ConfigItems.itemResource, 1, 17),
						new ItemStack(ConfigItems.itemResource, 1, 17)
				});
		praetor_chest = ThaumcraftApi.addInfusionCraftingRecipe("CRIMSON_PRAETOR_ARMOR", new ItemStack(ConfigItems.itemChestCultistLeaderPlate),
				2, new AspectList().add(Aspect.CLOTH, 8).add(Aspect.ELDRITCH, 16).add(Aspect.ARMOR, 16).add(Aspect.METAL, 8),
				new ItemStack(ConfigItems.itemChestCultistPlate),
				new ItemStack[] {
						new ItemStack(ItemReg.MRThaumcraft, 1, 4),
						new ItemStack(ItemReg.MRThaumcraft, 1, 3),
						new ItemStack(ConfigItems.itemResource, 1, 17),
						new ItemStack(ConfigItems.itemResource, 1, 17)
				});
		praetor_leggings = ThaumcraftApi.addInfusionCraftingRecipe("CRIMSON_PRAETOR_ARMOR", new ItemStack(ConfigItems.itemLegsCultistLeaderPlate),
				2, new AspectList().add(Aspect.CLOTH, 8).add(Aspect.ELDRITCH, 16).add(Aspect.ARMOR, 16).add(Aspect.METAL, 8),
				new ItemStack(ConfigItems.itemLegsCultistPlate),
				new ItemStack[] {
						new ItemStack(ItemReg.MRThaumcraft, 1, 4),
						new ItemStack(ItemReg.MRThaumcraft, 1, 3),
						new ItemStack(ConfigItems.itemResource, 1, 17),
						new ItemStack(ConfigItems.itemResource, 1, 17)
				});

		crimson_sword = ThaumcraftApi.addInfusionCraftingRecipe("CRIMSON_BLADE", new ItemStack(ConfigItems.itemSwordCrimson),
				5, new AspectList().add(Aspect.WEAPON, 32).add(Aspect.ELDRITCH, 16).add(Aspect.TRAP, 8).add(Aspect.DARKNESS, 8).add(Aspect.POISON, 8),
				new ItemStack(ConfigItems.itemSwordVoid),
				new ItemStack[]{
						new ItemStack(ItemReg.MRThaumcraft, 1, 4),
						new ItemStack(ItemReg.MRThaumcraft, 1, 4),
						new ItemStack(ItemReg.MRThaumcraft, 1, 0),
						new ItemStack(ItemReg.MRThaumcraft, 1, 0),
						new ItemStack(Items.quartz, 1),
						new ItemStack(Items.quartz, 1)
				});

	}

	private static void registerArcaneRecipes() {
		infused_blood = ThaumcraftApi.addArcaneCraftingRecipe("INFUSED_BLOOD", new ItemStack(ItemReg.MRThaumcraft, 1, 0),
				new AspectList().add(Aspect.ORDER, 16).add(Aspect.ENTROPY, 16).add(Aspect.EARTH, 16).add(Aspect.WATER, 16).add(Aspect.FIRE, 16).add(Aspect.AIR, 16),
				"sbs", " p ", 'p', new ItemStack(ConfigItems.itemEldritchObject, 1, 3), 'b', new ItemStack(TinkerWorld.strangeFood, 1, 1), 's', new ItemStack(ConfigItems.itemResource, 1, 14));

		blood_cloth = ThaumcraftApi.addShapelessArcaneCraftingRecipe("CRIMSON_CLERIC_ARMOR", new ItemStack(ItemReg.MRThaumcraft, 1, 3),
				new AspectList().add(Aspect.ORDER, 4).add(Aspect.ENTROPY, 4).add(Aspect.WATER, 4).add(Aspect.AIR, 8),
				new ItemStack(ConfigItems.itemResource, 1, 7), new ItemStack(ItemReg.MRThaumcraft, 1, 0));

		blood_ingot = ThaumcraftApi.addShapelessArcaneCraftingRecipe("CRIMSON_KNIGHT_ARMOR", new ItemStack(ItemReg.MRThaumcraft, 1, 4),
				new AspectList().add(Aspect.ORDER, 16).add(Aspect.ENTROPY, 16).add(Aspect.EARTH, 8).add(Aspect.WATER, 4),
				new ItemStack(ConfigItems.itemResource, 1, 2), new ItemStack(ItemReg.MRThaumcraft, 1, 0));
	}
}
