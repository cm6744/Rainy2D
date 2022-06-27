package r2dx.stg.factory;

import r2d.factory.Factory;
import r2dx.stg.element.ElementBullet;
import r2dx.stg.element.ElementEnemy;
import r2dx.stg.element.ElementItem;
import rutil.container.Array;

public class STGFactory extends Factory {

    public static Array<ElementBullet> bulletCache = new Array<>(max);
    public static Array<ElementEnemy> enemyCache = new Array<>(min);
    public static Array<ElementItem> itemCache = new Array<>(min);

    static int lastBullet;
    static int lastEnemy;
    static int lastItem;

    public static void init() {

        for(int i = 0; i < max; i++) {
            bulletCache.add(new ElementBullet(0, 0, 0, 0, 0, null));
        }

        for(int i = 0; i < min; i++) {
            enemyCache.add(new ElementEnemy(0, 0, 0, 0, 0, null));
            itemCache.add(new ElementItem(0, 0, 0, 0, null));
        }

    }

    public static ElementBullet newBullet(ElementBullet cloner) {

        ElementBullet e = bulletCache.get(lastBullet);

        e.setSize(cloner.getWidth(), cloner.getHeight());

        e.setImageAll(cloner.getTextureAll());

        e.setType(cloner.getType());
        e.setCutSize(cloner.getCutSize());
        e.setColor(cloner.getColor());
        e.setTexture(cloner.getTexture());
        e.setBoundSize(cloner.getBoundSize());

        e.reset();
        e.forRemove = false;
        e.setGrazed(false);

        lastBullet++;
        if(lastBullet >= bulletCache.size()) {
            lastBullet = 0;
        }

        return e;

    }

    public static ElementEnemy newEnemy(ElementEnemy cloner) {

        ElementEnemy e = enemyCache.get(lastEnemy);

        e.setSize(cloner.getWidth(), cloner.getHeight());
        e.setTexture(cloner.getBackupTexture());

        e.setMaxHealth(cloner.getMaxHealth());
        e.setHealth(cloner.getMaxHealth());

        e.reset();
        e.forRemove = false;

        e.setActionIndex(0);
        e.setCanRunAction(true);

        lastEnemy++;
        if(lastEnemy >= enemyCache.size()) {
            lastEnemy = 0;
        }

        return e;

    }

    public static ElementItem newItem(ElementItem cloner) {

        ElementItem e = itemCache.get(lastItem);

        e.setSize(cloner.getWidth(), cloner.getHeight());
        e.setTexture(cloner.getBackupTexture());

        e.reset();
        e.forRemove = false;

        lastItem++;
        if(lastItem >= itemCache.size()) {
            lastItem = 0;
        }

        return e;

    }

}
