package com.example.justin.soccerapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import Adapter.CustomAdapter;
import Model.standing.Standing;
import Model.standing.StandingTeam;
import Model.standing.StandingType;
import Model.team.Team;
import Model.team.TeamList;
import remote.ControlMatchList;
import remote.SoccerAPIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private CustomAdapter adapter;
    private RecyclerView recyclerView;
    private TextView textview;
    private List<String> theTeamList;

    ProgressDialog progressDoalog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressDoalog = new ProgressDialog(MainActivity.this);
        progressDoalog.setMessage("Loading....");
        progressDoalog.show();

        /*Create handle for the RetrofitInstance interface*/
        SoccerAPIService service = ControlMatchList.getSoccerAPIClient();

        Call<Standing> call = service.getListFL1Team();
        call.enqueue(new Callback<Standing>() {
            @Override
            public void onResponse(@NonNull Call<Standing> call, @NonNull Response<Standing> response) {
                progressDoalog.dismiss();
                generateDataList(response.body().getStandings().get(0).getTable());
            }

            @Override
            public void onFailure(@NonNull Call<Standing> call, @NonNull Throwable t) {
                progressDoalog.dismiss();
                Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void generateDataList(final List<StandingTeam> teamList) {
        recyclerView = findViewById(R.id.customRecyclerView);

        adapter = new CustomAdapter(this, teamList, new CustomAdapter.PostItemListener() {
            @Override
            public void onPostClick(int id) {

                Intent teamActivity = new Intent(MainActivity.this, TeamActivity.class);
                teamActivity.putExtra("team", id);
                startActivity(teamActivity);

            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }
}
