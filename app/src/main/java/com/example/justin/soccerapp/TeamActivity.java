package com.example.justin.soccerapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import Adapter.CustomAdapter;
import Adapter.TeamAdapter;
import Model.match.Match;
import Model.match.MatchList;
import Model.standing.Standing;
import Model.standing.StandingTeam;
import Model.team.Team;
import Model.team.TeamList;
import remote.ControlMatchList;
import remote.SoccerAPIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeamActivity extends AppCompatActivity {
    private MatchList matchlist;
    private TeamList teamlist;
    private HashMap<String, Team> hsmp;
    private Team team;
    ProgressDialog progressDoalog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        progressDoalog = new ProgressDialog(TeamActivity.this);
        progressDoalog.setMessage("Loading....");
        progressDoalog.show();
        hsmp = new HashMap<String, Team>();

        int idTeamIntent = Objects.requireNonNull(getIntent().getExtras()).getInt("team");

        SoccerAPIService service = ControlMatchList.getSoccerAPIClient();

        Call<MatchList> call = service.getTeamMatches(String.valueOf(idTeamIntent));
        final Call<TeamList> call2 = service.getTeams();

        call.enqueue(new Callback<MatchList>() {
            @Override
            public void onResponse(@NonNull Call<MatchList> call, @NonNull Response<MatchList> response) {

                matchlist = response.body();

            }

            @Override
            public void onFailure(@NonNull Call<MatchList> call, @NonNull Throwable t) {
                Toast.makeText(TeamActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }

        });

        call2.enqueue(new Callback<TeamList>() {
            @Override
            public void onResponse(@NonNull Call<TeamList> call, @NonNull Response<TeamList> response) {

                teamlist = response.body();
                for (Team team : teamlist.getTeams()
                        ) {
                    hsmp.put(team.getId(), team);
                }
                progressDoalog.dismiss();
                generateDataList(matchlist, hsmp);


            }

            @Override
            public void onFailure(@NonNull Call<TeamList> call, @NonNull Throwable t) {
                progressDoalog.dismiss();
                Toast.makeText(TeamActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void generateDataList(MatchList matchList, HashMap<String, Team> hsmap) {
        RecyclerView recyclerView = findViewById(R.id.customRecyclerView2);
        TeamAdapter adapter = new TeamAdapter(this, matchList, hsmap, new TeamAdapter.PostItemListener() {
            @Override
            public void onPostClick(int id) {

            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(TeamActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

}
