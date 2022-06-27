package r2dx.stg.replay;

import r2dx.stg.element.ElementPlayer;
import rutil.container.Array;
import rutil.maths.Maths;
import rutil.security.RDS;
import rutil.text.Data;
import rutil.text.VariableString;
import rwt.resource.Reader;

public class ReplayCamera {

    public Array<String> writeCommands = new Array<>(10000);

    VariableString p = new VariableString();

    public void cache(ElementPlayer player) {

        p.add(player.right);
        p.add(player.down);
        p.add(player.left);
        p.add(player.up);

        p.add(player.slow);
        p.add(player.shoot);
        p.add(player.sure);
        p.add(player.auto);
        p.add(player.bomb);

        writeCommands.add(p.create());
        p.clear();

    }

    public void write(String path, String h, int difficulty, ElementPlayer player) {

        Array<String> head = new Array<>();

        head.add(ReplayHeadInfo.id + " = " + Data.toString(RDS.allToRds(writeCommands)));
        head.add(ReplayHeadInfo.random + " = " + Data.toString(Maths.sec.startSeed));
        head.add(ReplayHeadInfo.serial + " = " + Data.toString(player.replaySerial));
        head.add(ReplayHeadInfo.difficulty + " = " + Data.toString(difficulty));

        Reader.write(writeCommands, path);
        Reader.write(head, h);

    }

}
