package r2dx.stg.replay;

import rsc.Properties;
import r2dx.stg.element.ElementPlayer;
import rutil.container.Array;
import rutil.maths.Maths;
import rutil.security.RDS;
import rutil.text.Data;
import rutil.text.Parser;
import rwt.resource.Reader;

public class Replay {

    public Array<String> readCommands = new Array<>();
    public Properties head;

    int nowIndex;

    public void read(String name, String h) {

        head = new Properties(h);
        readCommands = Reader.read(name);

        //set private random gen
        Maths.sec.setSeed(head.intKey(ReplayHeadInfo.random));

    }

    public boolean isCheat() {

        return !(RDS.compare(head.doubleKey(ReplayHeadInfo.id), readCommands));

    }

    public int getPlayerUidInReplay() {

        return head.intKey(ReplayHeadInfo.serial);

    }

    public int getDifficultInReplay() {

        return head.intKey(ReplayHeadInfo.difficulty);

    }

    public void play(ElementPlayer player) {

        nowIndex++;
        String command = readCommands.get(nowIndex);

        if(Data.isEmpty(command)) {
            return;
        }

        for(int i = 0; i < Data.lengthOf(command); i++) {
            boolean bn = Parser.parseBoolean1or0(command, i);
            setPlayerState(player, i, bn);
        }

    }
    
    public void setPlayerState(ElementPlayer player, int index, boolean state) {

        player.replaying = true;

        switch(index) {
            case 0:
                player.right = state;
                break;
            case 1:
                player.down = state;
                break;
            case 2:
                player.left = state;
                break;
            case 3:
                player.up = state;
                break;
            case 4:
                player.slow = state;
                break;
            case 5:
                player.shoot = state;
                break;
            case 6:
                player.sure = state;
                break;
            case 7:
                player.auto = state;
                break;
            case 8:
                player.bomb = state;
                break;
        }

    }

}
