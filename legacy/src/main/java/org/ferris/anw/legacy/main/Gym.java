package org.ferris.anw.legacy.main;

import java.util.Optional;

/**
 *
 * @author Michael
 */
public class Gym {
    protected String name;
    protected Optional<Long> id;
    
    public Gym(String name) {
        this.name = name;
        this.id = Optional.empty();
    }
    
    public Gym(Long id, String name) {
        this.id = Optional.of(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Optional<Long> getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Gym{" + "name=" + name + ", id=" + id.orElse(null) + '}';
    }
}
