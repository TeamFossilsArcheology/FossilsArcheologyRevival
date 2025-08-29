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
import com.github.teamfossilsarcheology.fossil.inventory.CultureVatMenu;
import com.github.teamfossilsarcheology.fossil.inventory.WorktableMenu;
import com.github.teamfossilsarcheology.fossil.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestAssertException;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HopperBlock;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraftforge.gametest.GameTestHolder;
import net.minecraftforge.gametest.PrefixGameTestTemplate;

@PrefixGameTestTemplate(value = false)
@GameTestHolder(FossilMod.MOD_ID)
public class HopperTests {
    private static final BlockPos MACHINE_POS = new BlockPos(1, 2, 1);
    private static final Direction[] directions = new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};

    private static void assertContainerContainsItemInSlot(GameTestHelper helper, BlockPos pos, int slot, Item item, int count) {
        BlockPos blockPos = helper.absolutePos(pos);
        BlockEntity blockEntity = helper.getLevel().getBlockEntity(blockPos);
        if (blockEntity instanceof BaseContainerBlockEntity containerBlockEntity) {
            if (!containerBlockEntity.getItem(slot).is(item)) {
                throw new GameTestAssertException("Container slot contains wrong item. Expected: " + item + " but got: " + containerBlockEntity.getItem(slot));
            }
            if (containerBlockEntity.getItem(slot).getCount() != count) {
                throw new GameTestAssertException("Container slot contains wrong amount. Expected: " + count + " but got: " + containerBlockEntity.getItem(slot).getCount());
            }
        }
    }

    /**
     * Tests that hoppers can insert fuel from all sides
     */
    @GameTest(batch = "culture_vat", template = "culture_vat_hopper", timeoutTicks = 11)
    public static void cultureVatHopperFuel(GameTestHelper helper) {
        BlockEntity blockEntity = RecipeTests.getBlockEntity(MACHINE_POS, helper, CultureVatBlock.class);
        if (blockEntity instanceof CultureVatBlockEntity cultureVatEntity) {
            helper.assertContainerEmpty(MACHINE_POS);
            for (int i = 0; i < 4; i++) {
                BlockPos sidePos = MACHINE_POS.relative(directions[i]);
                helper.setBlock(sidePos, Blocks.HOPPER.defaultBlockState().setValue(HopperBlock.FACING, directions[i].getOpposite()));
                BlockEntity hopper = RecipeTests.getBlockEntity(sidePos, helper, HopperBlock.class);
                if (hopper instanceof HopperBlockEntity hopperBlockEntity) {
                    Item fuelItem = ModItems.BIO_GOO.get();
                    helper.runAtTickTime(i * 2L, () -> hopperBlockEntity.setItem(0, new ItemStack(fuelItem)));
                    final int expectedCount = i + 1;
                    helper.runAtTickTime(i * 2L + 1L, () -> {
                        assertContainerContainsItemInSlot(helper, MACHINE_POS, CultureVatMenu.FUEL_SLOT_ID, fuelItem, expectedCount);
                    });
                }
            }
            helper.runAtTickTime(10, helper::succeed);
        }
    }

    /**
     * Tests that hoppers can extract outputs from the bottom
     */
    @GameTest(batch = "culture_vat", template = "culture_vat_hopper", timeoutTicks = 5)
    public static void cultureVatHopperOutput(GameTestHelper helper) {
        BlockEntity blockEntity = RecipeTests.getBlockEntity(MACHINE_POS, helper, CultureVatBlock.class);
        if (blockEntity instanceof CultureVatBlockEntity cultureVatEntity) {
            BlockPos downPos = MACHINE_POS.below();
            helper.setBlock(downPos, Blocks.HOPPER);
            helper.assertContainerEmpty(downPos);
            BlockEntity hopper = RecipeTests.getBlockEntity(downPos, helper, HopperBlock.class);
            if (hopper instanceof HopperBlockEntity) {
                cultureVatEntity.setItem(CultureVatMenu.OUTPUT_SLOT_ID, new ItemStack(PrehistoricEntityInfo.ALLOSAURUS.eggItem));
                helper.runAtTickTime(1, () -> helper.assertContainerContains(downPos, PrehistoricEntityInfo.ALLOSAURUS.eggItem));
                helper.runAtTickTime(4, helper::succeed);
            }
        }
    }

    /**
     * Tests that hoppers can insert inputs from the top
     */
    @GameTest(batch = "culture_vat", template = "culture_vat_hopper", timeoutTicks = 5)
    public static void cultureVatHopperInput(GameTestHelper helper) {
        BlockEntity blockEntity = RecipeTests.getBlockEntity(MACHINE_POS, helper, CultureVatBlock.class);
        if (blockEntity instanceof CultureVatBlockEntity) {
            helper.assertContainerEmpty(MACHINE_POS);
            BlockPos upPos = MACHINE_POS.above();
            helper.setBlock(upPos, Blocks.HOPPER);
            BlockEntity hopper = RecipeTests.getBlockEntity(upPos, helper, HopperBlock.class);
            if (hopper instanceof HopperBlockEntity hopperBlockEntity) {
                hopperBlockEntity.setItem(0, new ItemStack(PrehistoricEntityInfo.ALLOSAURUS.dnaItem));
                helper.runAtTickTime(1, () -> assertContainerContainsItemInSlot(helper, MACHINE_POS, CultureVatMenu.INPUT_SLOT_ID, PrehistoricEntityInfo.ALLOSAURUS.dnaItem, 1));
                helper.runAtTickTime(2, helper::succeed);
            }
        }
    }

    /**
     * Tests that hoppers can insert fuel from all sides
     */
    @GameTest(batch = "worktable", template = "worktable_hopper", timeoutTicks = 11)
    public static void worktableHopperFuel(GameTestHelper helper) {
        BlockEntity blockEntity = RecipeTests.getBlockEntity(MACHINE_POS, helper, WorktableBlock.class);
        if (blockEntity instanceof WorktableBlockEntity worktableEntity) {
            helper.assertContainerEmpty(MACHINE_POS);
            for (int i = 0; i < 4; i++) {
                BlockPos sidePos = MACHINE_POS.relative(directions[i]);
                helper.setBlock(sidePos, Blocks.HOPPER.defaultBlockState().setValue(HopperBlock.FACING, directions[i].getOpposite()));
                BlockEntity hopper = RecipeTests.getBlockEntity(sidePos, helper, HopperBlock.class);
                if (hopper instanceof HopperBlockEntity hopperBlockEntity) {
                    Item fuelItem = ModItems.POTTERY_SHARD.get();
                    helper.runAtTickTime(i * 2L, () -> hopperBlockEntity.setItem(0, new ItemStack(fuelItem)));
                    final int expectedCount = i + 1;
                    helper.runAtTickTime(i * 2L + 1L, () -> {
                        assertContainerContainsItemInSlot(helper, MACHINE_POS, WorktableMenu.FUEL_SLOT_ID, fuelItem, expectedCount);
                    });
                }
            }
            helper.runAtTickTime(10, helper::succeed);
        }
    }

    /**
     * Tests that hoppers can extract outputs from the bottom
     */
    @GameTest(batch = "worktable", template = "worktable_hopper", timeoutTicks = 5)
    public static void worktableHopperOutput(GameTestHelper helper) {
        BlockEntity blockEntity = RecipeTests.getBlockEntity(MACHINE_POS, helper, WorktableBlock.class);
        if (blockEntity instanceof WorktableBlockEntity worktableEntity) {
            BlockPos downPos = MACHINE_POS.below();
            helper.setBlock(downPos, Blocks.HOPPER);
            helper.assertContainerEmpty(downPos);
            BlockEntity hopper = RecipeTests.getBlockEntity(downPos, helper, HopperBlock.class);
            if (hopper instanceof HopperBlockEntity) {
                worktableEntity.setItem(WorktableMenu.OUTPUT_SLOT_ID, new ItemStack(ModBlocks.AMPHORA_VASE_RESTORED.get()));
                helper.runAtTickTime(1, () -> helper.assertContainerContains(downPos, ModBlocks.AMPHORA_VASE_RESTORED.get().asItem()));
                helper.runAtTickTime(4, helper::succeed);
            }
        }
    }

    /**
     * Tests that hoppers can insert inputs from the top
     */
    @GameTest(batch = "worktable", template = "worktable_hopper", timeoutTicks = 5)
    public static void worktableHopperInput(GameTestHelper helper) {
        BlockEntity blockEntity = RecipeTests.getBlockEntity(MACHINE_POS, helper, WorktableBlock.class);
        if (blockEntity instanceof WorktableBlockEntity) {
            helper.assertContainerEmpty(MACHINE_POS);
            BlockPos upPos = MACHINE_POS.above();
            helper.setBlock(upPos, Blocks.HOPPER);
            BlockEntity hopper = RecipeTests.getBlockEntity(upPos, helper, HopperBlock.class);
            if (hopper instanceof HopperBlockEntity hopperBlockEntity) {
                hopperBlockEntity.setItem(0, new ItemStack(ModBlocks.AMPHORA_VASE_DAMAGED.get()));
                helper.runAtTickTime(1, () -> assertContainerContainsItemInSlot(helper, MACHINE_POS,
                        WorktableMenu.INPUT_SLOT_ID, ModBlocks.AMPHORA_VASE_DAMAGED.get().asItem(), 1));
                helper.runAtTickTime(2, helper::succeed);
            }
        }
    }

    /**
     * Tests that hoppers can insert inputs from all sides
     */
    @GameTest(batch = "analyzer", template = "analyzer_hopper", timeoutTicks = 11)
    public static void analyzerHopperInputSide(GameTestHelper helper) {
        BlockEntity blockEntity = RecipeTests.getBlockEntity(MACHINE_POS, helper, AnalyzerBlock.class);
        if (blockEntity instanceof AnalyzerBlockEntity analyzerEntity) {
            helper.assertContainerEmpty(MACHINE_POS);
            for (int i = 0; i < 4; i++) {
                BlockPos sidePos = MACHINE_POS.relative(directions[i]);
                helper.setBlock(sidePos, Blocks.HOPPER.defaultBlockState().setValue(HopperBlock.FACING, directions[i].getOpposite()));
                BlockEntity hopper = RecipeTests.getBlockEntity(sidePos, helper, HopperBlock.class);
                if (hopper instanceof HopperBlockEntity hopperBlockEntity) {
                    Item inputItem = ModItems.BIO_FOSSIL.get();
                    helper.runAtTickTime(i * 2L, () -> hopperBlockEntity.setItem(0, new ItemStack(inputItem)));
                    final int expectedCount = i + 1;
                    helper.runAtTickTime(i * 2L + 1L, () -> {
                        assertContainerContainsItemInSlot(helper, MACHINE_POS, 0, inputItem, expectedCount);
                    });
                }
            }
            helper.runAtTickTime(10, helper::succeed);
        }
    }

    /**
     * Tests that hoppers can insert inputs from the top
     */
    @GameTest(batch = "analyzer", template = "analyzer_hopper", timeoutTicks = 5)
    public static void analyzerHopperInput(GameTestHelper helper) {
        BlockEntity blockEntity = RecipeTests.getBlockEntity(MACHINE_POS, helper, AnalyzerBlock.class);
        if (blockEntity instanceof AnalyzerBlockEntity) {
            helper.assertContainerEmpty(MACHINE_POS);
            BlockPos upPos = MACHINE_POS.above();
            helper.setBlock(upPos, Blocks.HOPPER);
            BlockEntity hopper = RecipeTests.getBlockEntity(upPos, helper, HopperBlock.class);
            if (hopper instanceof HopperBlockEntity hopperBlockEntity) {
                hopperBlockEntity.setItem(0, new ItemStack(ModItems.BIO_FOSSIL.get()));
                helper.runAtTickTime(1, () -> assertContainerContainsItemInSlot(helper, MACHINE_POS, 0, ModItems.BIO_FOSSIL.get(), 1));
                helper.runAtTickTime(2, helper::succeed);
            }
        }
    }

    /**
     * Tests that hoppers can extract outputs from the bottom
     */
    @GameTest(batch = "analyzer", template = "analyzer_hopper", timeoutTicks = 5)
    public static void analyzerHopperOutput(GameTestHelper helper) {
        BlockEntity blockEntity = RecipeTests.getBlockEntity(MACHINE_POS, helper, AnalyzerBlock.class);
        if (blockEntity instanceof AnalyzerBlockEntity analyzerEntity) {
            BlockPos downPos = MACHINE_POS.below();
            helper.setBlock(downPos, Blocks.HOPPER);
            helper.assertContainerEmpty(downPos);
            BlockEntity hopper = RecipeTests.getBlockEntity(downPos, helper, HopperBlock.class);
            if (hopper instanceof HopperBlockEntity) {
                analyzerEntity.setItem(9, new ItemStack(PrehistoricEntityInfo.ALLOSAURUS.dnaItem));
                helper.runAtTickTime(1, () -> helper.assertContainerContains(downPos, PrehistoricEntityInfo.ALLOSAURUS.dnaItem));
                helper.runAtTickTime(4, helper::succeed);
            }
        }
    }

    /**
     * Tests that hoppers can insert inputs from all sides
     */
    @GameTest(batch = "sifter", template = "sifter_hopper", timeoutTicks = 11)
    public static void sifterHopperInputSide(GameTestHelper helper) {
        BlockEntity blockEntity = RecipeTests.getBlockEntity(MACHINE_POS, helper, SifterBlock.class);
        if (blockEntity instanceof SifterBlockEntity sifterEntity) {
            helper.assertContainerEmpty(MACHINE_POS);
            for (int i = 0; i < 4; i++) {
                BlockPos sidePos = MACHINE_POS.relative(directions[i]);
                helper.setBlock(sidePos, Blocks.HOPPER.defaultBlockState().setValue(HopperBlock.FACING, directions[i].getOpposite()));
                BlockEntity hopper = RecipeTests.getBlockEntity(sidePos, helper, HopperBlock.class);
                if (hopper instanceof HopperBlockEntity hopperBlockEntity) {
                    Item inputItem = Blocks.SAND.asItem();
                    helper.runAtTickTime(i * 2L, () -> hopperBlockEntity.setItem(0, new ItemStack(inputItem)));
                    final int expectedCount = i + 1;
                    helper.runAtTickTime(i * 2L + 1L, () -> {
                        assertContainerContainsItemInSlot(helper, MACHINE_POS, 0, inputItem, expectedCount);
                    });
                }
            }
            helper.runAtTickTime(10, helper::succeed);
        }
    }

    /**
     * Tests that hoppers can extract outputs from the bottom
     */
    @GameTest(batch = "sifter", template = "sifter_hopper", timeoutTicks = 5)
    public static void sifterHopperOutput(GameTestHelper helper) {
        BlockEntity blockEntity = RecipeTests.getBlockEntity(MACHINE_POS, helper, SifterBlock.class);
        if (blockEntity instanceof SifterBlockEntity sifterEntity) {
            BlockPos downPos = MACHINE_POS.below();
            helper.setBlock(downPos, Blocks.HOPPER);
            helper.assertContainerEmpty(downPos);
            BlockEntity hopper = RecipeTests.getBlockEntity(downPos, helper, HopperBlock.class);
            if (hopper instanceof HopperBlockEntity) {
                sifterEntity.setItem(1, new ItemStack(ModItems.BIO_FOSSIL.get()));
                helper.runAtTickTime(1, () -> helper.assertContainerContains(downPos, ModItems.BIO_FOSSIL.get()));
                helper.runAtTickTime(4, helper::succeed);
            }
        }
    }

    /**
     * Tests that hoppers can insert inputs from the top
     */
    @GameTest(batch = "sifter", template = "sifter_hopper", timeoutTicks = 5)
    public static void sifterHopperInput(GameTestHelper helper) {
        BlockEntity blockEntity = RecipeTests.getBlockEntity(MACHINE_POS, helper, SifterBlock.class);
        if (blockEntity instanceof SifterBlockEntity) {
            helper.assertContainerEmpty(MACHINE_POS);
            BlockPos upPos = MACHINE_POS.above();
            helper.setBlock(upPos, Blocks.HOPPER);
            BlockEntity hopper = RecipeTests.getBlockEntity(upPos, helper, HopperBlock.class);
            if (hopper instanceof HopperBlockEntity hopperBlockEntity) {
                hopperBlockEntity.setItem(0, new ItemStack(Blocks.SAND));
                helper.runAtTickTime(1, () -> assertContainerContainsItemInSlot(helper, MACHINE_POS, 0, Blocks.SAND.asItem(), 1));
                helper.runAtTickTime(2, helper::succeed);
            }
        }
    }
}
