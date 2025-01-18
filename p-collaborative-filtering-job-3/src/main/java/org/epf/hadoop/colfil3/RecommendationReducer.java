package org.epf.hadoop.colfil3;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RecommendationReducer extends Reducer<Text, Text, Text, Text> {

    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        List<Recommendation> recommendations = new ArrayList<>();

        for (Text value : values) {
            String[] parts = value.toString().split(":");
            if (parts.length < 2) continue;

            String recommendedUser = parts[0];
            int commonFriends = Integer.parseInt(parts[1]);

            recommendations.add(new Recommendation(recommendedUser, commonFriends));
        }

        Collections.sort(recommendations, Comparator.comparingInt(Recommendation::getCommonFriends).reversed());

        StringBuilder output = new StringBuilder();
        for (int i = 0; i < Math.min(5, recommendations.size()); i++) {
            if (i > 0) output.append(",");
            output.append(recommendations.get(i).getUser()).append(":").append(recommendations.get(i).getCommonFriends());
        }

        context.write(key, new Text(output.toString()));
    }

    static class Recommendation {
        private final String user;
        private final int commonFriends;

        public Recommendation(String user, int commonFriends) {
            this.user = user;
            this.commonFriends = commonFriends;
        }

        public String getUser() {
            return user;
        }

        public int getCommonFriends() {
            return commonFriends;
        }
    }
}