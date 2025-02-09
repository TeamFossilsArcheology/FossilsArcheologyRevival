package com.github.teamfossilsarcheology.fossil.forge.data.providers;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.entity.ModEntities;
import com.github.teamfossilsarcheology.fossil.tags.ModEntityTypeTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static net.minecraft.world.entity.EntityType.*;

public class ModEntityTypeTagsProvider extends EntityTypeTagsProvider {

    public ModEntityTypeTagsProvider(DataGenerator arg, @Nullable ExistingFileHelper existingFileHelper) {
        super(arg, FossilMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        tag(ModEntityTypeTags.LIVESTOCK).add(AXOLOTL, CHICKEN, COW, DONKEY, GOAT, HORSE, LLAMA, MULE, PANDA, PIG, RABBIT, SHEEP);
        tag(EntityTypeTags.FREEZE_IMMUNE_ENTITY_TYPES).add(ModEntities.MAMMOTH.get(), ModEntities.ELASMOTHERIUM.get());
    }

    @Override
    public @NotNull String getName() {
        return "Fossil Entity Type Tags";
    }
}
