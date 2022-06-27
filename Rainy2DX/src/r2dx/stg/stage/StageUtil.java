package r2dx.stg.stage;

import r2d.element.Element;
import r2d.element.action.ElementProgressBar;
import r2d.element.vector.ElementEffect;
import r2d.element.vector.ElementVector;
import r2d.factory.ElementFactory;
import r2d.render.manager.FieldListener;
import r2d.vector.Vector;
import r2dx.stg.CanvasSTG;
import r2dx.stg.action.BulletAct;
import r2dx.stg.action.EnemyAct;
import r2dx.stg.display.Dialog;
import r2dx.stg.element.*;
import r2dx.stg.factory.STGFactory;
import rutil.container.Array;
import rutil.maths.Maths;
import rutil.shape.Direction;
import rwt.device.sound.SoundDevice;
import rwt.device.graphic.Draw;
import rwt.device.graphic.NumberFont;
import rwt.device.texture.TexSystem;
import rwt.device.texture.Texture;

/**
 * Stage大部分的操作包装在这个类中
 */
public class StageUtil extends FieldListener {

    public CanvasSTG canvas;

    public String defaultBulletSound;

    public Texture bulletEffect;//继承后加图！
    public Texture enemyEffect;

    private int difficulty;

    public Dialog dialog;//当前对话

    public boolean isDialogShowing;//检测是否有对话打开

    public StageUtil(CanvasSTG c) {

        super(c);

        canvas = c;

    }

    public void setDifficulty(int diff) {

        difficulty = diff;

    }

    public int getDifficulty() {

        return difficulty;

    }

    /**
     * 添加一个敌人
     * @param e 敌人模板
     * @param appearPercent 出现位置在field一条边上的百分比
     * @param direction 出现方向
     * @return 生成的新敌人
     */
    public ElementEnemy addEnemy(ElementEnemy e, double appearPercent,
                                 double speed, double angle, int direction, EnemyAct act) {

        ElementEnemy enemy = STGFactory.newEnemy(e);

        enemy.setAngle(angle);
        enemy.setSpeed(speed);
        enemy.setAction(act);

        if(direction == Direction.LEFT) {
            enemy.locate(left - enemy.getWidth() / 2, field.getY(appearPercent));
        }
        else if(direction == Direction.RIGHT) {
            enemy.locate(right + enemy.getWidth() / 2, field.getY(appearPercent));
        }
        else if(direction == Direction.UP) {
            enemy.locate(field.getX(appearPercent), top - enemy.getHeight() / 2);
        }
        else if(direction == Direction.DOWN) {
            enemy.locate(field.getX(appearPercent), bottom + enemy.getHeight() / 2);
        }

        canvas.enemies.add(enemy);

        return enemy;

    }

    public ElementEnemy addEnemy(ElementEnemy e, double x, double y,
                                 double speed, double angle, EnemyAct act) {

        ElementEnemy enemy = STGFactory.newEnemy(e);

        enemy.locate(x, y);
        enemy.setAngle(angle);
        enemy.setSpeed(speed);
        enemy.setAction(act);

        canvas.enemies.add(enemy);
        addEnemyClearEffect(enemy);

        return enemy;

    }

    public ElementEnemy addCollEnemy(ElementEnemy parent, ElementEnemy e, double x, double y,
                                     double speed, double angle, EnemyAct act) {

        ElementEnemy enemy = addEnemy(e, x, y, speed, angle, act);
        parent.addControl(enemy);

        return enemy;

    }

    public void addCollEnemies(ElementEnemy parent, ElementEnemy e,
                               int distance, int space, int number, int rings, EnemyAct act) {

        addCollEnemies(parent, e, parent.getX(), parent.getY(), distance, space, number, rings, act);

    }

    public void addCollEnemies(ElementEnemy parent, ElementEnemy e, double x, double y,
                               int distance, int space, int number, int rings, EnemyAct act) {

        addCollEnemiesArc(parent, e, x, y, 360, 0, distance, space, number, rings, act);

    }

    public void addCollEnemiesArc(ElementEnemy parent, ElementEnemy e, double x, double y,
                                  int arc, int face, int distance, int space, int number, int rings, EnemyAct act) {

        double eachRing = number / rings;
        double eachAngle = arc / eachRing;
        double add = 0;
        if(eachRing % 2 == 0) {
            add = eachAngle / 2;//如果每一环是双数个就加一半eachAngle对称
        }

        for(int i = 0; i < rings; i++) {
            for(double j = -eachRing / 2; j < eachRing / 2; j++) {
                double a = eachAngle * j + face + add;
                addCollEnemy(
                        parent,
                        e,
                        Vector.vectorX(x, i * space + distance, a),
                        Vector.vectorY(y, i * space + distance, a),
                        0,
                        0,
                        act
                );
            }
        }

    }

