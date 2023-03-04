package com.example.labgui.repo;


import com.example.labgui.domain.HasID;
import com.example.labgui.exceptions.RepositoryException;

import java.util.ArrayList;
import java.util.List;

public class inMemoryRepository <E extends HasID<ID>,ID> implements Repository<E,ID>{
    protected List<E> entities;

    public inMemoryRepository() {
        this.entities = new ArrayList<E>();
    }

    /**
     *
     * @param entity, given User
     * @throws RepositoryException, if given user already exists in repository.
     */
    public void add(E entity) throws RepositoryException {
        for(E e:entities){
            if(e.equals(entity)) {
                throw new RepositoryException("User already exists!\n");
            }
        }
        entities.add(entity);
    }

    /**
     * Function removes given user;
     * @param entity
     * @throws RepositoryException, if given user does not exist.
     */
    public void remove(E entity)throws RepositoryException
    {
        for(E e:entities){
            if(e.equals(entity)){
                entities.remove(e);
                return;
            }
        }
        throw new RepositoryException("User does not exist!\n");
    }

    /**
     *
     * @param id
     * @return e, user
     * @throws RepositoryException if user does not exist
     */
    public E find(ID id)throws RepositoryException
    {
        for(E e:entities){
            if(e.getId().equals(id))
                return e;
        }
        throw new RepositoryException("User does not exist.\n");
    }

    /**
     *
     * @return List, all the users
     */
    public List<E>get_all(){
        return entities;
    }

    @Override
    public void update(E entity){

        for(int i=0;i<entities.size();i++){
            if(entities.get(i).getId()==entity.getId()){
                entities.set(i,entity);
            }
        }
    }

    @Override
    public List<E> startsWith(ID startsWith) {
        return null;
    }

}
