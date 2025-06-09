package nl.rrx.ui;

import nl.rrx.sprite.Sprite;
import nl.rrx.state.GameState;
import nl.rrx.ui.character.UI_Character;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import static nl.rrx.config.DependencyManager.PLAYER;
import static nl.rrx.config.DependencyManager.STATE_HANDLER;
import static nl.rrx.ui.UIUtil.importFont;

public class UI {

    private final Font fontBold;
    private final Font fontPlain;

    private String dialogue = "";

    public UI() {
        fontBold = importFont(UIUtil.LIBERTY_D_REGULAR_TTF).deriveFont(Font.BOLD, 80);
        fontPlain = importFont(UIUtil.LIBERTY_D_REGULAR_TTF).deriveFont(Font.PLAIN, 20F);
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.white);

        switch (STATE_HANDLER.currentState()) {
            case PLAY -> UI_Play.draw(g2);
            case PAUSE -> UI_Pause.draw(g2, fontBold);
            case DIALOGUE -> UI_Dialogue.draw(g2, fontPlain, dialogue);
            case TITLE_SCREEN -> UI_Title.draw(g2, fontBold);
            case CHARACTER_SCREEN -> UI_Character.draw(g2, fontPlain);
        }
    }

    public void drawDebugStats(Graphics2D g2, long drawStart) {
        Sprite sprite = PLAYER;
//        Sprite sprite = MONSTER_MGR.get(0);
        DebugUIUtil.draw(g2, sprite, drawStart, fontPlain);
    }

    public void setDialogue(String dialogue) {
        STATE_HANDLER.setState(GameState.DIALOGUE);
        this.dialogue = dialogue;
    }
}
