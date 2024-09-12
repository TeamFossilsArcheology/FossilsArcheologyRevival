package com.fossil.fossil.client.gui.debug;

import com.fossil.fossil.client.gui.debug.instruction.EntityList;
import com.fossil.fossil.client.gui.debug.instruction.Instruction;
import com.fossil.fossil.client.gui.debug.instruction.InstructionsList;
import com.fossil.fossil.entity.ai.BreachAttackGoal;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricSwimming;
import com.fossil.fossil.entity.prehistoric.swimming.Meganeura;
import com.fossil.fossil.network.MessageHandler;
import com.fossil.fossil.network.debug.InstructionMessage;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.phys.BlockHitResult;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;

public class InstructionTab extends DebugTab<Prehistoric> {
    public static final Map<UUID, Pair> INSTRUCTIONS = new HashMap<>();
    private InstructionsList instructions;
    private AnimationList animations;
    private EntityList attackEntities;
    private EntityList breachEntities;
    public static Entity entityListHighlight;
    public static Entity highlightInstructionEntity;
    public static Instruction highlightInstruction;
    public static Instruction.Type positionMode = Instruction.Type.IDLE;
    public static int teleportRotation;
    public static Prehistoric activeEntity;

    protected InstructionTab(DebugScreen debugScreen, Prehistoric entity) {
        super(debugScreen, entity);
    }
    //TODO: Add spawner. Use EntityRenderer to render preview. Rotate with mousewheel
    //TODO: Flying mobs, Aquatic mobs
    //TODO: Anu support

