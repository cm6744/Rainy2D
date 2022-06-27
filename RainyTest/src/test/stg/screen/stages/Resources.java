package test.stg.screen.stages;

import r2d.element.image.ElementImage;
import r2d.render.image.RotatedImage;
import r2dx.stg.element.ElementBoss;
import r2dx.stg.element.ElementBullet;
import r2dx.stg.element.ElementEnemy;
import r2dx.stg.element.ElementItem;
import rutil.container.Map;
import rwt.device.graphic.NumberFont;
import rwt.device.texture.Texture;
import rwt.resource.Reader;

public class Resources {

    public static Resources INSTANCE = new Resources();

    public NumberFont nfr = new NumberFont(Reader.image("textures/font/numberFont"), true, 10);
    public NumberFont nfrNo = new NumberFont(Reader.image("textures/font/numberFont"), false, 10);
    public NumberFont nfrTwo = new NumberFont(Reader.image("textures/font/numberFont"), true, 2);
    public NumberFont nfr18 = new NumberFont(Reader.image("textures/font/numberFont18"), true, 10);
    public NumberFont nfrNo18 = new NumberFont(Reader.image("textures/font/numberFont18"), false, 10);
    public NumberFont nfrTwo18 = new NumberFont(Reader.image("textures/font/numberFont18"), true, 2);

    public Map<ElementBullet> bulletMap = new Map<>();
    static {
        INSTANCE.bulletMap.set("bl01", INSTANCE.bl01);
        INSTANCE.bulletMap.set("bl02", INSTANCE.bl02);
    }
    Texture bt = Reader.image("textures/bullet/bullets");
    public ElementBullet bl01 = new ElementBullet
            (0, 20, 20, 20, 3.2, 0, 10, bt);
    public ElementBullet bl02 = new ElementBullet
            (1, 20, 20, 20, 3.2, 0, 8, bt);
    public ElementBullet bl03 = new ElementBullet
            (2, 20, 20, 20, 3.2, 0, 6, bt);
    public ElementBullet bl04 = new ElementBullet
            (3, 20, 20, 20, 3.2, 0, 10, bt);
    public ElementBullet bl05 = new ElementBullet
            (4, 20, 20, 20, 3.2, 0, 12, bt);
    public ElementBullet bl00 = new ElementBullet
            (1, 54, 54, 54, 1.65, 0, 30, Reader.image("textures/bullet/bigBullets"));

    public ElementBullet playerBl = new ElementBullet
            (30, 30, 12.8, -90, 10, Reader.image("textures/bullet/playerBullet"));

    public ElementEnemy enemy = new ElementEnemy
            (48, 48, 1.5, 90, 50, Reader.image("textures/enemy/enemy1"));
    public ElementEnemy enemy2 = new ElementEnemy
            (64, 64, 1.5, 90, 50, Reader.image("textures/enemy/enemy2"));
    public ElementEnemy enemy2h = new ElementEnemy
            (64, 64, 1.5, 90, 5000, Reader.image("textures/enemy/enemy2"));
    public ElementBoss boss = new ElementBoss
            (84, 112, 3.5, 45, 700, Reader.image("textures/enemy/boss1"));
    public ElementBoss boss6 = new ElementBoss
            (84, 112, 3.5, 45, 700, Reader.image("textures/enemy/boss1"));

    public ElementItem point = new ElementItem
            (0, 24, 24, 24, Reader.image("textures/item/fallItems"));


    public RotatedImage magicRound = new RotatedImage(Reader.image("textures/effect/magicRound"));
    public RotatedImage bossRound = new RotatedImage(Reader.image("textures/effect/bossRound"));

    public RotatedImage player1B = new RotatedImage(Reader.image("textures/player/player1b"));

    public ElementImage stg6Img = new ElementImage(380, 250, 256, 128, Reader.image("textures/font/stgtxt"));

    public Texture enemyDie = Reader.image("textures/enemy/enemyEffect");
    public Texture bossBar = Reader.image("textures/picture/bossBar");

}
