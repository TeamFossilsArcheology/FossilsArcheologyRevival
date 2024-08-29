package com.fossil.fossil.client.gui.debug;

import com.fossil.fossil.client.gui.debug.instruction.EntityList;
import com.fossil.fossil.client.gui.debug.instruction.Instruction;
import com.fossil.fossil.client.gui.debug.instruction.InstructionsList;
import com.fossil.fossil.entity.ai.BreachAttackGoal;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricSwimming;
import com.fossil.fossil.network.MessageHandler;
import com.fossil.fossil.network.debug.C2SInstructionMessage;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;

public class InstructionTab extends DebugTab {
    public static final Map<Integer, List<Instruction>> INSTRUCTIONS = new HashMap<>();
    private InstructionsList animations;
    private EntityList attackEntities;
    private EntityList breachEntities;
    public static Entity entityListHighlight;
    public static Entity highlightInstructionEntity;
    public static Instruction highlightInstruction;
    public static Instruction.Type positionMode = Instruction.Type.IDLE;
    public static Entity activeEntity;

    protected InstructionTab(DebugScreen debugScreen, Entity entity) {
        super(debugScreen, entity);
    }
    //TODO: Loop, Sleep, Sit, Anim, Attack
    //TODO: Save across sessions
    //TODO: Teleport rotation
    //TODO: Drag instructions in list
    //TODO: Add spawner. Use EntityRenderer to render preview. Rotate with mousewheel
    //TODO: Flying mobs, Aquatic mobs
    //TODO: Arrow above dino instead of outline

    @Override
    protected void init(int width, int height) {
        super.init(width, height);
        animations = addWidget(new InstructionsList(INSTRUCTIONS.computeIfAbsent(entity.getId(), id -> new ArrayList<>()), minecraft));

        addWidget(new Button(5, 240, 50, 20, new TextComponent("Start"), button -> {
            MessageHandler.DEBUG_CHANNEL.sendToServer(new C2SInstructionMessage(entity.getId(), true, INSTRUCTIONS.get(entity.getId())));
            debugScreen.onClose();
            onClose();
        }));
        addWidget(new Button(5, 265, 50, 20, new TextComponent("Stop"), button -> {
            MessageHandler.DEBUG_CHANNEL.sendToServer(new C2SInstructionMessage(entity.getId(), true, List.of()));
        }));
        addWidget(new Button(60, 240, 70, 20, new TextComponent("Start All"), button -> {
            for (Map.Entry<Integer, List<Instruction>> entry : INSTRUCTIONS.entrySet()) {
                MessageHandler.DEBUG_CHANNEL.sendToServer(new C2SInstructionMessage(entry.getKey(), true, entry.getValue()));
            }
            debugScreen.onClose();
            onClose();
        }));
        addWidget(new Button(135, 240, 50, 20, new TextComponent("Debug"), button -> {
            MessageHandler.DEBUG_CHANNEL.sendToServer(new C2SInstructionMessage(entity.getId(), true, INSTRUCTIONS.get(entity.getId())));
        }));
        addWidget(new Button(220, 5, 100, 20, new TextComponent("Walk Builder"), button -> {
            positionMode = Instruction.Type.WALK_TO;
            debugScreen.onClose();
            //TODO: Custom icons next to crosshair for walk/teleport
        }));

        addWidget(new Button(220, 30, 100, 20, new TextComponent("Teleport Builder"), button -> {
            positionMode = Instruction.Type.TELEPORT;
            debugScreen.onClose();
        }));
        if (entity instanceof LivingEntity livingEntity) {
            var list = entity.level.getNearbyEntities(LivingEntity.class, TargetingConditions.forNonCombat().range(30).ignoreLineOfSight(), livingEntity, entity.getBoundingBox().inflate(30));
            attackEntities = new EntityList(width - 220, 200, 300, list, minecraft, entity1 -> {
                Instruction instruction = new Instruction.Attack(entity1.getId());
                animations.addInstruction(instruction);
                INSTRUCTIONS.get(entity.getId()).add(instruction);
            });
            addWidget(new Button(width - 315, 5, 90, 20, new TextComponent("Open Attack"), button -> {
                widgets.remove(breachEntities);
                renderables.remove(breachEntities);
                addWidget(attackEntities);
            }));
            if (entity instanceof PrehistoricSwimming) {
                list = entity.level.getNearbyEntities(LivingEntity.class, TargetingConditions.forNonCombat().range(30).ignoreLineOfSight()
                        .selector(target -> !BreachAttackGoal.isEntitySubmerged(target) && PrehistoricSwimming.isOverWater(target)), livingEntity, entity.getBoundingBox().inflate(30));
                breachEntities = new EntityList(width - 220, 200, 300, list, minecraft, entity1 -> {
                    Instruction instruction = new Instruction.Breach(entity1.getId());
                    animations.addInstruction(instruction);
                    INSTRUCTIONS.get(entity.getId()).add(instruction);
                });
                addWidget(new Button(width - 215, 5, 90, 20, new TextComponent("Open Breach"), button -> {
                    widgets.remove(attackEntities);
                    renderables.remove(attackEntities);
                    addWidget(breachEntities);
                }));
            }
        }

        EditBox zPosInput = addWidget(new EditBox(minecraft.font, 325, 30, 30, 20, new TextComponent("")));
        zPosInput.setValue(new DecimalFormat("##", DecimalFormatSymbols.getInstance(Locale.US)).format(5));
        addWidget(new Button(325, 5, 70, 20, new TextComponent("Add Idle"), button -> {
            Instruction instruction = new Instruction.Idle(Integer.parseInt(zPosInput.getValue()) * 20);
            animations.addInstruction(instruction);
            INSTRUCTIONS.get(entity.getId()).add(instruction);
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

    public static void addPosition(BlockPos target) {
        if (activeEntity == null) return;
        if (positionMode == Instruction.Type.WALK_TO) {
            INSTRUCTIONS.get(activeEntity.getId()).add(new Instruction.MoveTo(target));
        } else if (positionMode == Instruction.Type.TELEPORT) {
            INSTRUCTIONS.get(activeEntity.getId()).add(new Instruction.TeleportTo(target));
        }
    }

    public static boolean positionActive() {
        return positionMode != Instruction.Type.IDLE;
    }

    @Override
    protected void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        super.render(poseStack, mouseX, mouseY, partialTick);
    }
}
