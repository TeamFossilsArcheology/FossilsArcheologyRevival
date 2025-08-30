package com.github.teamfossilsarcheology.fossil.forge.tests;


import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.block.ModBlocks;
import com.github.teamfossilsarcheology.fossil.block.custom_blocks.AnalyzerBlock;
import com.github.teamfossilsarcheology.fossil.block.custom_blocks.CultureVatBlock;
import com.github.teamfossilsarcheology.fossil.block.custom_blocks.SifterBlock;
import com.github.teamfossilsarcheology.fossil.block.custom_blocks.WorktableBlock;
import com.github.teamfossilsarcheology.fossil.block.entity.AnalyzerBlockEntity;
import com.github.teamfossilsarcheology.fossil.block.entity.CultureVatBlockEntity;
import com.github.teamfossilsarcheology.fossil.block.entity.SifterBlockEntity;
import com.github.teamfossilsarcheology.fossil.block.entity.WorktableBlockEntity;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import com.github.teamfossilsarcheology.fossil.inventory.AnalyzerMenu;
import com.github.teamfossilsarcheology.fossil.inventory.CultureVatMenu;
import com.github.teamfossilsarcheology.fossil.inventory.SifterMenu;
import com.github.teamfossilsarcheology.fossil.inventory.WorktableMenu;
import com.github.teamfossilsarcheology.fossil.item.ModItems;
import com.github.teamfossilsarcheology.fossil.recipe.ModRecipes;
import com.github.teamfossilsarcheology.fossil.recipe.WithFuelRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestAssertException;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.gametest.GameTestHolder;
import net.minecraftforge.gametest.PrefixGameTestTemplate;

@PrefixGameTestTemplate(value = false)
@GameTestHolder(FossilMod.MOD_ID)
public class RecipeTests {
    private static final BlockPos MACHINE_POS = new BlockPos(0, 1, 0);

    protected static BlockEntity getBlockEntity(BlockPos machinePos, GameTestHelper helper, Class<?> machineBlockClass) {
        helper.assertBlock(machinePos, machineBlockClass::isInstance, "Machine block not found");
        return helper.getBlockEntity(machinePos);
    }

    //if a fail happens on any tick before the last tick: retry else fail
    //if a success happens on any tick before the last tick: success
    //failIf: fails if no exception is thrown
    //failIfEver: fails if an exception is thrown at any point
    //succeedIf: succeeds if no exception is thrown on this tick
    //succeedWhen: succeeds if no exception is thrown on any tick

    /**
     * Tests that the fuel item gets added correctly and does get removed when adding the input
     */
    @GameTest(batch = "culture_vat", template = "culture_vat", timeoutTicks = 20)
    public static void cultureVatFuel(GameTestHelper helper) {
        BlockEntity blockEntity = getBlockEntity(MACHINE_POS, helper, CultureVatBlock.class);
        if (blockEntity instanceof CultureVatBlockEntity cultureVatEntity) {
            Item fuelItem = ModItems.BIO_GOO.get();
            Item inputItem = PrehistoricEntityInfo.ALLOSAURUS.dnaItem;
            cultureVatEntity.setItem(CultureVatMenu.FUEL_SLOT_ID, new ItemStack(fuelItem));
            helper.assertContainerContains(MACHINE_POS, fuelItem);
            cultureVatEntity.setItem(CultureVatMenu.INPUT_SLOT_ID, new ItemStack(inputItem));
            helper.succeedOnTickWhen(1, () -> assertContainerDoesNotContain(helper, MACHINE_POS, fuelItem));
        }
    }

