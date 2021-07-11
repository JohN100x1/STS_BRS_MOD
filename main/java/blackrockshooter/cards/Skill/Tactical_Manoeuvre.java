package blackrockshooter.cards.Skill;

import blackrockshooter.BlackRockShooterMod;
import blackrockshooter.actions.Tactical_Manoeuvre_act;
import blackrockshooter.cards.AbstractDynamicCard;
import blackrockshooter.characters.BlackRockShooter;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static blackrockshooter.BlackRockShooterMod.makeCardPath;

public class Tactical_Manoeuvre extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Unplayable. When you draw this card, Upgrade ALL other cards in your hand for the rest of combat. (Exhaust.)
     */


    // TEXT DECLARATION

    public static final String ID = BlackRockShooterMod.makeID(Tactical_Manoeuvre.class.getSimpleName());
    public static final String IMG = makeCardPath("Tactical_Manoeuvre.png");

    // /TEXT DECLARATION/

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = BlackRockShooter.Enums.BRS_BLACK;

    private static final int COST = -2;


    // /STAT DECLARATION/


    public Tactical_Manoeuvre() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        exhaust = true;
    }

    @Override
    public void triggerWhenDrawn() {
        AbstractDungeon.actionManager.addToTop(new Tactical_Manoeuvre_act(uuid));
        if(!upgraded){
            AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(this, AbstractDungeon.player.hand));
        }
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return false;
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            exhaust = false;
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
