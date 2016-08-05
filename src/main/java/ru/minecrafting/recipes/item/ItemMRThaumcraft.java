package ru.minecrafting.recipes.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import ru.minecrafting.recipes.ModInfo;
import thaumcraft.common.Thaumcraft;

import java.util.List;

public class ItemMRThaumcraft extends Item {

	IIcon[] icons = new IIcon[5];
	public ItemMRThaumcraft() {
		super();
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
		this.setCreativeTab(Thaumcraft.tabTC);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister register) {
		icons[0] = register.registerIcon(ModInfo.MODID + ":infusedBlood");
		icons[1] = register.registerIcon(ModInfo.MODID + ":eldritchKnowledgeFragment");
		icons[2] = register.registerIcon(ModInfo.MODID + ":dullPrimordialPearl");
		icons[3] = register.registerIcon(ModInfo.MODID + ":bloodCloth");
		icons[4] = register.registerIcon(ModInfo.MODID + ":bloodIngot");
	}

	@Override
	public IIcon getIconFromDamage(int dmg) {
		return dmg>=icons.length?icons[0]:icons[dmg];
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return getUnlocalizedName().concat("." + stack.getItemDamage());
	}

	@Override
	public String getUnlocalizedName() {
		return "ItemMRThaumcraft";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tabs, List list) {
		list.add(new ItemStack(this, 1, 0));
		list.add(new ItemStack(this, 1, 1));
		list.add(new ItemStack(this, 1, 2));
		list.add(new ItemStack(this, 1, 3));
		list.add(new ItemStack(this, 1, 4));
	}

	@Override
	public boolean hasEffect(ItemStack par1ItemStack, int pass) {
		return par1ItemStack.getItemDamage() == 0;
	}
}