    /**
     * Tests that the input item gets added correctly and does not get removed when adding fuel
     */
    @GameTest(batch = "culture_vat", template = "culture_vat", timeoutTicks = 20)
    public static void cultureVatInput(GameTestHelper helper) {
        BlockEntity blockEntity = getBlockEntity(MACHINE_POS, helper, CultureVatBlock.class);
        if (blockEntity instanceof CultureVatBlockEntity cultureVatEntity) {
            Item fuelItem = ModItems.BIO_GOO.get();
            Item inputItem = PrehistoricEntityInfo.ALLOSAURUS.dnaItem;
            cultureVatEntity.setItem(CultureVatMenu.INPUT_SLOT_ID, new ItemStack(inputItem));
            helper.assertContainerContains(MACHINE_POS, inputItem);
            cultureVatEntity.setItem(CultureVatMenu.FUEL_SLOT_ID, new ItemStack(fuelItem));
            helper.runAtTickTime(5, () -> helper.succeedIf(() -> helper.assertContainerContains(MACHINE_POS, inputItem)));
        }
    }

    /**
     * Tests if the correct container data is set
     */
    @GameTest(batch = "culture_vat", template = "culture_vat", timeoutTicks = 5)
    public static void cultureVatData(GameTestHelper helper) {
        BlockEntity blockEntity = getBlockEntity(MACHINE_POS, helper, CultureVatBlock.class);
        if (blockEntity instanceof CultureVatBlockEntity cultureVatEntity) {
            Item fuelItem = ModItems.BIO_GOO.get();
            Item inputItem = PrehistoricEntityInfo.ALLOSAURUS.dnaItem;
            cultureVatEntity.setItem(CultureVatMenu.FUEL_SLOT_ID, new ItemStack(fuelItem));
            ContainerData dataAccess = cultureVatEntity.getDataAccess();
            helper.runAtTickTime(0, () -> {
                if (dataAccess.get(0) != 0) {
                    throw new GameTestAssertException("LitTime should be 0 but is " + dataAccess.get(0));
                } else if (dataAccess.get(1) != 0) {
                    throw new GameTestAssertException("LitDuration should be 0 but is " + dataAccess.get(1));
                } else if (dataAccess.get(2) != 0) {
                    throw new GameTestAssertException("CookingProgress should be 0 but is " + dataAccess.get(2));
                }
                cultureVatEntity.setItem(CultureVatMenu.INPUT_SLOT_ID, new ItemStack(inputItem));
            });
            int fuelTime = ModRecipes.getCultureVatFuelValue(fuelItem);
            helper.runAtTickTime(1, () -> {
                if (dataAccess.get(0) != fuelTime) {
                    throw new GameTestAssertException("LitTime should be " + fuelTime + " but is " + dataAccess.get(0));
                } else if (dataAccess.get(1) != fuelTime) {
                    throw new GameTestAssertException("LitDuration should be " + fuelTime + " but is " + dataAccess.get(1));
                } else if (dataAccess.get(2) != 1) {
                    throw new GameTestAssertException("CookingProgress should be 1 but is " + dataAccess.get(2));
                }
            });
            helper.runAtTickTime(2, helper::succeed);
        }
    }

    /**
     * Tests if all recipes return the correct output
     */
    @GameTest(batch = "culture_vat", template = "culture_vat", timeoutTicks = 2000)
    public static void cultureVatRecipes(GameTestHelper helper) {
        BlockEntity blockEntity = getBlockEntity(MACHINE_POS, helper, CultureVatBlock.class);
        if (blockEntity instanceof CultureVatBlockEntity cultureVatEntity) {
            var list = helper.getLevel().getRecipeManager().getAllRecipesFor(ModRecipes.CULTURE_VAT_TYPE.get());
            ContainerData dataAccess = cultureVatEntity.getDataAccess();
            for (int i = 0; i < list.size(); i++) {
                var recipe = list.get(i);
                Item fuelItem = recipe.getFuel().getItems()[0].getItem();
                Item inputItem = recipe.getInput().getItems()[0].getItem();
                int fuelNeeded = recipe.getDuration() / ModRecipes.getCultureVatFuelValue(fuelItem);
                long startTick = i * 3L;
                helper.runAtTickTime(startTick, () -> {
                    cultureVatEntity.setItem(CultureVatMenu.FUEL_SLOT_ID, new ItemStack(fuelItem, fuelNeeded));
                    cultureVatEntity.setItem(CultureVatMenu.INPUT_SLOT_ID, new ItemStack(inputItem));
                });
                helper.runAtTickTime(startTick + 1, () -> dataAccess.set(2, CultureVatMenu.CULTIVATION_DURATION - 1));
                helper.runAtTickTime(startTick + 2, () -> {
                    if (!ItemStack.isSameItem(cultureVatEntity.getItem(CultureVatMenu.OUTPUT_SLOT_ID), recipe.getResultItem(null))) {
                        throw new GameTestAssertException("Output does not contain " + recipe.getResultItem(null).getItem() + " but instead " + cultureVatEntity.getItem(CultureVatMenu.OUTPUT_SLOT_ID));
                    }
                    cultureVatEntity.setItem(CultureVatMenu.OUTPUT_SLOT_ID, ItemStack.EMPTY);
                });
            }
            helper.runAtTickTime(list.size() * 3L, helper::succeed);
        }
    }

