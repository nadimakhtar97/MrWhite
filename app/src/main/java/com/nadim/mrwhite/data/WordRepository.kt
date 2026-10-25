package com.nadim.mrwhite.data

import com.nadim.mrwhite.model.Category
import com.nadim.mrwhite.model.Difficulty
import com.nadim.mrwhite.model.WordPair

object WordRepository {
    private val handpickedWordPairs = buildList {
        addAll(
            listOf(
                // Food & Drink
                WordPair("Cappuccino", "Espresso", Category.FOOD_AND_DRINK, Difficulty.EASY),
                WordPair("Burger", "Sandwich", Category.FOOD_AND_DRINK, Difficulty.EASY),
                WordPair("Pizza", "Calzone", Category.FOOD_AND_DRINK, Difficulty.MEDIUM),
                WordPair("Pancake", "Waffle", Category.FOOD_AND_DRINK, Difficulty.EASY),
                WordPair("Sushi", "Sashimi", Category.FOOD_AND_DRINK, Difficulty.MEDIUM),
                WordPair("Lemonade", "Limeade", Category.FOOD_AND_DRINK, Difficulty.HARD),
                WordPair("Croissant", "Brioche", Category.FOOD_AND_DRINK, Difficulty.HARD),

                // Animals
                WordPair("Owl", "Hawk", Category.ANIMALS, Difficulty.EASY),
                WordPair("Dolphin", "Porpoise", Category.ANIMALS, Difficulty.MEDIUM),
                WordPair("Leopard", "Cheetah", Category.ANIMALS, Difficulty.MEDIUM),
                WordPair("Frog", "Toad", Category.ANIMALS, Difficulty.EASY),
                WordPair("Alligator", "Crocodile", Category.ANIMALS, Difficulty.HARD),
                WordPair("Butterfly", "Moth", Category.ANIMALS, Difficulty.EASY),

                // Occupations
                WordPair("Police Officer", "Detective", Category.OCCUPATIONS, Difficulty.EASY),
                WordPair("Doctor", "Surgeon", Category.OCCUPATIONS, Difficulty.EASY),
                WordPair("Pilot", "Astronaut", Category.OCCUPATIONS, Difficulty.MEDIUM),
                WordPair("Chef", "Baker", Category.OCCUPATIONS, Difficulty.EASY),
                WordPair("Lawyer", "Judge", Category.OCCUPATIONS, Difficulty.MEDIUM),

                // Places
                WordPair("Beach", "Desert", Category.PLACES, Difficulty.EASY),
                WordPair("Hospital", "Clinic", Category.PLACES, Difficulty.MEDIUM),
                WordPair("Library", "Bookstore", Category.PLACES, Difficulty.EASY),
                WordPair("Museum", "Art Gallery", Category.PLACES, Difficulty.MEDIUM),
                WordPair("Hotel", "Motel", Category.PLACES, Difficulty.EASY),

                // Objects
                WordPair("Laptop", "Tablet", Category.OBJECTS, Difficulty.EASY),
                WordPair("Guitar", "Ukulele", Category.OBJECTS, Difficulty.EASY),
                WordPair("Clock", "Watch", Category.OBJECTS, Difficulty.EASY),
                WordPair("Pencil", "Pen", Category.OBJECTS, Difficulty.EASY),
                WordPair("Sunglasses", "Goggles", Category.OBJECTS, Difficulty.MEDIUM)
            )
        )

        addAll(
            pairEntries(Category.FOOD_AND_DRINK, Difficulty.EASY, """
                Cortado|Flat White
                Latte|Cappuccino
                Americano|Long Black
                Macchiato|Piccolo
                Ristretto|Lungo
                Cold Brew|Iced Coffee
                Frappuccino|Frappe
                Earl Grey|English Breakfast Tea
                Matcha|Sencha
                Jasmine Tea|Oolong Tea
                Chamomile Tea|Peppermint Tea
                Masala Chai|Thai Tea
                Ginger Tea|Lemon Tea
                Orange Juice|Grapefruit Juice
                Apple Juice|Pear Juice
                Milkshake|Malt
                Gelato|Sorbet
                Pain au Chocolat|Chocolate Croissant
                Danish Pastry|Cinnamon Roll
                Scone|Biscuit
            """.trimIndent())
        )
        addAll(
            pairEntries(Category.FOOD_AND_DRINK, Difficulty.MEDIUM, """
                Baguette|Ciabatta
                Brioche|Challah
                Macaron|Meringue
                Brownie|Blondie
                Cheesecake|Lemon Tart
                Tiramisu|Trifle
                Custard|Panna Cotta
                Ravioli|Tortellini
                Penne|Rigatoni
                Fettuccine|Linguine
                Lasagna|Cannelloni
                Ramen|Udon
                Pho|Bun Bo Hue
                Soba|Somen
                Dim Sum|Bao
                Gyoza|Potsticker
                Churro|Beignet
                Eclair|Cream Puff
                Focaccia|Naan
                Pita|Lavash
            """.trimIndent())
        )
        addAll(
            pairEntries(Category.FOOD_AND_DRINK, Difficulty.HARD, """
                Hummus|Baba Ganoush
                Falafel|Kibbeh
                Chowder|Bisque
                Gazpacho|Vichyssoise
                Minestrone|Vegetable Soup
                Risotto|Paella
                Polenta|Grits
                Couscous|Bulgur
                Kimchi|Sauerkraut
                Prosciutto|Pancetta
                Chorizo|Salami
                Brie|Camembert
                Gouda|Edam
                Cheddar|Colby
                Roquefort|Gorgonzola
                Burrito|Enchilada
                Taco|Tostada
                Quesadilla|Arepa
                Baklava|Kunafa
                Porridge|Oatmeal
            """.trimIndent())
        )

        addAll(
            pairEntries(Category.ANIMALS, Difficulty.EASY, """
                Fox|Coyote
                Wolf|Dingo
                Seal|Sea Lion
                Walrus|Manatee
                Turtle|Tortoise
                Newt|Salamander
                Jaguar|Tiger
                Rabbit|Hare
                Llama|Alpaca
                Horse|Donkey
                Bison|Buffalo
                Moose|Elk
                Deer|Antelope
                Eagle|Falcon
                Hawk|Kestrel
                Raven|Crow
                Swan|Goose
                Penguin|Puffin
                Zebra|Giraffe
                Otter|Mink
            """.trimIndent())
        )
        addAll(
            pairEntries(Category.ANIMALS, Difficulty.MEDIUM, """
                Orca|Beluga
                Humpback Whale|Blue Whale
                Sperm Whale|Fin Whale
                Harbor Seal|Gray Seal
                Fur Seal|Elephant Seal
                Macaque|Baboon
                Chimpanzee|Bonobo
                Gorilla|Orangutan
                Lemur|Tarsier
                Kangaroo|Wallaby
                Koala|Wombat
                Meerkat|Mongoose
                Badger|Wolverine
                Stoat|Ferret
                House Cat|Wildcat
                Bobcat|Lynx
                Puffin|Auk
                Cormorant|Gannet
                Heron|Egret
                Crane|Stork
            """.trimIndent())
        )
        addAll(
            pairEntries(Category.ANIMALS, Difficulty.HARD, """
                Snail|Slug
                Clam|Oyster
                Mussel|Scallop
                Octopus|Cuttlefish
                Squid|Nautilus
                Lobster|Crayfish
                Crab|Hermit Crab
                Shrimp|Prawn
                Bee|Wasp
                Ant|Termite
                Dragonfly|Damselfly
                Gecko|Skink
                Chameleon|Iguana
                Cobra|Mamba
                Viper|Rattlesnake
                Python|Boa
                Salamander|Axolotl
                Newt|Mudpuppy
                Porcupine|Hedgehog
                Sloth|Anteater
            """.trimIndent())
        )

        addAll(
            pairEntries(Category.OCCUPATIONS, Difficulty.EASY, """
                Paramedic|Emergency Medical Technician
                Firefighter|Rescue Firefighter
                General Practitioner|Family Physician
                Pediatrician|Neonatologist
                Cardiologist|Cardiac Surgeon
                Dentist|Orthodontist
                Registered Nurse|Nurse Practitioner
                Pharmacist|Clinical Pharmacist
                Police Officer|Sheriff's Deputy
                Detective|Criminal Investigator
                Judge|Magistrate
                Prosecutor|Defense Attorney
                Teacher|Instructor
                Professor|Lecturer
                Principal|Headmaster
                Pilot|Flight Instructor
                Captain|First Officer
                Chef|Sous Chef
                Pastry Chef|Confectioner
                Baker|Pastry Cook
            """.trimIndent())
        )
        addAll(
            pairEntries(Category.OCCUPATIONS, Difficulty.MEDIUM, """
                Architect|Interior Architect
                Civil Engineer|Structural Engineer
                Electrician|Power-Line Technician
                Plumber|Pipefitter
                Carpenter|Joiner
                Mason|Bricklayer
                Roofer|Siding Installer
                Welder|Metal Fabricator
                Auto Mechanic|Diesel Mechanic
                Aircraft Mechanic|Avionics Technician
                Surveyor|Cartographer
                Geologist|Geophysicist
                Biologist|Ecologist
                Chemist|Toxicologist
                Astronomer|Astrophysicist
                Accountant|Bookkeeper
                Auditor|Tax Examiner
                Financial Analyst|Investment Analyst
                Journalist|News Reporter
                Copy Editor|Proofreader
            """.trimIndent())
        )
        addAll(
            pairEntries(Category.OCCUPATIONS, Difficulty.HARD, """
                Author|Novelist
                Poet|Playwright
                Screenwriter|Script Editor
                Film Director|Television Director
                Cinematographer|Camera Operator
                Photographer|Photojournalist
                Composer|Songwriter
                Conductor|Orchestrator
                Sculptor|Ceramic Artist
                Illustrator|Graphic Designer
                Translator|Interpreter
                Librarian|Archivist
                Curator|Museum Registrar
                Veterinarian|Animal Surgeon
                Horticulturist|Arborist
                Farmer|Crop Grower
                Rancher|Cattle Herder
                Sommelier|Wine Steward
                Barber|Hairdresser
                Tailor|Dressmaker
            """.trimIndent())
        )

        addAll(
            pairEntries(Category.PLACES, Difficulty.EASY, """
                Aquarium|Marine Park
                Zoo|Wildlife Park
                Stadium|Sports Arena
                Theater|Playhouse
                Cinema|Movie Theater
                Concert Hall|Music Hall
                Cafe|Coffee Shop
                Diner|Bistro
                Restaurant|Brasserie
                Bakery|Patisserie
                Supermarket|Grocery Store
                Market|Bazaar
                Shopping Mall|Shopping Center
                Hotel|Resort
                Hostel|Guesthouse
                Airport|Airfield
                Train Station|Railway Station
                Bus Station|Coach Terminal
                Harbor|Port
                Marina|Yacht Harbor
            """.trimIndent())
        )
        addAll(
            pairEntries(Category.PLACES, Difficulty.MEDIUM, """
                Hospital|Medical Center
                Clinic|Outpatient Center
                Pharmacy|Drugstore
                School|Academy
                College|University
                Kindergarten|Preschool
                Library|Reading Room
                Archive|Records Center
                Museum|History Museum
                Art Gallery|Exhibition Hall
                Courthouse|Justice Center
                City Hall|Town Hall
                Police Station|Sheriff's Office
                Fire Station|Firehouse
                Post Office|Mail Center
                Bank|Credit Union
                Factory|Manufacturing Plant
                Warehouse|Distribution Center
                Office|Business Center
                Laboratory|Research Center
            """.trimIndent())
        )
        addAll(
            pairEntries(Category.PLACES, Difficulty.HARD, """
                Beach|Seashore
                Cove|Bay
                Cliff|Escarpment
                Valley|Gorge
                Canyon|Ravine
                Mountain|Peak
                Hill|Knoll
                Forest|Woodland
                Jungle|Rainforest
                Meadow|Pasture
                Prairie|Grassland
                Marsh|Swamp
                Bog|Fen
                Lake|Reservoir
                Pond|Pool
                River|Stream
                Creek|Brook
                Waterfall|Cascade
                Cave|Cavern
                Desert|Dune
            """.trimIndent())
        )

        addAll(
            pairEntries(Category.OBJECTS, Difficulty.EASY, """
                Smartphone|Mobile Phone
                Desktop Computer|Workstation
                E-reader|Digital Reader
                Smartwatch|Fitness Watch
                Fountain Pen|Ballpoint Pen
                Marker|Highlighter
                Notebook|Journal
                Hardcover|Paperback
                Acoustic Guitar|Electric Guitar
                Violin|Viola
                Cello|Double Bass
                Flute|Piccolo
                Clarinet|Oboe
                Trumpet|Cornet
                Drum Kit|Percussion Set
                Headphones|Earbuds
                Microphone|Megaphone
                Camera|Camcorder
                Binoculars|Opera Glasses
                Telescope|Spyglass
            """.trimIndent())
        )
        addAll(
            pairEntries(Category.OBJECTS, Difficulty.MEDIUM, """
                Sofa|Couch
                Armchair|Recliner
                Stool|Ottoman
                Desk|Writing Table
                Bookshelf|Bookcase
                Cabinet|Cupboard
                Dresser|Chest of Drawers
                Kettle|Teapot
                Saucepan|Stockpot
                Frying Pan|Skillet
                Baking Sheet|Roasting Pan
                Plate|Platter
                Bowl|Dish
                Mug|Cup
                Tumbler|Drinking Glass
                Chef's Knife|Paring Knife
                Spatula|Turner
                Whisk|Beater
                Screwdriver|Nut Driver
                Wrench|Spanner
            """.trimIndent())
        )
        addAll(
            pairEntries(Category.OBJECTS, Difficulty.HARD, """
                Hammer|Mallet
                Pliers|Tongs
                Saw|Hacksaw
                Chisel|Gouge
                Drill|Impact Driver
                Flashlight|Lantern
                Umbrella|Parasol
                Backpack|Rucksack
                Suitcase|Travel Case
                Wallet|Card Holder
                Key|Keycard
                Lock|Padlock
                Bicycle|Mountain Bike
                Scooter|Kick Scooter
                Skateboard|Longboard
                Canoe|Kayak
                Rowboat|Dinghy
                Snowboard|Skis
                Tennis Racket|Badminton Racket
                Baseball Glove|Cricket Glove
            """.trimIndent())
        )

        // Additional related pairs informed by OED entry examples and cross-references.
        addAll(
            pairEntries(Category.FOOD_AND_DRINK, Difficulty.MEDIUM, """
                Queen Cake|King Cake
                Pound Cake|Lady Cake
                Lemon Cake|Ginger Cake
                Sponge Cake|Angel Cake
                Fairy Cake|Cupcake
                Fruitcake|Layer Cake
                Teacake|Welsh Cake
                Potato Cake|Fish Cake
                Journey Cake|Griddle Cake
                Rock Cake|Drop Cake
                Sago Cake|Coconut Cake
                Birthday Cake|Wedding Cake
                Chocolate Cake|Carrot Cake
                Jelly Cake|Loaf Cake
                Rum Cake|Tipsy Cake
                Beef|Mutton
                Pork|Bacon
                Chicken|Turkey
                Lamb|Veal
                Salmon|Trout
            """.trimIndent())
        )
        addAll(
            pairEntries(Category.ANIMALS, Difficulty.MEDIUM, """
                Lion|Tiger
                Panther|Jaguar
                Panther|Leopard
                Catbird|Mockingbird
                Songbird|Warbler
                Bowerbird|Lyrebird
                Meadowlark|Bobolink
                Osprey|Eagle
                Pigeon|Dove
                Sparrow|Finch
                Pelican|Cormorant
                Redstart|Nightingale
                Ostrich|Emu
                Duck|Goose
                Domestic Cat|Feral Cat
                Bobcat|Canada Lynx
                Tabby|Calico
                Panther|Cougar
                Macaw|Parrot
                Crow|Jackdaw
            """.trimIndent())
        )
        addAll(
            pairEntries(Category.OCCUPATIONS, Difficulty.MEDIUM, """
                Doctor|Physician
                Teacher|Educator
                Tutor|Instructor
                Teacher|Tutor
                Carpenter|Cabinetmaker
                Carpenter|Woodworker
                Blacksmith|Farrier
                Bookkeeper|Accountant
                Butcher|Fishmonger
                Brewer|Distiller
                Baker|Confectioner
                Farmer|Agriculturist
                Barber|Stylist
                Actor|Performer
                Editor|Proofreader
                Designer|Illustrator
                Miner|Quarryman
                Sailor|Seafarer
                Coach|Trainer
                Caregiver|Nurse
            """.trimIndent())
        )
        addAll(
            pairEntries(Category.PLACES, Difficulty.MEDIUM, """
                House|Home
                Abode|Dwelling
                Residence|Domicile
                Hut|Shack
                Cottage|Cabin
                Manor|Estate
                Inn|Tavern
                Pub|Alehouse
                Lodge|Chalet
                Apartment|Condominium
                Bungalow|Cottage
                Farmhouse|Ranch House
                Dormitory|Residence Hall
                Chapel|Oratory
                Monastery|Abbey
                Fortress|Citadel
                Storehouse|Warehouse
                Workshop|Atelier
                Courtyard|Patio
                Yard|Garden
            """.trimIndent())
        )
        addAll(
            pairEntries(Category.OBJECTS, Difficulty.MEDIUM, """
                Houseware|Homeware
                Household Goods|Household Effects
                Furniture|Furnishings
                Utensil|Implement
                Escritoire|Writing Desk
                Bureau|Escritoire
                Larder|Pantry
                Cabinet|Cupboard
                Wardrobe|Armoire
                Cutlery|Silverware
                Table|Dining Table
                Chair|Stool
                Shelf|Bookcase
                Bed|Cot
                Quilt|Comforter
                Blanket|Coverlet
                Saucer|Side Plate
                Pitcher|Jug
                Crockery|Tableware
                Broom|Brush
            """.trimIndent())
        )
    }

    val wordPairs: List<WordPair> = handpickedWordPairs

    private fun pairEntries(
        category: Category,
        difficulty: Difficulty,
        entries: String
    ): List<WordPair> {
        return entries.lineSequence()
            .filter { it.isNotBlank() }
            .map { line ->
                val (civilianWord, undercoverWord) = line.split("|", limit = 2)
                WordPair(
                    civilianWord = civilianWord.trim(),
                    undercoverWord = undercoverWord.trim(),
                    category = category,
                    difficulty = difficulty
                )
            }
            .toList()
    }

    fun getCategories(): List<Category> {
        return Category.values().toList()
    }

    fun getRandomWordPair(category: Category, difficulty: Difficulty): WordPair {
        val filtered = if (category == Category.ALL_CATEGORIES) {
            wordPairs
        } else {
            wordPairs.filter { it.category == category }
        }
        val difficultyFiltered = filtered.filter { it.difficulty == difficulty }
        val pool = difficultyFiltered.ifEmpty { filtered.ifEmpty { wordPairs } }
        return pool.random()
    }
}

