package fossilsarcheology.server.recipe;

import fossilsarcheology.server.block.FABlockRegistry;
import fossilsarcheology.server.block.FAFluidRegistry;
import fossilsarcheology.server.entity.prehistoric.MobType;
import fossilsarcheology.server.entity.prehistoric.PrehistoricEntityType;
import fossilsarcheology.server.item.FAItemRegistry;
import fossilsarcheology.server.item.variant.DinosaurBoneType;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.BannerPattern;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fluids.UniversalBucket;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class FARecipeRegistry {

    public static String[] dyes = {"Black", "Red", "Green", "Brown", "Blue", "Purple", "Cyan", "LightGray", "Gray", "Pink", "Lime", "Yellow", "LightBlue", "Magenta", "Orange", "White"};

    private FARecipeRegistry() {
    }

    public static void blocks() {
        FAItemRegistry.ANCIENT_HELMET_MATERIAL.setRepairItem(new ItemStack(FAItemRegistry.SCARAB_GEM));
        FAItemRegistry.SCARAB_MATERIAL.setRepairItem(new ItemStack(FAItemRegistry.SCARAB_GEM));
        FAItemRegistry.BONE.setRepairItem(new ItemStack(Items.BONE));
        addBanner("amphora", new ItemStack(FABlockRegistry.AMPHORA_VASE, 1, 1));
        addBanner("kylix", new ItemStack(FABlockRegistry.KYLIX_VASE, 1, 1));
        addBanner("volute", new ItemStack(FABlockRegistry.VOLUTE_VASE, 1, 1));
        addBanner("ancient_clock", new ItemStack(FAItemRegistry.ANCIENT_CLOCK));
        addBanner("ancient_sword", new ItemStack(FAItemRegistry.ANCIENT_SWORD));
        addBanner("dna", new ItemStack(FABlockRegistry.CULTIVATE_IDLE));
        addBanner("nautilus", new ItemStack(FAItemRegistry.SHELL));
        addBanner("biofossil", new ItemStack(FAItemRegistry.BIOFOSSIL));
        for (DinosaurBoneType bone : DinosaurBoneType.values()) {
            GameRegistry.addShapelessRecipe(new ResourceLocation("fossil:bonemeal_from_" + bone.getResourceName() + "_leg_bone"), new ResourceLocation("fossil"), new ItemStack(Items.DYE, 3, 15), Ingredient.fromStacks(new ItemStack(FAItemRegistry.LEG_BONE, 1, bone.ordinal())));
            GameRegistry.addShapelessRecipe(new ResourceLocation("fossil:bonemeal_from_" + bone.getResourceName() + "_unique_item"), new ResourceLocation("fossil"), new ItemStack(Items.DYE, 3, 15), Ingredient.fromStacks(new ItemStack(FAItemRegistry.UNIQUE_ITEM, 1, bone.ordinal())));
            GameRegistry.addShapelessRecipe(new ResourceLocation("fossil:bonemeal_from_" + bone.getResourceName() + "_foot"), new ResourceLocation("fossil"), new ItemStack(Items.DYE, 3, 15), Ingredient.fromStacks(new ItemStack(FAItemRegistry.FOOT, 1, bone.ordinal())));
            GameRegistry.addShapelessRecipe(new ResourceLocation("fossil:bonemeal_from_" + bone.getResourceName() + "_skull"), new ResourceLocation("fossil"), new ItemStack(Items.DYE, 3, 15), Ingredient.fromStacks(new ItemStack(FAItemRegistry.SKULL, 1, bone.ordinal())));
            GameRegistry.addShapelessRecipe(new ResourceLocation("fossil:bonemeal_from_" + bone.getResourceName() + "_arm_bone"), new ResourceLocation("fossil"), new ItemStack(Items.DYE, 3, 15), Ingredient.fromStacks(new ItemStack(FAItemRegistry.ARM_BONE, 1, bone.ordinal())));
            GameRegistry.addShapelessRecipe(new ResourceLocation("fossil:bonemeal_from_" + bone.getResourceName() + "_ribcage"), new ResourceLocation("fossil"), new ItemStack(Items.DYE, 3, 15), Ingredient.fromStacks(new ItemStack(FAItemRegistry.RIBCAGE, 1, bone.ordinal())));
            GameRegistry.addShapelessRecipe(new ResourceLocation("fossil:bonemeal_from_" + bone.getResourceName() + "_vertabrae"), new ResourceLocation("fossil"), new ItemStack(Items.DYE, 3, 15), Ingredient.fromStacks(new ItemStack(FAItemRegistry.VERTEBRAE, 1, bone.ordinal())));
        }
        GameRegistry.addSmelting(FABlockRegistry.DENSE_SAND, new ItemStack(FABlockRegistry.STRONG_GLASS), 3.0F);
        GameRegistry.addSmelting(FAItemRegistry.RAW_CHICKEN_SOUP, new ItemStack(FAItemRegistry.COOKED_CHICKEN_SOUP), 1.0F);
        GameRegistry.addSmelting(Items.EGG, new ItemStack(FAItemRegistry.COOKED_EGG), 1.0F);
        GameRegistry.addSmelting(FABlockRegistry.PALM_LOG, new ItemStack(Items.COAL, 1, 1), 0.15F);
        GameRegistry.addSmelting(FABlockRegistry.CALAMITES_LOG, new ItemStack(Items.COAL, 1, 1), 0.15F);
        GameRegistry.addSmelting(FABlockRegistry.CORDAITES_LOG, new ItemStack(Items.COAL, 1, 1), 0.15F);
        GameRegistry.addSmelting(FABlockRegistry.SIGILLARIA_LOG, new ItemStack(Items.COAL, 1, 1), 0.15F);
        for (int i = 0; i < PrehistoricEntityType.values().length; i++) {
            if (PrehistoricEntityType.values()[i].mobType == MobType.DINOSAUR) {
                GameRegistry.addSmelting(PrehistoricEntityType.values()[i].eggItem, new ItemStack(FAItemRegistry.COOKED_EGG), 1.0F);
            }
            if (PrehistoricEntityType.values()[i].mobType == MobType.BIRD) {
                GameRegistry.addSmelting(PrehistoricEntityType.values()[i].birdEggItem, new ItemStack(FAItemRegistry.COOKED_EGG), 1.0F);
                GameRegistry.addSmelting(PrehistoricEntityType.values()[i].bestBirdEggItem, new ItemStack(FAItemRegistry.COOKED_EGG), 1.0F);
            }
            if (PrehistoricEntityType.values()[i].mobType == MobType.CHICKEN) {
                GameRegistry.addSmelting(PrehistoricEntityType.values()[i].bestBirdEggItem, new ItemStack(FAItemRegistry.COOKED_EGG), 1.0F);
            }
            if (PrehistoricEntityType.values()[i].foodItem != null) {
                GameRegistry.addSmelting(PrehistoricEntityType.values()[i].foodItem, new ItemStack(PrehistoricEntityType.values()[i].cookedFoodItem), 1.5F);
            }
            if (PrehistoricEntityType.values()[i].fishItem != null && PrehistoricEntityType.values()[i] != PrehistoricEntityType.NAUTILUS) {
                GameRegistry.addSmelting(PrehistoricEntityType.values()[i].fishItem, new ItemStack(PrehistoricEntityType.values()[i].cookedFoodItem), 1.5F);
            }
            if (PrehistoricEntityType.values()[i].fishItem != null && PrehistoricEntityType.values()[i] == PrehistoricEntityType.NAUTILUS) {
                GameRegistry.addSmelting(PrehistoricEntityType.values()[i].fishItem, new ItemStack(FAItemRegistry.SJL), 1.5F);
            }

        }
    }

    public static BannerPattern addBanner(String name, ItemStack craftingStack) {
        Class<?>[] classes = {String.class, String.class, ItemStack.class};
        Object[] names = {name, "fossil." + name, craftingStack};
        return EnumHelper.addEnum(BannerPattern.class, name.toUpperCase(), classes, names);
    }
}
