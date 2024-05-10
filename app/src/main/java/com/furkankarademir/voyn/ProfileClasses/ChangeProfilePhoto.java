package com.furkankarademir.voyn.ProfileClasses;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;


import com.furkankarademir.voyn.databinding.ActivityChangeProfilePhotoBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;

public class ChangeProfilePhoto extends AppCompatActivity {

    private ActivityChangeProfilePhotoBinding binding;

    private ActivityResultLauncher<Intent> activityResultLauncher;

    private Uri imageData;

    private Bitmap bitmap;

    private ActivityResultLauncher<String> permissionLauncher;

    private FirebaseStorage storage;

    private FirebaseAuth auth;

    private FirebaseFirestore firestore;

    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangeProfilePhotoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        storage = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        storageReference = storage.getReference();

        registerLauncher();
        /*binding.profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(ChangeProfilePhoto.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                {
                    if(ActivityCompat.shouldShowRequestPermissionRationale(ChangeProfilePhoto.this, Manifest.permission.READ_EXTERNAL_STORAGE))
                    {
                        Snackbar.make(v, "Permission needed for gallery", Snackbar.LENGTH_INDEFINITE)
                                .setAction("Give Permission", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
                                    }
                                }).show();
                    }
                    else {
                        permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
                    }
                }
                else {
                   Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                   activityResultLauncher.launch(intent);

                }
            }

        });*/
        /*binding.profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(ChangeProfilePhoto.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(ChangeProfilePhoto.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                }
                else {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    activityResultLauncher.launch(intent);
                }
            }
        });*/
        binding.profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(ChangeProfilePhoto.this, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED)
                {
                    if(ActivityCompat.shouldShowRequestPermissionRationale(ChangeProfilePhoto.this, Manifest.permission.READ_MEDIA_IMAGES))
                    {
                        Snackbar.make(v, "Permission needed for gallery", Snackbar.LENGTH_INDEFINITE)
                                .setAction("Give Permission", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES);
                                    }
                                }).show();
                    }
                    else {
                        // Direct the user to the app settings
                        Snackbar.make(v, "Permission needed for gallery", Snackbar.LENGTH_INDEFINITE)
                                .setAction("Settings", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent();
                                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                                        intent.setData(uri);
                                        startActivity(intent);
                                    }
                                }).show();
                    }
                }
                else {
                    Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    intent.setType("image/*");
                    activityResultLauncher.launch(intent);
                }
            }
        });


        /*binding.profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(ChangeProfilePhoto.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                {
                    permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
                }
                else {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    activityResultLauncher.launch(intent);
                }
            }
        });*/

        /*binding.profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(ChangeProfilePhoto.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                {
                    permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
                }
                else {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    activityResultLauncher.launch(intent);
                }
            }
        });*/
    }

    private void registerLauncher()
    {

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult o) {
                if(o.getResultCode() == RESULT_OK)
                {
                    Intent intent = o.getData();
                    if(intent != null)
                    {
                        imageData = intent.getData();
                        try {
                            if(Build.VERSION.SDK_INT >= 28)
                            {
                                ImageDecoder.Source source = ImageDecoder.createSource(ChangeProfilePhoto.this.getContentResolver(), imageData);
                                bitmap = ImageDecoder.decodeBitmap(source);
                                binding.profilePicture.setImageBitmap(bitmap);
                            }
                            else
                            {
                                bitmap = MediaStore.Images.Media.getBitmap(ChangeProfilePhoto.this.getContentResolver(), imageData);
                                binding.profilePicture.setImageBitmap(bitmap);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });


        /*permissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean result) {
                if(result)
                {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    activityResultLauncher.launch(intent);
                }
                else {
                    Snackbar.make(binding.getRoot(), "Permission needed for gallery", Snackbar.LENGTH_SHORT).show();
                }
            }
        });*/
        permissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean result) {
                if(result)
                {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    activityResultLauncher.launch(intent);
                }
                else {
                    Snackbar.make(binding.getRoot(), "Permission needed for gallery", Snackbar.LENGTH_SHORT).show();


                }
            }
        });
    }

    public void confirmButtonClicked(View view)
    {
        if(imageData != null)
        {
            storageReference.child("ProfilePhotos").child(auth.getUid()).putFile(imageData).addOnSuccessListener(taskSnapshot -> {

                Snackbar.make(view, "Profile photo uploaded", Snackbar.LENGTH_SHORT).show();
                // Get the download url of the image
                storageReference = storage.getReference();

                storageReference.child("ProfilePhotos").child(auth.getUid()).getDownloadUrl().addOnSuccessListener(uri -> {
                    firestore.collection("Users").document(auth.getUid()).update("profilePhotoUrl", uri.toString()).addOnSuccessListener(aVoid -> {
                        Snackbar.make(view, "Profile photo uploaded to user information", Snackbar.LENGTH_SHORT).show();
                    }).addOnFailureListener(e -> {
                        Snackbar.make(view, e.getLocalizedMessage(), Snackbar.LENGTH_SHORT).show();
                    });
                    
                }).addOnFailureListener(e -> {
                    Snackbar.make(view, e.getLocalizedMessage(), Snackbar.LENGTH_SHORT).show();
                });
            }).addOnFailureListener(e -> {
                Snackbar.make(view, e.getLocalizedMessage(), Snackbar.LENGTH_SHORT).show();
            });

            Intent intent = new Intent(ChangeProfilePhoto.this, EditProfile.class);
            startActivity(intent);
            finish();

        }

    }
    /*
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                activityResultLauncher.launch(intent);
            } else {
                Snackbar.make(binding.getRoot(), "Permission needed for gallery", Snackbar.LENGTH_SHORT).show();
            }
        }
    }*/


}