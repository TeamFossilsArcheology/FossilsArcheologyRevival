package fossilsarcheology.server.entity.utility;

import net.ilexiconn.llibrary.server.entity.EntityProperties;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class FossilsPlayerProperties extends EntityProperties<EntityPlayer> {

	public boolean killedAnu;
	public boolean hasHatchedDinosaur;
	public int killedBiofossilCooldown;

	@Override
	public void saveNBTData(NBTTagCompound compound) {
		compound.setBoolean("KilledAnu", killedAnu);
		compound.setInteger("KilledBiofossil", killedBiofossilCooldown);
		compound.setBoolean("RaisedDinosaur", hasHatchedDinosaur);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		this.killedAnu = compound.getBoolean("KilledAnu");
		this.killedBiofossilCooldown = compound.getInteger("KilledBiofossil");
		this.hasHatchedDinosaur = compound.getBoolean("RaisedDinosaur");
	}

	@Override
	public void init() {
	}

	@Override
	public String getID() {
		return "Fossils - Player Tracker";
	}

	@Override
	public Class getEntityClass() {
		return EntityPlayer.class;
	}
}
