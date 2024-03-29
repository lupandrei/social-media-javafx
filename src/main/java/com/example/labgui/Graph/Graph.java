package com.example.labgui.Graph;

import java.util.LinkedList;
import java.util.Queue;

public class Graph {
    private int[][]mat;
    private int[]visited;
    private int[]parent;

    public Graph(int[][] mat, int[] visited) {
        this.mat = mat;
        this.visited = visited;
        parent = new int[visited.length];
    }

    /**
     * Function performs depth first search from given index.
     * @param index
     */
    public void dfs(int index)
    {
        visited[index]=1;
        for(int i=0;i<visited.length;i++)
            if(mat[index][i]==1&&visited[i]==0)
                dfs(i);
    }

    /**
     * Function performs breadth first search from given index.
     * @param index
     */
    private void bfs(int index){
        Queue<Integer> Q = new LinkedList<>();
        Q.add(index);
        visited[index]=1;

        parent[index]=-1;
        while(!Q.isEmpty()){
            int peek = Q.poll();
            for(int i=0;i<visited.length;i++){
                if(visited[i]==0&& mat[peek][i]==1){
                    visited[i]=visited[peek]+1;
                    parent[i]=peek;
                    Q.add(i);
                }
            }
        }
    }

    /**
     *
     * @return int[], array of all the indexes that are part of the longest component
     */
    public int[] longestComponent(){
        for(int i=0;i<visited.length;i++)
        {
            if(visited[i]==0){
                bfs(i);
            }
        }
        int componentLength=0;
        int indexOfEnd=0;
        for(int i=0;i<parent.length;i++)
        {
            if(visited[i]>componentLength){
                indexOfEnd=i;
                componentLength=visited[i];
            }
        }
        int [] path=new int[componentLength];
        int k=0;
        path[0]=indexOfEnd;
        while(parent[indexOfEnd]!=-1){
            indexOfEnd=parent[indexOfEnd];
            path[++k]=indexOfEnd;
        }
        return path;
    }

    public int numberOfComponents(int users) {
        int comm=0;
        for(int i =0;i<users;i++)
        {
            if(visited[i]==0){
                comm+=1;
                dfs(i);
            }
        }
        return comm;
    }
}
