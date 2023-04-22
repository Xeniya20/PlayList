package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.Manifest;
import android.widget.TextView;
import android.content.Intent;
public class MainActivity extends AppCompatActivity {
    ListView ListView;
    String[] items;
// In the onCreate() method, the ListView is initialized and the runtimePermission() method is called.
// В onCreate()методе инициализируется ListViewи runtimePermission()вызывается метод.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView = findViewById(R.id.ListViewSong);

        runtimePermission();
    }
// The app uses the Dexter library to request and manage permissions.
// Приложение использует библиотеку Dexter для запроса разрешений и управления ими.

    // The withListener() method is used to handle the result of the permission request.
    // If the user grants the requested permissions, the displaySongs() method is called.


    public void runtimePermission()
    {

        Dexter.withContext(this)
                .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO)
                .withListener(new MultiplePermissionsListener() {
                    //processing a permission request
                    //обработка запроса разрешения
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                        displaySongs();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {

                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }
// the method iterates through each file and checks whether the file ends with .mp3 or .wav using the endsWith() method.
// If yes, it uses the add() method and adds it to the return ArrayList

    // метод перебирает каждый файл и проверяет заканчивается ли файл .mp3 или .wav с помощью метода endsWith().
    // Ели да то использует метод add() и добавляет его в return arrayList
    public ArrayList<File> findSong (File file)
    {
           ArrayList<File> arrayList = new ArrayList<>();
           File[] files = file.listFiles();

           for (File singlefile: files)
           {
               if (singlefile.isDirectory() && !singlefile.isHidden())
               {
                   arrayList.addAll(findSong(singlefile));
               }
               else
               {
               if (singlefile.getName().endsWith(".mp3") || singlefile.getName().endsWith(".wav"))
               {
                   arrayList.add(singlefile);
               }
               }
           }
           return arrayList;
    }

    void displaySongs()
    {
        // The items array is initialized as an array of strings with a size equal to the number of items in mySongsArrayList.
        // Массив items инициализируется как массив строк с размером, равным количеству элементов в mySongsArrayList.
        final ArrayList<File> mySongs = findSong(Environment.getExternalStorageDirectory());
        items  = new String[mySongs.size()];
        for(int i = 0; i< mySongs.size();i++)
        {
            String target = ".mp3";
            items[i] = mySongs.get(i).getName().toString().replace(target, "").replace(".wav", "");
        }

        customAdapter customAdapter = new customAdapter();
        ListView.setAdapter(customAdapter);
// selecting a song on click
        // выбор песни по щелчку
        ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String songName = (String) ListView.getItemAtPosition(i);
                startActivity(new Intent(getApplicationContext(), MainActivity2_PlayerActivity.class)
                        .putExtra("songs", mySongs)
                        .putExtra("songname", songName)
                        .putExtra("pos", i)
                );
            }
        });
    }


    class customAdapter extends BaseAdapter
    {

        @Override
        public int getCount() {
            return items.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View myView = getLayoutInflater().inflate(R.layout.list_2, null);
            TextView textsong = myView.findViewById(R.id.txtsongname);
            textsong.setSelected(true);
            textsong.setText(items[i]);

            return myView;
        }
    }
}