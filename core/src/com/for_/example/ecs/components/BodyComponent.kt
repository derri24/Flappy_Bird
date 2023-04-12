package com.for_.example.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.math.Rectangle

class BodyComponent : Component {
    val rectangle = Rectangle()

    var isUWall: Boolean = false;
    var isBird: Boolean = false;

    var isCoin = false;
    var isCoins = false;
    var isStar = false;

    var isHidden: Boolean=false;
    //var isFirstWall: Boolean = false;
}