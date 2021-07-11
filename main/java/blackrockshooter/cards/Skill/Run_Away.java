package blackrockshooter.cards.Skill;

import blackrockshooter.BlackRockShooterMod;
import blackrockshooter.cards.AbstractDynamicCard;
import blackrockshooter.characters.BlackRockShooter;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static blackrockshooter.BlackRockShooterMod.makeCardPath;

public class Run_Away extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Draw 1(2) card. Exhaust 1 card.
     */


    // TEXT DECLARATION

    public static final String ID = BlackRockShooterMod.makeID(Run_Away.class.getSimpleName());
    public static final String IMG = makeCardPath("Run_Away.png");


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = BlackRockShooter.Enums.BRS_BLACK;

    private static final int COST = 0;
    private static final int AMOUNT = 1;
    private static final int UPGRADE_PLUS_AMOUNT = 1;


    // /STAT DECLARATION/


    public Run_Away() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDraw = AMOUNT;
        baseMagicNumber = magicNumber = AMOUNT;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(magicNumber));
        AbstractDungeon.actionManager.addToBottom(new ExhaustAction(1, false));
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_AMOUNT);
            initializeDescription();
        }
    }
}
