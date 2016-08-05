package ru.minecrafting.recipes.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import ru.minecrafting.recipes.ModInfo;
import thaumcraft.common.Thaumcraft;

public class ItemInfusedBlood extends Item {
	public ItemInfusedBlood() {
		super();
		this.setMaxStackSize(64);
		this.setUnlocalizedName("infusedBlood");
		this.setCreativeTab(Thaumcraft.tabTC);
		this.setTextureName(ModInfo.MODID + ":infusedBlood");
	}

	@Override
	public boolean hasEffect(ItemStack par1ItemStack, int pass) {
		return true;
	}


}
