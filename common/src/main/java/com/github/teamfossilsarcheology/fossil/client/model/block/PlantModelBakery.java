package com.github.teamfossilsarcheology.fossil.client.model.block;

import com.mojang.math.Transformation;
import net.minecraft.client.renderer.FaceInfo;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import org.joml.Vector3f;
import org.joml.Vector4f;

/**
 * @see PlantBlockModel
 */
public class PlantModelBakery {

    public static BakedQuad bakeFace(PlantBlockModel.PlantBlockElement part, PlantBlockModel.PlantBlockElementFace partFace, TextureAtlasSprite sprite, Direction direction, ModelState modelState) {
        return bakeQuad(part.from(), part.to(), partFace, sprite, direction, part.rotations(), part.origin(), modelState);
    }

    private static BakedQuad bakeQuad(Vector3f posFrom, Vector3f posTo, PlantBlockModel.PlantBlockElementFace face, TextureAtlasSprite sprite, Direction facing, Vector3f rotations, Vector3f origin, ModelState modelState) {
        PlantBlockModel.PlantBlockFaceUV blockFaceUV = face.uv();
        float[] tempUvs = new float[blockFaceUV.uvs().length];
        System.arraycopy(blockFaceUV.uvs(), 0, tempUvs, 0, tempUvs.length);
        float f = sprite.uvShrinkRatio();
        float g = (blockFaceUV.uvs()[0] + blockFaceUV.uvs()[0] + blockFaceUV.uvs()[2] + blockFaceUV.uvs()[2]) / 4;
        float h = (blockFaceUV.uvs()[1] + blockFaceUV.uvs()[1] + blockFaceUV.uvs()[3] + blockFaceUV.uvs()[3]) / 4;
        blockFaceUV.uvs()[0] = Mth.lerp(f, blockFaceUV.uvs()[0], g);
        blockFaceUV.uvs()[2] = Mth.lerp(f, blockFaceUV.uvs()[2], g);
        blockFaceUV.uvs()[1] = Mth.lerp(f, blockFaceUV.uvs()[1], h);
        blockFaceUV.uvs()[3] = Mth.lerp(f, blockFaceUV.uvs()[3], h);
        int[] vertices = makeVertices(blockFaceUV, sprite, facing, setupShape(posFrom, posTo), rotations, origin, modelState);
        Direction direction = calculateFacing(vertices);
        System.arraycopy(tempUvs, 0, blockFaceUV.uvs(), 0, tempUvs.length);
        return new BakedQuad(vertices, -1, direction, sprite, false);
    }

    private static int[] makeVertices(PlantBlockModel.PlantBlockFaceUV uvs, TextureAtlasSprite sprite, Direction orientation, float[] posDiv16, Vector3f rotations, Vector3f origin, ModelState modelState) {
        int[] is = new int[32];
        for (int i = 0; i < 4; ++i) {
            bakeVertex(is, i, orientation, uvs, posDiv16, sprite, rotations, origin, modelState);
        }
        return is;
    }

    private static float[] setupShape(Vector3f pos1, Vector3f pos2) {
        float[] fs = new float[Direction.values().length];
        fs[FaceInfo.Constants.MIN_X] = pos1.x() / 16;
        fs[FaceInfo.Constants.MIN_Y] = pos1.y() / 16;
        fs[FaceInfo.Constants.MIN_Z] = pos1.z() / 16;
        fs[FaceInfo.Constants.MAX_X] = pos2.x() / 16;
        fs[FaceInfo.Constants.MAX_Y] = pos2.y() / 16;
        fs[FaceInfo.Constants.MAX_Z] = pos2.z() / 16;
        return fs;
    }

