package com.example.myapplication.utils;

import java.util.Objects;

public class NumListEntry {
    private String uniqueName;

    public NumListEntry(String str) {
        this.uniqueName = str;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return Objects.equals(this.uniqueName, ((NumListEntry) obj).uniqueName);
    }

    public String getUniqueName() {
        return this.uniqueName;
    }

    public int hashCode() {
        return Objects.hash(this.uniqueName);
    }
}
