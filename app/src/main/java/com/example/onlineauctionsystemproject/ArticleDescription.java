package com.example.onlineauctionsystemproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ArticleDescription extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_description);
        Button bid= findViewById(R.id.upload);
        EditText min =findViewById(R.id.Name);
        EditText desc= findViewById(R.id.Description);
        EditText curr = findViewById(R.id.Minimum);
        Intent intent = getIntent();
        String itemName = intent.getStringExtra("Name");
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(itemName).child("Current Bid: ");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String currentBid = dataSnapshot.getValue(String.class);
                curr.setText(currentBid);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Database", "Failed to retrieve value: " + databaseError.getMessage());
            }
        });

        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference().child(itemName).child("Description");

        reference1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String currentBid = dataSnapshot.getValue(String.class);
                desc.setText(currentBid);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Database", "Failed to retrieve value: " + databaseError.getMessage());
            }
        });

        DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference().child(itemName).child("Min Bid: ");

        reference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String currentBid = dataSnapshot.getValue(String.class);
                min.setText(currentBid);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Database", "Failed to retrieve value: " + databaseError.getMessage());
            }
        });








        bid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(ArticleDescription.this);

                alert.setTitle("Amount");
                alert.setMessage("Enter Bidding Amount: ");

// Set an EditText view to get user input
                final EditText input = new EditText(ArticleDescription.this);
                alert.setView(input);

                alert.setPositiveButton("Bid", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String value = input.getText().toString();
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(itemName).child("Current Bid: ");

                        reference.setValue(value)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        // Value updated successfully
                                        Toast.makeText(getApplicationContext(),"Bid Successful",Toast.LENGTH_SHORT).show();
                                        Log.d("Database", "Value updated successfully");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Failed to update value
                                        Log.e("Database", "Failed to update value: " + e.getMessage());
                                    }
                                });


                        // Do something with value!





                        Toast.makeText(getApplicationContext(),"Bid Successfull!",Toast.LENGTH_SHORT);
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                    }
                });

                alert.show();
            }
        });
    }
}