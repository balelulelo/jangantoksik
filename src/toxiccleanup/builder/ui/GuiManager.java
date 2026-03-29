package toxiccleanup.builder.ui;

import toxiccleanup.builder.GameState;
import toxiccleanup.engine.Engine;
import toxiccleanup.engine.EngineState;
import toxiccleanup.engine.game.Position;
import toxiccleanup.engine.renderer.Renderable;

import java.util.ArrayList;
import java.util.List;

public class GuiManager implements Overlay {
    private int gameplayTicks = 18000;
    private List<Renderable> displayHuds = new ArrayList<>();
    private String timer = "";
    private String statusMessage = null;

    public GuiManager() {
        this.gameplayTicks = gameplayTicks;
        this.displayHuds = displayHuds;
        this.timer = timer;
        this.statusMessage = statusMessage;
    }

    public void tick(EngineState state, GameState game) {
        // clear initial state first so the old contents doesn't overlap
        this.displayHuds.clear();
        // if gameplay ticks (timer) still runs, subtract it periodically
        if (gameplayTicks > 0) {
            gameplayTicks--;
        }
        // ====== TIMER ======
        // per Javadoc: 18.000 ticks at 60 ticks per second.
        // 18.000 / 60 = 300 seconds (5 minutes)
        int secondsTicks = gameplayTicks / 60;
        int minuteTicks = secondsTicks / 60;
        // 300 % 60 will be 0. since gameplayTicks will be decremented periodically
        // this will mimic a second countdown (0 -> 59 -> 58 -> 57 -> ... -> and goes on)
        int secondsRemains = secondsTicks % 60;
        // per Javadoc: pads seconds in 2 digit -> %02d
        this.timer = String.format("%d:%02d", minuteTicks, secondsRemains);
        // ====== POWER ======
        // get the size of the tile to scale icons (can be used again for HP bar)
        int tileSize = state.getDimensions().tileSize();
        // sets a power icon in the top left of the game x = 0, y = 0
        this.displayHuds.add(new PowerIcon(new Position(0, 0)));

        int currentPower = game.getMachines().getPower();
        int maxPower = 14;
        for (int i = 0; i < maxPower; i++) {
            boolean powerCharged;
            powerCharged = i < currentPower;
            // X = 0 (places the power bar on the left side)
            // Y = (i + 1) * tileSize (places the power bar downwards along the Y plane)
            // note: assume power icon is (i), the bar would be placed below it (i + 1)
            Position barPosition = new Position(0, (i + 1) * tileSize);
            this.displayHuds.add(new PowerBar(barPosition, powerCharged));
        }

        // ====== HP ======
        int windowSize = state.getDimensions().windowSize();
        int hp = game.getPlayer().getHp();
        for (int i = 0; i < hp; i++) {
            int tileX = windowSize - tileSize;
            // im adding 20 pixel because the Health bar got cut off by my window
            int tileY = 20 + (i * tileSize);
            // X = window size - tile size (places the HP bar on the right side)
            // Y = i * tile size (places the HP bar downwards along with Y plane)
            Position heartPosition = new Position(tileX, tileY);
            this.displayHuds.add(new Heart(heartPosition));
        }
    }

    public void win(EngineState state) {
        statusMessage = "YOU WIN";
    }

    public void lose(EngineState state) {
        statusMessage = "GAME OVER";
    }

    public List<Renderable> render() {
        List<Renderable> renderables = new ArrayList<>(displayHuds);
        return renderables;
    }
}
