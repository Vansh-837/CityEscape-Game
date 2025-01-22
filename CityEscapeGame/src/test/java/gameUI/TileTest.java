package gameUI;

import org.junit.jupiter.api.Test;
import gameUI.Tile;

class TileTest {
    
    @Test
    void Testdefaulttile() {
        Tile tile = new Tile();
        assert(tile.image == null);
    }


}
