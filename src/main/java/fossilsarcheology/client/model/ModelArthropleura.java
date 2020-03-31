package fossilsarcheology.client.model;

import fossilsarcheology.server.entity.prehistoric.EntityArthropleura;
import fossilsarcheology.server.entity.prehistoric.EntityMegalograptus;
import fossilsarcheology.server.entity.prehistoric.EntityPrehistoric;
import net.ilexiconn.llibrary.client.model.ModelAnimator;
import net.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import net.ilexiconn.llibrary.server.animation.IAnimatedEntity;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import scala.util.parsing.combinator.PackratParsers;

public class ModelArthropleura extends ModelPrehistoric {
    private final ModelAnimator animator;
    public AdvancedModelRenderer bodySegmentBA;
    public AdvancedModelRenderer bodySegmentFA;
    public AdvancedModelRenderer flangeBAL;
    public AdvancedModelRenderer flangeBAR;
    public AdvancedModelRenderer legBARA;
    public AdvancedModelRenderer bodySegmentBB;
    public AdvancedModelRenderer legBARB;
    public AdvancedModelRenderer legBALA;
    public AdvancedModelRenderer legBALB;
    public AdvancedModelRenderer flangeFAL;
    public AdvancedModelRenderer flangeFAR;
    public AdvancedModelRenderer bodySegmentFB;
    public AdvancedModelRenderer legFARA;
    public AdvancedModelRenderer legFARB;
    public AdvancedModelRenderer legFALA;
    public AdvancedModelRenderer legFALB;
    public AdvancedModelRenderer flangeFBL;
    public AdvancedModelRenderer flangeFBR;
    public AdvancedModelRenderer legFBRA;
    public AdvancedModelRenderer head;
    public AdvancedModelRenderer legFBRB;
    public AdvancedModelRenderer legFBLA;
    public AdvancedModelRenderer legFBLB;
    public AdvancedModelRenderer mandibleL;
    public AdvancedModelRenderer mandibleR;
    public AdvancedModelRenderer antennaRA;
    public AdvancedModelRenderer antennaRA_1;
    public AdvancedModelRenderer antennaLB;
    public AdvancedModelRenderer antennaRB;
    public AdvancedModelRenderer flangeBBL;
    public AdvancedModelRenderer flangeBBR;
    public AdvancedModelRenderer legBBRA;
    public AdvancedModelRenderer bodySegmentBC;
    public AdvancedModelRenderer legBBRB;
    public AdvancedModelRenderer legBBLA;
    public AdvancedModelRenderer legBBLB;
    public AdvancedModelRenderer flangeBCL;
    public AdvancedModelRenderer flangeBCR;
    public AdvancedModelRenderer legBCRA;
    public AdvancedModelRenderer bodySegmentBD;
    public AdvancedModelRenderer legBCRB;
    public AdvancedModelRenderer legBCLA;
    public AdvancedModelRenderer legBCLB;
    public AdvancedModelRenderer flangeBDL;
    public AdvancedModelRenderer flangeBDR;
    public AdvancedModelRenderer legBDRA;
    public AdvancedModelRenderer bodySegmentBE;
    public AdvancedModelRenderer legBDRB;
    public AdvancedModelRenderer legBDLA;
    public AdvancedModelRenderer legBDLB;
    public AdvancedModelRenderer flangeBEL;
    public AdvancedModelRenderer flangeBER;
    public AdvancedModelRenderer legBERA;
    public AdvancedModelRenderer bodySegmentBF;
    public AdvancedModelRenderer legBERB;
    public AdvancedModelRenderer legBELA;
    public AdvancedModelRenderer legBELB;
    public AdvancedModelRenderer flangeBFL;
    public AdvancedModelRenderer flangeBFL_1;
    public AdvancedModelRenderer legBFRA;
    public AdvancedModelRenderer flangeBack;
    public AdvancedModelRenderer legBFRB;
    public AdvancedModelRenderer legBFLA;
    public AdvancedModelRenderer legBFLB;

