package ru.isha.store.dto;

public class SearchForm {
    private String searchName;

    public SearchForm(String searchName) {
        this.searchName = searchName;
    }

    public SearchForm() {
    }

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    @Override
    public String toString() {
        return "SearchForm{" +
                "search='" + searchName + '\'' +
                '}';
    }
}
