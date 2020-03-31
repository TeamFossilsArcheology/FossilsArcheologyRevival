package fossilsarcheology.server.api;

import net.minecraft.util.ResourceLocation;

public interface SubtypeRenderedItem {
    int[] getUsedSubtypes();

    String getResource(ResourceLocation name, int metadata);
}