    public void collEnemiesRotateBy(ElementEnemy parent, double x, double y, double distance, int space, int rings, double speed) {

        int size = parent.controls.size();

        if(size > 0) {
            int eachRing = size / rings;
            int eachAngle = 360 / eachRing;
            double rotateBy = speed * parent.timer;
            int index = 0;

            for(int i = 0; i < rings; i++) {
                for(int j = 0; j < eachRing; j++) {
                    parent.controls.get(index).locate
                            (Vector.vectorX(x, i * space + distance, eachAngle * j + rotateBy),
                            Vector.vectorY(y, i * space + distance, eachAngle * j + rotateBy));
                    index++;
                }
            }
        }

    }

    public void collEnemiesRotateBy(ElementEnemy parent, ElementVector target, double distance, int space, int rings, double speed) {

        collEnemiesRotateBy(parent, target.getX(), target.getY(), distance, space, rings, speed);

    }

    /**
     * 敌方攻击
     * @param e 遍历得到一个敌人
     * @param b 子弹模板（切记要使用模板而不是new对象，否则会极大影响性能）
     * @param angle 初始角度
     * @param canBeRotated 是否允许子弹的图片旋转
     * @return 返回发射出去的子弹，可以进一步操控
     */
    private ElementBullet shootBase(Element e, ElementBullet b, int color,
                               double speed, double angle, boolean canBeRotated, BulletAct act) {

        return shootBase(e, e.getX(), e.getY(), b, color, speed, angle, canBeRotated, act);

    }

    private ElementBullet shootBase(Element e, double x, double y, ElementBullet b, int color,
                               double speed, double angle, boolean canBeRotated, BulletAct act) {

        if(e.checkOutWindow(canvas, 0)) {
            return STGFactory.newBullet(b);
        }

        //复制一份子弹并设置信息
        ElementBullet bullet = STGFactory.newBullet(b);

        bullet.setParent(e);
        bullet.locate(x, y);
        bullet.setSpeed(speed);
        bullet.setAngle(angle);
        bullet.setState(ElementBullet.HIT_PLAYER);
        bullet.dye(color);
        bullet.rotateState(canBeRotated, true);
        bullet.setAction(act);
        //添加
        canvas.bullets.add(bullet);

        return bullet;

    }

    //run once on every method, or it will generate too many effects
    private void additionEffect(ElementBullet b) {

        addBulletEffect(b);
        SoundDevice.play(defaultBulletSound);

    }

    /** with effect and sound **/
    public ElementBullet shoot(Element e, ElementBullet b, int color,
                                    double speed, double angle, boolean canBeRotated, BulletAct act) {

        return shoot(e, e.getX(), e.getY(), b, color, speed, angle, canBeRotated, act);

    }

    public ElementBullet shoot(Element e, double x, double y, ElementBullet b, int color,
                                    double speed, double angle, boolean canBeRotated, BulletAct act) {

        ElementBullet bullet = shootBase(e, x, y, b, color, speed, angle, canBeRotated, act);

        additionEffect(bullet);

        return bullet;

    }

    /** 网状自机狙 */
    public void direShoot(Element e, double x, double y, ElementBullet b, int color,
                          double speed, double angle, int size, int space, boolean canBeRotated, BulletAct act) {

        double half = size / 2;

        if(size % 2 == 0) {
            half += 0.5;
        }

        ElementBullet bullet = null;

        for(double i = -half; i < half; i++) {
            bullet = shootBase(e, x, y, b, color, speed, angle + (i + 0.5) * space, canBeRotated, act);
        }

        additionEffect(bullet);

    }

    public void direShoot(Element e, ElementBullet b, int color,
                          double speed, double angle, int size, int space, boolean canBeRotated, BulletAct act) {

        direShoot(e, e.getX(), e.getY(), b, color, speed, angle, size, space, canBeRotated, act);

    }

    public void ringShoot(Element e, double x, double y, ElementBullet b, int color,
                          double speed, double angle, int value, boolean canBeRotated, BulletAct act) {

        ringShoot(e, x, y, 0, b, color, speed, angle, value, canBeRotated, act);

    }

