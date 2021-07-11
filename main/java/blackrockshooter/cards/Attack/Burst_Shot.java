package blackrockshooter.cards.Attack;

import blackrockshooter.BlackRockShooterMod;
import blackrockshooter.cards.AbstractDynamicCard;
import blackrockshooter.characters.BlackRockShooter;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static blackrockshooter.BlackRockShooterMod.makeCardPath;

public class Burst_Shot extends AbstractDynamicCard {

    // Deal 6(9) Damage. Deal an additional 4(5) damage for each attack played this turn.

    public static final String ID = BlackRockShooterMod.makeID(Burst_Shot.class.getSimpleName());
    public static final String IMG = makeCardPath("Burst_Shot.png");

    // /TEXT DECLARATION/

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = BlackRockShooter.Enums.BRS_BLACK;

    private static final int COST = 1;
    private static final int DAMAGE = 6;
    private static final int UPGRADE_PLUS_DMG = 3;
    private static final int MAGIC = 4;
    private static final int UPGRADE_PLUS_MAGIC = 1;
    private static final int AMOUNT = 0;

    // /STAT DECLARATION/


    public Burst_Shot() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
        baseCounterNumber = counterNumber = AMOUNT;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        damage += counterNumber;
        calculateCardDamage(m);
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage-magicNumber, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }

    @Override
    public void applyPowers() {
        int realBaseDamage = baseDamage;
        baseCounterNumber = magicNumber*attacksPlayedThisTurn();
        baseDamage += baseCounterNumber;
        super.applyPowers();
        baseDamage = realBaseDamage;
        isDamageModified = damage != baseDamage;
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        baseCounterNumber = magicNumber*attacksPlayedThisTurn();
        int realBaseDamage = baseDamage;
        baseDamage += baseCounterNumber;
        super.calculateCardDamage(mo);
        baseDamage = realBaseDamage;
        isDamageModified = damage != baseDamage;
    }

    public int attacksPlayedThisTurn() {
        int attacks_played = 0;
        for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisTurn){
            if (c.type == CardType.ATTACK){
                ++attacks_played;
            }
        }
        return attacks_played;
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
            initializeDescription();
        }
    }

}