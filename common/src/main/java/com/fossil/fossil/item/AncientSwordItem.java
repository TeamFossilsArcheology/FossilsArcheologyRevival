package com.fossil.fossil.item;

import com.fossil.fossil.entity.ModEntities;
import com.fossil.fossil.entity.monster.FriendlyPiglin;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;

public class AncientSwordItem extends SwordItem {
    public AncientSwordItem(Tier tier, int damage, float attackSpeed) {
        super(tier, damage, attackSpeed, new Item.Properties().tab(ModTabs.FAITEMTAB).durability(250));
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof ServerPlayer player) {
            Level level = player.level;
            if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.ANCIENT_HELMET.get())) {
                if (target instanceof Pig || target instanceof AbstractPiglin || target instanceof ZombifiedPiglin) {
                    FriendlyPiglin piglin = ModEntities.FRIENDLY_PIGLIN.get().create(level);
                    piglin.finalizeSpawn((ServerLevelAccessor) level, level.getCurrentDifficultyAt(target.blockPosition()), MobSpawnType.MOB_SUMMONED,
                            null, null);
                    piglin.moveTo(target.getX(), target.getY(), target.getZ(), target.getYRot(), target.getXRot());
                    piglin.tame(player);
                    if (target instanceof AgeableMob) {
                        piglin.setAge(((Pig) target).getAge());
                    } else {
                        piglin.setAge(target.isBaby() ? -level.random.nextInt(6000, 24000) : 0);
                    }
                    piglin.sendMessageToOwner(FriendlyPiglin.SUMMONED);
                    target.discard();
                    level.addFreshEntity(piglin);
                    LightningBolt lightningBolt = EntityType.LIGHTNING_BOLT.create(level);
                    lightningBolt.moveTo(Vec3.atBottomCenterOf(target.blockPosition()));
                    lightningBolt.setCause(player);
                    level.addFreshEntity(lightningBolt);
                } else if (attacker.getRandom().nextInt(5) == 0) {
                    LightningBolt lightningBolt = ModEntities.ANCIENT_LIGHTNING_BOLT.get().create(level);
                    lightningBolt.moveTo(Vec3.atBottomCenterOf(target.blockPosition()));
                    lightningBolt.setCause(player);
                    level.addFreshEntity(lightningBolt);
                }
            }
        }
        return super.hurtEnemy(stack, target, attacker);
    }
}
