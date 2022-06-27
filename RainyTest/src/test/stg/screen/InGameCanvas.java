package test.stg.screen;

import r2d.element.Element;
import r2d.element.action.ButtonGroup;
import r2d.element.action.ElementButtonText;
import r2d.element.action.ElementTextField;
import r2d.element.image.ElementImage;
import r2d.render.desktop.Screen;
import r2d.vector.Vector;
import r2dx.stg.CanvasSTG;
import r2dx.stg.element.ElementBullet;
import r2dx.stg.element.ElementEnemy;
import r2dx.stg.element.ElementItem;
import r2dx.stg.element.ElementPlayer;
import r2dx.stg.replay.Replay;
import r2dx.stg.replay.ReplayCamera;
import rutil.maths.Maths;
import rutil.maths.ToolMath;
import rutil.shape.Circle;
import rwt.device.graphic.Draw;
import rwt.device.sound.SoundDevice;
import rwt.device.texture.TexSystem;
import rwt.device.texture.Texture;
import rwt.resource.Reader;
import test.stg.GameTest;
import test.stg.screen.attacks.impl.player.MorisaAct;
import test.stg.screen.stages.Resources;
import test.stg.screen.stages.Stage1;

public class InGameCanvas extends CanvasSTG {

    public Resources r = Resources.INSTANCE;

    public ElementPlayer player = new ElementPlayer(
            36, 48, 5, 128, Reader.image("textures/player/mrsp"), Reader.image("textures/effect/plp")
    );

    public Element bar = new Element
            (500, 30, 300, 600, Reader.image("textures/picture/bar"));
    public Element titleIn = new ElementImage
            (70, 440, 256, 256, Reader.image("textures/picture/titleIn"));

    public Texture star = Reader.image("textures/effect/eff");

    public Replay rep = new Replay();
    public ReplayCamera camera = new ReplayCamera();

    int score;
    int hiScore;
    int power = 40;
    int life = 10;
    int graze;
    int time;
    public boolean autoShoot;

    boolean rpl = false;
    String rplName = "rpl02";
    String readRpl = "rpl01";

    public InGameCanvas(Screen scr) {

        super(scr);
        boundDisplayOn = false;

    }

    public void init() {

        super.init();

        setImageOverField(Reader.image("textures/picture/background/BGF"));

        imageMiddle.add(player);

        util.bulletEffect = Reader.image("textures/bullet/bulletEffects");
        util.enemyEffect = Reader.image("textures/enemy/enemyEffect");
        util.defaultBulletSound = ("sounds/effect/bullet");

        player.locate(field.getX(), field.getY(0.8));
        addStage(new Stage1(this));

        player.action = new MorisaAct();

        if(rpl) {
            rep.read("replays/rpl02", "replays/head/rpl02");
            System.out.println(rep.getPlayerUidInReplay());
            System.out.println(rep.getDifficultInReplay());
            System.out.println(rep.isCheat());
        }

        g.add(b1 = new ElementButtonText(field.getX(), 320, 164, 32, "Continue", "继续游戏"));
        g.add(b2 = new ElementButtonText(field.getX(), 360, 164, 32, "Back to Menu", "返回主菜单"));
        g.add(b3 = new ElementButtonText(field.getX(), 400, 164, 32, "Restart", "重新开始"));
        g.display();

    }

    public ElementTextField tx;

    ButtonGroup g = new ButtonGroup("sounds/effect/shoot");
    ElementButtonText b1;
    ElementButtonText b2;
    ElementButtonText b3;

    public void onPause() {

        imageAbove.add(g);

    }

    public void endPause() {

        imageAbove.remove(g);

    }

    public void pauseTick() {

        if(b1.isClicking()) {
            setPause(false);
        }
        if(b2.isClicking()) {
            setPause(false);
            nextCanvas(new MenuCanvas(screen));
        }
        if(b3.isClicking()) {
            setPause(false);
            nextCanvas(GameTest.igc = new InGameCanvas(screen));
        }

        g.tick(this);

    }

    double ptx;
    int progress;

    public void tick() {

        super.tick();

        if(stage.isEnd) {
            nextStage();
        }

        r.magicRound.tranAngle(1);

        if(!util.isDialogShowing) {
            if(player.bomb && power >= 20 && player.action.canBomb()) {
                player.action.callBomb();
                power -= 20;
            }
        }

        if(score > hiScore) {
            hiScore = score;
        }

        if(rpl) {
            rep.play(player);
        }
        else {
            camera.cache(player);
        }

        r.magicRound.tranAngle(3);
        r.bossRound.tranAngle(3);

        util.dialogTick(player, 5, 30);

        if(player.getX() + 1 < ptx) {
            bgManager.tranX(player.slow ? -0.2 : -0.4);
        }
        if(player.getX() - 1 > ptx) {
            bgManager.tranX(player.slow ? 0.2 : 0.4);
        }

        ptx = player.getX();

    }

