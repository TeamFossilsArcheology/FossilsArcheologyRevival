package fossilsarcheology.server.entity.ai;

import fossilsarcheology.server.entity.prehistoric.EntityPrehistoric;
import fossilsarcheology.server.entity.prehistoric.PrehistoricEntityTypeAI;
import net.minecraft.entity.ai.EntityAIPanic;

public class DinoAIPanic extends EntityAIPanic {
    EntityPrehistoric prehistoric;

    public DinoAIPanic(EntityPrehistoric prehistoric, double speed) {
        super(prehistoric, speed);
        this.prehistoric = prehistoric;
    }

    public boolean shouldExecute(){
        if(prehistoric.aiResponseType() != PrehistoricEntityTypeAI.Response.SCARED){
            return false;
        }
        return super.shouldExecute();
    }

    public void startExecuting()
    {
        super.startExecuting();
        prehistoric.isRunningAway = true;
    }
}
