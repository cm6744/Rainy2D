package r2dx.stg.display;

import r2d.element.vector.ElementVector;
import r2d.render.manager.ShadowManager;
import r2dx.stg.CanvasSTG;
import rutil.container.Array;
import r2d.logic.Tickable;
import rutil.maths.Maths;
import rutil.maths.ToolMath;
import rutil.shape.Rectangle;
import rutil.shape.Rectangle2D;
import rwt.device.graphic.Color4f;
import rwt.device.graphic.Draw;
import rwt.device.graphic.TextFont;
import rwt.device.texture.TexSystem;
import rwt.device.texture.Texture;

public class Dialog extends Tickable {

    CanvasSTG canvas;
    Rectangle field;
    Rectangle2D textBar;

    ShadowManager shadow;

    public int textIndex;

    boolean isStart;

    public Array<String> texts = new Array<>(400);

    private static int MAX_CONV_CAP = 200;
    private static int MAX_CHA_CAP = 4;
    private static double CHA_SPEED = 20;//成员进入速度
    private static double CHA_SPEED_BUFFER = -0.9;

    public int[] speak = new int[MAX_CONV_CAP];//=conv_cap,标记的是每个对话位置的说话成员编号
    public int[] join = new int[MAX_CHA_CAP];//=cha_cap,标记的是每个成员的出现位置

    Array<ElementVector> characters = new Array<>(MAX_CHA_CAP);//[0, 2, 4...]左侧---[1, 3, 5...]右侧
    Array<ElementVector> render = new Array<>(MAX_CHA_CAP);//用于排序

    public Array<TextureBinding> images = new Array<>();

    public int bossJoin;
    public int bossStart;

    private boolean textBarFullOpen;
    private boolean textBarFullClose;

    public Dialog(CanvasSTG c) {

        canvas = c;

        field = canvas.field;
        textBar = new Rectangle2D(
                field.getX(0.05),
                field.getY(0.85),
                field.getX(0.95),
                field.getY(0.95)
        );
        shadow = new ShadowManager(canvas);
        shadow.setRenderField(textBar);
        shadow.setMaxBlack(0.5);

        for(int i = 0; i < MAX_CHA_CAP; i++) {
            ElementVector e = new ElementVector(256, 256, 0, 0, null);
            initCharacterLocation(e, i);
            characters.add(e);
        }

        for(int i = 0; i < MAX_CONV_CAP; i++) {
            speak[i] = -1;
        }

        for(int i = 0; i < MAX_CHA_CAP; i++) {
            join[i] = MAX_CONV_CAP;
        }

    }

    private void initCharacterLocation(ElementVector cha, int index) {

        if(index % 2 == 0) {
            cha.locate(field.getX(0.15), field.getY2() + cha.getHeight());
        }
        else {
            cha.locate(field.getX(0.85), field.getY2() + cha.getHeight());
        }

        cha.setSpeed(CHA_SPEED);

    }

    public void next() {

        if(hasTextNext() && textBarFullOpen) {
            forceNext();
        }

    }

    private void forceNext() {

        if(notEnd()) {
            textIndex++;
            reset();
        }

    }

    public boolean isStart() {

        return isStart;

    }

    /**
     * 这方法会多返回一个值--这是为了让文本框最后有淡出的时间
     * @return 是否可以渲染
     */
    public boolean notEnd() {

        return !textBarFullClose;

    }

    public boolean hasTextNext() {

        return textIndex < texts.size();

    }

    public boolean fullEnd() {

        return textBarFullClose;

    }

    public void changeAllTextures() {

        TextureBinding binding = images.get(textIndex);

        if(binding == null) {
            return;
        }

        for(int i = 0; i < binding.size; i++) {
            Texture texture = binding.textures.get(i);
            int uid = binding.uids.get(i);

            if(texture != null) {
                characters.get(uid).setTexture(texture);
                characters.get(uid).setSize(texture.getWidth(), texture.getHeight());
            }
        }

    }

    private void characterAction(ElementVector cha, int index) {

        double unLightCrease = Maths.max((1 - 0.1 * timer), 0.5);
        double transparencyCrease = Maths.max((1 - 0.1 * timer), 0.7);

        if(textIndex > 0) {
            if(speak[textIndex] == index) {//如果现在正是这个成员在说话
                cha.cacheTexture(cha.getBackupTexture());
            }
            else if(speak[textIndex - 1] == index) {//如果这个成员上一次也没说，就不再次运行
                cha.cacheTexture(TexSystem.light(cha.getBackupTexture(), unLightCrease));
                cha.cacheTexture(TexSystem.trans(cha.getTexture(), transparencyCrease));
            }
        }

    }

    private void characterMoveIn(ElementVector cha, int index) {

        if(textIndex >= join[index]) {
            cha.tranLoc(0, -cha.getSpeed());
            if(cha.getY() < field.getY2()) {
                cha.setSpeed(ToolMath.ofDouble(cha.getSpeed(), CHA_SPEED_BUFFER));
            }
        }

    }

    private void characterTick(ElementVector cha, int index) {

        if(hasTextNext()) {
            if(textBarFullOpen) {
                characterAction(cha, index);
                characterMoveIn(cha, index);
            }
        }
        else {
            //如果没有文本了，让人物离开屏幕
            cha.cacheTexture(TexSystem.trans(cha.getTexture(), Maths.max((1 - 0.1 * timer), 0)));
        }

    }

    private void barTick() {

        shadow.tick();

        if(hasTextNext()) {
            shadow.blackIn();
            if(shadow.inEnd()) {
                textBarFullOpen = true;
            }
        }
        else {
            shadow.blackOut();
            textBarFullOpen = false;
            if(shadow.outEnd()) {
                textBarFullClose = true;
                forceNext();//强制结束最后的假对话
            }
        }

    }

    public void tick() {

        isStart = true;

        barTick();

        ElementVector cha;

        for(int i = 0; i < characters.size(); i++) {
            cha = characters.get(i);

            if(cha.getTexture() != null) {
                characterTick(cha, i);
            }
        }

        changeAllTextures();

    }

    /**
     * 渲染对话，每刻调用
     */
    public void render() {

        renderCharacter();

        shadow.render();

        if(textBarFullOpen && hasTextNext()) {
            Draw.font(TextFont.MICROSOFT);
            Draw.color(Color4f.WHITE);
            Draw.renderStringCenter(textBar.getX(), textBar.getY(), texts.get(textIndex));
        }

    }

    private void renderCharacter() {

        render.clear();

        ElementVector cha;

        for(int i = 0; i < characters.size(); i++) {
            cha = characters.get(i);
            if(cha.getTexture() != null) {
                if(speak[textIndex] != i) {
                    render.add(cha);//如果不是这个成员说话，先加进去
                }
            }
        }

        if(speak[textIndex] >= 0) {
            cha = characters.get(speak[textIndex]);
            if(cha.getTexture() != null) {
                render.add(characters.get(speak[textIndex]));//最后加入说话的成员达到置于顶层效果
            }
        }

        for(int i = 0; i < render.size(); i++) {
            render.get(i).render();//按照排序后的顺序渲染
        }

    }

}