    private static void bakeVertex(int[] vertexData, int vertexIndex, Direction facing, PlantBlockModel.PlantBlockFaceUV blockFaceUV, float[] posDiv16, TextureAtlasSprite sprite, Vector3f rotations, Vector3f origin, ModelState modelState) {
        FaceInfo.VertexInfo vertexInfo = FaceInfo.fromFacing(facing).getVertexInfo(vertexIndex);
        Vector3f vector3f = new Vector3f(posDiv16[vertexInfo.xFace], posDiv16[vertexInfo.yFace], posDiv16[vertexInfo.zFace]);
        applyElementRotation(vector3f, rotations, origin);
        applyModelRotation(vector3f, modelState.getRotation());
        fillVertex(vertexData, vertexIndex, vector3f, sprite, blockFaceUV);
    }

    private static void fillVertex(int[] vertexData, int vertexIndex, Vector3f vector, TextureAtlasSprite sprite, PlantBlockModel.PlantBlockFaceUV blockFaceUV) {
        int i = vertexIndex * 8;
        vertexData[i] = Float.floatToRawIntBits(vector.x());
        vertexData[i + 1] = Float.floatToRawIntBits(vector.y());
        vertexData[i + 2] = Float.floatToRawIntBits(vector.z());
        vertexData[i + 3] = -1;
        vertexData[i + 4] = Float.floatToRawIntBits(sprite.getU(blockFaceUV.getU(vertexIndex)));
        vertexData[i + 4 + 1] = Float.floatToRawIntBits(sprite.getV(blockFaceUV.getV(vertexIndex)));
    }


    private static void applyElementRotation(Vector3f pos, Vector3f rotations, Vector3f origin) {
        Vector4f temp = new Vector4f(pos.x() - origin.x(), pos.y() - origin.y(), pos.z() - origin.z(), 1);
        if (rotations.x() != 0) {
            temp.rotateX(rotations.x() * Mth.DEG_TO_RAD);
        }
        if (rotations.y() != 0) {
            temp.rotateY(rotations.y() * Mth.DEG_TO_RAD);
        }
        if (rotations.z() != 0) {
            temp.rotateZ(rotations.z() * Mth.DEG_TO_RAD);
        }
        pos.set(temp.x() + origin.x(), temp.y() + origin.y(), temp.z() + origin.z());
    }

    private static void applyModelRotation(Vector3f pos, Transformation transform) {
        if (transform == Transformation.identity()) {
            return;
        }
        Vector3f origin = new Vector3f(0.5f, 0.5f, 0.5f);
        Vector4f vector4f = new Vector4f(pos.x() - origin.x(), pos.y() - origin.y(), pos.z() - origin.z(), 1);
        transform.getMatrix().transform(vector4f);
        pos.set(vector4f.x() + origin.x(), vector4f.y() + origin.y(), vector4f.z() + origin.z());
    }

    private static Direction calculateFacing(int[] faceData) {
        Vector3f vector3f = new Vector3f(Float.intBitsToFloat(faceData[0]), Float.intBitsToFloat(faceData[1]), Float.intBitsToFloat(faceData[2]));
        Vector3f vector3f2 = new Vector3f(Float.intBitsToFloat(faceData[8]), Float.intBitsToFloat(faceData[9]), Float.intBitsToFloat(faceData[10]));
        Vector3f vector3f3 = new Vector3f(Float.intBitsToFloat(faceData[16]), Float.intBitsToFloat(faceData[17]), Float.intBitsToFloat(faceData[18]));
        Vector3f vector3f4 = new Vector3f(vector3f);
        vector3f4.sub(vector3f2);
        Vector3f vector3f5 = new Vector3f(vector3f3);
        vector3f5.sub(vector3f2);
        Vector3f vector3f6 = new Vector3f(vector3f5);
        vector3f6.cross(vector3f4);
        vector3f6.normalize();
        Direction direction = null;
        float f = 0;
        for (Direction direction2 : Direction.values()) {
            Vector3f vector3f7 = new Vector3f(direction2.getStepX(), direction2.getStepY(), direction2.getStepZ());
            float g = vector3f6.dot(vector3f7);
            if (!(g >= 0) || !(g > f)) continue;
            f = g;
            direction = direction2;
        }
        if (direction == null) {
            return Direction.UP;
        }
        return direction;
    }
}
