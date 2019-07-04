package fossilsarcheology.server.structure;

import fossilsarcheology.server.world.FAWorldGenerator;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

import java.util.Random;

public class StructureUtils {

    public static boolean generateStructureAtWithRandomRotation(ResourceLocation structure, World world, BlockPos pos, Random random, boolean checkGround, boolean removeAir) {
        EnumFacing facing = EnumFacing.byHorizontalIndex(random.nextInt(3));
        Rotation rotation = getRotationFromFacing(facing);
        MinecraftServer server = world.getMinecraftServer();
        TemplateManager templateManager = world.getSaveHandler().getStructureTemplateManager();
        PlacementSettings settings = new PlacementSettings().setRotation(rotation).setMirror(Mirror.NONE);
        if (removeAir) {
            settings.setReplacedBlock(Blocks.AIR);
        }
        Template template = templateManager.getTemplate(server, structure);
        BlockPos center = balancePos(pos.offset(facing, template.getSize().getZ() / 2).offset(facing.rotateYCCW(), template.getSize().getX() / 2), template.getSize());
        template.addBlocksToWorldChunk(world, center, settings);
        return true;
    }

    public static boolean canGenOnBlock(World world, BlockPos pos){
        IBlockState state = world.getBlockState(pos);
        if(!state.isSideSolid(world, pos, EnumFacing.UP)){
            return false;
        }
        if(!state.isOpaqueCube()){
            return false;
        }
        return !world.getBlockState(pos.up()).isOpaqueCube() && !(world.getBlockState(pos.up(2)).getBlock() instanceof BlockLiquid);
    }

    public static boolean generateStructureAt(ResourceLocation structure, World world, BlockPos pos, boolean removeAir) {
        EnumFacing facing = EnumFacing.NORTH;
        Rotation rotation = getRotationFromFacing(facing);
        MinecraftServer server = world.getMinecraftServer();
        TemplateManager templateManager = world.getSaveHandler().getStructureTemplateManager();
        PlacementSettings settings = new PlacementSettings().setRotation(rotation).setMirror(Mirror.NONE);
        if (removeAir) {
            settings.setReplacedBlock(Blocks.AIR);
        }
        Template template = templateManager.getTemplate(server, structure);
        BlockPos center = pos.offset(facing, template.getSize().getZ() / 2).offset(facing.rotateYCCW(), template.getSize().getX() / 2);
        template.addBlocksToWorldChunk(world, center, settings);
        return true;
    }

    private static BlockPos balancePos(BlockPos center, BlockPos templateSize){
        BlockPos heightNE = center.offset(EnumFacing.NORTH, templateSize.getZ() / 2).offset(EnumFacing.EAST, templateSize.getZ() / 2);
        BlockPos heightNW = center.offset(EnumFacing.NORTH, templateSize.getZ() / 2).offset(EnumFacing.WEST, templateSize.getZ() / 2);
        BlockPos heightSE = center.offset(EnumFacing.SOUTH, templateSize.getZ() / 2).offset(EnumFacing.EAST, templateSize.getZ() / 2);
        BlockPos heightSW = center.offset(EnumFacing.SOUTH, templateSize.getZ() / 2).offset(EnumFacing.WEST, templateSize.getZ() / 2);
        int averageHeight = (heightNE.getY() + heightNW.getY() + heightSE.getY() + heightSW.getY()) / 4 + 1;
        return new BlockPos(center.getX(), averageHeight, center.getZ());

    }

    public static Rotation getRotationFromFacing(EnumFacing facing) {
        switch (facing) {
            case EAST:
                return Rotation.CLOCKWISE_90;
            case SOUTH:
                return Rotation.CLOCKWISE_180;
            case WEST:
                return Rotation.COUNTERCLOCKWISE_90;
            default:
                return Rotation.NONE;
        }
    }

    public static void generateStructureAtWithRandomRotationWithLoot(ResourceLocation structure, ResourceLocation loot, World world, BlockPos pos, Random random, boolean checkGround, boolean removeAir) {
        EnumFacing facing = EnumFacing.byHorizontalIndex(random.nextInt(3));
        Rotation rotation = getRotationFromFacing(facing);
        MinecraftServer server = world.getMinecraftServer();
        TemplateManager templateManager = world.getSaveHandler().getStructureTemplateManager();
        PlacementSettings settings = new PlacementSettings().setRotation(rotation).setMirror(Mirror.NONE);
        if (removeAir) {
            settings.setReplacedBlock(Blocks.AIR);
        }
        Template template = templateManager.getTemplate(server, structure);
        BlockPos center = balancePos(pos.offset(facing, template.getSize().getZ() / 2).offset(facing.rotateYCCW(), template.getSize().getX() / 2), template.getSize());
        template.addBlocksToWorld(world, center, new FABlockProcessorLoot(center, settings, loot), settings, 2);
    }


