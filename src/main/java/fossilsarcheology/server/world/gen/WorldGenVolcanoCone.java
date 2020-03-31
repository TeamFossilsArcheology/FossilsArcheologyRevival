package fossilsarcheology.server.world.gen;

import fossilsarcheology.server.block.FABlockRegistry;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenVolcanoCone extends WorldGenerator {

    public boolean generate(World worldIn, Random rand, BlockPos position) {
        for (IBlockState iblockstate = worldIn.getBlockState(position); (iblockstate.getBlock().isAir(iblockstate, worldIn, position) || iblockstate.getBlock().isLeaves(iblockstate, worldIn, position)) && position.getY() > 0; iblockstate = worldIn.getBlockState(position)) {
            position = position.down();
        }
        int height = 10 + rand.nextInt(40);
        BlockPos center = position.up(height);
        int stopHeight = Math.max(position.getY() / 2, 0);
        int layer = 8;
        boolean first = true;
        while (center.getY() >= stopHeight) {
            layer++;
            for (float i = 0; i < layer * 0.5; i += 0.5) {
                for (float j = 0; j < 2 * Math.PI * i + rand.nextInt(2); j += 0.5) {
                    BlockPos stonePos = new BlockPos(Math.floor(center.getX() + Math.sin(j) * i + rand.nextInt(2)), center.getY(), Math.floor(center.getZ() + Math.cos(j) * i + rand.nextInt(2)));
                    if (worldIn.isAirBlock(stonePos)) {
                        worldIn.setBlockState(stonePos, FABlockRegistry.VOLCANIC_ROCK.getDefaultState(), 3);
                    }
                }
            }
            for (float i = 0; i < (first ? 0.45F : Math.max(layer * 0.2, 1)); i += 0.5) {
                float extra = i == 0 ? 3 : i;
                for (float j = 0; j < 2 * Math.PI * extra + rand.nextInt(2); j += 0.5) {

                    BlockPos lavaPos = new BlockPos(Math.floor(center.getX() + Math.sin(j) * extra + rand.nextInt(2)), center.getY(), Math.floor(center.getZ() + Math.cos(j) * extra + rand.nextInt(2)));
                    worldIn.setBlockState(lavaPos, Blocks.LAVA.getDefaultState(), 3);
                }
                first = false;
            }
            center = center.down();
        }
        layer++;
        return true;
    }

}
