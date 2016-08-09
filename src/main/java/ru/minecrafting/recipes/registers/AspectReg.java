package ru.minecrafting.recipes.registers;

import net.minecraft.item.ItemStack;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

public class AspectReg {
	public static void registerAspects() {
		ThaumcraftApi.registerObjectTag(new ItemStack(ItemReg.MRThaumcraft, 1, 0),
				new AspectList().add(Aspect.MAGIC, 6).add(Aspect.BEAST, 2).add(Aspect.SLIME, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(ItemReg.MRThaumcraft, 1, 1),
				new AspectList().add(Aspect.MIND, 8).add(Aspect.ELDRITCH, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(ItemReg.MRThaumcraft, 1, 2),
				new AspectList().add(Aspect.EARTH, 1).add(Aspect.WATER, 1).add(Aspect.FIRE, 1).add(Aspect.ORDER, 1).add(Aspect.AIR, 1).add(Aspect.ENTROPY, 1));
	}
}
