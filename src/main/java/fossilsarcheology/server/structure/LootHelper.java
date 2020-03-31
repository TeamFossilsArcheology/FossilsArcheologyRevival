package fossilsarcheology.server.structure;

import fossilsarcheology.Revival;
import fossilsarcheology.server.world.FAWorldGenerator;
import net.minecraft.util.ResourceLocation;

import java.util.Random;

public class LootHelper {

    private static final ResourceLocation AZTEC_TEMPLE_CHEST_1 = new ResourceLocation(Revival.MODID, "aztec_temple/aztec_temple_0");
    private static final ResourceLocation AZTEC_TEMPLE_CHEST_2 = new ResourceLocation(Revival.MODID, "aztec_temple/aztec_temple_1");
    private static final ResourceLocation AZTEC_TEMPLE_CHEST_3 = new ResourceLocation(Revival.MODID, "aztec_temple/aztec_temple_2");
    private static final ResourceLocation AZTEC_TEMPLE_CHEST_4 = new ResourceLocation(Revival.MODID, "aztec_temple/aztec_temple_3");
    private static final ResourceLocation AZTEC_TEMPLE_CHEST_5 = new ResourceLocation(Revival.MODID, "aztec_temple/aztec_temple_4");
    private static final ResourceLocation AZTEC_TEMPLE_CHEST_6 = new ResourceLocation(Revival.MODID, "aztec_temple/aztec_temple_5");
    private static final ResourceLocation AZTEC_TEMPLE_CHEST_7 = new ResourceLocation(Revival.MODID, "aztec_temple/aztec_temple_6");
    private static final ResourceLocation AZTEC_TEMPLE_CHEST_8 = new ResourceLocation(Revival.MODID, "aztec_temple/aztec_temple_7");
    private static final ResourceLocation AZTEC_TEMPLE_CHEST_9 = new ResourceLocation(Revival.MODID, "aztec_temple/aztec_temple_8");
    private static final ResourceLocation AZTEC_TEMPLE_CHEST_10 = new ResourceLocation(Revival.MODID, "aztec_temple/aztec_temple_9");
    private static final ResourceLocation AZTEC_TEMPLE_CHEST_11 = new ResourceLocation(Revival.MODID, "aztec_temple/aztec_temple_10");
    private static final ResourceLocation AZTEC_TEMPLE_CHEST_12 = new ResourceLocation(Revival.MODID, "aztec_temple/aztec_temple_11");
    private static final ResourceLocation AZTEC_TEMPLE_CHEST_13 = new ResourceLocation(Revival.MODID, "aztec_temple/aztec_temple_12");
    private static final ResourceLocation AZTEC_TEMPLE_CHEST_14 = new ResourceLocation(Revival.MODID, "aztec_temple/aztec_temple_13");
    private static final ResourceLocation AZTEC_TEMPLE_CHEST_15 = new ResourceLocation(Revival.MODID, "aztec_temple/aztec_temple_14");
    private static final ResourceLocation AZTEC_TEMPLE_CHEST_16 = new ResourceLocation(Revival.MODID, "aztec_temple/aztec_temple_15");

