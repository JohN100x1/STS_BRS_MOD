package blackrockshooter.actions;

import blackrockshooter.powers.Bleed_pw;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BB_Genocide_act extends AbstractGameAction {
    private final AbstractPlayer p;

    public BB_Genocide_act(AbstractPlayer p, int amount) {
        this.actionType = ActionType.BLOCK;
        this.amount = amount;
        this.p = p;
    }

    public void update() {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            for (final AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
                if (!mo.isDead && !mo.isDying && mo.hasPower("blackrockshooter:Bleed_pw")) {
                    int remainingAmount = 10 - mo.getPower("blackrockshooter:Bleed_pw").amount;
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, p, new Bleed_pw(mo, p, remainingAmount), remainingAmount));
                } else {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, p, new Bleed_pw(mo, p, this.amount), this.amount));
                }
            }
        }
        this.isDone = true;
    }
}
