package com.example.tourismapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.OnClickListenerBottom, TopDesternations.OnClickListenerTop {

    RecyclerView bottomRecyclerView, topRecyclerView;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    String[] cityList = {"Melbourne", "Canberra", "Hobart", "Sydney", "Brisbane", "Adelaide", "Perth", "Darwin"};
    String[] infoList = {"There are many things to do in Melbourne. Visitors in Melbourne can go to museums, look at art, eat food at restaurants, go to shops and visitors can enjoy of international events like Australian Open and Formula One both in the early year.", "Canberra is the capital city of Australia. There are 403,468 people who live there.[1] It doesn’t belong to a state but it is in the Australian Capital Territory (ACT)", "Hobart is a city in Australia. It is the capital city of the state of Tasmania, and it has about 200,000 people.[1] It is on the western shore of the Derwent River, although some of its suburbs are on the eastern shore. Just west of Hobart is Mount Wellington. ", "In Sydney, there are many famous buildings: the Sydney Opera House, the Queen Victoria Building and the Sydney Harbour Bridge. Sydney has a large harbour and many beaches. The most famous beach is Bondi Beach, some other famous beaches are Coogee Beach and Manly Beach. A popular coastal walk to do is the Bondi to Coogee Coastal Walk.[8] Famous parts of the harbour are Darling Harbour and Circular Quay.", "Brisbane (local nickname Brissie) is the seaport capital city and biggest city of Queensland in Australia. It has a population of 2.2 million people and is the third biggest city in Australia, after Sydney and Melbourne.", "Adelaide is near the Southern Ocean and is north of the Fleurieu Peninsula. It has a river going through it called the River Torrens. Many festivals are held there. Adelaide has a hot-summer and cool-wet-winter Mediterranean climate. Grapes for wine production are grown in the Barossa Valley about 50 kilometres (30 miles) northeast of Adelaide, in the McLaren Vale about 30 kilometres south of Adelaide and parts of the Mt Lofty Ranges to the east.", "Perth is famous for its beautiful white, sandy beaches. Popular local beaches include Cottesloe and Scarborough. These beaches are ideal for swimming and bodysurfing. A popular local tourist attraction is Rottnest Island, which is populated by small native mammals called Quokkas. Another famous attraction is Kings Park, which is one of the biggest city parks in the world. ", "Most of Darwin has been built since 1974, when Cyclone Tracy ripped through and destroyed the city. Since then, the city has recovered. It is a very important port, because it is on the coast closest to Indonesia and the rest of Asia. Darwin also has a large military base. "};
    int images[] = {R.drawable.melbourne, R.drawable.canberra, R.drawable.hobart, R.drawable.sydney, R.drawable.brisbane, R.drawable.adelaide, R.drawable.perth, R.drawable.darwin};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomRecyclerView = findViewById(R.id.BOTTOM);
        topRecyclerView = findViewById(R.id.TOP);

        RecyclerViewAdapter BottomAdapter = new RecyclerViewAdapter(this, cityList, infoList, images, this);
        bottomRecyclerView.setAdapter(BottomAdapter);
        bottomRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        TopDesternations TopAdapter = new TopDesternations(this, images, this);
        topRecyclerView.setAdapter(TopAdapter);
        topRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

    }

    @Override
    public void onClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putString("Title", cityList[position]);
        bundle.putString("Info", infoList[position]);
        bundle.putInt("Image", images[position]);

        Fragment fragment;
        fragment = new Display();
        fragment.setArguments(bundle);


        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment).commit();
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragmentContainer);
        if (fragment!=null) {
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(fragment);
            fragmentTransaction.commit();
        } else {
            super.onBackPressed();
        }
    }
}