    public static boolean generateStructureAtWithRotation(ResourceLocation structure, World world, BlockPos pos, Random random, Rotation rotation, boolean checkGround, boolean removeAir) {
        MinecraftServer server = world.getMinecraftServer();
        TemplateManager templateManager = world.getSaveHandler().getStructureTemplateManager();
        PlacementSettings settings = new PlacementSettings().setRotation(rotation).setMirror(Mirror.NONE);
        if (removeAir) {
            settings.setReplacedBlock(Blocks.AIR);
        }
        Template template = templateManager.getTemplate(server, structure);
        BlockPos center = pos;
        switch(rotation){
            case NONE:
                center = pos;
                break;
            case CLOCKWISE_90:
                center = pos.add(template.getSize().getZ() - 1, 0, 0);
                break;
            case COUNTERCLOCKWISE_90:
                center = pos.add(0, 0, template.getSize().getX() - 1);
                break;
            case CLOCKWISE_180:
                center = pos.add(template.getSize().getX() - 1, 0, template.getSize().getZ() - 1);
        }
        if (checkGround) {
            BlockPos corner1 = pos.down();
            BlockPos corner2 = pos.add(template.getSize().getX(), -1, 0);
            BlockPos corner3 = pos.add(template.getSize().getX(), -1, template.getSize().getZ());
            BlockPos corner4 = pos.add(0, -1, template.getSize().getZ());
            if (world.getBlockState(center).isOpaqueCube() && world.getBlockState(corner1).isOpaqueCube() && world.getBlockState(corner2).isOpaqueCube() && world.getBlockState(corner3).isOpaqueCube() && world.getBlockState(corner4).isOpaqueCube()) {
                template.addBlocksToWorldChunk(world, center, settings);
                return true;
            }
            return false;
        } else {
            template.addBlocksToWorldChunk(world, center, settings);
            return true;
        }
    }

    public static boolean generateStructureAtWithRotationWithLoot(ResourceLocation structure, ResourceLocation loot, World world, BlockPos pos, Random random, Rotation rotation, boolean checkGround, boolean removeAir) {
        Mirror mirror = Mirror.values()[random.nextInt(Mirror.values().length)];
        MinecraftServer server = world.getMinecraftServer();
        TemplateManager templateManager = world.getSaveHandler().getStructureTemplateManager();
        PlacementSettings settings = new PlacementSettings().setRotation(rotation).setMirror(Mirror.NONE);
        if (removeAir) {
            settings.setReplacedBlock(Blocks.AIR);
        }
        Template template = templateManager.getTemplate(server, structure);
        BlockPos center = pos.add(template.getSize().getX() / 2, 0, template.getSize().getZ() / 2);
        if (checkGround) {
            BlockPos corner1 = pos.down();
            BlockPos corner2 = pos.add(template.getSize().getX(), -1, 0);
            BlockPos corner3 = pos.add(template.getSize().getX(), -1, template.getSize().getZ());
            BlockPos corner4 = pos.add(0, -1, template.getSize().getZ());
            if (world.getBlockState(center).isOpaqueCube() && world.getBlockState(corner1).isOpaqueCube() && world.getBlockState(corner2).isOpaqueCube() && world.getBlockState(corner3).isOpaqueCube() && world.getBlockState(corner4).isOpaqueCube()) {
                template.addBlocksToWorld(world, center, structure == FAWorldGenerator.ANU_CASTLE ? new FABlockProcessorAnu(center, settings, loot) : new FABlockProcessorLoot(center, settings, loot), settings, 2);
                return true;
            }
            return false;
        } else {
            template.addBlocksToWorld(world, center, structure == FAWorldGenerator.ANU_CASTLE ? new FABlockProcessorAnu(center, settings, loot) : new FABlockProcessorLoot(center, settings, loot), settings, 2);
            return true;
        }
    }

    public static BlockPos getGround(BlockPos pos, World world){
        return getGround(pos.getX(), pos.getZ(), world);
    }

    public static BlockPos getGround(int x, int z, World world){
        BlockPos skyPos = new BlockPos(x, world.getHeight(), z);
        while ((!world.getBlockState(skyPos).isOpaqueCube() || canHeightSkipBlock(skyPos, world)) && skyPos.getY() > 1){
            skyPos = skyPos.down();
        }
        return skyPos;
    }

    private static boolean canHeightSkipBlock(BlockPos pos, World world){
        IBlockState state = world.getBlockState(pos);
        if(state.getBlock() instanceof BlockLog || state.getBlock() instanceof BlockLiquid){
            return true;
        }
        return false;
    }
}