    /**
     * Tests if the correct recipe result get added at the correct point in time
     */
    @GameTest(batch = "culture_vat", template = "culture_vat", timeoutTicks = 20)
    public static void cultureVatResult(GameTestHelper helper) {
        BlockEntity blockEntity = getBlockEntity(MACHINE_POS, helper, CultureVatBlock.class);
        if (blockEntity instanceof CultureVatBlockEntity cultureVatEntity) {
            Item fuelItem = ModItems.BIO_GOO.get();
            Item inputItem = PrehistoricEntityInfo.ALLOSAURUS.dnaItem;
            var recipe = ModRecipes.getCultureVatRecipeForItem(new WithFuelRecipe.ContainerWithAnyFuel(new ItemStack(inputItem), new ItemStack(fuelItem)), helper.getLevel());
            int fuelNeeded = recipe.getDuration() / ModRecipes.getCultureVatFuelValue(fuelItem);
            cultureVatEntity.setItem(CultureVatMenu.FUEL_SLOT_ID, new ItemStack(fuelItem, fuelNeeded));
            cultureVatEntity.setItem(CultureVatMenu.INPUT_SLOT_ID, new ItemStack(inputItem));
            ContainerData dataAccess = cultureVatEntity.getDataAccess();
            helper.runAtTickTime(1, () -> dataAccess.set(2, CultureVatMenu.CULTIVATION_DURATION - 10));
            helper.runAtTickTime(11, () -> {
                if (!ItemStack.isSameItem(cultureVatEntity.getItem(CultureVatMenu.OUTPUT_SLOT_ID), recipe.getResultItem(null))) {
                    throw new GameTestAssertException("Output does not contain " + recipe.getResultItem(null).getItem() + " but instead " + cultureVatEntity.getItem(CultureVatMenu.OUTPUT_SLOT_ID));
                }
            });
            helper.runAtTickTime(12, helper::succeed);
        }
    }

    /**
     * Tests that the fuel item gets added correctly and does get removed when adding the input
     */
    @GameTest(batch = "worktable", template = "worktable", timeoutTicks = 20)
    public static void worktableFuel(GameTestHelper helper) {
        BlockEntity blockEntity = getBlockEntity(MACHINE_POS, helper, WorktableBlock.class);
        if (blockEntity instanceof WorktableBlockEntity worktableEntity) {
            Item fuelItem = ModItems.POTTERY_SHARD.get();
            Item inputItem = ModBlocks.KYLIX_VASE_DAMAGED.get().asItem();
            worktableEntity.setItem(WorktableMenu.FUEL_SLOT_ID, new ItemStack(fuelItem));
            helper.assertContainerContains(MACHINE_POS, fuelItem);
            worktableEntity.setItem(WorktableMenu.INPUT_SLOT_ID, new ItemStack(inputItem));
            helper.succeedOnTickWhen(1, () -> assertContainerDoesNotContain(helper, MACHINE_POS, fuelItem));
        }
    }

