/*
package com.homan.homan.Models;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ModelRoom {
    public LiveData<List<Category>> getAll() {
        return AppLocalDB.db.categoryDao().getAllCategories();
    }

    public LiveData<List<Category>> getAllByCategoryType(String categoryType) {
        return AppLocalDB.db.categoryDao().getByCategoryType(categoryType);
    }

    public void getById(String id, Model.GetByIdListener listener) {
        class MyAsyncTask extends AsyncTask {
            private Category category;
            @Override
            protected Object doInBackground(Object[] objects) {
                Log.d("TAG", String.valueOf(id));
                category = AppLocalDB.db.categoryDao().getById(id);
                return null;
            }
            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                listener.onComplete(category);
            }
        }
        MyAsyncTask task = new MyAsyncTask();
        task.execute();
    }

    public interface AddListener {
        void onComplete();
    }
    public void add(Category category, AddListener listener) {
        class MyAsyncTask extends AsyncTask {
            @Override
            protected Object doInBackground(Object[] objects) {
                AppLocalDB.db.categoryDao().insertAll(category);
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

    public interface DeleteListener {
        void onComplete();
    }
    public void delete(Category category, DeleteListener listener) {
        class MyAsyncTask extends AsyncTask {
            @Override
            protected Object doInBackground(Object[] objects) {
                AppLocalDB.db.categoryDao().delete(category);
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
*/
