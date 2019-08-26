package fossilsarcheology.server.entity;

import fossilsarcheology.server.item.FAItemRegistry;
import fossilsarcheology.server.world.village.VillageArcheologistHouseCreator;
import fossilsarcheology.server.world.village.VillageComponentArcheologistHouse;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraftforge.fml.common.registry.VillagerRegistry;

public class FAVillagerRegistry {
    public static final VillagerRegistry.VillagerProfession ARCHEOLOGIST_PROFESSION = new VillagerRegistry.VillagerProfession("fossil:archeologist", "fossil:textures/model/archaeologist.png", "fossil:textures/model/archaeologist_zombie.png");

    public static void register(){
        VillagerRegistry.VillagerCareer career = new VillagerRegistry.VillagerCareer(ARCHEOLOGIST_PROFESSION, "archeologist");
        career.addTrade(1, new EntityVillager.ListItemForEmeralds(FAItemRegistry.BIOFOSSIL, new EntityVillager.PriceInfo(1, 7)));
        career.addTrade(1, new EntityVillager.ListItemForEmeralds(FAItemRegistry.RELIC_SCRAP, new EntityVillager.PriceInfo(1, 6)));
        career.addTrade(1, new EntityVillager.EmeraldForItems(FAItemRegistry.RELIC_SCRAP, new EntityVillager.PriceInfo(1, 3)));
        career.addTrade(2, new EntityVillager.EmeraldForItems(FAItemRegistry.POTTERY_SHARD, new EntityVillager.PriceInfo(1, 3)));
        career.addTrade(2, new EntityVillager.ListItemForEmeralds(FAItemRegistry.TAR_FOSSIL, new EntityVillager.PriceInfo(1, 3)));
        career.addTrade(3, new EntityVillager.ListItemForEmeralds(FAItemRegistry.ICED_MEAT, new EntityVillager.PriceInfo(1, 6)));
        career.addTrade(4, new EntityVillager.ListItemForEmeralds(FAItemRegistry.TARDROP, new EntityVillager.PriceInfo(1, 2)));
        career.addTrade(4, new EntityVillager.ListItemForEmeralds(FAItemRegistry.CHICKEN_ESSENCE, new EntityVillager.PriceInfo(1, 2)));
        career.addTrade(5, new EntityVillager.ItemAndEmeraldToItem(FAItemRegistry.BROKEN_HELMET, new EntityVillager.PriceInfo(1, 1), FAItemRegistry.ANCIENT_HELMET, new EntityVillager.PriceInfo(1, 1)));
        career.addTrade(5, new EntityVillager.ItemAndEmeraldToItem(FAItemRegistry.BROKEN_SWORD, new EntityVillager.PriceInfo(1, 1), FAItemRegistry.ANCIENT_SWORD, new EntityVillager.PriceInfo(1, 1)));
        MapGenStructureIO.registerStructureComponent(VillageComponentArcheologistHouse.class, "archeologist_house");
        VillagerRegistry.instance().registerVillageCreationHandler(new VillageArcheologistHouseCreator());
    }

}
