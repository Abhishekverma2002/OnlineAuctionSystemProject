package com.example.onlineauctionsystemproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class SellingInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selling_info);
        Button upload= findViewById(R.id.upload);
        EditText name= findViewById(R.id.Name);
        EditText min = findViewById(R.id.Minimum);
        EditText desc= findViewById(R.id.Description);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!name.getText().toString().isEmpty() &&
                        !min.getText().toString().isEmpty() &&
                        !desc.getText().toString().isEmpty()) {
                    HashMap<String, Object> map = new HashMap<>();

                    map.put("Min Bid: ", min.getText().toString());
                    map.put("Current Bid: ", min.getText().toString());
                    map.put("Description", desc.getText().toString());

                    FirebaseDatabase.getInstance().getReference()
                            .child(name.getText().toString())
                            .updateChildren(map)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(),"Updated Successfully", Toast.LENGTH_SHORT).show();
                                    } else {
                                        // Handle error
                                    }
                                }
                            });
                }
                else{
                    Toast.makeText(getApplicationContext(),"Misentered",Toast.LENGTH_SHORT).show();
                }
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });




    }
}