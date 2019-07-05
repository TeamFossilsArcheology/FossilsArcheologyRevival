package fossilsarcheology.server.compat.thaumcraft;

import fossilsarcheology.Revival;
import fossilsarcheology.server.block.FABlockRegistry;
import fossilsarcheology.server.entity.prehistoric.MobType;
import fossilsarcheology.server.entity.prehistoric.PrehistoricEntityType;
import fossilsarcheology.server.entity.prehistoric.TimePeriod;
import fossilsarcheology.server.item.FAItemRegistry;
import fossilsarcheology.server.item.variant.DinosaurBoneType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.AspectRegistryEvent;

/**
 * Created by Joseph on 4/29/2018.
 */
public class ThaumcraftCompat {


	//We define the aspects here
	static final Aspect TIME = new Aspect("chronos", 0Xb5a642, new Aspect[]{Aspect.EXCHANGE, Aspect.MOTION}, new ResourceLocation(Revival.MODID, "textures/thaumcraft/chronos.png"), 1);
	static final Aspect RUIN = new Aspect("anteanus", 0X8A9A5B, new Aspect[]{Aspect.MAN, ThaumcraftCompat.TIME}, new ResourceLocation(Revival.MODID, "textures/thaumcraft/anteanus.png"), 1);
	static final Aspect FOSSIL = new Aspect("priscus", 0X9f8170, new Aspect[]{Aspect.BEAST, ThaumcraftCompat.TIME}, new ResourceLocation(Revival.MODID, "textures/thaumcraft/priscus.png"), 1);
	private static final ThaumcraftCompat INSTANCE = new ThaumcraftCompat();
	private static boolean registered = false;

	@Deprecated
		static void register() {
		if (!registered) {
			registered = true;
			MinecraftForge.EVENT_BUS.register(INSTANCE);
		} else {
			throw new RuntimeException("You can only call ThaumcraftCompat.register() once");
		}
	}

