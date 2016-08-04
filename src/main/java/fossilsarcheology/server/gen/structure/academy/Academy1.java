package fossilsarcheology.server.gen.structure.academy;

import fossilsarcheology.server.block.FABlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

public class Academy1 {
    public static final int[][][][] blockArrayAcademy = {
            // This is where all the pos values go (i, j, k). They are broke
            // down by
            // layer. This is how the array works building from x to z and by
            // layer y,
            // then they are finished by removing all the world.setBlock and
            // pos coordinates leaving something like this
            // {Block.getIdFromBlock(Blocks.stone)},
            {
                    // y = 0 LAYER1
                    {
                            // x = 0
                            {}, // {Block.getIdFromBlock(Blocks.grass)},
                            {}, // {Block.getIdFromBlock(Blocks.grass)},
                            {}, // {Block.getIdFromBlock(Blocks.grass)},
                            {}, // {Block.getIdFromBlock(Blocks.grass)},
                            {}, // {Block.getIdFromBlock(Blocks.grass)},
                            {}, // {Block.getIdFromBlock(Blocks.grass)},
                            {}, // {Block.getIdFromBlock(Blocks.grass)},
                            {}, // {Block.getIdFromBlock(Blocks.grass)},
                            {}, // {Block.getIdFromBlock(Blocks.grass)},
                            {}, // {Block.getIdFromBlock(Blocks.grass)},
                            {}, // {Block.getIdFromBlock(Blocks.grass)},
                            {}, // {Block.getIdFromBlock(Blocks.grass)},
                            {}, // {Block.getIdFromBlock(Blocks.grass)},
                            {}, // {Block.getIdFromBlock(Blocks.grass)},
                            {}, // {Block.getIdFromBlock(Blocks.grass)},
                            {}, // {Block.getIdFromBlock(Blocks.grass)},
                            {}, // {Block.getIdFromBlock(Blocks.grass)},
                            {}, // {Block.getIdFromBlock(Blocks.grass)},
                            {}, // {Block.getIdFromBlock(Blocks.grass)},
                            {}, // {Block.getIdFromBlock(Blocks.grass)},
                            {}, // {Block.getIdFromBlock(Blocks.grass)},
                            {}, // {Block.getIdFromBlock(Blocks.grass)},
                            {}, // {Block.getIdFromBlock(Blocks.grass)},
                            {}, // {Block.getIdFromBlock(Blocks.grass)},
                            {}, // {Block.getIdFromBlock(Blocks.grass)},
                            {}, // {Block.getIdFromBlock(Blocks.grass)},
                            {}, // {Block.getIdFromBlock(Blocks.grass)},
                    }, {
                    // x = 1
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    { Block.getIdFromBlock(Blocks.dirt) }, { Block.getIdFromBlock(Blocks.dirt) }, {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    { Block.getIdFromBlock(Blocks.dirt) }, { Block.getIdFromBlock(Blocks.dirt) }, {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, {}, // {Block.getIdFromBlock(Blocks.grass)},
            }, {
                    // x = 2
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    { Block.getIdFromBlock(Blocks.dirt) }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, {}, // {Block.getIdFromBlock(Blocks.grass)},
            }, {
                    // x = 3
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { Block.getIdFromBlock(Blocks.stonebrick), 1 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { Block.getIdFromBlock(Blocks.stonebrick), 2 }, { Block.getIdFromBlock(Blocks.stonebrick), 2 }, { Block.getIdFromBlock(Blocks.stonebrick), 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, {}, // {Block.getIdFromBlock(Blocks.grass)},
            }, {
                    // x = 4
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { Block.getIdFromBlock(Blocks.stonebrick), 2 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { Block.getIdFromBlock(Blocks.stonebrick), 2 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
            }, {
                    // x = 5
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    { Block.getIdFromBlock(Blocks.dirt) }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
            }, {
                    // x = 6
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    { Block.getIdFromBlock(Blocks.dirt) }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { Block.getIdFromBlock(Blocks.stonebrick), 2 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
            }, {
                    // x = 7
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, {}, // {Block.getIdFromBlock(Blocks.grass)},
            }, {
                    // x = 8
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { Block.getIdFromBlock(Blocks.stonebrick), 2 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.glass) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { Block.getIdFromBlock(Blocks.double_stone_slab), 3 }, { Block.getIdFromBlock(Blocks.cobblestone) }, { Block.getIdFromBlock(Blocks.double_stone_slab), 3 }, { Block.getIdFromBlock(Blocks.double_stone_slab), 3 }, { Block.getIdFromBlock(Blocks.cobblestone) }, { Block.getIdFromBlock(Blocks.double_stone_slab), 3 }, { Block.getIdFromBlock(Blocks.double_stone_slab), 3 }, { Block.getIdFromBlock(Blocks.double_stone_slab), 3 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, {}, // {Block.getIdFromBlock(Blocks.grass)},
            }, {
                    // x = 9
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { Block.getIdFromBlock(Blocks.double_stone_slab), 3 }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.glass) }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { Block.getIdFromBlock(Blocks.double_stone_slab), 3 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.glass) }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { Block.getIdFromBlock(Blocks.double_stone_slab), 3 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
            }, {
                    // x = 10
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    { Block.getIdFromBlock(Blocks.dirt) }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { Block.getIdFromBlock(Blocks.double_stone_slab), 3 }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { Block.getIdFromBlock(Blocks.double_stone_slab), 3 }, { Block.getIdFromBlock(Blocks.double_stone_slab), 3 }, { Block.getIdFromBlock(Blocks.double_stone_slab), 3 }, { Block.getIdFromBlock(Blocks.double_stone_slab), 3 }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
            }, {
                    // x = 11
                    { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { Block.getIdFromBlock(Blocks.stonebrick), 2 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { Block.getIdFromBlock(Blocks.double_stone_slab), 3 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { Block.getIdFromBlock(Blocks.stonebrick), 2 }, { Block.getIdFromBlock(Blocks.double_stone_slab), 3 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { Block.getIdFromBlock(Blocks.double_stone_slab), 3 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
            }, {
                    // x = 12
                    { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { Block.getIdFromBlock(Blocks.double_stone_slab), 3 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { Block.getIdFromBlock(Blocks.stonebrick), 2 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { Block.getIdFromBlock(Blocks.double_stone_slab), 3 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { Block.getIdFromBlock(Blocks.stonebrick), 2 }, { Block.getIdFromBlock(Blocks.double_stone_slab), 3 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
            }, {
                    // x = 13
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { Block.getIdFromBlock(Blocks.double_stone_slab), 3 }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { Block.getIdFromBlock(Blocks.double_stone_slab), 3 }, { Block.getIdFromBlock(Blocks.double_stone_slab), 3 }, { Block.getIdFromBlock(Blocks.cobblestone) }, { Block.getIdFromBlock(Blocks.double_stone_slab), 3 }, { Block.getIdFromBlock(Blocks.double_stone_slab), 3 }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
            }, {
                    // x = 14
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    { Block.getIdFromBlock(Blocks.dirt) }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { Block.getIdFromBlock(Blocks.double_stone_slab), 3 }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.glass) }, { Block.getIdFromBlock(Blocks.stonebrick), 2 }, { Block.getIdFromBlock(Blocks.double_stone_slab), 3 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.glass) }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { Block.getIdFromBlock(Blocks.double_stone_slab), 3 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
            }, {
                    // x = 15
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.glass) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { Block.getIdFromBlock(Blocks.double_stone_slab), 3 }, { Block.getIdFromBlock(Blocks.double_stone_slab), 3 }, { Block.getIdFromBlock(Blocks.double_stone_slab), 3 }, { Block.getIdFromBlock(Blocks.double_stone_slab), 3 }, { Block.getIdFromBlock(Blocks.double_stone_slab), 3 }, { Block.getIdFromBlock(Blocks.double_stone_slab), 3 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { Block.getIdFromBlock(Blocks.double_stone_slab), 3 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, {}, // {Block.getIdFromBlock(Blocks.grass)},
            }, {
                    // x = 16
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, {}, // {Block.getIdFromBlock(Blocks.grass)},
            }, {
                    // x = 17
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
            }, {
                    // x = 18
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
            }, {
                    // x = 19
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { Block.getIdFromBlock(Blocks.stonebrick), 1 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
            }, {
                    // x = 20
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { Block.getIdFromBlock(Blocks.stonebrick), 2 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, {}, // {Block.getIdFromBlock(Blocks.grass)},
            }, {
                    // x = 21
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, {}, // {Block.getIdFromBlock(Blocks.grass)},
            }, {
                    // x = 22
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, {}, // {Block.getIdFromBlock(Blocks.grass)},
            }, {
                    // x = 23
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
            } }, {
            // y = 0 LAYER1
            {
                    // x = 0
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
                    {}, // {Block.getIdFromBlock(Blocks.grass)},
            }, {
            // x = 1
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            { Block.getIdFromBlock(Blocks.dirt) }, { Block.getIdFromBlock(Blocks.dirt) }, {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            { Block.getIdFromBlock(Blocks.dirt) }, { Block.getIdFromBlock(Blocks.dirt) }, {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, {}, // {Block.getIdFromBlock(Blocks.grass)},
    }, {
            // x = 2
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            { Block.getIdFromBlock(Blocks.dirt) }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, {}, // {Block.getIdFromBlock(Blocks.grass)},
    }, {
            // x = 3
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { Block.getIdFromBlock(Blocks.stonebrick), 1 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { Block.getIdFromBlock(Blocks.stonebrick), 2 }, { Block.getIdFromBlock(Blocks.stonebrick), 2 }, { Block.getIdFromBlock(Blocks.stonebrick), 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, {}, // {Block.getIdFromBlock(Blocks.grass)},
    }, {
            // x = 4
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { Block.getIdFromBlock(Blocks.stonebrick), 2 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { Block.getIdFromBlock(Blocks.stonebrick), 2 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
    }, {
            // x = 5
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            { Block.getIdFromBlock(Blocks.dirt) }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
    }, {
            // x = 6
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            { Block.getIdFromBlock(Blocks.dirt) }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { Block.getIdFromBlock(Blocks.stonebrick), 2 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
    }, {
            // x = 7
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, {}, // {Block.getIdFromBlock(Blocks.grass)},
    }, {
            // x = 8
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { Block.getIdFromBlock(Blocks.stonebrick), 2 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.glass) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { Block.getIdFromBlock(Blocks.double_stone_slab), 3 }, { Block.getIdFromBlock(Blocks.cobblestone) }, { Block.getIdFromBlock(Blocks.double_stone_slab), 3 }, { Block.getIdFromBlock(Blocks.double_stone_slab), 3 }, { Block.getIdFromBlock(Blocks.cobblestone) }, { Block.getIdFromBlock(Blocks.double_stone_slab), 3 }, { Block.getIdFromBlock(Blocks.double_stone_slab), 3 }, { Block.getIdFromBlock(Blocks.double_stone_slab), 3 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, {}, // {Block.getIdFromBlock(Blocks.grass)},
    }, {
            // x = 9
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { Block.getIdFromBlock(Blocks.double_stone_slab), 3 }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.glass) }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { Block.getIdFromBlock(Blocks.double_stone_slab), 3 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.glass) }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { Block.getIdFromBlock(Blocks.double_stone_slab), 3 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
    }, {
            // x = 10
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            { Block.getIdFromBlock(Blocks.dirt) }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { Block.getIdFromBlock(Blocks.double_stone_slab), 3 }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { Block.getIdFromBlock(Blocks.double_stone_slab), 3 }, { Block.getIdFromBlock(Blocks.double_stone_slab), 3 }, { Block.getIdFromBlock(Blocks.double_stone_slab), 3 }, { Block.getIdFromBlock(Blocks.double_stone_slab), 3 }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
    }, {
            // x = 11
            { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { Block.getIdFromBlock(Blocks.stonebrick), 2 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { Block.getIdFromBlock(Blocks.double_stone_slab), 3 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { Block.getIdFromBlock(Blocks.stonebrick), 2 }, { Block.getIdFromBlock(Blocks.double_stone_slab), 3 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { Block.getIdFromBlock(Blocks.double_stone_slab), 3 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
    }, {
            // x = 12
            { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { Block.getIdFromBlock(Blocks.double_stone_slab), 3 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { Block.getIdFromBlock(Blocks.stonebrick), 2 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { Block.getIdFromBlock(Blocks.double_stone_slab), 3 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { Block.getIdFromBlock(Blocks.stonebrick), 2 }, { Block.getIdFromBlock(Blocks.double_stone_slab), 3 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
    }, {
            // x = 13
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { Block.getIdFromBlock(Blocks.double_stone_slab), 3 }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { Block.getIdFromBlock(Blocks.double_stone_slab), 3 }, { Block.getIdFromBlock(Blocks.double_stone_slab), 3 }, { Block.getIdFromBlock(Blocks.cobblestone) }, { Block.getIdFromBlock(Blocks.double_stone_slab), 3 }, { Block.getIdFromBlock(Blocks.double_stone_slab), 3 }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
    }, {
            // x = 14
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            { Block.getIdFromBlock(Blocks.dirt) }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { Block.getIdFromBlock(Blocks.double_stone_slab), 3 }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.glass) }, { Block.getIdFromBlock(Blocks.stonebrick), 2 }, { Block.getIdFromBlock(Blocks.double_stone_slab), 3 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.glass) }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { Block.getIdFromBlock(Blocks.double_stone_slab), 3 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
    }, {
            // x = 15
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.glass) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { Block.getIdFromBlock(Blocks.double_stone_slab), 3 }, { Block.getIdFromBlock(Blocks.double_stone_slab), 3 }, { Block.getIdFromBlock(Blocks.double_stone_slab), 3 }, { Block.getIdFromBlock(Blocks.double_stone_slab), 3 }, { Block.getIdFromBlock(Blocks.double_stone_slab), 3 }, { Block.getIdFromBlock(Blocks.double_stone_slab), 3 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { Block.getIdFromBlock(Blocks.double_stone_slab), 3 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, {}, // {Block.getIdFromBlock(Blocks.grass)},
    }, {
            // x = 16
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, {}, // {Block.getIdFromBlock(Blocks.grass)},
    }, {
            // x = 17
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
    }, {
            // x = 18
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
    }, {
            // x = 19
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { Block.getIdFromBlock(Blocks.stonebrick), 1 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
    }, {
            // x = 20
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { Block.getIdFromBlock(Blocks.stonebrick), 2 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, {}, // {Block.getIdFromBlock(Blocks.grass)},
    }, {
            // x = 21
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, {}, // {Block.getIdFromBlock(Blocks.grass)},
    }, {
            // x = 22
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, {}, // {Block.getIdFromBlock(Blocks.grass)},
    }, {
            // x = 23
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
            {}, // {Block.getIdFromBlock(Blocks.grass)},
    } }, {
            // y = 1 LAYER2
            {
                    // x = 0
                    {}, {}, // {Block.getIdFromBlock(Blocks.tallgrass),
                    // 1},
                    {}, {}, {}, // {Block.getIdFromBlock(Blocks.tallgrass),
                    // 1},
                    {}, {}, // {Block.getIdFromBlock(Blocks.tallgrass),
                    // 1},
                    {}, // {Block.getIdFromBlock(Blocks.tallgrass), 1},
                    {}, // {Block.getIdFromBlock(Blocks.tallgrass), 1},
                    {}, // {Block.getIdFromBlock(Blocks.tallgrass), 1},
                    {}, // {Block.getIdFromBlock(Blocks.tallgrass), 1},
                    {}, // {Block.getIdFromBlock(Blocks.tallgrass), 1},
                    {}, // {Block.getIdFromBlock(Blocks.tallgrass), 1},
                    {}, {}, // {Block.getIdFromBlock(Blocks.tallgrass),
                    // 1},
                    {}, // {Block.getIdFromBlock(Blocks.tallgrass), 1},
                    {}, // {Block.getIdFromBlock(Blocks.tallgrass), 1},
                    {}, // {Block.getIdFromBlock(Blocks.tallgrass), 1},
                    {}, // {Block.getIdFromBlock(Blocks.tallgrass), 1},
                    {}, // {Block.getIdFromBlock(Blocks.tallgrass), 1},
                    {}, // {Block.getIdFromBlock(Blocks.tallgrass), 1},
                    {}, // {Block.getIdFromBlock(Blocks.tallgrass), 1},
                    {}, // {Block.getIdFromBlock(Blocks.tallgrass), 1},
                    {}, // {Block.getIdFromBlock(Blocks.tallgrass), 1},
                    {}, {}, // {Block.getIdFromBlock(Blocks.tallgrass),
                    // 1},
                    {}, // {Block.getIdFromBlock(Blocks.tallgrass), 1},
            }, {
            // x = 1
            {}, // {Block.getIdFromBlock(Blocks.tallgrass), 1},
            {}, // {Block.getIdFromBlock(Blocks.tallgrass), 1},
            {}, {}, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { 0 }, // {Block.getIdFromBlock(Blocks.tallgrass),
            // 1},
            { 0 }, // {Block.getIdFromBlock(Blocks.tallgrass),
            // 1},
            { 0 }, // {Block.getIdFromBlock(Blocks.tallgrass),
            // 1},
            { 0 }, // {Block.getIdFromBlock(Blocks.tallgrass),
            // 1},
            { 0 }, // {Block.getIdFromBlock(Blocks.tallgrass),
            // 1},
            { 0 }, // {Block.getIdFromBlock(Blocks.brown_mushroom)},
            { 0 }, // {Block.getIdFromBlock(Blocks.tallgrass),
            // 1},
            { 0 }, // {Block.getIdFromBlock(Blocks.brown_mushroom)},
            { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { 0 }, // {Block.getIdFromBlock(Blocks.tallgrass),
            // 1},
            { 0 }, // {Block.getIdFromBlock(Blocks.tallgrass),
            // 1},
            { 0 }, // {Block.getIdFromBlock(Blocks.tallgrass),
            // 1},
            { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { 0 }, // {Block.getIdFromBlock(Blocks.tallgrass),
            // 1},
            { 0 }, // {Block.getIdFromBlock(Blocks.tallgrass),
            // 1},
            { 0 }, // {Block.getIdFromBlock(Blocks.tallgrass),
            // 1},
            { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, {}, // {Block.getIdFromBlock(Blocks.tallgrass),
            // 1},
    }, {
            // x = 2
            {}, // {Block.getIdFromBlock(Blocks.tallgrass), 1},
            {}, // {Block.getIdFromBlock(Blocks.tallgrass), 1},
            {}, // {Block.getIdFromBlock(Blocks.tallgrass), 1},
            {}, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { Block.getIdFromBlock(Blocks.stonebrick), 1 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { Block.getIdFromBlock(Blocks.stonebrick), 2 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, {}, // {Block.getIdFromBlock(Blocks.tallgrass),
            // 1},
    }, {
            // x = 3
            {}, // {Block.getIdFromBlock(Blocks.tallgrass), 1},
            {}, // {Block.getIdFromBlock(Blocks.tallgrass), 1},
            {}, {}, {}, // {Block.getIdFromBlock(Blocks.brown_mushroom)},
            { Block.getIdFromBlock(Blocks.stonebrick), 1 }, { 0 }, // {Block.getIdFromBlock(Blocks.brown_mushroom)},
            { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { Block.getIdFromBlock(Blocks.stone_slab), 5 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.CUSTOM_CHEST, 5, AcademyUtil.COMMON_LOOT_F1 }, { 0 }, { AcademyUtil.CUSTOM_CHEST, 5, AcademyUtil.COMMON_LOOT_F1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, {}, // {Block.getIdFromBlock(Blocks.tallgrass),
            // 1},
    }, {
            // x = 4
            {}, // {Block.getIdFromBlock(Blocks.tallgrass), 1},
            {}, {}, // {Block.getIdFromBlock(Blocks.tallgrass),
            // 1},
            {}, {}, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { Block.getIdFromBlock(Blocks.stone_slab), 5 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { 0 }, { 0 }, { 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, {}, // {Block.getIdFromBlock(Blocks.tallgrass),
            // 1},
            {}, // {Block.getIdFromBlock(Blocks.tallgrass), 1},
    }, {
            // x = 5
            {}, {}, // {Block.getIdFromBlock(Blocks.tallgrass),
            // 1},
            {}, // {Block.getIdFromBlock(Blocks.tallgrass), 1},
            {}, // {Block.getIdFromBlock(Blocks.tallgrass), 1},
            { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { 0 }, { 0 }, { AcademyUtil.CUSTOM_CHEST, 2, AcademyUtil.RARE_LOOT_F1 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, {}, // {Block.getIdFromBlock(Blocks.tallgrass),
            // 1},
            {}, // {Block.getIdFromBlock(Blocks.tallgrass), 1},
    }, {
            // x = 6
            {}, // {Block.getIdFromBlock(Blocks.tallgrass), 1},
            {}, // {Block.getIdFromBlock(Blocks.tallgrass), 1},
            {}, // {Block.getIdFromBlock(Blocks.tallgrass), 1},
            {}, // {Block.getIdFromBlock(Blocks.tallgrass), 1},
            { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { 0 }, { 0 }, { 0 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { 0 }, { 0 }, { AcademyUtil.CUSTOM_CHEST, 2, AcademyUtil.COMMON_LOOT_F1 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, {}, // {Block.getIdFromBlock(Blocks.tallgrass),
            // 1},
            {}, // {Block.getIdFromBlock(Blocks.tallgrass), 1},
    }, {
            // x = 7
            {}, {}, // {Block.getIdFromBlock(Blocks.yellow_flower)},
            {}, // {Block.getIdFromBlock(Blocks.tallgrass), 1},
            {}, // {Block.getIdFromBlock(Blocks.tallgrass), 1},
            {}, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { 0 }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { Block.getIdFromBlock(Blocks.furnace), 5 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, {}, // {Block.getIdFromBlock(Blocks.tallgrass),
            // 1},
    }, {
            // x = 8
            {}, {}, // {Block.getIdFromBlock(Blocks.tallgrass),
            // 1},
            {}, // {Block.getIdFromBlock(Blocks.tallgrass), 1},
            {}, {}, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { Block.getIdFromBlock(Blocks.double_stone_slab), 3 }, { 0 }, { 0 }, { 0 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, {}, // {Block.getIdFromBlock(Blocks.tallgrass),
            // 1},
    }, {
            // x = 9
            {}, // {Block.getIdFromBlock(Blocks.tallgrass), 1},
            {}, {}, {}, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { 0 }, { 0 }, { 0 }, { 0 }, { Block.getIdFromBlock(Blocks.enchanting_table) }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, {}, // {Block.getIdFromBlock(Blocks.tallgrass),
            // 1},
            {}, }, {
            // x = 10
            {}, {}, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { 0 }, { 0 }, { 0 }, { 0 }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { Block.getIdFromBlock(Blocks.double_stone_slab), 3 }, { 0 }, { 0 }, { 0 }, { Block.getIdFromBlock(Blocks.double_stone_slab), 3 }, { 0 }, { 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, {}, {}, // {Block.getIdFromBlock(Blocks.yellow_flower)},
    }, {
            // x = 11
            {}, {}, {}, {}, { Block.getIdFromBlock(Blocks.stone_pressure_plate) }, { Block.getIdFromBlock(Blocks.iron_door), 3 }, {}, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, {}, // {Block.getIdFromBlock(Blocks.tallgrass),
            // 1},
            {}, // {Block.getIdFromBlock(Blocks.tallgrass), 1},
    }, {
            // x = 12
            {}, {}, {}, {}, {}, { Block.getIdFromBlock(Blocks.iron_door), 1 }, { Block.getIdFromBlock(Blocks.stone_pressure_plate) }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, {}, // {Block.getIdFromBlock(Blocks.tallgrass),
            // 1},
            {}, }, {
            // x = 13
            {}, {}, // {Block.getIdFromBlock(Blocks.tallgrass),
            // 1},
            { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { 0 }, { 0 }, { 0 }, { 0 }, { Block.getIdFromBlock(Blocks.furnace), 2 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { Block.getIdFromBlock(Blocks.double_stone_slab), 3 }, { 0 }, { 0 }, { 0 }, { Block.getIdFromBlock(Blocks.double_stone_slab), 3 }, { 0 }, { 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, {}, // {Block.getIdFromBlock(Blocks.tallgrass),
            // 1},
            {}, }, {
            // x = 14
            {}, // {Block.getIdFromBlock(Blocks.tallgrass), 1},
            {}, // {Block.getIdFromBlock(Blocks.tallgrass), 1},
            {}, // {Block.getIdFromBlock(Blocks.tallgrass), 1},
            {}, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { 0 }, { 0 }, { 0 }, { 0 }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, {}, // {Block.getIdFromBlock(Blocks.brown_mushroom)},
            {}, // {Block.getIdFromBlock(Blocks.tallgrass), 1},
    }, {
            // x = 15
            {}, // {Block.getIdFromBlock(Blocks.tallgrass), 1},
            {}, // {Block.getIdFromBlock(Blocks.yellow_flower)},
            {}, // {Block.getIdFromBlock(Blocks.tallgrass), 1},
            {}, // {Block.getIdFromBlock(Blocks.tallgrass), 1},
            {}, // {Block.getIdFromBlock(Blocks.tallgrass), 1},
            { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { Block.getIdFromBlock(Blocks.double_stone_slab), 3 }, { 0 }, { 0 }, { 0 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, {}, // {Block.getIdFromBlock(Blocks.tallgrass),
            // 1},
    }, {
            // x = 16
            {}, // {Block.getIdFromBlock(Blocks.tallgrass), 1},
            {}, // {Block.getIdFromBlock(Blocks.tallgrass), 1},
            {}, // {Block.getIdFromBlock(Blocks.tallgrass), 1},
            {}, // {Block.getIdFromBlock(Blocks.tallgrass), 1},
            {}, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { 0 }, { 0 }, { Block.getIdFromBlock(Blocks.gold_block) }, { Block.getIdFromBlock(Blocks.furnace), 4 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, {}, // {Block.getIdFromBlock(Blocks.tallgrass),
            // 1},
    }, {
            // x = 17
            {}, {}, // {Block.getIdFromBlock(Blocks.tallgrass),
            // 1},
            {}, // {Block.getIdFromBlock(Blocks.tallgrass), 1},
            {}, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { 0 }, { 0 }, { 0 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { 0 }, { 0 }, { AcademyUtil.CUSTOM_CHEST, 2, AcademyUtil.COMMON_LOOT_F1 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, {}, // {Block.getIdFromBlock(Blocks.yellow_flower)},
            {}, }, {
            // x = 18
            {}, // {Block.getIdFromBlock(Blocks.tallgrass), 1},
            {}, {}, // {Block.getIdFromBlock(Blocks.tallgrass),
            // 1},
            {}, // {Block.getIdFromBlock(Blocks.tallgrass), 1},
            { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { Block.getIdFromBlock(Blocks.stonebrick), 1 }, { Block.getIdFromBlock(Blocks.stonebrick), 2 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { 0 }, { 0 }, { AcademyUtil.CUSTOM_CHEST, 2, AcademyUtil.COMMON_LOOT_F1 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, {}, // {Block.getIdFromBlock(Blocks.tallgrass),
            // 1},
            {}, }, {
            // x = 19
            {}, {}, // {Block.getIdFromBlock(Blocks.yellow_flower)},
            {}, // {Block.getIdFromBlock(Blocks.tallgrass), 1},
            {}, // {Block.getIdFromBlock(Blocks.tallgrass), 1},
            {}, // {Block.getIdFromBlock(Blocks.tallgrass), 1},
            { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { Block.getIdFromBlock(Blocks.stone_slab), 5 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { 0 }, { 0 }, { 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, {}, // {Block.getIdFromBlock(Blocks.tallgrass),
            // 1},
            {}, // {Block.getIdFromBlock(Blocks.tallgrass), 1},
    }, {
            // x = 20
            {}, // {Block.getIdFromBlock(Blocks.tallgrass), 1},
            {}, // {Block.getIdFromBlock(Blocks.tallgrass), 1},
            {}, {}, // {Block.getIdFromBlock(Blocks.yellow_flower)},
            {}, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { Block.getIdFromBlock(Blocks.stone_slab), 5 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.CUSTOM_CHEST, 4, AcademyUtil.COMMON_LOOT_F1 }, { 0 }, { AcademyUtil.CUSTOM_CHEST, 4, AcademyUtil.COMMON_LOOT_F1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, {}, // {Block.getIdFromBlock(Blocks.tallgrass),
            // 1},
    }, {
            // x = 21
            {}, {}, // {Block.getIdFromBlock(Blocks.tallgrass),
            // 1},
            {}, // {Block.getIdFromBlock(Blocks.tallgrass), 1},
            {}, // {Block.getIdFromBlock(Blocks.tallgrass), 1},
            { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { Block.getIdFromBlock(Blocks.stonebrick), 2 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, {}, }, {
            // x = 22
            {}, // {Block.getIdFromBlock(Blocks.tallgrass), 1},
            {}, // {Block.getIdFromBlock(Blocks.tallgrass), 1},
            {}, {}, // {Block.getIdFromBlock(Blocks.tallgrass),
            // 1},
            { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, {}, // {Block.getIdFromBlock(Blocks.tallgrass),
            // 1},
            {}, // {Block.getIdFromBlock(Blocks.tallgrass), 1},
            {}, // {Block.getIdFromBlock(Blocks.tallgrass), 1},
            {}, // {Block.getIdFromBlock(Blocks.tallgrass), 1},
            {}, {}, // {Block.getIdFromBlock(Blocks.tallgrass),
            // 1},
            {}, // {Block.getIdFromBlock(Blocks.tallgrass), 1},
            {}, // {Block.getIdFromBlock(Blocks.tallgrass), 1},
            { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, {}, // {Block.getIdFromBlock(Blocks.tallgrass),
            // 1},
            {}, // {Block.getIdFromBlock(Blocks.tallgrass), 1},
            {}, // {Block.getIdFromBlock(Blocks.tallgrass), 1},
            { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, {}, // {Block.getIdFromBlock(Blocks.tallgrass),
            // 1},
            {}, // {Block.getIdFromBlock(Blocks.brown_mushroom)},
            {}, // {Block.getIdFromBlock(Blocks.tallgrass), 1},
            { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, {}, // {Block.getIdFromBlock(Blocks.tallgrass),
            // 1},
    }, {
            // x = 23
            {}, // {Block.getIdFromBlock(Blocks.tallgrass), 1},
            {}, {}, // {Block.getIdFromBlock(Blocks.tallgrass),
            // 1},
            {}, {}, {}, // {Block.getIdFromBlock(Blocks.yellow_flower)},
            {}, // {Block.getIdFromBlock(Blocks.yellow_flower)},
            {}, // {Block.getIdFromBlock(Blocks.tallgrass), 1},
            {}, // {Block.getIdFromBlock(Blocks.tallgrass), 1},
            {}, // {Block.getIdFromBlock(Blocks.tallgrass), 1},
            {}, // {Block.getIdFromBlock(Blocks.yellow_flower)},
            {}, // {Block.getIdFromBlock(Blocks.yellow_flower)},
            {}, // {Block.getIdFromBlock(Blocks.tallgrass), 1},
            {}, // {Block.getIdFromBlock(Blocks.tallgrass), 1},
            {}, {}, // {Block.getIdFromBlock(Blocks.tallgrass),
            // 1},
            {}, // {Block.getIdFromBlock(Blocks.tallgrass), 1},
            {}, // {Block.getIdFromBlock(Blocks.tallgrass), 1},
            {}, // {Block.getIdFromBlock(Blocks.tallgrass), 1},
            {}, // {Block.getIdFromBlock(Blocks.tallgrass), 1},
            {}, {}, // {Block.getIdFromBlock(Blocks.tallgrass),
            // 1},
            {}, // {Block.getIdFromBlock(Blocks.tallgrass), 1},
            {}, // {Block.getIdFromBlock(Blocks.tallgrass), 1},
            {}, {}, // {Block.getIdFromBlock(Blocks.tallgrass),
            // 1},
            {}, // {Block.getIdFromBlock(Blocks.yellow_flower)},
    } }, {
            // y = 4
            {
                    // x = 0
                    {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, }, {
            // x = 1
            {}, {}, {}, {}, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, {}, {}, {}, {}, {}, {}, {}, {}, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, {}, {}, {}, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, {}, {}, {}, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, {}, }, {
            // x = 2
            {}, {}, {}, {}, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { Block.getIdFromBlock(Blocks.stonebrick), 2 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, {}, }, {
            // x = 3
            {}, {}, {}, {}, {}, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { Block.getIdFromBlock(Blocks.stone_slab), 5 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.CUSTOM_CHEST, 5, AcademyUtil.COMMON_LOOT_F1 }, { 0 }, { AcademyUtil.CUSTOM_CHEST, 5, AcademyUtil.COMMON_LOOT_F1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, {}, }, {
            // x = 4
            {}, {}, {}, {}, {}, { Block.getIdFromBlock(Blocks.glass_pane) }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { Block.getIdFromBlock(Blocks.stone_slab), 5 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { 0 }, { 0 }, { 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, {}, {}, }, {
            // x = 5
            {}, {}, {}, {}, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { Block.getIdFromBlock(Blocks.clay) }, { 0 }, { 0 }, { AcademyUtil.CUSTOM_CHEST, 2, AcademyUtil.COMMON_LOOT_F1 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, {}, {}, }, {
            // x = 6
            {}, {}, {}, {}, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { 0 }, { 0 }, { 0 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { 0 }, { 0 }, { AcademyUtil.CUSTOM_CHEST, 2, AcademyUtil.COMMON_LOOT_F1 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, {}, {}, }, {
            // x = 7
            {}, {}, {}, {}, {}, { Block.getIdFromBlock(Blocks.glass_pane) }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { AcademyUtil.RANDOM_HOLE, 0, Block.getIdFromBlock(Blocks.double_stone_slab) }, { Block.getIdFromBlock(Blocks.furnace), 5 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, {}, }, {
            // x = 8
            {}, {}, {}, {}, {}, { Block.getIdFromBlock(Blocks.glass_pane) }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { Block.getIdFromBlock(FABlockRegistry.INSTANCE.drum) }, { 0 }, { 0 }, { 0 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, {}, }, {
            // x = 9
            {}, {}, {}, {}, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, {}, {}, }, {
            // x = 10
            {}, {}, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { Block.getIdFromBlock(Blocks.stonebrick), 2 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { Block.getIdFromBlock(FABlockRegistry.INSTANCE.drum) }, { 0 }, { 0 }, { 0 }, { Block.getIdFromBlock(FABlockRegistry.INSTANCE.drum) }, { 0 }, { 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, {}, {}, }, {
            // x = 11
            {}, {}, {}, {}, {}, { Block.getIdFromBlock(Blocks.iron_door), 12 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, {}, {}, }, {
            // x = 12
            {}, {}, {}, {}, {}, { Block.getIdFromBlock(Blocks.iron_door), 9 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, {}, {}, }, {
            // x = 13
            {}, {}, { Block.getIdFromBlock(Blocks.stonebrick), 2 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { Block.getIdFromBlock(FABlockRegistry.INSTANCE.drum) }, { 0 }, { 0 }, { 0 }, { Block.getIdFromBlock(FABlockRegistry.INSTANCE.drum) }, { 0 }, { 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, {}, {}, }, {
            // x = 14
            {}, {}, {}, {}, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, {}, {}, }, {
            // x = 15
            {}, {}, {}, {}, {}, { Block.getIdFromBlock(Blocks.glass_pane) }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { Block.getIdFromBlock(FABlockRegistry.INSTANCE.drum) }, { 0 }, { 0 }, { 0 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, {}, }, {
            // x = 16
            {}, {}, {}, {}, {}, { Block.getIdFromBlock(Blocks.glass_pane) }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { Block.getIdFromBlock(Blocks.furnace), 4 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, {}, }, {
            // x = 17
            {}, {}, {}, {}, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { 0 }, { 0 }, { 0 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { 0 }, { 0 }, { AcademyUtil.CUSTOM_CHEST, 2, AcademyUtil.COMMON_LOOT_F1 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, {}, {}, }, {
            // x = 18
            {}, {}, {}, {}, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { 0 }, { 0 }, { AcademyUtil.CUSTOM_CHEST, 2, AcademyUtil.COMMON_LOOT_F1 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, {}, {}, }, {
            // x = 19
            {}, {}, {}, {}, {}, { Block.getIdFromBlock(Blocks.glass_pane) }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { Block.getIdFromBlock(Blocks.stone_slab), 5 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { Block.getIdFromBlock(Blocks.stonebrick), 2 }, { 0 }, { 0 }, { 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, {}, {}, }, {
            // x = 20
            {}, {}, {}, {}, {}, { Block.getIdFromBlock(Blocks.stonebrick), 2 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { Block.getIdFromBlock(Blocks.stone_slab), 5 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.CUSTOM_CHEST, 4, AcademyUtil.RARE_LOOT_F1 }, { 0 }, { AcademyUtil.CUSTOM_CHEST, 4, AcademyUtil.COMMON_LOOT_F1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, {}, }, {
            // x = 21
            {}, {}, {}, {}, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { Block.getIdFromBlock(Blocks.stonebrick), 2 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 2, 0 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, {}, }, {
            // x = 22
            {}, {}, {}, {}, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, {}, {}, {}, {}, {}, {}, {}, {}, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, {}, {}, {}, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, {}, {}, {}, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, { AcademyUtil.BIOME_BLOCK, 0, 1, 1 }, {}, }, {
            // x = 23
            {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, } } };
}