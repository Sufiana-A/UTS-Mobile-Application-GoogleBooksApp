package com.example.googlebooksapp.model;

import java.util.List;

public class BookResponse {
    private List<Item> items;

    public List<Item> getItems() {
        return items;
    }

    public static class Item {
        private VolumeInfo volumeInfo;

        public VolumeInfo getVolumeInfo() {
            return volumeInfo;
        }
    }

    public static class VolumeInfo {
        private String title;
        private List<String> authors;

        public String getTitle() {
            return title;
        }

        public List<String> getAuthors() {
            return authors;
        }
    }
}

