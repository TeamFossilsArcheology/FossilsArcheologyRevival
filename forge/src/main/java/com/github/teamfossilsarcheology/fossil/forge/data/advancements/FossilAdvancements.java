package com.github.teamfossilsarcheology.fossil.forge.data.advancements;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.advancements.ImplantEmbryoTrigger;
import com.github.teamfossilsarcheology.fossil.advancements.IncubateEggTrigger;
import com.github.teamfossilsarcheology.fossil.advancements.OpenSarcophagusTrigger;
import com.github.teamfossilsarcheology.fossil.advancements.ScarabTameTrigger;
import com.github.teamfossilsarcheology.fossil.entity.ModEntities;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.VanillaEntityInfo;
import com.github.teamfossilsarcheology.fossil.tags.ModItemTags;
import com.github.teamfossilsarcheology.fossil.world.dimension.ModDimensions;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.*;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.github.teamfossilsarcheology.fossil.block.ModBlocks.*;
import static com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricEntityInfo.*;
import static com.github.teamfossilsarcheology.fossil.item.ModItems.*;

public class FossilAdvancements implements Consumer<Consumer<Advancement>> {
    @Override
    public void accept(Consumer<Advancement> consumer) {
        Advancement root = Advancement.Builder.advancement().display(BIO_FOSSIL.get(),
                        Component.translatable("advancements.fossil.root.title"),
                        Component.translatable("advancements.fossil.root.description"),
                        FossilMod.location("textures/block/ancient_stone_bricks.png"), FrameType.TASK, true, false, false)
                .addCriterion("requirement", InventoryChangeTrigger.TriggerInstance.hasItems(Blocks.CRAFTING_TABLE))
                .save(consumer, FossilMod.MOD_ID + ":fossil/root");
        Advancement breakFossil = simple(root, "break_fossil", consumer, RELIC_SCRAP, BIO_FOSSIL, PlANT_FOSSIL, SHALE_FOSSIL, SKULL_BLOCK);
        Advancement analyzer = simple(breakFossil, consumer, ANALYZER);
        Advancement fossilSeed = tag(FERN_SEED_FOSSIL.get(), ModItemTags.FOSSIL_SEEDS, analyzer, consumer);
        Advancement restoredSeed = tag(FERN_SEED.get(), ModItemTags.RESTORED_SEEDS, fossilSeed, consumer);

        Advancement figurine = tag(STEVE_FIGURINE_DESTROYED.get(), ModItemTags.FIGURINES, breakFossil, consumer);
        Advancement anuLair = other(figurine, ANU_STATUE, "anu_lair", ChangeDimensionTrigger.TriggerInstance.changedDimensionTo(ModDimensions.ANU_LAIR), consumer);
        Advancement anubite = other(anuLair, ANUBITE_STATUE, "anubite",
                KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(ModEntities.ANUBITE.get())), consumer);
        Advancement sarcophagus = other(anuLair, SARCOPHAGUS, "sarcophagus",
                OpenSarcophagusTrigger.TriggerInstance.useScarab(), consumer);
        Advancement killAnu = other(sarcophagus, ANCIENT_KEY, "kill_anu",
                KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(ModEntities.ANU_BOSS.get())), consumer);
        Advancement ancientClock = simple(killAnu, consumer, ANCIENT_CLOCK);

        Advancement frozenMeat = simple(breakFossil, consumer, FROZEN_MEAT);
        Advancement scarabGem = simple(breakFossil, consumer, SCARAB_GEM);
        Advancement scarabTame = other(scarabGem, AQUATIC_SCARAB_GEM, "scarab_tame", ScarabTameTrigger.TriggerInstance.useScarab(), consumer);

        Advancement stoneTablet = simple(analyzer, consumer, STONE_TABLET);
        Advancement tarDrop = simple(breakFossil, consumer, TAR_DROP);
        Advancement tarFossil = simple(tarDrop, consumer, TAR_FOSSIL);

        Advancement brokenSword = simple(root, consumer, BROKEN_SWORD, BROKEN_HELMET);
        Advancement worktable = simple(brokenSword, consumer, WORKTABLE);
        Advancement ancientSword = simple(worktable, consumer, ANCIENT_SWORD, ANCIENT_HELMET);

        Advancement dna = tag(TRICERATOPS.dnaItem, ModItemTags.DNA, analyzer, consumer);
        Advancement cultureVat = simple(dna, consumer, CULTURE_VAT);
        Advancement failuresaurus = other(cultureVat, FAILURESAURUS_FLESH, "failuresaurus",
                PlayerHurtEntityTrigger.TriggerInstance.playerHurtEntity(EntityPredicate.Builder.entity().of(ModEntities.FAILURESAURUS.get()).build()), consumer);
        Advancement embryo = tag(MAMMOTH.embryoItem, ModItemTags.EMBRYOS, cultureVat, consumer);
        Advancement dinoEgg = tag(TRICERATOPS.eggItem, ModItemTags.ALL_EGGS, cultureVat, consumer, "dino_eggs");
        Advancement dinopedia = simple(dinoEgg, consumer, DINOPEDIA);

        Advancement.Builder builder = Advancement.Builder.advancement().display(TYRANNOSAURUS.eggItem, title("all_eggs"),
                        description("all_eggs"), null, FrameType.CHALLENGE, true, true, false)
                .parent(dinoEgg).rewards(AdvancementRewards.Builder.experience(1000));
        for (PrehistoricEntityInfo info : PrehistoricEntityInfo.values()) {
            if (info.eggItem != null || info.cultivatedBirdEggItem != null) {
                builder.addCriterion(key(info.entityType()).getPath(), IncubateEggTrigger.TriggerInstance.incubateEgg(info.entityType()));
            }
        }
        builder.save(consumer, FossilMod.MOD_ID + ":fossil/all_eggs");

        builder = Advancement.Builder.advancement().display(MAMMOTH.embryoItem, title("all_embryos"), description("all_embryos"),
                        null, FrameType.CHALLENGE, true, true, false)
                .parent(embryo).rewards(AdvancementRewards.Builder.experience(500));
        for (PrehistoricEntityInfo info : values()) {
            if (info.embryoItem != null) {
                builder.addCriterion(key(info.embryoItem).getPath(), ImplantEmbryoTrigger.TriggerInstance.implantEmbryo(info.embryoItem));
            }
        }
        for (VanillaEntityInfo info : VanillaEntityInfo.values()) {
            if (info.embryoItem != null) {
                builder.addCriterion(key(info.embryoItem).getPath(), ImplantEmbryoTrigger.TriggerInstance.implantEmbryo(info.embryoItem));
            }
        }
        builder.save(consumer, FossilMod.MOD_ID + ":fossil/all_embryos");

    }

    private Advancement other(Advancement parent, RegistrySupplier<? extends ItemLike> item, String key, CriterionTriggerInstance trigger, Consumer<Advancement> consumer) {
        return Advancement.Builder.advancement().display(item.get(), title(key), description(key),
                        null, FrameType.TASK, true, true, false)
                .parent(parent)
                .addCriterion("requirement", trigger)
                .save(consumer, FossilMod.MOD_ID + ":fossil/" + key);
    }

    private Advancement simple(Advancement parent, String key, Consumer<Advancement> consumer, ItemLike... items) {
        ItemLike item = items[0];
        Advancement.Builder builder = Advancement.Builder.advancement().display(item, title(key), description(key),
                        null, FrameType.TASK, true, true, false)
                .parent(parent).requirements(RequirementsStrategy.OR);
        for (ItemLike itemLike : items) {
            builder.addCriterion(key(itemLike.asItem()).getPath(), InventoryChangeTrigger.TriggerInstance.hasItems(itemLike));
        }
        return builder.save(consumer, FossilMod.MOD_ID + ":fossil/" + key);
    }

    @SafeVarargs
    private Advancement simple(Advancement parent, String key, Consumer<Advancement> consumer, RegistrySupplier<? extends ItemLike>... items) {
        return simple(parent, key, consumer, Arrays.stream(items).map(Supplier::get).toArray(ItemLike[]::new));
    }

    @SafeVarargs
    private Advancement simple(Advancement parent, Consumer<Advancement> consumer, RegistrySupplier<? extends ItemLike>... items) {
        return simple(parent, key(items[0].get().asItem()).getPath(), consumer, Arrays.stream(items).map(Supplier::get).toArray(ItemLike[]::new));
    }

    private Advancement tag(ItemLike item, TagKey<Item> tag, Advancement parent, Consumer<Advancement> consumer, String key) {
        return Advancement.Builder.advancement().display(item, title(key), description(key),
                        null, FrameType.TASK, true, true, false)
                .parent(parent)
                .addCriterion("requirement", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(tag).build()))
                .save(consumer, FossilMod.MOD_ID + ":fossil/" + key);
    }

    private Advancement tag(ItemLike item, TagKey<Item> tag, Advancement parent, Consumer<Advancement> consumer) {
        String key = tag.location().getPath();
        return tag(item, tag, parent, consumer, key);
    }

    private Component title(String key) {
        return Component.translatable(String.format("advancements.%s.%s.title", FossilMod.MOD_ID, key));
    }

    private Component description(String key) {
        return Component.translatable(String.format("advancements.%s.%s.description", FossilMod.MOD_ID, key));
    }

    private ResourceLocation key(Item item) {
        return ForgeRegistries.ITEMS.getKey(item);
    }

    private ResourceLocation key(EntityType<?> entityType) {
        return ForgeRegistries.ENTITY_TYPES.getKey(entityType);
    }
}
