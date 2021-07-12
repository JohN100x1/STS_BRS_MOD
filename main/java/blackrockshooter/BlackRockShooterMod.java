package blackrockshooter;

import basemod.*;
import basemod.abstracts.CustomUnlockBundle;
import basemod.eventUtil.AddEventParams;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import blackrockshooter.cards.Attack.Dark_Beam;
import blackrockshooter.cards.Attack.Triple_Shot;
import blackrockshooter.cards.Power.Demonic_Power;
import blackrockshooter.cards.Power.Necrotic_Power;
import blackrockshooter.cards.Power.Psychotic_Power;
import blackrockshooter.cards.Skill.Rest;
import blackrockshooter.events.DeadMasterEvent;
import blackrockshooter.potions.*;
import blackrockshooter.relics.*;
import blackrockshooter.variables.CounterNumber;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.Exordium;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import blackrockshooter.characters.BlackRockShooter;
import blackrockshooter.util.IDCheckDontTouchPls;
import blackrockshooter.util.TextureLoader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

@SpireInitializer
public class BlackRockShooterMod implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        EditCharactersSubscriber,
        PostInitializeSubscriber {
    // Make sure to implement the subscribers *you* are using (read basemod wiki). Editing cards? EditCardsSubscriber.
    // Making relics? EditRelicsSubscriber. etc., etc., for a full list and how to make your own, visit the basemod wiki.
    public static final Logger logger = LogManager.getLogger(BlackRockShooterMod.class.getName());
    private static String modID;

    // Mod-settings settings. This is if you want an on/off savable button
    public static Properties BlackRockShooterSettings = new Properties();
    public static final String ENABLE_PLACEHOLDER_SETTINGS = "enablePlaceholder";
    public static boolean enablePlaceholder = true; // The boolean we'll be setting on/off (true/false)

    //This is for the in-game mod settings panel.
    private static final String MODNAME = "BlackRockShooterMod";
    private static final String AUTHOR = "JohN100x1"; // And pretty soon - You!
    private static final String DESCRIPTION = "A Slay the Spire mod";
    
    // =============== INPUT TEXTURE LOCATION =================
    
    // Colors (RGB)
    // Character Color
    public static final Color BRS_BLACK = CardHelper.getColor(29, 29, 29);
  
    // Card backgrounds - The actual rectangular card.
    private static final String ATTACK_BRS_BLACK = "blackrockshooterResources/images/512/bg_attack_brs_black.png";
    private static final String SKILL_BRS_BLACK = "blackrockshooterResources/images/512/bg_skill_brs_black.png";
    private static final String POWER_BRS_BLACK = "blackrockshooterResources/images/512/bg_power_brs_black.png";
    
    private static final String ENERGY_ORB_BRS_BLACK = "blackrockshooterResources/images/512/card_brs_black_orb.png";
    private static final String CARD_ENERGY_ORB = "blackrockshooterResources/images/512/card_small_orb.png";
    
    private static final String ATTACK_BRS_BLACK_PORTRAIT = "blackrockshooterResources/images/1024/bg_attack_brs_black.png";
    private static final String SKILL_BRS_BLACK_PORTRAIT = "blackrockshooterResources/images/1024/bg_skill_brs_black.png";
    private static final String POWER_BRS_BLACK_PORTRAIT = "blackrockshooterResources/images/1024/bg_power_brs_black.png";
    private static final String ENERGY_ORB_BRS_BLACK_PORTRAIT = "blackrockshooterResources/images/1024/card_default_brs_black.png";
    
    // Character assets
    private static final String BRS_BUTTON = "blackrockshooterResources/images/charSelect/BRSCharacterButton.png";
    private static final String BRS_BACKGROUND = "blackrockshooterResources/images/charSelect/BRSCharacterPortraitBG.png";
    public static final String BRS_CAMPFIRE_1 = "blackrockshooterResources/images/char/BRSCharacter/shoulder.png";
    public static final String BRS_CAMPFIRE_2 = "blackrockshooterResources/images/char/BRSCharacter/shoulder2.png";
    public static final String BRS_DEAD = "blackrockshooterResources/images/char/BRSCharacter/corpse.png";
    
    //Mod Badge - A small icon that appears in the mod settings menu next to your mod.
    public static final String BADGE_IMAGE = "blackrockshooterResources/images/Badge.png";
    
    // Atlas and JSON files for the Animations
    public static final String THE_DEFAULT_SKELETON_ATLAS = "blackrockshooterResources/images/char/BRSCharacter/skeleton.atlas";
    public static final String THE_DEFAULT_SKELETON_JSON = "blackrockshooterResources/images/char/BRSCharacter/skeleton.json";
    
    // =============== MAKE IMAGE PATHS =================
    public static String makeCardPath(String resourcePath) {
        return getModID() + "Resources/images/cards/" + resourcePath;
    }
    
    public static String makeRelicPath(String resourcePath) {
        return getModID() + "Resources/images/relics/" + resourcePath;
    }
    
    public static String makeRelicOutlinePath(String resourcePath) {
        return getModID() + "Resources/images/relics/outline/" + resourcePath;
    }
    
    public static String makeOrbPath(String resourcePath) {
        return getModID() + "Resources/images/orbs/" + resourcePath;
    }
    
    public static String makePowerPath(String resourcePath) {
        return getModID() + "Resources/images/powers/" + resourcePath;
    }

    public static String makeEventPath(String resourcePath) {
        return getModID() + "Resources/images/events/" + resourcePath;
    }

    public static String makeEnergyOrbPath(String resourcePath) {
        return getModID() + "Resources/images/char/BRSCharacter/orb/" + resourcePath;
    }

    public static String makeAnimationPath(String resourcePath) {
        return getModID() + "Resources/images/char/BRSCharacter/Spriter/" + resourcePath;
    }


    // =============== SUBSCRIBE, CREATE THE BRS_BLACK, INITIALIZE =================
    public BlackRockShooterMod() {
        logger.info("Subscribe to BaseMod hooks");
        BaseMod.subscribe(this);
        setModID("blackrockshooter");
        logger.info("Done subscribing");

        logger.info("Creating the color " + BlackRockShooter.Enums.BRS_BLACK.toString());
        BaseMod.addColor(BlackRockShooter.Enums.BRS_BLACK, BRS_BLACK, BRS_BLACK, BRS_BLACK,
                BRS_BLACK, BRS_BLACK, BRS_BLACK, BRS_BLACK,
                ATTACK_BRS_BLACK, SKILL_BRS_BLACK, POWER_BRS_BLACK, ENERGY_ORB_BRS_BLACK,
                ATTACK_BRS_BLACK_PORTRAIT, SKILL_BRS_BLACK_PORTRAIT, POWER_BRS_BLACK_PORTRAIT,
                ENERGY_ORB_BRS_BLACK_PORTRAIT, CARD_ENERGY_ORB);
        logger.info("Done creating the color");


        logger.info("Adding mod settings");
        BlackRockShooterSettings.setProperty(ENABLE_PLACEHOLDER_SETTINGS, "FALSE");
        try {
            SpireConfig config = new SpireConfig("BlackRockShooterMod", "BlackRockShooterConfig", BlackRockShooterSettings);
            config.load();
            enablePlaceholder = config.getBool(ENABLE_PLACEHOLDER_SETTINGS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("Done adding mod settings");
        
    }
    
    // ====== NO EDIT AREA ======
    public static void setModID(String ID) {
        Gson coolG = new Gson();
        InputStream in = BlackRockShooterMod.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json");
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class);
        logger.info("You are attempting to set your mod ID as: " + ID);
        if (ID.equals(EXCEPTION_STRINGS.DEFAULTID)) {
            throw new RuntimeException(EXCEPTION_STRINGS.EXCEPTION);
        } else if (ID.equals(EXCEPTION_STRINGS.DEVID)) {
            modID = EXCEPTION_STRINGS.DEFAULTID;
        } else {
            modID = ID;
        }
        logger.info("Success! ID is " + modID);
    }
    
    public static String getModID() { // NO
        return modID;
    }
    
    private static void pathCheck() {
        Gson coolG = new Gson();
        InputStream in = BlackRockShooterMod.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json");
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class);
        String packageName = BlackRockShooterMod.class.getPackage().getName();
        FileHandle resourcePathExists = Gdx.files.internal(getModID() + "Resources");
        if (!modID.equals(EXCEPTION_STRINGS.DEVID)) {
            if (!packageName.equals(getModID())) {
                throw new RuntimeException(EXCEPTION_STRINGS.PACKAGE_EXCEPTION + getModID());
            }
            if (!resourcePathExists.exists()) {
                throw new RuntimeException(EXCEPTION_STRINGS.RESOURCE_FOLDER_EXCEPTION + getModID() + "Resources");
            }
        }
    }


    // ====== YOU CAN EDIT AGAIN ======
    public static void initialize() {
        logger.info("========================= Initializing Default Mod. Hi. =========================");
        BlackRockShooterMod defaultmod = new BlackRockShooterMod();
        logger.info("========================= /Default Mod Initialized. Hello World./ =========================");
    }
    
    // =============== LOAD THE CHARACTER =================
    @Override
    public void receiveEditCharacters() {
        logger.info("Beginning to edit characters. " + "Add " + BlackRockShooter.Enums.Black_Rock_Shooter.toString());
        BaseMod.addCharacter(new BlackRockShooter("Black Rock Shooter", BlackRockShooter.Enums.Black_Rock_Shooter),
                BRS_BUTTON, BRS_BACKGROUND, BlackRockShooter.Enums.Black_Rock_Shooter);
        receiveEditPotions();
        logger.info("Added " + BlackRockShooter.Enums.Black_Rock_Shooter.toString());
    }
    
    // =============== POST-INITIALIZE =================
    @Override
    public void receivePostInitialize() {
        logger.info("Loading badge image and mod options");
        
        // Load the Mod Badge
        Texture badgeTexture = TextureLoader.getTexture(BADGE_IMAGE);
        
        // Create the Mod Menu
        ModPanel settingsPanel = new ModPanel();
        
        // Create the on/off button:
        ModLabeledToggleButton enableNormalsButton = new ModLabeledToggleButton("This is the text which goes next to the checkbox.",
                350.0f, 700.0f, Settings.CREAM_COLOR, FontHelper.charDescFont, // Position (trial and error it), color, font
                enablePlaceholder, // Boolean it uses
                settingsPanel, // The mod panel in which this button will be in
                (label) -> {}, // thing??????? idk
                (button) -> { // The actual button:
            
            enablePlaceholder = button.enabled; // The boolean true/false will be whether the button is enabled or not
            try {
                // And based on that boolean, set the settings and save them
                SpireConfig config = new SpireConfig("BlackRockShooterMod", "BlackRockShooterConfig", BlackRockShooterSettings);
                config.setBool(ENABLE_PLACEHOLDER_SETTINGS, enablePlaceholder);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        
        settingsPanel.addUIElement(enableNormalsButton); // Add the button to the settings panel. Button is a go.
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);


        // =============== EVENTS =================
        AddEventParams eventParams = new AddEventParams.Builder(DeadMasterEvent.ID, DeadMasterEvent.class) // for this specific event
                .dungeonID(Exordium.ID) // The dungeon (act) this event will appear in
                .playerClass(BlackRockShooter.Enums.Black_Rock_Shooter) // Character specific event
                .create();

        // Add the event
        BaseMod.addEvent(eventParams);
        logger.info("Done loading badge Image and mod options");
    }

    
    // ================ ADD POTIONS ===================
    public void receiveEditPotions() {
        logger.info("Beginning to edit potions");
        BaseMod.addPotion(Blood_Elixir.class, Color.RED, null, null,
                Blood_Elixir.POTION_ID, BlackRockShooter.Enums.Black_Rock_Shooter);
        BaseMod.addPotion(Reload_Potion.class, Color.YELLOW, null, null,
                Reload_Potion.POTION_ID, BlackRockShooter.Enums.Black_Rock_Shooter);
        logger.info("Done editing potions");
    }

    
    // ================ ADD RELICS ===================
    @Override
    public void receiveEditRelics() {
        logger.info("Adding relics");
        // This adds a character specific relic.
        BaseMod.addRelicToCustomPool(new Blue_Flame(), BlackRockShooter.Enums.BRS_BLACK);
        BaseMod.addRelicToCustomPool(new Purple_Flame(), BlackRockShooter.Enums.BRS_BLACK);
        BaseMod.addRelicToCustomPool(new Red_Flame(), BlackRockShooter.Enums.BRS_BLACK);
        BaseMod.addRelicToCustomPool(new Green_Flame(), BlackRockShooter.Enums.BRS_BLACK);
        BaseMod.addRelicToCustomPool(new Green_Skull(), BlackRockShooter.Enums.BRS_BLACK);
        BaseMod.addRelicToCustomPool(new Rock_Cannon(), BlackRockShooter.Enums.BRS_BLACK);
        BaseMod.addRelicToCustomPool(new King_Saw(), BlackRockShooter.Enums.BRS_BLACK);
        // This adds a relic to the Shared pool.
        BaseMod.addRelic(new Bloody_Water(), RelicType.SHARED);
        logger.info("Done adding relics!");
    }
    
    // ================ ADD CARDS ===================
    @Override
    public void receiveEditCards() {
        logger.info("Adding variables");
        pathCheck();
        logger.info("Add variables");
        BaseMod.addDynamicVariable(new CounterNumber());
        logger.info("Adding cards");
        new AutoAdd("BlackRockShooter").packageFilter("blackrockshooter.cards").setDefaultSeen(false).cards();
        logger.info("Done adding cards!");
    }

    // ================= Unlock set ====================
    public void receiveSetUnlocks() {
        BaseMod.addUnlockBundle(new CustomUnlockBundle(Triple_Shot.ID, Dark_Beam.ID, Rest.ID), BlackRockShooter.Enums.Black_Rock_Shooter, 0);
        UnlockTracker.addCard(Triple_Shot.ID);
        UnlockTracker.addCard(Dark_Beam.ID);
        UnlockTracker.addCard(Rest.ID);
        /*
        BaseMod.addUnlockBundle(new CustomUnlockBundle(AbstractUnlock.UnlockType.RELIC, BoxCutter.ID, LetterOfRequest.ID, PocketKnife.ID), BlackRockShooter.Enums.Black_Rock_Shooter, 1);
        UnlockTracker.addRelic(BoxCutter.ID);
        UnlockTracker.addRelic(LetterOfRequest.ID);
        UnlockTracker.addRelic(PocketKnife.ID);
        BaseMod.addUnlockBundle(new CustomUnlockBundle(GaleStrike.ID, Fraudulence.ID, UnstableBlock.ID), BlackRockShooter.Enums.Black_Rock_Shooter, 2);
        UnlockTracker.addCard(GaleStrike.ID);
        UnlockTracker.addCard(Fraudulence.ID);
        UnlockTracker.addCard(UnstableBlock.ID);
        BaseMod.addUnlockBundle(new CustomUnlockBundle(AbstractUnlock.UnlockType.RELIC, AcidSolution.ID, BrokenTicker.ID, CloakOfAssassin.ID), BlackRockShooter.Enums.Black_Rock_Shooter, 3);
        UnlockTracker.addRelic(AcidSolution.ID);
        UnlockTracker.addRelic(BrokenTicker.ID);
        UnlockTracker.addRelic(CloakOfAssassin.ID);*/
        /*
        BaseMod.addUnlockBundle(new CustomUnlockBundle(Demonic_Power.ID, Necrotic_Power.ID, Psychotic_Power.ID), BlackRockShooter.Enums.Black_Rock_Shooter, 4);
        UnlockTracker.addCard(Demonic_Power.ID);
        UnlockTracker.addCard(Necrotic_Power.ID);
        UnlockTracker.addCard(Psychotic_Power.ID);
        */
    }

    // ================ LOAD THE TEXT ===================
    @Override
    public void receiveEditStrings() {
        logger.info("Beginning to edit strings for mod with ID: " + getModID());
        
        // CardStrings
        BaseMod.loadCustomStringsFile(CardStrings.class,
                getModID() + "Resources/localization/eng/BlackRockShooterMod-Card-Strings.json");
        
        // PowerStrings
        BaseMod.loadCustomStringsFile(PowerStrings.class,
                getModID() + "Resources/localization/eng/BlackRockShooterMod-Power-Strings.json");
        
        // RelicStrings
        BaseMod.loadCustomStringsFile(RelicStrings.class,
                getModID() + "Resources/localization/eng/BlackRockShooterMod-Relic-Strings.json");
        
        // CharacterStrings
        BaseMod.loadCustomStringsFile(CharacterStrings.class,
                getModID() + "Resources/localization/eng/BlackRockShooterMod-Character-Strings.json");
        
        // OrbStrings
        BaseMod.loadCustomStringsFile(OrbStrings.class,
                getModID() + "Resources/localization/eng/BlackRockShooterMod-Orb-Strings.json");

        // PotionStrings
        BaseMod.loadCustomStringsFile(PotionStrings.class,
                getModID() + "Resources/localization/eng/BlackRockShooterMod-Potion-Strings.json");

        // Event Strings
        BaseMod.loadCustomStringsFile(EventStrings.class,
                getModID() + "Resources/localization/eng/BlackRockShooterMod-Event-Strings.json");

        // Event Strings
        BaseMod.loadCustomStringsFile(StanceStrings.class,
                getModID() + "Resources/localization/eng/BlackRockShooterMod-Stance-Strings.json");
        
        logger.info("Done editing strings");
    }

    // ================ LOAD THE KEYWORDS ===================
    @Override
    public void receiveEditKeywords() {
        Gson gson = new Gson();
        String k_loc = "Resources/localization/eng/BlackRockShooterMod-Keyword-Strings.json";
        String json = Gdx.files.internal(getModID() + k_loc).readString(String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json, com.evacipated.cardcrawl.mod.stslib.Keyword[].class);
        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(getModID().toLowerCase(), keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
            }
        }
    }
    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }
}
