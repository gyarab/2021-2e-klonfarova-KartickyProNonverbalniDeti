package com.example.rocnikovaprace;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class AktivityFragment extends Fragment {

    private AktivityViewModel mViewModel;
    String nazevslova = "";


    RecyclerView recyclerView;
    ArrayList<Slovicka> source;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    Adapter adapter;
    LinearLayoutManager HorizontalLayout;

    public static AktivityFragment newInstance() {
        return new AktivityFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        String c = "";
        File file = new File(getContext().getFilesDir(), "cislo.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            c =br.readLine();
        } catch (Exception e) {
            System.out.println("Chyba při čtení ze souboru.");
        }






        if(c.equals("ano")){

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, false))) {
                bw.write("ne");
                bw.newLine();
                bw.flush();

            } catch (Exception e) {
                System.out.println("Do souboru se nepovedlo zapsat.");
            }

            Intent i = new Intent(getContext(), MainActivity.class);
            startActivity(i);



        }


        View view = inflater.inflate(R.layout.aktivity_fragment, container, false);


        recyclerView
                = (RecyclerView) view.findViewById(
                R.id.recyclerview100);
        RecyclerViewLayoutManager
                = new

                LinearLayoutManager(
                getContext());

        // Přiřadí LayoutManager k Recycler View
        recyclerView.setLayoutManager(
                RecyclerViewLayoutManager);

        // Přidá položky do seznamu
        AddItemsToRecyclerViewArrayList();

        // Zavolá konstruktor
        adapter = new

                Adapter(source);

        // Nastaví Horizontal Layout Manager pro Recycler view
        HorizontalLayout
                = new

                LinearLayoutManager(
                getActivity().

                        getApplicationContext(),

                LinearLayoutManager.HORIZONTAL,
                false);
        recyclerView.setLayoutManager(HorizontalLayout);

        // Nastaví adapter pro recycler view
        recyclerView.setAdapter(adapter);


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AktivityViewModel.class);
        // TODO: Use the ViewModel
    }


    //Přidá položky do seznamu
    public void AddItemsToRecyclerViewArrayList() {
        source = new ArrayList<>();
        File file = new File(getContext().getFilesDir(), "rozvrh.txt");
        File file2 = new File(getContext().getFilesDir(), "aktivity.txt");
        //Nejdřív je načte ze souboru
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String s;
            int p = 0;
            while ((s = br.readLine()) != null) {
                Bitmap bitmap = new ImageSaver(getContext()).setFileName(s + ".png").setDirectoryName(file2.getName()).load();
                Slovicka slovo = new Slovicka(s, bitmap);
                //Potom je přidá do ArrayListu
                source.add(slovo);
                p++;
            }
        } catch (Exception e) {
            System.out.println("Chyba při čtení ze souboru.");
        }


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //if (notifyFragmentExit()) return false;
        switch(item.getItemId()){
            case R.id.nav_nastaveni2:
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_aktivity, NastaveniFragment.class, null).commit();

                break;
        }
        return super.onOptionsItemSelected(item);
    }

}