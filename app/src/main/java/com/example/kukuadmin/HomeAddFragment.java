package com.example.kukuadmin;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class HomeAddFragment extends Fragment {

    private EditText editTextTitle;
    private ImageView imageView;
    private Uri imageUri;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home_add, container, false);

        editTextTitle = view.findViewById(R.id.editTextTitle);
        imageView = view.findViewById(R.id.imageView);
        Button buttonSelectImage = view.findViewById(R.id.buttonSelectImage);
        Button buttonUpload = view.findViewById(R.id.buttonUpload);

        databaseReference = FirebaseDatabase.getInstance().getReference("home");
        storageReference = FirebaseStorage.getInstance().getReference("home");

        buttonSelectImage.setOnClickListener(v -> selectImage());
        buttonUpload.setOnClickListener(v -> uploadData());
        return view;
    }
    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }

    private void uploadData() {
        if (imageUri != null && !editTextTitle.getText().toString().isEmpty()) {
            String title = editTextTitle.getText().toString().trim();

            final StorageReference fileRef = storageReference.child(System.currentTimeMillis() + ".jpeg");

            fileRef.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()) {
                        fileRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                if (task.isSuccessful()) {
                                    Uri downloadUri = task.getResult();
                                    storeImageData(title,downloadUri.toString());
                                }
                            }
                        });
                    }
                }
            });
        } else {
            Toast.makeText(getContext(), "Please select an image and enter a title", Toast.LENGTH_SHORT).show();
        }
    }
    private void storeImageData(String title,String imageUrl) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Home home= new Home(title,imageUrl);
                databaseReference.child(title).setValue(home)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    // Data stored successfully
                                    Toast.makeText(getContext(), "Data stored successfully", Toast.LENGTH_SHORT).show();
                                    editTextTitle.setText("");
                                    imageView.setImageResource(0);

                                    Log.d("Firebase", "Data stored successfully");
                                } else {
                                    // Handle the error
                                    Toast.makeText(getContext(), "Failed to store data", Toast.LENGTH_SHORT).show();
                                    Log.e("Firebase", "Failed to store data", task.getException());
                                }
                            }
                        });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(getContext(), "Error retrieving data count", Toast.LENGTH_SHORT).show();
                // Handle possible errors
                Log.e("Firebase", "Error retrieving data count", error.toException());
            }
        });
    }
}