    public void ringShoot(Element e, double x, double y, double out, ElementBullet b, int color,
                               double speed, double angle, int value, boolean canBeRotated, BulletAct act) {

        ElementBullet bullet = null;

        for(int i = 0; i < value; i++) {
            double a = 360.0 / value * i + angle;
            double bx = Vector.vectorX(x, out, a);
            double by = Vector.vectorY(y, out, a);
            bullet = shootBase(e, bx, by, b, color, speed, a, canBeRotated, act);
        }

        additionEffect(bullet);

    }

    public void ringShoot(Element e, double out, ElementBullet b, int color,
                          double speed, double angle, int value, boolean canBeRotated, BulletAct act) {

        ringShoot(e, e.getX(), e.getY(), out, b, color, speed, angle, value, canBeRotated, act);

    }

    public void ringShoot(Element e, ElementBullet b, int color,
                          double speed, double angle, int value, boolean canBeRotated, BulletAct act) {

        ringShoot(e, e.getX(), e.getY(), b, color, speed, angle, value, canBeRotated, act);

    }

    public void playerShoot(ElementPlayer e, ElementBullet b,
                            double speed, double angle, boolean canBeRotated, BulletAct act) {

        playerShoot(e, e.getX(), e.getY(), b, speed, angle, canBeRotated, act);

    }

    public void playerShoot(ElementPlayer e, double x, double y, ElementBullet b,
                            double speed, double angle, boolean canBeRotated, BulletAct act) {

        SoundDevice.play(defaultBulletSound);

        ElementBullet bullet = STGFactory.newBullet(b);

        bullet.setParent(e);
        bullet.locate(x, y);
        bullet.setSpeed(speed);
        bullet.setAngle(angle);
        bullet.setState(ElementBullet.HIT_ENEMY);
        bullet.rotateState(canBeRotated, true);
        bullet.setAction(act);

        canvas.bullets.add(bullet);

    }

    public void addItem(double x, double y, ElementItem i) {

        ElementItem item = STGFactory.newItem(i);

        item.locate(x, y);
        item.setGravity(false);

        canvas.items.add(item);

    }

    public void addItem(ElementVector e, ElementItem i) {

        addItem(e.getX(), e.getY(), i);

    }

    public void addItems(ElementVector e, ElementItem i, int number) {

        addItems(e.getX(), e.getY(), i, number);

    }

    public void addItems(double x, double y, ElementItem i, int number) {

        int dis = i.getWidth() * 2;

        for(int j = 0; j < number; j++) {
            addItem(x + Maths.random(-dis, dis), y + Maths.random(-dis, dis), i);
        }

    }

    public void addBulletEffect(double x, double y, int size, int crease, int sizeLeast, int color) {

        addEffect(x, y, 0, 0, size, crease, sizeLeast, 0, TexSystem.cutLine(10, color, bulletEffect));

    }

    public void addBulletEffect(ElementBullet b) {

        addBulletEffect(b.getX(), b.getY(), b.getWidth() * 2, -5, b.getWidth(), b.getColor());

    }

    public void addBulletClearEffect(ElementBullet b) {

        addBulletEffect(b.getX(), b.getY(), b.getWidth() * 2, -3, 0, b.getColor());

    }

    public void addEnemyClearEffect(ElementEnemy e) {

        addEffect(e.getX(), e.getY(), 0, 0, e.getWidth(), 5, 0, -0.05, enemyEffect);

    }

    public ElementEffect addEffect(double x, double y, double speed, double angle, int size, int crease, int sizeLeast, double tran, Texture texture) {

        ElementEffect effect = ElementFactory.newEffect(x, y, size, size, speed, angle, texture);

        effect.setTranCrease(tran);
        effect.rotateState(true, true);
        effect.setLeastSize(sizeLeast);
        effect.setSizeCrease(crease);

        canvas.effects.add(effect);

        return effect;

    }

    /** CONVERSATION ACTIONS */

    public Dialog getDialogNow() {

        return dialog;

    }

    public boolean isDialogShowing() {

        return isDialogShowing;

    }

    public void setDialog(Dialog c) {

        if(dialog != c) {
            dialog = c;
            clear(false, -1);
        }

    }

    private boolean checkBossLocForDialog() {

        if(bossNow != null) {
            return bossNow.located;
        }

        return true;

    }

