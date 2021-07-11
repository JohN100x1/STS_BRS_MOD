package blackrockshooter.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Pursue_act extends AbstractGameAction {
    private AbstractMonster m;

    public Pursue_act(AbstractMonster m) {
        this.actionType = ActionType.BLOCK;
        this.m = m;
    }

    public void update() {
        if (this.m != null && !(this.m.getIntentBaseDmg() > 0)) {
            this.addToTop(new DrawCardAction(AbstractDungeon.player, 1));
            this.addToTop(new GainEnergyAction(1));
        }
        this.isDone = true;
    }
}
