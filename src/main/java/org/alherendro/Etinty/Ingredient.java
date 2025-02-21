package org.alherendro.Etinty;

import java.time.LocalDateTime;
import java.util.Objects;

public class Ingredient {
    private String name;
    private LocalDateTime modificated;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getModificated() {
        return modificated;
    }

    public void setModificated(LocalDateTime modificated) {
        this.modificated = modificated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient that = (Ingredient) o;
        return Objects.equals(getName(), that.getName()) && Objects.equals(getModificated(), that.getModificated());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getModificated());
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "name='" + name + '\'' +
                ", modificated=" + modificated +
                '}';
    }
}

