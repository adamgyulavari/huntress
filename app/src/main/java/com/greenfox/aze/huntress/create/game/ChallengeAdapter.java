package com.greenfox.aze.huntress.create.game;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.greenfox.aze.huntress.R;
import com.greenfox.aze.huntress.model.Challenge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChallengeAdapter extends RecyclerView.Adapter<ChallengeAdapter.ChallengeViewHolder> implements SimpleTouchCallback.ItemTouchHelperAdapter {

    List<Challenge> challenges;

    public ChallengeAdapter() {
        this.challenges = new ArrayList<>();
    }

    @NonNull
    @Override
    public ChallengeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ChallengeViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_challenge, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChallengeViewHolder challengeViewHolder, int i) {
        Challenge current = challenges.get(i);
        challengeViewHolder.name.setText(current.name);
    }

    @Override
    public int getItemCount() {
        return challenges.size();
    }

    public void add(Challenge challenge) {
        challenges.add(challenge);
        notifyDataSetChanged();
    }

    public void setChallenges(List<Challenge> challenges) {
        this.challenges = challenges;
        notifyDataSetChanged();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(challenges, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(challenges, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        challenges.remove(position);
        notifyItemRemoved(position);
    }


    public static class ChallengeViewHolder extends RecyclerView.ViewHolder {

        TextView name, edit, remove;

        public ChallengeViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            edit = itemView.findViewById(R.id.btn_edit);
            remove = itemView.findViewById(R.id.btn_remove);
        }
    }
}
