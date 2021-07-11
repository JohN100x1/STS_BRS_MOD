package blackrockshooter.cards.Attack;

import blackrockshooter.BlackRockShooterMod;
import blackrockshooter.cards.AbstractDynamicCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import blackrockshooter.characters.BlackRockShooter;

import static blackrockshooter.BlackRockShooterMod.makeCardPath;

public class Charge extends AbstractDynamicCard {

    // Deal 7 Damage. If this is the first attack you played this turn, draw 1 card (and gain 1 energy).

    public static final String ID = BlackRockShooterMod.makeID(Charge.class.getSimpleName());
    public static final String IMG = makeCardPath("Charge.png");

    // /TEXT DECLARATION/

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = BlackRockShooter.Enums.BRS_BLACK;

    private static final int COST = 0;
    private static final int DAMAGE = 7;
    private static final int AMOUNT = 1;

    // /STAT DECLARATION/


    public Charge() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseDraw = AMOUNT;
        baseMagicNumber = magicNumber = AMOUNT;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        boolean first_atk = false;
        for(final AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisTurn){
            if(c.type == CardType.ATTACK){
                first_atk = true;
                break;
            }
        }
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p,damage,damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        if(first_atk){
            if(upgraded){
                AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(magicNumber));
            }
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(magicNumber));
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        for(final AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisTurn){
            if (c.type == CardType.ATTACK) {
                glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
                break;
            }
        }
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

}