import no.brunostengine.GameObject;
import no.brunostengine.ResourcePool;
import no.brunostengine.components.BasePlayerController;
import no.brunostengine.components.Component;
import no.brunostengine.components.templates.MarioController;
import org.jbox2d.dynamics.contacts.Contact;
import org.joml.Vector2f;

public class Orb extends Component {
    private Vector2f topY;
    private float orbSpeed = 1.4f;
    private transient boolean playAnim = false;

    @Override
    public void onStart() {
        topY = new Vector2f(this.gameObject.transform.position.y).add(0, 0.5f);
    }

    @Override
    public void onUpdate(float dt) {
        if (playAnim) {
            if (this.gameObject.transform.position.y < topY.y) {
                this.gameObject.transform.position.y += dt * orbSpeed;
            } else {
                gameObject.destroy();
            }
        }
    }

    @Override
    public void preSolve(GameObject obj, Contact contact, Vector2f contactNormal) {
        if (obj.getComponent(BasePlayerController.class) != null) {
            ResourcePool.getSound("assets/sounds/coin.ogg").play();
            playAnim = true;
            contact.setEnabled(false);
        }
    }
}
