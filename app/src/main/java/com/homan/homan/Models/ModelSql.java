package com.homan.homan.Models;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import java.util.List;

public class ModelSql {

    public static ModelSql instance;


    public LiveData<List<Category>> getAll(String type) {
        return AppLocalDB.db.categoryDao().getByUserCategoryType(UserModel.instance.getEmail() , type);
    }


    public LiveData<List<Category>> setAll() {
        LiveData<List<Category>> temp = new LiveData<List<Category>>() {
            @Override
            public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super List<Category>> observer) {
                super.observe(owner, observer);
            }
        };
        return temp;
    }

    public LiveData<List<Category>> getAllByOwnerId(String userId) {
        return AppLocalDB.db.categoryDao().getAllByUserId(userId);
    }

    public interface GetAllCategoriesListener{
        void onComplete( List<Category> data);
    }


    public void getAllByCategoryAndID(Category ct){
        class MyAsyncTask extends AsyncTask{
            LiveData<List<Category>> data;
            @Override
            protected Object doInBackground(Object[] objects) {
                data = AppLocalDB.db.categoryDao().getByCategoryType(ct.getCategoryType(),ct.getDesc());
                return null;
            }

/*            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                listener.onComplete(data);
            }*/
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
