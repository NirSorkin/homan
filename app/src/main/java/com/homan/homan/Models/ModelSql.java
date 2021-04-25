package com.homan.homan.Models;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ModelSql {

    public static ModelSql instance;


    public LiveData<List<Category>> getAll() {
        return AppLocalDB.db.categoryDao().getByCategoryType("Cars");
    }
    public interface GetAllCategoriesListener{
        void onComplete( List<Category> data);
    }

    public void getAllByCategory(Model.GetAllCategoriesListener listener, String type){
        class MyAsyncTask extends AsyncTask{
            LiveData<List<Category>> data;
            @Override
            protected Object doInBackground(Object[] objects) {
                data = AppLocalDB.db.categoryDao().getByCategoryType(type);
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                listener.onComplete(data);
            }
        }

        MyAsyncTask task = new MyAsyncTask();
        task.execute();

    }

    public interface  AddItemListener{
        void onComplete();
    }

    public void addItem(Category item, AddItemListener listener){
        class MyAsyncTask extends AsyncTask {
            @Override
            protected Object doInBackground(Object[] objects) {
                AppLocalDB.db.categoryDao().insertCategory(item);
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                if(listener != null){
                    listener.onComplete();
                }
            }
        };
        MyAsyncTask task = new MyAsyncTask();
        task.execute();
    }

    public interface DeleteListener {
        void onComplete();
    }
    public void delete(Category ct, DeleteListener listener) {
        class MyAsyncTask extends AsyncTask {
            @Override
            protected Object doInBackground(Object[] objects) {
                AppLocalDB.db.categoryDao().deleteCategory(ct);
                return null;
            }
            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                if (listener != null) listener.onComplete();
            }
        }
        MyAsyncTask task = new MyAsyncTask();
        task.execute();
    }
}
