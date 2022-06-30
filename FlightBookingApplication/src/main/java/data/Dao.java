package data;

import java.util.List;

public interface Dao<Type> {
    void create(Type object);

    Type read(int id);

    List<Type> readAll();

    void update(int id, Type object);

    void delete(int id);
}
