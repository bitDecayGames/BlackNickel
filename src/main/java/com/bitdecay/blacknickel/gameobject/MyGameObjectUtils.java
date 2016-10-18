package com.bitdecay.blacknickel.gameobject;

import com.bitdecay.blacknickel.component.PositionComponent;
import com.bitdecay.blacknickel.component.SizeComponent;

public class MyGameObjectUtils {
    private MyGameObjectUtils(){}

    public static boolean overlap(MyGameObject a, MyGameObject b){
        return a.getComponent(SizeComponent.class).flatMap(sizeA -> a.getComponent(PositionComponent.class).flatMap(posA -> b.getComponent(SizeComponent.class).flatMap(sizeB -> b.getComponent(PositionComponent.class).map(posB -> overlap(posA, sizeA, posB, sizeB))))).orElse(false);
    }

    public static boolean overlap(PositionComponent aPos, SizeComponent aSize, PositionComponent bPos, SizeComponent bSize){
        return overlap(aPos.x, aPos.y, aSize.w, aSize.h, bPos.x, bPos.y, bSize.w, bSize.h);
    }

    public static boolean overlap(float aX, float aY, float aW, float aH, float bX, float bY, float bW, float bH){
        float aTop = aY + aH;
        float aBot = aY;
        float aLeft = aX;
        float aRight = aX + aW;

        float bTop = bY + bH;
        float bBot = bY;
        float bLeft = bX;
        float bRight = bX + bW;

        return aLeft < bRight && aRight > bLeft && aTop > bBot && aBot < bTop;
    }
}
