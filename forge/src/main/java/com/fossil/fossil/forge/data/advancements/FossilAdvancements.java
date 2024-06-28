package com.fossil.fossil.forge.data.advancements;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.advancements.ImplantEmbryoTrigger;
import com.fossil.fossil.advancements.IncubateEggTrigger;
import com.fossil.fossil.advancements.OpenSarcophagusTrigger;
import com.fossil.fossil.advancements.ScarabTameTrigger;
import com.fossil.fossil.block.ModBlocks;
import com.fossil.fossil.entity.ModEntities;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import com.fossil.fossil.entity.prehistoric.base.VanillaEntityInfo;
import com.fossil.fossil.item.ModItems;
import com.fossil.fossil.tags.ModItemTags;
import com.fossil.fossil.world.dimension.ModDimensions;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.*;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;

import java.util.function.Consumer;

public class FossilAdvancements implements Consumer<Consumer<Advancement>> {
    @Override
    public void accept(Consumer<Advancement> consumer) {
        Advancement root = Advancement.Builder.advancement().display(ModItems.BIO_FOSSIL.get(),
                        new TranslatableComponent("advancements.fossil.root.title"),
                        new TranslatableComponent("advancements.fossil.root.description"),
                        new ResourceLocation(Fossil.MOD_ID, "textures/block/ancient_stone_bricks.png"), FrameType.TASK, true, false, false)
                .addCriterion("requirement", InventoryChangeTrigger.TriggerInstance.hasItems(Blocks.CRAFTING_TABLE))
                .save(consumer, Fossil.MOD_ID + ":fossil/root");
        Advancement breakFossil = simple(root, "break_fossil", consumer, ModItems.RELIC_SCRAP.get(), ModItems.BIO_FOSSIL.get(), ModItems.PlANT_FOSSIL.get(), ModBlocks.SKULL_BLOCK.get());
        Advancement analyzer = simple(breakFossil, consumer, ModBlocks.ANALYZER.get());
        Advancement fossilSeed = tag(ModItems.FERN_SEED_FOSSIL.get(), ModItemTags.FOSSIL_SEEDS, analyzer, consumer);
        Advancement restoredSeed = tag(ModItems.FERN_SEED.get(), ModItemTags.RESTORED_SEEDS, fossilSeed, consumer);

        Advancement figurine = tag(ModBlocks.STEVE_FIGURINE_DESTROYED.get(), ModItemTags.FIGURINES, breakFossil, consumer);
        Advancement anuLair = other(figurine, ModBlocks.ANU_STATUE.get(), "anu_lair", ChangeDimensionTrigger.TriggerInstance.changedDimensionTo(ModDimensions.ANU_LAIR), consumer);
        Advancement anubite = other(anuLair, ModBlocks.ANUBITE_STATUE.get(), "anubite",
                KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(ModEntities.ANUBITE.get())), consumer);
        Advancement sarcophagus = other(anuLair, ModBlocks.SARCOPHAGUS.get(), "sarcophagus",
                OpenSarcophagusTrigger.TriggerInstance.useScarab(), consumer);
        Advancement killAnu = other(sarcophagus, ModItems.ANCIENT_KEY.get(), "kill_anu",
                KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(ModEntities.ANU_BOSS.get())), consumer);
        Advancement ancientClock = simple(killAnu, consumer, ModItems.ANCIENT_CLOCK.get());

        Advancement frozenMeat = simple(breakFossil, consumer, ModItems.FROZEN_MEAT.get());
        Advancement scarabGem = simple(breakFossil, consumer, ModItems.SCARAB_GEM.get());
        Advancement scarabTame = other(scarabGem, ModItems.AQUATIC_SCARAB_GEM.get(), "scarab_tame", ScarabTameTrigger.TriggerInstance.useScarab(), consumer);

        Advancement stoneTablet = simple(analyzer, consumer, ModItems.STONE_TABLET.get());
        Advancement tarDrop = simple(breakFossil, consumer, ModItems.TAR_DROP.get());
        Advancement tarFossil = simple(tarDrop, consumer, ModItems.TAR_FOSSIL.get());

        Advancement brokenSword = simple(root, consumer, ModItems.BROKEN_SWORD.get(), ModItems.BROKEN_HELMET.get());
        Advancement worktable = simple(brokenSword, consumer, ModBlocks.WORKTABLE.get());
        Advancement ancientSword = simple(worktable, consumer, ModItems.ANCIENT_SWORD.get(), ModItems.ANCIENT_HELMET.get());

        Advancement dna = tag(PrehistoricEntityInfo.TRICERATOPS.dnaItem, ModItemTags.DNA, analyzer, consumer);
        Advancement cultureVat = simple(dna, consumer, ModBlocks.CULTURE_VAT.get());
        Advancement failuresaurus = other(cultureVat, ModItems.FAILURESAURUS_FLESH.get(), "failuresaurus",
                PlayerHurtEntityTrigger.TriggerInstance.playerHurtEntity(EntityPredicate.Builder.entity().of(ModEntities.FAILURESAURUS.get()).build()), consumer);
        Advancement embryo = tag(PrehistoricEntityInfo.MAMMOTH.embryoItem, ModItemTags.EMBRYOS, cultureVat, consumer);
        Advancement dinoEgg = tag(PrehistoricEntityInfo.TRICERATOPS.eggItem, ModItemTags.DINO_EGGS, cultureVat, consumer);
        Advancement dinopedia = simple(dinoEgg, consumer, ModItems.DINOPEDIA.get());

        Advancement.Builder builder = Advancement.Builder.advancement().display(PrehistoricEntityInfo.TYRANNOSAURUS.eggItem,
                        title("all_eggs"), description("all_eggs"), null, FrameType.CHALLENGE, true, true, false)
                .parent(dinoEgg).rewards(AdvancementRewards.Builder.experience(1000));
        for (PrehistoricEntityInfo info : PrehistoricEntityInfo.values()) {
            if (info.eggItem != null || info.cultivatedBirdEggItem != null) {
                builder.addCriterion(info.entityType().getRegistryName().getPath(), IncubateEggTrigger.TriggerInstance.incubateEgg(info.entityType()));
            }
        }
        builder.save(consumer, Fossil.MOD_ID + ":fossil/all_eggs");

        builder = Advancement.Builder.advancement().display(PrehistoricEntityInfo.MAMMOTH.embryoItem,
                        title("all_embryos"), description("all_embryos"), null, FrameType.CHALLENGE, true, true, false)
                .parent(embryo).rewards(AdvancementRewards.Builder.experience(500));
        for (PrehistoricEntityInfo info : PrehistoricEntityInfo.values()) {
            if (info.embryoItem != null) {
                builder.addCriterion(info.embryoItem.getRegistryName().getPath(), ImplantEmbryoTrigger.TriggerInstance.implantEmbryo(info.embryoItem));
            }
        }for (VanillaEntityInfo info : VanillaEntityInfo.values()) {
            if (info.embryoItem != null) {
                builder.addCriterion(info.embryoItem.getRegistryName().getPath(), ImplantEmbryoTrigger.TriggerInstance.implantEmbryo(info.embryoItem));
            }
        }
        builder.save(consumer, Fossil.MOD_ID + ":fossil/all_embryos");

    }

    private Advancement other(Advancement parent, ItemLike item, String key, CriterionTriggerInstance trigger, Consumer<Advancement> consumer) {
        return Advancement.Builder.advancement().display(item, title(key), description(key),
                        null, FrameType.TASK, true, true, false)
                .parent(parent)
                .addCriterion("requirement", trigger)
                .save(consumer, Fossil.MOD_ID + ":fossil/" + key);
    }

    private Advancement simple(Advancement parent, String key, Consumer<Advancement> consumer, ItemLike... items) {
        ItemLike item = items[0];
        Advancement.Builder builder = Advancement.Builder.advancement().display(item, title(key), description(key),
                        null, FrameType.TASK, true, true, false)
                .parent(parent).requirements(RequirementsStrategy.OR);
        for (int i = 0; i < items.length; i++) {
            builder.addCriterion(items[i].asItem().getRegistryName().getPath(), InventoryChangeTrigger.TriggerInstance.hasItems(items[i]));
        }
        return builder.save(consumer, Fossil.MOD_ID + ":fossil/" + key);
    }

    private Advancement simple(Advancement parent, Consumer<Advancement> consumer, ItemLike... items) {
        return simple(parent, items[0].asItem().getRegistryName().getPath(), consumer, items);
    }

    private Advancement tag(ItemLike item, TagKey<Item> tag, Advancement parent, Consumer<Advancement> consumer) {
        String key = tag.location().getPath();
        return Advancement.Builder.advancement().display(item, title(key), description(key),
                        null, FrameType.TASK, true, true, false)
                .parent(parent)
                .addCriterion("requirement", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(tag).build()))
                .save(consumer, Fossil.MOD_ID + ":fossil/" + key);
    }

    private TranslatableComponent title(String key) {
        return new TranslatableComponent(String.format("advancements.%s.%s.title", Fossil.MOD_ID, key));
    }

    private TranslatableComponent description(String key) {
        return new TranslatableComponent(String.format("advancements.%s.%s.description", Fossil.MOD_ID, key));
    }
}
