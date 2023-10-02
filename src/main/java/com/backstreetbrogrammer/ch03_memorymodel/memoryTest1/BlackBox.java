package com.backstreetbrogrammer.ch03_memorymodel.memoryTest1;

public class BlackBox {
    private String secret = "A";

    public String getSecret() {
        return secret;
    }

    public void setSecret(final String secret) {
        this.secret = secret;
    }
}
