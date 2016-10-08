package com.bitdecay.blacknickel.gameobject;

import com.bitdecay.blacknickel.component.PositionComponent;
import com.bitdecay.blacknickel.component.SizeComponent;

public class MyGameObjectUtils {
    private MyGameObjectUtils(){}

    public static boolean overlap(MyGameObject a, MyGameObject b){
        return a.getComponent(SizeComponent.class).flatMap(sizeA -> a.getComponent(PositionComponent.class).flatMap(posA -> b.getComponent(SizeComponent.class).flatMap(sizeB -> b.getComponent(PositionComponent.class).map(posB -> {
            float aTop = posA.y + sizeA.h;
            float aBot = posA.y;
            float aLeft = posA.x;
            float aRight = posA.x + sizeA.w;

            float bTop = posB.y + sizeB.h;
            float bBot = posB.y;
            float bLeft = posB.x;
            float bRight = posB.x + sizeB.w;

            return aLeft < bRight && aRight > bLeft && aTop > bBot && aBot < bTop;
        })))).orElse(false);
    }
}
