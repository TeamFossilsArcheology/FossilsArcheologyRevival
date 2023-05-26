package com.fossil.fossil.entity;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.sounds.ModSounds;
import com.fossil.fossil.world.dimension.ModDimensions;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

public class AnuDead extends LivingEntity {
    private static final int MAX_LIFESPAN = 5960;

    public static AttributeSupplier.Builder createAttributes() {
        return createLivingAttributes().add(Attributes.MAX_HEALTH, 300);
    }

    public AnuDead(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
        noCulling = true;
    }

    @Override
    public void travel(Vec3 travelVector) {
        setDeltaMovement(0, 0, 0);
    }

    @Override
    public @NotNull InteractionResult interact(Player player, InteractionHand hand) {
        if (player instanceof ServerPlayer serverPlayer) {

            if (player.portalTime > 0) {
                player.portalTime = 10;
            } else if (!player.level.dimension().equals(ModDimensions.TREASURE_ROOM)) {
                player.portalTime = 10;
                ServerLevel treasureLevel = serverPlayer.server.getLevel(ModDimensions.TREASURE_ROOM);
                serverPlayer.teleportTo(treasureLevel, 6.5, 72.5, 6.5, 0, 0);
            } else {
                player.portalTime = 10;
                serverPlayer.teleportTo(serverPlayer.server.getLevel(Level.OVERWORLD), 0, 60, 0, 0, 0);
            }
            return InteractionResult.sidedSuccess(false);
        }
        return InteractionResult.sidedSuccess(true);
    }

    @Override
    public void tick() {
        if (tickCount == 1 && !level.isClientSide) {
            ServerLevel treasureLevel = ((ServerLevel) level).getServer().getLevel(ModDimensions.TREASURE_ROOM);
            if (treasureLevel.getDataStorage().get(c -> new TreasureRoom(), "treasure_spawned") == null) {
                StructureTemplate structure = treasureLevel.getStructureManager().getOrCreate(new ResourceLocation(Fossil.MOD_ID, "treasure_room"));
                BlockPos pos = new BlockPos(0, 70, 0);
                structure.placeInWorld(treasureLevel, pos, pos, new StructurePlaceSettings(), treasureLevel.random, 2);
                treasureLevel.getDataStorage().set("treasure_spawned", new TreasureRoom());
            }
        }
        if (tickCount == MAX_LIFESPAN) {
            kill();
        }
        if (tickCount == 40) {
            playSound(ModSounds.ANU_DEATH.get(), 1, 1);
        }
        for (int i = 0; i < 2; ++i) {
            level.addParticle(ParticleTypes.SMOKE, getX(), getY(), getZ(), 0, 0.1, 0);
        }
        super.tick();
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        return source == DamageSource.OUT_OF_WORLD;
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    protected float getSoundVolume() {
        return 5;
    }

    @Override
    public @NotNull Iterable<ItemStack> getArmorSlots() {
        return Collections.emptyList();
    }

    @Override
    public @NotNull ItemStack getItemBySlot(EquipmentSlot slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItemSlot(EquipmentSlot slot, ItemStack stack) {

    }

    @Override
    public @NotNull HumanoidArm getMainArm() {
        return HumanoidArm.RIGHT;
    }

    static class TreasureRoom extends SavedData {

        public static TreasureRoom load(CompoundTag compoundTag) {
            return new TreasureRoom();
        }
        @Override
        public @NotNull CompoundTag save(CompoundTag compoundTag) {
            compoundTag.putBoolean("Spawned", true);
            return compoundTag;
        }
    }
}
