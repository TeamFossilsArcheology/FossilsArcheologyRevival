package fossilsarcheology.server.item;

import fossilsarcheology.server.advancement.EggTrigger;
import fossilsarcheology.server.advancement.EmbryoTrigger;
import fossilsarcheology.server.api.DefaultRenderedItem;
import fossilsarcheology.server.entity.prehistoric.PrehistoricEntityType;
import fossilsarcheology.server.entity.utility.FossilsMammalProperties;
import net.ilexiconn.llibrary.server.entity.EntityPropertiesHandler;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class MammalEmbryoItem extends PrehistoricEntityItem implements DefaultRenderedItem {
	public static final EmbryoTrigger EMBRYO_TRIGGER = (EmbryoTrigger) CriteriaTriggers.register(new EmbryoTrigger());
	public MammalEmbryoItem(PrehistoricEntityType type) {
		super("syringe", type);
		this.setMaxDamage(0);
		this.type = type;
	}

	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected){
		if(!worldIn.isRemote && entityIn instanceof EntityPlayerMP){
			EMBRYO_TRIGGER.trigger((EntityPlayerMP)entityIn);
		}
	}


	@Override
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase entity, EnumHand hand) {
		if (PrehistoricEntityType.isMammal(entity) && !entity.isChild()) {
			FossilsMammalProperties properties = EntityPropertiesHandler.INSTANCE.getProperties(entity, FossilsMammalProperties.class);
			if (properties != null) {
				if (properties.isPregnant()) {
					return false;
				}else{
					properties.embryoProgress = 1;
					properties.embryo = this.type;
					if (!player.isCreative()) {
						stack.shrink(1);
					}
					for (int i = 0; i < 7; i++) {
						entity.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, entity.posX + entity.getRNG().nextFloat() * entity.width * 2.0F - entity.width, entity.posY + 0.5D + (entity.getRNG().nextFloat() * entity.height), entity.posZ + (entity.getRNG().nextFloat() * entity.width * 2.0F) - entity.width, entity.getRNG().nextGaussian() * 0.02D, entity.getRNG().nextGaussian() * 0.02D, entity.getRNG().nextGaussian() * 0.02D);
					}
					return true;
				}

			}
		}
		return false;
	}
}
