package Spells;

import Caractere.Entity;

public interface Visitor<T extends Entity> {
    void visit(T entity);
}
