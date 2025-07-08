package com.github.teamfossilsarcheology.fossil.client.gui.debug;

import com.github.teamfossilsarcheology.fossil.client.gui.debug.instruction.EntityList;
import com.github.teamfossilsarcheology.fossil.client.gui.debug.instruction.Instruction;
import com.github.teamfossilsarcheology.fossil.client.gui.debug.instruction.InstructionsList;
import com.github.teamfossilsarcheology.fossil.entity.animation.AnimationInfo;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.Prehistoric;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricFlying;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricLeaping;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.swimming.Meganeura;
import com.github.teamfossilsarcheology.fossil.network.MessageHandler;
import com.github.teamfossilsarcheology.fossil.network.debug.InstructionMessage;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.phys.BlockHitResult;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;
import java.util.function.Consumer;

public class InstructionTab extends DebugTab<Prehistoric> {
    public static final Map<UUID, Pair> INSTRUCTIONS = new Object2ObjectOpenHashMap<>();
    private InstructionsList instructions;
    private AbstractAnimationList animations;
    private EntityList attackEntities;
    private EntityList leapEntities;
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

    @Override
    protected void init(int width, int height) {
        super.init(width, height);
        instructions = addWidget(new InstructionsList(INSTRUCTIONS.computeIfAbsent(entity.getUUID(), id -> new Pair(entity.getId(), new ArrayList<>())), minecraft));

        addWidget(Button.builder(Component.literal("Start"), button -> {
            MessageHandler.DEBUG_CHANNEL.sendToServer(new InstructionMessage(entity.getId(), true, INSTRUCTIONS.get(entity.getUUID()).instructions));
            debugScreen.onClose();
            onClose();
        }).bounds(5, 340, 50, 20).build());
        addWidget(Button.builder(Component.literal("Stop"), button -> {
            MessageHandler.DEBUG_CHANNEL.sendToServer(new InstructionMessage(entity.getId(), true, List.of()));
        }).bounds(5, 365, 50, 20).build());
        addWidget(Button.builder(Component.literal("Stop All"), button -> {
            for (Map.Entry<UUID, Pair> entry : INSTRUCTIONS.entrySet()) {
                MessageHandler.DEBUG_CHANNEL.sendToServer(new InstructionMessage(entry.getValue().id, true, List.of()));
            }
            debugScreen.onClose();
            onClose();
        }).bounds(60, 365, 70, 20).tooltip(Tooltip.create(Component.literal("Stops Instruction for all mobs"))).build());
        addWidget(Button.builder(Component.literal("Start All"), button -> {
            for (Map.Entry<UUID, Pair> entry : INSTRUCTIONS.entrySet()) {
                MessageHandler.DEBUG_CHANNEL.sendToServer(new InstructionMessage(entry.getValue().id, true, entry.getValue().instructions));
            }
            debugScreen.onClose();
            onClose();
        }).bounds(60, 340, 70, 20).tooltip(Tooltip.create(Component.literal("Starts Instruction for all mobs"))).build());
        addWidget(Button.builder(Component.literal("Debug"), button -> {
            MessageHandler.DEBUG_CHANNEL.sendToServer(new InstructionMessage(entity.getId(), true, INSTRUCTIONS.get(entity.getUUID()).instructions));
        }).bounds(135, 340, 50, 20).tooltip(Tooltip.create(Component.literal("Starts Instruction without closing the debug menu"))).build());

        addWidget(Button.builder(Component.literal("Walk Builder"), button -> {
            positionMode = Instruction.Type.MOVE_TO;
            debugScreen.onClose();
            //TODO: Custom icons next to crosshair for walk/teleport
        }).bounds(220, 5, 100, 20).tooltip(Tooltip.create(Component.literal("Left click to place, Right click to cancel"))).build());

        addWidget(Button.builder(Component.literal("Teleport Builder"), button -> {
            positionMode = Instruction.Type.TELEPORT_TO;
            teleportRotation = 0;
            debugScreen.onClose();
        }).bounds(220, 30, 100, 20).tooltip(Tooltip.create(Component.literal("Left click to place, Right click to cancel, Mousewheel to rotate"))).build());
        if (entity instanceof Meganeura) {
            addWidget(Button.builder(Component.literal("Attach Builder"), button -> {
                positionMode = Instruction.Type.ATTACH_TO;
                debugScreen.onClose();
            }).bounds(220, 55, 100, 20).build());
        }
        var list = entity.level().getNearbyEntities(LivingEntity.class, TargetingConditions.forNonCombat().range(30).ignoreLineOfSight(), entity, entity.getBoundingBox().inflate(30));
        leapEntities = new EntityList(width - 315, 200, 300, list, minecraft, entity1 -> {
            Instruction instruction = new Instruction.LeapAttack(entity1.getId());
            instructions.addInstruction(instruction);
            INSTRUCTIONS.get(entity.getUUID()).instructions.add(instruction);
        });
        if (entity instanceof PrehistoricFlying) {
            addWidget(Button.builder(Component.literal("Fly Builder"), button -> {
                positionMode = Instruction.Type.FLY_TO;
                debugScreen.onClose();
            }).bounds(220, 55, 100, 20).build());
            addWidget(Button.builder(Component.literal("Land Builder"), button -> {
                positionMode = Instruction.Type.FLY_LAND;
                debugScreen.onClose();
            }).bounds(220, 80, 100, 20).build());
        }
        if (entity instanceof PrehistoricLeaping) {
            addWidget(Button.builder(Component.literal("Leap Builder"), button -> {
                positionMode = Instruction.Type.LEAP_LAND;
                debugScreen.onClose();
            }).bounds(220, 55, 100, 20).build());
            addWidget(Button.builder(Component.literal("Open Leap"), button -> {
                closeLists();
                addWidget(leapEntities);
            }).bounds(width - 115, 5, 90, 20).tooltip(Tooltip.create(Component.literal("Won't save correctly when leaving the world"))).build());
        }
        attackEntities = new EntityList(width - 315, 200, 300, list, minecraft, entity1 -> {
        });
       /* addWidget(new Button(width - 315, 5, 90, 20, Component.literal("Open Attack"), button -> {
            closeLists();
            addWidget(attackEntities);
        }, (button, poseStack, i, j) -> {
            debugScreen.renderTooltip(poseStack, Component.literal("Unused"), i, j);
        }));*/
        List<String> controllers = entity.getAnimatableInstanceCache().getManagerForId(entity.getId()).getAnimationControllers().keySet().stream().toList();
        animations = new AnimationList(width - 315, entity.getAllAnimations(), controllers, minecraft, animationObject -> {
            Instruction instruction = new Instruction.PlayAnim(animationObject.name(), animationObject.controller(), animationObject.loop(), (int) animationObject.transitionLength());
            instructions.addInstruction(instruction);
            INSTRUCTIONS.get(entity.getUUID()).instructions.add(instruction);
        });
        addWidget(Button.builder(Component.literal("Open Animations"), button -> {
            closeLists();
            addWidget(animations);
        }).bounds(width - 215, 5, 90, 20).tooltip(Tooltip.create(Component.literal("Stops and plays animation x times or for x seconds"))).build());

        EditBox zPosInput = addWidget(new EditBox(minecraft.font, 325, 30, 30, 20, Component.literal("")));
        zPosInput.setValue(new DecimalFormat("##", DecimalFormatSymbols.getInstance(Locale.US)).format(5));
        addWidget(Button.builder(Component.literal("Add Idle"), button -> {
            Instruction instruction = new Instruction.Idle(Integer.parseInt(zPosInput.getValue()) * 20);
            instructions.addInstruction(instruction);
            INSTRUCTIONS.get(entity.getUUID()).instructions.add(instruction);
        }).bounds(325, 5, 70, 20).build());
        addWidget(Button.builder(Component.literal("Add Sleep"), button -> {
            Instruction instruction = new Instruction.Sleep(Integer.parseInt(zPosInput.getValue()) * 20);
            instructions.addInstruction(instruction);
            INSTRUCTIONS.get(entity.getUUID()).instructions.add(instruction);
        }).bounds(400, 5, 70, 20).build());
    }

