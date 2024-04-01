package com.example.finalproject.views;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.finalproject.MusicalRecyclerAdapter;
import com.example.finalproject.R;
import com.example.finalproject.databinding.FragmentMusicalsListBinding;
import com.example.finalproject.repositories.RecipesRepository;
import com.example.finalproject.model.Recipe;
import java.util.LinkedList;
import java.util.List;

public class MusicalsListFragment extends Fragment {

    List<Recipe> data = new LinkedList<>();
    MusicalRecyclerAdapter adapter;
    FragmentMusicalsListBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentMusicalsListBinding.inflate(inflater,container,false);
        View view = binding.getRoot();

        binding.musicalFragList.setHasFixedSize(true);
        binding.musicalFragList.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new MusicalRecyclerAdapter(getLayoutInflater(),data);
        binding.musicalFragList.setAdapter(adapter);

        LiveData<List<Recipe>> data = RecipesRepository.instance.getRecipes();
        data.observe(getViewLifecycleOwner(),list-> {
            adapter.setData(list);
            binding.progressBar.setVisibility(View.GONE);
        } );

        adapter.setOnItemClickListener(new MusicalRecyclerAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(int pos) {
                Bundle bundle = new Bundle();
                Recipe mus = data.getValue().get(pos);
                bundle.putParcelable("Musical", mus);
                Navigation.findNavController(view)
                        .navigate(R.id.action_musicalsListFragment_to_musicalFragment, bundle);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        reloadData();
    }

    void reloadData(){
        binding.progressBar.setVisibility(View.VISIBLE);
    }
}