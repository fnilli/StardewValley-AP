package com.group16.stardewvalley.model.NPC;

import com.group16.stardewvalley.model.Request;
import com.group16.stardewvalley.model.agriculture.Seeds;
import com.group16.stardewvalley.model.food.*;
import com.group16.stardewvalley.model.food.FoodRecipe;
import com.group16.stardewvalley.model.items.*;
import com.group16.stardewvalley.model.map.Farm;
import com.group16.stardewvalley.model.map.FarmType;
import com.group16.stardewvalley.model.map.PlaceType;
import com.group16.stardewvalley.model.time.Season;
import com.group16.stardewvalley.model.weather.WeatherCondition;

import java.util.List;

public enum NPCType {
    //(●’◡’●) ಠ_ಠ (ᗒᗣᗕ) ( ˘ ³˘) (•̀ᴗ•́)  ﮩ٨ـﮩﮩ٨ـ♡ﮩ٨ـﮩﮩ٨ـ   ﮩ٨ـﮩﮩ٨ـ🖤ﮩ٨ـﮩﮩ٨ـ  (≧◡≦) (◡‿◡✿) (✿◠‿◠) (”__”)
    // (*^ -^*) (⊙▂⊙) (∪ ◡ ∪)  (✿ ♥‿♥)
    Sebastian("Sebastian",
            List.of("wool", "pumpkin pie", "pizza"),
            List.of(new Request("Sebastian",
                            new Iron("Iron", 150),
                            50,
                            new Diamond("Diamond", 500),
                            2,
                            0),
                    new Request("Sebastian",
                            FoodFactory.pumpkinPie(),
                            1,
                            null,
                            0,
                            5000),
                    new Request("Sebastian",
                            new Stone("Stone", 20),
                            150,
                            new Quartz("Quartz", 200),
                            50,
                            0)),
            PlaceType.Sebastian),
    Abigail("Abigail",
            List.of("stone", "iron ore", "coffee"),
            List.of(new Request("Abigail",
                            new Bar("Bar", BarType.gold, 100),
                            1,
                            null,
                            0,
                            0),
                    new Request("Abigail",
                            FoodFactory.pumpkinPie(),
                            1,
                            null,
                            0,
                            500),
                    new Request("Abigail",
                            new FoodIngredient("wheat", 100, Ingredient.WHEAT_FLOUR),
                            50,
                            FoodFactory.bread(),
                            1,
                            0)),
            PlaceType.Abigail),

    Harvey("Harvey",
            List.of("coffee", "pickle", "liquor"),
            List.of(new Request("Harvey",
                            Seeds.ACORNS,
                            12,
                            null,
                            0,
                            750),
                    new Request("Harvey", FoodFactory.salmonDinner(),
                            1,
                            null,
                            0,
                            0),
                    new Request("Harvey", FoodFactory.cookie(),
                            1,
                            FoodFactory.salad(),
                            5,
                            0)),
            PlaceType.Harvey),

    Leah("Leah",
            List.of("salad", "grape", "liquor"),
            List.of(new Request("Leah",
                            new Wood("hard wood", 15),
                            10,
                            null,
                            0,
                            500),
                    new Request("Leah", FoodFactory.salmonDinner(),
                            1,
                            new FoodRecipe("salmoon dinner recipe", 100, FoodFactory.salmonDinner()),
                            1,
                            0),
                    new Request("Leah",
                            FoodFactory.cookie(),
                            1,
                            FoodFactory.pancakes(),
                            1,
                            0)),
            PlaceType.Leah),

    Robin("Robin",
            List.of("spaghetti", "wood", "iron ingot"),
            List.of(new Request("Robin",
                            new Wood("wood", 10),
                            80,
                            null,
                            0,
                            1000),
                    new Request("Robin",
                            new Stone("stone", 20),
                            100,
                            FoodFactory.friedEgg(),
                            3,
                            0),
                    new Request("Robin",
                            new Wood("wood", 10),
                            1000,
                            null,
                            0,
                            0)),
            PlaceType.Robin);



    private String name;
    private final List<String> favoriteItems;
    private final List<Request> quests;
    private final PlaceType placeType;

    NPCType(String name,
            List<String> favoriteItems,
            List<Request> requests,
            PlaceType placeType) {
        this.name = name;
        this.favoriteItems = favoriteItems;
        this.quests = requests;
        this.placeType = placeType;
    }

