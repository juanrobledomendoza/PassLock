package com.example.passlock.util;

import android.util.Log;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AiSuggestionService {

    private static final String OPENAI_URL = "https://api.openai.com/v1/chat/completions";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private static final OkHttpClient client = new OkHttpClient();

    @Nullable
    public static String getPasswordTipSync(String password, int score, String feedback) {
        String apiKey ="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" ;
        if (apiKey == null || apiKey.isEmpty()) {
            return null;
        }

        JSONObject root = new JSONObject();
        try {
            root.put("model", "gpt-4o-mini");

            JSONArray messages = new JSONArray();

            JSONObject systemMsg = new JSONObject();
            systemMsg.put("role", "system");
            systemMsg.put("content", "You are a helpful assistant that gives short, student-friendly password-strength tips.");
            messages.put(systemMsg);

            JSONObject userMsg = new JSONObject();
            userMsg.put("role", "user");
            userMsg.put("content",
                    "Given this password, its numeric strength score (0-100), and existing feedback, " +
                            "write 1-2 short sentences of additional advice for a student. Be concise.\n" +
                            "Password: " + password + "\n" +
                            "Score: " + score + "\n" +
                            "Existing feedback: " + feedback);
            messages.put(userMsg);

            root.put("messages", messages);
            root.put("temperature", 0.3);
            root.put("max_tokens", 80);

        } catch (JSONException e) {
            return null;
        }

        RequestBody body = RequestBody.create(root.toString(), JSON);

        Request request = new Request.Builder()
                .url(OPENAI_URL)
                .header("Authorization", "Bearer " + apiKey)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful() || response.body() == null) {
                Log.e("AiSuggestionService", "OpenAI request failed: code=" + response.code());
                if (response.body() != null) {
                    String errorBody = response.body().string();
                    Log.e("AiSuggestionService", "Error body: " + errorBody);
                }
                return null;
            }
            String respStr = response.body().string();
            Log.d("AiSuggestionService", "OpenAI response: " + respStr);
            JSONObject respJson = new JSONObject(respStr);
            JSONArray choices = respJson.optJSONArray("choices");
            if (choices == null || choices.length() == 0) return null;
            JSONObject choice0 = choices.getJSONObject(0);
            JSONObject message = choice0.optJSONObject("message");
            if (message == null) return null;
            return message.optString("content", null);
        } catch (IOException | JSONException e) {
            Log.e("AiSuggestionService", "Exception calling OpenAI", e);
            return null;
        }
    }
}
