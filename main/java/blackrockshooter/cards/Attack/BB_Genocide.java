package blackrockshooter.cards.Attack;

import blackrockshooter.BlackRockShooterMod;
import blackrockshooter.actions.BB_Genocide_act;
import blackrockshooter.cards.AbstractDynamicCard;
import blackrockshooter.characters.BlackRockShooter;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static blackrockshooter.BlackRockShooterMod.makeCardPath;

public class BB_Genocide extends AbstractDynamicCard {

    // Deal 7(9) Damage to ALL enemies 3 times. Any Bleed stacks become 10, otherwise Apply 3 Bleed. (End your Turn.)

    public static final String ID = BlackRockShooterMod.makeID(BB_Genocide.class.getSimpleName());
    public static final String IMG = makeCardPath("BB_Genocide.png");

    // /TEXT DECLARATION/

    private static final CardStrings cardStrings2 = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String TALK1 = cardStrings2.EXTENDED_DESCRIPTION[0];

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = BlackRockShooter.Enums.BRS_BLACK;

    private static final int COST = 3;
    private static final int DAMAGE = 7;
    private static final int UPGRADE_PLUS_DMG = 2;
    private static final int MAGIC = 3;

    // /STAT DECLARATION/


    public BB_Genocide() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new TalkAction(true, TALK1, 2.0f, 1.0f));
        AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_IRON_1"));
        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, this.damageType, AbstractGameAction.AttackEffect.SLASH_VERTICAL, true));
        AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_IRON_2"));
        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, this.damageType, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, true));
        AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_IRON_3"));
        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, this.damageType, AbstractGameAction.AttackEffect.SLASH_DIAGONAL, true));
        AbstractDungeon.actionManager.addToBottom(new BB_Genocide_act(p, magicNumber));
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            initializeDescription();
        }
    }

}