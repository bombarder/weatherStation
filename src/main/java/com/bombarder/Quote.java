package com.bombarder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Quote {

    @JsonProperty("current")
    private Some some;

    public Some getSome() {
        return some;
    }

    public void setSome(Some some) {
        this.some = some;
    }

    public String getTempValue() {
        return getSome().getTemp();
    }

    @Override
    public String toString() {
        return "Quote{" +
                "some=" + some +
                '}';
    }

    public static class Some {

        @JsonProperty("temp_c")
        private String temp;

        public String getTemp() {
            return temp;
        }

        public void setTemp(String temp) {
            this.temp = temp;
        }

        @Override
        public String toString() {
            return "Some{" +
                    "temp='" + temp + '\'' +
                    '}';
        }
    }
}