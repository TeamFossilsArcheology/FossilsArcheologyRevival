package com.fossil.fossil.fabric.mixins;

import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.level.entity.LevelEntityGetter;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.function.Predicate;

/**
 * Fabric has no PartEntity so we mixin our own
 *
 * @see ServerLevelMixin
 */
@Mixin(Level.class)
public abstract class LevelMixin {

    @Shadow
    protected abstract LevelEntityGetter<Entity> getEntities();

    @Inject(method = "getEntities(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/phys/AABB;Ljava/util/function/Predicate;)Ljava/util/List;", at = @At(value = "RETURN"))
    private void addPartEntities(Entity entity, AABB area, Predicate<? super Entity> predicate, CallbackInfoReturnable<List<Entity>> cir) {
        getEntities().get(area, entity2 -> {
            if (entity2 instanceof Prehistoric prehistoric) {
                for (Entity part : prehistoric.getCustomParts()) {
                    if (part == entity || !part.getBoundingBox().intersects(area) || !predicate.test(part)) continue;
                    cir.getReturnValue().add(part);
                }
            }
        });


    }

    @Inject(method = "getEntities(Lnet/minecraft/world/level/entity/EntityTypeTest;Lnet/minecraft/world/phys/AABB;Ljava/util/function/Predicate;)Ljava/util/List;", at = @At(value = "RETURN"))
    private <T extends Entity> void addPartEntities(EntityTypeTest<Entity, T> entityTypeTest, AABB area, Predicate<? super T> predicate,
                                                    CallbackInfoReturnable<List<T>> cir) {
        getEntities().get(entityTypeTest, area, entity -> {
            if (entity instanceof Prehistoric prehistoric) {
                for (Entity part : prehistoric.getCustomParts()) {
                    T entity2 = entityTypeTest.tryCast(part);
                    if (entity2 == null || !predicate.test(entity2)) continue;
                    cir.getReturnValue().add(entity2);
                }
            }
        });
    }
/*
    @ModifyArg(method = "getEntities(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/phys/AABB;Ljava/util/function/Predicate;)Ljava/util/List;", at = @At(value = "INVOKE", target =
            "Lnet/minecraft/world/level/entity/LevelEntityGetter;get(Lnet/minecraft/world/phys/AABB;Ljava/util/function/Consumer;)V"), index = 1)
    public Consumer<EntityAccess> addPartEntities(Consumer<EntityAccess> var2) {
        this.getEntities().get(area, entity2 -> {
            if (entity2 != entity && predicate.test((Entity)entity2)) {
                list.add((Entity)entity2);
            }
            if (entity2 instanceof EnderDragon) {
                for (EnderDragonPart enderDragonPart : ((EnderDragon)entity2).getSubEntities()) {
                    if (entity2 == entity || !predicate.test(enderDragonPart)) continue;
                    list.add(enderDragonPart);
                }
            }
        });
    }*/
}
