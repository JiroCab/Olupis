package olupis.content;

import mindustry.game.Schematic;
import mindustry.game.Schematics;

public class NyfalisSchematic {

    public static Schematic basicRemnant, basicVestige, basicRelic, basicShrine, basicTemple;

    public static void LoadSchematics(){
        /*Note: launch schms don't use this since game doesn't likie or i think so, Please refer to assets/schematics for it*/
        basicRemnant = Schematics.readBase64("bXNjaAF4nCXKOwqAMBAFwGcIClp6Dq/iBcRiTRZcyI9sLGw8u4JTDwyMhU0UGXa96cGgjSmKx6ju5EhNnGLyrK5KaZITgD7QwUFhtr3DnMNVRBeXKy+VY6LUvtPh9wIpmRn/");
        basicVestige = Schematics.readBase64("bXNjaAF4nBWMQQ7CMAwEXVqBBEc+4AfQt/TAEXEwqSERSVzZSSt+TyqNRnsYLfTQDzBkSgz9tE1wssKUwgxnc54TleAMLjOb07CUIBmmu6j+blh8MGwQbqJfJJWaZzTBjdFRRk9rG9WKJIxUs/PoRNnwLdqyxKhM1g6PkV4cDQ6PZwdXiXUJNu7puLKV8GEA6Haa/hcLNnc=");
        basicRelic = Schematics.readBase64("bXNjaAF4nBWMwQoCMQxEs7Io6En8gXjXb/GgN/EQu9EW22ZJWhf/3hYewxzmDYwwNjIlhsPNB0PJjJ4MCd81H2FjhSmFCbbmPCcqwRnsJjanYS5BMlyuovo7Yel26OIi+kFSqXlCE1wYHeV2+m2lWpGEkWp2Hp0oG75E2ywxKpO1w3WkJ0eD1f0xwF5inYOd+/SsHIMDgKHT4g9Nijrs");
        basicShrine = Schematics.readBase64("bXNjaAF4nCXKMQqAIBiA0U9Ll7bu4Wkao8HshwRT0bp/Qbz1YTADY/aXYJezxSxMh/TQYr1jyYBNfpfU0eummEt6auwulCau/x8U6A8vEaQTRA==");
        basicTemple = Schematics.readBase64("bXNjaAF4nCXKOw6AIBBAwYeabey8B7exMxaIW5DwC+D9NTHTDoLMLNklRXZNNSrrrd23UEcoGZDoLo2d6TgNW4lPDd360tSO/4OB6cMLEZgTQg==");
    }
}
