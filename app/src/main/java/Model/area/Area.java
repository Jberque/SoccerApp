package Model.area;

public class Area {



    private String id;
    private String name;


    public Area() {
        super();
    }

    public Area(String id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    //GET/SET
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CompetitionArea [id=" + id + ", name=" + name + "]";
    }
}