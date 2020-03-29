package fossilsarcheology.server.world.village;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieces;

import java.util.List;
import java.util.Random;

public class VillageComponentPaleontologistHouse extends StructureVillagePieces.Village {
    private int averageGroundLevel = -1;
    int villagerCount = 0;

    public VillageComponentPaleontologistHouse() {
        super();
    }

    public VillageComponentPaleontologistHouse(StructureVillagePieces.Start startPiece, int p2, Random random, StructureBoundingBox structureBox, EnumFacing facing) {
        super();
        this.villagerCount = 0;
        this.setCoordBaseMode(facing);
        this.boundingBox = structureBox;
    }

    public static VillageComponentPaleontologistHouse buildComponent(StructureVillagePieces.Start startPiece, List<StructureComponent> pieces, Random random, int x, int y, int z, EnumFacing facing, int p5) {
        StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, -1, 6, 15, 7, facing);
        return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null ? new VillageComponentPaleontologistHouse(startPiece, p5, random, structureboundingbox, facing) : null;
    }
    @Override
    public boolean addComponentParts(World world, Random random, StructureBoundingBox sbb) {
        if (this.averageGroundLevel < 0) {
            this.averageGroundLevel = this.getAverageGroundLevel(world, sbb);
            if (this.averageGroundLevel < 0) {
                return false;
            }
            this.boundingBox.offset(0, this.averageGroundLevel - this.boundingBox.maxY + 4, 0);
        }
        BlockPos blockpos = new BlockPos(this.getXWithOffset(6, -1), this.getYWithOffset(0), this.getZWithOffset(7, -1));
        EnumFacing facing = this.getCoordBaseMode().getOpposite();
        BlockPos genPos = blockpos.up();
        if(facing == EnumFacing.SOUTH){
            genPos =  genPos.offset(EnumFacing.WEST, 6).offset(EnumFacing.SOUTH, 2);
        }
        return new WorldGenPaleontologistHouse(this, facing.rotateY()).generate(world, random, genPos);
    }

    public IBlockState getBiomeBlock(IBlockState state){
        return getBiomeSpecificBlockState(state);
    }

}
