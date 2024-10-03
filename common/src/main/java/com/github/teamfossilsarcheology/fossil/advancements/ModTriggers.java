package com.github.teamfossilsarcheology.fossil.advancements;

import net.minecraft.advancements.CriteriaTriggers;

public class ModTriggers {
    public static final OpenSarcophagusTrigger OPEN_SARCOPHAGUS_TRIGGER = CriteriaTriggers.register(new OpenSarcophagusTrigger());
    public static final ScarabTameTrigger SCARAB_TAME_TRIGGER = CriteriaTriggers.register(new ScarabTameTrigger());
    public static final ImplantEmbryoTrigger IMPLANT_EMBRYO_TRIGGER = CriteriaTriggers.register(new ImplantEmbryoTrigger());
    public static final IncubateEggTrigger INCUBATE_EGG_TRIGGER = CriteriaTriggers.register(new IncubateEggTrigger());

    public static void register() {
        //
    }
}
