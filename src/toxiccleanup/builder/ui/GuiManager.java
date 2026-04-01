package toxiccleanup.builder.ui;

import toxiccleanup.builder.GameState;
import toxiccleanup.engine.EngineState;
import toxiccleanup.engine.game.Position;
import toxiccleanup.engine.renderer.Renderable;
import toxiccleanup.engine.ui.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * manages the GUI elements including power bars, hp meter, and timer
 *
 */

public class GuiManager implements Overlay {
    /**
     * number of ticks remaining in the game (5 minutes).
     */
    private int gameplayTicks = 18000;
    /**
     * List of HUD elements to be rendered.
     */
    private List<Renderable> displayHuds = new ArrayList<>();
    private String timer = "";
    /**
     * overlay for win and lose
     */
    private Text gameStatus;
    private Text timerDisplay;

    /**
     * constructs a new Gui manager
     *
     */

    public GuiManager() {
        this.gameplayTicks = gameplayTicks;
        this.displayHuds = displayHuds;
        this.timer = timer;
        this.gameStatus = gameStatus;
        this.timerDisplay = timerDisplay;
    }

    @Override
    public void tick(EngineState state, GameState game) {
        // clear initial state first so the old contents doesn't overlap
        this.displayHuds.clear();

        // get the size of the tile to scale icons (could be used multiple times)
        int tileSize = state.getDimensions().tileSize();
        int windowSize = state.getDimensions().windowSize();


        // ====== TIMER ======
        // ongoing ticks is the current tick from engine
        // remaining ticks counts the remaining from 5 minutes (18.000 ticks)
        int ongoingTicks = state.currentTick();
        int remainingTicks = gameplayTicks - ongoingTicks;

        // dont let remainingTicks turn negative
        if (remainingTicks < 0) {
            remainingTicks = 0;
        }
        // per Javadoc: 18.000 ticks at 60 ticks per second.
        // 18.000 / 60 = 300 seconds (5 minutes)
        int secondsTicks = remainingTicks / 60;
        int minuteTicks = secondsTicks / 60;
        // 300 % 60 will be 0. since gameplayTicks will be decremented periodically
        // this will mimic a second countdown (0 -> 59 -> 58 -> 57 -> ... -> and goes on)
        int secondsRemains = secondsTicks % 60;
        // per Javadoc: pads seconds in 2 digit -> %02d
        this.timer = String.format("%d %02d", minuteTicks, secondsRemains);

        // this one is for printing the timer into the display
        if (timerDisplay == null) {
            int x = tileSize / 2;
            int y = windowSize - (tileSize / 2);
            int spacing = tileSize;
            timerDisplay = new Text(this.timer, x, y, spacing);
        } else {
            timerDisplay.update(this.timer);
        }
        // if gameplay ticks (timer) still runs, subtract it periodically
        if (gameplayTicks > 0) {
            gameplayTicks--;
        }

        // ====== POWER ======
        // sets a power icon in the top left of the game x = 0, y = 0
        int centerOffset = tileSize / 2;

        this.displayHuds.add(new PowerIcon(new Position(centerOffset, centerOffset)));

        int currentPower = game.getMachines().getPower();
        int maxPower = 14;
        for (int i = 0; i < maxPower; i++) {
            int pixelY = (i + 1) * tileSize + centerOffset;
            boolean powerCharged;
            powerCharged = i < currentPower;
            // X = 0 (places the power bar on the left side)
            // Y = (i + 1) * tileSize + offset (places the power bar downwards along the Y plane)
            // note: assume power icon is (i), the bar would be placed below it (i + 1)
            Position barPosition = new Position(centerOffset, pixelY);
            this.displayHuds.add(new PowerBar(barPosition, powerCharged));
        }


        // ====== HP ======
        int hp = game.getPlayer().getHp();
        for (int i = 0; i < hp; i++) {
            int tileX = windowSize - tileSize;
            // im adding 20 pixel because the Health bar got cut off by my window
            int tileY = (tileSize / 2) + (i * tileSize);
            // X = window size - tile size (places the HP bar on the right side)
            // Y = i * tile size (places the HP bar downwards along with Y plane)
            Position heartPosition = new Position(tileX, tileY);
            this.displayHuds.add(new Heart(heartPosition));
        }
    }

    /**
     * Displays you win game overlay
     *
     * @param state the engine state
     */

    public void win(EngineState state) {
        String statusMessage = "YOU WIN";
        int tileSize = state.getDimensions().tileSize();
        int windowSize = state.getDimensions().windowSize();
        gameStatus = new Text(statusMessage, windowSize / 2, windowSize / 2, tileSize);
    }

    /**
     * Displays game over game overlay
     *
     * @param state the engine state
     *
     */
    public void lose(EngineState state) {
        String statusMessage = "GAME OVER";
        int tileSize = state.getDimensions().tileSize();
        int windowSize = state.getDimensions().windowSize();
        gameStatus = new Text(statusMessage, windowSize / 2, windowSize / 2, tileSize);
    }

    @Override
    public List<Renderable> render() {
        List<Renderable> renderables = new ArrayList<>(displayHuds);
        // timer renderer
        if (timerDisplay != null) {
            renderables.addAll(this.timerDisplay.render());
        }
        //  win / lose renderer
        if (gameStatus != null) {
            renderables.addAll(this.gameStatus.render());
        }
        return renderables;
    }
}
