package fossilsarcheology.server.event;


import com.google.common.base.Predicate;
import fossilsarcheology.Revival;
import fossilsarcheology.server.ServerProxy;
import fossilsarcheology.server.block.FABlockRegistry;
import fossilsarcheology.server.entity.ai.AnimalAIFearDinosaur;
import fossilsarcheology.server.entity.prehistoric.*;
import fossilsarcheology.server.entity.utility.FossilsMammalProperties;
import fossilsarcheology.server.entity.utility.FossilsPlayerProperties;
import fossilsarcheology.server.item.FAItemRegistry;
import net.ilexiconn.llibrary.server.entity.EntityPropertiesHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityPolarBear;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nullable;
import java.util.Random;

public class FossilLivingEvent {

    private static final Predicate ANIMAL_FEAR_DINOSAUR = new Predicate<EntityLivingBase>() {
        public boolean apply(@Nullable EntityLivingBase entity) {
            return entity != null && entity instanceof IScaryDinosaur;
        }
    };

    @SubscribeEvent
    public void onBreakBlock(BlockEvent.BreakEvent event) {
        FossilsPlayerProperties properties = EntityPropertiesHandler.INSTANCE.getProperties(event.getPlayer(), FossilsPlayerProperties.class);
        if (event.getWorld().provider.getDimension() == Revival.CONFIG_OPTIONS.dimensionIDDarknessLair && event.getState().getBlock() != Blocks.OBSIDIAN && event.getState().getBlock() != FABlockRegistry.FAKE_OBSIDIAN && (properties != null && !properties.killedAnu)) {
            event.getPlayer().sendStatusMessage(new TextComponentTranslation("anu.breakblock"), true);
            event.setCanceled(true);
        }
        if (properties != null && properties.killedBiofossilCooldown > 0) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event) {
        if (event.getEntity() != null && isLivestock(event.getEntity()) && event.getEntity() instanceof EntityCreature && Revival.CONFIG_OPTIONS.animalsFearDinosaurs) {
            EntityCreature animal = (EntityCreature) event.getEntity();
            animal.tasks.addTask(1, new AnimalAIFearDinosaur(animal, EntityLivingBase.class, ANIMAL_FEAR_DINOSAUR, 12.0F, 1.2D, 1.5D));
        }
    }

    private boolean isLivestock(Entity entity) {
        String className = entity.getClass().getSimpleName();
        return (entity instanceof EntityCow || entity instanceof EntitySheep || entity instanceof EntityPig || entity instanceof EntityChicken
                || entity instanceof EntityRabbit || entity instanceof AbstractHorse
                || className.contains("Cow") || className.contains("Sheep") || className.contains("Pig") || className.contains("Chicken")
                || className.contains("Rabbit") || className.contains("Peacock") || className.contains("Goat") || className.contains("Ferret")
                || className.contains("Hedgehog") || className.contains("Peahen") || className.contains("Peafowl") || className.contains("Sow")
                || className.contains("Hog") || className.contains("Hog"));
    }

    @SubscribeEvent
    public void entityInteractEvent(PlayerInteractEvent.EntityInteractSpecific event) {
        if (event.getItemStack().getItem() == FAItemRegistry.DINOPEDIA && event.getTarget() instanceof EntityAnimal) {
            FossilsMammalProperties properties = EntityPropertiesHandler.INSTANCE.getProperties(event.getTarget(), FossilsMammalProperties.class);
            if (properties != null && properties.isPregnant()) {
                Revival.PEDIA_OBJECT = event.getTarget();
                Revival.PROXY.openPedia();
            }
        }
    }

    @SubscribeEvent
    public void onEntityLiving(LivingEvent.LivingUpdateEvent event) {
        if (event.getEntityLiving() instanceof EntityPlayer) {
            FossilsPlayerProperties properties = EntityPropertiesHandler.INSTANCE.getProperties((EntityPlayer) event.getEntityLiving(), FossilsPlayerProperties.class);
            if (properties != null && properties.killedBiofossilCooldown > 0) {
                properties.killedBiofossilCooldown--;
            }
        }
        if (PrehistoricEntityType.isMammal(event.getEntityLiving()) && !event.getEntityLiving().isChild()) {
            FossilsMammalProperties properties = EntityPropertiesHandler.INSTANCE.getProperties(event.getEntityLiving(), FossilsMammalProperties.class);
            if (properties != null && properties.embryo != null) {
                ++properties.embryoProgress;
                if (properties.embryoProgress >= Revival.CONFIG_OPTIONS.pregnancyTime) {
                    growEntity(properties.embryo, event);
                    properties.embryoProgress = 0;
                    properties.embryo = null;
                }
            }
        }
    }

