package fossilsarcheology.client.model;

import fossilsarcheology.server.entity.prehistoric.EntityMegaloceros;
import fossilsarcheology.server.entity.prehistoric.EntityPrehistoric;
import net.ilexiconn.llibrary.client.model.ModelAnimator;
import net.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import net.ilexiconn.llibrary.server.animation.IAnimatedEntity;
import net.minecraft.entity.Entity;

public class ModelMegaloceros extends ModelPrehistoric {
    public AdvancedModelRenderer Body;
    public AdvancedModelRenderer LegL1;
    public AdvancedModelRenderer LowerBody;
    public AdvancedModelRenderer Neck1;
    public AdvancedModelRenderer LegR1;
    public AdvancedModelRenderer Hump;
    public AdvancedModelRenderer LegL2;
    public AdvancedModelRenderer Tail;
    public AdvancedModelRenderer BackLegR1;
    public AdvancedModelRenderer BackLegL1;
    public AdvancedModelRenderer BackLegR2;
    public AdvancedModelRenderer BackLegL2;
    public AdvancedModelRenderer Neck2;
    public AdvancedModelRenderer HeadBase;
    public AdvancedModelRenderer HeadFront;
    public AdvancedModelRenderer Jaw;
    public AdvancedModelRenderer EarR;
    public AdvancedModelRenderer EarL;
    public AdvancedModelRenderer LAntlerBase;
    public AdvancedModelRenderer RAntlerBase;
    public AdvancedModelRenderer HeadFront2;
    public AdvancedModelRenderer LAntlerProng1;
    public AdvancedModelRenderer LAntler1;
    public AdvancedModelRenderer LAntlerProng2;
    public AdvancedModelRenderer LAntlerProng3;
    public AdvancedModelRenderer LAntler2;
    public AdvancedModelRenderer LAntler3;
    public AdvancedModelRenderer LAntler4;
    public AdvancedModelRenderer LAntler14;
    public AdvancedModelRenderer LAntler16;
    public AdvancedModelRenderer LAntler18;
    public AdvancedModelRenderer LAntler6;
    public AdvancedModelRenderer LAntler9;
    public AdvancedModelRenderer LAntler12;
    public AdvancedModelRenderer LAntler7;
    public AdvancedModelRenderer LAntler8;
    public AdvancedModelRenderer LAntler10;
    public AdvancedModelRenderer LAntler11;
    public AdvancedModelRenderer LAntler13;
    public AdvancedModelRenderer LAntler5;
    public AdvancedModelRenderer LAntler15;
    public AdvancedModelRenderer LAntler17;
    public AdvancedModelRenderer RAntlerProng1;
    public AdvancedModelRenderer RAntler1;
    public AdvancedModelRenderer RAntlerProng2;
    public AdvancedModelRenderer RAntlerProng3;
    public AdvancedModelRenderer RAntler2;
    public AdvancedModelRenderer RAntler3;
    public AdvancedModelRenderer RAntler4;
    public AdvancedModelRenderer RAntler14;
    public AdvancedModelRenderer RAntler16;
    public AdvancedModelRenderer RAntler18;
    public AdvancedModelRenderer RAntler6;
    public AdvancedModelRenderer RAntler9;
    public AdvancedModelRenderer RAntler12;
    public AdvancedModelRenderer RAntler7;
    public AdvancedModelRenderer RAntler8;
    public AdvancedModelRenderer RAntler10;
    public AdvancedModelRenderer RAntler11;
    public AdvancedModelRenderer RAntler13;
    public AdvancedModelRenderer RAntler5;
    public AdvancedModelRenderer RAntler15;
    public AdvancedModelRenderer RAntler17;
    public AdvancedModelRenderer LegR2;
    private final ModelAnimator animator;

