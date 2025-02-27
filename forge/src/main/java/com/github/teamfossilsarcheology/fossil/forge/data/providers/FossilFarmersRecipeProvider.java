package com.github.teamfossilsarcheology.fossil.forge.data.providers;

//public class FossilFarmersRecipeProvider extends RecipeProvider {
//    public FossilFarmersRecipeProvider(DataGenerator generator) {
//        super(generator);
//    }
//
//    @Override
//    public void buildCraftingRecipes(@NotNull Consumer<FinishedRecipe> consumer) {
//        fullCooking(ModItemTags.DINO_EGGS, ModItems.FRIED_EGG.get(), "fa_eggs_to_fried_egg", consumer, 0.5f);
//    }
//
//    private static void fullCooking(Ingredient ingredient, ItemPredicate predicate, ItemLike result, String ingredientName, String resultName, Consumer<FinishedRecipe> consumer2, float exp) {
//        ResourceLocation baseLocation = FossilMod.location(resultName);
//       /* ConditionalRecipe.builder().addCondition(new ModLoadedCondition(ModConstants.FARMERS)).addRecipe(consumer -> {
//            var furnace = SimpleCookingRecipeBuilder.smelting(ingredient, result, exp, 200)
//                    .unlockedBy("has_" + ingredientName, inventoryTrigger(predicate));
//            furnace.save(consumer, baseLocation);
//        }).build(consumer2, baseLocation);
//        ConditionalRecipe.builder().addCondition(new ModLoadedCondition(ModConstants.FARMERS)).addRecipe(consumer -> {
//            var campfire = SimpleCookingRecipeBuilder.campfireCooking(ingredient, result, exp, 600)
//                    .unlockedBy("has_" + ingredientName, inventoryTrigger(predicate));
//            campfire.save(consumer, baseLocation + "_from_campfire_cooking");
//        }).build(consumer2, baseLocation.getNamespace(), baseLocation.getPath() + "_from_campfire_cooking");
//        ConditionalRecipe.builder().addCondition(new ModLoadedCondition(ModConstants.FARMERS)).addRecipe(consumer -> {
//            var smoker = SimpleCookingRecipeBuilder.smoking(ingredient, result, exp, 100)
//                    .unlockedBy("has_" + ingredientName, inventoryTrigger(predicate));
//            smoker.save(consumer, baseLocation + "_from_smoking");
//        }).build(consumer2, baseLocation.getNamespace(), baseLocation.getPath() + "_from_smoking");
//        var furnace = SimpleCookingRecipeBuilder.smelting(ingredient, result, exp, 200)
//                .unlockedBy("has_" + ingredientName, inventoryTrigger(predicate));
//        var campfire = SimpleCookingRecipeBuilder.campfireCooking(ingredient, result, exp, 600)
//                .unlockedBy("has_" + ingredientName, inventoryTrigger(predicate));
//        var smoker = SimpleCookingRecipeBuilder.smoking(ingredient, result, exp, 100)
//                .unlockedBy("has_" + ingredientName, inventoryTrigger(predicate));
//        furnace.save(consumer2, baseLocation);
//        campfire.save(consumer2, baseLocation + "_from_campfire_cooking");
//        smoker.save(consumer2, baseLocation + "_from_smoking");*/
//    }
//
//    private static void fullCooking(TagKey<Item> ingredient, ItemLike result, String resultName, Consumer<FinishedRecipe> consumer, float exp) {
//        fullCooking(Ingredient.of(ingredient), ItemPredicate.Builder.item().of(ingredient).build(), result, ingredient.location().getPath(), resultName, consumer, exp);
//    }
//
//    private static void fullCooking(ItemLike ingredient, ItemLike result, String resultName, Consumer<FinishedRecipe> consumer, float exp) {
//        fullCooking(Ingredient.of(ingredient), ItemPredicate.Builder.item().of(ingredient).build(), result, RecipeBuilder.getDefaultRecipeId(ingredient).getPath(), resultName, consumer, exp);
//    }
//
//    private static void fullCooking(ItemLike ingredient, ItemLike result, Consumer<FinishedRecipe> consumer, float exp) {
//        fullCooking(ingredient, result, RecipeBuilder.getDefaultRecipeId(result).toString(), consumer, exp);
//    }
//}
