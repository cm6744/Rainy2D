package test.stg.screen.attacks.impl;

import r2dx.stg.action.EnemyAct;
import r2dx.stg.element.ElementPlayer;
import test.stg.GameTest;
import test.stg.screen.stages.Resources;

public class EnemyActImpl extends EnemyAct {

    public Resources r = Resources.INSTANCE;
    public ElementPlayer player;

    public EnemyActImpl() {

        super(GameTest.igc);
        player = GameTest.igc.player;

    }

}