    /**
     * Tests that the input item gets added correctly and does not get removed when adding fuel
     */
    @GameTest(batch = "worktable", template = "worktable", timeoutTicks = 20)
    public static void worktableInput(GameTestHelper helper) {
        BlockEntity blockEntity = getBlockEntity(MACHINE_POS, helper, WorktableBlock.class);
        if (blockEntity instanceof WorktableBlockEntity worktableEntity) {
            Item fuelItem = ModItems.POTTERY_SHARD.get();
            Item inputItem = ModBlocks.KYLIX_VASE_DAMAGED.get().asItem();
            worktableEntity.setItem(WorktableMenu.INPUT_SLOT_ID, new ItemStack(inputItem));
            helper.assertContainerContains(MACHINE_POS, inputItem);
            worktableEntity.setItem(WorktableMenu.FUEL_SLOT_ID, new ItemStack(fuelItem));
            helper.runAtTickTime(5, () -> helper.succeedIf(() -> helper.assertContainerContains(MACHINE_POS, inputItem)));
        }
    }

    /**
     * Tests if the correct container data is set
     */
    @GameTest(batch = "worktable", template = "worktable", timeoutTicks = 5)
    public static void worktableData(GameTestHelper helper) {
        BlockEntity blockEntity = getBlockEntity(MACHINE_POS, helper, WorktableBlock.class);
        if (blockEntity instanceof WorktableBlockEntity worktableEntity) {
            Item fuelItem = ModItems.POTTERY_SHARD.get();
            Item inputItem = ModBlocks.KYLIX_VASE_DAMAGED.get().asItem();
            worktableEntity.setItem(WorktableMenu.FUEL_SLOT_ID, new ItemStack(fuelItem));
            ContainerData dataAccess = worktableEntity.getDataAccess();
            helper.runAtTickTime(0, () -> {
                if (dataAccess.get(0) != 0) {
                    throw new GameTestAssertException("LitTime should be 0 but is " + dataAccess.get(0));
                } else if (dataAccess.get(1) != 0) {
                    throw new GameTestAssertException("LitDuration should be 0 but is " + dataAccess.get(1));
                } else if (dataAccess.get(2) != 0) {
                    throw new GameTestAssertException("CookingProgress should be 0 but is " + dataAccess.get(2));
                } else if (dataAccess.get(3) != 0) {
                    throw new GameTestAssertException("CookingTotalTime should be 0 but is " + dataAccess.get(3));
                }
                worktableEntity.setItem(WorktableMenu.INPUT_SLOT_ID, new ItemStack(inputItem));
            });
            int fuelTime = ModRecipes.getWorktableFuelValue(fuelItem);
            int smeltTime = worktableEntity.timeToSmelt(new ItemStack(inputItem), new ItemStack(fuelItem));
            helper.runAtTickTime(1, () -> {
                if (dataAccess.get(0) != fuelTime) {
                    throw new GameTestAssertException("LitTime should be " + fuelTime + " but is " + dataAccess.get(0));
                } else if (dataAccess.get(1) != fuelTime) {
                    throw new GameTestAssertException("LitDuration should be " + fuelTime + " but is " + dataAccess.get(1));
                } else if (dataAccess.get(2) != 1) {
                    throw new GameTestAssertException("CookingProgress should be 1 but is " + dataAccess.get(2));
                } else if (dataAccess.get(3) != smeltTime) {
                    throw new GameTestAssertException("CookingTotalTime should be " + smeltTime + " but is " + dataAccess.get(3));
                }
            });
            helper.runAtTickTime(2, helper::succeed);
        }
    }

