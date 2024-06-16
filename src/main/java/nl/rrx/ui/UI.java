package nl.rrx.ui;

import nl.rrx.config.DependencyManager;
import nl.rrx.sprite.Sprite;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import static nl.rrx.ui.UIUtil.importFont;

public class UI {

    private final DependencyManager dm;

    private final Font fontBold;
    private final Font fontPlain;

    public UI(DependencyManager dm) {
        this.dm = dm;
        fontBold = importFont(UIUtil.PURISA_BOLD_TTF).deriveFont(Font.BOLD, 80);
        fontPlain = importFont(UIUtil.PURISA_MEDIUM_TTF).deriveFont(Font.PLAIN, 20F);
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.white);

        switch (dm.stateManager.currentState()) {
            case PLAY -> {
                // TODO playstate stuff later
            }
            case PAUSE -> PauseUI.draw(g2, fontBold);
            case DIALOGUE -> DialogueUI.draw(g2, fontPlain);
            case TITLE_SCREEN -> TitleScreen.draw(g2, fontBold);
        }
    }

    public void drawDebugStats(Graphics2D g2, long drawStart) {
        Sprite sprite = dm.player;
//        Sprite sprite = dm.npcManager.getNpcs()[0];
        DebugUI.draw(g2, sprite, drawStart, fontPlain);
    }

    public void setCurrentDialogue(String dialogue) {
        DialogueUI.currentDialogue = dialogue;
    }
}
