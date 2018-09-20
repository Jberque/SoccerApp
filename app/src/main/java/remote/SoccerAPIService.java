package remote;
//ROUTES


import Model.match.MatchList;
import Model.standing.Standing;
import Model.team.TeamList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import Model.team.Team;
import retrofit2.http.Path;

public interface SoccerAPIService {

    @Headers("X-Auth-Token: 91134751c290476c8f76be8ba31fcd3d")
    @GET("/v2/competitions/FL1/standings")
    Call<Standing> getListFL1Team();

    @Headers("X-Auth-Token: 91134751c290476c8f76be8ba31fcd3d")
    @GET("/v2/teams/{id}/matches?competitions=FL1")
    Call<MatchList> getTeamMatches(@Path("id") String id);

    @Headers("X-Auth-Token: 91134751c290476c8f76be8ba31fcd3d")
    @GET("/v2/teams/{id}")
    Call<Team> getTeam(@Path("id") String id);

    @Headers("X-Auth-Token: 91134751c290476c8f76be8ba31fcd3d")
    @GET("/v2/teams?areas=2081,2000")
    Call<TeamList> getTeams();


}