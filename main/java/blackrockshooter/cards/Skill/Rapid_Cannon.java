package blackrockshooter.cards.Skill;

import blackrockshooter.BlackRockShooterMod;
import blackrockshooter.cards.AbstractDynamicCard;
import blackrockshooter.cards.Temp.Auto_Attack;
import blackrockshooter.characters.BlackRockShooter;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static blackrockshooter.BlackRockShooterMod.makeCardPath;

public class Rapid_Cannon extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Draw 2 Cards. Add 1(2) Auto-Attack to your hand.
     */


    // TEXT DECLARATION

    public static final String ID = BlackRockShooterMod.makeID(Rapid_Cannon.class.getSimpleName());
    public static final String IMG = makeCardPath("Rapid_Cannon.png");


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = BlackRockShooter.Enums.BRS_BLACK;

    private static final int COST = 1;
    private static final int DRAW_AMOUNT = 2;
    private static final int AMOUNT = 1;
    private static final int UPGRADE_PLUS_AMOUNT = 1;


    // /STAT DECLARATION/


    public Rapid_Cannon() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDraw = DRAW_AMOUNT;
        baseMagicNumber = magicNumber = AMOUNT;
        cardsToPreview = new Auto_Attack();
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(baseDraw));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(cardsToPreview.makeStatEquivalentCopy(), magicNumber));
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
