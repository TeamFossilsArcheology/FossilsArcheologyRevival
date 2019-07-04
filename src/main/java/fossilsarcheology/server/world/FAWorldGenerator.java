package fossilsarcheology.server.world;

import fossilsarcheology.Revival;
import fossilsarcheology.server.block.FABlockRegistry;
import fossilsarcheology.server.entity.FAVillagerRegistry;
import fossilsarcheology.server.entity.prehistoric.EntityAlligatorGar;
import fossilsarcheology.server.entity.prehistoric.EntityCoelacanth;
import fossilsarcheology.server.entity.prehistoric.EntityNautilus;
import fossilsarcheology.server.entity.prehistoric.EntitySturgeon;
import fossilsarcheology.server.structure.StructureUtils;
import fossilsarcheology.server.world.gen.HellBoatWorldGen;
import fossilsarcheology.server.world.gen.WorldGenPalm;
import fossilsarcheology.server.world.gen.WorldGenPermafrost;
import fossilsarcheology.server.world.gen.WorldGenTarPit;
import net.minecraft.block.material.Material;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class FAWorldGenerator implements IWorldGenerator {

	private static final ResourceLocation TAR_SITE = new ResourceLocation(Revival.MODID, "tar_site");
	private static final ResourceLocation FOSSIL_SITE = new ResourceLocation(Revival.MODID, "fossil_site");
	private static final ResourceLocation FOSSIL_SITE_TENT = new ResourceLocation(Revival.MODID, "fossil_site_tent");
	private static final ResourceLocation MOAI = new ResourceLocation(Revival.MODID, "moai");
	private static final ResourceLocation MOAI_WITH_HAT = new ResourceLocation(Revival.MODID, "moai_with_hat");
	private static final ResourceLocation AZTEC_TEMPLE = new ResourceLocation(Revival.MODID, "aztec_temple");
	private static final ResourceLocation AZTEC_WEAPONS_SHOP = new ResourceLocation(Revival.MODID, "aztec_weapons_shop");
	private static final ResourceLocation EGYPTIAN_ACADEMY = new ResourceLocation(Revival.MODID, "egyptian_academy");
	public static final ResourceLocation ANU_CASTLE = new ResourceLocation(Revival.MODID, "anu_castle");
	private static final ResourceLocation TREASURE_ROOM = new ResourceLocation(Revival.MODID, "treasure_room");
	public static ResourceLocation AZTEC_TEMPLE_CHEST;
	private static final ResourceLocation AZTEC_WEAPONS_CHEST = LootTableList.register(new ResourceLocation(Revival.MODID, "aztec_weapons_shop"));
	public static ResourceLocation EGYPTIAN_ACADEMY_CHEST;
	private static final ResourceLocation ANU_CASTLE_CHEST = LootTableList.register(new ResourceLocation(Revival.MODID, "anu_castle"));
	private static final BlockPos ANU_CASTLE_POS = new BlockPos(-70, 63, -70);
	private static final BlockPos TREASURE_ROOM_POS = new BlockPos(0, 56, 0);
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		boolean prevLogCascadingWorldGen = net.minecraftforge.common.ForgeModContainer.logCascadingWorldGeneration;
		if(!Revival.CONFIG_OPTIONS.logCascadingWorldGen) {
			net.minecraftforge.common.ForgeModContainer.logCascadingWorldGeneration = false;
		}
		for (int dimensionID : Revival.CONFIG_OPTIONS.oreGenerationDimensions) {
			if (world.provider.getDimension() == dimensionID) {
				if (Revival.CONFIG_OPTIONS.generateFossils) {
					boolean mesa = BiomeDictionary.hasType(world.getBiome(new BlockPos(chunkX * 16 + 8, 0, chunkX * 16 + 8)), BiomeDictionary.Type.MESA);
					for (int i = 0; i < (mesa ? 60 : 38); i++) {
						int Xcoord = chunkX * 16 + random.nextInt(16);
						int Ycoord = random.nextInt(100);
						int Zcoord = chunkZ * 16 + random.nextInt(16);
						(new WorldGenMinable(FABlockRegistry.FOSSIL.getDefaultState(), 5 + random.nextInt(6))).generate(world, random, new BlockPos(Xcoord, Ycoord, Zcoord));
					}
				}
				if (Revival.CONFIG_OPTIONS.generatePermafrost) {
					for (int i = 0; i < (BiomeDictionary.hasType(world.getBiome(new BlockPos(chunkX * 16 + 8, 0, chunkZ * 16 + 8)), BiomeDictionary.Type.SNOWY) ? 9 : 4); i++) {
						int Xcoord = chunkX * 16 + random.nextInt(16);
						int Ycoord = random.nextInt(30);
						int Zcoord = chunkZ * 16 + random.nextInt(16);
						(new WorldGenPermafrost(FABlockRegistry.PERMAFROST.getDefaultState(), 6 + random.nextInt(4))).generate(world, random, new BlockPos(Xcoord, Ycoord, Zcoord));
					}
				}
			}
		}

		if (world.provider.getDimension() == 0) {
			int x = chunkX * 16 + random.nextInt(16);
			int z = chunkZ * 16 + random.nextInt(16);

			if (BiomeDictionary.hasType(world.getBiome(new BlockPos(x, 0, z)), BiomeDictionary.Type.SWAMP) && random.nextInt(15) == 0) {
				if (Revival.CONFIG_OPTIONS.generateTarSites) {
					for (int k = 0; k < 10; k++) {
						int y = random.nextInt(128);
						(new WorldGenTarPit(FABlockRegistry.TAR)).generate(world, random, new BlockPos(x, y, z));
					}
				}
				if (Revival.CONFIG_OPTIONS.generatePalaeoraphe) {
					for (int count = 0; count < 2; count++) {
						BlockPos pos = world.getHeight(new BlockPos(x, 0, z));
						new WorldGenPalm().generate(world, random, pos);
					}
				}
			}
			if (Revival.CONFIG_OPTIONS.generateVolcanicRock) {
				for (int i = 0; i < 10; i++) {
					int y = random.nextInt(16);
					(new WorldGenPermafrost(FABlockRegistry.VOLCANIC_ROCK.getDefaultState(), 6 + random.nextInt(3))).generate(world, random, new BlockPos(x, y, z));
				}
			}
		}
		if (Revival.CONFIG_OPTIONS.generateHellShips && world.provider.getDimension() == -1) {
			if (random.nextInt(Math.max(Revival.CONFIG_OPTIONS.generateHellShipRarity, 1)) == 0) {
				int x = chunkX * 16 + random.nextInt(16);
				int z = chunkZ * 16 + random.nextInt(16);
				if (world.getBlockState(new BlockPos(x, 31, z)).getMaterial() == Material.LAVA) {
					new HellBoatWorldGen().generate(world, random, new BlockPos(x, 32, z));
				}
			}
		}
		int x = (chunkX * 16) + 8;
		int z = (chunkZ * 16) + 8;
		BlockPos height = StructureUtils.getGround(x, z, world);
		Biome biome = world.getBiome(height);
		if (Revival.CONFIG_OPTIONS.generateTarSites && random.nextInt(Math.max(Revival.CONFIG_OPTIONS.generateTarSiteRarity, 1)) == 0 && world.provider.hasSkyLight() && !world.provider.isNether() && biome.topBlock.getBlock() == Blocks.GRASS && StructureUtils.canGenOnBlock(world, height)) {
			if(StructureUtils.generateStructureAtWithRandomRotation(TAR_SITE, world, height.down(3), random, true, false)){
				BlockPos tentPos = random.nextBoolean() ? height.add(10  + random.nextInt(6), 10 + random.nextInt(6) , 10 + random.nextInt(6)) : height.add(-10 - random.nextInt(6), -10 - random.nextInt(6), -10 - random.nextInt(6));
				StructureUtils.generateStructureAtWithRandomRotation(FOSSIL_SITE_TENT, world, StructureUtils.getGround(tentPos, world), random, false, false);
				EntityVillager villager = new EntityVillager(world);
				villager.setProfession(FAVillagerRegistry.ARCHEOLOGIST_PROFESSION);
				villager.setLocationAndAngles(tentPos.getX() - 0.5D, tentPos.getY() - 0.5D, tentPos.getZ() - 0.5D, 0, 0);
				world.spawnEntity(villager);
			}
		}

		if (Revival.CONFIG_OPTIONS.generateFossilSites && random.nextInt(Math.max(Revival.CONFIG_OPTIONS.generateFossilSiteRarity, 1)) == 0 && world.provider.hasSkyLight() && !world.provider.isNether() && biome.topBlock.getBlock() == Blocks.GRASS && StructureUtils.canGenOnBlock(world, height)) {
			if(StructureUtils.generateStructureAtWithRandomRotation(FOSSIL_SITE, world, height.down(3), random, true, false)){
				BlockPos tentPos = random.nextBoolean() ? height.add(10  + random.nextInt(6), 10 + random.nextInt(6) , 10 + random.nextInt(6)) : height.add(-10 - random.nextInt(6), -10 - random.nextInt(6), -10 - random.nextInt(6));
				StructureUtils.generateStructureAtWithRandomRotation(FOSSIL_SITE_TENT, world, StructureUtils.getGround(tentPos, world), random, false, false);
				EntityVillager villager = new EntityVillager(world);
				villager.setProfession(FAVillagerRegistry.ARCHEOLOGIST_PROFESSION);
				villager.setLocationAndAngles(tentPos.getX() - 0.5D, tentPos.getY() - 0.5D, tentPos.getZ() - 0.5D, 0, 0);
				world.spawnEntity(villager);
			}
		}
		if (Revival.CONFIG_OPTIONS.generateMoai && random.nextInt(Math.max(Revival.CONFIG_OPTIONS.generateMoaiRarity, 1)) == 0 && world.provider.hasSkyLight() && !world.provider.isNether() && BiomeDictionary.hasType(biome, BiomeDictionary.Type.BEACH) && !BiomeDictionary.hasType(biome, BiomeDictionary.Type.COLD) && !BiomeDictionary.hasType(biome, BiomeDictionary.Type.SNOWY) && world.getBlockState(height.down()).isOpaqueCube()  && StructureUtils.canGenOnBlock(world, height)) {
			StructureUtils.generateStructureAtWithRandomRotation(random.nextInt(3) == 0 ? MOAI_WITH_HAT : MOAI, world, height.down(random.nextInt(5)), random, false, true);
		}
		if (Revival.CONFIG_OPTIONS.generateAztecWeaponShops && random.nextInt(Math.max(Revival.CONFIG_OPTIONS.generateWeaponShopRarity, 1)) == 0 && world.provider.hasSkyLight() && !world.provider.isNether() && BiomeDictionary.hasType(biome, BiomeDictionary.Type.JUNGLE) && StructureUtils.canGenOnBlock(world, height)) {
			StructureUtils.generateStructureAtWithRandomRotationWithLoot(AZTEC_WEAPONS_SHOP, AZTEC_WEAPONS_CHEST, world, height, random, true, false);
		}
		if (Revival.CONFIG_OPTIONS.generateTemple && random.nextInt(Math.max(Revival.CONFIG_OPTIONS.generateTempleRarity, 1)) == 0 && world.provider.hasSkyLight() && !world.provider.isNether() && BiomeDictionary.hasType(biome, BiomeDictionary.Type.JUNGLE) && StructureUtils.canGenOnBlock(world, height)) {
			StructureUtils.generateStructureAtWithRandomRotationWithLoot(AZTEC_TEMPLE, AZTEC_TEMPLE_CHEST, world, height, random, true, false);
		}
		if (Revival.CONFIG_OPTIONS.generateAcademy && random.nextInt(Math.max(Revival.CONFIG_OPTIONS.generateAcademyRarity, 1)) == 0 && world.provider.hasSkyLight() && !world.provider.isNether() && BiomeDictionary.hasType(biome, BiomeDictionary.Type.SANDY) && BiomeDictionary.hasType(biome, BiomeDictionary.Type.DRY) && BiomeDictionary.hasType(biome, BiomeDictionary.Type.HOT) && StructureUtils.canGenOnBlock(world, height)) {
			StructureUtils.generateStructureAtWithRandomRotationWithLoot(EGYPTIAN_ACADEMY, EGYPTIAN_ACADEMY_CHEST, world, height.down(), random, true, false);
		}
		if (world.getChunk(chunkX, chunkZ) == world.getChunk(ANU_CASTLE_POS) && world.provider.getDimension() == Revival.CONFIG_OPTIONS.dimensionIDDarknessLair) {
			int counter = 0;
			counter++;
			if (counter == 1) {
				StructureUtils.generateStructureAtWithRotationWithLoot(ANU_CASTLE, ANU_CASTLE_CHEST, world, new BlockPos(-140, 63, -140), random, Rotation.NONE, false, false);
				int structurebase = 140;
				int base = 14;
				for (int y = 50; y < 63; y++) {
					base--;
					for (int baseX = -70 - base; baseX < structurebase - 70 + base; baseX++) {
						for (int baseZ = -70 - base; baseZ < structurebase - 70 + base; baseZ++) {
							world.setBlockState(new BlockPos(baseX, y, baseZ), Blocks.NETHERRACK.getDefaultState());
						}
					}
				}
				world.setBlockState(new BlockPos(0, 73, 0), FABlockRegistry.SARCOPHAGUS.getDefaultState());
			}
		}
		if (world.getChunk(chunkX, chunkZ) == world.getChunk(TREASURE_ROOM_POS) && world.provider.getDimension() == Revival.CONFIG_OPTIONS.dimensionIDTreasure) {
			int counter = 0;
			counter++;
			if (counter == 1) {
				StructureUtils.generateStructureAt(TREASURE_ROOM, world, TREASURE_ROOM_POS, false);
			}
		}

		if(Revival.CONFIG_OPTIONS.spawnNautilus && BiomeDictionary.hasType(biome, BiomeDictionary.Type.OCEAN) &&  random.nextInt(Math.max(Revival.CONFIG_OPTIONS.nautilusSpawnRate, 1)) == 0){
			for(int i = 0; i < 1 + random.nextInt(3); i++){
				int ex = chunkX * 16 + random.nextInt(16);
				int zee = chunkZ * 16 + random.nextInt(16);
				int why = 30 + random.nextInt(Math.max(world.getSeaLevel() - 30, 1));
				if(world.getBlockState(new BlockPos(ex, why, zee)).getMaterial() == Material.WATER){
					EntityNautilus fish = new EntityNautilus(world);
					fish.setPosition(ex + 0.5F, why + 0.5F, zee + 0.5F);
					world.spawnEntity(fish);
				}
			}
		}
		if(Revival.CONFIG_OPTIONS.spawnCoelacanth && BiomeDictionary.hasType(biome, BiomeDictionary.Type.OCEAN) &&  random.nextInt(Math.max(Revival.CONFIG_OPTIONS.coelacanthSpawnRate, 1)) == 0){
			for(int i = 0; i < 1 + random.nextInt(3); i++){
				int ex = chunkX * 16 + random.nextInt(16);
				int zee = chunkZ * 16 + random.nextInt(16);
				int why = 10 + random.nextInt(25);
				if(world.getBlockState(new BlockPos(ex, why, zee)).getMaterial() == Material.WATER){
					EntityCoelacanth fish = new EntityCoelacanth(world);
					fish.setPosition(ex + 0.5F, why + 0.5F, zee + 0.5F);
					world.spawnEntity(fish);
				}
			}
		}
		if(Revival.CONFIG_OPTIONS.spawnAlligatorGar && BiomeDictionary.hasType(biome, BiomeDictionary.Type.SWAMP) &&  random.nextInt(Math.max(Revival.CONFIG_OPTIONS.alligatorGarSpawnRate, 1)) == 0){
			for(int i = 0; i < 1 + random.nextInt(3); i++){
				int ex = chunkX * 16 + random.nextInt(16);
				int zee = chunkZ * 16 + random.nextInt(16);
				int why = 40 + random.nextInt(Math.max(world.getSeaLevel() - 40, 1));
				if(world.getBlockState(new BlockPos(ex, why, zee)).getMaterial() == Material.WATER){
					EntityAlligatorGar fish = new EntityAlligatorGar(world);
					fish.setPosition(ex + 0.5F, why + 0.5F, zee + 0.5F);
					world.spawnEntity(fish);
				}
			}
		}
		if(Revival.CONFIG_OPTIONS.spawnSturgeon && BiomeDictionary.hasType(biome, BiomeDictionary.Type.RIVER) &&  random.nextInt(Math.max(Revival.CONFIG_OPTIONS.sturgeonSpawnRate, 1)) == 0){
			for(int i = 0; i < 1 + random.nextInt(3); i++){
				int ex = chunkX * 16 + random.nextInt(16);
				int zee = chunkZ * 16 + random.nextInt(16);
				int why = 40 + random.nextInt(Math.max(world.getSeaLevel() - 40, 1));
				if(world.getBlockState(new BlockPos(ex, why, zee)).getMaterial() == Material.WATER){
					EntitySturgeon fish = new EntitySturgeon(world);
					fish.setPosition(ex + 0.5F, why + 0.5F, zee + 0.5F);
					world.spawnEntity(fish);
				}
			}
		}
		if(!Revival.CONFIG_OPTIONS.logCascadingWorldGen) {
			net.minecraftforge.common.ForgeModContainer.logCascadingWorldGeneration = prevLogCascadingWorldGen;
		}
	}
}
