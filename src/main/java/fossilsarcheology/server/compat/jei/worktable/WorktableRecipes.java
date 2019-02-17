package fossilsarcheology.server.compat.jei.worktable;

import fossilsarcheology.server.block.FABlockRegistry;
import fossilsarcheology.server.item.FAItemRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WorktableRecipes {
    private static final Random RANDOM = new Random();

    public static List<RecipeWorktable> getRecipes() {
        List<RecipeWorktable> list = new ArrayList<>();
        list.add(new RecipeWorktable(new ItemStack(FAItemRegistry.BROKEN_SWORD), new ItemStack(FAItemRegistry.ANCIENT_SWORD), new ItemStack(FAItemRegistry.RELIC_SCRAP)));
        list.add(new RecipeWorktable(new ItemStack(FAItemRegistry.BROKEN_HELMET), new ItemStack(FAItemRegistry.ANCIENT_HELMET), new ItemStack(FAItemRegistry.RELIC_SCRAP)));
        list.add(new RecipeWorktable(createDamagedStack(FAItemRegistry.ANCIENT_SWORD), new ItemStack(FAItemRegistry.ANCIENT_SWORD), new ItemStack(FAItemRegistry.RELIC_SCRAP)));
        list.add(new RecipeWorktable(createDamagedStack(FAItemRegistry.ANCIENT_HELMET), new ItemStack(FAItemRegistry.ANCIENT_HELMET), new ItemStack(FAItemRegistry.RELIC_SCRAP)));
        list.add(new RecipeWorktable(createDamagedStack(FAItemRegistry.SCARAB_AXE), new ItemStack(FAItemRegistry.SCARAB_AXE), new ItemStack(FAItemRegistry.RELIC_SCRAP)));
        list.add(new RecipeWorktable(createDamagedStack(FAItemRegistry.SCARAB_PICKAXE), new ItemStack(FAItemRegistry.SCARAB_PICKAXE), new ItemStack(FAItemRegistry.RELIC_SCRAP)));
        list.add(new RecipeWorktable(createDamagedStack(FAItemRegistry.SCARAB_SWORD), new ItemStack(FAItemRegistry.SCARAB_SWORD), new ItemStack(FAItemRegistry.RELIC_SCRAP)));
        list.add(new RecipeWorktable(createDamagedStack(FAItemRegistry.SCARAB_HOE), new ItemStack(FAItemRegistry.SCARAB_HOE), new ItemStack(FAItemRegistry.RELIC_SCRAP)));
        list.add(new RecipeWorktable(createDamagedStack(FAItemRegistry.SCARAB_SHOVEL), new ItemStack(FAItemRegistry.SCARAB_SHOVEL), new ItemStack(FAItemRegistry.RELIC_SCRAP)));
        list.add(new RecipeWorktable(createDamagedStack(FAItemRegistry.WOODEN_JAVELIN), new ItemStack(FAItemRegistry.WOODEN_JAVELIN), new ItemStack(FAItemRegistry.RELIC_SCRAP)));
        list.add(new RecipeWorktable(createDamagedStack(FAItemRegistry.STONE_JAVELIN), new ItemStack(FAItemRegistry.STONE_JAVELIN), new ItemStack(FAItemRegistry.RELIC_SCRAP)));
        list.add(new RecipeWorktable(createDamagedStack(FAItemRegistry.IRON_JAVELIN), new ItemStack(FAItemRegistry.IRON_JAVELIN), new ItemStack(FAItemRegistry.RELIC_SCRAP)));
        list.add(new RecipeWorktable(createDamagedStack(FAItemRegistry.GOLD_JAVELIN), new ItemStack(FAItemRegistry.GOLD_JAVELIN), new ItemStack(FAItemRegistry.RELIC_SCRAP)));
        list.add(new RecipeWorktable(createDamagedStack(FAItemRegistry.DIAMOND_JAVELIN), new ItemStack(FAItemRegistry.DIAMOND_JAVELIN), new ItemStack(FAItemRegistry.RELIC_SCRAP)));
        list.add(new RecipeWorktable(createDamagedStack(FAItemRegistry.ANCIENT_JAVELIN), new ItemStack(FAItemRegistry.ANCIENT_JAVELIN), new ItemStack(FAItemRegistry.RELIC_SCRAP)));
        list.add(new RecipeWorktable(new ItemStack(FABlockRegistry.KYLIX_VASE, 1, 0), new ItemStack(FABlockRegistry.KYLIX_VASE, 1, 1), new ItemStack(FAItemRegistry.POTTERY_SHARD)));
        list.add(new RecipeWorktable(new ItemStack(FABlockRegistry.VOLUTE_VASE, 1, 0), new ItemStack(FABlockRegistry.VOLUTE_VASE, 1, 1), new ItemStack(FAItemRegistry.POTTERY_SHARD)));
        list.add(new RecipeWorktable(new ItemStack(FABlockRegistry.AMPHORA_VASE, 1, 0), new ItemStack(FABlockRegistry.AMPHORA_VASE, 1, 1), new ItemStack(FAItemRegistry.POTTERY_SHARD)));
        for(int i = 5; i <= 14; i++){
            list.add(new RecipeWorktable(new ItemStack(FABlockRegistry.FIGURINE, 1, i), new ItemStack(FABlockRegistry.FIGURINE, 1, i - 5), new ItemStack(FAItemRegistry.POTTERY_SHARD)));
        }
        return list;
    }

    private static ItemStack createDamagedStack(Item item){
        ItemStack stack = new ItemStack(item);
        stack.attemptDamageItem((int)(stack.getMaxDamage() * 0.75F), RANDOM, null);
        return stack;
    }
}
