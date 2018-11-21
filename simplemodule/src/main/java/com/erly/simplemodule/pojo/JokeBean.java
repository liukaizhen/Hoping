package com.erly.simplemodule.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 笑话实体POJO
 */
public class JokeBean {
    @SerializedName("data")
    private List<Joke> jokeList;

    public List<Joke> getJokeList() {
        return jokeList;
    }

    public void setJokeList(List<Joke> jokeList) {
        this.jokeList = jokeList;
    }

    @Override
    public String toString() {
        return "JokeBean{" +
                "jokeList=" + jokeList +
                '}';
    }

    /**
     * 笑话列表单个条目实体类
     */
    public static class Joke{
        private String content;//笑话内容

        @SerializedName("unixtime")
        private long unixTime;//请求数据时的时间戳

        @SerializedName("updatetime")
        private String updateTime;//笑话更新时间

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public long getUnixTime() {
            return unixTime;
        }

        public void setUnixTime(long unixTime) {
            this.unixTime = unixTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        @Override
        public String toString() {
            return "Joke{" +
                    "content='" + content + '\'' +
                    ", unixTime=" + unixTime +
                    ", updateTime='" + updateTime + '\'' +
                    '}';
        }
    }
}
