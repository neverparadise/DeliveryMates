package org.techtown.designthinking;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.techtown.designthinking.databinding.ActivityChatBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ChatActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    private FirebaseDatabase database;
    private DatabaseReference myref;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<ChatData> chatList;
    private String name;
    private AppBarConfiguration appBarConfiguration;

    private EditText et_chat;
    private AppCompatButton btn_send;


    public ChatActivity() {}


    //    mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).setValue(account);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Intent intent = getIntent();
        UserAccount userAccount = (UserAccount) intent.getSerializableExtra("userAccount");
        name = userAccount.getName() + userAccount.getStuId();
        database = FirebaseDatabase.getInstance();
        myref = database.getReference("DesignThinking/messages").child("message");

        btn_send = findViewById(R.id.btn_send);
        et_chat = findViewById(R.id.et_chat);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = et_chat.getText().toString();
                if (!msg.equals("")) {
                    ChatData chat = new ChatData();
                    chat.setName(name);
                    chat.setMessage(msg);
                    myref.push().setValue(chat);
                    et_chat.setText("");

//                    mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).setValue(account);

                }
                else {
                    Toast.makeText(ChatActivity.this, "메세지를 입력하세요.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        et_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });



        chatList = new ArrayList<>();
        mAdapter = new ChatAdapter(chatList, ChatActivity.this, name);

        mRecyclerView = findViewById(R.id.rv_chat);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        myref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Log.d("Chat : ", Objects.requireNonNull(snapshot.getValue()).toString());
                ChatData chat = snapshot.getValue(ChatData.class);
                ((ChatAdapter) mAdapter).addChat(chat);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}