package olupis.content;

import mindustry.game.Schematic;
import mindustry.game.Schematics;

public class NyfalisSchematic {

    public static Schematic basicRemnant, basicVestige, basicRelic, basicShrine, basicTemple;

    public static void LoadSchematics(){
        basicRemnant = Schematics.readBase64("bXNjaAF4nGNgYmBiZmDJS8xNZWDxq0ysY+BOSS1OLsosKMnMz2NgYGDLSUxKzSlmYIqOZWQQzs8pLcgs1k3OL0rVLUrNzUvMKwGqYWSAAADyAxLi");
        basicVestige = Schematics.readBase64("bXNjaAF4nGNgZmBmZmDJS8xNZWAPSy0uyUxPZeBOSS1OLsosKMnMz2NgYGDLSUxKzSlmYIqOZWQQzs8pLcgs1k3OL0rVLYNqYGBgBCEgAQBHxhQc");
        basicRelic = Schematics.readBase64("bXNjaAF4nGNgYWBhZmDJS8xNZWANSs3JTGbgTkktTi7KLCjJzM9jYGBgy0lMSs0pZmCKjmVkEMzPKS3ILNZNzi9K1S0CK2dgYAQhIAEA1/0SSg==");
        basicShrine = Schematics.readBase64("bXNjaAF4nCXKMQqAIBiA0U9Ll7bu4Wkao8HshwRT0bp/Qbz1YTADY/aXYJezxSxMh/TQYr1jyYBNfpfU0eummEt6auwulCau/x8U6A8vEaQTRA==");
        basicTemple = Schematics.readBase64("bXNjaAF4nCXKOw6AIBBAwYeabey8B7exMxaIW5DwC+D9NTHTDoLMLNklRXZNNSrrrd23UEcoGZDoLo2d6TgNW4lPDd360tSO/4OB6cMLEZgTQg==");
    }
}
