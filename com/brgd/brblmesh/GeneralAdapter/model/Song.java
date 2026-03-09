package com.brgd.brblmesh.GeneralAdapter.model;

/* JADX INFO: loaded from: classes.dex */
public class Song {
    public int duration;
    public boolean isSelect;
    public String name;
    public String path;
    public String singer;
    public long size;

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getSinger() {
        return this.singer;
    }

    public void setSinger(String str) {
        this.singer = str;
    }

    public long getSize() {
        return this.size;
    }

    public void setSize(long j) {
        this.size = j;
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int i) {
        this.duration = i;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String str) {
        this.path = str;
    }
}
