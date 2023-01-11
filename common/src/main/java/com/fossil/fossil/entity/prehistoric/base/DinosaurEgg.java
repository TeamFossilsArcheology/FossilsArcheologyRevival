package com.fossil.fossil.entity.prehistoric.base;


import com.fossil.fossil.entity.ModEntities;
import com.mojang.logging.LogUtils;
import dev.architectury.extensions.network.EntitySpawnExtension;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.Collections;
import java.util.List;

public class DinosaurEgg extends LivingEntity implements EntitySpawnExtension {
    public float scale;
    private EntityType<? extends Prehistoric> toSpawn;

    public DinosaurEgg(EntityType<? extends DinosaurEgg> type, Level level) {
        super(type, level);
    }

    public DinosaurEgg(Level level, EntityType<? extends Prehistoric> toSpawn, float scale) {
        this(ModEntities.DINOSAUR_EGG.get(), level);
        this.scale = scale;
        this.toSpawn = toSpawn;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 2D);
    }

    @Override
    public @NotNull Iterable<ItemStack> getArmorSlots() {
        return EMPTY_LIST;
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

    @Override
    public void saveAdditionalSpawnData(FriendlyByteBuf buf) {
        buf.writeFloat(scale);
        buf.writeResourceLocation(toSpawn.arch$registryName());
    }

    @Override
    public void loadAdditionalSpawnData(FriendlyByteBuf buf) {
        scale = buf.readFloat();
        EntityType<?> type = Registry.ENTITY_TYPE.get(buf.readResourceLocation());
        if (Prehistoric.class.isAssignableFrom(type.getBaseClass())) {
            toSpawn = (EntityType<? extends Prehistoric>) type;
        } else {
            LOGGER.error("Dinosaur egg " + stringUUID + " has invalid dinosaur specified!");
        }
    }

    private static final Logger LOGGER = LogUtils.getLogger();
    private static final List<ItemStack> EMPTY_LIST = Collections.emptyList();
}
