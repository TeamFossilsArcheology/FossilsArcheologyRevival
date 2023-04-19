package com.fossil.fossil.item;

import com.fossil.fossil.entity.Javelin;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class JavelinItem extends TieredItem {
    private final boolean ancient;

    public JavelinItem(Tier tier, boolean ancient) {
        super(tier, new JavelinProperties().stacksToWithDurability(16, 30).tab(ModTabs.FAITEMTAB));
        this.ancient = ancient;
    }

    public JavelinItem(Tier tier) {
        this(tier, false);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity shooter, int timeCharged) {
        if (!(shooter instanceof Player player)) {
            return;
        }
        boolean infiniteAmmo = player.getAbilities().instabuild || EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, stack) > 0;
        int i = this.getUseDuration(stack) - timeCharged;
        float speed = BowItem.getPowerForTime(i);
        if (speed < 0.1) {
            return;
        }
        if (!level.isClientSide) {
            int damage = stack.getMaxDamage() - (stack.getDamageValue() + (player.getAbilities().instabuild ? 0 : 1));
            Javelin javelin = new Javelin(level, shooter, getTier(), ancient, damage);
            javelin.shootFromRotation(player, player.getXRot(), player.getYRot(), 0, speed * 3, 1);
            if (speed == 1) {
                javelin.setCritArrow(true);
            }
            int powerLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, stack);
            if (powerLevel > 0) {
                javelin.setBaseDamage(javelin.getBaseDamage() + powerLevel * 0.5 + 0.5);
            }
            int punchLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH_ARROWS, stack);
            if (punchLevel > 0) {
                javelin.setKnockback(punchLevel);
            }
            int flameLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FLAMING_ARROWS, stack);
            if (flameLevel > 0) {
                javelin.setSecondsOnFire(100);
            }
            if (infiniteAmmo) {
                javelin.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
            }
            level.addFreshEntity(javelin);
        }
        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1,
                1 / (level.random.nextFloat() * 0.4f + 1.2f) + speed * 0.5f);
        if (!infiniteAmmo) {
            stack.shrink(1);
        }
        player.awardStat(Stats.ITEM_USED.get(this));
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    @Override
    public @NotNull UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemStack = player.getItemInHand(usedHand);
        player.startUsingItem(usedHand);
        return InteractionResultHolder.consume(itemStack);
    }

    @Override
    public int getEnchantmentValue() {
        return 1;
    }

    public static class JavelinProperties extends Item.Properties {

        public JavelinProperties stacksToWithDurability(int maxStackSize, int durability) {
            durability(durability);
            this.maxStackSize = maxStackSize;
            return this;
        }
    }
}
