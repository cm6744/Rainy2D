package test.stg.screen.attacks.impl.player;

import r2d.element.vector.ElementEffect;
import r2dx.stg.action.PlayerAction;
import r2dx.stg.element.ElementPlayer;
import rutil.maths.Maths;
import rwt.device.texture.TexSystem;
import test.stg.GameTest;
import test.stg.screen.InGameCanvas;
import test.stg.screen.stages.Resources;

public class MorisaAct extends PlayerAction {

    Resources r = Resources.INSTANCE;
    InGameCanvas igc = GameTest.igc;

    int timerBomb;
    ElementEffect playerBomb = new ElementEffect(128, 128, 0, 0, r.player1B.getTexture());
    double angle = -90;

    public MorisaAct() {

        super(GameTest.igc);

    }

    public void tick(ElementPlayer player) {

        if((player.shoot || player.auto) && !util.isDialogShowing && forTick(4)) {
            //angle = Direction.angleOf(dire);
            util.playerShoot(player, player.getX(), player.getOffsetY(), r.playerBl, 16, -90, true, null);
            util.playerShoot(player, player.getX() - 8, player.getOffsetY(), r.playerBl, 16, -95, true, null);
            util.playerShoot(player, player.getX() + 8, player.getOffsetY(), r.playerBl, 16, -85, true, null);
            util.playerShoot(player, player.getX() - 24, player.getOffsetY(), r.playerBl, 16, angle, true, null);
            util.playerShoot(player, player.getX() + 24, player.getOffsetY(), r.playerBl, 16, angle, true, null);
        }
        timerBomb--;
        if(timerBomb > 0) {
            playerBomb.locate(player.getX(), player.getY());
            player.hit(300);
            util.addEffect(player.getX(), player.getY(), 4, Maths.random(0, 360), Maths.randomInt(25, 50), -1, 0, -0.01, igc.star);
            util.clear(true, 1, false, playerBomb.getWidth() / 2, player.getX(), player.getY());
            playerBomb.tranSize(1, 1);
            if(timerBomb <= 64) {
                playerBomb.tranSize(12, 12);
                playerBomb.cacheTexture(TexSystem.trans(playerBomb.getBackupTexture(), timerBomb / 64.0));
            }
        }
        else if(timerBomb == 0) {
            igc.imageBottom.remove(playerBomb);
        }

        angle = -90 + canvas.cyclePM * 45;
    }

    public void callBomb() {

        if(timerBomb <= 0) {
            timerBomb = 300;
            playerBomb.setSize(128, 128);
            playerBomb.setTexture(playerBomb.getBackupTexture());
            igc.imageBottom.add(playerBomb);
        }

    }

    public boolean canBomb() {

        return timerBomb <= 0;

    }

}