    public void dialogTick(ElementPlayer player, int speed, int downTime) {

        if(dialog != null) {
            dialog.callTimer();
            dialog.tick();

            if(checkBossLocForDialog()) {//if boss hasn't located, can't next
                if(player.zTime > downTime && player.zTime < dialog.fullTimer && canvas.forTick(speed)) {
                    //按键时间足够且不超过对话存在时间，即是在对话开始之后按的
                    dialog.next();
                }
                if(player.sure) {
                    dialog.next();
                }
            }

            isDialogShowing = dialog.isStart() && dialog.notEnd();
        }
        else {
            isDialogShowing = false;
        }

    }

    //自动渲染对话
    public void dialogRender() {

        if(dialog != null) {
            if(dialog.notEnd()) {
                dialog.render();
            }
        }

    }

    /** BOSS ACTIONS */

    private ElementBoss bossNow;

    /**
     * USE on Stage logic for checking a boss state is on.
     * ex:
     * if(timer > 100 & util.bossNotEnd(resource.boss1)) {do sth...}
      */
    public boolean bossNotEnd(ElementBoss boss) {

        return !boss.out;
        //boss没完全出画面视为未完，不会进入下一个分支，同时避免了添加boss失败
        //检测enemies.contains(boss)会导致不能添加

    }

    /**
     * @return attack End -> true
     */
    private boolean bossAttack(ElementBoss boss, double x, double y) {

        boss.ended = (boss.getNowAction() == null);

        if(boss.ended) {
            return true;
        }

        if(boss.getNowAction().isEnd || boss.isDead()) {
            boss.timer = -100;//与wait100相适应
            boss.waiter.wait(100);

            clear(true, -1);
            boss.clearControls(canvas);//清除使魔

            boss.nextAction();
            boss.move(x, y);//移动到默认位置

            boss.setResistance(0);
            canvas.stage.bossDie(boss);
            boss.setHealth(1);//防止再次检测isDead
        }

        return false;

    }

    private void bossIn(ElementBoss boss, double x, double y) {

        boss.setActionIndex(0);
        boss.setCanRunAction(false);
        boss.locate(x, y);
        boss.setNotCheckOut(true);//imp!

        bossNow = boss;

        canvas.enemies.add(boss);
        clear(false, -1);

    }

    private void bossCheck(ElementBoss boss, Dialog conv, double x, double y) {

        if(!boss.in) {
            setDialog(conv);

            if(conv != null) {
                if(conv.textIndex >= conv.bossJoin) {
                    bossIn(boss, x, y);
                    boss.in = true;//必须放在if里，否则检测一次就不会再检测，也就添加不了boss
                }
            }
            else {
                bossIn(boss, x, y);
                boss.in = true;
            }
        }

        if(conv != null && !boss.started) {
            if(conv.textIndex >= conv.bossStart) {
                canvas.stage.bossStart(boss);//检测是否触发对话的bossStart脚本
                boss.started = true;
            }
        }
        else if(!boss.started) {
            canvas.stage.bossStart(boss);
            boss.started = true;
        }

    }

    public void wayBossTick(ElementBoss boss,
                            double x0, double y0, double x, double y, double x2, double y2) {

        wayBossTick(boss, x0, y0, x, y, x2, y2, false);

    }

    private void wayBossTick(ElementBoss boss,
                             double x0, double y0, double x, double y, double x2, double y2, boolean notLeave) {

        bossCheck(boss, null, x0, y0);

        if(boss.in) {
            if(!boss.located) {
                boss.move(x, y);
                if(boss.moveDown()) {
                    boss.located = true;//定位
                    boss.reset();
                }
            }
            else {
                if(bossAttack(boss, x, y) && !notLeave) {
                    boss.move(x2, y2);
                    if(boss.moveDown()) {
                        boss.out = true;//逃离
                        canvas.enemies.remove(boss);
                    }
                }
            }
        }

        //boss已经到位且攻击未结束，且没有对话显示，不处于等待状态就允许运行action，这是写在Canvas逻辑的
        boss.setCanRunAction(boss.located && !isDialogShowing && !boss.ended && boss.waiter.isWaitBack());

        if(!boss.canRunAction()) {
            boss.reHealth(10);//无攻击时回血无敌
            boss.tranLoc(0, canvas.cyclePM);
        }

    }

