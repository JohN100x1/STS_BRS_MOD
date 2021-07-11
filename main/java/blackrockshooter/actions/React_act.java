package blackrockshooter.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class React_act extends AbstractGameAction {
    private AbstractMonster m;

    public React_act(AbstractMonster m, int amount) {
        this.actionType = ActionType.BLOCK;
        this.m = m;
        this.amount = amount;
    }

    public void update() {
        if (this.m != null && this.m.getIntentBaseDmg() >= 0) {
            this.addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DexterityPower(AbstractDungeon.player, amount), amount));
        }
        if (this.m != null && this.m.getIntentBaseDmg() < 0) {
            this.addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, amount), amount));
        }
        this.isDone = true;
    }
}
