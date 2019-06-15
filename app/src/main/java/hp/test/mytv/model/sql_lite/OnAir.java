package hp.test.mytv.model.sql_lite;

public class OnAir {

    private Integer id;
    private String originalName;
    private String name;
    private String overview;
    private String posterPath;
    private Boolean favorite;

    public static final String TABLE_NAME = "OnAir";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_FAVORITE = "FAVORITE";
    public static final String COLUMN_NAME = "NAME";
    public static final String COLUMN_ORIGINAL_NAME = "ORIGINAL_NAME";
    public static final String COLUMN_POSTER = "POSTER";
    public static final String COLUMN_OVERVIEW="OVERVIEW";

    public OnAir(Integer id, String originalName, String name, String overview, String posterPath, Boolean favorite) {
        this.id = id;
        this.originalName = originalName;
        this.name = name;
        this.overview = overview;
        this.posterPath = posterPath;
        this.favorite = favorite;
    }

    public static final String CREATE_TABLE = String.format(
            "CREATE TABLE %s (" +
                    "%s INTEGER PRIMARY_KEY," +
                    "%s INTEGER," +
                    "%s TEXT," +
                    "%s TEXT," +
                    "%s TEXT," +
                    "%s TEXT," +
                    "timestamp DATETIME DEFAULT CURRENT_TIMESTAMP) ",
            TABLE_NAME,COLUMN_ID,COLUMN_FAVORITE,COLUMN_NAME,COLUMN_ORIGINAL_NAME,COLUMN_POSTER,COLUMN_OVERVIEW
    );
    public static final String DROP_TABLE =
            "DROP IF EXIST" + TABLE_NAME;


    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }



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
