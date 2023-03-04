package com.example.labgui.repo;

import com.example.labgui.domain.HasID;
import com.example.labgui.exceptions.RepositoryException;
//import domain.HasID;
//import domain.User;
//import exceptions.RepositoryException;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractFileRepo<ID,E extends HasID<ID>> extends inMemoryRepository<E,ID> {
    private String filename;

    public AbstractFileRepo(String filename)  {
        this.filename = filename;
        loadData();
    }

    private void loadData()  {
        try( BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String linie;
            while ((linie = br.readLine()) != null) {
                List<String> attr = Arrays.asList(linie.split(";"));
                E e = extractEntity(attr);
                try {
                    if(e!=null)
                        super.add(e);
                } catch (RepositoryException re) {

                }
            }
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public abstract E extractEntity(List<String> attr);

    @Override
    public void add(E entity) throws RepositoryException{
        super.add(entity);
        writeToFile(entity);
    }

    @Override
    public void update(E entity) {
        super.update(entity);
        try {
            PrintWriter writer = new PrintWriter(filename);
            writer.print("");
            for(E e: entities)
            {
                writeToFile(e);
            }
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    protected void writeToFile(E entity) {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(filename,true))){
            bw.write(createEntityAsString(entity));
            bw.newLine();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void remove(E entity) throws RepositoryException {
        super.remove(entity);
        try {
            PrintWriter writer = new PrintWriter(filename);
            writer.print("");
            for(E e: entities)
            {
                writeToFile(e);
            }
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }



    }

    protected abstract String createEntityAsString(E entity);
}
