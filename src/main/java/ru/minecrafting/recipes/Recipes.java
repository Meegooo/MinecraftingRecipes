package ru.minecrafting.recipes;

import buildcraft.BuildCraftTransport;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

@Mod(modid = Reference.MODID, version = Reference.VERSION, name = Reference.MOD_NAME, dependencies = "required-after:BuildCraft|Transport@6.4.3")
public class Recipes
{

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        OreDictionary.registerOre("gravel", new ItemStack(Blocks.gravel));
        String[] ingredients = new String[10];
        Item[] results = new Item[10];

        ingredients[0] = "plankWood";
        ingredients[1] = "cobblestone";
        ingredients[2] = "stone";
        ingredients[3] = "gemDiamond";
        ingredients[4] = "ingotGold";
        ingredients[5] = "ingotIron";
        ingredients[6] = "gemQuartz";
        ingredients[7] = "cobblestone";
        ingredients[8] = "gemEmerald";
        ingredients[9] = "sandstone";

        results[0] = BuildCraftTransport.pipePowerWood;
        results[1] = BuildCraftTransport.pipePowerCobblestone;
        results[2] = BuildCraftTransport.pipePowerStone;
        results[3] = BuildCraftTransport.pipePowerDiamond;
        results[4] = BuildCraftTransport.pipePowerGold;
        results[5] = BuildCraftTransport.pipePowerIron;
        results[6] = BuildCraftTransport.pipePowerQuartz;
        results[7] = BuildCraftTransport.pipeStructureCobblestone;
        results[8] = BuildCraftTransport.pipePowerEmerald;
        results[9] = BuildCraftTransport.pipePowerSandstone;



        try {
            if (ingredients.length != results.length) {
                throw new ArrayIndexOutOfBoundsException();
            }
            for(int n = 0; n < results.length; n++) {
                String modifier;
                String name;

                name = results[n].getUnlocalizedName();
                if (name.contains("Power"))
                {
                    modifier = "dustRedstone";
                }else if(name.contains("Structure"))
                {
                    modifier = "gravel";
                }else
                {
                    modifier = null;
                }


                GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(results[n], 8), "rrr", "igi", "rrr", 'r', modifier, 'i', ingredients[n], 'g', "blockGlass"));
            }
        }catch (ArrayIndexOutOfBoundsException exception) {
            LogHelper.error("Cannot register recipes");
            exception.printStackTrace();
        }
    }
}
