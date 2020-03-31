package fossilsarcheology.server.block;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static fossilsarcheology.server.block.FourTallFlowerBlock.LAYER;

public enum EnumFossilPlant {
    DILLHOFFIA(Type.SINGLE, FABlockRegistry.DILLHOFFIA_FLOWER),
    SARRACINA(Type.SINGLE, FABlockRegistry.SARRACENIA_FLOWER),
    CEPHALOTAXUS(Type.SINGLE, FABlockRegistry.CEPHALOTAXUS_FLOWER),
    LICOPODIOPHYTA(Type.SINGLE, FABlockRegistry.LICOPODIOPHYTA_FLOWER),
    FOOZIA(Type.DOUBLE, FABlockRegistry.FOOZIA_FLOWER),
    ZAMITES(Type.SINGLE, FABlockRegistry.ZAMITES_FLOWER),
    BENNETTITALES(Type.DOUBLE_BONEMEAL, FABlockRegistry.BENNETTITALES_SMALL_FLOWER, FABlockRegistry.BENNETTITALES_LARGE_FLOWER),
    WELWITSCHIA(Type.SINGLE, FABlockRegistry.WELWITSCHIA_FLOWER),
    HORSETAIL(Type.DOUBLE_BONEMEAL, FABlockRegistry.HORSETAIL_SMALL_FLOWER, FABlockRegistry.HORSETAIL_LARGE_FLOWER),
    TEMPSKYA(Type.FOUR, FABlockRegistry.TEMPSKYA_FLOWER),
    VACCINIUM(Type.SINGLE, FABlockRegistry.VACCINIUM_FLOWER),
    OSMUNDA(Type.SINGLE, FABlockRegistry.OSMUNDA_FLOWER),
    CRATAEGUS(Type.DOUBLE, FABlockRegistry.CRATAEGUS_FLOWER),
    FLORISSANTIA(Type.SINGLE, FABlockRegistry.FLORISSANTIA_FLOWER),
    EPHEDRA(Type.SINGLE, FABlockRegistry.EPENDRA_FLOWER),
    DUISBERGIA(Type.DOUBLE, FABlockRegistry.DUISBERGIA_FLOWER),
    DIPTERIS(Type.DOUBLE, FABlockRegistry.DIPTERIS_FLOWER),
    DICTYOPHYLLUM(Type.SINGLE, FABlockRegistry.DICTYOPHYLLUM_FLOWER),
    SAGENOPTERIS(Type.SINGLE, FABlockRegistry.SAGENOPTERIS_FLOWER),
    CYATHEA(Type.FOUR, FABlockRegistry.CYATHEA_FLOWER);

    private EnumFossilPlant.Type type;
    private Block[] placeBlock;

    EnumFossilPlant(EnumFossilPlant.Type type, Block... placeBlock) {
        this.type = type;
        this.placeBlock = placeBlock;
    }

    public void onPlace(World world, BlockPos pos) {
        if (type == Type.DOUBLE) {
            world.setBlockState(pos, placeBlock[0].getDefaultState().withProperty(TallFlowerBlock.HALF, TallFlowerBlock.EnumBlockHalf.LOWER));
            world.setBlockState(pos.up(), placeBlock[0].getDefaultState().withProperty(TallFlowerBlock.HALF, TallFlowerBlock.EnumBlockHalf.UPPER));
        } else if (type == Type.FOUR) {
            world.setBlockState(pos, placeBlock[0].getDefaultState().withProperty(LAYER, 0), 2);
            world.setBlockState(pos.up(), placeBlock[0].getDefaultState().withProperty(LAYER, 1), 2);
            world.setBlockState(pos.up(2), placeBlock[0].getDefaultState().withProperty(LAYER, 2), 2);
            world.setBlockState(pos.up(3), placeBlock[0].getDefaultState().withProperty(LAYER, 3), 2);
        } else {
            world.setBlockState(pos, placeBlock[0].getDefaultState());
        }
    }

    public int getClearance() {
        switch (type) {
            case SINGLE:
                return 1;
            case DOUBLE:
                return 2;
            case DOUBLE_BONEMEAL:
                return 1;
            case FOUR:
                return 4;
        }
        return 1;
    }

    public IBlockState defaultState() {
        return placeBlock[0].getDefaultState();
    }

    enum Type {
        SINGLE, DOUBLE, DOUBLE_BONEMEAL, FOUR
    }
}
