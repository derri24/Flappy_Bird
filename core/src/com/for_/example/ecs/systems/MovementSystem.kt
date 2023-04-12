package com.for_.example.ecs.systems

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Rectangle
import com.for_.example.ecs.components.BodyComponent
import com.for_.example.ecs.components.PositionComponent
import com.for_.example.ecs.components.VelocityComponent
import com.for_.example.ecs.notNull

class MovementSystem(
    private val screenRect: Rectangle
) : IteratingSystem(
    Family.all(
        BodyComponent::class.java,
        PositionComponent::class.java,
        VelocityComponent::class.java
    ).get()
) {

    private var wallIndex = 0;
    private var wallX = 1250f;
    private val bodyComponentMapper = ComponentMapper.getFor(BodyComponent::class.java)
    private val positionComponentMapper = ComponentMapper.getFor(PositionComponent::class.java)
    private val velocityComponentMapper = ComponentMapper.getFor(VelocityComponent::class.java)

    private var step = 0;
    private lateinit var birdBody: Rectangle;

    private var wallsSpeed: Float = -5f;

    override fun processEntity(entity: Entity?, deltaTime: Float) {
        notNull(
            positionComponentMapper[entity],
            velocityComponentMapper[entity],
            bodyComponentMapper[entity],
            deltaTime.coerceAtMost(0.25f),
            ::move
        )
    }

    private fun move(
        positionComponent: PositionComponent,
        velocityComponent: VelocityComponent,
        bodyComponent: BodyComponent,
        deltaTime: Float
    ) {


        if (bodyComponent.isBird) {
            birdBody = bodyComponent.rectangle;

            bodyComponent.rectangle.y -= velocityComponent.y;
            if (bodyComponent.rectangle.y <= 11) {
                velocityComponent.y = 0f;
            } else
                velocityComponent.y += 0.5f;
            if (!isBirdDead && Gdx.input.isTouched) {
                velocityComponent.y = -5f;
            }
        } else if (bodyComponent.isCoin || bodyComponent.isCoins || bodyComponent.isStar) {


                bodyComponent.rectangle.x += wallsSpeed;


            if (bodyComponent.rectangle.x <= -60) {
                bodyComponent.rectangle.y = 0f + (0..640).random();
                bodyComponent.rectangle.x = 1250f + (0..5000).random();
                bodyComponent.isHidden = false;
            }


            if (birdBody.overlaps(bodyComponent.rectangle) && !bodyComponent.isHidden) {

                bodyComponent.isHidden = true;
                if (bodyComponent.isCoin) {
                    score += 1;
                }
                if (bodyComponent.isCoins) {
                    score += 3;
                }
                if (bodyComponent.isStar) {
                    score += 5;
                }
            }
        } else {

            if (bodyComponent.rectangle.x <= -60) {
                if (bodyComponent.isUWall)
                    bodyComponent.rectangle.y = 450f;
                else
                    bodyComponent.rectangle.y = -350f;

                if (wallIndex == 0) {
                    step = (-150..150).random();
                }

                if (!isBirdDead) {
                    wallIndex++
                    bodyComponent.rectangle.y += step;
                    wallX = 1250f;
                    if (wallIndex >= 2)
                        wallIndex = 0;

                    if (bodyComponent.isUWall) {
                        score++;
                        wallsSpeed -= 0.5f;
                    }
                }

            }

            bodyComponent.rectangle.x = wallX;

            if (bodyComponent.isUWall)
                wallX += wallsSpeed;

            if (birdBody.overlaps(bodyComponent.rectangle)) {
                isBirdDead = true;
                Gdx.input.vibrate(500);
                wallsSpeed = -5f;
            }

        }

        if (bodyComponent.isBird && bodyComponent.rectangle.y <= 15f && Gdx.input.isTouched) {
            isBirdDead = false;
            score = 0;
        }


    }

    private fun hasHorizontalCollision(bodyComponent: BodyComponent) =
        bodyComponent.rectangle.x < screenRect.x ||
                (bodyComponent.rectangle.x + bodyComponent.rectangle.width) > (screenRect.x + screenRect.width)

    private fun hasVerticalCollision(bodyComponent: BodyComponent) =
        bodyComponent.rectangle.y < screenRect.y ||
                (bodyComponent.rectangle.y + bodyComponent.rectangle.height) > (screenRect.y + screenRect.height)


    companion object {
        var isBirdDead = false;
        var score = 0;
    }
}

