package hp.test.mytv.model.sql_lite;

public class Favorite {

    protected Integer id;
    protected String originalName;
    protected String name;
    protected String overview;
    protected String posterPath;

    public static final String TABLE_NAME = "Favorite";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_NAME = "NAME";
    public static final String COLUMN_ORIGINAL_NAME = "ORIGINAL_NAME";
    public static final String COLUMN_POSTER = "POSTER";
    public static final String COLUMN_OVERVIEW="OVERVIEW";

    public Favorite(Integer id, String originalName, String name, String overview, String posterPath) {
        this.id = id;
        this.originalName = originalName;
        this.name = name;
        this.overview = overview;
        this.posterPath = posterPath;
    }

    public static final String CREATE_TABLE = String.format(
            "CREATE TABLE %s (" +
                    "%s INTEGER PRIMARY_KEY," +
                    "%s TEXT," +
                    "%s TEXT," +
                    "%s TEXT," +
                    "%s TEXT," +
                    "timestamp DATETIME DEFAULT CURRENT_TIMESTAMP) ",
            TABLE_NAME,COLUMN_ID,COLUMN_NAME,COLUMN_ORIGINAL_NAME,COLUMN_POSTER,COLUMN_OVERVIEW
    );
    public static final String DROP_TABLE =
            "DROP IF EXIST" + TABLE_NAME;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }
}