    private static final ResourceLocation EGYPTIAN_ACADEMY_CHEST_1 = new ResourceLocation(Revival.MODID, "egyptian_academy/gyptian_academy_0");
    private static final ResourceLocation EGYPTIAN_ACADEMY_CHEST_2 = new ResourceLocation(Revival.MODID, "egyptian_academy/gyptian_academy_1");
    private static final ResourceLocation EGYPTIAN_ACADEMY_CHEST_3 = new ResourceLocation(Revival.MODID, "egyptian_academy/gyptian_academy_2");
    private static final ResourceLocation EGYPTIAN_ACADEMY_CHEST_4 = new ResourceLocation(Revival.MODID, "egyptian_academy/gyptian_academy_3");
    private static final ResourceLocation EGYPTIAN_ACADEMY_CHEST_5 = new ResourceLocation(Revival.MODID, "egyptian_academy/gyptian_academy_4");
    private static final ResourceLocation EGYPTIAN_ACADEMY_CHEST_6 = new ResourceLocation(Revival.MODID, "egyptian_academy/gyptian_academy_5");
    private static final ResourceLocation EGYPTIAN_ACADEMY_CHEST_7 = new ResourceLocation(Revival.MODID, "egyptian_academy/gyptian_academy_6");
    private static final ResourceLocation EGYPTIAN_ACADEMY_CHEST_8 = new ResourceLocation(Revival.MODID, "egyptian_academy/gyptian_academy_7");
    private static final ResourceLocation EGYPTIAN_ACADEMY_CHEST_9 = new ResourceLocation(Revival.MODID, "egyptian_academy/gyptian_academy_8");
    private static final ResourceLocation EGYPTIAN_ACADEMY_CHEST_10 = new ResourceLocation(Revival.MODID, "egyptian_academy/gyptian_academy_9");
    private static final ResourceLocation EGYPTIAN_ACADEMY_CHEST_11 = new ResourceLocation(Revival.MODID, "egyptian_academy/gyptian_academy_10");
    private static final ResourceLocation EGYPTIAN_ACADEMY_CHEST_12 = new ResourceLocation(Revival.MODID, "egyptian_academy/gyptian_academy_11");
    private static final ResourceLocation EGYPTIAN_ACADEMY_CHEST_13 = new ResourceLocation(Revival.MODID, "egyptian_academy/gyptian_academy_12");
    private static final ResourceLocation EGYPTIAN_ACADEMY_CHEST_14 = new ResourceLocation(Revival.MODID, "egyptian_academy/gyptian_academy_13");
    private static final ResourceLocation EGYPTIAN_ACADEMY_CHEST_15 = new ResourceLocation(Revival.MODID, "egyptian_academy/gyptian_academy_14");
    private static final ResourceLocation EGYPTIAN_ACADEMY_CHEST_16 = new ResourceLocation(Revival.MODID, "egyptian_academy/gyptian_academy_15");

    public static ResourceLocation getLoot(ResourceLocation lootTable, Random random) {
        if (lootTable == FAWorldGenerator.AZTEC_TEMPLE_CHEST) {
            return getAztecLoot(random);
        }
        if (lootTable == FAWorldGenerator.EGYPTIAN_ACADEMY_CHEST) {
            return getEgyptianLoot(random);
        }
        return lootTable;
    }

    private static ResourceLocation getEgyptianLoot(Random random) {
        switch (random.nextInt(16)) {
            case 0:
                return EGYPTIAN_ACADEMY_CHEST_1;
            case 1:
                return EGYPTIAN_ACADEMY_CHEST_2;
            case 2:
                return EGYPTIAN_ACADEMY_CHEST_3;
            case 3:
                return EGYPTIAN_ACADEMY_CHEST_4;
            case 4:
                return EGYPTIAN_ACADEMY_CHEST_5;
            case 5:
                return EGYPTIAN_ACADEMY_CHEST_6;
            case 6:
                return EGYPTIAN_ACADEMY_CHEST_7;
            case 7:
                return EGYPTIAN_ACADEMY_CHEST_8;
            case 8:
                return EGYPTIAN_ACADEMY_CHEST_9;
            case 9:
                return EGYPTIAN_ACADEMY_CHEST_10;
            case 10:
                return EGYPTIAN_ACADEMY_CHEST_11;
            case 11:
                return EGYPTIAN_ACADEMY_CHEST_12;
            case 12:
                return EGYPTIAN_ACADEMY_CHEST_13;
            case 13:
                return EGYPTIAN_ACADEMY_CHEST_14;
            case 14:
                return EGYPTIAN_ACADEMY_CHEST_15;
            default:
                return EGYPTIAN_ACADEMY_CHEST_16;
        }
    }

    private static ResourceLocation getAztecLoot(Random random) {
        switch (random.nextInt(16)) {
            case 0:
                return AZTEC_TEMPLE_CHEST_1;
            case 1:
                return AZTEC_TEMPLE_CHEST_2;
            case 2:
                return AZTEC_TEMPLE_CHEST_3;
            case 3:
                return AZTEC_TEMPLE_CHEST_4;
            case 4:
                return AZTEC_TEMPLE_CHEST_5;
            case 5:
                return AZTEC_TEMPLE_CHEST_6;
            case 6:
                return AZTEC_TEMPLE_CHEST_7;
            case 7:
                return AZTEC_TEMPLE_CHEST_8;
            case 8:
                return AZTEC_TEMPLE_CHEST_9;
            case 9:
                return AZTEC_TEMPLE_CHEST_10;
            case 10:
                return AZTEC_TEMPLE_CHEST_11;
            case 11:
                return AZTEC_TEMPLE_CHEST_12;
            case 12:
                return AZTEC_TEMPLE_CHEST_13;
            case 13:
                return AZTEC_TEMPLE_CHEST_14;
            case 14:
                return AZTEC_TEMPLE_CHEST_15;
            default:
                return AZTEC_TEMPLE_CHEST_16;
        }
    }
}
