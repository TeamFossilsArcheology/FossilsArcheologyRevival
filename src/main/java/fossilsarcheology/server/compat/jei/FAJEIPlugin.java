package fossilsarcheology.server.compat.jei;

import fossilsarcheology.client.gui.AnalyzerGUI;
import fossilsarcheology.client.gui.CultivateGUI;
import fossilsarcheology.client.gui.SifterGUI;
import fossilsarcheology.client.gui.WorktableGUI;
import fossilsarcheology.server.block.FABlockRegistry;
import fossilsarcheology.server.compat.jei.analyzer.*;
import fossilsarcheology.server.compat.jei.culture.*;
import fossilsarcheology.server.compat.jei.sifter.*;
import fossilsarcheology.server.compat.jei.worktable.*;
import fossilsarcheology.server.item.FAItemRegistry;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapperFactory;
import net.minecraft.item.ItemStack;

@JEIPlugin
public class FAJEIPlugin implements IModPlugin {

    public static final String ANALYZER_UID = "fossil.analyzer";
    public static final String CULTURE_VAT_UID = "fossil.culture_vat";
    public static final String WORKTABLE_UID = "fossil.worktable";
    public static final String SIFTER_UID = "fossil.sifter";

    @SuppressWarnings("deprecation")
    public void register(IModRegistry registry) {
        registry.addRecipes(AnalyzerRecipes.getRecipes(), ANALYZER_UID);
        registry.addRecipeHandlers(new AnalyzerRecipeHandler());
        registry.handleRecipes(JEIRecipeAnalyzer.class, new AnalyzerFactory(), ANALYZER_UID);
        registry.addRecipeCategoryCraftingItem(new ItemStack(FABlockRegistry.ANALYZER), ANALYZER_UID);
        registry.addRecipeClickArea(AnalyzerGUI.class, 79, 21, 23, 11, ANALYZER_UID);

        registry.addRecipes(CultivateRecipes.getRecipes(), CULTURE_VAT_UID);
        registry.addRecipeHandlers(new CultivateRecipeHandler());
        registry.handleRecipes(RecipeCultivate.class, new CultureVatFactory(), CULTURE_VAT_UID);
        registry.addRecipeCategoryCraftingItem(new ItemStack(FABlockRegistry.CULTIVATE_IDLE), CULTURE_VAT_UID);
        registry.addRecipeClickArea(CultivateGUI.class, 79, 20, 23, 13, CULTURE_VAT_UID);

        registry.addRecipes(WorktableRecipes.getRecipes(), WORKTABLE_UID);
        registry.addRecipeHandlers(new WorktableRecipeHandler());
        registry.handleRecipes(RecipeWorktable.class, new WorktableFactory(), WORKTABLE_UID);
        registry.addRecipeCategoryCraftingItem(new ItemStack(FABlockRegistry.WORKTABLE_IDLE), WORKTABLE_UID);
        registry.addRecipeClickArea(WorktableGUI.class, 79, 19, 26, 16, WORKTABLE_UID);

        registry.addRecipes(SifterRecipes.getRecipes(), SIFTER_UID);
        registry.addRecipeHandlers(new SifterRecipeHandler());
        registry.handleRecipes(RecipeSifter.class, new SifterFactory(), SIFTER_UID);
        registry.addRecipeCategoryCraftingItem(new ItemStack(FABlockRegistry.SIFTER_IDLE), SIFTER_UID);
        registry.addRecipeClickArea(SifterGUI.class, 75, 33, 26, 26, SIFTER_UID);

        addDescription(registry, new ItemStack(FAItemRegistry.BIOFOSSIL));
        addDescription(registry, new ItemStack(FAItemRegistry.TAR_FOSSIL));
        addDescription(registry, new ItemStack(FABlockRegistry.FEEDER));
        addDescription(registry, new ItemStack(FAItemRegistry.CHICKEN_ESSENCE));
        addDescription(registry, new ItemStack(FAItemRegistry.DINOPEDIA));
        addDescription(registry, new ItemStack(FAItemRegistry.SCARAB_GEM));
        addDescription(registry, new ItemStack(FAItemRegistry.AQUATIC_SCARAB_GEM));
        addDescription(registry, new ItemStack(FAItemRegistry.STONE_TABLET));
        for(int i = 0; i <= 15; i++){
            registry.addIngredientInfo(new ItemStack(FAItemRegistry.TOY_BALL, 1, i), ItemStack.class, "item.toy.jei_desc");
        }
        registry.addIngredientInfo(new ItemStack(FAItemRegistry.TOY_TETHERED_LOG), ItemStack.class, "item.toy.jei_desc");
        registry.addIngredientInfo(new ItemStack(FAItemRegistry.TOY_SCRATCHING_POST), ItemStack.class, "item.toy.jei_desc");
        addDescription(registry, new ItemStack(FAItemRegistry.WHIP));
        addDescription(registry, new ItemStack(FAItemRegistry.ANCIENT_HELMET));
        addDescription(registry, new ItemStack(FAItemRegistry.ANCIENT_SWORD));

    }

    public void registerCategories(IRecipeCategoryRegistration registry) {
        registry.addRecipeCategories(new AnalyzerRecipeCatagory());
        registry.addRecipeCategories(new CultivateRecipeCatagory());
        registry.addRecipeCategories(new WorktableRecipeCatagory());
        registry.addRecipeCategories(new SifterRecipeCatagory());
    }

    public class AnalyzerFactory implements IRecipeWrapperFactory<JEIRecipeAnalyzer> {
        @Override
        public IRecipeWrapper getRecipeWrapper(JEIRecipeAnalyzer recipe) {
            return new AnalyzerRecipeWrapper(recipe);
        }
    }

    public class CultureVatFactory implements IRecipeWrapperFactory<RecipeCultivate> {
        @Override
        public IRecipeWrapper getRecipeWrapper(RecipeCultivate recipe) {
            return new CultivateRecipeWrapper(recipe);
        }
    }

    public class WorktableFactory implements IRecipeWrapperFactory<RecipeWorktable> {
        @Override
        public IRecipeWrapper getRecipeWrapper(RecipeWorktable recipe) {
            return new WorktableRecipeWrapper(recipe);
        }
    }

    public class SifterFactory implements IRecipeWrapperFactory<RecipeSifter> {
        @Override
        public IRecipeWrapper getRecipeWrapper(RecipeSifter recipe) {
            return new SifterRecipeWrapper(recipe);
        }
    }

    private static void addDescription(IModRegistry registry, ItemStack stack){
        registry.addIngredientInfo(stack, ItemStack.class, stack.getTranslationKey() + ".jei_desc");

    }
}
