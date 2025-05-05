package nl.rrx.ui;

import nl.rrx.sprite.Sprite;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import static nl.rrx.config.DependencyManager.PLAYER;
import static nl.rrx.config.DependencyManager.STATE_MGR;
import static nl.rrx.ui.UIUtil.importFont;

public class UI {

    private final Font fontBold;
    private final Font fontPlain;

    private String dialogue = "";

    public UI() {
        fontBold = importFont(UIUtil.PURISA_BOLD_TTF).deriveFont(Font.BOLD, 80);
        fontPlain = importFont(UIUtil.PURISA_MEDIUM_TTF).deriveFont(Font.PLAIN, 20F);
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.white);

        switch (STATE_MGR.currentState()) {
            case PLAY -> PlayUI.draw(g2, PLAYER);
            case PAUSE -> PauseUI.draw(g2, fontBold);
            case DIALOGUE -> DialogueUI.draw(g2, fontPlain, dialogue);
            case TITLE_SCREEN -> TitleScreen.draw(g2, fontBold);
        }
    }

    public void drawDebugStats(Graphics2D g2, long drawStart) {
        Sprite sprite = PLAYER;
//        Sprite sprite = npcManager.getNpcs()[0];
        DebugUI.draw(g2, sprite, drawStart, fontPlain);
    }

    public void setDialogue(String dialogue) {
        this.dialogue = dialogue;
    }
}
