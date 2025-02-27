package com.github.teamfossilsarcheology.fossil.forge.data.providers;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.entity.ModEntities;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricMobType;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.VanillaEntityInfo;
import com.github.teamfossilsarcheology.fossil.tags.ModEntityTypeTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.EntityTypeTags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

import static net.minecraft.world.entity.EntityType.*;

public class ModEntityTypeTagsProvider extends EntityTypeTagsProvider {

    public ModEntityTypeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookup, FossilMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        tag(ModEntityTypeTags.LIVESTOCK).add(AXOLOTL, CHICKEN, COW, DONKEY, GOAT, HORSE, LLAMA, MULE, PANDA, PIG, RABBIT, SHEEP);
        var mammal = tag(ModEntityTypeTags.MAMMAL);
        for (VanillaEntityInfo info : VanillaEntityInfo.values()) {
            if (info.mobType == PrehistoricMobType.MAMMAL) {
                mammal.add(info.entityType());
            }
        }
        tag(EntityTypeTags.FREEZE_IMMUNE_ENTITY_TYPES).add(ModEntities.MAMMOTH.get(), ModEntities.ELASMOTHERIUM.get());
    }
}
