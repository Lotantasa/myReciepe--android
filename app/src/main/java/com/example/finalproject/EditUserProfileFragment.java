package com.example.finalproject;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.finalproject.model.ImageModel;
import com.example.finalproject.model.User;
import com.example.finalproject.databinding.FragmentEditUserProfileBinding;
import com.example.finalproject.model.UserModel;
import com.squareup.picasso.Picasso;

public class EditUserProfileFragment extends Fragment {

    FragmentEditUserProfileBinding binding;
    ActivityResultLauncher<Void> cameraLauncher;
    Boolean isImgSelected = false;
    User user;

    public EditUserProfileFragment() {}

    private void setParameters(User user) {
        this.user = user;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setParameters(EditUserProfileFragmentArgs.fromBundle(getArguments()).getUser());

        binding = FragmentEditUserProfileBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        binding.firstNameTp.setText(this.user.getFirstName());
        binding.lastNameTp.setText(this.user.getLastName());
        binding.mailTp.setText(this.user.getMail());
        binding.bioTp.setText(this.user.getBio());

        cameraLauncher = registerForActivityResult(new ActivityResultContracts.TakePicturePreview(), new ActivityResultCallback<Bitmap>() {
            @Override
            public void onActivityResult(Bitmap result) {
                if (result != null) {
                    binding.userImg.setImageBitmap(result);
                    isImgSelected = true;
                }
            }
        });

        if(user.getImgUrl() != null && user.getImgUrl() != "") {
            Picasso.get().load(user.getImgUrl()).placeholder(R.drawable.bear).into(binding.userImg);
        } else {
            binding.userImg.setImageResource(R.drawable.bear);
        }

        onPhotoClick(user);
        onSave(view);
        onCancel();

        return view;
    }

    private void onPhotoClick(User user) {
        binding.userImg.setOnClickListener(View -> {
            cameraLauncher.launch(null);
        });
    }

    private void uploadImg(User user, ImageModel.UploadImageListener callback) {
            binding.userImg.setDrawingCacheEnabled(true);
            binding.userImg.buildDrawingCache();
            Bitmap bitmap = ((BitmapDrawable) binding.userImg.getDrawable()).getBitmap();
            ImageModel.instance.uploadImage(user.getUid(), bitmap, callback);
    }

    private void onCancel() {
        NavDirections action = EditUserProfileFragmentDirections.actionEditUserProfileFragmentToUserProfileFragment();
        binding.cancelEditBtn.setOnClickListener(Navigation.createNavigateOnClickListener(action));
    }

    public void onSave(View view){
        binding.saveEditBtn.setOnClickListener(View -> {
            User editedUser = new User(this.user.getPassword(),
                    binding.mailTp.getText().toString(),
                    binding.firstNameTp.getText().toString(),
                    binding.lastNameTp.getText().toString(),
                    binding.bioTp.getText().toString(), this.user.getUid(),
                    user.getImgUrl());

            if(isImgSelected) {
                uploadImg(editedUser, (url) -> {
                    if(url != null) {
                        editedUser.setImgUrl(url);
                        saveUserNewData(editedUser, view);
                    }
                });
            } else {
                saveUserNewData(editedUser, view);
            }
        });
    }

    private void saveUserNewData(User editedUser, View view) {
        UserModel.instance.updateUser(editedUser, (unused) -> Navigation.findNavController(view)
                .navigate(R.id.action_editUserProfileFragment_to_userProfileFragment));
    }
}