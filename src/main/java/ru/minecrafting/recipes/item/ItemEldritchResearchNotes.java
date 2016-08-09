package ru.minecrafting.recipes.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.items.ItemResearchNotes;
import thaumcraft.common.lib.research.ResearchManager;

public class ItemEldritchResearchNotes extends ItemResearchNotes {

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		stack = new ItemStack(ConfigItems.itemResearchNotes, 1, 0);
		ResearchManager.createNote(stack, "ELDRITCH_KNOWLEDGE", world);
		stack.setItemDamage(0);
		return stack;
	}

	@Override
	public boolean hasEffect(ItemStack par1ItemStack, int pass) {
		return true;
	}
}
