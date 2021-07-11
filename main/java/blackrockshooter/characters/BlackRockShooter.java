package blackrockshooter.characters;

import blackrockshooter.BlackRockShooterMod;
import blackrockshooter.cards.Attack.*;
import blackrockshooter.cards.Power.*;
import blackrockshooter.cards.Skill.*;
import basemod.abstracts.CustomPlayer;
import basemod.animations.SpriterAnimation;
import blackrockshooter.ui.BRSEnergyOrb;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.cutscenes.CutscenePanel;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import blackrockshooter.relics.Blue_Flame;

import java.util.ArrayList;
import java.util.List;

import static blackrockshooter.BlackRockShooterMod.*;

public class BlackRockShooter extends CustomPlayer {
    public static final Logger logger = LogManager.getLogger(BlackRockShooterMod.class.getName());

    // =============== CHARACTER ENUMERATORS =================
    public static class Enums {
        @SpireEnum
        public static AbstractPlayer.PlayerClass Black_Rock_Shooter;
        @SpireEnum(name = "BRS_COLOR")
        public static AbstractCard.CardColor BRS_BLACK;
        @SpireEnum(name = "BRS_COLOR") @SuppressWarnings("unused")
        public static CardLibrary.LibraryType LIBRARY_COLOR;
    }


    // =============== BASE STATS =================
    public static final int ENERGY_PER_TURN = 3;
    public static final int STARTING_HP = 70;
    public static final int MAX_HP = 70;
    public static final int STARTING_GOLD = 99;
    public static final int CARD_DRAW = 5;
    public static final int ORB_SLOTS = 2;


    // =============== STRINGS =================
    private static final String ID = makeID("BRSCharacter");
    private static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString(ID);
    private static final String[] NAMES = characterStrings.NAMES;
    private static final String[] TEXT = characterStrings.TEXT;


    // =============== TEXTURES OF BIG ENERGY ORB ===============
    public static final String sprinterPath = makeAnimationPath("theDefaultAnimation.scml");
    public static final String[] orbTextures = {
            "blackrockshooterResources/images/char/BRSCharacter/orb/layer1.png",
            "blackrockshooterResources/images/char/BRSCharacter/orb/layer2.png",
            "blackrockshooterResources/images/char/BRSCharacter/orb/layer3.png",
            "blackrockshooterResources/images/char/BRSCharacter/orb/layer4.png",
            "blackrockshooterResources/images/char/BRSCharacter/orb/layer5.png",
            "blackrockshooterResources/images/char/BRSCharacter/orb/layer6.png",
            "blackrockshooterResources/images/char/BRSCharacter/orb/layer1d.png",
            "blackrockshooterResources/images/char/BRSCharacter/orb/layer2d.png",
            "blackrockshooterResources/images/char/BRSCharacter/orb/layer3d.png",
            "blackrockshooterResources/images/char/BRSCharacter/orb/layer4d.png",
            "blackrockshooterResources/images/char/BRSCharacter/orb/layer5d.png",};


    // =============== CHARACTER CLASS START =================
    public BlackRockShooter(String name, PlayerClass setClass) {
        super(name, setClass, new BRSEnergyOrb(orbTextures), new SpriterAnimation(sprinterPath));


        // =============== TEXTURES, ENERGY, LOADOUT =================
        initializeClass(null, BRS_CAMPFIRE_2, BRS_CAMPFIRE_1, BRS_DEAD,
                getLoadout(), 20.0F, -10.0F, 220.0F, 290.0F, new EnergyManager(ENERGY_PER_TURN));

        // =============== ANIMATIONS =================
        loadAnimation(
                THE_DEFAULT_SKELETON_ATLAS,
                THE_DEFAULT_SKELETON_JSON,
                1.0f);
        AnimationState.TrackEntry e = state.setAnimation(0, "animation", true);
        e.setTime(e.getEndTime() * MathUtils.random());

        // =============== TEXT BUBBLE LOCATION =================
        dialogX = (drawX + 0.0F * Settings.scale); // set location for text bubbles
        dialogY = (drawY + 220.0F * Settings.scale); // you can just copy these values
    }