    /**
     * Tests if all recipes return the correct output
     */
    @GameTest(batch = "worktable", template = "worktable", timeoutTicks = 2000)
    public static void worktableRecipes(GameTestHelper helper) {
        BlockEntity blockEntity = getBlockEntity(MACHINE_POS, helper, WorktableBlock.class);
        if (blockEntity instanceof WorktableBlockEntity worktableEntity) {
            var list = helper.getLevel().getRecipeManager().getAllRecipesFor(ModRecipes.WORKTABLE_TYPE.get());
           // list = list.stream().filter(worktableRecipe -> worktableRecipe.getFuel().test(new ItemStack(ModItems.POTTERY_SHARD.get()))).toList();
            ContainerData dataAccess = worktableEntity.getDataAccess();
            for (int i = 0; i < list.size(); i++) {
                var recipe = list.get(i);
                Item fuelItem = recipe.getFuel().getItems()[0].getItem();
                Item inputItem = recipe.getInput().getItems()[0].getItem();
                int fuelNeeded = recipe.getDuration() / ModRecipes.getWorktableFuelValue(fuelItem);
                long startTick = i * 3L;
                helper.runAtTickTime(startTick, () -> {
                    worktableEntity.setItem(WorktableMenu.FUEL_SLOT_ID, new ItemStack(fuelItem, fuelNeeded));
                    worktableEntity.setItem(WorktableMenu.INPUT_SLOT_ID, new ItemStack(inputItem));
                });
                int smeltTime = worktableEntity.timeToSmelt(new ItemStack(inputItem), new ItemStack(fuelItem));
                helper.runAtTickTime(startTick + 1, () -> {
                    dataAccess.set(2, smeltTime - 1);
                });
                helper.runAtTickTime(startTick + 2, () -> {
                    if (!ItemStack.isSameItem(worktableEntity.getItem(CultureVatMenu.OUTPUT_SLOT_ID), recipe.getResultItem(null))) {
                        throw new GameTestAssertException("Output does not contain " + recipe.getResultItem(null).getItem() + " but instead " + worktableEntity.getItem(WorktableMenu.OUTPUT_SLOT_ID));
                    }
                    worktableEntity.setItem(WorktableMenu.OUTPUT_SLOT_ID, ItemStack.EMPTY);
                });
            }
            helper.runAtTickTime(list.size() * 3L, helper::succeed);
        }
    }

    /**
     * Tests if the correct recipe result get added at the correct point in time
     */
    @GameTest(batch = "worktable", template = "worktable", timeoutTicks = 20)
    public static void worktableResult(GameTestHelper helper) {
        BlockEntity blockEntity = getBlockEntity(MACHINE_POS, helper, WorktableBlock.class);
        if (blockEntity instanceof WorktableBlockEntity worktableEntity) {
            Item fuelItem = ModItems.POTTERY_SHARD.get();
            Item inputItem = ModBlocks.KYLIX_VASE_DAMAGED.get().asItem();
            var recipe = ModRecipes.getWorktableRecipeForItem(new WithFuelRecipe.ContainerWithAnyFuel(new ItemStack(inputItem), new ItemStack(fuelItem)), helper.getLevel());
            int fuelNeeded = recipe.getDuration() / ModRecipes.getWorktableFuelValue(fuelItem);
            worktableEntity.setItem(WorktableMenu.FUEL_SLOT_ID, new ItemStack(fuelItem, fuelNeeded));
            worktableEntity.setItem(WorktableMenu.INPUT_SLOT_ID, new ItemStack(inputItem));
            ContainerData dataAccess = worktableEntity.getDataAccess();
            int smeltTime = worktableEntity.timeToSmelt(new ItemStack(inputItem), new ItemStack(fuelItem));
            helper.runAtTickTime(1, () -> dataAccess.set(2, smeltTime - 10));
            helper.runAtTickTime(11, () -> {
                if (!ItemStack.isSameItem(worktableEntity.getItem(CultureVatMenu.OUTPUT_SLOT_ID), recipe.getResultItem(null))) {
                    throw new GameTestAssertException("Output does not contain " + recipe.getResultItem(null).getItem() + " but instead " + worktableEntity.getItem(WorktableMenu.OUTPUT_SLOT_ID));
                }
            });
            helper.runAtTickTime(12, helper::succeed);
        }
    }

