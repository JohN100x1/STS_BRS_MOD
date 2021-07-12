package blackrockshooter.cards.Temp;

import blackrockshooter.BlackRockShooterMod;
import blackrockshooter.cards.AbstractDynamicCard;
import blackrockshooter.relics.Rock_Cannon;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import blackrockshooter.powers.Rock_Cannon_Mk2_pw;

import static blackrockshooter.BlackRockShooterMod.makeCardPath;

public class Auto_Attack extends AbstractDynamicCard {

    // Deal 2 Damage 2(3) times. Exhaust.

    public static final String ID = BlackRockShooterMod.makeID(Auto_Attack.class.getSimpleName());
    public static final String IMG = makeCardPath("Auto_Attack.png");

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = 0;
    private static final int DAMAGE = 2;
    private static final int AMOUNT = 2;
    private static final int UPGRADE_PLUS_AMOUNT = 1;

    // /STAT DECLARATION/


    public Auto_Attack() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        if (AbstractDungeon.player != null && AbstractDungeon.player.hasPower(Rock_Cannon_Mk2_pw.POWER_ID)) {
            baseMagicNumber = magicNumber = AMOUNT + AbstractDungeon.player.getPower(Rock_Cannon_Mk2_pw.POWER_ID).amount;
        } else {
            baseMagicNumber = magicNumber = AMOUNT;
        }
        baseDamage = DAMAGE;
        exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < magicNumber; i++) {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p,damage,damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        }
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_AMOUNT);
            initializeDescription();
        }
    }
}