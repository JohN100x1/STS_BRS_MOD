package blackrockshooter.cards.Skill;

import blackrockshooter.BlackRockShooterMod;
import blackrockshooter.cards.AbstractDynamicCard;
import blackrockshooter.characters.BlackRockShooter;
import blackrockshooter.powers.TreasureHunt_pw;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static blackrockshooter.BlackRockShooterMod.makeCardPath;

public class Treasure_Hunt extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * (Retain.) Enemy doubles max HP. When the enemy dies, gain gold equal to half its max HP. Exhaust.
     */


    // TEXT DECLARATION

    public static final String ID = BlackRockShooterMod.makeID(Treasure_Hunt.class.getSimpleName());
    public static final String IMG = makeCardPath("Treasure_Hunt.png");

    // /TEXT DECLARATION/

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = BlackRockShooter.Enums.BRS_BLACK;

    private static final int COST = 1;


    // /STAT DECLARATION/


    public Treasure_Hunt() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new TreasureHunt_pw(m, p, m.maxHealth), m.maxHealth));
        m.maxHealth += m.maxHealth;
        m.currentHealth += m.currentHealth;
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.retain = true;
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}