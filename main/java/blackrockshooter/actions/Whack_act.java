package blackrockshooter.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class Whack_act extends AbstractGameAction {
    private AbstractMonster m;

    public Whack_act(AbstractMonster m) {
        this.actionType = ActionType.BLOCK;
        this.m = m;
    }

    public void update() {
        if (this.m != null && this.m.getIntentBaseDmg() < 0) {
            this.addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, 1), 1));
        }
        this.isDone = true;
    }
}