    // Starting description and load-out
    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(NAMES[0], TEXT[0],
                STARTING_HP, MAX_HP, ORB_SLOTS, STARTING_GOLD, CARD_DRAW, this, getStartingRelics(),
                getStartingDeck(), false);
    }

    // Starting Deck
    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();

        logger.info("Begin loading starter Deck Strings");

        // Basic
        retVal.add(Strike_Black.ID);     // atk
        retVal.add(Strike_Black.ID);     // atk
        retVal.add(Strike_Black.ID);     // atk
        retVal.add(Strike_Black.ID);     // atk
        retVal.add(Defend_Black.ID);     // def
        retVal.add(Defend_Black.ID);     // def
        retVal.add(Defend_Black.ID);     // def
        retVal.add(Defend_Black.ID);     // def
        retVal.add(Vulcannon.ID);        // atk
        retVal.add(Charge_Shot.ID);      // atk (bonus dmg)

        // Common
        //retVal.add(Triple_Shot.ID);      // atk ################################## change pic
        //retVal.add(Rapid_Strike.ID);     // atk
        //retVal.add(Fade_Away.ID);        // def
        //retVal.add(Drop_of_Blood.ID);    // bleed
        //retVal.add(Run_Away.ID);         // draw + exhaust
        //retVal.add(Breakthrough.ID);     // atk + str
        //retVal.add(Backstep.ID);         // atk + def
        //retVal.add(Sidestep.ID);         // def
        //retVal.add(Rapid_Cannon.ID);     // atk + draw
        //retVal.add(Volcain_Cannon.ID);   // atk
        //retVal.add(See_through.ID);      // def + dex
        //retVal.add(Crouch.ID);           // def + draw
        //retVal.add(Jump_Shot.ID);        // atk + def + draw
        //retVal.add(Pierce.ID);           // atk + vuln.
        //retVal.add(Photon_Shower.ID);    // atk (AOE)
        //retVal.add(Repel.ID);            // atk (AOE)
        //retVal.add(Discern.ID);          // vuln. + weak
        //retVal.add(Outspeed.ID);         // def + slow
        //retVal.add(Annoying_Skull.ID);   // skull

        // Uncommon
        //retVal.add(Dark_Beam.ID);        // bleed
        //retVal.add(Dark_Comet.ID);       // bleed (AOE)
        //retVal.add(Rain_of_Blood.ID);    // bleed
        //retVal.add(Iksa_Blade.ID);       // atk + bleed
        //retVal.add(Toxic_Strike.ID);     // atk + poison
        //retVal.add(Ruined_Cube.ID);      // artifact
        //retVal.add(Burst_Shot.ID);       // atk (bonus dmg)
        //retVal.add(React.ID);            // str or dex
        //retVal.add(Seeing_Everything.ID);// energy
        //retVal.add(Parry.ID);            // def (special)
        //retVal.add(Whack.ID);            // atk + str
        //retVal.add(Bargain.ID);          // card manipulation
        //retVal.add(Duck.ID);             // def + dex
        //retVal.add(Charge.ID);           // atk (bonus energy, draw)
        //retVal.add(Pursue.ID);           // atk (bonus energy, draw)
        //retVal.add(Ponder.ID);           // scry + draw
        //retVal.add(Stun_Snipe.ID);       // atk + stun
        //retVal.add(Open_Fire.ID);        // atk
        //retVal.add(Outrage.ID);          // wraith stance (confused)
        //retVal.add(Explosive_Shot.ID);   // atk + exhaust
        //retVal.add(Auto_Reloader.ID);    // atk buff
        //retVal.add(Endure_no_Pain.ID);   // def
        //retVal.add(Evade.ID);            // def
        //retVal.add(Unreal_Motion.ID);    // def
        //retVal.add(Unrelenting_Fury.ID); // str
        //retVal.add(Sanctuary.ID);        // dex
        //retVal.add(Blue_Aura.ID);        // str + thorns
        //retVal.add(Aggressor.ID);        // str + dex
        //retVal.add(Rock_Cannon_Mk2.ID);  // atk buff
        //retVal.add(Rehabilitate.ID);     // card manipulation
        //retVal.add(Tactical_Manoeuvre.ID); // card manipulation
        //retVal.add(Lethal_Blast.ID);     // x2/x3 poison
        //retVal.add(Obnoxious_Skulls.ID); // skull
        //retVal.add(Noxious_Horde.ID);    // focus + orb slot
        //retVal.add(Keep_Firing.ID);      // atk
        //retVal.add(Attack_Trail.ID);     // block

        // Rare
        //retVal.add(Double_Team.ID);      // x2 cards played
        //retVal.add(Obliteration_Cannon.ID);// atk
        //retVal.add(Over_Limit.ID);       // dex
        //retVal.add(BB_Genocide.ID);      // atk + bleed (AOE)
        //retVal.add(Vortex_Cannon.ID);    // atk
        //retVal.add(Perforate.ID);        // bleed
        //retVal.add(Menacing_Aura.ID);    // str loss + slow (AOE)
        //retVal.add(Madness_Rain.ID);     // atk (AOE)
        //retVal.add(Star_Shot.ID);        // atk (bonus dmg)
        //retVal.add(Execute.ID);          // atk (perm dmg increase)
        //retVal.add(Rest.ID);             // heal
        //retVal.add(Re_Ignition.ID);      // energy
        //retVal.add(Demonic_Power.ID);    // str
        //retVal.add(Necrotic_Power.ID);   // poison
        //retVal.add(Psychotic_Power.ID);  // insane
        //retVal.add(Deadly_Decree.ID);    // skull buff

        // TODO: Make card scale from cards played this combat
        // TODO: Add a signature power for Black Rock Shooter
        // TODO: Change dark comet to something else
        // TODO: Change dark blade to something else
        // TODO: change effect of rock cannon relic
        return retVal;
    }

    // Starting Relics	
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(Blue_Flame.ID);
        UnlockTracker.markRelicAsSeen(Blue_Flame.ID);
        return retVal;
    }

    // character Select screen effect
    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA("BLUNT_FAST", 1.25f); // Sound Effect
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT,
                false); // Screen Effect
    }

    // character Select on-button-press sound effect
    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "BLUNT_FAST";
    }

    // Ascension 14 or higher max HP loss.
    @Override
    public int getAscensionMaxHPLoss() {
        return 4;
    }

    // Should return the card color enum to be associated with your character.
    @Override
    public AbstractCard.CardColor getCardColor() {
        return Enums.BRS_BLACK;
    }

    // Should return a color object to be used to color the trail of moving cards
    @Override
    public Color getCardTrailColor() {
        return BlackRockShooterMod.BRS_BLACK;
    }

    // Energy Orb font
    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontBlue;
    }

    // Should return class name as it appears in run history screen.
    @Override
    public String getLocalizedCharacterName() {
        return NAMES[0];
    }

    //Which card should be obtainable from the Match and Keep event?
    @Override
    public AbstractCard getStartCardForEvent() {
        return new Vulcannon();
    }

    // The class name as it appears next to your player name in-game
    @Override
    public String getTitle(AbstractPlayer.PlayerClass playerClass) {
        return NAMES[1];
    }

    // Should return a new instance of your character, sending name as its name parameter.
    @Override
    public AbstractPlayer newInstance() {
        return new BlackRockShooter(name, chosenClass);
    }

    // Run History mini card image colour
    @Override
    public Color getCardRenderColor() {
        return BlackRockShooterMod.BRS_BLACK;
    }

    // Spire Heart Attack screen tint
    @Override
    public Color getSlashAttackColor() {
        return BlackRockShooterMod.BRS_BLACK;
    }

    // Spire Heart Attack animation
    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY};
    }

    // For the Spire Heart Event
    @Override
    public String getSpireHeartText() {
        return TEXT[1];
    }

    // For the Vampire Event
    @Override
    public String getVampireText() {
        return TEXT[2];
    }

    @Override
    public Texture getCutsceneBg() {
        return ImageMaster.loadImage("blackrockshooterResources/images/char/BRSCharacter/cutscene/bg.png");
    }

    @Override
    public List<CutscenePanel> getCutscenePanels() {
        List<CutscenePanel> panels = new ArrayList();
        panels.add(new CutscenePanel("blackrockshooterResources/images/char/BRSCharacter/cutscene/scene1.png", "ATTACK_MAGIC_BEAM"));
        panels.add(new CutscenePanel("blackrockshooterResources/images/char/BRSCharacter/cutscene/scene2.png"));
        panels.add(new CutscenePanel("blackrockshooterResources/images/char/BRSCharacter/cutscene/scene3.png"));
        return panels;
    }
}
