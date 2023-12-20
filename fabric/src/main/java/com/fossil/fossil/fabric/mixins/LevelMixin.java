package com.fossil.fossil.fabric.mixins;

import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.entity.prehistoric.parts.MultiPart;
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
    private void addMultiPartsToEntityQuery(Entity entity, AABB area, Predicate<? super Entity> predicate, CallbackInfoReturnable<List<Entity>> cir) {
        getEntities().get(area, entity2 -> {
            if (entity2 instanceof Prehistoric prehistoric) {
                for (MultiPart multiPart : prehistoric.getCustomParts()) {
                    Entity part = multiPart.getEntity();
                    if (part == entity || !part.getBoundingBox().intersects(area) || !predicate.test(part)) continue;
                    cir.getReturnValue().add(part);
                }
            }
        });


    }

    @Inject(method = "getEntities(Lnet/minecraft/world/level/entity/EntityTypeTest;Lnet/minecraft/world/phys/AABB;Ljava/util/function/Predicate;)Ljava/util/List;", at = @At(value = "RETURN"))
    private <T extends Entity> void addMultiPartsToEntityQuery(EntityTypeTest<Entity, T> entityTypeTest, AABB area, Predicate<? super T> predicate,
                                                               CallbackInfoReturnable<List<T>> cir) {
        getEntities().get(entityTypeTest, area, entity -> {
            if (entity instanceof Prehistoric prehistoric) {
                for (MultiPart multiPart : prehistoric.getCustomParts()) {
                    Entity part = multiPart.getEntity();
                    T entity2 = entityTypeTest.tryCast(part);
                    if (entity2 == null || !predicate.test(entity2)) continue;
                    cir.getReturnValue().add(entity2);
                }
            }
        });
    }
}