    public ModelMegaloceros() {
        this.textureWidth = 128;
        this.textureHeight = 128;
        this.LAntler11 = new AdvancedModelRenderer(this, 15, 14);
        this.LAntler11.mirror = true;
        this.LAntler11.setRotationPoint(3.4F, 0.0F, 0.0F);
        this.LAntler11.addBox(0.0F, -0.5F, -0.6F, 3, 1, 1, 0.0F);
        this.setRotateAngle(LAntler11, 0.1960004749989632F, -0.5003858965467743F, 0.21537362969610024F);
        this.RAntler16 = new AdvancedModelRenderer(this, 0, 25);
        this.RAntler16.setRotationPoint(1.0F, 0.0F, -4.0F);
        this.RAntler16.addBox(-2.0F, -0.5F, -0.5F, 2, 1, 1, 0.0F);
        this.setRotateAngle(RAntler16, 0.0F, 3.141592653589793F, 0.34732052114687156F);
        this.Hump = new AdvancedModelRenderer(this, 35, 111);
        this.Hump.setRotationPoint(0.0F, 0.0F, -1.5F);
        this.Hump.addBox(-3.0F, -5.5F, -3.5F, 6, 3, 7, 0.0F);
        this.RAntlerBase = new AdvancedModelRenderer(this, 35, 60);
        this.RAntlerBase.setRotationPoint(-0.5F, -2.0F, -1.5F);
        this.RAntlerBase.addBox(-0.4F, -1.1F, -4.8F, 1, 1, 5, 0.0F);
        this.setRotateAngle(RAntlerBase, -2.356194490192345F, -0.45378560551852565F, -0.40142572795869574F);
        this.LegL2 = new AdvancedModelRenderer(this, 11, 100);
        this.LegL2.mirror = true;
        this.LegL2.setRotationPoint(0.0F, 7.4F, 0.0F);
        this.LegL2.addBox(-0.01F, 0.0F, -1.6F, 2, 8, 2, 0.0F);
        this.BackLegL1 = new AdvancedModelRenderer(this, 20, 96);
        this.BackLegL1.setRotationPoint(-3.0F, 1.3F, 8.7F);
        this.BackLegL1.addBox(-2.0F, 0.0F, -2.5F, 2, 9, 5, 0.0F);
        this.setRotateAngle(BackLegL1, 0.05235987755982988F, 0.0F, 0.0F);
        this.LAntler8 = new AdvancedModelRenderer(this, 5, 13);
        this.LAntler8.mirror = true;
        this.LAntler8.setRotationPoint(3.4F, 0.0F, 0.0F);
        this.LAntler8.addBox(0.0F, -0.5F, -0.5F, 3, 1, 1, 0.0F);
        this.setRotateAngle(LAntler8, 0.0F, -0.41015237421866746F, 0.21537362969610024F);
        this.HeadFront = new AdvancedModelRenderer(this, 0, 53);
        this.HeadFront.setRotationPoint(0.1F, -0.6F, -1.4F);
        this.HeadFront.addBox(-2.0F, -1.4F, -6.4F, 4, 3, 4, 0.0F);
        this.setRotateAngle(HeadFront, 0.091106186954104F, 0.0F, 0.0F);
        this.RAntler2 = new AdvancedModelRenderer(this, 0, 33);
        this.RAntler2.setRotationPoint(0.0F, 0.0F, -4.0F);
        this.RAntler2.addBox(-1.5F, -0.5F, -11.8F, 3, 1, 12, 0.0F);
        this.setRotateAngle(RAntler2, 0.0F, -0.13857914260834978F, -1.2292353921796064F);
        this.LAntler2 = new AdvancedModelRenderer(this, 0, 33);
        this.LAntler2.mirror = true;
        this.LAntler2.setRotationPoint(0.0F, 0.0F, -4.0F);
        this.LAntler2.addBox(-1.5F, -0.5F, -11.8F, 3, 1, 12, 0.0F);
        this.setRotateAngle(LAntler2, 0.0F, 0.13857914260834978F, 1.2292353921796064F);
        this.LAntler13 = new AdvancedModelRenderer(this, 30, 25);
        this.LAntler13.mirror = true;
        this.LAntler13.setRotationPoint(0.0F, 0.0F, -1.8F);
        this.LAntler13.addBox(-0.5F, -0.5F, -3.0F, 1, 1, 3, 0.0F);
        this.setRotateAngle(LAntler13, 0.46949356878647464F, -0.08063421144213803F, 0.41608649367544814F);
        this.RAntler6 = new AdvancedModelRenderer(this, 5, 19);
        this.RAntler6.setRotationPoint(-3.0F, 0.0F, 2.5F);
        this.RAntler6.addBox(0.0F, -0.5F, -0.5F, 2, 1, 1, 0.0F);
        this.setRotateAngle(RAntler6, 0.0F, 3.141592653589793F, -0.16580627893946132F);
        this.RAntlerProng3 = new AdvancedModelRenderer(this, 35, 50);
        this.RAntlerProng3.setRotationPoint(0.0F, 0.0F, 2.7F);
        this.RAntlerProng3.addBox(-0.5F, -0.5F, 0.2F, 1, 1, 2, 0.0F);
        this.setRotateAngle(RAntlerProng3, 0.4864232625308197F, 0.0F, 0.0F);
        this.LAntler15 = new AdvancedModelRenderer(this, 29, 39);
        this.LAntler15.mirror = true;
        this.LAntler15.setRotationPoint(0.0F, 0.0F, -2.8F);
        this.LAntler15.addBox(-0.5F, -0.5F, -4.0F, 1, 1, 4, 0.0F);
        this.setRotateAngle(LAntler15, 0.12095131716320703F, 0.18954275676658416F, 0.22043508452688382F);
        this.LAntler12 = new AdvancedModelRenderer(this, 20, 25);
        this.LAntler12.mirror = true;
        this.LAntler12.setRotationPoint(2.5F, 0.2F, -3.0F);
        this.LAntler12.addBox(-0.5F, -0.5F, -2.0F, 1, 1, 3, 0.0F);
        this.setRotateAngle(LAntler12, 0.3246312408709453F, -0.20193459445574394F, 0.6908013179393556F);
        this.RAntler14 = new AdvancedModelRenderer(this, 20, 40);
        this.RAntler14.setRotationPoint(0.0F, 0.0F, -11.5F);
        this.RAntler14.addBox(-0.5F, -0.5F, -3.0F, 1, 1, 3, 0.0F);
        this.setRotateAngle(RAntler14, -0.13473941825396224F, -0.1890191579909859F, -0.2778564169174973F);
        this.Jaw = new AdvancedModelRenderer(this, 18, 59);
        this.Jaw.setRotationPoint(0.0F, 1.8F, -1.2F);
        this.Jaw.addBox(-2.0F, -0.7F, -6.3F, 4, 1, 5, 0.0F);
        this.setRotateAngle(Jaw, 0.022514747350726852F, 0.0F, 0.0F);
        this.LAntlerProng2 = new AdvancedModelRenderer(this, 41, 50);
        this.LAntlerProng2.mirror = true;
        this.LAntlerProng2.setRotationPoint(0.0F, 0.0F, 2.7F);
        this.LAntlerProng2.addBox(-0.5F, -0.5F, 0.2F, 1, 1, 3, 0.0F);
        this.setRotateAngle(LAntlerProng2, -0.5918411493512771F, 0.0F, 0.0F);
        this.RAntler9 = new AdvancedModelRenderer(this, 15, 20);
        this.RAntler9.setRotationPoint(-3.0F, 0.0F, -0.5F);
        this.RAntler9.addBox(0.0F, -0.5F, -0.5F, 2, 1, 1, 0.0F);
        this.setRotateAngle(RAntler9, 0.0F, 0.3022910264454179F, -3.9222784280068566F);
        this.LAntler14 = new AdvancedModelRenderer(this, 20, 40);
        this.LAntler14.mirror = true;
        this.LAntler14.setRotationPoint(0.0F, 0.0F, -11.5F);
        this.LAntler14.addBox(-0.5F, -0.5F, -3.0F, 1, 1, 3, 0.0F);
        this.setRotateAngle(LAntler14, -0.13473941825396224F, 0.1890191579909859F, 0.2778564169174973F);
        this.LAntler9 = new AdvancedModelRenderer(this, 15, 20);
        this.LAntler9.mirror = true;
        this.LAntler9.setRotationPoint(3.0F, 0.0F, -0.5F);
        this.LAntler9.addBox(0.0F, -0.5F, -0.5F, 2, 1, 1, 0.0F);
        this.setRotateAngle(LAntler9, 0.0F, 0.3022910264454179F, 0.7806857744170635F);
        this.Neck1 = new AdvancedModelRenderer(this, 0, 74);
        this.Neck1.setRotationPoint(0.0F, 0.4F, -4.7F);
        this.Neck1.addBox(-2.5F, -2.0F, -6.0F, 5, 6, 9, 0.0F);
        this.setRotateAngle(Neck1, -0.6403613025567194F, 0.0F, 0.0F);
        this.Tail = new AdvancedModelRenderer(this, 50, 62);
        this.Tail.setRotationPoint(0.0F, -1.6F, 11.2F);
        this.Tail.addBox(-1.5F, -1.0F, 0.0F, 3, 2, 4, 0.0F);
        this.setRotateAngle(Tail, -1.0016444577195458F, 0.0F, 0.0F);
        this.LAntler17 = new AdvancedModelRenderer(this, 0, 22);
        this.LAntler17.mirror = true;
        this.LAntler17.setRotationPoint(-1.7F, 0.0F, 0.0F);
        this.LAntler17.addBox(-3.0F, -0.5F, -0.5F, 3, 1, 1, 0.0F);
        this.setRotateAngle(LAntler17, 0.0F, 0.0F, -0.38484510006474965F);
        this.LAntler7 = new AdvancedModelRenderer(this, 3, 16);
        this.LAntler7.mirror = true;
        this.LAntler7.setRotationPoint(2.0F, 0.0F, 0.0F);
        this.LAntler7.addBox(-0.3F, -0.5F, -0.5F, 4, 1, 1, 0.0F);
        this.setRotateAngle(LAntler7, 0.0F, -0.199142067652553F, 0.35779249665883756F);
        this.RAntler17 = new AdvancedModelRenderer(this, 0, 22);
        this.RAntler17.setRotationPoint(-1.7F, 0.0F, 0.0F);
        this.RAntler17.addBox(-3.0F, -0.5F, -0.5F, 3, 1, 1, 0.0F);
        this.setRotateAngle(RAntler17, 0.0F, 0.0F, -0.38484510006474965F);
        this.HeadFront2 = new AdvancedModelRenderer(this, 0, 53);
        this.HeadFront2.setRotationPoint(-0.1F, -0.6F, -1.3F);
        this.HeadFront2.addBox(-2.0F, -1.4F, -6.4F, 4, 3, 4, 0.0F);
        this.setRotateAngle(HeadFront2, 0.091106186954104F, 0.0F, 0.0F);
        this.RAntler11 = new AdvancedModelRenderer(this, 15, 14);
        this.RAntler11.setRotationPoint(3.4F, 0.0F, 0.0F);
        this.RAntler11.addBox(0.0F, -0.5F, -0.5F, 3, 1, 1, 0.0F);
        this.setRotateAngle(RAntler11, 0.1960004749989632F, -0.5003858965467743F, 0.21537362969610024F);
        this.LAntlerProng1 = new AdvancedModelRenderer(this, 35, 54);
        this.LAntlerProng1.mirror = true;
        this.LAntlerProng1.setRotationPoint(0.0F, -0.6F, -2.1F);
        this.LAntlerProng1.addBox(-0.5F, -0.5F, 0.2F, 1, 1, 3, 0.0F);
        this.setRotateAngle(LAntlerProng1, -1.9896753472735356F, -0.767944870877505F, 0.22689280275926282F);
        this.EarL = new AdvancedModelRenderer(this, 3, 48);
        this.EarL.mirror = true;
        this.EarL.setRotationPoint(2.0F, -1.7F, 0.0F);
        this.EarL.addBox(-0.6F, -1.1F, -2.5F, 2, 1, 3, 0.0F);
        this.setRotateAngle(EarL, -1.9198621771937625F, -0.047472955654245766F, 1.3962634015954636F);
        this.RAntler15 = new AdvancedModelRenderer(this, 29, 39);
        this.RAntler15.setRotationPoint(0.0F, 0.0F, -2.8F);
        this.RAntler15.addBox(-0.5F, -0.5F, -4.0F, 1, 1, 4, 0.0F);
        this.setRotateAngle(RAntler15, 0.12095131716320703F, -0.18954275676658416F, -0.22043508452688382F);
        this.LowerBody = new AdvancedModelRenderer(this, 31, 70);
        this.LowerBody.setRotationPoint(0.0F, 0.5F, 2.0F);
        this.LowerBody.addBox(-3.0F, -2.7F, -0.1F, 6, 9, 12, 0.0F);
        this.setRotateAngle(LowerBody, -0.022514747350726852F, 0.0F, 0.0F);
        this.RAntler13 = new AdvancedModelRenderer(this, 30, 25);
        this.RAntler13.setRotationPoint(0.0F, 0.0F, -1.8F);
        this.RAntler13.addBox(-0.5F, -0.5F, -3.0F, 1, 1, 3, 0.0F);
        this.setRotateAngle(RAntler13, 0.46949356878647464F, -0.08063421144213803F, 0.41608649367544814F);
        this.LegR1 = new AdvancedModelRenderer(this, 0, 100);
        this.LegR1.setRotationPoint(-3.0F, 3.0F, -2.5F);
        this.LegR1.addBox(-2.0F, 0.0F, -1.5F, 2, 8, 3, 0.0F);
        this.setRotateAngle(LegR1, 0.05235987755982988F, 0.0F, 0.0F);
        this.LegL1 = new AdvancedModelRenderer(this, 0, 100);
        this.LegL1.mirror = true;
        this.LegL1.setRotationPoint(3.0F, 3.0F, -2.5F);
        this.LegL1.addBox(0.0F, 0.0F, -1.5F, 2, 8, 3, 0.0F);
        this.setRotateAngle(LegL1, 0.05235987755982988F, 0.0F, 0.0F);
        this.LAntler16 = new AdvancedModelRenderer(this, 0, 25);
        this.LAntler16.mirror = true;
        this.LAntler16.setRotationPoint(-1.0F, 0.0F, -4.0F);
        this.LAntler16.addBox(-2.0F, -0.5F, -0.5F, 2, 1, 1, 0.0F);
        this.setRotateAngle(LAntler16, 0.0F, 0.0F, -0.34732052114687156F);
        this.RAntlerProng2 = new AdvancedModelRenderer(this, 41, 50);
        this.RAntlerProng2.setRotationPoint(0.0F, 0.0F, 2.7F);
        this.RAntlerProng2.addBox(-0.5F, -0.5F, 0.2F, 1, 1, 3, 0.0F);
        this.setRotateAngle(RAntlerProng2, -0.5918411493512771F, 0.0F, 0.0F);
        this.RAntler10 = new AdvancedModelRenderer(this, 15, 17);
        this.RAntler10.setRotationPoint(2.0F, 0.0F, 0.0F);
        this.RAntler10.addBox(-0.3F, -0.5F, -0.5F, 4, 1, 1, 0.0F);
        this.setRotateAngle(RAntler10, 0.290248254606657F, -0.2893755899806598F, 0.17296212887263807F);
        this.HeadBase = new AdvancedModelRenderer(this, 0, 63);
        this.HeadBase.setRotationPoint(0.0F, -0.1F, -4.2F);
        this.HeadBase.addBox(-3.0F, -2.51F, -3.8F, 6, 5, 5, 0.0F);
        this.setRotateAngle(HeadBase, 1.0629055144645467F, 0.0F, 0.0F);
        this.LAntler3 = new AdvancedModelRenderer(this, 0, 25);
        this.LAntler3.mirror = true;
        this.LAntler3.setRotationPoint(2.0F, 0.0F, -8.0F);
        this.LAntler3.addBox(-0.5F, -0.5F, -2.8F, 4, 1, 6, 0.0F);
        this.LAntler4 = new AdvancedModelRenderer(this, 0, 42);
        this.LAntler4.mirror = true;
        this.LAntler4.setRotationPoint(1.5F, 0.0F, -0.5F);
        this.LAntler4.addBox(-0.2F, -0.5F, -0.5F, 3, 1, 1, 0.0F);
        this.setRotateAngle(LAntler4, 0.0F, 0.0F, 0.41887902047863906F);
        this.BackLegL2 = new AdvancedModelRenderer(this, 0, 90);
        this.BackLegL2.setRotationPoint(0.0F, 8.7F, 1.3F);
        this.BackLegL2.addBox(-2.01F, 0.0F, -0.7F, 2, 7, 2, 0.0F);
        this.Neck2 = new AdvancedModelRenderer(this, 21, 70);
        this.Neck2.setRotationPoint(0.0F, 0.5F, -5.6F);
        this.Neck2.addBox(-2.51F, -2.21F, -5.01F, 5, 5, 6, 0.0F);
        this.setRotateAngle(Neck2, -0.2722713633111154F, 0.0F, 0.0F);
        this.EarR = new AdvancedModelRenderer(this, 3, 48);
        this.EarR.setRotationPoint(-2.0F, -1.7F, 0.0F);
        this.EarR.addBox(-1.4F, -1.1F, -2.5F, 2, 1, 3, 0.0F);
        this.setRotateAngle(EarR, -1.9198621771937625F, -0.13962634015954636F, -1.3962634015954636F);
        this.LAntler18 = new AdvancedModelRenderer(this, 0, 35);
        this.LAntler18.mirror = true;
        this.LAntler18.setRotationPoint(2.0F, 0.0F, -4.5F);
        this.LAntler18.addBox(-0.5F, -0.5F, -0.5F, 2, 1, 2, 0.0F);
        this.LAntlerBase = new AdvancedModelRenderer(this, 35, 60);
        this.LAntlerBase.mirror = true;
        this.LAntlerBase.setRotationPoint(0.5F, -2.0F, -1.5F);
        this.LAntlerBase.addBox(-0.6F, -1.1F, -4.8F, 1, 1, 5, 0.0F);
        this.setRotateAngle(LAntlerBase, -2.356194490192345F, 0.45378560551852565F, 0.40142572795869574F);
        this.LAntlerProng3 = new AdvancedModelRenderer(this, 35, 50);
        this.LAntlerProng3.mirror = true;
        this.LAntlerProng3.setRotationPoint(0.0F, 0.0F, 2.7F);
        this.LAntlerProng3.addBox(-0.5F, -0.5F, 0.2F, 1, 1, 2, 0.0F);
        this.setRotateAngle(LAntlerProng3, 0.4864232625308197F, 0.0F, 0.0F);
        this.LAntler10 = new AdvancedModelRenderer(this, 15, 17);
        this.LAntler10.mirror = true;
        this.LAntler10.setRotationPoint(2.0F, 0.0F, 0.0F);
        this.LAntler10.addBox(-0.3F, -0.5F, -0.5F, 4, 1, 1, 0.0F);
        this.setRotateAngle(LAntler10, 0.290248254606657F, -0.2893755899806598F, 0.17296212887263807F);
        this.LAntler6 = new AdvancedModelRenderer(this, 5, 19);
        this.LAntler6.mirror = true;
        this.LAntler6.setRotationPoint(3.0F, 0.0F, 2.5F);
        this.LAntler6.addBox(0.0F, -0.5F, -0.5F, 2, 1, 1, 0.0F);
        this.setRotateAngle(LAntler6, 0.0F, 0.0F, 0.16580627893946132F);
        this.BackLegR2 = new AdvancedModelRenderer(this, 0, 90);
        this.BackLegR2.mirror = true;
        this.BackLegR2.setRotationPoint(0.0F, 8.7F, 1.3F);
        this.BackLegR2.addBox(-0.01F, 0.0F, -0.7F, 2, 7, 2, 0.0F);
        this.RAntler4 = new AdvancedModelRenderer(this, 0, 42);
        this.RAntler4.setRotationPoint(-1.5F, 0.0F, -0.5F);
        this.RAntler4.addBox(-0.2F, -0.5F, -0.5F, 3, 1, 1, 0.0F);
        this.setRotateAngle(RAntler4, 0.0F, 3.141592653589793F, -0.41887902047863906F);
        this.RAntler18 = new AdvancedModelRenderer(this, 0, 35);
        this.RAntler18.setRotationPoint(-2.0F, 0.0F, -4.5F);
        this.RAntler18.addBox(-1.5F, -0.5F, -0.5F, 2, 1, 2, 0.0F);
        this.BackLegR1 = new AdvancedModelRenderer(this, 20, 96);
        this.BackLegR1.mirror = true;
        this.BackLegR1.setRotationPoint(3.0F, 1.3F, 8.7F);
        this.BackLegR1.addBox(0.0F, 0.0F, -2.5F, 2, 9, 5, 0.0F);
        this.setRotateAngle(BackLegR1, 0.05235987755982988F, 0.0F, 0.0F);
        this.LAntler5 = new AdvancedModelRenderer(this, 0, 39);
        this.LAntler5.mirror = true;
        this.LAntler5.setRotationPoint(3.0F, 0.0F, 0.0F);
        this.LAntler5.addBox(-0.4F, -0.5F, -0.5F, 2, 1, 1, 0.0F);
        this.setRotateAngle(LAntler5, 0.0F, 0.0F, 0.296705972839036F);
        this.RAntler12 = new AdvancedModelRenderer(this, 20, 25);
        this.RAntler12.setRotationPoint(-2.5F, 0.2F, -3.0F);
        this.RAntler12.addBox(-0.5F, -0.5F, -2.0F, 1, 1, 3, 0.0F);
        this.setRotateAngle(RAntler12, 0.3246312408709453F, -0.20193459445574394F, -0.6908013179393556F);
        this.Body = new AdvancedModelRenderer(this, 35, 91);
        this.Body.setRotationPoint(0.0F, 5.8F, -4.0F);
        this.Body.addBox(-3.5F, -3.0F, -6.0F, 7, 10, 10, 0.0F);
        this.setRotateAngle(Body, -0.05235987755982988F, 0.0F, 0.0F);
        this.RAntlerProng1 = new AdvancedModelRenderer(this, 35, 54);
        this.RAntlerProng1.setRotationPoint(0.0F, -0.6F, -2.1F);
        this.RAntlerProng1.addBox(-0.5F, -0.5F, 0.2F, 1, 1, 3, 0.0F);
        this.setRotateAngle(RAntlerProng1, -1.9896753472735356F, 0.767944870877505F, -0.22689280275926282F);
        this.LegR2 = new AdvancedModelRenderer(this, 11, 100);
        this.LegR2.setRotationPoint(0.0F, 7.4F, 0.0F);
        this.LegR2.addBox(-2.01F, 0.0F, -1.6F, 2, 8, 2, 0.0F);
        this.RAntler1 = new AdvancedModelRenderer(this, 35, 40);
        this.RAntler1.setRotationPoint(-0.4F, -0.5F, -5.0F);
        this.RAntler1.addBox(-0.5F, -0.5F, -4.2F, 1, 1, 5, 0.0F);
        this.setRotateAngle(RAntler1, 0.0F, 0.7110471372624898F, 0.0F);
        this.RAntler8 = new AdvancedModelRenderer(this, 5, 13);
        this.RAntler8.setRotationPoint(3.4F, 0.0F, 0.0F);
        this.RAntler8.addBox(0.0F, -0.5F, -0.5F, 3, 1, 1, 0.0F);
        this.setRotateAngle(RAntler8, 0.0F, 0.41015237421866746F, 0.21537362969610024F);
        this.RAntler5 = new AdvancedModelRenderer(this, 0, 39);
        this.RAntler5.setRotationPoint(3.0F, 0.0F, 0.0F);
        this.RAntler5.addBox(-0.4F, -0.5F, -0.5F, 2, 1, 1, 0.0F);
        this.setRotateAngle(RAntler5, 0.0F, 0.0F, 0.296705972839036F);
        this.RAntler3 = new AdvancedModelRenderer(this, 0, 25);
        this.RAntler3.setRotationPoint(-2.0F, 0.0F, -8.0F);
        this.RAntler3.addBox(-3.5F, -0.5F, -2.8F, 4, 1, 6, 0.0F);
        this.LAntler1 = new AdvancedModelRenderer(this, 35, 40);
        this.LAntler1.mirror = true;
        this.LAntler1.setRotationPoint(0.4F, -0.5F, -5.0F);
        this.LAntler1.addBox(-0.5F, -0.5F, -4.2F, 1, 1, 5, 0.0F);
        this.setRotateAngle(LAntler1, 0.0F, -0.7110471372624898F, 0.0F);
        this.RAntler7 = new AdvancedModelRenderer(this, 3, 16);
        this.RAntler7.setRotationPoint(2.0F, 0.0F, 0.0F);
        this.RAntler7.addBox(-0.3F, -0.5F, -0.5F, 4, 1, 1, 0.0F);
        this.setRotateAngle(RAntler7, 0.0F, 0.199142067652553F, 0.35779249665883756F);
        this.LAntler10.addChild(this.LAntler11);
        this.RAntler2.addChild(this.RAntler16);
        this.Body.addChild(this.Hump);
        this.HeadBase.addChild(this.RAntlerBase);
        this.LegL1.addChild(this.LegL2);
        this.LowerBody.addChild(this.BackLegL1);
        this.LAntler7.addChild(this.LAntler8);
        this.HeadBase.addChild(this.HeadFront);
        this.RAntler1.addChild(this.RAntler2);
        this.LAntler1.addChild(this.LAntler2);
        this.LAntler12.addChild(this.LAntler13);
        this.RAntler3.addChild(this.RAntler6);
        this.RAntlerProng1.addChild(this.RAntlerProng3);
        this.LAntler14.addChild(this.LAntler15);
        this.LAntler3.addChild(this.LAntler12);
        this.RAntler2.addChild(this.RAntler14);
        this.HeadBase.addChild(this.Jaw);
        this.LAntlerProng1.addChild(this.LAntlerProng2);
        this.RAntler3.addChild(this.RAntler9);
        this.LAntler2.addChild(this.LAntler14);
        this.LAntler3.addChild(this.LAntler9);
        this.Body.addChild(this.Neck1);
        this.LowerBody.addChild(this.Tail);
        this.LAntler16.addChild(this.LAntler17);
        this.LAntler6.addChild(this.LAntler7);
        this.RAntler16.addChild(this.RAntler17);
        this.HeadBase.addChild(this.HeadFront2);
        this.RAntler10.addChild(this.RAntler11);
        this.LAntlerBase.addChild(this.LAntlerProng1);
        this.HeadBase.addChild(this.EarL);
        this.RAntler14.addChild(this.RAntler15);
        this.Body.addChild(this.LowerBody);
        this.RAntler12.addChild(this.RAntler13);
        this.Body.addChild(this.LegR1);
        this.Body.addChild(this.LegL1);
        this.LAntler2.addChild(this.LAntler16);
        this.RAntlerProng1.addChild(this.RAntlerProng2);
        this.RAntler9.addChild(this.RAntler10);
        this.Neck2.addChild(this.HeadBase);
        this.LAntler2.addChild(this.LAntler3);
        this.LAntler2.addChild(this.LAntler4);
        this.BackLegL1.addChild(this.BackLegL2);
        this.Neck1.addChild(this.Neck2);
        this.HeadBase.addChild(this.EarR);
        this.LAntler2.addChild(this.LAntler18);
        this.HeadBase.addChild(this.LAntlerBase);
        this.LAntlerProng1.addChild(this.LAntlerProng3);
        this.LAntler9.addChild(this.LAntler10);
        this.LAntler3.addChild(this.LAntler6);
        this.BackLegR1.addChild(this.BackLegR2);
        this.RAntler2.addChild(this.RAntler4);
        this.RAntler2.addChild(this.RAntler18);
        this.LowerBody.addChild(this.BackLegR1);
        this.LAntler4.addChild(this.LAntler5);
        this.RAntler3.addChild(this.RAntler12);
        this.RAntlerBase.addChild(this.RAntlerProng1);
        this.LegR1.addChild(this.LegR2);
        this.RAntlerBase.addChild(this.RAntler1);
        this.RAntler7.addChild(this.RAntler8);
        this.RAntler4.addChild(this.RAntler5);
        this.RAntler2.addChild(this.RAntler3);
        this.LAntlerBase.addChild(this.LAntler1);
        this.RAntler6.addChild(this.RAntler7);
        this.updateDefaultPose();
        animator = ModelAnimator.create();
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        animate((IAnimatedEntity) entity, f, f1, f2, f3, f4, f5);
        this.Body.render(f5);

    }

