package remote;

public class ControlMatchList {
    public static final String BASE_URL = "https://api.football-data.org/v2/";

    public static SoccerAPIService getSoccerAPIClient() {
        return RetrofitClient.getRetrofitClient().create(SoccerAPIService.class);
    }

}
