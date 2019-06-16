package hp.test.mytv.model.sql_lite;

public class OnAir extends Favorite{

    private Boolean favorite;

    public static final String TABLE_NAME = "OnAir";
    public static final String COLUMN_FAVORITE = "FAVORITE";

    public OnAir(Integer id, String originalName, String name, String overview, String posterPath, Boolean favorite) {
        super(id, originalName, name, overview, posterPath);
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

}