    /**
     * Tests that the input item gets added correctly and only gets removed at the end
     */
    @GameTest(batch = "analyzer", template = "analyzer", timeoutTicks = 20)
    public static void analyzerInput(GameTestHelper helper) {
        BlockEntity blockEntity = getBlockEntity(MACHINE_POS, helper, AnalyzerBlock.class);
        if (blockEntity instanceof AnalyzerBlockEntity analyzerEntity) {
            Item inputItem = Blocks.WHITE_WOOL.asItem();
            analyzerEntity.setItem(0, new ItemStack(inputItem));
            helper.assertContainerContains(MACHINE_POS, inputItem);
            helper.runAtTickTime(1, () -> {
                analyzerEntity.getDataAccess().set(0, AnalyzerMenu.ANALYZE_DURATION - 1);
                helper.assertContainerContains(MACHINE_POS, inputItem);
            });
            helper.runAtTickTime(2, () -> helper.succeedIf(() -> assertContainerDoesNotContain(helper, MACHINE_POS, inputItem)));
        }
    }

    /**
     * Tests if the correct container data is set
     */
    @GameTest(batch = "analyzer", template = "analyzer", timeoutTicks = 5)
    public static void analyzerData(GameTestHelper helper) {
        BlockEntity blockEntity = getBlockEntity(MACHINE_POS, helper, AnalyzerBlock.class);
        if (blockEntity instanceof AnalyzerBlockEntity analyzerEntity) {
            Item inputItem = Blocks.WHITE_WOOL.asItem();
            ContainerData dataAccess = analyzerEntity.getDataAccess();
            helper.runAtTickTime(0, () -> {
                if (dataAccess.get(0) != 0) {
                    throw new GameTestAssertException("CookingProgress should be 0 but is " + dataAccess.get(0));
                }
                analyzerEntity.setItem(0, new ItemStack(inputItem));
            });
            helper.runAtTickTime(1, () -> {
                if (dataAccess.get(0) != 1) {
                    throw new GameTestAssertException("CookingProgress should be 1 but is " + dataAccess.get(2));
                }
            });
            helper.runAtTickTime(2, helper::succeed);
        }
    }

    /**
     * Tests that all recipes return the correct output
     */
    @GameTest(batch = "analyzer", template = "analyzer", timeoutTicks = 2000)
    public static void analyzerRecipes(GameTestHelper helper) {
        BlockEntity blockEntity = getBlockEntity(MACHINE_POS, helper, AnalyzerBlock.class);
        if (blockEntity instanceof AnalyzerBlockEntity analyzerEntity) {
            var list = helper.getLevel().getRecipeManager().getAllRecipesFor(ModRecipes.ANALYZER_TYPE.get());
            ContainerData dataAccess = analyzerEntity.getDataAccess();
            for (int i = 0; i < list.size(); i++) {
                var recipe = list.get(i);
                Item inputItem = recipe.getInput().getItems()[0].getItem();
                long startTick = i * 3L;
                helper.runAtTickTime(startTick, () -> analyzerEntity.setItem(0, new ItemStack(inputItem)));
                int smeltTime = AnalyzerMenu.ANALYZE_DURATION;
                helper.runAtTickTime(startTick + 1, () -> dataAccess.set(0, smeltTime - 1));
                helper.runAtTickTime(startTick + 2, () -> {
                    if (recipe.getWeightedOutputs().values().stream().noneMatch(itemStack -> ItemStack.isSameItem(itemStack, analyzerEntity.getItem(9)))) {
                        throw new GameTestAssertException("Output does not contain any results from " + recipe.getId() + " but instead " + analyzerEntity.getItem(9));
                    }
                    analyzerEntity.setItem(9, ItemStack.EMPTY);
                });
            }
            helper.runAtTickTime(list.size() * 3L, helper::succeed);
        }
    }
    //TODO: Test multiple analyzer inputs/output slots

