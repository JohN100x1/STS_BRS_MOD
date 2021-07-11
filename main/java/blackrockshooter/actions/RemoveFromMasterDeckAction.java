package blackrockshooter.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.Iterator;

public class RemoveFromMasterDeckAction extends AbstractGameAction {
    private final AbstractCard cardToRemove;

    public RemoveFromMasterDeckAction(AbstractCard cardToRemove) {
        this.cardToRemove = cardToRemove;
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    public void update() {
        AbstractCard upgradeMatch = null;
        AbstractCard match = null;
        Iterator var3 = AbstractDungeon.player.masterDeck.group.iterator();

        while(var3.hasNext()) {
            AbstractCard c = (AbstractCard)var3.next();
            if (c.cardID.equals(this.cardToRemove.cardID) && c.upgraded == this.cardToRemove.upgraded) {
                upgradeMatch = c;
                break;
            }

            if (c.cardID.equals(this.cardToRemove.cardID)) {
                match = c;
            }
        }

        if (null != upgradeMatch) {
            this.doRemovalFromMasterDeck(upgradeMatch);
        } else if (null != match) {
            this.doRemovalFromMasterDeck(match);
        }

        this.isDone = true;
    }

    private void doRemovalFromMasterDeck(AbstractCard c) {
        this.cardToRemove.lighten(true);
        this.cardToRemove.stopGlowing();
        this.cardToRemove.unhover();
        this.cardToRemove.unfadeOut();
        AbstractDungeon.player.masterDeck.removeCard(c);
    }
}
