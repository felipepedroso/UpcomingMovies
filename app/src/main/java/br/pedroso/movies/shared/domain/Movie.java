package br.pedroso.movies.shared.domain;

public class Movie {
    private Integer id;
    private String posterPath;
    private String title;
    private Double voteAverage;
    private String releaseDate;
    private String overview;

    private Movie(Builder builder) {
        id = builder.id;
        posterPath = builder.posterPath;
        title = builder.title;
        voteAverage = builder.voteAverage;
        releaseDate = builder.releaseDate;
        overview = builder.overview;
    }

    public Integer getId() {
        return id;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getTitle() {
        return title;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getOverview() {
        return overview;
    }

    @Override
    public String toString() {
        return String.format("{ id:\"%d\", title:\"%s\", posterPath:\"%s\", releaseDate:\"%s\", overview:\"%s\", id:\"%.2f\"   }",
                id, title, posterPath, releaseDate, overview, voteAverage);
    }

    public static class Builder {
        private Integer id;
        private String posterPath;
        private String title;
        private Double voteAverage;
        private String releaseDate;
        private String overview;

        public Builder(Integer id, String title) {
            this.id = id;
            this.title = title;
        }

        public Builder setPosterPath(String posterPath) {
            this.posterPath = posterPath;
            return this;
        }

        public Builder setVoteAverage(Double voteAverage) {
            this.voteAverage = voteAverage;
            return this;
        }

        public Builder setReleaseDate(String releaseDate) {
            this.releaseDate = releaseDate;
            return this;
        }

        public Builder setOverview(String overview) {
            this.overview = overview;
            return this;
        }

        public Movie build() {
            return new Movie(this);
        }

    }
}
