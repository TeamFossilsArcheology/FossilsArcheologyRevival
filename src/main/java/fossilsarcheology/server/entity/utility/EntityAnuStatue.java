package fossilsarcheology.server.entity.utility;

import fossilsarcheology.client.sound.FASoundRegistry;
import fossilsarcheology.server.block.FABlockRegistry;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityAnuStatue extends EntityLiving {

    public EntityAnuStatue(World worldIn) {
        super(worldIn);
        this.setSize(0.9F, 1.8F);
    }

    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAIWatchClosest(this, EntityPlayer.class, 64.0F));
    }

    public boolean attackEntityFrom(DamageSource source, float amount) {
        return false;
    }

    public void onUpdate() {
        super.onUpdate();
        this.motionY += 0.095;
        this.motionY *= 0.6F;
        if (this.ticksExisted > 200) {
            this.world.createExplosion(this, this.posX, this.posY, this.posZ, 5, net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.world, this));
            this.createPortal();
        }
    }

    private void createPortal() {
        BlockPos pos = new BlockPos(this);
        world.setBlockState(pos.down(), Blocks.OBSIDIAN.getDefaultState());
        world.setBlockState(pos, FABlockRegistry.ANU_PORTAL.getDefaultState());
        world.setBlockState(pos.up(), FABlockRegistry.ANU_PORTAL.getDefaultState());
        world.setBlockState(pos.up(2), Blocks.OBSIDIAN.getDefaultState());
        this.setDead();
    }


    public void playSummonSong() {
        this.playSound(FASoundRegistry.ANU_TOTEM, 1, 1);
    }
}
