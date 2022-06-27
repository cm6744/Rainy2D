package test.stg.screen.attacks.impl;

import r2dx.stg.action.BulletAct;
import test.stg.GameTest;
import test.stg.screen.stages.Resources;

public class BulletActImpl extends BulletAct {

    public Resources r = Resources.INSTANCE;

    public BulletActImpl() {

        super(GameTest.igc);

    }

}
