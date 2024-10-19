package com.github.teamfossilsarcheology.fossil.entity.prehistoric.flying;

import com.github.teamfossilsarcheology.fossil.entity.ai.*;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricFlying;
import com.github.teamfossilsarcheology.fossil.entity.util.Util;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.core.builder.Animation;

public class Tropeognathus extends PrehistoricFlying {
    public static final String FLY = "fa.tropeognathus.fly";
    public static final String IDLE = "fa.tropeognathus.idle";
    public static final String IDLE_CALL = "fa.tropeognathus.idlecall";
    public static final String IDLE_LOOKAROUND = "fa.tropeognathus.idlelookaround";
    public static final String IDLE_PREEN = "fa.tropeognathus.idlepreen";
    public static final String IDLE_SWIM = "fa.tropeognathus.idleswim";
    public static final String TAKEOFF_GROUND = "fa.tropeognathus.groundtakeoff";
    public static final String TAKEOFF_WATER = "fa.tropeognathus.watertakeoff";

    public Tropeognathus(EntityType<Tropeognathus> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(Util.IMMOBILE + 3, new FleeBattleGoal(this, 1.0D));
        goalSelector.addGoal(Util.ATTACK, new DelayedAttackGoal(this, 1.0, true));
        targetSelector.addGoal(1, new DinoOwnerHurtByTargetGoal(this));
        targetSelector.addGoal(2, new DinoOwnerHurtTargetGoal(this));
        targetSelector.addGoal(3, new DinoHurtByTargetGoal(this));
    }

    @Override
    public PrehistoricEntityInfo info() {
        return PrehistoricEntityInfo.TROPEOGNATHUS;
    }

    @Override
    public Item getOrderItem() {
        return Items.STICK;
    }

    @Override
    public @NotNull Animation nextIdleAnimation() {
        String key = IDLE;

        if (isInWater()) {
            key = IDLE_SWIM;
        } else if (isFlying()) {
            key = FLY;//TODO: Hover anim missing
        } else {
            int number = random.nextInt(10);
            switch (number) {
                case 7 -> key = IDLE_PREEN;
                case 8 -> key = IDLE_LOOKAROUND;
                case 9 -> key = IDLE_CALL;
            }
        }

        return getAllAnimations().get(key);
    }

    @Override
    public @NotNull Animation nextTakeOffAnimation() {
        String key = TAKEOFF_GROUND;
        if (isInWater()) key = TAKEOFF_WATER;

        return getAllAnimations().get(key);
    }
}
