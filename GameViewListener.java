import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;

public class GameViewListener implements MouseListener, MouseMotionListener {
    private final GameView gameView;
    private final GameController controller;

    private JLabel selectedLabel;
    private BufferedImage draggedImage;
    private boolean dragging = false;
    private boolean draggingShovel = false;
    private Point draggedPosition = new Point();
    private Point dragOffset = new Point();

    public GameViewListener(GameView gameView, GameController controller) {
        this.gameView = gameView;
        this.controller = controller;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Component clickedComponent = SwingUtilities.getDeepestComponentAt(gameView, e.getX(), e.getY());
        System.out.println( "Clicked component: " + clickedComponent.getName() + " (" + clickedComponent.getClass().getSimpleName() + ")");

        if (isPlantLabel(clickedComponent)) {
            startDragging((JLabel) clickedComponent, e.getPoint());
        } else if (isShovelLabel(clickedComponent)) {
            startShovelDragging((JLabel) clickedComponent, e.getPoint());
        }
        gameView.repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (dragging) {
            draggedPosition.setLocation(e.getPoint());
            gameView.repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (!dragging || selectedLabel == null) return;

        handleDrop(e.getPoint());
        resetDragState();
        gameView.repaint();
    }

    private boolean isPlantLabel(Component component) {
        ArrayList<JLabel> plantLabels = gameView.getPlantLabels();
        return component instanceof JLabel && plantLabels.contains(component);
    }

    private boolean isShovelLabel(Component component) {
        return component instanceof JLabel && "shovel".equals(component.getName());
    }

    private void startDragging(JLabel label, Point clickPoint) {
        selectedLabel = label;
        dragging = true;

        Point labelPosition = SwingUtilities.convertPoint(label, 0, 0, gameView);
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

    private void startShovelDragging(JLabel label, Point clickPoint) {
        selectedLabel = label;
        dragging = true;
        draggingShovel = true;

        Point labelPosition = SwingUtilities.convertPoint(label, 0, 0, gameView);
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

    private void handleDrop(Point dropPoint) {
        Point gridPosition = gameView.snapToGrid(dropPoint.x, dropPoint.y);

        if (gridPosition == null || controller == null) return;

        if (draggingShovel) {
            controller.removePlantFromBoard(gridPosition.y, gridPosition.x);
        } else {
            String plantType = selectedLabel.getName();
            if (plantType != null) {
                controller.placePlantOnBoard(plantType, gridPosition.y, gridPosition.x);
            }
        }
        controller.updateBoardDisplay();
    }

    private void resetDragState() {
        dragging = false;
        draggingShovel = false;
        draggedImage = null;
        selectedLabel = null;
    }

    public boolean isDragging() {
        return dragging;
    }

    public boolean isDraggingShovel() {
        return draggingShovel;
    }

    public BufferedImage getDraggedImage() {
        return draggedImage;
    }

    public Point getDraggedPosition() {
        return draggedPosition;
    }

    public Point getDragOffset() {
        return dragOffset;
    }

    // Unused mouse events
    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
    @Override public void mouseMoved(MouseEvent e) {}
}