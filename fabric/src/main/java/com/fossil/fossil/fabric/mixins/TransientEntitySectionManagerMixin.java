package com.fossil.fossil.fabric.mixins;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.entity.prehistoric.parts.MultiPart;
import net.minecraft.core.SectionPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.entity.*;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(TransientEntitySectionManager.class)
public abstract class TransientEntitySectionManagerMixin<T extends EntityAccess> {
    @Shadow
    @Final
    EntitySectionStorage<T> sectionStorage;

    /**
     * Vanilla hitresults stop working if the bounding box of an entity is in an empty EntitySection.
     * There are multiple ways to get around this, and we have opted to manually add/remove the part entities to/from their correct EntitySections.
     *
     * @see EntitySection
     * @see EntitySectionStorage#getEntities(AABB, Consumer)
     */
    @Inject(method = "addEntity", at = @At("HEAD"))
    private void addPartEntitiesToSections(T entity, CallbackInfo ci) {
        if (entity instanceof Prehistoric prehistoric && prehistoric.isCustomMultiPart()) {
            for (MultiPart part : prehistoric.getCustomParts()) {
                long l = SectionPos.asLong(part.getParent().blockPosition());
                EntitySection<T> section = this.sectionStorage.getOrCreateSection(l);
                T partEntity = (T) part.getEntity();
                section.add(partEntity);
                partEntity.setLevelCallback(new EntityInLevelCallback() {
                    private long currentSectionKey = l;
                    private EntitySection<T> currentSection = section;

                    @Override
                    public void onMove() {
                        long newSectionKey = SectionPos.asLong(partEntity.blockPosition());
                        if (newSectionKey != currentSectionKey) {
                            if (!currentSection.remove(partEntity)) {
                                Fossil.LOGGER.warn("PrehistoricPart {} wasn't found in section {} (moving to {})", partEntity, SectionPos.of(currentSectionKey), newSectionKey);
                            }
                            removeSectionIfEmpty(currentSectionKey, currentSection);
                            EntitySection<T> newSection = sectionStorage.getOrCreateSection(newSectionKey);
                            newSection.add(partEntity);
                            currentSection = newSection;
                            currentSectionKey = newSectionKey;
                        }
                    }

                    @Override
                    public void onRemove(Entity.RemovalReason reason) {
                        System.out.println("Test2");
                        if (!currentSection.remove(partEntity)) {
                            Fossil.LOGGER.warn("PrehistoricPart {} wasn't found in section {} (destroying due to {})", partEntity, SectionPos.of(currentSectionKey), reason);
                        }
                        partEntity.setLevelCallback(NULL);
                        removeSectionIfEmpty(currentSectionKey, currentSection);
                    }
                });
            }
        }
    }

    @Shadow
    abstract void removeSectionIfEmpty(long section, EntitySection<T> entitySection);
}
