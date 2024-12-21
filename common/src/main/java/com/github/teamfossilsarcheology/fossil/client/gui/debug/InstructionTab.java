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
import com.mojang.blaze3d.vertex.PoseStack;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.client.CycleOption;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
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

        addWidget(new Button(5, 340, 50, 20, new TextComponent("Start"), button -> {
            MessageHandler.DEBUG_CHANNEL.sendToServer(new InstructionMessage(entity.getId(), true, INSTRUCTIONS.get(entity.getUUID()).instructions));
            debugScreen.onClose();
            onClose();
        }));
        addWidget(new Button(5, 365, 50, 20, new TextComponent("Stop"), button -> {
            MessageHandler.DEBUG_CHANNEL.sendToServer(new InstructionMessage(entity.getId(), true, List.of()));
        }));
        addWidget(new Button(60, 365, 70, 20, new TextComponent("Stop All"), button -> {
            for (Map.Entry<UUID, Pair> entry : INSTRUCTIONS.entrySet()) {
                MessageHandler.DEBUG_CHANNEL.sendToServer(new InstructionMessage(entry.getValue().id, true,List.of()));
            }
            debugScreen.onClose();
            onClose();
        }, (button, poseStack, i, j) -> {
            debugScreen.renderTooltip(poseStack, new TextComponent("Stops Instruction for all mobs"), i, j);
        }));
        addWidget(new Button(60, 340, 70, 20, new TextComponent("Start All"), button -> {
            for (Map.Entry<UUID, Pair> entry : INSTRUCTIONS.entrySet()) {
                MessageHandler.DEBUG_CHANNEL.sendToServer(new InstructionMessage(entry.getValue().id, true, entry.getValue().instructions));
            }
            debugScreen.onClose();
            onClose();
        }, (button, poseStack, i, j) -> {
            debugScreen.renderTooltip(poseStack, new TextComponent("Starts Instruction for all mobs"), i, j);
        }));
        addWidget(new Button(135, 340, 50, 20, new TextComponent("Debug"), button -> {
            MessageHandler.DEBUG_CHANNEL.sendToServer(new InstructionMessage(entity.getId(), true, INSTRUCTIONS.get(entity.getUUID()).instructions));
        }, (button, poseStack, i, j) -> {
            debugScreen.renderTooltip(poseStack, new TextComponent("Starts Instruction without closing the debug menu"), i, j);
        }));
        addWidget(new Button(220, 5, 100, 20, new TextComponent("Walk Builder"), button -> {
            positionMode = Instruction.Type.MOVE_TO;
            debugScreen.onClose();
            //TODO: Custom icons next to crosshair for walk/teleport
        }, (button, poseStack, i, j) -> {
            debugScreen.renderTooltip(poseStack, new TextComponent("Left click to place, Right click to cancel"), i, j);
        }));

        addWidget(new Button(220, 30, 100, 20, new TextComponent("Teleport Builder"), button -> {
            positionMode = Instruction.Type.TELEPORT_TO;
            teleportRotation = 0;
            debugScreen.onClose();
        }, (button, poseStack, i, j) -> {
            debugScreen.renderTooltip(poseStack, new TextComponent("Left click to place, Right click to cancel, Mousewheel to rotate"), i, j);
        }));
        if (entity instanceof Meganeura) {
            addWidget(new Button(220, 55, 100, 20, new TextComponent("Attach Builder"), button -> {
                positionMode = Instruction.Type.ATTACH_TO;
                debugScreen.onClose();
            }));
        }
        var list = entity.level.getNearbyEntities(LivingEntity.class, TargetingConditions.forNonCombat().range(30).ignoreLineOfSight(), entity, entity.getBoundingBox().inflate(30));
        leapEntities = new EntityList(width - 315, 200, 300, list, minecraft, entity1 -> {
            Instruction instruction = new Instruction.LeapAttack(entity1.getId());
            instructions.addInstruction(instruction);
            INSTRUCTIONS.get(entity.getUUID()).instructions.add(instruction);
        });
        if (entity instanceof PrehistoricFlying) {
            addWidget(new Button(220, 55, 100, 20, new TextComponent("Fly Builder"), button -> {
                positionMode = Instruction.Type.FLY_TO;
                debugScreen.onClose();
            }));
            addWidget(new Button(220, 80, 100, 20, new TextComponent("Land Builder"), button -> {
                positionMode = Instruction.Type.FLY_LAND;
                debugScreen.onClose();
            }));
        }
        if (entity instanceof PrehistoricLeaping) {
            addWidget(new Button(220, 55, 100, 20, new TextComponent("Leap Builder"), button -> {
                positionMode = Instruction.Type.LEAP_LAND;
                debugScreen.onClose();
            }));
            addWidget(new Button(width - 115, 5, 90, 20, new TextComponent("Open Leap"), button -> {
                closeLists();
                addWidget(leapEntities);
            }, (button, poseStack, i, j) -> {
                debugScreen.renderTooltip(poseStack, new TextComponent("Won't save correctly when leaving the world"), i, j);
            }));
        }
        attackEntities = new EntityList(width - 315, 200, 300, list, minecraft, entity1 -> {
        });
       /* addWidget(new Button(width - 315, 5, 90, 20, new TextComponent("Open Attack"), button -> {
            closeLists();
            addWidget(attackEntities);
        }, (button, poseStack, i, j) -> {
            debugScreen.renderTooltip(poseStack, new TextComponent("Unused"), i, j);
        }));*/
        List<String> controllers = entity.getFactory().getOrCreateAnimationData(entity.getId()).getAnimationControllers().keySet().stream().toList();
        animations = new AnimationList(width - 315, entity.getAllAnimations(), controllers, minecraft, animationObject -> {
            Instruction instruction = new Instruction.PlayAnim(animationObject.name(), animationObject.controller(), animationObject.loop(), (int) animationObject.transitionLength());
            instructions.addInstruction(instruction);
            INSTRUCTIONS.get(entity.getUUID()).instructions.add(instruction);
        });
        addWidget(new Button(width - 215, 5, 90, 20, new TextComponent("Open Animations"), button -> {
            closeLists();
            addWidget(animations);
        }, (button, poseStack, i, j) -> {
            debugScreen.renderTooltip(poseStack, new TextComponent("Stops and plays animation x times or for x seconds"), i, j);
        }));

        EditBox zPosInput = addWidget(new EditBox(minecraft.font, 325, 30, 30, 20, new TextComponent("")));
        zPosInput.setValue(new DecimalFormat("##", DecimalFormatSymbols.getInstance(Locale.US)).format(5));
        addWidget(new Button(325, 5, 70, 20, new TextComponent("Add Idle"), button -> {
            Instruction instruction = new Instruction.Idle(Integer.parseInt(zPosInput.getValue()) * 20);
            instructions.addInstruction(instruction);
            INSTRUCTIONS.get(entity.getUUID()).instructions.add(instruction);
        }));
        addWidget(new Button(400, 5, 70, 20, new TextComponent("Add Sleep"), button -> {
            Instruction instruction = new Instruction.Sleep(Integer.parseInt(zPosInput.getValue()) * 20);
            instructions.addInstruction(instruction);
            INSTRUCTIONS.get(entity.getUUID()).instructions.add(instruction);
        }));
    }

    private void closeLists() {
        widgets.remove(leapEntities);
        renderables.remove(leapEntities);
        widgets.remove(animations);
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
        BlockPos target = hitResult.getBlockPos().offset(hitResult.getDirection().getNormal());
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

    @Override
    protected void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        super.render(poseStack, mouseX, mouseY, partialTick);
    }

    public record Pair(int id, List<Instruction> instructions) {

    }

    private static class AnimationList extends AbstractAnimationList {

        public AnimationList(int x0, Map<String, ? extends AnimationInfo> animations, List<String> controllers, Minecraft minecraft, Consumer<AnimationObject> function) {
            super(x0, 300, 25, 60, animations, minecraft, function);
            int buttonX = x0 + rowWidth + 15;
            if (!controllers.isEmpty()) {
                currentControllerName = controllers.get(0);
                addWidget(CycleOption.create("", () -> controllers, TextComponent::new,
                                options -> currentControllerName, (options, option, controller) -> currentControllerName = controller)
                        .createButton(Minecraft.getInstance().options, buttonX, y0, 100));
            }
            addWidget(new DebugSlider(buttonX, y0 + 25, 100, 20, new TextComponent("Count: "), new TextComponent(""), 0, 20, transitionLength, 1, 3, true) {
                @Override
                protected void applyValue() {
                    transitionLength = (float) (stepSize * Math.round(Mth.lerp(value, minValue, maxValue) / stepSize));
                }
            });
            addWidget(CycleOption.createOnOff("Time based", options -> loop, (options, option, loop) -> this.loop = loop)
                    .createButton(Minecraft.getInstance().options, buttonX, y0 + 50, 100));
        }
    }
}
