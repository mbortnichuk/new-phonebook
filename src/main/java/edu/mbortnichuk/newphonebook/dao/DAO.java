package edu.mbortnichuk.newphonebook.dao;

import java.util.List;

/**
 * Created by Mariana on 07-May-17.
 */
public interface DAO<T> {

    public List<T> read(String key, String value);

    public List<T> readALL();

    public T create(T record);

    public int update(T record, String key, String value);

    public int delete(String key, String value);


}
