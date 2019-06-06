package fossilsarcheology.server.compat.tinkers;

import fossilsarcheology.Revival;
import fossilsarcheology.server.block.FAFluidRegistry;
import fossilsarcheology.server.entity.monster.EntityTarSlime;
import fossilsarcheology.server.item.FAItemRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import slimeknights.mantle.util.RecipeMatch;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.*;
import slimeknights.tconstruct.library.smeltery.CastingRecipe;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.HarvestLevels;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;
import slimeknights.tconstruct.tools.TinkerMaterials;
import slimeknights.tconstruct.tools.TinkerTraits;
import slimeknights.tconstruct.tools.traits.TraitSlimey;

import static slimeknights.tconstruct.library.utils.HarvestLevels.STONE;

public class TinkersCompat {
    private static final TinkersCompat INSTANCE = new TinkersCompat();
    private static boolean registered = false;
    public static final Material ancientMetal = new Material("ancient_metal", 0XE01800);
    public static final Material tarSlime = new Material("tar_slime", 0X222222);
    public static final AbstractTrait lightningTrait = new TraitLightning();
    public static final AbstractTrait tarSlimeTrait = new TraitSlimey("tar", EntityTarSlime.class);
    public static AbstractTrait fossilModifier = new ModifierArcheology(true);
    public static AbstractTrait archeologistModifier = new ModifierArcheology(false);
    public static ModifierScarab scarabModifier = new ModifierScarab();
    static void register() {
        if (!registered) {
            registered = true;
            Revival.LOGGER.info("Started Tinkers integration for FossilsArcheology");
            MinecraftForge.EVENT_BUS.register(INSTANCE);
            init();
        } else {
            throw new RuntimeException("You can only call TinkersCompat.register() once");
        }
    }

    static void init() {
        TinkerMaterials.materials.add(ancientMetal);
        TinkerMaterials.materials.add(tarSlime);
        TinkerRegistry.integrate(ancientMetal).preInit();
        TinkerRegistry.integrate(tarSlime).preInit();
        ancientMetal.addTrait(lightningTrait);
        ancientMetal.addTrait(TinkerTraits.crude);
        ancientMetal.addItem(FAItemRegistry.BROKEN_SWORD, 1, Material.VALUE_Ingot);
        ancientMetal.addItem(FAItemRegistry.BROKEN_HELMET, 1, Material.VALUE_Ingot);
        ancientMetal.setRepresentativeItem(FAItemRegistry.BROKEN_SWORD);
        ancientMetal.setCraftable(true);
        ancientMetal.setCastable(false);
        TinkerRegistry.addMaterialStats(ancientMetal,
                new HeadMaterialStats(220, 5.00f, 4.00f, HarvestLevels.DIAMOND),
                new HandleMaterialStats(0.9F, 10),
                new ExtraMaterialStats(25));
        TinkerRegistry.addMaterialStats(ancientMetal, new BowMaterialStats(0.6f, 1.7f, 6f));
        tarSlime.addTrait(tarSlimeTrait);
        tarSlime.setCastable(true);
        tarSlime.setCraftable(false);
        tarSlime.addItem(FAItemRegistry.TARDROP, 1, Material.VALUE_Nugget);
        tarSlime.setRepresentativeItem(FAItemRegistry.TARDROP);
        tarSlime.setFluid(FAFluidRegistry.TAR_FLUID);
        TinkerRegistry.registerMelting(FAItemRegistry.TARDROP, FAFluidRegistry.TAR_FLUID, Material.VALUE_Nugget);
        TinkerRegistry.registerMelting(Items.COAL, FAFluidRegistry.TAR_FLUID, Material.VALUE_Nugget);
        TinkerRegistry.registerMelting(FAItemRegistry.TAR_FOSSIL, FAFluidRegistry.TAR_FLUID, Material.VALUE_Nugget);
        TinkerRegistry.addMaterialStats(tarSlime,
                new HeadMaterialStats(800, 3.0f, 3.00f, STONE),
                new HandleMaterialStats(0.60f, 50),
                new ExtraMaterialStats(400));
        TinkerRegistry.addMaterialStats(tarSlime, new BowMaterialStats(0.5f, 2.3f, 6));
        fossilModifier.addItem(FAItemRegistry.BIOFOSSIL);
        fossilModifier.addItem(FAItemRegistry.TAR_FOSSIL);
        fossilModifier.addItem("fossil");
        fossilModifier.addItem("fossil", 1, 1);
        fossilModifier.addRecipeMatch(new RecipeMatch.ItemCombination(1, new ItemStack(FAItemRegistry.BIOFOSSIL)));
        fossilModifier.addRecipeMatch(new RecipeMatch.ItemCombination(1, new ItemStack(FAItemRegistry.TAR_FOSSIL)));
        archeologistModifier.addItem(FAItemRegistry.RELIC_SCRAP);
        archeologistModifier.addItem("artifact");
        archeologistModifier.addItem("artifact", 1, 1);
        archeologistModifier.addRecipeMatch(new RecipeMatch.ItemCombination(1, new ItemStack(FAItemRegistry.RELIC_SCRAP)));
        scarabModifier.addItem(FAItemRegistry.SCARAB_GEM);
        scarabModifier.addItem("gemScarab");
        scarabModifier.addRecipeMatch(new RecipeMatch.ItemCombination(1, new ItemStack(FAItemRegistry.SCARAB_GEM)));
       }

    public static void post() {
        TinkerRegistry.registerTableCasting(new CastingRecipe(new ItemStack(FAItemRegistry.TAR_FOSSIL),  RecipeMatch.of("fossil"), FAFluidRegistry.TAR_FLUID, Material.VALUE_Nugget, true, false));
        TinkerRegistry.registerTableCasting(new ItemStack(FAItemRegistry.TARDROP), TinkerSmeltery.castNugget, FAFluidRegistry.TAR_FLUID, Material.VALUE_Nugget);
    }
}