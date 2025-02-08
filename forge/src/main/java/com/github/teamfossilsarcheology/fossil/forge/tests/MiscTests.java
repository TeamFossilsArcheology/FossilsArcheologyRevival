package com.github.teamfossilsarcheology.fossil.forge.tests;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.client.DinopediaBioLoader;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestAssertException;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;
import net.minecraftforge.gametest.PrefixGameTestTemplate;

@PrefixGameTestTemplate(value = false)
@GameTestHolder(FossilMod.MOD_ID)
public class MiscTests {

    /**
     * Tests that hoppers can extract outputs from the bottom
     */
    @GameTest(batch = "misc", template = "empty", timeoutTicks = 2)
    public static void ensureAllDinosHaveBio(GameTestHelper helper) {
        for (PrehistoricEntityInfo info : PrehistoricEntityInfo.values()) {
            if (!DinopediaBioLoader.INSTANCE.hasFallback(info.resourceName)) {
                throw new GameTestAssertException(info.resourceName + " should have an english bio");
            }
        }
        helper.succeed();
    }
}
