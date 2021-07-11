package blackrockshooter.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;

public class Over_Limit_act extends AbstractGameAction {
    private AbstractPlayer p;

    public Over_Limit_act() {
        this.actionType = ActionType.WAIT;
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_XFAST;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_XFAST && this.p.hasPower("Dexterity")) {
            int dexAmt = this.p.getPower("Dexterity").amount;
            this.addToTop(new ApplyPowerAction(this.p, this.p, new DexterityPower(this.p, dexAmt), dexAmt));
        }

        this.tickDuration();
    }
}
