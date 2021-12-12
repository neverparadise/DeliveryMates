package org.techtown.designthinking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseRef; // Realtime DB

    private AppCompatButton mBtnMyInfo;
    private AppCompatButton mBtnLogout;

    private ImageView iv_map;
    private ImageView iv_chat;
    private ImageView iv_board;

    public UserAccount userAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("DesignThinking/UserAccount");

        mBtnMyInfo = findViewById(R.id.btn_my_info);
        mBtnLogout = findViewById(R.id.btn_logout);
        mBtnMyInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDatabaseRef.child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        userAccount = snapshot.getValue(UserAccount.class);
                        String my_info = "데이터를 성공적으로 불러왔습니다." + '\n' + userAccount.getName() + '\n' + userAccount.getEmailId() + '\n' + userAccount.getStuId();
                        Toast.makeText(MainActivity.this, my_info, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.w("get userinfo failed : ", "getUser:onCancelled");
                    }
                });
            }
        });
        mBtnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFirebaseAuth.signOut();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        iv_map = findViewById(R.id.iv_map);
        iv_chat = findViewById(R.id.iv_chat);
        iv_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                intent.putExtra("userAccount", userAccount);
                startActivity(intent);
            }
        });
        iv_board = findViewById(R.id.iv_community);


    }
}