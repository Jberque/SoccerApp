package Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.justin.soccerapp.R;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;



import Model.standing.StandingTeam;
import Model.team.Team;

import java.util.List;



public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {


    private List<StandingTeam> dataList;
    private Context context;
    PostItemListener mItemListener;

    public CustomAdapter(Context context, List<StandingTeam> dataList, PostItemListener itemListener){
        this.context = context;
        this.dataList = dataList;
        mItemListener = itemListener;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final View mView;
        PostItemListener mItemListener;


        TextView txtRank;
        TextView txtTitle;
        TextView txtNbPts;
        TextView txtMJ;
        TextView txtG;
        TextView txtN;
        TextView txtP;
        TextView txtBP;
        TextView txtBC;
        TextView txtDB;
        private ImageView coverImage;


        CustomViewHolder(View itemView, PostItemListener postItemListener) {
            super(itemView);
            mView = itemView;

            txtRank = mView.findViewById(R.id.rank);

            txtTitle = mView.findViewById(R.id.title);
            txtNbPts = mView.findViewById(R.id.nbPts);
            txtMJ = mView.findViewById(R.id.matchsPlayed);
            txtG = mView.findViewById(R.id.matchsWon);
            txtN = mView.findViewById(R.id.matchsNull);
            txtP = mView.findViewById(R.id.matchsLost);
            txtBP = mView.findViewById(R.id.BP);
            txtBC = mView.findViewById(R.id.BC);
            txtDB = mView.findViewById(R.id.DB);
            coverImage = mView.findViewById(R.id.coverImage);

            this.mItemListener = postItemListener;
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            Team team = getItem(getAdapterPosition());
            this.mItemListener.onPostClick(Integer.parseInt(team.getId()));

            notifyDataSetChanged();
        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_row, parent, false);
        return new CustomViewHolder(view, this.mItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.txtTitle.setText(dataList.get(position).getTeam().getName());

        holder.txtRank.setText("  "+String.valueOf(position+1)+"  ");
        holder.txtNbPts.setText(dataList.get(position).getPoints() + " PTS");
        holder.txtMJ.setText("    "+dataList.get(position).getPlayedGames()+ " MJ");
        holder.txtG.setText("    "+dataList.get(position).getWon()+ " G");
        holder.txtN.setText("    "+dataList.get(position).getDraw()+ " N");
        holder.txtP.setText("    "+dataList.get(position).getLost()+ " P");
        holder.txtBP.setText("    "+dataList.get(position).getGoalsFor()+ " BP");
        holder.txtBC.setText("    "+dataList.get(position).getGoalsAgainst()+ " BC");
        holder.txtDB.setText("    "+dataList.get(position).getGoalDifference()+ " DB");


//        Picasso.Builder builder = new Picasso.Builder(context);
//        builder.downloader(new OkHttp3Downloader(context));
//        builder.build().load(dataList.get(position).getTeam().getCrestUrl())
//                .placeholder((R.drawable.ic_launcher_background))
//                .error(R.color.grey)
//                .into(holder.coverImage);
////        Picasso.get()
////                .load(dataList.get(position).getTeam().getCrestUrl())
////                .into(holder.coverImage);
        Picasso.get()
                .load(dataList.get(position).getTeam().getCrestUrl())
                .error(R.drawable.ic_launcher_background)
                .into(holder.coverImage);


    }

    @Override

   public int getItemCount() {
        return dataList.size();
    }


    private Team getItem(int adapterPosition) {
        return dataList.get(adapterPosition).getTeam();
    }

    public interface PostItemListener {
        void onPostClick(int id);
    }

}
