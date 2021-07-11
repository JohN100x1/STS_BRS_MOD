package blackrockshooter.events;

import blackrockshooter.BlackRockShooterMod;
import blackrockshooter.cards.Skill.Annoying_Skull;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import java.util.ArrayList;

import static blackrockshooter.BlackRockShooterMod.makeEventPath;

public class DeadMasterEvent extends AbstractImageEvent {


    public static final String ID = BlackRockShooterMod.makeID("DeadMasterEvent");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);

    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    public static final String IMG = makeEventPath("DeadMasterEvent.png");

    private int screenNum = 0; // The initial screen we will see when encountering the event - screen 0;
    private final int healthDamage;
    int gainGoldAmt = 100;
    int loseGoldAmt = 50;
    int cardsToRemove = 2;

    public DeadMasterEvent() {
        super(NAME, DESCRIPTIONS[0], IMG);
        int gold = AbstractDungeon.player.gold;
        float HEALTH_LOSS_PERCENTAGE = 0.1F;
        float HEALTH_LOSS_PERCENTAGE_LOW_ASCENSION = 0.07F;

        if (AbstractDungeon.ascensionLevel >= 15) {
            healthDamage = (int) ((float) AbstractDungeon.player.maxHealth * HEALTH_LOSS_PERCENTAGE);
        } else {
            healthDamage = (int) ((float) AbstractDungeon.player.maxHealth * HEALTH_LOSS_PERCENTAGE_LOW_ASCENSION);
        }

        // The first dialogue options available to us.

        // Obey - Remove all Strikes. Add 5 Annoying Skulls. Lose max HP.
        imageEventText.setDialogOption(OPTIONS[0] + healthDamage + OPTIONS[1], new Annoying_Skull());

        // Defy - Gain Gold. Lose HP.
        imageEventText.setDialogOption(OPTIONS[2] + gainGoldAmt + OPTIONS[3] + healthDamage + OPTIONS[4]);

        // Offer - Remove 2 cards. Lose Gold.
        if (gold >= loseGoldAmt){
            imageEventText.setDialogOption(OPTIONS[5] + cardsToRemove + OPTIONS[6] + loseGoldAmt + OPTIONS[7], false);
        } else {
            imageEventText.setDialogOption(OPTIONS[8] + loseGoldAmt + OPTIONS[9], true);
        }

        // Leave
        imageEventText.setDialogOption(OPTIONS[10]);
    }

    @Override
    protected void buttonEffect(int i) { // This is the event:
        switch (screenNum) {
            // Start Screen
            case 0:
                switch (i) {
                    case 0:
                        // Obey
                        // Replace all Strikes and lose max HP
                        replaceAttacks();
                        AbstractDungeon.player.decreaseMaxHealth(healthDamage);

                        this.imageEventText.loadImage(makeEventPath("DeadMasterEvent1.png"));
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[10]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 1;
                        break;
                    case 1:
                        // Defy
                        // Gain Gold and lose HP
                        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.MED, false);
                        CardCrawlGame.sound.play("BLUNT_FAST");
                        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.MED, false);
                        CardCrawlGame.sound.play("ATTACK_HEAVY");
                        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.MED, false);
                        CardCrawlGame.sound.play("BLUNT_HEAVY");
                        AbstractDungeon.player.gainGold(gainGoldAmt);
                        AbstractDungeon.player.damage(new DamageInfo(null, healthDamage));

                        this.imageEventText.loadImage(makeEventPath("DeadMasterEvent2.png"));
                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[10]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 1;
                        break;
                    case 2:
                        // Offer
                        // Remove cards. Lose gold.
                        AbstractDungeon.player.loseGold(loseGoldAmt);
                        if (CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards()).size() > 0) {
                            AbstractDungeon.gridSelectScreen.open(
                                    CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards()),
                                    cardsToRemove, OPTIONS[11] + cardsToRemove + OPTIONS[12], false, false, false, true);
                        }

                        this.imageEventText.updateBodyText(DESCRIPTIONS[3]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[10]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 1;
                        break;
                    case 3:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[4]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[10]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 1;
                        break;
                }
                break;
            case 1:
                if (i == 0) {
                    openMap();
                }
                break;
        }
    }

    private void replaceAttacks() {
        ArrayList<AbstractCard> masterDeck = AbstractDungeon.player.masterDeck.group;

        int i;
        int cardsRemoved = 0;
        for(i = masterDeck.size() - 1; i >= 0; --i) {
            AbstractCard card = masterDeck.get(i);
            if (card.tags.contains(AbstractCard.CardTags.STARTER_STRIKE)) {
                AbstractDungeon.player.masterDeck.removeCard(card);
                ++cardsRemoved;
            }
        }

        for(i = 0; i < cardsRemoved; ++i) {
            AbstractCard c = new Annoying_Skull();
            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
        }

    }

    public void update() {
        super.update();
        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards){
                AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(c, (float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2)));
                AbstractDungeon.player.masterDeck.removeCard(c);
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }

    }

}