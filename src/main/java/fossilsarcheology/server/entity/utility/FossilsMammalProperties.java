package fossilsarcheology.server.entity.utility;

import fossilsarcheology.server.entity.prehistoric.PrehistoricEntityType;
import net.ilexiconn.llibrary.server.entity.EntityProperties;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.nbt.NBTTagCompound;

public class FossilsMammalProperties extends EntityProperties<EntityAnimal> {

	public int embryoProgress;
	public PrehistoricEntityType embryo = null;

	@Override
	public int getTrackingTime() {
		return 20;
	}

	@Override
	public void saveNBTData(NBTTagCompound compound) {
		if(embryo != null){
			compound.setInteger("Embryo", embryo.ordinal());
		}else{
			compound.setInteger("Embryo", -1);

		}
		compound.setInteger("EmbryoProgress", embryoProgress);

	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		this.embryoProgress = compound.getInteger("EmbryoProgress");
		int ordinal = compound.getInteger("Embryo");
		if(ordinal < 0){
			this.embryo = null;
		}else{
			this.embryo = PrehistoricEntityType.values()[ordinal];
		}

	}

	public boolean isPregnant(){
		return embryoProgress > 0;
	}

	@Override
	public void init() {
	}

	@Override
	public String getID() {
		return "Fossils - Mammal Tracker";
	}

	@Override
	public Class getEntityClass() {
		return EntityAnimal.class;
	}
}
