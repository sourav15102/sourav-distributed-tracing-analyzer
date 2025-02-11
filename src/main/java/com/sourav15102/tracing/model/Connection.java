package com.sourav15102.tracing.model;
import java.lang.*;
import java.util.Objects;

public class Connection {
    private String destination;
    private int latency;

    public Connection(String destination, int latency){
        this.destination = destination;
        this.latency = latency;
    }
    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getLatency() {
        return latency;
    }

    public void setLatency(int latency) {
        this.latency = latency;
    }

    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        Connection that = (Connection) object;
        return latency == that.latency && Objects.equals(destination, that.destination);
    }

    public int hashCode() {
        return Objects.hash(super.hashCode(), destination, latency);
    }

    @Override
    public String toString() {
        return "Connection{" +
                "destination=" + destination +
                ", latency=" + latency +
                '}';
    }
}