    public ModelArthropleura() {
        this.textureWidth = 128;
        this.textureHeight = 64;
        this.mandibleL = new AdvancedModelRenderer(this, 2, 39);
        this.mandibleL.mirror = true;
        this.mandibleL.setRotationPoint(3.6F, 1.4F, -8.1F);
        this.mandibleL.addBox(-1.0F, -0.9F, -4.0F, 2, 2, 6, 0.0F);
        this.setRotateAngle(mandibleL, 0.0F, 0.8203047484373349F, 0.0F);
        this.flangeBBR = new AdvancedModelRenderer(this, 20, 33);
        this.flangeBBR.mirror = true;
        this.flangeBBR.setRotationPoint(0.0F, 0.2F, 6.5F);
        this.flangeBBR.addBox(-11.5F, -2.0F, -6.5F, 8, 2, 12, 0.0F);
        this.setRotateAngle(flangeBBR, 0.0F, 0.0F, -0.05235987755982988F);
        this.legBDRB = new AdvancedModelRenderer(this, 2, 25);
        this.legBDRB.setRotationPoint(-5.1F, 1.6F, 7.5F);
        this.legBDRB.addBox(-1.0F, -0.9F, -4.7F, 2, 2, 6, 0.0F);
        this.setRotateAngle(legBDRB, 1.5707963267948966F, 0.0F, 1.0471975511965976F);
        this.legBDRA = new AdvancedModelRenderer(this, 2, 25);
        this.legBDRA.setRotationPoint(-5.1F, 1.6F, 2.0F);
        this.legBDRA.addBox(-1.0F, -0.9F, -4.7F, 2, 2, 6, 0.0F);
        this.setRotateAngle(legBDRA, 1.5707963267948966F, 0.0F, 1.0471975511965976F);
        this.legBELA = new AdvancedModelRenderer(this, 2, 25);
        this.legBELA.mirror = true;
        this.legBELA.setRotationPoint(5.1F, 1.6F, 2.0F);
        this.legBELA.addBox(-1.0F, -0.9F, -4.7F, 2, 2, 6, 0.0F);
        this.setRotateAngle(legBELA, 1.5707963267948966F, 0.0F, -1.0471975511965976F);
        this.flangeFAR = new AdvancedModelRenderer(this, 20, 48);
        this.flangeFAR.mirror = true;
        this.flangeFAR.setRotationPoint(0.0F, 0.2F, -4.5F);
        this.flangeFAR.addBox(-10.5F, -2.0F, -6.5F, 7, 2, 12, 0.0F);
        this.setRotateAngle(flangeFAR, 0.0F, -0.03490658503988659F, -0.05235987755982988F);
        this.legBBRB = new AdvancedModelRenderer(this, 2, 25);
        this.legBBRB.setRotationPoint(-5.1F, 1.6F, 7.5F);
        this.legBBRB.addBox(-1.0F, -0.9F, -4.7F, 2, 2, 6, 0.0F);
        this.setRotateAngle(legBBRB, 1.5707963267948966F, 0.0F, 1.0471975511965976F);
        this.flangeBEL = new AdvancedModelRenderer(this, 20, 48);
        this.flangeBEL.setRotationPoint(0.0F, 0.2F, 6.5F);
        this.flangeBEL.addBox(3.3F, -2.0F, -6.5F, 7, 2, 12, 0.0F);
        this.setRotateAngle(flangeBEL, 0.0F, -0.03490658503988659F, 0.05235987755982988F);
        this.legBDLA = new AdvancedModelRenderer(this, 2, 25);
        this.legBDLA.mirror = true;
        this.legBDLA.setRotationPoint(5.1F, 1.6F, 2.0F);
        this.legBDLA.addBox(-1.0F, -0.9F, -4.7F, 2, 2, 6, 0.0F);
        this.setRotateAngle(legBDLA, 1.5707963267948966F, 0.0F, -1.0471975511965976F);
        this.legBERA = new AdvancedModelRenderer(this, 2, 25);
        this.legBERA.setRotationPoint(-5.1F, 1.6F, 2.0F);
        this.legBERA.addBox(-1.0F, -0.9F, -4.7F, 2, 2, 6, 0.0F);
        this.setRotateAngle(legBERA, 1.5707963267948966F, 0.0F, 1.0471975511965976F);
        this.mandibleR = new AdvancedModelRenderer(this, 2, 39);
        this.mandibleR.setRotationPoint(-3.6F, 1.4F, -8.1F);
        this.mandibleR.addBox(-1.0F, -0.9F, -4.0F, 2, 2, 6, 0.0F);
        this.setRotateAngle(mandibleR, 0.0F, -0.8203047484373349F, 0.0F);
        this.bodySegmentBB = new AdvancedModelRenderer(this, 85, 33);
        this.bodySegmentBB.setRotationPoint(0.0F, 0.0F, 10.5F);
        this.bodySegmentBB.addBox(-4.5F, -1.96F, 0.0F, 9, 5, 12, 0.0F);
        this.flangeBack = new AdvancedModelRenderer(this, 33, 3);
        this.flangeBack.setRotationPoint(0.0F, 0.3F, 9.3F);
        this.flangeBack.addBox(-6.5F, -2.0F, 2.5F, 13, 2, 6, 0.0F);
        this.setRotateAngle(flangeBack, -0.10471975511965977F, 0.0F, 0.0F);
        this.bodySegmentBA = new AdvancedModelRenderer(this, 85, 33);
        this.bodySegmentBA.setRotationPoint(0.0F, 19.2F, -1.5F);
        this.bodySegmentBA.addBox(-4.5F, -1.98F, 0.0F, 9, 5, 12, 0.0F);
        this.head = new AdvancedModelRenderer(this, 69, 15);
        this.head.setRotationPoint(0.0F, 0.2F, -9.9F);
        this.head.addBox(-5.0F, -2.0F, -8.0F, 10, 5, 8, 0.0F);
        this.bodySegmentBE = new AdvancedModelRenderer(this, 85, 33);
        this.bodySegmentBE.setRotationPoint(0.0F, 0.0F, 10.5F);
        this.bodySegmentBE.addBox(-4.5F, -1.9F, 0.0F, 9, 5, 12, 0.0F);
        this.legBCLA = new AdvancedModelRenderer(this, 2, 25);
        this.legBCLA.mirror = true;
        this.legBCLA.setRotationPoint(5.1F, 1.6F, 2.0F);
        this.legBCLA.addBox(-1.0F, -0.9F, -4.7F, 2, 2, 6, 0.0F);
        this.setRotateAngle(legBCLA, 1.5707963267948966F, 0.0F, -1.0471975511965976F);
        this.flangeBDL = new AdvancedModelRenderer(this, 20, 48);
        this.flangeBDL.setRotationPoint(0.0F, 0.2F, 6.5F);
        this.flangeBDL.addBox(4.0F, -2.0F, -6.5F, 7, 2, 12, 0.0F);
        this.setRotateAngle(flangeBDL, 0.0F, 0.0F, 0.05235987755982988F);
        this.flangeBDR = new AdvancedModelRenderer(this, 20, 48);
        this.flangeBDR.mirror = true;
        this.flangeBDR.setRotationPoint(0.0F, 0.2F, 6.5F);
        this.flangeBDR.addBox(-11.0F, -2.0F, -6.5F, 7, 2, 12, 0.0F);
        this.setRotateAngle(flangeBDR, 0.0F, 0.0F, -0.05235987755982988F);
        this.bodySegmentFB = new AdvancedModelRenderer(this, 85, 33);
        this.bodySegmentFB.setRotationPoint(0.0F, 0.0F, -10.5F);
        this.bodySegmentFB.addBox(-4.5F, -2.02F, -11.0F, 9, 5, 12, 0.0F);
        this.legBCLB = new AdvancedModelRenderer(this, 2, 25);
        this.legBCLB.mirror = true;
        this.legBCLB.setRotationPoint(5.1F, 1.6F, 7.5F);
        this.legBCLB.addBox(-1.0F, -0.9F, -4.7F, 2, 2, 6, 0.0F);
        this.setRotateAngle(legBCLB, 1.5707963267948966F, 0.0F, -1.0471975511965976F);
        this.flangeBER = new AdvancedModelRenderer(this, 20, 48);
        this.flangeBER.mirror = true;
        this.flangeBER.setRotationPoint(0.0F, 0.2F, 6.5F);
        this.flangeBER.addBox(-10.3F, -1.9F, -6.5F, 7, 2, 12, 0.0F);
        this.setRotateAngle(flangeBER, 0.0F, 0.03490658503988659F, -0.05235987755982988F);
        this.legBERB = new AdvancedModelRenderer(this, 2, 25);
        this.legBERB.setRotationPoint(-5.1F, 1.6F, 7.5F);
        this.legBERB.addBox(-1.0F, -0.9F, -4.7F, 2, 2, 6, 0.0F);
        this.setRotateAngle(legBERB, 1.5707963267948966F, 0.0F, 1.0471975511965976F);
        this.bodySegmentFA = new AdvancedModelRenderer(this, 85, 33);
        this.bodySegmentFA.setRotationPoint(0.0F, 0.0F, 0.5F);
        this.bodySegmentFA.addBox(-4.5F, -2.0F, -11.0F, 9, 5, 12, 0.0F);
        this.legBFRB = new AdvancedModelRenderer(this, 2, 25);
        this.legBFRB.setRotationPoint(-5.1F, 1.6F, 7.5F);
        this.legBFRB.addBox(-1.0F, -0.9F, -4.7F, 2, 2, 6, 0.0F);
        this.setRotateAngle(legBFRB, 1.5707963267948966F, 0.0F, 1.0471975511965976F);
        this.legBCRB = new AdvancedModelRenderer(this, 2, 25);
        this.legBCRB.setRotationPoint(-5.1F, 1.6F, 7.5F);
        this.legBCRB.addBox(-1.0F, -0.9F, -4.7F, 2, 2, 6, 0.0F);
        this.setRotateAngle(legBCRB, 1.5707963267948966F, 0.0F, 1.0471975511965976F);
        this.legBFLB = new AdvancedModelRenderer(this, 2, 25);
        this.legBFLB.mirror = true;
        this.legBFLB.setRotationPoint(5.1F, 1.6F, 7.5F);
        this.legBFLB.addBox(-1.0F, -0.9F, -4.7F, 2, 2, 6, 0.0F);
        this.setRotateAngle(legBFLB, 1.5707963267948966F, 0.0F, -1.0471975511965976F);
        this.flangeFAL = new AdvancedModelRenderer(this, 20, 48);
        this.flangeFAL.setRotationPoint(0.0F, 0.2F, -4.5F);
        this.flangeFAL.addBox(3.5F, -2.0F, -6.5F, 7, 2, 12, 0.0F);
        this.setRotateAngle(flangeFAL, 0.0F, 0.03490658503988659F, 0.05235987755982988F);
        this.flangeBFL_1 = new AdvancedModelRenderer(this, 20, 48);
        this.flangeBFL_1.mirror = true;
        this.flangeBFL_1.setRotationPoint(0.0F, 0.2F, 6.5F);
        this.flangeBFL_1.addBox(-9.0F, -2.0F, -6.5F, 7, 2, 12, 0.0F);
        this.setRotateAngle(flangeBFL_1, 0.0F, 0.10471975511965977F, -0.061086523819801536F);
        this.legFALB = new AdvancedModelRenderer(this, 2, 25);
        this.legFALB.mirror = true;
        this.legFALB.setRotationPoint(5.2F, 1.6F, -4.0F);
        this.legFALB.addBox(-1.0F, -0.9F, -4.7F, 2, 2, 6, 0.0F);
        this.setRotateAngle(legFALB, 1.5707963267948966F, 0.0F, -1.0471975511965976F);
        this.legBFLA = new AdvancedModelRenderer(this, 2, 25);
        this.legBFLA.mirror = true;
        this.legBFLA.setRotationPoint(5.1F, 1.6F, 2.0F);
        this.legBFLA.addBox(-1.0F, -0.9F, -4.7F, 2, 2, 6, 0.0F);
        this.setRotateAngle(legBFLA, 1.5707963267948966F, 0.0F, -1.0471975511965976F);
        this.flangeFBL = new AdvancedModelRenderer(this, 20, 16);
        this.flangeFBL.setRotationPoint(0.0F, 0.2F, -4.5F);
        this.flangeFBL.addBox(3.0F, -2.0F, -6.5F, 6, 2, 12, 0.0F);
        this.setRotateAngle(flangeFBL, 0.0F, 0.10471975511965977F, 0.061086523819801536F);
        this.legFARA = new AdvancedModelRenderer(this, 2, 25);
        this.legFARA.setRotationPoint(-5.2F, 1.6F, -9.0F);
        this.legFARA.addBox(-1.0F, -0.9F, -4.7F, 2, 2, 6, 0.0F);
        this.setRotateAngle(legFARA, 1.5707963267948966F, 0.0F, 1.0471975511965976F);
        this.antennaLB = new AdvancedModelRenderer(this, 2, 11);
        this.antennaLB.mirror = true;
        this.antennaLB.setRotationPoint(0.0F, 0.0F, -8.0F);
        this.antennaLB.addBox(-0.5F, -0.5F, -8.8F, 1, 1, 9, 0.0F);
        this.setRotateAngle(antennaLB, 0.4363323129985824F, 0.0F, 0.0F);
        this.legBBRA = new AdvancedModelRenderer(this, 2, 25);
        this.legBBRA.setRotationPoint(-5.1F, 1.6F, 2.0F);
        this.legBBRA.addBox(-1.0F, -0.9F, -4.7F, 2, 2, 6, 0.0F);
        this.setRotateAngle(legBBRA, 1.5707963267948966F, 0.0F, 1.0471975511965976F);
        this.legFARB = new AdvancedModelRenderer(this, 2, 25);
        this.legFARB.setRotationPoint(-5.2F, 1.6F, -4.0F);
        this.legFARB.addBox(-1.0F, -0.9F, -4.7F, 2, 2, 6, 0.0F);
        this.setRotateAngle(legFARB, 1.5707963267948966F, 0.0F, 1.0471975511965976F);
        this.legBELB = new AdvancedModelRenderer(this, 2, 25);
        this.legBELB.mirror = true;
        this.legBELB.setRotationPoint(5.1F, 1.6F, 7.5F);
        this.legBELB.addBox(-1.0F, -0.9F, -4.7F, 2, 2, 6, 0.0F);
        this.setRotateAngle(legBELB, 1.5707963267948966F, 0.0F, -1.0471975511965976F);
        this.legBARB = new AdvancedModelRenderer(this, 2, 25);
        this.legBARB.setRotationPoint(-5.1F, 1.6F, 7.5F);
        this.legBARB.addBox(-1.0F, -0.9F, -4.7F, 2, 2, 6, 0.0F);
        this.setRotateAngle(legBARB, 1.5707963267948966F, 0.0F, 1.0471975511965976F);
        this.flangeFBR = new AdvancedModelRenderer(this, 20, 16);
        this.flangeFBR.mirror = true;
        this.flangeFBR.setRotationPoint(0.0F, 0.2F, -4.5F);
        this.flangeFBR.addBox(-9.0F, -2.0F, -6.5F, 6, 2, 12, 0.0F);
        this.setRotateAngle(flangeFBR, 0.0F, -0.10471975511965977F, -0.061086523819801536F);
        this.antennaRA = new AdvancedModelRenderer(this, 2, 11);
        this.antennaRA.mirror = true;
        this.antennaRA.setRotationPoint(1.3F, -1.5F, -6.9F);
        this.antennaRA.addBox(-0.5F, -0.5F, -8.8F, 1, 1, 9, 0.0F);
        this.setRotateAngle(antennaRA, -0.20943951023931953F, -0.47123889803846897F, 0.0F);
        this.bodySegmentBD = new AdvancedModelRenderer(this, 85, 33);
        this.bodySegmentBD.setRotationPoint(0.0F, 0.0F, 10.5F);
        this.bodySegmentBD.addBox(-4.5F, -1.92F, 0.0F, 9, 5, 12, 0.0F);
        this.flangeBAL = new AdvancedModelRenderer(this, 20, 48);
        this.flangeBAL.setRotationPoint(0.0F, 0.2F, 6.5F);
        this.flangeBAL.addBox(4.0F, -1.9F, -6.5F, 7, 2, 12, 0.0F);
        this.setRotateAngle(flangeBAL, 0.0F, 0.0F, 0.05235987755982988F);
        this.legBALA = new AdvancedModelRenderer(this, 2, 25);
        this.legBALA.mirror = true;
        this.legBALA.setRotationPoint(5.1F, 1.6F, 2.0F);
        this.legBALA.addBox(-1.0F, -0.9F, -4.7F, 2, 2, 6, 0.0F);
        this.setRotateAngle(legBALA, 1.5707963267948966F, 0.0F, -1.0471975511965976F);
        this.bodySegmentBC = new AdvancedModelRenderer(this, 85, 33);
        this.bodySegmentBC.setRotationPoint(0.0F, 0.0F, 10.5F);
        this.bodySegmentBC.addBox(-4.5F, -1.94F, 0.0F, 9, 5, 12, 0.0F);
        this.legBALB = new AdvancedModelRenderer(this, 2, 25);
        this.legBALB.mirror = true;
        this.legBALB.setRotationPoint(5.1F, 1.6F, 7.5F);
        this.legBALB.addBox(-1.0F, -0.9F, -4.7F, 2, 2, 6, 0.0F);
        this.setRotateAngle(legBALB, 1.5707963267948966F, 0.0F, -1.0471975511965976F);
        this.legBCRA = new AdvancedModelRenderer(this, 2, 25);
        this.legBCRA.setRotationPoint(-5.1F, 1.6F, 2.0F);
        this.legBCRA.addBox(-1.0F, -0.9F, -4.7F, 2, 2, 6, 0.0F);
        this.setRotateAngle(legBCRA, 1.5707963267948966F, 0.0F, 1.0471975511965976F);
        this.legBBLB = new AdvancedModelRenderer(this, 2, 25);
        this.legBBLB.mirror = true;
        this.legBBLB.setRotationPoint(5.1F, 1.6F, 7.5F);
        this.legBBLB.addBox(-1.0F, -0.9F, -4.7F, 2, 2, 6, 0.0F);
        this.setRotateAngle(legBBLB, 1.5707963267948966F, 0.0F, -1.0471975511965976F);
        this.legFBRB = new AdvancedModelRenderer(this, 2, 25);
        this.legFBRB.setRotationPoint(-3.7F, 1.6F, -3.0F);
        this.legFBRB.addBox(-1.0F, -0.9F, -4.7F, 2, 2, 6, 0.0F);
        this.setRotateAngle(legFBRB, 1.5707963267948966F, 0.0F, 1.0471975511965976F);
        this.antennaRB = new AdvancedModelRenderer(this, 2, 11);
        this.antennaRB.mirror = true;
        this.antennaRB.setRotationPoint(0.0F, 0.0F, -8.0F);
        this.antennaRB.addBox(-0.5F, -0.5F, -8.8F, 1, 1, 9, 0.0F);
        this.setRotateAngle(antennaRB, 0.4363323129985824F, 0.0F, 0.0F);
        this.bodySegmentBF = new AdvancedModelRenderer(this, 85, 33);
        this.bodySegmentBF.setRotationPoint(0.0F, 0.0F, 10.5F);
        this.bodySegmentBF.addBox(-4.5F, -1.88F, 0.0F, 9, 5, 12, 0.0F);
        this.antennaRA_1 = new AdvancedModelRenderer(this, 2, 11);
        this.antennaRA_1.mirror = true;
        this.antennaRA_1.setRotationPoint(-1.3F, -1.5F, -6.9F);
        this.antennaRA_1.addBox(-0.5F, -0.5F, -8.8F, 1, 1, 9, 0.0F);
        this.setRotateAngle(antennaRA_1, -0.20943951023931953F, 0.47123889803846897F, 0.0F);
        this.legFBRA = new AdvancedModelRenderer(this, 2, 25);
        this.legFBRA.setRotationPoint(-3.2F, 1.6F, -8.5F);
        this.legFBRA.addBox(-1.0F, -0.9F, -4.7F, 2, 2, 6, 0.0F);
        this.setRotateAngle(legFBRA, 1.5707963267948966F, 0.0F, 1.0471975511965976F);
        this.flangeBCR = new AdvancedModelRenderer(this, 20, 33);
        this.flangeBCR.mirror = true;
        this.flangeBCR.setRotationPoint(0.0F, 0.2F, 6.5F);
        this.flangeBCR.addBox(-11.2F, -1.9F, -6.5F, 8, 2, 12, 0.0F);
        this.setRotateAngle(flangeBCR, 0.0F, 0.0F, -0.05235987755982988F);
        this.flangeBBL = new AdvancedModelRenderer(this, 20, 33);
        this.flangeBBL.setRotationPoint(0.0F, 0.2F, 6.5F);
        this.flangeBBL.addBox(3.5F, -2.0F, -6.5F, 8, 2, 12, 0.0F);
        this.setRotateAngle(flangeBBL, 0.0F, 0.0F, 0.05235987755982988F);
        this.legBBLA = new AdvancedModelRenderer(this, 2, 25);
        this.legBBLA.mirror = true;
        this.legBBLA.setRotationPoint(5.1F, 1.6F, 2.0F);
        this.legBBLA.addBox(-1.0F, -0.9F, -4.7F, 2, 2, 6, 0.0F);
        this.setRotateAngle(legBBLA, 1.5707963267948966F, 0.0F, -1.0471975511965976F);
        this.legFBLA = new AdvancedModelRenderer(this, 2, 25);
        this.legFBLA.mirror = true;
        this.legFBLA.setRotationPoint(3.2F, 1.6F, -8.5F);
        this.legFBLA.addBox(-1.0F, -0.9F, -4.7F, 2, 2, 6, 0.0F);
        this.setRotateAngle(legFBLA, 1.5707963267948966F, 0.0F, -1.0471975511965976F);
        this.legFBLB = new AdvancedModelRenderer(this, 2, 25);
        this.legFBLB.mirror = true;
        this.legFBLB.setRotationPoint(3.7F, 1.6F, -3.0F);
        this.legFBLB.addBox(-1.0F, -0.9F, -4.7F, 2, 2, 6, 0.0F);
        this.setRotateAngle(legFBLB, 1.5707963267948966F, 0.0F, -1.0471975511965976F);
        this.flangeBFL = new AdvancedModelRenderer(this, 20, 48);
        this.flangeBFL.setRotationPoint(0.0F, 0.2F, 6.5F);
        this.flangeBFL.addBox(2.0F, -2.0F, -6.5F, 7, 2, 12, 0.0F);
        this.setRotateAngle(flangeBFL, 0.0F, -0.10471975511965977F, 0.061086523819801536F);
        this.legBFRA = new AdvancedModelRenderer(this, 2, 25);
        this.legBFRA.setRotationPoint(-5.7F, 1.6F, 2.0F);
        this.legBFRA.addBox(-1.0F, -0.9F, -4.7F, 2, 2, 6, 0.0F);
        this.setRotateAngle(legBFRA, 1.5707963267948966F, 0.0F, 1.0471975511965976F);
        this.legFALA = new AdvancedModelRenderer(this, 2, 25);
        this.legFALA.mirror = true;
        this.legFALA.setRotationPoint(5.2F, 1.6F, -9.0F);
        this.legFALA.addBox(-1.0F, -0.9F, -4.7F, 2, 2, 6, 0.0F);
        this.setRotateAngle(legFALA, 1.5707963267948966F, 0.0F, -1.0471975511965976F);
        this.legBDLB = new AdvancedModelRenderer(this, 2, 25);
        this.legBDLB.mirror = true;
        this.legBDLB.setRotationPoint(5.1F, 1.6F, 7.5F);
        this.legBDLB.addBox(-1.0F, -0.9F, -4.7F, 2, 2, 6, 0.0F);
        this.setRotateAngle(legBDLB, 1.5707963267948966F, 0.0F, -1.0471975511965976F);
        this.legBARA = new AdvancedModelRenderer(this, 2, 25);
        this.legBARA.setRotationPoint(-5.1F, 1.6F, 2.0F);
        this.legBARA.addBox(-1.0F, -0.9F, -4.7F, 2, 2, 6, 0.0F);
        this.setRotateAngle(legBARA, 1.5707963267948966F, 0.0F, 1.0471975511965976F);
        this.flangeBAR = new AdvancedModelRenderer(this, 20, 48);
        this.flangeBAR.mirror = true;
        this.flangeBAR.setRotationPoint(0.0F, 0.2F, 6.5F);
        this.flangeBAR.addBox(-11.0F, -1.9F, -6.5F, 7, 2, 12, 0.0F);
        this.setRotateAngle(flangeBAR, 0.0F, 0.0F, -0.05235987755982988F);
        this.flangeBCL = new AdvancedModelRenderer(this, 20, 33);
        this.flangeBCL.setRotationPoint(0.0F, 0.2F, 6.5F);
        this.flangeBCL.addBox(3.2F, -1.9F, -6.5F, 8, 2, 12, 0.0F);
        this.setRotateAngle(flangeBCL, 0.0F, 0.0F, 0.05235987755982988F);
        this.head.addChild(this.mandibleL);
        this.bodySegmentBB.addChild(this.flangeBBR);
        this.bodySegmentBD.addChild(this.legBDRB);
        this.bodySegmentBD.addChild(this.legBDRA);
        this.bodySegmentBE.addChild(this.legBELA);
        this.bodySegmentFA.addChild(this.flangeFAR);
        this.bodySegmentBB.addChild(this.legBBRB);
        this.bodySegmentBE.addChild(this.flangeBEL);
        this.bodySegmentBD.addChild(this.legBDLA);
        this.bodySegmentBE.addChild(this.legBERA);
        this.head.addChild(this.mandibleR);
        this.bodySegmentBA.addChild(this.bodySegmentBB);
        this.bodySegmentBF.addChild(this.flangeBack);
        this.bodySegmentFB.addChild(this.head);
        this.bodySegmentBD.addChild(this.bodySegmentBE);
        this.bodySegmentBC.addChild(this.legBCLA);
        this.bodySegmentBD.addChild(this.flangeBDL);
        this.bodySegmentBD.addChild(this.flangeBDR);
        this.bodySegmentFA.addChild(this.bodySegmentFB);
        this.bodySegmentBC.addChild(this.legBCLB);
        this.bodySegmentBE.addChild(this.flangeBER);
        this.bodySegmentBE.addChild(this.legBERB);
        this.bodySegmentBA.addChild(this.bodySegmentFA);
        this.bodySegmentBF.addChild(this.legBFRB);
        this.bodySegmentBC.addChild(this.legBCRB);
        this.bodySegmentBF.addChild(this.legBFLB);
        this.bodySegmentFA.addChild(this.flangeFAL);
        this.bodySegmentBF.addChild(this.flangeBFL_1);
        this.bodySegmentFA.addChild(this.legFALB);
        this.bodySegmentBF.addChild(this.legBFLA);
        this.bodySegmentFB.addChild(this.flangeFBL);
        this.bodySegmentFA.addChild(this.legFARA);
        this.antennaRA.addChild(this.antennaLB);
        this.bodySegmentBB.addChild(this.legBBRA);
        this.bodySegmentFA.addChild(this.legFARB);
        this.bodySegmentBE.addChild(this.legBELB);
        this.bodySegmentBA.addChild(this.legBARB);
        this.bodySegmentFB.addChild(this.flangeFBR);
        this.head.addChild(this.antennaRA);
        this.bodySegmentBC.addChild(this.bodySegmentBD);
        this.bodySegmentBA.addChild(this.flangeBAL);
        this.bodySegmentBA.addChild(this.legBALA);
        this.bodySegmentBB.addChild(this.bodySegmentBC);
        this.bodySegmentBA.addChild(this.legBALB);
        this.bodySegmentBC.addChild(this.legBCRA);
        this.bodySegmentBB.addChild(this.legBBLB);
        this.bodySegmentFB.addChild(this.legFBRB);
        this.antennaRA_1.addChild(this.antennaRB);
        this.bodySegmentBE.addChild(this.bodySegmentBF);
        this.head.addChild(this.antennaRA_1);
        this.bodySegmentFB.addChild(this.legFBRA);
        this.bodySegmentBC.addChild(this.flangeBCR);
        this.bodySegmentBB.addChild(this.flangeBBL);
        this.bodySegmentBB.addChild(this.legBBLA);
        this.bodySegmentFB.addChild(this.legFBLA);
        this.bodySegmentFB.addChild(this.legFBLB);
        this.bodySegmentBF.addChild(this.flangeBFL);
        this.bodySegmentBF.addChild(this.legBFRA);
        this.bodySegmentFA.addChild(this.legFALA);
        this.bodySegmentBD.addChild(this.legBDLB);
        this.bodySegmentBA.addChild(this.legBARA);
        this.bodySegmentBA.addChild(this.flangeBAR);
        this.bodySegmentBC.addChild(this.flangeBCL);
        this.updateDefaultPose();
        animator = ModelAnimator.create();
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        animate((IAnimatedEntity) entity, f, f1, f2, f3, f4, f5);
        this.bodySegmentBA.render(f5);
    }

