package com.pscube.trivia.data;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.pscube.trivia.MainActivity;
import com.pscube.trivia.controller.AppController;
import com.pscube.trivia.models.questions;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;

public class questionBank {

    private String url = "https://raw.githubusercontent.com/curiousily/simple-quiz/master/script/statements-data.json";


    ArrayList<questions> questonsArrayList = new ArrayList<>();

    public List<questions> getQuestions(){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET
                , url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("happy",""+response);
                for (int i = 0;i<response.length();i++){

                    try {
                        questions questions =new questions();
                        questions.setAnswer(response.getJSONArray(i).getString(0));
                        questions.setAnswerTrue(response.getJSONArray(i).getBoolean(1));
                        //adding this question to our array
                        questonsArrayList.add(  questions   );

//                        Log.d("happy", "onResponse:"+ response.getJSONArray(i).getString(0));
//                        Log.d("happy_again ", "onResponse:"+ response.getJSONArray(i).getBoolean(1));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }

        );

        AppController.getInstance().addToRequestQueue(jsonArrayRequest);



        return questonsArrayList;


    }
}
