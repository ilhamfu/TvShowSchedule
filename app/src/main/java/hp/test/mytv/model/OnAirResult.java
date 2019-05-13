package hp.test.mytv.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OnAirResult {
    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("total_results")
    @Expose
    private Integer totalResults;
    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;
    @SerializedName("results")
    @Expose
    private List<OnAirItem> results = null;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public List<OnAirItem> getResults() {
        return results;
    }

    public void setResults(List<OnAirItem> results) {
        this.results = results;
    }
}
