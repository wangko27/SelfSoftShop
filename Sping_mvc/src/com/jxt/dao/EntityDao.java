package com.jxt.dao;

import java.util.List;
import org.springframework.dao.DataAccessException;

public abstract interface EntityDao<T>
{
  public abstract Long insertEntity(T paramT)
    throws DataAccessException;

  public abstract int updateEntity(T paramT)
    throws DataAccessException;

  public abstract int deleteEntity(T paramT)
    throws DataAccessException;

  public abstract T selectEntity(T paramT)
    throws DataAccessException;

  public abstract Long selectEntityCount(T paramT)
    throws DataAccessException;

  public abstract List<T> selectEntityList(T paramT)
    throws DataAccessException;

  public abstract List<T> selectEntityPaginatedList(T paramT)
    throws DataAccessException;
}