    public void animate(IAnimatedEntity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        EntityMegaloceros prehistoric = (EntityMegaloceros) entity;
        this.resetToDefaultPose();
        setRotationAngles(f, f1, f2, f3, f4, f5, (Entity) entity);
        animator.update(entity);
        animator.setAnimation(prehistoric.SPEAK_ANIMATION);
        animator.startKeyframe(10);
        ModelUtils.rotate(animator, Neck2, 15, 0, 0);
        ModelUtils.rotate(animator, HeadBase, -20, 0, 0);
        ModelUtils.rotate(animator, Jaw, 33, 0, 0);
        animator.endKeyframe();
        animator.resetKeyframe(10);
        animator.setAnimation(prehistoric.ATTACK_ANIMATION);
        animator.startKeyframe(5);
        ModelUtils.rotate(animator, HeadBase, 15, 0, 0);
        ModelUtils.rotate(animator, Neck2, 35, 0, 0);
        ModelUtils.rotate(animator, Neck1, 35, 0, 0);
        animator.endKeyframe();
        animator.setStaticKeyframe(5);
        animator.startKeyframe(10);
        ModelUtils.rotate(animator, HeadBase, -25, 0, 0);
        ModelUtils.rotate(animator, Neck1, -15, 0, 0);
        ModelUtils.rotate(animator, Neck2, -15, 0, 0);
        animator.endKeyframe();
        animator.setStaticKeyframe(5);
        animator.resetKeyframe(5);
    }

    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
        AdvancedModelRenderer[] tailParts = {this.Tail};
        AdvancedModelRenderer[] neckParts = {this.Neck1, this.Neck2, this.HeadBase};
        if (((EntityPrehistoric) entity).isSkeleton() || ((EntityPrehistoric) entity).isAIDisabled()) {
            return;
        }
        ModelUtils.faceTargetMod(this.Neck1, f3, f4, 0.33F);
        ModelUtils.faceTargetMod(this.Neck2, f3, f4, 0.33F);
        ModelUtils.faceTargetMod(this.HeadBase, f3, f4, 0.33F);
        float speed = 0.05F;
        float speed2 = 0.7F;
        this.chainWave(tailParts, speed, 0.05F, -3, f2, 1);
        this.chainSwing(tailParts, speed, 0.15F, -2, f2, 1);
        this.chainWave(neckParts, speed, 0.05F, 3, f2, 1);
        this.bob(Body, speed, 0.3F, false, f2, 1);
        this.bob(LegL1, speed, -0.3F, false, f2, 1);
        this.bob(LegR1, speed, -0.3F, false, f2, 1);
        this.bob(BackLegL1, speed, -0.3F, false, f2, 1);
        this.bob(BackLegR1, speed, -0.3F, false, f2, 1);

