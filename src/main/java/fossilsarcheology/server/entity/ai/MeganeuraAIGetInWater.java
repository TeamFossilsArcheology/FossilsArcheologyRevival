package fossilsarcheology.server.entity.ai;

import fossilsarcheology.server.entity.prehistoric.EntityMeganeura;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import javax.annotation.Nullable;
import java.util.Random;

public class MeganeuraAIGetInWater extends DinoAIGetInWater {

	public MeganeuraAIGetInWater(EntityMeganeura thedinoIn, double movementSpeedIn) {
		super(thedinoIn, movementSpeedIn);
		this.setMutexBits(0);
	}

	@Override
    public boolean shouldExecute() {
		return this.dino instanceof EntityMeganeura && this.dino.isChild() && super.shouldExecute();
	}

	@Nullable
	protected Vec3d findPossibleShelter() {
		Random random = this.dino.getRNG();
		BlockPos blockpos = new BlockPos(this.dino.posX, this.dino.getEntityBoundingBox().minY, this.dino.posZ);

		for (int i = 0; i < 30; ++i) {
			BlockPos blockpos1 = blockpos.add(random.nextInt(40) - 20, random.nextInt(6) - 3, random.nextInt(40) - 20);

			if (this.dino.world.getBlockState(blockpos1).getMaterial() == Material.WATER) {
				return new Vec3d((double) blockpos1.getX() + 0.5D, (double) blockpos1.getY(), (double) blockpos1.getZ() + 0.5D);
			}
		}

		return null;
	}
}