    /**
     * Tests that the input item gets added correctly and only gets removed at the end
     */
    @GameTest(batch = "sifter", template = "sifter", timeoutTicks = 20)
    public static void sifterInput(GameTestHelper helper) {
        BlockEntity blockEntity = getBlockEntity(MACHINE_POS, helper, SifterBlock.class);
        if (blockEntity instanceof SifterBlockEntity sifterEntity) {
            Item inputItem = Blocks.SAND.asItem();
            sifterEntity.setItem(0, new ItemStack(inputItem));
            helper.assertContainerContains(MACHINE_POS, inputItem);
            helper.runAtTickTime(1, () -> {
                sifterEntity.getDataAccess().set(0, SifterMenu.SIFTER_DURATION - 1);
                helper.assertContainerContains(MACHINE_POS, inputItem);
            });
            helper.runAtTickTime(2, () -> helper.succeedIf(() -> assertContainerDoesNotContain(helper, MACHINE_POS, inputItem)));
        }
    }

    /**
     * Tests if the correct container data is set
     */
    @GameTest(batch = "sifter", template = "sifter", timeoutTicks = 5)
    public static void sifterData(GameTestHelper helper) {
        BlockEntity blockEntity = getBlockEntity(MACHINE_POS, helper, SifterBlock.class);
        if (blockEntity instanceof SifterBlockEntity sifterEntity) {
            Item inputItem = Blocks.SAND.asItem();
            ContainerData dataAccess = sifterEntity.getDataAccess();
            helper.runAtTickTime(0, () -> {
                if (dataAccess.get(0) != 0) {
                    throw new GameTestAssertException("CookingProgress should be 0 but is " + dataAccess.get(0));
                }
                sifterEntity.setItem(0, new ItemStack(inputItem));
            });
            helper.runAtTickTime(1, () -> {
                if (dataAccess.get(0) != 1) {
                    throw new GameTestAssertException("CookingProgress should be 1 but is " + dataAccess.get(0));
                }
            });
            helper.runAtTickTime(2, helper::succeed);
        }
    }

    /**
     * Tests that all recipes return the correct output
     */
    @GameTest(batch = "sifter", template = "sifter", timeoutTicks = 2000)
    public static void sifterRecipes(GameTestHelper helper) {
        BlockEntity blockEntity = getBlockEntity(MACHINE_POS, helper, SifterBlock.class);
        if (blockEntity instanceof SifterBlockEntity sifterEntity) {
            var list = helper.getLevel().getRecipeManager().getAllRecipesFor(ModRecipes.SIFTER_TYPE.get());
            ContainerData dataAccess = sifterEntity.getDataAccess();
            for (int i = 0; i < list.size(); i++) {
                var recipe = list.get(i);
                Item inputItem = recipe.getInput().getItems()[0].getItem();
                long startTick = i * 3L;
                helper.runAtTickTime(startTick, () -> sifterEntity.setItem(0, new ItemStack(inputItem)));
                helper.runAtTickTime(startTick + 1, () -> dataAccess.set(0, SifterMenu.SIFTER_DURATION - 1));
                helper.runAtTickTime(startTick + 2, () -> {
                    if (recipe.getWeightedOutputs().values().stream().noneMatch(itemStack -> ItemStack.isSameItem(itemStack, sifterEntity.getItem(1)))) {
                        throw new GameTestAssertException("Output does not contain any results from " + recipe.getId() + " but instead " + sifterEntity.getItem(1));
                    }
                    sifterEntity.setItem(1, ItemStack.EMPTY);
                });
            }
            helper.runAtTickTime(list.size() * 3L, helper::succeed);
        }
    }


    private static void assertContainerDoesNotContain(GameTestHelper helper, BlockPos pos, Item item) {
        BlockPos blockPos = helper.absolutePos(pos);
        BlockEntity blockEntity = helper.getLevel().getBlockEntity(blockPos);
        if (blockEntity instanceof BaseContainerBlockEntity && ((BaseContainerBlockEntity) blockEntity).countItem(item) > 0) {
            throw new GameTestAssertException("Container should not contain: " + item);
        }
    }
}
