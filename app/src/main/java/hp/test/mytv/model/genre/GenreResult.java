package hp.test.mytv.model.genre;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import hp.test.mytv.model.genre.GenreItem;

public class GenreResult {
    @SerializedName("genres")
    @Expose
    private List<GenreItem> genreItems = null;

    public List<GenreItem> getGenreItems() {
        return genreItems;
    }

    public void setGenreItems(List<GenreItem> genreItems) {
        this.genreItems = genreItems;
    }

}