        this.walk(LegL1, speed2, 0.5F, true, 0F, 0F, f, f1);
        this.walk(LegR1, speed2, 0.5F, false, 0F, 0F, f, f1);

        this.walk(BackLegL1, speed2, 0.5F, true, 0F, -0.2F, f, f1);
        this.walk(BackLegR1, speed2, 0.5F, false, 0F, 0.2F, f, f1);

        this.walk(LegL2, speed2, 0.6F, false, -1F, 0.4F, f, f1);
        this.walk(LegR2, speed2, 0.6F, true, -1F, -0.4F, f, f1);
        this.walk(BackLegL2, speed2, 0.2F, true, 1F, 0.4F, f, f1);
        this.walk(BackLegR2, speed2, 0.2F, false, 1F, -0.4F, f, f1);
        {
            float sitProgress = ((EntityPrehistoric) (entity)).sitProgress;
            sitAnimationRotationPrev(Body, sitProgress, 0, 0, 0);
            sitAnimationRotationPrev(LegR1, sitProgress, (float) Math.toRadians(-50), 0, 0);
            sitAnimationRotationPrev(LegL1, sitProgress, (float) Math.toRadians(-50), 0, 0);
            sitAnimationRotationPrev(LegL2, sitProgress, (float) Math.toRadians(140), 0, 0);
            sitAnimationRotationPrev(LegR2, sitProgress, (float) Math.toRadians(140), 0, 0);
            sitAnimationRotationPrev(BackLegR1, sitProgress, (float) Math.toRadians(40), 0, 0);
            sitAnimationRotationPrev(BackLegL1, sitProgress, (float) Math.toRadians(40), 0, 0);
            sitAnimationRotationPrev(BackLegL2, sitProgress, (float) Math.toRadians(-130), 0, 0);
            sitAnimationRotationPrev(BackLegR2, sitProgress, (float) Math.toRadians(-130), 0, 0);
            sitAnimationPos(BackLegL1, sitProgress, 0, -0.8F, 0);
            sitAnimationPos(BackLegR1, sitProgress, 0, -0.8F, 0);
            sitAnimationPos(BackLegL2, sitProgress, 0, 0F, -2.3F);
            sitAnimationPos(BackLegR2, sitProgress, 0, 0F, -2.3F);
            sitAnimationPos(Body, sitProgress, 0, 9F, 0F);
        }
        {
            float sitProgress = ((EntityPrehistoric) (entity)).sleepProgress;
            sitAnimationRotationPrev(Body, sitProgress, 0, 0, 0);
            sitAnimationRotationPrev(LegR1, sitProgress, (float) Math.toRadians(-50), 0, 0);
            sitAnimationRotationPrev(LegL1, sitProgress, (float) Math.toRadians(-50), 0, 0);
            sitAnimationRotationPrev(LegL2, sitProgress, (float) Math.toRadians(140), 0, 0);
            sitAnimationRotationPrev(LegR2, sitProgress, (float) Math.toRadians(140), 0, 0);
            sitAnimationRotationPrev(BackLegR1, sitProgress, (float) Math.toRadians(40), 0, 0);
            sitAnimationRotationPrev(BackLegL1, sitProgress, (float) Math.toRadians(40), 0, 0);
            sitAnimationRotationPrev(BackLegL2, sitProgress, (float) Math.toRadians(-130), 0, 0);
            sitAnimationRotationPrev(BackLegR2, sitProgress, (float) Math.toRadians(-130), 0, 0);
            sitAnimationRotationPrev(Neck1, sitProgress, (float) Math.toRadians(30), (float) Math.toRadians(-10), 0);
            sitAnimationRotationPrev(Neck2, sitProgress, 0, (float) Math.toRadians(40), 0);
            sitAnimationRotationPrev(HeadBase, sitProgress, (float) Math.toRadians(-7), 0, (float) Math.toRadians(-7));
            sitAnimationPos(BackLegL1, sitProgress, 0, -0.8F, 0);
            sitAnimationPos(BackLegR1, sitProgress, 0, -0.8F, 0);
            sitAnimationPos(BackLegL2, sitProgress, 0, 0F, -2.3F);
            sitAnimationPos(BackLegR2, sitProgress, 0, 0F, -2.3F);
            sitAnimationPos(Body, sitProgress, 0, 9F, 0F);
        }
    }
}
