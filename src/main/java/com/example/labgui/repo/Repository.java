package com.example.labgui.repo;



import com.example.labgui.exceptions.RepositoryException;

import java.util.List;


    public interface Repository<E,ID> {
        void add(E entity)throws RepositoryException;
        void remove(E entity)throws RepositoryException;
        E find(ID id)throws RepositoryException;

        List<E> get_all();

        void update(E entity);

        List<E> startsWith(ID startsWith);
    }