    public boolean isFavorite(String name) {
        for (String favoriteName : favoriteItems) {
            if (favoriteName.equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }


    public String getName() {
        return name;
    }

    public List<Request> getQuests() {
        return quests;
    }

    public String getDialogueForSebastian(int friendshipLevel, Season season,
                                          WeatherCondition weatherCondition, int timeOfDay) {
        switch (friendshipLevel) {
            case 0: // Stranger
                switch (season) {
                    case Spring:
                        switch (weatherCondition) {
                            case SUNNY:
                                if (timeOfDay <= 12) return "You're blocking my path to the pond. Move.";
                                else return "Why are you still working? Take a break... elsewhere.";
                            case RAINY:
                                return "Rain means less work for you, lucky.";
                            case STORM:
                                return "If your crops die, don't blame me.";
                            case SNOWY:
                                return "Snow in spring? This valley's messed up.";
                        }
                    case Summer:
                        switch (weatherCondition) {
                            case SUNNY:
                                if (timeOfDay <= 12) return "Too bright out here...";
                                else return "Sunburn looks bad on you.";
                            case RAINY:
                                return "Rain's saving us from watering. Smartest worker on the farm.";
                            case STORM:
                                return "Storm's coming. Better check the hay bales won't fly away.";
                            case SNOWY:
                                return "Snow in summer? You're hallucinating from heat stroke.";
                        }
                    case Fall:
                        switch (weatherCondition) {
                            case SUNNY:
                                return "Your pumpkin patch is... adequate.";
                            case RAINY:
                                return "At least the mushrooms will grow.";
                            case STORM:
                                return "Hope you secured your scarecrows.";
                            case SNOWY:
                                return "Early winter? Typical.";
                        }
                    case Winter:
                        switch (weatherCondition) {
                            case SUNNY:
                                return "Snow glare hurts my eyes.";
                            case RAINY:
                                return "Winter rain is just cruel.";
                            case STORM:
                                return "Your animals must be freezing.";
                            case SNOWY:
                                return "Snow's quieter than your farming chatter.";
                        }
                }
            case 1: // Acquaintance
                switch (season) {
                    case Spring:
                        switch (weatherCondition) {
                            case SUNNY:
                                return "Your turnips look... fine, I guess.";
                            case RAINY:
                                return "The frogs like this weather better than you.";
                            case STORM:
                                return "Barn roof's leaking. You should fix that.";
                            case SNOWY:
                                return "Unseasonal snow. Typical.";
                        }
                    case Summer:
                        switch (weatherCondition) {
                            case SUNNY:
                                return "Your tan lines are ridiculous.";
                            case RAINY:
                                return "Ducks seem happy. That's something.";
                            case STORM:
                                return "Check your irrigation ditches.";
                            case SNOWY:
                                return "This isn't normal. Even for here.";
                        }
                    case Fall:
                        switch (weatherCondition) {
                            case SUNNY:
                                return "Your corn could use more space.";
                            case RAINY:
                                return "Mud season begins.";
                            case STORM:
                                return "Your scarecrow blew over.";
                            case SNOWY:
                                return "Early snow ruins crops. Obviously.";
                        }
                    case Winter:
                        switch (weatherCondition) {
                            case SUNNY:
                                return "Ice fishing might be tolerable today.";
                            case RAINY:
                                return "Slippery out. Watch your step.";
                            case STORM:
                                return "Firewood stockpile good?";
                            case SNOWY:
                                return "I'll help shovel if you stop talking.";
                        }
                }
            case 2: // Friend
                switch (season) {
                    case Spring:
                        switch (weatherCondition) {
                            case SUNNY:
                                return "You plant too many parsnips. Try potatoes.";
                            case RAINY:
                                return "Rain's good for your berry bushes.";
                            case STORM:
                                return "I moved your hay bales inside.";
                            case SNOWY:
                                return "Unusual snow. Check your seedlings.";
                        }
                    case Summer:
                        switch (weatherCondition) {
                            case SUNNY:
                                return "Your melons are coming along nicely.";
                            case RAINY:
                                return "Your dog keeps digging up my garden.";
                            case STORM:
                                return "Secured your beehives for you.";
                            case SNOWY:
                                return "This weather's insane. Even for you.";
                        }
                    case Fall:
                        switch (weatherCondition) {
                            case SUNNY:
                                return "Your pumpkin weights are impressive.";
                            case RAINY:
                                return "Mushroom forage might be good today.";
                            case STORM:
                                return "Fixed your broken fence post.";
                            case SNOWY:
                                return "Early frost. Cover your crops.";
                        }
                    case Winter:
                        switch (weatherCondition) {
                            case SUNNY:
                                return "Your frozen pond has decent ice.";
                            case RAINY:
                                return "Salt your walkways today.";
                            case STORM:
                                return "Stocked your animal feed.";
                            case SNOWY:
                                return "Snow's deep. Need help shoveling?";
                        }
                }
            case 3: // Close Friend
                switch (season) {
                    case Spring:
                        switch (weatherCondition) {
                            case SUNNY:
                                return "Tilled the south field early for you.";
                            case RAINY:
                                return "Rain's saving us watering time.";
                            case STORM:
                                return "I'll check the animals during the storm.";
                            case SNOWY:
                                return "Covered your seedlings with burlap.";
                        }
                    case Summer:
                        switch (weatherCondition) {
                            case SUNNY:
                                return "Your irrigation system looks efficient.";
                            case RAINY:
                                return "Ducks are happier in this weather.";
                            case STORM:
                                return "Secured your greenhouse windows.";
                            case SNOWY:
                                return "This is bizarre. Let's get inside.";
                        }
                    case Fall:
                        switch (weatherCondition) {
                            case SUNNY:
                                return "Your harvest festival display is ready.";
                            case RAINY:
                                return "Made sure your root cellar won't flood.";
                            case STORM:
                                return "Storm prep is done. Relax.";
                            case SNOWY:
                                return "Brought extra blankets for the coop.";
                        }
                    case Winter:
                        switch (weatherCondition) {
                            case SUNNY:
                                return "Ice fishing later? I'll bring coffee.";
                            case RAINY:
                                return "Check your livestock for chills.";
                            case STORM:
                                return "Stocked your firewood and pantry.";
                            case SNOWY:
                                return "Winter's quiet... almost peaceful.";
                        }
                }
            default:
                return "Invalid relationship level";
        }
    }

    public PlaceType getPlaceType() {
        return placeType;
    }


    public String getDialogueForAbigail(int friendshipLevel, Season season,
                                        WeatherCondition weatherCondition, int timeOfDay) {
        switch (friendshipLevel) {
            case 0: // Stranger
                switch (season) {
                    case Spring:
                        switch (weatherCondition) {
                            case SUNNY:
                                if (timeOfDay <= 12) return "Your crops look boring. Plant something dangerous!";
                                else return "Seen any cave carrots around here?";
                            case RAINY:
                                return "Rain makes worms come out. Great for fishing!";
                            case STORM:
                                return "Lightning might strike your scarecrow! Cool!";
                            case SNOWY:
                                return "Snow in spring? Let's have a snowball fight!";
                        }
                    case Summer:
                        switch (weatherCondition) {
                            case SUNNY:
                                return "Your melons could be weaponized. Just saying.";
                            case RAINY:
                                return "I dare you to swim in the irrigation ditch!";
                            case STORM:
                                return "Storm's perfect for ghost hunting!";
                            case SNOWY:
                                return "SUMMER SNOW?! This calls for an adventure!";
                        }
                    case Fall:
                        switch (weatherCondition) {
                            case SUNNY:
                                return "Pumpkins are nature's bowling balls.";
                            case RAINY:
                                return "Mud wrestling after chores? No? Fine.";
                            case STORM:
                                return "Bet I can stand in the storm longer than you!";
                            case SNOWY:
                                return "Early snow means early snowball fights!";
                        }
                    case Winter:
                        switch (weatherCondition) {
                            case SUNNY:
                                return "Your frozen crops look like modern art.";
                            case RAINY:
                                return "Winter rain is just sad. Let's explore the mines instead!";
                            case STORM:
                                return "Blizzards hide secrets... Wanna investigate?";
                            case SNOWY:
                                return "Built an igloo in your pasture. Your cow loves it!";
                        }
                }
            case 1: // Acquaintance
                switch (season) {
                    case Spring:
                        switch (weatherCondition) {
                            case SUNNY:
                                return "Your blue chickens are slightly less boring.";
                            case RAINY:
                                return "Found weird mushrooms near your silo. Want one?";
                            case STORM:
                                return "Your lightning rod is my new favorite decoration.";
                            case SNOWY:
                                return "Snowball ambush at noon. Be ready.";
                        }
                    case Summer:
                        switch (weatherCondition) {
                            case SUNNY:
                                return "I trained your dog to herd sheep. Success rate: 12%.";
                            case RAINY:
                                return "Your ducks formed a rebellion. I'm leading it.";
                            case STORM:
                                return "Borrowed your horse to chase tornadoes. He's fine.";
                            case SNOWY:
                                return "Made snowcones from your clean snow. Want some?";
                        }
                    case Fall:
                        switch (weatherCondition) {
                            case SUNNY:
                                return "Your biggest pumpkin could crush a man. I checked.";
                            case RAINY:
                                return "Jumped in your biggest puddle. Worth it.";
                            case STORM:
                                return "Your barn survived my 'storm stress test'.";
                            case SNOWY:
                                return "Early snowman army in your field. Don't destroy them.";
                        }
                    case Winter:
                        switch (weatherCondition) {
                            case SUNNY:
                                return "Your icicles make decent swords. For training.";
                            case RAINY:
                                return "Invented 'ice mud' in your barnyard. It's terrible.";
                            case STORM:
                                return "Your animals voted me 'Best Blizzard Buddy'.";
                            case SNOWY:
                                return "Built a snow golem. He's guarding your turnips.";
                        }
                }
            case 2: // Friend
                switch (season) {
                    case Spring:
                        switch (weatherCondition) {
                            case SUNNY:
                                return "Let's plant explosive peppers this year! What? They're real!";
                            case RAINY:
                                return "Your soggy scarecrow looks more alive than usual.";
                            case STORM:
                                return "I waterproofed your favorite tools. You're welcome.";
                            case SNOWY:
                                return "Snowball trap set by your mailbox. Watch out!";
                        }
                    case Summer:
                        switch (weatherCondition) {
                            case SUNNY:
                                return "Your sunflowers make perfect ninja stars. Theoretically.";
                            case RAINY:
                                return "Trained your ducks to quack in harmony. Mostly.";
                            case STORM:
                                return "Rewired your lightning rod for maximum zaps!";
                            case SNOWY:
                                return "Summer snow means... uh... magic? Let's roll with it!";
                        }
                    case Fall:
                        switch (weatherCondition) {
                            case SUNNY:
                                return "Your pumpkin patch is now a maze. With traps.";
                            case RAINY:
                                return "Invented 'extreme mushroom foraging'. You in?";
                            case STORM:
                                return "Your barn is now storm-proof. Probably.";
                            case SNOWY:
                                return "Early snow means early hot cocoa breaks!";
                        }
                    case Winter:
                        switch (weatherCondition) {
                            case SUNNY:
                                return "Your frozen pond is now an ice rink. With hazards.";
                            case RAINY:
                                return "Made 'winter rain soup' from your gutter. Tastes like regret.";
                            case STORM:
                                return "Your sheep and I are telling ghost stories.";
                            case SNOWY:
                                return "Built an obstacle course in your pasture. Try not to die.";
                        }
                }
            case 3: // Close Friend
                switch (season) {
                    case Spring:
                        switch (weatherCondition) {
                            case SUNNY:
                                return "Planted surprise flowers in your least fertile field!";
                            case RAINY:
                                return "Your ducks and I formed a band. We're terrible.";
                            case STORM:
                                return "Storm-proofed your coop with my secret methods!";
                            case SNOWY:
                                return "Made snow forts to protect your seedlings!";
                        }
                    case Summer:
                        switch (weatherCondition) {
                            case SUNNY:
                                return "Invented 'crop art' in your south field. Abstract!";
                            case RAINY:
                                return "Your irrigation system now has a sweet ramp!";
                            case STORM:
                                return "Chased your runaway goats back. They hate me now.";
                            case SNOWY:
                                return "Summer snowball tournament at your farm! Teams?";
                        }
                    case Fall:
                        switch (weatherCondition) {
                            case SUNNY:
                                return "Carved your pumpkins into monster faces! Scares crows AND kids!";
                            case RAINY:
                                return "Built a mudslide in your empty field. Test dummy needed!";
                            case STORM:
                                return "Your animals are safe in my secret storm bunker!";
                            case SNOWY:
                                return "Early snow means early snowboarding on your hills!";
                        }
                    case Winter:
                        switch (weatherCondition) {
                            case SUNNY:
                                return "Your icy field is perfect for 'crop skating'!";
                            case RAINY:
                                return "Invented 'rain catching' game. Bring buckets!";
                            case STORM:
                                return "Telling your cows epic blizzard survival stories!";
                            case SNOWY:
                                return "Built an igloo village for your livestock!";
                        }
                }
            default:
                return "Do I know you? (Invalid friendship level)";
        }
    }

    public String getDialogueForHarvey(int friendshipLevel, Season season,
                                       WeatherCondition weatherCondition, int timeOfDay) {
        switch (friendshipLevel) {
            case 0: // Stranger (Professional)
                switch (season) {
                    case Spring:
                        switch (weatherCondition) {
                            case SUNNY:
                                if (timeOfDay <= 12) return "Please remember sunscreen while farming today.";
                                else return "Don't forget to hydrate in this spring warmth.";
                            case RAINY:
                                return "Wet conditions increase slip hazards. Be cautious.";
                            case STORM:
                                return "Seek shelter immediately if you hear thunder.";
                            case SNOWY:
                                return "Unseasonal cold increases hypothermia risk.";
                        }
                    case Summer:
                        switch (weatherCondition) {
                            case SUNNY:
                                return "Heat stroke symptoms include dizziness and nausea.";
                            case RAINY:
                                return "Mosquito-borne illnesses peak in summer rains.";
                            case STORM:
                                return "Emergency kit stocked? Flashlights, bandages, water?";
                            case SNOWY:
                                return "This is... medically concerning. Please come for a checkup.";
                        }
                    case Fall:
                        switch (weatherCondition) {
                            case SUNNY:
                                return "Allergy season. Consider antihistamines if sneezing.";
                            case RAINY:
                                return "Damp conditions may aggravate joint pain.";
                            case STORM:
                                return "Barometric pressure changes can trigger migraines.";
                            case SNOWY:
                                return "Early frostbite warning: Cover extremities.";
                        }
                    case Winter:
                        switch (weatherCondition) {
                            case SUNNY:
                                return "Winter UV rays still damage skin. Wear protection.";
                            case RAINY:
                                return "Freezing rain is particularly hazardous.";
                            case STORM:
                                return "Have emergency heating sources ready.";
                            case SNOWY:
                                return "Watch for signs of frostbite every 30 minutes.";
                        }
                }
            case 1: // Acquaintance (Friendly Professional)
                switch (season) {
                    case Spring:
                        switch (weatherCondition) {
                            case SUNNY:
                                return "Your posture while planting concerns me. Back straight!";
                            case RAINY:
                                return "I noticed your chickens sneezing. Coop ventilation adequate?";
                            case STORM:
                                return "My clinic is open if any animals get storm anxiety.";
                            case SNOWY:
                                return "Unusual cold snap. How's your livestock's water supply?";
                        }
                    case Summer:
                        switch (weatherCondition) {
                            case SUNNY:
                                return "That sunburn looks painful. Aloe vera in my clinic.";
                            case RAINY:
                                return "Your irrigation ditches could breed mosquitoes. Check them.";
                            case STORM:
                                return "Headache from the pressure changes? I have aspirin.";
                            case SNOWY:
                                return "This weather defies medical logic. Are you feeling alright?";
                        }
                    case Fall:
                        switch (weatherCondition) {
                            case SUNNY:
                                return "Harvesting can cause repetitive strain injuries. Pace yourself.";
                            case RAINY:
                                return "Mud brings tetanus risks. Current on your vaccinations?";
                            case STORM:
                                return "Stress from the storm? Deep breathing exercises help.";
                            case SNOWY:
                                return "Early cold snap. Check your elderly animals first.";
                        }
                    case Winter:
                        switch (weatherCondition) {
                            case SUNNY:
                                return "Winter depression is common. Get enough sunlight.";
                            case RAINY:
                                return "Ice forms under rain. I've set extra bandages aside.";
                            case STORM:
                                return "Hypothermia risk is extreme tonight. Check on neighbors.";
                            case SNOWY:
                                return "Your livestock needs extra calories in this cold.";
                        }
                }
            case 2: // Friend (Caring Friend)
                switch (season) {
                    case Spring:
                        switch (weatherCondition) {
                            case SUNNY:
                                return "I packed extra electrolytes for your planting marathon.";
                            case RAINY:
                                return "Made you herbal tea for the damp weather. Clinic anytime.";
                            case STORM:
                                return "Your animals can shelter in my waiting room if needed.";
                            case SNOWY:
                                return "Brought extra thermal blankets for your coop.";
                        }
                    case Summer:
                        switch (weatherCondition) {
                            case SUNNY:
                                return "I adjusted my schedule to check farmworkers' heat stress.";
                            case RAINY:
                                return "Your duck pond needs mosquito control. I'll help after clinic.";
                            case STORM:
                                return "Prepped emergency medical kits for your barn and house.";
                            case SNOWY:
                                return "This isn't scientifically possible... but I brought hot cocoa.";
                        }
                    case Fall:
                        switch (weatherCondition) {
                            case SUNNY:
                                return "Compression gloves might help with your harvest aches.";
                            case RAINY:
                                return "I waterproofed your first aid kits. Check the redone barn one.";
                            case STORM:
                                return "Your livestock seems calmer during storms now. Good work.";
                            case SNOWY:
                                return "Early snow means early flu shots! Clinic open late today.";
                        }
                    case Winter:
                        switch (weatherCondition) {
                            case SUNNY:
                                return "Sun reflecting off snow can burn corneas. Wear goggles!";
                            case RAINY:
                                return "Salted your walkways already. Focus on your animals now.";
                            case STORM:
                                return "Stocked your farm with emergency medical supplies.";
                            case SNOWY:
                                return "Your animals' winter coats look healthy. Good nutrition!";
                        }
                }
            case 3: // Close Friend (Devoted Caretaker)
                switch (season) {
                    case Spring:
                        switch (weatherCondition) {
                            case SUNNY:
                                return "I rescheduled appointments to help with your planting.";
                            case RAINY:
                                return "Made chicken-friendly cough syrup. Let's dose them together.";
                            case STORM:
                                return "Sleeping at your farm tonight to monitor storm-stressed animals.";
                            case SNOWY:
                                return "Heated pads installed in your brooders. No frozen chicks!";
                        }
                    case Summer:
                        switch (weatherCondition) {
                            case SUNNY:
                                return "Set up shaded hydration stations for your field workers.";
                            case RAINY:
                                return "Testing natural mosquito repellents on your farm. Promising results!";
                            case STORM:
                                return "Emergency generator hooked to your milking machines. Just in case.";
                            case SNOWY:
                                return "Summer snow means... actually I have no medical protocol for this.";
                        }
                    case Fall:
                        switch (weatherCondition) {
                            case SUNNY:
                                return "Custom back brace for your harvest labor. Try it on!";
                            case RAINY:
                                return "Your muddy lanes are now medically non-slip. My special mixture!";
                            case STORM:
                                return "Storm anxiety remedies packed for every animal species you have.";
                            case SNOWY:
                                return "Winterized your entire farm medical kit. Even the fish supplies!";
                        }
                    case Winter:
                        switch (weatherCondition) {
                            case SUNNY:
                                return "Scheduled vitamin D checks for all your livestock this week.";
                            case RAINY:
                                return "Invented 'ice-proof' hoof balm for your animals. Test phase!";
                            case STORM:
                                return "Live-in doctor services activated until blizzard passes.";
                            case SNOWY:
                                return "Your farm's winter health metrics are my proudest achievement.";
                        }
                }
            default:
                return "[Medical note: Invalid friendship level detected]";
        }
    }

    public String getDialogueForLeah(int friendshipLevel, Season season,
                                     WeatherCondition weatherCondition, int timeOfDay) {
        switch (friendshipLevel) {
            case 0: // Stranger (Polite Artist)
                switch (season) {
                    case Spring:
                        switch (weatherCondition) {
                            case SUNNY:
                                if (timeOfDay <= 12) return "Your fields would make a lovely landscape painting.";
                                else return "The morning light was perfect for sketching your orchard.";
                            case RAINY:
                                return "Rain brings such rich colors to the earth.";
                            case STORM:
                                return "Nature's power is... inspiring, but please be safe.";
                            case SNOWY:
                                return "Snowdrops are pushing through! A hopeful sight.";
                        }
                    case Summer:
                        switch (weatherCondition) {
                            case SUNNY:
                                return "Your sunflowers are practically begging to be painted.";
                            case RAINY:
                                return "The sound of rain on your barn roof is quite musical.";
                            case STORM:
                                return "Such dramatic lighting! Though your crops may disagree...";
                            case SNOWY:
                                return "Summer snow? This belongs in a surrealist painting.";
                        }
                    case Fall:
                        switch (weatherCondition) {
                            case SUNNY:
                                return "Your pumpkin patch is a study in orange hues.";
                            case RAINY:
                                return "The mushrooms in your field look like fairy houses.";
                            case STORM:
                                return "Wind makes the trees dance... violently.";
                            case SNOWY:
                                return "Early snow on persimmons - nature's still life.";
                        }
                    case Winter:
                        switch (weatherCondition) {
                            case SUNNY:
                                return "The icicles on your barn are crystalline sculptures.";
                            case RAINY:
                                return "Winter rain has its own melancholy beauty.";
                            case STORM:
                                return "Blizzards create such interesting textures... from inside.";
                            case SNOWY:
                                return "Your farm under snow is a blank canvas waiting...";
                        }
                }
            case 1: // Acquaintance (Friendly Artist)
                switch (season) {
                    case Spring:
                        switch (weatherCondition) {
                            case SUNNY:
                                return "I sketched your new lambs - they're natural models!";
                            case RAINY:
                                return "Made cheese from your goat's milk. The rind needs work...";
                            case STORM:
                                return "Your scarecrow took quite a beating. Quite dramatic!";
                            case SNOWY:
                                return "Unseasonal snow makes interesting patterns in your fields.";
                        }
                    case Summer:
                        switch (weatherCondition) {
                            case SUNNY:
                                return "Your berry patch inspired a new series of watercolors.";
                            case RAINY:
                                return "The ducks seem to enjoy dancing in your puddles.";
                            case STORM:
                                return "Lightning makes your silo look quite Gothic!";
                            case SNOWY:
                                return "Summer snow? Let's make snow sculptures of your crops!";
                        }
                    case Fall:
                        switch (weatherCondition) {
                            case SUNNY:
                                return "Your corn maze is this season's best installation art.";
                            case RAINY:
                                return "Turned your excess apples into cider. Want to taste test?";
                            case STORM:
                                return "The way your hay bales held up is... artistically satisfying.";
                            case SNOWY:
                                return "Early snow on pumpkins - like nature's own gallery.";
                        }
                    case Winter:
                        switch (weatherCondition) {
                            case SUNNY:
                                return "The frost on your fences makes lovely lace patterns.";
                            case RAINY:
                                return "Winter rain makes such interesting ice formations.";
                            case STORM:
                                return "Your barn in the blizzard could inspire an epic poem.";
                            case SNOWY:
                                return "Built a little snow gallery by your pasture. Come see!";
                        }
                }
            case 2: // Friend (Creative Partner)
                switch (season) {
                    case Spring:
                        switch (weatherCondition) {
                            case SUNNY:
                                return "Let's plant a floral mandala in your south field!";
                            case RAINY:
                                return "Your muddy boot prints are accidentally artistic.";
                            case STORM:
                                return "We should make storm-damaged wood sculptures!";
                            case SNOWY:
                                return "Snow in spring? Perfect for temporary land art!";
                        }
                    case Summer:
                        switch (weatherCondition) {
                            case SUNNY:
                                return "Your sunflower rows inspired my new mural design!";
                            case RAINY:
                                return "Made 'rain harps' for your garden. The chickens hate them.";
                            case STORM:
                                return "Salvaged wood from your damaged fence for a new project!";
                            case SNOWY:
                                return "Summer snow means... ice sculptures that melt ironically!";
                        }
                    case Fall:
                        switch (weatherCondition) {
                            case SUNNY:
                                return "Your harvest colors inspired my new palette!";
                            case RAINY:
                                return "Turned your mushroom logs into a living art installation.";
                            case STORM:
                                return "The storm rearranged your scarecrows... improvement!";
                            case SNOWY:
                                return "Early snow means early yarn-bombing your trees!";
                        }
                    case Winter:
                        switch (weatherCondition) {
                            case SUNNY:
                                return "Your icy pastures are nature's glitter!";
                            case RAINY:
                                return "Made 'ice lanterns' from your broken buckets. Magical!";
                            case STORM:
                                return "Blizzard means cozy knitting with your sheep's wool!";
                            case SNOWY:
                                return "Built a snow gallery featuring your livestock!";
                        }
                }
            case 3: // Close Friend (Creative Co-Conspirator)
                switch (season) {
                    case Spring:
                        switch (weatherCondition) {
                            case SUNNY:
                                return "Planted a secret flower message in your north field!";
                            case RAINY:
                                return "Your rainy day cheese is aging beautifully in my cellar!";
                            case STORM:
                                return "Storm-watching together from your barn loft? I'll bring cider!";
                            case SNOWY:
                                return "Snow art in your fields - our secret until it melts!";
                        }
                    case Summer:
                        switch (weatherCondition) {
                            case SUNNY:
                                return "Your farm is my open-air studio today! Don't mind me painting!";
                            case RAINY:
                                return "Composed 'raindrop rhythms' using your irrigation system!";
                            case STORM:
                                return "Documenting your storm-proof barn design for my art thesis!";
                            case SNOWY:
                                return "Summer snow party in your pasture! Wear white!";
                        }
                    case Fall:
                        switch (weatherCondition) {
                            case SUNNY:
                                return "Your pumpkin patch is now an avant-garde art space!";
                            case RAINY:
                                return "Mushroom foraging then clay sculpting with your mud!";
                            case STORM:
                                return "Stormy night storytelling in your barn - I'll bring my guitar!";
                            case SNOWY:
                                return "Early snow means early holiday crafts with your wool!";
                        }
                    case Winter:
                        switch (weatherCondition) {
                            case SUNNY:
                                return "Ice sculptures in your pastures - temporary masterpieces!";
                            case RAINY:
                                return "Invented 'winter rain painting' - your fences are my canvas!";
                            case STORM:
                                return "Blizzard art retreat in your cozy barn! Supplies packed!";
                            case SNOWY:
                                return "Your farm is my winter muse. Forever grateful for this inspiration!";
                        }
                }
            default:
                return "[Artistic block - invalid friendship level]";
        }
    }

    public String getDialogueForRobin(int friendshipLevel, Season season,
                                      WeatherCondition weatherCondition, int timeOfDay) {
        switch (friendshipLevel) {
            case 0: // Stranger (Professional Carpenter)
                switch (season) {
                    case Spring:
                        switch (weatherCondition) {
                            case SUNNY:
                                if (timeOfDay <= 12) return "Good time for building - daylight lasts longer now.";
                                else return "Your fences could use some reinforcement before summer.";
                            case RAINY:
                                return "Wet wood warps. Best wait for drier weather to build.";
                            case STORM:
                                return "I'll check your structures after this storm passes.";
                            case SNOWY:
                                return "Unseasonable snow... let me know if your coop needs repairs.";
                        }
                    case Summer:
                        switch (weatherCondition) {
                            case SUNNY:
                                return "Your barn roof is sagging slightly on the north side.";
                            case RAINY:
                                return "Rain's good for spotting leaks in your buildings.";
                            case STORM:
                                return "Board up those windows! I've got extra plywood.";
                            case SNOWY:
                                return "Summer snow? That's... not in any building code I know.";
                        }
                    case Fall:
                        switch (weatherCondition) {
                            case SUNNY:
                                return "Best season for construction - cool but not cold yet.";
                            case RAINY:
                                return "Your gate's hinges are rusting. I can replace them.";
                            case STORM:
                                return "Your silo's looking sturdy. Good job reinforcing it!";
                            case SNOWY:
                                return "Early snow means early insulation checks for your barn.";
                        }
                    case Winter:
                        switch (weatherCondition) {
                            case SUNNY:
                                return "Ice dams can damage roofs. I'll check yours later.";
                            case RAINY:
                                return "Winter rain causes slick surfaces. Watch your step.";
                            case STORM:
                                return "Got emergency repairs covered if your barn takes damage.";
                            case SNOWY:
                                return "Snow load looks good on your structures so far.";
                        }
                }
            case 1: // Acquaintance (Friendly Carpenter)
                switch (season) {
                    case Spring:
                        switch (weatherCondition) {
                            case SUNNY:
                                return "I noticed your chicken coop needs new nesting boxes.";
                            case RAINY:
                                return "Your porch is perfect for rainy-day woodworking.";
                            case STORM:
                                return "Brought extra nails in case your shed needs quick fixes.";
                            case SNOWY:
                                return "Unusual snow... your animals need windbreaks?";
                        }
                    case Summer:
                        switch (weatherCondition) {
                            case SUNNY:
                                return "Your barn could use more ventilation in this heat.";
                            case RAINY:
                                return "Found some spare shingles for your leaky spots.";
                            case STORM:
                                return "Your structures held up well last storm. Good materials!";
                            case SNOWY:
                                return "This weather's crazy, but your buildings look solid.";
                        }
                    case Fall:
                        switch (weatherCondition) {
                            case SUNNY:
                                return "Perfect weather to build that new shed you wanted.";
                            case RAINY:
                                return "Your fence posts are holding up better than most.";
                            case STORM:
                                return "Pre-cut some emergency repair boards for your farm.";
                            case SNOWY:
                                return "Early snow means early winter prep for your barn.";
                        }
                    case Winter:
                        switch (weatherCondition) {
                            case SUNNY:
                                return "Sun on snow makes ice - watch for slick walkways.";
                            case RAINY:
                                return "Your roof drainage is handling this rain well.";
                            case STORM:
                                return "Got the generator ready if your power goes out.";
                            case SNOWY:
                                return "Your barn's withstanding the snow load beautifully.";
                        }
                }
            case 2: // Friend (Helpful Builder)
                switch (season) {
                    case Spring:
                        switch (weatherCondition) {
                            case SUNNY:
                                return "Let's build that greenhouse extension today!";
                            case RAINY:
                                return "Made you a custom tool rack for rainy-day organizing.";
                            case STORM:
                                return "Reinforced your barn doors against the wind.";
                            case SNOWY:
                                return "Built temporary shelters for your spring seedlings.";
                        }
                    case Summer:
                        switch (weatherCondition) {
                            case SUNNY:
                                return "Installed shade screens in your animal pens.";
                            case RAINY:
                                return "Your leaky roof inspired a new gutter design!";
                            case STORM:
                                return "Storm-proofed your chicken coop - no more escapes!";
                            case SNOWY:
                                return "Summer snow calls for... uh... emergency roof bracing?";
                        }
                    case Fall:
                        switch (weatherCondition) {
                            case SUNNY:
                                return "Built extra storage for your harvest. Surprise!";
                            case RAINY:
                                return "Waterproofed your tool shed as a rainy-day project.";
                            case STORM:
                                return "Your structures are ready for anything this storm brings!";
                            case SNOWY:
                                return "Early snow means early insulation for your coops!";
                        }
                    case Winter:
                        switch (weatherCondition) {
                            case SUNNY:
                                return "Built sun reflectors to warm your chicken run.";
                            case RAINY:
                                return "Ice-proofed your walkways with my special grit mix.";
                            case STORM:
                                return "Staying over to monitor your barn during the blizzard.";
                            case SNOWY:
                                return "Your farm is the best advertisement for my work!";
                        }
                }
            case 3: // Close Friend (Family Builder)
                switch (season) {
                    case Spring:
                        switch (weatherCondition) {
                            case SUNNY:
                                return "Surprise! I rebuilt your porch swing during lunch.";
                            case RAINY:
                                return "Made a special drying rack for all your wet gear.";
                            case STORM:
                                return "Sleeping in your barn tonight to watch for damage.";
                            case SNOWY:
                                return "Built heated shelters for your unexpected spring snow.";
                        }
                    case Summer:
                        switch (weatherCondition) {
                            case SUNNY:
                                return "Installed cooling fans in all your animal buildings!";
                            case RAINY:
                                return "Your leaky roof is now my personal challenge!";
                            case STORM:
                                return "Riding out the storm with you - brought extra tools!";
                            case SNOWY:
                                return "Summer snow means... time to build a mystery structure!";
                        }
                    case Fall:
                        switch (weatherCondition) {
                            case SUNNY:
                                return "Built you a custom cider press as a harvest gift!";
                            case RAINY:
                                return "Your muddy paths are now beautiful boardwalks!";
                            case STORM:
                                return "Storm preparations complete - even decorated your barn!";
                            case SNOWY:
                                return "Early snow means early holiday barn decorations!";
                        }
                    case Winter:
                        switch (weatherCondition) {
                            case SUNNY:
                                return "Built sun tunnels to brighten your animal sheds.";
                            case RAINY:
                                return "Ice-proofed your entire farm - my masterpiece!";
                            case STORM:
                                return "Your farm is my second home during blizzards!";
                            case SNOWY:
                                return "Every building here shows our friendship in wood and nails.";
                        }
                }
            default:
                return "[Construction error - invalid friendship level]";
        }
    }

}