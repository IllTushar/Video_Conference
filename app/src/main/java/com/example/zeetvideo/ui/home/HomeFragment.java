package com.example.zeetvideo.ui.home;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telecom.InCallService;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.zeetvideo.R;
import com.example.zeetvideo.SignIn;
import com.example.zeetvideo.databinding.FragmentHomeBinding;
import com.google.android.material.textfield.TextInputEditText;

import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;

public class HomeFragment extends Fragment {
    TextInputEditText cID, eID;
    Button join, share;
    URL server;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //final TextView textView = binding.textHome;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        cID = root.findViewById(R.id.ID);
        //eID = root.findViewById(R.id.ExistingID);
        join = root.findViewById(R.id.join);
        share = root.findViewById(R.id.share);
        try {
            server = new URL("https://meet.jit.si");
            JitsiMeetConferenceOptions defaultOption = new JitsiMeetConferenceOptions.Builder().setServerURL(server)
                    .setWelcomePageEnabled(false).build();
            JitsiMeet.setDefaultConferenceOptions(defaultOption);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str1 = cID.getText().toString();
          //      String str2 = eID.getText().toString();
             String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}";
                if (str1.isEmpty()) {
                    Toast.makeText(getContext(), "Enter ID", Toast.LENGTH_LONG).show();
                }
                else {
                    if(!(str1.matches(pattern))){
                        Toast.makeText(getContext(),"Create Valid ID!!\neg:xyz123",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else {
                    if (!(str1.isEmpty())) {
                            JitsiMeetConferenceOptions conferenceOptions = new JitsiMeetConferenceOptions.Builder()
                                    .setRoom(str1).setWelcomePageEnabled(false).build();
                            JitsiMeetActivity.launch(getContext(), conferenceOptions);
                        }

                    }
                }
            }
        });

                  share.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
                          ClipboardManager clipboardManager = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                          ClipData data = ClipData.newPlainText("Text",cID.getText().toString());
                          clipboardManager.setPrimaryClip(data);
                          Intent i = new Intent();
                          i.setAction(Intent.ACTION_SEND);
                          i.putExtra(Intent.EXTRA_TEXT,cID.getText().toString());
                          i.setType("text/plain");
                          i = Intent.createChooser(i,"Share by");
                          startActivity(i);
                      }
                  });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}