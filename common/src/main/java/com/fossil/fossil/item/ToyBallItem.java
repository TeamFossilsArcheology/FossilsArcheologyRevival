package com.fossil.fossil.item;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.entity.ModEntities;
import com.fossil.fossil.entity.ToyBall;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Predicate;

public class ToyBallItem extends Item {
    private static final Predicate<Entity> ENTITY_PREDICATE = EntitySelector.NO_SPECTATORS.and(Entity::isPickable);
    private final DyeColor color;
    private final Component name;

    public ToyBallItem(DyeColor color, Properties properties) {
        super(properties);
        this.color = color;
        this.name = new TranslatableComponent("item." + Fossil.MOD_ID + ".toy_ball", new TranslatableComponent("color.minecraft." + color.getName()));
    }

    @Override
    public @NotNull Component getName(ItemStack stack) {
        return name;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemStack = player.getItemInHand(usedHand);
        BlockHitResult hitResult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.ANY);
        if (hitResult.getType() == HitResult.Type.MISS) {
            return InteractionResultHolder.pass(itemStack);
        }
        Vec3 vec3 = player.getViewVector(1.0f);
        List<Entity> list = level.getEntities(player, player.getBoundingBox().expandTowards(vec3.scale(5)).inflate(1), ENTITY_PREDICATE);
        if (!list.isEmpty()) {
            Vec3 eyePos = player.getEyePosition();
            for (Entity entity : list) {
                AABB aABB = entity.getBoundingBox().inflate(entity.getPickRadius());
                if (!aABB.contains(eyePos)) continue;
                return InteractionResultHolder.pass(itemStack);
            }
        }
        if (hitResult.getType() == HitResult.Type.BLOCK) {
            if (level instanceof ServerLevel serverLevel) {
                ToyBall entity = ModEntities.TOY_BALL.get().create(serverLevel);
                if (entity == null) {
                    return InteractionResultHolder.fail(itemStack);
                }
                entity.setColor(color);
                entity.moveTo(hitResult.getLocation().x, hitResult.getLocation().y, hitResult.getLocation().z, 0, 0);
                level.addFreshEntity(entity);
            }
            return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
        }
        return InteractionResultHolder.pass(itemStack);
    }
}
