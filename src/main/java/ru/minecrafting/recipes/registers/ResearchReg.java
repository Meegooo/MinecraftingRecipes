package ru.minecrafting.recipes.registers;

import net.minecraft.item.ItemStack;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.common.config.ConfigItems;

public class ResearchReg {
	public static void registerResearch() {
		new ResearchItem("ELDRITCH_KNOWLEDGE", "ELDRITCH",
				new AspectList().add(Aspect.ELDRITCH, 10).add(Aspect.MAN, 5).add(Aspect.ORDER, 2).add(Aspect.MIND, 5),
				4, 3, 3, new ItemStack(ItemReg.MRThaumcraft, 1, 1))
				.setLost()
				.setSpecial()
				.setRound()
				.setPages(new ResearchPage("tc.research_page.ELDRITCH_KNOWLEDGE.1"))
				.registerResearchItem();
		ThaumcraftApi.addWarpToResearch("ELDRITCH_KNOWLEDGE", 5);

		new ResearchItem("PEARL_CREATION", "ELDRITCH",
				new AspectList().add(Aspect.MAGIC, 10).add(Aspect.ORDER, 10).add(Aspect.CRAFT, 10).add(Aspect.MAN, 10).add(Aspect.ELDRITCH, 10).add(Aspect.MIND, 10),
				4, 5, 3, new ItemStack(ItemReg.MRThaumcraft, 1, 2))
				.setItemTriggers(new ItemStack(ItemReg.MRThaumcraft, 1, 2))
				.setParents("ELDRITCH_KNOWLEDGE")
				.setConcealed()
				.setHidden()
				.setPages(new ResearchPage("tc.research_page.PEARL_CREATION.1"), new ResearchPage(RecipeReg.pearl_infusion))
				.registerResearchItem();
		ThaumcraftApi.addWarpToResearch("PEARL_CREATION", 2);

		new ResearchItem("INFUSED_BLOOD", "ELDRITCH",
				new AspectList().add(Aspect.MAGIC, 10).add(Aspect.BEAST, 4).add(Aspect.SLIME, 4).add(Aspect.HEAL, 4),
				4, 7, 1, new ItemStack(ItemReg.MRThaumcraft, 1, 0))
				.setParents("PEARL_CREATION")
				.setParentsHidden("PRIMPEARL")
				.setConcealed()
				.setPages(new ResearchPage("tc.research_page.INFUSED_BLOOD.1"), new ResearchPage(RecipeReg.infused_blood))
				.registerResearchItem();

		new ResearchItem("CRIMSON_CLERIC_ARMOR", "ELDRITCH",
				new AspectList().add(Aspect.CLOTH, 7).add(Aspect.ARMOR, 10).add(Aspect.ELDRITCH, 10).add(Aspect.CRAFT, 4),
				3, 8, 2, new ItemStack(ItemReg.MRThaumcraft, 1, 3))
				.setParents("INFUSED_BLOOD")
				.setConcealed()
				.setPages(new ResearchPage("tc.research_page.CRIMSON_CLERIC_ARMOR.1"),
						new ResearchPage(RecipeReg.blood_cloth),
						new ResearchPage(RecipeReg.cleric_helmet),
						new ResearchPage(RecipeReg.cleric_chest),
						new ResearchPage(RecipeReg.cleric_leggings),
						new ResearchPage(RecipeReg.crimson_boots))
				.registerResearchItem();

		new ResearchItem("CRIMSON_KNIGHT_ARMOR", "ELDRITCH",
				new AspectList().add(Aspect.METAL, 7).add(Aspect.ARMOR, 10).add(Aspect.ELDRITCH, 10).add(Aspect.CRAFT, 4),
				5, 8, 2, new ItemStack(ItemReg.MRThaumcraft, 1, 4))
				.setParents("INFUSED_BLOOD")
				.setParentsHidden("CRIMSON_CLERIC_ARMOR")
				.setConcealed()
				.setPages(new ResearchPage("tc.research_page.CRIMSON_KNIGHT_ARMOR.1"),
						new ResearchPage(RecipeReg.blood_ingot),
						new ResearchPage(RecipeReg.knight_helmet),
						new ResearchPage(RecipeReg.knight_chest),
						new ResearchPage(RecipeReg.knight_leggings))
				.registerResearchItem();

		new ResearchItem("CRIMSON_PRAETOR_ARMOR", "ELDRITCH",
				new AspectList().add(Aspect.METAL, 7).add(Aspect.ARMOR, 10).add(Aspect.ELDRITCH, 10).add(Aspect.CRAFT, 4).add(Aspect.GREED, 5).add(Aspect.METAL, 7),
				4, 9, 2, new ItemStack(ConfigItems.itemHelmetCultistLeaderPlate))
				.setParents("CRIMSON_KNIGHT_ARMOR", "CRIMSON_CLERIC_ARMOR")
				.setConcealed()
				.setPages(new ResearchPage("tc.research_page.CRIMSON_PRAETOR_ARMOR.1"),
						new ResearchPage(RecipeReg.praetor_helmet),
						new ResearchPage(RecipeReg.praetor_chest),
						new ResearchPage(RecipeReg.praetor_leggings))
				.registerResearchItem();

		new ResearchItem("CRIMSON_BLADE", "ELDRITCH",
				new AspectList().add(Aspect.WEAPON, 10).add(Aspect.TOOL, 10).add(Aspect.ELDRITCH, 10).add(Aspect.CRAFT, 4).add(Aspect.TRAP, 4),
				5, 10, 3, new ItemStack(ConfigItems.itemSwordCrimson))
				.setParents("CRIMSON_KNIGHT_ARMOR")
				.setConcealed()
				.setPages(new ResearchPage("tc.research_page.CRIMSON_BLADE.1"),
						new ResearchPage(RecipeReg.crimson_sword))
				.registerResearchItem();
	}
}
