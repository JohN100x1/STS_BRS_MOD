package blackrockshooter.potions;

import basemod.abstracts.CustomPotion;
import blackrockshooter.cards.Temp.Auto_Attack;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class Reload_Potion extends CustomPotion {

    public static final String POTION_ID = blackrockshooter.BlackRockShooterMod.makeID("Reload_Potion");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public Reload_Potion() {
        // The bottle shape and inside is determined by potion size and color. The actual colors are the main DefaultMod.java
        super(NAME, POTION_ID, PotionRarity.COMMON, PotionSize.H, PotionColor.ENERGY);
        potency = getPotency();
        labOutlineColor = Color.CYAN;
        description = DESCRIPTIONS[0] + potency + DESCRIPTIONS[1];
        isThrown = false;
        tips.add(new PowerTip(name, description));
    }

    @Override
    public void use(AbstractCreature target) {
        AbstractCard auto_attack = new Auto_Attack();
        auto_attack.upgrade();
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            this.addToBot(new MakeTempCardInHandAction(auto_attack.makeStatEquivalentCopy(), potency));
        }
    }

    @Override
    public AbstractPotion makeCopy() {
        return new Reload_Potion();
    }

    // This is your potency.
    @Override
    public int getPotency(final int potency) {
        return 3;
    }
}