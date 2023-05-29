import no.brunostengine.*;
import no.brunostengine.GameObject;
import no.brunostengine.ResourcePool;
import no.brunostengine.components.Animation;
import no.brunostengine.components.Animator;
import no.brunostengine.components.DefaultSideScrollerPlayerController;
import no.brunostengine.components.Spritesheet;
import no.brunostengine.components.templates.Coin;
import no.brunostengine.physics.components.CapsuleCollider;
import no.brunostengine.physics.components.CircleCollider;
import no.brunostengine.physics.components.Rigidbody;
import no.brunostengine.physics.enums.BodyType;

public class AssetCreator {
    public static GameObject generateRatgirl() {
        Spritesheet playerSprites = ResourcePool.getSpritesheet("assets/images/RatGirlSpritesheet.png");
        GameObject ratgirl = AssetBuilder.generateSpriteObject(playerSprites.getSprite(0), 0.25f, 0.25f);

        Animation run = new Animation();
        run.title = "Run";
        float defaultFrameTime = 0.1f;
        run.addFrame(playerSprites.getSprite(2), defaultFrameTime);
        run.addFrame(playerSprites.getSprite(3), defaultFrameTime);
        run.addFrame(playerSprites.getSprite(4), defaultFrameTime);
        run.addFrame(playerSprites.getSprite(5), defaultFrameTime);
        run.addFrame(playerSprites.getSprite(6), defaultFrameTime);
        run.addFrame(playerSprites.getSprite(7), defaultFrameTime);
        run.setLoop(true);

        Animation switchDirection = new Animation();
        switchDirection.title = "Switch Direction";
        switchDirection.addFrame(playerSprites.getSprite(3), 0.1f);
        switchDirection.setLoop(false);

        Animation idle = new Animation();
        idle.title = "Idle";
        idle.addFrame(playerSprites.getSprite(0), 2f);
        idle.addFrame(playerSprites.getSprite(1), 0.1f);
        idle.setLoop(true);

        Animation jump = new Animation();
        jump.title = "Jump";
        jump.addFrame(playerSprites.getSprite(5), 0.1f);
        jump.setLoop(false);


        Animation die = new Animation();
        die.title = "Die";
        die.addFrame(playerSprites.getSprite(6), 0.1f);
        die.setLoop(false);

        Animator animator = new Animator();
        animator.addState(run);
        animator.addState(idle);
        animator.addState(switchDirection);
        animator.addState(jump);
        animator.addState(die);

        animator.setDefaultState(idle.title);
        animator.addState(run.title, switchDirection.title, "switchDirection");
        animator.addState(run.title, idle.title, "stopRunning");
        animator.addState(run.title, jump.title, "jump");
        animator.addState(switchDirection.title, idle.title, "stopRunning");
        animator.addState(switchDirection.title, run.title, "startRunning");
        animator.addState(switchDirection.title, jump.title, "jump");
        animator.addState(idle.title, run.title, "startRunning");
        animator.addState(idle.title, jump.title, "jump");
        animator.addState(jump.title, idle.title, "stopJumping");

        animator.addState(run.title, die.title, "die");
        animator.addState(switchDirection.title, die.title, "die");
        animator.addState(idle.title, die.title, "die");
        animator.addState(jump.title, die.title, "die");
        ratgirl.addComponent(animator);

        CapsuleCollider pb = new CapsuleCollider();
        pb.width = 0.21f;
        pb.height = 0.25f;
        ratgirl.addComponent(pb);

        Rigidbody rb = new Rigidbody();
        rb.setBodyType(BodyType.Dynamic);
        rb.setContinuousCollision(false);
        rb.setFixedRotation(true);
        rb.setMass(25.0f);
        ratgirl.addComponent(rb);

        ratgirl.addComponent(new DefaultSideScrollerPlayerController());

        ratgirl.transform.zIndex = 10;

        return ratgirl;
    }


    public static GameObject generateOrb() {
        Spritesheet items = ResourcePool.getSpritesheet("assets/images/orb-Sheet.png");
        GameObject shinyOrb = AssetBuilder.generateSpriteObject(items.getSprite(0), 0.25f, 0.25f);

        // Create Shine animation
        Animation orbShine = new Animation();
        orbShine.title = "CoinFlip";
        float defaultFrameTime = 0.03f;
        orbShine.addFrame(items.getSprite(0), 2f);
        orbShine.addFrame(items.getSprite(1), defaultFrameTime);
        orbShine.addFrame(items.getSprite(2), defaultFrameTime);
        orbShine.addFrame(items.getSprite(1), defaultFrameTime);
        orbShine.setLoop(true);

        Animator animator = new Animator();
        animator.addState(orbShine);
        animator.setDefaultState(orbShine.title);
        shinyOrb.addComponent(animator);
        shinyOrb.addComponent(new Orb()); // TODO: replace with custom class

        CircleCollider circleCollider = new CircleCollider();
        circleCollider.setRadius(0.12f);
        shinyOrb.addComponent(circleCollider);
        Rigidbody rb = new Rigidbody();
        rb.setBodyType(BodyType.Dynamic);
        shinyOrb.addComponent(rb);

        return shinyOrb;
    }


}
