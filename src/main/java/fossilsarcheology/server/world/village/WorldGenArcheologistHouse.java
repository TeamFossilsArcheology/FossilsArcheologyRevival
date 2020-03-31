package fossilsarcheology.server.world.village;

import fossilsarcheology.Revival;
import fossilsarcheology.server.entity.FAVillagerRegistry;
import fossilsarcheology.server.structure.FABlockProcessorVillage;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.minecraft.world.storage.loot.LootTableList;

import java.util.Random;

public class WorldGenArcheologistHouse extends WorldGenerator {

    private static final ResourceLocation HOUSE = new ResourceLocation(Revival.MODID, "archeologist_villager_house");
    private static final ResourceLocation CHEST = LootTableList.register(new ResourceLocation(Revival.MODID, "archeologist_house"));
    private VillageComponentArcheologistHouse component;
    private Rotation rotation;
    private EnumFacing facing;

    public WorldGenArcheologistHouse(VillageComponentArcheologistHouse component, EnumFacing facing) {
        this.component = component;
        this.facing = facing;
        switch (facing) {
            case SOUTH:
                rotation = Rotation.CLOCKWISE_180;
                break;
            case EAST:
                rotation = Rotation.CLOCKWISE_90;
                break;
            case WEST:
                rotation = Rotation.COUNTERCLOCKWISE_90;
                break;
            default:
                rotation = Rotation.NONE;
                break;
        }
    }

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position) {
        if (worldIn == null) {
            return false;
        }
        MinecraftServer server = worldIn.getMinecraftServer();
        TemplateManager templateManager = worldIn.getSaveHandler().getStructureTemplateManager();
        PlacementSettings settings = new PlacementSettings().setRotation(rotation).setMirror(Mirror.NONE);
        Template template = templateManager.getTemplate(server, HOUSE);
        Biome biome = worldIn.getBiome(position);
        int xSize = template.getSize().getX() / 2;
        int zSize = template.getSize().getZ() / 2;

        template.addBlocksToWorld(worldIn, position.up(3).offset(EnumFacing.NORTH, xSize).offset(EnumFacing.SOUTH, zSize), new FABlockProcessorVillage(position.up(3), settings, CHEST, biome, facing.getOpposite()), settings, 2);
        if (component.villagerCount < 1) {
            EntityVillager villager = new EntityVillager(worldIn);
            villager.setProfession(FAVillagerRegistry.ARCHEOLOGIST_PROFESSION);
            BlockPos center = position.add(template.getSize().getX() / 2, 4, template.getSize().getZ() / 2);
            BlockPos villagerPos = center.up(4);
            villager.setLocationAndAngles(villagerPos.getX() + 0.5D, villagerPos.getY() + 0.5D, villagerPos.getZ() + 0.5D, 0, 0);
            worldIn.spawnEntity(villager);
            component.villagerCount++;
        }
        return true;
    }
}
