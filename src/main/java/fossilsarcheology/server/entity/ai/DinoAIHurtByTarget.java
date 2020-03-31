package fossilsarcheology.server.entity.ai;

import fossilsarcheology.server.entity.prehistoric.EntityPrehistoric;
import fossilsarcheology.server.entity.prehistoric.PrehistoricEntityTypeAI;
import net.minecraft.entity.ai.EntityAIHurtByTarget;

public class DinoAIHurtByTarget extends EntityAIHurtByTarget {
    EntityPrehistoric prehistoric;

    public DinoAIHurtByTarget(EntityPrehistoric prehistoric) {
        super(prehistoric, true);
        this.setMutexBits(1);
        this.prehistoric = prehistoric;
    }

    public boolean shouldExecute() {
        if (prehistoric.isChild() || prehistoric.aiResponseType() == PrehistoricEntityTypeAI.Response.SCARED) {
            return false;
        }
        return super.shouldExecute();
    }
}
