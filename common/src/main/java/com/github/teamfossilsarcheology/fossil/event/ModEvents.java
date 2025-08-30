package com.github.teamfossilsarcheology.fossil.event;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.advancements.ModTriggers;
import com.github.teamfossilsarcheology.fossil.block.ModBlocks;
import com.github.teamfossilsarcheology.fossil.capabilities.ModCapabilities;
import com.github.teamfossilsarcheology.fossil.config.FossilConfig;
import com.github.teamfossilsarcheology.fossil.entity.Quagga;
import com.github.teamfossilsarcheology.fossil.entity.ThrownBirdEgg;
import com.github.teamfossilsarcheology.fossil.entity.ai.AnimalFearGoal;
import com.github.teamfossilsarcheology.fossil.entity.monster.AnuBoss;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.EntityInfo;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.Prehistoric;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.VanillaEntityInfo;
import com.github.teamfossilsarcheology.fossil.food.FoodType;
import com.github.teamfossilsarcheology.fossil.item.MammalEmbryoItem;
import com.github.teamfossilsarcheology.fossil.item.ModItems;
import com.github.teamfossilsarcheology.fossil.recipe.ModRecipes;
import com.github.teamfossilsarcheology.fossil.sounds.ModSounds;
import com.github.teamfossilsarcheology.fossil.tags.ModEntityTypeTags;
import com.github.teamfossilsarcheology.fossil.world.dimension.ModDimensions;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.BlockEvent;
import dev.architectury.event.events.common.EntityEvent;
import dev.architectury.event.events.common.InteractionEvent;
import dev.architectury.event.events.common.LifecycleEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DispensibleContainerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class ModEvents {
    private static final Component ANU_BREAK_BLOCK = Component.translatable("entity.fossil.anu.break_block");

    public static void init() {
        EntityEvent.ADD.register((entity, level) -> {
            if (entity instanceof PathfinderMob mob && isLivestock(mob) && FossilConfig.isEnabled(FossilConfig.ANIMALS_FEAR_DINOS)) {
                mob.goalSelector.addGoal(1, new AnimalFearGoal(mob, Prehistoric.class, 12, 1.15, 1.25,
                        living -> living instanceof Prehistoric prehistoric && prehistoric.data().diet().canEat(FoodType.MEAT)));
            }
            return EventResult.pass();
        });
        BlockEvent.BREAK.register((level, pos, state, player, xp) -> {
            if (level.dimension() == ModDimensions.ANU_LAIR && level instanceof ServerLevel serverLevel) {
                if (!isBreakableInAnuLair(state) && !player.isCreative() && serverLevel.getServer().getProfilePermissions(player.getGameProfile()) < serverLevel.getServer().getOperatorUserPermissionLevel()) {
                    AnuBoss.AnuLair anuLair = serverLevel.getDataStorage().get(c -> new AnuBoss.AnuLair(), "anu_lair");
                    if (anuLair == null || !anuLair.isAnuKilled()) {
                        player.displayClientMessage(ANU_BREAK_BLOCK, true);
                        return EventResult.interruptFalse();
                    }
                }
            }
            return EventResult.pass();
        });
        LifecycleEvent.SETUP.register(() -> {
            ModRecipes.initRecipes();
            for (PrehistoricEntityInfo info : PrehistoricEntityInfo.values()) {
                if (info.birdEggItem != null) {
                    DispenserBlock.registerBehavior(info.birdEggItem, ThrownBirdEgg.getProjectile(info, false));
                }
                if (info.cultivatedBirdEggItem != null) {
                    DispenserBlock.registerBehavior(info.cultivatedBirdEggItem, ThrownBirdEgg.getProjectile(info, true));
                }
            }
            for (VanillaEntityInfo info : VanillaEntityInfo.values()) {
                if (info.cultivatedBirdEggItem != null) {
                    DispenserBlock.registerBehavior(info.cultivatedBirdEggItem, ThrownBirdEgg.getProjectile(info, true));
                }
            }
            DispenserBlock.registerBehavior(ModItems.TAR_BUCKET.get(), new DefaultDispenseItemBehavior() {
                private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();

                @Override
                public @NotNull ItemStack execute(BlockSource source, ItemStack stack) {
                    DispensibleContainerItem dispensibleContainerItem = (DispensibleContainerItem) ModItems.TAR_BUCKET.get();
                    BlockPos blockPos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));
                    if (dispensibleContainerItem.emptyContents(null, source.getLevel(), blockPos, null)) {
                        dispensibleContainerItem.checkExtraContent(null, source.getLevel(), stack, blockPos);
                        return new ItemStack(Items.BUCKET);
                    } else {
                        return defaultDispenseItemBehavior.dispense(source, stack);
                    }
                }
            });
        });
        InteractionEvent.INTERACT_ENTITY.register((player, entity, hand) -> {
            ItemStack stack = player.getItemInHand(hand);
            if (stack.getItem() instanceof MammalEmbryoItem embryoItem) {
                if (entity instanceof Animal animal && PrehistoricEntityInfo.isMammal(animal) && !animal.isBaby()) {
                    if (ModCapabilities.hasEmbryo(animal)) {
                        return EventResult.interruptFalse();
                    }
                    if (player instanceof ServerPlayer serverPlayer) {
                        ModTriggers.IMPLANT_EMBRYO_TRIGGER.trigger(serverPlayer, stack);
                        ModCapabilities.startPregnancy(animal, embryoItem.getInfo());
                        if (!player.getAbilities().instabuild) {
                            stack.shrink(1);
                        }
                    } else {
                        RandomSource random = player.getRandom();
                        for (int i = 0; i < 7; i++) {
                            double x = animal.getX() + random.nextFloat() * animal.getBbWidth() * 2 - animal.getBbWidth();
                            double y = animal.getY() + 0.5 + random.nextFloat() * animal.getBbHeight();
                            double z = animal.getZ() + random.nextFloat() * animal.getBbWidth() * 2 - animal.getBbWidth();
                            player.level().addParticle(ParticleTypes.SMOKE, x, y, z, random.nextGaussian() * 0.02, random.nextGaussian() * 0.02, random.nextGaussian() * 0.02);
                        }
                    }
                    return EventResult.interruptTrue();
                }
            } else if (stack.is(ModItems.WHIP.get())) {
                if (!player.level().isClientSide && player.isPassenger() && player.getVehicle() == entity) {
                    stack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
                    player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
                    player.playSound(ModSounds.WHIP.get(), 1, 1);
                    return EventResult.interruptTrue();
                }
                player.playSound(ModSounds.WHIP.get(), 1, 1);
                return EventResult.pass();
            }
            return EventResult.pass();
        });
    }

    private static boolean isBreakableInAnuLair(BlockState state) {
        return state.getBlock().getDescriptionId().toLowerCase(Locale.ROOT).contains("grave") || state.is(ModBlocks.FAKE_OBSIDIAN.get()) || state.is(BlockTags.FIRE);
    }

    private static boolean isLivestock(PathfinderMob mob) {
        if (mob.getType().is(ModEntityTypeTags.LIVESTOCK)) {
            return true;
        }
        String className = "";
        try {
            className = mob.getClass().getSimpleName();
        } catch (Exception e) {
            FossilMod.LOGGER.debug(e.getMessage());
        }
        return !className.isEmpty() && (className.contains("Cow") || className.contains("Sheep") || (className.contains("Pig") && !className.contains("piglin")) || className.contains("Chicken")
                || className.contains("Rabbit") || className.contains("Peacock") || className.contains("Goat") || className.contains("Ferret")
                || className.contains("Hedgehog") || className.contains("Peahen") || className.contains("Peafowl") || className.contains("Sow")
                || className.contains("Hog"));
    }

    public static void growEntity(EntityInfo embryo, LivingEntity parent) {
        RandomSource random = parent.level().random;
        if (parent.level().isClientSide) {
            for (int i = 0; i < 7; ++i) {
                double d = random.nextGaussian() * 0.02;
                double e = random.nextGaussian() * 0.02;
                double f = random.nextGaussian() * 0.02;
                parent.level().addParticle(ParticleTypes.HEART, parent.getRandomX(1.0), parent.getRandomY() + 0.5, parent.getRandomZ(1.0), d, e, f);
            }
            return;
        }
        ServerLevel level = (ServerLevel) parent.level();
        Entity newEntity = embryo.entityType().create(level);
        int result = random.nextInt(100);
        if (newEntity instanceof AbstractHorse newHorse) {
            if (parent instanceof AbstractHorse parentHorse && parent.getClass().equals(newEntity.getClass())) {
                //10% chance to tame the offspring if it's an instance of AbstractHorse
                if (result < 10) {
                    if (parentHorse.getOwnerUUID() != null) {
                        newHorse.setOwnerUUID(parentHorse.getOwnerUUID());
                        newHorse.setTamed(true);
                    }
                } else {
                    newEntity = parentHorse.getBreedOffspring(level, parentHorse);
                }
            } else {
                newHorse.finalizeSpawn(level, level.getCurrentDifficultyAt(newHorse.blockPosition()), MobSpawnType.BREEDING, null, null);
            }
        } else if (newEntity instanceof Quagga newQuagga) {
            double health = parent.getAttribute(Attributes.MAX_HEALTH).getBaseValue() + newQuagga.getAttribute(Attributes.MAX_HEALTH).getBaseValue() + newQuagga.getAttribute(Attributes.MAX_HEALTH).getBaseValue();
            newQuagga.getAttribute(Attributes.MAX_HEALTH).setBaseValue(health / 3);
            double speed = parent.getAttribute(Attributes.MOVEMENT_SPEED).getBaseValue() + newQuagga.getAttribute(Attributes.MOVEMENT_SPEED).getBaseValue() + newQuagga.getAttribute(Attributes.MOVEMENT_SPEED).getBaseValue();
            newQuagga.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(speed / 3);
        } else if (newEntity instanceof AgeableMob ageableMob) {
            if (parent.getClass().equals(newEntity.getClass())) {
                newEntity = ((AgeableMob) parent).getBreedOffspring(level, (AgeableMob) parent);
            } else {
                ageableMob.finalizeSpawn(level, level.getCurrentDifficultyAt(parent.blockPosition()), MobSpawnType.BREEDING, null, null);
            }
        }
        if (newEntity instanceof Prehistoric prehistoric) {
            //Always tame offspring if it's an instance of Prehistoric
            Player player = level.getNearestPlayer(parent, 15);
            if (player != null) {
                prehistoric.tame(player);
            }
            prehistoric.setAgeInDays(0);
        } else if (newEntity instanceof Animal) {
            ((Animal) newEntity).setBaby(true);
        }
        newEntity.moveTo(parent.getX(), parent.getY(), parent.getZ(), parent.getYRot(), parent.getXRot());
        level.addFreshEntity(newEntity);
    }
}