    public void animate(IAnimatedEntity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        EntityPrehistoric prehistoric = (EntityPrehistoric) entity;
        animator.update(entity);
        blockMovement(f, f1, f2, f3, f4, f5, (Entity) entity);
        this.resetToDefaultPose();
        setRotationAngles(f, f1, f2, f3, f4, f5, prehistoric);
        animator.setAnimation(prehistoric.SPEAK_ANIMATION);
        animator.startKeyframe(10);
        ModelUtils.rotate(animator, mandibleL, 0, -40, 0);
        ModelUtils.rotate(animator, mandibleR, 0, 40, 0);
        animator.endKeyframe();
        animator.resetKeyframe(10);
        animator.setAnimation(prehistoric.ATTACK_ANIMATION);
        animator.startKeyframe(10);
        ModelUtils.rotate(animator, bodySegmentFA, -20, 0, 0);
        ModelUtils.rotate(animator, bodySegmentFB, -20, 0, 0);
        ModelUtils.rotate(animator, head, 40, 0, 0);
        ModelUtils.rotate(animator, mandibleL, 0, -40, 0);
        ModelUtils.rotate(animator, mandibleR, 0, 40, 0);
        animator.endKeyframe();
        animator.startKeyframe(5);
        ModelUtils.rotate(animator, bodySegmentFA, -20, 0, 0);
        ModelUtils.rotate(animator, bodySegmentFB, -20, 0, 0);
        ModelUtils.rotate(animator, head, 40, 0, 0);
        ModelUtils.rotate(animator, mandibleL, 0, 20, 0);
        ModelUtils.rotate(animator, mandibleR, 0, -20, 0);
        animator.endKeyframe();
        animator.resetKeyframe(10);
    }

    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
        EntityArthropleura prehistoric = (EntityArthropleura) entity;
        if (prehistoric.isSkeleton() || prehistoric.isAIDisabled()) {
            return;
        }
        AdvancedModelRenderer[] legsLeft = new AdvancedModelRenderer[]{legFBLA, legFBLB, legFALA, legFALB, legBALA,
                legBALB, legBBLA, legBBLB, legBCLA, legBCLB, legBDLA, legBDLB, legBELA, legBELB, legBFLA, legBFLB};
        AdvancedModelRenderer[] legsRight = new AdvancedModelRenderer[]{legFBRA, legFBRB, legFARA, legFARB, legBARA,
                legBARB, legBBRA, legBBRB, legBCRA, legBCRB, legBDRA, legBDRB, legBERA, legBERB, legBFRA, legBFRB};
        AdvancedModelRenderer[] bodySegments = new AdvancedModelRenderer[]{
                bodySegmentBB, bodySegmentBC, bodySegmentBD, bodySegmentBE, bodySegmentBF};
        prehistoric.bodyBuffer.applyChainSwingBuffer(false, bodySegments);
        float speed_idle = prehistoric.isSleeping() ? 0.2F : 0.5F;
        float speed_walk = 2.7F;
        float degree_idle = prehistoric.isSleeping() ? 0.2F : 0.3F;
        float degree_walk = 1.75F;
        ModelUtils.faceTargetMod(bodySegmentFA, f3, 0, 0.3F);
        ModelUtils.faceTargetMod(bodySegmentBA, f3, 0, 0.3F);
        ModelUtils.faceTargetMod(head, f3, 0, 0.3F);
        this.chainFlap(legsLeft, speed_walk, degree_walk * 0.35F, -10, f, f1);
        this.chainFlap(legsRight, speed_walk, degree_walk * 0.35F, 10, f, f1);
        this.chainWave(legsLeft, speed_walk, -degree_walk * 0.35F, -10, f, f1);
        this.chainWave(legsRight, speed_walk, -degree_walk * 0.35F, 10, f, f1);
        this.swing(mandibleL, speed_idle, degree_idle * 0.15F, true, 1, 0, f2, 1);
        this.swing(mandibleR, speed_idle, degree_idle * 0.15F, false, 1, 0, f2, 1);
        this.swing(antennaRA, speed_idle, degree_idle * 0.25F, true, 1, 0, f2, 1);
        this.swing(antennaRA_1, speed_idle, degree_idle * 0.25F, false, 1, 0, f2, 1);
        this.walk(antennaRA, speed_idle, degree_idle * 0.15F, true, 1, 0, f2, 1);
        this.walk(antennaRA_1, speed_idle, degree_idle * 0.15F, false, 1, 0, f2, 1);
        this.walk(antennaLB, speed_idle, degree_idle * 0.1F, true, 0, -0.1F, f2, 1);
        this.walk(antennaRB, speed_idle, degree_idle * 0.1F, false, 0, 0.1F, f2, 1);
        {
            float sitProgress = prehistoric.sleepProgress;
            for (int i = 0; i < bodySegments.length; i++) {
                sitAnimationRotation(bodySegments[i], sitProgress, 0.0F, (float) Math.toRadians(-20F), 0.0F);
            }
            sitAnimationRotation(bodySegmentFA, sitProgress, 0.0F, (float) Math.toRadians(20F), 0.0F);
            sitAnimationRotation(bodySegmentFB, sitProgress, 0.0F, (float) Math.toRadians(20F), 0.0F);
            sitAnimationRotation(head, sitProgress, (float) Math.toRadians(10F), (float) Math.toRadians(20F), (float) Math.toRadians(20F));
        }
        {
            float sitProgress = prehistoric.sitProgress;
            sitAnimationRotation(bodySegmentFA, sitProgress, (float) Math.toRadians(-30F), 0.0F, 0.0F);
            sitAnimationRotation(bodySegmentFB, sitProgress, (float) Math.toRadians(-30F), 0.0F, 0.0F);
            sitAnimationRotation(head, sitProgress, (float) Math.toRadians(50F), 0, 0F);

        }
    }
}