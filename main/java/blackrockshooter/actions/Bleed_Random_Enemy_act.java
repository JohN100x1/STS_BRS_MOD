package blackrockshooter.actions;

import blackrockshooter.powers.Bleed_pw;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class Bleed_Random_Enemy_act extends AbstractGameAction {

    public Bleed_Random_Enemy_act(int amount) {
        this.amount = amount;
        this.actionType = ActionType.DEBUFF;
    }

    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        this.target = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
        if (this.target != null) {
            this.addToTop(new ApplyPowerAction(this.target, p, new Bleed_pw(this.target, p, this.amount), this.amount));
        }

        this.isDone = true;
    }
}