	@SuppressWarnings("deprecation")
	@SubscribeEvent
	public void aspectRegistrationEvent(AspectRegistryEvent evt) {
		evt.register.registerObjectTag(new ItemStack(FAItemRegistry.BIOFOSSIL), new AspectList().add(Aspect.EARTH, 5).add(Aspect.BEAST, 5).add(ThaumcraftCompat.TIME, 5).add(ThaumcraftCompat.FOSSIL, 5));
		evt.register.registerObjectTag(new ItemStack(FAItemRegistry.AMBER), new AspectList().add(Aspect.EARTH, 3).add(Aspect.CRYSTAL, 3).add(ThaumcraftCompat.TIME, 3));
		evt.register.registerObjectTag(new ItemStack(FAItemRegistry.ANCIENT_CLOCK), new AspectList().add(Aspect.METAL, 15).add(ThaumcraftCompat.TIME, 15).add(ThaumcraftCompat.RUIN, 35));
		evt.register.registerObjectTag(new ItemStack(FAItemRegistry.ANCIENT_HELMET, 1, OreDictionary.WILDCARD_VALUE), new AspectList().add(Aspect.PROTECT, 10).add(ThaumcraftCompat.TIME, 10).add(ThaumcraftCompat.RUIN, 15));
		evt.register.registerObjectTag(new ItemStack(FAItemRegistry.ANCIENT_JAVELIN, 1, OreDictionary.WILDCARD_VALUE), new AspectList().add(Aspect.AVERSION, 10).add(ThaumcraftCompat.TIME, 10).add(ThaumcraftCompat.RUIN, 15));
		evt.register.registerObjectTag(new ItemStack(FAItemRegistry.ANCIENT_KEY), new AspectList().add(Aspect.DESIRE, 10).add(Aspect.METAL, 10).add(ThaumcraftCompat.TIME, 10).add(ThaumcraftCompat.RUIN, 25));
		evt.register.registerObjectTag(new ItemStack(FAItemRegistry.ANCIENT_SWORD, 1, OreDictionary.WILDCARD_VALUE), new AspectList().add(Aspect.AVERSION, 10).add(Aspect.METAL, 10).add(ThaumcraftCompat.TIME, 10).add(ThaumcraftCompat.RUIN, 15));
		evt.register.registerObjectTag(new ItemStack(FAItemRegistry.AQUATIC_SCARAB_GEM), new AspectList().add(Aspect.WATER, 5).add(Aspect.EARTH, 5).add(Aspect.CRYSTAL, 5).add(Aspect.DESIRE, 5).add(ThaumcraftCompat.TIME, 5).add(ThaumcraftCompat.RUIN, 15));
		evt.register.registerObjectTag(new ItemStack(FAItemRegistry.DOMINICAN_AMBER), new AspectList().add(Aspect.WATER, 3).add(Aspect.EARTH, 3).add(Aspect.CRYSTAL, 3).add(ThaumcraftCompat.TIME, 3));
		evt.register.registerObjectTag(new ItemStack(FAItemRegistry.BROKEN_HELMET), new AspectList().add(Aspect.ENTROPY, 10).add(ThaumcraftCompat.TIME, 10).add(ThaumcraftCompat.RUIN, 15));
		evt.register.registerObjectTag(new ItemStack(FAItemRegistry.BROKEN_SWORD), new AspectList().add(Aspect.ENTROPY, 10).add(ThaumcraftCompat.TIME, 10).add(ThaumcraftCompat.RUIN, 15));
		evt.register.registerObjectTag(new ItemStack(FAItemRegistry.CHICKEN_ESSENCE), new AspectList().add(Aspect.CRAFT, 5).add(Aspect.BEAST, 5));
		evt.register.registerObjectTag(new ItemStack(FAItemRegistry.COOKED_CHICKEN_SOUP), new AspectList().add(Aspect.CRAFT, 10).add(Aspect.BEAST, 10).add(Aspect.WATER, 10));
		evt.register.registerObjectTag(new ItemStack(FAItemRegistry.COOKED_EGG), new AspectList().add(Aspect.DEATH, 5).add(Aspect.BEAST, 5).add(Aspect.CRAFT, 5));
		evt.register.registerObjectTag(new ItemStack(FAItemRegistry.TOOTH_DAGGER), new AspectList().add(Aspect.DEATH, 10).add(Aspect.BEAST, 10).add(Aspect.CRAFT, 10).add(Aspect.AVERSION, 10).add(ThaumcraftCompat.FOSSIL, 5));
		evt.register.registerObjectTag(new ItemStack(FAItemRegistry.SKULL_HELMET, 1, OreDictionary.WILDCARD_VALUE), new AspectList().add(Aspect.DEATH, 5).add(Aspect.BEAST, 5).add(ThaumcraftCompat.TIME, 5).add(ThaumcraftCompat.FOSSIL, 5));
		evt.register.registerObjectTag(new ItemStack(FAItemRegistry.RIBCAGE_CHESTPLATE, 1, OreDictionary.WILDCARD_VALUE), new AspectList().add(Aspect.DEATH, 5).add(Aspect.BEAST, 7).add(ThaumcraftCompat.TIME, 5).add(ThaumcraftCompat.FOSSIL, 7));
		evt.register.registerObjectTag(new ItemStack(FAItemRegistry.SHIN_LEGGINGS, 1, OreDictionary.WILDCARD_VALUE), new AspectList().add(Aspect.DEATH, 5).add(Aspect.BEAST, 7).add(ThaumcraftCompat.TIME, 5).add(ThaumcraftCompat.FOSSIL, 7));
		evt.register.registerObjectTag(new ItemStack(FAItemRegistry.FEET_BOOTS, 1, OreDictionary.WILDCARD_VALUE), new AspectList().add(Aspect.DEATH, 5).add(Aspect.BEAST, 5).add(ThaumcraftCompat.TIME, 5).add(ThaumcraftCompat.FOSSIL, 5));
		for (int i = 0; i < DinosaurBoneType.values().length; i++) {
			evt.register.registerObjectTag(new ItemStack(FAItemRegistry.FOOT, 1, i), new AspectList().add(Aspect.DEATH, 5).add(Aspect.BEAST, 5).add(ThaumcraftCompat.TIME, 5).add(ThaumcraftCompat.FOSSIL, 5));
			evt.register.registerObjectTag(new ItemStack(FAItemRegistry.LEG_BONE, 1, i), new AspectList().add(Aspect.DEATH, 5).add(Aspect.BEAST, 5).add(ThaumcraftCompat.TIME, 5).add(ThaumcraftCompat.FOSSIL, 5));
			evt.register.registerObjectTag(new ItemStack(FAItemRegistry.UNIQUE_ITEM, 1, i), new AspectList().add(Aspect.DEATH, 5).add(Aspect.BEAST, 5).add(ThaumcraftCompat.TIME, 5).add(Aspect.DESIRE, 2).add(ThaumcraftCompat.FOSSIL, 5));
			evt.register.registerObjectTag(new ItemStack(FAItemRegistry.RIBCAGE, 1, i), new AspectList().add(Aspect.DEATH, 5).add(Aspect.BEAST, 5).add(ThaumcraftCompat.TIME, 5).add(ThaumcraftCompat.FOSSIL, 5));
			evt.register.registerObjectTag(new ItemStack(FAItemRegistry.SKULL, 1, i), new AspectList().add(Aspect.DEATH, 5).add(Aspect.BEAST, 5).add(ThaumcraftCompat.TIME, 5).add(ThaumcraftCompat.FOSSIL, 5));
			evt.register.registerObjectTag(new ItemStack(FAItemRegistry.VERTEBRAE, 1, i), new AspectList().add(Aspect.DEATH, 5).add(Aspect.BEAST, 5).add(ThaumcraftCompat.TIME, 5).add(ThaumcraftCompat.FOSSIL, 5));
			evt.register.registerObjectTag(new ItemStack(FAItemRegistry.ARM_BONE, 1, i), new AspectList().add(Aspect.DEATH, 5).add(Aspect.BEAST, 5).add(ThaumcraftCompat.TIME, 5).add(ThaumcraftCompat.FOSSIL, 5));
		}
		evt.register.registerObjectTag(new ItemStack(FAItemRegistry.PALAE_SAPLING_FOSSIL), new AspectList().add(Aspect.EARTH, 5).add(Aspect.PLANT, 5).add(ThaumcraftCompat.TIME, 5).add(ThaumcraftCompat.FOSSIL, 5));
		evt.register.registerObjectTag(new ItemStack(FAItemRegistry.CALAMITES_SAPLING_FOSSIL), new AspectList().add(Aspect.EARTH, 5).add(Aspect.PLANT, 5).add(ThaumcraftCompat.TIME, 5).add(ThaumcraftCompat.FOSSIL, 5));
		evt.register.registerObjectTag(new ItemStack(FAItemRegistry.PLANT_FOSSIL), new AspectList().add(Aspect.EARTH, 5).add(Aspect.PLANT, 5).add(ThaumcraftCompat.TIME, 5).add(ThaumcraftCompat.FOSSIL, 7));
		evt.register.registerObjectTag(new ItemStack(FAItemRegistry.TAR_FOSSIL), new AspectList().add(Aspect.EARTH, 5).add(Aspect.ALCHEMY, 5).add(Aspect.BEAST, 5).add(ThaumcraftCompat.TIME, 5).add(ThaumcraftCompat.FOSSIL, 10));
		evt.register.registerObjectTag(new ItemStack(FAItemRegistry.TARDROP), new AspectList().add(Aspect.EARTH, 3).add(Aspect.WATER, 3).add(Aspect.DARKNESS, 3).add(Aspect.ALCHEMY, 3).add(Aspect.ENTROPY, 3).add(ThaumcraftCompat.FOSSIL, 4));
		//evt.register.registerObjectTag(new ItemStack(FAItemRegistry.TAR_BUCKET), new AspectList().add(Aspect.EARTH, 7).add(Aspect.WATER, 7).add(Aspect.DARKNESS, 7).add(Aspect.ALCHEMY, 7).add(Aspect.ENTROPY, 7));
		evt.register.registerObjectTag(new ItemStack(FAItemRegistry.TOY_BALL, 1, OreDictionary.WILDCARD_VALUE), new AspectList().add(Aspect.CRAFT, 8).add(Aspect.SENSES, 8));
		evt.register.registerObjectTag(new ItemStack(FAItemRegistry.POTTERY_SHARD), new AspectList().add(Aspect.EARTH, 2).add(Aspect.ENTROPY, 2).add(ThaumcraftCompat.TIME, 2).add(ThaumcraftCompat.RUIN, 3));
		evt.register.registerObjectTag(new ItemStack(FAItemRegistry.SCARAB_GEM), new AspectList().add(Aspect.CRYSTAL, 6).add(Aspect.DESIRE, 6).add(ThaumcraftCompat.TIME, 6).add(ThaumcraftCompat.RUIN, 15));
		evt.register.registerObjectTag(new ItemStack(FAItemRegistry.WHIP), new AspectList().add(Aspect.BEAST, 10).add(Aspect.DESIRE, 10).add(Aspect.AVERSION, 10));
		evt.register.registerObjectTag(new ItemStack(FAItemRegistry.ICED_MEAT), new AspectList().add(Aspect.BEAST, 5).add(Aspect.COLD, 5).add(Aspect.DEATH, 5).add(ThaumcraftCompat.FOSSIL, 5));
		evt.register.registerObjectTag(new ItemStack(FAItemRegistry.STONE_TABLET), new AspectList().add(Aspect.EARTH, 5).add(ThaumcraftCompat.RUIN, 5).add(ThaumcraftCompat.TIME, 5));
		evt.register.registerObjectTag(new ItemStack(FAItemRegistry.FOSSIL_RECORD_NANO_ANU), new AspectList().add(Aspect.SENSES, 5).add(Aspect.DESIRE, 5));
		evt.register.registerObjectTag(new ItemStack(FAItemRegistry.FOSSIL_RECORD_BONES), new AspectList().add(Aspect.SENSES, 5).add(Aspect.DESIRE, 5));
		evt.register.registerObjectTag(new ItemStack(FAItemRegistry.FOSSIL_RECORD_NANO_DISCOVERING), new AspectList().add(Aspect.SENSES, 5).add(Aspect.DESIRE, 5));
		evt.register.registerObjectTag(new ItemStack(FAItemRegistry.FOSSIL_RECORD_NANO_SCARAB), new AspectList().add(Aspect.SENSES, 5).add(Aspect.DESIRE, 5));
		evt.register.registerObjectTag(new ItemStack(FAItemRegistry.SHELL), new AspectList().add(Aspect.WATER, 5).add(Aspect.PROTECT, 5));
		evt.register.registerObjectTag(new ItemStack(FAItemRegistry.MAGIC_CONCH), new AspectList().add(Aspect.WATER, 5).add(Aspect.PROTECT, 5).add(Aspect.MAGIC, 5));
		evt.register.registerObjectTag(new ItemStack(FAItemRegistry.RELIC_SCRAP), new AspectList().add(Aspect.EARTH, 5).add(Aspect.CRAFT, 5).add(ThaumcraftCompat.TIME, 5).add(ThaumcraftCompat.RUIN, 5));
		for (int i = 0; i < 15; i++) {
			evt.register.registerObjectTag(new ItemStack(FAItemRegistry.SEED, 1, i), new AspectList().add(Aspect.PLANT, 5).add(ThaumcraftCompat.TIME, 5).add(ThaumcraftCompat.FOSSIL, 5));
			evt.register.registerObjectTag(new ItemStack(FAItemRegistry.FOSSIL_SEED, 1, i), new AspectList().add(Aspect.PLANT, 5).add(ThaumcraftCompat.TIME, 5).add(ThaumcraftCompat.FOSSIL, 5));
		}
		evt.register.registerObjectTag(new ItemStack(FAItemRegistry.FOSSIL_SEED_FERN), new AspectList().add(Aspect.PLANT, 5).add(ThaumcraftCompat.TIME, 5).add(ThaumcraftCompat.FOSSIL, 5));
		evt.register.registerObjectTag(new ItemStack(FAItemRegistry.FERN_SEED), new AspectList().add(Aspect.PLANT, 5).add(ThaumcraftCompat.TIME, 5).add(ThaumcraftCompat.FOSSIL, 5));
		evt.register.registerObjectTag(new ItemStack(FAItemRegistry.DIAMOND_JAVELIN, 1, OreDictionary.WILDCARD_VALUE), new AspectList().add(Aspect.AVERSION, 10).add(ThaumcraftCompat.TIME, 10).add(ThaumcraftCompat.RUIN, 5));
		evt.register.registerObjectTag(new ItemStack(FAItemRegistry.GOLD_JAVELIN, 1, OreDictionary.WILDCARD_VALUE), new AspectList().add(Aspect.AVERSION, 10).add(ThaumcraftCompat.TIME, 10).add(ThaumcraftCompat.RUIN, 5));
		evt.register.registerObjectTag(new ItemStack(FAItemRegistry.IRON_JAVELIN, 1, OreDictionary.WILDCARD_VALUE), new AspectList().add(Aspect.AVERSION, 10).add(ThaumcraftCompat.TIME, 10).add(ThaumcraftCompat.RUIN, 5));
		evt.register.registerObjectTag(new ItemStack(FAItemRegistry.STONE_JAVELIN, 1, OreDictionary.WILDCARD_VALUE), new AspectList().add(Aspect.AVERSION, 10).add(ThaumcraftCompat.TIME, 10).add(ThaumcraftCompat.RUIN, 5));
		evt.register.registerObjectTag(new ItemStack(FAItemRegistry.WOODEN_JAVELIN, 1, OreDictionary.WILDCARD_VALUE), new AspectList().add(Aspect.AVERSION, 10).add(ThaumcraftCompat.TIME, 10).add(ThaumcraftCompat.RUIN, 5));
		//Blocks
		evt.register.registerObjectTag(new ItemStack(FABlockRegistry.FOSSIL), new AspectList().add(Aspect.EARTH, 5).add(ThaumcraftCompat.TIME, 5).add(ThaumcraftCompat.FOSSIL, 5));
		evt.register.registerObjectTag(new ItemStack(FABlockRegistry.AMPHORA_VASE), new AspectList().add(Aspect.CRAFT, 15).add(Aspect.SENSES, 15).add(Aspect.DESIRE, 15).add(ThaumcraftCompat.TIME, 8).add(ThaumcraftCompat.RUIN, 10));
		evt.register.registerObjectTag(new ItemStack(FABlockRegistry.AMPHORA_VASE, 1, 1), new AspectList().add(Aspect.CRAFT, 15).add(Aspect.SENSES, 15).add(Aspect.DESIRE, 15).add(ThaumcraftCompat.TIME, 8).add(ThaumcraftCompat.RUIN, 10));
		evt.register.registerObjectTag(new ItemStack(FABlockRegistry.AMPHORA_VASE, 1, 2), new AspectList().add(Aspect.CRAFT, 15).add(Aspect.SENSES, 15).add(Aspect.DESIRE, 15).add(ThaumcraftCompat.TIME, 8).add(ThaumcraftCompat.RUIN, 10));
		evt.register.registerObjectTag(new ItemStack(FABlockRegistry.AMPHORA_VASE, 1, 3), new AspectList().add(Aspect.CRAFT, 15).add(Aspect.SENSES, 15).add(Aspect.DESIRE, 15).add(ThaumcraftCompat.TIME, 8).add(ThaumcraftCompat.RUIN, 10));
		evt.register.registerObjectTag(new ItemStack(FABlockRegistry.KYLIX_VASE), new AspectList().add(Aspect.CRAFT, 15).add(Aspect.SENSES, 15).add(Aspect.DESIRE, 15).add(ThaumcraftCompat.TIME, 8).add(ThaumcraftCompat.RUIN, 10));
		evt.register.registerObjectTag(new ItemStack(FABlockRegistry.KYLIX_VASE, 1, 1), new AspectList().add(Aspect.CRAFT, 15).add(Aspect.SENSES, 15).add(Aspect.DESIRE, 15).add(ThaumcraftCompat.TIME, 8).add(ThaumcraftCompat.RUIN, 10));
		evt.register.registerObjectTag(new ItemStack(FABlockRegistry.KYLIX_VASE, 1, 2), new AspectList().add(Aspect.CRAFT, 15).add(Aspect.SENSES, 15).add(Aspect.DESIRE, 15).add(ThaumcraftCompat.TIME, 8).add(ThaumcraftCompat.RUIN, 10));
		evt.register.registerObjectTag(new ItemStack(FABlockRegistry.KYLIX_VASE, 1, 3), new AspectList().add(Aspect.CRAFT, 15).add(Aspect.SENSES, 15).add(Aspect.DESIRE, 15).add(ThaumcraftCompat.TIME, 8).add(ThaumcraftCompat.RUIN, 10));
		evt.register.registerObjectTag(new ItemStack(FABlockRegistry.VOLUTE_VASE), new AspectList().add(Aspect.CRAFT, 15).add(Aspect.SENSES, 15).add(Aspect.DESIRE, 15).add(ThaumcraftCompat.TIME, 8).add(ThaumcraftCompat.RUIN, 10));
		evt.register.registerObjectTag(new ItemStack(FABlockRegistry.VOLUTE_VASE, 1, 1), new AspectList().add(Aspect.CRAFT, 15).add(Aspect.SENSES, 15).add(Aspect.DESIRE, 15).add(ThaumcraftCompat.TIME, 8).add(ThaumcraftCompat.RUIN, 10));
		evt.register.registerObjectTag(new ItemStack(FABlockRegistry.VOLUTE_VASE, 1, 2), new AspectList().add(Aspect.CRAFT, 15).add(Aspect.SENSES, 15).add(Aspect.DESIRE, 15).add(ThaumcraftCompat.TIME, 8).add(ThaumcraftCompat.RUIN, 10));
		evt.register.registerObjectTag(new ItemStack(FABlockRegistry.VOLUTE_VASE, 1, 3), new AspectList().add(Aspect.CRAFT, 15).add(Aspect.SENSES, 15).add(Aspect.DESIRE, 15).add(ThaumcraftCompat.TIME, 8).add(ThaumcraftCompat.RUIN, 10));
		evt.register.registerObjectTag(new ItemStack(FABlockRegistry.TIME_MACHINE), new AspectList().add(Aspect.CRAFT, 25).add(Aspect.MOTION, 15).add(Aspect.DESIRE, 15).add(ThaumcraftCompat.TIME, 25).add(ThaumcraftCompat.RUIN, 50));
		evt.register.registerObjectTag(new ItemStack(FABlockRegistry.ICED_STONE), new AspectList().add(Aspect.EARTH, 4).add(Aspect.COLD, 4));
		evt.register.registerObjectTag(new ItemStack(FABlockRegistry.DENSE_SAND), new AspectList().add(Aspect.EARTH, 4).add(Aspect.CRYSTAL, 2));
		evt.register.registerObjectTag(new ItemStack(FABlockRegistry.SKULL_BLOCK), new AspectList().add(Aspect.DEATH, 4).add(ThaumcraftCompat.RUIN, 2));
		evt.register.registerObjectTag(new ItemStack(FABlockRegistry.SKULL_LANTERN), new AspectList().add(Aspect.DEATH, 4).add(Aspect.LIGHT, 2).add(ThaumcraftCompat.RUIN, 2));
		evt.register.registerObjectTag(new ItemStack(FABlockRegistry.VOLCANIC_ROCK), new AspectList().add(Aspect.FIRE, 4).add(Aspect.EARTH, 4));
		evt.register.registerObjectTag(new ItemStack(FABlockRegistry.VOLCANIC_BRICK), new AspectList().add(Aspect.FIRE, 4).add(Aspect.EARTH, 4).add(Aspect.CRAFT, 2));
		evt.register.registerObjectTag(new ItemStack(FABlockRegistry.VOLCANIC_SINGLESLAB), new AspectList().add(Aspect.FIRE, 4).add(Aspect.EARTH, 4).add(Aspect.CRAFT, 2));
		evt.register.registerObjectTag(new ItemStack(FABlockRegistry.VOLCANIC_DOUBLESLAB), new AspectList().add(Aspect.FIRE, 4).add(Aspect.EARTH, 4).add(Aspect.CRAFT, 2));
		evt.register.registerObjectTag(new ItemStack(FABlockRegistry.VOLCANIC_STAIRS), new AspectList().add(Aspect.FIRE, 4).add(Aspect.EARTH, 4).add(Aspect.CRAFT, 2));
		evt.register.registerObjectTag(new ItemStack(FABlockRegistry.VOLCANIC_ASH), new AspectList().add(Aspect.FIRE, 4).add(Aspect.DARKNESS, 4));
		for(int i = 0; i < 16; i++){
			evt.register.registerObjectTag(new ItemStack(FABlockRegistry.FIGURINE, 1, i), new AspectList().add(Aspect.MAN, 10).add(ThaumcraftCompat.TIME, 10).add(ThaumcraftCompat.RUIN, 10));
		}
		evt.register.registerObjectTag(new ItemStack(FABlockRegistry.PALM_LOG), new AspectList().add(Aspect.PLANT, 10).add(ThaumcraftCompat.TIME, 10));
		evt.register.registerObjectTag(new ItemStack(FABlockRegistry.ANCIENT_GLASS), new AspectList().add(Aspect.CRYSTAL, 5).add(ThaumcraftCompat.TIME, 5).add(ThaumcraftCompat.RUIN, 3));
		evt.register.registerObjectTag(new ItemStack(FABlockRegistry.STRONG_GLASS), new AspectList().add(Aspect.CRYSTAL, 4).add(Aspect.PROTECT, 4));
		evt.register.registerObjectTag(new ItemStack(FABlockRegistry.ANCIENT_WOOD), new AspectList().add(Aspect.PLANT, 4).add(ThaumcraftCompat.TIME, 4).add(ThaumcraftCompat.RUIN, 3));
		evt.register.registerObjectTag(new ItemStack(FABlockRegistry.ANCIENT_WOOD_PLATE), new AspectList().add(Aspect.PLANT, 4).add(ThaumcraftCompat.TIME, 4).add(ThaumcraftCompat.RUIN, 3));
		evt.register.registerObjectTag(new ItemStack(FABlockRegistry.ANCIENT_WOOD_STAIRS), new AspectList().add(Aspect.PLANT, 4).add(ThaumcraftCompat.TIME, 4).add(ThaumcraftCompat.RUIN, 3));
		evt.register.registerObjectTag(new ItemStack(FABlockRegistry.ANCIENT_WOOD_DOUBLESLAB), new AspectList().add(Aspect.PLANT, 4).add(ThaumcraftCompat.TIME, 4).add(ThaumcraftCompat.RUIN, 3));
		evt.register.registerObjectTag(new ItemStack(FABlockRegistry.ANCIENT_WOOD_SINGLESLAB), new AspectList().add(Aspect.PLANT, 4).add(ThaumcraftCompat.TIME, 4).add(ThaumcraftCompat.RUIN, 3));
		evt.register.registerObjectTag(new ItemStack(FABlockRegistry.ANCIENT_WOOD_PILLAR), new AspectList().add(Aspect.PLANT, 4).add(ThaumcraftCompat.TIME, 4).add(ThaumcraftCompat.RUIN, 3));
		evt.register.registerObjectTag(new ItemStack(FABlockRegistry.ANALYZER), new AspectList().add(Aspect.MECHANISM, 25).add(Aspect.METAL, 25).add(ThaumcraftCompat.TIME, 20));
		evt.register.registerObjectTag(new ItemStack(FABlockRegistry.ANALYZER_ACTIVE), new AspectList().add(Aspect.MECHANISM, 25).add(Aspect.METAL, 25).add(ThaumcraftCompat.TIME, 20));
		evt.register.registerObjectTag(new ItemStack(FABlockRegistry.FEEDER), new AspectList().add(Aspect.MECHANISM, 25).add(Aspect.METAL, 25).add(Aspect.BEAST, 10));
		evt.register.registerObjectTag(new ItemStack(FABlockRegistry.CULTIVATE_IDLE), new AspectList().add(Aspect.MECHANISM, 25).add(Aspect.WATER, 25).add(Aspect.ALCHEMY, 25).add(Aspect.LIFE, 25).add(ThaumcraftCompat.TIME, 20));
		evt.register.registerObjectTag(new ItemStack(FABlockRegistry.CULTIVATE_ACTIVEE), new AspectList().add(Aspect.MECHANISM, 25).add(Aspect.WATER, 25).add(Aspect.ALCHEMY, 25).add(Aspect.LIFE, 25).add(ThaumcraftCompat.TIME, 20));
		evt.register.registerObjectTag(new ItemStack(FABlockRegistry.SIFTER_IDLE), new AspectList().add(Aspect.MECHANISM, 10).add(Aspect.CRAFT, 5).add(Aspect.MOTION, 5).add(Aspect.EARTH, 20));
		evt.register.registerObjectTag(new ItemStack(FABlockRegistry.SIFTER_ACTIVE), new AspectList().add(Aspect.MECHANISM, 10).add(Aspect.CRAFT, 5).add(Aspect.MOTION, 5).add(Aspect.EARTH, 20));
		evt.register.registerObjectTag(new ItemStack(FABlockRegistry.ANCIENT_STONE), new AspectList().add(Aspect.EARTH, 4).add(ThaumcraftCompat.TIME, 4).add(ThaumcraftCompat.RUIN, 3));
		evt.register.registerObjectTag(new ItemStack(FABlockRegistry.ANCIENT_STONE_BRICK), new AspectList().add(Aspect.EARTH, 4).add(ThaumcraftCompat.TIME, 4).add(ThaumcraftCompat.RUIN, 3));
		evt.register.registerObjectTag(new ItemStack(FABlockRegistry.ANCIENT_STONE_DOUBLESLAB), new AspectList().add(Aspect.EARTH, 4).add(ThaumcraftCompat.TIME, 4).add(ThaumcraftCompat.RUIN, 3));
		evt.register.registerObjectTag(new ItemStack(FABlockRegistry.ANCIENT_STONE_SINGLESLAB), new AspectList().add(Aspect.EARTH, 4).add(ThaumcraftCompat.TIME, 4).add(ThaumcraftCompat.RUIN, 3));
		evt.register.registerObjectTag(new ItemStack(FABlockRegistry.ANCIENT_STONE_STAIRS), new AspectList().add(Aspect.EARTH, 4).add(ThaumcraftCompat.TIME, 4).add(ThaumcraftCompat.RUIN, 3));
		evt.register.registerObjectTag(new ItemStack(FABlockRegistry.DILLHOFFIA_FLOWER), new AspectList().add(Aspect.PLANT, 4).add(ThaumcraftCompat.TIME, 4).add(ThaumcraftCompat.FOSSIL, 5));
		evt.register.registerObjectTag(new ItemStack(FABlockRegistry.FOOZIA_FLOWER), new AspectList().add(Aspect.PLANT, 4).add(ThaumcraftCompat.TIME, 4).add(ThaumcraftCompat.FOSSIL, 5));
		evt.register.registerObjectTag(new ItemStack(FABlockRegistry.HORSETAIL_LARGE_FLOWER), new AspectList().add(Aspect.PLANT, 4).add(ThaumcraftCompat.TIME, 4).add(ThaumcraftCompat.FOSSIL, 5));
		evt.register.registerObjectTag(new ItemStack(FABlockRegistry.HORSETAIL_SMALL_FLOWER), new AspectList().add(Aspect.PLANT, 4).add(ThaumcraftCompat.TIME, 4).add(ThaumcraftCompat.FOSSIL, 5));
		evt.register.registerObjectTag(new ItemStack(FABlockRegistry.PERMAFROST), new AspectList().add(Aspect.EARTH, 4).add(Aspect.COLD, 4).add(ThaumcraftCompat.FOSSIL, 2));
		evt.register.registerObjectTag(new ItemStack(FABlockRegistry.SARRACENIA_FLOWER), new AspectList().add(Aspect.PLANT, 4).add(ThaumcraftCompat.TIME, 4).add(ThaumcraftCompat.FOSSIL, 5));
		evt.register.registerObjectTag(new ItemStack(FABlockRegistry.FLORISSANTIA_FLOWER), new AspectList().add(Aspect.PLANT, 4).add(ThaumcraftCompat.TIME, 4).add(ThaumcraftCompat.FOSSIL, 5));
		evt.register.registerObjectTag(new ItemStack(FABlockRegistry.BENNETTITALES_LARGE_FLOWER), new AspectList().add(Aspect.PLANT, 4).add(ThaumcraftCompat.TIME, 4).add(ThaumcraftCompat.FOSSIL, 5));
		evt.register.registerObjectTag(new ItemStack(FABlockRegistry.BENNETTITALES_SMALL_FLOWER), new AspectList().add(Aspect.PLANT, 4).add(ThaumcraftCompat.TIME, 4).add(ThaumcraftCompat.FOSSIL, 5));
		evt.register.registerObjectTag(new ItemStack(FABlockRegistry.CEPHALOTAXUS_FLOWER), new AspectList().add(Aspect.PLANT, 4).add(ThaumcraftCompat.TIME, 4).add(ThaumcraftCompat.FOSSIL, 5));
		evt.register.registerObjectTag(new ItemStack(FABlockRegistry.CRATAEGUS_FLOWER), new AspectList().add(Aspect.PLANT, 4).add(ThaumcraftCompat.TIME, 4).add(ThaumcraftCompat.FOSSIL, 5));
		evt.register.registerObjectTag(new ItemStack(FABlockRegistry.EPENDRA_FLOWER), new AspectList().add(Aspect.PLANT, 4).add(ThaumcraftCompat.TIME, 4).add(ThaumcraftCompat.FOSSIL, 5));
		evt.register.registerObjectTag(new ItemStack(FABlockRegistry.LICOPODIOPHYTA_FLOWER), new AspectList().add(Aspect.PLANT, 4).add(ThaumcraftCompat.TIME, 4).add(ThaumcraftCompat.FOSSIL, 5));
		evt.register.registerObjectTag(new ItemStack(FABlockRegistry.OSMUNDA_FLOWER), new AspectList().add(Aspect.PLANT, 4).add(ThaumcraftCompat.TIME, 4).add(ThaumcraftCompat.FOSSIL, 5));
		evt.register.registerObjectTag(new ItemStack(FABlockRegistry.TEMPSKYA_FLOWER), new AspectList().add(Aspect.PLANT, 4).add(ThaumcraftCompat.TIME, 4).add(ThaumcraftCompat.FOSSIL, 5));
		evt.register.registerObjectTag(new ItemStack(FABlockRegistry.VACCINIUM_FLOWER), new AspectList().add(Aspect.PLANT, 4).add(ThaumcraftCompat.TIME, 4).add(ThaumcraftCompat.FOSSIL, 5));
		evt.register.registerObjectTag(new ItemStack(FABlockRegistry.WELWITSCHIA_FLOWER), new AspectList().add(Aspect.PLANT, 4).add(ThaumcraftCompat.TIME, 4).add(ThaumcraftCompat.FOSSIL, 5));
		evt.register.registerObjectTag(new ItemStack(FABlockRegistry.ZAMITES_FLOWER), new AspectList().add(Aspect.PLANT, 4).add(ThaumcraftCompat.TIME, 4).add(ThaumcraftCompat.FOSSIL, 5));
		evt.register.registerObjectTag(new ItemStack(FABlockRegistry.MUTANT_FLOWER), new AspectList().add(Aspect.PLANT, 4).add(Aspect.UNDEAD, 4));
		evt.register.registerObjectTag(new ItemStack(FABlockRegistry.PALM_SAPLING), new AspectList().add(Aspect.PLANT, 4).add(ThaumcraftCompat.TIME, 4).add(ThaumcraftCompat.FOSSIL, 5));
		evt.register.registerObjectTag(new ItemStack(FABlockRegistry.PALM_LEAVES), new AspectList().add(Aspect.PLANT, 3).add(ThaumcraftCompat.TIME, 2).add(ThaumcraftCompat.FOSSIL, 5));
		evt.register.registerObjectTag(new ItemStack(FABlockRegistry.SLIME_TRAIL), new AspectList().add(Aspect.ALCHEMY, 4).add(Aspect.WATER, 4).add(Aspect.UNDEAD, 4));
		evt.register.registerObjectTag(new ItemStack(FABlockRegistry.ANCIENT_CHEST), new AspectList().add(Aspect.DESIRE, 20).add(ThaumcraftCompat.RUIN, 15));
		evt.register.registerObjectTag(new ItemStack(FABlockRegistry.ANU_STATUE), new AspectList().add(Aspect.DESIRE, 20).add(ThaumcraftCompat.RUIN, 25));
		evt.register.registerObjectTag(new ItemStack(FABlockRegistry.OBSIDIAN_SPIKES), new AspectList().add(Aspect.DEATH, 5).add(Aspect.EARTH, 5).add(Aspect.FIRE, 5).add(Aspect.WATER, 5));
		evt.register.registerObjectTag(new ItemStack(FABlockRegistry.ANUBITE_STATUE), new AspectList().add(Aspect.DESIRE, 20).add(ThaumcraftCompat.RUIN, 15));
		evt.register.registerObjectTag(new ItemStack(FABlockRegistry.SARCOPHAGUS), new AspectList().add(Aspect.DESIRE, 20).add(ThaumcraftCompat.RUIN, 25));
		evt.register.registerObjectTag(new ItemStack(FABlockRegistry.ANU_PORTAL), new AspectList().add(Aspect.VOID, 10).add(ThaumcraftCompat.RUIN, 25).add(Aspect.DARKNESS, 10));
		evt.register.registerObjectTag(new ItemStack(FABlockRegistry.HOME_PORTAL), new AspectList().add(Aspect.LIFE, 10).add(ThaumcraftCompat.RUIN, 25).add(Aspect.LIGHT, 10));
		evt.register.registerObjectTag(new ItemStack(FABlockRegistry.DRUM), new AspectList().add(Aspect.MECHANISM, 5).add(Aspect.EARTH, 5).add(Aspect.PLANT, 5).add(Aspect.BEAST, 10));

		//Entities
		ThaumcraftApi.registerEntityTag("fossil.tyrannosaurus", new AspectList().add(Aspect.BEAST, 35).add(Aspect.AVERSION, 35).add(Aspect.AIR, 35).add(ThaumcraftCompat.TIME, 20).add(ThaumcraftCompat.FOSSIL, 20));
		ThaumcraftApi.registerEntityTag("fossil.triceratops", new AspectList().add(Aspect.BEAST, 35).add(Aspect.AVERSION, 35).add(Aspect.PROTECT, 35).add(ThaumcraftCompat.TIME, 20).add(ThaumcraftCompat.FOSSIL, 20));
		ThaumcraftApi.registerEntityTag("fossil.henodus", new AspectList().add(Aspect.BEAST, 15).add(Aspect.WATER, 15).add(Aspect.PROTECT, 20).add(ThaumcraftCompat.TIME, 15));
		ThaumcraftApi.registerEntityTag("fossil.dodo", new AspectList().add(Aspect.BEAST, 10).add(Aspect.AIR, 15).add(ThaumcraftCompat.TIME, 15));
		ThaumcraftApi.registerEntityTag("fossil.allosaurus", new AspectList().add(Aspect.BEAST, 30).add(Aspect.AIR, 20).add(Aspect.ENERGY, 20).add(ThaumcraftCompat.TIME, 20).add(ThaumcraftCompat.FOSSIL, 20));
		ThaumcraftApi.registerEntityTag("fossil.ankylosaurus", new AspectList().add(Aspect.BEAST, 35).add(Aspect.AVERSION, 35).add(Aspect.PROTECT, 35).add(ThaumcraftCompat.TIME, 20).add(ThaumcraftCompat.FOSSIL, 20));
		ThaumcraftApi.registerEntityTag("fossil.brachiosaurus", new AspectList().add(Aspect.BEAST, 35).add(Aspect.EARTH, 35).add(Aspect.PROTECT, 35).add(ThaumcraftCompat.TIME, 20).add(ThaumcraftCompat.FOSSIL, 20));
		ThaumcraftApi.registerEntityTag("fossil.ceratosaurus", new AspectList().add(Aspect.BEAST, 25).add(Aspect.AVERSION, 20).add(ThaumcraftCompat.TIME, 20).add(ThaumcraftCompat.FOSSIL, 20));
		ThaumcraftApi.registerEntityTag("fossil.compsognathus", new AspectList().add(Aspect.BEAST, 10).add(Aspect.AVERSION, 5).add(Aspect.DARKNESS, 5).add(ThaumcraftCompat.TIME, 20).add(ThaumcraftCompat.FOSSIL, 20));
		ThaumcraftApi.registerEntityTag("fossil.confuciusornis", new AspectList().add(Aspect.BEAST, 10).add(Aspect.FLIGHT, 5).add(Aspect.AIR, 5).add(ThaumcraftCompat.TIME, 20).add(ThaumcraftCompat.FOSSIL, 20));
		ThaumcraftApi.registerEntityTag("fossil.kelenken", new AspectList().add(Aspect.BEAST, 15).add(Aspect.FLIGHT, 10).add(Aspect.AIR, 5).add(Aspect.AVERSION, 5).add(ThaumcraftCompat.TIME, 20).add(ThaumcraftCompat.FOSSIL, 20));
		ThaumcraftApi.registerEntityTag("fossil.titanis", new AspectList().add(Aspect.BEAST, 15).add(Aspect.FLIGHT, 10).add(Aspect.AIR, 5).add(Aspect.AVERSION, 5).add(ThaumcraftCompat.TIME, 20).add(ThaumcraftCompat.FOSSIL, 20));
		ThaumcraftApi.registerEntityTag("fossil.gastornis", new AspectList().add(Aspect.BEAST, 15).add(Aspect.FLIGHT, 10).add(Aspect.AIR, 5).add(Aspect.AVERSION, 5).add(ThaumcraftCompat.TIME, 20).add(ThaumcraftCompat.FOSSIL, 20));
		ThaumcraftApi.registerEntityTag("fossil.phorusrhacos", new AspectList().add(Aspect.BEAST, 15).add(Aspect.FLIGHT, 10).add(Aspect.AIR, 5).add(Aspect.AVERSION, 5).add(ThaumcraftCompat.TIME, 20).add(ThaumcraftCompat.FOSSIL, 20));
		ThaumcraftApi.registerEntityTag("fossil.quagga", new AspectList().add(Aspect.BEAST, 20).add(Aspect.MOTION, 15).add(ThaumcraftCompat.TIME, 15));
		ThaumcraftApi.registerEntityTag("fossil.smilodon", new AspectList().add(Aspect.BEAST, 20).add(Aspect.COLD, 15).add(Aspect.AVERSION, 15).add(ThaumcraftCompat.TIME, 15));
		ThaumcraftApi.registerEntityTag("fossil.mammoth", new AspectList().add(Aspect.BEAST, 20).add(Aspect.COLD, 15).add(Aspect.PROTECT, 15).add(ThaumcraftCompat.TIME, 15));
		ThaumcraftApi.registerEntityTag("fossil.coelacanth", new AspectList().add(Aspect.BEAST, 5).add(Aspect.WATER, 5).add(Aspect.DARKNESS, 5).add(ThaumcraftCompat.TIME, 5));
		ThaumcraftApi.registerEntityTag("fossil.alligator_gar", new AspectList().add(Aspect.BEAST, 5).add(Aspect.WATER, 5).add(Aspect.AVERSION, 5).add(ThaumcraftCompat.TIME, 5));
		ThaumcraftApi.registerEntityTag("fossil.sturgeon", new AspectList().add(Aspect.BEAST, 5).add(Aspect.WATER, 5).add(Aspect.DESIRE, 5).add(ThaumcraftCompat.TIME, 5));
		ThaumcraftApi.registerEntityTag("fossil.nautilus", new AspectList().add(Aspect.BEAST, 5).add(Aspect.WATER, 5).add(Aspect.PROTECT, 5).add(ThaumcraftCompat.TIME, 5));
		ThaumcraftApi.registerEntityTag("fossil.velociraptor", new AspectList().add(Aspect.BEAST, 13).add(Aspect.AIR, 13).add(Aspect.MOTION, 13).add(ThaumcraftCompat.TIME, 13));
		ThaumcraftApi.registerEntityTag("fossil.dilophosaurus", new AspectList().add(Aspect.BEAST, 13).add(Aspect.AIR, 13).add(Aspect.MOTION, 13).add(ThaumcraftCompat.TIME, 13));
		ThaumcraftApi.registerEntityTag("fossil.elasmotherium", new AspectList().add(Aspect.BEAST, 20).add(Aspect.COLD, 15).add(Aspect.PROTECT, 15).add(ThaumcraftCompat.TIME, 15));
		ThaumcraftApi.registerEntityTag("fossil.dryosaurus", new AspectList().add(Aspect.BEAST, 10).add(Aspect.AIR, 10).add(Aspect.MOTION, 10).add(ThaumcraftCompat.TIME, 10));
		ThaumcraftApi.registerEntityTag("fossil.gallimimus", new AspectList().add(Aspect.BEAST, 10).add(Aspect.AIR, 10).add(Aspect.MOTION, 15).add(ThaumcraftCompat.TIME, 10));
		ThaumcraftApi.registerEntityTag("fossil.ichthyosaurus", new AspectList().add(Aspect.BEAST, 15).add(Aspect.WATER, 15).add(Aspect.SENSES, 15).add(ThaumcraftCompat.TIME, 15));
		ThaumcraftApi.registerEntityTag("fossil.liopleurodon", new AspectList().add(Aspect.BEAST, 15).add(Aspect.WATER, 15).add(Aspect.AVERSION, 15).add(ThaumcraftCompat.TIME, 15));
		ThaumcraftApi.registerEntityTag("fossil.mosasaurus", new AspectList().add(Aspect.BEAST, 15).add(Aspect.WATER, 15).add(Aspect.AVERSION, 15).add(ThaumcraftCompat.TIME, 15));
		ThaumcraftApi.registerEntityTag("fossil.pachycephalosaurus", new AspectList().add(Aspect.BEAST, 20).add(Aspect.AVERSION, 20).add(Aspect.PROTECT, 20).add(ThaumcraftCompat.TIME, 20).add(ThaumcraftCompat.FOSSIL, 20));
		ThaumcraftApi.registerEntityTag("fossil.pteranodon", new AspectList().add(Aspect.BEAST, 15).add(Aspect.AIR, 15).add(Aspect.FLIGHT, 15).add(ThaumcraftCompat.TIME, 15));
		ThaumcraftApi.registerEntityTag("fossil.spinosaurus", new AspectList().add(Aspect.BEAST, 35).add(Aspect.AVERSION, 35).add(Aspect.WATER, 35).add(ThaumcraftCompat.TIME, 20).add(ThaumcraftCompat.FOSSIL, 20));
		ThaumcraftApi.registerEntityTag("fossil.parasaurolophus", new AspectList().add(Aspect.BEAST, 15).add(Aspect.EARTH, 15).add(Aspect.SENSES, 15).add(ThaumcraftCompat.TIME, 15));
		ThaumcraftApi.registerEntityTag("fossil.stegosaurus", new AspectList().add(Aspect.BEAST, 35).add(Aspect.AVERSION, 35).add(Aspect.PROTECT, 35).add(ThaumcraftCompat.TIME, 20).add(ThaumcraftCompat.FOSSIL, 20));
		ThaumcraftApi.registerEntityTag("fossil.therizinosaurus", new AspectList().add(Aspect.BEAST, 35).add(Aspect.AVERSION, 35).add(Aspect.AIR, 35).add(ThaumcraftCompat.TIME, 20).add(ThaumcraftCompat.FOSSIL, 20));
		ThaumcraftApi.registerEntityTag("fossil.sarcosuchus", new AspectList().add(Aspect.BEAST, 35).add(Aspect.AVERSION, 35).add(Aspect.WATER, 35).add(ThaumcraftCompat.TIME, 20).add(ThaumcraftCompat.FOSSIL, 20));
		ThaumcraftApi.registerEntityTag("fossil.failuresaurus", new AspectList().add(Aspect.BEAST, 20).add(Aspect.UNDEAD, 20).add(Aspect.WATER, 20).add(Aspect.ALCHEMY, 20));
		ThaumcraftApi.registerEntityTag("fossil.dinoegg", new AspectList().add(Aspect.LIFE, 4).add(Aspect.PROTECT, 4));
		ThaumcraftApi.registerEntityTag("fossil.birdegg", new AspectList().add(Aspect.LIFE, 4).add(Aspect.PROTECT, 4));
		ThaumcraftApi.registerEntityTag("fossil.anu", new AspectList().add(Aspect.SOUL, 50).add(Aspect.DESIRE, 50).add(Aspect.UNDEAD, 50).add(Aspect.FIRE, 50).add(Aspect.FLIGHT, 50).add(Aspect.DARKNESS, 50));
		ThaumcraftApi.registerEntityTag("fossil.tarslime", new AspectList().add(Aspect.LIFE, 10).add(Aspect.ALCHEMY, 10).add(Aspect.WATER, 10).add(Aspect.DARKNESS, 10));
		ThaumcraftApi.registerEntityTag("fossil.anubite", new AspectList().add(Aspect.SOUL, 30).add(Aspect.DESIRE, 30).add(Aspect.UNDEAD, 30).add(Aspect.FIRE, 30).add(Aspect.DARKNESS, 30));
		ThaumcraftApi.registerEntityTag("fossil.sentrypigman", new AspectList().add(Aspect.SOUL, 30).add(Aspect.DESIRE, 30).add(Aspect.UNDEAD, 30).add(Aspect.FIRE, 30).add(Aspect.DARKNESS, 30));
		ThaumcraftApi.registerEntityTag("fossil.friendlypigman", new AspectList().add(Aspect.SOUL, 30).add(Aspect.DESIRE, 30).add(Aspect.UNDEAD, 30).add(Aspect.FIRE, 30).add(Aspect.DARKNESS, 30));

		ThaumcraftApi.registerEntityTag("fossil.tikaalik", new AspectList().add(Aspect.BEAST, 25).add(Aspect.EARTH, 25).add(Aspect.WATER, 25).add(Aspect.EXCHANGE, 15).add(ThaumcraftCompat.TIME, 20).add(ThaumcraftCompat.FOSSIL, 20));
		ThaumcraftApi.registerEntityTag("fossil.platybelodon", new AspectList().add(Aspect.BEAST, 35).add(Aspect.WATER, 35).add(Aspect.TOOL, 35).add(ThaumcraftCompat.TIME, 20).add(ThaumcraftCompat.FOSSIL, 20));
		
		ThaumcraftApi.registerEntityTag("fossil.diplocaulus", new AspectList().add(Aspect.BEAST, 25).add(Aspect.WATER, 25).add(ThaumcraftCompat.TIME, 20).add(ThaumcraftCompat.FOSSIL, 20));
		ThaumcraftApi.registerEntityTag("fossil.crassigyrinus", new AspectList().add(Aspect.BEAST, 25).add(Aspect.WATER, 25).add(ThaumcraftCompat.TIME, 20).add(ThaumcraftCompat.FOSSIL, 20));

		ThaumcraftApi.registerEntityTag("fossil.megalania", new AspectList().add(Aspect.BEAST, 35).add(Aspect.AVERSION, 35).add(Aspect.ALCHEMY, 35).add(ThaumcraftCompat.TIME, 20).add(ThaumcraftCompat.FOSSIL, 20));
		ThaumcraftApi.registerEntityTag("fossil.meganeura", new AspectList().add(Aspect.BEAST, 35).add(Aspect.AVERSION, 35).add(Aspect.WATER, 35).add(Aspect.FLIGHT, 35).add(ThaumcraftCompat.TIME, 20).add(ThaumcraftCompat.FOSSIL, 20));
		ThaumcraftApi.registerEntityTag("fossil.megaloceros", new AspectList().add(Aspect.BEAST, 35).add(Aspect.AVERSION, 35).add(Aspect.EARTH, 35).add(ThaumcraftCompat.TIME, 20).add(ThaumcraftCompat.FOSSIL, 20));
		ThaumcraftApi.registerEntityTag("fossil.megalograptus", new AspectList().add(Aspect.BEAST, 35).add(Aspect.AVERSION, 35).add(Aspect.WATER, 35).add(Aspect.PROTECT, 35).add(ThaumcraftCompat.TIME, 20).add(ThaumcraftCompat.FOSSIL, 20));
		ThaumcraftApi.registerEntityTag("fossil.megalodon", new AspectList().add(Aspect.BEAST, 35).add(Aspect.AVERSION, 35).add(Aspect.WATER, 35).add(ThaumcraftCompat.TIME, 20).add(ThaumcraftCompat.FOSSIL, 20));

		//Enum items
		for (PrehistoricEntityType type : PrehistoricEntityType.values()) {
			if (type.timePeriod != TimePeriod.CURRENT) {
				if (type.mobType == MobType.FISH) {
					evt.register.registerObjectTag(new ItemStack(type.eggItem), new AspectList().add(Aspect.BEAST, 5).add(Aspect.LIFE, 5).add(Aspect.PROTECT, 2).add(Aspect.WATER, 2));
					evt.register.registerObjectTag(new ItemStack(type.fishItem), new AspectList().add(Aspect.BEAST, 15).add(Aspect.LIFE, 5).add(Aspect.WATER, 10));
				}
				if (type.mobType == MobType.DINOSAUR || type.mobType == MobType.DINOSAUR_AQUATIC) {
					evt.register.registerObjectTag(new ItemStack(type.eggItem), new AspectList().add(Aspect.BEAST, 10).add(Aspect.PROTECT, 5));
				}
				if (type.mobType != MobType.FISH) {
					evt.register.registerObjectTag(new ItemStack(type.foodItem), new AspectList().add(Aspect.BEAST, 5).add(Aspect.DEATH, 5));
				}
				if (type != PrehistoricEntityType.NAUTILUS) {
					evt.register.registerObjectTag(new ItemStack(type.cookedFoodItem), new AspectList().add(Aspect.BEAST, 5).add(Aspect.FIRE, 2));
				}
			}
			if (type.mobType == MobType.BIRD || type.mobType == MobType.CHICKEN) {
				if (type.mobType == MobType.BIRD) {
					evt.register.registerObjectTag(new ItemStack(type.birdEggItem), new AspectList().add(Aspect.BEAST, 5).add(Aspect.PROTECT, 2).add(Aspect.AIR, 2));
				}
				evt.register.registerObjectTag(new ItemStack(type.bestBirdEggItem), new AspectList().add(Aspect.BEAST, 10).add(Aspect.PROTECT, 2).add(Aspect.AIR, 2));
			}
			if (type.mobType == MobType.MAMMAL || type.mobType == MobType.VANILLA) {
				evt.register.registerObjectTag(new ItemStack(type.embryoItem), new AspectList().add(Aspect.BEAST, 5).add(Aspect.DESIRE, 5).add(Aspect.LIFE, 3).add(Aspect.CRYSTAL, 3));
			}
			evt.register.registerObjectTag(new ItemStack(type.dnaItem), new AspectList().add(Aspect.BEAST, 15).add(Aspect.LIFE, 5).add(Aspect.DESIRE, 10));
		}

		//Other Thaumcraft stuff
		ThaumcraftApi.registerSeed(FABlockRegistry.FERNS, new ItemStack(FAItemRegistry.FERN_SEED));
		ThaumcraftApi.registerSeed(FABlockRegistry.DILLHOFFIA_FLOWER, new ItemStack(FAItemRegistry.SEED, 1, 0));
		ThaumcraftApi.registerSeed(FABlockRegistry.SARRACENIA_FLOWER, new ItemStack(FAItemRegistry.SEED, 1, 1));
		ThaumcraftApi.registerSeed(FABlockRegistry.CEPHALOTAXUS_FLOWER, new ItemStack(FAItemRegistry.SEED, 1, 2));
		ThaumcraftApi.registerSeed(FABlockRegistry.LICOPODIOPHYTA_FLOWER, new ItemStack(FAItemRegistry.SEED, 1, 3));
		ThaumcraftApi.registerSeed(FABlockRegistry.FOOZIA_FLOWER, new ItemStack(FAItemRegistry.SEED, 1, 4));
		ThaumcraftApi.registerSeed(FABlockRegistry.ZAMITES_FLOWER, new ItemStack(FAItemRegistry.SEED, 1, 5));
		ThaumcraftApi.registerSeed(FABlockRegistry.BENNETTITALES_SMALL_FLOWER, new ItemStack(FAItemRegistry.SEED, 1, 6));
		ThaumcraftApi.registerSeed(FABlockRegistry.WELWITSCHIA_FLOWER, new ItemStack(FAItemRegistry.SEED, 1, 7));
		ThaumcraftApi.registerSeed(FABlockRegistry.HORSETAIL_SMALL_FLOWER, new ItemStack(FAItemRegistry.SEED, 1, 8));
		ThaumcraftApi.registerSeed(FABlockRegistry.TEMPSKYA_FLOWER, new ItemStack(FAItemRegistry.SEED, 1, 9));
		ThaumcraftApi.registerSeed(FABlockRegistry.VACCINIUM_FLOWER, new ItemStack(FAItemRegistry.SEED, 1, 10));
		ThaumcraftApi.registerSeed(FABlockRegistry.OSMUNDA_FLOWER, new ItemStack(FAItemRegistry.SEED, 1, 11));
		ThaumcraftApi.registerSeed(FABlockRegistry.CRATAEGUS_FLOWER, new ItemStack(FAItemRegistry.SEED, 1, 12));
		ThaumcraftApi.registerSeed(FABlockRegistry.FLORISSANTIA_FLOWER, new ItemStack(FAItemRegistry.SEED, 1, 13));
		ThaumcraftApi.registerSeed(FABlockRegistry.EPENDRA_FLOWER, new ItemStack(FAItemRegistry.SEED, 1, 14));
	}
}
