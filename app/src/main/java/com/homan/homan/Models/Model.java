package com.homan.homan.Models;

import android.os.AsyncTask;

import java.util.List;

public class Model {

    public final static Model instance = new Model();

    private Model() { }

    //Houses
    public interface GetAllHousesListener{
        void onComplete(List<Category> hosesList);
    }

    public void getAllHouses(final GetAllHousesListener listener){
        class MyAsynceTask extends AsyncTask{
            List<Category> housesList;
            @Override

            protected Object doInBackground(Object[] objects) {
                housesList = AppLocalDB.db.categoryDao().getAllCategories();
                return null;
            }

            @Override
            protected void onPostExecute( Object o ){
                super.onPostExecute(o);
                listener.onComplete(housesList);
            }
        }
        MyAsynceTask task  = new MyAsynceTask();
        task.execute();

    }

 /*   public void addCategory(Category category){
        AppLocalDB.db.categoryDao().insertCategory(category);
    }*/





}
