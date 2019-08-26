package fossilsarcheology.server.compat.crafttweaker;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import fossilsarcheology.server.recipe.FAMachineRecipeRegistry;
import fossilsarcheology.server.recipe.RecipeAnalyzer;
import fossilsarcheology.server.recipe.RecipeWorktable;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.fossils.recipes")
public class CraftTweakerCompat {

    public static void preInit(){
        CraftTweakerAPI.registerClass(CraftTweakerCompat.class);
    }

    @ZenMethod
    public static void addAnalyzerOutput(IItemStack iinput, IItemStack ioutput, float weight) {
        ItemStack input = CraftTweakerMC.getItemStack(iinput);
        RecipeAnalyzer recipe = null;
        boolean addToFlag = false;
        for(RecipeAnalyzer recipesInList : FAMachineRecipeRegistry.analyzerRecipes){
            if(input.isItemEqual(recipesInList.getInput())){
                recipe = recipesInList;
            }
        }
        if(recipe == null){
            addToFlag = true;
            recipe = new RecipeAnalyzer(input);
        }
        recipe.addOutput(CraftTweakerMC.getItemStack(ioutput), weight);
        if(addToFlag){
            FAMachineRecipeRegistry.analyzerRecipes.add(recipe);
        }
    }

    @ZenMethod
    public static void removeAnalyzerInput(IItemStack iinput) {
        ItemStack input = CraftTweakerMC.getItemStack(iinput).copy();
        input.setCount(1);
        FAMachineRecipeRegistry.analyzerRecipes.removeIf(recipe -> recipe.getInput().copy().isItemEqual(input));
    }

    @ZenMethod
    public static void addCultivateRecipe(IItemStack iinput, IItemStack ioutput) {
        FAMachineRecipeRegistry.cultivateRecipes.put(CraftTweakerMC.getItemStack(iinput), CraftTweakerMC.getItemStack(ioutput));
    }

    @ZenMethod
    public static void removeCultivateRecipe(IItemStack iinput) {
        ItemStack input = CraftTweakerMC.getItemStack(iinput).copy();
        input.setCount(1);
        FAMachineRecipeRegistry.cultivateRecipes.remove(input);
    }

    @ZenMethod
    public static void addCultivateFuel(IItemStack istack, int time) {
        FAMachineRecipeRegistry.cultivateFuelValues.put(CraftTweakerMC.getItemStack(istack), time);
    }

    @ZenMethod
    public static void removeCultivateFuel(IItemStack istack) {
        ItemStack input = CraftTweakerMC.getItemStack(istack).copy();
        input.setCount(1);
        FAMachineRecipeRegistry.cultivateFuelValues.remove(input);
    }

    @ZenMethod
    public static void addWorktableRecipe(IItemStack iinput, IItemStack ifuel, IItemStack ioutput) {
        FAMachineRecipeRegistry.worktableRecipes.add(new RecipeWorktable(CraftTweakerMC.getItemStack(iinput), CraftTweakerMC.getItemStack(ioutput), CraftTweakerMC.getItemStack(ifuel)));
    }

    @ZenMethod
    public static void removeWorktableRecipe(IItemStack iinput) {
        ItemStack input = CraftTweakerMC.getItemStack(iinput).copy();
        input.setCount(1);
        FAMachineRecipeRegistry.worktableRecipes.removeIf(recipe -> recipe.getInput().copy().isItemEqual(input));
    }

    @ZenMethod
    public static void addSifterOutput(IItemStack iinput, IItemStack ioutput, float weight) {
        ItemStack input = CraftTweakerMC.getItemStack(iinput);
        RecipeAnalyzer recipe = null;
        boolean addToFlag = false;
        for(RecipeAnalyzer recipesInList : FAMachineRecipeRegistry.sifterRecipes){
            if(input.isItemEqual(recipesInList.getInput())){
                recipe = recipesInList;
            }
        }
        if(recipe == null){
            addToFlag = true;
            recipe = new RecipeAnalyzer(input);
        }
        recipe.addOutput(CraftTweakerMC.getItemStack(ioutput), weight);
        if(addToFlag){
            FAMachineRecipeRegistry.sifterRecipes.add(recipe);
        }
    }

    @ZenMethod
    public static void removeSifterInput(IItemStack iinput) {
        ItemStack input = CraftTweakerMC.getItemStack(iinput).copy();
        input.setCount(1);
        FAMachineRecipeRegistry.sifterRecipes.removeIf(recipe -> recipe.getInput().copy().isItemEqual(input));
    }


}
