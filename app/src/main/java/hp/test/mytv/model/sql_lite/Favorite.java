package hp.test.mytv.model.sql_lite;

public class Favorite {

    private int id;
    private long tmdb_id;

    public static final String TABLE_NAME = "Favorite";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TMDB_ID = "tmdbid";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("+
                COLUMN_ID + " INTEGER AUTO_INCREMENT PRIMARY KEY,"+
                COLUMN_TMDB_ID + " INTEGER UNIQUE," +
                    "timestamp DATETIME DEFAULT CURRENT_TIMESTAMP" +
            ")";

    public static final String DROP_TABLE =
            "DROP IF EXIST" + TABLE_NAME;

    public long getTmdbId() {
        return tmdb_id;
    }

    public void setTmdbId(long tmdb_id) {
        this.tmdb_id = tmdb_id;
    }

    public Favorite(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
