package r2dx.stg.element;

import r2d.element.vector.ElementMove;
import r2d.render.desktop.Canvas;
import r2dx.stg.CanvasSTG;
import r2dx.stg.action.EnemyAct;
import rutil.container.Array;
import rutil.maths.ToolMath;
import rwt.device.texture.Texture;

/**
 * 构造器：inset
 * 超类：inset
 */
public class ElementEnemy extends ElementMove {

    int health;
    int maxHealth;
    double resistance;

    public Array<EnemyAct> actions = new Array<>(20);
    int actionIndex;
    boolean canRunAction = true;

    ElementEnemy parent;
    public Array<ElementEnemy> controls = new Array<>();

    public ElementEnemy(int width, int height, double speed, double angle, int startHealth, Texture texture) {

        super(width, height, speed, angle, texture);

        health = startHealth;
        maxHealth = startHealth;

    }

    public void tick(Canvas canvas) {

        super.tick(canvas);

        EnemyAct ac = getNowAction();

        if(ac != null && canRunAction) {
            if(!ac.init) {
                ac.init(this);//初始化在此调用
                ac.init = true;
            }
            ac.update(this);
        }

    }

    public void clearControls(CanvasSTG canvas) {

        ElementEnemy e;

        for(int i = canvas.enemies.size(); i >= 0; i--) {
            e = canvas.enemies.get(i);
            if(controls.contains(e)) {
                canvas.enemies.remove(i);
            }
        }
        controls.clear();

    }

    public void addControl(ElementEnemy e) {

        e.parent = this;
        controls.add(e);

    }

    public void removeFromParent() {

        if(parent != null) {
            parent.controls.remove(this);
            parent = null;
        }

    }

    public boolean isControlled() {

        return parent != null;

    }

    public ElementEnemy getParent() {

        return parent;

    }

    public void setResistance(double resistance) {

        this.resistance = resistance;

    }

    public void setHealth(int health) {

        this.health = health;

    }

    public void setMaxHealth(int maxHealth) {

        this.maxHealth = maxHealth;

    }

    public void reHealth(int value) {

        health = ToolMath.ofInt(health, value, maxHealth);

    }

    public void setCanRunAction(boolean canRun) {

        canRunAction = canRun;

    }

    public void setAction(EnemyAct action) {

        actions.set(0, action);

    }

    public void nextAction() {

        actionIndex++;

    }

    public void setActionIndex(int index) {

        actionIndex = index;

    }

    public int getHealth() {

        return health;

    }

    public int getMaxHealth() {

        return maxHealth;

    }

    public void hit(int value) {

        health -= value * (1 - resistance);

    }

    public boolean isDead() {

        return health <= 0;

    }

    public boolean isBoss() {

        return false;

    }

    public boolean canRunAction() {

        return canRunAction;

    }

    public EnemyAct getNowAction() {

        return actions.get(actionIndex);

    }

    public int getActionIndex() {

        return actionIndex;

    }

}
