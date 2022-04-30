package fossilsarcheology.client.model;

import com.github.alexthe666.citadel.client.model.AdvancedEntityModel;
import com.github.alexthe666.citadel.client.model.AdvancedModelBox;
import fossilsarcheology.server.entity.prehistoric.base.EntityPrehistoric;
import net.minecraft.client.renderer.model.ModelRenderer;

import java.util.ArrayList;
import java.util.List;

public abstract class ModelPrehistoric<T extends EntityPrehistoric> extends AdvancedEntityModel<T> {

    private List<AdvancedModelBox> boxList = new ArrayList<>();

    public void addToListOfCubes(AdvancedModelBox box){
        boxList.add(box);
    }

    @Override
    public Iterable<AdvancedModelBox> getAllParts() {
        return boxList;
    }
}
