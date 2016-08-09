package ru.minecrafting.recipes.registers;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import ru.minecrafting.recipes.item.ItemEldritchResearchNotes;
import ru.minecrafting.recipes.item.ItemResource;

public class ItemReg {

	public static ItemResource MRThaumcraft;
	public static Item eldritchResearchNotes;
	public static void registerItems() {
		MRThaumcraft = new ItemResource();
		GameRegistry.registerItem(MRThaumcraft, "ItemMRThaumcraft");
		eldritchResearchNotes = new ItemEldritchResearchNotes();
		GameRegistry.registerItem(eldritchResearchNotes, "ItemEldritchResearchNotes");
	}
}
