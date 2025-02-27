package com.github.teamfossilsarcheology.fossil.entity.monster;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.monster.piglin.PiglinBrute;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SentryPiglin extends PiglinBrute {

    public SentryPiglin(EntityType<? extends PiglinBrute> entityType, Level level) {
        super(entityType, level);
        setImmuneToZombification(true);
    }

    public static AttributeSupplier.@NotNull Builder createAttributes() {
        return createMonsterAttributes().add(Attributes.MAX_HEALTH, 20).add(Attributes.MOVEMENT_SPEED, 0.3f).add(Attributes.ATTACK_DAMAGE, 5).add(Attributes.ARMOR, 8);
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty) {
        setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag dataTag) {
        SpawnGroupData spawnGroupData = super.finalizeSpawn(level, difficulty, reason, spawnData, dataTag);
        ((GroundPathNavigation) getNavigation()).setCanOpenDoors(true);
        populateDefaultEquipmentSlots(level.getRandom(), difficulty);
        populateDefaultEquipmentEnchantments(random, difficulty);
        return spawnGroupData;
    }
}
