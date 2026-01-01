package com.github.teamfossilsarcheology.fossil.forge.data.providers;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.entity.ModEntities;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricMobType;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.VanillaEntityInfo;
import com.github.teamfossilsarcheology.fossil.tags.ModEntityTypeTags;
import com.github.teamfossilsarcheology.fossil.util.ModConstants;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.ModList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

import static net.minecraft.world.entity.EntityType.*;

public class ModEntityTypeTagsProvider extends EntityTypeTagsProvider {

    public ModEntityTypeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookup, FossilMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        addTag(ModEntityTypeTags.LIVESTOCK, AXOLOTL, CHICKEN, COW, DONKEY, GOAT, HORSE, LLAMA, MULE, PANDA, PIG, RABBIT, SHEEP);
        var mammal = tag(ModEntityTypeTags.MAMMAL);
        for (VanillaEntityInfo info : VanillaEntityInfo.values()) {
            if (info.mobType == PrehistoricMobType.MAMMAL) {
                mammal.add(info.entityType());
            }
        }
        addTag(EntityTypeTags.FREEZE_IMMUNE_ENTITY_TYPES, ModEntities.MAMMOTH.get(), ModEntities.ELASMOTHERIUM.get());
        if (ModList.get().isLoaded(ModConstants.PREHISTORIC_FAUNA)) {
            PFaunaTagsProvider.addEntityTypeTags(this);
        }
    }

    protected IntrinsicTagAppender<EntityType<?>> addTag(TagKey<EntityType<?>> key, EntityType<?>... toAdd) {
        return tag(key).add(Arrays.stream(toAdd).toArray(EntityType<?>[]::new));
    }
}
