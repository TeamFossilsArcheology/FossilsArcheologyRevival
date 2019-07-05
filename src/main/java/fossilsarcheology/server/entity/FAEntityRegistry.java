package fossilsarcheology.server.entity;

import fossilsarcheology.Revival;
import fossilsarcheology.server.entity.monster.*;
import fossilsarcheology.server.entity.prehistoric.MobType;
import fossilsarcheology.server.entity.prehistoric.PrehistoricEntityType;
import fossilsarcheology.server.entity.projectile.AncientJavelinEntity;
import fossilsarcheology.server.entity.projectile.EntityBirdEgg;
import fossilsarcheology.server.entity.projectile.JavelinEntity;
import fossilsarcheology.server.entity.utility.*;
import net.ilexiconn.llibrary.server.entity.EntityPropertiesHandler;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class FAEntityRegistry {
	public static void register() {
		for (int id = 0; id < PrehistoricEntityType.values().length; id++) {
			PrehistoricEntityType type = PrehistoricEntityType.values()[id];
			if (type.mobType != MobType.CHICKEN && type.mobType != MobType.VANILLA) {
				registerSpawnable(type.getEntity(), "fossil." + type.name().toLowerCase(), id + 200, type.primaryEggColor, type.secondaryEggColor);
			}
		}
		registerEntity(JavelinEntity.class, "fossil.javelin", 0);
		registerEntity(AncientJavelinEntity.class, "fossil.ancient_javelin", 1);
		registerEntity(StoneTabletEntity.class, "fossil.stone_tablet", 2);
		registerSpawnable(EntityFailuresaurus.class, "fossil.failuresaurus", 5, 0x61ffbd, 0xf4eee6);
		registerEntity(EntityDinosaurEgg.class, "fossil.dinoegg", 8);
		registerEntity(EntityBirdEgg.class, "fossil.birdegg", 9);

		registerEntity(EntityFriendlyPigZombie.class, "fossil.friendlypigzombie", 12);
		registerEntity(EntityAnuStatue.class, "fossil.anueffect", 13);
		registerEntity(EntityAnuDead.class, "fossil.anudead", 15);
		registerSpawnable(EntityTarSlime.class, "fossil.tarslime", 16, 0X222222, 0x0B0B0B);
		registerSpawnable(EntitySentryPigman.class, "fossil.sentrypigman", 18, 15373203, 0XD0A750);

		registerSpawnable(EntityAnubite.class, "fossil.anubite", 39, 0X2E1E14, 0X601200);
		registerSpawnable(EntityAnu.class, "fossil.anu", 17, 0X0F0F0F, 0XF72D00);
		registerEntity(EntityDebugTest.class, "fossil.debugtest", 40);

		registerEntity(EntityToyBall.class, "fossil.toyball", 103);
		registerEntity(EntityToyTetheredLog.class, "fossil.toytetheredlog", 104);
		registerEntity(EntityToyScratchingPost.class, "fossil.toyscratchingpost", 105);
	}

	public static void registerEntity(Class<? extends Entity> entityClass, String name, int id) {
		EntityRegistry.registerModEntity(new ResourceLocation(Revival.MODID, "" + name), entityClass, name, id, Revival.INSTANCE, 64, 1, true);
	}

	public static void registerSpawnable(Class<? extends Entity> entityClass, String name, int id, int mainColor, int subColor) {
		EntityRegistry.registerModEntity(new ResourceLocation(Revival.MODID, "" + name), entityClass, name, id, Revival.INSTANCE, 64, 1, true, mainColor, subColor);
	}
}
