package com.specjo.imagesearch.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.specjo.imagesearch.R;

public class FiltersActivity extends AppCompatActivity {
    public static final String SIZE = "size";
    public static final String COLOR = "color";
    public static final String TYPE = "type";
    public static final String FILETYPE = "filetype";

    private Spinner spImageSize;
    private Spinner spImageColor;
    private Spinner spImageType;
    private Spinner spImageFiletype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);

        getSupportActionBar().hide();

        populateSpinners();
    }

    private void populateSpinners() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);

        spImageSize = (Spinner) findViewById(R.id.spImageSize);
        ArrayAdapter<CharSequence> sizeAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.entries_image_size_preference,
                android.R.layout.simple_spinner_item);
        sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spImageSize.setAdapter(sizeAdapter);

        int bla = pref.getInt(SIZE, 0);
        spImageSize.setSelection(bla);

        spImageColor = (Spinner) findViewById(R.id.spImageColor);
        ArrayAdapter<CharSequence> colorAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.entries_image_color_preference,
                android.R.layout.simple_spinner_item);
        colorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spImageColor.setAdapter(colorAdapter);
        spImageColor.setSelection(pref.getInt(COLOR, 0));

        spImageType = (Spinner) findViewById(R.id.spImageType);
        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.entries_image_type_preference,
                android.R.layout.simple_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spImageType.setAdapter(typeAdapter);
        spImageType.setSelection(pref.getInt(TYPE, 0));

        spImageFiletype = (Spinner) findViewById(R.id.spImageFiletype);
        ArrayAdapter<CharSequence> filetypeAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.entries_image_filetype_preference,
                android.R.layout.simple_spinner_item);
        filetypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spImageFiletype.setAdapter(filetypeAdapter);
        spImageFiletype.setSelection(pref.getInt(FILETYPE, 0));
    }

    public void onSaveSettings(View view) {
        int size = spImageSize.getSelectedItemPosition();
        int color = spImageColor.getSelectedItemPosition();
        int type = spImageType.getSelectedItemPosition();
        int filetype = spImageFiletype.getSelectedItemPosition();

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit = pref.edit();
        edit.putInt(SIZE, size);
        edit.putInt(COLOR, color);
        edit.putInt(TYPE, type);
        edit.putInt(FILETYPE, filetype);
        edit.commit();

        finish();
    }
}
