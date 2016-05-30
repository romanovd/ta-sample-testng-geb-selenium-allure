package org.goeuro.models

/**
 * Created by dmitriy.romanov on 5/29/2016.
 */
class SearchResultList {
    List<SearchResultItem> items = []

    public add(SearchResultItem item) {
        items.add(item)
    }
}