    private void closeLists() {
        listeners.remove(leapEntities);
        renderables.remove(leapEntities);
        listeners.remove(animations);
        renderables.remove(animations);
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
        BlockPos target = hitResult.getBlockPos().relative(hitResult.getDirection());
        if (positionMode == Instruction.Type.MOVE_TO) {
            INSTRUCTIONS.get(activeEntity.getUUID()).instructions.add(new Instruction.MoveTo(target));
        } else if (positionMode == Instruction.Type.FLY_LAND) {
            INSTRUCTIONS.get(activeEntity.getUUID()).instructions.add(new Instruction.FlyLand(target));
        } else if (positionMode == Instruction.Type.TELEPORT_TO) {
            INSTRUCTIONS.get(activeEntity.getUUID()).instructions.add(new Instruction.TeleportTo(target, -teleportRotation + 180));
        } else if (positionMode == Instruction.Type.ATTACH_TO) {
            target = hitResult.getBlockPos();
            INSTRUCTIONS.get(activeEntity.getUUID()).instructions.add(new Instruction.AttachTo(target, hitResult.getDirection(), hitResult.getLocation()));
        } else if (positionMode == Instruction.Type.LEAP_LAND) {
            INSTRUCTIONS.get(activeEntity.getUUID()).instructions.add(new Instruction.LeapLand(hitResult.getLocation(), hitResult.getLocation().add(0, 2, 0)));
        }
    }

    public static void addFlyPosition(BlockPos target) {
        if (positionMode == Instruction.Type.FLY_TO) {
            INSTRUCTIONS.get(activeEntity.getUUID()).instructions.add(new Instruction.FlyTo(target));
        }
    }

    public static boolean positionActive() {
        return positionMode != Instruction.Type.IDLE;
    }

    public record Pair(int id, List<Instruction> instructions) {

    }

    private static class AnimationList extends AbstractAnimationList {

        public AnimationList(int x0, Map<String, ? extends AnimationInfo> animations, List<String> controllers, Minecraft minecraft, Consumer<AnimationObject> function) {
            super(x0, 300, 25, 60, animations, minecraft, function);
            int buttonX = x0 + rowWidth + 15;
            if (!controllers.isEmpty()) {
                currentControllerName = controllers.get(0);
                addWidget(CycleButton.builder(Component::literal).withValues(controllers).withInitialValue(currentControllerName)
                        .create(buttonX, y0, 100, 20, Component.literal(""),
                                (cycleButton, controller) -> currentControllerName = controller));
            }
            addWidget(new DebugSlider(buttonX, y0 + 25, 100, 20, Component.literal("Count: "), Component.literal(""), 0, 20, transitionLength, 1, 3, true) {
                @Override
                protected void applyValue() {
                    transitionLength = (int) (stepSize * Math.round(Mth.lerp(value, minValue, maxValue) / stepSize));
                }
            });
            addWidget(CycleButton.onOffBuilder(loop).create(buttonX, y0 + 50, 100, 20,
                    Component.literal("Time based"), (button, loop) -> this.loop = loop));
        }
    }
}
