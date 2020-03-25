package fossilsarcheology.server.compat.tinkers;

import com.google.common.collect.ImmutableList;
import fossilsarcheology.server.block.FABlockRegistry;
import fossilsarcheology.server.item.FAItemRegistry;
import fossilsarcheology.server.item.variant.DinosaurBoneType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.mantle.util.RecipeMatch;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.modifiers.IToolMod;
import slimeknights.tconstruct.library.modifiers.ModifierAspect;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import slimeknights.tconstruct.library.modifiers.ModifierTrait;
import slimeknights.tconstruct.library.tools.DualToolHarvestUtils;
import slimeknights.tconstruct.library.tools.ToolCore;
import slimeknights.tconstruct.library.traits.ITrait;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.Tags;
import slimeknights.tconstruct.library.utils.TinkerUtil;
import slimeknights.tconstruct.library.utils.ToolHelper;

import java.util.List;
import java.util.Random;

public class ModifierArcheology extends ModifierTrait  {
    private boolean fossils;

    public ModifierArcheology(boolean fossils) {
        super(fossils ? "paleontologist" : "archeologist", 0XB6A074, 3, 50);
        this.fossils = fossils;
        addAspects(new ModifierAspect.MultiAspect(this, 3, 50, 1), ModifierAspect.harvestOnly);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void blockDropEvent(BlockEvent.HarvestDropsEvent event) {
        if(event.getHarvester() == null) {
            return;
        }
        ItemStack tool = DualToolHarvestUtils.getItemstackToUse(event.getHarvester(), event.getState());

        if(tool != null && tool.getItem() instanceof ToolCore && !ToolHelper.isBroken(tool)) {
            NBTTagList list = TagUtil.getTagListSafe(TagUtil.getTagSafe(tool), Tags.TOOL_MODIFIERS, TagUtil.TAG_TYPE_COMPOUND);
            for(int i = 0; i < list.tagCount(); i++) {
                String identity = list.getStringTagAt(i);
                String identifier = identity.substring(13, identity.indexOf("\","));
                ITrait trait = TinkerRegistry.getTrait(identifier);
                if(trait != null) {
                    trait.blockHarvestDrops(tool, event);
                }
            }
        }
    }

    private int getLevel(ItemStack tool) {
        return ModifierNBT.readInteger(TinkerUtil.getModifierTag(tool, getModifierIdentifier())).level;
    }

    @Override
    public boolean canApplyTogether(IToolMod toolmod) {
        String id = toolmod.getIdentifier();
        return !id.equals(fossils ? TinkersCompat.archeologistModifier.getIdentifier() : TinkersCompat.fossilModifier.getIdentifier());
    }

    @Override
    public boolean hasTexturePerMaterial() {
        return false;
    }

    @Override
    public void blockHarvestDrops(ItemStack tool, BlockEvent.HarvestDropsEvent event) {
        if (event.getState().getBlock() == FABlockRegistry.FOSSIL) {
            event.getDrops().clear();
            for (int i = 0; i < Math.max(Math.ceil(getLevel(tool) / 2D), 1); i++) {
                if (fossils) {
                    event.getDrops().add(getRandomFossilDrop(tool, random));
                }else{
                    event.getDrops().add(getRandomRelicDrop(tool, random));
                }
            }

        }
    }

    private ItemStack getRandomFossilDrop(ItemStack tool, Random rand){
        int i = rand.nextInt(100);
        if (i < 2) {
            int dropRandom = rand.nextInt(DinosaurBoneType.values().length);
            return new ItemStack(FAItemRegistry.LEG_BONE, 1, dropRandom);
        } else if (i < 4) {
            int dropRandom = rand.nextInt(DinosaurBoneType.values().length);
            return new ItemStack(FAItemRegistry.SKULL, 1, dropRandom);
        } else if (i < 6) {
            int dropRandom = rand.nextInt(DinosaurBoneType.values().length);
            return new ItemStack(FAItemRegistry.UNIQUE_ITEM, 1, dropRandom);
        } else if (i < 8) {
            int dropRandom = rand.nextInt(DinosaurBoneType.values().length);
            return new ItemStack(FAItemRegistry.FOOT, 1, dropRandom);
        } else if (i < 10) {
            int dropRandom = rand.nextInt(DinosaurBoneType.values().length);
            return new ItemStack(FAItemRegistry.VERTEBRAE, 1, dropRandom);
        } else if (i < 12) {
            int dropRandom = rand.nextInt(DinosaurBoneType.values().length);
            return new ItemStack(FAItemRegistry.ARM_BONE, 1, dropRandom);
        } else if (i < 14) {
            int dropRandom = rand.nextInt(DinosaurBoneType.values().length);
            return new ItemStack(FAItemRegistry.RIBCAGE, 1, dropRandom);
        } else if (i < 30) {
            return new ItemStack(FABlockRegistry.SKULL_BLOCK);
        } else if (i < 60) {
            return new ItemStack(FAItemRegistry.PLANT_FOSSIL);
        }else{
            return new ItemStack(FAItemRegistry.BIOFOSSIL);

        }
    }

    private ItemStack getRandomRelicDrop(ItemStack tool, Random rand){
        int i = rand.nextInt(1000);
        if (i < getLevel(tool) * 5) {
            return new ItemStack(FAItemRegistry.SCARAB_GEM);
        } else if (i < 900) {
            return new ItemStack(FAItemRegistry.RELIC_SCRAP);
        } else if (i < 950) {
            return  new ItemStack(FAItemRegistry.BROKEN_SWORD);
        }else{
            return  new ItemStack(FAItemRegistry.BROKEN_HELMET);
        }
    }

    @Override
    public void applyEffect(NBTTagCompound rootCompound, NBTTagCompound modifierTag) {

    }

    @Override
    public int getColor() {
        return color;
    }

    @Override
    public List<List<ItemStack>> getItems() {
        ImmutableList.Builder<List<ItemStack>> builder = ImmutableList.builder();

        for(RecipeMatch rm : items) {
            List<ItemStack> in = rm.getInputs();
            if(!in.isEmpty()) {
                builder.add(in);
            }
        }

        return builder.build();
    }
}
