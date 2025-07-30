import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * The GameViewListener class implements MouseListener and MouseMotionListener interfaces
 * to handle mouse events for the game view. It manages dragging and dropping of seed packets
 * and the shovel tool, as well as interactions with sun objects in the game.
 */
public class GameViewListener implements MouseListener, MouseMotionListener {
    private final GameView GAME_VIEW;
    private final GameController CONTROLLER;

    private JLabel selectedLabel;
    private BufferedImage draggedImage;
    private boolean dragging = false;
    private boolean draggingShovel = false;
    private Point draggedPosition = new Point();
    private Point dragOffset = new Point();

    /**
     * Constructs a GameViewListener with the specified GameView and GameController.
     * @param gameView The GameView instance associated with this listener.
     * @param controller The GameController instance that handles game logic.
     */
    public GameViewListener(GameView gameView, GameController controller) {
        this.GAME_VIEW = gameView;
        this.CONTROLLER = controller;
    }

    /**
     * Handles mouse press events. Initiates dragging for seed packets or the shovel tool,
     * and checks for clicks on active suns.
     * @param e The MouseEvent representing the press action.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        Component clickedComponent = SwingUtilities.getDeepestComponentAt(GAME_VIEW, e.getX(), e.getY());

        if (isSeedPacket(clickedComponent)) {
            startDragging((JLabel) clickedComponent, e.getPoint());
        } else if (isShovelLabel(clickedComponent)) {
            startShovelDragging((JLabel) clickedComponent, e.getPoint());
        }

        for (Sun sun : CONTROLLER.getActiveSuns()) {
            int x = GameView.GRID_START_X + (int)(sun.getColumnPos() * GameView.CELL_WIDTH);
            int y = GameView.GRID_START_Y + (int)(sun.getRowPos() * GameView.CELL_HEIGHT);
            Rectangle bounds = new Rectangle(x, y, 70, 70);
            if (bounds.contains(e.getPoint()) && sun.isActive()) {
                CONTROLLER.handleSunClick(sun);
                break;
            }
        }
        GAME_VIEW.repaint();
    }

    /**
     * Handles mouse drag events. Updates the position of the dragged item.
     * @param e The MouseEvent representing the drag action.y
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        if (dragging) {
            draggedPosition.setLocation(e.getPoint());
            GAME_VIEW.repaint();
        }
    }

    /**
     * Handles mouse release events. Processes the drop action for the dragged item.
     * @param e The MouseEvent representing the release action.
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        if (!dragging || selectedLabel == null) return;

        handleDrop(e.getPoint());
        resetDragState();
        GAME_VIEW.repaint();
    }

    /**
     * Starts dragging operation for a seed packet.
     * @param label The JLabel representing the seed packet being dragged.
     * @param clickPoint The Point where the mouse was clicked to start dragging.
     */
    private void startDragging(JLabel label, Point clickPoint) {
        selectedLabel = label;
        dragging = true;

        Point labelPosition = SwingUtilities.convertPoint(label, 0, 0, GAME_VIEW);
        dragOffset = new Point(clickPoint.x - labelPosition.x, clickPoint.y - labelPosition.y);
        draggedPosition = clickPoint;

        try {
            BufferedImage originalImage = ImageIO.read(new File("assets/plants/" + selectedLabel.getName() + ".png"));
            Image scaledImage = originalImage.getScaledInstance(GameView.CELL_WIDTH - 10, GameView.CELL_HEIGHT - 10, Image.SCALE_SMOOTH);
            draggedImage = new BufferedImage(GameView.CELL_WIDTH, GameView.CELL_HEIGHT, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = draggedImage.createGraphics();
            g2d.drawImage(scaledImage, 0, 0, null);
            g2d.dispose();
        } catch (Exception e) {
            System.err.println("Failed to create dragged image for " + selectedLabel.getName() + ": " + e.getMessage());
        }
    }

    /**
     * Starts dragging operation for the shovel tool.
     *
     * @param label The JLabel representing the shovel being dragged.
     * @param clickPoint The Point where the mouse was clicked to start dragging.
     */
    private void startShovelDragging(JLabel label, Point clickPoint) {
        selectedLabel = label;
        dragging = true;
        draggingShovel = true;

        Point labelPosition = SwingUtilities.convertPoint(label, 0, 0, GAME_VIEW);
        dragOffset = new Point(clickPoint.x - labelPosition.x, clickPoint.y - labelPosition.y);
        draggedPosition = clickPoint;

        try {
            BufferedImage originalImage = ImageIO.read(new File("assets/ui/Shovel.png"));
            Image scaledImage = originalImage.getScaledInstance(GameView.CELL_WIDTH, GameView.CELL_HEIGHT, Image.SCALE_SMOOTH);
            draggedImage = new BufferedImage(GameView.CELL_WIDTH, GameView.CELL_HEIGHT, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = draggedImage.createGraphics();
            g2d.drawImage(scaledImage, 0, 0, null);
            g2d.dispose();
        } catch (Exception e) {
            System.err.println("Failed to create dragged image for shovel: " + e.getMessage());
        }
    }

    /**
     * Handles the drop action when a dragged item is released.
     * @param dropPoint The Point where the item was dropped.
     */
    private void handleDrop(Point dropPoint) {
        Point gridPosition = snapToGrid(dropPoint.x, dropPoint.y);
        if (gridPosition == null || CONTROLLER == null) return;

        int row = gridPosition.y;
        int col = gridPosition.x;

        if (draggingShovel) {
            CONTROLLER.removePlantFromBoard(row, col); // Use 2D array access
        } else {
            String plantType = selectedLabel.getName();
            if (plantType != null) {
                CONTROLLER.placePlantOnBoard(plantType, row, col);
            }
        }

        CONTROLLER.updatePlantBoardDisplay();
    }

    /**
     * Snaps a screen coordinate to the nearest grid cell in the game.
     * @param x The x-coordinate of the point to snap.
     * @param y The y-coordinate of the point to snap.
     * @return A Point representing the snapped grid coordinates (column, row).
     */
    public Point snapToGrid(int x, int y) {
        int col = (x - GameView.GRID_START_X) / GameView.CELL_WIDTH;
        int row = (y - GameView.GRID_START_Y) / GameView.CELL_HEIGHT;
        return new Point(col, row);
    }

    /**
     * Resets the dragging state after a drag operation is completed.
     */
    private void resetDragState() {
        dragging = false;
        draggingShovel = false;
        draggedImage = null;
        selectedLabel = null;
    }

    /**
     * Checks if an item is currently being dragged.
     * @return true if dragging is in progress, false otherwise.
     */
    public boolean isDragging() {
        return dragging;
    }

    /**
     * Checks if a component is a seed packet.
     * @param component The component to check.
     * @return true if the component is a seed packet, false otherwise.
     */
    private boolean isSeedPacket(Component component) {
        ArrayList<JLabel> plantLabels = GAME_VIEW.getSeedPackets();
        return component instanceof JLabel && plantLabels.contains(component);
    }

    /**
     * Checks if a component is the shovel tool.
     * @param component The component to check.
     * @return true if the component is the shovel tool, false otherwise.
     */
    private boolean isShovelLabel(Component component) {
        return component instanceof JLabel && "shovel".equals(component.getName());
    }

    /**
     * Retrieves the image of the currently dragged item.
     * @return The BufferedImage of the dragged item, or null if no item is being dragged.
     */
    public BufferedImage getDraggedImage() {
        return draggedImage;
    }

    /**
     * Retrieves the current position of the dragged item.
     * @return A Point representing the current screen coordinates of the dragged item.
     */
    public Point getDraggedPosition() {
        return draggedPosition;
    }

    /**
     * Retrieves the offset between the mouse cursor and the dragged item's origin.
     * @return A Point representing the offset coordinates.
     */
    public Point getDragOffset() {
        return dragOffset;
    }

    // Unused mouse events
    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
    @Override public void mouseMoved(MouseEvent e) {}
}