import no.brunostengine.*;
import no.brunostengine.components.Collideable;
import no.brunostengine.components.KeyControls;
import no.brunostengine.components.NonInteractable;
import no.brunostengine.components.Spritesheet;
import no.brunostengine.components.cameras.FollowTargetCamera;
import no.brunostengine.components.debug_tools.DebugTools;
import no.brunostengine.components.debug_tools.GridLines;
import no.brunostengine.physics.components.BoxCollider;
import no.brunostengine.physics.components.Rigidbody;
import no.brunostengine.physics.enums.BodyType;
import no.brunostengine.scenes.Scene;
import no.brunostengine.scenes.SceneBuilder;
import org.joml.Vector2f;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Game game = Game.get();
        game.init("Hello World!");
        Game.changeScene(new SceneBuilder() {
            @Override
            public String assignTitleToScene() {
                return "hello_world";
            }

            @Override
            public void loadResources(Scene scene) {
                String orbSheet = "assets/images/orb-Sheet.png";
                String ratgirlSheet = "assets/images/RatGirlSpritesheet.png";
                String tileSheet = "assets/images/defaultTiles.png";
                ResourcePool.addSpritesheet(orbSheet,
                        new Spritesheet(ResourcePool.getTexture(orbSheet), 64, 64, 3, 0));
                ResourcePool.addSpritesheet(ratgirlSheet,
                        new Spritesheet(ResourcePool.getTexture(ratgirlSheet), 70, 75, 8, 0));
                ResourcePool.addSpritesheet(tileSheet,
                        new Spritesheet(ResourcePool.getTexture(tileSheet), 64, 64, 4, 1));
                ResourcePool.addSound("assets/sounds/coin.ogg", false);
            }

            @Override
            public void init(Scene scene) {
                Tilemap tilemap = Tilemap.generateTilemap(60, 60);
                Spritesheet tileSprites = ResourcePool.getSpritesheet("assets/images/defaultTiles.png");
                GameObject tile1 = AssetBuilder.generateSpriteObject(tileSprites.getSprite(1), 0.25f, 0.25f);
                tile1.addComponent(new Collideable());

                Rigidbody rb = new Rigidbody();
                rb.setBodyType(BodyType.Static);
                tile1.addComponent(rb);
                BoxCollider b2d = new BoxCollider();
                b2d.setHalfSize(new Vector2f(0.25f, 0.25f));
                tile1.addComponent(b2d);

                tilemap.setTilemapBackground(tileSprites.getSprite(2));
                tilemap.fillBorder(tile1);
                tilemap.addTilemapToScene();
                GameObject debuggingTools = scene.createGameObject("debuggingTools");

                debuggingTools.setNoSerialize();
                debuggingTools.addComponent(new DebugTools());
                debuggingTools.addComponent(new KeyControls());
                debuggingTools.addComponent(new GridLines());
                debuggingTools.addComponent(new FollowTargetCamera(scene.camera()));
                scene.addGameObjectToScene(debuggingTools);


                GameObject ratgirl = AssetCreator.generateRatgirl();
                ratgirl.transform.position = new Vector2f(2, 3);
                scene.addGameObjectToScene(ratgirl);

                debuggingTools.getComponent(DebugTools.class).gameObjectToPlace = AssetCreator.generateOrb();
            }
        });
        game.run();
    }
}
