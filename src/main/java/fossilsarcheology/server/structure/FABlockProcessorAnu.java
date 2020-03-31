package fossilsarcheology.server.structure;

import fossilsarcheology.server.block.AnubiteStatueBlock;
import fossilsarcheology.server.block.FABlockRegistry;
import fossilsarcheology.server.block.SkullBlock;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;

import javax.annotation.Nullable;

public class FABlockProcessorAnu extends FABlockProcessorLoot {
    private Rotation rotation;

    public FABlockProcessorAnu(BlockPos pos, PlacementSettings settings, ResourceLocation loot) {
        super(pos, settings, loot);
        rotation = settings.getRotation();
    }

    @Nullable
    public Template.BlockInfo processBlock(World worldIn, BlockPos pos, Template.BlockInfo blockInfoIn) {
        if (blockInfoIn.blockState.getBlock() == Blocks.STAINED_GLASS_PANE) {
            return new Template.BlockInfo(pos, FABlockRegistry.ANCIENT_GLASS.getDefaultState(), null);
        }
        if (blockInfoIn.blockState.getBlock() == Blocks.GLASS) {
            return new Template.BlockInfo(pos, Blocks.STAINED_GLASS.getDefaultState().withProperty(BlockStainedGlass.COLOR, EnumDyeColor.BLACK), null);
        }
        if (blockInfoIn.blockState.getBlock() == Blocks.WALL_SIGN) {
            return new Template.BlockInfo(pos, Blocks.AIR.getDefaultState(), null);
        }
        if (blockInfoIn.blockState.getBlock() == Blocks.LIT_PUMPKIN) {
            EnumFacing facing = blockInfoIn.blockState.withRotation(this.rotation).getValue(BlockHorizontal.FACING);
            return new Template.BlockInfo(pos, FABlockRegistry.SKULL_LANTERN.getDefaultState().withProperty(SkullBlock.FACING, facing), null);
        }
        if (blockInfoIn.blockState.getBlock() == Blocks.PUMPKIN) {
            EnumFacing facing = blockInfoIn.blockState.withRotation(this.rotation).getValue(BlockHorizontal.FACING);
            return new Template.BlockInfo(pos, FABlockRegistry.ANUBITE_STATUE.getDefaultState().withProperty(AnubiteStatueBlock.FACING, facing), null);
        }
        return super.processBlock(worldIn, pos, blockInfoIn);
    }
}