    @Override
    protected void init(int width, int height) {
        super.init(width, height);
        instructions = addWidget(new InstructionsList(INSTRUCTIONS.computeIfAbsent(entity.getUUID(), id -> new Pair(entity.getId(), new ArrayList<>())), minecraft));

        addWidget(new Button(5, 340, 50, 20, new TextComponent("Start"), button -> {
            MessageHandler.DEBUG_CHANNEL.sendToServer(new InstructionMessage(entity.getId(), true, INSTRUCTIONS.get(entity.getUUID()).instructions));
            debugScreen.onClose();
            onClose();
        }));
        addWidget(new Button(5, 365, 50, 20, new TextComponent("Stop"), button -> {
            MessageHandler.DEBUG_CHANNEL.sendToServer(new InstructionMessage(entity.getId(), true, List.of()));
        }));
        addWidget(new Button(60, 340, 70, 20, new TextComponent("Start All"), button -> {
            for (Map.Entry<UUID, Pair> entry : INSTRUCTIONS.entrySet()) {
                MessageHandler.DEBUG_CHANNEL.sendToServer(new InstructionMessage(entry.getValue().id, true, entry.getValue().instructions));
            }
            debugScreen.onClose();
            onClose();
        }));
        addWidget(new Button(135, 340, 50, 20, new TextComponent("Debug"), button -> {
            MessageHandler.DEBUG_CHANNEL.sendToServer(new InstructionMessage(entity.getId(), true, INSTRUCTIONS.get(entity.getUUID()).instructions));
        }));
        addWidget(new Button(220, 5, 100, 20, new TextComponent("Walk Builder"), button -> {
            positionMode = Instruction.Type.MOVE_TO;
            debugScreen.onClose();
            //TODO: Custom icons next to crosshair for walk/teleport
        }));

        addWidget(new Button(220, 30, 100, 20, new TextComponent("Teleport Builder"), button -> {
            positionMode = Instruction.Type.TELEPORT_TO;
            teleportRotation = 0;
            debugScreen.onClose();
        }));
        if (entity instanceof Meganeura) {
            addWidget(new Button(220, 55, 100, 20, new TextComponent("Attach Builder"), button -> {
                positionMode = Instruction.Type.ATTACH_TO;
                debugScreen.onClose();
            }));
        }
        var list = entity.level.getNearbyEntities(LivingEntity.class, TargetingConditions.forNonCombat().range(30).ignoreLineOfSight(), entity, entity.getBoundingBox().inflate(30));
        attackEntities = new EntityList(width - 315, 200, 300, list, minecraft, entity1 -> {
            Instruction instruction = new Instruction.Attack(entity1.getId());
            instructions.addInstruction(instruction);
            INSTRUCTIONS.get(entity.getUUID()).instructions.add(instruction);
        });
        addWidget(new Button(width - 315, 5, 90, 20, new TextComponent("Open Attack"), button -> {
            widgets.remove(breachEntities);
            renderables.remove(breachEntities);
            widgets.remove(animations);
            renderables.remove(animations);
            addWidget(attackEntities);
        }));
        if (entity instanceof PrehistoricSwimming swimming && swimming.canDoBreachAttack()) {
            list = entity.level.getNearbyEntities(LivingEntity.class, TargetingConditions.forNonCombat().range(30).ignoreLineOfSight()
                    .selector(target -> !BreachAttackGoal.isEntitySubmerged(target) && PrehistoricSwimming.isOverWater(target)), entity, entity.getBoundingBox().inflate(30));
            breachEntities = new EntityList(width - 315, 200, 300, list, minecraft, entity1 -> {
                Instruction instruction = new Instruction.Breach(entity1.getId());
                instructions.addInstruction(instruction);
                INSTRUCTIONS.get(entity.getUUID()).instructions.add(instruction);
            });
            addWidget(new Button(width - 215, 5, 90, 20, new TextComponent("Open Breach"), button -> {
                widgets.remove(attackEntities);
                renderables.remove(attackEntities);
                widgets.remove(animations);
                renderables.remove(animations);
                addWidget(breachEntities);
            }));
        }
        List<String> controllers = entity.getFactory().getOrCreateAnimationData(entity.getId()).getAnimationControllers().keySet().stream().toList();
        animations = new AnimationList(width - 315, 300, entity.getAllAnimations(), controllers, true, minecraft, animationObject -> {
            Instruction instruction = new Instruction.PlayAnim(animationObject.name(), animationObject.controller(), animationObject.loop(), (int) animationObject.transitionLength());
            instructions.addInstruction(instruction);
            INSTRUCTIONS.get(entity.getUUID()).instructions.add(instruction);
        });
        addWidget(new Button(width - 215, 5, 90, 20, new TextComponent("Open Animations"), button -> {
            widgets.remove(attackEntities);
            renderables.remove(attackEntities);
            widgets.remove(breachEntities);
            renderables.remove(breachEntities);
            addWidget(animations);
        }));

        EditBox zPosInput = addWidget(new EditBox(minecraft.font, 325, 30, 30, 20, new TextComponent("")));
        zPosInput.setValue(new DecimalFormat("##", DecimalFormatSymbols.getInstance(Locale.US)).format(5));
        addWidget(new Button(325, 5, 70, 20, new TextComponent("Add Idle"), button -> {
            Instruction instruction = new Instruction.Idle(Integer.parseInt(zPosInput.getValue()) * 20);
            instructions.addInstruction(instruction);
            INSTRUCTIONS.get(entity.getUUID()).instructions.add(instruction);
        }));
    }

    @Override
    protected void onOpen() {
        activeEntity = entity;
    }

    @Override
    protected void onClose() {
        activeEntity = null;
    }

    public static void addPosition(BlockHitResult hitResult) {
        if (activeEntity == null) return;
        if (positionMode == Instruction.Type.MOVE_TO) {
            BlockPos target = hitResult.getBlockPos().offset(hitResult.getDirection().getNormal());
            INSTRUCTIONS.get(activeEntity.getUUID()).instructions.add(new Instruction.MoveTo(target));
        } else if (positionMode == Instruction.Type.TELEPORT_TO) {
            BlockPos target = hitResult.getBlockPos().offset(hitResult.getDirection().getNormal());
            INSTRUCTIONS.get(activeEntity.getUUID()).instructions.add(new Instruction.TeleportTo(target, -teleportRotation + 180));
        } else if (positionMode == Instruction.Type.ATTACH_TO) {
            BlockPos target = hitResult.getBlockPos();
            INSTRUCTIONS.get(activeEntity.getUUID()).instructions.add(new Instruction.AttachTo(target, hitResult.getDirection(), hitResult.getLocation()));
        }
    }

    public static boolean positionActive() {
        return positionMode != Instruction.Type.IDLE;
    }

    @Override
    protected void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        super.render(poseStack, mouseX, mouseY, partialTick);
    }

    public record Pair(int id, List<Instruction> instructions) {

    }
}