    /**
     * @param cb 前对话
     * @param ca 后对话
     * @param x0 出现位置x
     * @param y0 出现位置y
     * @param x 默认位置x
     * @param y 默认位置y
     * @param x2 逃离位置x
     * @param y2 逃离位置y
     */
    public void bossTick(ElementBoss boss, Dialog cb, Dialog ca,
                         double x0, double y0, double x, double y, double x2, double y2) {

        bossCheck(boss, cb, x0, y0);

        if(cb.fullEnd()) {
            wayBossTick(boss, x0, y0, x, y, x2, y2, true);//对话结束后开始和道中boss相同的逻辑
        }
        else if(!boss.located) {//为了在结束对话之前boss能正常移动
            boss.move(x, y);
            if(boss.moveDown()) {
                boss.located = true;
                boss.reset();
            }
        }

        if(boss.ended) {
            setDialog(ca);//还有一个结束时的对话，逻辑很简单

            if(ca.fullEnd()) {
                boss.move(x2, y2);
                if(boss.moveDown()) {
                    boss.out = true;
                    canvas.enemies.remove(boss);
                }
            }
        }

    }

    ElementProgressBar bossBar;
    ElementProgressBar bossBarFull;

    //自动渲染boss血量条 & 剩余时间
    public void bossBarRender(Texture barTexture, NumberFont nf) {

        if(bossNow != null) {
            if(bossNow.located && !bossNow.ended && bossNow.getNowAction() != null) {
                if(bossBar == null) {
                    bossBar = new ElementProgressBar(left + 30, top + 5, field.getWidth() - 40, 5, 0, barTexture);
                    bossBarFull = new ElementProgressBar(left + 30, top + 12, field.getWidth() - 40, 5, 0, barTexture);
                }

                bossBarFull.setMaxValue(bossNow.actions.size());
                bossBarFull.setValue(bossNow.actions.size() - bossNow.getActionIndex());
                bossBarFull.setSpikes(bossNow.actions.size(), 3);
                bossBar.setMaxValue(bossNow.getMaxHealth());
                bossBar.setValue(bossNow.getHealth());

                bossBar.render();
                bossBarFull.render();

                EnemyAct a = bossNow.getNowAction();
                int sec = (a.maxTime - a.timer) / 60;//tick = 1/16sec
                int total = Maths.min(sec, 99);
                Draw.renderFontNumber(left, top, 14, 20, total, nf);
            }
        }

    }

    public void clear(boolean quake, int hit) {

        clear(quake, hit, true);

    }

    public void clear(boolean quake, int hit, boolean clearPlayer) {

        clear(quake, hit, clearPlayer, -1, 0, 0);

    }

    public void clear(boolean quake, int hit, int distance, double x, double y) {

        clear(quake, hit, true, distance, x, y);

    }

    /**
     * 清除子弹
     * @param quake 是否震动
     * @param hit 对敌人伤害，-1为直接清除
     * @param clearPlayer 是否清除己方子弹
     * @param distance 清除范围
     * @param x 范围坐标x
     * @param y 范围坐标y
     */
    public void clear(boolean quake, int hit, boolean clearPlayer, int distance, double x, double y) {

        ElementBullet b;
        ElementEnemy e;

        Array<ElementEnemy> enemies = canvas.enemies;
        Array<ElementBullet> bullets = canvas.bullets;

        for(int i = bullets.size(); i >= 0; i--) {
            b = bullets.get(i);
            if(b != null) {
                if(distance == -1 || Vector.distanceBetweenAB(b.getX(), b.getY(), x, y) < distance) {
                    if(b.getState() == ElementBullet.HIT_ENEMY && clearPlayer) {
                        addBulletClearEffect(b);
                        bullets.remove(i);
                    }
                    if(b.getState() == ElementBullet.HIT_PLAYER) {
                        addBulletClearEffect(b);
                        bullets.remove(i);
                    }
                }
            }
        }

        for(int i = enemies.size(); i >= 0; i--) {
            e = enemies.get(i);
            if(e != null) {
                if(!e.isControlled() && (distance == -1 || Vector.distanceBetweenAB(e.getX(), e.getY(), x, y) < distance)) {
                    e.hit(hit);//如果是boss不受秒杀伤害
                    if((hit == -1 || e.isDead()) && !e.isBoss()) {
                        addEnemyClearEffect(e);
                        e.removeFromParent();
                        e.clearControls(canvas);
                        enemies.remove(i);
                    }
                }
            }
        }

        if(quake) {
            canvas.quake(30, 3);
        }

    }

}
