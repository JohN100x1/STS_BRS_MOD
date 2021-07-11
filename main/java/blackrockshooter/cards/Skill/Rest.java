package blackrockshooter.cards.Skill;

import blackrockshooter.actions.RemoveFromMasterDeckAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import blackrockshooter.BlackRockShooterMod;
import blackrockshooter.cards.AbstractDynamicCard;
import blackrockshooter.characters.BlackRockShooter;
import blackrockshooter.powers.Sleep_pw;

import static blackrockshooter.BlackRockShooterMod.makeCardPath;

public class Rest extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Retain. Heal 50%(all) of max HP. Gain 3 sleep. End your turn. Remove this card from your deck.
     */


    // TEXT DECLARATION

    public static final String ID = BlackRockShooterMod.makeID(Rest.class.getSimpleName());
    public static final String IMG = makeCardPath("Rest.png");

    // /TEXT DECLARATION/

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = BlackRockShooter.Enums.BRS_BLACK;

    private static final int COST = 3;
    private static final int MAGIC = 3;


    // /STAT DECLARATION/


    public Rest() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.selfRetain = true;
        this.purgeOnUse = true;
        baseMagicNumber = magicNumber = MAGIC;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!upgraded) {
            AbstractDungeon.actionManager.addToBottom(new HealAction(p, p, (int)(0.5F*p.maxHealth)));
        }
        else{
            AbstractDungeon.actionManager.addToBottom(new HealAction(p, p, p.maxHealth));
        }
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new Sleep_pw(p, p, magicNumber), magicNumber));
        AbstractDungeon.actionManager.addToBottom(new PressEndTurnButtonAction());
        AbstractDungeon.actionManager.addToBottom(new RemoveFromMasterDeckAction(this));
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}