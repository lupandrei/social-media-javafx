
package com.example.labgui.repo;


import com.example.labgui.domain.Friendship;
import com.example.labgui.exceptions.RepositoryException;


import java.util.ArrayList;
import java.util.List;

public class FriendshipRepo<E extends Friendship,ID> implements Repository<E,ID> {

    private List<E> friendships;

    @Override
    public void update(E entity) {
        for(int i=0;i<friendships.size();i++){
            if(friendships.get(i).getId()==entity.getId()){
                friendships.set(i,entity);
            }
        }
    }

    @Override
    public List<E> startsWith(ID startsWith) {
        return null;
    }

    public FriendshipRepo() {
        this.friendships = new ArrayList<>();
    }


    /**
     *
     * @return List, all friendships
     */

    @Override
    public List<E> get_all() {
        return friendships;
    }


/**
     *
     * @return int, number of friendships
     */

    public int size(){
        return friendships.size();
    }


/**
     * Function adds friendship
     * @param entity
     * @throws RepositoryException, if users don't exist.
     */

    @Override
    public void add(E entity) throws RepositoryException {
        for(E e:friendships){
            if(e.equals(entity)){
                throw new RepositoryException("Friendship already exists.\n");
            }
        }
        friendships.add(entity);
    }


/**
     * Function removes friendship.
     * @param entity
     * @throws RepositoryException - if friendship does not exist
     *
     */

    @Override
    public void remove(E entity) throws RepositoryException {
        for(E e:friendships){
            if(e.equals(entity)){
               friendships.remove(entity);
               return;
            }
        }
        throw new RepositoryException("Friendship does not exist.\n");
    }


/**
     *
     * @param id
     * @return entity, if found; null,else
     */

    @Override
    public E find(ID id) {
        for(E e:friendships) {
            if (e.getId().equals(id))
                return e;
        }
        return null;
    }
}

