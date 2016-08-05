package ru.minecrafting.recipes.item;

import net.minecraft.item.Item;
import ru.minecrafting.recipes.ModInfo;
import thaumcraft.common.Thaumcraft;

public class ItemEldritchKnowledgeFragment extends Item {

	public ItemEldritchKnowledgeFragment() {
		super();
		this.setMaxStackSize(64);
		this.setUnlocalizedName("eldritchKnowledgeFragment");
		this.setCreativeTab(Thaumcraft.tabTC);
		this.setTextureName(ModInfo.MODID + ":eldritchKnowledgeFragment");

	}
}
