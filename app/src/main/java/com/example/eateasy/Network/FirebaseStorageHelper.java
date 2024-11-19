package com.example.eateasy.Network;

import android.net.Uri;
import android.util.Log;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FirebaseStorageHelper {

    private FirebaseStorage storage;
    private StorageReference storageRef;

    public FirebaseStorageHelper() {
        storage = FirebaseStorage.getInstance("gs://project1-8e184.firebasestorage.app");
        storageRef = storage.getReference();
    }

    // Hàm upload ảnh
    public void uploadImage(Uri imageUri, OnImageUploadCallback callback) {
        if (imageUri == null) {
            callback.onFailure("Image URI is null");
            return;
        }

        // Đặt tên file dựa trên thời gian hiện tại
        String fileName = "images/" + System.currentTimeMillis() + ".jpg";
        StorageReference imageRef = storageRef.child(fileName);

        // Upload ảnh lên Firebase Storage
        imageRef.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> {
                    imageRef.getDownloadUrl()
                            .addOnSuccessListener(uri -> {
                                String downloadUrl = uri.toString();
                                Log.d("FirebaseStorage", "File uploaded successfully: " + downloadUrl);
                                callback.onSuccess(downloadUrl);
                            })
                            .addOnFailureListener(e -> {
                                Log.e("FirebaseStorage", "Failed to get download URL: " + e.getMessage());
                                callback.onFailure(e.getMessage());
                            });
                })
                .addOnFailureListener(e -> {
                    Log.e("FirebaseStorage", "Failed to upload file: " + e.getMessage());
                    callback.onFailure(e.getMessage());
                });


    }

    // Giao diện callback để xử lý kết quả
    public interface OnImageUploadCallback {
        void onSuccess(String imageUrl);
        void onFailure(String errorMessage);
    }
}
