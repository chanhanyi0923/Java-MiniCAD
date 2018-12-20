import java.awt.Color;

class State {
    public enum Task {
        EMPTY,
        SELECT,
        RESIZE,
        DRAW
    };

    private String text;
    private Shape.Type shapeType;
    private Task task;
    private Color color;
    private float size;

    State() {
        this.text = "";
        this.shapeType = Shape.Type.RECTANGLE;
        this.task = State.Task.EMPTY;
        this.color = Color.BLACK;
        this.size = 1.0f;
    }

    public void setText(String text) { this.text = text; }
    public void setShapeType(Shape.Type shapeType) { this.shapeType = shapeType; }
    public void setTask(Task task) { this.task = task; }
    public void setColor(Color color) { this.color = color; }
    public void setSize(float size) { this.size = size; }

    public String getText() { return this.text; }
    public Shape.Type getShapeType() { return this.shapeType; }
    public Task getTask() { return this.task; }
    public Color getColor() { return this.color; }
    public float getSize() { return this.size; }
}
