package com.github.teamfossilsarcheology.fossil.entity.ai.anu;

import com.github.teamfossilsarcheology.fossil.block.ModBlocks;
import com.github.teamfossilsarcheology.fossil.config.FossilConfig;
import com.github.teamfossilsarcheology.fossil.entity.ModEntities;
import com.github.teamfossilsarcheology.fossil.entity.monster.AnuBoss;
import com.github.teamfossilsarcheology.fossil.entity.monster.SentryPiglin;
import com.github.teamfossilsarcheology.fossil.sounds.ModSounds;
import com.github.teamfossilsarcheology.fossil.world.feature.structures.AnuDefenseHut;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class AnuDefensePhase extends AbstractAnuPhaseInstance {
    private static final Component ANU_COMBAT_BRUTES = Component.translatable("entity.fossil.anu.brutes");
    private static final Component ANU_COMBAT_ARCHERS = Component.translatable("entity.fossil.anu.archers");
    private static final Component ANU_COMBAT_BLAZES = Component.translatable("entity.fossil.anu.blazes");

    public AnuDefensePhase(AnuBoss anuBoss) {
        super(anuBoss);
    }

    @Override
    public void doClientTick() {
        if (anu.deathTime <= 0) {
            for (int i = 0; i < 2; ++i) {
                anu.level().addParticle(ParticleTypes.DRIPPING_LAVA, anu.getRandomX(0.5), anu.getRandomY(), anu.getRandomZ(0.5), 0, 0, 0);
            }
        }
    }

    @Override
    public void doServerTick() {
        if (anu.tickCount % 20 == 0) {
            anu.heal(2);
        }
        Player player = anu.level().getNearestPlayer(anu.getX(), anu.getY(), anu.getZ(), AnuBoss.ARENA_RADIUS * 2,
                player1 -> arenaBounds.contains(player1.getX(), player1.getY(), player1.getZ()));
        if (player != null) {
            moveAwayFromPlayer(player);
            spawnStructures();
        }
        switchPhaseByHealth();
    }

    private void moveAwayFromPlayer(Player player) {
        if (anu.getNavigation().isDone()) {
            Vec3 away = DefaultRandomPos.getPosAway(anu, 16, 7, player.position());
            if (away == null || away.subtract(anu.getSpawnPos()).horizontalDistance() > Mth.square(AnuBoss.ARENA_RADIUS)) {
                for (int i = 0; i < 5; i++) {
                    float r = (float) (AnuBoss.ARENA_RADIUS * Math.sqrt(anu.getRandom().nextFloat()));
                    float t = anu.getRandom().nextFloat() * 2 * Mth.PI;
                    away = new Vec3(anu.getSpawnPos().x + r * Mth.cos(t), anu.getY(), anu.getSpawnPos().z + r * Mth.sin(t));
                    if (anu.level().isEmptyBlock(BlockPos.containing(away)) && away.subtract(player.position()).horizontalDistance() > 5) {
                        break;
                    }
                }
            }
            Path path = anu.getNavigation().createPath(away.x, away.y, away.z, 2);
            if (path == null) {
                anu.getMoveControl().setWantedPosition(away.x, away.y, away.z, 1);
            } else {
                anu.getNavigation().moveTo(path, 1.33);
            }
        }
    }

    private void spawnStructures() {
        Level level = anu.level();
        RandomSource random = anu.getRandom();
        List<Player> players = level.getNearbyPlayers(TargetingConditions.DEFAULT, anu, arenaBounds);
        boolean summonSpikes = random.nextInt(250) == 0;
        boolean summonDefenses = random.nextInt(600) == 0 && anu.getSpawnPos().distanceTo(anu.position()) > 5;
        boolean summonPiglin = random.nextInt(350) == 0;
        boolean summonWitherSkeleton = random.nextInt(400) == 0;
        boolean summonBlaze = random.nextInt(400) == 0;
        if (FossilConfig.isEnabled(FossilConfig.ANU_BLOCK_PLACING)) {
            if (summonSpikes) {
                anu.playSound(SoundEvents.STONE_HIT, 1, 1);
                BlockPos.MutableBlockPos blockPos = anu.blockPosition().mutable();
                BlockState blockState = level.getBlockState(blockPos);
                while ((blockState.isAir() || blockState.is(BlockTags.LEAVES)) && blockPos.getY() > 0) {
                    blockPos.move(Direction.DOWN);
                }
                for (int i = 0; i < 4; ++i) {
                    blockPos.move(random.nextInt(8) - random.nextInt(8), random.nextInt(4) - random.nextInt(4), random.nextInt(8) - random.nextInt(8));
                    if (level.isEmptyBlock(blockPos) && ModBlocks.OBSIDIAN_SPIKES.get().canSurvive(level.getBlockState(blockPos.below()), level, blockPos)) {
                        level.setBlock(blockPos, ModBlocks.OBSIDIAN_SPIKES.get().defaultBlockState(), 2);
                    }
                }
            } else if (summonDefenses) {
                anu.playSound(SoundEvents.STONE_HIT, 1, 1);
                Vec3 pos = anu.position();
                AnuDefenseHut.generateDefenseHutP2(level, BlockPos.containing(pos));
                AnuDefenseHut.generateDefenseHutP2(level, BlockPos.containing(pos.add(0, 1, 0)));
                AnuDefenseHut.generateDefenseHutP2(level, BlockPos.containing(pos.add(0, 2, 0)));
                AnuDefenseHut.generateDefenseHutP1(level, BlockPos.containing(pos.add(0, 4, 0)));
            }
        }
        if (summonPiglin) {
            SentryPiglin sentryPiglin = ModEntities.SENTRY_PIGLIN.get().create(level);
            sentryPiglin.moveTo(anu.getX() + random.nextInt(4), anu.getY(), anu.getZ() + random.nextInt(4), anu.getYRot(), anu.getXRot());
            level.addFreshEntity(sentryPiglin);
            players.forEach(player -> player.displayClientMessage(ANU_COMBAT_BRUTES, false));
        } else if (summonWitherSkeleton) {
            WitherSkeleton witherSkeleton = EntityType.WITHER_SKELETON.create(level);
            witherSkeleton.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
            witherSkeleton.moveTo(anu.getX() + random.nextInt(4), anu.getY(), anu.getZ() + random.nextInt(4), anu.getYRot(), anu.getXRot());
            level.addFreshEntity(witherSkeleton);
            players.forEach(player -> player.displayClientMessage(ANU_COMBAT_ARCHERS, false));
        } else if (summonBlaze) {
            Blaze blaze = EntityType.BLAZE.create(level);
            blaze.moveTo(anu.getX() + random.nextInt(4), anu.getY(), anu.getZ() + random.nextInt(4), anu.getYRot(), anu.getXRot());
            level.addFreshEntity(blaze);
            players.forEach(player -> player.displayClientMessage(ANU_COMBAT_BLAZES, false));
        }
    }

    @Override
    public AnuPhase getPhase() {
        return AnuPhase.DEFENSE;
    }

    @Override
    public SoundEvent getAmbientSound() {
        return ModSounds.ANU_COUGH.get();
    }

    @Override
    public boolean isFlying() {
        return false;
    }
}
