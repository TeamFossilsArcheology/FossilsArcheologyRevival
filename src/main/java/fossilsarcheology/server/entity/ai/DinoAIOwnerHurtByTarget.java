package fossilsarcheology.server.entity.ai;

import fossilsarcheology.server.entity.prehistoric.EntityPrehistoric;
import fossilsarcheology.server.entity.prehistoric.PrehistoricEntityTypeAI;
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;

public class DinoAIOwnerHurtByTarget extends EntityAIOwnerHurtByTarget {
    EntityPrehistoric prehistoric;

    public DinoAIOwnerHurtByTarget(EntityPrehistoric prehistoric) {
        super(prehistoric);
        this.setMutexBits(0);
        this.prehistoric = prehistoric;
    }

    public boolean shouldExecute(){
        if(prehistoric.isChild() || prehistoric.aiResponseType() == PrehistoricEntityTypeAI.Response.SCARED){
            return false;
        }
        return super.shouldExecute();
    }
}
