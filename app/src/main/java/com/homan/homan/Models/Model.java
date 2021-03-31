/*

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.homan.homan.Models.Category;
import com.homan.homan.Models.ModelRoom;

import java.util.List;

public class Model {

    public final static Model instance = new Model();

    //private final ModelFirebase modelFirebase = new ModelFirebase();
    private final ModelRoom modelRoom = new ModelRoom();

    private LiveData<List<Category>> categories;

    private ProductModel() { }

    public LiveData<List<Category>> getAll() {
        if (categories == null) {
            categories = modelRoom.getAll();
        }
        fetchUpdatedDataFromFirebase(null);
        return categories;
    }

    public LiveData<List<Category>> getAllByOwner() {
        String ownerId = FirebaseAuth.getInstance().getUid();
        LiveData<List<Category>> productsByOwner = modelRoom.getAllByOwnerId(ownerId);
        //fetchUpdatedDataFromFirebase(null);
        return productsByOwner;
    }

    public interface UpdateListener {
        void onComplete();
    }
    private void fetchUpdatedDataFromFirebase(UpdateListener listener) {
        SharedPreferences sharedPreferences = MyApplication.context.getSharedPreferences("TAG", Context.MODE_PRIVATE);
        long lastUpdated = sharedPreferences.getLong("lastUpdated", 0);

      modelFirebase.getAll(lastUpdated, result -> {
            long lastU = 0;
            for (Product product : result) {
                modelRoom.add(product, null);
                if (product.getLastUpdated() > lastU) {
                    lastU = product.getLastUpdated();
                }
                if (product.getRemoved()) {
                    modelRoom.delete(product, null);
                }
            }
            sharedPreferences.edit().putLong("lastUpdated", lastU).apply();
            if (listener != null) listener.onComplete();
        })
;
    }

    public interface GetByIdListener {
        void onComplete(Category product);
    }
    public void getById(String id, GetByIdListener listener) {
        modelRoom.getById(id, listener);
    }

    public interface AddListener {
        void onComplete();
    }


  public void add(Category category, AddListener listener) {
        modelFirebase.add(category, listener);
    }


   public interface EditListener {
        void onComplete();
    }

    public void edit(Category oldVersion, Category newVersion, EditListener listener) {
        delete(oldVersion, null);
        add(newVersion, () -> {
            fetchUpdatedDataFromFirebase(listener::onComplete);
        });
    }


    public interface UploadImageListener {
        void onComplete(String url);
    }
    public void uploadImage(Bitmap imageBmp, String name, UploadImageListener listener) {
        modelFirebase.uploadImage(imageBmp, name, listener);
    }

    public interface DeleteListener {
        void onComplete();
    }
    public void delete(Product product, DeleteListener listener) {
        modelRoom.delete(product, null);
        modelFirebase.delete(product, listener);
    }

}
*/