    public void tick(ElementItem e) {

        if(player.slow && player.getY() < 200) {
            e.setGravity(false);
            e.setAngle(Vector.angleBetweenAB(player, e));
            e.setSpeed(15);
        }
        if(e.getCircle().intersects(player.getCircle())) {
            items.remove(e);
            power += 1;
        }

    }

    public void tick(ElementEnemy e, ElementBullet b) {

        Circle ec = e.getCircle();
        Circle bc = b.getCircle();

        if(b.getState() == ElementBullet.HIT_ENEMY && !e.isControlled()) {
            if(ec.intersects(bc)) {
                e.hit(1);
                hitEnemy(b);
                time += 5;
                if(e.isDead() && !e.isBoss()) {//检测碰撞移除敌人和子弹
                    for(int i = 0; i < e.controls.size(); i++) {
                        util.addEnemyClearEffect(e.controls.get(i));
                        enemies.remove(e.controls.get(i));
                    }
                    e.callRemove();
                    e.removeFromParent();
                    util.addEnemyClearEffect(e);
                    if(e.getNowAction() != null) {
                        e.getNowAction().remove(e);
                    }
                }
            }
        }

        if(b.getState() == ElementBullet.HIT_PLAYER) {
            if(((bc.intersects(player.getPointCircle()) && player.waiter.isWaitBack()))) {
                playerDie();
            }
        }

    }

    int sizeBossRound;

    public void renderBottomField() {

        ElementEnemy e;

        for(int i = 0; i < enemies.size(); i++) {
            e = enemies.get(i);
            if(e.isControlled()) {
                e.setTexture(r.magicRound.getTexture());
            }
            else if(e.isBoss()) {
                sizeBossRound = Maths.toInt(64 * cycle + 128);
                Draw.renderIn(e.getX(), e.getY(), sizeBossRound, sizeBossRound, r.magicRound.getTexture());
            }
        }

    }

    public void renderFrontField() {

        util.dialogRender();
        util.bossBarRender(r.bossBar, r.nfrTwo);
        if(stage != null)
        stage.render();

        if(isPause) {
            ts = ToolMath.ofInt(ts, 5, 255);
        }
        else {
            ts = 0;
        }

        if(ts > 0) {
            Texture tex = TexSystem.trans(pauseT, ts / 255.0);
            Draw.render(left, top, tex);
        }

    }

    int ts;
    Texture pauseT = Reader.image("textures/picture/pause");

    public void renderAboveField() {

        Draw.renderFontNumber(14, 58, 12, 18, hiScore, r.nfr18);
        Draw.renderFontNumber(14, 110, 12, 18, score, r.nfr18);
        Draw.renderFontNumber(100, 160, 12, 18, life, r.nfrNo18);
        Draw.renderFontNumber(36, 160, 12, 18, power, r.nfrNo18);
        //Draw.renderFontNumber(right + 100, top + 145, 16, 24, graze, r.nfrNo);
        Draw.renderFontNumber(14, 212, 12, 18, timer / 60, r.nfr18);
        //Draw.render(bar);
        //Draw.render(titleIn);
        Draw.renderFontNumber(left, bottom, 8, 12, Maths.toInt(screen.nowFpsUpdate), r.nfrNo18);
        Draw.renderFontNumber(left + 48, bottom, 8, 12, Maths.toInt(screen.nowFpsRender), r.nfrNo18);
        Draw.renderFontNumber(left + 96, bottom, 8, 12, bullets.size(), r.nfrNo18);

    }

    String pld = ("sounds/effect/player_die");
    String shb = ("sounds/effect/shoot");

    public void playerDie() {

        player.hit(200);
        SoundDevice.playRush(pld);
        //util.clear(false, 0);
        player.waiter.wait(200);
        life--;
        if(!rpl) {
            camera.write("replays/" + rplName, "replays/head/" + rplName, util.getDifficulty(), player);
        }

    }

    public void hitEnemy(ElementBullet b) {

        util.addEffect(b.getX(), b.getY(), 3, b.getAngle(), 70, -3, 10, 0, star);
        SoundDevice.play(shb);
        b.callRemove();

    }

}
