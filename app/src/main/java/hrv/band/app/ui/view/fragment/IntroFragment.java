package hrv.band.app.ui.view.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import hrv.band.app.R;

/**
 * Copyright (c) 2017
 * Created by Thomas Czogalik on 19.01.2017
 * <p>
 * Fragment showing one intro page.
 */
public class IntroFragment extends Fragment {

    /**
     * Key value for the tutorial title of this intro page (fragment).
     **/
    private static final String INTRO_TITLE_ID = "intro_title_id";
    /**
     * Key value for the tutorial description of this intro page (fragment).
     **/
    private static final String INTRO_DESC_ID = "intro_desc_id";
    /**
     * Key value for the tutorial image of this intro page (fragment).
     **/
    private static final String INTRO_IMAGE_ID = "intro_image_id";

    /**
     * Returns a new instance of this fragment.
     *
     * @return a new instance of this fragment.
     */
    public static IntroFragment newInstance(String title, String desc, int imageId) {
        IntroFragment fragment = new IntroFragment();
        Bundle args = new Bundle();
        args.putString(INTRO_TITLE_ID, title);
        args.putString(INTRO_DESC_ID, desc);
        args.putInt(INTRO_IMAGE_ID, imageId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.intro_fragment, container, false);

        TextView title = rootView.findViewById(R.id.intro_title);
        ImageView image = rootView.findViewById(R.id.intro_image);
        TextView desc = rootView.findViewById(R.id.intro_desc);

        title.setText(getArguments().getString(INTRO_TITLE_ID));

        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inSampleSize = 2;

        Bitmap img = BitmapFactory.decodeResource(getResources(), getArguments().getInt(INTRO_IMAGE_ID), o);
        image.setImageBitmap(img);

        desc.setText(getArguments().getString(INTRO_DESC_ID));

        return rootView;
    }
}
