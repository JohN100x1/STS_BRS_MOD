package blackrockshooter.variables;

import basemod.abstracts.DynamicVariable;
import blackrockshooter.BlackRockShooterMod;
import blackrockshooter.cards.AbstractDefaultCard;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class CounterNumber extends DynamicVariable {
    public CounterNumber() {
    }

    public String key() {
        return BlackRockShooterMod.makeID("CounterNumber");
    }

    public boolean isModified(AbstractCard card) {
        return ((AbstractDefaultCard)card).isCounterNumberModified;
    }

    public int value(AbstractCard card) {
        return ((AbstractDefaultCard)card).counterNumber;
    }

    public int baseValue(AbstractCard card) {
        return ((AbstractDefaultCard)card).baseCounterNumber;
    }

    public boolean upgraded(AbstractCard card) {
        return ((AbstractDefaultCard)card).upgradedCounterNumber;
    }
}
