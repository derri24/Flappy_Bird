package com.for_.example

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.utils.viewport.FitViewport
import com.for_.example.ecs.Engine
import com.for_.example.ecs.components.BodyComponent
import com.for_.example.ecs.components.PositionComponent
import com.for_.example.ecs.components.TextureComponent
import com.for_.example.ecs.components.VelocityComponent

class ForExampleGame : ApplicationAdapter() {
    private val screenRect = Rectangle(0f, 0f, WIDTH * 2, HEIGHT * 2)
    private val viewPort = FitViewport(WIDTH, HEIGHT)
    private val camera by lazy(viewPort::getCamera)
    private val engine by lazy {
        Engine(
            batch = SpriteBatch(),
            camera = camera,
            screenRect = screenRect
        )
    }

    override fun create() {
        (camera as? OrthographicCamera)?.zoom = 1f
        camera.position.x = WIDTH / 2f
        camera.position.y = HEIGHT / 2f
        engine.addEntity(createBird())
        engine.addEntity(createBackground())

        engine.addEntity(createDwallFirst())
        engine.addEntity(createUwallFirst())

        engine.addEntity(createCoin());
        engine.addEntity(createCoins());
        engine.addEntity(createStar());

    }


    override fun render() {
        Gdx.gl.glClearColor(0.5f, 0f, 0.2f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        engine.update(Gdx.graphics.deltaTime)
    }

    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
        viewPort.update(width, height)
        screenRect.width = viewPort.worldWidth
        screenRect.height = viewPort.worldHeight
    }

    override fun dispose() {
        engine.dispose()
    }

    private fun createUwallFirst() = engine.createEntity().also { entity ->
        entity.add(engine.createComponent(PositionComponent::class.java).apply {
            z = 0f
        })
        entity.add(engine.createComponent(BodyComponent::class.java).apply {
            isUWall = true;

            rectangle.setWidth(80f)
            rectangle.setHeight(600f)

            rectangle.setX(2000f);
            rectangle.setY(450f);
        })
        entity.add(engine.createComponent(TextureComponent::class.java).apply {
            texture = Texture("uwall.png")
        })

        entity.add(engine.createComponent(VelocityComponent::class.java).apply {
            x = WALL_SPEED
            y = 1f
        })
    }

    private fun createDwallFirst() = engine.createEntity().also { entity ->
        entity.add(engine.createComponent(PositionComponent::class.java).apply {
            z = 0f
        })
        entity.add(engine.createComponent(BodyComponent::class.java).apply {
            isUWall = false;

            rectangle.setWidth(80f)
            rectangle.setHeight(600f)

            rectangle.setY(-350f);
            rectangle.setX(2000f);
        })
        entity.add(engine.createComponent(TextureComponent::class.java).apply {
            texture = Texture("dwall.png")
        })
        entity.add(engine.createComponent(VelocityComponent::class.java).apply {
            x = WALL_SPEED
            y = 1f
        })
    }

    private fun createCoin() = engine.createEntity().also { entity ->
        entity.add(engine.createComponent(PositionComponent::class.java).apply {
            z = 0f
        })
        entity.add(engine.createComponent(BodyComponent::class.java).apply {
            isCoin = true;
            rectangle.setWidth(90f)
            rectangle.setHeight(90f)

            rectangle.setY(300f);
            rectangle.setX(600f);
        })
        entity.add(engine.createComponent(TextureComponent::class.java).apply {
            texture = Texture("coin.png")
        })
        entity.add(engine.createComponent(VelocityComponent::class.java).apply {

        })
    }

    private fun createCoins() = engine.createEntity().also { entity ->
        entity.add(engine.createComponent(PositionComponent::class.java).apply {
            z = 0f
        })
        entity.add(engine.createComponent(BodyComponent::class.java).apply {
            isCoins=true;
            rectangle.setWidth(120f)
            rectangle.setHeight(120f)

            rectangle.setY(300f);
            rectangle.setX(600f);
        })
        entity.add(engine.createComponent(TextureComponent::class.java).apply {
            texture = Texture("coins.png")
        })
        entity.add(engine.createComponent(VelocityComponent::class.java).apply {

        })
    }

    private fun createStar() = engine.createEntity().also { entity ->
        entity.add(engine.createComponent(PositionComponent::class.java).apply {
            z = 0f
        })
        entity.add(engine.createComponent(BodyComponent::class.java).apply {

            isStar=true;
            rectangle.setWidth(90f)
            rectangle.setHeight(90f)

            rectangle.setY(300f);
            rectangle.setX(600f);
        })
        entity.add(engine.createComponent(TextureComponent::class.java).apply {
            texture = Texture("star.png")
        })
        entity.add(engine.createComponent(VelocityComponent::class.java).apply {

        })
    }


    private fun createBackground() = engine.createEntity().also { entity ->
        entity.add(engine.createComponent(PositionComponent::class.java).apply {
            z = 0f
        })
        entity.add(engine.createComponent(BodyComponent::class.java).apply {
            rectangle.setWidth(WIDTH)
            rectangle.setHeight(HEIGHT)
        })
        entity.add(engine.createComponent(TextureComponent::class.java).apply {
            texture = Texture("background.jpg")
        })
    }

    private fun createBird() = engine.createEntity().also { entity ->
        entity.add(engine.createComponent(PositionComponent::class.java).apply {
            z = 1f
        })
        entity.add(engine.createComponent(BodyComponent::class.java).apply {
            isBird = true;

            rectangle.setWidth(65f)
            rectangle.setHeight(55f)

            rectangle.setY(150f);
            rectangle.setX(20f)
        })
        entity.add(engine.createComponent(TextureComponent::class.java).apply {
            texture = Texture("bird.png")
        })
        entity.add(engine.createComponent(VelocityComponent::class.java).apply {
            y = 1f;
        })
    }

    companion object {
        private const val WIDTH = 1250f
        private const val HEIGHT = 640f

        private const val WALL_SPEED = -8f;
    }
}