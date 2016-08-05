package ru.minecrafting.recipes.registers;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import ru.minecrafting.recipes.item.ItemEldritchResearchNotes;
import ru.minecrafting.recipes.item.ItemMRThaumcraft;

public class ItemReg {

	public static ItemMRThaumcraft MRThaumcraft;
	public static Item eldritchResearchNotes;
	public static void registerItems() {
		MRThaumcraft = new ItemMRThaumcraft();
		GameRegistry.registerItem(MRThaumcraft, "ItemMRThaumcraft");
		eldritchResearchNotes = new ItemEldritchResearchNotes();
		GameRegistry.registerItem(eldritchResearchNotes, "ItemEldritchResearchNotes");
	}
}
