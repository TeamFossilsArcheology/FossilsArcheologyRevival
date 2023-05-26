package com.fossil.fossil.block.custom_blocks;

import com.fossil.fossil.world.dimension.ModDimensions;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.portal.PortalInfo;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class HomePortal extends Block {
    public HomePortal(Properties properties) {
        super(properties);
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (!entity.isPassenger() && !entity.isVehicle() && entity.canChangeDimensions()) {
            if (entity.isOnPortalCooldown()) {
                entity.setPortalCooldown();
                return;
            }
            if (!entity.level.isClientSide && !pos.equals(entity.portalEntrancePos)) {
                entity.portalEntrancePos = pos.immutable();
            }
            Level entityLevel = entity.level;
            MinecraftServer server = entityLevel.getServer();
            if (server != null) {
                ServerLevel overworld = server.getLevel(Level.OVERWORLD);
                entity.setPortalCooldown();
                BlockPos blockPos = overworld.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, overworld.getSharedSpawnPos());
                ModDimensions.changeDimension(entity, overworld, new PortalInfo(new Vec3(blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5), entity.getDeltaMovement(), entity.getYRot(), entity.getXRot()));
            }
        }
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, Random random) {
        if (random.nextInt(100) == 0) {
            level.playLocalSound((double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, SoundEvents.PORTAL_AMBIENT, SoundSource.BLOCKS, 0.5f, random.nextFloat() * 0.4f + 0.8f, false);
        }
        for (int i = 0; i < 4; ++i) {
            double d = (double)pos.getX() + random.nextDouble();
            double e = (double)pos.getY() + random.nextDouble();
            double f = (double)pos.getZ() + random.nextDouble();
            double g = ((double)random.nextFloat() - 0.5) * 0.5;
            double h = ((double)random.nextFloat() - 0.5) * 0.5;
            double j = ((double)random.nextFloat() - 0.5) * 0.5;
            int k = random.nextInt(2) * 2 - 1;
            if (level.getBlockState(pos.west()).is(this) || level.getBlockState(pos.east()).is(this)) {
                f = (double)pos.getZ() + 0.5 + 0.25 * (double)k;
                j = random.nextFloat() * 2.0f * (float)k;
            } else {
                d = (double)pos.getX() + 0.5 + 0.25 * (double)k;
                g = random.nextFloat() * 2.0f * (float)k;
            }
            level.addParticle(ParticleTypes.PORTAL, d, e, f, g, h, j);
        }
    }
    @Override
    public @NotNull ItemStack getCloneItemStack(BlockGetter level, BlockPos pos, BlockState state) {
        return ItemStack.EMPTY;
    }
}
