package com.group16.stardewvalley.model.menu;

public enum GameMenuCommands implements CommandsInterface{
    NewGame("\\s*game\\s+new\\s+-u\\s*(?<usernames>.+?)"),
    Username( "\\s*[a-zA-Z0-9-]+\\s*"),
    ChooseMap("\\s*game\\s+map\\s+(?<mapNumber>\\d+)\\s*"),
    LoadGame("\\s*load\\s+game\\s*" ),
    Exit("\\s*exit\\s+game\\s*" ),
    CurrentTurn("\\s*current\\s+turn\\s*" ),
    NextTurn("\\s*next\\s+turn\\s*" ),
    ForceTerminateVote("\\s*force-terminate\\s+vote\\s*"),
    ShowCurrentMenu("\\s*show\\s+current\\s+menu\\s*"),
    ExitMenu("\\s*menu\\s+exit\\s*"),

    Walk("\\s*walk\\s+-l\\s+(?<x>\\d+)\\s*,\\s*(?<y>\\d+)\\s*"),
    PrintMap("\\s*print\\s+map\\s+-l\\s+(?<x>\\d+)\\s*,\\s*(?<y>\\d+)\\s+-s\\s+(?<size>\\d+)\\s*"),
    HelpReadingMap("\\s*help\\s+reading\\s+map\\s*"),

    //Main menu commands
    ChangeMenu("\\s*menu\\s+enter\\s+(?<MenuName>.+)\\s*"),
    Logout("\\s*user\\s+logout\\s*"),

    //timeDate commands
    Time("time"),
    Date("date"),
    DateTime("datetime"),
    DayOfWeek("day of the week"),
    CheatAdvanceTime("cheat advance time\\s*(?<amount>\\d+)\\s*h"),
    CheatAdvanceDate("cheat advance date\\s*(?<amount>\\d+)\\s*d"),
    Season("season"),


    //NPC commands
    MeetNPC("\\s*meet\\s+NPC\\s+(?<name>\\S+)\\s*"),
    GiftNPC("\\s*gift\\s+NPC\\s+(?<name>\\S+)\\s+-i\\s+(?<item>\\S+)\\s*"),
    FriendshipNPC("\\s*friendship\\s+NPC\\s+list\\s*"),
    QuestsList("\\s*quests\\s+list\\s*"),
    QuestsFinish("\\s*quests\\s+finish\\s+-i\\s+(?<index>\\d+)\\s*"),

    //shop commands
    ShopBuildCoopBarn("\\S*build\\s+-a\\s+(?<buildingName>\\S+)\\s+-l\\s+(?<x>\\d+)\\s*,\\s*(?<y>\\d+)\\s*"),
    BuyAnimal("^buy\\s+animal\\s+-a\\s+(?<animal>\\S+)\\s+-n\\s+(?<name>\\S+)\\s*$ "),
    ShowAllProducts("^\\s*show\\s*all\\s*products\\s*$"),
    ShowAllAvailableProducts("^\\s*show\\s*all\\s*available\\s*products\\s*$"),
    Purchase("^\\s*purchase\\s*(?<productName>\\S+)(?:\\s+-n\\s+(?<count>\\d+))?\\s*$"),
    Sell("^\\s*sell\\s*(?<productName>\\S+)(?:\\s+-n(?<count>\\d+))?\\s*$"),
    CraftInfo("\\s*craft info -n (?<name>.+?)\\s*"),
    TreeInfo("tree info -n (?<name>.+?)\\s*"),
    ForagingInfo("foraging info -n (?<name>.+?)\\s*"),
    PlantSeed("plant -s (?<seed>.+?) -d (?<dir>.+?)"),
    ShowPlant("show plant -l (?<x>\\d+),\\s*(?<y>\\d+)"),
    Fertilize("fertilize -f (?<fertilizer>.+?) -d (?<dir>.+?)"),
    HowMuchWater("how much water"),
    PutFood("cooking refrigerator put (?<food>.+?)"),
    PickFood("cooking refrigerator pick (?<food>.+?)"),
    CookingRecipes("cooking show recipes"),
    PrepareFood("cooking prepare (?<food>.+?)"),
    EatFood("eat (?<food>.+?)"),

    //Animals
    Pet("\\s*pet\\s+-n\\s+(?<name>\\S+)\\s*"),
    CheatSetAnimalFriendship("\\s*cheat\\s+set\\s+friendship\\s+-n\\s+(?<name>\\S+)\\s+-c\\s+(?<count>\\d+)\\s*"),
    ShowAnimalInfo("\\s*animals\\s*"),
    ShepherdAnimals("^\\s*shepherd\\s+animals\\s+-n\\s+(?<name>\\S+)\\s+-l\\s+(?<x>\\d+)\\s*,\\s*(?<y>\\d+)\\s*$"),
    FeedHay("\\s*feed\\s+hay\\s+-n\\s+(?<name>\\S+)\\s*"),
    AnimalProduces("\\s*produces\\s*"),
    CollectProduct("\\s*collect\\s+produce\\s+-n\\s+(?<name>\\S+)\\s*"),
    SellAnimal("\\s*sell\\s+animal\\s+-n\\s+(?<name>\\S+)\\s*"),
    Fishing("fishing -p (?<fishingPole>.+?)"),


