package ru.minecrafting.recipes.entity;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Random;

public class StaticEntityItem extends EntityItem {

	private final float rotationYawFinal;
	Random rand = new Random();

	public StaticEntityItem(World world) {
		super(world);
		rotationYawFinal = 0;
		init();
	}

	public StaticEntityItem(World world, double x, double y, double z) {
		super(world, x, y, z);
		rotationYawFinal = 0;
		init();
	}

	public StaticEntityItem(World world, double x, double y, double z, ItemStack stack) {
		super(world, x, y, z, stack);
		rotationYawFinal = 0;
		init();
	}

	private void init() {
		isImmuneToFire = true;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		motionX = 0;
		motionY = 0;
		motionZ = 0;

	}

	@Override
	public void setFire(int par1) {
	}

	@Override
	protected void dealFireDamage(int par1) {
	}

	@Override
	protected void setOnFireFromLava() {
	}
}