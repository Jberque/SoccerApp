package Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;


import com.example.justin.soccerapp.R;
import com.squareup.picasso.Picasso;


import Model.match.Match;
import Model.match.MatchList;
import Model.team.Team;

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.CustomViewHolder> {

    private MatchList dataList;
    private Context context;
    private PostItemListener mItemListener;
    private HashMap<String, Team> hashmap;

    public TeamAdapter(Context context, MatchList dataList, HashMap<String, Team> hashmap, PostItemListener itemListener) {
        this.context = context;
        this.dataList = dataList;
        this.hashmap = hashmap;
        this.mItemListener = itemListener;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final View mView;
        PostItemListener mItemListener;

        TextView txtDate;
        TextView txtNomEquipe1;
        TextView txtNbPtsEquipe1;
        TextView txtNbPtsEquipe2;
        TextView txtNomEquipe2;

        private ImageView coverImage;
        private ImageView coverImage2;

        CustomViewHolder(View itemView, PostItemListener postItemListener) {
            super(itemView);
            mView = itemView;

            txtDate = mView.findViewById(R.id.date);
            txtNomEquipe1 = mView.findViewById(R.id.nomEquipeActive);
            txtNomEquipe2 = mView.findViewById(R.id.nomEquipeAdverse);
            txtNbPtsEquipe1 = mView.findViewById(R.id.pointsEquipeActive);
            txtNbPtsEquipe2 = mView.findViewById(R.id.pointsEquipeAdverse);
            coverImage = mView.findViewById(R.id.coverImage);
            coverImage2 = mView.findViewById(R.id.coverImage2);

            this.mItemListener = postItemListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_row_team, parent, false);
        return new CustomViewHolder(view, this.mItemListener);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {

        String date = dataList.getMatches().get(position).getUtcDate().substring(0, 10);
        String finaldate = setUtcDateToDate(date);

        String matchday = dataList.getMatches().get(position).getMatchday();
        holder.txtDate.setText("  date du match: " + finaldate + "  journée n°: " + matchday);

        try {
            holder.txtNomEquipe1.setText("  " + hashmap.get(dataList.getMatches().get(position).getHomeTeam().getId()).getShortName());
        } catch (NullPointerException e) {
            holder.txtNomEquipe1.setText("  " + dataList.getMatches().get(position).getHomeTeam().getName());
        }

        try {
            holder.txtNomEquipe2.setText("  " + hashmap.get(dataList.getMatches().get(position).getAwayTeam().getId()).getShortName());
        } catch (NullPointerException e) {
            holder.txtNomEquipe2.setText("  " + dataList.getMatches().get(position).getAwayTeam().getName());
        }

        try {
            if (dataList.getMatches().get(position).getScore().getFullTime().getHomeTeam().equals("null") && dataList.getMatches().get(position).getScore().getFullTime().getAwayTeam().equals("null")) {
            } else {
                holder.txtNbPtsEquipe1.setText("  " + dataList.getMatches().get(position).getScore().getFullTime().getHomeTeam());
                holder.txtNbPtsEquipe2.setText("  " + dataList.getMatches().get(position).getScore().getFullTime().getAwayTeam() + "  ");
            }
        } catch (NullPointerException e) {
            holder.txtNbPtsEquipe1.setText("      ");
            holder.txtNbPtsEquipe2.setText("    ");
        }

        Picasso.get()
                .load(hashmap.get(dataList.getMatches().get(position).getAwayTeam().getId()).getCrestUrl())
                .resize(70, 70)
                .error(R.color.grey)
                .into(holder.coverImage);

        Picasso.get()
                .load(hashmap.get(dataList.getMatches().get(position).getHomeTeam().getId()).getCrestUrl())
                .resize(70, 70)
                .error(R.color.grey)
                .into(holder.coverImage2);

    }

    @Override
    public int getItemCount() {
        return dataList.getMatches().size();
    }


    private Match getItem(int adapterPosition) {
        return dataList.getMatches().get(adapterPosition);
    }

    public interface PostItemListener {
        void onPostClick(int id);
    }


    public String setUtcDateToDate(String unUtcDate) {
        String finalDate;

        String day = unUtcDate.substring(8);
        String month = unUtcDate.substring(5, 7);
        String year = unUtcDate.substring(0, 4);
        finalDate = day + "-" + month + "-" + year;
        return finalDate;
    }


}