    //weather
    Thor("cheat Thor -l (?<x>\\d+)\\s*,\\s*(?<y>\\d+)"),
    Weather("weather"),
    WeatherForecast("weather forecast"),
    ChangeWeather("cheat weather set (?<Type>.+?)"),
    BuildGreenHouse("greenhouse build"),


    //Crafting commands
    ShowRecipes("\\s*crafting\\s+show\\s+recipes\\s*"),
    Craft("\\s*crafting\\s+craft\\s+(?<itemName>.+)\\s*"),
    PlaceItem("\\s*place\\s+item\\s+-n\\s+(?<itemName>.+)\\s+-d\\s+(?<direction>\\S+)\\s*"),
    CheatAddItem("\\s*cheat\\s+add\\s+item\\s+-n\\s+(?<itemName>.+)\\s+-c\\s+(?<count>\\d+)\\s*"),
    LearnCraftingRecipe("\\s*learn\\s+crafting\\s+recipe\\s+-n\\s+(?<itemName>.+)\\s*"),

    // player
    Show_Coin("^\\s*show\\s*coin\\s*"),
    Cheat_Set_friendship("^\\s*set\\s*friendship\\s*level\\s*-u\\s*(?<username>\\S+)\\s*-a\\s*(?<amount>\\d+)\\s*$"),
    Show_Inventory("^\\s*show\\s*inventory\\s*$"),
    Cheat_Add_To_Inventory("^\\s*cheat\\s*add\\s*(?<itemName>\\S+)\\s*(?<count>\\d+)\\s*$"),

    //Artisan commands
    ArtisanUse("\\s*artisan\\s+use\\s+(?<artisanName>\\S+)\\s+(?<itemsName>.+)\\s*"),
    ArtisanGet("\\s*artisan\\s+get\\s+(?<artisanName>\\S+)\\s*"),

    //Trade
    StartTrade("\\s*start\\s+trade\\s*"),
    Trade("\\s*\\s+-u\\s+(?<username>\\S+)\\s+-t\\s+(?<type>\\S+)\\s+-i\\s+(?<amount>\\d+)\\s+\\s*$"),
    TradeList("\\s*trade\\s+list\\s*"),
    TradeResponse("\\s*trade\\s+response\\s+(-accept|-reject)\\s+-i\\s+(?<id>\\S+)\\s*"),
    TradeHistory("\\s*trade\\s+history\\s*"),




    // relationship commands
    Friendship("^\\s*friendships\\s*$"),
    Talk("^\\s*talk\\s*-u\\s*(?<username>\\S+)\\s*-m\\s*(?<message>.+?)$"),
    TalkHistory("^\\s*talk\\s*history\\s*-u\\s*(?<username>\\S+)\\s*$"),
    Gift("^\\s*gift\\s*-u\\s*(?<username>\\S+)\\s*-i\\s*(?<itemName>\\S+)\\s*-a\\s*(?<amount>\\d+)\\s*$"),
    GiftList("^\\s*gift\\s*list\\s*$"),
    GiftRate("^\\s*gift\\s*rate\\s*-i\\s*(?<giftNumber>\\d+)\\s*-r\\s*(?<rate>\\d+)\\s*$"),
    GiftHistory("^\\s*gift\\s*history\\s*-u\\s*(?<username>\\S+)\\s*$"),
    Hug("^\\s*hug\\s*-u\\s*(?<username>\\S+)\\s*$"),
    Flower("^\\s*flower\\s*-u\\s*(?<username>\\S+)\\s*$"),
    AskMarriage("^\\s*ask\\s*marriage\\s*-u\\s*(?<username>\\S+)\\s*-r\\s*(?<ring>\\S+)\\s*$"),
    Respond("^\\s*respond\\s+(?<action>accept|reject)\\s+-u\\s+(?<username>\\S+)\\s*$"),
    Show_Notification("^\\s*show\\s*notification\\s*"),

    Cheat_Add_Flower("^\\s*add\\s*flower\\s*$"),
    Cheat_Set_Position("^\\s*cheat\\s*set\\s*position\\s*(?<x>\\d+)\\s*(?<y>\\d+)\\s*$"),
    Cheat_Set_Location("^\\s*cheat\\s*(?<location>\\S+)\\s*\\s*");

    private final String pattern;

    GameMenuCommands(String pattern) {
        this.pattern = pattern;
    }
    @Override
    public String getPattern() {
        return pattern;
    }
}

