package fossilsarcheology.client.model;

import com.github.alexthe666.citadel.client.model.AdvancedEntityModel;
import com.github.alexthe666.citadel.client.model.AdvancedModelBox;

public class AdvancedModelRenderer extends AdvancedModelBox {

    public AdvancedModelRenderer(AdvancedEntityModel model, int textureOffsetX, int textureOffsetY) {
        super(model, textureOffsetX, textureOffsetY);
        if(model instanceof ModelPrehistoric){
            ((ModelPrehistoric) model).addToListOfCubes(this);
        }
    }
}