    public void growEntity(PrehistoricEntityType embryo, LivingEvent.LivingUpdateEvent event) {
        float rnd = new Random().nextInt(100);
        Entity birthEntity;
        EntityLivingBase entity = event.getEntityLiving();
        birthEntity = embryo.invokeClass(entity.world);
        if (embryo == PrehistoricEntityType.DONKEY) {
            if (entity instanceof EntityDonkey) {
                if (rnd < 5) {
                    birthEntity = new EntityDonkey(entity.world);
                    if (((AbstractHorse) entity).getOwnerUniqueId() != null) {
                        ((EntityDonkey) birthEntity).setOwnerUniqueId(((AbstractHorse) entity).getOwnerUniqueId());
                        ((EntityDonkey) birthEntity).setHorseTamed(true);
                    }
                } else if (rnd < 10) {
                    birthEntity = new EntityDonkey(entity.world);
                    if (((AbstractHorse) entity).getOwnerUniqueId() != null) {
                        ((EntityDonkey) birthEntity).setOwnerUniqueId(((AbstractHorse) entity).getOwnerUniqueId());
                        ((EntityDonkey) birthEntity).setHorseTamed(true);
                    }
                } else {
                    birthEntity = ((EntityDonkey) entity).createChild(new EntityDonkey(entity.world));
                }
            } else {
                EntityDonkey entityHorse = new EntityDonkey(entity.world);
                birthEntity = entityHorse.createChild(new EntityDonkey(entity.world));
            }
        }
        if (embryo == PrehistoricEntityType.HORSE) {
            if (entity instanceof EntityHorse) {
                if (rnd < 5) {
                    birthEntity = new EntityHorse(entity.world);
                    ((EntityHorse) birthEntity).setHorseVariant(3);
                    if (((AbstractHorse) entity).getOwnerUniqueId() != null) {
                        ((EntityHorse) birthEntity).setOwnerUniqueId(((AbstractHorse) entity).getOwnerUniqueId());
                        ((EntityHorse) birthEntity).setHorseTamed(true);
                    }
                } else if (rnd < 10) {
                    birthEntity = new EntityHorse(entity.world);
                    ((EntityHorse) birthEntity).setHorseVariant(4);
                    if (((AbstractHorse) entity).getOwnerUniqueId() != null) {
                        ((EntityHorse) birthEntity).setOwnerUniqueId(((AbstractHorse) entity).getOwnerUniqueId());
                        ((EntityHorse) birthEntity).setHorseTamed(true);
                    }
                } else {
                    birthEntity = ((EntityHorse) entity).createChild(new EntityHorse(entity.world));
                }
            } else {
                EntityHorse entityHorse = new EntityHorse(entity.world);
                birthEntity = entityHorse.createChild(new EntityHorse(entity.world));
            }
        }
        if (embryo == PrehistoricEntityType.LLAMA) {
            if (entity instanceof EntityLlama) {
                if (rnd < 5) {
                    birthEntity = new EntityLlama(entity.world);
                    if (((AbstractHorse) entity).getOwnerUniqueId() != null) {
                        ((EntityLlama) birthEntity).setOwnerUniqueId(((AbstractHorse) entity).getOwnerUniqueId());
                        ((EntityLlama) birthEntity).setHorseTamed(true);
                    }
                } else if (rnd < 10) {
                    birthEntity = new EntityLlama(entity.world);
                    if (((AbstractHorse) entity).getOwnerUniqueId() != null) {
                        ((EntityLlama) birthEntity).setOwnerUniqueId(((AbstractHorse) entity).getOwnerUniqueId());
                        ((EntityLlama) birthEntity).setHorseTamed(true);
                    }
                } else {
                    birthEntity = ((EntityLlama) entity).createChild(new EntityLlama(entity.world));
                }
            } else {
                EntityLlama entityHorse = new EntityLlama(entity.world);
                birthEntity = entityHorse.createChild(new EntityLlama(entity.world));
            }
        }
        if (birthEntity instanceof EntityPrehistoric) {
            if (entity.world.getClosestPlayerToEntity(entity, 15) != null) {
                ((EntityPrehistoric) birthEntity).setTamed(true);
                ((EntityPrehistoric) birthEntity).setOwnerId(entity.world.getClosestPlayerToEntity(entity, 15).getUniqueID());
            }
        }
        if (birthEntity instanceof EntityQuagga) {
            int d0 = (int) (entity.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getBaseValue() + ((EntityQuagga) birthEntity).getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getBaseValue() + (int) ((EntityQuagga) birthEntity).getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getBaseValue());
            ((EntityQuagga) birthEntity).getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(d0 / 3.0D);
            double d2 = entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getBaseValue() + ((EntityQuagga) birthEntity).getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getBaseValue() + ((EntityQuagga) birthEntity).getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getBaseValue();
            ((EntityQuagga) birthEntity).getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(d2 / 3.0D);
        }
        if (birthEntity instanceof EntityPrehistoric) {
            ((EntityPrehistoric) birthEntity).setGender(new Random().nextInt(2));
        } else if (birthEntity instanceof EntityAnimal) {
            ((EntityAnimal) birthEntity).setGrowingAge(-24000);
        }
        birthEntity.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch);
        for (int var3 = 0; var3 < 7; ++var3) {
            double var4 = event.getEntityLiving().getRNG().nextGaussian() * 0.02D;
            double var6 = event.getEntityLiving().getRNG().nextGaussian() * 0.02D;
            double var8 = event.getEntityLiving().getRNG().nextGaussian() * 0.02D;
            entity.world.spawnParticle(EnumParticleTypes.HEART, entity.posX + (double) (event.getEntityLiving().getRNG().nextFloat() * entity.width * 2.0F) - (double) entity.width, entity.posY + 0.5D + (double) (event.getEntityLiving().getRNG().nextFloat() * entity.height), entity.posZ + (double) (event.getEntityLiving().getRNG().nextFloat() * entity.width * 2.0F) - (double) entity.width, var4, var6, var8);
        }
        if (!entity.world.isRemote) {
            entity.world.spawnEntity(birthEntity);
        }
    }

}
